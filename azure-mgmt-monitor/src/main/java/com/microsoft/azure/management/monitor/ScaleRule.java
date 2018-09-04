/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.LangMethodDefinition;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.monitor.implementation.ScaleRuleInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasParent;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Settable;
import org.joda.time.Period;

/**
 * An immutable client-side representation of an Azure autoscale profile scale rule.
 */
@Fluent
public interface ScaleRule extends
        HasInner<ScaleRuleInner>,
        HasParent<AutoscaleProfile> {
    /**
     * Get the resource identifier of the resource the rule monitors.
     *
     * @return the metricResourceUri value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String metricSource();

    /**
     * Get the name of the metric that defines what the rule monitors.
     *
     * @return the metricName value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String metricName();

    /**
     * Get the range of time in which instance data is collected. This value must be greater than the delay in metric collection, which can vary from resource-to-resource. Must be between 12 hours and 5 minutes.
     *
     * @return the timeWindow value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    Period duration();

    /**
     * Get the granularity of metrics the rule monitors. Must be one of the predefined values returned from metric definitions for the metric. Must be between 12 hours and 1 minute.
     *
     * @return the timeGrain value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    Period frequency();

    /**
     * Get the metric statistic type. How the metrics from multiple instances are combined. Possible values include: 'Average', 'Min', 'Max', 'Sum'.
     *
     * @return the statistic value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    MetricStatisticType frequencyStatistic();

    /**
     * Get the operator that is used to compare the metric data and the threshold. Possible values include: 'Equals', 'NotEquals', 'GreaterThan', 'GreaterThanOrEqual', 'LessThan', 'LessThanOrEqual'.
     *
     * @return the operator value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    ComparisonOperationType condition();

    /**
     * Get the time aggregation type. How the data that is collected should be combined over time. The default value is Average. Possible values include: 'Average', 'Minimum', 'Maximum', 'Total', 'Count'.
     *
     * @return the timeAggregation value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    TimeAggregationType timeAggregation();

    /**
     * Get the threshold of the metric that triggers the scale action.
     *
     * @return the threshold value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    double threshold();

    /**
     * Get the scale direction. Whether the scaling action increases or decreases the number of instances. Possible values include: 'None', 'Increase', 'Decrease'.
     *
     * @return the direction value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    ScaleDirection scaleDirection();

    /**
     * Get the type of action that should occur when the scale rule fires. Possible values include: 'ChangeCount', 'PercentChangeCount', 'ExactCount'.
     *
     * @return the type value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    ScaleType scaleType();

    /**
     * Get the number of instances that are involved in the scaling action.
     *
     * @return the value value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    int scaleInstanceCount();

    /**
     * Get the amount of time to wait since the last scaling action before this action occurs. It must be between 1 week and 1 minute in ISO 8601 format.
     *
     * @return the cooldown value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    Period coolDown();

    /**
     * The entirety of an autoscale profile scale rule definition.
     */
    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.WithMetricName,
            DefinitionStages.WithStatistic,
            DefinitionStages.WithCondition,
            DefinitionStages.WithScaleAction,
            DefinitionStages.WithAttach {
    }

    /**
     * Grouping of autoscale profile scale rule definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of autoscale profile scale rule definition.
         */
        interface Blank {
            /**
             * Sets the resource identifier of the resource the rule monitors.
             *
             * @param metricSourceResourceId resourceId of the resource.
             * @return the next stage of the definition.
             */
            WithMetricName withMetricSource(String metricSourceResourceId);
        }

        /**
         * The stage of the definition which specifies metric name.
         */
        interface WithMetricName {
            /**
             * Sets the name of the metric that defines what the rule monitors.
             *
             * @param metricName name of the metric.
             * @return the next stage of the definition.
             */
            WithStatistic withMetricName(String metricName);
        }

        /**
         * The stage of the definition which specifies what kind of statistics should be used to calculate autoscale trigger action.
         */
        interface WithStatistic {
            /**
             * Sets statistics for autoscale trigger action.
             *
             * @param duration the range of time in which instance data is collected. This value must be greater than the delay in metric collection, which can vary from resource-to-resource. Must be between 12 hours and 5 minutes.
             * @param frequency the granularity of metrics the rule monitors. Must be one of the predefined values returned from metric definitions for the metric. Must be between 12 hours and 1 minute.
             * @param statisticType the metric statistic type. How the metrics from multiple instances are combined. Possible values include: 'Average', 'Min', 'Max', 'Sum'.
             * @return the next stage of the definition.
             */
            WithCondition withStatistic(Period duration, Period frequency, MetricStatisticType statisticType);
        }

        /**
         * The stage of the definition which specifies metric alert condition.
         */
        interface WithCondition {
            /**
             * Sets the condition to monitor for the current metric alert.
             *
             * @param condition the operator that is used to compare the metric data and the threshold. Possible values include: 'Equals', 'NotEquals', 'GreaterThan', 'GreaterThanOrEqual', 'LessThan', 'LessThanOrEqual'.
             * @param timeAggregation the time aggregation type. How the data that is collected should be combined over time. The default value is Average. Possible values include: 'Average', 'Minimum', 'Maximum', 'Total', 'Count'.
             * @param threshold the threshold of the metric that triggers the scale action.
             * @return the next stage of the definition.
             */
            WithScaleAction withCondition(ComparisonOperationType condition, TimeAggregationType timeAggregation, double threshold);
        }

        /**
         * The stage of the definition which specifies action to take when the metric alert will be triggered.
         */
        interface WithScaleAction {
            /**
             * Sets the action to be performed when the scale rule will be active.
             *
             * @param direction the scale direction. Whether the scaling action increases or decreases the number of instances. Possible values include: 'None', 'Increase', 'Decrease'.
             * @param type the type of action that should occur when the scale rule fires. Possible values include: 'ChangeCount', 'PercentChangeCount', 'ExactCount'.
             * @param instanceCountChange the number of instances that are involved in the scaling action.
             * @param cooldown the amount of time to wait since the last scaling action before this action occurs. It must be between 1 week and 1 minute in ISO 8601 format.
             * @return the next stage of the definition.
             */
            WithAttach withScaleAction(ScaleDirection direction, ScaleType type, int instanceCountChange, Period cooldown);
        }

        /**
         * The final stage of the definition which attaches defined scale rule to the current Autoscale profile.
         */
        interface WithAttach extends
                Attachable.InDefinition<AutoscaleProfile.DefinitionStages.WithScaleRuleOptional> {
        }
    }

    /**
     * The entirety of an autoscale profile scale rule definition during parent Autoscale Profile definition in Autoscale Settings update stage.
     */
    interface ParentUpdateDefinition extends
            ParentUpdateDefinitionStages.Blank,
            ParentUpdateDefinitionStages.WithMetricName,
            ParentUpdateDefinitionStages.WithStatistic,
            ParentUpdateDefinitionStages.WithCondition,
            ParentUpdateDefinitionStages.WithScaleAction,
            ParentUpdateDefinitionStages.WithAttach {
    }

    /**
     * Grouping of autoscale profile scale rule definition stages during definition of Autoscale Profile in the Autoscale update stage.
     */
    interface ParentUpdateDefinitionStages {
        /**
         * The first stage of autoscale profile scale rule definition.
         */
        interface Blank {
            /**
             * Sets the resource identifier of the resource the rule monitors.
             *
             * @param metricSourceResourceId resourceId of the resource.
             * @return the next stage of the definition.
             */
            WithMetricName withMetricSource(String metricSourceResourceId);
        }

        /**
         * The stage of the definition which specifies metric name.
         */
        interface WithMetricName {
            /**
             * Sets the name of the metric that defines what the rule monitors.
             *
             * @param metricName name of the metric.
             * @return the next stage of the definition.
             */
            WithStatistic withMetricName(String metricName);
        }

        /**
         * The stage of the definition which specifies what kind of statistics should be used to calculate autoscale trigger action.
         */
        interface WithStatistic {
            /**
             * Sets statistics for autoscale trigger action.
             *
             * @param duration the range of time in which instance data is collected. This value must be greater than the delay in metric collection, which can vary from resource-to-resource. Must be between 12 hours and 5 minutes.
             * @param frequency the granularity of metrics the rule monitors. Must be one of the predefined values returned from metric definitions for the metric. Must be between 12 hours and 1 minute.
             * @param statisticType the metric statistic type. How the metrics from multiple instances are combined. Possible values include: 'Average', 'Min', 'Max', 'Sum'.
             * @return the next stage of the definition.
             */
            WithCondition withStatistic(Period duration, Period frequency, MetricStatisticType statisticType);
        }

        /**
         * The stage of the definition which specifies metric alert condition.
         */
        interface WithCondition {
            /**
             * Sets the condition to monitor for the current metric alert.
             *
             * @param condition the operator that is used to compare the metric data and the threshold. Possible values include: 'Equals', 'NotEquals', 'GreaterThan', 'GreaterThanOrEqual', 'LessThan', 'LessThanOrEqual'.
             * @param timeAggregation the time aggregation type. How the data that is collected should be combined over time. The default value is Average. Possible values include: 'Average', 'Minimum', 'Maximum', 'Total', 'Count'.
             * @param threshold the threshold of the metric that triggers the scale action.
             * @return the next stage of the definition.
             */
            WithScaleAction withCondition(ComparisonOperationType condition, TimeAggregationType timeAggregation, double threshold);
        }

        /**
         * The stage of the definition which specifies action to take when the metric alert will be triggered.
         */
        interface WithScaleAction {
            /**
             * Sets the action to be performed when the scale rule will be active.
             *
             * @param direction the scale direction. Whether the scaling action increases or decreases the number of instances. Possible values include: 'None', 'Increase', 'Decrease'.
             * @param type the type of action that should occur when the scale rule fires. Possible values include: 'ChangeCount', 'PercentChangeCount', 'ExactCount'.
             * @param instanceCountChange the number of instances that are involved in the scaling action.
             * @param cooldown the amount of time to wait since the last scaling action before this action occurs. It must be between 1 week and 1 minute in ISO 8601 format.
             * @return the next stage of the definition.
             */
            WithAttach withScaleAction(ScaleDirection direction, ScaleType type, int instanceCountChange, Period cooldown);
        }

        /**
         * The final stage of the definition which attaches defined scale rule to the current Autoscale profile.
         */
        interface WithAttach {
            /**
             * Attaches sclae rule to the new autoscale profile in the autoscale update definition stage.
             *
             * @return the next stage of the parent definition
             */
            @Method
            AutoscaleProfile.UpdateDefinitionStages.WithScaleRuleOptional attach();
        }
    }

    /**
     * The entirety of an autoscale profile scale rule definition during parent Autoscale Profile update in Autoscale Settings update stage.
     */
    interface UpdateDefinition extends
            UpdateDefinitionStages.Blank,
            UpdateDefinitionStages.WithMetricName,
            UpdateDefinitionStages.WithStatistic,
            UpdateDefinitionStages.WithCondition,
            UpdateDefinitionStages.WithScaleAction,
            UpdateDefinitionStages.WithAttach {
    }

    /**
     * Grouping of autoscale profile scale rule definition stages during update of Autoscale Profile in the Autoscale update stage.
     */
    interface UpdateDefinitionStages {
        /**
         * The first stage of autoscale profile scale rule definition.
         */
        interface Blank {
            /**
             * Sets the resource identifier of the resource the rule monitors.
             *
             * @param metricSourceResourceId resourceId of the resource.
             * @return the next stage of the definition.
             */
            WithMetricName withMetricSource(String metricSourceResourceId);
        }

        /**
         * The stage of the definition which specifies metric name.
         */
        interface WithMetricName {
            /**
             * Sets the name of the metric that defines what the rule monitors.
             *
             * @param metricName name of the metric.
             * @return the next stage of the definition.
             */
            WithStatistic withMetricName(String metricName);
        }

        /**
         * The stage of the definition which specifies what kind of statistics should be used to calculate autoscale trigger action.
         */
        interface WithStatistic {
            /**
             * Sets statistics for autoscale trigger action.
             *
             * @param duration the range of time in which instance data is collected. This value must be greater than the delay in metric collection, which can vary from resource-to-resource. Must be between 12 hours and 5 minutes.
             * @param frequency the granularity of metrics the rule monitors. Must be one of the predefined values returned from metric definitions for the metric. Must be between 12 hours and 1 minute.
             * @param statisticType the metric statistic type. How the metrics from multiple instances are combined. Possible values include: 'Average', 'Min', 'Max', 'Sum'.
             * @return the next stage of the definition.
             */
            WithCondition withStatistic(Period duration, Period frequency, MetricStatisticType statisticType);
        }

        /**
         * The stage of the definition which specifies metric alert condition.
         */
        interface WithCondition {
            /**
             * Sets the condition to monitor for the current metric alert.
             *
             * @param condition the operator that is used to compare the metric data and the threshold. Possible values include: 'Equals', 'NotEquals', 'GreaterThan', 'GreaterThanOrEqual', 'LessThan', 'LessThanOrEqual'.
             * @param timeAggregation the time aggregation type. How the data that is collected should be combined over time. The default value is Average. Possible values include: 'Average', 'Minimum', 'Maximum', 'Total', 'Count'.
             * @param threshold the threshold of the metric that triggers the scale action.
             * @return the next stage of the definition.
             */
            WithScaleAction withCondition(ComparisonOperationType condition, TimeAggregationType timeAggregation, double threshold);
        }
        /**
         * The stage of the definition which specifies action to take when the metric alert will be triggered.
         */
        interface WithScaleAction {
            /**
             * Sets the action to be performed when the scale rule will be active.
             *
             * @param direction the scale direction. Whether the scaling action increases or decreases the number of instances. Possible values include: 'None', 'Increase', 'Decrease'.
             * @param type the type of action that should occur when the scale rule fires. Possible values include: 'ChangeCount', 'PercentChangeCount', 'ExactCount'.
             * @param instanceCountChange the number of instances that are involved in the scaling action.
             * @param cooldown the amount of time to wait since the last scaling action before this action occurs. It must be between 1 week and 1 minute in ISO 8601 format.
             * @return the next stage of the definition.
             */
            WithAttach withScaleAction(ScaleDirection direction, ScaleType type, int instanceCountChange, Period cooldown);
        }

        /**
         * The final stage of the definition which attaches defined scale rule to the current Autoscale profile.
         */
        interface WithAttach extends
            /**
             * Attaches sclae rule to the new autoscale profile in the autoscale update definition stage.
             *
             * @return the next stage of the parent definition
             */
            Attachable.InUpdate<AutoscaleProfile.Update> {
        }
    }

    /**
     * Grouping of scale rule update stages.
     */
    interface Update extends
            Settable<AutoscaleProfile.Update> {
        /**
         * Updates the resource identifier of the resource the rule monitors.
         *
         * @param metricSourceResourceId resourceId of the resource.
         * @return the next stage of the scale rule update.
         */
        Update withMetricSource(String metricSourceResourceId);

        /**
         * the name of the metric that defines what the rule monitors.
         *
         * @param metricName metricName name of the metric.
         * @return the next stage of the scale rule update.
         */
        Update withMetricName(String metricName);

        /**
         * Updates the statistics for autoscale trigger action.
         *
         * @param duration the range of time in which instance data is collected. This value must be greater than the delay in metric collection, which can vary from resource-to-resource. Must be between 12 hours and 5 minutes.
         * @param frequency the granularity of metrics the rule monitors. Must be one of the predefined values returned from metric definitions for the metric. Must be between 12 hours and 1 minute.
         * @param statisticType the metric statistic type. How the metrics from multiple instances are combined. Possible values include: 'Average', 'Min', 'Max', 'Sum'.
         * @return the next stage of the scale rule update.
         */
        Update withStatistic(Period duration, Period frequency, MetricStatisticType statisticType);

        /**
         * Updates the condition to monitor for the current metric alert.
         *
         * @param condition the operator that is used to compare the metric data and the threshold. Possible values include: 'Equals', 'NotEquals', 'GreaterThan', 'GreaterThanOrEqual', 'LessThan', 'LessThanOrEqual'.
         * @param timeAggregation the time aggregation type. How the data that is collected should be combined over time. The default value is Average. Possible values include: 'Average', 'Minimum', 'Maximum', 'Total', 'Count'.
         * @param threshold the threshold of the metric that triggers the scale action.
         * @return the next stage of the scale rule update.
         */
        Update withCondition(ComparisonOperationType condition, TimeAggregationType timeAggregation, double threshold);

        /**
         * Updates the action to be performed when the scale rule will be active.
         *
         * @param direction the scale direction. Whether the scaling action increases or decreases the number of instances. Possible values include: 'None', 'Increase', 'Decrease'.
         * @param type the type of action that should occur when the scale rule fires. Possible values include: 'ChangeCount', 'PercentChangeCount', 'ExactCount'.
         * @param instanceCountChange the number of instances that are involved in the scaling action.
         * @param cooldown the amount of time to wait since the last scaling action before this action occurs. It must be between 1 week and 1 minute in ISO 8601 format.
         * @return the next stage of the scale rule update.
         */
        Update withScaleAction(ScaleDirection direction, ScaleType type, int instanceCountChange, Period cooldown);
    }
}
