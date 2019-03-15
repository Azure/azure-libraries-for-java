package com.microsoft.azure.management.storage;

import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.serializer.AzureJacksonAdapter;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .withExistingStorageAccount(storageAccount.resourceGroupName(), storageAccount.name())
                .withDeleteRetentionPolicyEnabled(5)
                .create();

        Assert.assertTrue(blobService.deleteRetentionPolicy().enabled());
        Assert.assertEquals(blobService.deleteRetentionPolicy().days().intValue(), 5);

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
                .withExistingStorageAccount(storageAccount.resourceGroupName(), storageAccount.name())
                .withDeleteRetentionPolicyEnabled(5)
                .create();

        blobService.update()
                .withDeleteRetentionPolicyDisabled()
                .apply();

        Assert.assertTrue(blobService.deleteRetentionPolicy().enabled());
        Assert.assertEquals(blobService.deleteRetentionPolicy().days().intValue(), 5);
        Assert.assertFalse(blobService.deleteRetentionPolicy().enabled());

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


        Assert.assertEquals(blobContainer.name(), "blob-test");
        Assert.assertEquals(blobContainer.publicAccess(), PublicAccess.CONTAINER);
        Assert.assertEquals(blobContainer.metadata(), metadataTest);
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

        Assert.assertEquals(blobContainer.name(), "blob-test");
        Assert.assertEquals(blobContainer.publicAccess(), PublicAccess.BLOB);
        Assert.assertEquals(blobContainer.metadata(), metadataTest);
    }

    @Test
    public void canCreateManagementPolicies() throws IOException {
        AzureJacksonAdapter azureJacksonAdapter = new AzureJacksonAdapter();
        PolicyObject policyObject = new PolicyObject();
        policyObject.version = "0.5";
        policyObject.rules = new ArrayList<PolicyObject.PolicyRule>();
        PolicyObject.PolicyRule policyRule = new PolicyObject.PolicyRule();
        policyRule.name = "agingRule";
        policyRule.type = "Lifecycle";
        PolicyObject.PolicyRule.RuleDefinition definition = new PolicyObject.PolicyRule.RuleDefinition();
        PolicyObject.PolicyRule.RuleDefinition.RuleFilters ruleFilters = new PolicyObject.PolicyRule.RuleDefinition.RuleFilters();

        List<PolicyObject.PolicyRule.RuleDefinition.RuleFilters.BlobTypes> blobTypes = new ArrayList<>();
        blobTypes.add(PolicyObject.PolicyRule.RuleDefinition.RuleFilters.BlobTypes.BLOCKBLOB);

        List<String> prefixMatches = new ArrayList<>();
        prefixMatches.add("container1/foo");
        prefixMatches.add("container2/bar");

        ruleFilters.blobTypes = blobTypes;
        ruleFilters.prefixMatches = prefixMatches;


        definition.filters = ruleFilters;

        PolicyObject.PolicyRule.RuleDefinition.RuleActions actions = new PolicyObject.PolicyRule.RuleDefinition.RuleActions();
        Map<PolicyObject.PolicyRule.RuleDefinition.RuleActions.RuleAction, PolicyObject.PolicyRule.RuleDefinition.RuleActions.RuleActionCondition> baseBlobConditions = new HashMap<>();
        PolicyObject.PolicyRule.RuleDefinition.RuleActions.RuleActionCondition tierToCoolCondition = new PolicyObject.PolicyRule.RuleDefinition.RuleActions.RuleActionCondition();
        tierToCoolCondition.daysAfterModificationGreaterThan = 30;
        baseBlobConditions.put(PolicyObject.PolicyRule.RuleDefinition.RuleActions.RuleAction.TIERTOCOOL, tierToCoolCondition);

        PolicyObject.PolicyRule.RuleDefinition.RuleActions.RuleActionCondition tierToArchiveCondition = new PolicyObject.PolicyRule.RuleDefinition.RuleActions.RuleActionCondition();
        tierToArchiveCondition.daysAfterModificationGreaterThan = 90;
        baseBlobConditions.put(PolicyObject.PolicyRule.RuleDefinition.RuleActions.RuleAction.TIERTOARCHIVE, tierToArchiveCondition);

        actions.baseBlobConditions = baseBlobConditions;
        definition.actions = actions;

        policyRule.definition = definition;
        policyObject.rules.add(policyRule);


        System.out.println(azureJacksonAdapter.serialize(policyObject));


//        String SA_NAME = generateRandomResourceName("javacmsa", 15);
//
//        StorageAccount storageAccount = storageManager.storageAccounts()
//                .define(SA_NAME)
//                .withRegion(Region.US_EAST)
//                .withNewResourceGroup(RG_NAME)
//                .create();
//
//        ManagementPolicies managementPolicies = this.storageManager.managementPolicies();
//        StorageAccountManagementPolicies managementPolicy = managementPolicies.define("management-test")
//                .withExistingStorageAccount(RG_NAME, SA_NAME)
//                .withPolicy(policyObject)
//                .create();
    }
}
