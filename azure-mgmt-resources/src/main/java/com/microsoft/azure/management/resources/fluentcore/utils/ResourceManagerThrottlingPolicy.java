// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.azure.management.resources.fluentcore.utils;

import com.azure.core.http.HttpPipelineCallContext;
import com.azure.core.http.HttpPipelineNextPolicy;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.HttpResponse;
import java.util.Objects;

import com.azure.core.http.policy.ExponentialBackoff;
import com.azure.core.http.policy.HttpPipelinePolicy;
import com.azure.core.http.policy.RetryStrategy;
import com.azure.core.util.CoreUtils;
import com.azure.core.util.logging.ClientLogger;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * A pipeline policy that retries when a recoverable HTTP error occurs.
 */
public class ResourceManagerThrottlingPolicy implements HttpPipelinePolicy {

    private static final String RETRY_AFTER_MS_HEADER = "retry-after-ms";

    private final ClientLogger logger = new ClientLogger(ResourceManagerThrottlingPolicy.class);
    private final RetryStrategy retryStrategy;

    /**
     * Creates a default {@link ExponentialBackoff} retry policy.
     */
    public ResourceManagerThrottlingPolicy() {
        this(new ExponentialBackoff());
    }

    /**
     * Creates a RetryPolicy with the provided {@link RetryStrategy}.
     *
     * @param retryStrategy The {@link RetryStrategy} used for retries.
     */
    public ResourceManagerThrottlingPolicy(RetryStrategy retryStrategy) {
        this.retryStrategy = Objects.requireNonNull(retryStrategy, "'retryStrategy' cannot be null");
    }

    @Override
    public Mono<HttpResponse> process(HttpPipelineCallContext context, HttpPipelineNextPolicy next) {
        return attemptAsync(context, next, context.getHttpRequest(), 0);
    }

    private Mono<HttpResponse> attemptAsync(final HttpPipelineCallContext context, final HttpPipelineNextPolicy next,
                                            final HttpRequest originalHttpRequest, final int tryCount) {
        context.setHttpRequest(originalHttpRequest.copy());
        return next.clone().process()
                .flatMap(httpResponse -> {
                    if (shouldRetry(httpResponse, tryCount)) {
                        final Duration delayDuration = determineDelayDuration(httpResponse, tryCount);
                        logger.verbose("[Retrying] Try count: {}, Delay duration in seconds: {}", tryCount,
                                delayDuration.getSeconds());
                        return attemptAsync(context, next, originalHttpRequest, tryCount + 1)
                                .delaySubscription(delayDuration);
                    } else {
                        return Mono.just(httpResponse);
                    }
                })
                .onErrorResume(err -> {
                    int maxRetries = retryStrategy.getMaxRetries();
                    if (tryCount < maxRetries) {
                        logger.verbose("[Error Resume] Try count: {}, Error: {}", tryCount, err);
                        return attemptAsync(context, next, originalHttpRequest, tryCount + 1)
                                .delaySubscription(retryStrategy.calculateRetryDelay(tryCount));
                    } else {
                        return Mono.error(new RuntimeException(
                                String.format("Max retries %d times exceeded. Error Details: %s", maxRetries, err.getMessage()),
                                err));
                    }
                });
    }

    private boolean shouldRetry(HttpResponse response, int tryCount) {
        return tryCount < retryStrategy.getMaxRetries() && retryStrategy.shouldRetry(response);
    }

    /**
     * Determines the delay duration that should be waited before retrying.
     * @param response HTTP response
     * @return If the HTTP response has a retry-after-ms header that will be returned,
     *     otherwise the duration used during the construction of the policy.
     */
    private Duration determineDelayDuration(HttpResponse response, int tryCount) {
        int code = response.getStatusCode();

        // Response will not have a retry-after-ms header.
        if (code != 429        // too many requests
                && code != 503) {  // service unavailable
            return retryStrategy.calculateRetryDelay(tryCount);
        }

        String retryHeader = response.getHeaderValue(RETRY_AFTER_MS_HEADER);

        // Retry header is missing or empty, return the default delay duration.
        if (CoreUtils.isNullOrEmpty(retryHeader)) {
            return retryStrategy.calculateRetryDelay(tryCount);
        }

        // Use the response delay duration, the server returned it for a reason.
        return Duration.ofMillis(Integer.parseInt(retryHeader));
    }
}


