/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.provisioningservices;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * List of possible provisoning service SKUs.
 */
public class IotDpsSkuInfo {
    /**
     * Sku name. Possible values include: 'S1'.
     */
    @JsonProperty(value = "name")
    private IotDpsSku name;

    /**
     * Pricing tier name of the provisioning service.
     */
    @JsonProperty(value = "tier", access = JsonProperty.Access.WRITE_ONLY)
    private String tier;

    /**
     * The number of units to provision.
     */
    @JsonProperty(value = "capacity")
    private Long capacity;

    /**
     * Get the name value.
     *
     * @return the name value
     */
    public IotDpsSku name() {
        return this.name;
    }

    /**
     * Set the name value.
     *
     * @param name the name value to set
     * @return the IotDpsSkuInfo object itself.
     */
    public IotDpsSkuInfo withName(IotDpsSku name) {
        this.name = name;
        return this;
    }

    /**
     * Get the tier value.
     *
     * @return the tier value
     */
    public String tier() {
        return this.tier;
    }

    /**
     * Get the capacity value.
     *
     * @return the capacity value
     */
    public Long capacity() {
        return this.capacity;
    }

    /**
     * Set the capacity value.
     *
     * @param capacity the capacity value to set
     * @return the IotDpsSkuInfo object itself.
     */
    public IotDpsSkuInfo withCapacity(Long capacity) {
        this.capacity = capacity;
        return this;
    }

}
