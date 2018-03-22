/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.AzureBlobFileSystem;
import com.microsoft.azure.management.batchai.AzureBlobFileSystemReference;
import com.microsoft.azure.management.batchai.AzureFileShare;
import com.microsoft.azure.management.batchai.AzureFileShareReference;
import com.microsoft.azure.management.batchai.BatchAICluster;
import com.microsoft.azure.management.batchai.BatchAIJob;
import com.microsoft.azure.management.batchai.CNTKsettings;
import com.microsoft.azure.management.batchai.Caffe2Settings;
import com.microsoft.azure.management.batchai.CaffeSettings;
import com.microsoft.azure.management.batchai.ChainerSettings;
import com.microsoft.azure.management.batchai.ContainerImageSettings;
import com.microsoft.azure.management.batchai.ContainerSettings;
import com.microsoft.azure.management.batchai.CustomToolkitSettings;
import com.microsoft.azure.management.batchai.EnvironmentVariable;
import com.microsoft.azure.management.batchai.EnvironmentVariableWithSecretValue;
import com.microsoft.azure.management.batchai.ExecutionState;
import com.microsoft.azure.management.batchai.FileServer;
import com.microsoft.azure.management.batchai.FileServerReference;
import com.microsoft.azure.management.batchai.ImageSourceRegistry;
import com.microsoft.azure.management.batchai.InputDirectory;
import com.microsoft.azure.management.batchai.JobPreparation;
import com.microsoft.azure.management.batchai.JobPropertiesConstraints;
import com.microsoft.azure.management.batchai.JobPropertiesExecutionInfo;
import com.microsoft.azure.management.batchai.KeyVaultSecretReference;
import com.microsoft.azure.management.batchai.MountVolumes;
import com.microsoft.azure.management.batchai.OutputDirectory;
import com.microsoft.azure.management.batchai.OutputDirectorySettings;
import com.microsoft.azure.management.batchai.OutputFile;
import com.microsoft.azure.management.batchai.ProvisioningState;
import com.microsoft.azure.management.batchai.PyTorchSettings;
import com.microsoft.azure.management.batchai.ResourceId;
import com.microsoft.azure.management.batchai.TensorFlowSettings;
import com.microsoft.azure.management.batchai.ToolType;
import com.microsoft.azure.management.batchai.ToolTypeSettings;
import com.microsoft.azure.management.batchai.UnmanagedFileSystemReference;
import com.microsoft.azure.management.batchai.model.HasMountVolumes;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import org.joda.time.DateTime;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;

import static com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl.convertPageToInnerAsync;

/**
 * Implementation for BatchAIJob and its create interface.
 */
