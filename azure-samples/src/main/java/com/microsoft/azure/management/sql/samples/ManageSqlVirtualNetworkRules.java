/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.samples;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.AzureResponseBuilder;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.network.Network;
import com.microsoft.azure.management.network.ServiceEndpointType;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.azure.management.sql.SqlServer;
import com.microsoft.azure.management.sql.SqlVirtualNetworkRule;
import com.microsoft.azure.serializer.AzureJacksonAdapter;
import com.microsoft.rest.LogLevel;
import com.microsoft.rest.RestClient;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Azure SQL sample for managing SQL Virtual Network Rules
 *  - Create a Virtual Network with two subnets.
 *  - Create a SQL Server along with one virtual network rule.
 *  - Add another virtual network rule in the SQL Server
 *  - Get a virtual network rule.
 *  - Update a virtual network rule.
 *  - List all virtual network rules.
 *  - Delete a virtual network.
 *  - Delete Sql Server
 */

public class ManageSqlVirtualNetworkRules {
    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        final String sqlServerName = Utils.createRandomName("sqlserver");
        final String rgName = Utils.createRandomName("rgRSSDFW");
        final String administratorLogin = "sqladmin3423";
        final String administratorPassword = "myS3cureP@ssword";
        final String vnetName = SdkContext.randomResourceName("vnetsql", 20);

        try {

            // ============================================================
            // Create a virtual network with two subnets.
            System.out.println("Create a virtual network with two subnets: subnet1 and subnet2");

            Network virtualNetwork = azure.networks().define(vnetName)
                .withRegion(Region.US_EAST)
                .withNewResourceGroup(rgName)
                .withAddressSpace("192.168.0.0/16")
                .defineSubnet("subnet1")
                    .withAddressPrefix("192.168.1.0/24")
                    .withAccessFromService(ServiceEndpointType.MICROSOFT_SQL)
                    .attach()
                .withSubnet("subnet2", "192.168.2.0/24")
                .create();

            System.out.println("Created a virtual network");
            // Print the virtual network details
            Utils.print(virtualNetwork);

            // ============================================================
            // Create a SQL Server, with one virtual network rule.
            System.out.println("Create a SQL server with one virtual network rule");

            SqlServer sqlServer = azure.sqlServers().define(sqlServerName)
                .withRegion(Region.US_EAST)
                .withExistingResourceGroup(rgName)
                .withAdministratorLogin(administratorLogin)
                .withAdministratorPassword(administratorPassword)
                .withoutAccessFromAzureServices()
                .defineVirtualNetworkRule("virtualNetworkRule1")
                    .withSubnet(virtualNetwork.id(), "subnet1")
                    .attach()
                .create();

            Utils.print(sqlServer);


            // ============================================================
            // Get the virtual network rule created above.
            SqlVirtualNetworkRule virtualNetworkRule = azure.sqlServers().virtualNetworkRules()
                .getBySqlServer(rgName, sqlServerName, "virtualNetworkRule1");

            Utils.print(virtualNetworkRule);


            // ============================================================
            // Add new virtual network rules.
            System.out.println("adding another virtual network rule in existing SQL Server");
            virtualNetworkRule = sqlServer.virtualNetworkRules()
                .define("virtualNetworkRule2")
                .withSubnet(virtualNetwork.id(), "subnet2")
                .ignoreMissingSqlServiceEndpoint()
                .create();

            Utils.print(virtualNetworkRule);


            // ============================================================
            // Update a virtual network rules.
            System.out.println("Updating an existing virtual network rules in SQL Server.");
            virtualNetworkRule.update()
                .withSubnet(virtualNetwork.id(), "subnet1")
                .apply();

            Utils.print(virtualNetworkRule);


            // ============================================================
            // List and delete all virtual network rules.
            System.out.println("Listing all virtual network rules in SQL Server.");

            List<SqlVirtualNetworkRule> virtualNetworkRules = sqlServer.virtualNetworkRules().list();
            for (SqlVirtualNetworkRule vnetRule : virtualNetworkRules) {
                // Delete the virtual network rule.
                System.out.println("Deleting a virtual network rule");
                vnetRule.delete();
            }


            // Delete the SQL Server.
            System.out.println("Deleting a Sql Server");
            azure.sqlServers().deleteById(sqlServer.id());
            return true;
        } catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        } finally {
            try {
                System.out.println("Deleting Resource Group: " + rgName);
                azure.resourceGroups().deleteByName(rgName);
                System.out.println("Deleted Resource Group: " + rgName);
            }
            catch (Exception e) {
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


            ApplicationTokenCredentials credentials = ApplicationTokenCredentials.fromFile(credFile);
            RestClient restClient = new RestClient.Builder()
                .withBaseUrl(AzureEnvironment.AZURE, AzureEnvironment.Endpoint.RESOURCE_MANAGER)
                .withSerializerAdapter(new AzureJacksonAdapter())
                .withResponseBuilderFactory(new AzureResponseBuilder.Factory())
                .withReadTimeout(150, TimeUnit.SECONDS)
                .withLogLevel(LogLevel.BODY)
                .withCredentials(credentials).build();
            Azure azure = Azure.authenticate(restClient, credentials.domain(), credentials.defaultSubscriptionId()).withDefaultSubscription();

            // Print selected subscription
            System.out.println("Selected subscription: " + azure.subscriptionId());

            runSample(azure);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
