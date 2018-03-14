/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;
import com.microsoft.azure.management.sql.CheckNameAvailabilityResult;
import com.microsoft.azure.management.sql.RegionCapabilities;
import com.microsoft.azure.management.sql.SqlDatabaseOperations;
import com.microsoft.azure.management.sql.SqlElasticPoolOperations;
import com.microsoft.azure.management.sql.SqlFirewallRuleOperations;
import com.microsoft.azure.management.sql.SqlServer;
import com.microsoft.azure.management.sql.SqlServerKeyOperations;
import com.microsoft.azure.management.sql.SqlServers;
import com.microsoft.azure.management.sql.SqlVirtualNetworkRuleOperations;
import rx.Observable;
import rx.functions.Func1;

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
    private SqlVirtualNetworkRuleOperations virtualNetworkRules;
    private SqlElasticPoolOperations elasticPools;
    private SqlDatabaseOperations databases;
    private SqlServerDnsAliasOperationsImpl dnsAliases;
    private SqlServerKeyOperationsImpl serverKeys;

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
        if (this.firewallRules == null) {
            this.firewallRules = new SqlFirewallRuleOperationsImpl(this.manager());
        }

        return this.firewallRules;
    }

    @Override
    public SqlVirtualNetworkRuleOperations virtualNetworkRules() {
        if (this.virtualNetworkRules == null) {
            this.virtualNetworkRules = new SqlVirtualNetworkRuleOperationsImpl(this.manager());
        }

        return this.virtualNetworkRules;
    }

    @Override
    public SqlServerDnsAliasOperationsImpl dnsAliases() {
        if (this.dnsAliases == null) {
            this.dnsAliases = new SqlServerDnsAliasOperationsImpl(this.manager());
        }

        return this.dnsAliases;
    }

    @Override
    public SqlServerKeyOperations serverKeys() {
        if (this.serverKeys == null) {
            this.serverKeys = new SqlServerKeyOperationsImpl(this.manager());
        }

        return this.serverKeys;
    }

    @Override
    public SqlElasticPoolOperations elasticPools() {
        if (this.elasticPools == null) {
            this.elasticPools = new SqlElasticPoolOperationsImpl(this.manager());
        }

        return this.elasticPools;
    }

    @Override
    public SqlDatabaseOperations databases() {
        if (this.databases == null) {
            this.databases = new SqlDatabaseOperationsImpl(this.manager());
        }

        return this.databases;
    }

    @Override
    public CheckNameAvailabilityResult checkNameAvailability(String name) {
        return new CheckNameAvailabilityResultImpl(this.inner().checkNameAvailability(name));
    }

    @Override
    public Observable<CheckNameAvailabilityResult> checkNameAvailabilityAsync(String name) {
        return this.inner().checkNameAvailabilityAsync(name)
            .map(new Func1<CheckNameAvailabilityResponseInner, CheckNameAvailabilityResult>() {
                @Override
                public CheckNameAvailabilityResult call(CheckNameAvailabilityResponseInner checkNameAvailabilityResponseInner) {
                    return new CheckNameAvailabilityResultImpl(checkNameAvailabilityResponseInner);
                }
            });
    }

    @Override
    public RegionCapabilities getCapabilitiesByRegion(Region region) {
        LocationCapabilitiesInner capabilitiesInner = this.manager().inner().capabilities()
            .listByLocation(region.name());
        return capabilitiesInner != null ? new RegionCapabilitiesImpl(capabilitiesInner) : null;
    }

    @Override
    public Observable<RegionCapabilities> getCapabilitiesByRegionAsync(Region region) {
        return this.manager().inner().capabilities()
            .listByLocationAsync(region.name())
            .map(new Func1<LocationCapabilitiesInner, RegionCapabilities>() {
                @Override
                public RegionCapabilities call(LocationCapabilitiesInner capabilitiesInner) {
                    return new RegionCapabilitiesImpl(capabilitiesInner);
                }
            });
    }
}
