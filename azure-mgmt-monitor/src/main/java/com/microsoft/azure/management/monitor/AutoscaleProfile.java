/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.monitor.implementation.AutoscaleProfileInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasParent;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Settable;
import org.joda.time.DateTime;

import java.util.List;

/**
 */
@Fluent
public interface AutoscaleProfile extends
        HasInner<AutoscaleProfileInner>,
        HasParent<AutoscaleSetting>,
        HasName {

    int minInstanceCount();
    int maxInstanceCount();
    int defaultInstanceCount();
    TimeWindow fixedDateSchedule();
    Recurrence recurrentSchedule();
    List<ScaleRule> rules();

    interface Definition extends
            DefinitionStages.WithAttach,
            DefinitionStages.Blank,
            DefinitionStages.WithScaleRule,
            DefinitionStages.WithScaleRuleOptional,
            DefinitionStages.WithScaleSchedule {
    }

    interface DefinitionStages {
        interface WithAttach extends
                Attachable.InDefinition<AutoscaleSetting.DefinitionStages.WithCreate> {
        }

        interface Blank {
            WithScaleRule withMetricBasedScale(int minimumInstanceCount, int maximumInstanceCount, int defaultInstanceCount);
            WithScaleSchedule withScheduleBasedScale(int instanceCount);
        }

        interface WithScaleRule {
            ScaleRule.DefinitionStages.Blank defineScaleRule();
        }

        interface WithScaleRuleOptional extends
                WithAttach {
            ScaleRule.DefinitionStages.Blank defineScaleRule();
            WithScaleRuleOptional withFixedDateSchedule(String timeZone, DateTime start, DateTime end);
            WithScaleRuleOptional withRecurrentSchedule(String scheduleTimeZone, String startTime, DayOfWeek... weekday);
        }

        interface WithScaleSchedule {
            WithAttach withFixedDateSchedule(String timeZone, DateTime start, DateTime end);
            WithAttach withRecurrentSchedule(String scheduleTimeZone, String startTime, DayOfWeek... weekday);
        }
    }

    interface UpdateDefinition extends
            UpdateDefinitionStages.WithAttach,
            UpdateDefinitionStages.Blank,
            UpdateDefinitionStages.WithScaleRule,
            UpdateDefinitionStages.WithScaleRuleOptional,
            UpdateDefinitionStages.WithScaleSchedule {
    }

    interface UpdateDefinitionStages {
        interface WithAttach extends
                Attachable.InUpdate<AutoscaleSetting.Update> {
        }

        interface Blank {
            WithScaleRule withMetricBasedScale(int minimumInstanceCount, int maximumInstanceCount, int defaultInstanceCount);
            WithScaleSchedule withScheduleBasedScale(int instanceCount);
        }

        interface WithScaleRule {
            ScaleRule.ParentUpdateDefinitionStages.Blank defineScaleRule();
        }

        interface WithScaleRuleOptional extends
                WithAttach {
            ScaleRule.ParentUpdateDefinitionStages.Blank defineScaleRule();
            WithScaleRuleOptional withFixedDateSchedule(String timeZone, DateTime start, DateTime end);
            WithScaleRuleOptional withRecurrentSchedule(String scheduleTimeZone, String startTime, DayOfWeek... weekday);
        }

        interface WithScaleSchedule {
            WithAttach withFixedDateSchedule(String timeZone, DateTime start, DateTime end);
            WithAttach withRecurrentSchedule(String scheduleTimeZone, String startTime, DayOfWeek... weekday);
        }
    }

    interface Update extends
            Settable<AutoscaleSetting.Update> {
        Update withMetricBasedScale(int minimumInstanceCount, int maximumInstanceCount, int defaultInstanceCount);
        Update withScheduleBasedScale(int instanceCount);
        Update withFixedDateSchedule(String timeZone, DateTime start, DateTime end);
        Update withRecurrentSchedule(String scheduleTimeZone, String startTime, DayOfWeek... weekday);
        ScaleRule.UpdateDefinitionStages.Blank defineScaleRule();
        ScaleRule.Update updateScaleRule(int ruleIndex);
        Update withoutScaleRule(int ruleIndex);
    }
}
