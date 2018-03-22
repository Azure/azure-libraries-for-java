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
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.azure.management.sql.SampleName;
import com.microsoft.azure.management.sql.SqlDatabase;
import com.microsoft.azure.management.sql.SqlDatabaseImportExportResponse;
import com.microsoft.azure.management.sql.SqlServer;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.azure.serializer.AzureJacksonAdapter;
import com.microsoft.rest.LogLevel;
import com.microsoft.rest.RestClient;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Azure SQL sample for managing import/export SQL Database -
 *  - Create a SQL Server with one database from a pre-existing sample.
 *  - Create a storage account and export a database
 *  - Create a new database from a backup using the import functionality
 *  - Update an empty database with a backup database using the import functionality
 *  - Delete storage account, databases and SQL Server
 */
public final class ManageSqlImportExportDatabase {
    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        final String sqlServerName = Utils.createRandomName("sqlserver");
        final String rgName = Utils.createRandomName("rgsql");
        String storageName = SdkContext.randomResourceName(sqlServerName, 23);
        final String administratorLogin = "sqladmin3423";
        final String administratorPassword = "myS3cureP@ssword";
        final String dbFromSampleName = "db-from-sample";
        try {

            // ============================================================
            // Create a SQL Server with one database from a sample.
            SqlServer sqlServer = azure.sqlServers().define(sqlServerName)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(rgName)
                .withAdministratorLogin(administratorLogin)
                .withAdministratorPassword(administratorPassword)
                .defineDatabase(dbFromSampleName)
                    .fromSample(SampleName.ADVENTURE_WORKS_LT)
                    .withBasicEdition()
                    .attach()
                .create();
            Utils.print(sqlServer);

            SqlDatabase dbFromSample = sqlServer.databases()
                .get(dbFromSampleName);
            Utils.print(dbFromSample);

            // ============================================================
            // Export a database from a SQL server created above to a new storage account within the same resource group.
            System.out.println("Exporting a database from a SQL server created above to a new storage account within the same resource group.");

            SqlDatabaseImportExportResponse exportedDB;
            StorageAccount storageAccount = azure.storageAccounts().getByResourceGroup(sqlServer.resourceGroupName(), storageName);
            if (storageAccount == null) {
                Creatable<StorageAccount> storageAccountCreatable = azure.storageAccounts()
                    .define(storageName)
                    .withRegion(sqlServer.regionName())
                    .withExistingResourceGroup(sqlServer.resourceGroupName());

                exportedDB = dbFromSample.exportTo(storageAccountCreatable, "container-name", "dbfromsample.bacpac")
                    .withSqlAdministratorLoginAndPassword(administratorLogin, administratorPassword)
                    .execute();
                storageAccount = azure.storageAccounts().getByResourceGroup(sqlServer.resourceGroupName(), storageName);
            } else {
                exportedDB = dbFromSample.exportTo(storageAccount, "container-name", "dbfromsample.bacpac")
                    .withSqlAdministratorLoginAndPassword(administratorLogin, administratorPassword)
                    .execute();
            }

            // ============================================================
            // Import a database within a new elastic pool from a storage account container created above.
            System.out.println("Importing a database within a new elastic pool from a storage account container created above.");

            SqlDatabase dbFromImport = sqlServer.databases()
                .define("db-from-import1")
                    .defineElasticPool("epi")
                        .withStandardPool()
                        .attach()
                    .importFrom(storageAccount, "container-name", "dbfromsample.bacpac")
                        .withSqlAdministratorLoginAndPassword(administratorLogin, administratorPassword)
                    .create();
            Utils.print(dbFromImport);

            // Delete the database.
            System.out.println("Deleting a database");
            dbFromImport.delete();

            // ============================================================
            // Create an empty database within an elastic pool.
            SqlDatabase dbEmpty = sqlServer.databases()
                .define("db-from-import2")
                .withExistingElasticPool("epi")
                .create();

            // ============================================================
            // Import data from a BACPAC to an empty database within an elastic pool.
            System.out.println("Importing data from a BACPAC to an empty database within an elastic pool.");

            dbEmpty
                .importBacpac(storageAccount, "container-name", "dbfromsample.bacpac")
                .withSqlAdministratorLoginAndPassword(administratorLogin, administratorPassword)
                .execute();
            Utils.print(dbFromImport);

            // Delete the storage account.
            System.out.println("Deleting the storage account");
            azure.storageAccounts().deleteById(storageAccount.id());

            // Delete the databases.
            System.out.println("Deleting the databases");
            dbEmpty.delete();
            dbFromSample.delete();

            // Delete the elastic pool.
            System.out.println("Deleting the elastic pool");
            sqlServer.elasticPools().delete("epi");

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
