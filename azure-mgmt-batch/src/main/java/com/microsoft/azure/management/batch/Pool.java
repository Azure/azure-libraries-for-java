/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.batch;

import com.microsoft.azure.management.batch.implementation.PoolInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.ExternalChildResource;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Settable;

import java.util.List;

/**
 * An immutable client-side representation of an Azure Batch account pool.
 */
public interface Pool extends
        ExternalChildResource<Pool, BatchAccount>,
        HasInner<PoolInner> {
    /**
     * @return the networkConfiguration value
     */
    NetworkConfiguration networkConfiguration();

    /**
     * @return the mountConfiguration value
     */
    List<MountConfiguration> mountConfiguration();

    /**
     * The entirety of a Batch pool definition as a part of a Batch account definition.
     *
     * @param <ParentT> the stage of the parent Batch account definition to return to after attaching this definition
     */
    interface Definition<ParentT> extends
            DefinitionStages.Blank<ParentT>,
            DefinitionStages.WithConfig<ParentT>{
    }

    /**
     * Grouping of all the pool definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of a batch pool definition.
         *
         * @param <ParentT> the stage of the parent Batch account definition to return to after attaching this definition
         */
        interface Blank<ParentT> extends WithConfig<ParentT>{
        }

        /**
         * The stage of a Batch pool definition that allows the creation of a pool.
         *
         * @param <ParentT> the stage of the parent Batch account definition to return to after attaching this definition
         */
        interface WithConfig<ParentT> extends
                Attachable.InDefinition<ParentT> {
            /**
             * Specifies the network configuration for the pool
             *
             * @param networkConfiguration network configuration value
             * @return the next stage of the definition
             */
            DefinitionStages.WithConfig<ParentT> withNetworkConfiguration(NetworkConfiguration networkConfiguration);

            /**
             * Specifies the file system configuration for the pool to mount on each node
             *
             * @param mountConfigurations mount configuration value
             * @return the next stage of the definition
             */
            DefinitionStages.WithConfig<ParentT> withMountConfiguration(List<MountConfiguration> mountConfigurations);
        }
    }

    /**
     * The entirety of a Batch pool definition as a part of parent update.
     * @param <ParentT> the stage of the parent Batch account update to return to after attaching this definition
     */
    interface UpdateDefinition<ParentT> extends
            UpdateDefinitionStages.Blank<ParentT>,
            UpdateDefinitionStages.WithConfig<ParentT> {
    }

    /**
     * Grouping of pool definition stages as part of a Batch account update.
     */
    interface UpdateDefinitionStages<ParentT> {
        /**
         * The first stage of a Batch pool definition.
         *
         * @param <ParentT> the stage of the parent Batch account definition to return to after attaching this definition
         */
        interface Blank<ParentT> extends WithConfig<ParentT> {
        }

        /**
         * The stage of a Batch pool definition allowing the creation of configurations.
         *
         * @param <ParentT> the stage of the parent Batch account definition to return to after attaching this definition
         */
        interface WithConfig<ParentT> extends
                Attachable.InUpdate<ParentT> {
            /**
             * Specifies the network configuration for the pool
             *
             * @param networkConfiguration network configuration value
             * @return the next stage of the definition
             */
            UpdateDefinitionStages.WithConfig<ParentT> withNetworkConfiguration(NetworkConfiguration networkConfiguration);

            /**
             * Specifies the file system configuration for the pool to mount on each node
             *
             * @param mountConfigurations mount configuration value
             * @return the next stage of the definition
             */
            UpdateDefinitionStages.WithConfig<ParentT> withMountConfiguration(List<MountConfiguration> mountConfigurations);

        }
    }

    /**
     * Grouping of Batch pool update stages.
     */
    interface UpdateStages{
        /**
         * The stage of a Batch pool update allowing the creation of configurations.
         */
        interface WithConfig {
            /**
             * Specifies the network configuration for the pool
             *
             * @param networkConfiguration network configuration value
             * @return the next stage of the update
             */
            Update withNetworkConfiguration(NetworkConfiguration networkConfiguration);

            /**
             * Specifies the file system configuration for the pool to mount on each node
             *
             * @param mountConfigurations mount configuration value
             * @return the next stage of the update
             */
            Update withMountConfiguration(List<MountConfiguration> mountConfigurations);
        }

    }

    /**
     * The entirety of a Batch pool update as a part of a Batch account update.
     */
    interface Update extends
            Settable<BatchAccount.Update>,
            UpdateStages.WithConfig {
    }

}
