/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault;

import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.keyvault.models.Attributes;
import com.microsoft.azure.keyvault.webkey.JsonWebKey;
import com.microsoft.azure.keyvault.webkey.JsonWebKeyOperation;
import com.microsoft.azure.keyvault.webkey.JsonWebKeyType;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import java.io.File;
import java.security.KeyPairGenerator;
import java.util.Arrays;
import java.util.List;

public class KeyTests extends KeyVaultManagementTest {
    @Test
    public void canCRUDKey() throws Exception {
        String vaultName = SdkContext.randomResourceName("vault", 20);
        String keyName = SdkContext.randomResourceName("key", 20);

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

        Key key = vault.keys().define(keyName)
                .withKeyType(JsonWebKeyType.RSA)
                .withKeyOperations(Arrays.asList(JsonWebKeyOperation.SIGN, JsonWebKeyOperation.VERIFY))
                .create();

        Assert.assertNotNull(key);
        Assert.assertNotNull(key.id());
        Assert.assertEquals(2, key.jsonWebKey().keyOps().size());

        Key key1 = vault.keys().getById(key.id());
        Assert.assertNotNull(key1);
        Assert.assertEquals(key.id(), key1.id());

        key = key.update()
                .withKeyOperations(Arrays.asList(JsonWebKeyOperation.ENCRYPT))
                .apply();

        Assert.assertEquals(1, key.jsonWebKey().keyOps().size());
    }

    @Test
    public void canImportKey() throws Exception {
        String vaultName = SdkContext.randomResourceName("vault", 20);
        String keyName = SdkContext.randomResourceName("key", 20);

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


        Key key = vault.keys().define(keyName)
                .withKey(JsonWebKey.fromRSA(KeyPairGenerator.getInstance("RSA").generateKeyPair()))
                .create();

        Assert.assertNotNull(key);
        Assert.assertNotNull(key.id());
    }
}
