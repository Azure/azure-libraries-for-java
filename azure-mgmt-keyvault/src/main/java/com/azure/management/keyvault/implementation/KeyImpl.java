/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.keyvault.implementation;

import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedIterable;
import com.azure.management.keyvault.Key;
import com.azure.management.keyvault.Vault;
import com.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.azure.management.resources.fluentcore.utils.Utils;
import com.azure.security.keyvault.keys.cryptography.models.DecryptResult;
import com.azure.security.keyvault.keys.cryptography.models.EncryptResult;
import com.azure.security.keyvault.keys.cryptography.models.EncryptionAlgorithm;
import com.azure.security.keyvault.keys.cryptography.models.KeyWrapAlgorithm;
import com.azure.security.keyvault.keys.cryptography.models.SignResult;
import com.azure.security.keyvault.keys.cryptography.models.SignatureAlgorithm;
import com.azure.security.keyvault.keys.cryptography.models.UnwrapResult;
import com.azure.security.keyvault.keys.cryptography.models.VerifyResult;
import com.azure.security.keyvault.keys.cryptography.models.WrapResult;
import com.azure.security.keyvault.keys.models.CreateEcKeyOptions;
import com.azure.security.keyvault.keys.models.CreateKeyOptions;
import com.azure.security.keyvault.keys.models.CreateRsaKeyOptions;
import com.azure.security.keyvault.keys.models.ImportKeyOptions;
import com.azure.security.keyvault.keys.models.JsonWebKey;
import com.azure.security.keyvault.keys.models.KeyOperation;
import com.azure.security.keyvault.keys.models.KeyProperties;
import com.azure.security.keyvault.keys.models.KeyType;
import com.azure.security.keyvault.keys.models.KeyVaultKey;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Implementation for Vault and its parent interfaces.
 */
