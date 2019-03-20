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
        Assert.assertEquals(5, blobService.deleteRetentionPolicy().days().intValue());

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
                .withMetadata(new HashMap<String, String>())
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

    @Test
    public void canCreateManagementPolicies() {
        String SA_NAME = generateRandomResourceName("javacmsa", 15);
        StorageAccount storageAccount = storageManager.storageAccounts()
                .define(SA_NAME)
                .withRegion(Region.US_WEST_CENTRAL)
                .withNewResourceGroup(RG_NAME)
                .withBlobStorageAccountKind()
                .withAccessTier(AccessTier.COOL)
                .create();


        ManagementPolicies managementPolicies = this.storageManager.managementPolicies();
        ManagementPolicy managementPolicy = managementPolicies.define("management-test")
                .withExistingStorageAccount(RG_NAME, SA_NAME)
                .defineRule()
                    .withName("rule1")
                    .withType("Lifecycle")
                    .withBlobTypeToFilterFor("blockBlob")
                    .withPrefixToFilterFor("container1/foo")
                    .withTierToCoolActionOnBaseBlob(30)
                    .withTierToArchiveActionOnBaseBlob(90)
                    .withDeleteActionOnBaseBlob(2555)
                    .withDeleteActionOnSnapShot(90)
                    .attach()
                .create();

        List<String> blobTypesToFilterFor = new ArrayList<>();
        blobTypesToFilterFor.add("blockBlob");

        List<String> prefixesToFilterFor = new ArrayList<>();
        prefixesToFilterFor.add("container1/foo");

        //Assert.assertEquals("management-test", managementPolicy.policy().);
        Assert.assertEquals("rule1", managementPolicy.policy().rules().get(0).name());
        Assert.assertEquals(blobTypesToFilterFor, managementPolicy.policy().rules().get(0).definition().filters().blobTypes());
        Assert.assertEquals(prefixesToFilterFor, managementPolicy.policy().rules().get(0).definition().filters().prefixMatch());
        Assert.assertEquals(30, managementPolicy.policy().rules().get(0).definition().actions().baseBlob().tierToCool().daysAfterModificationGreaterThan());
        Assert.assertEquals(90, managementPolicy.policy().rules().get(0).definition().actions().baseBlob().tierToArchive().daysAfterModificationGreaterThan());
        Assert.assertEquals(2555, managementPolicy.policy().rules().get(0).definition().actions().baseBlob().delete().daysAfterModificationGreaterThan());
        Assert.assertEquals(90, managementPolicy.policy().rules().get(0).definition().actions().snapshot().delete().daysAfterCreationGreaterThan());
    }

    @Test
    public void managementPolicyGetters() {
        String SA_NAME = generateRandomResourceName("javacmsa", 15);
        StorageAccount storageAccount = storageManager.storageAccounts()
                .define(SA_NAME)
                .withRegion(Region.US_WEST_CENTRAL)
                .withNewResourceGroup(RG_NAME)
                .withBlobStorageAccountKind()
                .withAccessTier(AccessTier.COOL)
                .create();


        ManagementPolicies managementPolicies = this.storageManager.managementPolicies();
        ManagementPolicy managementPolicy = managementPolicies.define("management-test")
                .withExistingStorageAccount(RG_NAME, SA_NAME)
                .defineRule()
                    .withName("rule1")
                    .withType("Lifecycle")
                    .withBlobTypeToFilterFor("blockBlob")
                    .withPrefixToFilterFor("container1/foo")
                    .withTierToCoolActionOnBaseBlob(30)
                    .withTierToArchiveActionOnBaseBlob(90)
                    .withDeleteActionOnBaseBlob(2555)
                    .withDeleteActionOnSnapShot(90)
                    .attach()
                .create();

        List<String> blobTypesToFilterFor = new ArrayList<>();
        blobTypesToFilterFor.add("blockBlob");

        List<String> prefixesToFilterFor = new ArrayList<>();
        prefixesToFilterFor.add("container1/foo");

        List<PolicyRule> rules = managementPolicy.rules();
        Assert.assertEquals("rule1", rules.get(0).name());
        Assert.assertEquals(blobTypesToFilterFor, rules.get(0).blobTypesToFilterFor());
        Assert.assertEquals(prefixesToFilterFor, rules.get(0).prefixesToFilterFor());
        Assert.assertEquals(30, rules.get(0).daysAfterBaseBlobModificationUntilCooling().intValue());
        Assert.assertTrue(rules.get(0).tierToCoolActionOnBaseBlobEnabled());
        Assert.assertEquals(90, rules.get(0).daysAfterBaseBlobModificationUntilArchiving().intValue());
        Assert.assertTrue(rules.get(0).tierToArchiveActionOnBaseBlobEnabled());
        Assert.assertEquals(2555, rules.get(0).daysAfterBaseBlobModificationUntilDeleting().intValue());
        Assert.assertTrue(rules.get(0).deleteActionOnBaseBlobEnabled());
        Assert.assertEquals(90,rules.get(0).daysAfterSnapShotCreationUntilDeleting().intValue());
        Assert.assertTrue(rules.get(0).deleteActionOnSnapShotEnabled());
    }

}


