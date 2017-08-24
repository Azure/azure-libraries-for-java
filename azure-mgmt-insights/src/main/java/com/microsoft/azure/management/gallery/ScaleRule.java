/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.gallery;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A rule that provide the triggers and parameters for the scaling action.
 */
public class ScaleRule {
    /**
     * the trigger that results in a scaling action.
     */
    @JsonProperty(value = "metricTrigger", required = true)
    private MetricTrigger metricTrigger;

    /**
     * the parameters for the scaling action.
     */
    @JsonProperty(value = "scaleAction", required = true)
    private ScaleAction scaleAction;

    /**
     * Get the metricTrigger value.
     *
     * @return the metricTrigger value
     */
    public MetricTrigger metricTrigger() {
        return this.metricTrigger;
    }

    /**
     * Set the metricTrigger value.
     *
     * @param metricTrigger the metricTrigger value to set
     * @return the ScaleRule object itself.
     */
    public ScaleRule withMetricTrigger(MetricTrigger metricTrigger) {
        this.metricTrigger = metricTrigger;
        return this;
    }

    /**
     * Get the scaleAction value.
     *
     * @return the scaleAction value
     */
    public ScaleAction scaleAction() {
        return this.scaleAction;
    }

    /**
     * Set the scaleAction value.
     *
     * @param scaleAction the scaleAction value to set
     * @return the ScaleRule object itself.
     */
    public ScaleRule withScaleAction(ScaleAction scaleAction) {
        this.scaleAction = scaleAction;
        return this;
    }

}
