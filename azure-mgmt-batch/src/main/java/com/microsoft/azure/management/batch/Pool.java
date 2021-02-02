/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.batch;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.batch.implementation.PoolInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.ExternalChildResource;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Settable;

import java.util.List;

/**
 * An immutable client-side representation of an Azure Batch account pool.
 */
@Fluent
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
     * @return the scaleSettings value
     */
    ScaleSettings scaleSettings();

    /**
     * @return the startTask value
     */
    StartTask startTask();

    /**
     * @return the metadata value
     */
    List<MetadataItem> metadata();

    /**
     * @return the applicationPackages value
     */
    List<ApplicationPackageReference> applicationPackages();

    /**
     * @return the certificates value
     */
    List<CertificateReference> certificates();

    /**
     * @return the size of virtual machine in the pool
     */
    String vmSize();

    /**
     * @return the deployment configuration value
     */
    DeploymentConfiguration deploymentConfiguration();

    /**
     * @return the display name for the pool
     */
    String displayName();

    /**
     * @return the interNodeCommunication value
     */
    InterNodeCommunicationState interNodeCommunication();

    /**
     * @return the maxTasksPerNode value
     */
    @Deprecated
    Integer maxTasksPerNode();

    /**
     * @return the taskSchedulingPolicy value
     */
    TaskSchedulingPolicy taskSchedulingPolicy();

    /**
     * @return the userAccounts value
     */
    List<UserAccount> userAccounts();

    /**
     * @return the applicationLicenses value
     */
    List<String> applicationLicenses();

    /**
     * The entirety of a Batch pool definition as a part of a Batch account definition.
     *
     * @param <ParentT> the stage of the parent Batch account definition to return to after attaching this definition
     */
    interface Definition<ParentT> extends
            DefinitionStages.Blank<ParentT>,
            DefinitionStages.WithAttach<ParentT> {
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
        interface Blank<ParentT> extends WithAttach<ParentT> {
        }

        /**
         * The stage of a Batch pool definition that allows the creation of a pool.
         *
         * @param <ParentT> the stage of the parent Batch account definition to return to after attaching this definition
         */
        interface WithAttach<ParentT> extends
                Attachable.InDefinition<ParentT> {
            /**
             * Specifies the network configuration for the pool.
             *
             * @param networkConfiguration network configuration value
             * @return the next stage of the definition
             */
            DefinitionStages.WithAttach<ParentT> withNetworkConfiguration(NetworkConfiguration networkConfiguration);

            /**
             * Specifies the file system configuration for the pool to mount on each node.
             *
             * @param mountConfigurations mount configuration value
             * @return the next stage of the definition
             */
            DefinitionStages.WithAttach<ParentT> withMountConfiguration(List<MountConfiguration> mountConfigurations);

            /**
             * Specifies the scale settings for the pool.
             *
             * @param scaleSettings scale settings value
             * @return the next stage of the definition
             */
            DefinitionStages.WithAttach<ParentT> withScaleSettings(ScaleSettings scaleSettings);

            /**
             * Specifies the start task for the pool.
             *
             * @param startTask start task value
             * @return the next stage of the definition
             */
            DefinitionStages.WithAttach<ParentT> withStartTask(StartTask startTask);

            /**
             * Specifies the metadata for the use of user code.
             *
             * @param metadata metadata value
             * @return the next stage of the definition
             */
            DefinitionStages.WithAttach<ParentT> withMetadata(List<MetadataItem> metadata);

            /**
             * Specifies the application package references affect all new compute nodes joining the pool.
             *
             * @param applicationPackages applicationPackages value
             * @return the next stage of the definition
             */
            DefinitionStages.WithAttach<ParentT> withApplicationPackages(List<ApplicationPackageReference> applicationPackages);

            /**
             * Specifies the certificates for compute nodes.
             *
             * @param certificates certificates value
             * @return the next stage of the definition
             */
            DefinitionStages.WithAttach<ParentT> withCertificates(List<CertificateReference> certificates);

            /**
             * Specifies the available sizes of virtual machine for Cloud Services pools.
             *
             * @param vmSize vmSize value
             * @return the next stage of the definition
             */
            DefinitionStages.WithAttach<ParentT> withVmSize(String vmSize);

            /**
             * Specifies the creation of nodes using CloudServiceConfiguration/VirtualMachineConfiguration.
             *
             * @param deploymentConfiguration deploymentConfiguration value
             * @return the next stage of the definition
             */
            DefinitionStages.WithAttach<ParentT> withDeploymentConfiguration(DeploymentConfiguration deploymentConfiguration);

            /**
             * Specifies the display name for the pool.
             *
             * @param displayName displayName value
             * @return the next stage of the definition
             */
            DefinitionStages.WithAttach<ParentT> withDisplayName(String displayName);

            /**
             * Specifies the restrictions on which nodes can be assigned to the pool.
             *
             * @param interNodeCommunication interNodeCommunication value
             * @return the next stage of the definition
             */
            DefinitionStages.WithAttach<ParentT> withInterNodeCommunication(InterNodeCommunicationState interNodeCommunication);

            /**
             * Specifies the maximum value of tasks to run on each node.
             *
             * @param maxTasksPerNode maxTasksPerNode value
             * @return the next stage of the definition
             */
            @Deprecated
            DefinitionStages.WithAttach<ParentT> withMaxTasksPerNode(Integer maxTasksPerNode);

            /**
             * Specifies the task scheduling policy.
             *
             * @param taskSchedulingPolicy taskSchedulingPolicy value
             * @return the next stage of the definition
             */
            DefinitionStages.WithAttach<ParentT> withTaskSchedulingPolicy(TaskSchedulingPolicy taskSchedulingPolicy);

            /**
             * Specifies the user accounts value.
             *
             * @param userAccounts userAccounts value
             * @return the next stage of the definition
             */
            DefinitionStages.WithAttach<ParentT> withUserAccounts(List<UserAccount> userAccounts);

            /**
             * Specifies the list of application licenses.
             *
             * @param applicationLicenses applicationLicenses value
             * @return the next stage of the definition
             */
            DefinitionStages.WithAttach<ParentT> withApplicationLicenses(List<String> applicationLicenses);
        }
    }

    /**
     * The entirety of a Batch pool definition as a part of parent update.
     *
     * @param <ParentT> the stage of the parent Batch account update to return to after attaching this definition
     */
    interface UpdateDefinition<ParentT> extends
            UpdateDefinitionStages.Blank<ParentT>,
            UpdateDefinitionStages.WithAttach<ParentT> {
    }

    /**
     * Grouping of pool definition stages as part of a Batch account update.
     */
    interface UpdateDefinitionStages {
        /**
         * The first stage of a Batch pool definition.
         *
         * @param <ParentT> the stage of the parent Batch account definition to return to after attaching this definition
         */
        interface Blank<ParentT> extends WithAttach<ParentT> {
        }

        /**
         * The stage of a Batch pool definition allowing the creation of configurations.
         *
         * @param <ParentT> the stage of the parent Batch account definition to return to after attaching this definition
         */
        interface WithAttach<ParentT> extends
                Attachable.InUpdate<ParentT> {
            /**
             * Specifies the network configuration for the pool.
             *
             * @param networkConfiguration network configuration value
             * @return the next stage of the definition
             */
            UpdateDefinitionStages.WithAttach<ParentT> withNetworkConfiguration(NetworkConfiguration networkConfiguration);

            /**
             * Specifies the file system configuration for the pool to mount on each node.
             *
             * @param mountConfigurations mount configuration value
             * @return the next stage of the definition
             */
            UpdateDefinitionStages.WithAttach<ParentT> withMountConfiguration(List<MountConfiguration> mountConfigurations);

            /**
             * Specifies the scale settings for the pool.
             *
             * @param scaleSettings scale settings value
             * @return the next stage of the definition
             */
            UpdateDefinitionStages.WithAttach<ParentT> withScaleSettings(ScaleSettings scaleSettings);

            /**
             * Specifies the start task for the pool.
             *
             * @param startTask start task value
             * @return the next stage of the definition
             */
            UpdateDefinitionStages.WithAttach<ParentT> withStartTask(StartTask startTask);

            /**
             * Specifies the metadata for the use of user code.
             *
             * @param metadata metadata value
             * @return the next stage of the definition
             */
            UpdateDefinitionStages.WithAttach<ParentT> withMetadata(List<MetadataItem> metadata);

            /**
             * Specifies the application package references affect all new compute nodes joining the pool.
             *
             * @param applicationPackages applicationPackages value
             * @return the next stage of the definition
             */
            UpdateDefinitionStages.WithAttach<ParentT> withApplicationPackages(List<ApplicationPackageReference> applicationPackages);

            /**
             * Specifies the certificates for compute nodes.
             *
             * @param certificates certificates value
             * @return the next stage of the definition
             */
            UpdateDefinitionStages.WithAttach<ParentT> withCertificates(List<CertificateReference> certificates);

            /**
             * Specifies the available sizes of virtual machine for Cloud Services pools.
             *
             * @param vmSize vmSize value
             * @return the next stage of the definition
             */
            UpdateDefinitionStages.WithAttach<ParentT> withVmSize(String vmSize);

            /**
             * Specifies the creation of nodes using CloudServiceConfiguration/VirtualMachineConfiguration.
             *
             * @param deploymentConfiguration deploymentConfiguration value
             * @return the next stage of the definition
             */
            UpdateDefinitionStages.WithAttach<ParentT> withDeploymentConfiguration(DeploymentConfiguration deploymentConfiguration);

            /**
             * Specifies the display name for the pool.
             *
             * @param displayName displayName value
             * @return the next stage of the definition
             */
            UpdateDefinitionStages.WithAttach<ParentT> withDisplayName(String displayName);

            /**
             * Specifies the restrictions on which nodes can be assigned to the pool.
             *
             * @param interNodeCommunication interNodeCommunication value
             * @return the next stage of the definition
             */
            UpdateDefinitionStages.WithAttach<ParentT> withInterNodeCommunication(InterNodeCommunicationState interNodeCommunication);

            /**
             * Specifies the maximum value of tasks to run on each node.
             *
             * @param maxTasksPerNode maxTasksPerNode value
             * @return the next stage of the definition
             */
            @Deprecated
            UpdateDefinitionStages.WithAttach<ParentT> withMaxTasksPerNode(Integer maxTasksPerNode);

            /**
             * Specifies the task scheduling policy.
             *
             * @param taskSchedulingPolicy taskSchedulingPolicy value
             * @return the next stage of the definition
             */
            UpdateDefinitionStages.WithAttach<ParentT> withTaskSchedulingPolicy(TaskSchedulingPolicy taskSchedulingPolicy);

            /**
             * Specifies the user accounts value.
             *
             * @param userAccounts userAccounts value
             * @return the next stage of the definition
             */
            UpdateDefinitionStages.WithAttach<ParentT> withUserAccounts(List<UserAccount> userAccounts);

            /**
             * Specifies the list of application licenses.
             *
             * @param applicationLicenses applicationLicenses value
             * @return the next stage of the definition
             */
            UpdateDefinitionStages.WithAttach<ParentT> withApplicationLicenses(List<String> applicationLicenses);
        }
    }

    /**
     * Grouping of Batch pool update stages.
     */
    interface UpdateStages {
        /**
         * The stage of a Batch pool update allowing the creation of configurations.
         */
        interface WithAttach {
            /**
             * Specifies the network configuration for the pool.
             *
             * @param networkConfiguration network configuration value
             * @return the next stage of the update
             */
            Update withNetworkConfiguration(NetworkConfiguration networkConfiguration);

            /**
             * Specifies the file system configuration for the pool to mount on each node.
             *
             * @param mountConfigurations mount configuration value
             * @return the next stage of the update
             */
            Update withMountConfiguration(List<MountConfiguration> mountConfigurations);

            /**
             * Specifies the scale settings for the pool.
             *
             * @param scaleSettings scale settings value
             * @return the next stage of the update
             */
            Update withScaleSettings(ScaleSettings scaleSettings);

            /**
             * Specifies the start task for the pool.
             *
             * @param startTask start task value
             * @return the next stage of the update
             */
            Update withStartTask(StartTask startTask);

            /**
             * Specifies the metadata for the use of user code.
             *
             * @param metadata metadata value
             * @return the next stage of the update
             */
            Update withMetadata(List<MetadataItem> metadata);

            /**
             * Specifies the application package references affect all new compute nodes joining the pool.
             *
             * @param applicationPackages applicationPackages value
             * @return the next stage of the update
             */
            Update withApplicationPackages(List<ApplicationPackageReference> applicationPackages);

            /**
             * Specifies the certificates for compute nodes.
             *
             * @param certificates certificates value
             * @return the next stage of the update
             */
            Update withCertificates(List<CertificateReference> certificates);

            /**
             * Specifies the available sizes of virtual machine for Cloud Services pools.
             *
             * @param vmSize vmSize value
             * @return the next stage of the update
             */
            Update withVmSize(String vmSize);

            /**
             * Specifies the creation of nodes using CloudServiceConfiguration/VirtualMachineConfiguration.
             *
             * @param deploymentConfiguration deploymentConfiguration value
             * @return the next stage of the update
             */
            Update withDeploymentConfiguration(DeploymentConfiguration deploymentConfiguration);

            /**
             * Specifies the display name for the pool.
             *
             * @param displayName displayName value
             * @return the next stage of the update
             */
            Update withDisplayName(String displayName);

            /**
             * Specifies the restrictions on which nodes can be assigned to the pool.
             *
             * @param interNodeCommunication interNodeCommunication value
             * @return the next stage of the update
             */
            Update withInterNodeCommunication(InterNodeCommunicationState interNodeCommunication);

            /**
             * Specifies the maximum value of tasks to run on each node.
             *
             * @param maxTasksPerNode maxTasksPerNode value
             * @return the next stage of the update
             */
            @Deprecated
            Update withMaxTasksPerNode(Integer maxTasksPerNode);

            /**
             * Specifies the task scheduling policy.
             *
             * @param taskSchedulingPolicy taskSchedulingPolicy value
             * @return the next stage of the update
             */
            Update withTaskSchedulingPolicy(TaskSchedulingPolicy taskSchedulingPolicy);

            /**
             * Specifies the user accounts value.
             *
             * @param userAccounts userAccounts value
             * @return the next stage of the update
             */
            Update withUserAccounts(List<UserAccount> userAccounts);

            /**
             * Specifies the list of application licenses.
             *
             * @param applicationLicenses applicationLicenses value
             * @return the next stage of the update
             */
            Update withApplicationLicenses(List<String> applicationLicenses);
        }

    }

    /**
     * The entirety of a Batch pool update as a part of a Batch account update.
     */
    interface Update extends
            Settable<BatchAccount.Update>,
            UpdateStages.WithAttach {
    }

}
