/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources;

import com.azure.management.RestClient;
import com.azure.management.resources.fluentcore.arm.Region;
import com.azure.management.resources.fluentcore.utils.SdkContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
            if (rg.name().equals(rgName)) {
                groupResult = rg;
                break;
            }
        }
        Assertions.assertNotNull(groupResult);
        Assertions.assertEquals("finance", groupResult.tags().get("department"));
        Assertions.assertEquals("tagvalue", groupResult.tags().get("tagname"));
        Assertions.assertTrue(region.name().equalsIgnoreCase(groupResult.regionName()));

        // Check existence
        Assertions.assertTrue(resourceGroups.contain(rgName));

        // Get
        ResourceGroup getGroup = resourceGroups.getByName(rgName);
        Assertions.assertNotNull(getGroup);
        Assertions.assertEquals(rgName, getGroup.name());
        // Update
        ResourceGroup updatedGroup = getGroup.update()
                .withTag("tag1", "value1")
                .apply();
        Assertions.assertEquals("value1", updatedGroup.tags().get("tag1"));
        Assertions.assertTrue(region.name().equalsIgnoreCase(getGroup.regionName()));
        // Delete
        resourceGroups.deleteByName(rgName);
        // Assertions.assertFalse(resourceGroups.checkExistence(rgName));
    }
}
