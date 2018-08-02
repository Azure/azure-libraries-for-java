/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage;

import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.v2.management.storage.StorageAccount;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Test;

public class StorageAccountNetworkRuleTests extends StorageManagementTest {
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
    public void canConfigureNetworkRulesWithCreate() throws Exception {
        String SA_NAME1 = generateRandomResourceName("javacsmsa", 15);
        String SA_NAME2 = generateRandomResourceName("javacsmsa", 15);
        String SA_NAME3 = generateRandomResourceName("javacsmsa", 15);
        String SA_NAME4 = generateRandomResourceName("javacsmsa", 15);

        StorageAccount storageAccount1 = storageManager.storageAccounts()
                .define(SA_NAME1)
                .withRegion(Region.US_EAST)
                .withNewResourceGroup(RG_NAME)
                .create();

        Assert.assertNotNull(storageAccount1.networkSubnetsWithAccess());
        Assert.assertEquals(0, storageAccount1.networkSubnetsWithAccess().size());

        Assert.assertNotNull(storageAccount1.ipAddressesWithAccess());
        Assert.assertEquals(0, storageAccount1.ipAddressesWithAccess().size());

        Assert.assertNotNull(storageAccount1.ipAddressRangesWithAccess());
        Assert.assertEquals(0, storageAccount1.ipAddressRangesWithAccess().size());

        Assert.assertTrue(storageAccount1.isAccessAllowedFromAllNetworks());
        Assert.assertTrue(storageAccount1.canAccessFromAzureServices());
        Assert.assertTrue(storageAccount1.canReadMetricsFromAnyNetwork());
        Assert.assertTrue(storageAccount1.canReadMetricsFromAnyNetwork());

        ResourceGroup resourceGroup = resourceManager
                .resourceGroups()
                .getByName(storageAccount1.resourceGroupName());

        StorageAccount storageAccount2 = storageManager.storageAccounts()
                .define(SA_NAME2)
                .withRegion(Region.US_EAST)
                .withExistingResourceGroup(resourceGroup)
                .withAccessFromIpAddress("23.20.0.0")
                .create();

        Assert.assertNotNull(storageAccount2.inner().networkRuleSet());
        Assert.assertNotNull(storageAccount2.inner().networkRuleSet().defaultAction());
        Assert.assertNotNull(storageAccount2.inner().networkRuleSet().defaultAction().equals(DefaultAction.DENY));

        Assert.assertNotNull(storageAccount2.networkSubnetsWithAccess());
        Assert.assertEquals(0, storageAccount2.networkSubnetsWithAccess().size());

        Assert.assertNotNull(storageAccount2.ipAddressesWithAccess());
        Assert.assertEquals(1, storageAccount2.ipAddressesWithAccess().size());

        Assert.assertNotNull(storageAccount2.ipAddressRangesWithAccess());
        Assert.assertEquals(0, storageAccount2.ipAddressRangesWithAccess().size());

        Assert.assertFalse(storageAccount2.isAccessAllowedFromAllNetworks());
        Assert.assertFalse(storageAccount2.canAccessFromAzureServices());
        Assert.assertFalse(storageAccount2.canReadMetricsFromAnyNetwork());
        Assert.assertFalse(storageAccount2.canReadMetricsFromAnyNetwork());

        StorageAccount storageAccount3 = storageManager.storageAccounts()
                .define(SA_NAME3)
                .withRegion(Region.US_EAST)
                .withExistingResourceGroup(resourceGroup)
                .withAccessFromAllNetworks()
                .withAccessFromIpAddress("23.20.0.0")
                .create();

        Assert.assertNotNull(storageAccount3.inner().networkRuleSet());
        Assert.assertNotNull(storageAccount3.inner().networkRuleSet().defaultAction());
        Assert.assertNotNull(storageAccount3.inner().networkRuleSet().defaultAction().equals(DefaultAction.ALLOW));

        Assert.assertNotNull(storageAccount3.networkSubnetsWithAccess());
        Assert.assertEquals(0, storageAccount3.networkSubnetsWithAccess().size());

        Assert.assertNotNull(storageAccount3.ipAddressesWithAccess());
        Assert.assertEquals(1, storageAccount3.ipAddressesWithAccess().size());

        Assert.assertNotNull(storageAccount3.ipAddressRangesWithAccess());
        Assert.assertEquals(0, storageAccount3.ipAddressRangesWithAccess().size());

        Assert.assertTrue(storageAccount3.isAccessAllowedFromAllNetworks());
        Assert.assertTrue(storageAccount3.canAccessFromAzureServices());
        Assert.assertTrue(storageAccount3.canReadMetricsFromAnyNetwork());
        Assert.assertTrue(storageAccount3.canReadLogEntriesFromAnyNetwork());

        StorageAccount storageAccount4 = storageManager.storageAccounts()
                .define(SA_NAME4)
                .withRegion(Region.US_EAST)
                .withExistingResourceGroup(resourceGroup)
                .withReadAccessToLogEntriesFromAnyNetwork()
                .withReadAccessToMetricsFromAnyNetwork()
                .create();

        Assert.assertNotNull(storageAccount4.inner().networkRuleSet());
        Assert.assertNotNull(storageAccount4.inner().networkRuleSet().defaultAction());
        Assert.assertNotNull(storageAccount4.inner().networkRuleSet().defaultAction().equals(DefaultAction.DENY));

        Assert.assertNotNull(storageAccount4.networkSubnetsWithAccess());
        Assert.assertEquals(0, storageAccount4.networkSubnetsWithAccess().size());

        Assert.assertNotNull(storageAccount4.ipAddressesWithAccess());
        Assert.assertEquals(0, storageAccount4.ipAddressesWithAccess().size());

        Assert.assertNotNull(storageAccount3.ipAddressRangesWithAccess());
        Assert.assertEquals(0, storageAccount4.ipAddressRangesWithAccess().size());

        Assert.assertFalse(storageAccount4.isAccessAllowedFromAllNetworks());
        Assert.assertFalse(storageAccount4.canAccessFromAzureServices());
        Assert.assertTrue(storageAccount4.canReadMetricsFromAnyNetwork());
        Assert.assertTrue(storageAccount4.canReadLogEntriesFromAnyNetwork());
    }

