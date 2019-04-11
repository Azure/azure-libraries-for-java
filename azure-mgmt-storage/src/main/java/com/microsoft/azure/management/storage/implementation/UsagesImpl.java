/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.storage.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import com.microsoft.azure.management.storage.StorageUsage;
import com.microsoft.azure.management.storage.Usages;
import rx.Observable;

/**
 * The implementation of {@link Usages}.
 */
@LangDefinition
class UsagesImpl extends ReadableWrappersImpl<StorageUsage, UsageImpl, UsageInner>
        implements
        Usages {
    private final StorageManager manager;

    UsagesImpl(StorageManager storageManager) {
        this.manager = storageManager;
    }

    @Override
    public PagedList<StorageUsage> list() {
        //TODO: service is not supporting this listing anymore
        throw new RuntimeException("Storage is no longer supporting usages listing anymore");
    }

    @Override
    public Observable<StorageUsage> listAsync() {
        //TODO: service is not supporting this async listing anymore
        //return wrapPageAsync(inner().listAsync());
        throw new RuntimeException("Storage is no longer supporting usages listing anymore");
    }

    @Override
    protected UsageImpl wrapModel(UsageInner usageInner) {
        if (usageInner == null) {
            return null;
        }
        return new UsageImpl(usageInner);
    }

    @Override
    public UsagesInner inner() {
        return this.manager.inner().usages();
    }

    @Override
    public StorageManager manager() {
        return this.manager;
    }
}