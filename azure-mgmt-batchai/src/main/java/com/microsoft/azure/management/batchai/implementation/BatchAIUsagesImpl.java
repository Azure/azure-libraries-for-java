/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIUsage;
import com.microsoft.azure.management.batchai.BatchAIUsages;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import rx.Observable;

/**
 * The implementation of {@link BatchAIUsages}.
 */
@LangDefinition
class BatchAIUsagesImpl extends ReadableWrappersImpl<BatchAIUsage, BatchAIUsageImpl, UsageInner>
        implements BatchAIUsages {
    private final BatchAIManagementClientImpl client;

    BatchAIUsagesImpl(BatchAIManagementClientImpl client) {
        this.client = client;
    }

    @Override
    public PagedList<BatchAIUsage> listByRegion(Region region) {
        return listByRegion(region.name());
    }

    @Override
    public PagedList<BatchAIUsage> listByRegion(String regionName) {
        return wrapList(client.usages().list(regionName));
    }

    @Override
    public Observable<BatchAIUsage> listByRegionAsync(Region region) {
        return listByRegionAsync(region.name());
    }

    @Override
    public Observable<BatchAIUsage> listByRegionAsync(String regionName) {
        return wrapPageAsync(client.usages().listAsync(regionName));
    }

    @Override
    protected BatchAIUsageImpl wrapModel(UsageInner usageInner) {
        if (usageInner == null) {
            return null;
        }
        return new BatchAIUsageImpl(usageInner);
    }
}