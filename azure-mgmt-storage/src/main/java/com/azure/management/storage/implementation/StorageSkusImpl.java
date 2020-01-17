/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.storage.implementation;

import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedIterable;
import com.azure.management.storage.StorageSkus;
import com.azure.management.storage.models.SkuInner;
import com.azure.management.storage.models.SkusInner;

/**
 * The implementation for {@link StorageSkus}.
 */
class StorageSkusImpl
        implements
        StorageSkus {

    private final StorageManager manager;

    StorageSkusImpl(StorageManager storageManager) {
        this.manager = storageManager;
    }

    @Override
    public StorageManager getManager() {
        return this.manager;
    }

    @Override
    public PagedIterable<SkuInner> list() {
        return this.getInner().list();
    }

    @Override
    public PagedFlux<SkuInner> listAsync() {
        return this.getInner().listAsync();
    }

    @Override
    public SkusInner getInner() {
        return this.getInner();
    }
}