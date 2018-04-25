/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.network.implementation.ConnectionMonitorsInner;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingByNameAsync;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingByName;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * Entry point to connection monitors management API in Azure.
 */
@Fluent
@Beta
public interface ConnectionMonitors extends
        SupportsCreating<ConnectionMonitor.DefinitionStages.WithSource>,
        SupportsListing<ConnectionMonitor>,
        SupportsGettingByNameAsync<ConnectionMonitor>,
        SupportsDeletingByName,
        HasInner<ConnectionMonitorsInner> {
}
