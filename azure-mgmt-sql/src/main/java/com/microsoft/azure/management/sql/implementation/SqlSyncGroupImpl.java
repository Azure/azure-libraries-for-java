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
import com.microsoft.azure.management.sql.SqlSyncGroupLogProperty;
import com.microsoft.azure.management.sql.SqlSyncGroupOperations;
import com.microsoft.azure.management.sql.SqlSyncMemberOperations;
import com.microsoft.azure.management.sql.SyncConflictResolutionPolicy;
import com.microsoft.azure.management.sql.SyncGroupSchema;
import com.microsoft.azure.management.sql.SyncGroupState;
import org.joda.time.DateTime;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.Objects;

/**
 * Implementation for SqlSyncGroup.
 */
@LangDefinition
public class SqlSyncGroupImpl
    extends
        ExternalChildResourceImpl<SqlSyncGroup, SyncGroupInner, SqlDatabaseImpl, SqlDatabase>
    implements
        SqlSyncGroup,
        SqlSyncGroup.Update,
        SqlSyncGroupOperations.SqlSyncGroupOperationsDefinition {

    private SqlServerManager sqlServerManager;
    private String resourceGroupName;
    private String sqlServerName;
    private String sqlDatabaseName;

    private SqlSyncMemberOperations.SqlSyncMemberActionsDefinition syncMemberOps;

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param name        the name of this external child resource
     * @param parent      reference to the parent of this external child resource
     * @param innerObject reference to the inner object representing this external child resource
     * @param sqlServerManager reference to the SQL server manager that accesses DNS alias operations
     */
    SqlSyncGroupImpl(String name, SqlDatabaseImpl parent, SyncGroupInner innerObject, SqlServerManager sqlServerManager) {
        super(name, parent, innerObject);

        Objects.requireNonNull(parent);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        this.resourceGroupName = parent.resourceGroupName();
        this.sqlServerName = parent.sqlServerName();
        this.sqlDatabaseName = parent.name();
    }

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param resourceGroupName the resource group name
     * @param sqlServerName     the parent SQL server name
     * @param sqlDatabaseName   the parent SQL Database name
     * @param name              the name of this external child resource
     * @param innerObject       reference to the inner object representing this external child resource
     * @param sqlServerManager  reference to the SQL server manager that accesses DNS alias operations
     */
    SqlSyncGroupImpl(String resourceGroupName, String sqlServerName, String sqlDatabaseName, String name, SyncGroupInner innerObject, SqlServerManager sqlServerManager) {
        super(name, null, innerObject);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        this.resourceGroupName = resourceGroupName;
        this.sqlServerName = sqlServerName;
        this.sqlDatabaseName = sqlDatabaseName;
    }

    /**
     * Creates an instance of external child resource in-memory.
     *
     * @param name             the name of this external child resource
     * @param innerObject      reference to the inner object representing this external child resource
     * @param sqlServerManager reference to the SQL server manager that accesses DNS alias operations
     */
    SqlSyncGroupImpl(String name, SyncGroupInner innerObject, SqlServerManager sqlServerManager) {
        super(name, null, innerObject);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        if (innerObject != null && innerObject.id() != null) {
            try {
                ResourceId resourceId = ResourceId.fromString(innerObject.id());
                this.resourceGroupName = resourceId.resourceGroupName();
                this.sqlServerName = resourceId.parent().parent().name();
                this.sqlDatabaseName = resourceId.parent().name();
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
    public String parentId() {
        return ResourceUtils.parentResourceIdFromResourceId(this.inner().id());
    }

    @Override
    public int interval() {
        return this.inner().interval();
    }

    @Override
    public DateTime lastSyncTime() {
        return this.inner().lastSyncTime();
    }

    @Override
    public SyncConflictResolutionPolicy conflictResolutionPolicy() {
        return this.inner().conflictResolutionPolicy();
    }

    @Override
    public String syncDatabaseId() {
        return this.inner().syncDatabaseId();
    }

    @Override
    public String databaseUserName() {
        return this.inner().hubDatabaseUserName();
    }

    @Override
    public SyncGroupState syncState() {
        return this.inner().syncState();
    }

    @Override
    public SyncGroupSchema schema() {
        return this.inner().schema();
    }

    @Override
    public void refreshHubSchema() {
        this.sqlServerManager.inner().syncGroups()
            .refreshHubSchema(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.name());
    }

    @Override
    public Completable refreshHubSchemaAsync() {
        return this.sqlServerManager.inner().syncGroups()
            .refreshHubSchemaAsync(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.name()).toCompletable();
    }

    @Override
    public PagedList<SqlSyncFullSchemaProperty> listHubSchemas() {
        final PagedListConverter<SyncFullSchemaPropertiesInner, SqlSyncFullSchemaProperty> converter = new PagedListConverter<SyncFullSchemaPropertiesInner, SqlSyncFullSchemaProperty>() {
            @Override
            public Observable<SqlSyncFullSchemaProperty> typeConvertAsync(SyncFullSchemaPropertiesInner inner) {
                return Observable.just((SqlSyncFullSchemaProperty) new SqlSyncFullSchemaPropertyImpl(inner));
            }
        };

        return converter.convert(this.sqlServerManager.inner().syncGroups()
            .listHubSchemas(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.name()));
    }

    @Override
    public Observable<SqlSyncFullSchemaProperty> listHubSchemasAsync() {
        return this.sqlServerManager.inner().syncGroups()
            .listHubSchemasAsync(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.name())
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
    public PagedList<SqlSyncGroupLogProperty> listLogs(String startTime, String endTime, String type) {
        final PagedListConverter<SyncGroupLogPropertiesInner, SqlSyncGroupLogProperty> converter = new PagedListConverter<SyncGroupLogPropertiesInner, SqlSyncGroupLogProperty>() {
            @Override
            public Observable<SqlSyncGroupLogProperty> typeConvertAsync(SyncGroupLogPropertiesInner inner) {
                return Observable.just((SqlSyncGroupLogProperty) new SqlSyncGroupLogPropertyImpl(inner));
            }
        };

        return converter.convert(this.sqlServerManager.inner().syncGroups()
            .listLogs(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.name(), startTime, endTime, type));
    }

    @Override
    public Observable<SqlSyncGroupLogProperty> listLogsAsync(String startTime, String endTime, String type) {
        return this.sqlServerManager.inner().syncGroups()
            .listLogsAsync(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.name(), startTime, endTime, type)
            .flatMap(new Func1<Page<SyncGroupLogPropertiesInner>, Observable<SyncGroupLogPropertiesInner>>() {
                @Override
                public Observable<SyncGroupLogPropertiesInner> call(Page<SyncGroupLogPropertiesInner> syncGroupLogPropertiesInnerPage) {
                    return Observable.from(syncGroupLogPropertiesInnerPage.items());
                }
            })
            .map(new Func1<SyncGroupLogPropertiesInner, SqlSyncGroupLogProperty>() {
                @Override
                public SqlSyncGroupLogProperty call(SyncGroupLogPropertiesInner syncGroupLogPropertiesInner) {
                    return new SqlSyncGroupLogPropertyImpl(syncGroupLogPropertiesInner);
                }
            });
    }

    @Override
    public void triggerSynchronization() {
        this.sqlServerManager.inner().syncGroups()
            .triggerSync(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.name());
    }

    @Override
    public Completable triggerSynchronizationAsync() {
        return this.sqlServerManager.inner().syncGroups()
            .triggerSyncAsync(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.name()).toCompletable();
    }

    @Override
    public void cancelSynchronization() {
        this.sqlServerManager.inner().syncGroups()
            .cancelSync(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.name());
    }

    @Override
    public Completable cancelSynchronizationAsync() {
        return this.sqlServerManager.inner().syncGroups()
            .cancelSyncAsync(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.name()).toCompletable();
    }

    @Override
    public SqlSyncGroupImpl withExistingSqlServer(String resourceGroupName, String sqlServerName) {
        this.resourceGroupName = resourceGroupName;
        this.sqlServerName = sqlServerName;
        return this;
    }

    @Override
    public SqlSyncGroupImpl withExistingSqlDatabase(SqlDatabase sqlDatabase) {
        this.resourceGroupName = sqlDatabase.resourceGroupName();
        this.sqlServerName = sqlDatabase.sqlServerName();
        this.sqlDatabaseName = sqlDatabase.name();
        return this;
    }

    @Override
    public SqlSyncGroupImpl withExistingDatabaseName(String databaseName) {
        this.sqlDatabaseName = databaseName;
        return this;
    }

    @Override
    public SqlSyncGroupImpl withSyncDatabaseId(String syncDatabaseId) {
        this.inner().withSyncDatabaseId(syncDatabaseId);
        return this;
    }

    @Override
    public SqlSyncGroupImpl withDatabaseUserName(String userName) {
        this.inner().withHubDatabaseUserName(userName);
        return this;
    }

    @Override
    public SqlSyncGroupImpl withDatabasePassword(String password) {
        this.inner().withHubDatabasePassword(password);
        return this;
    }

    @Override
    public SqlSyncGroupImpl withConflictResolutionPolicyHubWins() {
        this.inner().withConflictResolutionPolicy(SyncConflictResolutionPolicy.HUB_WIN);
        return this;
    }

    @Override
    public SqlSyncGroupImpl withConflictResolutionPolicyMemberWins() {
        this.inner().withConflictResolutionPolicy(SyncConflictResolutionPolicy.MEMBER_WIN);
        return this;
    }

    @Override
    public SqlSyncGroupImpl withInterval(int interval) {
        this.inner().withInterval(interval);
        return this;
    }

    @Override
    public SqlSyncGroupImpl withSchema(SyncGroupSchema schema) {
        this.inner().withSchema(schema);
        return this;
    }

    @Override
    public Update update() {
        this.setPendingOperation(PendingOperation.ToBeUpdated);
        return this;
    }

    @Override
    public Observable<SqlSyncGroup> createResourceAsync() {
        final SqlSyncGroupImpl self = this;
        return this.sqlServerManager.inner().syncGroups()
            .createOrUpdateAsync(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.name(), this.inner())
            .map(new Func1<SyncGroupInner, SqlSyncGroup>() {
                @Override
                public SqlSyncGroup call(SyncGroupInner syncGroupInner) {
                    self.setInner(syncGroupInner);
                    return self;
                }
            });
    }

    @Override
    public Observable<SqlSyncGroup> updateResourceAsync() {
        return createResourceAsync();
    }

    @Override
    public Observable<Void> deleteResourceAsync() {
        return this.sqlServerManager.inner().syncGroups()
            .deleteAsync(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.name());
    }

    @Override
    protected Observable<SyncGroupInner> getInnerAsync() {
        return this.sqlServerManager.inner().syncGroups()
            .getAsync(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.name());
    }

    @Override
    public void delete() {
        this.sqlServerManager.inner().syncGroups()
            .delete(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.name());
    }

    @Override
    public Completable deleteAsync() {
        return this.deleteResourceAsync().toCompletable();
    }

    @Override
    public SqlSyncMemberOperations.SqlSyncMemberActionsDefinition syncMembers() {
        if (this.syncMemberOps == null) {
            this.syncMemberOps = new SqlSyncMemberOperationsImpl(this, this.sqlServerManager);
        }

        return this.syncMemberOps;
    }

}
