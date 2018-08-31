/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.monitor.implementation.AutoscaleSettingResourceInner;
import com.microsoft.azure.management.monitor.implementation.MonitorManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;

import java.util.Map;

/**
 */
@Fluent
public interface AutoscaleSetting extends
    GroupableResource<MonitorManager, AutoscaleSettingResourceInner>,
    Refreshable<AutoscaleSetting>,
    Updatable<AutoscaleSetting.Update> {


    interface Definition extends
        DefinitionStages.Blank,
        DefinitionStages.WithCreate,
        DefinitionStages.DefineAutoscaleSettingResourceProfiles,
        DefinitionStages.WithAutoscaleSettingResourceTargetResourceUri,
        DefinitionStages.WithAutoscaleSettingResourceEnabled {
    }

    interface DefinitionStages {

        interface Blank extends
                GroupableResource.DefinitionStages.WithGroupAndRegion<WithAutoscaleSettingResourceTargetResourceUri>{
        }

        interface WithAutoscaleSettingResourceTargetResourceUri {
            DefineAutoscaleSettingResourceProfiles withTargetResource(String targetResourceId);
        }

        interface DefineAutoscaleSettingResourceProfiles {
            /**
             * the collection of automatic scaling profiles that specify different scaling parameters for different time periods. A maximum of 20 profiles can be specified.
             *
             * @param name
             * @return the next stage
             */
            AutoscaleProfile.DefinitionStages.Blank defineAutoscaleProfile(String name);
        }

        interface DefineAutoscaleSettingResourceNotifications {
            WithCreate withAdminEmailNotification();
            WithCreate withCoAdminEmailNotification();
            WithCreate withCustomEmailNotification(String customEmailAddresses);
            WithCreate withWebhookNotification(String serviceUri);
        }

        interface WithAutoscaleSettingResourceEnabled {
            WithCreate withAutoscaleDisabled();
        }

        interface WithCreate extends
            Creatable<AutoscaleSetting>,
            DefineAutoscaleSettingResourceProfiles,
            DefineAutoscaleSettingResourceNotifications,
            WithAutoscaleSettingResourceEnabled{
        }
    }

    interface Update extends
            Appliable<AutoscaleSetting>,
            Resource.UpdateWithTags<Update>,
            UpdateStages.DefineAutoscaleSettingProfiles,
            UpdateStages.UpdateAutoscaleSettings {
    }

    interface UpdateStages {
        interface DefineAutoscaleSettingProfiles {
            /**
             * the collection of automatic scaling profiles that specify different scaling parameters for different time periods. A maximum of 20 profiles can be specified.
             *
             * @return the next stage
             */
            AutoscaleProfile.UpdateDefinitionStages.Blank defineAutoscaleProfile(String name);
            /**
             * the collection of automatic scaling profiles that specify different scaling parameters for different time periods. A maximum of 20 profiles can be specified.
             *
             * @param name
             * @return the next stage
             */
            AutoscaleProfile.Update updateAutoscaleProfile(String name);
        }
        interface UpdateAutoscaleSettings {
            Update withoutAutoscaleProfile(String name);
            Update withAutoscaleEnabled();
            Update withAutoscaleDisabled();

            Update withAdminEmailNotification();
            Update withCoAdminEmailNotification();
            Update withCustomEmailNotification(String customEmailAddresses);
            Update withWebhookNotification(String serviceUri);

            Update withoutAdminEmailNotification();
            Update withoutCoAdminEmailNotification();
            Update withoutCustomEmailNotification(String customEmailAddresses);
            Update withoutWebhookNotification(String serviceUri);
        }
    }

}
