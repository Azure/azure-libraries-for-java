/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.network.implementation.LocalNetworkGatewaysInner;
import com.microsoft.azure.v2.management.network.implementation.NetworkManager;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsDeletingByResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsGettingByResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.SupportsListingByResourceGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsDeletingById;
import com.microsoft.azure.v2.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.rest.v2.annotations.Beta;

/**
 * Entry point to local network gateways management API in Azure.
 */
@Fluent
@Beta(since = "V1_3_0")
public interface LocalNetworkGateways extends
        SupportsCreating<LocalNetworkGateway.DefinitionStages.Blank>,
        SupportsListing<LocalNetworkGateway>,
        SupportsListingByResourceGroup<LocalNetworkGateway>,
        SupportsGettingByResourceGroup<LocalNetworkGateway>,
        SupportsGettingById<LocalNetworkGateway>,
        SupportsDeletingById,
        SupportsDeletingByResourceGroup,
        HasManager<NetworkManager>,
        HasInner<LocalNetworkGatewaysInner> {
}