/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.sql;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.arm.models.ExternalChildResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import com.microsoft.azure.management.sql.implementation.ElasticPoolInner;

import org.joda.time.DateTime;
import rx.Completable;
import rx.Observable;

import java.util.List;

/**
 * An immutable client-side representation of an Azure SQL Elastic Pool.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_7_0)
public interface SqlElasticPool
    extends
        ExternalChildResource<SqlElasticPool, SqlServer>,
        HasInner<ElasticPoolInner>,
        HasResourceGroup,
        Refreshable<SqlElasticPool>,
        Updatable<SqlElasticPool.Update> {

    /**
     * @return name of the SQL Server to which this elastic pool belongs
     */
    String sqlServerName();

    /**
     * @return the creation date of the Azure SQL Elastic Pool
     */
    DateTime creationDate();

    /**
     * @return the state of the Azure SQL Elastic Pool
     */
    ElasticPoolState state();

    /**
     * @return the edition of Azure SQL Elastic Pool
     */
    ElasticPoolEditions edition();

    /**
     * @return The total shared DTU for the SQL Azure Database Elastic Pool
     */
    int dtu();

    /**
     * @return the maximum DTU any one SQL Azure database can consume.
     */
    int databaseDtuMax();

    /**
     * @return the minimum DTU all SQL Azure Databases are guaranteed
     */
    int databaseDtuMin();

    /**
     * @return the storage limit for the SQL Azure Database Elastic Pool in MB
     */
    @Deprecated
    int storageMB();

    /**
     * @return the storage capacity limit for the SQL Azure Database Elastic Pool in MB
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    int storageCapacityInMB();

    /**
     * @return the parent SQL server ID
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    String parentId();

    /**
     * @return the name of the region the resource is in
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    String regionName();

    /**
     * @return the region the resource is in
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    Region region();


    // Actions

    /**
     * @return the information about elastic pool activities
     */
    @Method
    List<ElasticPoolActivity> listActivities();

    /**
     * @return a representation of the deferred computation of the information about elastic pool activities
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    @Method
    Observable<ElasticPoolActivity> listActivitiesAsync();

    /**
     * @return the information about elastic pool database activities
     */
    @Method
    List<ElasticPoolDatabaseActivity> listDatabaseActivities();

    /**
     * @return the information about elastic pool database activities
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    @Method
    Observable<ElasticPoolDatabaseActivity> listDatabaseActivitiesAsync();

    /**
     * Lists the database metrics for this SQL Elastic Pool.
     *
     * @param filter an OData filter expression that describes a subset of metrics to return
     * @return the elastic pool's database metrics
     */
    @Method
    @Beta(Beta.SinceVersion.V1_7_0)
    List<SqlDatabaseMetric> listDatabaseMetrics(String filter);

    /**
     * Asynchronously lists the database metrics for this SQL Elastic Pool.
     *
     * @param filter an OData filter expression that describes a subset of metrics to return
     * @return a representation of the deferred computation of this call
     */
    @Method
    @Beta(Beta.SinceVersion.V1_7_0)
    Observable<SqlDatabaseMetric> listDatabaseMetricsAsync(String filter);

    /**
     * Lists the database metric definitions for this SQL Elastic Pool.
     *
     * @return the elastic pool's metric definitions
     */
    @Method
    @Beta(Beta.SinceVersion.V1_7_0)
    List<SqlDatabaseMetricDefinition> listDatabaseMetricDefinitions();

    /**
     * Asynchronously lists the database metric definitions for this SQL Elastic Pool.
     *
     * @return a representation of the deferred computation of this call
     */
    @Method
    @Beta(Beta.SinceVersion.V1_7_0)
    Observable<SqlDatabaseMetricDefinition> listDatabaseMetricDefinitionsAsync();

    /**
     * Lists the SQL databases in this SQL Elastic Pool.
     *
     * @return the information about databases in elastic pool
     */
    @Method
    List<SqlDatabase> listDatabases();

    /**
     * Asynchronously lists the SQL databases in this SQL Elastic Pool.
     *
     * @return a representation of the deferred computation of this call
     */
    @Method
    @Beta(Beta.SinceVersion.V1_7_0)
    Observable<SqlDatabase> listDatabasesAsync();

    /**
     * Gets the specific database in the elastic pool.
     *
     * @param databaseName name of the database to look into
     * @return the information about specific database in elastic pool
     */
    SqlDatabase getDatabase(String databaseName);

    /**
     * Adds a new SQL Database to the Elastic Pool.
     *
     * @param databaseName name of the database
     * @return the database
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    SqlDatabase addNewDatabase(String databaseName);

    /**
     * Adds an existing SQL Database to the Elastic Pool.
     *
     * @param databaseName name of the database
     * @return the database
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    SqlDatabase addExistingDatabase(String databaseName);

    /**
     * Adds an existing SQL Database to the Elastic Pool.
     *
     * @param database the database to be added
     * @return the database
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    SqlDatabase addExistingDatabase(SqlDatabase database);

    /**
     * Removes an existing SQL Database from the Elastic Pool.
     *
     * @param databaseName name of the database
     * @return the database
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    SqlDatabase removeDatabase(String databaseName);

    /**
     * Deletes this SQL Elastic Pool from the parent SQL server.
     */
    @Method
    void delete();

    /**
     * Deletes this SQL Elastic Pool asynchronously from the parent SQL server.
     *
     * @return a representation of the deferred computation of this call
     */
    @Method
    Completable deleteAsync();


    /**************************************************************
     * Fluent interfaces to provision a SQL Elastic Pool
     **************************************************************/

    /**
     * Container interface for all the definitions that need to be implemented.
     *
     * @param <ParentT> the stage of the parent definition to return to after attaching this definition
     */
    interface SqlElasticPoolDefinition<ParentT> extends
            DefinitionStages.Blank<ParentT>,
            DefinitionStages.WithEdition<ParentT>,
            DefinitionStages.WithBasicEdition<ParentT>,
            DefinitionStages.WithStandardEdition<ParentT>,
            DefinitionStages.WithPremiumEdition<ParentT>,
            DefinitionStages.WithAttach<ParentT> {
    }

    /**
     * Grouping of all the storage account definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of the SQL Server definition.
         *
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface Blank<ParentT> extends SqlElasticPool.DefinitionStages.WithEdition<ParentT> {
        }

        /**
         * The SQL Elastic Pool definition to set the edition for database.
         *
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithEdition<ParentT> {
            /**
             * Sets the edition for the SQL Elastic Pool.
             *
             * @param edition edition to be set for elastic pool.
             * @return The next stage of the definition.
             */
            @Deprecated
            @Beta(Beta.SinceVersion.V1_7_0)
            SqlElasticPool.DefinitionStages.WithAttach<ParentT> withEdition(ElasticPoolEditions edition);

            /**
             * Sets the basic edition for the SQL Elastic Pool.
             *
             * @return The next stage of the definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            @Method
            SqlElasticPool.DefinitionStages.WithBasicEdition<ParentT> withBasicPool();

            /**
             * Sets the standard edition for the SQL Elastic Pool.
             *
             * @return The next stage of the definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            @Method
            SqlElasticPool.DefinitionStages.WithStandardEdition<ParentT> withStandardPool();

            /**
             * Sets the premium edition for the SQL Elastic Pool.
             *
             * @return The next stage of the definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            @Method
            SqlElasticPool.DefinitionStages.WithPremiumEdition<ParentT> withPremiumPool();
        }

        /**
         * The SQL Elastic Pool definition to set the eDTU and storage capacity limits for a basic pool.
         *
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithBasicEdition<ParentT> extends SqlElasticPool.DefinitionStages.WithAttach<ParentT> {
            /**
             * Sets the total shared eDTU for the SQL Azure Database Elastic Pool.
             *
             * @param eDTU total shared eDTU for the SQL Azure Database Elastic Pool
             * @return The next stage of the definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            SqlElasticPool.DefinitionStages.WithBasicEdition<ParentT> withReservedDtu(SqlElasticPoolBasicEDTUs eDTU);

            /**
             * Sets the maximum number of eDTU a database in the pool can consume.
             *
             * @param eDTU maximum eDTU a database in the pool can consume
             * @return The next stage of the definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            SqlElasticPool.DefinitionStages.WithBasicEdition<ParentT> withDatabaseDtuMax(SqlElasticPoolBasicMaxEDTUs eDTU);

            /**
             * Sets the minimum number of eDTU for each database in the pool are regardless of its activity.
             *
             * @param eDTU minimum eDTU for all SQL Azure databases
             * @return The next stage of the definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            SqlElasticPool.DefinitionStages.WithBasicEdition<ParentT> withDatabaseDtuMin(SqlElasticPoolBasicMinEDTUs eDTU);
        }

        /**
         * The SQL Elastic Pool definition to set the eDTU and storage capacity limits for a standard pool.
         *
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithStandardEdition<ParentT> extends SqlElasticPool.DefinitionStages.WithAttach<ParentT> {
            /**
             * Sets the total shared eDTU for the SQL Azure Database Elastic Pool.
             *
             * @param eDTU total shared eDTU for the SQL Azure Database Elastic Pool
             * @return The next stage of the definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            SqlElasticPool.DefinitionStages.WithStandardEdition<ParentT> withReservedDtu(SqlElasticPoolStandardEDTUs eDTU);

            /**
             * Sets the maximum number of eDTU a database in the pool can consume.
             *
             * @param eDTU maximum eDTU a database in the pool can consume
             * @return The next stage of the definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            SqlElasticPool.DefinitionStages.WithStandardEdition<ParentT> withDatabaseDtuMax(SqlElasticPoolStandardMaxEDTUs eDTU);

            /**
             * Sets the minimum number of eDTU for each database in the pool are regardless of its activity.
             *
             * @param eDTU minimum eDTU for all SQL Azure databases
             * @return The next stage of the definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            SqlElasticPool.DefinitionStages.WithStandardEdition<ParentT> withDatabaseDtuMin(SqlElasticPoolStandardMinEDTUs eDTU);

            /**
             * Sets the storage capacity for the SQL Azure Database Elastic Pool.
             *
             * @param storageCapacity storage capacity for the SQL Azure Database Elastic Pool
             * @return The next stage of the definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            SqlElasticPool.DefinitionStages.WithStandardEdition<ParentT> withStorageCapacity(SqlElasticPoolStandardStorage storageCapacity);
        }

        /**
         * The SQL Elastic Pool definition to set the eDTU and storage capacity limits for a premium pool.
         *
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithPremiumEdition<ParentT> extends SqlElasticPool.DefinitionStages.WithAttach<ParentT> {
            /**
             * Sets the total shared eDTU for the SQL Azure Database Elastic Pool.
             *
             * @param eDTU total shared eDTU for the SQL Azure Database Elastic Pool
             * @return The next stage of the definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            SqlElasticPool.DefinitionStages.WithPremiumEdition<ParentT> withReservedDtu(SqlElasticPoolPremiumEDTUs eDTU);

            /**
             * Sets the maximum number of eDTU a database in the pool can consume.
             *
             * @param eDTU maximum eDTU a database in the pool can consume
             * @return The next stage of the definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            SqlElasticPool.DefinitionStages.WithPremiumEdition<ParentT> withDatabaseDtuMax(SqlElasticPoolPremiumMaxEDTUs eDTU);

            /**
             * Sets the minimum number of eDTU for each database in the pool are regardless of its activity.
             *
             * @param eDTU minimum eDTU for all SQL Azure databases
             * @return The next stage of the definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            SqlElasticPool.DefinitionStages.WithPremiumEdition<ParentT> withDatabaseDtuMin(SqlElasticPoolPremiumMinEDTUs eDTU);

            /**
             * Sets the storage capacity for the SQL Azure Database Elastic Pool.
             *
             * @param storageCapacity storage capacity for the SQL Azure Database Elastic Pool
             * @return The next stage of the definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            SqlElasticPool.DefinitionStages.WithPremiumEdition<ParentT> withStorageCapacity(SqlElasticPoolPremiumSorage storageCapacity);
        }

        /**
         * The SQL Elastic Pool definition to set the minimum DTU for database.
         *
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithDatabaseDtuMin<ParentT> {
            /**
             * Sets the minimum DTU all SQL Azure Databases are guaranteed.
             *
             * @param databaseDtuMin minimum DTU for all SQL Azure databases
             * @return The next stage of the definition.
             */
            @Deprecated
            SqlElasticPool.DefinitionStages.WithAttach<ParentT> withDatabaseDtuMin(int databaseDtuMin);
        }

        /**
         * The SQL Elastic Pool definition to set the maximum DTU for one database.
         *
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithDatabaseDtuMax<ParentT> {
            /**
             * Sets the maximum DTU any one SQL Azure Database can consume.
             *
             * @param databaseDtuMax maximum DTU any one SQL Azure Database can consume
             * @return The next stage of the definition.
             */
            @Deprecated
            SqlElasticPool.DefinitionStages.WithAttach<ParentT> withDatabaseDtuMax(int databaseDtuMax);
        }

        /**
         * The SQL Elastic Pool definition to set the number of shared DTU for elastic pool.
         *
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithDtu<ParentT> {
            /**
             * Sets the total shared DTU for the SQL Azure Database Elastic Pool.
             *
             * @param dtu total shared DTU for the SQL Azure Database Elastic Pool
             * @return The next stage of the definition.
             */
            @Deprecated
            SqlElasticPool.DefinitionStages.WithAttach<ParentT> withDtu(int dtu);
        }

        /**
         * The SQL Elastic Pool definition to set the storage limit for the SQL Azure Database Elastic Pool in MB.
         *
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithStorageCapacity<ParentT> {
            /**
             * Sets the storage limit for the SQL Azure Database Elastic Pool in MB.
             *
             * @param storageMB storage limit for the SQL Azure Database Elastic Pool in MB
             * @return The next stage of the definition.
             */
            @Deprecated
            SqlElasticPool.DefinitionStages.WithAttach<ParentT> withStorageCapacity(int storageMB);
        }

        /** The final stage of the SQL Elastic Pool definition.
         * <p>
         * At this stage, any remaining optional settings can be specified, or the SQL Elastic Pool definition
         * can be attached to the parent SQL Server definition.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithAttach<ParentT> extends
                WithDatabaseDtuMin<ParentT>,
                WithDatabaseDtuMax<ParentT>,
                WithDtu<ParentT>,
                WithStorageCapacity<ParentT>,
                Attachable.InDefinition<ParentT> {
        }
    }

    /**
     * The template for a SQL Elastic Pool update operation, containing all the settings that can be modified.
     */
    interface Update extends
            UpdateStages.WithReservedDTUAndStorageCapacity,
            UpdateStages.WithDatabaseDtuMax,
            UpdateStages.WithDatabaseDtuMin,
            UpdateStages.WithDtu,
            UpdateStages.WithStorageCapacity,
            UpdateStages.WithDatabase,
            Resource.UpdateWithTags<SqlElasticPool.Update>,
            Appliable<SqlElasticPool> {
    }

    /**
     * Grouping of all the SQL Elastic Pool update stages.
     */
    interface UpdateStages {

        /**
         * The SQL Elastic Pool definition to set the minimum DTU for database.
         */
        interface WithDatabaseDtuMin {
            /**
             * Sets the minimum DTU all SQL Azure Databases are guaranteed.
             *
             * @param databaseDtuMin minimum DTU for all SQL Azure databases
             * @return The next stage of definition.
             */
            @Deprecated
            Update withDatabaseDtuMin(int databaseDtuMin);
        }

        /**
         * The SQL Elastic Pool definition to set the maximum DTU for one database.
         */
        interface WithDatabaseDtuMax {
            /**
             * Sets the maximum DTU any one SQL Azure Database can consume.
             *
             * @param databaseDtuMax maximum DTU any one SQL Azure Database can consume
             * @return The next stage of definition.
             */
            @Deprecated
            Update withDatabaseDtuMax(int databaseDtuMax);
        }

        /**
         * The SQL Elastic Pool definition to set the number of shared DTU for elastic pool.
         */
        interface WithDtu {
            /**
             * Sets the total shared DTU for the SQL Azure Database Elastic Pool.
             *
             * @param dtu total shared DTU for the SQL Azure Database Elastic Pool
             * @return The next stage of definition.
             */
            @Deprecated
            Update withDtu(int dtu);
        }

        /**
         * The SQL Elastic Pool definition to set the storage limit for the SQL Azure Database Elastic Pool in MB.
         */
        interface WithStorageCapacity {
            /**
             * Sets the storage limit for the SQL Azure Database Elastic Pool in MB.
             *
             * @param storageMB storage limit for the SQL Azure Database Elastic Pool in MB
             * @return The next stage of definition.
             */
            @Deprecated
            Update withStorageCapacity(int storageMB);
        }

        /**
         * The SQL Elastic Pool update definition to set the eDTU and storage capacity limits.
         */
        interface WithReservedDTUAndStorageCapacity {
            /**
             * Sets the total shared eDTU for the SQL Azure Database Elastic Pool.
             *
             * @param eDTU total shared eDTU for the SQL Azure Database Elastic Pool
             * @return The next stage of the update definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            Update withReservedDtu(SqlElasticPoolBasicEDTUs eDTU);

            /**
             * Sets the maximum number of eDTU a database in the pool can consume.
             *
             * @param eDTU maximum eDTU a database in the pool can consume
             * @return The next stage of the update definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            Update withDatabaseDtuMax(SqlElasticPoolBasicMaxEDTUs eDTU);

            /**
             * Sets the minimum number of eDTU for each database in the pool are regardless of its activity.
             *
             * @param eDTU minimum eDTU for all SQL Azure databases
             * @return The next stage of the update definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            Update withDatabaseDtuMin(SqlElasticPoolBasicMinEDTUs eDTU);

            /**
             * Sets the total shared eDTU for the SQL Azure Database Elastic Pool.
             *
             * @param eDTU total shared eDTU for the SQL Azure Database Elastic Pool
             * @return The next stage of the update definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            Update withReservedDtu(SqlElasticPoolStandardEDTUs eDTU);

            /**
             * Sets the maximum number of eDTU a database in the pool can consume.
             *
             * @param eDTU maximum eDTU a database in the pool can consume
             * @return The next stage of the update definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            Update withDatabaseDtuMax(SqlElasticPoolStandardMaxEDTUs eDTU);

            /**
             * Sets the minimum number of eDTU for each database in the pool are regardless of its activity.
             *
             * @param eDTU minimum eDTU for all SQL Azure databases
             * @return The next stage of the update definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            Update withDatabaseDtuMin(SqlElasticPoolStandardMinEDTUs eDTU);

            /**
             * Sets the storage capacity for the SQL Azure Database Elastic Pool.
             *
             * @param storageCapacity storage capacity for the SQL Azure Database Elastic Pool
             * @return The next stage of the update definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            Update withStorageCapacity(SqlElasticPoolStandardStorage storageCapacity);

            /**
             * Sets the total shared eDTU for the SQL Azure Database Elastic Pool.
             *
             * @param eDTU total shared eDTU for the SQL Azure Database Elastic Pool
             * @return The next stage of the update definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            Update withReservedDtu(SqlElasticPoolPremiumEDTUs eDTU);

            /**
             * Sets the maximum number of eDTU a database in the pool can consume.
             *
             * @param eDTU maximum eDTU a database in the pool can consume
             * @return The next stage of the update definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            Update withDatabaseDtuMax(SqlElasticPoolPremiumMaxEDTUs eDTU);

            /**
             * Sets the minimum number of eDTU for each database in the pool are regardless of its activity.
             *
             * @param eDTU minimum eDTU for all SQL Azure databases
             * @return The next stage of the update definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            Update withDatabaseDtuMin(SqlElasticPoolPremiumMinEDTUs eDTU);

            /**
             * Sets the storage capacity for the SQL Azure Database Elastic Pool.
             *
             * @param storageCapacity storage capacity for the SQL Azure Database Elastic Pool
             * @return The next stage of the update definition.
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            Update withStorageCapacity(SqlElasticPoolPremiumSorage storageCapacity);
        }

        /**
         * The SQL Elastic Pool definition to add the Database in the elastic pool.
         */
        interface WithDatabase {
            /**
             * Creates a new database in the SQL elastic pool.
             *
             * @param databaseName name of the new database to be added in the elastic pool
             * @return The next stage of the definition.
             */
            Update withNewDatabase(String databaseName);

            /**
             * Adds an existing database in the SQL elastic pool.
             *
             * @param databaseName name of the existing database to be added in the elastic pool
             * @return The next stage of the definition.
             */
            Update withExistingDatabase(String databaseName);

            /**
             * Adds the database in the SQL elastic pool.
             *
             * @param database database instance to be added in SQL elastic pool
             * @return The next stage of the definition.
             */
            Update withExistingDatabase(SqlDatabase database);
        }
    }
}
