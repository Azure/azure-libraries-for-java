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
 * Specify action need to be taken when rule type is converting log to metric.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "odata\\.type")
@JsonTypeName("Microsoft.WindowsAzure.Management.Monitoring.Alerts.Models.Microsoft.AppInsights.Nexus.DataContracts.Resources.ScheduledQueryRules.LogToMetricAction")
public class LogToMetricAction extends Action {
    /**
     * Criteria of Metric.
     */
    @JsonProperty(value = "criteria", required = true)
    private List<Criteria> criteria;

    /**
     * Get criteria of Metric.
     *
     * @return the criteria value
     */
    public List<Criteria> criteria() {
        return this.criteria;
    }

    /**
     * Set criteria of Metric.
     *
     * @param criteria the criteria value to set
     * @return the LogToMetricAction object itself.
     */
    public LogToMetricAction withCriteria(List<Criteria> criteria) {
        this.criteria = criteria;
        return this;
    }

}
