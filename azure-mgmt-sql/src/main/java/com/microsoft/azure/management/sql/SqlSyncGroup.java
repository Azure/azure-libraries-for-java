/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.resources.fluentcore.arm.models.ExternalChildResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import com.microsoft.azure.management.sql.implementation.SyncGroupInner;
import org.joda.time.DateTime;
import rx.Completable;
import rx.Observable;

/**
 * An immutable client-side representation of an Azure SQL Server Sync Group.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_9_0)
public interface SqlSyncGroup
    extends
    ExternalChildResource<SqlSyncGroup, SqlDatabase>,
    HasInner<SyncGroupInner>,
    HasResourceGroup,
    Refreshable<SqlSyncGroup>,
    Updatable<SqlSyncGroup.Update> {
    /**
     * @return name of the SQL Server to which this Sync Group belongs
     */
    String sqlServerName();

    /**
     * @return name of the SQL Database to which this Sync Group belongs
     */
    String sqlDatabaseName();

    /**
     * @return the parent SQL Database ID
     */
    String parentId();

    /**
     * @return sync interval of the sync group
     */
    int interval();

    /**
     * @return last sync time of the sync group
     */
    DateTime lastSyncTime();

    /**
     * @return conflict resolution policy of the sync group
     */
    SyncConflictResolutionPolicy conflictResolutionPolicy();

    /**
     * @return the ARM resource id of the sync database in the sync group
     */
    String syncDatabaseId();

    /**
     * @return user name for the sync group hub database credential
     */
    String databaseUserName();

    /**
     * @return sync state of the sync group
     */
    SyncGroupState syncState();

    /**
     * @return sync schema of the sync group
     */
    SyncGroupSchema schema();

    /**
     * Deletes the Sync Group resource.
     */
    @Method
    void delete();

    /**
     * Deletes the SQL Sync Group resource asynchronously.
     *
     * @return a representation of the deferred computation of this call
     */
    @Method
    Completable deleteAsync();

    /**
     * Refreshes a hub database schema.
     */
    @Method
    void refreshHubSchema();

    /**
     * Refreshes a hub database schema asynchronously.
     *
     * @return a representation of the deferred computation of this call
     */
    @Method
    Completable refreshHubSchemaAsync();

    /**
     * Gets a collection of hub database schemas.
     *
     * @return the paged list of SyncFullSchemaProperty objects if successful.
     */
    @Method
    PagedList<SqlSyncFullSchemaProperty> listHubSchemas();

    /**
     * Gets a collection of hub database schemas asynchronously.
     *
     * @return a representation of the deferred computation of this call.
     */
    @Method
    Observable<SqlSyncFullSchemaProperty> listHubSchemasAsync();

    /**
     * Gets a collection of sync group logs.
     *
     * @param startTime get logs generated after this time.
     * @param endTime get logs generated before this time.
     * @param type the types of logs to retrieve
     * @return the paged list containing the group log property objects if successful.
     */
    PagedList<SqlSyncGroupLogProperty> listLogs(String startTime, String endTime, String type);

    /**
     * Gets a collection of sync group logs asynchronously.
     *
     * @param startTime get logs generated after this time.
     * @param endTime get logs generated before this time.
     * @param type the types of logs to retrieve
     * @return a representation of the deferred computation of this call returning the group log property objects if successful.
     */
    Observable<SqlSyncGroupLogProperty> listLogsAsync(String startTime, String endTime, String type);

    /**
     * Triggers a sync group synchronization.
     */
    @Method
    void triggerSynchronization();

    /**
     * Triggers a sync group synchronization.
     *
     * @return a representation of the deferred computation of this call
     */
    @Method
    Completable triggerSynchronizationAsync();

    /**
     * Cancels a sync group synchronization.
     */
    @Method
    void cancelSynchronization();

    /**
     * Cancels a sync group synchronization asynchronously.
     *
     * @return a representation of the deferred computation of this call
     */
    @Method
    Completable cancelSynchronizationAsync();

    /**
     * @return the SQL Sync Member entry point
     */
    @Beta(Beta.SinceVersion.V1_9_0)
    SqlSyncMemberOperations.SqlSyncMemberActionsDefinition syncMembers();



    /**************************************************************
     * Fluent interfaces to provision a SQL Sync Group
     **************************************************************/

    /**
     * The template for a SQL Sync Group update operation, containing all the settings that can be modified.
     */
    @Beta(Beta.SinceVersion.V1_9_0)
    interface Update extends
        SqlSyncGroup.UpdateStages.WithSyncDatabaseId,
        SqlSyncGroup.UpdateStages.WithDatabaseUserName,
        SqlSyncGroup.UpdateStages.WithDatabasePassword,
        SqlSyncGroup.UpdateStages.WithConflictResolutionPolicy,
        SqlSyncGroup.UpdateStages.WithInterval,
        SqlSyncGroup.UpdateStages.WithSchema,
        Appliable<SqlSyncGroup> {
    }

    /**
     * Grouping of all the SQL Sync Group update stages.
     */
    @Beta(Beta.SinceVersion.V1_9_0)
    interface UpdateStages {
        /**
         * The SQL Sync Group definition to set the database ID to sync with.
         */
        @Beta(Beta.SinceVersion.V1_9_0)
        interface WithSyncDatabaseId {
            /**
             * Sets the sync database ID.
             *
             * @param databaseId the sync database ID value to set
             * @return The next stage of the definition.
             */
             SqlSyncGroup.Update withSyncDatabaseId(String databaseId);
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
            SqlSyncGroup.Update withDatabaseUserName(String userName);
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
            SqlSyncGroup.Update withDatabasePassword(String password);
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
            SqlSyncGroup.Update withSchema(SyncGroupSchema schema);
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
            @Method
            SqlSyncGroup.Update withConflictResolutionPolicyHubWins();

            /**
             * Sets the conflict resolution policy to "MemberWin".
             *
             * @return The next stage of the definition.
             */
            @Method
            SqlSyncGroup.Update withConflictResolutionPolicyMemberWins();
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
            SqlSyncGroup.Update withInterval(int interval);
        }

    }
}
