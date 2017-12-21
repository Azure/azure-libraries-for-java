/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault.implementation;

import com.microsoft.azure.ListOperationCallback;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.keyvault.KeyIdentifier;
import com.microsoft.azure.keyvault.SecretIdentifier;
import com.microsoft.azure.keyvault.models.Attributes;
import com.microsoft.azure.keyvault.models.BackupKeyResult;
import com.microsoft.azure.keyvault.models.KeyAttributes;
import com.microsoft.azure.keyvault.models.KeyBundle;
import com.microsoft.azure.keyvault.models.KeyItem;
import com.microsoft.azure.keyvault.models.KeyOperationResult;
import com.microsoft.azure.keyvault.models.KeyVerifyResult;
import com.microsoft.azure.keyvault.models.SecretBundle;
import com.microsoft.azure.keyvault.models.SecretItem;
import com.microsoft.azure.keyvault.requests.CreateKeyRequest;
import com.microsoft.azure.keyvault.requests.ImportKeyRequest;
import com.microsoft.azure.keyvault.requests.UpdateKeyRequest;
import com.microsoft.azure.keyvault.webkey.JsonWebKey;
import com.microsoft.azure.keyvault.webkey.JsonWebKeyEncryptionAlgorithm;
import com.microsoft.azure.keyvault.webkey.JsonWebKeyOperation;
import com.microsoft.azure.keyvault.webkey.JsonWebKeySignatureAlgorithm;
import com.microsoft.azure.keyvault.webkey.JsonWebKeyType;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.keyvault.Key;
import com.microsoft.azure.management.keyvault.Key.DefinitionStages.WithCreate;
import com.microsoft.azure.management.keyvault.Key.DefinitionStages.WithImport;
import com.microsoft.azure.management.keyvault.Secret;
import com.microsoft.azure.management.keyvault.Vault;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import com.microsoft.rest.ServiceFuture;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func0;
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
                Key.UpdateWithCreate,
                Key.UpdateWithImport {

    private final Vault vault;
    private CreateKeyRequest.Builder createKeyRequest;
    private UpdateKeyRequest.Builder updateKeyRequest;
    private ImportKeyRequest.Builder importKeyRequest;

    KeyImpl(String name, KeyBundle innerObject, Vault vault) {
        super(name, innerObject);
        this.vault = vault;
        this.updateKeyRequest = new UpdateKeyRequest.Builder(vault.vaultUri(), name);
    }

    @Override
    public String id() {
        if (inner().keyIdentifier() == null) {
            return null;
        }
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
    public PagedList<Key> listVersions() {
        return new PagedListConverter<KeyItem, Key>() {

            @Override
            public Observable<Key> typeConvertAsync(final KeyItem keyItem) {
                return new KeyVaultFutures.ServiceFutureConverter<KeyBundle, Key>() {

                    @Override
                    protected ServiceFuture<KeyBundle> callAsync() {
                        return vault.client().getKeyAsync(keyItem.identifier().identifier(), null);
                    }

                    @Override
                    protected Key wrapModel(KeyBundle keyBundle) {
                        return new KeyImpl(keyBundle.keyIdentifier().name(), keyBundle, vault);
                    }
                }.toObservable();
            }
        }.convert(vault.client().listKeyVersions(vault.vaultUri(), name()));
    }

    @Override
    public Observable<Key> listVersionsAsync() {
        return new KeyVaultFutures.ListCallbackObserver<KeyItem, KeyIdentifier>() {

            @Override
            protected void list(ListOperationCallback<KeyItem> callback) {
                vault.client().listKeyVersionsAsync(vault.vaultUri(), name(), callback);
            }

            @Override
            protected Observable<KeyIdentifier> typeConvertAsync(KeyItem o) {
                return Observable.just(o.identifier());
            }
        }.toObservable()
                .flatMap(new Func1<KeyIdentifier, Observable<Key>>() {
                    @Override
                    public Observable<Key> call(final KeyIdentifier keyIdentifier) {
                        return new KeyVaultFutures.ServiceFutureConverter<KeyBundle, Key>() {

                            @Override
                            protected ServiceFuture<KeyBundle> callAsync() {
                                return vault.client().getKeyAsync(keyIdentifier.identifier(), null);
                            }

                            @Override
                            protected Key wrapModel(KeyBundle keyBundle) {
                                return new KeyImpl(keyIdentifier.name(), keyBundle, vault);
                            }
                        }.toObservable();
                    }
                });
    }

    @Override
    public byte[] backup() {
        return vault.client().backupKey(vault.vaultUri(), name()).value();
    }

    @Override
    public Observable<byte[]> backupAsync() {
        return new KeyVaultFutures.ServiceFutureConverter<BackupKeyResult, byte[]>() {
            @Override
            protected ServiceFuture<BackupKeyResult> callAsync() {
                return vault.client().backupKeyAsync(vault.vaultUri(), name(), null);
            }

            @Override
            protected byte[] wrapModel(BackupKeyResult backupKeyResult) {
                return backupKeyResult.value();
            }
        }.toObservable();
    }

    @Override
    public byte[] encrypt(JsonWebKeyEncryptionAlgorithm algorithm, byte[] value) {
        return vault.client().encrypt(inner().keyIdentifier().identifier(), algorithm, value).result();
    }

    @Override
    public Observable<byte[]> encryptAsync(final JsonWebKeyEncryptionAlgorithm algorithm, final byte[] value) {
        return new KeyVaultFutures.ServiceFutureConverter<KeyOperationResult, byte[]>() {

            @Override
            protected ServiceFuture<KeyOperationResult> callAsync() {
                return vault.client().encryptAsync(inner().keyIdentifier().identifier(), algorithm, value, null);
            }

            @Override
            protected byte[] wrapModel(KeyOperationResult keyOperationResult) {
                return keyOperationResult.result();
            }
        }.toObservable();
    }

    @Override
    public byte[] decrypt(JsonWebKeyEncryptionAlgorithm algorithm, byte[] value) {
        return vault.client().decrypt(inner().keyIdentifier().identifier(), algorithm, value).result();
    }

    @Override
    public Observable<byte[]> decryptAsync(final JsonWebKeyEncryptionAlgorithm algorithm, final byte[] value) {
        return new KeyVaultFutures.ServiceFutureConverter<KeyOperationResult, byte[]>() {

            @Override
            protected ServiceFuture<KeyOperationResult> callAsync() {
                return vault.client().decryptAsync(inner().keyIdentifier().identifier(), algorithm, value, null);
            }

            @Override
            protected byte[] wrapModel(KeyOperationResult keyOperationResult) {
                return keyOperationResult.result();
            }
        }.toObservable();
    }

    @Override
    public byte[] sign(JsonWebKeySignatureAlgorithm algorithm, byte[] value) {
        return vault.client().sign(inner().keyIdentifier().identifier(), algorithm, value).result();
    }

    @Override
    public Observable<byte[]> signAsync(final JsonWebKeySignatureAlgorithm algorithm, final byte[] value) {
        return new KeyVaultFutures.ServiceFutureConverter<KeyOperationResult, byte[]>() {

            @Override
            protected ServiceFuture<KeyOperationResult> callAsync() {
                return vault.client().signAsync(inner().keyIdentifier().identifier(), algorithm, value, null);
            }

            @Override
            protected byte[] wrapModel(KeyOperationResult keyOperationResult) {
                return keyOperationResult.result();
            }
        }.toObservable();
    }

    @Override
    public boolean verify(JsonWebKeySignatureAlgorithm algorithm, byte[] digest, byte[] value) {
        return vault.client().verify(inner().keyIdentifier().identifier(), algorithm, digest, value).value();
    }

    @Override
    public Observable<Boolean> verifyAsync(final JsonWebKeySignatureAlgorithm algorithm, final byte[] digest, final byte[] value) {
        return new KeyVaultFutures.ServiceFutureConverter<KeyVerifyResult, Boolean>() {

            @Override
            protected ServiceFuture<KeyVerifyResult> callAsync() {
                return vault.client().verifyAsync(inner().keyIdentifier().identifier(), algorithm, digest, value, null);
            }

            @Override
            protected Boolean wrapModel(KeyVerifyResult keyOperationResult) {
                return keyOperationResult.value();
            }
        }.toObservable();
    }

    @Override
    public byte[] wrapKey(JsonWebKeyEncryptionAlgorithm algorithm, byte[] value) {
        return vault.client().wrapKey(inner().keyIdentifier().identifier(), algorithm, value).result();
    }

    @Override
    public Observable<byte[]> wrapKeyAsync(final JsonWebKeyEncryptionAlgorithm algorithm, final byte[] value) {
        return new KeyVaultFutures.ServiceFutureConverter<KeyOperationResult, byte[]>() {

            @Override
            protected ServiceFuture<KeyOperationResult> callAsync() {
                return vault.client().wrapKeyAsync(inner().keyIdentifier().identifier(), algorithm, value, null);
            }

            @Override
            protected byte[] wrapModel(KeyOperationResult keyOperationResult) {
                return keyOperationResult.result();
            }
        }.toObservable();
    }

    @Override
    public byte[] unwrapKey(JsonWebKeyEncryptionAlgorithm algorithm, byte[] value) {
        return vault.client().unwrapKey(inner().keyIdentifier().identifier(), algorithm, value).result();
    }

    @Override
    public Observable<byte[]> unwrapKeyAsync(final JsonWebKeyEncryptionAlgorithm algorithm, final byte[] value) {
        return new KeyVaultFutures.ServiceFutureConverter<KeyOperationResult, byte[]>() {

            @Override
            protected ServiceFuture<KeyOperationResult> callAsync() {
                return vault.client().unwrapKeyAsync(inner().keyIdentifier().identifier(), algorithm, value, null);
            }

            @Override
            protected byte[] wrapModel(KeyOperationResult keyOperationResult) {
                return keyOperationResult.result();
            }
        }.toObservable();
    }

    @Override
    protected Observable<KeyBundle> getInnerAsync() {
        return Observable.from(vault.client().getKeyAsync(id(), null));
    }

    @Override
    public KeyImpl withTags(Map<String, String> tags) {
        if (isInCreateMode()) {
            if (createKeyRequest != null) {
                createKeyRequest.withTags(tags);
            } else {
                importKeyRequest.withTags(tags);
            }
        } else {
            updateKeyRequest.withTags(tags);
        }
        return this;
    }

    @Override
    public boolean isInCreateMode() {
        return id() == null;
    }

    @Override
    public Observable<Key> createResourceAsync() {
        return Observable.defer(new Func0<Observable<Key>>() {
            @Override
            public Observable<Key> call() {
                if (createKeyRequest != null) {
                    return Observable.from(vault.client().createKeyAsync(createKeyRequest.build(), null))
                            .map(innerToFluentMap(KeyImpl.this))
                            .doOnCompleted(new Action0() {
                                @Override
                                public void call() {
                                    createKeyRequest = null;
                                    updateKeyRequest = new UpdateKeyRequest.Builder(vault.vaultUri(), name());
                                }
                            });
                } else {
                    return Observable.from(vault.client().importKeyAsync(importKeyRequest.build(), null))
                            .map(innerToFluentMap(KeyImpl.this))
                            .doOnCompleted(new Action0() {
                                @Override
                                public void call() {
                                    importKeyRequest = null;
                                    updateKeyRequest = new UpdateKeyRequest.Builder(vault.vaultUri(), name());
                                }
                            });
                }
            }
        });
    }

    @Override
    public Observable<Key> updateResourceAsync() {
        Observable<Key> set = Observable.just((Key) this);
        if (createKeyRequest != null || importKeyRequest != null) {
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
                importKeyRequest = null;
                updateKeyRequest = new UpdateKeyRequest.Builder(vault.vaultUri(), name());
            }
        });
    }

    @Override
    public KeyImpl withAttributes(Attributes attributes) {
        if (isInCreateMode()) {
            if (createKeyRequest != null) {
                createKeyRequest.withAttributes(attributes);
            } else {
                importKeyRequest.withAttributes(attributes);
            }
        } else {
            updateKeyRequest.withAttributes(attributes);
        }
        return this;
    }

    @Override
    public KeyImpl withKeyType(JsonWebKeyType keyType) {
        createKeyRequest = new CreateKeyRequest.Builder(vault.vaultUri(), name(), keyType);
        return this;
    }

    @Override
    public KeyImpl withKey(JsonWebKey key) {
        importKeyRequest = new ImportKeyRequest.Builder(vault.vaultUri(), name(), key);
        return this;
    }

    @Override
    public KeyImpl withKeyOperations(List<JsonWebKeyOperation> keyOperations) {
        if (isInCreateMode()) {
            createKeyRequest.withKeyOperations(keyOperations);
        } else {
            updateKeyRequest.withKeyOperations(keyOperations);
        }
        return this;
    }

    @Override
    public KeyImpl withHsm(boolean isHsm) {
        importKeyRequest.withHsm(isHsm);
        return this;
    }

    @Override
    public KeyImpl withKeySize(int size) {
        createKeyRequest.withKeySize(size);
        return this;
    }
}
