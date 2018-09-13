/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.monitor.MetricAlert;
import com.microsoft.azure.management.monitor.MetricAlertCondition;
import com.microsoft.azure.management.monitor.MetricAlertRuleCondition;
import com.microsoft.azure.management.monitor.MetricAlertRuleTimeAggregation;
import com.microsoft.azure.management.monitor.MetricCriteria;
import com.microsoft.azure.management.monitor.MetricDimension;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.TreeMap;

/**
 * Implementation for MetricAlertCondition.
 */
@LangDefinition
class MetricAlertConditionImpl
        extends WrapperImpl<MetricCriteria>
        implements
            MetricAlertCondition,

            MetricAlertCondition.DefinitionStages,
            MetricAlertCondition.DefinitionStages.Blank.MetricName<MetricAlert.DefinitionStages.WithCreate>,
            MetricAlertCondition.DefinitionStages.WithCriteriaOperator<MetricAlert.DefinitionStages.WithCreate>,
            MetricAlertCondition.DefinitionStages.WithConditionAttach<MetricAlert.DefinitionStages.WithCreate>,

            MetricAlertCondition.UpdateDefinitionStages,
            MetricAlertCondition.UpdateDefinitionStages.Blank.MetricName<MetricAlert.Update>,
            MetricAlertCondition.UpdateDefinitionStages.WithCriteriaOperator<MetricAlert.Update>,
            MetricAlertCondition.UpdateDefinitionStages.WithConditionAttach<MetricAlert.Update>,

            MetricAlertCondition.UpdateStages {
    private final MetricAlertImpl parent;
    private TreeMap<String, MetricDimension> dimensions;

    MetricAlertConditionImpl(String name, MetricCriteria innerObject, MetricAlertImpl parent) {
        super(innerObject);
        this.inner().withName(name);
        this.parent = parent;
        this.dimensions = new TreeMap<>();
        if (this.inner().dimensions() != null) {
            for (MetricDimension md : this.inner().dimensions()) {
                dimensions.put(md.name(), md);
            }
        }
    }

    @Override
    public MetricAlertConditionImpl withCondition(MetricAlertRuleTimeAggregation timeAggregation, MetricAlertRuleCondition condition, double threshold) {
        this.inner().withOperator(condition);
        this.inner().withTimeAggregation(timeAggregation);
        this.inner().withThreshold(threshold);
        return this;
    }

    @Override
    public MetricAlertConditionImpl withMetricName(String metricName, String metricNamespace) {
        this.inner().withMetricNamespace(metricNamespace);
        return this.withMetricName(metricName);
    }

    @Override
    public MetricAlertConditionImpl withMetricName(String metricName) {
        this.inner().withMetricName(metricName);
        return this;
    }

    @Override
    public MetricAlertConditionImpl withDimension(String dimensionName, String... values) {
        if (this.dimensions.containsKey(dimensionName)) {
            dimensions.remove(dimensionName);
        }
        MetricDimension md = new MetricDimension();
        md.withName(dimensionName);
        md.withOperator("Include");
        md.withValues(Arrays.asList(values));
        dimensions.put(dimensionName, md);
        return this;
    }

    @Override
    public MetricAlertConditionImpl withoutDimension(String dimensionName) {
        if (this.dimensions.containsKey(dimensionName)) {
            dimensions.remove(dimensionName);
        }
        return this;
    }

    @Override
    public MetricAlertImpl parent() {
        this.inner().withDimensions(new ArrayList(this.dimensions.values()));
        return this.parent;
    }

    @Override
    public MetricAlertImpl attach() {
        this.inner().withDimensions(new ArrayList(this.dimensions.values()));
        return this.parent().withAlertCriteria(this);
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public String metricName() {
        return this.inner().metricName();
    }

    @Override
    public String metricNamespace() {
        return this.inner().metricNamespace();
    }

    @Override
    public MetricAlertRuleCondition condition() {
        return this.inner().operator();
    }

    @Override
    public MetricAlertRuleTimeAggregation timeAggregation() {
        return this.inner().timeAggregation();
    }

    @Override
    public double threshold() {
        return this.inner().threshold();
    }

    @Override
    public Collection<MetricDimension> dimensions() {
        return this.dimensions.values();
    }
}

