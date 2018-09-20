/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.fluentcore.utils;

import com.microsoft.azure.v2.CloudError;
import com.microsoft.azure.v2.credentials.AzureTokenCredentials;
import com.microsoft.azure.v2.management.resources.Provider;
import com.microsoft.azure.v2.management.resources.Providers;
import com.microsoft.azure.v2.management.resources.implementation.ResourceManager;
import com.microsoft.azure.v2.serializer.AzureJacksonAdapter;
import com.microsoft.rest.v2.http.HttpPipeline;
import com.microsoft.rest.v2.http.HttpRequest;
import com.microsoft.rest.v2.http.HttpResponse;
import com.microsoft.rest.v2.policy.CredentialsPolicyFactory;
import com.microsoft.rest.v2.policy.HostPolicyFactory;
import com.microsoft.rest.v2.policy.RequestPolicy;
import com.microsoft.rest.v2.policy.RequestPolicyFactory;
import com.microsoft.rest.v2.policy.RequestPolicyOptions;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An interceptor for automatic provider registration in Azure.
 */
public final class ProviderRegistrationPolicyFactory implements RequestPolicyFactory {
    private static final Pattern SUBSCRIPTION_PATTERN = Pattern.compile("/subscriptions/([\\w-]+)/", Pattern.CASE_INSENSITIVE);
    private static final Pattern PROVIDER_PATTERN = Pattern.compile(".*'(.*)'");
    private static final AzureJacksonAdapter SERIALIZER = new AzureJacksonAdapter();
    private final AzureTokenCredentials credentials;

    /**
     * Initialize a provider registration policy factory with a credential that's authorized
     * to register the provider.
     *
     * @param credentials the credential for provider registration
     */
    public ProviderRegistrationPolicyFactory(AzureTokenCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public RequestPolicy create(RequestPolicy next, RequestPolicyOptions options) {
        return new ProviderRegistrationPolicy(next);
    }

    private final class ProviderRegistrationPolicy implements RequestPolicy {
        private final RequestPolicy next;
        private ProviderRegistrationPolicy(RequestPolicy next) {
            this.next = next;
        }

        @Override
        public Single<HttpResponse> sendAsync(final HttpRequest request) {
            return next.sendAsync(request).flatMap(response -> {
                if (response.statusCode() >= 200 && response.statusCode() < 300) {
                    return Single.just(response);
                } else {
                    return registerProviderIfNeeded(request, response.buffer());
                }
            });
        }

        private Single<HttpResponse> registerProviderIfNeeded(final HttpRequest request, final HttpResponse bufferedResponse) {
            return bufferedResponse.bodyAsString().flatMap((Function<String, Single<? extends HttpResponse>>) bodyContent -> {
                CloudError cloudError = SERIALIZER.deserialize(bodyContent, CloudError.class);
                boolean isMissingProviderError = cloudError != null && "MissingSubscriptionRegistration".equals(cloudError.code());
                if (!isMissingProviderError) {
                    return Single.just(bufferedResponse);
                } else {
                    Matcher matcher = SUBSCRIPTION_PATTERN.matcher(request.url().toString());
                    matcher.find();

                    HttpPipeline pipeline = HttpPipeline.build(
                            new HostPolicyFactory(request.url().getHost()),
                            new CredentialsPolicyFactory(credentials));

                    final Providers providers = ResourceManager.authenticate(pipeline, credentials.environment())
                            .withSubscription(matcher.group(1))
                            .providers();

                    matcher = PROVIDER_PATTERN.matcher(cloudError.message());
                    matcher.find();

                    final String namespace = matcher.group(1);
                    return providers.registerAsync(matcher.group(1))
                            .toSingle()
                            .flatMapCompletable((Function<Provider, Completable>) provider -> pollProviderAsync(provider, providers, namespace))
                            .andThen(next.sendAsync(request));
                }
            });
        }

        private Completable pollProviderAsync(final Provider currentProvider, final Providers providers, final String namespace) {
            if (currentProvider.registrationState().equalsIgnoreCase("Unregistered")
                    || currentProvider.registrationState().equalsIgnoreCase("Registering")) {
                return providers.getByNameAsync(namespace)
                        .toSingle()
                        .flatMapCompletable(newProvider -> Completable.complete().delay(5000, TimeUnit.MILLISECONDS)
                                .andThen(pollProviderAsync(newProvider, providers, namespace)));
            } else {
                return Completable.complete();
            }
        }
    }
}