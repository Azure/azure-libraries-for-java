/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network.samples;

import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.network.ExpressRouteCrossConnection;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.rest.LogLevel;

import java.io.File;
import java.util.List;

/**
 * Azure Network sample for managing express route cross connections.
 *  - List Express Route circuit
 *  - Create private peering
 *  - Create Microsoft peering
 */
public final class ManageExpressRouteCrossConnection {

    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        final String rgName = SdkContext.randomResourceName("rg", 20);
        final String connectionId = "/subscriptions/8030cec9-2c0c-4361-9949-1655c6e4b0fa/resourceGroups/CrossConnection-DenverTest/providers/Microsoft.Network/expressRouteCrossConnections/d7cf20fb-4050-45c3-aacf-9ea23a5b47f4";
        try {
            //============================================================
            // create Express Route Circuit
            List<ExpressRouteCrossConnection> connections = azure.expressRouteCrossConnections().list();

            ExpressRouteCrossConnection crossConnection = azure.expressRouteCrossConnections().getById(connectionId);

            azure.expressRouteCircuits()
                    .getById(crossConnection.expressRouteCircuit().id())
                    .update()
                    .apply();
            crossConnection.update()
                    .apply();

            crossConnection.peerings()
                    .defineAzurePrivatePeering()
                    .withPrimaryPeerAddressPrefix("10.0.0.0/30")
                    .withSecondaryPeerAddressPrefix("10.0.0.4/30")
                    .withVlanId(100)
                    .withPeerAsn(500)
                    .withSharedKey("A1B2C3D4")
                    .create();

//            crossConnection.peerings()
//                    .defineMicrosoftPeering()
//                    .withAdvertisedPublicPrefixes("123.1.0.0/24")
//                    .withPrimaryPeerAddressPrefix("10.0.0.0/30")
//                    .withSecondaryPeerAddressPrefix("10.0.0.4/30")
//                    .withVlanId(300)
//                    .withPeerAsn(500)
//                    .withSharedKey("A1B2C3D4")
//                    .create();
//

            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
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

    private ManageExpressRouteCrossConnection() {
    }
}
