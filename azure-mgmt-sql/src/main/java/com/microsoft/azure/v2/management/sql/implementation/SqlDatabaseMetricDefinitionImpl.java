/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.WrapperImpl;
import com.microsoft.azure.v2.management.sql.MetricAvailability;
import com.microsoft.azure.v2.management.sql.PrimaryAggregationType;
import com.microsoft.azure.v2.management.sql.SqlDatabaseMetricAvailability;
import com.microsoft.azure.v2.management.sql.SqlDatabaseMetricDefinition;
import com.microsoft.azure.v2.management.sql.UnitDefinitionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Response containing the SQL database metric definitions.
 */
@LangDefinition
public class SqlDatabaseMetricDefinitionImpl extends WrapperImpl<MetricDefinitionInner> implements SqlDatabaseMetricDefinition {
    protected SqlDatabaseMetricDefinitionImpl(MetricDefinitionInner innerObject) {
        super(innerObject);
    }

    @Override
    public String name() {
        return this.inner().name().value();
    }

    @Override
    public PrimaryAggregationType primaryAggregationType() {
        return this.inner().primaryAggregationType();
    }

    @Override
    public String resourceUri() {
        return this.inner().resourceUri();
    }

    @Override
    public UnitDefinitionType unit() {
        return this.inner().unit();
    }

    @Override
    public List<SqlDatabaseMetricAvailability> metricAvailabilities() {
        List<SqlDatabaseMetricAvailability> sqlDatabaseMetricAvailabilities = new ArrayList<>();
        if (this.inner().metricAvailabilities() != null) {
            for (MetricAvailability metricAvailability : this.inner().metricAvailabilities()) {
                sqlDatabaseMetricAvailabilities.add(new SqlDatabaseMetricAvailabilityImpl(metricAvailability));
            }
        }
        return Collections.unmodifiableList(sqlDatabaseMetricAvailabilities);
    }
}
