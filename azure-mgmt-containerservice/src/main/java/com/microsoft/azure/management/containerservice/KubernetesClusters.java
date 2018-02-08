/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerservice;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.containerservice.implementation.ContainerServiceManager;
import com.microsoft.azure.management.containerservice.implementation.ManagedClustersInner;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsDeletingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsListingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsBatchCreation;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingById;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import rx.Observable;

import java.util.Set;

/**
 *  Entry point to managed Kubernetes service management API.
 */
@Fluent()
@Beta(Beta.SinceVersion.V1_4_0)
public interface KubernetesClusters extends
    HasManager<ContainerServiceManager>,
    HasInner<ManagedClustersInner>,
    SupportsCreating<KubernetesCluster.DefinitionStages.Blank>,
    SupportsBatchCreation<KubernetesCluster>,
    SupportsListing<KubernetesCluster>,
    SupportsGettingById<KubernetesCluster>,
    SupportsDeletingById,
    SupportsDeletingByResourceGroup,
    SupportsListingByResourceGroup<KubernetesCluster>,
    SupportsGettingByResourceGroup<KubernetesCluster> {

    /**
     * Returns the list of available Kubernetes versions available for the given Azure region.
     *
     * @param region the Azure region to query into
     * @return a set of Kubernetes versions which can be used when creating a service in this region
     */
    Set<String> listKubernetesVersions(Region region);

    /**
     * Returns the list of available Kubernetes versions available for the given Azure region.
     *
     * @param region the Azure region to query into
     * @return a future representation of a set of Kubernetes versions which can be used when creating a service in this region
     */
    Observable<Set<String>> listKubernetesVersionsAsync(Region region);

    /**
     * Returns the admin Kube.config content which can be used with a Kubernetes client.
     *
     * @param resourceGroupName the resource group name where the cluster is
     * @param kubernetesClusterName the managed cluster name
     * @return the Kube.config content which can be used with a Kubernetes client
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    byte[] getAdminKubeConfigContent(String resourceGroupName, String kubernetesClusterName);

    /**
     * Returns asynchronously the admin Kube.config content which can be used with a Kubernetes client.
     *
     * @param resourceGroupName the resource group name where the cluster is
     * @param kubernetesClusterName the managed cluster name
     * @return a future representation of the Kube.config content which can be used with a Kubernetes client
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    Observable<byte[]> getAdminKubeConfigContentAsync(String resourceGroupName, String kubernetesClusterName);

    /**
     * Returns the user Kube.config content which can be used with a Kubernetes client.
     *
     * @param resourceGroupName the resource group name where the cluster is
     * @param kubernetesClusterName the managed cluster name
     * @return the Kube.config content which can be used with a Kubernetes client
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    byte[] getUserKubeConfigContent(String resourceGroupName, String kubernetesClusterName);

    /**
     * Returns asynchronously the user Kube.config content which can be used with a Kubernetes client.
     *
     * @param resourceGroupName the resource group name where the cluster is
     * @param kubernetesClusterName the managed cluster name
     * @return a future representation of the Kube.config content which can be used with a Kubernetes client
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    Observable<byte[]> getUserKubeConfigContentAsync(String resourceGroupName, String kubernetesClusterName);
}
