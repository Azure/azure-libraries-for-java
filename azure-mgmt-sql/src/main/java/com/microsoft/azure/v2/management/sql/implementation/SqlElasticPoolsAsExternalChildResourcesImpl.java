/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.ExternalChildResourcesNonCachedImpl;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.dag.TaskGroup;
import com.microsoft.azure.management.sql.SqlElasticPool;
import com.microsoft.azure.management.sql.SqlServer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a SQL Elastic Pool collection associated with an Azure SQL server.
 */
@LangDefinition
public class SqlElasticPoolsAsExternalChildResourcesImpl
    extends
        ExternalChildResourcesNonCachedImpl<SqlElasticPoolImpl,
            SqlElasticPool,
            ElasticPoolInner,
            SqlServerImpl,
            SqlServer> {

    SqlServerManager sqlServerManager;

    /**
     * Creates a new ExternalChildResourcesNonCachedImpl.
     *
     * @param parent            the parent Azure resource
     * @param childResourceName the child resource name
     */
    protected SqlElasticPoolsAsExternalChildResourcesImpl(SqlServerImpl parent, String childResourceName) {
        super(parent, parent.taskGroup(), childResourceName);
        this.sqlServerManager = parent.manager();
    }

    /**
     * Creates a new ExternalChildResourcesNonCachedImpl.
     *
     * @param sqlServerManager the manager
     * @param childResourceName the child resource name (for logging)
     */
    protected SqlElasticPoolsAsExternalChildResourcesImpl(SqlServerManager sqlServerManager, String childResourceName) {
        super(null, null, childResourceName);
        this.sqlServerManager = sqlServerManager;
    }

    /**
     * Creates a new ExternalChildResourcesNonCachedImpl.
     *
     * @param parentTaskGroup the parent task group
     * @param sqlServerManager the manager
     * @param childResourceName the child resource name (for logging)
     */
    protected SqlElasticPoolsAsExternalChildResourcesImpl(TaskGroup parentTaskGroup, SqlServerManager sqlServerManager, String childResourceName) {
        super(null, parentTaskGroup, childResourceName);
        this.sqlServerManager = sqlServerManager;
    }

    SqlElasticPoolImpl defineIndependentElasticPool(String name) {
        // resource group, server name and location will be set by the next method in the Fluent flow
        return prepareIndependentDefine(new SqlElasticPoolImpl(name, new ElasticPoolInner(), this.sqlServerManager));
    }

    SqlElasticPoolImpl defineInlineElasticPool(String name) {
        if (this.parent() == null) {
            // resource group and server name will be set by the next method in the Fluent flow
            return prepareInlineDefine(new SqlElasticPoolImpl(name, new ElasticPoolInner(), this.sqlServerManager));
        } else {
            return prepareInlineDefine(new SqlElasticPoolImpl(name, this.parent(), new ElasticPoolInner(), this.parent().manager()));
        }
    }

    SqlElasticPoolImpl updateInlineElasticPool(String name) {
        if (this.parent() == null) {
            // resource group and server name will be set by the next method in the Fluent flow
            return prepareInlineUpdate(new SqlElasticPoolImpl(name, new ElasticPoolInner(), this.sqlServerManager));
        } else {
            return prepareInlineUpdate(new SqlElasticPoolImpl(name, this.parent(), new ElasticPoolInner(), this.parent().manager()));
        }
    }

    void removeInlineElasticPool(String name) {
        if (this.parent() == null) {
            // resource group and server name will be set by the next method in the Fluent flow
            prepareInlineRemove(new SqlElasticPoolImpl(name, new ElasticPoolInner(), this.sqlServerManager));
        } else {
            prepareInlineRemove(new SqlElasticPoolImpl(name, this.parent(), new ElasticPoolInner(), this.parent().manager()));
        }
    }

    List<SqlElasticPoolImpl> getChildren(ExternalChildResourceImpl.PendingOperation pendingOperation) {
        List<SqlElasticPoolImpl> results = new ArrayList<>();
        for (SqlElasticPoolImpl child : this.childCollection.values()) {
            if (child.pendingOperation() == pendingOperation) {
                results.add(child);
            }
        }

        return results;
    }
}
