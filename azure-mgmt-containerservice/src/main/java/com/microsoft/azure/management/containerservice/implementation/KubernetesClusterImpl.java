/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerservice.implementation;

import com.google.common.io.BaseEncoding;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerservice.ContainerServiceAgentPool;
import com.microsoft.azure.management.containerservice.ContainerServiceAgentPoolProfile;
import com.microsoft.azure.management.containerservice.KeyVaultSecretRef;
import com.microsoft.azure.management.containerservice.KubernetesCluster;
import com.microsoft.azure.management.containerservice.KubernetesClusterAgentPool;
import com.microsoft.azure.management.containerservice.KubernetesVersion;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
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

    private boolean useLatestVersion;

    protected KubernetesClusterImpl(String name, ManagedClusterInner innerObject, ContainerServiceManager manager) {
        super(name, innerObject, manager);
        if (this.inner().agentPoolProfiles() == null) {
            this.inner().withAgentPoolProfiles(new ArrayList<ContainerServiceAgentPoolProfile>());
        }

        this.useLatestVersion = false;
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
    public KubernetesVersion version() {
        return KubernetesVersion.fromString(this.inner().kubernetesVersion());
    }

    @Override
    public byte[] adminKubeConfigContent() {
        if (this.inner().accessProfiles() == null
            || this.inner().accessProfiles().clusterAdmin() == null
            || this.inner().accessProfiles().clusterAdmin().kubeConfig() == null) {
            return new byte[0];
        } else {
            return BaseEncoding.base64().decode(this.inner().accessProfiles().clusterAdmin().kubeConfig());
        }
    }

    @Override
    public byte[] userKubeConfigContent() {
        if (this.inner().accessProfiles() == null
            || this.inner().accessProfiles().clusterUser() == null
            || this.inner().accessProfiles().clusterUser().kubeConfig() == null) {
            return new byte[0];
        } else {
            return BaseEncoding.base64().decode(this.inner().accessProfiles().clusterUser().kubeConfig());
        }
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
    public KeyVaultSecretRef keyVaultSecretRef() {
        if (this.inner().servicePrincipalProfile() != null) {
            return this.inner().servicePrincipalProfile().keyVaultSecretRef();
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
            for (ContainerServiceAgentPoolProfile agentPoolProfile : this.inner().agentPoolProfiles()) {
                agentPoolMap.put(agentPoolProfile.name(), new KubernetesClusterAgentPoolImpl(agentPoolProfile, this));
            }
        }

        return Collections.unmodifiableMap(agentPoolMap);
    }


    @Override
    protected Observable<ManagedClusterInner> getInnerAsync() {
        return null;
    }

    @Override
    public Observable<KubernetesCluster> createResourceAsync() {
        return null;
    }

    @Override
    public KubernetesClusterImpl withVersion(KubernetesVersion kubernetesVersion) {
        return null;
    }

    @Override
    public KubernetesClusterImpl withLatestVersion() {
        return null;
    }

    @Override
    public KubernetesClusterImpl withRootUsername(String rootUserName) {
        return null;
    }

    @Override
    public KubernetesClusterImpl withSshKey(String sshKeyData) {
        return null;
    }

    @Override
    public KubernetesClusterImpl withServicePrincipalClientId(String clientId) {
        return null;
    }

    @Override
    public KubernetesClusterImpl withoutServicePrincipalProfile() {
        return null;
    }

    @Override
    public KubernetesClusterImpl withServicePrincipalSecret(String secret) {
        return null;
    }

    @Override
    public KubernetesClusterImpl withKeyVaultReference(String vaultId) {
        return null;
    }

    @Override
    public KubernetesClusterImpl withKeyVaultSecret(String secret) {
        return null;
    }

    @Override
    public KubernetesClusterImpl withKeyVaultSecret(String secret, String version) {
        return null;
    }

    @Override
    public KubernetesClusterImpl withDnsPrefix(String dnsPrefix) {
        return null;
    }

    @Override
    public ContainerServiceAgentPool.DefinitionStages.Blank<KubernetesCluster.DefinitionStages.WithCreate> defineAgentPool(String name) {
        return null;
    }

    @Override
    public KubernetesClusterImpl withAgentVMCount(String agentPoolName, int agentCount) {
        return null;
    }

}
