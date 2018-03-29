/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.batchai;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.batchai.implementation.BatchAIManager;
import com.microsoft.azure.management.batchai.implementation.ClusterInner;
import com.microsoft.azure.management.batchai.model.HasMountVolumes;
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
@Beta(Beta.SinceVersion.V1_6_0)
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
     * @return the entry point to Batch AI jobs management API for this cluster
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
            DefinitionStages.WithAppInsightsKey,
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

        /**
         * Specifies the name of the administrator account that gets created on each of the nodes of a cluster.
         */
        interface WithUserName {
            /**
             * @param userName the name of the administrator account
             * @return the next stage of the definition
             */
            WithUserCredentials withUserName(String userName);
        }

        /**
         * Specifies the credentials to use for authentication on each of the nodes of a cluster.
         */
        interface WithUserCredentials {
            /**
             * @param password Admin user password (linux only)
             * @return the next stage of the definition
             */
            WithScaleSettings withPassword(String password);

            /**
             * @param sshPublicKey SSH public keys used to authenticate with linux based VMs
             * @return the next stage of the definition
             */
            WithScaleSettings withSshPublicKey(String sshPublicKey);
        }

        /**
         * Specifies scale settings for the cluster.
         */
        interface WithScaleSettings {
            /**
             * If autoScale settings are specified, the system automatically scales the cluster up and down (within
             * the supplied limits) based on the pending jobs on the cluster.
             * @param minimumNodeCount the minimum number of compute nodes the cluster can have
             * @param maximumNodeCount the maximum number of compute nodes the cluster can have
             * @return the next stage of the definition
             */
            WithCreate withAutoScale(int minimumNodeCount, int maximumNodeCount);

            /**
             * If autoScale settings are specified, the system automatically scales the cluster up and down (within
             * the supplied limits) based on the pending jobs on the cluster.
             * @param minimumNodeCount the minimum number of compute nodes the cluster can have
             * @param maximumNodeCount the maximum number of compute nodes the cluster can have
             * @param initialNodeCount the number of compute nodes to allocate on cluster creation.
             * Note that this value is used only during cluster creation.
             * @return the next stage of the definition
             */
            WithCreate withAutoScale(int minimumNodeCount, int maximumNodeCount, int initialNodeCount);

            /**
             * Specifies that cluster should be scaled by manual settings.
             * @param targetNodeCount the desired number of compute nodes in the Cluster
             * @return the next stage of the definition
             */
            WithCreate withManualScale(int targetNodeCount);

            /**
             * Specifies that cluster should be scaled by manual settings.
             * @param targetNodeCount the desired number of compute nodes in the Cluster
             * @param deallocationOption determines what to do with the job(s) running on compute node if the cluster size is decreasing. The default value is requeue.
             * @return the next stage of the definition
             */
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
            @Method
            NodeSetupTask.DefinitionStages.Blank<WithCreate> defineSetupTask();
        }

        /**
         * Specifies Azure Application Insights information for performance counters reporting.
         */
        @Beta(Beta.SinceVersion.V1_8_0)
        interface WithAppInsightsResourceId {
            /**
             * @param resoureId Azure Application Insights component resource id
             * @return the next stage of the definition
             */
            WithAppInsightsKey withAppInsightsComponentId(String resoureId);
        }

        @Beta(Beta.SinceVersion.V1_8_0)
        interface WithAppInsightsKey {
            /**
             * @param instrumentationKey value of the Azure Application Insights instrumentation key
             * @return the next stage of the definition
             */
            WithCreate withInstrumentationKey(String instrumentationKey);

            /**
             * Specifies KeyVault Store and Secret which contains the value for the instrumentation key.
             * @param keyVaultId fully qualified resource Id for the Key Vault
             * @param secretUrl the URL referencing a secret in a Key Vault
             * @return the next stage of the definition
             */
            WithCreate withInstrumentationKeySecretReference(String keyVaultId, String secretUrl);
        }

        /**
         * Defines subnet for the cluster.
         */
        @Beta(Beta.SinceVersion.V1_8_0)
        interface WithSubnet {
            /**
             * @param subnetId identifier of the subnet
             * @return the next stage of the definition
             */
            WithCreate withSubnet(String subnetId);

            /**
             * @param networkId identifier of the network
             * @param subnetName subnet name
             * @return the next stage of the definition
             */
            WithCreate withSubnet(String networkId, String subnetName);
        }


        /**
         * Specifies virtual machine image.
         */
        @Beta(Beta.SinceVersion.V1_8_0)
        interface WithVirtualMachineImage {
            /**
             * Specifies virtual machine image.
             * @param publisher publisher of the image
             * @param offer offer of the image
             * @param sku sku of the image
             * @param version version of the image
             * @return the next stage of the definition
             */
            WithCreate withVirtualMachineImage(String publisher, String offer, String sku, String version);

            /**
             * Specifies virtual machine image.
             * @param publisher publisher of the image
             * @param offer offer of the image
             * @param sku sku of the image
             * @return the next stage of the definition
             */
            WithCreate withVirtualMachineImage(String publisher, String offer, String sku);

            /**
             * Computes nodes of the cluster will be created using this custom image. This is of the form
             * /subscriptions/{subscriptionId}/resourceGroups/{resourceGroup}/providers/Microsoft.Compute/images/{imageName}.
             * The virtual machine image must be in the same region and subscription as
             * the cluster. For information about the firewall settings for the Batch
             * node agent to communicate with the Batch service see
             * https://docs.microsoft.com/en-us/azure/batch/batch-api-basics#virtual-network-vnet-and-firewall-configuration.
             * Note, you need to provide publisher, offer and sku of the base OS image
             * of which the custom image has been derived from.
             * @param virtualMachineImageId the ARM resource identifier of the virtual machine image
             * @param publisher publisher of the image
             * @param offer offer of the image
             * @param sku sku of the image
             * @return the next stage of the definition
             */
            WithCreate withVirtualMachineImageId(String virtualMachineImageId, String publisher, String offer, String sku);
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
                HasMountVolumes.DefinitionStages.WithMountVolumes<WithCreate>,
                DefinitionStages.WithAppInsightsResourceId,
                DefinitionStages.WithVirtualMachineImage,
                DefinitionStages.WithSubnet,
                Resource.DefinitionWithTags<WithCreate> {
        }
    }

    /**
     * Grouping of Batch AI cluster update stages.
     */
    interface UpdateStages {
        interface WithScaleSettings {
            /**
             * If autoScale settings are specified, the system automatically scales the cluster up and down (within
             * the supplied limits) based on the pending jobs on the cluster.
             * @param minimumNodeCount the minimum number of compute nodes the cluster can have
             * @param maximumNodeCount the maximum number of compute nodes the cluster can have
             * @return the next stage of the update
             */
            Update withAutoScale(int minimumNodeCount, int maximumNodeCount);

            /**
             * If autoScale settings are specified, the system automatically scales the cluster up and down (within
             * the supplied limits) based on the pending jobs on the cluster.
             * @param minimumNodeCount the minimum number of compute nodes the cluster can have
             * @param maximumNodeCount the maximum number of compute nodes the cluster can have
             * @param initialNodeCount the number of compute nodes to allocate on cluster creation.
             * Note that this value is used only during cluster creation.
             * @return the next stage of the update
             */
            Update withAutoScale(int minimumNodeCount, int maximumNodeCount, int initialNodeCount);

            /**
             * Specifies that cluster should be scaled by manual settings.
             * @param targetNodeCount the desired number of compute nodes in the Cluster
             * @return the next stage of the update
             */
            Update withManualScale(int targetNodeCount);

            /**
             * Specifies that cluster should be scaled by manual settings.
             * @param targetNodeCount the desired number of compute nodes in the Cluster
             * @param deallocationOption determines what to do with the job(s) running on compute node if the cluster size is decreasing. The default value is requeue.
             * @return the next stage of the update
             */
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