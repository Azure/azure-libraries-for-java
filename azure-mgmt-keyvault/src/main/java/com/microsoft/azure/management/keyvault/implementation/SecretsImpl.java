/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault.implementation;

import com.microsoft.azure.ListOperationCallback;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.keyvault.KeyVaultClient;
import com.microsoft.azure.keyvault.SecretIdentifier;
import com.microsoft.azure.keyvault.models.SecretBundle;
import com.microsoft.azure.keyvault.models.SecretItem;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.keyvault.Secret;
import com.microsoft.azure.management.keyvault.Secrets;
import com.microsoft.azure.management.keyvault.Vault;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.CreatableWrappersImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import com.microsoft.rest.protocol.SerializerAdapter;
import rx.Completable;
import rx.Observable;

import java.io.IOException;

/**
 * The implementation of Secrets and its parent interfaces.
 */
@LangDefinition
class SecretsImpl
        extends CreatableWrappersImpl<
        Secret,
        SecretImpl,
        SecretBundle>
        implements Secrets {
    private final KeyVaultClient inner;
    private final Vault vault;

    private final PagedListConverter<SecretItem, Secret> itemConverter = new PagedListConverter<SecretItem, Secret>() {
        @Override
        public Observable<Secret> typeConvertAsync(SecretItem inner) {
            return Observable.just((Secret) wrapModel(inner));
        }
    };

    SecretsImpl(KeyVaultClient client, Vault vault) {
        this.inner = client;
        this.vault = vault;
    }

    @Override
    public SecretImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    protected SecretImpl wrapModel(String name) {
        return new SecretImpl(name, new SecretBundle(), vault);
    }

    @Override
    public Secret getById(String id) {
        return wrapModel(inner.getSecret(id));
    }

    @Override
    public Observable<Secret> getByIdAsync(String id) {
        return Observable.from(getByIdAsync(id, null));
    }

    @Override
    public ServiceFuture<Secret> getByIdAsync(final String id, final ServiceCallback<Secret> callback) {
        return new KeyVaultFutures.ServiceFutureConverter<SecretBundle, Secret>() {

            @Override
            protected ServiceFuture<SecretBundle> callAsync() {
                return inner.getSecretAsync(id, null);
            }

            @Override
            protected Secret wrapModel(SecretBundle secretBundle) {
                return SecretsImpl.this.wrapModel(secretBundle);
            }
        }.toFuture(callback);
    }

    @Override
    protected SecretImpl wrapModel(SecretBundle inner) {
        if (inner == null) {
            return null;
        }
        return new SecretImpl(inner.secretIdentifier().name(), inner, vault);
    }

    private SecretImpl wrapModel(SecretItem inner) {
        if (inner == null) {
            return null;
        }
        SerializerAdapter<?> serializer = vault.manager().inner().restClient().serializerAdapter();
        try {
            return wrapModel(serializer.<SecretBundle>deserialize(serializer.serialize(inner), SecretBundle.class));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        SecretIdentifier identifier = new SecretIdentifier(id);
        return Completable.fromFuture(inner.deleteSecretAsync(identifier.vault(), identifier.name(), null));
    }

    @Override
    public PagedList<Secret> list() {
        return itemConverter.convert(inner.listSecrets(vault.vaultUri()));
    }

    @Override
    public Observable<Secret> listAsync() {
        return new KeyVaultFutures.ListCallbackObserver<SecretItem, Secret>() {
            @Override
            protected void list(ListOperationCallback<SecretItem> callback) {
                inner.listSecretsAsync(vault.vaultUri(), callback);
            }

            @Override
            protected Observable<Secret> typeConvertAsync(SecretItem secretItem) {
                return Observable.just((Secret) SecretsImpl.this.wrapModel(secretItem));
            }
        }.toObservable();
    }

    @Override
    public Observable<Secret> getByNameAsync(final String name) {
        return new KeyVaultFutures.ServiceFutureConverter<SecretBundle, Secret>() {

            @Override
            ServiceFuture<SecretBundle> callAsync() {
                return inner.getSecretAsync(vault.vaultUri(), name, null);
            }

            @Override
            Secret wrapModel(SecretBundle o) {
                return null;
            }
        }.toObservable();
    }

    @Override
    public Secret getByName(String name) {
        return wrapModel(inner.getSecret(vault.vaultUri(), name));
    }

    @Override
    public Secret getByNameAndVersion(String name, String version) {
        return wrapModel(inner.getSecret(vault.vaultUri(), name, version));
    }

    @Override
    public Observable<Secret> getByNameAndVersionAsync(final String name, final String version) {
        return new KeyVaultFutures.ServiceFutureConverter<SecretBundle, Secret>() {

            @Override
            ServiceFuture<SecretBundle> callAsync() {
                return inner.getSecretAsync(vault.vaultUri(), name, version, null);
            }

            @Override
            Secret wrapModel(SecretBundle o) {
                return null;
            }
        }.toObservable();
    }
}
