/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.network.samples;

import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.network.Network;
import com.microsoft.azure.management.privatedns.v2018_09_01.implementation.privatednsManager;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.rest.LogLevel;

import java.io.File;

public class ManagePrivateDns {
    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @param prDnsManager instance of PrivateDns client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure, privatednsManager prDnsManager) {
        final Region region = Region.US_EAST;
        final String rgName = SdkContext.randomResourceName("rg", 24);
        final String nwName = SdkContext.randomResourceName("nw", 24);

        try {
            Network network = azure.networks().define(nwName)
                    .withRegion(Region.US_EAST)
                    .withNewResourceGroup(rgName)
                    .withAddressSpace("10.0.0.0/27")
                    .withSubnet("subnet1", "10.0.0.0/27")
                    .create();
            //
            // Private DNS hybrid sample
            //
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
            final ApplicationTokenCredentials cred = ApplicationTokenCredentials.fromFile(credFile);
            //
            Azure azure = Azure.configure()
                    .withLogLevel(LogLevel.BODY)
                    .authenticate(credFile)
                    .withDefaultSubscription();
            privatednsManager prDnsManager = privatednsManager.authenticate(cred, cred.defaultSubscriptionId());
            // Print selected subscription
            //
            System.out.println("Selected subscription: " + azure.subscriptionId());
            //
            runSample(azure, prDnsManager);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private ManagePrivateDns() {
    }
}
