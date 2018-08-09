/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasParent;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

import java.util.Collection;
import java.util.List;

/**
 * An immutable client-side representation of an Azure metric alert criteria.
 */
@Fluent
public interface MetricAlertCondition extends
        HasInner<MetricCriteria>,
        HasParent<MetricAlert> {
    /**
     * Get name of the criteria.
     *
     * @return the name value
     */
    String name();

    /**
     * Get name of the metric.
     *
     * @return the metricName value
     */
    String signalName();

    /**
     * Get namespace of the metric.
     *
     * @return the metricNamespace value
     */
    String metricNamespace();

    /**
     * Get the criteria operator. Possible values include: 'Equals', 'NotEquals', 'GreaterThan', 'GreaterThanOrEqual', 'LessThan', 'LessThanOrEqual'.
     *
     * @return the operator value
     */
    MetricAlertRuleCondition condition();

    /**
     * Get the criteria time aggregation types. Possible values include: 'Average', 'Minimum', 'Maximum', 'Total'.
     *
     * @return the timeAggregation value
     */
    MetricAlertRuleTimeAggregation timeAggregation();

    /**
     * Get the criteria threshold value that activates the alert.
     *
     * @return the threshold value
     */
    double threshold();

    /**
     * Get list of dimension conditions.
     *
     * @return the dimensions value
     */
    Collection<MetricDimension> dimensions();

    interface DefinitionStages {
        interface Blank {
            interface MetricName<ParentT> {
                WithCriteriaOperator<ParentT> withSignalName(String metricName);
            }

        }

        interface WithCriteriaOperator<ParentT> {
            WithConditionAttach<ParentT> withCondition(MetricAlertRuleCondition condition, MetricAlertRuleTimeAggregation timeAggregation, double threshold);
        }

        interface WithConditionAttach<ParentT> {

            WithConditionAttach<ParentT> withMetricNamespace(String metricNamespace);

            WithConditionAttach<ParentT> withDimensionFilter(String dimensionName, String... values);

            @Method
            ParentT attach();
        }
    }

    interface UpdateDefinitionStages {
        interface Blank {
            interface MetricName<ParentT> {
                WithCriteriaOperator<ParentT> withSignalName(String metricName);
            }
        }

        interface WithCriteriaOperator<ParentT> {
            WithConditionAttach<ParentT> withCondition(MetricAlertRuleCondition operator, MetricAlertRuleTimeAggregation timeAggregation, double threshold);
        }

        interface WithConditionAttach<ParentT> {

            WithConditionAttach<ParentT> withMetricNamespace(String metricNamespace);

            WithConditionAttach<ParentT> withDimensionFilter(String dimensionName, String... values);

            @Method
            ParentT attach();
        }
    }

    interface UpdateStages {

        UpdateStages withCondition(MetricAlertRuleCondition operator, MetricAlertRuleTimeAggregation timeAggregation, double threshold);

        UpdateStages withMetricNamespace(String metricNamespace);

        UpdateStages withoutMetricNamespace();

        UpdateStages withDimensionFilter(String dimensionName, String... values);

        UpdateStages withoutDimensionFilter(String name);

        @Method
        MetricAlert.Update parent();
    }
}