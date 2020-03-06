/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.azure.management.cosmosdb.implementation;

import org.joda.time.DateTime;
import com.azure.management.cosmosdb.UnitType;
import com.azure.management.cosmosdb.MetricName;
import java.util.List;
import com.azure.management.cosmosdb.MetricValue;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Metric data.
 */
public class MetricInner {
    /**
     * The start time for the metric (ISO-8601 format).
     */
    @JsonProperty(value = "startTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime startTime;

    /**
     * The end time for the metric (ISO-8601 format).
     */
    @JsonProperty(value = "endTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime endTime;

    /**
     * The time grain to be used to summarize the metric values.
     */
    @JsonProperty(value = "timeGrain", access = JsonProperty.Access.WRITE_ONLY)
    private String timeGrain;

    /**
     * The unit of the metric. Possible values include: 'Count', 'Bytes',
     * 'Seconds', 'Percent', 'CountPerSecond', 'BytesPerSecond',
     * 'Milliseconds'.
     */
    @JsonProperty(value = "unit")
    private UnitType unit;

    /**
     * The name information for the metric.
     */
    @JsonProperty(value = "name", access = JsonProperty.Access.WRITE_ONLY)
    private MetricName name;

    /**
     * The metric values for the specified time window and timestep.
     */
    @JsonProperty(value = "metricValues", access = JsonProperty.Access.WRITE_ONLY)
    private List<MetricValue> metricValues;

    /**
     * Get the start time for the metric (ISO-8601 format).
     *
     * @return the startTime value
     */
    public DateTime startTime() {
        return this.startTime;
    }

    /**
     * Get the end time for the metric (ISO-8601 format).
     *
     * @return the endTime value
     */
    public DateTime endTime() {
        return this.endTime;
    }

    /**
     * Get the time grain to be used to summarize the metric values.
     *
     * @return the timeGrain value
     */
    public String timeGrain() {
        return this.timeGrain;
    }

    /**
     * Get the unit of the metric. Possible values include: 'Count', 'Bytes', 'Seconds', 'Percent', 'CountPerSecond', 'BytesPerSecond', 'Milliseconds'.
     *
     * @return the unit value
     */
    public UnitType unit() {
        return this.unit;
    }

    /**
     * Set the unit of the metric. Possible values include: 'Count', 'Bytes', 'Seconds', 'Percent', 'CountPerSecond', 'BytesPerSecond', 'Milliseconds'.
     *
     * @param unit the unit value to set
     * @return the MetricInner object itself.
     */
    public MetricInner withUnit(UnitType unit) {
        this.unit = unit;
        return this;
    }

    /**
     * Get the name information for the metric.
     *
     * @return the name value
     */
    public MetricName name() {
        return this.name;
    }

    /**
     * Get the metric values for the specified time window and timestep.
     *
     * @return the metricValues value
     */
    public List<MetricValue> metricValues() {
        return this.metricValues;
    }

}
