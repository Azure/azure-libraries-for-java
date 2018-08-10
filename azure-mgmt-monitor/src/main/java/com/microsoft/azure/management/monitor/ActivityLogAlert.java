/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.monitor.implementation.ActivityLogAlertResourceInner;
import com.microsoft.azure.management.monitor.implementation.MonitorManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;

import java.util.Map;

/**
 * An immutable client-side representation of an Azure Activity Log Alert.
 */
@Fluent
public interface ActivityLogAlert extends
        GroupableResource<MonitorManager, ActivityLogAlertResourceInner>,
        Refreshable<ActivityLogAlert>,
        Updatable<ActivityLogAlert.Update> {

    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.WithCreate,
            DefinitionStages.WithScopes,
            DefinitionStages.WithDescription,
            DefinitionStages.WithAlertEnabled,
            DefinitionStages.WithActionGroup,
            DefinitionStages.WithCriteriaDefinition {
    }

    interface DefinitionStages {

        interface Blank extends GroupableResource.DefinitionStages.WithGroupAndRegion<WithScopes> {
        }

        interface WithScopes {
            WithDescription withTargetResource(String resourceId);
            WithDescription withTargetResource(HasId resource);
            WithDescription withTargetSubscription(String targetSubscriptionId);
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
            WithCreate withEqualsCondition(String field, String equals);
            WithCreate withEqualsConditions(Map<String, String> fieldEqualsMap);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be created
         * but also allows for any other optional settings to be specified.
         */
        interface WithCreate extends
                Creatable<ActivityLogAlert>,
                DefinitionWithTags<WithCreate>,
                WithCriteriaDefinition {
        }
    }

    interface UpdateStages {

        interface WithActivityLogUpdate {
            Update withDescription(String description);
            Update withRuleEnabled();
            Update withRuleDisabled();
            Update withActionGroups(String... actionGroupId);
            Update withoutActionGroup(String actionGroupId);
            Update withEqualsCondition(String field, String equals);
            Update withEqualsConditions(Map<String, String> fieldEqualsMap);
            Update withoutEqualsCondition(String field);

        }
    }

    /**
     * The template for an update operation, containing all the settings that can be modified.
     */
    interface Update extends
            Appliable<ActivityLogAlert>,
            UpdateStages.WithActivityLogUpdate,
            Resource.UpdateWithTags<Update> {
    }
}