/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.monitor;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Specifies the metric alert criteria for a single resource that has multiple
 * metric criteria.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "odata.type")
@JsonTypeName("Microsoft.Azure.Monitor.SingleResourceMultipleMetricCriteria")
public class MetricAlertSingleResourceMultipleMetricCriteria extends MetricAlertCriteria {
    /**
     * The list of metric criteria for this 'all of' operation.
     */
    @JsonProperty(value = "allOf")
    private List<MetricCriteria> allOf;

    /**
     * Get the allOf value.
     *
     * @return the allOf value
     */
    public List<MetricCriteria> allOf() {
        return this.allOf;
    }

    /**
     * Set the allOf value.
     *
     * @param allOf the allOf value to set
     * @return the MetricAlertSingleResourceMultipleMetricCriteria object itself.
     */
    public MetricAlertSingleResourceMultipleMetricCriteria withAllOf(List<MetricCriteria> allOf) {
        this.allOf = allOf;
        return this;
    }

}
