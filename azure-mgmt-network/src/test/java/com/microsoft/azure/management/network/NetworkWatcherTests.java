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

public class NetworkWatcherTests extends NetworkManagementTest {

    @Test
    public void canListAvailableProviders() throws Exception {
        String nwName = SdkContext.randomResourceName("nw", 8);
        Region region = Region.US_SOUTH_CENTRAL;
        // make sure Network Watcher is disabled in current subscription and region as only one can exist
        List<NetworkWatcher> nwList = networkManager.networkWatchers().list();
        for (NetworkWatcher nw : nwList) {
            if (region.equals(nw.region())) {
                networkManager.networkWatchers().deleteById(nw.id());
            }
        }
        // create Network Watcher
        NetworkWatcher nw = networkManager.networkWatchers().define(nwName)
                .withRegion(region)
                .withNewResourceGroup(RG_NAME)
                .create();
        AvailableProviders providers = nw.availableProviders()
                .execute();
        Assert.assertTrue(providers.providersByCountry().size() > 1);
        Assert.assertNotNull(providers.providersByCountry().get("United States"));

        providers = nw.availableProviders()
                .withAzureLocation("West US")
                .withCountry("United States")
                .withState("washington")
                .withCity("seattle")
                .execute();
        Assert.assertEquals(1, providers.providersByCountry().size());
        Assert.assertEquals("washington", providers.providersByCountry().get("United States").states().get(0).stateName());
        Assert.assertEquals("seattle", providers.providersByCountry().get("United States").states().get(0).cities().get(0).cityName());
    }
}
