/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.sql;

import com.microsoft.rest.v2.annotations.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.v2.management.sql.implementation.MetricInner;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Response containing the Azure SQL Database metric.
 */
@Fluent
@Beta(since = "V1_7_0")
public interface SqlDatabaseMetric extends HasInner<MetricInner> {

    /**
     * @return the metric name
     */
    String name();

    /**
     * @return the start time
     */
    DateTime startTime();

    /**
     * @return the end time
     */
    DateTime endTime();

    /**
     * @return the time grain
     */
    String timeGrain();

    /**
     * @return the metric's unit type
     */
    UnitType unit();

    /**
     * @return the metric values
     */
    List<SqlDatabaseMetricValue> metricValues();
}