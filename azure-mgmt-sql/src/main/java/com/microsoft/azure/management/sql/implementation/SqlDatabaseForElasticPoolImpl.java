/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.sql.CreateMode;
import com.microsoft.azure.management.sql.RestorePoint;
import com.microsoft.azure.management.sql.SampleName;
import com.microsoft.azure.management.sql.SqlDatabase;
import com.microsoft.azure.management.sql.SqlElasticPoolOperations;
import com.microsoft.azure.management.storage.StorageAccount;
import org.joda.time.DateTime;

import java.util.Objects;

/**
 * Implementation for SqlDatabase as inline definition inside a SqlElasticPool definition.
 */
@LangDefinition
public class SqlDatabaseForElasticPoolImpl
    implements
        SqlDatabase.DefinitionStages.WithExistingDatabaseAfterElasticPool<SqlElasticPoolOperations.DefinitionStages.WithCreate>,
        SqlDatabase.DefinitionStages.WithStorageKeyAfterElasticPool<SqlElasticPoolOperations.DefinitionStages.WithCreate>,
        SqlDatabase.DefinitionStages.WithAuthenticationAfterElasticPool<SqlElasticPoolOperations.DefinitionStages.WithCreate>,
        SqlDatabase.DefinitionStages.WithCreateMode<SqlElasticPoolOperations.DefinitionStages.WithCreate>,
        SqlDatabase.DefinitionStages.WithAttachAfterElasticPoolOptions<SqlElasticPoolOperations.DefinitionStages.WithCreate> {

    private SqlDatabaseImpl sqlDatabase;
    private SqlElasticPoolImpl sqlElasticPool;

    SqlDatabaseForElasticPoolImpl(SqlElasticPoolImpl sqlElasticPool, SqlDatabaseImpl sqlDatabase) {
        Objects.requireNonNull(sqlElasticPool);
        Objects.requireNonNull(sqlDatabase);
        Objects.requireNonNull(sqlDatabase.inner());
        this.sqlElasticPool = sqlElasticPool;
        this.sqlDatabase = sqlDatabase;
        this.sqlDatabase.inner().withLocation(sqlElasticPool.regionName());
        this.sqlDatabase.inner().withElasticPoolName(this.sqlElasticPool.name());
        this.sqlDatabase.inner().withEdition(null);
        this.sqlDatabase.inner().withRequestedServiceObjectiveId(null);
        this.sqlDatabase.inner().withRequestedServiceObjectiveName(null);
    }

    @Override
    public SqlElasticPoolImpl attach() {
//        this.sqlDatabase.addParentDependency(this.sqlElasticPool);
        return this.sqlElasticPool;
    }

    @Override
    public SqlDatabaseForElasticPoolImpl withSourceDatabase(String sourceDatabaseId) {
        this.sqlDatabase.inner().withSourceDatabaseId(sourceDatabaseId);
        return this;
    }

    @Override
    public SqlDatabaseForElasticPoolImpl withSourceDatabase(SqlDatabase sourceDatabase) {
        this.sqlDatabase.inner().withSourceDatabaseId(sourceDatabase.databaseId());
        return this;
    }

    @Override
    public SqlDatabaseForElasticPoolImpl withMode(CreateMode createMode) {
        this.sqlDatabase.withMode(createMode);
        return this;
    }

    @Override
    public SqlDatabaseForElasticPoolImpl withCollation(String collation) {
        this.sqlDatabase.withCollation(collation);
        return this;
    }

    @Override
    public SqlDatabaseForElasticPoolImpl withMaxSizeBytes(long maxSizeBytes) {
        this.sqlDatabase.withMaxSizeBytes(maxSizeBytes);
        return this;
    }

    @Override
    public SqlDatabaseForElasticPoolImpl importFrom(String storageUri) {
        this.sqlDatabase.importFrom(storageUri);
        return this;
    }

    @Override
    public SqlDatabaseForElasticPoolImpl importFrom(StorageAccount storageAccount, String containerName, String fileName) {
        this.sqlDatabase.importFrom(storageAccount, containerName, fileName);
        return this;
    }

    @Override
    public SqlDatabaseForElasticPoolImpl withStorageAccessKey(String storageAccessKey) {
        this.sqlDatabase.withStorageAccessKey(storageAccessKey);
        return this;
    }

    @Override
    public SqlDatabaseForElasticPoolImpl withSharedAccessKey(String sharedAccessKey) {
        this.sqlDatabase.withSharedAccessKey(sharedAccessKey);
        return this;
    }

    @Override
    public SqlDatabaseForElasticPoolImpl withSqlAdministratorLoginAndPassword(String administratorLogin, String administratorPassword) {
        this.sqlDatabase.withSqlAdministratorLoginAndPassword(administratorLogin, administratorPassword);
        return this;
    }

    @Override
    public SqlDatabaseForElasticPoolImpl withActiveDirectoryLoginAndPassword(String administratorLogin, String administratorPassword) {
        this.sqlDatabase.withActiveDirectoryLoginAndPassword(administratorLogin, administratorPassword);
        return this;
    }

    @Override
    public SqlDatabaseForElasticPoolImpl fromRestorePoint(RestorePoint restorePoint) {
        this.sqlDatabase.fromRestorePoint(restorePoint);
        return this;
    }

    @Override
    public SqlDatabaseForElasticPoolImpl fromRestorePoint(RestorePoint restorePoint, DateTime restorePointDateTime) {
        this.sqlDatabase.fromRestorePoint(restorePoint, restorePointDateTime);
        return this;
    }

    @Override
    public SqlDatabaseForElasticPoolImpl fromSample(SampleName sampleName) {
        this.sqlDatabase.fromSample(sampleName);
        return this;
    }
}
