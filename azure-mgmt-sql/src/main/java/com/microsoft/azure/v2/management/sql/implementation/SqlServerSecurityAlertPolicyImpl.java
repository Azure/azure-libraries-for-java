/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceId;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
import com.microsoft.azure.v2.management.sql.SecurityAlertPolicyState;
import com.microsoft.azure.v2.management.sql.SqlServer;
import com.microsoft.azure.v2.management.sql.SqlServerSecurityAlertPolicy;
import com.microsoft.azure.v2.management.sql.SqlServerSecurityAlertPolicyOperations;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Implementation for SQL Server Security Alert Policy interface.
 */
@LangDefinition
public class SqlServerSecurityAlertPolicyImpl
    extends
        ExternalChildResourceImpl<SqlServerSecurityAlertPolicy, ServerSecurityAlertPolicyInner, SqlServerImpl, SqlServer>
    implements
        SqlServerSecurityAlertPolicy,
        SqlServerSecurityAlertPolicy.Update,
        SqlServerSecurityAlertPolicyOperations.SqlServerSecurityAlertPolicyOperationsDefinition {

    private SqlServerManager sqlServerManager;
    private String resourceGroupName;
    private String sqlServerName;

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param parent      reference to the parent of this external child resource
     * @param innerObject reference to the inner object representing this external child resource
     * @param sqlServerManager reference to the SQL server manager that accesses firewall rule operations
     */
    SqlServerSecurityAlertPolicyImpl(SqlServerImpl parent, ServerSecurityAlertPolicyInner innerObject, SqlServerManager sqlServerManager) {
        super("Default", parent, innerObject);

        Objects.requireNonNull(parent);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        this.resourceGroupName = parent.resourceGroupName();
        this.sqlServerName = parent.name();
    }

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param resourceGroupName the resource group name
     * @param sqlServerName     the parent SQL server name
     * @param innerObject       reference to the inner object representing this external child resource
     * @param sqlServerManager  reference to the SQL server manager that accesses firewall rule operations
     */
    SqlServerSecurityAlertPolicyImpl(String resourceGroupName, String sqlServerName, ServerSecurityAlertPolicyInner innerObject, SqlServerManager sqlServerManager) {
        super("Default", null, innerObject);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        this.resourceGroupName = resourceGroupName;
        this.sqlServerName = sqlServerName;
    }

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param innerObject      reference to the inner object representing this external child resource
     * @param sqlServerManager reference to the SQL server manager that accesses firewall rule operations
     */
    SqlServerSecurityAlertPolicyImpl(ServerSecurityAlertPolicyInner innerObject, SqlServerManager sqlServerManager) {
        super("Default", null, innerObject);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        if (innerObject != null && innerObject.id() != null) {
            try {
                ResourceId resourceId = ResourceId.fromString(innerObject.id());
                this.resourceGroupName = resourceId.resourceGroupName();
                this.sqlServerName = resourceId.parent().name();
            } catch (NullPointerException e) {
            }
        }
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public String name() {
        return "Default";
    }

    @Override
    public String resourceGroupName() {
        return this.resourceGroupName;
    }

    @Override
    public String sqlServerName() {
        return this.sqlServerName;
    }

    @Override
    public String parentId() {
        return ResourceUtils.parentResourceIdFromResourceId(this.inner().id());
    }

    @Override
    public SecurityAlertPolicyState state() {
        return this.inner().state();
    }

    @Override
    public List<String> disabledAlerts() {
        return Collections.unmodifiableList(this.inner().disabledAlerts());
    }

    @Override
    public List<String> emailAddresses() {
        return Collections.unmodifiableList(this.inner().emailAddresses());
    }

    @Override
    public boolean emailAccountAdmins() {
        return this.inner().emailAccountAdmins();
    }

    @Override
    public String storageEndpoint() {
        return this.inner().storageEndpoint();
    }

    @Override
    public String storageAccountAccessKey() {
        return this.inner().storageAccountAccessKey();
    }

    @Override
    public int retentionDays() {
        return this.inner().retentionDays();
    }

    @Override
    public SqlServerSecurityAlertPolicyImpl withExistingSqlServer(String resourceGroupName, String sqlServerName) {
        this.resourceGroupName = resourceGroupName;
        this.sqlServerName = sqlServerName;
        return this;
    }

    @Override
    public SqlServerSecurityAlertPolicyImpl withExistingSqlServerId(String sqlServerId) {
        Objects.requireNonNull(sqlServerId);
        ResourceId resourceId = ResourceId.fromString(sqlServerId);
        this.resourceGroupName = resourceId.resourceGroupName();
        this.sqlServerName = resourceId.name();
        return this;
    }

    @Override
    public SqlServerSecurityAlertPolicyImpl withExistingSqlServer(SqlServer sqlServer) {
        Objects.requireNonNull(sqlServer);
        this.resourceGroupName = sqlServer.resourceGroupName();
        this.sqlServerName = sqlServer.name();
        return this;
    }

    @Override
    public SqlServerSecurityAlertPolicyImpl update() {
        super.prepareUpdate();
        // storageAccountAccessKey parameter can not be null when storageEndpoint parameter is not null
        this.inner().withStorageEndpoint(null);
        return this;
    }

    @Override
    public Observable<SqlServerSecurityAlertPolicy> createResourceAsync() {
        return this.sqlServerManager.inner().serverSecurityAlertPolicies()
                .createOrUpdateAsync(this.resourceGroupName, this.sqlServerName, this.inner())
                .map(serverSecurityAlertPolicyInner -> {
                    this.setInner(serverSecurityAlertPolicyInner);
                    return (SqlServerSecurityAlertPolicy) this;
                })
                .toObservable();
    }

    @Override
    public Observable<SqlServerSecurityAlertPolicy> updateResourceAsync() {
        this.inner().withStorageEndpoint(null);
        return createResourceAsync();
    }

    @Override
    public Completable deleteResourceAsync() {
        return null;
    }

    @Override
    protected Maybe<ServerSecurityAlertPolicyInner> getInnerAsync() {
        return this.sqlServerManager.inner().serverSecurityAlertPolicies()
                .getAsync(this.resourceGroupName, this.sqlServerName);
    }

    @Override
    public SqlServerSecurityAlertPolicyImpl withState(SecurityAlertPolicyState state) {
        this.inner().withState(state);
        return this;
    }

    @Override
    public SqlServerSecurityAlertPolicyImpl withEmailAccountAdmins() {
        this.inner().withEmailAccountAdmins(true);
        return this;
    }

    @Override
    public SqlServerSecurityAlertPolicyImpl withoutEmailAccountAdmins() {
        this.inner().withEmailAccountAdmins(false);
        return this;
    }

    @Override
    public SqlServerSecurityAlertPolicyImpl withStorageEndpoint(String storageEndpointUri, String storageAccessKey) {
        this.inner().withStorageEndpoint(storageEndpointUri);
        this.inner().withStorageAccountAccessKey(storageAccessKey);
        return this;
    }

    @Override
    public SqlServerSecurityAlertPolicyImpl withEmailAddresses(String... emailAddresses) {
        this.inner().withEmailAddresses(Arrays.asList(emailAddresses));
        return this;
    }

    @Override
    public SqlServerSecurityAlertPolicyImpl withDisabledAlerts(String... disabledAlerts) {
        this.inner().withDisabledAlerts(Arrays.asList(disabledAlerts));
        return this;
    }

    @Override
    public SqlServerSecurityAlertPolicyImpl withRetentionDays(int days) {
        this.inner().withRetentionDays(days);
        return this;
    }
}
