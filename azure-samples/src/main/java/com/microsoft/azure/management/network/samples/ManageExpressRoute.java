/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network.samples;

import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.network.ExpressRouteCircuit;
import com.microsoft.azure.management.network.ExpressRouteCircuitSkuType;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.rest.LogLevel;

import java.io.File;

/**
 * Azure Network sample for managing express route circuits.
 *  - Create Express Route circuit
 *  - Create Express Route circuit peering
 */
public final class ManageExpressRoute {

    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        final Region region = Region.US_NORTH_CENTRAL;
        final String rgName = SdkContext.randomResourceName("rg", 20);
        final String ercName = SdkContext.randomResourceName("erc", 20);

        try {
            //============================================================
            // create Express Route Circuit
            System.out.println("Creating express route circuit...");
            ExpressRouteCircuit erc = azure.expressRouteCircuits().define(ercName)
                    .withRegion(region)
                    .withNewResourceGroup(rgName)
                    .withServiceProvider("Microsoft ER Test")
                    .withPeeringLocation("Area51")
                    .withBandwidthInMbps(50)
                    .withSku(ExpressRouteCircuitSkuType.PREMIUM_METEREDDATA)
                    .create();
            System.out.println("Created express route circuit");

            //============================================================
            // Create Express Route circuit peering
            System.out.println("Creating express route circuit peering...");
            erc.peerings().defineAzurePrivatePeering()
                    .withPrimaryPeerAddressPrefix("123.0.0.0/30")
                    .withSecondaryPeerAddressPrefix("123.0.0.4/30")
                    .withVlanId(200)
                    .withPeerAsn(100)
                    .create();
            System.out.println("Created express route circuit peering");
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Deleting Resource Group: " + rgName);
                azure.resourceGroups().beginDeleteByName(rgName);
            } catch (NullPointerException npe) {
                System.out.println("Did not create any resources in Azure. No clean up is necessary");
            } catch (Exception g) {
                g.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Main entry point.
     * @param args the parameters
     */
    public static void main(String[] args) {
        try {
            //=============================================================
            // Authenticate
            final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));

            Azure azure = Azure.configure()
                    .withLogLevel(LogLevel.BODY)
                    .authenticate(credFile)
                    .withDefaultSubscription();

            // Print selected subscription
            System.out.println("Selected subscription: " + azure.subscriptionId());

            runSample(azure);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private ManageExpressRoute() {
    }
}
