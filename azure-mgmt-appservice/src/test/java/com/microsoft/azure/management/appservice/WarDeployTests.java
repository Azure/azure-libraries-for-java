/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice;

import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.rest.RestClient;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class WarDeployTests extends AppServiceTest {
    private static String WEBAPP_NAME = "";

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        WEBAPP_NAME = generateRandomResourceName("java-webapp-", 20);

        super.initializeClients(restClient, defaultSubscription, domain);
    }

    @Test
    public void canDeployWar() throws Exception {
        // Create web app
        WebApp webApp = appServiceManager.webApps().define(WEBAPP_NAME)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(RG_NAME)
                .withNewWindowsPlan(PricingTier.STANDARD_S1)
                .withJavaVersion(JavaVersion.JAVA_8_NEWEST)
                .withWebContainer(WebContainer.TOMCAT_9_0_NEWEST)
                .create();
        Assert.assertNotNull(webApp);

        webApp.warDeploy(new File(WarDeployTests.class.getResource("/helloworld.war").getPath()));

        if (!isPlaybackMode()) {
            Response response = curl("http://" + WEBAPP_NAME + "." + "azurewebsites.net");
            Assert.assertEquals(200, response.code());
            String body = response.body().string();
            Assert.assertNotNull(body);
            Assert.assertTrue(body.contains("Azure Samples Hello World"));
        }
    }

    @Test
    public void canDeployMultipleWars() throws Exception {
        // Create web app
        WebApp webApp = appServiceManager.webApps().define(WEBAPP_NAME)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(RG_NAME)
                .withNewWindowsPlan(PricingTier.STANDARD_S1)
                .withJavaVersion(JavaVersion.JAVA_8_NEWEST)
                .withWebContainer(WebContainer.TOMCAT_9_0_NEWEST)
                .create();
        Assert.assertNotNull(webApp);

        webApp.warDeploy(new File(WarDeployTests.class.getResource("/helloworld.war").getPath()));
        webApp.warDeploy(WarDeployTests.class.getResourceAsStream("/helloworld.war"), "app2");

        if (!isPlaybackMode()) {
            Response response = curl("http://" + WEBAPP_NAME + "." + "azurewebsites.net");
            Assert.assertEquals(200, response.code());
            String body = response.body().string();
            Assert.assertNotNull(body);
            Assert.assertTrue(body.contains("Azure Samples Hello World"));

            response = curl("http://" + WEBAPP_NAME + "." + "azurewebsites.net/app2");
            Assert.assertEquals(200, response.code());
            body = response.body().string();
            Assert.assertNotNull(body);
            Assert.assertTrue(body.contains("Azure Samples Hello World"));
        }
    }
}