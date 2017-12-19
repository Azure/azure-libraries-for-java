/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault.implementation;

import com.microsoft.azure.ListOperationCallback;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.keyvault.KeyIdentifier;
import com.microsoft.azure.keyvault.KeyVaultClient;
import com.microsoft.azure.keyvault.models.KeyBundle;
import com.microsoft.azure.keyvault.models.KeyItem;
import com.microsoft.azure.keyvault.models.SecretBundle;
import com.microsoft.azure.keyvault.models.SecretItem;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.keyvault.Key;
import com.microsoft.azure.management.keyvault.Keys;
import com.microsoft.azure.management.keyvault.Secret;
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
 * The implementation of Vaults and its parent interfaces.
 */
@LangDefinition
class KeysImpl
        extends CreatableWrappersImpl<
        Key,
        KeyImpl,
        KeyBundle>
        implements Keys {
    private final KeyVaultClient inner;
    private final Vault vault;

    private final PagedListConverter<KeyItem, Key> itemConverter = new PagedListConverter<KeyItem, Key>() {
        @Override
        public Observable<Key> typeConvertAsync(KeyItem inner) {
            return Observable.just((Key) wrapModel(inner));
        }
    };

    KeysImpl(KeyVaultClient client, Vault vault) {
        this.inner = client;
        this.vault = vault;
    }

    @Override
    public KeyImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    protected KeyImpl wrapModel(String name) {
        return new KeyImpl(name, new KeyBundle(), vault);
    }

    @Override
    public Key getById(String id) {
        return wrapModel(inner.getKey(id));
    }

    @Override
    public Observable<Key> getByIdAsync(String id) {
        return Observable.from(getByIdAsync(id, null));
    }

    @Override
    public ServiceFuture<Key> getByIdAsync(final String id, final ServiceCallback<Key> callback) {
        return new KeyVaultFutures.ServiceFutureConverter<KeyBundle, Key>() {
            @Override
            protected ServiceFuture<KeyBundle> callAsync() {
                return inner.getKeyAsync(id, null);
            }

            @Override
            protected Key wrapModel(KeyBundle keyBundle) {
                return KeysImpl.this.wrapModel(keyBundle);
            }
        }.toFuture(callback);
    }

    @Override
    protected KeyImpl wrapModel(KeyBundle inner) {
        return new KeyImpl(inner.keyIdentifier().name(), inner, vault);
    }

    private KeyImpl wrapModel(KeyItem inner) {
        if (inner == null) {
            return null;
        }
        SerializerAdapter<?> serializer = vault.manager().inner().restClient().serializerAdapter();
        try {
            return wrapModel(serializer.<KeyBundle>deserialize(serializer.serialize(inner), KeyBundle.class));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        KeyIdentifier identifier = new KeyIdentifier(id);
        return Completable.fromFuture(inner.deleteSecretAsync(identifier.vault(), identifier.name(), null));
    }

    @Override
    public PagedList<Key> list() {
        return itemConverter.convert(inner.listKeys(vault.vaultUri()));
    }

    @Override
    public Observable<Key> listAsync() {
        return new KeyVaultFutures.ListCallbackObserver<KeyItem, Key>() {
            @Override
            protected void list(ListOperationCallback<KeyItem> callback) {
                inner.listKeysAsync(vault.vaultUri(), callback);
            }

            @Override
            protected Key wrapModel(KeyItem keyItem) {
                return KeysImpl.this.wrapModel(keyItem);
            }
        }.toObservable();
    }

    @Override
    public Key restore(byte[] backup) {
        return wrapModel(vault.client().restoreKey(vault.vaultUri(), backup));
    }

    @Override
    public Observable<Key> restoreAsync(final byte[] backup) {
        return new KeyVaultFutures.ServiceFutureConverter<KeyBundle, Key>() {
            @Override
            protected ServiceFuture<KeyBundle> callAsync() {
                return vault.client().restoreKeyAsync(vault.vaultUri(), backup, null);
            }

            @Override
            protected Key wrapModel(KeyBundle keyBundle) {
                return KeysImpl.this.wrapModel(keyBundle);
            }
        }.toObservable();
    }
}
