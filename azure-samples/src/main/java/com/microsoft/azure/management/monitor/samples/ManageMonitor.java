/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor.samples;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.monitor.EventData;
import com.microsoft.azure.management.monitor.MetricCollection;
import com.microsoft.azure.management.monitor.MetricDefinition;
import com.microsoft.azure.management.monitor.ResultType;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.rest.LogLevel;
import org.joda.time.DateTime;
import java.io.File;
import java.util.List;

public final class ManageMonitor {

    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        final String resourceGroupName = Utils.createRandomName("rg");
        final String cdnProfileName = Utils.createRandomName("cdnStandardProfile");

        try {
            VirtualMachine vm = azure.virtualMachines().list().get(0);

            List<MetricDefinition> mt = azure.metricDefinitions().listByResource(vm.id());

            MetricCollection metrics = mt.get(0).defineQuery()
                    .withStartTime(DateTime.now().minusDays(30))
                    .withEndTime(DateTime.now())
                    .withResultType(ResultType.DATA)
                    .execute();

            PagedList<EventData> retval = azure.activityLogs()
                    .defineQuery()
                    .withStartTime(DateTime.now().minusDays(30))
                    .withEndTime(DateTime.now())
                    .defineResponseProperties()
                        .withEventTimestamp()
                        .withOperationName()
                        .withEventName()
                        .apply()
                    .filterByResource(vm.id())
                    .execute();

            for (EventData event : retval) {
                System.out.println("Operation: " + event.eventName().localizedValue() + " -> " + event.operationName().localizedValue());
            }
            return true;
        } catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        } finally {
            if (azure.resourceGroups().getByName(resourceGroupName) != null) {
                System.out.println("Deleting Resource Group: " + resourceGroupName);
                azure.resourceGroups().deleteByName(resourceGroupName);
                System.out.println("Deleted Resource Group: " + resourceGroupName);
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
                    .withLogLevel(LogLevel.BODY_AND_HEADERS)
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

    private ManageMonitor() {
    }
}