///**
// * Copyright (c) Microsoft Corporation. All rights reserved.
// * Licensed under the MIT License. See License.txt in the project root for
// * license information.
// */
//
//package com.microsoft.azure.management.resources.fluentcore.utils;
//
//import com.azure.core.http.HttpPipeline;
//import com.azure.core.http.HttpPipelineCallContext;
//import com.azure.core.http.HttpPipelineNextPolicy;
//import com.azure.core.http.HttpResponse;
//import com.azure.core.http.policy.HttpPipelinePolicy;
//import com.azure.core.implementation.DateTimeRfc1123;
//import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
//import org.joda.time.DateTime;
//import org.joda.time.Duration;
//import org.slf4j.LoggerFactory;
//import reactor.core.publisher.Mono;
//
//import java.io.IOException;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.ReentrantLock;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
///**
// * An interceptor for automatic retry when Azure Resource Manager is throttling because of too many read/write requests.
// * <p>
// * For each subscription and tenant, Azure Resource Manager limits read requests to 15,000 per hour and
// *   write requests to 1,200 per hour. These limits apply to each Azure Resource Manager instance.
// */
//public class ResourceManagerThrottlingPolicy implements HttpPipelinePolicy {
//    private static final String LOGGING_HEADER = "x-ms-logging-context";
//    private static final ConcurrentMap<String, ReentrantLock> REENTRANT_LOCK_MAP = new ConcurrentHashMap<>();
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        // Gate control
//        String subscriptionId = ResourceUtils.extractFromResourceId(chain.request().url().url().getPath(), "subscriptions");
//        if (subscriptionId == null) {
//            subscriptionId = "global";
//        }
//        REENTRANT_LOCK_MAP.putIfAbsent(subscriptionId, new ReentrantLock());
//        try {
//            synchronized (REENTRANT_LOCK_MAP.get(subscriptionId)) {
//                if (REENTRANT_LOCK_MAP.get(subscriptionId).isLocked()) {
//                    REENTRANT_LOCK_MAP.get(subscriptionId).wait();
//                }
//            }
//        } catch (InterruptedException e) {
//            throw new IOException(e);
//        }
//        Response response = chain.proceed(chain.request());
//        if (response.code() != 429) {
//            return response;
//        }
//
//        try {
//            synchronized (REENTRANT_LOCK_MAP.get(subscriptionId)) {
//                if (REENTRANT_LOCK_MAP.get(subscriptionId).isLocked()) {
//                    REENTRANT_LOCK_MAP.get(subscriptionId).wait();
//                    return chain.proceed(chain.request());
//                } else {
//                    REENTRANT_LOCK_MAP.get(subscriptionId).lock();
//                }
//            }
//        } catch (InterruptedException e) {
//            throw new IOException(e);
//        }
//
//        try {
//            String retryAfterHeader = response.header("Retry-After");
//            int retryAfter = 0;
//            if (retryAfterHeader != null) {
//                DateTime retryWhen = null;
//                try {
//                    retryWhen = new DateTimeRfc1123(retryAfterHeader).dateTime();
//                } catch (Exception e) { }
//                if (retryWhen == null) {
//                    retryAfter = Integer.parseInt(retryAfterHeader);
//                } else {
//                    retryAfter = new Duration(null, retryWhen).toStandardSeconds().getSeconds();
//                }
//            }
//            if (retryAfter <= 0) {
//                Pattern pattern = Pattern.compile("try again after '([0-9]*)' minutes", Pattern.CASE_INSENSITIVE);
//                Matcher matcher = pattern.matcher(content(response.body()));
//                if (matcher.find()) {
//                    retryAfter = (int) TimeUnit.MINUTES.toSeconds(Integer.parseInt(matcher.group(1)));
//                } else {
//                    pattern = Pattern.compile("try again after '([0-9]*)' seconds", Pattern.CASE_INSENSITIVE);
//                    matcher = pattern.matcher(content(response.body()));
//                    if (matcher.find()) {
//                        retryAfter = Integer.parseInt(matcher.group(1));
//                    }
//                }
//            }
//            if (retryAfter > 0) {
//                String context = chain.request().header(LOGGING_HEADER);
//                if (context == null) {
//                    context = "";
//                }
//                LoggerFactory.getLogger(context)
//                    .info("Azure Resource Manager read/write per hour limit reached. Will retry in: " + retryAfter + " seconds");
//                SdkContext.sleep((int) (TimeUnit.SECONDS.toMillis(retryAfter) + 100));
//            }
//            return chain.proceed(chain.request());
//        } catch (Throwable t) {
//            throw new IOException(t);
//        } finally {
//            if (response.body() != null) {
//                response.body().close();
//            }
//            synchronized (REENTRANT_LOCK_MAP.get(subscriptionId)) {
//                REENTRANT_LOCK_MAP.get(subscriptionId).unlock();
//                REENTRANT_LOCK_MAP.get(subscriptionId).notifyAll();
//            }
//        }
//    }
//
//    private String content(ResponseBody responseBody) throws IOException {
//        if (responseBody == null) {
//            return null;
//        }
//        BufferedSource source = responseBody.source();
//        source.request(Long.MAX_VALUE); // Buffer the entire body.
//        Buffer buffer = source.buffer();
//        return buffer.readUtf8();
//    }
//
//    @Override
//    public Mono<HttpResponse> process(HttpPipelineCallContext httpPipelineCallContext, HttpPipelineNextPolicy httpPipelineNextPolicy) {
//        // Gate control
//        String subscriptionId = ResourceUtils.extractFromResourceId(httpPipelineCallContext.getHttpRequest().getUrl().getPath(), "subscriptions");
//        if (subscriptionId == null) {
//            subscriptionId = "global";
//        }
//        REENTRANT_LOCK_MAP.putIfAbsent(subscriptionId, new ReentrantLock());
//        try {
//            synchronized (REENTRANT_LOCK_MAP.get(subscriptionId)) {
//                if (REENTRANT_LOCK_MAP.get(subscriptionId).isLocked()) {
//                    REENTRANT_LOCK_MAP.get(subscriptionId).wait();
//                }
//            }
//        } catch (InterruptedException e) {
//            throw new IOException(e);
//        }
//        HttpResponse response = httpPipelineNextPolicy.process(httpPipelineCallContext, httpPipelineNextPolicy).block();
//        if (response.getStatusCode() != 429) {
//            return response;
//        }
//
//        try {
//            synchronized (REENTRANT_LOCK_MAP.get(subscriptionId)) {
//                if (REENTRANT_LOCK_MAP.get(subscriptionId).isLocked()) {
//                    REENTRANT_LOCK_MAP.get(subscriptionId).wait();
//                    return chain.proceed(chain.request());
//                } else {
//                    REENTRANT_LOCK_MAP.get(subscriptionId).lock();
//                }
//            }
//        } catch (InterruptedException e) {
//            throw new IOException(e);
//        }
//
//        try {
//            String retryAfterHeader = response.header("Retry-After");
//            int retryAfter = 0;
//            if (retryAfterHeader != null) {
//                DateTime retryWhen = null;
//                try {
//                    retryWhen = new DateTimeRfc1123(retryAfterHeader).dateTime();
//                } catch (Exception e) { }
//                if (retryWhen == null) {
//                    retryAfter = Integer.parseInt(retryAfterHeader);
//                } else {
//                    retryAfter = new Duration(null, retryWhen).toStandardSeconds().getSeconds();
//                }
//            }
//            if (retryAfter <= 0) {
//                Pattern pattern = Pattern.compile("try again after '([0-9]*)' minutes", Pattern.CASE_INSENSITIVE);
//                Matcher matcher = pattern.matcher(content(response.body()));
//                if (matcher.find()) {
//                    retryAfter = (int) TimeUnit.MINUTES.toSeconds(Integer.parseInt(matcher.group(1)));
//                } else {
//                    pattern = Pattern.compile("try again after '([0-9]*)' seconds", Pattern.CASE_INSENSITIVE);
//                    matcher = pattern.matcher(content(response.body()));
//                    if (matcher.find()) {
//                        retryAfter = Integer.parseInt(matcher.group(1));
//                    }
//                }
//            }
//            if (retryAfter > 0) {
//                String context = chain.request().header(LOGGING_HEADER);
//                if (context == null) {
//                    context = "";
//                }
//                LoggerFactory.getLogger(context)
//                        .info("Azure Resource Manager read/write per hour limit reached. Will retry in: " + retryAfter + " seconds");
//                SdkContext.sleep((int) (TimeUnit.SECONDS.toMillis(retryAfter) + 100));
//            }
//            return chain.proceed(chain.request());
//        } catch (Throwable t) {
//            throw new IOException(t);
//        } finally {
//            if (response.body() != null) {
//                response.body().close();
//            }
//            synchronized (REENTRANT_LOCK_MAP.get(subscriptionId)) {
//                REENTRANT_LOCK_MAP.get(subscriptionId).unlock();
//                REENTRANT_LOCK_MAP.get(subscriptionId).notifyAll();
//            }
//        }
//    }
//}
