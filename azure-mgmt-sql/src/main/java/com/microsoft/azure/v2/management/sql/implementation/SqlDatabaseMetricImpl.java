/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.WrapperImpl;
import com.microsoft.azure.v2.management.sql.MetricValue;
import com.microsoft.azure.v2.management.sql.SqlDatabaseMetric;
import com.microsoft.azure.v2.management.sql.SqlDatabaseMetricValue;
import com.microsoft.azure.v2.management.sql.UnitType;
import java.time.OffsetDateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Response containing the SQL database metrics.
 */
@LangDefinition
public class SqlDatabaseMetricImpl extends WrapperImpl<MetricInner> implements SqlDatabaseMetric {
    protected SqlDatabaseMetricImpl(MetricInner innerObject) {
        super(innerObject);
    }

    @Override
    public String name() {
        return this.inner().name().value();
    }

    @Override
    public DateTime startTime() {
        return this.inner().startTime();
    }

    @Override
    public DateTime endTime() {
        return this.inner().endTime();
    }

    @Override
    public String timeGrain() {
        return this.inner().timeGrain();
    }

    @Override
    public UnitType unit() {
        return this.inner().unit();
    }

    @Override
    public List<SqlDatabaseMetricValue> metricValues() {
        List<SqlDatabaseMetricValue> sqlDatabaseMetricValues = new ArrayList<>();
        if (this.inner().metricValues() != null) {
            for (MetricValue metricValue : this.inner().metricValues()) {
                sqlDatabaseMetricValues.add(new SqlDatabaseMetricValueImpl(metricValue));
            }
        }
        return Collections.unmodifiableList(sqlDatabaseMetricValues);
    }
}
