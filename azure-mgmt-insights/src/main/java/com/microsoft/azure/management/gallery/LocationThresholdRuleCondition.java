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
 * A rule condition based on a certain number of locations failing.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "odata.type")
@JsonTypeName("Microsoft.Azure.Management.Insights.Models.LocationThresholdRuleCondition")
public class LocationThresholdRuleCondition extends RuleCondition {
    /**
     * the resource from which the rule collects its data. For this type
     * dataSource will always be of type RuleMetricDataSource.
     */
    @JsonProperty(value = "dataSource")
    private RuleDataSource dataSource;

    /**
     * the period of time (in ISO 8601 duration format) that is used to monitor
     * alert activity based on the threshold. If specified then it must be
     * between 5 minutes and 1 day.
     */
    @JsonProperty(value = "windowSize")
    private Period windowSize;

    /**
     * the number of locations that must fail to activate the alert.
     */
    @JsonProperty(value = "failedLocationCount", required = true)
    private int failedLocationCount;

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
     * @return the LocationThresholdRuleCondition object itself.
     */
    public LocationThresholdRuleCondition withDataSource(RuleDataSource dataSource) {
        this.dataSource = dataSource;
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
     * @return the LocationThresholdRuleCondition object itself.
     */
    public LocationThresholdRuleCondition withWindowSize(Period windowSize) {
        this.windowSize = windowSize;
        return this;
    }

    /**
     * Get the failedLocationCount value.
     *
     * @return the failedLocationCount value
     */
    public int failedLocationCount() {
        return this.failedLocationCount;
    }

    /**
     * Set the failedLocationCount value.
     *
     * @param failedLocationCount the failedLocationCount value to set
     * @return the LocationThresholdRuleCondition object itself.
     */
    public LocationThresholdRuleCondition withFailedLocationCount(int failedLocationCount) {
        this.failedLocationCount = failedLocationCount;
        return this;
    }

}
