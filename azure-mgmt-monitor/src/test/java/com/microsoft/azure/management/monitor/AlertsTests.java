/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.rest.RestClient;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Test;

public class AlertsTests extends MonitorManagementTest {
    private static String RG_NAME = "";
    private static String SA_NAME = "";

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        RG_NAME = generateRandomResourceName("jMonitor_", 18);
        SA_NAME = generateRandomResourceName("jMonitorSA", 18);

        super.initializeClients(restClient, defaultSubscription, domain);
    }
    @Override
    protected void cleanUpResources() {
        resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
    }

    @Test
    public void canCRUDMetricAlerts() throws Exception {

        try {
            StorageAccount sa = storageManager.storageAccounts()
                    .define(SA_NAME)
                    .withRegion(Region.US_EAST2)
                    .withNewResourceGroup(RG_NAME)
                    .create();

            ActionGroup ag = monitorManager.actionGroups().define("simpleActionGroup")
                    .withExistingResourceGroup(RG_NAME)
                    .defineReceiver("first")
                        .withAzureAppPush("azurepush@outlook.com")
                        .withEmail("justemail@outlook.com")
                        .withSms("1", "4255655665")
                        .withVoice("1", "2062066050")
                        .withWebhook("https://www.rate.am")
                        .attach()
                    .defineReceiver("second")
                        .withEmail("secondemail@outlook.com")
                        .withWebhook("https://www.spyur.am")
                        .attach()
                    .create();

            MetricAlert ma = monitorManager.alertRules().metricAlerts().define("somename")
                    .withExistingResourceGroup(RG_NAME)
                    .withTargetResource(sa.id())
                    .withWindowSize(Period.minutes(15))
                    .withEvaluationFrequency(Period.minutes(1))
                    .withSeverity(3)
                    .withDescription("This alert rule is for U3 - Single resource  multiple-criteria  with dimensions-single timeseries")
                    .withRuleEnabled()
                    .withActionGroups(ag.id())
                    .defineAlertCriteria("Metric1")
                        .withSignalName("Transactions")
                        .withCondition(MetricAlertRuleCondition.GREATER_THAN, MetricAlertRuleTimeAggregation.TOTAL, 100)
                        .withDimensionFilter("ResponseType", "Success")
                        .withDimensionFilter("ApiName", "GetBlob")
                        .withMetricNamespace("Microsoft.Storage/storageAccounts")
                        .attach()
                    .withAutoMitigation()
                    .create();

            ma.update()
                    .withRuleDisabled()
                    .updateAlertCriteria("Metric1")
                        .withoutMetricNamespace()
                        .parent()
                    .defineAlertCriteria("Metric2")
                        .withSignalName("SuccessE2ELatency")
                        .withCondition(MetricAlertRuleCondition.GREATER_THAN, MetricAlertRuleTimeAggregation.AVERAGE, 200)
                        .withDimensionFilter("ApiName", "GetBlob")
                        .withMetricNamespace("Microsoft.Storage/storageAccounts")
                        .attach()
                    .apply();
        }
        finally {
            resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
        }
    }

    @Test
    public void canCRUDActivityLogAlerts() throws Exception {

        try {
            ActionGroup ag = monitorManager.actionGroups().define("simpleActionGroup")
                    .withNewResourceGroup(RG_NAME, Region.US_EAST2)
                    .defineReceiver("first")
                        .withAzureAppPush("azurepush@outlook.com")
                        .withEmail("justemail@outlook.com")
                        .withSms("1", "4255655665")
                        .withVoice("1", "2062066050")
                        .withWebhook("https://www.rate.am")
                        .attach()
                    .defineReceiver("second")
                        .withEmail("secondemail@outlook.com")
                        .withWebhook("https://www.spyur.am")
                        .attach()
                    .create();

            VirtualMachine justAvm = computeManager.virtualMachines().list().get(0);

            ActivityLogAlert ma = monitorManager.alertRules().activityLogAlerts().define("somename")
                    .withExistingResourceGroup(RG_NAME)
                    .withTargetSubscription(monitorManager.subscriptionId())
                    .withDescription("AutoScale-VM-Creation-Failed")
                    .withRuleEnabled()
                    .withActionGroups(ag.id())
                    .withEqualsCondition("category", "Administrative")
                    .withEqualsCondition("resourceId", justAvm.id())
                    .withEqualsCondition("operationName", "Microsoft.Compute/virtualMachines/delete")
                    .create();

            ma.update()
                    .withRuleDisabled()
                    .withoutEqualsCondition("operationName")
                    .withEqualsCondition("status", "Failed")
                    .apply();
        }
        finally {
            resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
        }
    }
}

