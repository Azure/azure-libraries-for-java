/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.batch;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A SKU capability, such as the number of cores.
 */
public class SkuCapability {
    /**
     * The name of the feature.
     */
    @JsonProperty(value = "name", access = JsonProperty.Access.WRITE_ONLY)
    private String name;

    /**
     * The value of the feature.
     */
    @JsonProperty(value = "value", access = JsonProperty.Access.WRITE_ONLY)
    private String value;

    /**
     * Get the name of the feature.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Get the value of the feature.
     *
     * @return the value value
     */
    public String value() {
        return this.value;
    }

}