/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.keyvault.implementation;

import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.util.polling.LongRunningOperationStatus;
import com.azure.management.resources.fluentcore.arm.collection.implementation.CreatableWrappersImpl;
import com.azure.security.keyvault.secrets.SecretAsyncClient;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import com.azure.management.keyvault.Secret;
import com.azure.management.keyvault.Secrets;
import com.azure.management.keyvault.Vault;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * The implementation of Secrets and its parent interfaces.
 */
class SecretsImpl
        extends CreatableWrappersImpl<
                Secret,
                SecretImpl,
                KeyVaultSecret>
        implements Secrets {
    private final SecretAsyncClient inner;
    private final Vault vault;

    SecretsImpl(SecretAsyncClient client, Vault vault) {
        this.inner = client;
        this.vault = vault;
    }

    @Override
    public SecretImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    protected SecretImpl wrapModel(String name) {
        // TODO value not valid
        return new SecretImpl(name, new KeyVaultSecret(name, null), vault);
    }

    @Override
    public Secret getById(String id) {
        return getByIdAsync(id).block();
    }

    @Override
    public Mono<Secret> getByIdAsync(String id) {
        // TODO name in getSecret
        return inner.getSecret(id).map(this::wrapModel);
    }

    @Override
    protected SecretImpl wrapModel(KeyVaultSecret inner) {
        if (inner == null) {
            return null;
        }
        return new SecretImpl(inner.getName(), inner, vault);
    }

    @Override
    public Mono<Void> deleteByIdAsync(String id) {
        // TODO name in beginDeleteSecret
        return inner.beginDeleteSecret(id).last().flatMap(asyncPollResponse -> {
            if (asyncPollResponse.getStatus() == LongRunningOperationStatus.SUCCESSFULLY_COMPLETED) {
                return asyncPollResponse.getFinalResult();
            } else {
                return Mono.error(new RuntimeException("polling completed unsuccessfully with status:" + asyncPollResponse.getStatus()));
            }
        });
    }

    @Override
    public PagedIterable<Secret> list() {
        return new PagedIterable<>(listAsync());
    }

    @Override
    public PagedFlux<Secret> listAsync() {
        // TODO async for PagedFlux
        return vault.secretClient().listPropertiesOfSecrets()
                .mapPage(p -> {
                    KeyVaultSecret secret = vault.secretClient().getSecret(p.getId(), p.getVersion()).block();
                    return wrapModel(secret);
                });
    }

    @Override
    public Mono<Secret> getByNameAsync(final String name) {
        return inner.getSecret(name).map(this::wrapModel);
    }

    @Override
    public Secret getByName(String name) {
        return getByNameAsync(name).block();
    }

    @Override
    public Secret getByNameAndVersion(String name, String version) {
        return getByNameAndVersionAsync(name, version).block();
    }

    @Override
    public Mono<Secret> getByNameAndVersionAsync(final String name, final String version) {
        return inner.getSecret(name, version).map(this::wrapModel);
    }
}
