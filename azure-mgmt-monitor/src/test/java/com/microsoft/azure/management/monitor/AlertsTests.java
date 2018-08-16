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

            MetricAlert maFromGet = monitorManager.alertRules().metricAlerts().getById(ma.id());

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

            maFromGet = monitorManager.alertRules().metricAlerts().getById(ma.id());

            PagedList<MetricAlert> alertsInRg = monitorManager.alertRules().metricAlerts().listByResourceGroup(RG_NAME);

            Assert.assertEquals(1, alertsInRg.size());
            maFromGet = alertsInRg.get(0);

            monitorManager.alertRules().metricAlerts().deleteById(ma.id());
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

            Assert.assertNotNull(ma);
            Assert.assertEquals(1, ma.scopes().size());
            Assert.assertEquals("/subscriptions/" + monitorManager.subscriptionId(), ma.scopes().iterator().next());
            Assert.assertEquals("AutoScale-VM-Creation-Failed", ma.description());
            Assert.assertEquals(true, ma.enabled());
            Assert.assertEquals(1, ma.actionGroupIds().size());
            Assert.assertEquals(ag.id(), ma.actionGroupIds().iterator().next());
            Assert.assertEquals(3, ma.equalsConditions().size());
            Assert.assertEquals("Administrative", ma.equalsConditions().get("category"));
            Assert.assertEquals(justAvm.id(), ma.equalsConditions().get("resourceId"));
            Assert.assertEquals("Microsoft.Compute/virtualMachines/delete", ma.equalsConditions().get("operationName"));

            ActivityLogAlert maFromGet = monitorManager.alertRules().activityLogAlerts().getById(ma.id());

            Assert.assertEquals(ma.scopes().size(), maFromGet.scopes().size());
            Assert.assertEquals(ma.scopes().iterator().next(), maFromGet.scopes().iterator().next());
            Assert.assertEquals(ma.description(), maFromGet.description());
            Assert.assertEquals(ma.enabled(), maFromGet.enabled());
            Assert.assertEquals(ma.actionGroupIds().size(), maFromGet.actionGroupIds().size());
            Assert.assertEquals(ma.actionGroupIds().iterator().next(), maFromGet.actionGroupIds().iterator().next());
            Assert.assertEquals(ma.equalsConditions().size(), maFromGet.equalsConditions().size());
            Assert.assertEquals(ma.equalsConditions().get("category"), maFromGet.equalsConditions().get("category"));
            Assert.assertEquals(ma.equalsConditions().get("resourceId"), maFromGet.equalsConditions().get("resourceId"));
            Assert.assertEquals(ma.equalsConditions().get("operationName"), maFromGet.equalsConditions().get("operationName"));

            ma.update()
                    .withRuleDisabled()
                    .withoutEqualsCondition("operationName")
                    .withEqualsCondition("status", "Failed")
                    .apply();

            Assert.assertEquals(1, ma.scopes().size());
            Assert.assertEquals("/subscriptions/" + monitorManager.subscriptionId(), ma.scopes().iterator().next());
            Assert.assertEquals("AutoScale-VM-Creation-Failed", ma.description());
            Assert.assertEquals(false, ma.enabled());
            Assert.assertEquals(1, ma.actionGroupIds().size());
            Assert.assertEquals(ag.id(), ma.actionGroupIds().iterator().next());
            Assert.assertEquals(3, ma.equalsConditions().size());
            Assert.assertEquals("Administrative", ma.equalsConditions().get("category"));
            Assert.assertEquals(justAvm.id(), ma.equalsConditions().get("resourceId"));
            Assert.assertEquals("Failed", ma.equalsConditions().get("status"));
            Assert.assertEquals(false, ma.equalsConditions().containsKey("operationName"));

            PagedList<ActivityLogAlert> alertsInRg = monitorManager.alertRules().activityLogAlerts().listByResourceGroup(RG_NAME);

            Assert.assertEquals(1, alertsInRg.size());
            maFromGet = alertsInRg.get(0);;

            Assert.assertEquals(ma.scopes().size(), maFromGet.scopes().size());
            Assert.assertEquals(ma.scopes().iterator().next(), maFromGet.scopes().iterator().next());
            Assert.assertEquals(ma.description(), maFromGet.description());
            Assert.assertEquals(ma.enabled(), maFromGet.enabled());
            Assert.assertEquals(ma.actionGroupIds().size(), maFromGet.actionGroupIds().size());
            Assert.assertEquals(ma.actionGroupIds().iterator().next(), maFromGet.actionGroupIds().iterator().next());
            Assert.assertEquals(ma.equalsConditions().size(), maFromGet.equalsConditions().size());
            Assert.assertEquals(ma.equalsConditions().get("category"), maFromGet.equalsConditions().get("category"));
            Assert.assertEquals(ma.equalsConditions().get("resourceId"), maFromGet.equalsConditions().get("resourceId"));
            Assert.assertEquals(ma.equalsConditions().get("status"), maFromGet.equalsConditions().get("status"));
            Assert.assertEquals(ma.equalsConditions().containsKey("operationName"), maFromGet.equalsConditions().containsKey("operationName"));

            monitorManager.alertRules().activityLogAlerts().deleteById(ma.id());
        }
        finally {
            resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
        }
    }
}

