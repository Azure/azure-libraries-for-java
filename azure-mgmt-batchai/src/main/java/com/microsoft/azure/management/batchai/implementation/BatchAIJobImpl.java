/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAICluster;
import com.microsoft.azure.management.batchai.BatchAIJob;
import com.microsoft.azure.management.batchai.ResourceId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import rx.Observable;

/**
 * Implementation for BatchAIJob and its create interface.
 */
@LangDefinition
public class BatchAIJobImpl
        extends GroupableResourceImpl<BatchAIJob, JobInner, BatchAIJobImpl, BatchAIManager>
        implements BatchAIJob,
        BatchAIJob.Definition {
    private final BatchAICluster parent;
    private JobCreateParametersInner createParameters = new JobCreateParametersInner();

    BatchAIJobImpl(String name,
                   BatchAIClusterImpl parent,
                   JobInner inner) {
        super(name, inner, parent.manager());
        this.parent = parent;
    }

    @Override
    public BatchAICluster parent() {
        return parent;
    }

    @Override
    protected Observable<JobInner> getInnerAsync() {
        return myManager.inner().jobs().getByResourceGroupAsync(resourceGroupName(), name());
    }

    @Override
    public Observable<BatchAIJob> createResourceAsync() {
        ResourceId resourceId = new ResourceId().withId(parent.id());
        createParameters.withCluster(resourceId).withLocation(inner().location());
        return myManager.inner().jobs().createAsync(
                this.resourceGroupName(), this.name(), createParameters)
                .map(innerToFluentMap(this));
    }

    @Override
    public BatchAIJobImpl withStdOutErrPathPrefix(String stdOutErrPathPrefix) {
        createParameters.withStdOutErrPathPrefix(stdOutErrPathPrefix);
        return this;
    }

    @Override
    public BatchAIJobImpl withNodeCount(int nodeCount) {
        createParameters.withNodeCount(nodeCount);
        return this;
    }

    @Override
    public BatchAIJob.DefinitionStages.WithCreate withCognitiveToolikit() {
        return this;
    }
}
