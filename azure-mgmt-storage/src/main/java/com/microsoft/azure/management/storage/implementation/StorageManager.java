/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage.implementation;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.AzureResponseBuilder;
import com.microsoft.azure.credentials.AzureTokenCredentials;
import com.microsoft.azure.management.resources.fluentcore.arm.AzureConfigurable;
import com.microsoft.azure.management.resources.fluentcore.arm.implementation.AzureConfigurableImpl;
import com.microsoft.azure.management.resources.fluentcore.arm.implementation.Manager;
import com.microsoft.azure.management.resources.fluentcore.utils.ProviderRegistrationInterceptor;
import com.microsoft.azure.management.resources.fluentcore.utils.ResourceManagerThrottlingInterceptor;
import com.microsoft.azure.management.storage.BlobContainers;
import com.microsoft.azure.management.storage.BlobServices;
import com.microsoft.azure.management.storage.ManagementPolicies;
import com.microsoft.azure.management.storage.StorageAccounts;
import com.microsoft.azure.management.storage.StorageSkus;
import com.microsoft.azure.management.storage.Usages;
import com.microsoft.azure.serializer.AzureJacksonAdapter;
import com.microsoft.rest.RestClient;

/**
 * Entry point to Azure storage resource management.
 */
public final class StorageManager extends Manager<StorageManager, StorageManagementClientImpl> {
    // Collections
    private StorageAccounts storageAccounts;
    private Usages storageUsages;
    private StorageSkus storageSkus;
    private BlobContainers blobContainers;
    private BlobServices blobServices;
    private ManagementPolicies managementPolicies;

    /**
     * Get a Configurable instance that can be used to create StorageManager with optional configuration.
     *
     * @return the instance allowing configurations
     */
    public static Configurable configure() {
        return new StorageManager.ConfigurableImpl();
    }

    /**
     * Creates an instance of StorageManager that exposes storage resource management API entry points.
     *
     * @param credentials the credentials to use
     * @param subscriptionId the subscription UUID
     * @return the StorageManager
     */
    public static StorageManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
        return new StorageManager(new RestClient.Builder()
                .withBaseUrl(credentials.environment(), AzureEnvironment.Endpoint.RESOURCE_MANAGER)
                .withCredentials(credentials)
                .withSerializerAdapter(new AzureJacksonAdapter())
                .withResponseBuilderFactory(new AzureResponseBuilder.Factory())
                .withInterceptor(new ProviderRegistrationInterceptor(credentials))
                .withInterceptor(new ResourceManagerThrottlingInterceptor())
                .build(), subscriptionId);
    }

    /**
     * Creates an instance of StorageManager that exposes storage resource management API entry points.
     *
     * @param restClient the RestClient to be used for API calls.
     * @param subscriptionId the subscription UUID
     * @return the StorageManager
     */
    public static StorageManager authenticate(RestClient restClient, String subscriptionId) {
        return new StorageManager(restClient, subscriptionId);
    }

    /**
     * The interface allowing configurations to be set.
     */
    public interface Configurable extends AzureConfigurable<Configurable> {
        /**
         * Creates an instance of StorageManager that exposes storage management API entry points.
         *
         * @param credentials the credentials to use
         * @param subscriptionId the subscription UUID
         * @return the interface exposing storage management API entry points that work across subscriptions
         */
        StorageManager authenticate(AzureTokenCredentials credentials, String subscriptionId);
    }

    /**
     * The implementation for Configurable interface.
     */
    private static final class ConfigurableImpl extends AzureConfigurableImpl<Configurable> implements Configurable {
        public StorageManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
            return StorageManager.authenticate(buildRestClient(credentials), subscriptionId);
        }
    }

    private StorageManager(RestClient restClient, String subscriptionId) {
        super(
                restClient,
                subscriptionId,
                new StorageManagementClientImpl(restClient).withSubscriptionId(subscriptionId));
    }

    /**
     * @return the storage account management API entry point
     */
    public StorageAccounts storageAccounts() {
        if (storageAccounts == null) {
            storageAccounts = new StorageAccountsImpl(this);
        }
        return storageAccounts;
    }

    /**
     * @return the storage service usage management API entry point
     */
    public Usages usages() {
        if (storageUsages == null) {
            storageUsages = new UsagesImpl(this);
        }
        return storageUsages;
    }

    /**
     * @return the storage service SKU management API entry point
     */
    public StorageSkus storageSkus() {
        if (storageSkus == null) {
            storageSkus = new StorageSkusImpl(this);
        }
        return storageSkus;
    }

    /**
     * @return the blob container management API entry point
     */
    public BlobContainers blobContainers() {
        if (blobContainers == null) {
            blobContainers = new BlobContainersImpl(this);
        }
        return blobContainers;
    }

    /**
     * @return the blob service management API entry point
     */
    public BlobServices blobServices() {
        if (blobServices == null) {
            blobServices = new BlobServicesImpl(this);
        }
        return blobServices;
    }

    /**
     * @return the management policy management API entry point
     */
    public ManagementPolicies managementPolicies() {
        if (managementPolicies == null) {
            managementPolicies = new ManagementPoliciesImpl(this);
        }
        return managementPolicies;
    }
}