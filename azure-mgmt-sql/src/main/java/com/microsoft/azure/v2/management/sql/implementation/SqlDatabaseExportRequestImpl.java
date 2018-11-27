/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.dag.FunctionalTaskItem;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.ExecutableImpl;
import com.microsoft.azure.management.sql.AuthenticationType;
import com.microsoft.azure.management.sql.SqlDatabase;
import com.microsoft.azure.management.sql.SqlDatabaseExportRequest;
import com.microsoft.azure.management.sql.SqlDatabaseImportExportResponse;
import com.microsoft.azure.management.sql.StorageKeyType;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.azure.management.storage.StorageAccountKey;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Func1;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.List;
import java.util.Objects;

/**
 * Implementation for SqlDatabaseExportRequest.
 */
@LangDefinition
public class SqlDatabaseExportRequestImpl extends ExecutableImpl<SqlDatabaseImportExportResponse>
    implements
        SqlDatabaseExportRequest,
        SqlDatabaseExportRequest.SqlDatabaseExportRequestDefinition {

    private final SqlDatabaseImpl sqlDatabase;
    private final SqlServerManager sqlServerManager;
    private ExportRequestInner inner;

    SqlDatabaseExportRequestImpl(SqlDatabaseImpl sqlDatabase, SqlServerManager sqlServerManager) {
        this.sqlDatabase = sqlDatabase;
        this.sqlServerManager = sqlServerManager;
        this.inner = new ExportRequestInner();
    }

    @Override
    public SqlDatabase parent() {
        return this.sqlDatabase;
    }

    @Override
    public ExportRequestInner inner() {
        return this.inner;
    }

    @Override
    public Observable<SqlDatabaseImportExportResponse> executeWorkAsync() {
        return this.sqlServerManager.inner().databases()
            .exportAsync(this.sqlDatabase.resourceGroupName, this.sqlDatabase.sqlServerName, this.sqlDatabase.name(), this.inner())
            .map(new Func1<ImportExportResponseInner, SqlDatabaseImportExportResponse>() {
                @Override
                public SqlDatabaseImportExportResponse call(ImportExportResponseInner importExportResponseInner) {
                    return new SqlDatabaseImportExportResponseImpl(importExportResponseInner);
                }
            });
    }

    @Override
    public SqlDatabaseExportRequestImpl exportTo(String storageUri) {
        if (this.inner == null) {
            this.inner = new ExportRequestInner();
        }
        this.inner.withStorageUri(storageUri);
        return this;
    }

    private Observable<Indexable> getOrCreateStorageAccountContainer(final StorageAccount storageAccount, final String containerName, final String fileName, final FunctionalTaskItem.Context context) {
        final SqlDatabaseExportRequestImpl self = this;
        return storageAccount.getKeysAsync()
            .flatMap(new Func1<List<StorageAccountKey>, Observable<StorageAccountKey>>() {
                @Override
                public Observable<StorageAccountKey> call(List<StorageAccountKey> storageAccountKeys) {
                    return Observable.from(storageAccountKeys).first();
                }
            })
            .flatMap(new Func1<StorageAccountKey, Observable<Indexable>>() {
                @Override
                public Observable<Indexable> call(StorageAccountKey storageAccountKey) {
                    self.inner.withStorageUri(String.format("%s%s/%s", storageAccount.endPoints().primary().blob(), containerName, fileName));
                    self.inner.withStorageKeyType(StorageKeyType.STORAGE_ACCESS_KEY);
                    self.inner.withStorageKey(storageAccountKey.value());
                    try {
                        CloudStorageAccount cloudStorageAccount =
                            CloudStorageAccount.parse(String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net", storageAccount.name(), storageAccountKey.value()));
                        CloudBlobClient blobClient = cloudStorageAccount.createCloudBlobClient();
                        blobClient.getContainerReference(containerName)
                            .createIfNotExists();
                    } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                        throw Exceptions.propagate(indexOutOfBoundsException);
                    } catch (URISyntaxException syntaxException) {
                        throw Exceptions.propagate(syntaxException);
                    } catch (StorageException stgException) {
                        throw Exceptions.propagate(stgException);
                    } catch (InvalidKeyException keyException) {
                        throw Exceptions.propagate(keyException);
                    }
                    return context.voidObservable();
                }
            });
    }

    @Override
    public SqlDatabaseExportRequestImpl exportTo(final StorageAccount storageAccount, final String containerName, final String fileName) {
        Objects.requireNonNull(storageAccount);
        Objects.requireNonNull(containerName);
        Objects.requireNonNull(fileName);
        if (this.inner == null) {
            this.inner = new ExportRequestInner();
        }
        final SqlDatabaseExportRequestImpl self = this;
        this.addDependency(new FunctionalTaskItem() {
            @Override
            public Observable<Indexable> call(final Context context) {
                return getOrCreateStorageAccountContainer(storageAccount, containerName, fileName, context);
            }
        });
        return this;
    }

    @Override
    public SqlDatabaseExportRequestImpl exportTo(final Creatable<StorageAccount> storageAccountCreatable, final String containerName, final String fileName) {
        if (this.inner == null) {
            this.inner = new ExportRequestInner();
        }
        this.addDependency(new FunctionalTaskItem() {
            @Override
            public Observable<Indexable> call(final Context context) {
                return storageAccountCreatable.createAsync()
                    .last()
                    .flatMap(new Func1<Indexable, Observable<Indexable>>() {
                        @Override
                        public Observable<Indexable> call(final Indexable storageAccount) {
                            return getOrCreateStorageAccountContainer((StorageAccount) storageAccount, containerName, fileName, context);
                        }
                    });
            }
        });
        return this;
    }

    SqlDatabaseExportRequestImpl withStorageKeyType(StorageKeyType storageKeyType) {
        this.inner.withStorageKeyType(storageKeyType);
        return this;
    }

    @Override
    public SqlDatabaseExportRequestImpl withStorageAccessKey(String storageAccessKey) {
        this.inner.withStorageKeyType(StorageKeyType.STORAGE_ACCESS_KEY);
        this.inner.withStorageKey(storageAccessKey);
        return this;
    }

    @Override
    public SqlDatabaseExportRequestImpl withSharedAccessKey(String sharedAccessKey) {
        this.inner.withStorageKeyType(StorageKeyType.SHARED_ACCESS_KEY);
        this.inner.withStorageKey(sharedAccessKey);
        return this;
    }

    SqlDatabaseExportRequestImpl withStorageKey(String storageKey) {
        this.inner.withStorageKey(storageKey);
        return this;
    }

    SqlDatabaseExportRequestImpl withAuthenticationType(AuthenticationType authenticationType) {
        this.inner.withAuthenticationType(authenticationType);
        return this;
    }

    @Override
    public SqlDatabaseExportRequestImpl withSqlAdministratorLoginAndPassword(String administratorLogin, String administratorPassword) {
        this.inner.withAuthenticationType(AuthenticationType.SQL);
        return this.withLoginAndPassword(administratorLogin, administratorPassword);
    }

    @Override
    public SqlDatabaseExportRequestImpl withActiveDirectoryLoginAndPassword(String administratorLogin, String administratorPassword) {
        this.inner.withAuthenticationType(AuthenticationType.ADPASSWORD);
        return this.withLoginAndPassword(administratorLogin, administratorPassword);
    }

    SqlDatabaseExportRequestImpl withLoginAndPassword(String administratorLogin, String administratorPassword) {
        this.inner.withAdministratorLogin(administratorLogin);
        this.inner.withAdministratorLoginPassword(administratorPassword);
        return this;
    }
}
