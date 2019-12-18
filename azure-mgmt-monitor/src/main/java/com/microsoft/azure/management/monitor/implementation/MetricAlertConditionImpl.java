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

import java.util.ArrayList;

/**
 * Implementation for MetricAlertCondition.
 */
@LangDefinition
class MetricAlertConditionImpl
        extends MetricAlertConditionBaseImpl<MetricCriteria, MetricAlertConditionImpl>
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

    MetricAlertConditionImpl(String name, MetricCriteria innerObject, MetricAlertImpl parent) {
        super(name, innerObject, parent);
    }

    @Override
    public MetricAlertConditionImpl withCondition(MetricAlertRuleTimeAggregation timeAggregation, MetricAlertRuleCondition condition, double threshold) {
        this.inner().withOperator(condition);
        this.inner().withTimeAggregation(timeAggregation);
        this.inner().withThreshold(threshold);
        return this;
    }

    @Override
    public MetricAlertImpl attach() {
        this.inner().withDimensions(new ArrayList<>(this.dimensions.values()));
        return this.parent().withAlertCriteria(this);
    }

    @Override
    public MetricAlertRuleCondition condition() {
        return MetricAlertRuleCondition.fromString(this.inner().operator().toString());
    }

    @Override
    public double threshold() {
        return this.inner().threshold();
    }
}

