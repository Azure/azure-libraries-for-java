/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.ExternalChildResourcesNonCachedImpl;
import com.microsoft.azure.management.sql.SqlFirewallRule;
import com.microsoft.azure.management.sql.SqlServer;

/**
 * Represents a SQL Firewall rules collection associated with an Azure SQL server.
 */
@LangDefinition
public class SqlFirewallRulesAsExternalChildResourcesImpl
    extends
        ExternalChildResourcesNonCachedImpl<SqlFirewallRuleImpl,
            SqlFirewallRule,
            FirewallRuleInner,
            SqlServerImpl,
            SqlServer> {

    SqlServerManager sqlServerManager;

    /**
     * Creates a new ExternalNonInlineChildResourcesImpl.
     *
     * @param parent            the parent Azure resource
     * @param childResourceName the child resource name
     */
    protected SqlFirewallRulesAsExternalChildResourcesImpl(SqlServerImpl parent, String childResourceName) {
        super(parent, parent.taskGroup(), childResourceName);
        this.sqlServerManager = parent.manager();
    }

    /**
     * Creates a new ExternalChildResourcesNonCachedImpl.
     *
     * @param sqlServerManager the manager
     * @param childResourceName the child resource name (for logging)
     */
    protected SqlFirewallRulesAsExternalChildResourcesImpl(SqlServerManager sqlServerManager, String childResourceName) {
        super(null, null, childResourceName);
        this.sqlServerManager = sqlServerManager;
    }

    SqlFirewallRuleImpl defineIndependentFirewallRule(String name) {
        // resource group and server name will be set by the next method in the Fluent flow
        return prepareIndependentDefine(new SqlFirewallRuleImpl(name, new FirewallRuleInner(), this.sqlServerManager));
    }

    SqlFirewallRuleImpl defineInlineFirewallRule(String name) {
        if (this.parent() == null) {
            // resource group and server name will be set by the next method in the Fluent flow
            return prepareInlineDefine(new SqlFirewallRuleImpl(name, new FirewallRuleInner(), this.sqlServerManager));
        } else {
            return prepareInlineDefine(new SqlFirewallRuleImpl(name, this.parent(), new FirewallRuleInner(), this.parent().manager()));
        }
    }

    SqlFirewallRuleImpl updateInlineFirewallRule(String name) {
        if (this.parent() == null) {
            // resource group and server name will be set by the next method in the Fluent flow
            return prepareInlineUpdate(new SqlFirewallRuleImpl(name, new FirewallRuleInner(), this.sqlServerManager));
        } else {
            return prepareInlineUpdate(new SqlFirewallRuleImpl(name, this.parent(), new FirewallRuleInner(), this.parent().manager()));
        }
    }

    void removeInlineFirewallRule(String name) {
        if (this.parent() == null) {
            // resource group and server name will be set by the next method in the Fluent flow
            prepareInlineRemove(new SqlFirewallRuleImpl(name, new FirewallRuleInner(), this.sqlServerManager));
        } else {
            prepareInlineRemove(new SqlFirewallRuleImpl(name, this.parent(), new FirewallRuleInner(), this.parent().manager()));
        }
    }
}
