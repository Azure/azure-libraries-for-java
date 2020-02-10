/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network;

import com.azure.core.http.rest.PagedIterable;
import com.azure.management.resources.fluentcore.arm.Region;
import com.azure.management.resources.fluentcore.utils.SdkContext;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.time.OffsetDateTime;
import java.util.List;

public class NetworkWatcherTests extends NetworkManagementTest {

    @Test
    @Ignore("https://github.com/Azure/azure-rest-api-specs/issues/7579")
    public void canListProvidersAndGetReachabilityReport() throws Exception {
        String nwName = SdkContext.randomResourceName("nw", 8);
        Region region = Region.US_WEST;
        // make sure Network Watcher is disabled in current subscription and region as only one can exist
        PagedIterable<NetworkWatcher> nwList = networkManager.networkWatchers().list();
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
                .execute();
        Assert.assertEquals(1, providers.providersByCountry().size());
        Assert.assertEquals("washington", providers.providersByCountry().get("United States").states().get(0).stateName());
        Assert.assertTrue(providers.providersByCountry().get("United States").states().get(0).providers().size() > 0);

        String localProvider = providers.providersByCountry().get("United States").states().get(0).providers().get(0);
        AzureReachabilityReport report = nw.azureReachabilityReport()
                .withProviderLocation("United States", "washington")
                .withStartTime(OffsetDateTime.parse("2018-04-10"))
                .withEndTime(OffsetDateTime.parse("2018-04-12"))
                .withProviders(localProvider)
                .withAzureLocations("West US")
                .execute();
        Assert.assertEquals("State", report.aggregationLevel());
        Assert.assertTrue(report.reachabilityReport().size() > 0);
    }
}
