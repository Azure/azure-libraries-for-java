/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import com.microsoft.azure.management.sql.SqlSyncGroup;
import com.microsoft.azure.management.sql.SqlSyncGroupOperations;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Implementation for SQL Sync Group operations.
 */
@LangDefinition
public class SqlSyncGroupOperationsImpl
    implements
        SqlSyncGroupOperations,
        SqlSyncGroupOperations.SqlSyncGroupActionsDefinition {

    protected SqlServerManager sqlServerManager;
    protected String resourceGroupName;
    protected String sqlServerName;
    protected String sqlDatabaseName;
    protected SqlDatabaseImpl sqlDatabase;

    SqlSyncGroupOperationsImpl(SqlDatabaseImpl parent, SqlServerManager sqlServerManager) {
        Objects.requireNonNull(parent);
        Objects.requireNonNull(sqlServerManager);
        this.sqlDatabase = parent;
        this.sqlServerManager = sqlServerManager;
        this.resourceGroupName = parent.resourceGroupName;
        this.sqlServerName = parent.sqlServerName;
        this.sqlDatabaseName = parent.name();
    }

    SqlSyncGroupOperationsImpl(SqlServerManager sqlServerManager) {
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
    }

    @Override
    public SqlSyncGroup getBySqlServer(String resourceGroupName, String sqlServerName, String databaseName, String name) {
        SyncGroupInner syncGroupInner = this.sqlServerManager.inner().syncGroups()
            .get(resourceGroupName, sqlServerName, databaseName, name);
        return syncGroupInner != null ? new SqlSyncGroupImpl(resourceGroupName, sqlServerName, databaseName, name, syncGroupInner, this.sqlServerManager) : null;
    }

    @Override
    public Observable<SqlSyncGroup> getBySqlServerAsync(final String resourceGroupName, final String sqlServerName, final String databaseName, final String name) {
        return this.sqlServerManager.inner().syncGroups()
            .getAsync(resourceGroupName, sqlServerName, databaseName, name)
            .map(new Func1<SyncGroupInner, SqlSyncGroup>() {
                @Override
                public SqlSyncGroup call(SyncGroupInner syncGroupInner) {
                    return new SqlSyncGroupImpl(resourceGroupName, sqlServerName, databaseName, name, syncGroupInner, sqlServerManager);
                }
            });
    }

    @Override
    public PagedList<String> listSyncDatabaseIds(String locationName) {
        final SqlSyncGroupOperationsImpl self = this;
        final PagedListConverter<SyncDatabaseIdPropertiesInner, String> converter = new PagedListConverter<SyncDatabaseIdPropertiesInner, String>() {
            @Override
            public Observable<String> typeConvertAsync(SyncDatabaseIdPropertiesInner inner) {
                return Observable.just(inner.id());
            }
        };
        return converter.convert(this.sqlServerManager.inner().syncGroups()
            .listSyncDatabaseIds(locationName));
    }

    @Override
    public Observable<String> listSyncDatabaseIdsAsync(String locationName) {
        return this.sqlServerManager.inner().syncGroups()
            .listSyncDatabaseIdsAsync(locationName)
            .flatMap(new Func1<Page<SyncDatabaseIdPropertiesInner>, Observable<SyncDatabaseIdPropertiesInner>>() {
                @Override
                public Observable<SyncDatabaseIdPropertiesInner> call(Page<SyncDatabaseIdPropertiesInner> syncDatabaseIdPropertiesInnerPage) {
                    return Observable.from(syncDatabaseIdPropertiesInnerPage.items());
                }
            })
            .map(new Func1<SyncDatabaseIdPropertiesInner, String>() {
                @Override
                public String call(SyncDatabaseIdPropertiesInner syncDatabaseIdPropertiesInner) {
                    return syncDatabaseIdPropertiesInner.id();
                }
            });
    }

    @Override
    public PagedList<String> listSyncDatabaseIds(Region region) {
        return this.listSyncDatabaseIds(region.name());
    }

    @Override
    public Observable<String> listSyncDatabaseIdsAsync(Region region) {
        return this.listSyncDatabaseIdsAsync(region.name());
    }

    @Override
    public SqlSyncGroupImpl define(String name) {
        SqlSyncGroupImpl result = new SqlSyncGroupImpl(name, new SyncGroupInner(), this.sqlServerManager);
        result.setPendingOperation(ExternalChildResourceImpl.PendingOperation.ToBeCreated);
        return (this.sqlDatabase != null) ? result.withExistingSqlDatabase(this.sqlDatabase) : result;
    }

    @Override
    public SqlSyncGroup get(String name) {
        if (this.sqlDatabase == null) {
            return null;
        }
        return this.getBySqlServer(this.sqlDatabase.resourceGroupName(), this.sqlDatabase.sqlServerName(), this.sqlDatabase.name(), name);
    }

    @Override
    public Observable<SqlSyncGroup> getAsync(String name) {
        if (this.sqlDatabase == null) {
            return null;
        }
        return this.getBySqlServerAsync(this.sqlDatabase.resourceGroupName(), this.sqlDatabase.sqlServerName(), this.sqlDatabase.name(), name);
    }

    @Override
    public SqlSyncGroup getById(String id) {
        Objects.requireNonNull(id);
        try {
            ResourceId resourceId = ResourceId.fromString(id);
            return this.getBySqlServer(resourceId.resourceGroupName(),
                resourceId.parent().parent().name(),
                resourceId.parent().name(),
                resourceId.name());
        } catch (NullPointerException e) {
        }
        return null;
    }

    @Override
    public Observable<SqlSyncGroup> getByIdAsync(String id) {
        Objects.requireNonNull(id);
        try {
            ResourceId resourceId = ResourceId.fromString(id);
            return this.getBySqlServerAsync(resourceId.resourceGroupName(),
                resourceId.parent().parent().name(),
                resourceId.parent().name(),
                resourceId.name());
        } catch (NullPointerException e) {
        }
        return null;
    }

    @Override
    public void delete(String name) {
        if (this.sqlDatabase == null) {
            return;
        }
        this.sqlServerManager.inner().syncGroups()
            .delete(this.sqlDatabase.resourceGroupName(), this.sqlDatabase.sqlServerName(), this.sqlDatabase.name(), name);
    }

    @Override
    public Completable deleteAsync(String name) {
        if (this.sqlDatabase == null) {
            return null;
        }
        return this.sqlServerManager.inner().syncGroups()
            .deleteAsync(this.sqlDatabase.resourceGroupName(), this.sqlDatabase.sqlServerName(), this.sqlDatabase.name(), name).toCompletable();
    }

    @Override
    public void deleteById(String id) {
        Objects.requireNonNull(id);
        try {
            ResourceId resourceId = ResourceId.fromString(id);
            this.sqlServerManager.inner().syncGroups().delete(resourceId.resourceGroupName(),
                resourceId.parent().parent().name(),
                resourceId.parent().name(),
                resourceId.name());
        } catch (NullPointerException e) {
        }
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        try {
            ResourceId resourceId = ResourceId.fromString(id);
            return this.sqlServerManager.inner().syncGroups().deleteAsync(resourceId.resourceGroupName(),
                resourceId.parent().parent().name(),
                resourceId.parent().name(),
                resourceId.name()).toCompletable();
        } catch (NullPointerException e) {
        }
        return null;
    }

    @Override
    public List<SqlSyncGroup> list() {
        List<SqlSyncGroup> sqlSyncGroups = new ArrayList<>();
        if (this.sqlDatabase != null) {
            PagedList<SyncGroupInner> syncGroupInners = this.sqlServerManager.inner().syncGroups()
                .listByDatabase(this.sqlDatabase.resourceGroupName(), this.sqlDatabase.sqlServerName(), this.sqlDatabase.name());
            if (syncGroupInners != null) {
                for (SyncGroupInner groupInner : syncGroupInners) {
                    sqlSyncGroups.add(new SqlSyncGroupImpl(groupInner.name(), this.sqlDatabase, groupInner, this.sqlServerManager));
                }
            }
        }
        return Collections.unmodifiableList(sqlSyncGroups);
    }

    @Override
    public Observable<SqlSyncGroup> listAsync() {
        final SqlSyncGroupOperationsImpl self = this;
        return this.sqlServerManager.inner().syncGroups()
            .listByDatabaseAsync(this.sqlDatabase.resourceGroupName(), this.sqlDatabase.sqlServerName(), this.sqlDatabase.name())
            .flatMap(new Func1<Page<SyncGroupInner>, Observable<SyncGroupInner>>() {
                @Override
                public Observable<SyncGroupInner> call(Page<SyncGroupInner> syncGroupInnerPage) {
                    return Observable.from(syncGroupInnerPage.items());
                }
            })
            .map(new Func1<SyncGroupInner, SqlSyncGroup>() {
                @Override
                public SqlSyncGroup call(SyncGroupInner syncGroupInner) {
                    return new SqlSyncGroupImpl(syncGroupInner.name(), self.sqlDatabase, syncGroupInner, self.sqlServerManager);
                }
            });
    }
}
