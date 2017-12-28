/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice;

import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.rest.RestClient;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Test;

public class WebAppsMsiTests extends AppServiceTest {
    private static String RG_NAME_1 = "";
    private static String WEBAPP_NAME_1 = "";
    private static String VAULT_NAME = "";

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        WEBAPP_NAME_1 = generateRandomResourceName("java-webapp-", 20);
        RG_NAME_1 = generateRandomResourceName("javacsmrg", 20);
        VAULT_NAME = generateRandomResourceName("java-vault-", 20);

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
                .withRemoteDebuggingEnabled(RemoteVisualStudioVersion.VS2013)
                .withSystemAssignedManagedServiceIdentity()
                .withJavaVersion(JavaVersion.JAVA_8_NEWEST)
                .withWebContainer(WebContainer.TOMCAT_8_0_NEWEST)
                .create();
        Assert.assertNotNull(webApp);
        Assert.assertEquals(Region.US_WEST, webApp.region());
        AppServicePlan plan = appServiceManager.appServicePlans().getById(webApp.appServicePlanId());
        Assert.assertNotNull(plan);
        Assert.assertEquals(Region.US_WEST, plan.region());
        Assert.assertEquals(PricingTier.BASIC_B1, plan.pricingTier());
        Assert.assertNotNull(webApp.managedServiceIdentity());
        Assert.assertEquals("SystemAssigned", webApp.managedServiceIdentity().type());
        Assert.assertNotNull(webApp.managedServiceIdentity().tenantId());
        Assert.assertNotNull(webApp.managedServiceIdentity().principalId());

        if (!isPlaybackMode()) {
            // Check availability of environment variables
            uploadFileToWebApp(webApp.getPublishingProfile(), "appservicemsi_war.war", WebAppsMsiTests.class.getResourceAsStream("/appservicemsi_war.war"));

            SdkContext.sleep(10000);

            Response response = curl("http://" + WEBAPP_NAME_1 + "." + "azurewebsites.net/appservicemsi_war/");
            Assert.assertEquals(200, response.code());
            String body = response.body().string();
            Assert.assertNotNull(body);
            Assert.assertTrue(body.contains("http://127.0.0.1"));
        }
    }
}