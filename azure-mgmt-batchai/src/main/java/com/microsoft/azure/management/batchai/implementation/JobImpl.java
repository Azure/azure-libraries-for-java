/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.Cluster;
import com.microsoft.azure.management.batchai.Job;
import com.microsoft.azure.management.batchai.ResourceId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import rx.Observable;

/**
 * Implementation for Job and its create interface.
 */
@LangDefinition
public class JobImpl
        extends GroupableResourceImpl<Job, JobInner, JobImpl, BatchAIManager>
        implements Job,
        Job.Definition {
    private final Cluster parent;
    private JobCreateParametersInner createParameters = new JobCreateParametersInner();

    JobImpl(String name,
            ClusterImpl parent,
            JobInner inner) {
        super(name, inner, parent.manager());
        this.parent = parent;
    }

    @Override
    public Cluster parent() {
        return parent;
    }

    @Override
    protected Observable<JobInner> getInnerAsync() {
        return myManager.inner().jobs().getByResourceGroupAsync(resourceGroupName(), name());
    }

    @Override
    public Observable<Job> createResourceAsync() {
        ResourceId resourceId = new ResourceId().withId(parent.id());
        createParameters.withCluster(resourceId).withLocation(inner().location());
        return myManager.inner().jobs().createAsync(
                this.resourceGroupName(), this.name(), createParameters)
                .map(innerToFluentMap(this));
    }

    @Override
    public JobImpl withStdOutErrPathPrefix(String stdOutErrPathPrefix) {
        createParameters.withStdOutErrPathPrefix(stdOutErrPathPrefix);
        return this;
    }

    @Override
    public JobImpl withNodeCount(int nodeCount) {
        createParameters.withNodeCount(nodeCount);
        return this;
    }

    @Override
    public Job.DefinitionStages.WithCreate withCognitiveToolikit() {
        return this;
    }
}
