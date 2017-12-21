/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.keyvault.models.Attributes;
import com.microsoft.azure.keyvault.models.KeyAttributes;
import com.microsoft.azure.keyvault.models.KeyBundle;
import com.microsoft.azure.keyvault.webkey.JsonWebKey;
import com.microsoft.azure.keyvault.webkey.JsonWebKeyEncryptionAlgorithm;
import com.microsoft.azure.keyvault.webkey.JsonWebKeyOperation;
import com.microsoft.azure.keyvault.webkey.JsonWebKeySignatureAlgorithm;
import com.microsoft.azure.keyvault.webkey.JsonWebKeyType;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.keyvault.Key.DefinitionStages.WithKey;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import rx.Observable;

import java.util.List;
import java.util.Map;

/**
 * An immutable client-side representation of an Azure Key Vault key.
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.Fluent.KeyVault")
public interface Key extends
        Indexable,
        HasInner<KeyBundle>,
        HasId,
        HasName,
        Updatable<Key.Update> {
    /**
     * @return the Json web key.
     */
    JsonWebKey jsonWebKey();

    /**
     * @return the key management attributes.
     */
    KeyAttributes attributes();

    /**
     * @return application specific metadata in the form of key-value pairs.
     */
    Map<String, String> tags();

    /**
     * @return true if the key's lifetime is managed by key vault. If this is a key
     * backing a certificate, then managed will be true.
     */
    boolean managed();

    /**
     * @return a list of individual key versions with the same key name
     */
    @Method
    PagedList<Key> listVersions();

    /**
     * @return a list of individual key versions with the same key name
     */
    @Method
    Observable<Key> listVersionsAsync();

    /**
     * @return a backup of the specified key be downloaded to the client
     */
    @Method
    byte[] backup();

    /**
     * @return a backup of the specified key be downloaded to the client
     */
    @Method
    Observable<byte[]> backupAsync();

    /**
     * Encrypts an arbitrary sequence of bytes using an encryption key that is stored in a key vault.
     *
     * @param algorithm the JWK encryption algorithm
     * @param content the content to be encrypted
     * @return the encrypted value
     */
    byte[] encrypt(JsonWebKeyEncryptionAlgorithm algorithm, byte[] content);

    /**
     * Encrypts an arbitrary sequence of bytes using an encryption key that is stored in a key vault.
     *
     * @param algorithm the JWK encryption algorithm
     * @param content the content to be encrypted
     * @return the encrypted value
     */
    Observable<byte[]> encryptAsync(JsonWebKeyEncryptionAlgorithm algorithm, byte[] content);

    /**
     * Decrypts a single block of encrypted data.
     *
     * @param algorithm the JWK encryption algorithm
     * @param content the content to be decrypted
     * @return the decrypted value
     */
    byte[] decrypt(JsonWebKeyEncryptionAlgorithm algorithm, byte[] content);

    /**
     * Decrypts a single block of encrypted data.
     *
     * @param algorithm the JWK encryption algorithm
     * @param content the content to be decrypted
     * @return the decrypted value
     */
    Observable<byte[]> decryptAsync(JsonWebKeyEncryptionAlgorithm algorithm, byte[] content);

    /**
     * Creates a signature from a digest.
     *
     * @param algorithm the JWK signing algorithm
     * @param digest the content to be signed
     * @return the signature in a byte array
     */
    byte[] sign(JsonWebKeySignatureAlgorithm algorithm, byte[] digest);

    /**
     * Creates a signature from a digest.
     *
     * @param algorithm the JWK signing algorithm
     * @param digest the content to be signed
     * @return the signature in a byte array
     */
    Observable<byte[]> signAsync(JsonWebKeySignatureAlgorithm algorithm, byte[] digest);

    /**
     * Verifies a signature from a digest.
     *
     * @param algorithm the JWK signing algorithm
     * @param digest the content to be signed
     * @param signature the signature to verify
     * @return true if the signature is valid
     */
    boolean verify(JsonWebKeySignatureAlgorithm algorithm, byte[] digest, byte[] signature);

    /**
     * Verifies a signature from a digest.
     *
     * @param algorithm the JWK signing algorithm
     * @param digest the content to be signed
     * @param signature the signature to verify
     * @return true if the signature is valid
     */
    Observable<Boolean> verifyAsync(JsonWebKeySignatureAlgorithm algorithm, byte[] digest, byte[] signature);

    /**
     * Wraps a symmetric key using the specified algorithm.
     *
     * @param algorithm the JWK encryption algorithm
     * @param key the symmetric key to wrap
     * @return the wrapped key
     */
    byte[] wrapKey(JsonWebKeyEncryptionAlgorithm algorithm, byte[] key);

    /**
     * Wraps a symmetric key using the specified algorithm.
     *
     * @param algorithm the JWK encryption algorithm
     * @param key the symmetric key to wrap
     * @return the wrapped key
     */
    Observable<byte[]> wrapKeyAsync(JsonWebKeyEncryptionAlgorithm algorithm, byte[] key);

    /**
     * Unwraps a symmetric key wrapped originally by this Key Vault key.
     *
     * @param algorithm the JWK encryption algorithm
     * @param key the key to unwrap
     * @return the unwrapped symmetric key
     */
    byte[] unwrapKey(JsonWebKeyEncryptionAlgorithm algorithm, byte[] key);

    /**
     * Unwraps a symmetric key wrapped originally by this Key Vault key.
     *
     * @param algorithm the JWK encryption algorithm
     * @param key the key to unwrap
     * @return the unwrapped symmetric key
     */
    Observable<byte[]> unwrapKeyAsync(JsonWebKeyEncryptionAlgorithm algorithm, byte[] key);

    /**
     * Container interface for all the definitions.
     */
    interface Definition extends
            DefinitionStages.Blank,
            WithKey,
            DefinitionStages.WithImport,
            DefinitionStages.WithCreate {
    }

    /**
     * Grouping of key definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of a key definition.
         */
        interface Blank extends WithKey {
        }

        /**
         * The stage of a key definition allowing to specify whether
         * to create a key or to import a key.
         */
        interface WithKey {
            /**
             * Specifies a key type to create a new key.
             * @param keyType the JWK type to create
             * @return the next stage of the definition
             */
            WithCreate withKeyType(JsonWebKeyType keyType);

            /**
             * Specifies an existing key to import.
             * @param key the existing JWK to import
             * @return the next stage of the definition
             */
            WithImport withKey(JsonWebKey key);
        }

        /**
         * The stage of a key definition allowing to specify the key size.
         */
        interface WithKeySize {
            /**
             * Specifies the size of the key to create.
             * @param size the size of the key in integer
             * @return the next stage of the definition
             */
            WithCreate withKeySize(int size);
        }

        /**
         * The stage of a key definition allowing to specify the allowed operations for the key.
         */
        interface WithKeyOperations {
            /**
             * Specifies the list of allowed key operations. By default all operations are allowed.
             * @param keyOperations the list of JWK operations
             * @return the next stage of the definition
             */
            WithCreate withKeyOperations(List<JsonWebKeyOperation> keyOperations);
        }

        /**
         * The stage of a key definition allowing to specify whether to store the key in
         * hardware security modules.
         */
        interface WithHsm {
            /**
             * Specifies whether to store the key in hardware security modules.
             * @param isHsm store in Hsm if true
             * @return the next stage of the definition
             */
            WithImport withHsm(boolean isHsm);
        }

        /**
         * The stage of a key definition allowing to specify the attributes of the key.
         */
        interface WithAttributes {
            /**
             * Specifies the attributes of the key.
             * @param attributes the object attributes managed by Key Vault service
             * @return the next stage of the definition
             */
            WithCreate withAttributes(Attributes attributes);
        }

        /**
         * The stage of a key definition allowing to specify the tags of the key.
         */
        interface WithTags {
            /**
             * Specifies the tags on the key.
             * @param tags the key value pair of the tags
             * @return the next stage of the definition
             */
            WithCreate withTags(Map<String, String> tags);
        }

        /**
         * The base stage of the key definition allowing for any other optional settings to be specified.
         */
        interface WithCreateBase extends
                Creatable<Key>,
                WithAttributes,
                WithTags {
        }

        /**
         * The stage of the key definition which contains all the minimum required inputs for
         * the key to be created but also allows for any other optional settings to be specified.
         */
        interface WithCreate extends
                WithKeyOperations,
                WithKeySize,
                WithCreateBase {
        }

        /**
         * The stage of the key definition which contains all the minimum required inputs for
         * the key to be imported but also allows for any other optional settings to be specified.
         */
        interface WithImport extends
                WithHsm,
                WithCreateBase {
        }
    }

    /**
     * Grouping of key update stages.
     */
    interface UpdateStages {
        /**
         * The stage of a key update allowing to create a new version of the key.
         */
        interface WithKey {
            /**
             * Specifies a key type to create a new key version.
             * @param keyType the JWK type to create
             * @return the next stage of the update
             */
            UpdateWithCreate withKeyType(JsonWebKeyType keyType);

            /**
             * Specifies an existing key to import as a new version.
             * @param key the existing JWK to import
             * @return the next stage of the update
             */
            UpdateWithImport withKey(JsonWebKey key);
        }

        /**
         * The stage of a key update allowing to specify the key size.
         */
        interface WithKeySize {
            /**
             * Specifies the size of the key to create.
             * @param size the size of the key in integer
             * @return the next stage of the update
             */
            UpdateWithCreate withKeySize(int size);
        }

        /**
         * The stage of a key update allowing to specify whether to store the key in
         * hardware security modules.
         */
        interface WithHsm {
            /**
             * Specifies whether to store the key in hardware security modules.
             * @param isHsm store in Hsm if true
             * @return the next stage of the update
             */
            UpdateWithImport withHsm(boolean isHsm);
        }

        /**
         * The stage of a key update allowing to specify the allowed operations for the key.
         */
        interface WithKeyOperations {
            /**
             * Specifies the list of allowed key operations. By default all operations are allowed.
             * @param keyOperations the list of JWK operations
             * @return the next stage of the update
             */
            Update withKeyOperations(List<JsonWebKeyOperation> keyOperations);
        }

        /**
         * The stage of a key update allowing to specify the attributes of the key.
         */
        interface WithAttributes {
            /**
             * Specifies the attributes of the key.
             * @param attributes the object attributes managed by Key Vault service
             * @return the next stage of the update
             */
            Update withAttributes(Attributes attributes);
        }

        /**
         * The stage of a key update allowing to specify the tags of the key.
         */
        interface WithTags {
            /**
             * Specifies the tags on the key.
             * @param tags the key value pair of the tags
             * @return the next stage of the update
             */
            Update withTags(Map<String, String> tags);
        }
    }

    /**
     * The template for a key update operation, containing all the settings that can be modified.
     */
    interface Update extends
            Appliable<Key>,
            UpdateStages.WithKey,
            UpdateStages.WithKeyOperations,
            UpdateStages.WithAttributes,
            UpdateStages.WithTags {
    }

    /**
     * The template for a key vault update operation, with a new key version to be created.
     */
    interface UpdateWithCreate extends
            Update,
            UpdateStages.WithKeySize {
    }

    /**
     * The template for a key vault update operation, with a new key version to be imported.
     */
    interface UpdateWithImport extends
            Update,
            UpdateStages.WithHsm {
    }
}

