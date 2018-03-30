/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management;

import com.microsoft.azure.management.network.ExpressRouteCircuit;
import com.microsoft.azure.management.network.ExpressRouteCircuitSkuType;
import com.microsoft.azure.management.network.ExpressRouteCircuits;
import com.microsoft.azure.management.network.ExpressRoutePeeringType;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import org.junit.Assert;

/**
 * Tests Express Route Circuit.
 */
public class TestExpressRouteCircuit {
    private static String TEST_ID = "";
    private static Region REGION = Region.US_NORTH_CENTRAL;
    private static String CIRCUIT_NAME;

    private static void initializeResourceNames() {
        TEST_ID = SdkContext.randomResourceName("", 8);
        CIRCUIT_NAME = "erc" + TEST_ID;
    }

    /**
     * Test Express Route Circuit Create and Update.
     */
    public static class Basic extends TestTemplate<ExpressRouteCircuit, ExpressRouteCircuits> {
        @Override
        public ExpressRouteCircuit createResource(ExpressRouteCircuits expressRouteCircuits) throws Exception {
            initializeResourceNames();

            // create Express Route Circuit
            ExpressRouteCircuit erc = expressRouteCircuits.define(CIRCUIT_NAME)
                    .withRegion(REGION)
                    .withNewResourceGroup()
                    .withServiceProvider("Microsoft ER Test")
                    .withPeeringLocation("Area51")
                    .withBandwidthInMbps(50)
                    .withSku(ExpressRouteCircuitSkuType.STANDARD_METEREDDATA)
                    .withTag("tag1", "value1")
                    .create();
            return erc;
        }

        @Override
        public ExpressRouteCircuit updateResource(ExpressRouteCircuit resource) throws Exception {
            resource.update()
                    .withTag("tag2", "value2")
                    .withoutTag("tag1")
                    .withBandwidthInMbps(200)
                    .withSku(ExpressRouteCircuitSkuType.PREMIUM_UNLIMITEDDATA)
                    .apply();
            resource.refresh();
            Assert.assertTrue(resource.tags().containsKey("tag2"));
            Assert.assertTrue(!resource.tags().containsKey("tag1"));
            Assert.assertEquals(Integer.valueOf(200), resource.serviceProviderProperties().bandwidthInMbps());
            Assert.assertEquals(ExpressRouteCircuitSkuType.PREMIUM_UNLIMITEDDATA, resource.sku());
            return resource;
        }

        @Override
        public void print(ExpressRouteCircuit resource) {
            printExpressRouteCircuit(resource);
        }
    }

    /**
     * Test Virtual Network Gateway Create and Update.
     */
    public static class ExpressRouteCircuitPeering extends TestTemplate<ExpressRouteCircuit, ExpressRouteCircuits> {
        @Override
        public ExpressRouteCircuit createResource(ExpressRouteCircuits expressRouteCircuits) throws Exception {
            initializeResourceNames();

            // create Express Route Circuit
            ExpressRouteCircuit erc = expressRouteCircuits.define(CIRCUIT_NAME)
                    .withRegion(REGION)
                    .withNewResourceGroup()
                    .withServiceProvider("Microsoft ER Test")
                    .withPeeringLocation("Area51")
                    .withBandwidthInMbps(50)
                    .withSku(ExpressRouteCircuitSkuType.PREMIUM_METEREDDATA)
                    .withTag("tag1", "value1")
                    .create();
            erc.peerings().defineMicrosoftPeering()
                    .withAdvertisedPublicPrefixes("123.1.0.0/24")
                    .withPrimaryPeerAddressPrefix("123.0.0.0/30")
                    .withSecondaryPeerAddressPrefix("123.0.0.4/30")
                    .withVlanId(200)
                    .withPeerAsn(100)
                    .create();
            Assert.assertEquals(erc.peeringsMap().size(), 1);
            return erc;
        }

        @Override
        public ExpressRouteCircuit updateResource(ExpressRouteCircuit resource) throws Exception {
            Assert.assertTrue(resource.peeringsMap().containsKey(ExpressRoutePeeringType.MICROSOFT_PEERING.toString()));
            com.microsoft.azure.management.network.ExpressRouteCircuitPeering peering =
                    resource.peeringsMap().get(ExpressRoutePeeringType.MICROSOFT_PEERING.toString())
                    .update()
                    .withVlanId(300)
                    .withPeerAsn(101)
                    .withSecondaryPeerAddressPrefix("123.0.0.8/30")
                    .apply();
            Assert.assertEquals(300, peering.vlanId());
            Assert.assertEquals(101, peering.peerAsn());
            Assert.assertEquals("123.0.0.8/30", peering.secondaryPeerAddressPrefix());
            return resource;
        }

        @Override
        public void print(ExpressRouteCircuit resource) {
            printExpressRouteCircuit(resource);
        }
    }

    private static void printExpressRouteCircuit(ExpressRouteCircuit resource) {
        StringBuilder info = new StringBuilder();
        info.append("Express Route Circuit: ").append(resource.id())
                .append("\n\tName: ").append(resource.name())
                .append("\n\tResource group: ").append(resource.resourceGroupName())
                .append("\n\tRegion: ").append(resource.regionName())
                .append("\n\tTags: ").append(resource.tags());
        System.out.println(info.toString());
    }
}


