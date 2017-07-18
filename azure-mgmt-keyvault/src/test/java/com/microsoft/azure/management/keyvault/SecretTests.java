/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault;

import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class SecretTests extends KeyVaultManagementTest {
    @Test
    public void canCRUDSecret() throws Exception {
        String vaultName = SdkContext.randomResourceName("vault", 20);
        String secretName = SdkContext.randomResourceName("secret", 20);

        ApplicationTokenCredentials credentials = ApplicationTokenCredentials.fromFile(new File(System.getenv("AZURE_AUTH_LOCATION")));

        Vault vault = keyVaultManager.vaults().define(vaultName)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(RG_NAME)
                .defineAccessPolicy()
                    .forServicePrincipal(credentials.clientId())
                    .allowSecretAllPermissions()
                    .attach()
                .create();

        Assert.assertNotNull(vault);

        SdkContext.sleep(10000);

        Secret secret = vault.secrets().define(secretName)
                .withValue("Some secret value")
                .create();

        Assert.assertNotNull(secret);
        Assert.assertNotNull(secret.id());
        Assert.assertEquals("Some secret value", secret.value());

        secret = secret.update()
                .withValue("Some updated value")
                .apply();

        Assert.assertEquals("Some updated value", secret.value());
    }
}
