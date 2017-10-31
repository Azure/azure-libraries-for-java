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
}
