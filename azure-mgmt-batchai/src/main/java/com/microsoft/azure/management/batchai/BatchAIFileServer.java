/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.batchai;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.batchai.implementation.BatchAIManager;
import com.microsoft.azure.management.batchai.implementation.FileServerInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import org.joda.time.DateTime;

/**
 * Entry point for Batch AI file server management API in Azure.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_6_0)
public interface BatchAIFileServer extends
        GroupableResource<BatchAIManager, FileServerInner>,
        Refreshable<BatchAIFileServer> {

    /**
     * @return the size of the virtual machine of the File Server.
     * For information about available VM sizes for File Server from the
     * Virtual Machines Marketplace, see Sizes for Virtual Machines (Linux).
     */
    String vmSize();

    /**
     * @return SSH settings for the File Server
     */
    SshConfiguration sshConfiguration();

    /**
     * @return settings for the data disk which would be created for the File Server
     */
    DataDisks dataDisks();

    /**
     * @return the identifier of the subnet
     */
    ResourceId subnet();

    /**
     * @return details of the File Server
     */
    MountSettings mountSettings();

    /**
     * @return time when the status was changed
     */
    DateTime provisioningStateTransitionTime();

    /**
     * @return time when the FileServer was created
     */
    DateTime creationTime();

    /**
     * @return the provisioning state of the File Server
     */
    FileServerProvisioningState provisioningState();

    /**
     * The entirety of a Batch AI file server definition.
     */
    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.WithGroup,
            DefinitionStages.WithDataDisks,
            DefinitionStages.WithVMSize,
            DefinitionStages.WithUserName,
            DefinitionStages.WithUserCredentials,
            DefinitionStages.WithCreate {
    }

    /**
     * Grouping of Batch AI file server definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of a Batch AI file server definition.
         */
        interface Blank extends DefinitionWithRegion<WithGroup> {
        }

        /**
         * The stage of a Batch AI file server definition allowing the resource group to be specified.
         */
        interface WithGroup extends GroupableResource.DefinitionStages.WithGroup<WithDataDisks> {
        }

        /**
         * This stage of a Batch AI file server definition allows to specify data disks parameters.
         */
        interface WithDataDisks {
            /**
             * Specifies settings for the data disks which would be created for the file server.
             * @param diskSizeInGB initial disk size in GB for blank data disks
             * @param diskCount number of data disks to be attached to the VM. RAID level 0 will be applied in the case of multiple disks.
             * @param storageAccountType type of storage account to be used on the disk
             * @return the next stage of the definition
             */
            WithVMSize withDataDisks(int diskSizeInGB, int diskCount, StorageAccountType storageAccountType);

            /**
             * Specifies settings for the data disks which would be created for the file server.
             * @param diskSizeInGB initial disk size in GB for blank data disks.
             * @param diskCount number of data disks to be attached to the VM. RAID level 0 will be applied in the case of multiple disks.
             * @param storageAccountType type of storage account to be used on the disk
             * @param cachingType caching type
             * @return the next stage of the definition
             */
            WithVMSize withDataDisks(int diskSizeInGB, int diskCount, StorageAccountType storageAccountType, CachingType cachingType);
        }

        /**
         * This stage of a Batch AI file server definition allows to specify virtual machine size.
         */
        interface WithVMSize {
            /**
             * Specifies size of the virtual machine of the File Server.
             * @param vmSize virtual machine size
             * @return next stage of the definition
             */
            WithUserName withVMSize(String vmSize);
        }

        /**
         * This stage of a Batch AI file server definition allows to specify administrator account name.
         */
        interface WithUserName {
            /**
             * Specifies admin user name.
             * @param userName the name of the administrator account
             * @return the next stage of the definition
             */
            WithUserCredentials withUserName(String userName);
        }

        /**
         * This stage of a Batch AI file server definition allows to specify user credentials.
         */
        interface WithUserCredentials {
            /**
             * Specifies admin user password.
             * @param password admin user Password (linux only)
             * @return the next stage of the definition
             */
            WithCreate withPassword(String password);

            /**
             * Specifies public key for authentication.
             * @param sshPublicKey SSH public keys used to authenticate with linux based VMs
             * @return the next stage of the definition
             */
            WithCreate withSshPublicKey(String sshPublicKey);
        }

        /**
         * Defines subnet for the file server.
         */
        @Beta(Beta.SinceVersion.V1_8_0)
        interface WithSubnet {
            /**
             * Specifies subnet id.
             * @param subnetId identifier of the subnet
             * @return the next stage of the definition
             */
            WithCreate withSubnet(String subnetId);

            /**
             * Specifies network id and subnet name within this network.
             * @param networkId identifier of the network
             * @param subnetName subnet name
             * @return the next stage of the definition
             */
            WithCreate withSubnet(String networkId, String subnetName);
        }


        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be created
         * but also allows for any other optional settings to be specified.
         */
        interface WithCreate extends
                Creatable<BatchAIFileServer>,
                Resource.DefinitionWithTags<WithCreate>,
                DefinitionStages.WithUserCredentials,
                DefinitionStages.WithSubnet {
        }
    }
}