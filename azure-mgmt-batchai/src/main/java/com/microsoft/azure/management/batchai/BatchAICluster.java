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
import org.joda.time.DateTime;

import java.util.List;

/**
 * Entry point for Batch AI cluster management API in Azure.
 */
@Fluent
@Beta()
public interface BatchAICluster extends
        GroupableResource<BatchAIManager, ClusterInner>,
        Refreshable<BatchAICluster>,
        Updatable<BatchAICluster.Update> {
    /**
     * All virtual machines in a cluster are the same size. For information
     * about available VM sizes for clusters using images from the Virtual
     * Machines Marketplace (see Sizes for Virtual Machines (Linux) or Sizes
     * for Virtual Machines (Windows). Batch AI service supports all Azure VM
     * sizes except STANDARD_A0 and those with premium storage (STANDARD_GS,
     * STANDARD_DS, and STANDARD_DSV2 series).
     * @return the size of the virtual machines in the cluster
     */
    String vmSize();

    /**
     * The default value is dedicated. The node can get preempted while the
     * task is running if lowpriority is choosen. This is best suited if the
     * workload is checkpointing and can be restarted.
     * @return virtual machine priority status
     */
    VmPriority vmPriority();

    /**
     * @return desired scale for the Cluster
     */
    ScaleSettings scaleSettings();

    /**
     * @return settings for OS image and mounted data volumes
     */
    VirtualMachineConfiguration virtualMachineConfiguration();

    /**
     * @return setup to be done on all compute nodes in the Cluster
     */
    NodeSetup nodeSetup();

    /**
     * @return administrator account name for compute nodes.
     */
    String adminUserName();

    /**
     * @return the identifier of the subnet
     */
    ResourceId subnet();

    /**
     * @return the creation time of the cluster
     */
    DateTime creationTime();

    /**
     * @return the provisioning state of the cluster
     */
    ProvisioningState provisioningState();

    /**
     * @return the provisioning state transition time of the cluster
     */
    DateTime provisioningStateTransitionTime();

    /**
     * Indicates whether the cluster is resizing.
     * @return cluster allocation state
     */
    AllocationState allocationState();

    /**
     * @return the time at which the cluster entered its current allocation state
     */
    DateTime allocationStateTransitionTime();

    /**
     * @return all the errors encountered by various compute nodes during node setup
     */
    List<BatchAIError> errors();

    /**
     * @return the number of compute nodes currently assigned to the cluster
     */
    int currentNodeCount();

    /**
     * @return counts of various node states on the cluster
     */
    NodeStateCounts nodeStateCounts();

    /**
     * @return the entry point to virtual network gateway connections management API for this virtual network gateway
     */
    BatchAIJobs jobs();

    /**
     * The entirety of a Batch AI cluster definition.
     */
    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.WithGroup,
            DefinitionStages.WithVMSize,
            DefinitionStages.WithUserName,
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
            WithUserName withVMSize(String vmSize);
        }

        interface WithUserName {
            WithUserCredentials withUserName(String userName);
        }

        interface WithUserCredentials {
            WithScaleSettings withPassword(String password);

            WithScaleSettings withSshPublicKey(String sshPublicKey);
        }

        interface WithScaleSettings {
            WithCreate withAutoScale(int minimumNodeCount, int maximumNodeCount);

            WithCreate withAutoScale(int minimumNodeCount, int maximumNodeCount, int initialNodeCount);

            WithCreate withManualScale(int targetNodeCount);

            WithCreate withManualScale(int targetNodeCount, DeallocationOption deallocationOption);
        }

        interface WithVMPriority {
            WithCreate withLowPriority();
        }

        /**
         * Specifies a setup task which can be used to customize the compute nodes
         * of the cluster. The task runs everytime a VM is rebooted. For
         * that reason the task code needs to be idempotent. Generally it is used
         * to either download static data that is required for all jobs that run on
         * the cluster VMs or to download/install software.
         * NOTE: The volumes specified in mountVolumes are mounted first and then the setupTask is run.
         * Therefore the setup task can use local mountPaths in its execution.
         */
        interface WithSetupTask {
            /**
             * Begins the definition of setup task.
             * @return the first stage of the setup task definition
             */
            NodeSetupTask.DefinitionStages.Blank<WithCreate> defineSetupTask();
        }

        interface WithAzureFileShare {
            AzureFileShare.DefinitionStages.Blank<WithCreate> defineAzureFileShare();
        }

        interface WithAzureBlobFileSystem {
            AzureBlobFileSystem.DefinitionStages.Blank<WithCreate> defineAzureBlobFileSystem();
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be created
         * but also allows for any other optional settings to be specified.
         */
        interface WithCreate extends
                Creatable<BatchAICluster>,
                DefinitionStages.WithUserCredentials,
                DefinitionStages.WithVMPriority,
                DefinitionStages.WithSetupTask,
                DefinitionStages.WithAzureFileShare,
                DefinitionStages.WithAzureBlobFileSystem,
                Resource.DefinitionWithTags<WithCreate> {
        }
    }

    /**
     * Grouping of Batch AI cluster update stages.
     */
    interface UpdateStages {
        interface WithScaleSettings {
            Update withAutoScale(int minimumNodeCount, int maximumNodeCount);

            Update withAutoScale(int minimumNodeCount, int maximumNodeCount, int initialNodeCount);

            Update withManualScale(int targetNodeCount);

            Update withManualScale(int targetNodeCount, DeallocationOption deallocationOption);
        }
    }

    /**
     * The template for an update operation, containing all the settings that can be modified.
     */
    interface Update extends
            Appliable<BatchAICluster>,
            UpdateStages.WithScaleSettings,
            Resource.UpdateWithTags<Update> {
    }
}