/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.monitor.AutoscaleProfile;
import com.microsoft.azure.management.monitor.ComparisonOperationType;
import com.microsoft.azure.management.monitor.DayOfWeek;
import com.microsoft.azure.management.monitor.MetricDimension;
import com.microsoft.azure.management.monitor.MetricStatisticType;
import com.microsoft.azure.management.monitor.ScaleCapacity;
import com.microsoft.azure.management.monitor.ScaleDirection;
import com.microsoft.azure.management.monitor.ScaleRule;
import com.microsoft.azure.management.monitor.ScaleType;
import com.microsoft.azure.management.monitor.TimeAggregationType;
import com.microsoft.azure.management.monitor.TimeWindow;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.List;
import java.util.TreeMap;

/**
 * Implementation for ScaleRule.
 */
@LangDefinition
class ScaleRuleImpl
        extends WrapperImpl<ScaleRuleInner>
        implements
            ScaleRule,
            ScaleRule.Definition,
            ScaleRule.ParentUpdateDefinition,
            ScaleRule.UpdateDefinition,
            ScaleRule.Update {

    private final AutoscaleProfileImpl parent;

    ScaleRuleImpl(String name, ScaleRuleInner innerObject, AutoscaleProfileImpl parent) {
        super(innerObject);
        this.parent = parent;
    }

    @Override
    public AutoscaleProfileImpl parent() {
        return null;
    }

    @Override
    public AutoscaleProfileImpl attach() {
        return null;
    }

    @Override
    public ScaleRuleImpl withMetricSource(String metricSourceResourceId) {
        return null;
    }

    @Override
    public ScaleRuleImpl withMetricName(String metricName) {
        return null;
    }

    @Override
    public ScaleRuleImpl withMetricName(String metricName, String metricNamespace) {
        return null;
    }

    @Override
    public ScaleRuleImpl withStatistic(Period duration, Period frequency, MetricStatisticType statisticType) {
        return null;
    }

    @Override
    public ScaleRuleImpl withCondition(ComparisonOperationType condition, TimeAggregationType timeAggregation, double threshold) {
        return null;
    }

    @Override
    public ScaleRuleImpl withScaleAction(ScaleDirection direction, ScaleType type, int instanceCountChange, Period cooldown) {
        return null;
    }
}

