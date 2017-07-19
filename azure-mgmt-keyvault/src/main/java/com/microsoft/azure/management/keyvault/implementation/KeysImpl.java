/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault.implementation;

import com.microsoft.azure.keyvault.KeyIdentifier;
import com.microsoft.azure.keyvault.KeyVaultClient;
import com.microsoft.azure.keyvault.models.KeyBundle;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.keyvault.Key;
import com.microsoft.azure.management.keyvault.Keys;
import com.microsoft.azure.management.keyvault.Vault;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.CreatableWrappersImpl;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;
import rx.Observable;

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

    @Override
    public Completable deleteByIdAsync(String id) {
        KeyIdentifier identifier = new KeyIdentifier(id);
        return Completable.fromFuture(inner.deleteSecretAsync(identifier.vault(), identifier.name(), null));
    }
}
