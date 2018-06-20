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
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

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
    private final BatchAIWorkspaceImpl workspace;

    BatchAIFileServersImpl(final BatchAIWorkspaceImpl workspace) {
        this.workspace = workspace;
    }

    @Override
    public BatchAIFileServerImpl define(String name) {
        return wrapModel(name);
    }

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
    public PagedList<BatchAIFileServer> list() {
        return wrapList(BatchAIFileServersImpl.this.inner().listByWorkspace(workspace.resourceGroupName(), workspace.name()));
    }

    @Override
    public Observable<BatchAIFileServer> listAsync() {
        return wrapPageAsync(inner().listByWorkspaceAsync(workspace.resourceGroupName(), workspace.name()));
    }

    @Override
    public BatchAIFileServerImpl getById(String id) {
        return (BatchAIFileServerImpl) getByIdAsync(id).toBlocking().single();
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
    public FileServersInner inner() {
        return manager().inner().fileServers();
    }

    @Override
    public Observable<BatchAIFileServer> getByNameAsync(String name) {
        return inner().getAsync(workspace.resourceGroupName(), workspace.name(), name).map(new Func1<FileServerInner, BatchAIFileServer>() {
            @Override
            public BatchAIFileServer call(FileServerInner inner) {
                return wrapModel(inner);
            }
        });
    }

    @Override
    public BatchAIFileServerImpl getByName(String name) {
        return (BatchAIFileServerImpl) getByNameAsync(name).toBlocking().single();
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
