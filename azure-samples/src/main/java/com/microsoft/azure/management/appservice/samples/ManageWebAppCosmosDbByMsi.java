/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.samples;

import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.keyvault.KeyVaultClient;
import com.microsoft.azure.keyvault.authentication.KeyVaultCredentials;
import com.microsoft.azure.keyvault.requests.SetSecretRequest;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.appservice.JavaVersion;
import com.microsoft.azure.management.appservice.PricingTier;
import com.microsoft.azure.management.appservice.WebApp;
import com.microsoft.azure.management.appservice.WebContainer;
import com.microsoft.azure.management.cosmosdb.CosmosDBAccount;
import com.microsoft.azure.management.cosmosdb.DatabaseAccountKind;
import com.microsoft.azure.management.keyvault.Vault;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.rest.LogLevel;

import java.io.File;
import java.io.IOException;

import static com.microsoft.azure.management.samples.Utils.curl;

/**
 * Azure App Service basic sample for managing web apps.
 *  - Create a Cosmos DB with credentials stored in a Key Vault
 *  - Create a web app which interacts with the Cosmos DB by first
 *      reading the secrets from the Key Vault.
 *
 *      The source code of the web app is located at
 *      https://github.com/Microsoft/todo-app-java-on-azure/tree/keyvault-secrets
 */
public final class ManageWebAppCosmosDbByMsi {

    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        // New resources
        final Region region         = Region.US_WEST;
        final String appName        = SdkContext.randomResourceName("webapp1-", 20);
        final String rgName         = SdkContext.randomResourceName("rg1NEMV_", 24);
        final String vaultName      = SdkContext.randomResourceName("vault", 20);
        final String cosmosName     = SdkContext.randomResourceName("cosmosdb", 20);
        final String appUrl         = appName + ".azurewebsites.net";

        try {
            //============================================================
            // Create a CosmosDB

            System.out.println("Creating a CosmosDB...");
            CosmosDBAccount cosmosDBAccount = azure.cosmosDBAccounts().define(cosmosName)
                    .withRegion(region)
                    .withNewResourceGroup(rgName)
                    .withKind(DatabaseAccountKind.GLOBAL_DOCUMENT_DB)
                    .withEventualConsistency()
                    .withWriteReplication(Region.US_EAST)
                    .withReadReplication(Region.US_CENTRAL)
                    .create();

            System.out.println("Created CosmosDB");
            Utils.print(cosmosDBAccount);

            //============================================================
            // Create a key vault

            final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));
            final ApplicationTokenCredentials credentials = ApplicationTokenCredentials.fromFile(credFile);

            Vault vault = azure.vaults()
                    .define(vaultName)
                    .withRegion(region)
                    .withExistingResourceGroup(rgName)
                    .defineAccessPolicy()
                        .forServicePrincipal(credentials.clientId())
                        .allowSecretAllPermissions()
                        .attach()
                    .create();

            SdkContext.sleep(10000);

            KeyVaultClient client = new KeyVaultClient(new KeyVaultCredentials() {
                @Override
                public String doAuthenticate(String authorization, String resource, String scope) {
                    try {
                        return credentials.getToken(resource);
                    } catch (IOException e) {
                        return null;
                    }
                }
            });

            //============================================================
            // Store Cosmos DB credentials in Key Vault

            client.setSecret(new SetSecretRequest.Builder(vault.vaultUri(), "azure-documentdb-uri", cosmosDBAccount.documentEndpoint()).build());
            client.setSecret(new SetSecretRequest.Builder(vault.vaultUri(), "azure-documentdb-key", cosmosDBAccount.listKeys().primaryMasterKey()).build());
            client.setSecret(new SetSecretRequest.Builder(vault.vaultUri(), "azure-documentdb-database", "tododb").build());

            //============================================================
            // Create a web app with a new app service plan

            System.out.println("Creating web app " + appName + " in resource group " + rgName + "...");

            WebApp app = azure.webApps()
                    .define(appName)
                    .withRegion(Region.US_WEST)
                    .withNewResourceGroup(rgName)
                    .withNewWindowsPlan(PricingTier.STANDARD_S1)
                    .withJavaVersion(JavaVersion.JAVA_8_NEWEST)
                    .withWebContainer(WebContainer.TOMCAT_8_5_NEWEST)
                    .withAppSetting("AZURE_KEYVAULT_URI", vault.vaultUri())
                    .withSystemAssignedManagedServiceIdentity()
                    .create();

            System.out.println("Created web app " + app.name());
            Utils.print(app);

            //============================================================
            // Update vault to allow the web app to access

            vault.update()
                    .defineAccessPolicy()
                        .forObjectId(app.systemAssignedManagedServiceIdentityPrincipalId())
                        .allowSecretAllPermissions()
                        .attach()
                    .apply();

            //============================================================
            // Deploy to app through FTP

            System.out.println("Deploying a spring boot app to " + appName + " through FTP...");

            Utils.uploadFileToWebAppWwwRoot(app.getPublishingProfile(), "ROOT.jar", ManageWebAppCosmosDbByMsi.class.getResourceAsStream("/todo-app-java-on-azure-1.0-SNAPSHOT.jar"));
            Utils.uploadFileToWebAppWwwRoot(app.getPublishingProfile(), "web.config", ManageWebAppCosmosDbByMsi.class.getResourceAsStream("/web.config"));

            System.out.println("Deployment to web app " + app.name() + " completed");
            Utils.print(app);

            // warm up
            System.out.println("Warming up " + appUrl + "...");
            curl("http://" + appUrl);
            SdkContext.sleep(10000);
            System.out.println("CURLing " + appUrl);
            System.out.println(curl("http://" + appUrl));


            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Deleting Resource Group: " + rgName);
                azure.resourceGroups().beginDeleteByName(rgName);
                System.out.println("Deleted Resource Group: " + rgName);
            } catch (NullPointerException npe) {
                System.out.println("Did not create any resources in Azure. No clean up is necessary");
            } catch (Exception g) {
                g.printStackTrace();
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

            //=============================================================
            // Authenticate

            final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));

            Azure azure = Azure
                    .configure()
                    .withLogLevel(LogLevel.BODY_AND_HEADERS)
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
