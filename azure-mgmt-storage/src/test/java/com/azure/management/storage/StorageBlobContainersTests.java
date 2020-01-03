package com.azure.management.storage;

import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class StorageBlobContainersTests extends StorageManagementTest {
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
    public void canCreateBlobContainer() {
        String SA_NAME = generateRandomResourceName("javacmsa", 15);
        Map<String, String> metadataTest = new HashMap<String, String>();
        metadataTest.put("a", "b");
        metadataTest.put("c", "d");


        StorageAccount storageAccount = storageManager.storageAccounts()
                .define(SA_NAME)
                .withRegion(Region.US_EAST)
                .withNewResourceGroup(RG_NAME)
                .create();

        BlobContainers blobContainers = this.storageManager.blobContainers();
        BlobContainer blobContainer = blobContainers.defineContainer("blob-test")
                .withExistingBlobService(RG_NAME, SA_NAME)
                .withPublicAccess(PublicAccess.CONTAINER)
                .withMetadata("a", "b")
                .withMetadata("c", "d")
                .create();


        Assert.assertEquals("blob-test", blobContainer.name());
        Assert.assertEquals(PublicAccess.CONTAINER, blobContainer.publicAccess());
        Assert.assertEquals(metadataTest, blobContainer.metadata());
    }

    @Test
    public void canUpdateBlobContainer() {
        String SA_NAME = generateRandomResourceName("javacmsa", 15);

        Map<String, String> metadataInitial = new HashMap<String, String>();
        metadataInitial.put("a", "b");

        Map<String, String> metadataTest = new HashMap<String, String>();
        metadataTest.put("c", "d");
        metadataTest.put("e", "f");


        StorageAccount storageAccount = storageManager.storageAccounts()
                .define(SA_NAME)
                .withRegion(Region.US_EAST)
                .withNewResourceGroup(RG_NAME)
                .create();

        BlobContainers blobContainers = this.storageManager.blobContainers();
        BlobContainer blobContainer = blobContainers.defineContainer("blob-test")
                .withExistingBlobService(RG_NAME, SA_NAME)
                .withPublicAccess(PublicAccess.CONTAINER)
                .withMetadata(metadataInitial)
                .create();

        blobContainer.update()
                .withPublicAccess(PublicAccess.BLOB)
                .withMetadata("c", "d")
                .withMetadata("e", "f")
                .apply();

        Assert.assertEquals("blob-test", blobContainer.name());
        Assert.assertEquals(PublicAccess.BLOB, blobContainer.publicAccess());
        Assert.assertEquals(metadataTest, blobContainer.metadata());
    }
}