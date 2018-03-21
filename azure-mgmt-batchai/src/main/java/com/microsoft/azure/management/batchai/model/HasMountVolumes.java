/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.model;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.batchai.AzureBlobFileSystem;
import com.microsoft.azure.management.batchai.AzureFileShare;
import com.microsoft.azure.management.batchai.FileServer;

/**
 * An interface representing a model's ability to attach mount volumes.
 */
@Fluent
public interface HasMountVolumes {

    /**
     * @param azureFileShare Azure fileshare to be attached
     */
    void attachAzureFileShare(AzureFileShare azureFileShare);

    /**
     * @param azureBlobFileSystem Azure blob filesystem to be attached
     */
    void attachAzureBlobFileSystem(AzureBlobFileSystem azureBlobFileSystem);

    /**
     * @param fileServer file server to be attached
     */
    void attachFileServer(FileServer fileServer);

    /**
     * Grouping of definition stages involving specifying mount volume to mount.
     */
    interface DefinitionStages {
        /**
         * Defines the volumes to mount on the cluster.
         */
        interface WithMountVolumes<ReturnT> {
            /**
             * Begins the definition of Azure file share reference to be mounted on each cluster node.
             *
             * @return the first stage of file share reference definition
             */
            @Method
            AzureFileShare.DefinitionStages.Blank<ReturnT> defineAzureFileShare();

            /**
             * Begins the definition of Azure blob file system reference to be mounted on each cluster node.
             *
             * @return the first stage of Azure blob file system reference definition
             */
            @Method
            AzureBlobFileSystem.DefinitionStages.Blank<ReturnT> defineAzureBlobFileSystem();

            /**
             * Begins the definition of Azure file server reference.
             *
             * @return the first stage of file server reference definition
             */
            @Method
            FileServer.DefinitionStages.Blank<ReturnT> defineFileServer();

            /**
             * Specifies the details of the file system to mount on the compute cluster nodes.
             *
             * @param mountCommand      command used to mount the unmanaged file system
             * @param relativeMountPath the relative path on the compute cluster node where the file system will be mounted.
             * @return the next stage of Batch AI cluster definition
             */
            ReturnT withUnmanagedFileSystem(String mountCommand, String relativeMountPath);
        }
    }
}
