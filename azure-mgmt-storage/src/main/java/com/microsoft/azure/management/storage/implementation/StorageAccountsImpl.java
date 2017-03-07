/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage.implementation;

import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.TopLevelCrudableResourcesImpl;
import com.microsoft.azure.management.storage.CheckNameAvailabilityResult;
import com.microsoft.azure.management.storage.SkuName;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.azure.management.storage.StorageAccounts;
import rx.Completable;

/**
 * The implementation of StorageAccounts and its parent interfaces.
 */
class StorageAccountsImpl
        extends TopLevelCrudableResourcesImpl<
                                StorageAccount,
                                StorageAccountImpl,
                                StorageAccountInner,
                                StorageAccountsInner,
                                StorageManager>
        implements StorageAccounts {

    StorageAccountsImpl(final StorageManager storageManager) {
        super(storageManager.inner().storageAccounts(), storageManager);
    }

    @Override
    public CheckNameAvailabilityResult checkNameAvailability(String name) {
        return new CheckNameAvailabilityResult(this.inner().checkNameAvailability(name));
    }

    @Override
    protected Completable deleteInnerAsync(String resourceGroupName, String name) {
        return this.inner().deleteAsync(resourceGroupName, name).toCompletable();
    }

    @Override
    public StorageAccountImpl define(String name) {
        return wrapModel(name)
                .withSku(SkuName.STANDARD_GRS)
                .withGeneralPurposeAccountKind();
    }

    @Override
    protected StorageAccountImpl wrapModel(String name) {
        return new StorageAccountImpl(name, new StorageAccountInner(), this.manager());
    }

    @Override
    protected StorageAccountImpl wrapModel(StorageAccountInner storageAccountInner) {
        if (storageAccountInner == null) {
            return null;
        }
        return new StorageAccountImpl(storageAccountInner.name(), storageAccountInner, this.manager());
    }
}
