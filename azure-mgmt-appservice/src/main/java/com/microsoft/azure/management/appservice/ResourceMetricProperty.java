/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.appservice;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Resource metric property.
 */
public class ResourceMetricProperty {
    /**
     * Key for resource metric property.
     */
    @JsonProperty(value = "key")
    private String key;

    /**
     * Value of pair.
     */
    @JsonProperty(value = "value")
    private String value;

    /**
     * Get the key value.
     *
     * @return the key value
     */
    public String key() {
        return this.key;
    }

    /**
     * Set the key value.
     *
     * @param key the key value to set
     * @return the ResourceMetricProperty object itself.
     */
    public ResourceMetricProperty withKey(String key) {
        this.key = key;
        return this;
    }

    /**
     * Get the value value.
     *
     * @return the value value
     */
    public String value() {
        return this.value;
    }

    /**
     * Set the value value.
     *
     * @param value the value value to set
     * @return the ResourceMetricProperty object itself.
     */
    public ResourceMetricProperty withValue(String value) {
        this.value = value;
        return this;
    }

}
