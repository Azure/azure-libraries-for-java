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
import com.microsoft.azure.management.sql.implementation.SyncMemberInner;
import rx.Completable;
import rx.Observable;

/**
 * An immutable client-side representation of an Azure SQL Server Sync Member.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_9_0)
public interface SqlSyncMember
    extends
        ExternalChildResource<SqlSyncMember, SqlSyncGroup>,
        HasInner<SyncMemberInner>,
        HasResourceGroup,
        Refreshable<SqlSyncMember>,
        Updatable<SqlSyncMember.Update> {
    /**
     * @return name of the SQL Server to which this Sync Member belongs
     */
    String sqlServerName();

    /**
     * @return name of the SQL Database to which this Sync Member belongs
     */
    String sqlDatabaseName();

    /**
     * @return name of the SQL Sync Group to which this Sync Member belongs
     */
    String sqlSyncGroupName();

    /**
     * @return the parent SQL Sync Group ID
     */
    String parentId();

    /**
     * @return the Database type of the sync member
     */
    SyncMemberDbType databaseType();

    /**
     * @return the ARM resource id of the sync agent in the sync member
     */
    String syncAgentId();

    /**
     * @return the SQL Database id of the sync member
     */
    String sqlServerDatabaseId();

    /**
     * @return the SQL Server name of the member database in the sync member
     */
    String memberServerName();

    /**
     * @return Database name of the member database in the sync member
     */
    String memberDatabaseName();

    /**
     * @return the user name of the member database in the sync member
     */
    String userName();

    /**
     * @return the sync direction of the sync member
     */
    SyncDirection syncDirection();

    /**
     * @return the sync state of the sync member
     */
    SyncMemberState syncState();

    /**
     * Deletes the Sync Member resource.
     */
    @Method
    void delete();

    /**
     * Deletes the SQL Member resource asynchronously.
     *
     * @return a representation of the deferred computation of this call
     */
    @Method
    Completable deleteAsync();

    /**
     * Lists the sync member database schemas.
     *
     * @return the paged list object if successful.
     */
    @Method
    PagedList<SqlSyncFullSchemaProperty> listMemberSchemas();

    /**
     * Lists the sync member database schemas asynchronously.
     *
     * @return a representation of the deferred computation of this call.
     */
    @Method
    Observable<SqlSyncFullSchemaProperty> listMemberSchemasAsync();

    /**
     * Refreshes a sync member database schema.
     */
    @Method
    void refreshMemberSchema();

    /**
     * Refreshes a sync member database schema asynchronously.
     *
     * @return a representation of the deferred computation of this call
     */
    @Method
    Completable refreshMemberSchemaAsync();



    /**************************************************************
     * Fluent interfaces to provision a SQL Sync Group
     **************************************************************/

    /**
     * The template for a SQL Sync Group update operation, containing all the settings that can be modified.
     */
    @Beta(Beta.SinceVersion.V1_9_0)
    interface Update extends
        SqlSyncMember.UpdateStages.WithMemberUserName,
        SqlSyncMember.UpdateStages.WithMemberPassword,
        SqlSyncMember.UpdateStages.WithMemberDatabaseType,
        SqlSyncMember.UpdateStages.WithSyncDirection,
        Appliable<SqlSyncMember> {
    }

    /**
     * Grouping of all the SQL Sync Group update stages.
     */
    @Beta(Beta.SinceVersion.V1_9_0)
    interface UpdateStages {
        /**
         * The SQL Sync Member definition to set the member database user name.
         */
        @Beta(Beta.SinceVersion.V1_9_0)
        interface WithMemberUserName {
            /**
             * Sets the member SQL Database username.
             *
             * @param userName the member SQL Database username value to set
             * @return The next stage of the definition.
             */
            SqlSyncMember.Update withMemberUserName(String userName);
        }

        /**
         * The SQL Sync Member definition to set the member database password.
         */
        @Beta(Beta.SinceVersion.V1_9_0)
        interface WithMemberPassword {
            /**
             * Sets the member SQL Database password.
             *
             * @param password the member SQL Database password value to set
             * @return The next stage of the definition.
             */
            SqlSyncMember.Update withMemberPassword(String password);
        }

        /**
         * The SQL Sync Member definition to set the database type.
         */
        @Beta(Beta.SinceVersion.V1_9_0)
        interface WithMemberDatabaseType {
            /**
             * Sets the member database type.
             *
             * @param databaseType the database type value to set
             * @return The next stage of the definition.
             */
            SqlSyncMember.Update withMemberDatabaseType(SyncMemberDbType databaseType);
        }

        /**
         * The SQL Sync Member definition to set the sync direction.
         */
        @Beta(Beta.SinceVersion.V1_9_0)
        interface WithSyncDirection {
            /**
             * Sets the sync direction.
             *
             * @param syncDirection the sync direction value to set
             * @return The next stage of the definition.
             */
            SqlSyncMember.Update withDatabaseType(SyncDirection syncDirection);
        }
    }
}
