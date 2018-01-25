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

        interface WithDataDisks {
            WithVMSize withDataDisks(int diskSizeInGB, int diskCount, StorageAccountType storageAccountType);
        }

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
            WithCreate withPassword(String password);

            WithCreate withSshPublicKey(String sshPublicKey);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be created
         * but also allows for any other optional settings to be specified.
         */
        interface WithCreate extends
                Creatable<BatchAIFileServer>,
                Resource.DefinitionWithTags<WithCreate>,
                DefinitionStages.WithUserCredentials {
        }
    }
}