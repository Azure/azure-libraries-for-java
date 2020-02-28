/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.eventhub.samples;

import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.cosmosdb.CosmosDBAccount;
import com.microsoft.azure.management.cosmosdb.DatabaseAccountKind;
import com.microsoft.azure.management.eventhub.EventHubNamespace;
import com.microsoft.azure.management.eventhub.EventHubNamespaceAuthorizationRule;
import com.microsoft.azure.management.monitor.DiagnosticSetting;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.rest.LogLevel;
import org.joda.time.Period;

import java.io.File;

/**
 * Stream Azure Service Logs and Metrics for consumption through Event Hub.
 *   - Create a DocumentDB instance
 *   - Creates a Event Hub namespace and an Event Hub in it
 *   - Retrieve the root namespace authorization rule
 *   - Enable diagnostics on a existing cosmosDB to stream events to event hub
 */
public class ManageEventHubEvents {
    /**
     * Main function which runs the actual sample.
     *
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        final String storageAccountConnectionStringFormat = "DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net";
        final Region region = Region.US_EAST;
        final String rgName = Utils.createRandomName("rgEvHb");
        final String namespaceName = Utils.createRandomName("ns");
        final String storageAccountName = Utils.createRandomName("stg");
        final String eventHubName = "FirstEventHub";
        String diagnosticSettingId = null;

        try {
            //=============================================================
            // Creates a Cosmos DB.
            //
            CosmosDBAccount docDb = azure.cosmosDBAccounts()
                    .define(namespaceName)
                    .withRegion(region)
                    .withNewResourceGroup(rgName)
                    .withKind(DatabaseAccountKind.MONGO_DB)
                    .withEventualConsistency()
                    .withWriteReplication(Region.US_WEST)
                    .withReadReplication(Region.US_CENTRAL)
                    .create();

            System.out.println("Created a DocumentDb instance.");
            Utils.print(docDb);
            //=============================================================
            // Creates a Event Hub namespace and an Event Hub in it.
            //

            System.out.println("Creating event hub namespace and event hub");

            EventHubNamespace namespace = azure.eventHubNamespaces()
                    .define(namespaceName)
                    .withRegion(region)
                    .withExistingResourceGroup(rgName)
                    .withNewEventHub(eventHubName)
                    .create();

            System.out.println(String.format("Created event hub namespace %s and event hub %s ", namespace.name(), eventHubName));
            System.out.println();
            Utils.print(namespace);

            //=============================================================
            // Retrieve the root namespace authorization rule.
            //

            System.out.println("Retrieving the namespace authorization rule");

            EventHubNamespaceAuthorizationRule eventHubAuthRule = azure.eventHubNamespaces().authorizationRules()
                    .getByName(namespace.resourceGroupName(), namespace.name(), "RootManageSharedAccessKey");

            System.out.println("Namespace authorization rule Retrieved");

            //=============================================================
            // Enable diagnostics on a cosmosDB to stream events to event hub
            //

            System.out.println("Enabling diagnostics events of a cosmosdb to stream to event hub");

            // Store Id of created Diagnostic settings only for clean-up
            DiagnosticSetting ds  = azure.diagnosticSettings()
                    .define("DiaEventHub")
                        .withResource(docDb.id())
                        .withEventHub(eventHubAuthRule.id(), eventHubName)
                        .withLog("DataPlaneRequests", 0)
                        .withLog("MongoRequests", 0)
                        .withMetric("AllMetrics", Period.minutes(5), 0)
                    .create();

            Utils.print(ds);
            diagnosticSettingId = ds.id();

            System.out.println("Streaming of diagnostics events to event hub is enabled");

            //=============================================================
            // Listen for events from event hub using Event Hub dataplane APIs.

            return true;
        } catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        } finally {
            try {
                if (diagnosticSettingId != null) {
                    System.out.println("Deleting Diagnostic Setting: " + diagnosticSettingId);
                    azure.diagnosticSettings().deleteById(diagnosticSettingId);
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
     *
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
