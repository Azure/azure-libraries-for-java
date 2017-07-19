/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault.implementation;

import com.microsoft.azure.keyvault.models.Attributes;
import com.microsoft.azure.keyvault.models.KeyAttributes;
import com.microsoft.azure.keyvault.models.KeyBundle;
import com.microsoft.azure.keyvault.requests.CreateKeyRequest;
import com.microsoft.azure.keyvault.requests.UpdateKeyRequest;
import com.microsoft.azure.keyvault.webkey.JsonWebKey;
import com.microsoft.azure.keyvault.webkey.JsonWebKeyOperation;
import com.microsoft.azure.keyvault.webkey.JsonWebKeyType;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.keyvault.Key;
import com.microsoft.azure.management.keyvault.Vault;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func1;

import java.util.List;
import java.util.Map;

/**
 * Implementation for Vault and its parent interfaces.
 */
@LangDefinition
class KeyImpl
        extends CreatableUpdatableImpl<
                Key,
                KeyBundle,
                KeyImpl>
        implements
                Key,
                Key.Definition,
                Key.Update {

    private final Vault vault;
    private CreateKeyRequest.Builder createKeyRequest;
    private UpdateKeyRequest.Builder updateKeyRequest;

    KeyImpl(String name, KeyBundle innerObject, Vault vault) {
        super(name, innerObject);
        this.vault = vault;
        this.updateKeyRequest = new UpdateKeyRequest.Builder(vault.vaultUri(), name);
    }

    @Override
    public String id() {
        return inner().keyIdentifier().identifier();
    }

    @Override
    public JsonWebKey jsonWebKey() {
        return inner().key();
    }

    @Override
    public KeyAttributes attributes() {
        return inner().attributes();
    }

    @Override
    public Map<String, String> tags() {
        return inner().tags();
    }

    @Override
    public boolean managed() {
        return Utils.toPrimitiveBoolean(inner().managed());
    }

    @Override
    protected Observable<KeyBundle> getInnerAsync() {
        return Observable.from(vault.client().getKeyAsync(id(), null));
    }

    @Override
    public KeyImpl withTags(Map<String, String> tags) {
        createKeyRequest.withTags(tags);
        updateKeyRequest.withTags(tags);
        return this;
    }

    @Override
    public boolean isInCreateMode() {
        return id() == null;
    }

    @Override
    public Observable<Key> createResourceAsync() {
        return Observable.from(vault.client().createKeyAsync(createKeyRequest.build(), null))
                .map(innerToFluentMap(this))
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        createKeyRequest = null;
                        updateKeyRequest = new UpdateKeyRequest.Builder(vault.vaultUri(), name());
                    }
                });
    }

    @Override
    public Observable<Key> updateResourceAsync() {
        Observable<Key> set = Observable.just((Key) this);
        if (createKeyRequest != null) {
            set = createResourceAsync();
        }
        return set.flatMap(new Func1<Key, Observable<KeyBundle>>() {
            @Override
            public Observable<KeyBundle> call(Key secret) {
                return Observable.from(vault.client().updateKeyAsync(updateKeyRequest.build(), null));
            }
        }).flatMap(new Func1<KeyBundle, Observable<Key>>() {
            @Override
            public Observable<Key> call(KeyBundle secretBundle) {
                return refreshAsync();
            }
        }).doOnCompleted(new Action0() {
            @Override
            public void call() {
                createKeyRequest = null;
                updateKeyRequest = new UpdateKeyRequest.Builder(vault.vaultUri(), name());
            }
        });
    }

    @Override
    public KeyImpl withAttributes(Attributes attributes) {
        createKeyRequest.withAttributes(attributes);
        updateKeyRequest.withAttributes(attributes);
        return this;
    }

    @Override
    public KeyImpl withKeyType(JsonWebKeyType keyType) {
        return null;
    }

    @Override
    public KeyImpl withKeyOperations(List<JsonWebKeyOperation> keyOperations) {
        return null;
    }
}
