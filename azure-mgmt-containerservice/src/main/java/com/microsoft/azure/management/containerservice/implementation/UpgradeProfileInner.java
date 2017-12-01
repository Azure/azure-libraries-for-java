/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.containerservice.implementation;

import com.microsoft.azure.management.containerservice.UpgradeProfileProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The list of available upgrades for compute pools.
 */
public class UpgradeProfileInner {
    /**
     * Id of upgrade profile.
     */
    @JsonProperty(value = "id", access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    /**
     * Name of upgrade profile.
     */
    @JsonProperty(value = "name", access = JsonProperty.Access.WRITE_ONLY)
    private String name;

    /**
     * Type of upgrade profile.
     */
    @JsonProperty(value = "type", access = JsonProperty.Access.WRITE_ONLY)
    private String type;

    /**
     * Properties of upgrade profile.
     */
    @JsonProperty(value = "properties", required = true)
    private UpgradeProfileProperties properties;

    /**
     * Get the id value.
     *
     * @return the id value
     */
    public String id() {
        return this.id;
    }

    /**
     * Get the name value.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Get the type value.
     *
     * @return the type value
     */
    public String type() {
        return this.type;
    }

    /**
     * Get the properties value.
     *
     * @return the properties value
     */
    public UpgradeProfileProperties properties() {
        return this.properties;
    }

    /**
     * Set the properties value.
     *
     * @param properties the properties value to set
     * @return the UpgradeProfileInner object itself.
     */
    public UpgradeProfileInner withProperties(UpgradeProfileProperties properties) {
        this.properties = properties;
        return this;
    }

}
