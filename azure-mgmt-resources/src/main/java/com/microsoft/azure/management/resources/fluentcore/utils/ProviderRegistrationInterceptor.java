/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.utils;

import com.microsoft.azure.AzureResponseBuilder;
import com.microsoft.azure.CloudError;
import com.microsoft.azure.credentials.AzureTokenCredentials;
import com.microsoft.azure.management.resources.Provider;
import com.microsoft.azure.management.resources.implementation.ResourceManager;
import com.microsoft.azure.serializer.AzureJacksonAdapter;
import com.microsoft.rest.RestClient;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An interceptor for automatic provider registration in Azure.
 */
public final class ProviderRegistrationInterceptor implements Interceptor {
    private final AzureTokenCredentials credentials;

    /**
     * Initialize a provider registration interceptor with a credential that's authorized
     * to register the provider.
     * @param credentials the credential for provider registration
     */
    public ProviderRegistrationInterceptor(AzureTokenCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (!response.isSuccessful()) {
            String content = Utils.getResponseBodyInString(response.body());
            AzureJacksonAdapter jacksonAdapter = new AzureJacksonAdapter();
            CloudError cloudError = jacksonAdapter.deserialize(content, CloudError.class);
            if (cloudError != null && "MissingSubscriptionRegistration".equals(cloudError.code())) {
                Pattern pattern = Pattern.compile("/subscriptions/([\\w-]+)/", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(chain.request().url().toString());
                matcher.find();
                RestClient.Builder restClientBuilder = new RestClient.Builder();
                restClientBuilder.withBaseUrl("https://" + chain.request().url().host())
                        .withCredentials(credentials)
                        .withSerializerAdapter(jacksonAdapter)
                        .withResponseBuilderFactory(new AzureResponseBuilder.Factory());
                if (credentials.proxy() != null) {
                    restClientBuilder.withProxy(credentials.proxy());
                }
                RestClient restClient = restClientBuilder.build();
                ResourceManager resourceManager = ResourceManager.authenticate(restClient)
                        .withSubscription(matcher.group(1));
                pattern = Pattern.compile(".*'(.*)'");
                matcher = pattern.matcher(cloudError.message());
                matcher.find();
                Provider provider = registerProvider(matcher.group(1), resourceManager);
                while (provider.registrationState().equalsIgnoreCase("Unregistered")
                        || provider.registrationState().equalsIgnoreCase("Registering")) {
                    SdkContext.sleep(5 * 1000);
                    provider = resourceManager.providers().getByName(provider.namespace());
                }
                // Retry
                response = chain.proceed(chain.request());
            }
        }
        return response;
    }

    private Provider registerProvider(String namespace, ResourceManager resourceManager) {
        return resourceManager.providers().register(namespace);
    }
}
