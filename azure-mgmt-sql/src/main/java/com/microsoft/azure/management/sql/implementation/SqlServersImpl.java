/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;
import com.microsoft.azure.management.sql.SqlDatabaseOperations;
import com.microsoft.azure.management.sql.SqlElasticPoolOperations;
import com.microsoft.azure.management.sql.SqlFirewallRuleOperations;
import com.microsoft.azure.management.sql.SqlServer;
import com.microsoft.azure.management.sql.SqlServers;

/**
 * Implementation for SqlServers and its parent interfaces.
 */
@LangDefinition
class SqlServersImpl
        extends TopLevelModifiableResourcesImpl<
            SqlServer,
            SqlServerImpl,
            ServerInner,
            ServersInner,
            SqlServerManager>
        implements SqlServers {

    private SqlFirewallRuleOperations firewallRules;
    private SqlElasticPoolOperations elasticPools;
    private SqlDatabaseOperations databases;

    protected SqlServersImpl(SqlServerManager manager) {
        super(manager.inner().servers(), manager);
    }

    @Override
    protected SqlServerImpl wrapModel(String name) {
        ServerInner inner = new ServerInner();
        return new SqlServerImpl(name, inner, this.manager());
    }

    @Override
    protected SqlServerImpl wrapModel(ServerInner inner) {
        if (inner == null) {
            return null;
        }

        return new SqlServerImpl(inner.name(), inner, this.manager());
    }

    @Override
    public SqlServer.DefinitionStages.Blank define(String name) {
        return wrapModel(name);
    }

    @Override
    public SqlFirewallRuleOperations firewallRules() {
        if (firewallRules == null) {
            this.firewallRules = new SqlFirewallRuleOperationsImpl(this.manager());
        }

        return this.firewallRules;
    }

    @Override
    public SqlElasticPoolOperations elasticPools() {
        if (elasticPools == null) {
            this.elasticPools = new SqlElasticPoolOperationsImpl(this.manager());
        }

        return this.elasticPools;
    }

    @Override
    public SqlDatabaseOperations databases() {
        if (databases == null) {
            this.databases = new SqlDatabaseOperationsImpl(this.manager());
        }

        return this.databases;
    }
}
