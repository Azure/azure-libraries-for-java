/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
import com.microsoft.azure.v2.management.sql.SqlServer;
import com.microsoft.azure.v2.management.sql.SqlServerSecurityAlertPolicy;
import com.microsoft.azure.v2.management.sql.SqlServerSecurityAlertPolicyOperations;
import io.reactivex.Observable;

import java.util.Objects;

/**
 * Implementation for SQL Server Security Alert Policy operations.
 */
@LangDefinition
public class SqlServerSecurityAlertPolicyOperationsImpl
    implements
        SqlServerSecurityAlertPolicyOperations,
        SqlServerSecurityAlertPolicyOperations.SqlServerSecurityAlertPolicyActionsDefinition {

    protected SqlServerManager sqlServerManager;
    protected SqlServer sqlServer;

    SqlServerSecurityAlertPolicyOperationsImpl(SqlServer parent, SqlServerManager sqlServerManager) {
        Objects.requireNonNull(parent);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServer = parent;
        this.sqlServerManager = sqlServerManager;
    }

    SqlServerSecurityAlertPolicyOperationsImpl(SqlServerManager sqlServerManager) {
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
    }

    @Override
    public SqlServerSecurityAlertPolicyImpl define() {
        SqlServerSecurityAlertPolicyImpl result = new SqlServerSecurityAlertPolicyImpl(new ServerSecurityAlertPolicyInner(), this.sqlServerManager);
        result.setPendingOperation(ExternalChildResourceImpl.PendingOperation.ToBeCreated);
        return (this.sqlServer != null) ? result.withExistingSqlServer(this.sqlServer) : result;
    }

    @Override
    public SqlServerSecurityAlertPolicy get() {
        if (this.sqlServer == null) {
            return null;
        }
        return this.getBySqlServer(this.sqlServer);
    }

    @Override
    public Observable<SqlServerSecurityAlertPolicy> getAsync() {
        if (this.sqlServer == null) {
            return null;
        }
        return this.getBySqlServerAsync(this.sqlServer);
    }

    @Override
    public SqlServerSecurityAlertPolicy getBySqlServer(String resourceGroupName, String sqlServerName) {
        ServerSecurityAlertPolicyInner serverSecurityAlertPolicyInner = this.sqlServerManager.inner().serverSecurityAlertPolicies()
            .get(resourceGroupName, sqlServerName);
        return serverSecurityAlertPolicyInner != null ? new SqlServerSecurityAlertPolicyImpl(resourceGroupName, sqlServerName, serverSecurityAlertPolicyInner, this.sqlServerManager) : null;
    }

    @Override
    public Observable<SqlServerSecurityAlertPolicy> getBySqlServerAsync(final String resourceGroupName, final String sqlServerName) {
        final SqlServerSecurityAlertPolicyOperationsImpl self = this;
        return this.sqlServerManager.inner().serverSecurityAlertPolicies()
            .getAsync(resourceGroupName, sqlServerName)
            .map(new Func1<ServerSecurityAlertPolicyInner, SqlServerSecurityAlertPolicy>() {
                @Override
                public SqlServerSecurityAlertPolicy call(ServerSecurityAlertPolicyInner serverSecurityAlertPolicyInner) {
                    return new SqlServerSecurityAlertPolicyImpl(resourceGroupName, sqlServerName, serverSecurityAlertPolicyInner, self.sqlServerManager);
                }
            });
    }

    @Override
    public SqlServerSecurityAlertPolicy getBySqlServer(SqlServer sqlServer) {
        Objects.requireNonNull(sqlServer);
        ServerSecurityAlertPolicyInner serverSecurityAlertPolicyInner = sqlServer.manager().inner().serverSecurityAlertPolicies()
            .get(sqlServer.resourceGroupName(), sqlServer.name());
        return serverSecurityAlertPolicyInner != null ? new SqlServerSecurityAlertPolicyImpl((SqlServerImpl) sqlServer, serverSecurityAlertPolicyInner, sqlServer.manager()) : null;
    }

    @Override
    public Observable<SqlServerSecurityAlertPolicy> getBySqlServerAsync(final SqlServer sqlServer) {
        Objects.requireNonNull(sqlServer);
        return sqlServer.manager().inner().serverSecurityAlertPolicies()
            .getAsync(sqlServer.resourceGroupName(), sqlServer.name())
            .map(new Func1<ServerSecurityAlertPolicyInner, SqlServerSecurityAlertPolicy>() {
                @Override
                public SqlServerSecurityAlertPolicy call(ServerSecurityAlertPolicyInner serverSecurityAlertPolicyInner) {
                    return new SqlServerSecurityAlertPolicyImpl((SqlServerImpl) sqlServer, serverSecurityAlertPolicyInner, sqlServer.manager());
                }
            });
    }

    @Override
    public SqlServerSecurityAlertPolicy getById(String id) {
        Objects.requireNonNull(id);
        return this.getBySqlServer(ResourceUtils.groupFromResourceId(id),
            ResourceUtils.nameFromResourceId(ResourceUtils.parentRelativePathFromResourceId(id)));
    }

    @Override
    public Observable<SqlServerSecurityAlertPolicy> getByIdAsync(String id) {
        Objects.requireNonNull(id);
        return this.getBySqlServerAsync(ResourceUtils.groupFromResourceId(id),
            ResourceUtils.nameFromResourceId(ResourceUtils.parentRelativePathFromResourceId(id)));
    }
}
