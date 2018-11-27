/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.sql.implementation.MetricInner;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Response containing the Azure SQL Database metric.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_7_0)
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
