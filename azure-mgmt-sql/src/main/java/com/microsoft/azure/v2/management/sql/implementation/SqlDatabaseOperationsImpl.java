/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.v2.management.sql.SqlDatabase;
import com.microsoft.azure.v2.management.sql.SqlDatabaseOperations;
import com.microsoft.azure.v2.management.sql.SqlServer;
import io.reactivex.Completable;
import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Implementation for SQL Firewall Rule operations.
 */
@LangDefinition
public class SqlDatabaseOperationsImpl
    implements
        SqlDatabaseOperations,
        SqlDatabaseOperations.SqlDatabaseActionsDefinition {

    private SqlServerManager manager;
    private SqlServerImpl sqlServer;
    private SqlDatabasesAsExternalChildResourcesImpl sqlDatabases;

    SqlDatabaseOperationsImpl(SqlServerImpl parent, SqlServerManager manager) {
        Objects.requireNonNull(manager);
        this.sqlServer = parent;
        this.manager = manager;
        this.sqlDatabases = new SqlDatabasesAsExternalChildResourcesImpl(this.sqlServer.taskGroup(), manager, "SqlDatabase");
    }

    SqlDatabaseOperationsImpl(SqlServerManager manager) {
        Objects.requireNonNull(manager);
        this.manager = manager;
        this.sqlDatabases = new SqlDatabasesAsExternalChildResourcesImpl(null, manager, "SqlDatabase");
    }

    @Override
    public SqlDatabase getBySqlServer(String resourceGroupName, String sqlServerName, String name) {
        DatabaseInner inner = this.manager.inner().databases().get(resourceGroupName, sqlServerName, name);
        return (inner != null) ? new SqlDatabaseImpl(resourceGroupName, sqlServerName, inner.location(), inner.name(), inner, manager) : null;
    }

    @Override
    public Observable<SqlDatabase> getBySqlServerAsync(final String resourceGroupName, final String sqlServerName, final String name) {
        return this.manager.inner().databases().getAsync(resourceGroupName, sqlServerName, name)
                .map(dbInner ->
                        (SqlDatabase) new SqlDatabaseImpl(resourceGroupName, sqlServerName, dbInner.location(), dbInner.name(), dbInner, manager))
                .toObservable();
    }

    @Override
    public SqlDatabase getBySqlServer(SqlServer sqlServer, String name) {
        if (sqlServer == null) {
            return null;
        }
        DatabaseInner inner = this.manager.inner().databases().get(sqlServer.resourceGroupName(), sqlServer.name(), name);
        return (inner != null) ? new SqlDatabaseImpl(inner.name(), (SqlServerImpl) sqlServer, inner, manager) : null;
    }

    @Override
    public Observable<SqlDatabase> getBySqlServerAsync(final SqlServer sqlServer, String name) {
        Objects.requireNonNull(sqlServer);
        return sqlServer.manager().inner().databases().getAsync(sqlServer.resourceGroupName(), sqlServer.name(), name)
                .map(dbInner -> (SqlDatabase) new SqlDatabaseImpl(dbInner.name(), (SqlServerImpl) sqlServer, dbInner, manager))
                .toObservable();
    }

    @Override
    public SqlDatabase get(String name) {
        if (sqlServer == null) {
            return null;
        }
        return this.getBySqlServer(this.sqlServer.resourceGroupName(), this.sqlServer.name(), name);
    }

    @Override
    public Observable<SqlDatabase> getAsync(String name) {
        if (sqlServer == null) {
            return null;
        }
        return this.getBySqlServerAsync(this.sqlServer.resourceGroupName(), this.sqlServer.name(), name);
    }

    @Override
    public SqlDatabase getById(String id) {
        Objects.requireNonNull(id);
        return this.getBySqlServer(ResourceUtils.groupFromResourceId(id),
            ResourceUtils.nameFromResourceId(ResourceUtils.parentRelativePathFromResourceId(id)),
            ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public Observable<SqlDatabase> getByIdAsync(String id) {
        Objects.requireNonNull(id);
        return this.getBySqlServerAsync(ResourceUtils.groupFromResourceId(id),
            ResourceUtils.nameFromResourceId(ResourceUtils.parentRelativePathFromResourceId(id)),
            ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public void deleteBySqlServer(String resourceGroupName, String sqlServerName, String name) {
        this.manager.inner().databases().delete(resourceGroupName, sqlServerName, name);
    }

    @Override
    public Completable deleteBySqlServerAsync(String resourceGroupName, String sqlServerName, String name) {
        return this.manager.inner().databases().deleteAsync(resourceGroupName, sqlServerName, name);
    }

    @Override
    public void deleteById(String id) {
        Objects.requireNonNull(id);
        this.deleteBySqlServer(ResourceUtils.groupFromResourceId(id),
            ResourceUtils.nameFromResourceId(ResourceUtils.parentRelativePathFromResourceId(id)),
            ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        Objects.requireNonNull(id);
        return this.deleteBySqlServerAsync(ResourceUtils.groupFromResourceId(id),
            ResourceUtils.nameFromResourceId(ResourceUtils.parentRelativePathFromResourceId(id)),
            ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public void delete(String name) {
        if (sqlServer != null) {
            this.deleteBySqlServer(this.sqlServer.resourceGroupName(), this.sqlServer.name(), name);
        }
    }

    @Override
    public Completable deleteAsync(String name) {
        if (sqlServer == null) {
            return null;
        }
        return this.deleteBySqlServerAsync(this.sqlServer.resourceGroupName(), this.sqlServer.name(), name);
    }

    @Override
    public List<SqlDatabase> listBySqlServer(String resourceGroupName, String sqlServerName) {
        List<SqlDatabase> databasesSet = new ArrayList<>();
        for (DatabaseInner inner : this.manager.inner().databases().listByServer(resourceGroupName, sqlServerName)) {
            databasesSet.add(new SqlDatabaseImpl(resourceGroupName, sqlServerName, inner.location(), inner.name(), inner, manager));
        }
        return Collections.unmodifiableList(databasesSet);
    }

    @Override
    public Observable<SqlDatabase> listBySqlServerAsync(final String resourceGroupName, final String sqlServerName) {
        return this.manager.inner().databases().listByServerAsync(resourceGroupName, sqlServerName)
                .flatMapObservable(list -> Observable.fromIterable(list))
                .map(dbInner -> new SqlDatabaseImpl(resourceGroupName, sqlServerName, dbInner.location(), dbInner.name(), dbInner, manager));
    }

    @Override
    public List<SqlDatabase> listBySqlServer(SqlServer sqlServer) {
        List<SqlDatabase> firewallRuleSet = new ArrayList<>();
        if (sqlServer != null) {
            for (DatabaseInner inner : this.manager.inner().databases().listByServer(sqlServer.resourceGroupName(), sqlServer.name())) {
                firewallRuleSet.add(new SqlDatabaseImpl(inner.name(), (SqlServerImpl) sqlServer, inner, manager));
            }
        }
        return Collections.unmodifiableList(firewallRuleSet);
    }

    @Override
    public Observable<SqlDatabase> listBySqlServerAsync(final SqlServer sqlServer) {
        return sqlServer.manager().inner().databases().listByServerAsync(sqlServer.resourceGroupName(), sqlServer.name())
                .flatMapObservable(list -> Observable.fromIterable(list))
                .map(inner -> new SqlDatabaseImpl(inner.name(), (SqlServerImpl) sqlServer, inner, sqlServer.manager()));
    }

    @Override
    public List<SqlDatabase> list() {
        if (sqlServer == null) {
            return null;
        }
        return this.listBySqlServer(this.sqlServer.resourceGroupName(), this.sqlServer.name());
    }

    @Override
    public Observable<SqlDatabase> listAsync() {
        if (sqlServer == null) {
            return null;
        }
        return this.listBySqlServerAsync(this.sqlServer.resourceGroupName(), this.sqlServer.name());
    }

    @Override
    public SqlDatabaseImpl define(String name) {
        SqlDatabaseImpl result = this.sqlDatabases.defineIndependentDatabase(name);
        return (this.sqlServer != null) ? result.withExistingSqlServer(this.sqlServer) : result;
    }
}
