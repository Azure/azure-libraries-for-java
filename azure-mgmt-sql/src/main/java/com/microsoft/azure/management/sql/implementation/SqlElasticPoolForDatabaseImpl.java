/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.sql.ElasticPoolEditions;
import com.microsoft.azure.management.sql.SqlDatabaseOperations;
import com.microsoft.azure.management.sql.SqlElasticPool;
import com.microsoft.azure.management.sql.SqlElasticPoolBasicEDTUs;
import com.microsoft.azure.management.sql.SqlElasticPoolBasicMaxEDTUs;
import com.microsoft.azure.management.sql.SqlElasticPoolBasicMinEDTUs;
import com.microsoft.azure.management.sql.SqlElasticPoolPremiumEDTUs;
import com.microsoft.azure.management.sql.SqlElasticPoolPremiumMaxEDTUs;
import com.microsoft.azure.management.sql.SqlElasticPoolPremiumMinEDTUs;
import com.microsoft.azure.management.sql.SqlElasticPoolPremiumSorage;
import com.microsoft.azure.management.sql.SqlElasticPoolStandardEDTUs;
import com.microsoft.azure.management.sql.SqlElasticPoolStandardMaxEDTUs;
import com.microsoft.azure.management.sql.SqlElasticPoolStandardMinEDTUs;
import com.microsoft.azure.management.sql.SqlElasticPoolStandardStorage;

/**
 * Implementation for SqlElasticPool as inline definition inside a SqlDatabase definition.
 */
@LangDefinition
public class SqlElasticPoolForDatabaseImpl
    implements
    SqlElasticPool.SqlElasticPoolDefinition<SqlDatabaseOperations.DefinitionStages.WithExistingDatabaseAfterElasticPool> {

    private SqlElasticPoolImpl sqlElasticPool;
    private SqlDatabaseImpl sqlDatabase;

    SqlElasticPoolForDatabaseImpl(SqlDatabaseImpl sqlDatabase, SqlElasticPoolImpl sqlElasticPool) {
        this.sqlDatabase = sqlDatabase;
        this.sqlElasticPool = sqlElasticPool;
    }

    @Override
    public SqlDatabaseImpl attach() {
        this.sqlDatabase.addParentDependency(this.sqlElasticPool);
        return this.sqlDatabase;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl withEdition(ElasticPoolEditions edition) {
        this.sqlElasticPool.withEdition(edition);
        return this;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl withBasicPool() {
        this.sqlElasticPool.withBasicPool();
        return this;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl withStandardPool() {
        this.sqlElasticPool.withStandardPool();
        return this;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl withPremiumPool() {
        this.sqlElasticPool.withPremiumPool();
        return this;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl withReservedDtu(SqlElasticPoolBasicEDTUs eDTU) {
        this.sqlElasticPool.withReservedDtu(eDTU);
        return this;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl withDatabaseDtuMax(SqlElasticPoolBasicMaxEDTUs eDTU) {
        this.sqlElasticPool.withDatabaseDtuMax(eDTU);
        return this;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl withDatabaseDtuMin(SqlElasticPoolBasicMinEDTUs eDTU) {
        this.sqlElasticPool.withDatabaseDtuMin(eDTU);
        return this;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl withReservedDtu(SqlElasticPoolStandardEDTUs eDTU) {
        this.sqlElasticPool.withReservedDtu(eDTU);
        return this;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl withDatabaseDtuMax(SqlElasticPoolStandardMaxEDTUs eDTU) {
        this.sqlElasticPool.withDatabaseDtuMax(eDTU);
        return this;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl withDatabaseDtuMin(SqlElasticPoolStandardMinEDTUs eDTU) {
        this.sqlElasticPool.withDatabaseDtuMin(eDTU);
        return this;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl withStorageCapacity(SqlElasticPoolStandardStorage storageCapacity) {
        this.sqlElasticPool.withStorageCapacity(storageCapacity);
        return this;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl withReservedDtu(SqlElasticPoolPremiumEDTUs eDTU) {
        this.sqlElasticPool.withReservedDtu(eDTU);
        return this;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl withDatabaseDtuMax(SqlElasticPoolPremiumMaxEDTUs eDTU) {
        this.sqlElasticPool.withDatabaseDtuMax(eDTU);
        return this;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl withDatabaseDtuMin(SqlElasticPoolPremiumMinEDTUs eDTU) {
        this.sqlElasticPool.withDatabaseDtuMin(eDTU);
        return this;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl withStorageCapacity(SqlElasticPoolPremiumSorage storageCapacity) {
        this.sqlElasticPool.withStorageCapacity(storageCapacity);
        return this;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl withDatabaseDtuMin(int databaseDtuMin) {
        this.sqlElasticPool.withDatabaseDtuMin(databaseDtuMin);
        return this;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl withDatabaseDtuMax(int databaseDtuMax) {
        this.sqlElasticPool.withDatabaseDtuMax(databaseDtuMax);
        return this;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl withDtu(int dtu) {
        this.sqlElasticPool.withDtu(dtu);
        return this;
    }

    @Override
    public SqlElasticPoolForDatabaseImpl withStorageCapacity(int storageMB) {
        this.sqlElasticPool.withStorageCapacity(storageMB);
        return this;
    }
}
