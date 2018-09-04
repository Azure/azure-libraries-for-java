/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.network.implementation.ExpressRouteCircuitStatsInner;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.rest.v2.annotations.Beta;

/**
 * Contains stats associated with the peering.
 */
@Fluent
@Beta(since = "V1_4_0")
public interface ExpressRouteCircuitStats extends HasInner<ExpressRouteCircuitStatsInner> {
    /**
     * @return inbound bytes through primary channel of the peering
     */
    long primaryBytesIn();

    /**
     * @return outbound bytes through primary channel of the peering
     */
    long primaryBytesOut();

    /**
     * @return inbound bytes through secondary channel of the peering
     */
    long secondaryBytesIn();

    /**
     * @return outbound bytes through secondary channel of the peering
     */
    long secondaryBytesOut();
}
