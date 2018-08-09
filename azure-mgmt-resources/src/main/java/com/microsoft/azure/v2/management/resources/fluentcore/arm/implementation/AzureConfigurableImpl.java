/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.fluentcore.arm.implementation;

import com.microsoft.azure.v2.credentials.AzureTokenCredentials;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.AzureConfigurable;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.ProviderRegistrationPolicyFactory;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.ResourceManagerThrottlingPolicyFactory;
import com.microsoft.rest.v2.http.HttpClient;
import com.microsoft.rest.v2.http.HttpClientConfiguration;
import com.microsoft.rest.v2.http.HttpPipeline;
import com.microsoft.rest.v2.http.HttpPipelineBuilder;
import com.microsoft.rest.v2.policy.CredentialsPolicyFactory;
import com.microsoft.rest.v2.policy.RequestPolicyFactory;

import java.net.Proxy;

/**
 * The implementation for {@link AzureConfigurable<T>} and the base class for
 * configurable implementations.
 *
 * @param <T> the type of the configurable interface
 */
public class AzureConfigurableImpl<T extends AzureConfigurable<T>>
        implements AzureConfigurable<T> {
    private Proxy proxy;
    private HttpPipelineBuilder pipelineBuilder;

    protected AzureConfigurableImpl() {
        this.pipelineBuilder = new HttpPipelineBuilder();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T withRequestPolicy(RequestPolicyFactory factory) {
        this.pipelineBuilder.withRequestPolicy(factory);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T withProxy(Proxy proxy) {
        this.proxy = proxy;
        return (T) this;
    }

    protected HttpPipeline buildPipeline(AzureTokenCredentials credentials) {
        HttpPipeline pipeline = pipelineBuilder
                .withRequestPolicy(new CredentialsPolicyFactory(credentials))
                .withRequestPolicy(new ProviderRegistrationPolicyFactory(credentials))
                .withRequestPolicy(new ResourceManagerThrottlingPolicyFactory())
                .withHttpClient(HttpClient.createDefault(new HttpClientConfiguration(proxy, false)))
                .build();

        if (proxy != null) {
            credentials.withProxy(proxy);
        }

        return pipeline;
    }
}
