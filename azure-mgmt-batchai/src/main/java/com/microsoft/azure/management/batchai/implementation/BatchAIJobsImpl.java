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
import com.microsoft.azure.management.batchai.BatchAIJobs;
import com.microsoft.azure.management.batchai.Experiment;
import com.microsoft.azure.management.batchai.Workspace;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.ExternalChildResourcesNonCachedImpl;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupPagedList;
import com.microsoft.azure.management.resources.fluentcore.dag.TaskGroup;
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
class BatchAIJobsImpl
        extends ExternalChildResourcesNonCachedImpl<
                BatchAIJobImpl,
                BatchAIJob,
                ExperimentImpl,
                Experiment,
        Experiment>
        implements BatchAIJobs {


//    WebhookImpl,
//    Webhook,
//    WebhookInner,
//    RegistryImpl,
//    Registry


    /**
     * Creates a new ExternalNonInlineChildResourcesImpl.
     *
     * @param parent            the parent Azure resource
     * @param parentTaskGroup   the TaskGroup the parent Azure resource belongs to
     * @param childResourceName the child resource name
     */
    protected BatchAIJobsImpl(Experiment parent, TaskGroup parentTaskGroup, String childResourceName) {
        super(parent, parentTaskGroup, childResourceName);
    }

//    BatchAIJobsImpl(final ExperimentImpl parent) {
//        super(parent.parent().manager().inner().jobs(), parent.parent().manager());
//        this.parent = parent;
//    }
//
//    BatchAIJobsImpl(final BatchAIManager manager) {
//        super(manager.inner().jobs(), manager);
//        parent = null;
//    }


//    @Override
//    protected BatchAIJobImpl wrapModel(String name) {
//        return new BatchAIJobImpl(name, new JobInner(), manager());
//    }
//
//    @Override
//    protected BatchAIJobImpl wrapModel(JobInner inner) {
//        if (inner == null) {
//            return null;
//        }
//        return new BatchAIJobImpl(inner.name(), inner, manager());
//    }

    @Override
    public BatchAIJobImpl define(String name) {
        return null;
//        return wrapModel(name);
    }

    @Override
    public void deleteByName(String name) {
        deleteByNameAsync(name).await();
    }

    @Override
    public ServiceFuture<Void> deleteByNameAsync(String name, ServiceCallback<Void> callback) {
        Workspace workspace = parent().parent();
        return this.inner().deleteAsync(p.resourceGroupName(), name, callback);
    }

    @Override
    public Completable deleteByNameAsync(String name) {
        Workspace workspace = parent().parent();
        return this.inner().deleteAsync(workspace.resourceGroupName(), workspace.name(), parent().name(), name).toCompletable();
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
    public BatchAIJob getByName(String name) {
        JobInner inner = this.manager().inner().jobs()
                .getByResourceGroup(this.parent().parent().resourceGroupName(), name);
        return new BatchAIJobImpl(name, inner, manager());
    }

    @Override
    public Observable<BatchAIJob> listAsync() {
        return this.parent().parent().manager().resourceManager().resourceGroups().listAsync()
                .flatMap(new Func1<ResourceGroup, Observable<BatchAIJob>>() {
                    @Override
                    public Observable<BatchAIJob> call(ResourceGroup resourceGroup) {
                        return wrapPageAsync(inner().listByResourceGroupAsync(resourceGroup.name()));
                    }
                });
    }

    @Override
    protected Completable deleteInnerAsync(String resourceGroupName, String name) {
        return inner().deleteAsync(resourceGroupName, name).toCompletable();
    }

    @Override
    public Observable<BatchAIJob> getByNameAsync(String name) {
        Workspace workspace = parent().parent();
        return inner().getAsync(workspace.resourceGroupName(), workspace.name(), parent().name(), name)
                .map(new Func1<JobInner, BatchAIJob>() {
                    @Override
                    public BatchAIJob call(JobInner inner) {
                        return wrapModel(inner);
                    }
                });
    }

    @Override
    public BatchAIJob getById(String id) {
        return null;
    }

    @Override
    public Observable<BatchAIJob> getByIdAsync(String id) {
        return null;
    }

    @Override
    public ServiceFuture<BatchAIJob> getByIdAsync(String id, ServiceCallback<BatchAIJob> callback) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public ServiceFuture<Void> deleteByIdAsync(String id, ServiceCallback<Void> callback) {
        return null;
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        return null;
    }

    @Override
    public JobsInner inner() {
        return null;
    }
}