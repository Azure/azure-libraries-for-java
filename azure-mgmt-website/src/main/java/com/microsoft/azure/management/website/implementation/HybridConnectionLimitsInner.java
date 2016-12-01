/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.website.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.Resource;

/**
 * Hybrid Connection limits contract. This is used to return the plan limits
 * of Hybrid Connections.
 */
@JsonFlatten
public class HybridConnectionLimitsInner extends Resource {
    /**
     * The current number of Hybrid Connections.
     */
    @JsonProperty(value = "properties.current", access = JsonProperty.Access.WRITE_ONLY)
    private Integer current;

    /**
     * The maximum number of Hybrid Connections allowed.
     */
    @JsonProperty(value = "properties.maximum", access = JsonProperty.Access.WRITE_ONLY)
    private Integer maximum;

    /**
     * Get the current value.
     *
     * @return the current value
     */
    public Integer current() {
        return this.current;
    }

    /**
     * Get the maximum value.
     *
     * @return the maximum value
     */
    public Integer maximum() {
        return this.maximum;
    }

}
