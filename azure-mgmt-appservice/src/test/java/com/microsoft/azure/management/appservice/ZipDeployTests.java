/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.appservice;

import com.microsoft.azure.management.resources.core.TestBase;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class ZipDeployTests extends AppServiceTest {
    private static String RG_NAME_3 = "";
    private static String WEBAPP_NAME_4 = "";

    public ZipDeployTests() {
        super(TestBase.RunCondition.LIVE_ONLY);
    }
    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        WEBAPP_NAME_4 = generateRandomResourceName("java-func-", 20);
        RG_NAME_3 = generateRandomResourceName("javacsmrg", 20);

        super.initializeClients(restClient, defaultSubscription, domain);
    }

    @Test
    public void canZipDeployFunction() {
        // Create function app
        FunctionApp functionApp = appServiceManager.functionApps().define(WEBAPP_NAME_4)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(RG_NAME_3)
                .create();
        Assert.assertNotNull(functionApp);
        SdkContext.sleep(5000);
        functionApp.zipDeploy(new File(FunctionAppsTests.class.getResource("/square-function-app.zip").getPath()));
        SdkContext.sleep(5000);
        String response = post("http://" + WEBAPP_NAME_4 + ".azurewebsites.net" + "/api/square", "25");
        Assert.assertNotNull(response);
        Assert.assertEquals("625", response);
    }
}
