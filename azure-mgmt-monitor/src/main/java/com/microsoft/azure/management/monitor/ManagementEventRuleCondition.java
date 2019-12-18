/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.monitor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;

/**
 * A management event rule condition.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "odata.type")
@JsonTypeName("Microsoft.Azure.Management.Insights.Models.ManagementEventRuleCondition")
@JsonTypeResolver(OdataTypeDiscriminatorTypeResolver.class)
public class ManagementEventRuleCondition extends RuleCondition {
    /**
     * How the data that is collected should be combined over time and when the
     * alert is activated. Note that for management event alerts aggregation is
     * optional – if it is not provided then any event will cause the alert to
     * activate.
     */
    @JsonProperty(value = "aggregation")
    private ManagementEventAggregationCondition aggregation;

    /**
     * Get how the data that is collected should be combined over time and when the alert is activated. Note that for management event alerts aggregation is optional – if it is not provided then any event will cause the alert to activate.
     *
     * @return the aggregation value
     */
    public ManagementEventAggregationCondition aggregation() {
        return this.aggregation;
    }

    /**
     * Set how the data that is collected should be combined over time and when the alert is activated. Note that for management event alerts aggregation is optional – if it is not provided then any event will cause the alert to activate.
     *
     * @param aggregation the aggregation value to set
     * @return the ManagementEventRuleCondition object itself.
     */
    public ManagementEventRuleCondition withAggregation(ManagementEventAggregationCondition aggregation) {
        this.aggregation = aggregation;
        return this;
    }

}
