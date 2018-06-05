/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIClusters;
import com.microsoft.azure.management.batchai.BatchAIFileServers;
import com.microsoft.azure.management.batchai.Experiments;
import com.microsoft.azure.management.batchai.ProvisioningState;
import com.microsoft.azure.management.batchai.Workspace;
import com.microsoft.azure.management.batchai.WorkspaceCreateParameters;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import org.joda.time.DateTime;
import rx.Observable;

@LangDefinition
class WorkspaceImpl extends GroupableResourceImpl<
        Workspace,
        WorkspaceInner,
        WorkspaceImpl,
        BatchAIManager>
        implements
            Workspace,
            Workspace.Definition,
            Workspace.Update {
    private BatchAIClusters clusters;
    private BatchAIFileServers fileServers;
    private Experiments experiments;

    private WorkspaceCreateParameters createParameters = new WorkspaceCreateParameters();

    WorkspaceImpl(String name, WorkspaceInner innerObject, BatchAIManager manager) {
        super(name, innerObject, manager);
    }

    @Override
    public DateTime creationTime() {
        return inner().creationTime();
    }

    @Override
    public ProvisioningState provisioningState() {
        return inner().provisioningState();
    }

    @Override
    public DateTime provisioningStateTransitionTime() {
        return inner().provisioningStateTransitionTime();
    }

    @Override
    public BatchAIClusters clusters() {
        if (clusters == null) {
            clusters = new BatchAIClustersImpl(this);
        }
        return clusters;
    }

    @Override
    public Experiments experiments() {
        if (experiments == null) {
            experiments = new ExperimentsImpl(this);
        }
        return experiments;
    }

    @Override
    public BatchAIFileServers fileServers() {
        if (fileServers == null) {
            fileServers = new BatchAIFileServersImpl(this);
        }
        return fileServers;
    }

    @Override
    protected Observable<WorkspaceInner> getInnerAsync() {
        return this.manager().inner().workspaces().getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public Observable<Workspace> createResourceAsync() {
        createParameters.withLocation(this.regionName());
        createParameters.withTags(this.inner().getTags());
        return this.manager().inner().workspaces().createAsync(resourceGroupName(), name(), createParameters)
                .map(innerToFluentMap(this));
    }

    @Override
    public Observable<Workspace> updateResourceAsync() {
        return this.manager().inner().workspaces().updateAsync(resourceGroupName(), name(), this.inner().getTags())
                .map(innerToFluentMap(this));
    }
}
