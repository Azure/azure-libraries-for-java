/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import com.microsoft.azure.management.sql.MetricAvailability;
import com.microsoft.azure.management.sql.SqlDatabaseMetricAvailability;

/**
 * Response containing the SQL database metric availability.
 */
@LangDefinition
public class SqlDatabaseMetricAvailabilityImpl extends WrapperImpl<MetricAvailability> implements SqlDatabaseMetricAvailability {
    protected SqlDatabaseMetricAvailabilityImpl(MetricAvailability innerObject) {
        super(innerObject);
    }

    @Override
    public String retention() {
        return this.inner().retention();
    }

    @Override
    public String timeGrain() {
        return this.inner().timeGrain();
    }
}
