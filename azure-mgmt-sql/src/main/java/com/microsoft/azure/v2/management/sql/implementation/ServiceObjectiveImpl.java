/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.RefreshableWrapperImpl;
import com.microsoft.azure.management.sql.ServiceObjective;
import rx.Observable;

/**
 * Implementation for Azure SQL Server's Service Objective.
 */
@LangDefinition
class ServiceObjectiveImpl
        extends RefreshableWrapperImpl<ServiceObjectiveInner, ServiceObjective>
        implements ServiceObjective {
    private final SqlServerImpl sqlServer;

    protected ServiceObjectiveImpl(ServiceObjectiveInner innerObject, SqlServerImpl sqlServer) {
        super(innerObject);
        this.sqlServer = sqlServer;
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public String resourceGroupName() {
        return this.sqlServer.resourceGroupName();
    }

    @Override
    public String sqlServerName() {
        return this.sqlServer.name();
    }

    @Override
    public String serviceObjectiveName() {
        return this.inner().serviceObjectiveName();
    }

    @Override
    public boolean isDefault() {
        return this.inner().isDefault();
    }

    @Override
    public boolean isSystem() {
        return this.inner().isSystem();
    }

    @Override
    public boolean enabled() {
        return false;
    }

    @Override
    public String description() {
        return this.inner().description();
    }

    @Override
    protected Observable<ServiceObjectiveInner> getInnerAsync() {
        return this.sqlServer.manager().inner().serviceObjectives()
            .getAsync(this.resourceGroupName(), this.sqlServerName(), this.name());
    }
}
