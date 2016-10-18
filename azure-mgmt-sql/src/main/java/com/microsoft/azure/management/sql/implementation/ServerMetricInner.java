/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import org.joda.time.DateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents Azure SQL Server metrics.
 */
public class ServerMetricInner {
    /**
     * The name of the resource.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String resourceName;

    /**
     * The metric display name.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String displayName;

    /**
     * The current value of the metric.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Double currentValue;

    /**
     * The current limit of the metric.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Double limit;

    /**
     * The units of the metric.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String unit;

    /**
     * The next reset time for the metric.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private DateTime nextResetTime;

    /**
     * Get the resourceName value.
     *
     * @return the resourceName value
     */
    public String resourceName() {
        return this.resourceName;
    }

    /**
     * Get the displayName value.
     *
     * @return the displayName value
     */
    public String displayName() {
        return this.displayName;
    }

    /**
     * Get the currentValue value.
     *
     * @return the currentValue value
     */
    public Double currentValue() {
        return this.currentValue;
    }

    /**
     * Get the limit value.
     *
     * @return the limit value
     */
    public Double limit() {
        return this.limit;
    }

    /**
     * Get the unit value.
     *
     * @return the unit value
     */
    public String unit() {
        return this.unit;
    }

    /**
     * Get the nextResetTime value.
     *
     * @return the nextResetTime value
     */
    public DateTime nextResetTime() {
        return this.nextResetTime;
    }

}
