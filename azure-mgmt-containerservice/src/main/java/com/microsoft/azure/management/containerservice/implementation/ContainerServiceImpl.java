/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerservice.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerservice.ContainerService;
import com.microsoft.azure.management.containerservice.ContainerServiceAgentPool;
import com.microsoft.azure.management.containerservice.ContainerServiceAgentPoolProfile;
import com.microsoft.azure.management.containerservice.ContainerServiceDiagnosticsProfile;
import com.microsoft.azure.management.containerservice.ContainerServiceLinuxProfile;
import com.microsoft.azure.management.containerservice.ContainerServiceMasterProfile;
import com.microsoft.azure.management.containerservice.ContainerServiceMasterProfileCount;
import com.microsoft.azure.management.containerservice.ContainerServiceOrchestratorProfile;
import com.microsoft.azure.management.containerservice.ContainerServiceOrchestratorTypes;
import com.microsoft.azure.management.containerservice.ContainerServiceServicePrincipalProfile;
import com.microsoft.azure.management.containerservice.ContainerServiceSshConfiguration;
import com.microsoft.azure.management.containerservice.ContainerServiceSshPublicKey;
import com.microsoft.azure.management.containerservice.ContainerServiceStorageProfileTypes;
import com.microsoft.azure.management.containerservice.ContainerServiceVMDiagnostics;
import com.microsoft.azure.management.containerservice.ContainerServiceVMSizeTypes;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;

/**
 * The implementation for ContainerService and its create and update interfaces.
 */
