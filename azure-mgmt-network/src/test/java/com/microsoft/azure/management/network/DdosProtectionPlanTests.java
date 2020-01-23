/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network;

import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DdosProtectionPlanTests extends NetworkManagementTest {

    @Test
    public void canCRUDDdosProtectionPlan() throws Exception {
        String ppName = SdkContext.randomResourceName("ddosplan", 15);

        DdosProtectionPlan pPlan = networkManager.ddosProtectionPlans().define(ppName)
                .withRegion(Region.US_SOUTH_CENTRAL)
                .withNewResourceGroup(RG_NAME)
                .withTag("tag1", "value1")
                .create();
        Assert.assertEquals("value1", pPlan.tags().get("tag1"));

        List<DdosProtectionPlan> ppList = networkManager.ddosProtectionPlans().list();
        Assert.assertTrue(ppList.size() > 0);

        ppList = networkManager.ddosProtectionPlans().listByResourceGroup(RG_NAME);
        Assert.assertTrue(ppList.size() > 0);

        networkManager.ddosProtectionPlans().deleteById(pPlan.id());
        ppList = networkManager.ddosProtectionPlans().listByResourceGroup(RG_NAME);
        Assert.assertTrue(ppList.isEmpty());
    }
}