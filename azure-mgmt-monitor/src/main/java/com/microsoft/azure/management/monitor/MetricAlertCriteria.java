/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.monitor;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonSubTypes;

/**
 * The rule criteria that defines the conditions of the alert rule.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "odata\\.type")
@JsonTypeName("MetricAlertCriteria")
@JsonSubTypes({
    @JsonSubTypes.Type(name = "Microsoft.Azure.Monitor.SingleResourceMultipleMetricCriteria", value = MetricAlertSingleResourceMultipleMetricCriteria.class),
    @JsonSubTypes.Type(name = "Microsoft.Azure.Monitor.MultipleResourceMultipleMetricCriteria", value = MetricAlertMultipleResourceMultipleMetricCriteria.class)
})
public class MetricAlertCriteria {
    /**
     * Unmatched properties from the message are deserialized this collection.
     */
    @JsonProperty(value = "")
    private Map<String, Object> additionalProperties;

    /**
     * Get unmatched properties from the message are deserialized this collection.
     *
     * @return the additionalProperties value
     */
    public Map<String, Object> additionalProperties() {
        return this.additionalProperties;
    }

    /**
     * Set unmatched properties from the message are deserialized this collection.
     *
     * @param additionalProperties the additionalProperties value to set
     * @return the MetricAlertCriteria object itself.
     */
    public MetricAlertCriteria withAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
        return this;
    }

}
