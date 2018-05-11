/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.network.implementation.ApplicationSecurityGroupInner;
import com.microsoft.azure.management.network.implementation.NetworkManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;

/**
 * Application security group.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_10_0)
public interface ApplicationSecurityGroup extends
        GroupableResource<NetworkManager, ApplicationSecurityGroupInner>,
        Refreshable<ApplicationSecurityGroup>,
        Updatable<ApplicationSecurityGroup.Update> {
    /**
     * @return the resource GUID property of the application security group resource.
     * It uniquely identifies a resource, even if the user changes its name or
     * migrate the resource across subscriptions or resource groups.
     */
    String resourceGuid();

    /**
     * @return the provisioning state of the application security group resource
     */
    String provisioningState();

    // Fluent interfaces for creating Application Security Groups

    /**
     * The entirety of the application security group definition.
     */
    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.WithGroup,
            DefinitionStages.WithCreate {
    }

    /**
     * Grouping of application security group definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of the definition.
         */
        interface Blank
                extends GroupableResource.DefinitionWithRegion<WithGroup> {
        }

        /**
         * The stage allowing to specify the resource group.
         */
        interface WithGroup
                extends GroupableResource.DefinitionStages.WithGroup<WithCreate> {
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for
         * the resource to be created (via {@link WithCreate#create()}), but also allows
         * for any other optional settings to be specified.
         */
        interface WithCreate extends
                Creatable<ApplicationSecurityGroup>,
                Resource.DefinitionWithTags<WithCreate> {
        }
    }

    /**
     * The template for an update operation, containing all the settings that
     * can be modified.
     * <p>
     * Call {@link Update#apply()} to apply the changes to the resource in Azure.
     */
    interface Update extends
            Appliable<ApplicationSecurityGroup>,
            Resource.UpdateWithTags<Update> {
    }
}
