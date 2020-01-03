/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.storage.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;
import com.azure.management.storage.CheckNameAvailabilityResult;
import com.azure.management.storage.ServiceSasParameters;
import com.azure.management.storage.SkuName;
import com.azure.management.storage.StorageAccount;
import com.azure.management.storage.StorageAccounts;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

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
        return this.checkNameAvailabilityAsync(name).toBlocking().last();
    }

    @Override
    public Observable<CheckNameAvailabilityResult> checkNameAvailabilityAsync(String name) {
        return this.inner().checkNameAvailabilityAsync(name).map(new Func1<CheckNameAvailabilityResultInner, CheckNameAvailabilityResult>() {
            @Override
            public CheckNameAvailabilityResult call(CheckNameAvailabilityResultInner checkNameAvailabilityResultInner) {
                return new CheckNameAvailabilityResult(checkNameAvailabilityResultInner);
            }
        });
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

    @Override
    public String createSasToken(String resourceGroupName, String accountName, ServiceSasParameters parameters) {
        return createSasTokenAsync(resourceGroupName, accountName, parameters).toBlocking().last();
    }

    @Override
    public Observable<String> createSasTokenAsync(String resourceGroupName, String accountName, ServiceSasParameters parameters) {
        return this.inner().listServiceSASAsync(resourceGroupName, accountName, parameters).map(new Func1<ListServiceSasResponseInner, String>() {
            @Override
            public String call(ListServiceSasResponseInner listServiceSasResponseInner) {
                return listServiceSasResponseInner.serviceSasToken();
            }
        });
    }

    @Override
    public void failover(String resourceGroupName, String accountName) {
        failoverAsync(resourceGroupName, accountName).await();
    }

    @Override
    public Completable failoverAsync(String resourceGroupName, String accountName) {
        return this.inner().failoverAsync(resourceGroupName, accountName).toCompletable();
    }
}
