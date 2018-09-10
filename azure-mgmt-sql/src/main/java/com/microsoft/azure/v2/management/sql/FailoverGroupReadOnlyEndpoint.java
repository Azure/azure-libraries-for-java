/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.v2.management.sql;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Read-only endpoint of the failover group instance.
 */
public final class FailoverGroupReadOnlyEndpoint {
    /**
     * Failover policy of the read-only endpoint for the failover group.
     * Possible values include: 'Disabled', 'Enabled'.
     */
    @JsonProperty(value = "failoverPolicy")
    private ReadOnlyEndpointFailoverPolicy failoverPolicy;

    /**
     * Get the failoverPolicy value.
     *
     * @return the failoverPolicy value.
     */
    public ReadOnlyEndpointFailoverPolicy failoverPolicy() {
        return this.failoverPolicy;
    }

    /**
     * Set the failoverPolicy value.
     *
     * @param failoverPolicy the failoverPolicy value to set.
     * @return the FailoverGroupReadOnlyEndpoint object itself.
     */
    public FailoverGroupReadOnlyEndpoint withFailoverPolicy(ReadOnlyEndpointFailoverPolicy failoverPolicy) {
        this.failoverPolicy = failoverPolicy;
        return this;
    }
}
