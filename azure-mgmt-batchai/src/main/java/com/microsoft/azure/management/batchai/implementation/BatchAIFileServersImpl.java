/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIFileServer;
import com.microsoft.azure.management.batchai.BatchAIFileServers;
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
 *  Implementation for BatchAIFileServers.
 */
@LangDefinition
class BatchAIFileServersImpl
        extends CreatableResourcesImpl<
                        BatchAIFileServer,
                        BatchAIFileServerImpl,
                        FileServerInner>
        implements BatchAIFileServers {
    private final WorkspaceImpl workspace;

    BatchAIFileServersImpl(final WorkspaceImpl workspace) {
        this.workspace = workspace;
    }

    @Override
    public BatchAIFileServerImpl define(String name) {
        return wrapModel(name);
    }

    // Fluent model create helpers

    @Override
    protected BatchAIFileServerImpl wrapModel(String name) {
        FileServerInner inner = new FileServerInner();
        return new BatchAIFileServerImpl(name, workspace, inner);
    }

    @Override
    protected BatchAIFileServerImpl wrapModel(FileServerInner inner) {
        if (inner == null) {
            return null;
        }
        return new BatchAIFileServerImpl(inner.name(), workspace, inner);
    }

    @Override
    public PagedList<BatchAIFileServer> listByResourceGroup(String resourceGroupName) {
        return null;
    }

    @Override
    public Observable<BatchAIFileServer> listByResourceGroupAsync(String resourceGroupName) {
        return null;
    }

    @Override
    public PagedList<BatchAIFileServer> list() {
        return new GroupPagedList<BatchAIFileServer>(this.manager().resourceManager().resourceGroups().list()) {
            @Override
            public List<BatchAIFileServer> listNextGroup(String resourceGroupName) {
                return wrapList(BatchAIFileServersImpl.this.inner().listByWorkspace(resourceGroupName, workspace.name()));
            }
        };
    }

    @Override
    public Observable<BatchAIFileServer> listAsync() {
        return null;
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
    public BatchAIFileServer getById(String id) {
        return null;
    }

    @Override
    public Observable<BatchAIFileServer> getByIdAsync(String id) {
        String fileServerName = ResourceId.fromString(id).name();
        return manager().inner().fileServers().getAsync(workspace.resourceGroupName(), workspace.name(), fileServerName).map(new Func1<FileServerInner, BatchAIFileServer>() {
            @Override
            public BatchAIFileServer call(FileServerInner fileServerInner) {
                if (fileServerInner == null) {
                    return null;
                } else {
                    return new BatchAIFileServerImpl(fileServerInner.name(), workspace, fileServerInner);
                }
            }
        });
    }

    @Override
    public ServiceFuture<BatchAIFileServer> getByIdAsync(String id, ServiceCallback<BatchAIFileServer> callback) {
        return null;
    }

    @Override
    public BatchAIFileServer getByResourceGroup(String resourceGroupName, String name) {
        return null;
    }

    @Override
    public Observable<BatchAIFileServer> getByResourceGroupAsync(String resourceGroupName, String name) {
        return null;
    }

    @Override
    public ServiceFuture<BatchAIFileServer> getByResourceGroupAsync(String resourceGroupName, String name, ServiceCallback<BatchAIFileServer> callback) {
        return null;
    }

    @Override
    public BatchAIManager manager() {
        return null;
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        return null;
    }

    @Override
    public FileServersInner inner() {
        return null;
    }
}
