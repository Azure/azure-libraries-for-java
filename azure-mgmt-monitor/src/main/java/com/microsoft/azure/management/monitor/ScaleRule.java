/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.monitor.implementation.ScaleRuleInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasParent;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Settable;
import org.joda.time.Period;

/**
 */
@Fluent
public interface ScaleRule extends
        HasInner<ScaleRuleInner>,
        HasParent<AutoscaleProfile> {

    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.WithMetricName,
            DefinitionStages.WithStatistic,
            DefinitionStages.WithCondition,
            DefinitionStages.WithScaleAction,
            DefinitionStages.WithAttach {
    }

    interface DefinitionStages {
        interface Blank {
            WithMetricName withMetricSource(String metricSourceResourceId);
        }

        interface WithMetricName {
            WithStatistic withMetricName(String metricName);
        }

        interface WithStatistic {
            WithCondition withStatistic(Period duration, Period frequency, MetricStatisticType statisticType);
        }

        interface WithCondition {
            WithScaleAction withCondition(ComparisonOperationType condition, TimeAggregationType timeAggregation, double threshold);
        }

        interface WithScaleAction
        {
            WithAttach withScaleAction(ScaleDirection direction, ScaleType type, int instanceCountChange, Period cooldown);
        }

        interface WithAttach extends
                Attachable.InDefinition<AutoscaleProfile.DefinitionStages.WithScaleRuleOptional>{
        }
    }

    interface ParentUpdateDefinition extends
            ParentUpdateDefinitionStages.Blank,
            ParentUpdateDefinitionStages.WithMetricName,
            ParentUpdateDefinitionStages.WithStatistic,
            ParentUpdateDefinitionStages.WithCondition,
            ParentUpdateDefinitionStages.WithScaleAction,
            ParentUpdateDefinitionStages.WithAttach {
    }

    interface ParentUpdateDefinitionStages {
        interface Blank {
            WithMetricName withMetricSource(String metricSourceResourceId);
        }

        interface WithMetricName {
            WithStatistic withMetricName(String metricName);
        }

        interface WithStatistic {
            WithCondition withStatistic(Period duration, Period frequency, MetricStatisticType statisticType);
        }

        interface WithCondition {
            WithScaleAction withCondition(ComparisonOperationType condition, TimeAggregationType timeAggregation, double threshold);
        }

        interface WithScaleAction
        {
            WithAttach withScaleAction(ScaleDirection direction, ScaleType type, int instanceCountChange, Period cooldown);
        }

        interface WithAttach {
            AutoscaleProfile.UpdateDefinitionStages.WithScaleRuleOptional attach();
        }
    }

    interface UpdateDefinition extends
            UpdateDefinitionStages.Blank,
            UpdateDefinitionStages.WithMetricName,
            UpdateDefinitionStages.WithStatistic,
            UpdateDefinitionStages.WithCondition,
            UpdateDefinitionStages.WithScaleAction,
            UpdateDefinitionStages.WithAttach {
    }

    interface UpdateDefinitionStages {
        interface Blank {
            WithMetricName withMetricSource(String metricSourceResourceId);
        }

        interface WithMetricName {
            WithStatistic withMetricName(String metricName);
        }

        interface WithStatistic {
            WithCondition withStatistic(Period duration, Period frequency, MetricStatisticType statisticType);
        }

        interface WithCondition {
            WithScaleAction withCondition(ComparisonOperationType condition, TimeAggregationType timeAggregation, double threshold);
        }

        interface WithScaleAction
        {
            WithAttach withScaleAction(ScaleDirection direction, ScaleType type, int instanceCountChange, Period cooldown);
        }

        interface WithAttach extends
                Attachable.InUpdate<AutoscaleProfile.Update>{
        }
    }

    interface Update extends
            Settable<AutoscaleProfile.Update>{
        Update withMetricSource(String metricSourceResourceId);
        Update withMetricName(String metricName);
        Update withStatistic(Period duration, Period frequency, MetricStatisticType statisticType);
        Update withCondition(ComparisonOperationType condition, TimeAggregationType timeAggregation, double threshold);
        Update withScaleAction(ScaleDirection direction, ScaleType type, int instanceCountChange, Period cooldown);
    }
}
