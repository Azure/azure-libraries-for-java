/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.keyvault.implementation;

import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedIterable;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import com.azure.management.keyvault.Secret;
import com.azure.management.keyvault.Vault;
import com.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.azure.management.resources.fluentcore.utils.Utils;
import com.azure.security.keyvault.secrets.models.SecretProperties;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

/**
 * Implementation for Vault and its parent interfaces.
 */
class SecretImpl
        extends CreatableUpdatableImpl<
                Secret,
                KeyVaultSecret,
                SecretImpl>
        implements
        Secret,
        Secret.Definition,
        Secret.Update {

    private final Vault vault;

    private String valueToSet;

    SecretImpl(String name, KeyVaultSecret innerObject, Vault vault) {
        super(name, innerObject);
        this.vault = vault;
    }

    private SecretImpl wrapModel(KeyVaultSecret secret) {
        return new SecretImpl(secret.getName(), secret, vault);
    }

    @Override
    public String getId() {
        return getInner().getId();
    }

    @Override
    public String getValue() {
        return getInner().getValue();
    }

    @Override
    public SecretProperties getAttributes() {
        return getInner().getProperties();
    }

    @Override
    public Map<String, String> getTags() {
        return getInner().getProperties().getTags();
    }

    @Override
    public String getContentType() {
        return getInner().getProperties().getContentType();
    }

    @Override
    public String getKid() {
        return getInner().getProperties().getKeyId();
    }

    @Override
    public boolean isManaged() {
        return Utils.toPrimitiveBoolean(getInner().getProperties().isManaged());
    }

    @Override
    public PagedIterable<Secret> listVersions() {
        return new PagedIterable<>(this.listVersionsAsync());
    }

    @Override
    public PagedFlux<Secret> listVersionsAsync() {
        return vault.secretClient().listPropertiesOfSecretVersions(getName())
                .mapPage(p -> {
                    KeyVaultSecret secret = vault.secretClient().getSecret(p.getId(), p.getVersion()).block();  // TODO async for PagedFlux
                    return wrapModel(secret);
                });
    }

    @Override
    protected Mono<KeyVaultSecret> getInnerAsync() {
        return vault.secretClient().getSecret(getName(), null);
    }

    @Override
    public SecretImpl withTags(Map<String, String> tags) {
        this.getInner().getProperties().setTags(tags);
        return this;
    }

    @Override
    public boolean isInCreateMode() {
        return getId() == null;
    }

    @Override
    public Mono<Secret> createResourceAsync() {
        KeyVaultSecret newSecret = new KeyVaultSecret(this.getName(), valueToSet);
        newSecret.setProperties(this.getAttributes());
        return vault.secretClient().setSecret(newSecret)
                .flatMap(s -> {
                    this.setInner(s);
                    valueToSet = null;
                    return Mono.just(this);
                });
    }

    @Override
    public Mono<Secret> updateResourceAsync() {
        if (valueToSet == null) {
            // if no update on value, just update properties
            return vault.secretClient().updateSecretProperties(this.getAttributes())
                    .flatMap(p -> {
                        this.getInner().setProperties(p);
                        return Mono.just(this);
                    });
        } else {
            return this.createResourceAsync();
        }
    }

    @Override
    public SecretImpl withAttributes(SecretProperties attributes) {
        this.getInner().setProperties(attributes);
        return this;
    }

    @Override
    public SecretImpl withVersion(String version) {
        // TODO updateSecretRequest.withVersion(version);
        return this;
    }

    @Override
    public SecretImpl withValue(String value) {
        Objects.requireNonNull(value);
        valueToSet = value;
        return this;
    }

    @Override
    public SecretImpl withContentType(String contentType) {
        this.getInner().getProperties().setContentType(contentType);
        return this;
    }
}
