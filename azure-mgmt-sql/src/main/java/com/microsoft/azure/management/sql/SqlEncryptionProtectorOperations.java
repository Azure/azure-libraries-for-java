/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import rx.Observable;

import java.util.List;

/**
 * A representation of the Azure SQL Encryption Protector operations.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_9_0)
public interface SqlEncryptionProtectorOperations {
    /**
     * Gets the information about an Encryption Protector resource from Azure SQL server, identifying it by its resource group and parent.
     *
     * @param resourceGroupName the name of resource group
     * @param sqlServerName the name of SQL server parent resource
     * @return an immutable representation of the resource
     */
    SqlEncryptionProtector getBySqlServer(String resourceGroupName, String sqlServerName);

    /**
     * Asynchronously gets the information about an Encryption Protector resource from Azure SQL server, identifying it by its resource group and parent.
     *
     * @param resourceGroupName the name of resource group
     * @param sqlServerName the name of SQL server parent resource
     * @return a representation of the deferred computation of this call returning the found resource
     */
    Observable<SqlEncryptionProtector> getBySqlServerAsync(String resourceGroupName, String sqlServerName);

    /**
     * Gets the information about an Encryption Protector resource from Azure SQL server, identifying it by its resource group and parent.
     *
     * @param sqlServer the SQL server parent resource
     * @return an immutable representation of the resource
     */
    SqlEncryptionProtector getBySqlServer(SqlServer sqlServer);

    /**
     * Asynchronously gets the information about an Encryption Protector resource from Azure SQL server, identifying it by its resource group and parent.
     *
     * @param sqlServer the SQL server parent resource
     * @return a representation of the deferred computation of this call returning the found resource
     */
    @Beta(Beta.SinceVersion.V1_9_0)
    Observable<SqlEncryptionProtector> getBySqlServerAsync(SqlServer sqlServer);

    /**
     * Gets the information about an Encryption Protector resource from Azure SQL server using the resource ID.
     *
     * @param id the ID of the resource.
     * @return an immutable representation of the resource
     */
    SqlEncryptionProtector getById(String id);

    /**
     * Asynchronously gets the information about an Encryption Protector resource from Azure SQL server using the resource ID.
     *
     * @param id the ID of the resource.
     * @return a representation of the deferred computation of this call
     */
    Observable<SqlEncryptionProtector> getByIdAsync(String id);

    /**
     * Lists Azure SQL the Encryption Protector resources of the specified Azure SQL server in the specified resource group.
     *
     * @param resourceGroupName the name of the resource group to list the resources from
     * @param sqlServerName the name of parent Azure SQL server.
     * @return the list of resources
     */
    List<SqlEncryptionProtector> listBySqlServer(String resourceGroupName, String sqlServerName);

    /**
     * Asynchronously lists Azure SQL the Encryption Protector resources of the specified Azure SQL server in the specified resource group.
     *
     * @param resourceGroupName the name of the resource group to list the resources from
     * @param sqlServerName the name of parent Azure SQL server.
     * @return a representation of the deferred computation of this call
     */
    Observable<SqlEncryptionProtector> listBySqlServerAsync(String resourceGroupName, String sqlServerName);

    /**
     * Lists Azure SQL the Encryption Protector resources of the specified Azure SQL server in the specified resource group.
     *
     * @param sqlServer the parent Azure SQL server.
     * @return the list of resources
     */
    List<SqlEncryptionProtector> listBySqlServer(SqlServer sqlServer);

    /**
     * Asynchronously lists Azure SQL the Encryption Protector resources of the specified Azure SQL server in the specified resource group.
     *
     * @param sqlServer the parent Azure SQL server.
     * @return a representation of the deferred computation of this call
     */
    @Beta(Beta.SinceVersion.V1_9_0)
    Observable<SqlEncryptionProtector> listBySqlServerAsync(SqlServer sqlServer);

    /**
     * Grouping of the Azure SQL Server Key common actions.
     */
    @Beta(Beta.SinceVersion.V1_9_0)
    interface SqlEncryptionProtectorActionsDefinition {
        /**
         * Gets the information about an Encryption Protector resource from Azure SQL server.
         *
         * @return an immutable representation of the resource
         */
        @Method
        SqlEncryptionProtector get();

        /**
         * Asynchronously gets the information about an Encryption Protector resource from Azure SQL server.
         *
         * @return a representation of the deferred computation of this call returning the found resource
         */
        @Method
        Observable<SqlEncryptionProtector> getAsync();

        /**
         * Lists Azure SQL the Encryption Protector resources.
         *
         * @return the list of resources
         */
        @Method
        List<SqlEncryptionProtector> list();

        /**
         * Asynchronously lists Azure SQL the Encryption Protector resources.
         *
         * @return a representation of the deferred computation of this call
         */
        @Method
        Observable<SqlEncryptionProtector> listAsync();
    }
}

