/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerservice.implementation;

import com.google.common.io.BaseEncoding;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerservice.KubernetesCluster;
import com.microsoft.azure.management.containerservice.KubernetesClusterAccessProfileRole;
import com.microsoft.azure.management.containerservice.KubernetesClusters;
import com.microsoft.azure.management.containerservice.OrchestratorVersionProfile;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.GroupableResourcesImpl;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupPagedList;
import rx.Completable;
import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * The implementation for KubernetesClusters.
 */
@LangDefinition
public class KubernetesClustersImpl extends
    GroupableResourcesImpl<
        KubernetesCluster,
        KubernetesClusterImpl,
        ManagedClusterInner,
        ManagedClustersInner,
        ContainerServiceManager>
    implements KubernetesClusters {

    KubernetesClustersImpl(final ContainerServiceManager containerServiceManager) {
        super(containerServiceManager.inner().managedClusters(), containerServiceManager);
    }

    @Override
    public PagedList<KubernetesCluster> list() {
        final KubernetesClustersImpl self = this;
        return new GroupPagedList<KubernetesCluster>(this.manager().resourceManager().resourceGroups().list()) {
            @Override
            public List<KubernetesCluster> listNextGroup(String resourceGroupName) {
                return wrapList(self.inner().listByResourceGroup(resourceGroupName));
            }
        };
    }

    @Override
    public Observable<KubernetesCluster> listAsync() {
        return this.manager().resourceManager().resourceGroups().listAsync()
            .flatMap(new Func1<ResourceGroup, Observable<KubernetesCluster>>() {
                @Override
                public Observable<KubernetesCluster> call(ResourceGroup resourceGroup) {
                    return wrapPageAsync(inner().listByResourceGroupAsync(resourceGroup.name()));
                }
            });
    }

    @Override
    public PagedList<KubernetesCluster> listByResourceGroup(String resourceGroupName) {
        return wrapList(this.inner().listByResourceGroup(resourceGroupName));
    }

    @Override
    public Observable<KubernetesCluster> listByResourceGroupAsync(String resourceGroupName) {
        return wrapPageAsync(this.inner().listByResourceGroupAsync(resourceGroupName));
    }

    @Override
    protected Observable<ManagedClusterInner> getInnerAsync(String resourceGroupName, String name) {
        return this.inner().getByResourceGroupAsync(resourceGroupName, name);
    }

    @Override
    protected Completable deleteInnerAsync(String resourceGroupName, String name) {
        return this.inner().deleteAsync(resourceGroupName, name).toCompletable();
    }

    /**************************************************************
     * Fluent model helpers.
     **************************************************************/

    @Override
    protected KubernetesClusterImpl wrapModel(String name) {
        return new KubernetesClusterImpl(name,
            new ManagedClusterInner(),
            this.manager());
    }

    @Override
    protected KubernetesClusterImpl wrapModel(ManagedClusterInner inner) {
        if (inner == null) {
            return null;
        }

        return new KubernetesClusterImpl(inner.name(),
            inner,
            this.manager());
    }

    @Override
    public KubernetesClusterImpl define(String name) {
        return this.wrapModel(name);
    }

    @Override
    public Set<String> listKubernetesVersions(Region region) {
        TreeSet<String> kubernetesVersions = new TreeSet<>();
        OrchestratorVersionProfileListResultInner inner = this.manager().inner().containerServices().listOrchestrators(region.name());

        if (inner != null && inner.orchestrators() != null && inner.orchestrators().size() > 0) {
            for (OrchestratorVersionProfile orchestrator : inner.orchestrators()) {
                if (orchestrator.orchestratorType().equals("Kubernetes")) {
                    kubernetesVersions.add(orchestrator.orchestratorVersion());
                }
            }
        }

        return Collections.unmodifiableSet(kubernetesVersions);
    }

    @Override
    public Observable<Set<String>> listKubernetesVersionsAsync(Region region) {
        return this.manager().inner().containerServices().listOrchestratorsAsync(region.name())
            .collect(new Func0<TreeSet<String>>() {
                @Override
                public TreeSet<String> call() {
                    return new TreeSet<String>();
                }
            }, new Action2<TreeSet<String>, OrchestratorVersionProfileListResultInner>() {
                @Override
                public void call(TreeSet<String> kubernetesVersions, OrchestratorVersionProfileListResultInner inner) {
                    if (inner != null && inner.orchestrators() != null && inner.orchestrators().size() > 0) {
                        for (OrchestratorVersionProfile orchestrator : inner.orchestrators()) {
                            if (orchestrator.orchestratorType().equals("Kubernetes")) {
                                kubernetesVersions.add(orchestrator.orchestratorVersion());
                            }
                        }
                    }
                }
            }).map(new Func1<TreeSet<String>, Set<String>>() {
                @Override
                public Set<String> call(TreeSet<String> kubernetesVersions) {
                    return Collections.unmodifiableSet(kubernetesVersions);
                }
            });
    }

    @Override
    public byte[] getAdminKubeConfigContent(String resourceGroupName, String kubernetesClusterName) {
        ManagedClusterAccessProfileInner profileInner = this.manager().inner().managedClusters().getAccessProfiles(resourceGroupName, kubernetesClusterName, KubernetesClusterAccessProfileRole.ADMIN.toString());
        if (profileInner == null) {
            return new byte[0];
        } else {
            return BaseEncoding.base64().decode(profileInner.kubeConfig());
        }
    }

    @Override
    public Observable<byte[]> getAdminKubeConfigContentAsync(String resourceGroupName, String kubernetesClusterName) {
        return this.manager().inner().managedClusters()
            .getAccessProfilesAsync(resourceGroupName, kubernetesClusterName, KubernetesClusterAccessProfileRole.ADMIN.toString())
            .map(new Func1<ManagedClusterAccessProfileInner, byte[]>() {
                @Override
                public byte[] call(ManagedClusterAccessProfileInner profileInner) {
                    if (profileInner == null) {
                        return new byte[0];
                    } else {
                        return BaseEncoding.base64().decode(profileInner.kubeConfig());
                    }
                }
            });
    }

    @Override
    public byte[] getUserKubeConfigContent(String resourceGroupName, String kubernetesClusterName) {
        ManagedClusterAccessProfileInner profileInner = this.manager().inner().managedClusters().getAccessProfiles(resourceGroupName, kubernetesClusterName, KubernetesClusterAccessProfileRole.USER.toString());
        if (profileInner == null) {
            return new byte[0];
        } else {
            return BaseEncoding.base64().decode(profileInner.kubeConfig());
        }
    }

    @Override
    public Observable<byte[]> getUserKubeConfigContentAsync(String resourceGroupName, String kubernetesClusterName) {
        return this.manager().inner().managedClusters()
            .getAccessProfilesAsync(resourceGroupName, kubernetesClusterName, KubernetesClusterAccessProfileRole.USER.toString())
            .map(new Func1<ManagedClusterAccessProfileInner, byte[]>() {
                @Override
                public byte[] call(ManagedClusterAccessProfileInner profileInner) {
                    if (profileInner == null) {
                        return new byte[0];
                    } else {
                        return BaseEncoding.base64().decode(profileInner.kubeConfig());
                    }
                }
            });
    }
}
