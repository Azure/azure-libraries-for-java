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

public class RouteFilterTests extends NetworkManagementTest {

    @Test
    public void canCRUDRouteFilter() throws Exception {
        String rfName = SdkContext.randomResourceName("rf", 15);

        RouteFilter routeFilter = networkManager.routeFilters().define(rfName)
                .withRegion(Region.US_SOUTH_CENTRAL)
                .withNewResourceGroup(RG_NAME)
                .withTag("tag1", "value1")
                .create();
        Assert.assertEquals("value1", routeFilter.tags().get("tag1"));

        List<RouteFilter> rfList = networkManager.routeFilters().list();
        Assert.assertTrue(rfList.size() > 0);

        rfList = networkManager.routeFilters().listByResourceGroup(RG_NAME);
        Assert.assertTrue(rfList.size() > 0);

        networkManager.routeFilters().deleteById(routeFilter.id());
        rfList = networkManager.routeFilters().listByResourceGroup(RG_NAME);
        Assert.assertTrue(rfList.isEmpty());
    }
}
