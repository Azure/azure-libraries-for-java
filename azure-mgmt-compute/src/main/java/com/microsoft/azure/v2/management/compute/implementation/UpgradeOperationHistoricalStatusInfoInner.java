/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.v2.management.compute.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.azure.v2.management.compute.UpgradeOperationHistoricalStatusInfoProperties;

/**
 * Virtual Machine Scale Set OS Upgrade History operation response.
 */
public final class UpgradeOperationHistoricalStatusInfoInner {
    /**
     * Information about the properties of the upgrade operation.
     */
    @JsonProperty(value = "properties", access = JsonProperty.Access.WRITE_ONLY)
    private UpgradeOperationHistoricalStatusInfoProperties properties;

    /**
     * Resource type.
     */
    @JsonProperty(value = "type", access = JsonProperty.Access.WRITE_ONLY)
    private String type;

    /**
     * Resource location.
     */
    @JsonProperty(value = "location", access = JsonProperty.Access.WRITE_ONLY)
    private String location;

    /**
     * Get the properties value.
     *
     * @return the properties value.
     */
    public UpgradeOperationHistoricalStatusInfoProperties properties() {
        return this.properties;
    }

    /**
     * Get the type value.
     *
     * @return the type value.
     */
    public String type() {
        return this.type;
    }

    /**
     * Get the location value.
     *
     * @return the location value.
     */
    public String location() {
        return this.location;
    }
}
