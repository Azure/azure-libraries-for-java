/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.gallery;

import org.joda.time.Period;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * A rule condition based on a metric crossing a threshold.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "odata.type")
@JsonTypeName("Microsoft.Azure.Management.Insights.Models.ThresholdRuleCondition")
public class ThresholdRuleCondition extends RuleCondition {
    /**
     * the resource from which the rule collects its data. For this type
     * dataSource will always be of type RuleMetricDataSource.
     */
    @JsonProperty(value = "dataSource")
    private RuleDataSource dataSource;

    /**
     * the operator used to compare the data and the threshold. Possible values
     * include: 'GreaterThan', 'GreaterThanOrEqual', 'LessThan',
     * 'LessThanOrEqual'.
     */
    @JsonProperty(value = "operator", required = true)
    private ConditionOperator operator;

    /**
     * the threshold value that activates the alert.
     */
    @JsonProperty(value = "threshold", required = true)
    private double threshold;

    /**
     * the period of time (in ISO 8601 duration format) that is used to monitor
     * alert activity based on the threshold. If specified then it must be
     * between 5 minutes and 1 day.
     */
    @JsonProperty(value = "windowSize")
    private Period windowSize;

    /**
     * the time aggregation operator. How the data that are collected should be
     * combined over time. The default value is the PrimaryAggregationType of
     * the Metric. Possible values include: 'Average', 'Minimum', 'Maximum',
     * 'Total', 'Last'.
     */
    @JsonProperty(value = "timeAggregation")
    private TimeAggregationOperator timeAggregation;

    /**
     * Get the dataSource value.
     *
     * @return the dataSource value
     */
    public RuleDataSource dataSource() {
        return this.dataSource;
    }

    /**
     * Set the dataSource value.
     *
     * @param dataSource the dataSource value to set
     * @return the ThresholdRuleCondition object itself.
     */
    public ThresholdRuleCondition withDataSource(RuleDataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    /**
     * Get the operator value.
     *
     * @return the operator value
     */
    public ConditionOperator operator() {
        return this.operator;
    }

    /**
     * Set the operator value.
     *
     * @param operator the operator value to set
     * @return the ThresholdRuleCondition object itself.
     */
    public ThresholdRuleCondition withOperator(ConditionOperator operator) {
        this.operator = operator;
        return this;
    }

    /**
     * Get the threshold value.
     *
     * @return the threshold value
     */
    public double threshold() {
        return this.threshold;
    }

    /**
     * Set the threshold value.
     *
     * @param threshold the threshold value to set
     * @return the ThresholdRuleCondition object itself.
     */
    public ThresholdRuleCondition withThreshold(double threshold) {
        this.threshold = threshold;
        return this;
    }

    /**
     * Get the windowSize value.
     *
     * @return the windowSize value
     */
    public Period windowSize() {
        return this.windowSize;
    }

    /**
     * Set the windowSize value.
     *
     * @param windowSize the windowSize value to set
     * @return the ThresholdRuleCondition object itself.
     */
    public ThresholdRuleCondition withWindowSize(Period windowSize) {
        this.windowSize = windowSize;
        return this;
    }

    /**
     * Get the timeAggregation value.
     *
     * @return the timeAggregation value
     */
    public TimeAggregationOperator timeAggregation() {
        return this.timeAggregation;
    }

    /**
     * Set the timeAggregation value.
     *
     * @param timeAggregation the timeAggregation value to set
     * @return the ThresholdRuleCondition object itself.
     */
    public ThresholdRuleCondition withTimeAggregation(TimeAggregationOperator timeAggregation) {
        this.timeAggregation = timeAggregation;
        return this;
    }

}
