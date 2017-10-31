/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerservice.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerservice.KubernetesCluster;
import com.microsoft.azure.management.containerservice.KubernetesClusters;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.GroupableResourcesImpl;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupPagedList;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.List;

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
}
