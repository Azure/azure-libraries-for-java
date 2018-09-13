/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.monitor.implementation.AutoscaleSettingsInner;
import com.microsoft.azure.management.monitor.implementation.MonitorManager;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsBatchDeletion;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsDeletingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsListingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsBatchCreation;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingById;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;


/**
 * Entry point to autoscale management API in Azure.
 */
@Beta(Beta.SinceVersion.V1_15_0)
@Fluent
public interface AutoscaleSettings extends
        SupportsCreating<AutoscaleSetting.DefinitionStages.Blank>,
        SupportsListing<AutoscaleSetting>,
        SupportsListingByResourceGroup<AutoscaleSetting>,
        SupportsGettingById<AutoscaleSetting>,
        SupportsBatchCreation<AutoscaleSetting>,
        SupportsDeletingById,
        SupportsDeletingByResourceGroup,
        SupportsBatchDeletion,
        HasManager<MonitorManager>,
        HasInner<AutoscaleSettingsInner> {
}
