/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.v2.management.sql.SqlEncryptionProtector;
import com.microsoft.azure.v2.management.sql.SqlEncryptionProtectorOperations;
import com.microsoft.azure.v2.management.sql.SqlServer;
import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Implementation for SQL Encryption Protector operations.
 */
@LangDefinition
public class SqlEncryptionProtectorOperationsImpl
    implements
        SqlEncryptionProtectorOperations,
        SqlEncryptionProtectorOperations.SqlEncryptionProtectorActionsDefinition {

    protected SqlServerManager sqlServerManager;
    protected SqlServer sqlServer;

    SqlEncryptionProtectorOperationsImpl(SqlServer parent, SqlServerManager sqlServerManager) {
        Objects.requireNonNull(sqlServerManager);
        Objects.requireNonNull(parent);
        this.sqlServer = parent;
        this.sqlServerManager = sqlServerManager;
    }

    SqlEncryptionProtectorOperationsImpl(SqlServerManager sqlServerManager) {
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
    }

    @Override
    public SqlEncryptionProtector getBySqlServer(String resourceGroupName, String sqlServerName) {
        EncryptionProtectorInner encryptionProtectorInner = this.sqlServerManager.inner().encryptionProtectors()
            .get(resourceGroupName, sqlServerName);
        return encryptionProtectorInner != null ? new SqlEncryptionProtectorImpl(resourceGroupName, sqlServerName, encryptionProtectorInner, this.sqlServerManager) : null;
    }

    @Override
    public Observable<SqlEncryptionProtector> getBySqlServerAsync(final String resourceGroupName, final String sqlServerName) {
        final SqlEncryptionProtectorOperationsImpl self = this;
        return this.sqlServerManager.inner().encryptionProtectors()
            .getAsync(resourceGroupName, sqlServerName)
                .map(encryptionProtectorInner -> (SqlEncryptionProtector) new SqlEncryptionProtectorImpl(resourceGroupName, sqlServerName, encryptionProtectorInner, self.sqlServerManager))
                .toObservable();
    }

    @Override
    public SqlEncryptionProtector getBySqlServer(SqlServer sqlServer) {
        Objects.requireNonNull(sqlServer);
        EncryptionProtectorInner encryptionProtectorInner = sqlServer.manager().inner().encryptionProtectors()
            .get(sqlServer.resourceGroupName(), sqlServer.name());
        return encryptionProtectorInner != null ? new SqlEncryptionProtectorImpl((SqlServerImpl) sqlServer, encryptionProtectorInner, sqlServer.manager()) : null;
    }

    @Override
    public Observable<SqlEncryptionProtector> getBySqlServerAsync(final SqlServer sqlServer) {
        Objects.requireNonNull(sqlServer);
        return sqlServer.manager().inner().encryptionProtectors()
                .getAsync(sqlServer.resourceGroupName(), sqlServer.name())
                .map(encryptionProtectorInner -> (SqlEncryptionProtector) new SqlEncryptionProtectorImpl((SqlServerImpl) sqlServer, encryptionProtectorInner, sqlServer.manager()))
                .toObservable();
    }

    @Override
    public SqlEncryptionProtector get() {
        if (this.sqlServer == null) {
            return null;
        }
        return this.getBySqlServer(this.sqlServer);
    }

    @Override
    public Observable<SqlEncryptionProtector> getAsync() {
        if (this.sqlServer == null) {
            return null;
        }
        return this.getBySqlServerAsync(this.sqlServer);
    }

    @Override
    public SqlEncryptionProtector getById(String id) {
        Objects.requireNonNull(id);
        return this.getBySqlServer(ResourceUtils.groupFromResourceId(id),
            ResourceUtils.nameFromResourceId(ResourceUtils.parentRelativePathFromResourceId(id)));
    }

    @Override
    public Observable<SqlEncryptionProtector> getByIdAsync(String id) {
        Objects.requireNonNull(id);
        return this.getBySqlServerAsync(ResourceUtils.groupFromResourceId(id),
            ResourceUtils.nameFromResourceId(ResourceUtils.parentRelativePathFromResourceId(id)));
    }

    @Override
    public List<SqlEncryptionProtector> list() {
        if (this.sqlServer == null) {
            return null;
        }
        return this.listBySqlServer(this.sqlServer);
    }

    @Override
    public Observable<SqlEncryptionProtector> listAsync() {
        if (sqlServer == null) {
            return null;
        }
        return this.listBySqlServerAsync(this.sqlServer);
    }

    @Override
    public List<SqlEncryptionProtector> listBySqlServer(String resourceGroupName, String sqlServerName) {
        List<SqlEncryptionProtector> encryptionProtectors = new ArrayList<>();
        List<EncryptionProtectorInner> encryptionProtectorInners = this.sqlServerManager.inner().encryptionProtectors()
            .listByServer(resourceGroupName, sqlServerName);
        if (encryptionProtectorInners != null) {
            for (EncryptionProtectorInner inner : encryptionProtectorInners) {
                encryptionProtectors.add(new SqlEncryptionProtectorImpl(resourceGroupName, sqlServerName, inner, this.sqlServerManager));
            }
        }
        return Collections.unmodifiableList(encryptionProtectors);
    }

    @Override
    public Observable<SqlEncryptionProtector> listBySqlServerAsync(final String resourceGroupName, final String sqlServerName) {
        final SqlEncryptionProtectorOperationsImpl self = this;
        return this.sqlServerManager.inner().encryptionProtectors()
            .listByServerAsync(resourceGroupName, sqlServerName)
            .flatMap(encryptionProtectorInnerPage -> Observable.fromIterable(encryptionProtectorInnerPage.items()))
            .map(encryptionProtectorInner -> new SqlEncryptionProtectorImpl(resourceGroupName, sqlServerName, encryptionProtectorInner, self.sqlServerManager));
    }

    @Override
    public List<SqlEncryptionProtector> listBySqlServer(SqlServer sqlServer) {
        Objects.requireNonNull(sqlServer);
        List<SqlEncryptionProtector> encryptionProtectors = new ArrayList<>();
        List<EncryptionProtectorInner> encryptionProtectorInners = sqlServer.manager().inner().encryptionProtectors()
            .listByServer(sqlServer.resourceGroupName(), sqlServer.name());
        if (encryptionProtectorInners != null) {
            for (EncryptionProtectorInner inner : encryptionProtectorInners) {
                encryptionProtectors.add(new SqlEncryptionProtectorImpl((SqlServerImpl) sqlServer, inner, sqlServer.manager()));
            }
        }
        return Collections.unmodifiableList(encryptionProtectors);
    }

    @Override
    public Observable<SqlEncryptionProtector> listBySqlServerAsync(final SqlServer sqlServer) {
        Objects.requireNonNull(sqlServer);
        return sqlServer.manager().inner().encryptionProtectors()
            .listByServerAsync(sqlServer.resourceGroupName(), sqlServer.name())
            .flatMap(encryptionProtectorInnerPage -> Observable.fromIterable(encryptionProtectorInnerPage.items()))
            .map(encryptionProtectorInner -> new SqlEncryptionProtectorImpl((SqlServerImpl) sqlServer, encryptionProtectorInner, sqlServer.manager()));
    }
}
