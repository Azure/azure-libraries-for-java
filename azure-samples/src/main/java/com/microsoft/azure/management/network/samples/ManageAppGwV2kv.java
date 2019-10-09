/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.network.samples;

import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.network.ApplicationGateway;
import com.microsoft.azure.management.network.ApplicationGatewaySkuName;
import com.microsoft.azure.management.network.ApplicationGatewayTier;
import com.microsoft.azure.management.network.Network;
import com.microsoft.azure.management.network.Subnet;
import com.microsoft.azure.management.network.PublicIPAddress;
import com.microsoft.azure.management.network.PublicIPSkuType;
import com.microsoft.azure.management.network.ManagedServiceIdentity;
import com.microsoft.azure.management.network.ResourceIdentityType;
import com.microsoft.azure.management.network.ManagedServiceIdentityUserAssignedIdentitiesValue;
import com.microsoft.azure.management.keyvault.Vault;
import com.microsoft.azure.management.keyvault.Secret;
import com.microsoft.azure.management.keyvault.implementation.KeyVaultManager;
import com.microsoft.azure.management.msi.Identity;
import com.microsoft.azure.management.msi.implementation.MSIManager;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.SubResource;
import com.microsoft.rest.LogLevel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import com.google.gson.JsonObject;
import com.google.common.io.Files;
import java.nio.charset.Charset;

/**
 * Azure Network sample for managing private DNS -
 *  - Create a private DNS zone
 *  - Update a virtual network.
 * */

public final class ManageAppGwV2kv {
    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */

    final static Region region = Region.US_WEST2;
    final static String rgName = SdkContext.randomResourceName("rg", 24);
    final static String nwName = SdkContext.randomResourceName("nw", 24);
    final static String identityName = SdkContext.randomResourceName("id", 10);
    final static String sbName = "subnetappgw";
    final static String pipName = "pipAppGW";
    final strauc String certificateName = "test.certificate";

    public static boolean runSample(Azure azure, ApplicationTokenCredentials cred) {
        try {
            ManageAppGwV2kv appgw = ManageAppGwV2kv();            
            Network network = azure.networks().define(nwName)
                    .withRegion(region)
                    .withNewResourceGroup(rgName)
                    .withAddressSpace("10.0.0.0/27")
                    .withSubnet(sbName, "10.0.0.0/27")
                    .create();
            PublicIPAddress pipAppGw = azure.publicIPAddresses().define(pipName)
                    .withRegion(region)
                    .withExistingResourceGroup(rgName)
                    .withSku(PublicIPSkuType.STANDARD)
                    .create();
            MSIManager msiManager = MSIManager
                    .authenticate(cred, cred.defaultSubscriptionId());
            KeyVaultManager keyVaultManager = KeyVaultManager.authenticate(cred, cred.defaultSubscriptionId());
            Identity identity = msiManager.identities()
                   .define(identityName)
                   .withRegion(region)
                   .withExistingResourceGroup(rgName)
                   .create();
            KeyVaultManager keyVaultManager = KeyVaultManager.authenticate(cred, cred.defaultSubscriptionId());
            Secret secret1 = appgw.createKeyVaultSecret(cred.clientId(), identity.principalId(), keyVaultManager, rgName);
            ApplicationGateway gw1 = azure.applicationGateways().define(appGwName)
                    .withRegion(region)
                    .withExistingResourceGroup(rgName)
                    .defineRequestRoutingRule("rule1")
                        .fromPublicFrontend()
                        .fromFrontendHttpsPort(443)
                        .withSslCertificate("ssl1")
                        .toBackendHttpPort(80)
                        .toBackendIPAddress("192.168.22.4")
                        .toBackendIPAddress("192.168.22.5")
                        .attach()
                    .withIdentity(serviceIdentity)
                    .defineSslCertificate("ssl1")
                        .withKeyVaultSecretId(secret1.id())
                        .attach()
                    .withExistingSubnet(subnetVnet)
                    .withSize(ApplicationGatewaySkuName.STANDARD_V2)
                    .withTier(ApplicationGatewayTier.STANDARD_V2)                 
                    .withExistingPublicIPAddress(pipAppGw)
                    .create();            
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Deleting Resource Group: " + rgName);
                azure.resourceGroups().beginDeleteByName(rgName);
            } catch (NullPointerException npe) {
                System.out.println("Did not create any resources in Azure. No clean up is necessary");
            } catch (Exception g) {
                g.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            //=============================================================
            // Authenticate                      

            final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));
            final ApplicationTokenCredentials cred = ApplicationTokenCredentials.fromFile(credFile);
            
            Azure azure = Azure.configure()
                    .withLogLevel(LogLevel.BODY)
                    .authenticate(credFile)
                    .withDefaultSubscription();            

            runSample(azure, cred);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private ManageAppGwV2kv() {
    }

    private static ManagedServiceIdentity createManagedServiceIdentityFromIdentity(Identity identity) throws Exception{
        JsonObject userAssignedIdentitiesValueObject = new JsonObject();
        userAssignedIdentitiesValueObject.addProperty("principalId", identity.principalId());
        userAssignedIdentitiesValueObject.addProperty("clientId", identity.clientId());
        ManagedServiceIdentityUserAssignedIdentitiesValue userAssignedIdentitiesValue =
            new JacksonAdapter().deserialize(userAssignedIdentitiesValueObject.toString(), 
                ManagedServiceIdentityUserAssignedIdentitiesValue.class);
        Map<String, ManagedServiceIdentityUserAssignedIdentitiesValue> userAssignedIdentities = 
            new HashMap<String,ManagedServiceIdentityUserAssignedIdentitiesValue>();
        userAssignedIdentities.put(identity.id(), userAssignedIdentitiesValue);
        ManagedServiceIdentity serviceIdentity = new ManagedServiceIdentity();
        serviceIdentity.withType(ResourceIdentityType.USER_ASSIGNED);
        serviceIdentity.withUserAssignedIdentities(userAssignedIdentities);
        return serviceIdentity;
    }

    private Secret createKeyVaultSecret(String servicePrincipal, String identityPrincipal, KeyVaultManager keyVaultManager, String rgName) throws Exception {
        String vaultName = SdkContext.randomResourceName("vlt", 10);
        String secretName = SdkContext.randomResourceName("srt", 10);
        String secretValue = Files.readFirstLine(new File(getClass().getClassLoader()
            .getResource(certificateName).getFile()), 
            Charset.defaultCharset());
        Vault vault = keyVaultManager.vaults()
                .define(vaultName)
                .withRegion(Region.US_WEST2.name())
                .withExistingResourceGroup(rgName)
                .defineAccessPolicy()
                    .forServicePrincipal(servicePrincipal)
                    .allowSecretAllPermissions()
                    .attach()
                .defineAccessPolicy()
                    .forObjectId(identityPrincipal)
                    .allowSecretAllPermissions()
                    .attach()
                .withAccessFromAzureServices()
                .withDeploymentEnabled()
                // Important!! Only soft delete enabled key vault can be assigned to application gateway
                // See also: https://github.com/MicrosoftDocs/azure-docs/issues/34382
                .withSoftDeleteEnabled()
                .create();
        return vault.secrets()
                .define(secretName)
                .withValue(secretValue)
                .create();
    }
}