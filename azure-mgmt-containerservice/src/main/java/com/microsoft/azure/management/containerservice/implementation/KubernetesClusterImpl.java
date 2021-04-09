/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerservice.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerservice.ContainerServiceLinuxProfile;
import com.microsoft.azure.management.containerservice.ContainerServiceNetworkProfile;
import com.microsoft.azure.management.containerservice.ContainerServiceSshConfiguration;
import com.microsoft.azure.management.containerservice.ContainerServiceSshPublicKey;
import com.microsoft.azure.management.containerservice.KubernetesCluster;
import com.microsoft.azure.management.containerservice.KubernetesClusterAgentPool;
import com.microsoft.azure.management.containerservice.KubernetesVersion;
import com.microsoft.azure.management.containerservice.ManagedClusterAddonProfile;
import com.microsoft.azure.management.containerservice.ManagedClusterAgentPoolProfile;
import com.microsoft.azure.management.containerservice.ManagedClusterSKU;
import com.microsoft.azure.management.containerservice.ManagedClusterServicePrincipalProfile;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.dag.FunctionalTaskItem;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The implementation for KubernetesCluster and its create and update interfaces.
 */
@LangDefinition
public class KubernetesClusterImpl extends
    GroupableResourceImpl<
        KubernetesCluster,
        ManagedClusterInner,
        KubernetesClusterImpl,
        ContainerServiceManager>
    implements
    KubernetesCluster,
    KubernetesCluster.Definition,
    KubernetesCluster.Update {

    private byte[] adminKubeConfigContent;
    private byte[] userKubeConfigContent;

    protected KubernetesClusterImpl(String name, ManagedClusterInner innerObject, ContainerServiceManager manager) {
        super(name, innerObject, manager);
        if (this.inner().agentPoolProfiles() == null) {
            this.inner().withAgentPoolProfiles(new ArrayList<ManagedClusterAgentPoolProfile>());
        }

        this.adminKubeConfigContent = null;
        this.userKubeConfigContent = null;
    }

    @Override
    public String provisioningState() {
        return this.inner().provisioningState();
    }

    @Override
    public String dnsPrefix() {
        return this.inner().dnsPrefix();
    }

    @Override
    public String fqdn() {
        return this.inner().fqdn();
    }

    @Override
    public ManagedClusterSKU sku() {
        return this.inner().sku();
    }

    @Override
    public KubernetesVersion version() {
        return KubernetesVersion.fromString(this.inner().kubernetesVersion());
    }

    @Override
    public byte[] adminKubeConfigContent() {
        if (this.adminKubeConfigContent == null) {
            this.adminKubeConfigContent = this.manager().kubernetesClusters()
                .getAdminKubeConfigContent(this.resourceGroupName(), this.name());
        }
        return this.adminKubeConfigContent;
    }

    @Override
    public byte[] userKubeConfigContent() {
        if (this.userKubeConfigContent == null) {
            this.userKubeConfigContent = this.manager().kubernetesClusters()
                .getUserKubeConfigContent(this.resourceGroupName(), this.name());
        }
        return this.userKubeConfigContent;
    }

    @Override
    public String servicePrincipalClientId() {
        if (this.inner().servicePrincipalProfile() != null) {
            return this.inner().servicePrincipalProfile().clientId();
        } else {
            return null;
        }
    }

    @Override
    public String servicePrincipalSecret() {
        if (this.inner().servicePrincipalProfile() != null) {
            return this.inner().servicePrincipalProfile().secret();
        } else {
            return null;
        }
    }

    @Override
    public String linuxRootUsername() {
        if (this.inner().linuxProfile() != null) {
            return this.inner().linuxProfile().adminUsername();
        } else {
            return null;
        }
    }

    @Override
    public String sshKey() {
        if (this.inner().linuxProfile() == null
            || this.inner().linuxProfile().ssh() == null
            || this.inner().linuxProfile().ssh().publicKeys() == null
            || this.inner().linuxProfile().ssh().publicKeys().size() == 0) {
            return null;
        } else {
            return this.inner().linuxProfile().ssh().publicKeys().get(0).keyData();
        }
    }

    @Override
    public Map<String, KubernetesClusterAgentPool> agentPools() {
        Map<String, KubernetesClusterAgentPool> agentPoolMap = new HashMap<>();
        if (this.inner().agentPoolProfiles() != null && this.inner().agentPoolProfiles().size() > 0) {
            for (ManagedClusterAgentPoolProfile agentPoolProfile : this.inner().agentPoolProfiles()) {
                agentPoolMap.put(agentPoolProfile.name(), new KubernetesClusterAgentPoolImpl(agentPoolProfile, this));
            }
        }

        return Collections.unmodifiableMap(agentPoolMap);
    }

    @Override
    public ContainerServiceNetworkProfile networkProfile() {
        return this.inner().networkProfile();
    }

    @Override
    public Map<String, ManagedClusterAddonProfile> addonProfiles() {
        return Collections.unmodifiableMap(this.inner().addonProfiles());
    }

    @Override
    public String nodeResourceGroup() {
        return this.inner().nodeResourceGroup();
    }

    @Override
    public boolean enableRBAC() {
        return this.inner().enableRBAC();
    }

    private Observable<byte[]> getAdminConfig(final KubernetesClusterImpl self) {
        return this.manager().kubernetesClusters()
            .getAdminKubeConfigContentAsync(self.resourceGroupName(), self.name())
            .map(new Func1<byte[], byte[]>() {
                @Override
                public byte[] call(byte[] kubeConfigContent) {
                    self.adminKubeConfigContent = kubeConfigContent;
                    return self.adminKubeConfigContent;
                }
            });
    }

    private Observable<byte[]> getUserConfig(final KubernetesClusterImpl self) {
        return this.manager().kubernetesClusters()
            .getUserKubeConfigContentAsync(self.resourceGroupName(), self.name())
            .map(new Func1<byte[], byte[]>() {
                @Override
                public byte[] call(byte[] kubeConfigContent) {
                    self.userKubeConfigContent = kubeConfigContent;
                    return self.userKubeConfigContent;
                }
            });
    }


    @Override
    protected Observable<ManagedClusterInner> getInnerAsync() {
        final KubernetesClusterImpl self = this;
        final Observable<byte[]> adminConfig = getAdminConfig(self);
        final Observable<byte[]> userConfig = getUserConfig(self);
        return this.manager().inner().managedClusters().getByResourceGroupAsync(this.resourceGroupName(), this.name())
            .flatMap(new Func1<ManagedClusterInner, Observable<ManagedClusterInner>>() {
                @Override
                public Observable<ManagedClusterInner> call(final ManagedClusterInner managedClusterInner) {
                    return Observable.merge(adminConfig, userConfig).last()
                        .map(new Func1<byte[], ManagedClusterInner>() {
                            @Override
                            public ManagedClusterInner call(byte[] bytes) {
                                return managedClusterInner;
                            }
                        });
                }
            });
    }

    @Override
    public Observable<KubernetesCluster> createResourceAsync() {
        final KubernetesClusterImpl self = this;
        if (!this.isInCreateMode()) {
            this.inner().withServicePrincipalProfile(null);
        }
        final Observable<byte[]> adminConfig = getAdminConfig(self);
        final Observable<byte[]> userConfig = getUserConfig(self);
        final Observable<KubernetesCluster> mergedConfigs = Observable.merge(adminConfig, userConfig).last()
            .map(new Func1<byte[], KubernetesCluster>() {
                @Override
                public KubernetesCluster call(byte[] bytes) {
                    return self;
                }
            });

            return this.manager().inner().managedClusters().createOrUpdateAsync(self.resourceGroupName(), self.name(), self.inner())
                .map(new Func1<ManagedClusterInner, KubernetesCluster>() {
                    @Override
                    public KubernetesCluster call(ManagedClusterInner inner) {
                        self.setInner(inner);
                        return self;
                    }
                }).flatMap(new Func1<KubernetesCluster, Observable<KubernetesCluster>>() {
                    @Override
                    public Observable<KubernetesCluster> call(KubernetesCluster kubernetesCluster) {
                        return mergedConfigs;
                    }
                });
    }

    @Override
    public KubernetesClusterImpl withVersion(KubernetesVersion kubernetesVersion) {
        this.inner().withKubernetesVersion(kubernetesVersion.toString());
        return this;
    }

    @Override
    public KubernetesClusterImpl withVersion(String kubernetesVersion) {
        this.inner().withKubernetesVersion(kubernetesVersion);
        return this;
    }

    @Override
    public KubernetesClusterImpl withLatestVersion() {
        this.inner().withKubernetesVersion("");
        return this;
    }

    @Override
    public KubernetesClusterImpl withRootUsername(String rootUserName) {
        if (this.inner().linuxProfile() == null) {
            this.inner().withLinuxProfile(new ContainerServiceLinuxProfile());
        }
        this.inner().linuxProfile().withAdminUsername(rootUserName);

        return this;
    }

    @Override
    public KubernetesClusterImpl withSshKey(String sshKeyData) {
        this.inner().linuxProfile()
            .withSsh(new ContainerServiceSshConfiguration()
                .withPublicKeys(new ArrayList<ContainerServiceSshPublicKey>()));
        this.inner().linuxProfile().ssh().publicKeys()
            .add(new ContainerServiceSshPublicKey()
                .withKeyData(sshKeyData));

        return this;
    }

    @Override
    public KubernetesClusterImpl withServicePrincipalClientId(String clientId) {
        this.inner().withServicePrincipalProfile(new ManagedClusterServicePrincipalProfile().withClientId(clientId));
        return this;
    }

    @Override
    public KubernetesClusterImpl withServicePrincipalSecret(String secret) {
        this.inner().servicePrincipalProfile().withSecret(secret);
        return this;
    }

    @Override
    public KubernetesClusterImpl withDnsPrefix(String dnsPrefix) {
        this.inner().withDnsPrefix(dnsPrefix);
        return this;
    }

    @Override
    public KubernetesClusterAgentPoolImpl defineAgentPool(String name) {
        ManagedClusterAgentPoolProfile innerPoolProfile = new ManagedClusterAgentPoolProfile();
        innerPoolProfile.withName(name);
        return new KubernetesClusterAgentPoolImpl(innerPoolProfile, this);
    }

    @Override
    public KubernetesClusterImpl withAgentPoolVirtualMachineCount(String agentPoolName, int agentCount) {
        if (this.inner().agentPoolProfiles() != null && this.inner().agentPoolProfiles().size() > 0) {
            for (ManagedClusterAgentPoolProfile agentPoolProfile : this.inner().agentPoolProfiles()) {
                if (agentPoolProfile.name().equals(agentPoolName)) {
                    agentPoolProfile.withCount(agentCount);
                    break;
                }
            }
        }
        return this;
    }

    @Override
    public KubernetesClusterImpl withAgentPoolVirtualMachineCount(int agentCount) {
        if (this.inner().agentPoolProfiles() != null && this.inner().agentPoolProfiles().size() > 0) {
            for (ManagedClusterAgentPoolProfile agentPoolProfile : this.inner().agentPoolProfiles()) {
                agentPoolProfile.withCount(agentCount);
            }
        }
        return this;
    }

    @Override
    public KubernetesClusterAgentPoolImpl updateAgentPool(String name) {
        for (ManagedClusterAgentPoolProfile agentPoolProfile : inner().agentPoolProfiles()) {
            if (agentPoolProfile.name().equals(name)) {
                return new KubernetesClusterAgentPoolImpl(agentPoolProfile, this);
            }
        }
        throw new IllegalArgumentException(String.format(
                "Cannot get agent pool named %s", name));
    }

    @Override
    public KubernetesCluster.DefinitionStages.NetworkProfileDefinitionStages.Blank<KubernetesCluster.DefinitionStages.WithCreate> defineNetworkProfile() {
        return new KubernetesClusterNetworkProfileImpl(this);
    }

    @Override
    public KubernetesClusterImpl withAddOnProfiles(Map<String, ManagedClusterAddonProfile> addOnProfileMap) {
        this.inner().withAddonProfiles(addOnProfileMap);
        return this;
    }

    @Override
    public KubernetesClusterImpl withNetworkProfile(ContainerServiceNetworkProfile networkProfile) {
        this.inner().withNetworkProfile(networkProfile);
        return this;
    }

    @Override
    public KubernetesClusterImpl withRBACEnabled() {
        this.inner().withEnableRBAC(true);
        return this;
    }

    @Override
    public KubernetesClusterImpl withRBACDisabled() {
        this.inner().withEnableRBAC(false);
        return this;
    }

    @Override
    public KubernetesCluster.DefinitionStages.WithCreate withSku(ManagedClusterSKU sku) {
        this.inner().withSku(sku);
        return this;
    }

    KubernetesClusterImpl addNewAgentPool(final KubernetesClusterAgentPoolImpl agentPool) {
        if (!isInCreateMode()) {
            this.addDependency(
                    new FunctionalTaskItem() {
                        @Override
                        public Observable<Indexable> call(final Context context) {
                            return manager().inner().agentPools().createOrUpdateAsync(
                                    resourceGroupName(), name(), agentPool.name(), agentPool.getAgentPoolInner()
                            ).flatMap(new Func1<AgentPoolInner, Observable<Indexable>>() {
                                @Override
                                public Observable<Indexable> call(AgentPoolInner agentPoolInner) {
                                    return context.voidObservable();
                                }
                            });
                        }
                    });
        }
        inner().agentPoolProfiles().add(agentPool.inner());
        return this;
    }
}
