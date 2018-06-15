/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIJob;
import com.microsoft.azure.management.batchai.BatchAIJobs;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.GroupableResourcesImpl;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupPagedList;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.List;

/**
 * The implementation of Jobs.
 */
@LangDefinition
class BatchAIJobsImpl
        extends GroupableResourcesImpl<
        BatchAIJob,
        BatchAIJobImpl,
        JobInner,
        JobsInner,
        BatchAIManager>
        implements BatchAIJobs {

    BatchAIJobsImpl(final BatchAIManager manager) {
        super(manager.inner().jobs(), manager);
    }


    @Override
    protected BatchAIJobImpl wrapModel(String name) {
        return new BatchAIJobImpl(name, new JobInner(), manager());
    }

    @Override
    protected BatchAIJobImpl wrapModel(JobInner inner) {
        if (inner == null) {
            return null;
        }
        return new BatchAIJobImpl(inner.name(), inner, manager());
    }

    @Override
    public BatchAIJobImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    public PagedList<BatchAIJob> list() {
        return new GroupPagedList<BatchAIJob>(this.manager().resourceManager().resourceGroups().list()) {
            @Override
            public List<BatchAIJob> listNextGroup(String resourceGroupName) {
                return wrapList(BatchAIJobsImpl.this.inner().listByResourceGroup(resourceGroupName));
            }
        };
    }

    @Override
    public Observable<BatchAIJob> listAsync() {
        return this.manager().resourceManager().resourceGroups().listAsync()
                .flatMap(new Func1<ResourceGroup, Observable<BatchAIJob>>() {
                    @Override
                    public Observable<BatchAIJob> call(ResourceGroup resourceGroup) {
                        return wrapPageAsync(inner().listByResourceGroupAsync(resourceGroup.name()));
                    }
                });
    }

    @Override
    protected Observable<JobInner> getInnerAsync(String resourceGroupName, String name) {
        return inner().getByResourceGroupAsync(resourceGroupName, name);
    }

    @Override
    protected Completable deleteInnerAsync(String resourceGroupName, String name) {
        return inner().deleteAsync(resourceGroupName, name).toCompletable();
    }
}