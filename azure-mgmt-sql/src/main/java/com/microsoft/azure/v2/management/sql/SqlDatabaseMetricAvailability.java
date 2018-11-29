/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.sql;

import com.microsoft.rest.v2.annotations.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;

/**
 * Response containing the Azure SQL Database metric availability.
 */
@Fluent
@Beta(since = "V1_7_0")
public interface SqlDatabaseMetricAvailability extends HasInner<MetricAvailability> {
    /**
     * @return the length of retention for the database metric
     */
    String retention();

    /**
     * @return the granularity of the database metric
     */
    String timeGrain();
}
