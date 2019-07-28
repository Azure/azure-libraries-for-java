/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.sql.CapabilityStatus;
import java.util.List;
import com.microsoft.azure.management.sql.ServerVersionCapability;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The capabilities for a location.
 */
public class LocationCapabilitiesInner {
    /**
     * The location name.
     */
    @JsonProperty(value = "name", access = JsonProperty.Access.WRITE_ONLY)
    private String name;

    /**
     * Azure SQL Database's status for the location. Possible values include:
     * 'Visible', 'Available', 'Default', 'Disabled'.
     */
    @JsonProperty(value = "status", access = JsonProperty.Access.WRITE_ONLY)
    private CapabilityStatus status;

    /**
     * The list of supported server versions.
     */
    @JsonProperty(value = "supportedServerVersions", access = JsonProperty.Access.WRITE_ONLY)
    private List<ServerVersionCapability> supportedServerVersions;

    /**
     * Get the location name.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Get azure SQL Database's status for the location. Possible values include: 'Visible', 'Available', 'Default', 'Disabled'.
     *
     * @return the status value
     */
    public CapabilityStatus status() {
        return this.status;
    }

    /**
     * Get the list of supported server versions.
     *
     * @return the supportedServerVersions value
     */
    public List<ServerVersionCapability> supportedServerVersions() {
        return this.supportedServerVersions;
    }

}
