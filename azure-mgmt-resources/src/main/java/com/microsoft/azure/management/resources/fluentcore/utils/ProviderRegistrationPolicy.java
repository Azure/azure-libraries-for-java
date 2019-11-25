/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.utils;

import com.azure.core.credential.TokenCredential;
import com.azure.core.http.HttpPipelineCallContext;
import com.azure.core.http.HttpPipelineNextPolicy;
import com.azure.core.http.HttpResponse;
import com.azure.core.http.policy.HttpPipelinePolicy;
import com.azure.core.management.CloudError;
import com.azure.core.management.serializer.AzureJacksonAdapter;
import com.azure.core.util.serializer.SerializerEncoding;
import com.microsoft.azure.management.RestClient;
import com.microsoft.azure.management.RestClientBuilder;
import com.microsoft.azure.management.resources.Provider;
import com.microsoft.azure.management.resources.implementation.ResourceManager;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An interceptor for automatic provider registration in Azure.
 */
public final class ProviderRegistrationPolicy implements HttpPipelinePolicy {
    private final TokenCredential credential;

    /**
     * Initialize a provider registration interceptor with a credential that's authorized
     * to register the provider.
     *
     * @param credential the credential for provider registration
     */
    public ProviderRegistrationPolicy(TokenCredential credential) {
        this.credential = credential;
    }

    private Provider registerProvider(String namespace, ResourceManager resourceManager) {
        return resourceManager.providers().register(namespace);
    }

    @Override
    public Mono<HttpResponse> process(HttpPipelineCallContext httpPipelineCallContext, HttpPipelineNextPolicy next) {
        return next.clone().process()
                .flatMap(httpResponse -> {
                    if (!isSuccessful(httpResponse.getStatusCode())) {
                        String content = httpResponse.getBodyAsString().block();
                        AzureJacksonAdapter jacksonAdapter = new AzureJacksonAdapter();
                        try {
                            CloudError cloudError = jacksonAdapter.deserialize(content, CloudError.class, SerializerEncoding.JSON);
                            if (cloudError != null && "MissingSubscriptionRegistration".equals(cloudError.getCode())) {

                                Pattern pattern = Pattern.compile("/subscriptions/([\\w-]+)/", Pattern.CASE_INSENSITIVE);
                                Matcher matcher = pattern.matcher(httpPipelineCallContext.getHttpRequest().getUrl().toString());
                                matcher.find();
                                RestClientBuilder restClientBuilder = new RestClientBuilder();
                                restClientBuilder.withBaseUrl("https://" + httpResponse.getRequest().getUrl().getHost())
                                        .withCredential(credential)
                                        .withSerializerAdapter(jacksonAdapter);
//                                if (credentials.proxy() != null) {
//                                    restClientBuilder.withProxy(credentials.proxy());
//                                }
                                RestClient restClient = restClientBuilder.buildClient();
                                ResourceManager resourceManager = ResourceManager.authenticate(restClient)
                                        .withSubscription(matcher.group(1));
                                pattern = Pattern.compile(".*'(.*)'");
                                matcher = pattern.matcher(cloudError.getMessage());
                                matcher.find();
                                Provider provider = registerProvider(matcher.group(1), resourceManager);
                                while (provider.registrationState().equalsIgnoreCase("Unregistered")
                                        || provider.registrationState().equalsIgnoreCase("Registering")) {
                                    SdkContext.sleep(5 * 1000);
                                    provider = resourceManager.providers().getByName(provider.namespace());
                                }
                                // Retry
                                return next.process();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return Mono.just(httpResponse);
                });
    }

    boolean isSuccessful(int statusCode) {
        return statusCode >= 200 && statusCode < 300;
    }
}
