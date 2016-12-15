/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import org.joda.time.DateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents Azure SQL recommended elastic pool metric.
 */
public class RecommendedElasticPoolMetricInner {
    /**
     * The time of metric (ISO8601 format).
     */
    @JsonProperty(value = "dateTime")
    private DateTime dateTimeProperty;

    /**
     * Gets or sets the DTUs (Database Transaction Units). See
     * https://azure.microsoft.com/documentation/articles/sql-database-what-is-a-dtu/.
     */
    private Double dtu;

    /**
     * Gets or sets size in gigabytes.
     */
    private Double sizeGB;

    /**
     * Get the dateTimeProperty value.
     *
     * @return the dateTimeProperty value
     */
    public DateTime dateTimeProperty() {
        return this.dateTimeProperty;
    }

    /**
     * Set the dateTimeProperty value.
     *
     * @param dateTimeProperty the dateTimeProperty value to set
     * @return the RecommendedElasticPoolMetricInner object itself.
     */
    public RecommendedElasticPoolMetricInner withDateTimeProperty(DateTime dateTimeProperty) {
        this.dateTimeProperty = dateTimeProperty;
        return this;
    }

    /**
     * Get the dtu value.
     *
     * @return the dtu value
     */
    public Double dtu() {
        return this.dtu;
    }

    /**
     * Set the dtu value.
     *
     * @param dtu the dtu value to set
     * @return the RecommendedElasticPoolMetricInner object itself.
     */
    public RecommendedElasticPoolMetricInner withDtu(Double dtu) {
        this.dtu = dtu;
        return this;
    }

    /**
     * Get the sizeGB value.
     *
     * @return the sizeGB value
     */
    public Double sizeGB() {
        return this.sizeGB;
    }

    /**
     * Set the sizeGB value.
     *
     * @param sizeGB the sizeGB value to set
     * @return the RecommendedElasticPoolMetricInner object itself.
     */
    public RecommendedElasticPoolMetricInner withSizeGB(Double sizeGB) {
        this.sizeGB = sizeGB;
        return this;
    }

}
