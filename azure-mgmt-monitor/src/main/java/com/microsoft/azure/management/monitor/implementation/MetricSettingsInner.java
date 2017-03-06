/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.monitor.implementation;

import com.microsoft.azure.management.monitor.RetentionPolicy;
import org.joda.time.Period;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Part of MultiTenantDiagnosticSettings. Specifies the settings for a
 * particular metric.
 */
public class MetricSettingsInner {
    /**
     * the timegrain of the metric in ISO8601 format.
     */
    @JsonProperty(value = "timeGrain", required = true)
    private Period timeGrain;

    /**
     * a value indicating whether this timegrain is enabled.
     */
    @JsonProperty(value = "enabled", required = true)
    private boolean enabled;

    /**
     * the retention policy for this timegrain.
     */
    @JsonProperty(value = "retentionPolicy")
    private RetentionPolicy retentionPolicy;

    /**
     * Get the timeGrain value.
     *
     * @return the timeGrain value
     */
    public Period timeGrain() {
        return this.timeGrain;
    }

    /**
     * Set the timeGrain value.
     *
     * @param timeGrain the timeGrain value to set
     * @return the MetricSettings object itself.
     */
    public MetricSettingsInner withTimeGrain(Period timeGrain) {
        this.timeGrain = timeGrain;
        return this;
    }

    /**
     * Get the enabled value.
     *
     * @return the enabled value
     */
    public boolean enabled() {
        return this.enabled;
    }

    /**
     * Set the enabled value.
     *
     * @param enabled the enabled value to set
     * @return the MetricSettings object itself.
     */
    public MetricSettingsInner withEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    /**
     * Get the retentionPolicy value.
     *
     * @return the retentionPolicy value
     */
    public RetentionPolicy retentionPolicy() {
        return this.retentionPolicy;
    }

    /**
     * Set the retentionPolicy value.
     *
     * @param retentionPolicy the retentionPolicy value to set
     * @return the MetricSettings object itself.
     */
    public MetricSettingsInner withRetentionPolicy(RetentionPolicy retentionPolicy) {
        this.retentionPolicy = retentionPolicy;
        return this;
    }

}