    @Test
    public void canConfigureNetworkRulesWithUpdate() throws Exception {
        String SA_NAME1 = generateRandomResourceName("javacsmsa", 15);

        StorageAccount storageAccount1 = storageManager.storageAccounts()
                .define(SA_NAME1)
                .withRegion(Region.US_EAST)
                .withNewResourceGroup(RG_NAME)
                .create();

        Assert.assertNotNull(storageAccount1.networkSubnetsWithAccess());
        Assert.assertEquals(0, storageAccount1.networkSubnetsWithAccess().size());

        Assert.assertNotNull(storageAccount1.ipAddressesWithAccess());
        Assert.assertEquals(0, storageAccount1.ipAddressesWithAccess().size());

        Assert.assertNotNull(storageAccount1.ipAddressRangesWithAccess());
        Assert.assertEquals(0, storageAccount1.ipAddressRangesWithAccess().size());

        Assert.assertTrue(storageAccount1.isAccessAllowedFromAllNetworks());
        Assert.assertTrue(storageAccount1.canAccessFromAzureServices());
        Assert.assertTrue(storageAccount1.canReadMetricsFromAnyNetwork());
        Assert.assertTrue(storageAccount1.canReadMetricsFromAnyNetwork());

        storageAccount1.update()
                .withAccessFromSelectedNetworks()
                .withAccessFromIpAddressRange("23.20.0.0/20")
                .apply();

        Assert.assertNotNull(storageAccount1.networkSubnetsWithAccess());
        Assert.assertEquals(0, storageAccount1.networkSubnetsWithAccess().size());

        Assert.assertNotNull(storageAccount1.ipAddressesWithAccess());
        Assert.assertEquals(0, storageAccount1.ipAddressesWithAccess().size());

        Assert.assertNotNull(storageAccount1.ipAddressRangesWithAccess());
        Assert.assertEquals(1, storageAccount1.ipAddressRangesWithAccess().size());

        Assert.assertFalse(storageAccount1.isAccessAllowedFromAllNetworks());
        Assert.assertTrue(storageAccount1.canAccessFromAzureServices());
        Assert.assertFalse(storageAccount1.canReadMetricsFromAnyNetwork());
        Assert.assertFalse(storageAccount1.canReadMetricsFromAnyNetwork());
    }
}
