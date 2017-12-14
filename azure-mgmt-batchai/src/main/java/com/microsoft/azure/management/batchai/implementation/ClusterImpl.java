/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.AllocationState;
import com.microsoft.azure.management.batchai.AutoScaleSettings;
import com.microsoft.azure.management.batchai.BatchAIError;
import com.microsoft.azure.management.batchai.Cluster;
import com.microsoft.azure.management.batchai.DeallocationOption;
import com.microsoft.azure.management.batchai.Jobs;
import com.microsoft.azure.management.batchai.ManualScaleSettings;
import com.microsoft.azure.management.batchai.NodeSetup;
import com.microsoft.azure.management.batchai.NodeSetupTask;
import com.microsoft.azure.management.batchai.NodeStateCounts;
import com.microsoft.azure.management.batchai.ProvisioningState;
import com.microsoft.azure.management.batchai.ResourceId;
import com.microsoft.azure.management.batchai.ScaleSettings;
import com.microsoft.azure.management.batchai.SetupTask;
import com.microsoft.azure.management.batchai.UserAccountSettings;
import com.microsoft.azure.management.batchai.VirtualMachineConfiguration;
import com.microsoft.azure.management.batchai.VmPriority;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import org.joda.time.DateTime;
import rx.Observable;

import java.util.List;

/**
 * Implementation for Cluster and its create and update interfaces.
 */
@LangDefinition
class ClusterImpl extends GroupableResourceImpl<
        Cluster,
        ClusterInner,
        ClusterImpl,
        BatchAIManager>
        implements
        Cluster,
        Cluster.Definition,
        Cluster.Update {
    private ClusterCreateParametersInner createParameters = new ClusterCreateParametersInner();
    private ClusterUpdateParametersInner updateParameters = new ClusterUpdateParametersInner();

    private JobsImpl jobs;

    ClusterImpl(String name, ClusterInner innerObject, BatchAIManager manager) {
        super(name, innerObject, manager);
    }

    @Override
    public Observable<Cluster> createResourceAsync() {
        createParameters.withLocation(this.regionName());
        createParameters.withTags(this.inner().getTags());
        return this.manager().inner().clusters().createAsync(resourceGroupName(), name(), createParameters)
                .map(innerToFluentMap(this));
    }

    @Override
    public Observable<Cluster> updateResourceAsync() {
        updateParameters.withTags(this.inner().getTags());
        return this.manager().inner().clusters().updateAsync(resourceGroupName(), name(), updateParameters)
                .map(innerToFluentMap(this));
    }

    @Override
    protected Observable<ClusterInner> getInnerAsync() {
        return this.manager().inner().clusters().getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public ClusterImpl withVMSize(String vmSize) {
        createParameters.withVmSize(vmSize);
        return this;
    }

    @Override
    public ClusterImpl withUserName(String userName) {
        ensureUserAccountSettings().withAdminUserName(userName);
        return this;
    }

    @Override
    public Cluster.DefinitionStages.WithScaleSettings withPassword(String password) {
        ensureUserAccountSettings().withAdminUserPassword(password);
        return this;
    }

    @Override
    public Cluster.DefinitionStages.WithScaleSettings withSshPublicKey(String sshPublicKey) {
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
    public ClusterImpl withAutoScale(int minimumNodeCount, int maximumNodeCount) {
        AutoScaleSettings autoScaleSettings = new AutoScaleSettings().withMinimumNodeCount(minimumNodeCount).withMaximumNodeCount(maximumNodeCount);
        if (isInCreateMode()) {
            ensureScaleSettings().withAutoScale(autoScaleSettings);
        } else {
            updateParameters.withScaleSettings(new ScaleSettings().withAutoScale(autoScaleSettings));
        }
        return this;
    }

    @Override
    public ClusterImpl withAutoScale(int minimumNodeCount, int maximumNodeCount, int initialNodeCount) {
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
    public ClusterImpl withManualScale(int targetNodeCount) {
        ManualScaleSettings manualScaleSettings = new ManualScaleSettings().withTargetNodeCount(targetNodeCount);
        if (isInCreateMode()) {
            ensureScaleSettings().withManual(manualScaleSettings);
        } else {
            updateParameters.withScaleSettings(new ScaleSettings().withManual(manualScaleSettings));
        }
        return this;
    }

    @Override
    public ClusterImpl withManualScale(int targetNodeCount, DeallocationOption deallocationOption) {
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
    public Cluster.DefinitionStages.WithCreate withLowPriority() {
        createParameters.withVmPriority(VmPriority.LOWPRIORITY);
        return this;
    }

    @Override
    public NodeSetupTask.DefinitionStages.Blank<Cluster.DefinitionStages.WithCreate> defineSetupTask() {
        return new NodeSetupTaskImpl(new SetupTask(), this);
    }

    ClusterImpl withSetupTask(NodeSetupTaskImpl setupTask) {
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
    public Jobs jobs() {
        if (jobs == null) {
            jobs = new JobsImpl(this);
        }
        return jobs;
    }
}
