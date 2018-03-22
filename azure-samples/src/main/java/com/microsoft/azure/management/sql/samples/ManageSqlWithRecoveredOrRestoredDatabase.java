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
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.azure.management.sql.RestorePoint;
import com.microsoft.azure.management.sql.SampleName;
import com.microsoft.azure.management.sql.SqlDatabase;
import com.microsoft.azure.management.sql.SqlDatabaseStandardServiceObjective;
import com.microsoft.azure.management.sql.SqlRestorableDroppedDatabase;
import com.microsoft.azure.management.sql.SqlServer;
import com.microsoft.azure.serializer.AzureJacksonAdapter;
import com.microsoft.rest.LogLevel;
import com.microsoft.rest.RestClient;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Azure SQL sample for managing point in time restore and recover a deleted SQL Database -
 *  - Create a SQL Server with two database from a pre-existing sample.
 *  - Create a new database from a point in time restore
 *  - Delete a database then restore it from a recoverable dropped database automatic backup
 *  - Delete databases and SQL Server
 */
public final class ManageSqlWithRecoveredOrRestoredDatabase {
    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        final String sqlServerName = Utils.createRandomName("sqlserver");
        final String rgName = Utils.createRandomName("rgsql");
        final String administratorLogin = "sqladmin3423";
        final String administratorPassword = "myS3cureP@ssword";
        final String dbToDeleteName = "db-to-delete";
        final String dbToRestoreName = "db-to-restore";
        try {

            // ============================================================
            // Create a SQL Server with two databases from a sample.
            System.out.println("Creating a SQL Server with two databases from a sample.");
            SqlServer sqlServer = azure.sqlServers().define(sqlServerName)
                .withRegion(Region.US_WEST2)
                .withNewResourceGroup(rgName)
                .withAdministratorLogin(administratorLogin)
                .withAdministratorPassword(administratorPassword)
                .defineDatabase(dbToDeleteName)
                    .fromSample(SampleName.ADVENTURE_WORKS_LT)
                    .withStandardEdition(SqlDatabaseStandardServiceObjective.S0)
                    .attach()
                .defineDatabase(dbToRestoreName)
                    .fromSample(SampleName.ADVENTURE_WORKS_LT)
                    .withStandardEdition(SqlDatabaseStandardServiceObjective.S0)
                    .attach()
                .create();
            Utils.print(sqlServer);

            // Sleep for 5 minutes to allow for the service to be aware of the new server and databases
            SdkContext.sleep(5 * 60 * 1000);

            SqlDatabase dbToBeDeleted = sqlServer.databases()
                .get(dbToDeleteName);
            Utils.print(dbToBeDeleted);
            SqlDatabase dbToRestore = sqlServer.databases()
                .get(dbToRestoreName);
            Utils.print(dbToRestore);

            // ============================================================
            // Loop until a point in time restore is available.
            System.out.println("Loop until a point in time restore is available.");

            int retries = 50;
            while (retries > 0 && dbToRestore.listRestorePoints().size() == 0) {
                retries--;
                // Sleep for about 3 minutes
                SdkContext.sleep(3 * 60 * 1000);
            }
            if (retries == 0) {
                return false;
            }

            RestorePoint restorePointInTime = dbToRestore.listRestorePoints().get(0);
            // Restore point might not be ready right away and we will have to wait for it.
            DateTime currentTime = new DateTime(DateTimeZone.UTC);
            long waitForRestoreToBeReady = restorePointInTime.earliestRestoreDate().getMillis() - currentTime.getMillis() + 5 * 60 * 1000;
            if (waitForRestoreToBeReady > 0) {
                SdkContext.sleep((int) waitForRestoreToBeReady);
            }

            SqlDatabase dbRestorePointInTime = sqlServer.databases()
                .define("db-restore-pit")
                .fromRestorePoint(restorePointInTime)
                .create();
            Utils.print(dbRestorePointInTime);
            dbRestorePointInTime.delete();

            // ============================================================
            // Restore the database form a point in time restore which is 5 minutes ago.
            dbRestorePointInTime = sqlServer.databases()
                .define("db-restore-pit")
                .fromRestorePoint(restorePointInTime, new DateTime(DateTimeZone.UTC).minusMinutes(5))
                .create();
            Utils.print(dbRestorePointInTime);
            dbRestorePointInTime.delete();

            // ============================================================
            // Delete the database than loop until the restorable dropped database backup is available.
            System.out.println("Deleting the database than loop until the restorable dropped database backup is available.");

            dbToBeDeleted.delete();
            retries = 24;
            while (retries > 0 && sqlServer.listRestorableDroppedDatabases().size() == 0) {
                retries--;
                // Sleep for about 5 minutes
                SdkContext.sleep(5 * 60 * 1000);
            }
            SqlRestorableDroppedDatabase restorableDroppedDatabase = sqlServer.listRestorableDroppedDatabases().get(0);
            SqlDatabase dbRestoreDeleted = sqlServer.databases()
                .define("db-restore-deleted")
                .fromRestorableDroppedDatabase(restorableDroppedDatabase)
                .create();
            Utils.print(dbRestoreDeleted);

            // Delete databases
            dbToRestore.delete();
            dbRestoreDeleted.delete();

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
                .withReadTimeout(150, TimeUnit.SECONDS)
                .withLogLevel(LogLevel.BODY)
                .withResponseBuilderFactory(new AzureResponseBuilder.Factory())
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
