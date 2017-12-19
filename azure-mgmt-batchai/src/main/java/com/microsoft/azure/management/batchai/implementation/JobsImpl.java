/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.Cluster;
import com.microsoft.azure.management.batchai.Job;
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
        Job,
        JobImpl,
        JobInner,
        JobsInner,
        BatchAIManager>
        implements Jobs {

    private final ClusterImpl parent;

    JobsImpl(final ClusterImpl parent) {
        super(parent.manager().inner().jobs(), parent.manager());
        this.parent = parent;
    }


    @Override
    protected JobImpl wrapModel(String name) {
        return new JobImpl(name, parent, new JobInner())
                .withRegion(parent.regionName())
                .withExistingResourceGroup(parent.resourceGroupName());
    }

    @Override
    protected JobImpl wrapModel(JobInner inner) {
        if (inner == null) {
            return null;
        }
        return new JobImpl(inner.name(), parent, inner);
    }

    @Override
    public JobImpl define(String name) {
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
    public PagedList<Job> list() {
        return new GroupPagedList<Job>(this.manager().resourceManager().resourceGroups().list()) {
            @Override
            public List<Job> listNextGroup(String resourceGroupName) {
                return wrapList(JobsImpl.this.inner().listByResourceGroup(resourceGroupName));
            }
        };
    }

    @Override
    public Job getByName(String name) {
        JobInner inner = this.manager().inner().jobs()
                .getByResourceGroup(this.parent().resourceGroupName(), name);
        return new JobImpl(name, parent, inner);
    }

    @Override
    public Cluster parent() {
        return this.parent;
    }

    @Override
    public Observable<Job> listAsync() {
        return this.manager().resourceManager().resourceGroups().listAsync()
                .flatMap(new Func1<ResourceGroup, Observable<Job>>() {
                    @Override
                    public Observable<Job> call(ResourceGroup resourceGroup) {
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
    public Observable<Job> getByNameAsync(String name) {
        return inner().getByResourceGroupAsync(parent.resourceGroupName(), name)
                .map(new Func1<JobInner, Job>() {
                    @Override
                    public Job call(JobInner inner) {
                        return wrapModel(inner);
                    }
                });
    }
}