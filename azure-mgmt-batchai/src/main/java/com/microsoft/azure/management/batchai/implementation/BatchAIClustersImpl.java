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
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.GroupableResourcesImpl;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupPagedList;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.List;

/**
 *  Implementation for BatchAIClusters.
 */
@LangDefinition
class BatchAIClustersImpl
        extends GroupableResourcesImpl<
                BatchAICluster,
                BatchAIClusterImpl,
                ClusterInner,
                ClustersInner,
                BatchAIManager>
        implements BatchAIClusters {
    private final WorkspaceImpl parent;

    BatchAIClustersImpl(final WorkspaceImpl workspace) {
        super(workspace.manager().inner().clusters(), workspace.manager());
        this.parent = workspace;
    }

    @Override
    public BatchAIClusterImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    protected BatchAIClusterImpl wrapModel(String name) {
        ClusterInner inner = new ClusterInner();
        return new BatchAIClusterImpl(name, parent, inner);
    }

    @Override
    protected BatchAIClusterImpl wrapModel(ClusterInner inner) {
        if (inner == null) {
            return null;
        }
        return new BatchAIClusterImpl(inner.name(), parent, inner);
    }

    @Override
    public PagedList<BatchAICluster> listByResourceGroup(String resourceGroupName) {
        return wrapList(BatchAIClustersImpl.this.inner().listByWorkspace(resourceGroupName, parent.name()));
    }

    @Override
    public Observable<BatchAICluster> listByResourceGroupAsync(String resourceGroupName) {
        return wrapPageAsync(inner().listByWorkspaceAsync(resourceGroupName, parent.name()));
    }

    @Override
    protected Observable<ClusterInner> getInnerAsync(String resourceGroupName, String name) {
        return inner().getAsync(resourceGroupName, parent.name(), name);
    }

    @Override
    protected Completable deleteInnerAsync(String resourceGroupName, String name) {
        return inner().deleteAsync(resourceGroupName, parent.name(), name).toCompletable();
    }

    @Override
    public PagedList<BatchAICluster> list() {
        return new GroupPagedList<BatchAICluster>(this.manager().resourceManager().resourceGroups().list()) {
            @Override
            public List<BatchAICluster> listNextGroup(String resourceGroupName) {
                return wrapList(BatchAIClustersImpl.this.inner().listByWorkspace(resourceGroupName, parent.name()));
            }
        };
    }

    @Override
    public Observable<BatchAICluster> listAsync() {
        return this.manager().resourceManager().resourceGroups().listAsync()
                .flatMap(new Func1<ResourceGroup, Observable<BatchAICluster>>() {
                    @Override
                    public Observable<BatchAICluster> call(ResourceGroup resourceGroup) {
                        return wrapPageAsync(inner().listByWorkspaceAsync(resourceGroup.name(), parent.name()));
                    }
                });
    }

    @Override
    public Workspace parent() {
        return parent;
    }
}
