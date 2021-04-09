/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerservice.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerservice.AgentPoolMode;
import com.microsoft.azure.management.containerservice.AgentPoolType;
import com.microsoft.azure.management.containerservice.ContainerServiceVMSizeTypes;
import com.microsoft.azure.management.containerservice.KubernetesClusterAgentPool;
import com.microsoft.azure.management.containerservice.ManagedClusterAgentPoolProfile;
import com.microsoft.azure.management.containerservice.OSType;
import com.microsoft.azure.management.containerservice.OrchestratorServiceBase;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ChildResourceImpl;

/**
 * The implementation for KubernetesClusterAgentPool and its create and update interfaces.
 */
@LangDefinition
public class KubernetesClusterAgentPoolImpl
    extends
        ChildResourceImpl<ManagedClusterAgentPoolProfile,
            KubernetesClusterImpl,
            OrchestratorServiceBase>
    implements
        KubernetesClusterAgentPool,
        KubernetesClusterAgentPool.Definition<KubernetesClusterImpl>,
        KubernetesClusterAgentPool.Update<KubernetesClusterImpl> {

    private String subnetName;

    KubernetesClusterAgentPoolImpl(ManagedClusterAgentPoolProfile inner, KubernetesClusterImpl parent) {
        super(inner, parent);
        String subnetId = (inner != null) ? this.inner().vnetSubnetID() : null;
        this.subnetName = ResourceUtils.nameFromResourceId(subnetId);
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public int count() {
        return this.inner().count();
    }

    @Override
    public ContainerServiceVMSizeTypes vmSize() {
        return this.inner().vmSize();
    }

    @Override
    public int osDiskSizeInGB() {
        return this.inner().osDiskSizeGB();
    }

    @Override
    public OSType osType() {
        return this.inner().osType();
    }

    @Override
    public AgentPoolType type() {
        return this.inner().type();
    }

    @Override
    public String subnetName() {
        if (this.subnetName != null) {
            return this.subnetName;
        } else {
            return ResourceUtils.nameFromResourceId(this.inner().vnetSubnetID());
        }
    }

    @Override
    public String networkId() {
        String subnetId = (this.inner() != null) ? this.inner().vnetSubnetID() : null;
        return (subnetId != null) ? ResourceUtils.parentResourceIdFromResourceId(subnetId) : null;
    }

    @Override
    public AgentPoolMode mode() {
        return this.inner().mode();
    }

    @Override
    public KubernetesClusterAgentPoolImpl withVirtualMachineSize(ContainerServiceVMSizeTypes param0) {
        this.inner().withVmSize(param0);
        if (this.inner().count() == null) {
            // default VM count
            this.inner().withCount(1);
        }
        return this;
    }

    @Override
    public KubernetesClusterAgentPoolImpl withOSType(OSType osType) {
        this.inner().withOsType(osType);
        return this;
    }

    @Override
    public KubernetesClusterAgentPoolImpl withOSDiskSizeInGB(int osDiskSizeInGB) {
        this.inner().withOsDiskSizeGB(osDiskSizeInGB);
        return this;
    }

    @Override
    public KubernetesClusterAgentPoolImpl withAgentPoolType(AgentPoolType agentPoolType) {
        this.inner().withType(agentPoolType);
        return this;
    }

    @Override
    public KubernetesClusterAgentPoolImpl withAgentPoolTypeName(String agentPoolTypeName) {
        this.inner().withType(AgentPoolType.fromString(agentPoolTypeName));
        return this;
    }

    @Override
    public KubernetesClusterAgentPoolImpl withAgentPoolVirtualMachineCount(int count) {
        this.inner().withCount(count);
        return this;
    }

    @Override
    public KubernetesClusterAgentPoolImpl withMaxPodsCount(int podsCount) {
        this.inner().withMaxPods(podsCount);
        return this;
    }

    @Override
    public KubernetesClusterAgentPoolImpl withVirtualNetwork(String virtualNetworkId, String subnetName) {
        String vnetSubnetId = virtualNetworkId + "/subnets/" + subnetName;
        this.subnetName = subnetName;
        this.inner().withVnetSubnetID(vnetSubnetId);
        return this;
    }

    @Override
    public KubernetesClusterAgentPoolImpl withMode(AgentPoolMode mode) {
        this.inner().withMode(mode);
        return this;
    }

    @Override
    public KubernetesClusterImpl attach() {
        return this.parent().addNewAgentPool(this);
    }

    @Override
    public KubernetesClusterAgentPoolImpl withAgentPoolMode(AgentPoolMode agentPoolMode) {
        inner().withMode(agentPoolMode);
        return this;
    }

    AgentPoolInner getAgentPoolInner() {
        AgentPoolInner agentPoolInner = new AgentPoolInner();
        agentPoolInner.withCount(inner().count());
        agentPoolInner.withVmSize(inner().vmSize());
        agentPoolInner.withOsDiskSizeGB(inner().osDiskSizeGB());
        agentPoolInner.withVnetSubnetID(inner().vnetSubnetID());
        agentPoolInner.withMaxPods(inner().maxPods());
        agentPoolInner.withOsType(inner().osType());
        agentPoolInner.withMaxCount(inner().maxCount());
        agentPoolInner.withMinCount(inner().minCount());
        agentPoolInner.withEnableAutoScaling(inner().enableAutoScaling());
        agentPoolInner.withAgentPoolType(inner().type());
        agentPoolInner.withMode(inner().mode());
        agentPoolInner.withOrchestratorVersion(inner().orchestratorVersion());
//        agentPoolInner.withNodeImageVersion(inner().nodeImageVersion());
        agentPoolInner.withUpgradeSettings(inner().upgradeSettings());
        agentPoolInner.withAvailabilityZones(inner().availabilityZones());
        agentPoolInner.withEnableNodePublicIP(inner().enableNodePublicIP());
        agentPoolInner.withScaleSetPriority(inner().scaleSetPriority());
        agentPoolInner.withScaleSetEvictionPolicy(inner().scaleSetEvictionPolicy());
        agentPoolInner.withSpotMaxPrice(inner().spotMaxPrice());
        agentPoolInner.withTags(inner().tags());
        agentPoolInner.withNodeLabels(inner().nodeLabels());
        agentPoolInner.withNodeTaints(inner().nodeTaints());
        agentPoolInner.withProximityPlacementGroupID(inner().proximityPlacementGroupID());
        return agentPoolInner;
    }
}
