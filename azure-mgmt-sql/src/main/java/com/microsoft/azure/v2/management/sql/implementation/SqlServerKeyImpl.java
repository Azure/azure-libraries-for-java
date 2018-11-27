/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceId;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
import com.microsoft.azure.management.sql.ServerKeyType;
import com.microsoft.azure.management.sql.SqlServer;
import com.microsoft.azure.management.sql.SqlServerKey;
import com.microsoft.azure.management.sql.SqlServerKeyOperations;
import org.joda.time.DateTime;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.Objects;

/**
 * Implementation for SQL Server Key interface.
 */
@LangDefinition
public class SqlServerKeyImpl
    extends
        ExternalChildResourceImpl<SqlServerKey, ServerKeyInner, SqlServerImpl, SqlServer>
    implements
        SqlServerKey,
        SqlServerKey.Update,
        SqlServerKeyOperations.SqlServerKeyOperationsDefinition {

    private SqlServerManager sqlServerManager;
    private String resourceGroupName;
    private String sqlServerName;
    private String serverKeyName;

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param name        the name of this external child resource
     * @param parent      reference to the parent of this external child resource
     * @param innerObject reference to the inner object representing this external child resource
     * @param sqlServerManager reference to the SQL server manager that accesses firewall rule operations
     */
    SqlServerKeyImpl(String name, SqlServerImpl parent, ServerKeyInner innerObject, SqlServerManager sqlServerManager) {
        super(name, parent, innerObject);

        Objects.requireNonNull(parent);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        this.resourceGroupName = parent.resourceGroupName();
        this.sqlServerName = parent.name();
        if (innerObject != null && innerObject.name() != null) {
            this.serverKeyName = innerObject.name();
        }
    }

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param resourceGroupName the resource group name
     * @param sqlServerName     the parent SQL server name
     * @param name              the name of this external child resource
     * @param innerObject       reference to the inner object representing this external child resource
     * @param sqlServerManager  reference to the SQL server manager that accesses firewall rule operations
     */
    SqlServerKeyImpl(String resourceGroupName, String sqlServerName, String name, ServerKeyInner innerObject, SqlServerManager sqlServerManager) {
        super(name, null, innerObject);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        this.resourceGroupName = resourceGroupName;
        this.sqlServerName = sqlServerName;
        if (innerObject != null && innerObject.name() != null) {
            this.serverKeyName = innerObject.name();
        }
    }

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param name             the name of this external child resource
     * @param innerObject      reference to the inner object representing this external child resource
     * @param sqlServerManager reference to the SQL server manager that accesses firewall rule operations
     */
    SqlServerKeyImpl(String name, ServerKeyInner innerObject, SqlServerManager sqlServerManager) {
        super(name, null, innerObject);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        if (innerObject != null && innerObject.id() != null) {
            if (innerObject.name() != null) {
                this.serverKeyName = innerObject.name();
            }
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
        return this.serverKeyName;
    }

    @Override
    public SqlServerKeyImpl withExistingSqlServer(String resourceGroupName, String sqlServerName) {
        this.resourceGroupName = resourceGroupName;
        this.sqlServerName = sqlServerName;
        return this;
    }

    @Override
    public SqlServerKeyImpl withExistingSqlServerId(String sqlServerId) {
        Objects.requireNonNull(sqlServerId);
        ResourceId resourceId = ResourceId.fromString(sqlServerId);
        this.resourceGroupName = resourceId.resourceGroupName();
        this.sqlServerName = resourceId.name();
        return this;
    }

    @Override
    public SqlServerKeyImpl withExistingSqlServer(SqlServer sqlServer) {
        Objects.requireNonNull(sqlServer);
        this.resourceGroupName = sqlServer.resourceGroupName();
        this.sqlServerName = sqlServer.name();
        return this;
    }

    @Override
    public SqlServerKeyImpl withAzureKeyVaultKey(String uri) {
        this.inner().withServerKeyType(ServerKeyType.AZURE_KEY_VAULT);
        this.inner().withUri(uri);
        // If the key URI is "https://YourVaultName.vault.azure.net/keys/YourKeyName/01234567890123456789012345678901",
        // then the Server Key Name should be formatted as: "YourVaultName_YourKeyName_01234567890123456789012345678901"
        String[] items = uri.split("\\/");
        this.serverKeyName = String.format("%s_%s_%s", items[2].split("\\.")[0], items[4], items[5]);
        return this;
    }

    @Override
    public SqlServerKeyImpl withThumbprint(String thumbprint) {
        this.inner().withThumbprint(thumbprint);
        return this;
    }

    @Override
    public SqlServerKeyImpl withCreationDate(DateTime creationDate) {
        this.inner().withCreationDate(creationDate);
        return this;
    }

    @Override
    public Observable<SqlServerKey> createResourceAsync() {
        final SqlServerKeyImpl self = this;
        return this.sqlServerManager.inner().serverKeys()
            .createOrUpdateAsync(self.resourceGroupName, self.sqlServerName, self.serverKeyName, self.inner())
            .map(new Func1<ServerKeyInner, SqlServerKey>() {
                @Override
                public SqlServerKey call(ServerKeyInner serverKeyInner) {
                    self.setInner(serverKeyInner);
                    return self;
                }
            });
    }

    @Override
    public Observable<SqlServerKey> updateResourceAsync() {
        return this.createResourceAsync();
    }

    @Override
    public Observable<Void> deleteResourceAsync() {
        return this.sqlServerManager.inner().serverKeys()
            .deleteAsync(this.resourceGroupName, this.sqlServerName, this.name());
    }

    @Override
    protected Observable<ServerKeyInner> getInnerAsync() {
        return this.sqlServerManager.inner().serverKeys()
            .getAsync(this.resourceGroupName, this.sqlServerName, this.name());
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
    public String kind() {
        return this.inner().kind();
    }

    @Override
    public Region region() {
        return Region.fromName(this.inner().location());
    }

    @Override
    public ServerKeyType serverKeyType() {
        return this.inner().serverKeyType();
    }

    @Override
    public String uri() {
        return this.inner().uri();
    }

    @Override
    public String thumbprint() {
        return this.inner().thumbprint();
    }

    @Override
    public DateTime creationDate() {
        return this.inner().creationDate();
    }

    @Override
    public void delete() {
        this.sqlServerManager.inner().serverKeys()
            .delete(this.resourceGroupName, this.sqlServerName, this.name());
    }

    @Override
    public Completable deleteAsync() {
        return this.deleteResourceAsync().toCompletable();
    }

    @Override
    public Update update() {
        super.prepareUpdate();
        return this;
    }
}
