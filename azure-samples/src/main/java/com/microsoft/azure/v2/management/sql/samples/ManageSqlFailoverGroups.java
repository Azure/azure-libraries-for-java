/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.sql.samples;

import com.microsoft.azure.v2.management.Azure;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.v2.management.samples.Utils;
import com.microsoft.azure.v2.management.sql.SampleName;
import com.microsoft.azure.v2.management.sql.SqlDatabase;
import com.microsoft.azure.v2.management.sql.SqlDatabaseStandardServiceObjective;
import com.microsoft.azure.v2.management.sql.SqlFailoverGroup;
import com.microsoft.azure.v2.management.sql.SqlServer;
import com.microsoft.rest.v2.policy.HttpLogDetailLevel;
import com.microsoft.rest.v2.policy.HttpLoggingPolicyFactory;

import java.io.File;

/**
 * Azure SQL sample for managing SQL Failover Groups
 *  - Create a primary SQL Server with a sample database and a secondary SQL Server.
 *  - Get a failover group from the primary SQL server to the secondary SQL server.
 *  - Update a failover group.
 *  - List all failover groups.
 *  - Delete a failover group.
 *  - Delete Sql Server
 */
public class ManageSqlFailoverGroups {
    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        final String sqlPrimaryServerName = Utils.createRandomName("sqlpri");
        final String sqlSecondaryServerName = Utils.createRandomName("sqlsec");
        final String rgName = Utils.createRandomName("rgsql");
        final String failoverGroupName = Utils.createRandomName("fog");
        final String dbName = "dbSample";
        final String administratorLogin = "sqladmin3423";
        // [SuppressMessage("Microsoft.Security", "CS002:SecretInNextLine", Justification="Serves as an example, not for deployment. Please change when using this in your code.")]
        final String administratorPassword = "myS3cureP@ssword";

        try {

            // ============================================================
            // Create a primary SQL Server with a sample database.
            System.out.println("Creating a primary SQL Server with a sample database");

            SqlServer sqlPrimaryServer = azure.sqlServers().define(sqlPrimaryServerName)
                .withRegion(Region.US_EAST)
                .withNewResourceGroup(rgName)
                .withAdministratorLogin(administratorLogin)
                .withAdministratorPassword(administratorPassword)
                .defineDatabase(dbName)
                    .fromSample(SampleName.ADVENTURE_WORKS_LT)
                    .withStandardEdition(SqlDatabaseStandardServiceObjective.S0)
                    .attach()
                .create();

            Utils.print(sqlPrimaryServer);

            // ============================================================
            // Create a secondary SQL Server with a sample database.
            System.out.println("Creating a secondary SQL Server with a sample database");

            SqlServer sqlSecondaryServer = azure.sqlServers().define(sqlSecondaryServerName)
                .withRegion(Region.US_WEST)
                .withExistingResourceGroup(rgName)
                .withAdministratorLogin(administratorLogin)
                .withAdministratorPassword(administratorPassword)
                .create();

            Utils.print(sqlSecondaryServer);


            // ============================================================
            // Create a Failover Group from the primary SQL server to the secondary SQL server.
            System.out.println("Creating a Failover Group from the primary SQL server to the secondary SQL server");

            SqlFailoverGroup failoverGroup = sqlPrimaryServer.failoverGroups().define(failoverGroupName)
                .withManualReadWriteEndpointPolicy()
                .withPartnerServerId(sqlSecondaryServer.id())
                .withReadOnlyEndpointPolicyDisabled()
                .create();

            Utils.print(failoverGroup);

            // ============================================================
            // Get the Failover Group from the secondary SQL server.
            System.out.println("Getting the Failover Group from the secondary SQL server");

            SqlFailoverGroup failoverGroupOnPartner = sqlSecondaryServer.failoverGroups().get(failoverGroup.name());

            Utils.print(failoverGroup);


            // ============================================================
            // Update the Failover Group Endpoint policies and tags.
            System.out.println("Updating the Failover Group Endpoint policies and tags");

            failoverGroup.update()
                .withAutomaticReadWriteEndpointPolicyAndDataLossGracePeriod(120)
                .withReadOnlyEndpointPolicyEnabled()
                .withTag("tag1", "value1")
                .apply();

            Utils.print(failoverGroup);


            // ============================================================
            // Update the Failover Group to add database and change read-write endpoint's failover policy.
            System.out.println("Updating the Failover Group to add database and change read-write endpoint's failover policy");

            SqlDatabase db = sqlPrimaryServer.databases().get(dbName);

            Utils.print(db);

            failoverGroup.update()
                .withManualReadWriteEndpointPolicy()
                .withReadOnlyEndpointPolicyDisabled()
                .withNewDatabaseId(db.id())
                .apply();

            Utils.print(failoverGroup);


            // ============================================================
            // List the Failover Group on the secondary server.
            System.out.println("Listing the Failover Group on the secondary server");

            for (SqlFailoverGroup item : sqlSecondaryServer.failoverGroups().list()) {
                Utils.print(item);
            }

            // ============================================================
            // Get the database from the secondary SQL server.
            System.out.println("Getting the database from the secondary server");
            SdkContext.sleep(3 * 60 * 1000);

            db = sqlSecondaryServer.databases().get(dbName);

            Utils.print(db);

            // ============================================================
            // Delete the Failover Group.
            System.out.println("Deleting the Failover Group");

            sqlPrimaryServer.failoverGroups().delete(failoverGroup.name());



            // Delete the SQL Servers.
            System.out.println("Deleting the Sql Servers");
            azure.sqlServers().deleteById(sqlPrimaryServer.id());
            azure.sqlServers().deleteById(sqlSecondaryServer.id());
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
