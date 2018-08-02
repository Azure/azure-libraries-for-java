/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.storage.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import com.microsoft.azure.v2.management.storage.StorageSku;
import com.microsoft.azure.v2.management.storage.StorageSkus;
import io.reactivex.Observable;

/**
 * The implementation for {@link StorageSkus}.
 */
@LangDefinition
class StorageSkusImpl
        extends
        ReadableWrappersImpl<StorageSku, StorageSkuImpl, SkuInner>
        implements
        StorageSkus {

    private final StorageManager manager;

    StorageSkusImpl(StorageManager storageManager) {
        this.manager = storageManager;
    }

    @Override
    protected StorageSkuImpl wrapModel(SkuInner inner) {
        return new StorageSkuImpl(inner);
    }

    @Override
    public PagedList<StorageSku> list() {
        return wrapList(this.inner().list());
    }

    @Override
    public Observable<StorageSku> listAsync() {
        return wrapPageAsync(this.inner().listAsync());
    }

    @Override
    public SkusInner inner() {
        return this.manager.inner().skus();
    }

    @Override
    public StorageManager manager() {
        return this.manager;
    }
}