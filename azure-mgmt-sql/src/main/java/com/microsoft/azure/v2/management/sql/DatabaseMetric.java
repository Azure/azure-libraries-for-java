/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.sql;

import com.microsoft.rest.v2.annotations.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import org.joda.time.DateTime;


/**
 * An immutable client-side representation of an Azure SQL DatabaseMetric.
 */
@Fluent
@Beta(since = "V1_7_0")
public interface DatabaseMetric {

    /**
     * @return the name of the resource
     */
    String resourceName();

    /**
     * @return the metric display name
     */
    String displayName();

    /**
     * @return the current value of the metric
     */
    double currentValue();

    /**
     * @return the current limit of the metric
     */
    double limit();

    /**
     * @return the units of the metric
     */
    String unit();

    /**
     * @return the next reset time for the metric (ISO8601 format)
     */
    DateTime nextResetTime();
}
