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
import com.microsoft.azure.management.batchai.JobsListByExperimentOptions;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceId;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.CreatableResourcesImpl;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;


/**
 * The implementation of Jobs.
 */
@LangDefinition
class BatchAIJobsImpl
        extends CreatableResourcesImpl<
                        BatchAIJob,
                        BatchAIJobImpl,
                        JobInner>
        implements BatchAIJobs {
    private final BatchAIWorkspaceImpl workspace;
    private final BatchAIExperimentImpl experiment;

    BatchAIJobsImpl(BatchAIExperimentImpl experiment) {
        this.workspace = (BatchAIWorkspaceImpl) experiment.workspace();
        this.experiment = experiment;
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
        return this.inner().deleteAsync(workspace.resourceGroupName(), workspace.name(), experiment.name(), name, callback);
    }

    @Override
    public Completable deleteByNameAsync(String name) {
        return this.inner().deleteAsync(workspace.resourceGroupName(), workspace.name(), parent().name(), name).toCompletable();
    }

    @Override
    public PagedList<BatchAIJob> list() {
        return wrapList(workspace.manager().inner().jobs().listByExperiment(workspace.resourceGroupName(), workspace.name(), experiment.name()));
    }

    @Override
    public PagedList<BatchAIJob> list(int maxResults) {
        return wrapList(workspace.manager().inner().jobs()
                .listByExperiment(workspace.resourceGroupName(), workspace.name(), experiment.name(), new JobsListByExperimentOptions().withMaxResults(maxResults)));
    }

    @Override
    public BatchAIJob getByName(String name) {
        JobInner inner = workspace.manager().inner().jobs()
                .get(workspace.resourceGroupName(), workspace.name(), experiment.name(), name);
        return new BatchAIJobImpl(name, experiment, inner);
    }

    @Override
    public Observable<BatchAIJob> listAsync() {
        return wrapPageAsync(inner().listByExperimentAsync(workspace.resourceGroupName(), workspace.name(), experiment.name()));
    }

    @Override
    public Observable<BatchAIJob> getByNameAsync(String name) {
        return inner().getAsync(workspace.resourceGroupName(), workspace.name(), parent().name(), name)
                .map(new Func1<JobInner, BatchAIJob>() {
                    @Override
                    public BatchAIJob call(JobInner inner) {
                        return wrapModel(inner);
                    }
                });
    }

    @Override
    public BatchAIJobImpl getById(String id) {
        return (BatchAIJobImpl) getByIdAsync(id).toBlocking().single();
    }

    @Override
    public Observable<BatchAIJob> getByIdAsync(String id) {
        ResourceId resourceId = ResourceId.fromString(id);
        return inner().getAsync(resourceId.resourceGroupName(), workspace.name(), experiment.name(), resourceId.name())
                .map(new Func1<JobInner, BatchAIJob>() {
            @Override
            public BatchAIJob call(JobInner inner) {
                return wrapModel(inner);
            }
        });
    }

    @Override
    public ServiceFuture<BatchAIJob> getByIdAsync(String id, ServiceCallback<BatchAIJob> callback) {
        return ServiceFuture.fromBody(getByIdAsync(id), callback);
    }

    @Override
    protected BatchAIJobImpl wrapModel(String name) {
        JobInner inner = new JobInner();
        return new BatchAIJobImpl(name, experiment, inner);
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        ResourceId resourceId = ResourceId.fromString(id);
        return inner().deleteAsync(resourceId.resourceGroupName(), workspace.name(), experiment.name(), resourceId.name()).toCompletable();
    }

    @Override
    public JobsInner inner() {
        return manager().inner().jobs();
    }

    @Override
    protected BatchAIJobImpl wrapModel(JobInner inner) {
        if (inner == null) {
            return null;
        }
        return new BatchAIJobImpl(inner.name(), experiment, inner);
    }

    @Override
    public BatchAIExperimentImpl parent() {
        return experiment;
    }

    @Override
    public BatchAIManager manager() {
        return workspace.manager();
    }
}