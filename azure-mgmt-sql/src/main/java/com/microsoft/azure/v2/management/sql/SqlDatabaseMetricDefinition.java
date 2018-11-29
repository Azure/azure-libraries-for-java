/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.sql;

import com.microsoft.rest.v2.annotations.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.v2.management.sql.implementation.MetricDefinitionInner;

import java.util.List;

/**
 * Response containing the Azure SQL Database metric definition.
 */
@Fluent
@Beta(since = "V1_7_0")
public interface SqlDatabaseMetricDefinition extends HasInner<MetricDefinitionInner> {
    /**
     * @return the name of the metric
     */
    String name();

    /**
     * @return the primary aggregation type
     */
    PrimaryAggregationType primaryAggregationType();

    /**
     * @return the resource URI
     */
    String resourceUri();

    /**
     * @return the unit type
     */
    UnitDefinitionType unit();

    /**
     * @return the metric availabilities
     */
    List<SqlDatabaseMetricAvailability> metricAvailabilities();
}
