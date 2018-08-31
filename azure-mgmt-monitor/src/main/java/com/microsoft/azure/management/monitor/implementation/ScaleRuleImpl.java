/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.monitor.ComparisonOperationType;
import com.microsoft.azure.management.monitor.MetricStatisticType;
import com.microsoft.azure.management.monitor.ScaleDirection;
import com.microsoft.azure.management.monitor.ScaleRule;
import com.microsoft.azure.management.monitor.ScaleType;
import com.microsoft.azure.management.monitor.TimeAggregationType;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import org.joda.time.Period;

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

    ScaleRuleImpl(ScaleRuleInner innerObject, AutoscaleProfileImpl parent) {
        super(innerObject);
        this.parent = parent;
        if(this.inner().metricTrigger() == null) {
            this.inner().withMetricTrigger(new MetricTriggerInner());
        }
        if(this.inner().scaleAction() == null) {
            this.inner().withScaleAction(new ScaleActionInner());
        }
    }

    @Override
    public AutoscaleProfileImpl parent() {
        // end of update
        return this.parent;
    }

    @Override
    public AutoscaleProfileImpl attach() {
        return this.parent.addNewScaleRule(this);
    }

    @Override
    public ScaleRuleImpl withMetricSource(String metricSourceResourceId) {
        this.inner().metricTrigger().withMetricResourceUri(metricSourceResourceId);
        return this;
    }

    @Override
    public ScaleRuleImpl withMetricName(String metricName) {
        this.inner().metricTrigger().withMetricName(metricName);
        return this;
    }

    @Override
    public ScaleRuleImpl withStatistic(Period duration, Period frequency, MetricStatisticType statisticType) {
        this.inner().metricTrigger().withStatistic(statisticType);
        this.inner().metricTrigger().withTimeWindow(duration);
        this.inner().metricTrigger().withTimeGrain(frequency);
        return this;
    }

    @Override
    public ScaleRuleImpl withCondition(ComparisonOperationType condition, TimeAggregationType timeAggregation, double threshold) {
        this.inner().metricTrigger().withOperator(condition);
        this.inner().metricTrigger().withTimeAggregation(timeAggregation);
        this.inner().metricTrigger().withThreshold(threshold);
        return this;
    }

    @Override
    public ScaleRuleImpl withScaleAction(ScaleDirection direction, ScaleType type, int instanceCountChange, Period cooldown) {
        this.inner().scaleAction().withDirection(direction);
        this.inner().scaleAction().withType(type);
        this.inner().scaleAction().withValue(Integer.toString(instanceCountChange));
        this.inner().scaleAction().withCooldown(cooldown);
        return this;
    }
}

