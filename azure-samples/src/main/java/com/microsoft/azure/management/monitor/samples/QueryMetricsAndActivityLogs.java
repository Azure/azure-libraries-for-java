/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor.samples;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.monitor.EventData;
import com.microsoft.azure.management.monitor.MetadataValue;
import com.microsoft.azure.management.monitor.Metric;
import com.microsoft.azure.management.monitor.MetricCollection;
import com.microsoft.azure.management.monitor.MetricDefinition;
import com.microsoft.azure.management.monitor.MetricValue;
import com.microsoft.azure.management.monitor.TimeSeriesElement;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.rest.LogLevel;
import org.joda.time.DateTime;
import org.joda.time.Period;
import java.io.File;

/**
 * This sample shows examples of retrieving metrics and activity logs for Storage Account.
 *  - List all metric definitions available for a storage account
 *  - Retrieve and show metrics for the past 7 days (query date is Jav 31, 2018) for Transactions where
 *    - Api name was 'GetBlob' and
 *    - response type was 'Success' and
 *    - Geo type was 'Primary'
 *  -  Retrieve and show all activity logs for the past 7 days (query date is Jav 31, 2018) for the same Storage account.
 */
public final class QueryMetricsAndActivityLogs {


    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        final String storageAccountName = Utils.createRandomName("saMonitor");
        final String rgName = Utils.createRandomName("rgMonitor");
        final DateTime recordDateTime = DateTime.parse("2018-01-31T00:01:20.000Z");

        try {

            // ============================================================
            // Create a storage account

            System.out.println("Creating a Storage Account");

            StorageAccount storageAccount = azure.storageAccounts().define(storageAccountName)
                    .withRegion(Region.US_EAST)
                    .withNewResourceGroup(rgName)
                    .create();

            System.out.println("Created a Storage Account:");
            Utils.print(storageAccount);

            // get metric definitions for storage account.
            for (MetricDefinition  metricDefinition : azure.metricDefinitions().listByResource(storageAccount.id())) {
                // find metric definition for Transactions
                if (metricDefinition.name().localizedValue().equalsIgnoreCase("transactions")) {
                    // get metric records
                    MetricCollection metrics = metricDefinition.defineQuery()
                            .startingFrom(recordDateTime.minusDays(7))
                            .endsBefore(recordDateTime)
                            .withAggregation("Average")
                            .withInterval(Period.minutes(5))
                            .withOdataFilter("apiName eq 'GetBlob' and responseType eq 'Success' and geoType eq 'Primary'")
                            .execute();

                    System.out.println("Metrics for '" + storageAccount.id() + "':");
                    System.out.println("Query time: " + metrics.timespan());
                    System.out.println("Time Grain: " + metrics.interval());
                    System.out.println("Cost: " + metrics.cost());

                    for (Metric metric : metrics.metrics()) {
                        System.out.println("\tMetric: " + metric.name().localizedValue());
                        System.out.println("\tType: " + metric.type());
                        System.out.println("\tUnit: " + metric.unit());
                        System.out.println("\tTime Series: ");
                        for (TimeSeriesElement timeElement : metric.timeseries()) {
                            System.out.println("\t\tMetadata: ");
                            for (MetadataValue metadata : timeElement.metadatavalues()) {
                                System.out.println("\t\t\t" + metadata.name().localizedValue() + ": " + metadata.value());
                            }
                            System.out.println("\t\tData: ");
                            for (MetricValue data : timeElement.data()) {
                                System.out.println("\t\t\t" + data.timeStamp()
                                        + " : (Min) " + data.minimum()
                                        + " : (Max) " + data.maximum()
                                        + " : (Avg) " + data.average()
                                        + " : (Total) " + data.total()
                                        + " : (Count) " + data.count());
                            }
                        }
                    }
                    break;
                }
            }

            // get activity logs for the same period.
            PagedList<EventData> logs = azure.activityLogs().defineQuery()
                    .startingFrom(recordDateTime.minusDays(7))
                    .endsBefore(recordDateTime)
                    .withAllPropertiesInResponse()
                    .filterByResource(storageAccount.id())
                    .execute();

            System.out.println("Activity logs for the Storage Account:");
            for (EventData event : logs) {
                System.out.println("\tEvent: " + event.eventName().localizedValue());
                System.out.println("\tOperation: " + event.operationName().localizedValue());
                System.out.println("\tCaller: " + event.caller());
                System.out.println("\tCorrelationId: " + event.correlationId());
                System.out.println("\tSubscriptionId: " + event.subscriptionId());
            }

            return true;
        } catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        } finally {
            if (azure.resourceGroups().getByName(rgName) != null) {
                System.out.println("Deleting Resource Group: " + rgName);
                azure.resourceGroups().deleteByName(rgName);
                System.out.println("Deleted Resource Group: " + rgName);
            } else {
                System.out.println("Did not create any resources in Azure. No clean up is necessary");
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

            final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));

            Azure azure = Azure.configure()
                    .withLogLevel(LogLevel.BASIC)
                    //.withProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 8888)))
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
