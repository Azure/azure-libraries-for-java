/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.Workspace;
import com.microsoft.azure.management.batchai.Workspaces;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.GroupableResourcesImpl;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupPagedList;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.List;

/**
 * Implementation for Workspaces.
 */
@LangDefinition
public class WorkspacesImpl extends GroupableResourcesImpl<
        Workspace,
        WorkspaceImpl,
        WorkspaceInner,
        WorkspacesInner,
        BatchAIManager>
        implements Workspaces {
    WorkspacesImpl(BatchAIManager manager) {
        super(manager.inner().workspaces(), manager);
    }

    @Override
    public PagedList<Workspace> listByResourceGroup(String groupName) {
        return wrapList(this.inner().listByResourceGroup(groupName));
    }

    @Override
    public Observable<Workspace> listByResourceGroupAsync(String groupName) {
        return this.manager().resourceManager().resourceGroups().listAsync()
                .flatMap(new Func1<ResourceGroup, Observable<Workspace>>() {
                    @Override
                    public Observable<Workspace> call(ResourceGroup resourceGroup) {
                        return wrapPageAsync(inner().listByResourceGroupAsync(resourceGroup.name()));
                    }
                });
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
    protected WorkspaceImpl wrapModel(String name) {
        WorkspaceInner inner = new WorkspaceInner();
        return new WorkspaceImpl(name, inner, super.manager());
    }

    @Override
    protected WorkspaceImpl wrapModel(WorkspaceInner inner) {
        if (inner == null) {
            return null;
        }
        return new WorkspaceImpl(inner.name(), inner, this.manager());
    }

    @Override
    public WorkspaceImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    public PagedList<Workspace> list() {
        return wrapList(WorkspacesImpl.this.inner().list());
    }

    @Override
    public Observable<Workspace> listAsync() {
        return this.manager().resourceManager().resourceGroups().listAsync()
                .flatMap(new Func1<ResourceGroup, Observable<Workspace>>() {
                    @Override
                    public Observable<Workspace> call(ResourceGroup resourceGroup) {
                        return wrapPageAsync(inner().listByResourceGroupAsync(resourceGroup.name()));
                    }
                });
    }
}
