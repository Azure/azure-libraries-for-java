/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network;


import com.azure.management.network.implementation.ExpressRouteCrossConnectionPeeringsInner;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingByName;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingByNameAsync;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasParent;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingById;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingByName;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * Entry point for express route cross connection peerings management API in Azure.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_11_0)
public interface ExpressRouteCrossConnectionPeerings extends
        SupportsListing<ExpressRouteCrossConnectionPeering>,
        SupportsGettingByName<ExpressRouteCrossConnectionPeering>,
        SupportsGettingById<ExpressRouteCrossConnectionPeering>,
        SupportsGettingByNameAsync<ExpressRouteCrossConnectionPeering>,
        SupportsDeletingByName,
        SupportsDeletingById,
        HasInner<ExpressRouteCrossConnectionPeeringsInner>,
        HasParent<ExpressRouteCrossConnection> {
    /**
     * Begins definition of Azure private peering.
     * @return next peering definition stage
     */
    @Method
    ExpressRouteCrossConnectionPeering.DefinitionStages.Blank defineAzurePrivatePeering();

    /**
     * Begins definition of Microsoft peering.
     * @return next peering definition stage
     */
    @Method
    ExpressRouteCrossConnectionPeering.DefinitionStages.WithAdvertisedPublicPrefixes defineMicrosoftPeering();
}