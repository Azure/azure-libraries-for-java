/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceId;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import com.microsoft.azure.management.sql.SqlDatabase;
import com.microsoft.azure.management.sql.SqlSyncFullSchemaProperty;
import com.microsoft.azure.management.sql.SqlSyncGroup;
import com.microsoft.azure.management.sql.SqlSyncMember;
import com.microsoft.azure.management.sql.SqlSyncMemberOperations;
import com.microsoft.azure.management.sql.SyncDirection;
import com.microsoft.azure.management.sql.SyncMemberDbType;
import com.microsoft.azure.management.sql.SyncMemberState;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.Objects;

/**
 * Implementation for SqlSyncMember.
 */
@LangDefinition
public class SqlSyncMemberImpl
    extends
        ExternalChildResourceImpl<SqlSyncMember, SyncMemberInner, SqlSyncGroupImpl, SqlSyncGroup>
    implements
        SqlSyncMember,
        SqlSyncMember.Update,
        SqlSyncMemberOperations.SqlSyncMemberOperationsDefinition {

    private SqlServerManager sqlServerManager;
    private String resourceGroupName;
    private String sqlServerName;
    private String sqlDatabaseName;
    private String sqlSyncGroupName;

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param name        the name of this external child resource
     * @param parent      reference to the parent of this external child resource
     * @param innerObject reference to the inner object representing this external child resource
     * @param sqlServerManager reference to the SQL server manager that accesses DNS alias operations
     */
    SqlSyncMemberImpl(String name, SqlSyncGroupImpl parent, SyncMemberInner innerObject, SqlServerManager sqlServerManager) {
        super(name, parent, innerObject);

        Objects.requireNonNull(parent);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        this.resourceGroupName = parent.resourceGroupName();
        this.sqlServerName = parent.sqlServerName();
        this.sqlDatabaseName = parent.sqlDatabaseName();
        this.sqlSyncGroupName = parent.name();
    }

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param resourceGroupName the resource group name
     * @param sqlServerName     the parent SQL server name
     * @param sqlDatabaseName   the parent SQL Database name
     * @param sqlSyncGroupName  the parent SQL Sync Group name
     * @param name              the name of this external child resource
     * @param innerObject       reference to the inner object representing this external child resource
     * @param sqlServerManager  reference to the SQL server manager that accesses DNS alias operations
     */
    SqlSyncMemberImpl(String resourceGroupName, String sqlServerName, String sqlDatabaseName, String sqlSyncGroupName, String name, SyncMemberInner innerObject, SqlServerManager sqlServerManager) {
        super(name, null, innerObject);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        this.resourceGroupName = resourceGroupName;
        this.sqlServerName = sqlServerName;
        this.sqlDatabaseName = sqlDatabaseName;
        this.sqlSyncGroupName = sqlSyncGroupName;
    }

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param name             the name of this external child resource
     * @param innerObject      reference to the inner object representing this external child resource
     * @param sqlServerManager reference to the SQL server manager that accesses DNS alias operations
     */
    SqlSyncMemberImpl(String name, SyncMemberInner innerObject, SqlServerManager sqlServerManager) {
        super(name, null, innerObject);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        if (innerObject != null && innerObject.id() != null) {
            try {
                ResourceId resourceId = ResourceId.fromString(innerObject.id());
                this.resourceGroupName = resourceId.resourceGroupName();
                this.sqlServerName = resourceId.parent().parent().parent().name();
                this.sqlDatabaseName = resourceId.parent().parent().name();
                this.sqlSyncGroupName = resourceId.parent().name();
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
    public String sqlDatabaseName() {
        return this.sqlDatabaseName;
    }

    @Override
    public String sqlSyncGroupName() {
        return this.sqlSyncGroupName;
    }

    @Override
    public String parentId() {
        return ResourceUtils.parentResourceIdFromResourceId(this.inner().id());
    }

    @Override
    public SyncMemberDbType databaseType() {
        return this.inner().databaseType();
    }

    @Override
    public String syncAgentId() {
        return this.inner().syncAgentId();
    }

    @Override
    public String sqlServerDatabaseId() {
        return this.inner().sqlServerDatabaseId().toString();
    }

    @Override
    public String memberServerName() {
        return this.inner().serverName();
    }

    @Override
    public String memberDatabaseName() {
        return this.inner().databaseName();
    }

    @Override
    public String userName() {
        return this.inner().userName();
    }

    @Override
    public SyncDirection syncDirection() {
        return this.inner().syncDirection();
    }

    @Override
    public SyncMemberState syncState() {
        return this.inner().syncState();
    }

    @Override
    public Observable<SqlSyncMember> createResourceAsync() {
        final SqlSyncMemberImpl self = this;
        return this.sqlServerManager.inner().syncMembers()
            .createOrUpdateAsync(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.sqlSyncGroupName, this.name(), this.inner())
            .map(new Func1<SyncMemberInner, SqlSyncMember>() {
                @Override
                public SqlSyncMember call(SyncMemberInner syncMemberInner) {
                    self.setInner(syncMemberInner);
                    return self;
                }
            });
    }

    @Override
    public Observable<SqlSyncMember> updateResourceAsync() {
        return createResourceAsync();
    }

    @Override
    public Observable<Void> deleteResourceAsync() {
        return this.sqlServerManager.inner().syncMembers()
            .deleteAsync(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.sqlSyncGroupName, this.name());
    }

    @Override
    protected Observable<SyncMemberInner> getInnerAsync() {
        return this.sqlServerManager.inner().syncMembers()
            .getAsync(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.sqlSyncGroupName, this.name());
    }

    @Override
    public void delete() {
        this.sqlServerManager.inner().syncMembers()
            .delete(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.sqlSyncGroupName, this.name());
    }

    @Override
    public Completable deleteAsync() {
        return this.deleteResourceAsync().toCompletable();
    }

    @Override
    public Update update() {
        this.setPendingOperation(PendingOperation.ToBeUpdated);
        return this;
    }

    @Override
    public PagedList<SqlSyncFullSchemaProperty> listMemberSchemas() {
        final PagedListConverter<SyncFullSchemaPropertiesInner, SqlSyncFullSchemaProperty> converter = new PagedListConverter<SyncFullSchemaPropertiesInner, SqlSyncFullSchemaProperty>() {
            @Override
            public Observable<SqlSyncFullSchemaProperty> typeConvertAsync(SyncFullSchemaPropertiesInner inner) {
                return Observable.just((SqlSyncFullSchemaProperty) new SqlSyncFullSchemaPropertyImpl(inner));
            }
        };

        return converter.convert(this.sqlServerManager.inner().syncMembers()
            .listMemberSchemas(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.sqlSyncGroupName, this.name()));
    }

    @Override
    public Observable<SqlSyncFullSchemaProperty> listMemberSchemasAsync() {
        return this.sqlServerManager.inner().syncMembers()
            .listMemberSchemasAsync(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.sqlSyncGroupName, this.name())
            .flatMap(new Func1<Page<SyncFullSchemaPropertiesInner>, Observable<SyncFullSchemaPropertiesInner>>() {
                @Override
                public Observable<SyncFullSchemaPropertiesInner> call(Page<SyncFullSchemaPropertiesInner> syncFullSchemaPropertiesInnerPage) {
                    return Observable.from(syncFullSchemaPropertiesInnerPage.items());
                }
            })
            .map(new Func1<SyncFullSchemaPropertiesInner, SqlSyncFullSchemaProperty>() {
                @Override
                public SqlSyncFullSchemaProperty call(SyncFullSchemaPropertiesInner syncFullSchemaPropertiesInner) {
                    return new SqlSyncFullSchemaPropertyImpl(syncFullSchemaPropertiesInner);
                }
            });
    }

    @Override
    public void refreshMemberSchema() {
        this.sqlServerManager.inner().syncMembers()
            .refreshMemberSchema(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.sqlSyncGroupName, this.name());
    }

    @Override
    public Completable refreshMemberSchemaAsync() {
        return this.sqlServerManager.inner().syncMembers()
            .refreshMemberSchemaAsync(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.sqlSyncGroupName, this.name()).toCompletable();
    }

    @Override
    public SqlSyncMemberImpl withExistingSqlServer(String resourceGroupName, String sqlServerName) {
        this.resourceGroupName = resourceGroupName;
        this.sqlServerName = sqlServerName;
        return this;
    }

    @Override
    public SqlSyncMemberImpl withExistingSyncGroup(SqlSyncGroup sqlSyncGroup) {
        this.resourceGroupName = sqlSyncGroup.resourceGroupName();
        this.sqlServerName = sqlSyncGroup.sqlServerName();
        this.sqlDatabaseName = sqlSyncGroup.sqlDatabaseName();
        this.sqlSyncGroupName = sqlSyncGroup.name();
        return this;
    }

    @Override
    public SqlSyncMemberImpl withExistingDatabaseName(String databaseName) {
        this.sqlDatabaseName = databaseName;
        return this;
    }

    @Override
    public SqlSyncMemberImpl withMemberUserName(String userName) {
        this.inner().withUserName(userName);
        return this;
    }

    @Override
    public SqlSyncMemberImpl withExistingSyncGroupName(String syncGroupName) {
        this.sqlSyncGroupName = syncGroupName;
        return this;
    }

    @Override
    public SqlSyncMemberImpl withMemberPassword(String password) {
        this.inner().withPassword(password);
        return this;
    }

    @Override
    public SqlSyncMemberImpl withMemberSqlServerName(String sqlServerName) {
        this.inner().withServerName(sqlServerName);
        return this;
    }

    @Override
    public SqlSyncMemberImpl withMemberSqlDatabase(SqlDatabase sqlDatabase) {
        this.inner().withServerName(sqlDatabase.sqlServerName());
        this.inner().withDatabaseName(sqlDatabase.name());
        return this;
    }

    @Override
    public SqlSyncMemberImpl withMemberDatabaseType(SyncMemberDbType databaseType) {
        this.inner().withDatabaseType(databaseType);
        return this;
    }

    @Override
    public SqlSyncMemberImpl withDatabaseType(SyncDirection syncDirection) {
        this.inner().withSyncDirection(syncDirection);
        return this;
    }

    @Override
    public SqlSyncMemberImpl withMemberSqlDatabaseName(String sqlDatabaseName) {
        this.inner().withDatabaseName(sqlDatabaseName);
        return this;
    }
}
