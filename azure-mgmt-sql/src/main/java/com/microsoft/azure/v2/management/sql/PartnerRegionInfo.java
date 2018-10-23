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
 * Partner region information for the failover group.
 */
public final class PartnerRegionInfo {
    /**
     * Geo location of the partner managed instances.
     */
    @JsonProperty(value = "location")
    private String location;

    /**
     * Replication role of the partner managed instances. Possible values
     * include: 'Primary', 'Secondary'.
     */
    @JsonProperty(value = "replicationRole", access = JsonProperty.Access.WRITE_ONLY)
    private InstanceFailoverGroupReplicationRole replicationRole;

    /**
     * Get the location value.
     *
     * @return the location value.
     */
    public String location() {
        return this.location;
    }

    /**
     * Set the location value.
     *
     * @param location the location value to set.
     * @return the PartnerRegionInfo object itself.
     */
    public PartnerRegionInfo withLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * Get the replicationRole value.
     *
     * @return the replicationRole value.
     */
    public InstanceFailoverGroupReplicationRole replicationRole() {
        return this.replicationRole;
    }
}