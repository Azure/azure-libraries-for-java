/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import com.microsoft.azure.management.sql.MetricValue;
import com.microsoft.azure.management.sql.SqlDatabaseMetricValue;
import org.joda.time.DateTime;

/**
 * Implementation for SqlDatabaseMetricValue.
 */
@LangDefinition
public class SqlDatabaseMetricValueImpl extends WrapperImpl<MetricValue>
    implements SqlDatabaseMetricValue {
    protected SqlDatabaseMetricValueImpl(MetricValue innerObject) {
        super(innerObject);
    }

    @Override
    public double count() {
        return this.inner().count();
    }

    @Override
    public double average() {
        return this.inner().average();
    }

    @Override
    public double maximum() {
        return this.inner().maximum();
    }

    @Override
    public double minimum() {
        return this.inner().minimum();
    }

    @Override
    public DateTime timestamp() {
        return this.inner().timestamp();
    }

    @Override
    public double total() {
        return this.inner().total();
    }
}
