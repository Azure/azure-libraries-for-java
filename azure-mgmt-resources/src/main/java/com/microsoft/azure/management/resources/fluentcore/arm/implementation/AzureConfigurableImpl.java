/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.arm.implementation;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.arm.resources.AzureConfigurable;
import com.microsoft.azure.arm.resources.implementation.AzureConfigurableCoreImpl;
import com.microsoft.azure.arm.utils.ResourceManagerThrottlingInterceptor;
import com.microsoft.azure.credentials.AzureTokenCredentials;
import com.microsoft.azure.management.resources.fluentcore.utils.ProviderRegistrationInterceptor;
import com.microsoft.rest.RestClient;

/**
 * The implementation for {@link AzureConfigurable<T>} and the base class for
 * configurable implementations.
 *
 * @param <T> the type of the configurable interface
 */
public class AzureConfigurableImpl<T extends AzureConfigurable<T>> extends AzureConfigurableCoreImpl<T> {
    protected RestClient.Builder restClientBuilder;

    protected AzureConfigurableImpl() {
        super();
    }

    protected RestClient buildRestClient(AzureTokenCredentials credentials, AzureEnvironment.Endpoint endpoint) {
        RestClient client =  restClientBuilder
                .withBaseUrl(credentials.environment(), endpoint)
                .withCredentials(credentials)
                .withInterceptor(new ProviderRegistrationInterceptor(credentials))
                .withInterceptor(new ResourceManagerThrottlingInterceptor())
                .build();
        if (client.httpClient().proxy() != null) {
            credentials.withProxy(client.httpClient().proxy());
        }
        return client;
    }
}
