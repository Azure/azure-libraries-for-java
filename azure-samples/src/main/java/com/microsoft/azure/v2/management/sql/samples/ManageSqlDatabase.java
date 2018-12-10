/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.sql.samples;

import com.microsoft.azure.v2.management.Azure;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.v2.management.samples.Utils;
import com.microsoft.azure.v2.management.sql.DatabaseEdition;
import com.microsoft.azure.v2.management.sql.DatabaseMetric;
import com.microsoft.azure.v2.management.sql.ServiceObjectiveName;
import com.microsoft.azure.v2.management.sql.SqlDatabase;
import com.microsoft.azure.v2.management.sql.SqlFirewallRule;
import com.microsoft.azure.v2.management.sql.SqlServer;
import com.microsoft.rest.v2.policy.HttpLogDetailLevel;
import com.microsoft.rest.v2.policy.HttpLoggingPolicyFactory;

import java.io.File;
import java.util.List;

/**
 * Azure SQL sample for managing SQL Database -
 *  - Create a SQL Server along with 2 firewalls.
 *  - Create a database in SQL server
 *  - Change performance level (SKU) of SQL Database
 *  - List and delete firewalls.
 *  - Create another firewall in the SQlServer
 *  - Delete database, firewall and SQL Server
 */

public final class ManageSqlDatabase {

    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        final String sqlServerName = Utils.createRandomName("sqlserver");
        final String rgName = Utils.createRandomName("rgRSDSI");
        final String administratorLogin = "sqladmin3423";
        // [SuppressMessage("Microsoft.Security", "CS002:SecretInNextLine", Justification="Serves as an example, not for deployment. Please change when using this in your code.")]
        final String administratorPassword = "myS3cureP@ssword";
        final String firewallRuleIPAddress = "10.0.0.1";
        final String firewallRuleStartIPAddress = "10.2.0.1";
        final String firewallRuleEndIPAddress = "10.2.0.10";
        final String databaseName = "mydatabase";
        try {

            // ============================================================
            // Create a SQL Server, with 2 firewall rules.
            SqlServer sqlServer = azure.sqlServers().define(sqlServerName)
                    .withRegion(Region.US_EAST)
                    .withNewResourceGroup(rgName)
                    .withAdministratorLogin(administratorLogin)
                    .withAdministratorPassword(administratorPassword)
                    .withNewFirewallRule(firewallRuleIPAddress)
                    .withNewFirewallRule(firewallRuleStartIPAddress, firewallRuleEndIPAddress)
                    .create();

            Utils.print(sqlServer);

            // ============================================================
            // Create a Database in SQL server created above.
            System.out.println("Creating a database");

            SqlDatabase database = sqlServer.databases()
                    .define(databaseName)
                    .create();
            Utils.print(database);

            // ============================================================
            // Update the edition of database.
            System.out.println("Updating a database");
            database = database.update()
                    .withEdition(DatabaseEdition.STANDARD)
                    .withServiceObjective(ServiceObjectiveName.S3)
                    .apply();
            Utils.print(database);

            // ============================================================
            // List and delete all firewall rules.
            System.out.println("Listing all firewall rules");

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
                    .withIPAddress("10.10.10.10")
                    .create();

            Utils.print(firewallRule);

            List<DatabaseMetric> usages = database.listUsages();

            // Delete the database.
            System.out.println("Deleting a database");
            database.delete();

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

            Azure azure = Azure
                    .configure()
                    .withRequestPolicy(new HttpLoggingPolicyFactory(HttpLogDetailLevel.BODY_AND_HEADERS))
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

    private ManageSqlDatabase() {

    }


}