/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIWorkspace;
import com.microsoft.azure.management.batchai.BatchAIWorkspaces;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.GroupableResourcesImpl;
import rx.Completable;
import rx.Observable;

/**
 * Implementation for Workspaces.
 */
@LangDefinition
public class BatchAIWorkspacesImpl extends GroupableResourcesImpl<
        BatchAIWorkspace,
        BatchAIWorkspaceImpl,
        WorkspaceInner,
        WorkspacesInner,
        BatchAIManager>
        implements BatchAIWorkspaces {
    BatchAIWorkspacesImpl(BatchAIManager manager) {
        super(manager.inner().workspaces(), manager);
    }

    @Override
    public PagedList<BatchAIWorkspace> listByResourceGroup(String groupName) {
        return wrapList(this.inner().listByResourceGroup(groupName));
    }

    @Override
    public Observable<BatchAIWorkspace> listByResourceGroupAsync(String groupName) {
        return wrapPageAsync(inner().listByResourceGroupAsync(groupName));
    }

    @Override
    protected Observable<WorkspaceInner> getInnerAsync(String groupName, String name) {
        return this.inner().getByResourceGroupAsync(groupName, name);
    }

    @Override
    protected Completable deleteInnerAsync(String groupName, String name) {
        return this.inner().deleteAsync(groupName, name).toCompletable();
    }

    @Override
    protected BatchAIWorkspaceImpl wrapModel(String name) {
        WorkspaceInner inner = new WorkspaceInner();
        return new BatchAIWorkspaceImpl(name, inner, super.manager());
    }

    @Override
    protected BatchAIWorkspaceImpl wrapModel(WorkspaceInner inner) {
        if (inner == null) {
            return null;
        }
        return new BatchAIWorkspaceImpl(inner.name(), inner, this.manager());
    }

    @Override
    public BatchAIWorkspaceImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    public PagedList<BatchAIWorkspace> list() {
        return wrapList(BatchAIWorkspacesImpl.this.inner().list());
    }

    @Override
    public Observable<BatchAIWorkspace> listAsync() {
        return wrapPageAsync(inner().listAsync());
    }
}
