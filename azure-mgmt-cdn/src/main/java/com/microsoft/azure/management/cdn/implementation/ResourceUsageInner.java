/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cdn.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Output of check resource usage API.
 */
public class ResourceUsageInner {
    /**
     * Resource type for which the usage is provided.
     */
    @JsonProperty(value = "resourceType", access = JsonProperty.Access.WRITE_ONLY)
    private String resourceType;

    /**
     * Unit of the usage. e.g. Count.
     */
    @JsonProperty(value = "unit", access = JsonProperty.Access.WRITE_ONLY)
    private String unit;

    /**
     * Actual value of usage on the specified resource type.
     */
    @JsonProperty(value = "currentValue", access = JsonProperty.Access.WRITE_ONLY)
    private Integer currentValue;

    /**
     * Quota of the specified resource type.
     */
    @JsonProperty(value = "limit", access = JsonProperty.Access.WRITE_ONLY)
    private Integer limit;

    /**
     * Get resource type for which the usage is provided.
     *
     * @return the resourceType value
     */
    public String resourceType() {
        return this.resourceType;
    }

    /**
     * Get unit of the usage. e.g. Count.
     *
     * @return the unit value
     */
    public String unit() {
        return this.unit;
    }

    /**
     * Get actual value of usage on the specified resource type.
     *
     * @return the currentValue value
     */
    public Integer currentValue() {
        return this.currentValue;
    }

    /**
     * Get quota of the specified resource type.
     *
     * @return the limit value
     */
    public Integer limit() {
        return this.limit;
    }

}
