/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.batchai;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.batchai.implementation.BatchAIManager;
import com.microsoft.azure.management.batchai.implementation.ClusterInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;

/**
 * Entry point for Batch AI cluster management API in Azure.
 */
@Fluent
@Beta()
public interface Cluster extends
        GroupableResource<BatchAIManager, ClusterInner>,
        Refreshable<Cluster>,
        Updatable<Cluster.Update> {

    /**
     * The entirety of a Batch AI cluster definition.
     */
    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.WithGroup,
            DefinitionStages.WithVMSize,
            DefinitionStages.WithUserCredentials,
            DefinitionStages.WithScaleSettings,
            DefinitionStages.WithCreate {
    }

    /**
     * Grouping of Batch AI cluster definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of a Batch AI cluster definition.
         */
        interface Blank extends DefinitionWithRegion<WithGroup> {
        }

        /**
         * The stage of a Batch AI cluster definition allowing the resource group to be specified.
         */
        interface WithGroup extends GroupableResource.DefinitionStages.WithGroup<WithVMSize> {
        }

        /**
         * The stage of a Batch AI cluster definition allowing to specify virtual machine size. All virtual machines in a cluster are the same size.
         * For information about available VM sizes for clusters using images from the Virtual
         * Machines Marketplace (see Sizes for Virtual Machines (Linux) or Sizes for Virtual Machines (Windows). Batch AI service supports all Azure VM
         * sizes except STANDARD_A0 and those with premium storage (STANDARD_GS, STANDARD_DS, and STANDARD_DSV2 series).
         */
        interface WithVMSize {
            /**
             * @param vmSize virtual machine size
             * @return next stage of the definition
             */
            WithUserCredentials withVMSize(String vmSize);
        }

        interface WithUserCredentials {
            WithScaleSettings withUserName(String userName, String password);
        }

        interface WithScaleSettings {
            WithCreate withAutoScale(int minimumNodeCount, int maximumNodeCount);

            WithCreate withAutoScale(int minimumNodeCount, int maximumNodeCount, int initialNodeCount);

            WithCreate withManualScale(int targetNodeCount);

            WithCreate withManualScale(int targetNodeCount, DeallocationOption deallocationOption);
        }
        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be created
         * but also allows for any other optional settings to be specified.
         */
        interface WithCreate extends
                Creatable<Cluster>,
                Resource.DefinitionWithTags<WithCreate> {
        }
    }

    /**
     * Grouping of Batch AI cluster update stages.
     */
    interface UpdateStages {

    }

    /**
     * The template for an update operation, containing all the settings that can be modified.
     */
    interface Update extends
            Appliable<Cluster>,
            Resource.UpdateWithTags<Update> {
    }
}