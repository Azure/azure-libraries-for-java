/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerservice;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.models.ChildResource;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * A client-side representation for a Kubernetes cluster agent pool.
 */
@Fluent
@Beta(SinceVersion.V1_4_0)
public interface KubernetesClusterAgentPool
    extends
    ChildResource<OrchestratorServiceBase>,
    HasInner<ContainerServiceAgentPoolProfile> {

    /**
     * @return the number of agents (VMs) to host docker containers
     */
    int count();

    /**
     * @return size of agent VMs
     */
    ContainerServiceVMSizeTypes vmSize();

    /**
     * @return OS Disk Size in GB set for every machine in the agent pool
     */
    int osDiskSizeInGB();

    /**
     * @return OS type set for every machine in the agent pool
     */
    OSType osType();

    /**
     * @return the storage kind set for every machine in the agent pool
     */
    ContainerServiceStorageProfileTypes storageProfile();

    // Fluent interfaces

    /**
     * The entirety of a container service agent pool definition as a part of a parent definition.
     * @param <ParentT>  the stage of the container service definition to return to after attaching this definition
     */
    interface Definition<ParentT> extends
        DefinitionStages.Blank<ParentT>,
        DefinitionStages.WithVMSize<ParentT>,
        DefinitionStages.WithAttach<ParentT> {
    }

    /**
     * Grouping of container service agent pool definition stages as a part of parent container service definition.
     */
    interface DefinitionStages {

        /**
         * The first stage of a container service agent pool definition.
         *
         * @param <ParentT>  the stage of the container service definition to return to after attaching this definition
         */
        interface Blank<ParentT> {
            /**
             * Specifies the number of agents (VMs) to host docker containers.
             * @param count the count
             * @return the next stage of the definition
             */
            WithVMSize<ParentT> withVMCount(int count);
        }

        /**
         * The stage of a container service agent pool definition allowing to specify the agent VM size.
         *
         * @param <ParentT>  the stage of the container service definition to return to after attaching this definition
         */
        interface WithVMSize<ParentT> {
            /**
             * Specifies the size of the agents VMs.
             * @param vmSize the size of the VM
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withVMSize(ContainerServiceVMSizeTypes vmSize);
        }

        /**
         * The stage of a container service agent pool definition allowing to specify the agent pool OS type.
         *
         * @param <ParentT>  the stage of the container service definition to return to after attaching this definition
         */
        interface WithOSType<ParentT> {
            /**
             * OS type to be used for every machine in the agent pool.
             *
             * Default is Linux.
             * @param osType OS type to be used for every machine in the agent pool
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withOSType(OSType osType);
        }

        /**
         * The stage of a container service agent pool definition allowing to specify the agent pool OS disk size.
         *
         * @param <ParentT>  the stage of the container service definition to return to after attaching this definition
         */
        interface WithOSDiskSize<ParentT> {
            /**
             * OS Disk Size in GB to be used for every machine in the agent pool.
             *
             * If you specify 0, the default osDisk size will be used according to the vmSize specified.
             * @param osDiskSizeInGB OS Disk Size in GB to be used for every machine in the agent pool
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withOSDiskSizeInGB(int osDiskSizeInGB);
        }

        /** The final stage of a container service agent pool definition.
         * At this stage, any remaining optional settings can be specified, or the container service agent pool
         * can be attached to the parent container service definition.
         * @param <ParentT> the stage of the container service definition to return to after attaching this definition
         */
        interface WithAttach<ParentT> extends
            WithOSType<ParentT>,
            WithOSDiskSize<ParentT>,
            Attachable.InDefinition<ParentT> {
        }

    }
}
