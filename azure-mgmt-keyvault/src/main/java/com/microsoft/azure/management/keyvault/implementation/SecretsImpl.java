/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault.implementation;

import com.microsoft.azure.keyvault.KeyVaultClient;
import com.microsoft.azure.keyvault.models.SecretBundle;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.keyvault.Secret;
import com.microsoft.azure.management.keyvault.Secrets;
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
class SecretsImpl
        extends CreatableWrappersImpl<
        Secret,
        SecretImpl,
        SecretBundle>
        implements Secrets {
    private final KeyVaultClient inner;
    private final Vault vault;

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
    public ServiceFuture<Secret> getByIdAsync(String id, final ServiceCallback<Secret> callback) {
        return KeyVaultFutures.fromInner(inner.getSecretAsync(id, null), callback, vault);
    }

    @Override
    protected SecretImpl wrapModel(SecretBundle inner) {
        return new SecretImpl(KeyVaultUtils.nameFromId(inner.id()), inner, vault);
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        return Completable.fromFuture(inner.deleteSecretAsync(KeyVaultUtils.vaultUrlFromid(id), KeyVaultUtils.nameFromId(id), null));
    }
}
