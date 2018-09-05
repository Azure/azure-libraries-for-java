/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.network.implementation.ConnectionMonitorQueryResultInner;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.rest.v2.annotations.Beta;

import java.util.List;

/**
 * List of connection states snaphots.
 */
@Fluent
@Beta(since = "V1_10_0")
public interface ConnectionMonitorQueryResult extends HasInner<ConnectionMonitorQueryResultInner> {
    /**
     * @return status of connection monitor source
     */
    ConnectionMonitorSourceStatus sourceStatus();

    /**
     * @return information about connection states
     */
    List<ConnectionStateSnapshot> states();
}
