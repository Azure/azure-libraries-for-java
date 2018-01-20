/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.fluentcore.utils;

import com.microsoft.azure.v2.CloudException;
import com.microsoft.rest.v2.http.HttpRequest;
import com.microsoft.rest.v2.http.HttpResponse;
import com.microsoft.rest.v2.policy.RequestPolicy;
import com.microsoft.rest.v2.policy.RequestPolicyFactory;
import com.microsoft.rest.v2.policy.RequestPolicyOptions;
import io.reactivex.Completable;
import io.reactivex.SingleSource;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import org.slf4j.LoggerFactory;
import io.reactivex.Single;
import io.reactivex.functions.Function;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An interceptor for automatic retry when Azure Resource Manager is throttling because of too many read/write requests.
 * <p>
 * For each subscription and tenant, Azure Resource Manager limits read requests to 15,000 per hour and
 *   write requests to 1,200 per hour. These limits apply to each Azure Resource Manager instance.
 */
public final class ResourceManagerThrottlingPolicyFactory implements RequestPolicyFactory {
    private static final int HTTP_TOO_MANY_REQUESTS = 429;
    private final Subject<Boolean> isDelayingSubject = BehaviorSubject.create();

    /**
     * Creates a ResourceManagerThrottlingPolicyFactory.
     */
    public ResourceManagerThrottlingPolicyFactory() {
        isDelayingSubject.onNext(false);
    }

    private final Completable awaitDelayCompletion = isDelayingSubject.takeUntil(new Predicate<Boolean>() {
        @Override
        public boolean test(Boolean isDelaying) throws Exception {
            return !isDelaying;
        }
    }).lastElement().ignoreElement();

    @Override
    public RequestPolicy create(RequestPolicy next, RequestPolicyOptions options) {
        return new ResourceManagerThrottlingPolicy(next);
    }

    private final class ResourceManagerThrottlingPolicy implements RequestPolicy {
        private final RequestPolicy next;
        private ResourceManagerThrottlingPolicy(RequestPolicy next) {
            this.next = next;
        }

        @Override
        public Single<HttpResponse> sendAsync(final HttpRequest request) {
            Single<HttpResponse> asyncResponse = next.sendAsync(request).flatMap(new Function<HttpResponse, Single<HttpResponse>>() {
                @Override
                public Single<HttpResponse> apply(final HttpResponse response) {
                    if (response.statusCode() != HTTP_TOO_MANY_REQUESTS) {
                        return Single.just(response);
                    } else {
                        final HttpResponse bufferedResponse = response.buffer();
                        return bufferedResponse.bodyAsStringAsync().flatMap(new Function<String, Single<HttpResponse>>() {
                            @Override
                            public Single<HttpResponse> apply(String body) {
                                isDelayingSubject.onNext(true);
                                return delayIfTooManyRequests(request, bufferedResponse, body);
                            }
                        });
                    }
                }
            });

            return awaitDelayCompletion.andThen(asyncResponse);
        }

        private Single<HttpResponse> delayIfTooManyRequests(final HttpRequest request, HttpResponse bufferedResponse, String body) {
            String retryAfterHeader = bufferedResponse.headerValue("Retry-After");
            int retryAfter = 0;
            if (retryAfterHeader != null) {
                try {
                    retryAfter = Integer.parseInt(retryAfterHeader);
                } catch (NumberFormatException e) {
                    isDelayingSubject.onNext(false);
                    return Single.error(new CloudException("Invalid format for Retry-After header", bufferedResponse, null));
                }
            }

            if (retryAfter <= 0) {
                Pattern pattern = Pattern.compile("try again after '([0-9]*)' minutes", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(body);
                if (matcher.find()) {
                    retryAfter = (int) TimeUnit.MINUTES.toSeconds(Integer.parseInt(matcher.group(1)));
                }
            }

            if (retryAfter > 0) {
                String context = request.callerMethod();
                LoggerFactory.getLogger(context)
                        .info("Azure Resource Manager read/write per hour limit reached. Will retry in: " + retryAfter + " seconds");

                return Completable.complete().delay(retryAfter, TimeUnit.SECONDS)
                        .andThen(Single.defer(new Callable<SingleSource<HttpResponse>>() {
                            @Override
                            public SingleSource<HttpResponse> call() throws Exception {
                                isDelayingSubject.onNext(false);
                                return next.sendAsync(request);
                            }
                        }));
            }

            isDelayingSubject.onNext(false);
            return next.sendAsync(request);
        }
    }
}
