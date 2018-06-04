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
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceId;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.CreatableResourcesImpl;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupPagedList;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.List;

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
    public PagedList<BatchAICluster> listByResourceGroup(String resourceGroupName) {
        return wrapList(BatchAIClustersImpl.this.inner().listByWorkspace(resourceGroupName, workspace.name()));
    }

    @Override
    public Observable<BatchAICluster> listByResourceGroupAsync(String resourceGroupName) {
        return wrapPageAsync(inner().listByWorkspaceAsync(resourceGroupName, workspace.name()));
    }

//    @Override
//    protected Observable<ClusterInner> getInnerAsync(String resourceGroupName, String name) {
//        return inner().getAsync(resourceGroupName, workspace.name(), name);
//    }
//
//    @Override
//    protected Completable deleteInnerAsync(String resourceGroupName, String name) {
//        return inner().deleteAsync(resourceGroupName, workspace.name(), name).toCompletable();
//    }

    @Override
    public PagedList<BatchAICluster> list() {
        return new GroupPagedList<BatchAICluster>(this.manager().resourceManager().resourceGroups().list()) {
            @Override
            public List<BatchAICluster> listNextGroup(String resourceGroupName) {
                return wrapList(BatchAIClustersImpl.this.inner().listByWorkspace(resourceGroupName, workspace.name()));
            }
        };
    }

    @Override
    public Observable<BatchAICluster> listAsync() {
        return this.manager().resourceManager().resourceGroups().listAsync()
                .flatMap(new Func1<ResourceGroup, Observable<BatchAICluster>>() {
                    @Override
                    public Observable<BatchAICluster> call(ResourceGroup resourceGroup) {
                        return wrapPageAsync(inner().listByWorkspaceAsync(resourceGroup.name(), workspace.name()));
                    }
                });
    }

    @Override
    public Workspace parent() {
        return workspace;
    }

    @Override
    public void deleteByResourceGroup(String resourceGroupName, String name) {

    }

    @Override
    public ServiceFuture<Void> deleteByResourceGroupAsync(String resourceGroupName, String name, ServiceCallback<Void> callback) {
        return null;
    }

    @Override
    public Completable deleteByResourceGroupAsync(String resourceGroupName, String name) {
        return null;
    }

    @Override
    public BatchAICluster getById(String id) {
        return null;
    }

    @Override
    public Observable<BatchAICluster> getByIdAsync(String id) {
        return null;
    }

    @Override
    public ServiceFuture<BatchAICluster> getByIdAsync(String id, ServiceCallback<BatchAICluster> callback) {
        return null;
    }

    @Override
    public BatchAICluster getByResourceGroup(String resourceGroupName, String name) {
        return null;
    }

    @Override
    public Observable<BatchAICluster> getByResourceGroupAsync(String resourceGroupName, String name) {
        return null;
    }

    @Override
    public ServiceFuture<BatchAICluster> getByResourceGroupAsync(String resourceGroupName, String name, ServiceCallback<BatchAICluster> callback) {
        return null;
    }

    @Override
    public BatchAIManager manager() {
        return null;
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        ResourceId resourceId = ResourceId.fromString(id);
        return inner().deleteAsync(resourceId.resourceGroupName(), workspace.name(), resourceId.name()).toCompletable();
    }

    @Override
    public ClustersInner inner() {
        return null;
    }
}
