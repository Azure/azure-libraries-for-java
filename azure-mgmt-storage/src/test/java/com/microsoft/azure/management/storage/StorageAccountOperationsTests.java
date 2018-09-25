/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage;

import com.microsoft.azure.v2.AzureEnvironment;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.v2.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.Utils;
import com.microsoft.azure.v2.management.storage.CheckNameAvailabilityResult;
import com.microsoft.azure.v2.management.storage.SkuName;
import com.microsoft.azure.v2.management.storage.StorageAccount;
import com.microsoft.azure.v2.management.storage.StorageAccountEncryptionKeySource;
import com.microsoft.azure.v2.management.storage.StorageAccountEncryptionStatus;
import com.microsoft.azure.v2.management.storage.StorageAccountKey;
import com.microsoft.azure.v2.management.storage.StorageService;
import com.microsoft.rest.v2.http.HttpPipeline;
import io.reactivex.Observable;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class StorageAccountOperationsTests extends StorageManagementTest {
    private static String RG_NAME = "";
    private static String SA_NAME = "";

    @Override
    protected void initializeClients(HttpPipeline httpPipeline, String defaultSubscription, String domain, AzureEnvironment environment) {
        RG_NAME = generateRandomResourceName("javacsmrg", 15);
        SA_NAME = generateRandomResourceName("javacsmsa", 15);

        super.initializeClients(httpPipeline, defaultSubscription, domain, environment);
    }
    @Override
    protected void cleanUpResources() {
        resourceManager.resourceGroups().deleteByName(RG_NAME);
    }

    @Test
    public void canCRUDStorageAccount() throws Exception {
        // Name available
        CheckNameAvailabilityResult result = storageManager.storageAccounts()
                .checkNameAvailability(SA_NAME);
        Assert.assertEquals(true, result.isAvailable());
        // Create
        Observable<Indexable> resourceStream = storageManager.storageAccounts()
                .define(SA_NAME)
                .withRegion(Region.ASIA_EAST)
                .withNewResourceGroup(RG_NAME)
                .withTag("tag1", "value1")
                .createAsync();
        StorageAccount storageAccount = Utils.<StorageAccount>rootResource(resourceStream)
                .blockingGet();
        Assert.assertEquals(RG_NAME, storageAccount.resourceGroupName());
        Assert.assertEquals(SkuName.STANDARD_GRS, storageAccount.sku().name());
        // List
        List<StorageAccount> accounts = storageManager.storageAccounts().listByResourceGroup(RG_NAME);
        boolean found = false;
        for (StorageAccount account : accounts) {
            if (account.name().equals(SA_NAME)) {
                found = true;
            }
        }
        Assert.assertTrue(found);
        Assert.assertEquals(1, storageAccount.tags().size());

        // Get
        storageAccount = storageManager.storageAccounts().getByResourceGroup(RG_NAME, SA_NAME);
        Assert.assertNotNull(storageAccount);

        // Get Keys
        List<StorageAccountKey> keys = storageAccount.getKeys();
        Assert.assertTrue(keys.size() > 0);

        // Regen key
        StorageAccountKey oldKey = keys.get(0);
        List<StorageAccountKey> updatedKeys = storageAccount.regenerateKey(oldKey.keyName());
        Assert.assertTrue(updatedKeys.size() > 0);
        for (StorageAccountKey updatedKey : updatedKeys) {
            if (updatedKey.keyName().equalsIgnoreCase(oldKey.keyName())) {
                Assert.assertNotEquals(oldKey.value(), updatedKey.value());
                break;
            }
        }

        Map<StorageService, StorageAccountEncryptionStatus> statuses = storageAccount.encryptionStatuses();
        Assert.assertNotNull(statuses);
        Assert.assertTrue(statuses.size() > 0);

        Assert.assertTrue(statuses.containsKey(StorageService.BLOB));
        StorageAccountEncryptionStatus blobServiceEncryptionStatus = statuses.get(StorageService.BLOB);
        Assert.assertNotNull(blobServiceEncryptionStatus);
        Assert.assertTrue(blobServiceEncryptionStatus.isEnabled()); // Service will enable this by default

        Assert.assertTrue(statuses.containsKey(StorageService.FILE));
        StorageAccountEncryptionStatus fileServiceEncryptionStatus = statuses.get(StorageService.FILE);
        Assert.assertNotNull(fileServiceEncryptionStatus);
        Assert.assertTrue(fileServiceEncryptionStatus.isEnabled()); // Service will enable this by default

        // Update
        storageAccount = storageAccount.update()
                .withSku(SkuName.STANDARD_LRS)
                .withTag("tag2", "value2")
                .apply();
        Assert.assertEquals(SkuName.STANDARD_LRS, storageAccount.sku().name());
        Assert.assertEquals(2, storageAccount.tags().size());
    }

    @Ignore("new storage service doesn't allow disabling default encryption")
    public void canEnableDisableEncryptionOnStorageAccount() throws Exception {
        StorageAccount storageAccount = storageManager.storageAccounts()
                .define(SA_NAME)
                    .withRegion(Region.US_EAST2)
                    .withNewResourceGroup(RG_NAME)
                    .withBlobEncryption()
                    .create();

        Map<StorageService, StorageAccountEncryptionStatus> statuses = storageAccount.encryptionStatuses();
        Assert.assertNotNull(statuses);
        Assert.assertTrue(statuses.size() > 0);
        StorageAccountEncryptionStatus blobServiceEncryptionStatus = statuses.get(StorageService.BLOB);
        Assert.assertNotNull(blobServiceEncryptionStatus);
        Assert.assertTrue(blobServiceEncryptionStatus.isEnabled());
        Assert.assertNotNull(blobServiceEncryptionStatus.lastEnabledTime());
        Assert.assertNotNull(storageAccount.encryptionKeySource());
        Assert.assertTrue(storageAccount.encryptionKeySource().equals(StorageAccountEncryptionKeySource.MICROSOFT_STORAGE));

        storageAccount = storageAccount.update()
                .withoutBlobEncryption()
                .apply();
        statuses = storageAccount.encryptionStatuses();
        Assert.assertNotNull(statuses);
        Assert.assertTrue(statuses.size() > 0);
        blobServiceEncryptionStatus = statuses.get(StorageService.BLOB);
        Assert.assertNotNull(blobServiceEncryptionStatus);
        Assert.assertFalse(blobServiceEncryptionStatus.isEnabled());
    }

    @Ignore("new storage service doesn't allow disabling default encryption")
    public void canEnableDisableFileEncryptionOnStorageAccount() throws Exception {
        StorageAccount storageAccount = storageManager.storageAccounts()
                .define(SA_NAME)
                .withRegion(Region.US_EAST2)
                .withNewResourceGroup(RG_NAME)
                .withFileEncryption()
                .create();

        Map<StorageService, StorageAccountEncryptionStatus> statuses = storageAccount.encryptionStatuses();
        Assert.assertNotNull(statuses);
        Assert.assertTrue(statuses.size() > 0);

        Assert.assertTrue(statuses.containsKey(StorageService.BLOB));
        StorageAccountEncryptionStatus blobServiceEncryptionStatus = statuses.get(StorageService.BLOB);
        Assert.assertNotNull(blobServiceEncryptionStatus);
        Assert.assertTrue(blobServiceEncryptionStatus.isEnabled()); // Enabled by default by default

        Assert.assertTrue(statuses.containsKey(StorageService.FILE));
        StorageAccountEncryptionStatus fileServiceEncryptionStatus = statuses.get(StorageService.FILE);
        Assert.assertNotNull(fileServiceEncryptionStatus);
        Assert.assertTrue(fileServiceEncryptionStatus.isEnabled());

        storageAccount.update()
                .withoutFileEncryption()
                .apply();

        statuses = storageAccount.encryptionStatuses();

        Assert.assertTrue(statuses.containsKey(StorageService.FILE));
        fileServiceEncryptionStatus = statuses.get(StorageService.FILE);
        Assert.assertNotNull(fileServiceEncryptionStatus);
        Assert.assertFalse(fileServiceEncryptionStatus.isEnabled());

    }
}
