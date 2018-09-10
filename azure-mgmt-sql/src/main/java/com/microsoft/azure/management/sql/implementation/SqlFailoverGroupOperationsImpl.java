/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.Page;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
import com.microsoft.azure.management.sql.SqlFailoverGroup;
import com.microsoft.azure.management.sql.SqlFailoverGroupOperations;
import com.microsoft.azure.management.sql.SqlServer;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Implementation for SQL Failover Group operations.
 */
@LangDefinition
public class SqlFailoverGroupOperationsImpl
    extends
        SqlChildrenOperationsImpl<SqlFailoverGroup>
    implements
        SqlFailoverGroupOperations,
        SqlFailoverGroupOperations.SqlFailoverGroupActionsDefinition {

    SqlFailoverGroupOperationsImpl(SqlServer parent, SqlServerManager sqlServerManager) {
        super(parent, sqlServerManager);
        Objects.requireNonNull(parent);
    }

    SqlFailoverGroupOperationsImpl(SqlServerManager sqlServerManager) {
        super(null, sqlServerManager);
    }

    @Override
    public SqlFailoverGroup getBySqlServer(String resourceGroupName, String sqlServerName, String name) {
        FailoverGroupInner failoverGroupInner = this.sqlServerManager.inner().failoverGroups()
            .get(resourceGroupName, sqlServerName, name);
        return failoverGroupInner != null ? new SqlFailoverGroupImpl(name, failoverGroupInner, this.sqlServerManager) : null;
    }

    @Override
    public Observable<SqlFailoverGroup> getBySqlServerAsync(final String resourceGroupName, final String sqlServerName, final String name) {
        final SqlFailoverGroupOperationsImpl self = this;
        return this.sqlServerManager.inner().failoverGroups()
            .getAsync(resourceGroupName, sqlServerName, name)
            .map(new Func1<FailoverGroupInner, SqlFailoverGroup>() {
                @Override
                public SqlFailoverGroup call(FailoverGroupInner failoverGroupInner) {
                    return new SqlFailoverGroupImpl(name, failoverGroupInner, self.sqlServerManager);
                }
            });
    }

    @Override
    public SqlFailoverGroup getBySqlServer(SqlServer sqlServer, String name) {
        Objects.requireNonNull(sqlServer);
        FailoverGroupInner failoverGroupInner = sqlServer.manager().inner().failoverGroups()
            .get(sqlServer.resourceGroupName(), sqlServer.name(), name);
        return failoverGroupInner != null ? new SqlFailoverGroupImpl(name, (SqlServerImpl) sqlServer, failoverGroupInner, sqlServer.manager()) : null;
    }

    @Override
    public Observable<SqlFailoverGroup> getBySqlServerAsync(final SqlServer sqlServer, final String name) {
        Objects.requireNonNull(sqlServer);
        return sqlServer.manager().inner().failoverGroups()
            .getAsync(sqlServer.resourceGroupName(), sqlServer.name(), name)
            .map(new Func1<FailoverGroupInner, SqlFailoverGroup>() {
                @Override
                public SqlFailoverGroup call(FailoverGroupInner failoverGroupInner) {
                    return new SqlFailoverGroupImpl(name, (SqlServerImpl) sqlServer, failoverGroupInner, sqlServer.manager());
                }
            });
    }

    @Override
    public void deleteBySqlServer(String resourceGroupName, String sqlServerName, String name) {
        this.sqlServerManager.inner().failoverGroups().delete(resourceGroupName, sqlServerName, name);
    }

    @Override
    public Completable deleteBySqlServerAsync(String resourceGroupName, String sqlServerName, String name) {
        return this.sqlServerManager.inner().failoverGroups().deleteAsync(resourceGroupName, sqlServerName, name).toCompletable();
    }

    @Override
    public List<SqlFailoverGroup> listBySqlServer(String resourceGroupName, String sqlServerName) {
        List<SqlFailoverGroup> failoverGroups = new ArrayList<>();
        List<FailoverGroupInner> failoverGroupInners = this.sqlServerManager.inner().failoverGroups()
            .listByServer(resourceGroupName, sqlServerName);
        if (failoverGroupInners != null) {
            for (FailoverGroupInner inner : failoverGroupInners) {
                failoverGroups.add(new SqlFailoverGroupImpl(inner.name(), inner, this.sqlServerManager));
            }
        }
        return Collections.unmodifiableList(failoverGroups);
    }

    @Override
    public Observable<SqlFailoverGroup> listBySqlServerAsync(final String resourceGroupName, final String sqlServerName) {
        final SqlFailoverGroupOperationsImpl self = this;
        return this.sqlServerManager.inner().failoverGroups()
            .listByServerAsync(resourceGroupName, sqlServerName)
            .flatMap(new Func1<Page<FailoverGroupInner>, Observable<FailoverGroupInner>>() {
                @Override
                public Observable<FailoverGroupInner> call(Page<FailoverGroupInner> failoverGroupInnerPage) {
                    return Observable.from(failoverGroupInnerPage.items());
                }
            })
            .map(new Func1<FailoverGroupInner, SqlFailoverGroup>() {
                @Override
                public SqlFailoverGroup call(FailoverGroupInner failoverGroupInner) {
                    return new SqlFailoverGroupImpl(failoverGroupInner.name(), failoverGroupInner, self.sqlServerManager);
                }
            });
    }

    @Override
    public List<SqlFailoverGroup> listBySqlServer(final SqlServer sqlServer) {
        List<SqlFailoverGroup> failoverGroups = new ArrayList<>();
        List<FailoverGroupInner> failoverGroupInners = sqlServer.manager().inner().failoverGroups()
            .listByServer(sqlServer.resourceGroupName(), sqlServer.name());
        if (failoverGroupInners != null) {
            for (FailoverGroupInner inner : failoverGroupInners) {
                failoverGroups.add(new SqlFailoverGroupImpl(inner.name(), (SqlServerImpl) sqlServer, inner, this.sqlServerManager));
            }
        }
        return Collections.unmodifiableList(failoverGroups);
    }

    @Override
    public Observable<SqlFailoverGroup> listBySqlServerAsync(final SqlServer sqlServer) {
        return sqlServer.manager().inner().failoverGroups()
            .listByServerAsync(sqlServer.resourceGroupName(), sqlServer.name())
            .flatMap(new Func1<Page<FailoverGroupInner>, Observable<FailoverGroupInner>>() {
                @Override
                public Observable<FailoverGroupInner> call(Page<FailoverGroupInner> failoverGroupInnerPage) {
                    return Observable.from(failoverGroupInnerPage.items());
                }
            })
            .map(new Func1<FailoverGroupInner, SqlFailoverGroup>() {
                @Override
                public SqlFailoverGroup call(FailoverGroupInner failoverGroupInner) {
                    return new SqlFailoverGroupImpl(failoverGroupInner.name(), (SqlServerImpl) sqlServer, failoverGroupInner, sqlServer.manager());
                }
            });
    }

    @Override
    public SqlFailoverGroupImpl define(String name) {
        SqlFailoverGroupImpl result = new SqlFailoverGroupImpl(name, new FailoverGroupInner(), this.sqlServerManager);
        result.setPendingOperation(ExternalChildResourceImpl.PendingOperation.ToBeCreated);
        return (this.sqlServer != null) ? result.withExistingSqlServer(this.sqlServer) : result;
    }

    @Override
    public SqlFailoverGroup failover(String failoverGroupName) {
        Objects.requireNonNull(this.sqlServer);
        FailoverGroupInner failoverGroupInner = sqlServer.manager().inner().failoverGroups()
            .failover(sqlServer.resourceGroupName(), sqlServer.name(), failoverGroupName);
        return failoverGroupInner != null ? new SqlFailoverGroupImpl(failoverGroupInner.name(), (SqlServerImpl) this.sqlServer, failoverGroupInner, sqlServer.manager()) : null;
    }

    @Override
    public Observable<SqlFailoverGroup> failoverAsync(String failoverGroupName) {
        Objects.requireNonNull(this.sqlServer);
        final SqlFailoverGroupOperationsImpl self = this;
        return sqlServer.manager().inner().failoverGroups()
            .failoverAsync(sqlServer.resourceGroupName(), sqlServer.name(), failoverGroupName)
            .map(new Func1<FailoverGroupInner, SqlFailoverGroup>() {
                @Override
                public SqlFailoverGroup call(FailoverGroupInner failoverGroupInner) {
                    return new SqlFailoverGroupImpl(failoverGroupInner.name(), (SqlServerImpl) sqlServer, failoverGroupInner, sqlServer.manager());
                }
            });
    }

    @Override
    public SqlFailoverGroup forceFailoverAllowDataLoss(String failoverGroupName) {
        Objects.requireNonNull(this.sqlServer);
        FailoverGroupInner failoverGroupInner = sqlServer.manager().inner().failoverGroups()
            .forceFailoverAllowDataLoss(sqlServer.resourceGroupName(), sqlServer.name(), failoverGroupName);
        return failoverGroupInner != null ? new SqlFailoverGroupImpl(failoverGroupInner.name(), (SqlServerImpl) this.sqlServer, failoverGroupInner, sqlServer.manager()) : null;
    }

    @Override
    public Observable<SqlFailoverGroup> forceFailoverAllowDataLossAsync(String failoverGroupName) {
        Objects.requireNonNull(this.sqlServer);
        final SqlFailoverGroupOperationsImpl self = this;
        return sqlServer.manager().inner().failoverGroups()
            .forceFailoverAllowDataLossAsync(sqlServer.resourceGroupName(), sqlServer.name(), failoverGroupName)
            .map(new Func1<FailoverGroupInner, SqlFailoverGroup>() {
                @Override
                public SqlFailoverGroup call(FailoverGroupInner failoverGroupInner) {
                    return new SqlFailoverGroupImpl(failoverGroupInner.name(), (SqlServerImpl) sqlServer, failoverGroupInner, sqlServer.manager());
                }
            });
    }

    @Override
    public SqlFailoverGroup failover(String resourceGroupName, String serverName, String failoverGroupName) {
        FailoverGroupInner failoverGroupInner = this.sqlServerManager.inner().failoverGroups()
            .failover(resourceGroupName, serverName, failoverGroupName);
        return failoverGroupInner != null ? new SqlFailoverGroupImpl(failoverGroupInner.name(), failoverGroupInner, this.sqlServerManager) : null;
    }

    @Override
    public Observable<SqlFailoverGroup> failoverAsync(final String resourceGroupName, final String serverName, final String failoverGroupName) {
        final SqlFailoverGroupOperationsImpl self = this;
        return this.sqlServerManager.inner().failoverGroups()
            .failoverAsync(resourceGroupName, serverName, failoverGroupName)
            .map(new Func1<FailoverGroupInner, SqlFailoverGroup>() {
                @Override
                public SqlFailoverGroup call(FailoverGroupInner failoverGroupInner) {
                    return new SqlFailoverGroupImpl(failoverGroupInner.name(), failoverGroupInner, self.sqlServerManager);
                }
            });
    }

    @Override
    public SqlFailoverGroup forceFailoverAllowDataLoss(String resourceGroupName, String serverName, String failoverGroupName) {
        FailoverGroupInner failoverGroupInner = this.sqlServerManager.inner().failoverGroups()
            .forceFailoverAllowDataLoss(resourceGroupName, serverName, failoverGroupName);
        return failoverGroupInner != null ? new SqlFailoverGroupImpl(failoverGroupInner.name(), failoverGroupInner, this.sqlServerManager) : null;
    }

    @Override
    public Observable<SqlFailoverGroup> forceFailoverAllowDataLossAsync(final String resourceGroupName, final String serverName, String failoverGroupName) {
        final SqlFailoverGroupOperationsImpl self = this;
        return this.sqlServerManager.inner().failoverGroups()
            .forceFailoverAllowDataLossAsync(resourceGroupName, serverName, failoverGroupName)
            .map(new Func1<FailoverGroupInner, SqlFailoverGroup>() {
                @Override
                public SqlFailoverGroup call(FailoverGroupInner failoverGroupInner) {
                    return new SqlFailoverGroupImpl(failoverGroupInner.name(), failoverGroupInner, self.sqlServerManager);
                }
            });
    }
}
