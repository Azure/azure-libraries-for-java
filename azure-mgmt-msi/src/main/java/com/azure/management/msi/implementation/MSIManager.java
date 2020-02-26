/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.msi.implementation;

import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.serializer.AzureJacksonAdapter;
import com.azure.management.AzureTokenCredential;
import com.azure.management.RestClient;
import com.azure.management.RestClientBuilder;
import com.azure.management.graphrbac.implementation.GraphRbacManager;
import com.azure.management.msi.Identities;
import com.azure.management.msi.models.ManagedServiceIdentityClientBuilder;
import com.azure.management.msi.models.ManagedServiceIdentityClientImpl;
import com.azure.management.resources.fluentcore.arm.AzureConfigurable;
import com.azure.management.resources.fluentcore.arm.implementation.AzureConfigurableImpl;
import com.azure.management.resources.fluentcore.arm.implementation.Manager;

/**
 * Entry point to Azure Managed Service Identity (MSI) resource management.
 */
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
     * @param credential the credentials to use
     * @param subscriptionId the subscription UUID
     * @return the MSIManager
     */
    public static MSIManager authenticate(AzureTokenCredential credential, String subscriptionId) {
        return new MSIManager(new RestClientBuilder()
                .withBaseUrl(credential.getEnvironment(), AzureEnvironment.Endpoint.RESOURCE_MANAGER)
                .withCredential(credential)
                .withSerializerAdapter(new AzureJacksonAdapter())
                //.withResponseBuilderFactory(new AzureResponseBuilder.Factory())
                //.withInterceptor(new ProviderRegistrationInterceptor(credentials))
                //.withInterceptor(new ResourceManagerThrottlingInterceptor())
                .buildClient(), subscriptionId);
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
     * Creates an instance of MSIManager that exposes Managed Service Identity (MSI) resource management API entry points.
     *
     * @param restClient the RestClient to be used for API calls.
     * @param tenantId the tenant UUID
     * @param subscriptionId the subscription UUID
     * @return the MSIManager
     */
    public static MSIManager authenticate(RestClient restClient, String tenantId, String subscriptionId) {
        return new MSIManager(restClient, tenantId, subscriptionId);
    }

    /**
     * The interface allowing configurations to be set.
     */
    public interface Configurable extends AzureConfigurable<Configurable> {
        /**
         * Creates an instance of MSIManager that exposes EventHub management API entry points.
         *
         * @param credential the credentials to use
         * @param subscriptionId the subscription UUID
         * @return the interface exposing Managed Service Identity (MSI) resource management API entry points that work across subscriptions
         */
        MSIManager authenticate(AzureTokenCredential credential, String subscriptionId);
    }

    /**
     * The implementation for Configurable interface.
     */
    private static final class ConfigurableImpl extends AzureConfigurableImpl<Configurable> implements Configurable {
        public MSIManager authenticate(AzureTokenCredential credential, String subscriptionId) {
            return MSIManager.authenticate(buildRestClient(credential), subscriptionId);
        }
    }

    private MSIManager(RestClient restClient, String subscriptionId) {
        this(restClient, null, subscriptionId);
    }

    private MSIManager(RestClient restClient, String tenantId, String subscriptionId) {
        super(restClient, subscriptionId, new ManagedServiceIdentityClientBuilder()
                .pipeline(restClient.getHttpPipeline())
                .subscriptionId(subscriptionId)
                .build());
        if (tenantId == null) {
            tenantId = tenantIdFromRestClient(restClient);
        }
        rbacManager = GraphRbacManager.authenticate(restClient, tenantId, subscriptionId);
    }

    private String tenantIdFromRestClient(RestClient restClient) {
        if (restClient.getCredential() != null && restClient.getCredential() instanceof AzureTokenCredential) {
            return ((AzureTokenCredential) (restClient.getCredential())).getDomain();
        }
        throw new IllegalArgumentException("The credential in RestClient must be an instance of AzureTokenCredential.");
    }

    /**
     * @return entry point to Azure MSI Identity resource management API
     */
    public Identities identities() {
        if (identities == null) {
            this.identities = new IdentitesImpl(this.inner().userAssignedIdentities(), this);
        }
        return this.identities;
    }

    /**
     * @return the Graph RBAC manager.
     */
    public GraphRbacManager graphRbacManager() {
        return this.rbacManager;
    }
}
