/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.network.implementation.ExpressRouteCrossConnectionsInner;
import com.microsoft.azure.v2.management.network.implementation.NetworkManager;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsGettingByResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsListingByResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.rest.v2.annotations.Beta;

/**
 * Entry point to express route crosss connections management API in Azure.
 */
@Fluent
@Beta(since = "V1_11_0")
public interface ExpressRouteCrossConnections extends
        SupportsListing<ExpressRouteCrossConnection>,
        SupportsListingByResourceGroup<ExpressRouteCrossConnection>,
        SupportsGettingByResourceGroup<ExpressRouteCrossConnection>,
        SupportsGettingById<ExpressRouteCrossConnection>,
        HasManager<NetworkManager>,
        HasInner<ExpressRouteCrossConnectionsInner> {
}