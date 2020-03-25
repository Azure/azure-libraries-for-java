// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.storage.models;

import com.azure.core.annotation.Immutable;
import com.azure.management.storage.UsageName;
import com.azure.management.storage.UsageUnit;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Usage model.
 */
@Immutable
public final class UsageInner {
    /*
     * Gets the unit of measurement.
     */
    @JsonProperty(value = "unit", access = JsonProperty.Access.WRITE_ONLY)
    private UsageUnit unit;

    /*
     * Gets the current count of the allocated resources in the subscription.
     */
    @JsonProperty(value = "currentValue", access = JsonProperty.Access.WRITE_ONLY)
    private Integer currentValue;

    /*
     * Gets the maximum count of the resources that can be allocated in the
     * subscription.
     */
    @JsonProperty(value = "limit", access = JsonProperty.Access.WRITE_ONLY)
    private Integer limit;

    /*
     * Gets the name of the type of usage.
     */
    @JsonProperty(value = "name", access = JsonProperty.Access.WRITE_ONLY)
    private UsageName name;

    /**
     * Get the unit property: Gets the unit of measurement.
     * 
     * @return the unit value.
     */
    public UsageUnit getUnit() {
        return this.unit;
    }

    /**
     * Get the currentValue property: Gets the current count of the allocated
     * resources in the subscription.
     * 
     * @return the currentValue value.
     */
    public Integer getCurrentValue() {
        return this.currentValue;
    }

    /**
     * Get the limit property: Gets the maximum count of the resources that can
     * be allocated in the subscription.
     * 
     * @return the limit value.
     */
    public Integer getLimit() {
        return this.limit;
    }

    /**
     * Get the name property: Gets the name of the type of usage.
     * 
     * @return the name value.
     */
    public UsageName getName() {
        return this.name;
    }
}
