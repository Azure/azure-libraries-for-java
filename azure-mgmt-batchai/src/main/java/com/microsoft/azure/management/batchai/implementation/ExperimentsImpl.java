/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.Experiment;
import com.microsoft.azure.management.batchai.Experiments;
import com.microsoft.azure.management.batchai.Workspace;
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
class ExperimentsImpl
        extends CreatableResourcesImpl<
                Experiment,
                ExperimentImpl,
                ExperimentInner>
        implements Experiments {
    private final WorkspaceImpl workspace;

    ExperimentsImpl(final WorkspaceImpl workspace) {
        this.workspace = workspace;
    }

    @Override
    public ExperimentImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    protected ExperimentImpl wrapModel(String name) {
        ExperimentInner inner = new ExperimentInner();
        return new ExperimentImpl(name, workspace, inner);
    }

    @Override
    protected ExperimentImpl wrapModel(ExperimentInner inner) {
        if (inner == null) {
            return null;
        }
        return new ExperimentImpl(inner.name(), workspace, inner);
    }

    @Override
    public PagedList<Experiment> list() {
        return wrapList(inner().listByWorkspace(workspace.resourceGroupName(), workspace.name()));
    }

    @Override
    public Observable<Experiment> listAsync() {
        return wrapPageAsync(inner().listByWorkspaceAsync(workspace.resourceGroupName(), workspace.name()));
    }

    @Override
    public Workspace parent() {
        return workspace;
    }

    @Override
    public ExperimentImpl getById(String id) {
        return (ExperimentImpl) getByIdAsync(id).toBlocking().single();
    }

    @Override
    public Observable<Experiment> getByIdAsync(String id) {
        ResourceId resourceId = ResourceId.fromString(id);
        return inner().getAsync(resourceId.resourceGroupName(), workspace.name(), resourceId.name()).map(new Func1<ExperimentInner, Experiment>() {
            @Override
            public Experiment call(ExperimentInner innerT) {
                return wrapModel(innerT);
            }
        });
    }

    @Override
    public ServiceFuture<Experiment> getByIdAsync(String id, ServiceCallback<Experiment> callback) {
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
    public Observable<Experiment> getByNameAsync(String name) {
        return inner().getAsync(workspace.resourceGroupName(), workspace.name(), name).map(new Func1<ExperimentInner, Experiment>() {
            @Override
            public Experiment call(ExperimentInner innerT) {
                return wrapModel(innerT);
            }
        });
    }

    @Override
    public ExperimentImpl getByName(String name) {
        return (ExperimentImpl) getByNameAsync(name).toBlocking().single();
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
