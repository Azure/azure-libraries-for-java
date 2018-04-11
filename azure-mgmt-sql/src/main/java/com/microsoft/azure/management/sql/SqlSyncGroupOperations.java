/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import rx.Observable;

/**
 * A representation of the Azure SQL Sync Group operations.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_9_0)
public interface SqlSyncGroupOperations extends
    SupportsCreating<SqlSyncGroupOperations.DefinitionStages.WithSqlServer> {

    /**
     * Gets the information about a child resource from Azure SQL server, identifying it by its name and its resource group.
     *
     * @param resourceGroupName the name of resource group
     * @param sqlServerName the name of SQL server resource
     * @param databaseName the name of SQL Database parent resource
     * @param name the name of the child resource
     * @return an immutable representation of the resource
     */
    SqlSyncGroup getBySqlServer(String resourceGroupName, String sqlServerName, String databaseName, String name);

    /**
     * Asynchronously gets the information about a child resource from Azure SQL server, identifying it by its name and its resource group.
     *
     * @param resourceGroupName the name of resource group
     * @param sqlServerName the name of SQL server parent resource
     * @param databaseName the name of SQL Database parent resource
     * @param name the name of the child resource
     * @return a representation of the deferred computation of this call returning the found resource
     */
    Observable<SqlSyncGroup> getBySqlServerAsync(String resourceGroupName, String sqlServerName, String databaseName, String name);

    /**
     * Gets a collection of sync database ids.
     *
     * @param locationName The name of the region where the resource is located.
     * @return a paged list of database IDs if successful.
     */
    PagedList<String> listSyncDatabaseIds(String locationName);

    /**
     * Gets a collection of sync database ids.
     *
     * @param locationName The name of the region where the resource is located.
     * @return a paged list of database IDs if successful.
     */
    Observable<String> listSyncDatabaseIdsAsync(String locationName);

    /**
     * Gets a collection of sync database ids.
     *
     * @param region the region where the resource is located.
     * @return a paged list of database IDs if successful.
     */
    PagedList<String> listSyncDatabaseIds(Region region);

    /**
     * Gets a collection of sync database ids.
     *
     * @param region the region where the resource is located.
     * @return a paged list of database IDs if successful.
     */
    Observable<String> listSyncDatabaseIdsAsync(Region region);


    /**
     * Container interface for all the definitions that need to be implemented.
     */
    @Beta(Beta.SinceVersion.V1_9_0)
    interface SqlSyncGroupOperationsDefinition extends
        SqlSyncGroupOperations.DefinitionStages.WithSqlServer,
        SqlSyncGroupOperations.DefinitionStages.WithSyncGroupDatabase,
        SqlSyncGroupOperations.DefinitionStages.WithSyncDatabaseId,
        SqlSyncGroupOperations.DefinitionStages.WithDatabaseUserName,
        SqlSyncGroupOperations.DefinitionStages.WithDatabasePassword,
        SqlSyncGroupOperations.DefinitionStages.WithConflictResolutionPolicy,
        SqlSyncGroupOperations.DefinitionStages.WithInterval,
        SqlSyncGroupOperations.DefinitionStages.WithSchema,
        SqlSyncGroupOperations.DefinitionStages.WithCreate {
    }

    /**
     * Grouping of all the SQL Sync Group definition stages.
     */
    @Beta(Beta.SinceVersion.V1_9_0)
    interface DefinitionStages {
        /**
         * The first stage of the SQL Sync Group definition.
         */
        @Beta(Beta.SinceVersion.V1_9_0)
        interface WithSqlServer {
            /**
             * Sets the parent SQL server name and resource group it belongs to.
             *
             * @param resourceGroupName the name of the resource group the parent SQL server
             * @param sqlServerName     the parent SQL server name
             * @return The next stage of the definition.
             */
            SqlSyncGroupOperations.DefinitionStages.WithSyncGroupDatabase withExistingSqlServer(String resourceGroupName, String sqlServerName);

            /**
             * Sets the parent SQL server for the new Sync Group.
             *
             * @param sqlDatabase the parent SQL database
             * @return The next stage of the definition.
             */
            SqlSyncGroupOperations.DefinitionStages.WithSyncDatabaseId withExistingSqlDatabase(SqlDatabase sqlDatabase);
        }

        /**
         * The SQL Sync Group definition to set the parent database name.
         */
        @Beta(Beta.SinceVersion.V1_9_0)
        interface WithSyncGroupDatabase {
            /**
             * Sets the name of the database on which the sync group is hosted.
             *
             * @param databaseName the name of the database on which the sync group is hosted
             * @return The next stage of the definition.
             */
            SqlSyncGroupOperations.DefinitionStages.WithSyncDatabaseId withExistingDatabaseName(String databaseName);
        }

        /**
         * The SQL Sync Group definition to set the database ID to sync with.
         */
        @Beta(Beta.SinceVersion.V1_9_0)
        interface WithSyncDatabaseId {
            /**
             * Sets the sync database ID.
             *
             * @param syncDatabaseId the sync database ID value to set
             * @return The next stage of the definition.
             */
            SqlSyncGroupOperations.DefinitionStages.WithDatabaseUserName withSyncDatabaseId(String syncDatabaseId);
        }

        /**
         * The SQL Sync Group definition to set the database user name.
         */
        @Beta(Beta.SinceVersion.V1_9_0)
        interface WithDatabaseUserName {
            /**
             * Sets the database user name.
             *
             * @param userName the database user name
             * @return The next stage of the definition.
             */
            SqlSyncGroupOperations.DefinitionStages.WithDatabasePassword withDatabaseUserName(String userName);
        }

        /**
         * The SQL Sync Group definition to set the database login password.
         */
        @Beta(Beta.SinceVersion.V1_9_0)
        interface WithDatabasePassword {
            /**
             * Sets the database login password.
             *
             * @param password the database login password
             * @return The next stage of the definition.
             */
            SqlSyncGroupOperations.DefinitionStages.WithConflictResolutionPolicy withDatabasePassword(String password);
        }

        /**
         * The SQL Sync Group definition to set the conflict resolution policy.
         */
        @Beta(Beta.SinceVersion.V1_9_0)
        interface WithConflictResolutionPolicy {
            /**
             * Sets the conflict resolution policy to "HubWin".
             *
             * @return The next stage of the definition.
             */
            SqlSyncGroupOperations.DefinitionStages.WithCreate withConflictResolutionPolicyHubWins();

            /**
             * Sets the conflict resolution policy to "MemberWin".
             *
             * @return The next stage of the definition.
             */
            SqlSyncGroupOperations.DefinitionStages.WithCreate withConflictResolutionPolicyMemberWins();
        }

        /**
         * The SQL Sync Group definition to set the sync frequency.
         */
        @Beta(Beta.SinceVersion.V1_9_0)
        interface WithInterval {
            /**
             * Sets the sync frequency.
             *
             * @param interval the sync frequency; set to -1 for manual sync
             * @return The next stage of the definition.
             */
            SqlSyncGroupOperations.DefinitionStages.WithCreate withInterval(int interval);
        }

        /**
         * The SQL Sync Group definition to set the schema.
         */
        @Beta(Beta.SinceVersion.V1_9_0)
        interface WithSchema {
            /**
             * Sets the schema.
             *
             * @param schema the schema object to set
             * @return The next stage of the definition.
             */
            SqlSyncGroupOperations.DefinitionStages.WithCreate withSchema(SyncGroupSchema schema);
        }

        /**
         * The final stage of the SQL Sync Group definition.
         */
        interface WithCreate extends
            WithInterval,
            WithSchema,
            Creatable<SqlSyncGroup> {
        }
    }

    /**
     * Grouping of the Azure SQL Server Sync Group common actions.
     */
    @Beta(Beta.SinceVersion.V1_9_0)
    interface SqlSyncGroupActionsDefinition extends SqlChildrenOperations.SqlChildrenActionsDefinition<SqlSyncGroup> {
        /**
         * Begins the definition of a new SQL Sync Group to be added to this server.
         *
         * @param syncGroupName the name of the new SQL Sync Group
         * @return the first stage of the new SQL Virtual Network Rule definition
         */
        SqlSyncGroupOperations.DefinitionStages.WithSyncDatabaseId define(String syncGroupName);
    }
}
