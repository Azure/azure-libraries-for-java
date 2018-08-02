/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.storage;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsBatchDeletion;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsDeletingByResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsGettingByResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsListingByResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsBatchCreation;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsDeletingById;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.v2.management.storage.StorageAccount.DefinitionStages.Blank;
import com.microsoft.azure.v2.management.storage.implementation.StorageAccountsInner;
import com.microsoft.azure.v2.management.storage.implementation.StorageManager;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import io.reactivex.Maybe;

/**
 * Entry point for storage accounts management API.
 */
@Fluent
public interface StorageAccounts extends
        SupportsListing<StorageAccount>,
        SupportsCreating<Blank>,
        SupportsDeletingById,
        SupportsListingByResourceGroup<StorageAccount>,
        SupportsGettingByResourceGroup<StorageAccount>,
        SupportsGettingById<StorageAccount>,
        SupportsDeletingByResourceGroup,
        SupportsBatchCreation<StorageAccount>,
        SupportsBatchDeletion,
        HasManager<StorageManager>,
        HasInner<StorageAccountsInner> {
    /**
     * Checks that account name is valid and is not in use.
     *
     * @param name the account name to check
     * @return whether the name is available and other info if not
     */
    CheckNameAvailabilityResult checkNameAvailability(String name);

    /**
     * Checks that account name is valid and is not in use asynchronously.
     *
     * @param name the account name to check
     * @return a representation of the deferred computation of this call, returning whether the name is available and other info if not
     */
    Maybe<CheckNameAvailabilityResult> checkNameAvailabilityAsync(String name);

    /**
     * Checks that account name is valid and is not in use asynchronously.
     *
     * @param name the account name to check
     * @param callback the callback to call on success or failure
     * @return a handle to cancel the request
     */
    ServiceFuture<CheckNameAvailabilityResult> checkNameAvailabilityAsync(String name, ServiceCallback<CheckNameAvailabilityResult> callback);
}
