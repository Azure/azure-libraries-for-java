/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.msi.implementation;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.AzureResponseBuilder;
import com.microsoft.azure.credentials.AzureTokenCredentials;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.graphrbac.implementation.GraphRbacManager;
import com.microsoft.azure.management.msi.Identities;
import com.microsoft.azure.management.resources.fluentcore.arm.AzureConfigurable;
import com.microsoft.azure.management.resources.fluentcore.arm.implementation.AzureConfigurableImpl;
import com.microsoft.azure.management.resources.fluentcore.arm.implementation.Manager;
import com.microsoft.azure.management.resources.fluentcore.utils.ProviderRegistrationInterceptor;
import com.microsoft.azure.management.resources.fluentcore.utils.ResourceManagerThrottlingInterceptor;
import com.microsoft.azure.serializer.AzureJacksonAdapter;
import com.microsoft.rest.RestClient;

/**
 * Entry point to Azure Managed Service Identity (MSI) resource management.
 */
@Beta // TODO Add since v1.5 param
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
        return new MSIManager(new RestClient.Builder()
                .withBaseUrl(credentials.environment(), AzureEnvironment.Endpoint.RESOURCE_MANAGER)
                .withCredentials(credentials)
                .withSerializerAdapter(new AzureJacksonAdapter())
                .withResponseBuilderFactory(new AzureResponseBuilder.Factory())
                .withInterceptor(new ProviderRegistrationInterceptor(credentials))
                .withInterceptor(new ResourceManagerThrottlingInterceptor())
                .build(), subscriptionId);
    }

    /**
     * Creates an instance of MSIManager that exposes Managed Service Identity (MSI) resource management API entry points.
     *
     * @param restClient the RestClient to be used for API calls.
     * @param subscriptionId the subscription UUID
     * @return the MSIManager
     */
    public static MSIManager authenticate(RestClient restClient, String subscriptionId) {
        return new MSIManager(restClient, subscriptionId);
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
            return MSIManager.authenticate(buildRestClient(credentials), subscriptionId);
        }
    }

    private MSIManager(RestClient restClient, String subscriptionId) {
        super(restClient, subscriptionId, new ManagedServiceIdentityClientImpl(restClient).withSubscriptionId(subscriptionId));
        rbacManager = GraphRbacManager.authenticate(restClient, ((AzureTokenCredentials) (restClient.credentials())).domain());
    }

    /**
     * @return entry point to Azure MSI Identity resource management API
     */
    public Identities identities() {
        // TODO
        return null;
    }

    /**
     * @return the Graph RBAC manager.
     */
    public GraphRbacManager graphRbacManager() {
        return this.rbacManager;
    }
}
