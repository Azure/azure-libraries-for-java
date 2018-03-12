/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.sql.SqlFirewallRule;
import com.microsoft.azure.management.sql.SqlFirewallRuleOperations;
import com.microsoft.azure.management.sql.SqlServer;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Implementation for SQL Firewall Rule operations.
 */
@LangDefinition
public class SqlFirewallRuleOperationsImpl
    implements
        SqlFirewallRuleOperations,
        SqlFirewallRuleOperations.SqlFirewallRuleActionsDefinition {

    private SqlServerManager sqlServerManager;
    private SqlServer sqlServer;
    private SqlFirewallRulesAsExternalChildResourcesImpl sqlFirewallRules;

    SqlFirewallRuleOperationsImpl(SqlServer parent, SqlServerManager sqlServerManager) {
        Objects.requireNonNull(sqlServerManager);
        this.sqlServer = parent;
        this.sqlServerManager = sqlServerManager;
        this.sqlFirewallRules = new SqlFirewallRulesAsExternalChildResourcesImpl(sqlServerManager, "SqlFirewallRule");
    }

    SqlFirewallRuleOperationsImpl(SqlServerManager sqlServerManager) {
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        this.sqlFirewallRules = new SqlFirewallRulesAsExternalChildResourcesImpl(sqlServerManager, "SqlFirewallRule");
    }

    @Override
    public SqlFirewallRule getBySqlServer(String resourceGroupName, String sqlServerName, String name) {
        FirewallRuleInner inner = this.sqlServerManager.inner().firewallRules().get(resourceGroupName, sqlServerName, name);
        return (inner != null) ? new SqlFirewallRuleImpl(resourceGroupName, sqlServerName, inner.name(), inner, sqlServerManager) : null;
    }

    @Override
    public Observable<SqlFirewallRule> getBySqlServerAsync(final String resourceGroupName, final String sqlServerName, final String name) {
        return this.sqlServerManager.inner().firewallRules()
            .getAsync(resourceGroupName, sqlServerName, name)
            .map(new Func1<FirewallRuleInner, SqlFirewallRule>() {
                @Override
                public SqlFirewallRule call(FirewallRuleInner inner) {
                    return new SqlFirewallRuleImpl(resourceGroupName, sqlServerName, inner.name(), inner, sqlServerManager);
                }
            });
    }

    @Override
    public SqlFirewallRule getBySqlServer(SqlServer sqlServer, String name) {
        Objects.requireNonNull(sqlServer);
        FirewallRuleInner inner = this.sqlServerManager.inner().firewallRules().get(sqlServer.resourceGroupName(), sqlServer.name(), name);
        return (inner != null) ? new SqlFirewallRuleImpl(inner.name(), (SqlServerImpl) sqlServer, inner, sqlServer.manager()) : null;
    }

    @Override
    public Observable<SqlFirewallRule> getBySqlServerAsync(final SqlServer sqlServer, final String name) {
        Objects.requireNonNull(sqlServer);
        return this.sqlServerManager.inner().firewallRules()
            .getAsync(sqlServer.resourceGroupName(), sqlServer.name(), name)
            .map(new Func1<FirewallRuleInner, SqlFirewallRule>() {
                @Override
                public SqlFirewallRule call(FirewallRuleInner inner) {
                    return new SqlFirewallRuleImpl(name, (SqlServerImpl) sqlServer, inner, sqlServer.manager());
                }
            });
    }

    @Override
    public SqlFirewallRule get(String name) {
        if (this.sqlServer == null) {
            return null;
        }
        return this.getBySqlServer(this.sqlServer, name);
    }

    @Override
    public Observable<SqlFirewallRule> getAsync(String name) {
        if (this.sqlServer == null) {
            return null;
        }
        return this.getBySqlServerAsync(this.sqlServer, name);
    }

    @Override
    public SqlFirewallRule getById(String id) {
        Objects.requireNonNull(id);
        return this.getBySqlServer(ResourceUtils.groupFromResourceId(id),
            ResourceUtils.nameFromResourceId(ResourceUtils.parentRelativePathFromResourceId(id)),
            ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public Observable<SqlFirewallRule> getByIdAsync(String id) {
        Objects.requireNonNull(id);
        return this.getBySqlServerAsync(ResourceUtils.groupFromResourceId(id),
            ResourceUtils.nameFromResourceId(ResourceUtils.parentRelativePathFromResourceId(id)),
            ResourceUtils.nameFromResourceId(id));
    }

    @Override
    public void deleteBySqlServer(String resourceGroupName, String sqlServerName, String name) {
        this.sqlServerManager.inner().firewallRules().delete(resourceGroupName, sqlServerName, name);
    }

    @Override
    public Completable deleteBySqlServerAsync(String resourceGroupName, String sqlServerName, String name) {
        return this.sqlServerManager.inner().firewallRules().deleteAsync(resourceGroupName, sqlServerName, name).toCompletable();
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
        if (this.sqlServer != null) {
            this.deleteBySqlServer(this.sqlServer.resourceGroupName(), this.sqlServer.name(), name);
        }
    }

    @Override
    public Completable deleteAsync(String name) {
        if (this.sqlServer == null) {
            return null;
        }
        return this.deleteBySqlServerAsync(this.sqlServer.resourceGroupName(), this.sqlServer.name(), name);
    }

    @Override
    public List<SqlFirewallRule> listBySqlServer(String resourceGroupName, String sqlServerName) {
        List<SqlFirewallRule> firewallRuleSet = new ArrayList<>();
        List<FirewallRuleInner> firewallRuleInners = this.sqlServerManager.inner().firewallRules().listByServer(resourceGroupName, sqlServerName);
        if (firewallRuleInners != null) {
            for (FirewallRuleInner inner : firewallRuleInners) {
                firewallRuleSet.add(new SqlFirewallRuleImpl(resourceGroupName, sqlServerName, inner.name(), inner, this.sqlServerManager));
            }
        }
        return Collections.unmodifiableList(firewallRuleSet);
    }

    @Override
    public Observable<SqlFirewallRule> listBySqlServerAsync(final String resourceGroupName, final String sqlServerName) {
        return this.sqlServerManager.inner().firewallRules().listByServerAsync(resourceGroupName, sqlServerName)
            .flatMap(new Func1<List<FirewallRuleInner>, Observable<FirewallRuleInner>>() {
                @Override
                public Observable<FirewallRuleInner> call(List<FirewallRuleInner> firewallRuleInners) {
                    return Observable.from(firewallRuleInners);
                }
            })
            .map(new Func1<FirewallRuleInner, SqlFirewallRule>() {
                @Override
                public SqlFirewallRule call(FirewallRuleInner inner) {
                    return new SqlFirewallRuleImpl(resourceGroupName, sqlServerName, inner.name(), inner, sqlServerManager);
                }
            });
    }

    @Override
    public List<SqlFirewallRule> listBySqlServer(SqlServer sqlServer) {
        Objects.requireNonNull(sqlServer);
        List<SqlFirewallRule> firewallRuleSet = new ArrayList<>();
        for (FirewallRuleInner inner : sqlServer.manager().inner().firewallRules().listByServer(sqlServer.resourceGroupName(), sqlServer.name())) {
            firewallRuleSet.add(new SqlFirewallRuleImpl(inner.name(), (SqlServerImpl) sqlServer, inner, sqlServer.manager()));
        }
        return Collections.unmodifiableList(firewallRuleSet);
    }

    @Override
    public Observable<SqlFirewallRule> listBySqlServerAsync(final SqlServer sqlServer) {
        Objects.requireNonNull(sqlServer);
        return sqlServer.manager().inner().firewallRules()
            .listByServerAsync(sqlServer.resourceGroupName(), sqlServer.name())
            .flatMap(new Func1<List<FirewallRuleInner>, Observable<FirewallRuleInner>>() {
                @Override
                public Observable<FirewallRuleInner> call(List<FirewallRuleInner> firewallRuleInners) {
                    return Observable.from(firewallRuleInners);
                }
            })
            .map(new Func1<FirewallRuleInner, SqlFirewallRule>() {
                @Override
                public SqlFirewallRule call(FirewallRuleInner inner) {
                    return new SqlFirewallRuleImpl(inner.name(), (SqlServerImpl) sqlServer, inner, sqlServer.manager());
                }
            });
    }

    @Override
    public List<SqlFirewallRule> list() {
        if (this.sqlServer == null) {
            return null;
        }
        return this.listBySqlServer(this.sqlServer);
    }

    @Override
    public Observable<SqlFirewallRule> listAsync() {
        if (sqlServer == null) {
            return null;
        }
        return this.listBySqlServerAsync(this.sqlServer.resourceGroupName(), this.sqlServer.name());
    }

    @Override
    public SqlFirewallRuleImpl define(String name) {
        SqlFirewallRuleImpl result = sqlFirewallRules.defineIndependentFirewallRule(name);
        return (this.sqlServer != null) ? result.withExistingSqlServer(this.sqlServer) : result;
    }
}
