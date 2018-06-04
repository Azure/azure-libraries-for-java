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
import com.microsoft.azure.management.batchai.JobsListByExperimentOptions;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.CreatableResourcesImpl;
import com.microsoft.azure.management.resources.fluentcore.dag.TaskGroup;
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
    private final WorkspaceImpl workspace;
    private final ExperimentImpl experiment;

    protected BatchAIJobsImpl(ExperimentImpl experiment, TaskGroup parentTaskGroup, String childResourceName) {
        this.workspace = experiment.parent();
        this.experiment = experiment;
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
        return this.inner().deleteAsync(workspace.resourceGroupName(), workspace.name(), experiment.name(), name, callback);
    }

    @Override
    public Completable deleteByNameAsync(String name) {
        return this.inner().deleteAsync(workspace.resourceGroupName(), workspace.name(), parent().name(), name).toCompletable();
    }

    @Override
    public PagedList<BatchAIJob> list() {
        return wrapList(workspace.manager().inner().jobs().listByExperiment(workspace.resourceGroupName(), workspace.name(), experiment.name()));
//        return new GroupPagedList<BatchAIJob>(workspace.manager().inner().experiments().resourceManager().resourceGroups().list()) {
//            @Override
//            public List<BatchAIJob> listNextGroup(String resourceGroupName) {
//                return wrapList(BatchAIJobsImpl.this.inner().listByExperiment(resourceGroupName));
//            }
//        };
    }

    @Override
    public PagedList<BatchAIJob> list(int maxResults) {
        return wrapList(workspace.manager().inner().jobs()
                .listByExperiment(workspace.resourceGroupName(), workspace.name(), experiment.name(), new JobsListByExperimentOptions().withMaxResults(maxResults)));
//        return new GroupPagedList<BatchAIJob>(workspace.manager().inner().experiments().resourceManager().resourceGroups().list()) {
//            @Override
//            public List<BatchAIJob> listNextGroup(String resourceGroupName) {
//                return wrapList(BatchAIJobsImpl.this.inner().listByExperiment(resourceGroupName));
//            }
//        };
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

//    @Override
//    protected Completable deleteInnerAsync(String resourceGroupName, String name) {
//        return inner().deleteAsync(resourceGroupName, name).toCompletable();
//    }

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
    protected BatchAIJobImpl wrapModel(String name) {
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

    @Override
    protected BatchAIJobImpl wrapModel(JobInner inner) {
        return null;
    }

    @Override
    public BatchAICluster parent() {
        return null;
    }
}