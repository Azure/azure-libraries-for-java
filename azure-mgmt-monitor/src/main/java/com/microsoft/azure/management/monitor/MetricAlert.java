/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.monitor.implementation.MetricAlertResourceInner;
import com.microsoft.azure.management.monitor.implementation.MonitorManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import org.joda.time.Period;

/**
 * An immutable client-side representation of an Azure Action Group.
 */
@Fluent
public interface MetricAlert extends
        GroupableResource<MonitorManager, MetricAlertResourceInner>,
        Refreshable<MetricAlert>,
        Updatable<MetricAlert.Update> {

    /**
     * The entirety of a Action Group definition.
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

    interface DefinitionStages {
        /**
         * The first stage of a Metric Alert definition.
         */
        interface Blank extends GroupableResource.DefinitionStages.WithGroupAndRegion<WithScopes> {
        }

        interface WithScopes {
            WithWindowSize withTargetResource(String resourceId);
            WithWindowSize withTargetResource(HasId resource);
        }

        interface WithWindowSize {
            WithEvaluationFrequency withWindowSize(Period size);
        }
        interface WithEvaluationFrequency {
            WithSeverity withEvaluationFrequency(Period frequency);
        }

        interface WithSeverity {
            WithDescription withSeverity(int severity);
        }

        interface WithDescription {
            WithAlertEnabled withDescription(String description);
        }

        interface WithAlertEnabled {
            WithActionGroup withRuleEnabled();
            WithActionGroup withRuleDisabled();
        }

        interface WithActionGroup {
            WithCriteriaDefinition withActionGroups(String... actionGroupId);
        }

        interface WithCriteriaDefinition {
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
            WithCreate withAutoMitigation();
        }
    }

    interface UpdateStages {

        interface WithMetricUpdate {
            Update withWindowSize(Period size);
            Update withEvaluationFrequency(Period frequency);
            Update withSeverity(int severity);
            Update withDescription(String description);
            Update withRuleEnabled();
            Update withRuleDisabled();
            Update withActionGroups(String... actionGroupId);
            Update withoutActionGroup(String actionGroupId);
            MetricAlertCondition.UpdateDefinitionStages.Blank.MetricName<Update> defineAlertCriteria(String name);
            MetricAlertCondition.UpdateStages updateAlertCriteria(String name);
            Update withoutAlertCriteria(String name);
            Update withAutoMitigation();
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