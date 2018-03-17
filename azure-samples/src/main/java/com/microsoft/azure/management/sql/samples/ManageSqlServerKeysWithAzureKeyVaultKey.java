/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.sql.samples;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.AzureResponseBuilder;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.keyvault.webkey.JsonWebKeyOperation;
import com.microsoft.azure.keyvault.webkey.JsonWebKeyType;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.keyvault.Key;
import com.microsoft.azure.management.keyvault.KeyPermissions;
import com.microsoft.azure.management.keyvault.Vault;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.azure.management.sql.SqlServer;
import com.microsoft.azure.management.sql.SqlServerKey;
import com.microsoft.azure.serializer.AzureJacksonAdapter;
import com.microsoft.rest.LogLevel;
import com.microsoft.rest.RestClient;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Azure SQL sample for managing SQL secrets (Server Keys) using Azure Key Vault -
 *  - Create a SQL Server with "system assigned" managed service identity.
 *  - Create an Azure Key Vault with giving access to the SQL Server
 *  - Create, get, list and delete SQL Server Keys
 *  - Delete SQL Server
 */

public class ManageSqlServerKeysWithAzureKeyVaultKey {
    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @param objectId the object ID of the service principal/user used to authenticate to Azure
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure, String objectId) {
        final String sqlServerName = Utils.createRandomName("sqlsrv");
        final String rgName = Utils.createRandomName("rgsql");
        final String vaultName = Utils.createRandomName("sqlkv");
        final String keyName = Utils.createRandomName("sqlkey");
        final String administratorLogin = "sqladmin3423";
        final String administratorPassword = "myS3cureP@ssword";

        try {

            // ============================================================
            // Create a SQL Server with system assigned managed service identity.
            System.out.println("Creating a SQL Server with system assigned managed service identity");

            SqlServer sqlServer = azure.sqlServers().define(sqlServerName)
                .withRegion(Region.US_EAST)
                .withNewResourceGroup(rgName)
                .withAdministratorLogin(administratorLogin)
                .withAdministratorPassword(administratorPassword)
                .withSystemAssignedManagedServiceIdentity()
                .create();

            Utils.print(sqlServer);

            // ============================================================
            // Create an Azure Key Vault and set the access policies.
            System.out.println("Creating an Azure Key Vault and set the access policies");

            Vault vault = azure.vaults().define(vaultName)
                .withRegion(Region.US_EAST)
                .withExistingResourceGroup(rgName)
                .defineAccessPolicy()
                    .forObjectId(sqlServer.systemAssignedManagedServiceIdentityPrincipalId())
                    .allowKeyPermissions(KeyPermissions.WRAP_KEY, KeyPermissions.UNWRAP_KEY, KeyPermissions.GET, KeyPermissions.LIST)
                    .attach()
                .defineAccessPolicy()
                    .forServicePrincipal(objectId)
                    .allowKeyAllPermissions()
                    .attach()
                .create();

            SdkContext.sleep(3 * 60 * 1000);

            Key keyBundle = vault.keys().define(keyName)
                .withKeyTypeToCreate(JsonWebKeyType.RSA)
                .withKeyOperations(JsonWebKeyOperation.ALL_OPERATIONS)
                .create();

            // ============================================================
            // Create a SQL server key with Azure Key Vault key.
            System.out.println("Creating a SQL server key with Azure Key Vault key");

            String keyUri = keyBundle.jsonWebKey().kid();

            // Work around for SQL server key name must be formatted as "vault_key_version"
            String serverKeyName = String.format("%s_%s_%s", vaultName, keyName,
                keyUri.substring(keyUri.lastIndexOf("/") + 1));

            SqlServerKey sqlServerKey = sqlServer.serverKeys().define()
                .withAzureKeyVaultKey(keyUri)
                .create();

            Utils.print(sqlServerKey);


            // Validate key exists by getting key
            System.out.println("Validating key exists by getting the key");

            sqlServerKey = sqlServer.serverKeys().get(serverKeyName);

            Utils.print(sqlServerKey);


            // Validate key exists by listing keys
            System.out.println("Validating key exists by listing keys");

            List<SqlServerKey> serverKeys = sqlServer.serverKeys().list();
            for (SqlServerKey item : serverKeys) {
                Utils.print(item);
            }


            // Delete key
            System.out.println("Deleting the key");

            azure.sqlServers().serverKeys().deleteBySqlServer(rgName, sqlServerName, serverKeyName);


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

            runSample(azure, credentials.clientId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
