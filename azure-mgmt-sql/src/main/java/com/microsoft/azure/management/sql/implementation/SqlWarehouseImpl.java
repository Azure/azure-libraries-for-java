/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.sql.SqlWarehouse;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;

/**
 * Implementation for SqlWarehouse and its parent interfaces.
 */
@LangDefinition
class SqlWarehouseImpl
        extends SqlDatabaseImpl
        implements SqlWarehouse {

    SqlWarehouseImpl(String name, SqlServerImpl parent, DatabaseInner innerObject, SqlServerManager sqlServerManager) {
        super(name, parent, innerObject, sqlServerManager);
    }

    SqlWarehouseImpl(String resourceGroupName, String sqlServerName, String sqlServerLocation, String name, DatabaseInner innerObject, SqlServerManager sqlServerManager) {
        super(resourceGroupName, sqlServerName, sqlServerLocation, name, innerObject, sqlServerManager);
    }

    @Override
    public void pauseDataWarehouse() {
        this.sqlServerManager.inner().databases()
            .pause(this.resourceGroupName, this.sqlServerName, this.name());
    }

    @Override
    public Completable pauseDataWarehouseAsync() {
        return this.sqlServerManager.inner().databases()
            .pauseAsync(this.resourceGroupName, this.sqlServerName, this.name()).toCompletable();
    }

    @Override
    public ServiceFuture<Void> pauseDataWarehouseAsync(ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(this.pauseDataWarehouseAsync(), callback);
    }

    @Override
    public void resumeDataWarehouse() {
        this.sqlServerManager.inner().databases()
            .resume(this.resourceGroupName, this.sqlServerName, this.name());
    }

    @Override
    public Completable resumeDataWarehouseAsync() {
        return this.sqlServerManager.inner().databases()
            .resumeAsync(this.resourceGroupName, this.sqlServerName, this.name()).toCompletable();
    }

    @Override
    public ServiceFuture<Void> resumeDataWarehouseAsync(ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(this.resumeDataWarehouseAsync(), callback);
    }
}
