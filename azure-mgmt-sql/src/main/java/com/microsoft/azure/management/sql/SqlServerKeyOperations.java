/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import org.joda.time.DateTime;

/**
 * A representation of the Azure SQL Server Key operations.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_8_0)
public interface SqlServerKeyOperations extends
    SqlChildrenOperations<SqlServerKey> {

    /**
     * Begins a definition for a new SQL Server Key resource.
     *
     * @return the first stage of the resource definition
     */
    @Method
    SqlServerKeyOperations.DefinitionStages.WithSqlServer define();

    /**
     * Container interface for all the definitions that need to be implemented.
     */
    @Beta(Beta.SinceVersion.V1_8_0)
    interface SqlServerKeyOperationsDefinition extends
        SqlServerKeyOperations.DefinitionStages.WithSqlServer,
        SqlServerKeyOperations.DefinitionStages.WithServerKeyType,
        SqlServerKeyOperations.DefinitionStages.WithThumbprint,
        SqlServerKeyOperations.DefinitionStages.WithCreationDate,
        SqlServerKeyOperations.DefinitionStages.WithCreate {
    }

    /**
     * Grouping of all the SQL Server Key definition stages.
     */
    @Beta(Beta.SinceVersion.V1_8_0)
    interface DefinitionStages {
        /**
         * The first stage of the SQL Server Key definition.
         */
        @Beta(Beta.SinceVersion.V1_8_0)
        interface WithSqlServer {
            /**
             * Sets the parent SQL server name and resource group it belongs to.
             *
             * @param resourceGroupName the name of the resource group the parent SQL server
             * @param sqlServerName     the parent SQL server name
             * @return The next stage of the definition.
             */
            SqlServerKeyOperations.DefinitionStages.WithServerKeyType withExistingSqlServer(String resourceGroupName, String sqlServerName);

            /**
             * Sets the parent SQL server for the new Server Key.
             *
             * @param sqlServerId the parent SQL server ID
             * @return The next stage of the definition.
             */
            SqlServerKeyOperations.DefinitionStages.WithServerKeyType withExistingSqlServerId(String sqlServerId);

            /**
             * Sets the parent SQL server for the new Server Key.
             *
             * @param sqlServer the parent SQL server
             * @return The next stage of the definition.
             */
            SqlServerKeyOperations.DefinitionStages.WithServerKeyType withExistingSqlServer(SqlServer sqlServer);
        }

        /**
         * The SQL Server Key definition to set the server key type.
         */
        @Beta(Beta.SinceVersion.V1_8_0)
        interface WithServerKeyType {
            /**
             * Sets the server key type as "AzureKeyVault" and the URI to the key.
             *
             * @param uri the URI of the server key
             * @return The next stage of the definition.
             */
            SqlServerKeyOperations.DefinitionStages.WithCreate withAzureKeyVaultKey(String uri);
        }

        /**
         * The SQL Server Key definition to set the thumbprint.
         */
        @Beta(Beta.SinceVersion.V1_8_0)
        interface WithThumbprint {
            /**
             * Sets the thumbprint of the server key.
             *
             * @param thumbprint the thumbprint of the server key
             * @return The next stage of the definition.
             */
            SqlServerKeyOperations.DefinitionStages.WithCreate withThumbprint(String thumbprint);
        }

        /**
         * The SQL Server Key definition to set the server key creation date.
         */
        @Beta(Beta.SinceVersion.V1_8_0)
        interface WithCreationDate {
            /**
             * Sets the server key creation date.
             *
             * @param creationDate the server key creation date
             * @return The next stage of the definition.
             */
            SqlServerKeyOperations.DefinitionStages.WithCreate withCreationDate(DateTime creationDate);
        }

        /**
         * The final stage of the SQL Server Key definition.
         */
        interface WithCreate extends
            SqlServerKeyOperations.DefinitionStages.WithThumbprint,
            SqlServerKeyOperations.DefinitionStages.WithCreationDate,
            Creatable<SqlServerKey> {
        }
    }

    /**
     * Grouping of the Azure SQL Server Key common actions.
     */
    @Beta(Beta.SinceVersion.V1_8_0)
    interface SqlServerKeyActionsDefinition extends SqlChildrenActionsDefinition<SqlServerKey> {
        /**
         * Begins the definition of a new SQL Server key to be added to this server.
         *
         * @return the first stage of the new SQL Server key definition
         */
        @Method
        SqlServerKeyOperations.DefinitionStages.WithServerKeyType define();
    }
}
