/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasParent;
import com.microsoft.azure.v2.management.resources.fluentcore.model.Executable;
import org.joda.time.DateTime;

import java.util.List;

/**
 * A client-side representation allowing user to get troubleshooting information for virtual network gateway or virtual network gateway connection.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_4_0)
public interface Troubleshooting extends Executable<Troubleshooting>,
        HasParent<NetworkWatcher> {
    /**
     * Get the resource identifier of the target resource against which the action
     * is to be performed.
     *
     * @return the targetResourceId value
     */
    String targetResourceId();

    /**
     * @return id of the storage account where troubleshooting information was saved
     */
    String storageId();

    /**
    * @return the path to the blob to save the troubleshoot result in
    */
     String storagePath();

    /**
     * @return The start time of the troubleshooting
     */
    DateTime startTime();

    /**
     * @return the end time of the troubleshooting
     */
    DateTime endTime();

    /**
     * @return the result code of the troubleshooting
     */
    String code();

    /**
     * @return information from troubleshooting
     */
    List<TroubleshootingDetails> results();

    /**
     * The entirety of troubleshooting parameters definition.
     */
    interface Definition extends
            DefinitionStages.WithTargetResource,
            DefinitionStages.WithStorageAccount,
            DefinitionStages.WithStoragePath,
            DefinitionStages.WithExecute {
    }


    /**
     * Grouping of troubleshooting definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of troubleshooting parameters definition.
         */
        interface WithTargetResource {
            /**
             * Set the targetResourceId value (virtual network gateway or virtual network gateway connecyion id).
             *
             * @param targetResourceId the targetResourceId value to set
             * @return the next stage of definition
             */
            WithStorageAccount withTargetResourceId(String targetResourceId);
        }

        /**
         * Sets the storage account to save the troubleshoot result.
         */
        interface WithStorageAccount {
            /**
             * Set the storageAccounId value.
             *
             * @param storageAccountId the ID for the storage account to save the troubleshoot result
             * @return the next stage of definition.
             */
            WithStoragePath withStorageAccount(String storageAccountId);
        }

        /**
         * Sets the path to the blob to save the troubleshoot result in.
         */
        interface WithStoragePath {
            WithExecute withStoragePath(String storagePath);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for execution, but also allows
         * for any other optional settings to be specified.
         */
        interface WithExecute extends
                Executable<Troubleshooting> {
        }
    }
}
