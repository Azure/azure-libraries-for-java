/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage;

import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class StorageAccountOperationsTests extends StorageManagementTest {
    private static String RG_NAME = "";
    private static String SA_NAME = "";

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        RG_NAME = generateRandomResourceName("javacsmrg", 15);
        SA_NAME = generateRandomResourceName("javacsmsa", 15);

        super.initializeClients(restClient, defaultSubscription, domain);
    }
    @Override
    protected void cleanUpResources() {
        resourceManager.resourceGroups().deleteByName(RG_NAME);
    }

    @Test
    public void canCRUDStorageAccount() throws Exception {
        // Name available
        // Skipping checking name availability for now because of 503 error 'The service is not yet ready to process any requests. Please retry in a few moments.'
//        CheckNameAvailabilityResult result = storageManager.storageAccounts()
//                .checkNameAvailability(SA_NAME);
//        Assert.assertEquals(true, result.isAvailable());
        // Create
        Observable<Indexable> resourceStream = storageManager.storageAccounts()
                .define(SA_NAME)
                .withRegion(Region.ASIA_EAST)
                .withNewResourceGroup(RG_NAME)
                .withGeneralPurposeAccountKindV2()
                .withTag("tag1", "value1")
                .withHnsEnabled(true)
                .withAzureFilesAadIntegrationEnabled(false)
                .createAsync();
        StorageAccount storageAccount = Utils.<StorageAccount>rootResource(resourceStream)
                .toBlocking().last();
        Assert.assertEquals(RG_NAME, storageAccount.resourceGroupName());
        Assert.assertEquals(SkuName.STANDARD_GRS, storageAccount.sku().name());
        Assert.assertTrue(storageAccount.isHnsEnabled());
        Assert.assertFalse(storageAccount.isAzureFilesAadIntegrationEnabled());
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
                if (isRecordMode()) {
                    Assert.assertNotEquals(oldKey.value(), updatedKey.value());
                }
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

    @Test
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

//        Storage no-longer support disabling encryption
//
//        storageAccount = storageAccount.update()
//                .withoutBlobEncryption()
//                .apply();
//        statuses = storageAccount.encryptionStatuses();
//        Assert.assertNotNull(statuses);
//        Assert.assertTrue(statuses.size() > 0);
//        blobServiceEncryptionStatus = statuses.get(StorageService.BLOB);
//        Assert.assertNotNull(blobServiceEncryptionStatus);
//        Assert.assertFalse(blobServiceEncryptionStatus.isEnabled());
    }


    @Test
    public void canEnableLargeFileSharesOnStorageAccount() throws Exception {
        StorageAccount storageAccount = storageManager.storageAccounts()
                .define(SA_NAME)
                .withRegion(Region.US_EAST2)
                .withNewResourceGroup(RG_NAME)
                .withSku(StorageAccountSkuType.STANDARD_LRS)
                .withLargeFileShares(true)
                .create();

        Assert.assertTrue(storageAccount.isLargeFileSharesEnabled());
    }

    @Test
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

// Service no longer support disabling file encryption
//        storageAccount.update()
//                .withoutFileEncryption()
//                .apply();
//
//        statuses = storageAccount.encryptionStatuses();
//
//        Assert.assertTrue(statuses.containsKey(StorageService.FILE));
//        fileServiceEncryptionStatus = statuses.get(StorageService.FILE);
//        Assert.assertNotNull(fileServiceEncryptionStatus);
//        Assert.assertFalse(fileServiceEncryptionStatus.isEnabled());

    }
}
