/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceId;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
import com.microsoft.azure.v2.management.sql.FailoverGroupReadOnlyEndpoint;
import com.microsoft.azure.v2.management.sql.FailoverGroupReadWriteEndpoint;
import com.microsoft.azure.v2.management.sql.FailoverGroupReplicationRole;
import com.microsoft.azure.v2.management.sql.PartnerInfo;
import com.microsoft.azure.v2.management.sql.ReadOnlyEndpointFailoverPolicy;
import com.microsoft.azure.v2.management.sql.ReadWriteEndpointFailoverPolicy;
import com.microsoft.azure.v2.management.sql.SqlFailoverGroup;
import com.microsoft.azure.v2.management.sql.SqlFailoverGroupOperations;
import com.microsoft.azure.v2.management.sql.SqlServer;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation for SqlFailoverGroup.
 */
@LangDefinition
public class SqlFailoverGroupImpl
    extends
        ExternalChildResourceImpl<SqlFailoverGroup, FailoverGroupInner, SqlServerImpl, SqlServer>
    implements
        SqlFailoverGroup,
        SqlFailoverGroup.Update,
        SqlFailoverGroupOperations.SqlFailoverGroupOperationsDefinition {

    private SqlServerManager sqlServerManager;
    private String resourceGroupName;
    private String sqlServerName;
    protected String sqlServerLocation;

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param name        the name of this external child resource
     * @param parent      reference to the parent of this external child resource
     * @param innerObject reference to the inner object representing this external child resource
     * @param sqlServerManager reference to the SQL server manager that accesses failover group operations
     */
    SqlFailoverGroupImpl(String name, SqlServerImpl parent, FailoverGroupInner innerObject, SqlServerManager sqlServerManager) {
        super(name, parent, innerObject);

        Objects.requireNonNull(parent);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        this.resourceGroupName = parent.resourceGroupName();
        this.sqlServerName = parent.name();
        this.sqlServerLocation = parent.regionName();
    }

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param resourceGroupName the resource group name
     * @param sqlServerName     the parent SQL server name
     * @param name              the name of this external child resource
     * @param innerObject       reference to the inner object representing this external child resource
     * @param sqlServerManager  reference to the SQL server manager that accesses failover group operations
     */
    SqlFailoverGroupImpl(String resourceGroupName, String sqlServerName, String sqlServerLocation, String name, FailoverGroupInner innerObject, SqlServerManager sqlServerManager) {
        super(name, null, innerObject);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        this.resourceGroupName = resourceGroupName;
        this.sqlServerName = sqlServerName;
        this.sqlServerLocation = sqlServerLocation;
    }

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param name             the name of this external child resource
     * @param innerObject      reference to the inner object representing this external child resource
     * @param sqlServerManager reference to the SQL server manager that accesses failover group operations
     */
    SqlFailoverGroupImpl(String name, FailoverGroupInner innerObject, SqlServerManager sqlServerManager) {
        super(name, null, innerObject);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        if (innerObject != null && innerObject.id() != null) {
            try {
                ResourceId resourceId = ResourceId.fromString(innerObject.id());
                this.resourceGroupName = resourceId.resourceGroupName();
                this.sqlServerName = resourceId.parent().name();
                this.sqlServerLocation = innerObject.location();
            } catch (NullPointerException e) {
            }
        }
    }

    @Override
    public String resourceGroupName() {
        return this.resourceGroupName;
    }

    @Override
    public String id() {
        return this.inner().id();
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
    public ReadWriteEndpointFailoverPolicy readWriteEndpointPolicy() {
        return this.inner().readWriteEndpoint() != null ? this.inner().readWriteEndpoint().failoverPolicy() : null;
    }

    @Override
    public int readWriteEndpointDataLossGracePeriodMinutes() {
        return this.inner().readWriteEndpoint() != null  && this.inner().readWriteEndpoint().failoverWithDataLossGracePeriodMinutes() != null ? this.inner().readWriteEndpoint().failoverWithDataLossGracePeriodMinutes() : 0;
    }

    @Override
    public ReadOnlyEndpointFailoverPolicy readOnlyEndpointPolicy() {
        return this.inner().readOnlyEndpoint() != null ? this.inner().readOnlyEndpoint().failoverPolicy() : null;
    }

    @Override
    public FailoverGroupReplicationRole replicationRole() {
        return this.inner().replicationRole();
    }

    @Override
    public String replicationState() {
        return this.inner().replicationState();
    }

    @Override
    public List<PartnerInfo> partnerServers() {
        return Collections.unmodifiableList(this.inner().partnerServers() != null ? this.inner().partnerServers() : new ArrayList<PartnerInfo>());
    }

    @Override
    public List<String> databases() {
        return Collections.unmodifiableList(this.inner().databasesProperty() != null ? this.inner().databasesProperty() : new ArrayList<String>());
    }

    @Override
    public void delete() {
        this.sqlServerManager.inner().failoverGroups()
            .delete(this.resourceGroupName, this.sqlServerName, this.name());
    }

    @Override
    public Completable deleteAsync() {
        return this.deleteResourceAsync();
    }

    @Override
    public SqlFailoverGroupImpl withExistingSqlServer(String resourceGroupName, String sqlServerName, String sqlServerLocation) {
        this.resourceGroupName = resourceGroupName;
        this.sqlServerName = sqlServerName;
        this.sqlServerLocation = sqlServerLocation;
        return this;
    }

    @Override
    public SqlFailoverGroupImpl withExistingSqlServer(SqlServer sqlServer) {
        Objects.requireNonNull(sqlServer);
        this.resourceGroupName = sqlServer.resourceGroupName();
        this.sqlServerName = sqlServer.name();
        this.sqlServerLocation = sqlServer.regionName();
        return this;
    }

    @Override
    public SqlFailoverGroupImpl update() {
        super.prepareUpdate();
        return this;
    }

    @Override
    public Observable<SqlFailoverGroup> createResourceAsync() {
        final SqlFailoverGroupImpl self = this;
        return this.sqlServerManager.inner().failoverGroups()
                .createOrUpdateAsync(self.resourceGroupName, self.sqlServerName, self.name(), self.inner())
                .map(failoverGroupInner ->  {
                    self.setInner(failoverGroupInner);
                    return (SqlFailoverGroup) self;
                })
                .toObservable();
    }

    @Override
    public Observable<SqlFailoverGroup> updateResourceAsync() {
        return this.createResourceAsync();
    }

    @Override
    public Completable deleteResourceAsync() {
        return this.sqlServerManager.inner().failoverGroups()
            .deleteAsync(this.resourceGroupName, this.sqlServerName, this.name());
    }

    @Override
    protected Maybe<FailoverGroupInner> getInnerAsync() {
        return this.sqlServerManager.inner().failoverGroups()
            .getAsync(this.resourceGroupName, this.sqlServerName, this.name());
    }

    @Override
    public String type() {
        return this.inner().type();
    }

    @Override
    public String regionName() {
        return this.inner().location();
    }

    @Override
    public Region region() {
        return Region.fromName(this.inner().location());
    }

    @Override
    public Map<String, String> tags() {
        return this.inner().tags();
    }

    @Override
    public SqlFailoverGroupImpl withTags(Map<String, String> tags) {
        this.inner().withTags(new HashMap<>(tags));
        return this;
    }

    @Override
    public SqlFailoverGroupImpl withTag(String key, String value) {
        if (this.inner().tags() == null) {
            this.inner().withTags(new HashMap<String, String>());
        }
        this.inner().tags().put(key, value);
        return this;
    }

    @Override
    public SqlFailoverGroupImpl withoutTag(String key) {
        if (this.inner().tags() != null) {
            this.inner().tags().remove(key);
        }
        return this;
    }

    @Override
    public SqlFailoverGroupImpl withAutomaticReadWriteEndpointPolicyAndDataLossGracePeriod(int gracePeriodInMinutes) {
        if (this.inner().readWriteEndpoint() == null) {
            this.inner().withReadWriteEndpoint(new FailoverGroupReadWriteEndpoint());
        }
        this.inner().readWriteEndpoint().withFailoverPolicy(ReadWriteEndpointFailoverPolicy.AUTOMATIC);
        this.inner().readWriteEndpoint().withFailoverWithDataLossGracePeriodMinutes(gracePeriodInMinutes);
        return this;
    }

    @Override
    public SqlFailoverGroupImpl withManualReadWriteEndpointPolicy() {
        if (this.inner().readWriteEndpoint() == null) {
            this.inner().withReadWriteEndpoint(new FailoverGroupReadWriteEndpoint());
        }
        this.inner().readWriteEndpoint().withFailoverPolicy(ReadWriteEndpointFailoverPolicy.MANUAL);
        this.inner().readWriteEndpoint().withFailoverWithDataLossGracePeriodMinutes(null);
        return this;
    }

    @Override
    public SqlFailoverGroupImpl withReadOnlyEndpointPolicyEnabled() {
        if (this.inner().readOnlyEndpoint() == null) {
            this.inner().withReadOnlyEndpoint(new FailoverGroupReadOnlyEndpoint());
        }
        this.inner().readOnlyEndpoint().withFailoverPolicy(ReadOnlyEndpointFailoverPolicy.ENABLED);
        return this;
    }

    @Override
    public SqlFailoverGroupImpl withReadOnlyEndpointPolicyDisabled() {
        if (this.inner().readOnlyEndpoint() == null) {
            this.inner().withReadOnlyEndpoint(new FailoverGroupReadOnlyEndpoint());
        }
        this.inner().readOnlyEndpoint().withFailoverPolicy(ReadOnlyEndpointFailoverPolicy.DISABLED);
        return this;
    }

    @Override
    public SqlFailoverGroupImpl withPartnerServerId(String id) {
        this.inner().withPartnerServers(new ArrayList<PartnerInfo>());
        this.inner().partnerServers().add(new PartnerInfo().withId(id));
        return this;
    }

    @Override
    public SqlFailoverGroupImpl withDatabaseId(String id) {
        if (this.inner().databasesProperty() == null) {
            this.inner().withDatabasesProperty(new ArrayList<String>());
        }
        this.inner().databasesProperty().add(id);
        return this;
    }

    @Override
    public SqlFailoverGroupImpl withNewDatabaseId(String id) {
        return this.withDatabaseId(id);
    }

    @Override
    public SqlFailoverGroupImpl withDatabaseIds(String... ids) {
        this.inner().withDatabasesProperty(new ArrayList<String>());
        for (String id : ids) {
            this.inner().databasesProperty().add(id);
        }
        return this;
    }

    @Override
    public SqlFailoverGroupImpl withoutDatabaseId(String id) {
        if (this.inner().databasesProperty() != null) {
            this.inner().databasesProperty().remove(key);
        }
        return this;
    }
}
