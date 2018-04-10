/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.AllocationState;
import com.microsoft.azure.management.batchai.AppInsightsReference;
import com.microsoft.azure.management.batchai.AutoScaleSettings;
import com.microsoft.azure.management.batchai.AzureBlobFileSystem;
import com.microsoft.azure.management.batchai.AzureBlobFileSystemReference;
import com.microsoft.azure.management.batchai.AzureFileShare;
import com.microsoft.azure.management.batchai.AzureFileShareReference;
import com.microsoft.azure.management.batchai.BatchAICluster;
import com.microsoft.azure.management.batchai.BatchAIError;
import com.microsoft.azure.management.batchai.FileServer;
import com.microsoft.azure.management.batchai.BatchAIJobs;
import com.microsoft.azure.management.batchai.DeallocationOption;
import com.microsoft.azure.management.batchai.FileServerReference;
import com.microsoft.azure.management.batchai.ImageReference;
import com.microsoft.azure.management.batchai.KeyVaultSecretReference;
import com.microsoft.azure.management.batchai.ManualScaleSettings;
import com.microsoft.azure.management.batchai.MountVolumes;
import com.microsoft.azure.management.batchai.NodeSetup;
import com.microsoft.azure.management.batchai.NodeSetupTask;
import com.microsoft.azure.management.batchai.NodeStateCounts;
import com.microsoft.azure.management.batchai.PerformanceCountersSettings;
import com.microsoft.azure.management.batchai.ProvisioningState;
import com.microsoft.azure.management.batchai.ResourceId;
import com.microsoft.azure.management.batchai.ScaleSettings;
import com.microsoft.azure.management.batchai.SetupTask;
import com.microsoft.azure.management.batchai.UnmanagedFileSystemReference;
import com.microsoft.azure.management.batchai.UserAccountSettings;
import com.microsoft.azure.management.batchai.VirtualMachineConfiguration;
import com.microsoft.azure.management.batchai.VmPriority;
import com.microsoft.azure.management.batchai.model.HasMountVolumes;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import org.joda.time.DateTime;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation for Cluster and its create and update interfaces.
 */
