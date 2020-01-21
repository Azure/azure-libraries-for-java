/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.keyvault;

import com.azure.core.http.rest.PagedIterable;
import com.azure.management.ApplicationTokenCredential;
import com.azure.management.resources.fluentcore.arm.Region;
import com.azure.management.resources.fluentcore.utils.SdkContext;
import org.junit.Assert;
import org.junit.Ignore;

import java.io.File;

public class SecretTests extends KeyVaultManagementTest {
    @Ignore("Mock framework doesn't support data plane")
    public void canCRUDSecret() throws Exception {
        String vaultName = SdkContext.randomResourceName("vault", 20);
        String secretName = SdkContext.randomResourceName("secret", 20);

        ApplicationTokenCredential credentials = ApplicationTokenCredential.fromFile(new File(System.getenv("AZURE_AUTH_LOCATION")));

        Vault vault = keyVaultManager.vaults().define(vaultName)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(RG_NAME)
                .defineAccessPolicy()
                    .forServicePrincipal(credentials.getClientId())
                    .allowSecretAllPermissions()
                    .attach()
                .create();

        Assert.assertNotNull(vault);

        SdkContext.sleep(10000);

        Secret secret = vault.secrets().define(secretName)
                .withValue("Some secret value")
                .create();

        Assert.assertNotNull(secret);
        Assert.assertNotNull(secret.getId());
        Assert.assertEquals("Some secret value", secret.getValue());

        secret = secret.update()
                .withValue("Some updated value")
                .apply();

        Assert.assertEquals("Some updated value", secret.getValue());

        Iterable<Secret> versions = secret.listVersions();

        int count = 2;
        for (Secret version : versions) {
            if ("Some secret value".equals(version.getValue())) {
                count --;
            }
            if ("Some updated value".equals(version.getValue())) {
                count --;
            }
        }
        Assert.assertEquals(0, count);

    }
}
