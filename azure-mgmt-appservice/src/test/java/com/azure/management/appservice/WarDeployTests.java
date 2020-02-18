/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.appservice;

import com.azure.management.RestClient;
import com.azure.management.resources.fluentcore.arm.Region;
import okhttp3.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class WarDeployTests extends AppServiceTest {
    private static String WEBAPP_NAME = "";

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        WEBAPP_NAME = "JAVA" + generateRandomResourceName("webapp-", 20);

        super.initializeClients(restClient, defaultSubscription, domain);
    }

    @Test
    public void canDeployWar() throws Exception {
        if (!isPlaybackMode()) {
            // webApp.warDeploy method randomly fails in playback mode with error java.net.UnknownHostException,
            // Run this only in live mode ignore in playback until we find the root cause
            // https://api.travis-ci.org/v3/job/427936160/log.txt
            //
            // Create web app
            WebApp webApp = appServiceManager.webApps().define(WEBAPP_NAME)
                    .withRegion(Region.US_WEST)
                    .withNewResourceGroup(RG_NAME)
                    .withNewWindowsPlan(PricingTier.STANDARD_S1)
                    .withJavaVersion(JavaVersion.JAVA_8_NEWEST)
                    .withWebContainer(WebContainer.TOMCAT_9_0_NEWEST)
                    .create();
            Assertions.assertNotNull(webApp);

            webApp.warDeploy(new File(WarDeployTests.class.getResource("/helloworld.war").getPath()));

            if (!isPlaybackMode()) {
                Response response = curl("http://" + WEBAPP_NAME + "." + "azurewebsites.net");
                Assertions.assertEquals(200, response.code());
                String body = response.body().string();
                Assertions.assertNotNull(body);
                Assertions.assertTrue(body.contains("Azure Samples Hello World"));
            }
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
        Assertions.assertNotNull(webApp);

        if (!isPlaybackMode()) {
            webApp.warDeploy(new File(WarDeployTests.class.getResource("/helloworld.war").getPath()));
            webApp.warDeploy(new File(WarDeployTests.class.getResource("/helloworld.war").getPath()), "app2");

            Response response = curl("http://" + WEBAPP_NAME + "." + "azurewebsites.net");
            Assertions.assertEquals(200, response.code());
            String body = response.body().string();
            Assertions.assertNotNull(body);
            Assertions.assertTrue(body.contains("Azure Samples Hello World"));

            response = curl("http://" + WEBAPP_NAME + "." + "azurewebsites.net/app2");
            Assertions.assertEquals(200, response.code());
            body = response.body().string();
            Assertions.assertNotNull(body);
            Assertions.assertTrue(body.contains("Azure Samples Hello World"));
        }
    }
}