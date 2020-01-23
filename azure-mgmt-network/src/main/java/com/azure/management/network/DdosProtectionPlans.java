/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network;

import com.azure.management.network.implementation.DdosProtectionPlansInner;
import com.azure.management.network.implementation.NetworkManager;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsBatchDeletion;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsDeletingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsListingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsBatchCreation;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingById;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;


/**
 * Entry point to DDoS protection plans management.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_10_0)
public interface DdosProtectionPlans extends
        SupportsCreating<DdosProtectionPlan.DefinitionStages.Blank>,
        SupportsListing<DdosProtectionPlan>,
        SupportsListingByResourceGroup<DdosProtectionPlan>,
        SupportsGettingByResourceGroup<DdosProtectionPlan>,
        SupportsGettingById<DdosProtectionPlan>,
        SupportsDeletingById,
        SupportsDeletingByResourceGroup,
        SupportsBatchCreation<DdosProtectionPlan>,
        SupportsBatchDeletion,
        HasManager<NetworkManager>,
        HasInner<DdosProtectionPlansInner> {
}
