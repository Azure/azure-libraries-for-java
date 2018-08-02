/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.storage.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;
import com.microsoft.azure.v2.management.storage.CheckNameAvailabilityResult;
import com.microsoft.azure.v2.management.storage.SkuName;
import com.microsoft.azure.v2.management.storage.StorageAccount;
import com.microsoft.azure.v2.management.storage.StorageAccounts;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import io.reactivex.Maybe;

/**
 * The implementation of StorageAccounts and its parent interfaces.
 */
@LangDefinition
class StorageAccountsImpl
    extends TopLevelModifiableResourcesImpl<
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
        return this.checkNameAvailabilityAsync(name).blockingGet();
    }

    @Override
    public Maybe<CheckNameAvailabilityResult> checkNameAvailabilityAsync(String name) {
        return this.inner().checkNameAvailabilityAsync(name).map(CheckNameAvailabilityResult::new);
    }

    @Override
    public ServiceFuture<CheckNameAvailabilityResult> checkNameAvailabilityAsync(String name, ServiceCallback<CheckNameAvailabilityResult> callback) {
        return ServiceFuture.fromBody(this.checkNameAvailabilityAsync(name), callback);
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
