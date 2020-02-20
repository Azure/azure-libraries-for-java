/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.sql;

import com.azure.management.resources.fluentcore.arm.models.HasResourceGroup;
import com.azure.management.resources.fluentcore.arm.models.Resource;
import com.azure.management.resources.fluentcore.model.Appliable;
import com.azure.management.resources.fluentcore.model.HasInner;
import com.azure.management.resources.fluentcore.model.Refreshable;
import com.azure.management.resources.fluentcore.model.Updatable;
import com.azure.management.sql.implementation.FailoverGroupInner;
import rx.Completable;

import java.util.List;

/**
 * An immutable client-side representation of an Azure SQL Failover Group.
 */
@Fluent
public interface SqlFailoverGroup
    extends
        Resource,
        HasInner<FailoverGroupInner>,
        HasResourceGroup,
        Refreshable<SqlFailoverGroup>,
        Updatable<SqlFailoverGroup.Update> {

    /**
     * @return name of the SQL Server to which this Failover Group belongs
     */
    String sqlServerName();

    /**
     * @return the parent SQL server ID
     */
    String parentId();

    /**
     * @return the failover policy of the read-write endpoint for the failover group
     */
    ReadWriteEndpointFailoverPolicy readWriteEndpointPolicy();

    /**
     * @return the grace period before failover with data loss is attempted for the read-write endpoint
     */
    int readWriteEndpointDataLossGracePeriodMinutes();

    /**
     * @return the failover policy of the read-only endpoint for the failover group
     */
    ReadOnlyEndpointFailoverPolicy readOnlyEndpointPolicy();

    /**
     * @return the local replication role of the failover group instance
     */
    FailoverGroupReplicationRole replicationRole();

    /**
     * @return the replication state of the failover group instance
     */
    String replicationState();

    /**
     * @return the list of partner server information for the failover group
     */
    List<PartnerInfo> partnerServers();

    /**
     * @return the list of database IDs in the failover group
     */
    List<String> databases();

    /**
     * Deletes the Failover Group.
     */
    void delete();

    /**
     * Deletes the Failover Group asynchronously.
     *
     * @return a representation of the deferred computation of this call
     */
    Completable deleteAsync();


    /**
     * The template for a SQL Failover Group update operation, containing all the settings that can be modified.
     */
    interface Update extends
        SqlFailoverGroup.UpdateStages.WithReadWriteEndpointPolicy,
        SqlFailoverGroup.UpdateStages.WithReadOnlyEndpointPolicy,
        SqlFailoverGroup.UpdateStages.WithDatabase,
        Resource.UpdateWithTags<SqlFailoverGroup.Update>,
        Appliable<SqlFailoverGroup> {
    }

    /**
     * Grouping of all the SQL Virtual Network Rule update stages.
     */
    interface UpdateStages {
        /**
         * The SQL Failover Group update definition to set the read-write endpoint failover policy.
         */
        interface WithReadWriteEndpointPolicy {
            /**
             * Sets the SQL Failover Group read-write endpoint failover policy as "Automatic".
             *
             * @param gracePeriodInMinutes the grace period before failover with data loss is attempted for the read-write endpoint
             * @return the next stage of the definition
             */
            SqlFailoverGroup.Update withAutomaticReadWriteEndpointPolicyAndDataLossGracePeriod(int gracePeriodInMinutes);

            /**
             * Sets the SQL Failover Group read-write endpoint failover policy as "Manual".
             *
             * @return the next stage of the definition
             */
            SqlFailoverGroup.Update withManualReadWriteEndpointPolicy();
        }

        /**
         * The SQL Failover Group update definition to set the failover policy of the read-only endpoint.
         */
        interface WithReadOnlyEndpointPolicy {
            /**
             * Sets the SQL Failover Group failover policy of the read-only endpoint to "Enabled".
             *
             * @return The next stage of the definition.
             */
            SqlFailoverGroup.Update withReadOnlyEndpointPolicyEnabled();

            /**
             * Sets the SQL Failover Group failover policy of the read-only endpoint to "Disabled".
             *
             * @return the next stage of the definition
             */
            SqlFailoverGroup.Update withReadOnlyEndpointPolicyDisabled();
        }

        /**
         * The SQL Failover Group update definition to set the partner servers.
         */
        interface WithDatabase {
            /**
             * Sets the SQL Failover Group database.
             *
             * @param id the ID of the database
             * @return The next stage of the definition.
             */
            SqlFailoverGroup.Update withNewDatabaseId(String id);

            /**
             * Sets the SQL Failover Group partner servers.
             *
             * @param ids the IDs of the databases
             * @return the next stage of the definition
             */
            SqlFailoverGroup.Update withDatabaseIds(String... ids);

            /**
             * Removes the SQL Failover Group database.
             *
             * @param id the ID of the database to be removed
             * @return The next stage of the definition.
             */
            SqlFailoverGroup.Update withoutDatabaseId(String id);
        }
    }
}
