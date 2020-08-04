/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.utils;

import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.rest.DateTimeRfc1123;
import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * An interceptor for automatic retry when Azure Resource Manager is throttling because of too many read/write requests.
 * <p>
 * For each subscription and tenant, Azure Resource Manager limits read requests to 15,000 per hour and
 *   write requests to 1,200 per hour. These limits apply to each Azure Resource Manager instance.
 */
public class ResourceManagerThrottlingInterceptor implements Interceptor {
    private static final String LOGGING_HEADER = "x-ms-logging-context";
    private static final ConcurrentMap<String, ReentrantLock> REENTRANT_LOCK_MAP = new ConcurrentHashMap<>();

    @Override
    public Response intercept(Chain chain) throws IOException {
        // Gate control
        String subscriptionId = ResourceUtils.extractFromResourceId(chain.request().url().url().getPath(), "subscriptions");
        if (subscriptionId == null) {
            subscriptionId = "global";
        }
        final String subscriptionAndMethod = subscriptionId + "|" + chain.request().method().toLowerCase(Locale.ROOT);
        REENTRANT_LOCK_MAP.putIfAbsent(subscriptionAndMethod, new ReentrantLock());
        try {
            synchronized (REENTRANT_LOCK_MAP.get(subscriptionAndMethod)) {
                if (REENTRANT_LOCK_MAP.get(subscriptionAndMethod).isLocked()) {
                    REENTRANT_LOCK_MAP.get(subscriptionAndMethod).wait();
                }
            }
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
        Response response = chain.proceed(chain.request());
        if (response.code() != 429) {
            return response;
        }

        try {
            synchronized (REENTRANT_LOCK_MAP.get(subscriptionAndMethod)) {
                if (REENTRANT_LOCK_MAP.get(subscriptionAndMethod).isLocked()) {
                    response.close();
                    REENTRANT_LOCK_MAP.get(subscriptionAndMethod).wait();
                    return chain.proceed(chain.request());
                } else {
                    REENTRANT_LOCK_MAP.get(subscriptionAndMethod).lock();
                }
            }
        } catch (InterruptedException e) {
            throw new IOException(e);
        }

        try {
            String retryAfterHeader = response.header("Retry-After");
            int retryAfter = 0;
            if (retryAfterHeader != null) {
                DateTime retryWhen = null;
                try {
                    retryWhen = new DateTimeRfc1123(retryAfterHeader).dateTime();
                } catch (Exception e) { }
                if (retryWhen == null) {
                    retryAfter = Integer.parseInt(retryAfterHeader);
                } else {
                    retryAfter = new Duration(null, retryWhen).toStandardSeconds().getSeconds();
                }
            }
            if (retryAfter <= 0) {
                Pattern pattern = Pattern.compile("try again after '([0-9]*)' minutes", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(content(response.body()));
                if (matcher.find()) {
                    retryAfter = (int) TimeUnit.MINUTES.toSeconds(Integer.parseInt(matcher.group(1)));
                } else {
                    pattern = Pattern.compile("try again after '([0-9]*)' seconds", Pattern.CASE_INSENSITIVE);
                    matcher = pattern.matcher(content(response.body()));
                    if (matcher.find()) {
                        retryAfter = Integer.parseInt(matcher.group(1));
                    }
                }
            }
            if (retryAfter > 0) {
                String context = chain.request().header(LOGGING_HEADER);
                if (context == null) {
                    context = "";
                }
                LoggerFactory.getLogger(context)
                    .info("Azure Resource Manager read/write per hour limit reached. Will retry in: " + retryAfter + " seconds");
                response.close();
                SdkContext.sleep((int) (TimeUnit.SECONDS.toMillis(retryAfter) + 100));
            }
            return chain.proceed(chain.request());
        } catch (Throwable t) {
            throw new IOException(t);
        } finally {
            response.close();
            synchronized (REENTRANT_LOCK_MAP.get(subscriptionAndMethod)) {
                REENTRANT_LOCK_MAP.get(subscriptionAndMethod).unlock();
                REENTRANT_LOCK_MAP.get(subscriptionAndMethod).notifyAll();
            }
        }
    }

    private String content(ResponseBody responseBody) throws IOException {
        if (responseBody == null) {
            return null;
        }
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        return buffer.readUtf8();
    }
}
