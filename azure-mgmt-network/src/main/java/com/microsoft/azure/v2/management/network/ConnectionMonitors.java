/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.network.implementation.ConnectionMonitorsInner;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsGettingByNameAsync;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsDeletingByName;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.rest.v2.annotations.Beta;

/**
 * Entry point to connection monitors management API in Azure.
 */
@Fluent
@Beta(since = "V1_10_0")
public interface ConnectionMonitors extends
        SupportsCreating<ConnectionMonitor.DefinitionStages.WithSource>,
        SupportsListing<ConnectionMonitor>,
        SupportsGettingByNameAsync<ConnectionMonitor>,
        SupportsDeletingByName,
        HasInner<ConnectionMonitorsInner> {
}
