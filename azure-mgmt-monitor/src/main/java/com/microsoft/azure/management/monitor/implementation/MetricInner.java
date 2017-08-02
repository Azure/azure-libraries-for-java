/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.monitor.implementation;

import com.microsoft.azure.management.monitor.Unit;
import java.util.List;
import com.microsoft.azure.management.monitor.MetricValue;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A set of metric values in a time range.
 */
public class MetricInner {
    /**
     * the id, resourceId, of the metric.
     */
    @JsonProperty(value = "id")
    private String id;

    /**
     * the resource type of the metric resource.
     */
    @JsonProperty(value = "type")
    private String type;

    /**
     * the name and the display name of the metric, i.e. it is localizable
     * string.
     */
    @JsonProperty(value = "name", required = true)
    private LocalizableStringInner name;

    /**
     * the unit of the metric. Possible values include: 'Count', 'Bytes',
     * 'Seconds', 'CountPerSecond', 'BytesPerSecond', 'Percent',
     * 'MilliSeconds'.
     */
    @JsonProperty(value = "unit", required = true)
    private Unit unit;

    /**
     * Array of data points representing the metric values.
     */
    @JsonProperty(value = "data", required = true)
    private List<MetricValue> data;

    /**
     * Get the id value.
     *
     * @return the id value
     */
    public String id() {
        return this.id;
    }

    /**
     * Set the id value.
     *
     * @param id the id value to set
     * @return the MetricInner object itself.
     */
    public MetricInner withId(String id) {
        this.id = id;
        return this;
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
     * Set the type value.
     *
     * @param type the type value to set
     * @return the MetricInner object itself.
     */
    public MetricInner withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get the name value.
     *
     * @return the name value
     */
    public LocalizableStringInner name() {
        return this.name;
    }

    /**
     * Set the name value.
     *
     * @param name the name value to set
     * @return the MetricInner object itself.
     */
    public MetricInner withName(LocalizableStringInner name) {
        this.name = name;
        return this;
    }

    /**
     * Get the unit value.
     *
     * @return the unit value
     */
    public Unit unit() {
        return this.unit;
    }

    /**
     * Set the unit value.
     *
     * @param unit the unit value to set
     * @return the MetricInner object itself.
     */
    public MetricInner withUnit(Unit unit) {
        this.unit = unit;
        return this;
    }

    /**
     * Get the data value.
     *
     * @return the data value
     */
    public List<MetricValue> data() {
        return this.data;
    }

    /**
     * Set the data value.
     *
     * @param data the data value to set
     * @return the MetricInner object itself.
     */
    public MetricInner withData(List<MetricValue> data) {
        this.data = data;
        return this;
    }

}
