/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAICluster;
import com.microsoft.azure.management.batchai.BatchAIClusters;
import com.microsoft.azure.management.batchai.Workspace;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceId;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.CreatableResourcesImpl;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

/**
 *  Implementation for BatchAIClusters.
 */
@LangDefinition
class BatchAIClustersImpl
        extends CreatableResourcesImpl<
                        BatchAICluster,
                        BatchAIClusterImpl,
                        ClusterInner>
        implements BatchAIClusters {
    private final WorkspaceImpl workspace;

    BatchAIClustersImpl(final WorkspaceImpl workspace) {
        this.workspace = workspace;
    }

    @Override
    public BatchAIClusterImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    protected BatchAIClusterImpl wrapModel(String name) {
        ClusterInner inner = new ClusterInner();
        return new BatchAIClusterImpl(name, workspace, inner);
    }

    @Override
    protected BatchAIClusterImpl wrapModel(ClusterInner inner) {
        if (inner == null) {
            return null;
        }
        return new BatchAIClusterImpl(inner.name(), workspace, inner);
    }

    @Override
    public PagedList<BatchAICluster> list() {
        return wrapList(BatchAIClustersImpl.this.inner().listByWorkspace(workspace.resourceGroupName(), workspace.name()));
    }

    @Override
    public Observable<BatchAICluster> listAsync() {
        return wrapPageAsync(inner().listByWorkspaceAsync(workspace.resourceGroupName(), workspace.name()));
    }

    @Override
    public Workspace parent() {
        return workspace;
    }

   @Override
    public BatchAIClusterImpl getById(String id) {
       return (BatchAIClusterImpl) getByIdAsync(id).toBlocking().single();
    }

    @Override
    public Observable<BatchAICluster> getByIdAsync(String id) {
        ResourceId resourceId = ResourceId.fromString(id);
        return inner().getAsync(resourceId.resourceGroupName(), workspace.name(), resourceId.name()).map(new Func1<ClusterInner, BatchAICluster>() {
            @Override
            public BatchAICluster call(ClusterInner innerT) {
                return wrapModel(innerT);
            }
        });
    }

    @Override
    public ServiceFuture<BatchAICluster> getByIdAsync(String id, ServiceCallback<BatchAICluster> callback) {
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
    public ClustersInner inner() {
        return manager().inner().clusters();
    }

    @Override
    public Observable<BatchAICluster> getByNameAsync(String name) {
        return inner().getAsync(workspace.resourceGroupName(), workspace.name(), name).map(new Func1<ClusterInner, BatchAICluster>() {
            @Override
            public BatchAICluster call(ClusterInner innerT) {
                return wrapModel(innerT);
            }
        });
    }

    @Override
    public BatchAIClusterImpl getByName(String name) {
        return (BatchAIClusterImpl) getByNameAsync(name).toBlocking().single();
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
