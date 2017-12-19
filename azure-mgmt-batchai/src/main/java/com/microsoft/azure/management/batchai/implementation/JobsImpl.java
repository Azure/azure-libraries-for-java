/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAICluster;
import com.microsoft.azure.management.batchai.BatchAIJob;
import com.microsoft.azure.management.batchai.Jobs;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.GroupableResourcesImpl;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupPagedList;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.List;

/**
 * The implementation of Jobs.
 */
@LangDefinition
class JobsImpl
        extends GroupableResourcesImpl<
        BatchAIJob,
        BatchAIJobImpl,
        JobInner,
        JobsInner,
        BatchAIManager>
        implements Jobs {

    private final BatchAIClusterImpl parent;

    JobsImpl(final BatchAIClusterImpl parent) {
        super(parent.manager().inner().jobs(), parent.manager());
        this.parent = parent;
    }


    @Override
    protected BatchAIJobImpl wrapModel(String name) {
        return new BatchAIJobImpl(name, parent, new JobInner())
                .withRegion(parent.regionName())
                .withExistingResourceGroup(parent.resourceGroupName());
    }

    @Override
    protected BatchAIJobImpl wrapModel(JobInner inner) {
        if (inner == null) {
            return null;
        }
        return new BatchAIJobImpl(inner.name(), parent, inner);
    }

    @Override
    public BatchAIJobImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    public void deleteByName(String name) {
        deleteByNameAsync(name).await();
    }

    @Override
    public ServiceFuture<Void> deleteByNameAsync(String name, ServiceCallback<Void> callback) {
        return this.inner().deleteAsync(parent.resourceGroupName(), name, callback);
    }

    @Override
    public Completable deleteByNameAsync(String name) {
        return this.inner().deleteAsync(parent.resourceGroupName(), name).toCompletable();
    }

    @Override
    public PagedList<BatchAIJob> list() {
        return new GroupPagedList<BatchAIJob>(this.manager().resourceManager().resourceGroups().list()) {
            @Override
            public List<BatchAIJob> listNextGroup(String resourceGroupName) {
                return wrapList(JobsImpl.this.inner().listByResourceGroup(resourceGroupName));
            }
        };
    }

    @Override
    public BatchAIJob getByName(String name) {
        JobInner inner = this.manager().inner().jobs()
                .getByResourceGroup(this.parent().resourceGroupName(), name);
        return new BatchAIJobImpl(name, parent, inner);
    }

    @Override
    public BatchAICluster parent() {
        return this.parent;
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

    @Override
    public Observable<BatchAIJob> getByNameAsync(String name) {
        return inner().getByResourceGroupAsync(parent.resourceGroupName(), name)
                .map(new Func1<JobInner, BatchAIJob>() {
                    @Override
                    public BatchAIJob call(JobInner inner) {
                        return wrapModel(inner);
                    }
                });
    }
}