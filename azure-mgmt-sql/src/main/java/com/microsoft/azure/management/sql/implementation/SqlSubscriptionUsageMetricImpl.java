/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.RefreshableWrapperImpl;
import com.microsoft.azure.management.sql.SqlSubscriptionUsageMetric;
import rx.Observable;

import java.util.Objects;

/**
 * Implementation for Azure SQL subscription usage.
 */
@LangDefinition
public class SqlSubscriptionUsageMetricImpl
    extends RefreshableWrapperImpl<SubscriptionUsageInner, SqlSubscriptionUsageMetric>
    implements SqlSubscriptionUsageMetric {

    private final SqlServerManager sqlServerManager;
    private final String location;

    protected SqlSubscriptionUsageMetricImpl(String location, SubscriptionUsageInner innerObject, SqlServerManager sqlServerManager) {
        super(innerObject);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        this.location = location;
    }

    @Override
    protected Observable<SubscriptionUsageInner> getInnerAsync() {
        return this.sqlServerManager.inner().subscriptionUsages()
            .getAsync(this.location, this.name());
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public String displayName() {
        return this.inner().displayName();
    }

    @Override
    public double currentValue() {
        return this.inner().currentValue() != null ? this.inner().currentValue() : 0;
    }

    @Override
    public double limit() {
        return this.inner().limit() != null ? this.inner().limit() : 0;
    }

    @Override
    public String unit() {
        return this.inner().unit();
    }

    @Override
    public String type() {
        return this.inner().type();
    }
}
