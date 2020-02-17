/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.appservice;

import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Test;

public class DiagnosticLogsTests extends AppServiceTest {
    private static String RG_NAME_1 = "";
    private static String WEBAPP_NAME_1 = "";

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        WEBAPP_NAME_1 = generateRandomResourceName("java-webapp-", 20);
        RG_NAME_1 = generateRandomResourceName("javacsmrg", 20);

        super.initializeClients(restClient, defaultSubscription, domain);
    }

    @Override
    protected void cleanUpResources() {
        resourceManager.resourceGroups().beginDeleteByName(RG_NAME_1);
    }

    @Test
    public void canCRUDWebAppWithDiagnosticLogs() throws Exception {
        // Create with new app service plan
        WebApp webApp1 = appServiceManager.webApps().define(WEBAPP_NAME_1)
                .withRegion(Region.US_WEST)
                .withNewResourceGroup(RG_NAME_1)
                .withNewWindowsPlan(PricingTier.BASIC_B1)
                .defineDiagnosticLogsConfiguration()
                    .withApplicationLogging()
                    .withLogLevel(LogLevel.INFORMATION)
                    .withApplicationLogsStoredOnFileSystem()
                    .attach()
                .defineDiagnosticLogsConfiguration()
                    .withWebServerLogging()
                    .withWebServerLogsStoredOnFileSystem()
                    .withWebServerFileSystemQuotaInMB(50)
                    .withUnlimitedLogRetentionDays()
                    .attach()
                .create();
        Assert.assertNotNull(webApp1);
        Assert.assertEquals(Region.US_WEST, webApp1.region());
        AppServicePlan plan1 = appServiceManager.appServicePlans().getById(webApp1.appServicePlanId());
        Assert.assertNotNull(plan1);
        Assert.assertEquals(Region.US_WEST, plan1.region());
        Assert.assertEquals(PricingTier.BASIC_B1, plan1.pricingTier());

        Assert.assertNotNull(webApp1.diagnosticLogsConfig());
        Assert.assertEquals(LogLevel.INFORMATION, webApp1.diagnosticLogsConfig().applicationLoggingFileSystemLogLevel());
        Assert.assertEquals(LogLevel.OFF, webApp1.diagnosticLogsConfig().applicationLoggingStorageBlobLogLevel());
        Assert.assertNull(webApp1.diagnosticLogsConfig().applicationLoggingStorageBlobContainer());
        Assert.assertEquals(0, webApp1.diagnosticLogsConfig().applicationLoggingStorageBlobRetentionDays());
        Assert.assertEquals(50, webApp1.diagnosticLogsConfig().webServerLoggingFileSystemQuotaInMB());
        // 0 means unlimited
        Assert.assertEquals(0, webApp1.diagnosticLogsConfig().webServerLoggingFileSystemRetentionDays());
        Assert.assertNull(webApp1.diagnosticLogsConfig().webServerLoggingStorageBlobContainer());
        Assert.assertEquals(0, webApp1.diagnosticLogsConfig().webServerLoggingStorageBlobRetentionDays());
        Assert.assertFalse(webApp1.diagnosticLogsConfig().detailedErrorMessages());
        Assert.assertFalse(webApp1.diagnosticLogsConfig().failedRequestsTracing());

        // Update
        webApp1.update()
                .updateDiagnosticLogsConfiguration()
                    .withoutApplicationLogging()
                    .parent()
                .updateDiagnosticLogsConfiguration()
                    .withWebServerLogging()
                    .withWebServerLogsStoredOnFileSystem()
                    .withWebServerFileSystemQuotaInMB(80)
                    .withLogRetentionDays(3)
                    .withDetailedErrorMessages(true)
                    .parent()
                .apply();

        Assert.assertNotNull(webApp1.diagnosticLogsConfig());
        Assert.assertEquals(LogLevel.OFF, webApp1.diagnosticLogsConfig().applicationLoggingFileSystemLogLevel());
        Assert.assertEquals(LogLevel.OFF, webApp1.diagnosticLogsConfig().applicationLoggingStorageBlobLogLevel());
        Assert.assertNull(webApp1.diagnosticLogsConfig().applicationLoggingStorageBlobContainer());
        Assert.assertEquals(0, webApp1.diagnosticLogsConfig().applicationLoggingStorageBlobRetentionDays());
        Assert.assertEquals(80, webApp1.diagnosticLogsConfig().webServerLoggingFileSystemQuotaInMB());
        Assert.assertEquals(3, webApp1.diagnosticLogsConfig().webServerLoggingFileSystemRetentionDays());
        Assert.assertNull(webApp1.diagnosticLogsConfig().webServerLoggingStorageBlobContainer());
        Assert.assertEquals(3, webApp1.diagnosticLogsConfig().webServerLoggingStorageBlobRetentionDays());
        Assert.assertTrue(webApp1.diagnosticLogsConfig().detailedErrorMessages());
        Assert.assertFalse(webApp1.diagnosticLogsConfig().failedRequestsTracing());
    }
}