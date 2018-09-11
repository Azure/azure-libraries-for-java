/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.sql;

import com.microsoft.rest.v2.annotations.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasParent;
import com.microsoft.azure.v2.management.resources.fluentcore.model.Executable;
import com.microsoft.azure.v2.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.v2.management.sql.implementation.ImportExtensionRequestInner;
import com.microsoft.azure.management.storage.StorageAccount;

/**
 * An immutable client-side representation of an Azure SQL Database import operation request.
 */
@Fluent
@Beta(since = "V1_7_0")
public interface SqlDatabaseImportRequest extends
    HasInner<ImportExtensionRequestInner>,
    Executable<SqlDatabaseImportExportResponse>,
    HasParent<SqlDatabase> {

    /**
     * The entirety of database import operation definition.
     */
    interface SqlDatabaseImportRequestDefinition extends
        SqlDatabaseImportRequest.DefinitionStages.ImportFrom,
        SqlDatabaseImportRequest.DefinitionStages.WithStorageTypeAndKey,
        SqlDatabaseImportRequest.DefinitionStages.WithAuthenticationTypeAndLoginPassword,
        SqlDatabaseImportRequest.DefinitionStages.WithExecute {
    }

    /**
     * Grouping of database import definition stages.
     */
    interface DefinitionStages {
        /**
         * Sets the storage URI to use.
         */
        interface ImportFrom {
            /**
             * @param storageUri the storage URI to use
             * @return next definition stage
             */
            SqlDatabaseImportRequest.DefinitionStages.WithStorageTypeAndKey importFrom(String storageUri);

            /**
             * @param storageAccount an existing storage account to be used
             * @param containerName the container name within the storage account to use
             * @param fileName the exported database file name
             */
            SqlDatabaseImportRequest.DefinitionStages.WithAuthenticationTypeAndLoginPassword importFrom(StorageAccount storageAccount, String containerName, String fileName);
        }

        /**
         * Sets the storage key type and value to use.
         */
        interface WithStorageTypeAndKey {
            /**
             * @param storageAccessKey the storage access key to use
             * @return next definition stage
             */
            SqlDatabaseImportRequest.DefinitionStages.WithAuthenticationTypeAndLoginPassword withStorageAccessKey(String storageAccessKey);

            /**
             * @param sharedAccessKey the shared access key to use; it must be preceded with a "?."
             * @return next definition stage
             */
            SqlDatabaseImportRequest.DefinitionStages.WithAuthenticationTypeAndLoginPassword withSharedAccessKey(String sharedAccessKey);
        }

        /**
         * Sets the authentication type and SQL or Active Directory administrator login and password.
         */
        interface WithAuthenticationTypeAndLoginPassword {
            /**
             * @param administratorLogin the SQL administrator login
             * @param administratorPassword the SQL administrator password
             * @return next definition stage
             */
            SqlDatabaseImportRequest.DefinitionStages.WithExecute withSqlAdministratorLoginAndPassword(String administratorLogin, String administratorPassword);

            /**
             * @param administratorLogin the Active Directory administrator login
             * @param administratorPassword the Active Directory administrator password
             * @return next definition stage
             */
            SqlDatabaseImportRequest.DefinitionStages.WithExecute withActiveDirectoryLoginAndPassword(String administratorLogin, String administratorPassword);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for execution, but also allows
         * for any other optional settings to be specified.
         */
        interface WithExecute extends
            Executable<SqlDatabaseImportExportResponse> {
        }
    }
}