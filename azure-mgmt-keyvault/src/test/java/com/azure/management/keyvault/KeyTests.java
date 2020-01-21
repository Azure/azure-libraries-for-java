/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.keyvault;

import com.azure.core.http.rest.PagedIterable;
import com.azure.management.ApplicationTokenCredential;
import com.azure.management.resources.core.TestUtilities;
import com.azure.management.resources.fluentcore.arm.Region;
import com.azure.management.resources.fluentcore.utils.SdkContext;
import com.azure.security.keyvault.keys.cryptography.models.EncryptionAlgorithm;
import com.azure.security.keyvault.keys.cryptography.models.KeyWrapAlgorithm;
import com.azure.security.keyvault.keys.cryptography.models.SignatureAlgorithm;
import com.azure.security.keyvault.keys.models.JsonWebKey;
import com.azure.security.keyvault.keys.models.KeyOperation;
import com.azure.security.keyvault.keys.models.KeyType;
import org.junit.Assert;
import org.junit.Ignore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.Signature;

public class KeyTests extends KeyVaultManagementTest {
    @Ignore("Mock framework doesn't support data plane")
    public void canCRUDKey() throws Exception {
        Vault vault = createVault();
        String keyName = SdkContext.randomResourceName("key", 20);

        // Create
        Key key = vault.keys().define(keyName)
                .withKeyTypeToCreate(KeyType.RSA)
                .withKeyOperations(KeyOperation.SIGN, KeyOperation.VERIFY)
                .create();

        Assert.assertNotNull(key);
        Assert.assertNotNull(key.getId());
        Assert.assertEquals(2, key.getJsonWebKey().getKeyOps().size());

        // Get
        Key key1 = vault.keys().getById(key.getId());
        Assert.assertNotNull(key1);
        Assert.assertEquals(key.getId(), key1.getId());

        // Update
        key = key.update()
                .withKeyOperations(KeyOperation.ENCRYPT)
                .apply();

        Assert.assertEquals(1, key.getJsonWebKey().getKeyOps().size());

        // New version
        key = key.update()
                .withKeyTypeToCreate(KeyType.RSA)
                .withKeyOperations(KeyOperation.ENCRYPT, KeyOperation.DECRYPT, KeyOperation.SIGN)
                .apply();

        Assert.assertEquals(3, key.getJsonWebKey().getKeyOps().size());

        // List versions
        PagedIterable<Key> keys = key.listVersions();
        Assert.assertEquals(2, TestUtilities.getPagedIterableSize(keys));
    }

    @Ignore("Mock framework doesn't support data plane")
    public void canImportKey() throws Exception {
        Vault vault = createVault();
        String keyName = SdkContext.randomResourceName("key", 20);

        Key key = vault.keys().define(keyName)
                .withLocalKeyToImport(JsonWebKey.fromRsa(KeyPairGenerator.getInstance("RSA").generateKeyPair()))
                .create();

        Assert.assertNotNull(key);
        Assert.assertNotNull(key.getId());
    }

    @Ignore("Mock framework doesn't support data plane")
    public void canBackupAndRestore() throws Exception {
        Vault vault = createVault();
        String keyName = SdkContext.randomResourceName("key", 20);

        Key key = vault.keys().define(keyName)
                .withLocalKeyToImport(JsonWebKey.fromRsa(KeyPairGenerator.getInstance("RSA").generateKeyPair()))
                .create();

        Assert.assertNotNull(key);

        byte[] backup = key.backup();

        vault.keys().deleteById(key.getId());
        Assert.assertEquals(0, TestUtilities.getPagedIterableSize(vault.keys().list()));

        vault.keys().restore(backup);
        PagedIterable<Key> keys = vault.keys().list();
        Assert.assertEquals(1, TestUtilities.getPagedIterableSize(keys));

        Assert.assertEquals(key.getJsonWebKey(), keys.iterator().next().getJsonWebKey());
    }

    @Ignore("Mock framework doesn't support data plane")
    public void canEncryptAndDecrypt() throws Exception {
        Vault vault = createVault();
        String keyName = SdkContext.randomResourceName("key", 20);

        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();

        Key key = vault.keys().define(keyName)
                .withLocalKeyToImport(JsonWebKey.fromRsa(keyPair))
                .create();

        Assert.assertNotNull(key);

        String s = "the quick brown fox jumps over the lazy dog";
        byte[] data = s.getBytes();

        // Remote encryption
        byte[] encrypted = key.encrypt(EncryptionAlgorithm.RSA1_5, data);
        Assert.assertNotNull(encrypted);

        byte[] decrypted = key.decrypt(EncryptionAlgorithm.RSA1_5, encrypted);
        Assert.assertEquals(s, new String(decrypted));

        // Local encryption
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        encrypted = cipher.doFinal(data);

        decrypted = key.decrypt(EncryptionAlgorithm.RSA_OAEP, encrypted);
        Assert.assertEquals(s, new String(decrypted));
    }

    @Ignore("Mock framework doesn't support data plane")
    public void canSignAndVerify() throws Exception {
        Vault vault = createVault();
        String keyName = SdkContext.randomResourceName("key", 20);

        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();

        Key key = vault.keys().define(keyName)
                .withLocalKeyToImport(JsonWebKey.fromRsa(keyPair))
                .create();

        Assert.assertNotNull(key);

        String s = "the quick brown fox jumps over the lazy dog";
        byte[] data = s.getBytes();
        byte[] digest = MessageDigest.getInstance("SHA-256").digest(data);
        byte[] signature = key.sign(SignatureAlgorithm.RS256, digest);
        Assert.assertNotNull(signature);

        // Local verification
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initVerify(keyPair.getPublic());
        sign.update(data);
        Assert.assertTrue(sign.verify(signature));

        // Remote verification
        Assert.assertTrue(key.verify(SignatureAlgorithm.RS256, digest, signature));
    }

    @Ignore("Mock framework doesn't support data plane")
    public void canWrapAndUnwrap() throws Exception {
        Vault vault = createVault();
        String keyName = SdkContext.randomResourceName("key", 20);

        Key key = vault.keys().define(keyName)
                .withLocalKeyToImport(JsonWebKey.fromRsa(KeyPairGenerator.getInstance("RSA").generateKeyPair()))
                .create();

        SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();

        byte[] wrapped = key.wrapKey(KeyWrapAlgorithm.RSA1_5, secretKey.getEncoded());
        Assert.assertNotNull(wrapped);

        byte[] unwrapped = key.unwrapKey(KeyWrapAlgorithm.RSA1_5, wrapped);
        Assert.assertNotNull(unwrapped);
        Assert.assertEquals(secretKey, new SecretKeySpec(unwrapped, "AES"));
    }

    private Vault createVault() throws Exception {
        String vaultName = SdkContext.randomResourceName("vault", 20);

        ApplicationTokenCredential credentials = ApplicationTokenCredential.fromFile(new File(System.getenv("AZURE_AUTH_LOCATION")));

        Vault vault = keyVaultManager.vaults().define(vaultName)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(RG_NAME)
                .defineAccessPolicy()
                    .forServicePrincipal(credentials.getClientId())
                    .allowKeyAllPermissions()
                    .attach()
                .create();

        Assert.assertNotNull(vault);

        SdkContext.sleep(10000);

        return vault;
    }
}
