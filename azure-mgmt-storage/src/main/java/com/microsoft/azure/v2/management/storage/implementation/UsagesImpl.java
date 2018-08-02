/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.storage.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import com.microsoft.azure.v2.management.storage.StorageUsage;
import com.microsoft.azure.v2.management.storage.Usages;
import io.reactivex.Observable;

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
        return wrapList(inner().list());
    }

    @Override
    public Observable<StorageUsage> listAsync() {
        return wrapPageAsync(inner().listAsync());
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