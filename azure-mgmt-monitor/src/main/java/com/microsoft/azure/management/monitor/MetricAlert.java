/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.LangMethodDefinition;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.monitor.implementation.MetricAlertResourceInner;
import com.microsoft.azure.management.monitor.implementation.MonitorManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * An immutable client-side representation of a Metric Alert.
 */
@Fluent
public interface MetricAlert extends
        GroupableResource<MonitorManager, MetricAlertResourceInner>,
        Refreshable<MetricAlert>,
        Updatable<MetricAlert.Update> {

    /**
     * Get the description of the metric alert that will be included in the alert email.
     *
     * @return the description value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String description();

    /**
     * Get alert severity {0, 1, 2, 3, 4}.
     *
     * @return the severity value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    int severity();

    /**
     * Get the flag that indicates whether the metric alert is enabled.
     *
     * @return the enabled value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    boolean enabled();

    /**
     * Get the list of resource id's that this metric alert is scoped to.
     *
     * @return the scopes value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    Collection<String> scopes();

    /**
     * Get how often the metric alert is evaluated represented in ISO 8601 duration format.
     *
     * @return the evaluationFrequency value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    Period evaluationFrequency();

    /**
     * Get the period of time (in ISO 8601 duration format) that is used to monitor alert activity based on the threshold.
     *
     * @return the windowSize value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    Period windowSize();

    /**
     * @return metric alert criterias, indexed by name
     */
    Map<String, MetricAlertCondition> alertCriterias();

    /**
     * Get the flag that indicates whether the alert should be auto resolved or not.
     *
     * @return the autoMitigate value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    boolean autoMitigate();

    /**
     * Get the array of actions that are performed when the alert rule becomes active, and when an alert condition is resolved.
     *
     * @return the actions value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    Collection<String> actionGroupIds();

    /**
     * Get last time the rule was updated in ISO8601 format.
     *
     * @return the lastUpdatedTime value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    DateTime lastUpdatedTime();

    /**
     * The entirety of a Metric Alert definition.
     */
    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.WithCreate,
            DefinitionStages.WithScopes,
            DefinitionStages.WithWindowSize,
            DefinitionStages.WithEvaluationFrequency,
            DefinitionStages.WithSeverity,
            DefinitionStages.WithDescription,
            DefinitionStages.WithAlertEnabled,
            DefinitionStages.WithActionGroup,
            DefinitionStages.WithCriteriaDefinition {
    }

    /**
     * Grouping of metric alerts definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of a Metric Alert definition.
         */
        interface Blank extends GroupableResource.DefinitionStages.WithGroupAndRegion<WithScopes> {
        }

        /**
         * The stage of the definition which specifies target resource for metric alert.
         */
        interface WithScopes {
            /**
             * Sets specified resource as a target to alert on metric.
             *
             * @param resourceId resource Id string.
             * @return the next stage of metric alert definition.
             */
            WithWindowSize withTargetResource(String resourceId);

            /**
             * Sets specified resource as a target to alert on metric.
             *
             * @param resource resource type that is inherited from {@link HasId} interface
             * @return the next stage of metric alert definition.
             */
            WithWindowSize withTargetResource(HasId resource);
        }

        /**
         * The stage of the definition which specifies monitoring window for metric alert.
         */
        interface WithWindowSize {
            /**
             * Sets the period of time (in ISO 8601 duration format) that is used to monitor alert activity based on the threshold.
             *
             * @param size the windowSize value to set
             * @return the next stage of metric alert definition.
             */
            WithEvaluationFrequency withWindowSize(Period size);
        }

        /**
         * The stage of the definition which specifies evaluation frequency for metric alert.
         */
        interface WithEvaluationFrequency {
            /**
             * Set how often the metric alert is evaluated represented in ISO 8601 duration format.
             *
             * @param frequency the evaluationFrequency value to set.
             * @return the next stage of metric alert definition.
             */
            WithSeverity withEvaluationFrequency(Period frequency);
        }

        /**
         * The stage of the definition which specifies severity for metric alert.
         */
        interface WithSeverity {
            /**
             * Set alert severity {0, 1, 2, 3, 4}.
             *
             * @param severity the severity value to set
             * @return the next stage of metric alert definition.
             */
            WithDescription withSeverity(int severity);
        }

        /**
         * The stage of the definition which specifies description text for metric alert.
         */
        interface WithDescription {
            /**
             * Sets description for metric alert.
             *
             * @param description Human readable text description of the metric alert.
             * @return the next stage of metric alert definition.
             */
            WithAlertEnabled withDescription(String description);
        }

        /**
         * The stage of the definition which specifies if the metric alert should be enabled upon creation.
         */
        interface WithAlertEnabled {
            /**
             * Sets metric alert as enabled during the creation.
             *
             * @return the next stage of metric alert definition.
             */
            @Method
            WithActionGroup withRuleEnabled();

            /**
             * Sets metric alert as disabled during the creation.
             *
             * @return the next stage of metric alert definition.
             */
            @Method
            WithActionGroup withRuleDisabled();
        }

        /**
         * The stage of the definition which specifies actions that will be activated when the conditions are met in the metric alert rules.
         */
        interface WithActionGroup {
            /**
             * Sets the actions that will activate when the condition is met.
             *
             * @param actionGroupId resource Ids of the {@link ActionGroup}.
             * @return the next stage of metric alert definition.
             */
            WithCriteriaDefinition withActionGroups(String... actionGroupId);
        }

        /**
         * The stage of the definition which specifies condition that will cause this alert to activate.
         */
        interface WithCriteriaDefinition {
            /**
             * Starts definition of the metric alert condition.
             *
             * @param name sets the name of the condition.
             * @return the next stage of metric alert condition definition.
             */
            MetricAlertCondition.DefinitionStages.Blank.MetricName<WithCreate> defineAlertCriteria(String name);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be created
         * but also allows for any other optional settings to be specified.
         */
        interface WithCreate extends
                Creatable<MetricAlert>,
                DefinitionWithTags<WithCreate>,
                WithCriteriaDefinition {
            /**
             * Set the flag that indicates the alert should be auto resolved.
             *
             * @return the next stage of metric alert condition definition.
             */
            @Method
            WithCreate withAutoMitigation();
        }
    }

    /**
     * Grouping of metric alerts update stages.
     */
    interface UpdateStages {
        /**
         * The stage of a metric alerts update allowing to modify settings.
         */
        interface WithMetricUpdate {
            /**
             * Sets the period of time (in ISO 8601 duration format) that is used to monitor alert activity based on the threshold.
             *
             * @param size the windowSize value to set
             * @return the next stage of the metric alert update.
             */
            Update withWindowSize(Period size);

            /**
             * Set how often the metric alert is evaluated represented in ISO 8601 duration format.
             *
             * @param frequency the evaluationFrequency value to set.
             * @return the next stage of the metric alert update.
             */
            Update withEvaluationFrequency(Period frequency);

            /**
             * Set alert severity {0, 1, 2, 3, 4}.
             *
             * @param severity the severity value to set
             * @return the next stage of the metric alert update.
             */
            Update withSeverity(int severity);

            /**
             * Sets description for metric alert.
             *
             * @param description Human readable text description of the metric alert.
             * @return the next stage of the metric alert update.
             */
            Update withDescription(String description);

            /**
             * Sets metric alert as enabled.
             *
             * @return the next stage of the metric alert update.
             */
            @Method
            Update withRuleEnabled();

            /**
             * Sets metric alert as disabled.
             *
             * @return the next stage of the metric alert update.
             */
            @Method
            Update withRuleDisabled();

            /**
             * Sets the actions that will activate when the condition is met.
             *
             * @param actionGroupId resource Ids of the {@link ActionGroup}.
             * @return the next stage of the metric alert update.
             */
            Update withActionGroups(String... actionGroupId);

            /**
             * Removes the specified action group from the actions list.
             *
             * @param actionGroupId resource Id of the {@link ActionGroup} to remove.
             * @return the next stage of the metric alert update.
             */
            Update withoutActionGroup(String actionGroupId);

            /**
             * Starts definition of the metric alert condition.
             *
             * @param name sets the name of the condition.
             * @return the next stage of the metric alert update.
             */
            MetricAlertCondition.UpdateDefinitionStages.Blank.MetricName<Update> defineAlertCriteria(String name);

            /**
             * Starts update of the previously defined metric alert condition.
             *
             * @param name name of the condition that should be updated.
             * @return the next stage of the metric alert update.
             */
            MetricAlertCondition.UpdateStages updateAlertCriteria(String name);

            /**
             * Removes a condition from the previously defined metric alert conditions.
             *
             * @param name name of the condition that should be removed.
             * @return the next stage of the metric alert update.
             */
            Update withoutAlertCriteria(String name);

            /**
             * Set the flag that indicates the alert should be auto resolved.
             *
             * @return the next stage of the metric alert update.
             */
            @Method
            Update withAutoMitigation();

            /**
             * Set the flag that indicates the alert should not be auto resolved.
             *
             * @return the next stage of the metric alert update.
             */
            @Method
            Update withoutAutoMitigation();
        }
    }

    /**
     * The template for an update operation, containing all the settings that can be modified.
     */
    interface Update extends
            Appliable<MetricAlert>,
            UpdateStages.WithMetricUpdate,
            Resource.UpdateWithTags<Update> {
    }
}