@LangDefinition
class BatchAIClusterImpl extends GroupableResourceImpl<
        BatchAICluster,
        ClusterInner,
        BatchAIClusterImpl,
        BatchAIManager>
        implements
        BatchAICluster,
        BatchAICluster.Definition,
        BatchAICluster.Update,
        HasMountVolumes {
    private ClusterCreateParametersInner createParameters = new ClusterCreateParametersInner();
    private ClusterUpdateParametersInner updateParameters = new ClusterUpdateParametersInner();

    private BatchAIJobsImpl jobs;

    BatchAIClusterImpl(String name, ClusterInner innerObject, BatchAIManager manager) {
        super(name, innerObject, manager);
    }

    @Override
    public Observable<BatchAICluster> createResourceAsync() {
        createParameters.withLocation(this.regionName());
        createParameters.withTags(this.inner().getTags());
        return this.manager().inner().clusters().createAsync(resourceGroupName(), name(), createParameters)
                .map(innerToFluentMap(this));
    }

    @Override
    public Observable<BatchAICluster> updateResourceAsync() {
        updateParameters.withTags(this.inner().getTags());
        return this.manager().inner().clusters().updateAsync(resourceGroupName(), name(), updateParameters)
                .map(innerToFluentMap(this));
    }

    @Override
    protected Observable<ClusterInner> getInnerAsync() {
        return this.manager().inner().clusters().getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public BatchAIClusterImpl withVMSize(String vmSize) {
        createParameters.withVmSize(vmSize);
        return this;
    }

    @Override
    public BatchAIClusterImpl withUserName(String userName) {
        ensureUserAccountSettings().withAdminUserName(userName);
        return this;
    }

    @Override
    public BatchAICluster.DefinitionStages.WithScaleSettings withPassword(String password) {
        ensureUserAccountSettings().withAdminUserPassword(password);
        return this;
    }

    @Override
    public BatchAICluster.DefinitionStages.WithScaleSettings withSshPublicKey(String sshPublicKey) {
        ensureUserAccountSettings().withAdminUserSshPublicKey(sshPublicKey);
        return this;
    }

    private UserAccountSettings ensureUserAccountSettings() {
        if (createParameters.userAccountSettings() == null) {
            createParameters.withUserAccountSettings(new UserAccountSettings());
        }
        return createParameters.userAccountSettings();
    }

    @Override
    public BatchAIClusterImpl withAutoScale(int minimumNodeCount, int maximumNodeCount) {
        AutoScaleSettings autoScaleSettings = new AutoScaleSettings().withMinimumNodeCount(minimumNodeCount).withMaximumNodeCount(maximumNodeCount);
        if (isInCreateMode()) {
            ensureScaleSettings().withAutoScale(autoScaleSettings);
        } else {
            updateParameters.withScaleSettings(new ScaleSettings().withAutoScale(autoScaleSettings));
        }
        return this;
    }

    @Override
    public BatchAIClusterImpl withAutoScale(int minimumNodeCount, int maximumNodeCount, int initialNodeCount) {
        AutoScaleSettings autoScaleSettings = new AutoScaleSettings()
                .withMinimumNodeCount(minimumNodeCount)
                .withMaximumNodeCount(maximumNodeCount)
                .withInitialNodeCount(initialNodeCount);
        if (isInCreateMode()) {
            ensureScaleSettings().withAutoScale(autoScaleSettings);
        } else {
            updateParameters.withScaleSettings(new ScaleSettings().withAutoScale(autoScaleSettings));
        }
        return this;
    }

    @Override
    public BatchAIClusterImpl withManualScale(int targetNodeCount) {
        ManualScaleSettings manualScaleSettings = new ManualScaleSettings().withTargetNodeCount(targetNodeCount);
        if (isInCreateMode()) {
            ensureScaleSettings().withManual(manualScaleSettings);
        } else {
            updateParameters.withScaleSettings(new ScaleSettings().withManual(manualScaleSettings));
        }
        return this;
    }

    @Override
    public BatchAIClusterImpl withManualScale(int targetNodeCount, DeallocationOption deallocationOption) {
        ManualScaleSettings manualScaleSettings = new ManualScaleSettings().withTargetNodeCount(targetNodeCount).withNodeDeallocationOption(deallocationOption);
        if (isInCreateMode()) {
            ensureScaleSettings().withManual(manualScaleSettings);
        } else {
            updateParameters.withScaleSettings(new ScaleSettings().withManual(manualScaleSettings));
        }
        return this;
    }

    private ScaleSettings ensureScaleSettings() {
        if (createParameters.scaleSettings() == null) {
            createParameters.withScaleSettings(new ScaleSettings());
        }
        return createParameters.scaleSettings();
    }

    @Override
    public BatchAICluster.DefinitionStages.WithCreate withLowPriority() {
        createParameters.withVmPriority(VmPriority.LOWPRIORITY);
        return this;
    }

    @Override
    public NodeSetupTask.DefinitionStages.Blank<BatchAICluster.DefinitionStages.WithCreate> defineSetupTask() {
        return new NodeSetupTaskImpl(new SetupTask(), this);
    }

    BatchAIClusterImpl withSetupTask(NodeSetupTaskImpl setupTask) {
        ensureNodeSetup().withSetupTask(setupTask.inner());
        return this;
    }

    private NodeSetup ensureNodeSetup() {
        if (createParameters.nodeSetup() == null) {
            createParameters.withNodeSetup(new NodeSetup());
        }
        return createParameters.nodeSetup();
    }

    @Override
    public String vmSize() {
        return inner().vmSize();
    }

    @Override
    public VmPriority vmPriority() {
        return inner().vmPriority();
    }

    @Override
    public ScaleSettings scaleSettings() {
        return inner().scaleSettings();
    }

    @Override
    public VirtualMachineConfiguration virtualMachineConfiguration() {
        return inner().virtualMachineConfiguration();
    }

    @Override
    public NodeSetup nodeSetup() {
        return inner().nodeSetup();
    }

    @Override
    public String adminUserName() {
        return inner().userAccountSettings().adminUserName();
    }

    @Override
    public ResourceId subnet() {
        return inner().subnet();
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
    public AllocationState allocationState() {
        return inner().allocationState();
    }

    @Override
    public DateTime allocationStateTransitionTime() {
        return inner().allocationStateTransitionTime();
    }

    @Override
    public List<BatchAIError> errors() {
        return inner().errors();
    }

    @Override
    public int currentNodeCount() {
        return Utils.toPrimitiveInt(inner().currentNodeCount());
    }

    @Override
    public NodeStateCounts nodeStateCounts() {
        return inner().nodeStateCounts();
    }

    @Override
    public BatchAIJobs jobs() {
        if (jobs == null) {
            jobs = new BatchAIJobsImpl(this);
        }
        return jobs;
    }

    @Override
    public AzureFileShare.DefinitionStages.Blank<BatchAICluster.DefinitionStages.WithCreate> defineAzureFileShare() {
        return new AzureFileShareImpl<BatchAICluster.DefinitionStages.WithCreate>(new AzureFileShareReference(), this);
    }


    @Override
    public AzureBlobFileSystem.DefinitionStages.Blank<BatchAICluster.DefinitionStages.WithCreate> defineAzureBlobFileSystem() {
        return new AzureBlobFileSystemImpl<BatchAICluster.DefinitionStages.WithCreate>(new AzureBlobFileSystemReference(), this);
    }

    @Override
    public FileServer.DefinitionStages.Blank<BatchAICluster.DefinitionStages.WithCreate> defineFileServer() {
        return new FileServerImpl<BatchAICluster.DefinitionStages.WithCreate>(new FileServerReference(), this);
    }

    @Override
    public BatchAIClusterImpl withUnmanagedFileSystem(String mountCommand, String relativeMountPath) {
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
        if (ensureNodeSetup().mountVolumes() == null) {
            createParameters.nodeSetup().withMountVolumes(new MountVolumes());
        }
        return createParameters.nodeSetup().mountVolumes();
    }

    @Override
    public BatchAIClusterImpl withSubnet(String subnetId) {
        createParameters.withSubnet(new ResourceId().withId(subnetId));
        return this;
    }

    @Override
    public BatchAIClusterImpl withSubnet(String networkId, String subnetName) {
        createParameters.withSubnet(new ResourceId().withId(networkId + "/subnets/" + subnetName));
        return this;
    }

    @Override
    public BatchAIClusterImpl withAppInsightsComponentId(String resoureId) {
        if (ensureNodeSetup().performanceCountersSettings() == null) {
            createParameters.nodeSetup().withPerformanceCountersSettings(new PerformanceCountersSettings());
        }
        createParameters.nodeSetup().performanceCountersSettings().withAppInsightsReference(new AppInsightsReference()
                .withComponent(new ResourceId().withId(resoureId)));
        return this;
    }

    @Override
    public BatchAIClusterImpl withInstrumentationKey(String instrumentationKey) {
        createParameters.nodeSetup().performanceCountersSettings().appInsightsReference().withInstrumentationKey(instrumentationKey);
        return this;
    }

    @Override
    public BatchAIClusterImpl withInstrumentationKeySecretReference(String keyVaultId, String secretUrl) {
        createParameters.nodeSetup().performanceCountersSettings().appInsightsReference()
                .withInstrumentationKeySecretReference(new KeyVaultSecretReference().withSourceVault(new ResourceId().withId(keyVaultId)).withSecretUrl(secretUrl));
        return this;
    }

    @Override
    public BatchAIClusterImpl withVirtualMachineImage(String publisher, String offer, String sku, String version) {
        withVirtualMachineImage(publisher, offer, sku).createParameters.virtualMachineConfiguration().imageReference().withVersion(version);
        return this;
    }

    @Override
    public BatchAIClusterImpl withVirtualMachineImage(String publisher, String offer, String sku) {
        ensureVirtualMachineConfiguration().withImageReference(
                new ImageReference()
                        .withPublisher(publisher)
                        .withOffer(offer)
                        .withSku(sku));
        return this;
    }

    @Override
    public BatchAIClusterImpl withVirtualMachineImageId(String virtualMachineImageId, String publisher, String offer, String sku) {
        withVirtualMachineImage(publisher, offer, sku).createParameters.virtualMachineConfiguration().imageReference().withVirtualMachineImageId(virtualMachineImageId);
        return this;
    }

    private VirtualMachineConfiguration ensureVirtualMachineConfiguration() {
        if (createParameters.virtualMachineConfiguration() == null) {
            createParameters.withVirtualMachineConfiguration(new VirtualMachineConfiguration());
        }
        return createParameters.virtualMachineConfiguration();
    }
}
