/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault;

import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.keyvault.models.Attributes;
import com.microsoft.azure.keyvault.webkey.JsonWebKey;
import com.microsoft.azure.keyvault.webkey.JsonWebKeyEncryptionAlgorithm;
import com.microsoft.azure.keyvault.webkey.JsonWebKeyOperation;
import com.microsoft.azure.keyvault.webkey.JsonWebKeySignatureAlgorithm;
import com.microsoft.azure.keyvault.webkey.JsonWebKeyType;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.Signature;
import java.util.Arrays;
import java.util.List;

public class KeyTests extends KeyVaultManagementTest {
    @Ignore("Mock framework doesn't support data plane")
    public void canCRUDKey() throws Exception {
        Vault vault = createVault();
        String keyName = SdkContext.randomResourceName("key", 20);

        // Create
        Key key = vault.keys().define(keyName)
                .withKeyType(JsonWebKeyType.RSA)
                .withKeyOperations(Arrays.asList(JsonWebKeyOperation.SIGN, JsonWebKeyOperation.VERIFY))
                .create();

        Assert.assertNotNull(key);
        Assert.assertNotNull(key.id());
        Assert.assertEquals(2, key.jsonWebKey().keyOps().size());

        // Get
        Key key1 = vault.keys().getById(key.id());
        Assert.assertNotNull(key1);
        Assert.assertEquals(key.id(), key1.id());

        // Update
        key = key.update()
                .withKeyOperations(Arrays.asList(JsonWebKeyOperation.ENCRYPT))
                .apply();

        Assert.assertEquals(1, key.jsonWebKey().keyOps().size());

        // New version
        key = key.update()
                .withKeyType(JsonWebKeyType.RSA)
                .withKeyOperations(Arrays.asList(JsonWebKeyOperation.ENCRYPT, JsonWebKeyOperation.DECRYPT, JsonWebKeyOperation.SIGN))
                .apply();

        Assert.assertEquals(3, key.jsonWebKey().keyOps().size());

        // List versions
        List<Key> keys = key.listVersions();
        Assert.assertEquals(2, keys.size());
    }

    @Ignore("Mock framework doesn't support data plane")
    public void canImportKey() throws Exception {
        Vault vault = createVault();
        String keyName = SdkContext.randomResourceName("key", 20);

        Key key = vault.keys().define(keyName)
                .withKey(JsonWebKey.fromRSA(KeyPairGenerator.getInstance("RSA").generateKeyPair()))
                .create();

        Assert.assertNotNull(key);
        Assert.assertNotNull(key.id());
    }

    @Ignore("Mock framework doesn't support data plane")
    public void canBackupAndRestore() throws Exception {
        Vault vault = createVault();
        String keyName = SdkContext.randomResourceName("key", 20);

        Key key = vault.keys().define(keyName)
                .withKey(JsonWebKey.fromRSA(KeyPairGenerator.getInstance("RSA").generateKeyPair()))
                .create();

        Assert.assertNotNull(key);

        byte[] backup = key.backup();

        vault.keys().deleteById(key.id());
        Assert.assertEquals(0, vault.keys().list().size());

        vault.keys().restore(backup);
        List<Key> keys = vault.keys().list();
        Assert.assertEquals(1, keys.size());

        Assert.assertEquals(key.jsonWebKey(), keys.get(0).jsonWebKey());
    }

    @Ignore("Mock framework doesn't support data plane")
    public void canEncryptAndDecrypt() throws Exception {
        Vault vault = createVault();
        String keyName = SdkContext.randomResourceName("key", 20);

        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();

        Key key = vault.keys().define(keyName)
                .withKey(JsonWebKey.fromRSA(keyPair))
                .create();

        Assert.assertNotNull(key);

        String s = "the quick brown fox jumps over the lazy dog";
        byte[] data = s.getBytes();

        // Remote encryption
        byte[] encrypted = key.encrypt(JsonWebKeyEncryptionAlgorithm.RSA1_5, data);
        Assert.assertNotNull(encrypted);

        byte[] decrypted = key.decrypt(JsonWebKeyEncryptionAlgorithm.RSA1_5, encrypted);
        Assert.assertEquals(s, new String(decrypted));

        // Local encryption
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        encrypted = cipher.doFinal(data);

        decrypted = key.decrypt(JsonWebKeyEncryptionAlgorithm.RSA_OAEP, encrypted);
        Assert.assertEquals(s, new String(decrypted));
    }

    @Ignore("Mock framework doesn't support data plane")
    public void canSignAndVerify() throws Exception {
        Vault vault = createVault();
        String keyName = SdkContext.randomResourceName("key", 20);

        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();

        Key key = vault.keys().define(keyName)
                .withKey(JsonWebKey.fromRSA(keyPair))
                .create();

        Assert.assertNotNull(key);

        String s = "the quick brown fox jumps over the lazy dog";
        byte[] data = s.getBytes();
        byte[] digest = MessageDigest.getInstance("SHA-256").digest(data);
        byte[] signature = key.sign(JsonWebKeySignatureAlgorithm.RS256, digest);
        Assert.assertNotNull(signature);

        // Local verification
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initVerify(keyPair.getPublic());
        sign.update(data);
        Assert.assertTrue(sign.verify(signature));

        // Remote verification
        Assert.assertTrue(key.verify(JsonWebKeySignatureAlgorithm.RS256, digest, signature));
    }

    @Ignore("Mock framework doesn't support data plane")
    public void canWrapAndUnwrap() throws Exception {
        Vault vault = createVault();
        String keyName = SdkContext.randomResourceName("key", 20);

        Key key = vault.keys().define(keyName)
                .withKey(JsonWebKey.fromRSA(KeyPairGenerator.getInstance("RSA").generateKeyPair()))
                .create();

        SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();

        byte[] wrapped = key.wrapKey(JsonWebKeyEncryptionAlgorithm.RSA1_5, secretKey.getEncoded());
        Assert.assertNotNull(wrapped);

        byte[] unwrapped = key.unwrapKey(JsonWebKeyEncryptionAlgorithm.RSA1_5, wrapped);
        Assert.assertNotNull(unwrapped);
        Assert.assertEquals(secretKey, new SecretKeySpec(unwrapped, "AES"));
    }

    private Vault createVault() throws Exception {
        String vaultName = SdkContext.randomResourceName("vault", 20);

        ApplicationTokenCredentials credentials = ApplicationTokenCredentials.fromFile(new File(System.getenv("AZURE_AUTH_LOCATION")));

        Vault vault = keyVaultManager.vaults().define(vaultName)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(RG_NAME)
                .defineAccessPolicy()
                    .forServicePrincipal(credentials.clientId())
                    .allowKeyAllPermissions()
                    .attach()
                .create();

        Assert.assertNotNull(vault);

        SdkContext.sleep(10000);

        return vault;
    }
}
