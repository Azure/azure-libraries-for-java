/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceId;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.RefreshableWrapperImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import com.microsoft.azure.management.sql.ReplicationLink;
import com.microsoft.azure.management.sql.ReplicationRole;
import com.microsoft.azure.management.sql.ReplicationState;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import org.joda.time.DateTime;
import rx.Completable;
import rx.Observable;

/**
 * Implementation for SQL replication link interface.
 */
@LangDefinition
class ReplicationLinkImpl
        extends RefreshableWrapperImpl<ReplicationLinkInner, ReplicationLink>
        implements ReplicationLink {

    private final String sqlServerName;
    private final String resourceGroupName;
    private final SqlServerManager sqlServerManager;
    private final ResourceId resourceId;

    protected ReplicationLinkImpl(String resourceGroupName, String sqlServerName, ReplicationLinkInner innerObject, SqlServerManager sqlServerManager) {
        super(innerObject);
        this.resourceGroupName = resourceGroupName;
        this.sqlServerName = sqlServerName;
        this.sqlServerManager = sqlServerManager;
        this.resourceId = ResourceId.fromString(this.inner().id());
    }

    @Override
    protected Observable<ReplicationLinkInner> getInnerAsync() {
        return this.sqlServerManager.inner().replicationLinks()
            .getAsync(this.resourceGroupName,
                this.sqlServerName,
                this.databaseName(),
                this.name());
    }

    @Override
    public String sqlServerName() {
        return this.sqlServerName;
    }

    @Override
    public String databaseName() {
        return this.resourceId.parent().name();
    }

    @Override
    public String partnerServer() {
        return this.inner().partnerServer();
    }

    @Override
    public String partnerDatabase() {
        return this.inner().partnerDatabase();
    }

    @Override
    public String partnerLocation() {
        return this.inner().partnerLocation();
    }

    @Override
    public ReplicationRole role() {
        return this.inner().role();
    }

    @Override
    public ReplicationRole partnerRole() {
        return this.inner().partnerRole();
    }

    @Override
    public DateTime startTime() {
        return this.inner().startTime();
    }

    @Override
    public int percentComplete() {
        return Utils.toPrimitiveInt(this.inner().percentComplete());
    }

    @Override
    public ReplicationState replicationState() {
        return this.inner().replicationState();
    }

    @Override
    public String location() {
        return this.inner().location();
    }

    @Override
    public boolean isTerminationAllowed() {
        return this.inner().isTerminationAllowed();
    }

    @Override
    public String replicationMode() {
        return this.inner().replicationMode();
    }

    @Override
    public void delete() {
        this.sqlServerManager.inner().replicationLinks()
            .delete(this.resourceGroupName,
                this.sqlServerName,
                this.databaseName(),
                this.name());
    }

    @Override
    public void failover() {
        this.sqlServerManager.inner().replicationLinks()
            .failover(this.resourceGroupName,
                this.sqlServerName,
                this.databaseName(),
                this.name());
    }

    @Override
    public Completable failoverAsync() {
        return this.sqlServerManager.inner().replicationLinks()
            .failoverAsync(this.resourceGroupName,
                this.sqlServerName,
                this.databaseName(),
                this.name()).toCompletable();
    }

    @Override
    public ServiceFuture<Void> failoverAsync(ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(this.failoverAsync(), callback);
    }

    @Override
    public void forceFailoverAllowDataLoss() {
        this.sqlServerManager.inner().replicationLinks()
            .failoverAllowDataLoss(this.resourceGroupName,
                this.sqlServerName,
                this.databaseName(),
                this.name());
    }

    @Override
    public Completable forceFailoverAllowDataLossAsync() {
        return this.sqlServerManager.inner().replicationLinks()
            .failoverAllowDataLossAsync(this.resourceGroupName,
                this.sqlServerName,
                this.databaseName(),
                this.name()).toCompletable();
    }

    @Override
    public ServiceFuture<Void> forceFailoverAllowDataLossAsync(ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(this.forceFailoverAllowDataLossAsync(), callback);
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
        return this.resourceGroupName;
    }
}
