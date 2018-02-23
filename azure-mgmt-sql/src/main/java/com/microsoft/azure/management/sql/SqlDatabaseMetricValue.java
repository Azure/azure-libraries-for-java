/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import org.joda.time.DateTime;

/**
 * Response containing the Azure SQL Database metric value.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_7_0)
public interface SqlDatabaseMetricValue extends HasInner<MetricValue> {
    /**
     * @return  the number of values for the metric
     */
    double count();

    /**
     * @return the average value of the metric
     */
    double average();

    /**
     * @return the max value of the metric
     */
    double maximum();

    /**
     * @return the min value of the metric
     */
    double minimum();

    /**
     * @return the metric timestamp (ISO-8601 format)
     */
    DateTime timestamp();

    /**
     * @return the total value of the metric
     */
    double total();
}
