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
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.azure.management.storage.AccessTier;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.azure.management.storage.StorageAccountKey;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.LoggingOperations;
import com.microsoft.azure.storage.LoggingProperties;
import com.microsoft.azure.storage.MetricsLevel;
import com.microsoft.azure.storage.MetricsProperties;
import com.microsoft.azure.storage.ServiceProperties;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.rest.LogLevel;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.EnumSet;
import java.util.List;

/**
 * This sample shows examples of retrieving metrics and activity logs for Storage Account.
 *  - List all metric definitions available for a storage account
 *  - Retrieve and show metrics for the past 7 days for Transactions where
 *    - Api name was 'PutBlob' and
 *    - response type was 'Success' and
 *    - Geo type was 'Primary'
 *  -  Retrieve and show all activity logs for the past 7 days for the same Storage account.
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

        try {
            // ============================================================
            // Create a storage account

            System.out.println("Creating a Storage Account");

            StorageAccount storageAccount = azure.storageAccounts().define(storageAccountName)
                    .withRegion(Region.US_EAST)
                    .withNewResourceGroup(rgName)
                    .withBlobStorageAccountKind()
                    .withAccessTier(AccessTier.COOL)
                    .create();

            System.out.println("Created a Storage Account:");
            Utils.print(storageAccount);

            List<StorageAccountKey> storageAccountKeys = storageAccount.getKeys();
            final String storageConnectionString = String.format("DefaultEndpointsProtocol=http;AccountName=%s;AccountKey=%s",
                    storageAccount.name(),
                    storageAccountKeys.get(0).value());

            // Add some blob transaction events
            addBlobTransactions(storageConnectionString);

            DateTime recordDateTime = DateTime.now();
            // get metric definitions for storage account.
            for (MetricDefinition  metricDefinition : azure.metricDefinitions().listByResource(storageAccount.id())) {
                // find metric definition for Transactions
                if (metricDefinition.name().localizedValue().equalsIgnoreCase("transactions")) {
                    // get metric records
                    MetricCollection metricCollection = metricDefinition.defineQuery()
                            .startingFrom(recordDateTime.minusDays(7))
                            .endsBefore(recordDateTime)
                            .withAggregation("Average")
                            .withInterval(Period.minutes(5))
                            .withOdataFilter("apiName eq 'PutBlob' and responseType eq 'Success' and geoType eq 'Primary'")
                            .execute();

                    System.out.println("Metrics for '" + storageAccount.id() + "':");
                    System.out.println("Namespacse: " + metricCollection.namespace());
                    System.out.println("Query time: " + metricCollection.timespan());
                    System.out.println("Time Grain: " + metricCollection.interval());
                    System.out.println("Cost: " + metricCollection.cost());

                    for (Metric metric : metricCollection.metrics()) {
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
                if (event.eventName() != null) {
                    System.out.println("\tEvent: " + event.eventName().localizedValue());
                }
                if (event.operationName() != null) {
                    System.out.println("\tOperation: " + event.operationName().localizedValue());
                }
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
                    .withLogLevel(LogLevel.NONE)
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

    private static void addBlobTransactions(String storageConnectionString) throws IOException, URISyntaxException, InvalidKeyException, StorageException {
        // Get the script to upload
        //
        InputStream scriptFileAsStream = QueryMetricsAndActivityLogs
                .class
                .getResourceAsStream("/install_apache.sh");

        // Get the size of the stream
        //
        int fileSize;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[256];
        int bytesRead;
        while ((bytesRead = scriptFileAsStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        fileSize = outputStream.size();
        outputStream.close();

        // Upload the script file as block blob
        //
        CloudStorageAccount account = CloudStorageAccount.parse(storageConnectionString);
        CloudBlobClient cloudBlobClient = account.createCloudBlobClient();
        CloudBlobContainer container = cloudBlobClient.getContainerReference("scripts");
        container.createIfNotExists();

        ServiceProperties serviceProps = cloudBlobClient.downloadServiceProperties();

        // configure Storage logging and metrics
        LoggingProperties logProps = new LoggingProperties();
        logProps.setLogOperationTypes(EnumSet.of(LoggingOperations.READ, LoggingOperations.WRITE));
        logProps.setRetentionIntervalInDays(2);
        logProps.setVersion("1.0");
        serviceProps.setLogging(logProps);

        MetricsProperties metricProps = new MetricsProperties();
        metricProps.setMetricsLevel(MetricsLevel.SERVICE_AND_API);
        metricProps.setRetentionIntervalInDays(2);
        metricProps.setVersion("1.0");
        serviceProps.setHourMetrics(metricProps);
        serviceProps.setMinuteMetrics(metricProps);

        // Set the default service version to be used for anonymous requests.
        serviceProps.setDefaultServiceVersion("2015-04-05");

        // Set the service properties.
        cloudBlobClient.uploadServiceProperties(serviceProps);

        CloudBlockBlob blob = container.getBlockBlobReference("install_apache.sh");
        blob.upload(scriptFileAsStream, fileSize);

        // give sometime for the infrastructure to process the records and fit into time grain.
        SdkContext.sleep(6 * 60000);
    }
}
