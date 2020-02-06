/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources.fluentcore.utils;

import com.azure.core.http.HttpPipelineCallContext;
import com.azure.core.http.HttpPipelineNextPolicy;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.HttpResponse;
import com.azure.core.http.policy.HttpPipelinePolicy;
import com.azure.core.util.DateTimeRfc1123;
import com.azure.core.util.FluxUtil;
import com.azure.core.util.logging.ClientLogger;
import com.azure.management.resources.fluentcore.arm.ResourceUtils;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Http Pipeline Policy for automatic retry when Azure Resource Manager is throttling because of too many read/write requests.
 * <p>
 * For each subscription and tenant, Azure Resource Manager limits read requests to 15,000 per hour and
 *   write requests to 1,200 per hour. These limits apply to each Azure Resource Manager instance.
 */
public class ResourceManagerThrottlingPolicy implements HttpPipelinePolicy {
    private static final ConcurrentMap<String, ReentrantLock> REENTRANT_LOCK_MAP = new ConcurrentHashMap<>();

    @Override
    public Mono<HttpResponse> process(HttpPipelineCallContext context, HttpPipelineNextPolicy next) {
        final HttpRequest request = context.getHttpRequest();
        String extractSubscriptionId = ResourceUtils.extractFromResourceId(request.getUrl().getPath(), "subscriptions");
        if (extractSubscriptionId == null) {
            extractSubscriptionId = "global";
        }
        final String subscriptionId = extractSubscriptionId;
        REENTRANT_LOCK_MAP.putIfAbsent(subscriptionId, new ReentrantLock());
        try {
            synchronized (REENTRANT_LOCK_MAP.get(subscriptionId)) {
                if (REENTRANT_LOCK_MAP.get(subscriptionId).isLocked()) {
                    REENTRANT_LOCK_MAP.get(subscriptionId).wait();
                }
            }
        } catch (InterruptedException e) {
            return Mono.error(e);
        }

        return next.clone().process().flatMap(
            response -> {
                if (response.getStatusCode() != 429) {
                    return Mono.just(response);
                }

                try {
                    synchronized (REENTRANT_LOCK_MAP.get(subscriptionId)) {
                        if (REENTRANT_LOCK_MAP.get(subscriptionId).isLocked()) {
                            REENTRANT_LOCK_MAP.get(subscriptionId).wait();
                            return next.process();
                        } else {
                            REENTRANT_LOCK_MAP.get(subscriptionId).lock();
                        }
                    }
                } catch (InterruptedException e) {
                    return Mono.error(e);
                }

                try {
                    return FluxUtil.collectBytesInByteBufferStream(response.getBody()).flatMap(
                        body -> {
                            String bodyStr = new String(body, StandardCharsets.UTF_8);

                            String retryAfterHeader = response.getHeaderValue("Retry-After");
                            int retryAfter = 0;
                            if (retryAfterHeader != null) {
                                OffsetDateTime retryWhen = null;
                                try {
                                    retryWhen = new DateTimeRfc1123(retryAfterHeader).getDateTime();
                                } catch (Exception e) { }
                                if (retryWhen == null) {
                                    retryAfter = Integer.parseInt(retryAfterHeader);
                                } else {
                                    retryAfter = (int)Duration.between(Instant.now(), retryWhen).getSeconds();
                                }
                            }

                            if (retryAfter <= 0) {
                                FluxUtil.collectBytesInByteBufferStream(response.getBody());
                                Pattern pattern = Pattern.compile("try again after '([0-9]*)' minutes", Pattern.CASE_INSENSITIVE);
                                Matcher matcher = pattern.matcher(bodyStr);
                                if (matcher.find()) {
                                    retryAfter = (int) TimeUnit.MINUTES.toSeconds(Integer.parseInt(matcher.group(1)));
                                } else {
                                    pattern = Pattern.compile("try again after '([0-9]*)' seconds", Pattern.CASE_INSENSITIVE);
                                    matcher = pattern.matcher(bodyStr);
                                    if (matcher.find()) {
                                        retryAfter = Integer.parseInt(matcher.group(1));
                                    }
                                }
                            }

                            if (retryAfter > 0) {
                                final ClientLogger logger = new ClientLogger((String) context.getData("caller-method").orElse(""));
                                logger.info("Azure Resource Manager read/write per hour limit reached. Will retry in: " + retryAfter + " seconds");
                                SdkContext.sleep((int) (TimeUnit.SECONDS.toMillis(retryAfter) + 100));
                            }
                            return next.process();
                        }
                    );
                } catch (Throwable t) {
                    return Mono.error(t);
                } finally {
                    response.close();
                    synchronized (REENTRANT_LOCK_MAP.get(subscriptionId)) {
                        REENTRANT_LOCK_MAP.get(subscriptionId).unlock();
                        REENTRANT_LOCK_MAP.get(subscriptionId).notifyAll();
                    }
                }
            }
        );
    }
}
