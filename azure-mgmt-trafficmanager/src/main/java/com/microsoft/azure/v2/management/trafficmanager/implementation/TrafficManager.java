/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.trafficmanager.implementation;

import com.microsoft.azure.v2.AzureEnvironment;
import com.microsoft.azure.v2.credentials.AzureTokenCredentials;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.AzureConfigurable;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.implementation.AzureConfigurableImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.implementation.Manager;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.ProviderRegistrationPolicyFactory;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.ResourceManagerThrottlingPolicyFactory;
import com.microsoft.azure.v2.management.trafficmanager.TrafficManagerProfiles;
import com.microsoft.rest.v2.http.HttpPipeline;
import com.microsoft.rest.v2.http.HttpPipelineBuilder;
import com.microsoft.rest.v2.policy.CredentialsPolicyFactory;

/**
 * Entry point to Azure traffic manager management.
 */
public final class TrafficManager extends Manager<TrafficManager, TrafficManagerManagementClientImpl> {
    // Collections
    private TrafficManagerProfiles profiles;

    /**
     * Get a Configurable instance that can be used to create {@link TrafficManager}
     * with optional configuration.
     *
     * @return the instance allowing configurations
     */
    public static Configurable configure() {
        return new TrafficManager.ConfigurableImpl();
    }

    /**
     * Creates an instance of TrafficManager that exposes traffic manager management API entry points.
     *
     * @param credentials the credentials to use
     * @param subscriptionId the subscription UUID
     * @return the TrafficManager
     */
    public static TrafficManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
        return new TrafficManager(new HttpPipelineBuilder()
                .withRequestPolicy(new CredentialsPolicyFactory(credentials))
                .withRequestPolicy(new ProviderRegistrationPolicyFactory(credentials))
                .withRequestPolicy(new ResourceManagerThrottlingPolicyFactory())
                .build(), subscriptionId, credentials.environment());
    }

    /**
     * Creates an instance of TrafficManager that exposes traffic manager management API entry points.
     *
     * @param httpPipeline the HttpPipeline to be used for API calls.
     * @param subscriptionId the subscription UUID
     * @return the TrafficManager
     */
    public static TrafficManager authenticate(HttpPipeline httpPipeline, String subscriptionId, AzureEnvironment environment) {
        return new TrafficManager(httpPipeline, subscriptionId, environment);
    }

    /**
     * The interface allowing configurations to be set.
     */
    public interface Configurable extends AzureConfigurable<Configurable> {
        /**
         * Creates an instance of TrafficManager that exposes traffic manager management API entry points.
         *
         * @param credentials the credentials to use
         * @param subscriptionId the subscription UUID
         * @return the interface exposing traffic manager management API entry points that work across subscriptions
         */
        TrafficManager authenticate(AzureTokenCredentials credentials, String subscriptionId);
    }

    /**
     * The implementation for Configurable interface.
     */
    private static class ConfigurableImpl
            extends AzureConfigurableImpl<Configurable>
            implements Configurable {

        public TrafficManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
            return TrafficManager.authenticate(buildPipeline(credentials), subscriptionId, credentials.environment());
        }
    }

    private TrafficManager(HttpPipeline httpPipeline, String subscriptionId, AzureEnvironment environment) {
        super(
                httpPipeline,
                subscriptionId,
                environment,
                new TrafficManagerManagementClientImpl(httpPipeline, environment).withSubscriptionId(subscriptionId));
    }

    /**
     * @return entry point to traffic manager profile management
     */
    public TrafficManagerProfiles profiles() {
        if (this.profiles == null) {
            this.profiles = new TrafficManagerProfilesImpl(this);
        }
        return this.profiles;
    }
}
