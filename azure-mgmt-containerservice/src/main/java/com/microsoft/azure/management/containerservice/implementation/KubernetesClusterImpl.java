/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerservice.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerservice.ContainerServiceAgentPool;
import com.microsoft.azure.management.containerservice.ContainerServiceAgentPoolProfile;
import com.microsoft.azure.management.containerservice.KeyVaultSecretRef;
import com.microsoft.azure.management.containerservice.KubernetesCluster;
import com.microsoft.azure.management.containerservice.KubernetesVersion;
import com.microsoft.azure.management.containerservice.ManagedClusterProperties;
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
        if (this.inner().properties() == null) {
            this.inner().withProperties(new ManagedClusterProperties());
        }
        if (this.inner().properties().agentPoolProfiles() == null) {
            this.inner().properties().withAgentPoolProfiles(new ArrayList<ContainerServiceAgentPoolProfile>());
        }

        this.useLatestVersion = false;
    }

    @Override
    public String provisioningState() {
        return null;
    }

    @Override
    public String dnsPrefix() {
        return null;
    }

    @Override
    public String fqdn() {
        return null;
    }

    @Override
    public KubernetesVersion version() {
        return null;
    }

    @Override
    public byte[] adminKubeConfigContent() {
        return new byte[0];
    }

    @Override
    public byte[] userKubeConfigContent() {
        return new byte[0];
    }

    @Override
    public String servicePrincipalClientId() {
        return null;
    }

    @Override
    public String servicePrincipalSecret() {
        return null;
    }

    @Override
    public KeyVaultSecretRef keyVaultSecretRef() {
        return null;
    }

    @Override
    public String linuxRootUsername() {
        return null;
    }

    @Override
    public String sshKey() {
        return null;
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
