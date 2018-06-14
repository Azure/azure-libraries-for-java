/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIExperiment;
import com.microsoft.azure.management.batchai.BatchAIExperiments;
import com.microsoft.azure.management.batchai.BatchAIWorkspace;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceId;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.CreatableResourcesImpl;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

/**
 *  Implementation for Experiments.
 */
@LangDefinition
class BatchAIExperimentsImpl
        extends CreatableResourcesImpl<
        BatchAIExperiment,
        BatchAIExperimentImpl,
                ExperimentInner>
        implements BatchAIExperiments {
    private final BatchAIWorkspaceImpl workspace;

    BatchAIExperimentsImpl(final BatchAIWorkspaceImpl workspace) {
        this.workspace = workspace;
    }

    @Override
    public BatchAIExperimentImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    protected BatchAIExperimentImpl wrapModel(String name) {
        ExperimentInner inner = new ExperimentInner();
        return new BatchAIExperimentImpl(name, workspace, inner);
    }

    @Override
    protected BatchAIExperimentImpl wrapModel(ExperimentInner inner) {
        if (inner == null) {
            return null;
        }
        return new BatchAIExperimentImpl(inner.name(), workspace, inner);
    }

    @Override
    public PagedList<BatchAIExperiment> list() {
        return wrapList(inner().listByWorkspace(workspace.resourceGroupName(), workspace.name()));
    }

    @Override
    public Observable<BatchAIExperiment> listAsync() {
        return wrapPageAsync(inner().listByWorkspaceAsync(workspace.resourceGroupName(), workspace.name()));
    }

    @Override
    public BatchAIWorkspace parent() {
        return workspace;
    }

    @Override
    public BatchAIExperimentImpl getById(String id) {
        return (BatchAIExperimentImpl) getByIdAsync(id).toBlocking().single();
    }

    @Override
    public Observable<BatchAIExperiment> getByIdAsync(String id) {
        ResourceId resourceId = ResourceId.fromString(id);
        return inner().getAsync(resourceId.resourceGroupName(), workspace.name(), resourceId.name()).map(new Func1<ExperimentInner, BatchAIExperiment>() {
            @Override
            public BatchAIExperiment call(ExperimentInner innerT) {
                return wrapModel(innerT);
            }
        });
    }

    @Override
    public ServiceFuture<BatchAIExperiment> getByIdAsync(String id, ServiceCallback<BatchAIExperiment> callback) {
        return ServiceFuture.fromBody(getByIdAsync(id), callback);
    }

    @Override
    public BatchAIManager manager() {
        return workspace.manager();
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        ResourceId resourceId = ResourceId.fromString(id);
        return inner().deleteAsync(resourceId.resourceGroupName(), workspace.name(), resourceId.name()).toCompletable();
    }

    @Override
    public ExperimentsInner inner() {
        return manager().inner().experiments();
    }

    @Override
    public Observable<BatchAIExperiment> getByNameAsync(String name) {
        return inner().getAsync(workspace.resourceGroupName(), workspace.name(), name).map(new Func1<ExperimentInner, BatchAIExperiment>() {
            @Override
            public BatchAIExperiment call(ExperimentInner innerT) {
                return wrapModel(innerT);
            }
        });
    }

    @Override
    public BatchAIExperimentImpl getByName(String name) {
        return (BatchAIExperimentImpl) getByNameAsync(name).toBlocking().single();
    }

    @Override
    public void deleteByName(String name) {
        deleteByNameAsync(name).await();
    }

    @Override
    public ServiceFuture<Void> deleteByNameAsync(String name, ServiceCallback<Void> callback) {
        return ServiceFuture.fromResponse(inner().deleteWithServiceResponseAsync(workspace.resourceGroupName(), workspace.name(), name), callback);
    }

    @Override
    public Completable deleteByNameAsync(String name) {
        return inner().deleteAsync(workspace.resourceGroupName(), workspace.name(), name).toCompletable();
    }
}