@LangDefinition
public class ContainerServiceImpl extends
        GroupableResourceImpl<
            ContainerService,
            ContainerServiceInner,
            ContainerServiceImpl,
            ContainerServiceManager>
        implements
            ContainerService,
            ContainerService.Definition,
            ContainerService.Update {

    private String networkId;
    private String subnetName;

    protected ContainerServiceImpl(String name, ContainerServiceInner innerObject, ContainerServiceManager manager) {
        super(name, innerObject, manager);
        if (this.inner().agentPoolProfiles() == null) {
            this.inner().withAgentPoolProfiles(new ArrayList<ContainerServiceAgentPoolProfile>());
        }

        if (this.inner().masterProfile() != null && this.inner().masterProfile().vnetSubnetID() != null) {
            this.networkId = ResourceUtils.parentResourceIdFromResourceId(this.inner().masterProfile().vnetSubnetID());
            this.subnetName = ResourceUtils.nameFromResourceId(this.inner().masterProfile().vnetSubnetID());
        } else {
            this.networkId = null;
            this.subnetName = null;
        }
    }

    @Override
    public int masterNodeCount() {
        if (this.inner().masterProfile() == null
                || this.inner().masterProfile().count() == null) {
            return 0;
        }

        return this.inner().masterProfile().count();
    }

    @Override
    public ContainerServiceOrchestratorTypes orchestratorType() {
        if (this.inner().orchestratorProfile() == null) {
            throw new RuntimeException("Orchestrator profile is missing!");
        }

        return this.inner().orchestratorProfile().orchestratorType();
    }

    @Override
    public String masterLeafDomainLabel() {
        if (this.inner().masterProfile() == null) {
            return null;
        }

        return this.inner().masterProfile().dnsPrefix();
    }

    @Override
    public String masterFqdn() {
        if (this.inner().masterProfile() == null) {
            return null;
        }

        return this.inner().masterProfile().fqdn();
    }

    @Override
    public String agentPoolName() {
        if (this.getSingleAgentPool() == null) {
            return null;
        }

        return this.getSingleAgentPool().name();
    }

    @Override
    public int agentPoolCount() {
        if (this.getSingleAgentPool() == null) {
            return 0;
        }

        return this.getSingleAgentPool().count();
    }

    @Override
    public String agentPoolLeafDomainLabel() {
        if (this.getSingleAgentPool() == null) {
            return null;
        }

        return this.getSingleAgentPool().dnsPrefix();
    }

    @Override
    public ContainerServiceVMSizeTypes agentPoolVMSize() {
        if (this.getSingleAgentPool() == null) {
            return null;
        }

        return this.getSingleAgentPool().vmSize();
    }

    @Override
    public String agentPoolFqdn() {
        if (this.getSingleAgentPool() == null) {
            return null;
        }

        return this.getSingleAgentPool().fqdn();
    }

    @Override
    public String linuxRootUsername() {
        if (this.inner().linuxProfile() == null) {
            return null;
        }

        return this.inner().linuxProfile().adminUsername();
    }

    @Override
    public String sshKey() {
        if (this.inner().linuxProfile() == null
                || this.inner().linuxProfile().ssh() == null
                || this.inner().linuxProfile().ssh().publicKeys() == null
                || this.inner().linuxProfile().ssh().publicKeys().size() == 0) {
            return null;
        }

        return this.inner().linuxProfile().ssh().publicKeys().get(0).keyData();
    }

    @Override
    public String servicePrincipalClientId() {
        if (this.inner().servicePrincipalProfile() == null) {
            return null;
        }

        return this.inner().servicePrincipalProfile().clientId();
    }

    @Override
    public String servicePrincipalSecret() {
        if (this.inner().servicePrincipalProfile() == null) {
            return null;
        }

        return this.inner().servicePrincipalProfile().secret();
    }

    @Override
    public int masterOSDiskSizeInGB() {
        if (this.inner().masterProfile() == null || this.inner().masterProfile().osDiskSizeGB() == null) {
            return 0;
        }

        return this.inner().masterProfile().osDiskSizeGB();
    }

    @Override
    public ContainerServiceStorageProfileTypes masterStorageProfile() {
        if (this.inner().masterProfile() == null) {
            return null;
        }

        return this.inner().masterProfile().storageProfile();
    }

    @Override
    public String masterSubnetName() {
        return subnetName;
    }

    @Override
    public String networkId() {
        return networkId;
    }

    @Override
    public boolean isDiagnosticsEnabled() {
        if (this.inner().diagnosticsProfile() == null
                || this.inner().diagnosticsProfile().vmDiagnostics() == null) {
            throw new RuntimeException("Diagnostic profile is missing!");
        }

        return this.inner().diagnosticsProfile().vmDiagnostics().enabled();
    }

    @Override
    public ContainerServiceImpl withMasterNodeCount(ContainerServiceMasterProfileCount profileCount) {
        ContainerServiceMasterProfile masterProfile = new ContainerServiceMasterProfile().withVmSize(ContainerServiceVMSizeTypes.STANDARD_D2_V2);
        masterProfile.withCount(profileCount.count());
        this.inner().withMasterProfile(masterProfile);
        return this;
    }

    @Override
    public ContainerServiceImpl withMasterLeafDomainLabel(String dnsPrefix) {
        this.inner().masterProfile().withDnsPrefix(dnsPrefix);
        return this;
    }

    @Override
    public ContainerServiceAgentPoolImpl defineAgentPool(String name) {
        ContainerServiceAgentPoolProfile innerPoolProfile = new ContainerServiceAgentPoolProfile();
        innerPoolProfile.withName(name);
        return new ContainerServiceAgentPoolImpl(innerPoolProfile, this);
    }

    @Override
    public ContainerServiceImpl withDiagnostics() {
        this.withDiagnosticsProfile(true);
        return this;
    }

    @Override
    public ContainerServiceImpl withLinux() {
        if (this.inner().linuxProfile() == null) {
            this.inner().withLinuxProfile(new ContainerServiceLinuxProfile());
        }

        return this;
    }

    @Override
    public ContainerServiceImpl withRootUsername(String rootUserName) {
        this.inner().linuxProfile().withAdminUsername(rootUserName);
        return this;
    }

    @Override
    public ContainerServiceImpl withSshKey(String sshKeyData) {
        ContainerServiceSshConfiguration ssh = new ContainerServiceSshConfiguration();
        ssh.withPublicKeys(new ArrayList<ContainerServiceSshPublicKey>());
        ContainerServiceSshPublicKey sshPublicKey = new ContainerServiceSshPublicKey();
        sshPublicKey.withKeyData(sshKeyData);
        ssh.publicKeys().add(sshPublicKey);
        this.inner().linuxProfile().withSsh(ssh);
        return this;
    }

    @Override
    public ContainerServiceImpl withSwarmOrchestration() {
        this.withOrchestratorProfile(ContainerServiceOrchestratorTypes.SWARM);
        return this;
    }

    @Override
    public ContainerServiceImpl withDcosOrchestration() {
        this.withOrchestratorProfile(ContainerServiceOrchestratorTypes.DCOS);
        return this;
    }

    @Override
    public ContainerServiceImpl withKubernetesOrchestration() {
        this.withOrchestratorProfile(ContainerServiceOrchestratorTypes.KUBERNETES);
        return this;
    }

    @Override
    public ContainerServiceImpl withServicePrincipal(String clientId, String secret) {
        ContainerServiceServicePrincipalProfile serviceProfile =
                new ContainerServiceServicePrincipalProfile();
        serviceProfile.withClientId(clientId);
        serviceProfile.withSecret(secret);
        this.inner().withServicePrincipalProfile(serviceProfile);
        return this;
    }


    void attachAgentPoolProfile(ContainerServiceAgentPool agentPoolProfile) {
        this.inner().agentPoolProfiles().add(agentPoolProfile.inner());
    }

    private ContainerServiceImpl withOrchestratorProfile(ContainerServiceOrchestratorTypes orchestratorType) {
        ContainerServiceOrchestratorProfile orchestratorProfile = new ContainerServiceOrchestratorProfile();
        orchestratorProfile.withOrchestratorType(orchestratorType);
        this.inner().withOrchestratorProfile(orchestratorProfile);
        return this;
    }

    private ContainerServiceImpl withDiagnosticsProfile(boolean enabled) {
        if (this.inner().diagnosticsProfile() == null) {
            this.inner().withDiagnosticsProfile(new ContainerServiceDiagnosticsProfile());
        }

        if (this.inner().diagnosticsProfile().vmDiagnostics() == null) {
            this.inner().diagnosticsProfile().withVmDiagnostics(new ContainerServiceVMDiagnostics());
        }

        this.inner().diagnosticsProfile().vmDiagnostics().withEnabled(enabled);
        return this;
    }

    @Override
    public ContainerServiceImpl withAgentVMCount(int agentCount) {
        this.inner().agentPoolProfiles().get(0).withCount(agentCount);
        return this;
    }

    @Override
    public ContainerServiceImpl withMasterVMSize(ContainerServiceVMSizeTypes vmSize) {
        this.inner().masterProfile().withVmSize(vmSize);
        return this;
    }

    @Override
    public ContainerServiceImpl withMasterStorageProfile(ContainerServiceStorageProfileTypes storageProfile) {
        this.inner().masterProfile().withStorageProfile(storageProfile);
        return this;
    }

    @Override
    public ContainerServiceImpl withMasterOSDiskSizeInGB(int osDiskSizeInGB) {
        this.inner().masterProfile().withOsDiskSizeGB(osDiskSizeInGB);
        return this;
    }

    @Override
    public ContainerServiceImpl withSubnet(String networkId, String subnetName) {
        this.networkId = networkId;
        this.subnetName = subnetName;
        this.inner().masterProfile().withVnetSubnetID(networkId + "/subnets/" + subnetName);
        if (this.inner().agentPoolProfiles() != null) {
            for (ContainerServiceAgentPoolProfile agentPoolProfile : this.inner().agentPoolProfiles()) {
                String agentPoolSubnet = agentPoolProfile.vnetSubnetID();
                if (agentPoolSubnet == null) {
                    agentPoolProfile.withVnetSubnetID(networkId + "/subnets/" + subnetName);
                } else {
                    agentPoolProfile.withVnetSubnetID(networkId + "/subnets/" + agentPoolSubnet);
                }
            }
        }
        return this;
    }

    @Override
    protected Observable<ContainerServiceInner> getInnerAsync() {
        return this.manager().inner().containerServices().getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public Observable<ContainerService> createResourceAsync() {
        final ContainerServiceImpl self = this;
        if (!this.isInCreateMode()) {
            this.inner().withServicePrincipalProfile(null);
        }

        return this.manager().inner().containerServices().createOrUpdateAsync(resourceGroupName(), name(), inner())
                .map(new Func1<ContainerServiceInner, ContainerService>() {
                    @Override
                    public ContainerService call(ContainerServiceInner containerServiceInner) {
                        self.setInner(containerServiceInner);
                        return self;
                    }
                });
    }

    private ContainerServiceAgentPoolProfile getSingleAgentPool() {
        if (this.inner().agentPoolProfiles() == null
                || this.inner().agentPoolProfiles().size() == 0) {
            return null;
        }

        return this.inner().agentPoolProfiles().get(0);
    }

}
