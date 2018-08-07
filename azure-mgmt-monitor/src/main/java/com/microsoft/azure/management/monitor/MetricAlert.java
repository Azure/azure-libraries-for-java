/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.LangMethodDefinition;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.monitor.implementation.ActionGroupResourceInner;
import com.microsoft.azure.management.monitor.implementation.MetricAlertResourceInner;
import com.microsoft.azure.management.monitor.implementation.MonitorManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;

import java.util.List;

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
            DefinitionStages.WithCreate {
    }

    /**
     * Grouping of Action Group definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of a Metric Alert definition.
         */
        interface Blank extends GroupableResource.DefinitionStages.WithGroupAndRegion<WithDescription> {
        }

        /**
         * The stage of a Metric Alert definition allowing the resource group to be specified.
         */
        interface WithDescription {
            WithSeverity withDescription(String description);
        }

        interface WithSeverity {
            WithScopes withSeverity(String severity);
        }

        interface WithScopes {
            WithEvaluationFrequency withResourceScope(String resourceId);
            WithEvaluationFrequency withResourceScopes(List resourceIds);
        }

        interface WithEvaluationFrequency {
            WithWindowSize withEvaluationFrequency(String frequency);
        }

        interface WithWindowSize {
            WithAlertEnabled withWindowSize(String size);
        }

        interface WithAlertEnabled {
            WithActionGroup withAlertEnabled();
            WithActionGroup withAlertDisabled();
        }

        interface WithActionGroup {
            WithCriteriaDefinition withActionGroup(String actionGroupId);
            WithCriteriaDefinition withActionGroup(ActionGroup actionGroup);
        }

        interface WithCriteriaDefinition {
            WithCreate defineMetricCriteria(String name);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be created
         * but also allows for any other optional settings to be specified.
         */
        interface WithCreate extends
                Creatable<MetricAlert>,
                DefinitionWithTags<WithCreate> {

            WithCreate withAutoMitigation();
        }
    }

    /**
     * Grouping of Action Group update stages.
     */
    interface UpdateStages {

        /**
         * The stage of update which contains all the top level fields and transition stages to receiver updates.
         */
        interface WithActionDefinition {
            /**
             * Removes all the receivers that contain specified actionNamePrefix string in the name.
             *
             * @param actionNamePrefix the actionNamePrefix value to use during receiver filtering.
             * @return the next stage of the update
             */
            Update withoutReceiver(String actionNamePrefix);

            /**
             * Sets the short name of the action group. This will be used in SMS messages. Maximum length cannot exceed 12 symbols.
             *
             * @param shortName short name of the action group. Cannot exceed 12 symbols
             * @return the next stage of the update
             */
            Update withShortName(String shortName);

        }
    }

    /**
     * The template for an update operation, containing all the settings that can be modified.
     */
    interface Update extends
            Appliable<MetricAlert>,
            UpdateStages.WithActionDefinition,
            Resource.UpdateWithTags<Update> {
    }
}