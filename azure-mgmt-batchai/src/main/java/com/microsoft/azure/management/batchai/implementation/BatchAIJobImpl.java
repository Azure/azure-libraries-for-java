/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAICluster;
import com.microsoft.azure.management.batchai.BatchAIJob;
import com.microsoft.azure.management.batchai.CNTKsettings;
import com.microsoft.azure.management.batchai.CognitiveToolkit;
import com.microsoft.azure.management.batchai.ContainerSettings;
import com.microsoft.azure.management.batchai.ImageSourceRegistry;
import com.microsoft.azure.management.batchai.InputDirectory;
import com.microsoft.azure.management.batchai.JobPreparation;
import com.microsoft.azure.management.batchai.OutputDirectory;
import com.microsoft.azure.management.batchai.ResourceId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

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
    public BatchAIJobImpl withCommandLine(String commandLine) {
        createParameters.withJobPreparation(new JobPreparation().withCommandLine(commandLine));
        return this;
    }

    @Override
    public BatchAIJobImpl withInputDirectory(String id, String path) {
        ensureInputDirectories().add(new InputDirectory().withId(id).withPath(path));
        return this;
    }

    private List<InputDirectory> ensureInputDirectories() {
        if (createParameters.inputDirectories() == null) {
            createParameters.withInputDirectories(new ArrayList<InputDirectory>());
        }
        return createParameters.inputDirectories();
    }

    @Override
    public BatchAIJobImpl withOutputDirectory(String id, String pathPrefix) {
        if (createParameters.outputDirectories() == null) {
            createParameters.withOutputDirectories(new ArrayList<OutputDirectory>());
        }
        createParameters.outputDirectories().add(new OutputDirectory().withId(id).withPathPrefix(pathPrefix));
        return this;
    }

    @Override
    public BatchAIJob.DefinitionStages.WithCreate withContainerImage(String image) {
        if (ensureContainerSettings().imageSourceRegistry() == null) {
            createParameters.containerSettings().withImageSourceRegistry(new ImageSourceRegistry());
        }
        createParameters.containerSettings().imageSourceRegistry().withImage(image);
        return this;
    }

    private ContainerSettings ensureContainerSettings() {
        if (createParameters.containerSettings() == null) {
            createParameters.withContainerSettings(new ContainerSettings());
        }
        return createParameters.containerSettings();
    }

    @Override
    public CognitiveToolkit.DefinitionStages.Blank<BatchAIJob.DefinitionStages.WithCreate> defineCognitiveToolkit() {
        return new CognitiveToolkitImpl(new CNTKsettings(), this);
    }

    void attachAzureBlobFileSystem(CognitiveToolkitImpl cognitiveToolkit) {
        createParameters.withCntkSettings(cognitiveToolkit.inner());
    }
}
