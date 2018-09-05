/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.LangMethodDefinition;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.monitor.implementation.AutoscaleProfileInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasParent;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Settable;
import org.joda.time.DateTime;

import java.util.List;

/**
 * An immutable client-side representation of an Azure autoscale profile.
 */
@Fluent
public interface AutoscaleProfile extends
        HasInner<AutoscaleProfileInner>,
        HasParent<AutoscaleSetting>,
        HasName {
    /**
     * Get the minimum number of instances for the resource.
     *
     * @return the minimum value.
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    int minInstanceCount();

    /**
     * Get the maximum number of instances for the resource. The actual maximum number of instances is limited by the cores that are available in the subscription.
     *
     * @return the maximum value.
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    int maxInstanceCount();

    /**
     * Get the number of instances that will be set if metrics are not available for evaluation. The default is only used if the current instance count is lower than the default.
     *
     * @return the defaultProperty value.
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    int defaultInstanceCount();

    /**
     * Get the specific date-time for the profile. This element is not used if the Recurrence element is used.
     *
     * @return the fixedDate value.
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    TimeWindow fixedDateSchedule();

    /**
     * Get the repeating times at which this profile begins. This element is not used if the FixedDate element is used.
     *
     * @return the recurrence value.
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    Recurrence recurrentSchedule();

    /**
     * Get the collection of rules that provide the triggers and parameters for the scaling action. A maximum of 10 rules can be specified.
     *
     * @return the rules value.
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    List<ScaleRule> rules();

    /**
     * The entirety of an autoscale profile definition.
     */
    interface Definition extends
            DefinitionStages.WithAttach,
            DefinitionStages.Blank,
            DefinitionStages.WithScaleRule,
            DefinitionStages.WithScaleRuleOptional,
            DefinitionStages.WithScaleSchedule {
    }

    /**
     * Grouping of autoscale profile definition stages.
     */
    interface DefinitionStages {
        /**
         * The final stage of the definition which attaches defined profile to the current Autoscale settings.
         */
        interface WithAttach extends
                Attachable.InDefinition<AutoscaleSetting.DefinitionStages.WithCreate> {
        }

        /**
         * The first stage of autoscale profile definition.
         */
        interface Blank {
            /**
             * Selects metric based autoscale profile.
             *
             * @param minimumInstanceCount the minimum number of instances for the resource.
             * @param maximumInstanceCount the maximum number of instances for the resource. The actual maximum number of instances is limited by the cores that are available in the subscription.
             * @param defaultInstanceCount the number of instances that will be set if metrics are not available for evaluation. The default is only used if the current instance count is lower than the default.
             * @return the next stage of the definition.
             */
            WithScaleRule withMetricBasedScale(int minimumInstanceCount, int maximumInstanceCount, int defaultInstanceCount);

            /**
             * Selects schedule based autoscale profile.
             *
             * @param instanceCount the number of instances that will be set during specified schedule. The actual number of instances is limited by the cores that are available in the subscription.
             * @return the next stage of the definition.
             */
            WithScaleSchedule withScheduleBasedScale(int instanceCount);
        }

        /**
         * The stage of the definition which adds scale rules.
         */
        interface WithScaleRule {
            /**
             * Starts the definition of scale rule for the current autoscale profile.
             *
             * @return the next stage of the definition.
             */
            @Method
            ScaleRule.DefinitionStages.Blank defineScaleRule();
        }

        /**
         * The stage of the definition which adds optional scale rules and schedules.
         */
        interface WithScaleRuleOptional extends
                WithAttach {
            /**
             * Starts the definition of scale rule for the current autoscale profile.
             *
             * @return the next stage of the definition.
             */
            @Method
            ScaleRule.DefinitionStages.Blank defineScaleRule();

            /**
             * Specifies fixed date schedule for autoscale profile.
             *
             * @param timeZone time zone for the schedule.
             * @param start start time.
             * @param end end time.
             * @return the next stage of the definition.
             */
            WithScaleRuleOptional withFixedDateSchedule(String timeZone, DateTime start, DateTime end);

            /**
             * Specifies recurrent schedule for autoscale profile.
             *
             * @param scheduleTimeZone time zone for the schedule.
             * @param startTime start time in hh:mm format.
             * @param weekday list of week days when the schedule should be active.
             * @return the next stage of the definition.
             */
            WithScaleRuleOptional withRecurrentSchedule(String scheduleTimeZone, String startTime, DayOfWeek... weekday);
        }

        /**
         * The stage of the definition which specifies autoscale profile schedule.
         */
        interface WithScaleSchedule {
            /**
             * Specifies fixed date schedule for autoscale profile.
             *
             * @param timeZone time zone for the schedule.
             * @param start start time.
             * @param end end time.
             * @return the next stage of the definition.
             */
            WithAttach withFixedDateSchedule(String timeZone, DateTime start, DateTime end);

            /**
             * Specifies recurrent schedule for autoscale profile.
             *
             * @param scheduleTimeZone time zone for the schedule.
             * @param startTime start time in hh:mm format.
             * @param weekday list of week days when the schedule should be active.
             * @return the next stage of the definition.
             */
            WithAttach withRecurrentSchedule(String scheduleTimeZone, String startTime, DayOfWeek... weekday);
        }
    }

    /**
     * The entirety of an autoscale profile definition during current autoscale settings update.
     */
    interface UpdateDefinition extends
            UpdateDefinitionStages.WithAttach,
            UpdateDefinitionStages.Blank,
            UpdateDefinitionStages.WithScaleRule,
            UpdateDefinitionStages.WithScaleRuleOptional,
            UpdateDefinitionStages.WithScaleSchedule {
    }

    /**
     * Grouping of autoscale profile definition stages during current autoscale settings update stage.
     */
    interface UpdateDefinitionStages {
        /**
         * The final stage of the definition which attaches defined profile to the current Autoscale settings.
         */
        interface WithAttach extends
                Attachable.InUpdate<AutoscaleSetting.Update> {
        }

        /**
         * The first stage of autoscale profile definition.
         */
        interface Blank {
            /**
             * Selects metric based autoscale profile.
             *
             * @param minimumInstanceCount the minimum number of instances for the resource.
             * @param maximumInstanceCount the maximum number of instances for the resource. The actual maximum number of instances is limited by the cores that are available in the subscription.
             * @param defaultInstanceCount the number of instances that will be set if metrics are not available for evaluation. The default is only used if the current instance count is lower than the default.
             * @return the next stage of the definition.
             */
            WithScaleRule withMetricBasedScale(int minimumInstanceCount, int maximumInstanceCount, int defaultInstanceCount);

            /**
             * Selects schedule based autoscale profile.
             *
             * @param instanceCount the number of instances that will be set during specified schedule. The actual number of instances is limited by the cores that are available in the subscription.
             * @return the next stage of the definition.
             */
            WithScaleSchedule withScheduleBasedScale(int instanceCount);
        }

        /**
         * The stage of the definition which adds scale rules.
         */
        interface WithScaleRule {
            /**
             * Starts the definition of scale rule for the current autoscale profile.
             *
             * @return the next stage of the definition.
             */
            @Method
            ScaleRule.ParentUpdateDefinitionStages.Blank defineScaleRule();
        }

        /**
         * The stage of the definition which adds optional scale rules and schedules.
         */
        interface WithScaleRuleOptional extends
                WithAttach {
            /**
             * Starts the definition of scale rule for the current autoscale profile.
             *
             * @return the next stage of the definition.
             */
            @Method
            ScaleRule.ParentUpdateDefinitionStages.Blank defineScaleRule();

            /**
             * Specifies fixed date schedule for autoscale profile.
             *
             * @param timeZone time zone for the schedule.
             * @param start start time.
             * @param end end time.
             * @return the next stage of the definition.
             */
            WithScaleRuleOptional withFixedDateSchedule(String timeZone, DateTime start, DateTime end);

            /**
             * Specifies recurrent schedule for autoscale profile.
             *
             * @param scheduleTimeZone time zone for the schedule.
             * @param startTime start time in hh:mm format.
             * @param weekday list of week days when the schedule should be active.
             * @return the next stage of the definition.
             */
            WithScaleRuleOptional withRecurrentSchedule(String scheduleTimeZone, String startTime, DayOfWeek... weekday);
        }

        /**
         * The stage of the definition which specifies autoscale profile schedule.
         */
        interface WithScaleSchedule {
            /**
             * Specifies fixed date schedule for autoscale profile.
             *
             * @param timeZone time zone for the schedule.
             * @param start start time.
             * @param end end time.
             * @return the next stage of the definition.
             */
            WithAttach withFixedDateSchedule(String timeZone, DateTime start, DateTime end);

            /**
             * Specifies recurrent schedule for autoscale profile.
             *
             * @param scheduleTimeZone time zone for the schedule.
             * @param startTime start time in hh:mm format.
             * @param weekday list of week days when the schedule should be active.
             * @return the next stage of the definition.
             */
            WithAttach withRecurrentSchedule(String scheduleTimeZone, String startTime, DayOfWeek... weekday);
        }
    }

    /**
     * Grouping of autoscale profile update stages.
     */
    interface Update extends
            Settable<AutoscaleSetting.Update> {
        /**
         * Updates metric based autoscale profile.
         *
         * @param minimumInstanceCount the minimum number of instances for the resource.
         * @param maximumInstanceCount the maximum number of instances for the resource. The actual maximum number of instances is limited by the cores that are available in the subscription.
         * @param defaultInstanceCount the number of instances that will be set if metrics are not available for evaluation. The default is only used if the current instance count is lower than the default.
         * @return the next stage of the autoscale profile update.
         */
        Update withMetricBasedScale(int minimumInstanceCount, int maximumInstanceCount, int defaultInstanceCount);

        /**
         * Updates schedule based autoscale profile.
         *
         * @param instanceCount instanceCount the number of instances that will be set during specified schedule. The actual number of instances is limited by the cores that are available in the subscription.
         * @return the next stage of the autoscale profile update.
         */
        Update withScheduleBasedScale(int instanceCount);

        /**
         * Updates fixed date schedule for autoscale profile.
         *
         * @param timeZone time zone for the schedule.
         * @param start start time.
         * @param end end time.
         * @return the next stage of the autoscale profile update.
         */
        Update withFixedDateSchedule(String timeZone, DateTime start, DateTime end);

        /**
         * Updates recurrent schedule for autoscale profile.
         *
         * @param scheduleTimeZone time zone for the schedule.
         * @param startTime start time in hh:mm format.
         * @param weekday list of week days when the schedule should be active.
         * @return the next stage of the autoscale profile update.
         */
        Update withRecurrentSchedule(String scheduleTimeZone, String startTime, DayOfWeek... weekday);

        /**
         * Starts the definition of scale rule for the current autoscale profile.
         *
         * @return the next stage of the autoscale profile update.
         */
        @Method
        ScaleRule.UpdateDefinitionStages.Blank defineScaleRule();

        /**
         * Starts the update of the scale rule for the current autoscale profile.
         *
         * @param ruleIndex the index of the scale rule in the current autoscale profile. The index represents the order at which rules were added to the current profile.
         * @return the next stage of the autoscale profile update.
         */
        ScaleRule.Update updateScaleRule(int ruleIndex);

        /**
         * Removes scale rule from the current autoscale profile.
         *
         * @param ruleIndex the index of the scale rule in the current autoscale profile.
         * @return the next stage of the autoscale profile update.
         */
        Update withoutScaleRule(int ruleIndex);
    }
}
