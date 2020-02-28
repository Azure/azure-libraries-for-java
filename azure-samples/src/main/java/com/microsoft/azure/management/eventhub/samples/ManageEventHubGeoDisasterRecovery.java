/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.eventhub.samples;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.eventhub.EventHubDisasterRecoveryPairing;
import com.microsoft.azure.management.eventhub.EventHubNamespace;
import com.microsoft.azure.management.eventhub.ProvisioningStateDR;
import com.microsoft.azure.management.eventhub.EventHub;
import com.microsoft.azure.management.eventhub.DisasterRecoveryPairingAuthorizationRule;
import com.microsoft.azure.management.eventhub.DisasterRecoveryPairingAuthorizationKey;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.rest.LogLevel;

import java.io.File;

/**
 * Azure Event Hub sample for managing geo disaster recovery pairing -
 *   - Create two event hub namespaces
 *   - Create a pairing between two namespaces
 *   - Create an event hub in the primary namespace and retrieve it from the secondary namespace
 *   - Retrieve the pairing connection string
 *   - Fail over so that secondary namespace become primary.
 */
public class ManageEventHubGeoDisasterRecovery {
    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        final String rgName = SdkContext.randomResourceName("rgNEMV_", 24);
        final String primaryNamespaceName = SdkContext.randomResourceName("ns", 14);
        final String secondaryNamespaceName = SdkContext.randomResourceName("ns", 14);
        final String geoDRName = SdkContext.randomResourceName("geodr", 14);
        final String eventHubName = SdkContext.randomResourceName("eh", 14);
        boolean isFailOverSucceeded = false;
        EventHubDisasterRecoveryPairing pairing = null;

        try {

            //============================================================
            // Create resource group for the namespaces and recovery pairings
            //
            ResourceGroup resourceGroup = azure.resourceGroups().define(rgName)
                    .withRegion(Region.US_SOUTH_CENTRAL)
                    .create();

            System.out.println("Creating primary event hub namespace " + primaryNamespaceName);

            EventHubNamespace primaryNamespace = azure.eventHubNamespaces()
                    .define(primaryNamespaceName)
                    .withRegion(Region.US_SOUTH_CENTRAL)
                    .withExistingResourceGroup(resourceGroup)
                    .create();

            System.out.println("Primary event hub namespace created");
            Utils.print(primaryNamespace);

            System.out.println("Creating secondary event hub namespace " + primaryNamespaceName);

            EventHubNamespace secondaryNamespace = azure.eventHubNamespaces()
                    .define(secondaryNamespaceName)
                    .withRegion(Region.US_NORTH_CENTRAL)
                    .withExistingResourceGroup(resourceGroup)
                    .create();

            System.out.println("Secondary event hub namespace created");
            Utils.print(secondaryNamespace);

            //============================================================
            // Create primary and secondary namespaces and recovery pairing
            //
            System.out.println("Creating geo-disaster recovery pairing " + geoDRName);

            pairing = azure.eventHubDisasterRecoveryPairings()
                    .define(geoDRName)
                    .withExistingPrimaryNamespace(primaryNamespace)
                    .withExistingSecondaryNamespace(secondaryNamespace)
                    .create();

            while (pairing.provisioningState() != ProvisioningStateDR.SUCCEEDED) {
                pairing = pairing.refresh();
                SdkContext.sleep(15 * 1000);
                if (pairing.provisioningState() == ProvisioningStateDR.FAILED) {
                    throw new Exception("Provisioning state of the pairing is FAILED");
                }
            }

            System.out.println("Created geo-disaster recovery pairing " + geoDRName);
            Utils.print(pairing);

            //============================================================
            // Create an event hub and consumer group in primary namespace
            //

            System.out.println("Creating an event hub and consumer group in primary namespace");

            EventHub eventHubInPrimaryNamespace = azure.eventHubs()
                    .define(eventHubName)
                    .withExistingNamespace(primaryNamespace)
                    .withNewConsumerGroup("consumerGrp1")
                    .create();

            System.out.println("Created event hub and consumer group in primary namespace");
            Utils.print(eventHubInPrimaryNamespace);

            System.out.println("Waiting for 60 seconds to allow metadata to sync across primary and secondary");
            SdkContext.sleep(60 * 1000);    // Wait for syncing to finish

            System.out.println("Retrieving the event hubs in secondary namespace");

           EventHub eventHubInSecondaryNamespace = azure.eventHubs().getByName(rgName, secondaryNamespaceName, eventHubName);

            System.out.println("Retrieved the event hubs in secondary namespace");
            Utils.print(eventHubInSecondaryNamespace);

            //============================================================
            // Retrieving the connection string
            //
            PagedList<DisasterRecoveryPairingAuthorizationRule> rules = pairing.listAuthorizationRules();
            for (DisasterRecoveryPairingAuthorizationRule rule : rules) {
               DisasterRecoveryPairingAuthorizationKey key = rule.getKeys();
                Utils.print(key);
            }

            System.out.println("Initiating fail over");

            pairing.failOver();
            isFailOverSucceeded = true;

            System.out.println("Fail over initiated");
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                try {
                    // It is necessary to break pairing before deleting resource group
                    //
                    if (pairing != null && !isFailOverSucceeded) {
                        pairing.breakPairing();
                    }
                } catch (Exception ex) {
                    System.out.println("Pairing breaking failed:" + ex.getMessage());
                }

                System.out.println("Deleting Resource Group: " + rgName);
                azure.resourceGroups().beginDeleteByName(rgName);
                System.out.println("Deleted Resource Group: " + rgName);
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
                    .withLogLevel(LogLevel.BASIC)
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
}
