/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The minimum per-database DTU capability.
 */
public class ElasticPoolPerDatabaseMinDtuCapability {
    /**
     * The maximum DTUs per database.
     */
    @JsonProperty(value = "limit", access = JsonProperty.Access.WRITE_ONLY)
    private Long limit;

    /**
     * The status of the capability. Possible values include: 'Visible',
     * 'Available', 'Default', 'Disabled'.
     */
    @JsonProperty(value = "status", access = JsonProperty.Access.WRITE_ONLY)
    private CapabilityStatus status;

    /**
     * Get the limit value.
     *
     * @return the limit value
     */
    public Long limit() {
        return this.limit;
    }

    /**
     * Get the status value.
     *
     * @return the status value
     */
    public CapabilityStatus status() {
        return this.status;
    }

}
