/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.resources.fluentcore.dag.FunctionalTaskItem;
import com.microsoft.azure.v2.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.ExecutableImpl;
import com.microsoft.azure.v2.management.sql.AuthenticationType;
import com.microsoft.azure.v2.management.sql.ImportExtensionRequest;
import com.microsoft.azure.v2.management.sql.SqlDatabaseImportExportResponse;
import com.microsoft.azure.v2.management.sql.SqlDatabaseImportRequest;
import com.microsoft.azure.v2.management.sql.SqlDatabase;
import com.microsoft.azure.v2.management.sql.StorageKeyType;
import com.microsoft.azure.v2.management.storage.StorageAccount;
import com.microsoft.azure.v2.management.storage.StorageAccountKey;
import io.reactivex.Observable;

import java.util.List;
import java.util.Objects;

/**
 * Implementation for SqlDatabaseImportRequest.
 */
@LangDefinition
public class SqlDatabaseImportRequestImpl extends ExecutableImpl<SqlDatabaseImportExportResponse>
    implements
    SqlDatabaseImportRequest,
    SqlDatabaseImportRequest.SqlDatabaseImportRequestDefinition {

    private final SqlDatabaseImpl sqlDatabase;
    private final SqlServerManager sqlServerManager;
    private ImportExtensionRequest inner;

    SqlDatabaseImportRequestImpl(SqlDatabaseImpl sqlDatabase, SqlServerManager sqlServerManager) {
        this.sqlDatabase = sqlDatabase;
        this.sqlServerManager = sqlServerManager;
        this.inner = new ImportExtensionRequest();
    }

    @Override
    public SqlDatabase parent() {
        return null;
    }

    @Override
    public ImportExtensionRequest inner() {
        return this.inner;
    }

    @Override
    public Observable<SqlDatabaseImportExportResponse> executeWorkAsync() {
        return this.sqlServerManager.inner().databases()
                .createImportOperationAsync(this.sqlDatabase.resourceGroupName, this.sqlDatabase.sqlServerName, this.sqlDatabase.name(), this.inner())
                .flatMap(importExportResponseInner ->
                        this.sqlDatabase
                                .refreshAsync()
                                .map(sqlDatabase -> (SqlDatabaseImportExportResponse) new SqlDatabaseImportExportResponseImpl(importExportResponseInner)))
                .toObservable();
    }

    private Observable<Indexable> getOrCreateStorageAccountContainer(final StorageAccount storageAccount, final String containerName, final String fileName, final FunctionalTaskItem.Context context) {
        final SqlDatabaseImportRequestImpl self = this;
        return storageAccount.getKeysAsync()
                .flatMap(storageAccountKeys -> Observable.fromIterable(storageAccountKeys).firstElement())
                .flatMapObservable(storageAccountKey ->  {
                    self.inner.withStorageUri(String.format("%s%s/%s", storageAccount.endPoints().primary().blob(), containerName, fileName));
                    self.inner.withStorageKeyType(StorageKeyType.STORAGE_ACCESS_KEY);
                    self.inner.withStorageKey(storageAccountKey.value());
                    return context.voidObservable();
                });
    }

    @Override
    public SqlDatabaseImportRequestImpl importFrom(String storageUri) {
        if (this.inner == null) {
            this.inner = new ImportExtensionRequest();
        }
        this.inner.withStorageUri(storageUri);
        return this;
    }

    @Override
    public SqlDatabaseImportRequestImpl importFrom(final StorageAccount storageAccount, final String containerName, final String fileName) {
        Objects.requireNonNull(storageAccount);
        Objects.requireNonNull(containerName);
        Objects.requireNonNull(fileName);
        if (this.inner == null) {
            this.inner = new ImportExtensionRequest();
        }
        final SqlDatabaseImportRequestImpl self = this;
        this.addDependency(new FunctionalTaskItem() {
            @Override
            public Observable<Indexable> apply(final Context context) {
                return getOrCreateStorageAccountContainer(storageAccount, containerName, fileName, context);
            }
        });
        return this;
    }

    @Override
    public SqlDatabaseImportRequestImpl withStorageAccessKey(String storageAccessKey) {
        this.inner.withStorageKeyType(StorageKeyType.STORAGE_ACCESS_KEY);
        this.inner.withStorageKey(storageAccessKey);
        return this;
    }

    @Override
    public SqlDatabaseImportRequestImpl withSharedAccessKey(String sharedAccessKey) {
        this.inner.withStorageKeyType(StorageKeyType.SHARED_ACCESS_KEY);
        this.inner.withStorageKey(sharedAccessKey);
        return this;
    }

    @Override
    public SqlDatabaseImportRequestImpl withSqlAdministratorLoginAndPassword(String administratorLogin, String administratorPassword) {
        this.inner.withAuthenticationType(AuthenticationType.SQL);
        return this.withLoginAndPassword(administratorLogin, administratorPassword);
    }

    @Override
    public SqlDatabaseImportRequestImpl withActiveDirectoryLoginAndPassword(String administratorLogin, String administratorPassword) {
        this.inner.withAuthenticationType(AuthenticationType.ADPASSWORD);
        return this.withLoginAndPassword(administratorLogin, administratorPassword);
    }

    SqlDatabaseImportRequestImpl withLoginAndPassword(String administratorLogin, String administratorPassword) {
        this.inner.withAdministratorLogin(administratorLogin);
        this.inner.withAdministratorLoginPassword(administratorPassword);
        return this;
    }
}