class KeyImpl
        extends CreatableUpdatableImpl<
                Key,
                KeyVaultKey,
                KeyImpl>
        implements
                Key,
                Key.Definition,
                Key.UpdateWithCreate,
                Key.UpdateWithImport {

    private final Vault vault;

    private CreateKeyOptions createKeyRequest;
    private UpdateKeyOptions updateKeyRequest;
    private ImportKeyOptions importKeyRequest;

    private static class UpdateKeyOptions {
        private KeyProperties keyProperties = new KeyProperties();
        private List<KeyOperation> keyOperations = new ArrayList<>();
    }

    KeyImpl(String name, KeyVaultKey innerObject, Vault vault) {
        super(name, innerObject);
        this.vault = vault;
        this.updateKeyRequest = new UpdateKeyOptions();
    }

    private KeyImpl wrapModel(KeyVaultKey key) {
        return new KeyImpl(key.getName(), key, vault);
    }

    @Override
    public String getId() {
        return this.getInner() == null ? null : this.getInner().getId();
    }

    @Override
    public JsonWebKey getJsonWebKey() {
        return getInner().getKey();
    }

    @Override
    public KeyProperties getAttributes() {
        return getInner().getProperties();
    }

    @Override
    public Map<String, String> getTags() {
        return getInner().getProperties().getTags();
    }

    @Override
    public boolean isManaged() {
        return Utils.toPrimitiveBoolean(getInner().getProperties().isManaged());
    }

    @Override
    public PagedIterable<Key> listVersions() {
        return new PagedIterable<>(this.listVersionsAsync());
    }

    @Override
    public PagedFlux<Key> listVersionsAsync() {
        return vault.keyClient().listPropertiesOfKeyVersions(this.getName())
                .mapPage(p -> {
                    KeyVaultKey key = vault.keyClient().getKey(p.getId(), p.getVersion()).block();  // TODO async for PagedFlux
                    return wrapModel(key);
                });
    }

    @Override
    public byte[] backup() {
        return backupAsync().block();
    }

    @Override
    public Mono<byte[]> backupAsync() {
        return vault.keyClient().backupKey(this.getName());
    }

    @Override
    public byte[] encrypt(EncryptionAlgorithm algorithm, byte[] content) {
        return encryptAsync(algorithm, content).block();
    }

    @Override
    public Mono<byte[]> encryptAsync(final EncryptionAlgorithm algorithm, final byte[] content) {
        return vault.cryptographyClient().encrypt(algorithm, content).map(EncryptResult::getCipherText);
    }

    @Override
    public byte[] decrypt(EncryptionAlgorithm algorithm, byte[] content) {
        return decryptAsync(algorithm, content).block();
    }

    @Override
    public Mono<byte[]> decryptAsync(final EncryptionAlgorithm algorithm, final byte[] content) {
        return vault.cryptographyClient().decrypt(algorithm, content).map(DecryptResult::getPlainText);
    }

    @Override
    public byte[] sign(SignatureAlgorithm algorithm, byte[] digest) {
        return signAsync(algorithm, digest).block();
    }

    @Override
    public Mono<byte[]> signAsync(final SignatureAlgorithm algorithm, final byte[] digest) {
        return vault.cryptographyClient().sign(algorithm, digest).map(SignResult::getSignature);
    }

    @Override
    public boolean verify(SignatureAlgorithm algorithm, byte[] digest, byte[] signature) {
        return Utils.toPrimitiveBoolean(verifyAsync(algorithm, digest, signature).block());
    }

    @Override
    public Mono<Boolean> verifyAsync(final SignatureAlgorithm algorithm, final byte[] digest, final byte[] signature) {
        return vault.cryptographyClient().verify(algorithm, digest, signature).map(VerifyResult::isValid);
    }

    @Override
    public byte[] wrapKey(KeyWrapAlgorithm algorithm, byte[] key) {
        return wrapKeyAsync(algorithm, key).block();
    }

    @Override
    public Mono<byte[]> wrapKeyAsync(final KeyWrapAlgorithm algorithm, final byte[] key) {
        return vault.cryptographyClient().wrapKey(algorithm, key).map(WrapResult::getEncryptedKey);
    }

    @Override
    public byte[] unwrapKey(KeyWrapAlgorithm algorithm, byte[] key) {
        return unwrapKeyAsync(algorithm, key).block();
    }

    @Override
    public Mono<byte[]> unwrapKeyAsync(final KeyWrapAlgorithm algorithm, final byte[] key) {
        return vault.cryptographyClient().unwrapKey(algorithm, key).map(UnwrapResult::getKey);
    }

    @Override
    protected Mono<KeyVaultKey> getInnerAsync() {
        return vault.keyClient().getKey(this.getName());
    }

    @Override
    public KeyImpl withTags(Map<String, String> tags) {
        if (isInCreateMode()) {
            if (createKeyRequest != null) {
                createKeyRequest.setTags(tags);
            } else if (importKeyRequest != null) {
                importKeyRequest.setTags(tags);
            }
        } else {
            updateKeyRequest.keyProperties.setTags(tags);
        }
        return this;
    }

    @Override
    public boolean isInCreateMode() {
        return getId() == null;
    }

    @Override
    public Mono<Key> createResourceAsync() {
        Mono<KeyVaultKey> mono = null;
        if (createKeyRequest != null) {
            if (createKeyRequest instanceof CreateEcKeyOptions) {
                mono = vault.keyClient().createEcKey((CreateEcKeyOptions) createKeyRequest);
            } else if (createKeyRequest instanceof CreateRsaKeyOptions) {
                mono = vault.keyClient().createRsaKey((CreateRsaKeyOptions) createKeyRequest);
            } else {
                mono = vault.keyClient().createKey(createKeyRequest);
            }
        } else {
            mono = vault.keyClient().importKey(importKeyRequest);
        }
        return mono.map(inner -> {
            this.setInner(inner);
            return (Key) this;
        }).doOnSuccess(ignore -> {
            createKeyRequest = null;
            importKeyRequest = null;
            this.updateKeyRequest = new UpdateKeyOptions();
        });
    }

    @Override
    public Mono<Key> updateResourceAsync() {
        return vault.keyClient().updateKeyProperties(updateKeyRequest.keyProperties, updateKeyRequest.keyOperations.toArray(new KeyOperation[0]))
                .map(inner -> {
                    this.setInner(inner);
                    return (Key) this;
                }).doOnSuccess(ignore -> {
                    createKeyRequest = null;
                    importKeyRequest = null;
                    this.updateKeyRequest = new UpdateKeyOptions();
                });
    }

    @Override
    public KeyImpl withAttributes(KeyProperties attributes) {
        if (isInCreateMode()) {
            if (createKeyRequest != null) {
                createKeyRequest.setEnabled(attributes.isEnabled());
                createKeyRequest.setExpiresOn(attributes.getExpiresOn());
                createKeyRequest.setNotBefore(attributes.getNotBefore());
            } else if (importKeyRequest != null) {
                importKeyRequest.setEnabled(attributes.isEnabled());
                importKeyRequest.setExpiresOn(attributes.getExpiresOn());
                importKeyRequest.setNotBefore(attributes.getNotBefore());
            }
        } else {
            updateKeyRequest.keyProperties = attributes;
        }
        return this;
    }

    @Override
    public KeyImpl withKeyTypeToCreate(KeyType keyType) {
        if (keyType == KeyType.EC || keyType == KeyType.EC_HSM) {
            CreateEcKeyOptions request = new CreateEcKeyOptions(getName());
            request.setHardwareProtected(keyType == KeyType.EC_HSM);

            createKeyRequest = request;
        } else if (keyType == KeyType.RSA || keyType == KeyType.RSA_HSM) {
            CreateRsaKeyOptions request = new CreateRsaKeyOptions(getName());
            request.setHardwareProtected(keyType == KeyType.RSA_HSM);

            createKeyRequest = request;
        } else {
            createKeyRequest = new CreateKeyOptions(getName(), keyType);
        }
        return this;
    }

    @Override
    public KeyImpl withLocalKeyToImport(JsonWebKey key) {
        importKeyRequest = new ImportKeyOptions(getName(), key);
        return this;
    }

    @Override
    public KeyImpl withKeyOperations(List<KeyOperation> keyOperations) {
        if (isInCreateMode()) {
            createKeyRequest.setKeyOperations(keyOperations.toArray(new KeyOperation[0]));
        } else {
            updateKeyRequest.keyOperations = keyOperations;
        }
        return this;
    }

    @Override
    public KeyImpl withKeyOperations(KeyOperation... keyOperations) {
        return withKeyOperations(Arrays.asList(keyOperations));
    }

    @Override
    public KeyImpl withHsm(boolean isHsm) {
        importKeyRequest.setHardwareProtected(isHsm);
        return this;
    }

    @Override
    public KeyImpl withKeySize(int size) {
        if (createKeyRequest instanceof CreateEcKeyOptions) {
            // TODO
            //((CreateEcKeyOptions) createKeyRequest).setKeySize(size);
        } else if (createKeyRequest instanceof CreateRsaKeyOptions) {
            ((CreateRsaKeyOptions) createKeyRequest).setKeySize(size);
        }
        return this;
    }
}
