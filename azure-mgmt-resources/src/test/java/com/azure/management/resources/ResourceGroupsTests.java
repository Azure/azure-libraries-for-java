/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources;

import com.azure.management.RestClient;
import com.azure.management.resources.fluentcore.arm.Region;
import com.azure.management.resources.fluentcore.utils.SdkContext;
import org.junit.Assert;
import org.junit.Test;

public class ResourceGroupsTests extends ResourceManagerTestBase {
    private static ResourceGroups resourceGroups;

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        super.initializeClients(restClient, defaultSubscription, domain);
        resourceGroups = resourceClient.resourceGroups();
    }

    @Test
    public void canCreateResourceGroup() throws Exception {
        final String rgName = SdkContext.randomResourceName("rg", 9);
        Region region = Region.US_SOUTH_CENTRAL;
        // Create
        resourceGroups.define(rgName)
                .withRegion(Region.US_SOUTH_CENTRAL)
                .withTag("department", "finance")
                .withTag("tagname", "tagvalue")
                .create();
        // List
        ResourceGroup groupResult = null;
        for (ResourceGroup rg : resourceGroups.listByTag("department", "finance")) {
            if (rg.getName().equals(rgName)) {
                groupResult = rg;
                break;
            }
        }
        Assert.assertNotNull(groupResult);
        Assert.assertEquals("finance", groupResult.getTags().get("department"));
        Assert.assertEquals("tagvalue", groupResult.getTags().get("tagname"));
        Assert.assertTrue(region.name().equalsIgnoreCase(groupResult.getRegionName()));

        // Check existence
        Assert.assertTrue(resourceGroups.contain(rgName));

        // Get
        ResourceGroup getGroup = resourceGroups.getByName(rgName);
        Assert.assertNotNull(getGroup);
        Assert.assertEquals(rgName, getGroup.getName());
        // Update
        ResourceGroup updatedGroup = getGroup.update()
                .withTag("tag1", "value1")
                .apply();
        Assert.assertEquals("value1", updatedGroup.getTags().get("tag1"));
        Assert.assertTrue(region.name().equalsIgnoreCase(getGroup.getRegionName()));
        // Delete
        resourceGroups.deleteByName(rgName);
        // Assert.assertFalse(resourceGroups.checkExistence(rgName));
    }
}
