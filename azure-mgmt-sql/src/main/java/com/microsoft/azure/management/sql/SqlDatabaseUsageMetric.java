/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.sql.implementation.DatabaseUsageInner;
import org.joda.time.DateTime;

/**
 * The result of SQL server usages per SQL Database.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_8_0)
public interface SqlDatabaseUsageMetric extends
    HasName,
    HasInner<DatabaseUsageInner> {

    /**
     * @return the name of the SQL Database resource
     */
    String resourceName();

    /**
     * @return a user-readable name of the metric
     */
    String displayName();

    /**
     * @return the current value of the metric
     */
    double currentValue();

    /**
     * @return the boundary value of the metric
     */
    double limit();

    /**
     * @return the unit of the metric
     */
    String unit();

    /**
     * @return the next reset time for the usage metric (ISO8601 format)
     */
    DateTime nextResetTime();
}
