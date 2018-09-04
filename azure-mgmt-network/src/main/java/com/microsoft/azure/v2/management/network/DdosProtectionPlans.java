/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.network.implementation.DdosProtectionPlansInner;
import com.microsoft.azure.v2.management.network.implementation.NetworkManager;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsBatchDeletion;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsDeletingByResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsGettingByResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsListingByResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsBatchCreation;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsDeletingById;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.rest.v2.annotations.Beta;


/**
 * Entry point to DDoS protection plans management.
 */
@Fluent
@Beta(since = "V1_10_0")
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
