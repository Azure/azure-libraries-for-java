/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIExperiment;
import com.microsoft.azure.management.batchai.BatchAIJobs;
import com.microsoft.azure.management.batchai.BatchAIWorkspace;
import com.microsoft.azure.management.batchai.ProvisioningState;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import org.joda.time.DateTime;
import rx.Observable;

@LangDefinition
class BatchAIExperimentImpl extends CreatableUpdatableImpl<
        BatchAIExperiment,
        ExperimentInner,
        BatchAIExperimentImpl>
        implements
        BatchAIExperiment,
        BatchAIExperiment.Definition {
    private final BatchAIWorkspaceImpl workspace;

    private final BatchAIJobs jobs;

    BatchAIExperimentImpl(String name, BatchAIWorkspaceImpl workspace, ExperimentInner innerObject) {
        super(name, innerObject);
        this.workspace = workspace;
        jobs = new BatchAIJobsImpl(this);
    }

    @Override
    public String id() {
        return inner().id();
    }

    @Override
    public BatchAIJobs jobs() {
        return jobs;
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
    public BatchAIWorkspace workspace() {
        return workspace;
    }

    @Override
    public BatchAIManager manager() {
        return workspace.manager();
    }

    @Override
    public boolean isInCreateMode() {
        return inner().id() == null;
    }

    @Override
    public Observable<BatchAIExperiment> createResourceAsync() {
        return this.manager().inner().experiments().createAsync(workspace.resourceGroupName(), workspace.name(), name())
                .map(innerToFluentMap(this));
    }

    @Override
    protected Observable<ExperimentInner> getInnerAsync() {
        return this.manager().inner().experiments().getAsync(workspace.resourceGroupName(), workspace.name(), this.name());
    }
}