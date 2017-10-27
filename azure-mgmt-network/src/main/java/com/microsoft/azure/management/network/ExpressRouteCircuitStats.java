/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.network.implementation.ExpressRouteCircuitStatsInner;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * Contains stats associated with the peering.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_4_0)
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
