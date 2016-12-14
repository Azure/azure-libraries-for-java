/**
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 *
 */

package com.microsoft.azure.management.sql.samples;

import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.azure.management.sql.DatabaseEditions;
import com.microsoft.azure.management.sql.ServiceObjectiveName;
import com.microsoft.azure.management.sql.SqlDatabase;
import com.microsoft.azure.management.sql.SqlFirewallRule;
import com.microsoft.azure.management.sql.SqlServer;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.File;
import java.util.List;

/**
 * Azure Storage sample for managing SQL Database -
 *  - Create a SQL Server along with 2 firewalls.
 *  - Create a database in SQL server
 *  - Change performance level (SKU) of SQL Database
 *  - List and delete firewalls.
 *  - Create another firewall in the SQlServer
 *  - Delete database, firewall and SQL Server
 */

public final class ManageSqlDatabase {

    /**
     * Main entry point.
     * @param args the parameters
     */
    public static void main(String[] args) {

        final String sqlServerName = Utils.createRandomName("sqlserver");
        final String rgName = Utils.createRandomName("rgRSDSI");

        try {

            final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));

            Azure azure = Azure
                    .configure()
                    .withLogLevel(HttpLoggingInterceptor.Level.BASIC)
                    .authenticate(credFile)
                    .withDefaultSubscription();

            // Print selected subscription
            System.out.println("Selected subscription: " + azure.subscriptionId());

            try {

                // ============================================================
                // Create a SQL Server, with 2 firewall rules.

                SqlServer sqlServer = azure.sqlServers().define(sqlServerName)
                        .withRegion(Region.US_EAST)
                        .withNewResourceGroup(rgName)
                        .withAdministratorLogin("adminlogin123")
                        .withAdministratorPassword("myS3cureP@ssword")
                        .withNewFirewallRule("10.0.0.1")
                        .withNewFirewallRule("10.2.0.1", "10.2.0.10")
                        .create();

                Utils.print(sqlServer);

                // ============================================================
                // Create a Database in SQL server created above.
                System.out.println("Creating a database");

                SqlDatabase database = sqlServer.databases().define("myNewDatabase")
                        .withoutElasticPool()
                        .withoutSourceDatabaseId()
                        .withEdition(DatabaseEditions.BASIC)
                        .create();
                Utils.print(database);

                // ============================================================
                // Update the edition of database.
                System.out.println("Updating a database");
                database = database.update()
                        .withEdition(DatabaseEditions.PREMIUM)
                        .withServiceObjective(ServiceObjectiveName.P3)
                        .apply();
                Utils.print(database);

                // ============================================================
                // List and delete all firewall rules.
                List<SqlFirewallRule> firewallRules = sqlServer.firewallRules().list();
                for (SqlFirewallRule firewallRule: firewallRules) {
                    // Print information of the firewall rule.
                    Utils.print(firewallRule);

                    // Delete the firewall rule.
                    System.out.println("Deleting a firewall rule");
                    firewallRule.delete();
                }

                // ============================================================
                // Add new firewall rules.
                System.out.println("Creating a firewall rule for SQL Server");
                SqlFirewallRule firewallRule = sqlServer.firewallRules().define("myFirewallRule")
                        .withIpAddress("10.10.10.10")
                        .create();

                Utils.print(firewallRule);

                // Delete the database.
                System.out.println("Deleting a database");
                database.delete();


                // Delete the SQL Server.
                System.out.println("Deleting a Sql Server");
                azure.sqlServers().deleteById(sqlServer.id());

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
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private ManageSqlDatabase() {

    }


}