/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.Page;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.sql.SqlVirtualNetworkRule;
import com.microsoft.azure.management.sql.SqlVirtualNetworkRuleOperations;
import com.microsoft.azure.management.sql.SqlServer;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Implementation for SQL Virtual Network Rule operations.
 */
@LangDefinition
public class SqlVirtualNetworkRuleOperationsImpl
    implements
        SqlVirtualNetworkRuleOperations,
        SqlVirtualNetworkRuleOperations.SqlVirtualNetworkRuleActionsDefinition {

    private SqlServerManager sqlServerManager;
    private SqlServer sqlServer;
    private SqlVirtualNetworkRulesAsExternalChildResourcesImpl sqlVirtualNetworkRules;

    SqlVirtualNetworkRuleOperationsImpl(SqlServer parent, SqlServerManager sqlServerManager) {
        Objects.requireNonNull(sqlServerManager);
        this.sqlServer = parent;
        this.sqlServerManager = sqlServerManager;
        this.sqlVirtualNetworkRules = new SqlVirtualNetworkRulesAsExternalChildResourcesImpl(sqlServerManager, "SqlVirtualNetworkRule");
    }

    SqlVirtualNetworkRuleOperationsImpl(SqlServerManager sqlServerManager) {
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        this.sqlVirtualNetworkRules = new SqlVirtualNetworkRulesAsExternalChildResourcesImpl(sqlServerManager, "SqlVirtualNetworkRule");
    }

    @Override
    public SqlVirtualNetworkRule getBySqlServer(String resourceGroupName, String sqlServerName, String name) {
        VirtualNetworkRuleInner inner = this.sqlServerManager.inner().virtualNetworkRules()
            .get(resourceGroupName, sqlServerName, name);
        return (inner != null) ? new SqlVirtualNetworkRuleImpl(resourceGroupName, sqlServerName, inner.name(), inner, sqlServerManager) : null;
    }

    @Override
    public Observable<SqlVirtualNetworkRule> getBySqlServerAsync(final String resourceGroupName, final String sqlServerName, final String name) {
        return this.sqlServerManager.inner().virtualNetworkRules()
            .getAsync(resourceGroupName, sqlServerName, name)
            .map(new Func1<VirtualNetworkRuleInner, SqlVirtualNetworkRule>() {
                @Override
                public SqlVirtualNetworkRule call(VirtualNetworkRuleInner inner) {
                    return new SqlVirtualNetworkRuleImpl(resourceGroupName, sqlServerName, inner.name(), inner, sqlServerManager);
                }
            });
    }

    @Override
    public SqlVirtualNetworkRule getBySqlServer(SqlServer sqlServer, String name) {
        if (sqlServer == null) {
            return null;
        }
        VirtualNetworkRuleInner inner = this.sqlServerManager.inner().virtualNetworkRules()
            .get(sqlServer.resourceGroupName(), sqlServer.name(), name);
        return (inner != null) ? new SqlVirtualNetworkRuleImpl(inner.name(), (SqlServerImpl) sqlServer, inner, sqlServerManager) : null;
    }

    @Override
    public SqlVirtualNetworkRule get(String name) {
        if (sqlServer == null) {
            return null;
        }
        return this.getBySqlServer(this.sqlServer.resourceGroupName(), this.sqlServer.name(), name);
    }

    @Override
    public Observable<SqlVirtualNetworkRule> getAsync(String name) {
        if (sqlServer == null) {
            return null;
        }
        return this.getBySqlServerAsync(this.sqlServer.resourceGroupName(), this.sqlServer.name(), name);
    }

    @Override
    public SqlVirtualNetworkRule getById(String id) {
        Objects.requireNonNull(id);
        return this.getBySqlServer(ResourceUtils.groupFromResourceId(id),
            ResourceUtils.nameFromResourceId(ResourceUtils.parentRelativePathFromResourceId(id)),
            ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public Observable<SqlVirtualNetworkRule> getByIdAsync(String id) {
        Objects.requireNonNull(id);
        return this.getBySqlServerAsync(ResourceUtils.groupFromResourceId(id),
            ResourceUtils.nameFromResourceId(ResourceUtils.parentRelativePathFromResourceId(id)),
            ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public void deleteBySqlServer(String resourceGroupName, String sqlServerName, String name) {
        this.sqlServerManager.inner().virtualNetworkRules().delete(resourceGroupName, sqlServerName, name);
    }

    @Override
    public Completable deleteBySqlServerAsync(String resourceGroupName, String sqlServerName, String name) {
        return this.sqlServerManager.inner().virtualNetworkRules().deleteAsync(resourceGroupName, sqlServerName, name).toCompletable();
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
    public List<SqlVirtualNetworkRule> listBySqlServer(String resourceGroupName, String sqlServerName) {
        List<SqlVirtualNetworkRule> virtualNetworkRuleSet = new ArrayList<>();
        for (VirtualNetworkRuleInner inner : this.sqlServerManager.inner().virtualNetworkRules().listByServer(resourceGroupName, sqlServerName)) {
            virtualNetworkRuleSet.add(new SqlVirtualNetworkRuleImpl(resourceGroupName, sqlServerName, inner.name(), inner, this.sqlServerManager));
        }
        return Collections.unmodifiableList(virtualNetworkRuleSet);
    }

    @Override
    public Observable<SqlVirtualNetworkRule> listBySqlServerAsync(final String resourceGroupName, final String sqlServerName) {
        return this.sqlServerManager.inner().virtualNetworkRules().listByServerAsync(resourceGroupName, sqlServerName)
            .flatMap(new Func1<Page<VirtualNetworkRuleInner>, Observable<VirtualNetworkRuleInner>>() {
                @Override
                public Observable<VirtualNetworkRuleInner> call(Page<VirtualNetworkRuleInner> virtualNetworkRuleInnerPage) {
                    return Observable.from(virtualNetworkRuleInnerPage.items());
                }
            })
            .map(new Func1<VirtualNetworkRuleInner, SqlVirtualNetworkRule>() {
                @Override
                public SqlVirtualNetworkRule call(VirtualNetworkRuleInner inner) {
                    return new SqlVirtualNetworkRuleImpl(resourceGroupName, sqlServerName, inner.name(), inner, sqlServerManager);
                }
            });
    }

    @Override
    public List<SqlVirtualNetworkRule> listBySqlServer(SqlServer sqlServer) {
        List<SqlVirtualNetworkRule> virtualNetworkRuleSet = new ArrayList<>();
        if (sqlServer != null) {
            for (VirtualNetworkRuleInner inner : this.sqlServerManager.inner().virtualNetworkRules().listByServer(sqlServer.resourceGroupName(), sqlServer.name())) {
                virtualNetworkRuleSet.add(new SqlVirtualNetworkRuleImpl(inner.name(), (SqlServerImpl) sqlServer, inner, this.sqlServerManager));
            }
        }
        return Collections.unmodifiableList(virtualNetworkRuleSet);
    }

    @Override
    public List<SqlVirtualNetworkRule> list() {
        if (sqlServer == null) {
            return null;
        }
        return this.listBySqlServer(this.sqlServer.resourceGroupName(), this.sqlServer.name());
    }

    @Override
    public Observable<SqlVirtualNetworkRule> listAsync() {
        if (sqlServer == null) {
            return null;
        }
        return this.listBySqlServerAsync(this.sqlServer.resourceGroupName(), this.sqlServer.name());
    }

    @Override
    public SqlVirtualNetworkRuleImpl define(String name) {
        SqlVirtualNetworkRuleImpl result = sqlVirtualNetworkRules.defineIndependentVirtualNetworkRule(name);
        return (this.sqlServer != null) ? result.withExistingSqlServer(this.sqlServer) : result;
    }
}
