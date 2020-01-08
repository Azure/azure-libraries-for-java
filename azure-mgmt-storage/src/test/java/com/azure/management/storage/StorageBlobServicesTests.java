package com.azure.management.storage;

import com.azure.management.RestClient;
import com.azure.management.resources.fluentcore.arm.Region;
import org.junit.Assert;
import org.junit.Test;

public class StorageBlobServicesTests extends StorageManagementTest {
    private static String RG_NAME = "";

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        RG_NAME = generateRandomResourceName("javacsmrg", 15);

        super.initializeClients(restClient, defaultSubscription, domain);
    }

    @Override
    protected void cleanUpResources() {
        resourceManager.resourceGroups().deleteByName(RG_NAME);
    }


    @Test
    public void canCreateBlobServices() {
        String SA_NAME = generateRandomResourceName("javacsmsa", 15);

        StorageAccount storageAccount = storageManager.storageAccounts()
                .define(SA_NAME)
                .withRegion(Region.US_EAST)
                .withNewResourceGroup(RG_NAME)
                .create();

        BlobServices blobServices = this.storageManager.blobServices();
        BlobServiceProperties blobService = blobServices.define("blobServicesTest")
                .withExistingStorageAccount(storageAccount.getResourceGroupName(), storageAccount.getName())
                .withDeleteRetentionPolicyEnabled(5)
                .create();

        Assert.assertTrue(blobService.deleteRetentionPolicy().isEnabled());
        Assert.assertEquals(5, blobService.deleteRetentionPolicy().getDays().intValue());

    }

    @Test
    public void canUpdateBlobServices() {
        String SA_NAME = generateRandomResourceName("javacsmsa", 15);

        StorageAccount storageAccount = storageManager.storageAccounts()
                .define(SA_NAME)
                .withRegion(Region.US_EAST)
                .withNewResourceGroup(RG_NAME)
                .create();

        BlobServices blobServices = this.storageManager.blobServices();
        BlobServiceProperties blobService = blobServices.define("blobServicesTest")
                .withExistingStorageAccount(storageAccount.getResourceGroupName(), storageAccount.getName())
                .withDeleteRetentionPolicyEnabled(5)
                .create();

        blobService.update()
                .withDeleteRetentionPolicyDisabled()
                .apply();

        Assert.assertFalse(blobService.deleteRetentionPolicy().isEnabled());

    }
}