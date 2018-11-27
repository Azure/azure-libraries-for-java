/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.ExternalChildResourcesNonCachedImpl;
import com.microsoft.azure.management.sql.SqlServer;
import com.microsoft.azure.management.sql.SqlVirtualNetworkRule;

/**
 * Represents a SQL Virtual Network Rules collection associated with an Azure SQL server.
 */
@LangDefinition
public class SqlVirtualNetworkRulesAsExternalChildResourcesImpl
    extends
        ExternalChildResourcesNonCachedImpl<SqlVirtualNetworkRuleImpl,
            SqlVirtualNetworkRule,
            VirtualNetworkRuleInner,
            SqlServerImpl,
            SqlServer> {

    SqlServerManager sqlServerManager;

    /**
     * Creates a new ExternalNonInlineChildResourcesImpl.
     *
     * @param parent            the parent Azure resource
     * @param childResourceName the child resource name
     */
    protected SqlVirtualNetworkRulesAsExternalChildResourcesImpl(SqlServerImpl parent, String childResourceName) {
        super(parent, parent.taskGroup(), childResourceName);
        this.sqlServerManager = parent.manager();
    }

    /**
     * Creates a new ExternalChildResourcesNonCachedImpl.
     *
     * @param sqlServerManager the manager
     * @param childResourceName the child resource name (for logging)
     */
    protected SqlVirtualNetworkRulesAsExternalChildResourcesImpl(SqlServerManager sqlServerManager, String childResourceName) {
        super(null, null, childResourceName);
        this.sqlServerManager = sqlServerManager;
    }

    SqlVirtualNetworkRuleImpl defineIndependentVirtualNetworkRule(String name) {
        // resource group and server name will be set by the next method in the Fluent flow
        return prepareIndependentDefine(new SqlVirtualNetworkRuleImpl(name, new VirtualNetworkRuleInner(), this.sqlServerManager));
    }

    SqlVirtualNetworkRuleImpl defineInlineVirtualNetworkRule(String name) {
        if (this.parent() == null) {
            // resource group and server name will be set by the next method in the Fluent flow
            return prepareInlineDefine(new SqlVirtualNetworkRuleImpl(name, new VirtualNetworkRuleInner(), this.sqlServerManager));
        } else {
            return prepareInlineDefine(new SqlVirtualNetworkRuleImpl(name, this.parent(), new VirtualNetworkRuleInner(), this.parent().manager()));
        }
    }

    SqlVirtualNetworkRuleImpl updateInlineVirtualNetworkRule(String name) {
        if (this.parent() == null) {
            // resource group and server name will be set by the next method in the Fluent flow
            return prepareInlineUpdate(new SqlVirtualNetworkRuleImpl(name, new VirtualNetworkRuleInner(), this.sqlServerManager));
        } else {
            return prepareInlineUpdate(new SqlVirtualNetworkRuleImpl(name, this.parent(), new VirtualNetworkRuleInner(), this.parent().manager()));
        }
    }

    void removeInlineVirtualNetworkRule(String name) {
        if (this.parent() == null) {
            // resource group and server name will be set by the next method in the Fluent flow
            prepareInlineRemove(new SqlVirtualNetworkRuleImpl(name, new VirtualNetworkRuleInner(), this.sqlServerManager));
        } else {
            prepareInlineRemove(new SqlVirtualNetworkRuleImpl(name, this.parent(), new VirtualNetworkRuleInner(), this.parent().manager()));
        }
    }
}