//    @Test
//    public void canCreateManagementPolicies() throws IOException {
//        AzureJacksonAdapter azureJacksonAdapter = new AzureJacksonAdapter();
//        PolicyObject policyObject = new PolicyObject();
//        policyObject.version = "0.5";
//        policyObject.rules = new ArrayList<PolicyObject.PolicyRule>();
//        PolicyObject.PolicyRule policyRule = new PolicyObject.PolicyRule();
//        policyRule.name = "agingRule";
//        policyRule.type = "Lifecycle";
//        PolicyObject.PolicyRule.RuleDefinition definition = new PolicyObject.PolicyRule.RuleDefinition();
//        PolicyObject.PolicyRule.RuleDefinition.RuleFilters ruleFilters = new PolicyObject.PolicyRule.RuleDefinition.RuleFilters();
//
//        List<PolicyObject.PolicyRule.RuleDefinition.RuleFilters.BlobTypes> blobTypes = new ArrayList<>();
//        blobTypes.add(PolicyObject.PolicyRule.RuleDefinition.RuleFilters.BlobTypes.BLOCKBLOB);
//
//        List<String> prefixMatches = new ArrayList<>();
//        prefixMatches.add("container1/foo");
//        prefixMatches.add("container2/bar");
//
//        ruleFilters.blobTypes = blobTypes;
//        ruleFilters.prefixMatches = prefixMatches;
//
//
//        definition.filters = ruleFilters;
//
//        PolicyObject.PolicyRule.RuleDefinition.RuleActions actions = new PolicyObject.PolicyRule.RuleDefinition.RuleActions();
//        Map<PolicyObject.PolicyRule.RuleDefinition.RuleActions.RuleAction, PolicyObject.PolicyRule.RuleDefinition.RuleActions.RuleActionCondition> baseBlobConditions = new HashMap<>();
//        PolicyObject.PolicyRule.RuleDefinition.RuleActions.RuleActionCondition tierToCoolCondition = new PolicyObject.PolicyRule.RuleDefinition.RuleActions.RuleActionCondition();
//        tierToCoolCondition.daysAfterModificationGreaterThan = 30;
//        baseBlobConditions.put(PolicyObject.PolicyRule.RuleDefinition.RuleActions.RuleAction.TIERTOCOOL, tierToCoolCondition);
//
//        PolicyObject.PolicyRule.RuleDefinition.RuleActions.RuleActionCondition tierToArchiveCondition = new PolicyObject.PolicyRule.RuleDefinition.RuleActions.RuleActionCondition();
//        tierToArchiveCondition.daysAfterModificationGreaterThan = 90;
//        baseBlobConditions.put(PolicyObject.PolicyRule.RuleDefinition.RuleActions.RuleAction.TIERTOARCHIVE, tierToArchiveCondition);
//
//        actions.baseBlobConditions = baseBlobConditions;
//        definition.actions = actions;
//
//        policyRule.definition = definition;
//        policyObject.rules.add(policyRule);
//
//
//        System.out.println(azureJacksonAdapter.serialize(policyObject));
//
//
////        String SA_NAME = generateRandomResourceName("javacmsa", 15);
////
////        StorageAccount storageAccount = storageManager.storageAccounts()
////                .define(SA_NAME)
////                .withRegion(Region.US_EAST)
////                .withNewResourceGroup(RG_NAME)
////                .create();
////
////        ManagementPolicies managementPolicies = this.storageManager.managementPolicies();
////        StorageAccountManagementPolicies managementPolicy = managementPolicies.define("management-test")
////                .withExistingStorageAccount(RG_NAME, SA_NAME)
////                .withPolicy(policyObject)
////                .create();
//    }