@LangDefinition
class BatchAIJobImpl
        extends GroupableResourceImpl<BatchAIJob, JobInner, BatchAIJobImpl, BatchAIManager>
        implements BatchAIJob,
        BatchAIJob.Definition,
        HasMountVolumes {
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
    public OutputDirectorySettings.DefinitionStages.Blank<BatchAIJob.DefinitionStages.WithCreate> defineOutputDirectory(String id) {
        return new OutputDirectorySettingsImpl(new OutputDirectory().withId(id), this);
    }

    @Override
    public BatchAIJobImpl withContainerImage(String image) {
        if (ensureContainerSettings().imageSourceRegistry() == null) {
            createParameters.containerSettings().withImageSourceRegistry(new ImageSourceRegistry());
        }
        createParameters.containerSettings().imageSourceRegistry().withImage(image);
        return this;
    }

    @Override
    public ContainerImageSettings.DefinitionStages.Blank<BatchAIJob.DefinitionStages.WithCreate> defineContainerSettings(String image) {
        return new ContainerImageSettingsImpl(new ImageSourceRegistry().withImage(image), this);
    }

    private ContainerSettings ensureContainerSettings() {
        if (createParameters.containerSettings() == null) {
            createParameters.withContainerSettings(new ContainerSettings());
        }
        return createParameters.containerSettings();
    }

    @Override
    public BatchAIJob.DefinitionStages.WithCreate withExperimentName(String experimentName) {
        inner().withExperimentName(experimentName);
        return this;
    }

    @Override
    public ToolTypeSettings.CognitiveToolkit.DefinitionStages.Blank<BatchAIJob.DefinitionStages.WithCreate> defineCognitiveToolkit() {
        return new CognitiveToolkitImpl(new CNTKsettings(), this);
    }

    @Override
    public ToolTypeSettings.TensorFlow.DefinitionStages.Blank<BatchAIJob.DefinitionStages.WithCreate> defineTensorflow() {
        return new TensorFlowImpl(new TensorFlowSettings(), this);
    }

    @Override
    public ToolTypeSettings.Caffe.DefinitionStages.Blank<BatchAIJob.DefinitionStages.WithCreate> defineCaffe() {
        return new CaffeImpl(new CaffeSettings(), this);
    }

    @Override
    public ToolTypeSettings.Caffe2.DefinitionStages.Blank<BatchAIJob.DefinitionStages.WithCreate> defineCaffe2() {
        return new Caffe2Impl(new Caffe2Settings(), this);
    }

    @Override
    public ToolTypeSettings.Chainer.DefinitionStages.Blank<BatchAIJob.DefinitionStages.WithCreate> defineChainer() {
        return new ChainerImpl(new ChainerSettings(), this);
    }

    @Override
    public ToolTypeSettings.PyTorch.DefinitionStages.Blank<BatchAIJob.DefinitionStages.WithCreate> definePyTorch() {
        return new PyTorchImpl(new PyTorchSettings(), this);
    }

    @Override
    public BatchAIJobImpl withCustomCommandLine(String commandLine) {
        inner().withCustomToolkitSettings(new CustomToolkitSettings().withCommandLine(commandLine));
        return this;
    }

    @Override
    public BatchAIJobImpl withEnvironmentVariable(String name, String value) {
        ensureEnvironmentVariables().add(new EnvironmentVariable().withName(name).withValue(value));
        return this;
    }

    @Override
    public BatchAIJobImpl withEnvironmentVariableSecretValue(String name, String value) {
        ensureEnvironmentVariablesWithSecrets().add(new EnvironmentVariableWithSecretValue().withName(name).withValue(value));
        return this;
    }

    @Override
    public BatchAIJobImpl withEnvironmentVariableSecretValue(String name, String keyVaultId, String secretUrl) {
        KeyVaultSecretReference secretReference = new KeyVaultSecretReference()
                .withSourceVault(new ResourceId().withId(keyVaultId)).withSecretUrl(secretUrl);
        ensureEnvironmentVariablesWithSecrets().add(new EnvironmentVariableWithSecretValue().withName(name).withValueSecretReference(secretReference));
        return this;
    }

    @Override
    public AzureFileShareImpl defineAzureFileShare() {
        return new AzureFileShareImpl<BatchAIJob.DefinitionStages.WithCreate>(new AzureFileShareReference(), this);
    }


    @Override
    public AzureBlobFileSystemImpl defineAzureBlobFileSystem() {
        return new AzureBlobFileSystemImpl(new AzureBlobFileSystemReference(), this);
    }

    @Override
    public FileServerImpl defineFileServer() {
        return new FileServerImpl<BatchAIJob.DefinitionStages.WithCreate>(new FileServerReference(), this);
    }

    @Override
    public BatchAIJobImpl withUnmanagedFileSystem(String mountCommand, String relativeMountPath) {
        MountVolumes mountVolumes = ensureMountVolumes();
        if (mountVolumes.unmanagedFileSystems() == null) {
            mountVolumes.withUnmanagedFileSystems(new ArrayList<UnmanagedFileSystemReference>());
        }
        mountVolumes.unmanagedFileSystems().add(new UnmanagedFileSystemReference().withMountCommand(mountCommand).withRelativeMountPath(relativeMountPath));
        return this;
    }

    @Override
    public void attachAzureFileShare(AzureFileShare azureFileShare) {
        MountVolumes mountVolumes = ensureMountVolumes();
        if (mountVolumes.azureFileShares() == null) {
            mountVolumes.withAzureFileShares(new ArrayList<AzureFileShareReference>());
        }
        mountVolumes.azureFileShares().add(azureFileShare.inner());
    }

    @Override
    public void attachAzureBlobFileSystem(AzureBlobFileSystem azureBlobFileSystem) {
        MountVolumes mountVolumes = ensureMountVolumes();
        if (mountVolumes.azureBlobFileSystems() == null) {
            mountVolumes.withAzureBlobFileSystems(new ArrayList<AzureBlobFileSystemReference>());
        }
        mountVolumes.azureBlobFileSystems().add(azureBlobFileSystem.inner());
    }

    @Override
    public void attachFileServer(FileServer fileServer) {
        MountVolumes mountVolumes = ensureMountVolumes();
        if (mountVolumes.fileServers() == null) {
            mountVolumes.withFileServers(new ArrayList<FileServerReference>());
        }
        mountVolumes.fileServers().add(fileServer.inner());
    }

    private MountVolumes ensureMountVolumes() {
        if (createParameters.mountVolumes() == null) {
            createParameters.withMountVolumes(new MountVolumes());
        }
        return createParameters.mountVolumes();
    }

    private List<EnvironmentVariableWithSecretValue> ensureEnvironmentVariablesWithSecrets() {
        if (inner().secrets() == null) {
            inner().withSecrets(new ArrayList<EnvironmentVariableWithSecretValue>());
        }
        return inner().secrets();
    }


    private List<EnvironmentVariable> ensureEnvironmentVariables() {
        if (createParameters.environmentVariables() == null) {
            createParameters.withEnvironmentVariables(new ArrayList<EnvironmentVariable>());
        }
        return createParameters.environmentVariables();
    }

    void attachCntkSettings(CognitiveToolkitImpl cognitiveToolkit) {
        createParameters.withCntkSettings(cognitiveToolkit.inner());
    }

    void attachTensorFlowSettings(TensorFlowImpl tensorFlow) {
        createParameters.withTensorFlowSettings(tensorFlow.inner());
    }

    void attachCaffeSettings(CaffeImpl caffe) {
        createParameters.withCaffeSettings(caffe.inner());
    }

    void attachCaffe2Settings(Caffe2Impl caffe2) {
        createParameters.withCaffe2Settings(caffe2.inner());
    }

    void attachChainerSettings(ChainerImpl chainer) {
        createParameters.withChainerSettings(chainer.inner());
    }

    void attachPyTorchSettings(PyTorchImpl pyTorch) {
        createParameters.withPyTorchSettings(pyTorch.inner());
    }

    void attachOutputDirectory(OutputDirectorySettingsImpl outputDirectorySettings) {
        if (createParameters.outputDirectories() == null) {
            createParameters.withOutputDirectories(new ArrayList<OutputDirectory>());
        }
        createParameters.outputDirectories().add(outputDirectorySettings.inner());
    }

    void attachImageSourceRegistry(ContainerImageSettingsImpl containerImageSettings) {
        ensureContainerSettings().withImageSourceRegistry(containerImageSettings.inner());
    }

    @Override
    public void terminate() {
        terminateAsync().await();
    }

    @Override
    public Completable terminateAsync() {
        Observable<Void> stopObservable = this.manager().inner().jobs().terminateAsync(this.resourceGroupName(), this.name());
        Observable<BatchAIJob> refreshObservable = refreshAsync();
        // Refresh after stop to ensure the job operational state is updated
        return Observable.concat(stopObservable, refreshObservable).toCompletable();
    }

    @Override
    public PagedList<OutputFile> listFiles(String outputDirectoryId) {
        PagedListConverter<FileInner, OutputFile> converter = new PagedListConverter<FileInner, OutputFile>() {
            @Override
            public Observable<OutputFile> typeConvertAsync(FileInner fileInner) {
                return Observable.just((OutputFile) new OutputFileImpl(fileInner));
            }
        };
        return converter.convert(this.manager().inner().jobs().listOutputFiles(resourceGroupName(), name(), new JobsListOutputFilesOptionsInner().withOutputdirectoryid(outputDirectoryId)));
    }

    @Override
    public Observable<OutputFile> listFilesAsync(String outputDirectoryId) {
        return convertPageToInnerAsync(this.manager().inner().jobs().listOutputFilesAsync(resourceGroupName(), name(),
                new JobsListOutputFilesOptionsInner().withOutputdirectoryid(outputDirectoryId))).map(new Func1<FileInner, OutputFile>() {
            @Override
            public OutputFile call(FileInner fileInner) {
                return new OutputFileImpl(fileInner);
            }
        });
    }

    @Override
    public PagedList<OutputFile> listFiles(String outputDirectoryId, String directory, Integer linkExpiryMinutes, Integer maxResults) {
        PagedListConverter<FileInner, OutputFile> converter = new PagedListConverter<FileInner, OutputFile>() {
            @Override
            public Observable<OutputFile> typeConvertAsync(FileInner fileInner) {
                return Observable.just((OutputFile) new OutputFileImpl(fileInner));
            }
        };
        return converter.convert(this.manager().inner().jobs().listOutputFiles(resourceGroupName(), name(),
                new JobsListOutputFilesOptionsInner().withOutputdirectoryid(outputDirectoryId)
                        .withDirectory(directory)
                        .withLinkexpiryinminutes(linkExpiryMinutes)
                        .withMaxResults(maxResults)));

    }

    @Override
    public Observable<OutputFile> listFilesAsync(String outputDirectoryId, String directory, Integer linkExpiryMinutes, Integer maxResults) {
        return convertPageToInnerAsync(this.manager().inner().jobs().listOutputFilesAsync(resourceGroupName(), name(),
                new JobsListOutputFilesOptionsInner().withOutputdirectoryid(outputDirectoryId)
                        .withDirectory(directory)
                        .withLinkexpiryinminutes(linkExpiryMinutes)
                        .withMaxResults(maxResults)))
                .map(new Func1<FileInner, OutputFile>() {
                    @Override
                    public OutputFile call(FileInner fileInner) {
                        return new OutputFileImpl(fileInner);
                    }
                });
    }

    @Override
    public String experimentName() {
        return inner().experimentName();
    }

    @Override
    public Integer priority() {
        return inner().priority();
    }

    @Override
    public ResourceId cluster() {
        return inner().cluster();
    }

    @Override
    public MountVolumes mountVolumes() {
        return inner().mountVolumes();
    }

    @Override
    public String jobOutputDirectoryPathSegment() {
        return inner().jobOutputDirectoryPathSegment();
    }

    @Override
    public int nodeCount() {
        return Utils.toPrimitiveInt(inner().nodeCount());
    }

    @Override
    public ContainerSettings containerSettings() {
        return inner().containerSettings();
    }

    @Override
    public ToolType toolType() {
        return inner().toolType();
    }

    @Override
    public CNTKsettings cntkSettings() {
        return inner().cntkSettings();
    }

    @Override
    public PyTorchSettings pyTorchSettings() {
        return inner().pyTorchSettings();
    }

    @Override
    public TensorFlowSettings tensorFlowSettings() {
        return inner().tensorFlowSettings();
    }

    @Override
    public CaffeSettings caffeSettings() {
        return inner().caffeSettings();
    }

    @Override
    public ChainerSettings chainerSettings() {
        return inner().chainerSettings();
    }

    @Override
    public CustomToolkitSettings customToolkitSettings() {
        return inner().customToolkitSettings();
    }

    @Override
    public JobPreparation jobPreparation() {
        return inner().jobPreparation();
    }

    @Override
    public String stdOutErrPathPrefix() {
        return inner().stdOutErrPathPrefix();
    }

    @Override
    public List<InputDirectory> inputDirectories() {
        return inner().inputDirectories();
    }

    @Override
    public List<OutputDirectory> outputDirectories() {
        return inner().outputDirectories();
    }

    @Override
    public List<EnvironmentVariable> environmentVariables() {
        return inner().environmentVariables();
    }

    @Override
    public List<EnvironmentVariableWithSecretValue> secrets() {
        return inner().secrets();
    }

    @Override
    public JobPropertiesConstraints constraints() {
        return inner().constraints();
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
    public ExecutionState executionState() {
        return inner().executionState();
    }

    @Override
    public DateTime executionStateTransitionTime() {
        return inner().executionStateTransitionTime();
    }

    @Override
    public JobPropertiesExecutionInfo executionInfo() {
        return inner().executionInfo();
    }
}
