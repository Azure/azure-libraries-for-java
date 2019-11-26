/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice;

import com.microsoft.azure.management.graphrbac.BuiltInRole;
import com.microsoft.azure.management.msi.Identity;
import com.microsoft.azure.management.msi.implementation.MSIManager;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.rest.RestClient;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class WebAppsMsiTests extends AppServiceTest {
    private MSIManager msiManager;
    private static String RG_NAME_1 = "";
    private static String WEBAPP_NAME_1 = "";
    private static String VAULT_NAME = "";

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        WEBAPP_NAME_1 = generateRandomResourceName("java-webapp-", 20);
        RG_NAME_1 = generateRandomResourceName("javacsmrg", 20);
        VAULT_NAME = generateRandomResourceName("java-vault-", 20);
        this.msiManager = MSIManager.authenticate(restClient, defaultSubscription);

        super.initializeClients(restClient, defaultSubscription, domain);
    }

    @Override
    protected void cleanUpResources() {
        resourceManager.resourceGroups().beginDeleteByName(RG_NAME_1);
    }

    @Test
    public void canCRUDWebAppWithMsi() throws Exception {
        // Create with new app service plan
        WebApp webApp = appServiceManager.webApps().define(WEBAPP_NAME_1)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(RG_NAME_1)
                .withNewWindowsPlan(PricingTier.BASIC_B1)
                .withRemoteDebuggingEnabled(RemoteVisualStudioVersion.VS2019)
                .withSystemAssignedManagedServiceIdentity()
                .withSystemAssignedIdentityBasedAccessToCurrentResourceGroup(BuiltInRole.CONTRIBUTOR)
                .withJavaVersion(JavaVersion.JAVA_8_NEWEST)
                .withWebContainer(WebContainer.TOMCAT_8_0_NEWEST)
                .create();
        Assert.assertNotNull(webApp);
        Assert.assertEquals(Region.US_WEST, webApp.region());
        AppServicePlan plan = appServiceManager.appServicePlans().getById(webApp.appServicePlanId());
        Assert.assertNotNull(plan);
        Assert.assertEquals(Region.US_WEST, plan.region());
        Assert.assertEquals(PricingTier.BASIC_B1, plan.pricingTier());
        Assert.assertNotNull(webApp.systemAssignedManagedServiceIdentityPrincipalId());
        Assert.assertNotNull(webApp.systemAssignedManagedServiceIdentityTenantId());

        if (!isPlaybackMode()) {
            // Check availability of environment variables
            uploadFileToWebApp(webApp.getPublishingProfile(), "appservicemsi.war", WebAppsMsiTests.class.getResourceAsStream("/appservicemsi.war"));

            SdkContext.sleep(10000);

            Response response = curl("http://" + WEBAPP_NAME_1 + "." + "azurewebsites.net/appservicemsi/");
            Assert.assertEquals(200, response.code());
            String body = response.body().string();
            Assert.assertNotNull(body);
            Assert.assertTrue(body.contains(webApp.resourceGroupName()));
            Assert.assertTrue(body.contains(webApp.id()));
        }
    }

    @Test
    public void canCRUDWebAppWithUserAssignedMsi() throws Exception {

        String identityName1 = generateRandomResourceName("msi-id", 15);
        String identityName2 = generateRandomResourceName("msi-id", 15);

        // Prepare a definition for yet-to-be-created resource group
        //
        Creatable<ResourceGroup> creatableRG = resourceManager.resourceGroups()
                .define(RG_NAME)
                .withRegion(Region.US_WEST);

        // Create an "User Assigned (External) MSI" residing in the above RG and assign reader access to the virtual network
        //
        final Identity createdIdentity = msiManager.identities()
                .define(identityName1)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(creatableRG)
                .withAccessToCurrentResourceGroup(BuiltInRole.CONTRIBUTOR)
                .create();

        // Prepare a definition for yet-to-be-created "User Assigned (External) MSI" with contributor access to the resource group
        // it resides
        //
        Creatable<Identity> creatableIdentity = msiManager.identities()
                .define(identityName2)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(creatableRG)
                .withAccessToCurrentResourceGroup(BuiltInRole.CONTRIBUTOR);

        // Create with new app service plan
        WebApp webApp = appServiceManager.webApps().define(WEBAPP_NAME_1)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(RG_NAME_1)
                .withNewWindowsPlan(PricingTier.BASIC_B1)
                .withRemoteDebuggingEnabled(RemoteVisualStudioVersion.VS2019)
                .withSystemAssignedManagedServiceIdentity()
                .withSystemAssignedIdentityBasedAccessToCurrentResourceGroup(BuiltInRole.CONTRIBUTOR)
                .withJavaVersion(JavaVersion.JAVA_8_NEWEST)
                .withWebContainer(WebContainer.TOMCAT_8_0_NEWEST)
                .withUserAssignedManagedServiceIdentity()
                .withNewUserAssignedManagedServiceIdentity(creatableIdentity)
                .withExistingUserAssignedManagedServiceIdentity(createdIdentity)
                .create();
        Assert.assertNotNull(webApp);
        Assert.assertEquals(Region.US_WEST, webApp.region());
        AppServicePlan plan = appServiceManager.appServicePlans().getById(webApp.appServicePlanId());
        Assert.assertNotNull(plan);
        Assert.assertEquals(Region.US_WEST, plan.region());
        Assert.assertEquals(PricingTier.BASIC_B1, plan.pricingTier());
        Assert.assertNotNull(webApp.systemAssignedManagedServiceIdentityPrincipalId());
        Assert.assertNotNull(webApp.systemAssignedManagedServiceIdentityTenantId());
        Set<String> identityIds = webApp.userAssignedManagedServiceIdentityIds();
        Assert.assertNotNull(identityIds);
        Assert.assertEquals(identityIds.size(), 2);
        Assert.assertTrue(setContainsValue(identityIds, identityName1));
        Assert.assertTrue(setContainsValue(identityIds, identityName2));

        if (!isPlaybackMode()) {
            // Check availability of environment variables
            uploadFileToWebApp(webApp.getPublishingProfile(), "appservicemsi.war", WebAppsMsiTests.class.getResourceAsStream("/appservicemsi.war"));

            SdkContext.sleep(10000);

            Response response = curl("http://" + WEBAPP_NAME_1 + "." + "azurewebsites.net/appservicemsi/");
            Assert.assertEquals(200, response.code());
            String body = response.body().string();
            Assert.assertNotNull(body);
            Assert.assertTrue(body.contains(webApp.resourceGroupName()));
            Assert.assertTrue(body.contains(webApp.id()));
        }
    }

    boolean setContainsValue(Set<String> stringSet, String value) {
        boolean found = false;
        for (String setContent : stringSet) {
            if (setContent.contains(value)) {
                found = true;
                break;
            }
        }

        return found;
    }
}