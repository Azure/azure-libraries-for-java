/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.sql.SqlElasticPool;
import com.microsoft.azure.management.sql.SqlElasticPoolOperations;
import com.microsoft.azure.management.sql.SqlServer;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Implementation for SQL Elastic Pool operations.
 */
@LangDefinition
public class SqlElasticPoolOperationsImpl
    implements
        SqlElasticPoolOperations,
        SqlElasticPoolOperations.SqlElasticPoolActionsDefinition {

    private SqlServerManager manager;
    private SqlServerImpl sqlServer;
    private SqlElasticPoolsAsExternalChildResourcesImpl sqlElasticPools;

    SqlElasticPoolOperationsImpl(SqlServerImpl parent, SqlServerManager manager) {
        Objects.requireNonNull(manager);
        this.sqlServer = parent;
        this.manager = manager;
        this.sqlElasticPools = new SqlElasticPoolsAsExternalChildResourcesImpl(manager, "SqlElasticPool");
    }

    SqlElasticPoolOperationsImpl(SqlServerManager manager) {
        Objects.requireNonNull(manager);
        this.manager = manager;
        this.sqlElasticPools = new SqlElasticPoolsAsExternalChildResourcesImpl(manager, "SqlElasticPool");
    }

    @Override
    public SqlElasticPool getBySqlServer(String resourceGroupName, String sqlServerName, String name) {
        ElasticPoolInner inner = this.manager.inner().elasticPools().get(resourceGroupName, sqlServerName, name);
        return (inner != null) ? new SqlElasticPoolImpl(resourceGroupName, sqlServerName, inner.location(), inner.name(), inner, manager) : null;
    }

    @Override
    public Observable<SqlElasticPool> getBySqlServerAsync(final String resourceGroupName, final String sqlServerName, final String name) {
        return this.manager.inner().elasticPools().getAsync(resourceGroupName, sqlServerName, name)
            .map(new Func1<ElasticPoolInner, SqlElasticPool>() {
                @Override
                public SqlElasticPool call(ElasticPoolInner inner) {
                    return new SqlElasticPoolImpl(resourceGroupName, sqlServerName, inner.location(), inner.name(), inner, manager);
                }
            });
    }

    @Override
    public SqlElasticPool getBySqlServer(SqlServer sqlServer, String name) {
        if (sqlServer == null) {
            return null;
        }
        ElasticPoolInner inner = this.manager.inner().elasticPools().get(sqlServer.resourceGroupName(), sqlServer.name(), name);
        return (inner != null) ? new SqlElasticPoolImpl(inner.name(), (SqlServerImpl) sqlServer, inner, manager) : null;
    }

    @Override
    public Observable<SqlElasticPool> getBySqlServerAsync(final SqlServer sqlServer, String name) {
        Objects.requireNonNull(sqlServer);
        return sqlServer.manager().inner().elasticPools().getAsync(sqlServer.resourceGroupName(), sqlServer.name(), name)
            .map(new Func1<ElasticPoolInner, SqlElasticPool>() {
                @Override
                public SqlElasticPool call(ElasticPoolInner inner) {
                    return new SqlElasticPoolImpl(inner.name(), (SqlServerImpl) sqlServer, inner, manager);
                }
            });
    }

    @Override
    public SqlElasticPool get(String name) {
        if (sqlServer == null) {
            return null;
        }
        return this.getBySqlServer(this.sqlServer.resourceGroupName(), this.sqlServer.name(), name);
    }

    @Override
    public Observable<SqlElasticPool> getAsync(String name) {
        if (sqlServer == null) {
            return null;
        }
        return this.getBySqlServerAsync(this.sqlServer.resourceGroupName(), this.sqlServer.name(), name);
    }

    @Override
    public SqlElasticPool getById(String id) {
        Objects.requireNonNull(id);
        return this.getBySqlServer(ResourceUtils.groupFromResourceId(id),
            ResourceUtils.nameFromResourceId(ResourceUtils.parentRelativePathFromResourceId(id)),
            ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public Observable<SqlElasticPool> getByIdAsync(String id) {
        Objects.requireNonNull(id);
        return this.getBySqlServerAsync(ResourceUtils.groupFromResourceId(id),
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
    public void deleteBySqlServer(String resourceGroupName, String sqlServerName, String name) {
        this.manager.inner().elasticPools().delete(resourceGroupName, sqlServerName, name);
    }

    @Override
    public Completable deleteBySqlServerAsync(String resourceGroupName, String sqlServerName, String name) {
        return this.manager.inner().elasticPools().deleteAsync(resourceGroupName, sqlServerName, name).toCompletable();
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
    public List<SqlElasticPool> list() {
        if (sqlServer == null) {
            return null;
        }
        return this.listBySqlServer(this.sqlServer.resourceGroupName(), this.sqlServer.name());
    }

    @Override
    public Observable<SqlElasticPool> listAsync() {
        if (sqlServer == null) {
            return null;
        }
        return this.listBySqlServerAsync(this.sqlServer.resourceGroupName(), this.sqlServer.name());
    }

    @Override
    public List<SqlElasticPool> listBySqlServer(String resourceGroupName, String sqlServerName) {
        List<SqlElasticPool> elasticPoolSet = new ArrayList<>();
        for (ElasticPoolInner inner : this.manager.inner().elasticPools().listByServer(resourceGroupName, sqlServerName)) {
            elasticPoolSet.add(new SqlElasticPoolImpl(resourceGroupName, sqlServerName, inner.location(), inner.name(), inner, manager));
        }
        return Collections.unmodifiableList(elasticPoolSet);
    }

    @Override
    public Observable<SqlElasticPool> listBySqlServerAsync(final String resourceGroupName, final String sqlServerName) {
        return this.manager.inner().elasticPools().listByServerAsync(resourceGroupName, sqlServerName)
            .flatMap(new Func1<List<ElasticPoolInner>, Observable<ElasticPoolInner>>() {
                @Override
                public Observable<ElasticPoolInner> call(List<ElasticPoolInner> elasticPoolInners) {
                    return Observable.from(elasticPoolInners);
                }
            })
            .map(new Func1<ElasticPoolInner, SqlElasticPool>() {
                @Override
                public SqlElasticPool call(ElasticPoolInner inner) {
                    return new SqlElasticPoolImpl(resourceGroupName, sqlServerName, inner.location(), inner.name(), inner, manager);
                }
            });
    }

    @Override
    public List<SqlElasticPool> listBySqlServer(SqlServer sqlServer) {
        List<SqlElasticPool> elasticPoolSet = new ArrayList<>();
        if (sqlServer != null) {
            for (ElasticPoolInner inner : this.manager.inner().elasticPools().listByServer(sqlServer.resourceGroupName(), sqlServer.name())) {
                elasticPoolSet.add(new SqlElasticPoolImpl(inner.name(), (SqlServerImpl) sqlServer, inner, manager));
            }
        }
        return Collections.unmodifiableList(elasticPoolSet);
    }

    @Override
    public Observable<SqlElasticPool> listBySqlServerAsync(final SqlServer sqlServer) {
        Objects.requireNonNull(sqlServer);
        return sqlServer.manager().inner().elasticPools().listByServerAsync(sqlServer.resourceGroupName(), sqlServer.name())
            .flatMap(new Func1<List<ElasticPoolInner>, Observable<ElasticPoolInner>>() {
                @Override
                public Observable<ElasticPoolInner> call(List<ElasticPoolInner> elasticPoolInners) {
                    return Observable.from(elasticPoolInners);
                }
            })
            .map(new Func1<ElasticPoolInner, SqlElasticPool>() {
                @Override
                public SqlElasticPool call(ElasticPoolInner inner) {
                    return new SqlElasticPoolImpl(inner.name(), (SqlServerImpl) sqlServer, inner, manager);
                }
            });
    }

    @Override
    public SqlElasticPoolImpl define(String name) {
        SqlElasticPoolImpl result = sqlElasticPools.defineIndependentElasticPool(name);
        return (this.sqlServer != null) ? result.withExistingSqlServer(this.sqlServer) : result;
    }
}
