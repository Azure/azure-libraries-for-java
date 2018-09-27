/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.storage.implementation;

import com.microsoft.azure.v2.AzureEnvironment;
import com.microsoft.azure.v2.credentials.AzureTokenCredentials;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.AzureConfigurable;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.implementation.AzureConfigurableImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.implementation.Manager;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.ProviderRegistrationPolicyFactory;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.ResourceManagerThrottlingPolicyFactory;
import com.microsoft.azure.v2.management.storage.StorageAccounts;
import com.microsoft.azure.v2.management.storage.StorageSkus;
import com.microsoft.azure.v2.management.storage.Usages;
import com.microsoft.rest.v2.http.HttpPipeline;
import com.microsoft.rest.v2.http.HttpPipelineBuilder;
import com.microsoft.rest.v2.policy.CredentialsPolicyFactory;

/**
 * Entry point to Azure storage resource management.
 */
public final class StorageManager extends Manager<StorageManager, StorageManagementClientImpl> {
    // Collections
    private StorageAccounts storageAccounts;
    private Usages storageUsages;
    private StorageSkus storageSkus;

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
        return new StorageManager(new HttpPipelineBuilder()
                .withRequestPolicy(new CredentialsPolicyFactory(credentials))
                .withRequestPolicy(new ProviderRegistrationPolicyFactory(credentials))
                .withRequestPolicy(new ResourceManagerThrottlingPolicyFactory())
                .build(), subscriptionId, credentials.environment());
    }

    /**
     * Creates an instance of StorageManager that exposes storage resource management API entry points.
     *
     * @param httpPipeline the HttpPipeline to be used for API calls
     * @param subscriptionId the subscription UUID
     * @param environment the azure environment hosting the APIs
     * @return the StorageManager
     */
    public static StorageManager authenticate(HttpPipeline httpPipeline, String subscriptionId, AzureEnvironment environment) {
        return new StorageManager(httpPipeline, subscriptionId, environment);
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
            return StorageManager.authenticate(buildPipeline(credentials), subscriptionId, credentials.environment());
        }
    }

    private StorageManager(HttpPipeline httpPipeline, String subscriptionId, AzureEnvironment environment) {
        super(
                httpPipeline,
                subscriptionId,
                environment,
                new StorageManagementClientImpl(httpPipeline, environment).withSubscriptionId(subscriptionId));
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
}