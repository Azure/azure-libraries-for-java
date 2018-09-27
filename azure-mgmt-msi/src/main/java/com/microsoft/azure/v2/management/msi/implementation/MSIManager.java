/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.msi.implementation;

import com.microsoft.azure.v2.AzureEnvironment;
import com.microsoft.azure.v2.credentials.AzureTokenCredentials;
import com.microsoft.azure.v2.management.graphrbac.implementation.GraphRbacManager;
import com.microsoft.azure.v2.management.msi.Identities;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.AzureConfigurable;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.implementation.AzureConfigurableImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.implementation.Manager;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.ProviderRegistrationPolicyFactory;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.ResourceManagerThrottlingPolicyFactory;
import com.microsoft.rest.v2.annotations.Beta;
import com.microsoft.rest.v2.http.HttpPipeline;
import com.microsoft.rest.v2.http.HttpPipelineBuilder;
import com.microsoft.rest.v2.policy.CredentialsPolicyFactory;

/**
 * Entry point to Azure Managed Service Identity (MSI) resource management.
 */
@Beta(since = "V1_5_1")
public final class MSIManager extends Manager<MSIManager, ManagedServiceIdentityClientImpl> {
    private final GraphRbacManager rbacManager;

    private Identities identities;

    /**
     * Get a Configurable instance that can be used to create MSIManager with optional configuration.
     *
     * @return the instance allowing configurations
     */
    public static Configurable configure() {
        return new MSIManager.ConfigurableImpl();
    }

    /**
     * Creates an instance of MSIManager that exposes Managed Service Identity (MSI) resource management API entry points.
     *
     * @param credentials the credentials to use
     * @param subscriptionId the subscription UUID
     * @return the MSIManager
     */
    public static MSIManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
        return new MSIManager(new HttpPipelineBuilder()
                .withRequestPolicy(new CredentialsPolicyFactory(credentials))
                .withRequestPolicy(new ProviderRegistrationPolicyFactory(credentials))
                .withRequestPolicy(new ResourceManagerThrottlingPolicyFactory())
                .build(),
                subscriptionId,
                credentials.domain(),
                credentials.environment());
    }

    /**
     * Creates an instance of MSIManager that exposes Managed Service Identity (MSI) resource management API entry points.
     *
     * @param httpPipeline the httpPipeline to be used for API calls.
     * @param subscriptionId the subscription UUID
     * @param domain the domain
     * @param environment the azure environment hosting the APIs
     * @return the MSIManager
     */
    public static MSIManager authenticate(HttpPipeline httpPipeline, String subscriptionId, String domain, AzureEnvironment environment) {
        return new MSIManager(httpPipeline, subscriptionId, domain, environment);
    }
    /**
     * The interface allowing configurations to be set.
     */
    public interface Configurable extends AzureConfigurable<Configurable> {
        /**
         * Creates an instance of MSIManager that exposes EventHub management API entry points.
         *
         * @param credentials the credentials to use
         * @param subscriptionId the subscription UUID
         * @return the interface exposing Managed Service Identity (MSI) resource management API entry points that work across subscriptions
         */
        MSIManager authenticate(AzureTokenCredentials credentials, String subscriptionId);
    }

    /**
     * The implementation for Configurable interface.
     */
    private static final class ConfigurableImpl extends AzureConfigurableImpl<Configurable> implements Configurable {
        public MSIManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
            return MSIManager.authenticate(buildPipeline(credentials), subscriptionId, credentials.domain(), credentials.environment());
        }
    }

    private MSIManager(HttpPipeline httpPipeline, String subscriptionId, String domain, AzureEnvironment environment) {
        super(httpPipeline,
              subscriptionId,
              environment,
              new ManagedServiceIdentityClientImpl(httpPipeline, environment).withSubscriptionId(subscriptionId));
        rbacManager = GraphRbacManager.authenticate(httpPipeline, domain, environment);
    }

    /**
     * @return entry point to Azure MSI Identity resource management API
     */
    @Beta(since = "V1_5_1")
    public Identities identities() {
        if (identities == null) {
            this.identities = new IdentitesImpl(this.inner().userAssignedIdentities(), this);
        }
        return this.identities;
    }

    /**
     * @return the Graph RBAC manager.
     */
    @Beta(since = "V1_5_1")
    public GraphRbacManager graphRbacManager() {
        return this.rbacManager;
    }
}
