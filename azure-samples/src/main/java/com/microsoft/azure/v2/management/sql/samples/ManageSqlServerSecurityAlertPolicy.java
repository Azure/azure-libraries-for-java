/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.sql.samples;

import com.microsoft.azure.v2.management.Azure;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.v2.management.samples.Utils;
import com.microsoft.azure.v2.management.sql.SampleName;
import com.microsoft.azure.v2.management.sql.SecurityAlertPolicyState;
import com.microsoft.azure.v2.management.sql.SqlDatabaseStandardServiceObjective;
import com.microsoft.azure.v2.management.sql.SqlServer;
import com.microsoft.azure.v2.management.sql.SqlServerSecurityAlertPolicy;
import com.microsoft.azure.v2.management.storage.StorageAccount;
import com.microsoft.rest.v2.policy.HttpLogDetailLevel;
import com.microsoft.rest.v2.policy.HttpLoggingPolicyFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Azure SQL sample for managing SQL Server Security Alert Policy
 *  - Create a SQL Server.
 *  - Create an Azure Storage Account and get the storage account blob entry point
 *  - Create a Server Security Alert Policy
 *  - Get the Server Security Alert Policy.
 *  - Update the Server Security Alert Policy.
 *  - Delete the Sql Server
 */
public class ManageSqlServerSecurityAlertPolicy {
    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        final String sqlServerName = Utils.createRandomName("sql");
        final String storageAccountName = Utils.createRandomName("sqlsa");
        final String rgName = Utils.createRandomName("rgsql");
        final Region region = Region.US_EAST;
        final String dbName = "dbSample";
        final String administratorLogin = "sqladmin3423";
        // [SuppressMessage("Microsoft.Security", "CS002:SecretInNextLine", Justification="Serves as an example, not for deployment. Please change when using this in your code.")]
        final String administratorPassword = "myS3cureP@ssword";

        try {

            // ============================================================
            // Create a primary SQL Server with a sample database.
            System.out.println("Creating a primary SQL Server with a sample database");

            SqlServer sqlServer = azure.sqlServers().define(sqlServerName)
                .withRegion(region)
                .withNewResourceGroup(rgName)
                .withAdministratorLogin(administratorLogin)
                .withAdministratorPassword(administratorPassword)
                .defineDatabase(dbName)
                    .fromSample(SampleName.ADVENTURE_WORKS_LT)
                    .withStandardEdition(SqlDatabaseStandardServiceObjective.S0)
                    .attach()
                .create();

            Utils.print(sqlServer);

            // ============================================================
            // Create an Azure Storage Account and get the storage account blob entry point.
            System.out.println("Creating an Azure Storage Account and a storage account blob");
            StorageAccount storageAccount = azure.storageAccounts().define(storageAccountName)
                .withRegion(region)
                .withExistingResourceGroup(rgName)
                .create();
            String accountKey = storageAccount.getKeys().get(0).value();
            String blobEntrypoint = storageAccount.endPoints().primary().blob();

            // ============================================================
            // Create a Server Security Alert Policy.
            System.out.println("Creating a Server Security Alert Policy");
            sqlServer.serverSecurityAlertPolicies().define()
                .withState(SecurityAlertPolicyState.ENABLED)
                .withEmailAccountAdmins()
                .withStorageEndpoint(blobEntrypoint, accountKey)
                .withDisabledAlerts("Access_Anomaly", "Sql_Injection")
                .withRetentionDays(5)
                .create();


            // ============================================================
            // Get the Server Security Alert Policy.
            System.out.println("Getting the Server Security Alert Policy");
            SqlServerSecurityAlertPolicy sqlSecurityAlertPolicy = sqlServer.serverSecurityAlertPolicies().get();


            // ============================================================
            // Update the Server Security Alert Policy.
            System.out.println("Updating the Server Security Alert Policy");
            sqlSecurityAlertPolicy = sqlSecurityAlertPolicy.update()
                .withoutEmailAccountAdmins()
                .withEmailAddresses("testSecurityAlert@contoso.com")
                .withRetentionDays(1)
                .apply();


            // Delete the SQL Servers.
            System.out.println("Deleting the Sql Servers");
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
}
