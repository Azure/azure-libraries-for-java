/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.sql;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsDeletingByParent;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsBatchCreation;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingById;

/**
 *  Entry point to SQL Elastic Pool management API.
 */
@Fluent
public interface SqlElasticPools extends
        SupportsCreating<SqlElasticPool.DefinitionStages.Blank>,
        SupportsDeletingById,
        SupportsGettingById<SqlElasticPool>,
        SupportsBatchCreation<SqlElasticPool>,
        SupportsDeletingByParent {

    /**
     * Gets the SQLElasticPool based on the resource group name, SQLServer name and SQLElasticPool name.
     *
     * @param resourceGroup the name of resource group.
     * @param sqlServerName the name of SQLServer.
     * @param name the name of SQLElasticPool.
     * @return an immutable representation of the SQLElasticPool
     */
    SqlElasticPool getBySqlServer(String resourceGroup, String sqlServerName, String name);

    /**
     * Gets the SQLElasticPool based on the SQLServer instance and SQLElasticPool name.
     *
     * @param sqlServer the instance of SQLServer.
     * @param name the name of SQLElasticPool
     * @return an immutable representation of the SQLElasticPool
     */
    SqlElasticPool getBySqlServer(GroupableResource sqlServer, String name);

    /**
     * Lists resources of the specified type in the specified resource group and SQLServer.
     *
     * @param resourceGroupName the name of the resource group to list the resources from
     * @param sqlServerName the name of SQLServer
     * @return the list of SQLElasticPools in a SQLServer
     */
    PagedList<SqlElasticPool> listBySqlServer(String resourceGroupName, String sqlServerName);

    /**
     * Gets the SQLElasticPool based on the SQLServer.
     *
     * @param sqlServer the instance of SQLServer
     * @return the list of SQLElasticPools in a SQLServer
     */
    PagedList<SqlElasticPool> listBySqlServer(GroupableResource sqlServer);
}
