/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.compute.KnownLinuxVirtualMachineImage;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.resources.core.TestUtilities;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.rest.RestClient;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

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
                        .withPushNotification("azurepush@outlook.com")
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
                    .withPeriod(Period.minutes(15))
                    .withFrequency(Period.minutes(1))
                    .withAlertDetails(3, "This alert rule is for U3 - Single resource  multiple-criteria  with dimensions-single timeseries")
                    .withActionGroups(ag.id())
                    .defineAlertCriteria("Metric1")
                        .withMetricName("Transactions", "Microsoft.Storage/storageAccounts")
                        .withCondition(MetricAlertRuleTimeAggregation.TOTAL, MetricAlertRuleCondition.GREATER_THAN, 100)
                        .withDimension("ResponseType", "Success")
                        .withDimension("ApiName", "GetBlob")
                        .attach()
                    .create();

            Assert.assertNotNull(ma);
            Assert.assertEquals(1, ma.scopes().size());
            Assert.assertEquals(sa.id(), ma.scopes().iterator().next());
            Assert.assertEquals("This alert rule is for U3 - Single resource  multiple-criteria  with dimensions-single timeseries", ma.description());
            Assert.assertEquals(Period.minutes(15), ma.windowSize());
            Assert.assertEquals(Period.minutes(1), ma.evaluationFrequency());
            Assert.assertEquals(3, ma.severity());
            Assert.assertEquals(true, ma.enabled());
            Assert.assertEquals(true, ma.autoMitigate());
            Assert.assertEquals(1, ma.actionGroupIds().size());
            Assert.assertEquals(ag.id(), ma.actionGroupIds().iterator().next());
            Assert.assertEquals(1, ma.alertCriterias().size());
            MetricAlertCondition ac1 = ma.alertCriterias().values().iterator().next();
            Assert.assertEquals("Metric1", ac1.name());
            Assert.assertEquals("Transactions", ac1.metricName());
            Assert.assertEquals("Microsoft.Storage/storageAccounts", ac1.metricNamespace());
            Assert.assertEquals(MetricAlertRuleCondition.GREATER_THAN, ac1.condition());
            Assert.assertEquals(MetricAlertRuleTimeAggregation.TOTAL, ac1.timeAggregation());
            Assert.assertEquals(100, ac1.threshold(), 0.001);
            Assert.assertEquals(2, ac1.dimensions().size());
            Iterator<MetricDimension> iterator = ac1.dimensions().iterator();
            MetricDimension d2 = iterator.next();
            MetricDimension d1 = iterator.next();
            Assert.assertEquals("ResponseType", d1.name());
            Assert.assertEquals(1, d1.values().size());
            Assert.assertEquals("Success", d1.values().get(0));
            Assert.assertEquals("ApiName", d2.name());
            Assert.assertEquals(1, d2.values().size());
            Assert.assertEquals("GetBlob", d2.values().get(0));

            MetricAlert maFromGet = monitorManager.alertRules().metricAlerts().getById(ma.id());
            Assert.assertNotNull(maFromGet);
            Assert.assertEquals(ma.scopes().size(), maFromGet.scopes().size());
            Assert.assertEquals(ma.scopes().iterator().next(), maFromGet.scopes().iterator().next());
            Assert.assertEquals(ma.description(), maFromGet.description());
            Assert.assertEquals(ma.windowSize(), maFromGet.windowSize());
            Assert.assertEquals(ma.evaluationFrequency(), maFromGet.evaluationFrequency());
            Assert.assertEquals(ma.severity(), maFromGet.severity());
            Assert.assertEquals(ma.enabled(), maFromGet.enabled());
            Assert.assertEquals(ma.autoMitigate(), maFromGet.autoMitigate());
            Assert.assertEquals(ma.actionGroupIds().size(), maFromGet.actionGroupIds().size());
            Assert.assertEquals(ma.actionGroupIds().iterator().next(), maFromGet.actionGroupIds().iterator().next());
            Assert.assertEquals(ma.alertCriterias().size(), maFromGet.alertCriterias().size());
            ac1 = maFromGet.alertCriterias().values().iterator().next();
            Assert.assertEquals("Metric1", ac1.name());
            Assert.assertEquals("Transactions", ac1.metricName());
            Assert.assertEquals("Microsoft.Storage/storageAccounts", ac1.metricNamespace());
            Assert.assertEquals(MetricAlertRuleCondition.GREATER_THAN, ac1.condition());
            Assert.assertEquals(MetricAlertRuleTimeAggregation.TOTAL, ac1.timeAggregation());
            Assert.assertEquals(100, ac1.threshold(), 0.001);
            Assert.assertEquals(2, ac1.dimensions().size());
            iterator = ac1.dimensions().iterator();
            d2 = iterator.next();
            d1 = iterator.next();
            Assert.assertEquals("ResponseType", d1.name());
            Assert.assertEquals(1, d1.values().size());
            Assert.assertEquals("Success", d1.values().get(0));
            Assert.assertEquals("ApiName", d2.name());
            Assert.assertEquals(1, d2.values().size());
            Assert.assertEquals("GetBlob", d2.values().get(0));

            ma.update()
                    .withRuleDisabled()
                    .updateAlertCriteria("Metric1")
                        .withCondition(MetricAlertRuleTimeAggregation.TOTAL, MetricAlertRuleCondition.GREATER_THAN, 99)
                        .parent()
                    .defineAlertCriteria("Metric2")
                        .withMetricName("SuccessE2ELatency", "Microsoft.Storage/storageAccounts")
                        .withCondition(MetricAlertRuleTimeAggregation.AVERAGE, MetricAlertRuleCondition.GREATER_THAN, 200)
                        .withDimension("ApiName", "GetBlob")
                        .attach()
                    .apply();

            Assert.assertNotNull(ma);
            Assert.assertEquals(1, ma.scopes().size());
            Assert.assertEquals(sa.id(), ma.scopes().iterator().next());
            Assert.assertEquals("This alert rule is for U3 - Single resource  multiple-criteria  with dimensions-single timeseries", ma.description());
            Assert.assertEquals(Period.minutes(15), ma.windowSize());
            Assert.assertEquals(Period.minutes(1), ma.evaluationFrequency());
            Assert.assertEquals(3, ma.severity());
            Assert.assertEquals(false, ma.enabled());
            Assert.assertEquals(true, ma.autoMitigate());
            Assert.assertEquals(1, ma.actionGroupIds().size());
            Assert.assertEquals(ag.id(), ma.actionGroupIds().iterator().next());
            Assert.assertEquals(2, ma.alertCriterias().size());
            Iterator<MetricAlertCondition> maCriteriaIterator = ma.alertCriterias().values().iterator();
            ac1 = maCriteriaIterator.next();
            MetricAlertCondition ac2 = maCriteriaIterator.next();
            Assert.assertEquals("Metric1", ac1.name());
            Assert.assertEquals("Transactions", ac1.metricName());
            Assert.assertEquals(MetricAlertRuleCondition.GREATER_THAN, ac1.condition());
            Assert.assertEquals(MetricAlertRuleTimeAggregation.TOTAL, ac1.timeAggregation());
            Assert.assertEquals(99, ac1.threshold(), 0.001);
            Assert.assertEquals(2, ac1.dimensions().size());
            iterator = ac1.dimensions().iterator();
            d2 = iterator.next();
            d1 = iterator.next();
            Assert.assertEquals("ResponseType", d1.name());
            Assert.assertEquals(1, d1.values().size());
            Assert.assertEquals("Success", d1.values().get(0));
            Assert.assertEquals("ApiName", d2.name());
            Assert.assertEquals(1, d2.values().size());
            Assert.assertEquals("GetBlob", d2.values().get(0));

            Assert.assertEquals("Metric2", ac2.name());
            Assert.assertEquals("SuccessE2ELatency", ac2.metricName());
            Assert.assertEquals("Microsoft.Storage/storageAccounts", ac2.metricNamespace());
            Assert.assertEquals(MetricAlertRuleCondition.GREATER_THAN, ac2.condition());
            Assert.assertEquals(MetricAlertRuleTimeAggregation.AVERAGE, ac2.timeAggregation());
            Assert.assertEquals(200, ac2.threshold(), 0.001);
            Assert.assertEquals(1, ac2.dimensions().size());
            d1 = ac2.dimensions().iterator().next();
            Assert.assertEquals("ApiName", d1.name());
            Assert.assertEquals(1, d1.values().size());
            Assert.assertEquals("GetBlob", d1.values().get(0));

            maFromGet = monitorManager.alertRules().metricAlerts().getById(ma.id());

            Assert.assertNotNull(maFromGet);
            Assert.assertEquals(1, maFromGet.scopes().size());
            Assert.assertEquals(sa.id(), maFromGet.scopes().iterator().next());
            Assert.assertEquals("This alert rule is for U3 - Single resource  multiple-criteria  with dimensions-single timeseries", ma.description());
            Assert.assertEquals(Period.minutes(15), maFromGet.windowSize());
            Assert.assertEquals(Period.minutes(1), maFromGet.evaluationFrequency());
            Assert.assertEquals(3, maFromGet.severity());
            Assert.assertEquals(false, maFromGet.enabled());
            Assert.assertEquals(true, maFromGet.autoMitigate());
            Assert.assertEquals(1, maFromGet.actionGroupIds().size());
            Assert.assertEquals(ag.id(), maFromGet.actionGroupIds().iterator().next());
            Assert.assertEquals(2, maFromGet.alertCriterias().size());
            maCriteriaIterator = maFromGet.alertCriterias().values().iterator();
            ac1 = maCriteriaIterator.next();
            ac2 = maCriteriaIterator.next();
            Assert.assertEquals("Metric1", ac1.name());
            Assert.assertEquals("Transactions", ac1.metricName());
            Assert.assertEquals(MetricAlertRuleCondition.GREATER_THAN, ac1.condition());
            Assert.assertEquals(MetricAlertRuleTimeAggregation.TOTAL, ac1.timeAggregation());
            Assert.assertEquals(99, ac1.threshold(), 0.001);
            Assert.assertEquals(2, ac1.dimensions().size());
            iterator = ac1.dimensions().iterator();
            d2 = iterator.next();
            d1 = iterator.next();
            Assert.assertEquals("ResponseType", d1.name());
            Assert.assertEquals(1, d1.values().size());
            Assert.assertEquals("Success", d1.values().get(0));
            Assert.assertEquals("ApiName", d2.name());
            Assert.assertEquals(1, d2.values().size());
            Assert.assertEquals("GetBlob", d2.values().get(0));

            Assert.assertEquals("Metric2", ac2.name());
            Assert.assertEquals("SuccessE2ELatency", ac2.metricName());
            Assert.assertEquals("Microsoft.Storage/storageAccounts", ac2.metricNamespace());
            Assert.assertEquals(MetricAlertRuleCondition.GREATER_THAN, ac2.condition());
            Assert.assertEquals(MetricAlertRuleTimeAggregation.AVERAGE, ac2.timeAggregation());
            Assert.assertEquals(200, ac2.threshold(), 0.001);
            Assert.assertEquals(1, ac2.dimensions().size());
            d1 = ac2.dimensions().iterator().next();
            Assert.assertEquals("ApiName", d1.name());
            Assert.assertEquals(1, d1.values().size());
            Assert.assertEquals("GetBlob", d1.values().get(0));

            PagedList<MetricAlert> alertsInRg = monitorManager.alertRules().metricAlerts().listByResourceGroup(RG_NAME);

            Assert.assertEquals(1, alertsInRg.size());
            maFromGet = alertsInRg.get(0);

            Assert.assertNotNull(maFromGet);
            Assert.assertEquals(1, maFromGet.scopes().size());
            Assert.assertEquals(sa.id(), maFromGet.scopes().iterator().next());
            Assert.assertEquals("This alert rule is for U3 - Single resource  multiple-criteria  with dimensions-single timeseries", ma.description());
            Assert.assertEquals(Period.minutes(15), maFromGet.windowSize());
            Assert.assertEquals(Period.minutes(1), maFromGet.evaluationFrequency());
            Assert.assertEquals(3, maFromGet.severity());
            Assert.assertEquals(false, maFromGet.enabled());
            Assert.assertEquals(true, maFromGet.autoMitigate());
            Assert.assertEquals(1, maFromGet.actionGroupIds().size());
            Assert.assertEquals(ag.id(), maFromGet.actionGroupIds().iterator().next());
            Assert.assertEquals(2, maFromGet.alertCriterias().size());
            maCriteriaIterator = maFromGet.alertCriterias().values().iterator();
            ac1 = maCriteriaIterator.next();
            ac2 = maCriteriaIterator.next();
            Assert.assertEquals("Metric1", ac1.name());
            Assert.assertEquals("Transactions", ac1.metricName());
            Assert.assertEquals(MetricAlertRuleCondition.GREATER_THAN, ac1.condition());
            Assert.assertEquals(MetricAlertRuleTimeAggregation.TOTAL, ac1.timeAggregation());
            Assert.assertEquals(99, ac1.threshold(), 0.001);
            Assert.assertEquals(2, ac1.dimensions().size());
            iterator = ac1.dimensions().iterator();
            d2 = iterator.next();
            d1 = iterator.next();
            Assert.assertEquals("ResponseType", d1.name());
            Assert.assertEquals(1, d1.values().size());
            Assert.assertEquals("Success", d1.values().get(0));
            Assert.assertEquals("ApiName", d2.name());
            Assert.assertEquals(1, d2.values().size());
            Assert.assertEquals("GetBlob", d2.values().get(0));

            Assert.assertEquals("Metric2", ac2.name());
            Assert.assertEquals("SuccessE2ELatency", ac2.metricName());
            Assert.assertEquals("Microsoft.Storage/storageAccounts", ac2.metricNamespace());
            Assert.assertEquals(MetricAlertRuleCondition.GREATER_THAN, ac2.condition());
            Assert.assertEquals(MetricAlertRuleTimeAggregation.AVERAGE, ac2.timeAggregation());
            Assert.assertEquals(200, ac2.threshold(), 0.001);
            Assert.assertEquals(1, ac2.dimensions().size());
            d1 = ac2.dimensions().iterator().next();
            Assert.assertEquals("ApiName", d1.name());
            Assert.assertEquals(1, d1.values().size());
            Assert.assertEquals("GetBlob", d1.values().get(0));

            monitorManager.alertRules().metricAlerts().deleteById(ma.id());
        }
        finally {
            resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
        }
    }

    @Test
    public void canCRUDMultipleResourceMetricAlerts() throws Exception {
        try {
            final String userName = "tirekicker";
            final String password = TestUtilities.createPassword();

            String alertName = generateRandomResourceName("jMonitorMA", 18);
            String vmName1 = generateRandomResourceName("jMonitorVM1", 18);
            String vmName2 = generateRandomResourceName("jMonitorVM2", 18);

            VirtualMachine vm1 = computeManager.virtualMachines().define(vmName1)
                    .withRegion(Region.US_EAST2)
                    .withNewResourceGroup(RG_NAME)
                    .withNewPrimaryNetwork("10.0.0.0/28")
                    .withPrimaryPrivateIPAddressDynamic()
                    .withoutPrimaryPublicIPAddress()
                    .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                    .withRootUsername(userName)
                    .withRootPassword(password)
                    .create();

            VirtualMachine vm2 = computeManager.virtualMachines().define(vmName2)
                    .withRegion(Region.US_EAST2)
                    .withExistingResourceGroup(RG_NAME)
                    .withNewPrimaryNetwork("10.0.0.0/28")
                    .withPrimaryPrivateIPAddressDynamic()
                    .withoutPrimaryPublicIPAddress()
                    .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                    .withRootUsername(userName)
                    .withRootPassword(password)
                    .create();

            MetricAlert ma = monitorManager.alertRules().metricAlerts().define(alertName)
                    .withExistingResourceGroup(RG_NAME)
                    .withMultipleTargetResources(Arrays.asList(vm1, vm2))
                    .withPeriod(Period.minutes(15))
                    .withFrequency(Period.minutes(5))
                    .withAlertDetails(3, "This alert rule is for U3 - Multiple resource, static criteria")
                    .withActionGroups()
                    .defineAlertCriteria("Metric1")
                        .withMetricName("Percentage CPU", vm1.type())
                        .withCondition(MetricAlertRuleTimeAggregation.AVERAGE, MetricAlertRuleCondition.GREATER_THAN, 80)
                        .attach()
                    .create();

            ma.refresh();
            Assert.assertEquals(2, ma.scopes().size());
            Assert.assertEquals(vm1.type(), ma.inner().targetResourceType());
            Assert.assertEquals(vm1.regionName(), ma.inner().targetResourceRegion());
            Assert.assertEquals(1, ma.alertCriterias().size());
            Assert.assertEquals(0, ma.dynamicAlertCriterias().size());
            Assert.assertEquals("Percentage CPU", ma.alertCriterias().get("Metric1").metricName());

            DateTime time30MinBefore = DateTime.now().minusMinutes(30);
            ma.update()
                    .withDescription("This alert rule is for U3 - Multiple resource, dynamic criteria")
                    .withoutAlertCriteria("Metric1")
                    .defineDynamicAlertCriteria("Metric2")
                        .withMetricName("Percentage CPU", vm1.type())
                        .withCondition(MetricAlertRuleTimeAggregation.AVERAGE, DynamicThresholdOperator.GREATER_THAN, DynamicThresholdSensitivity.HIGH)
                        .withFailingPeriods(new DynamicThresholdFailingPeriods().withNumberOfEvaluationPeriods(4).withMinFailingPeriodsToAlert(2))
                        .withIgnoreDataBefore(time30MinBefore)
                        .attach()
                    .apply();

            ma.refresh();
            Assert.assertEquals(2, ma.scopes().size());
            Assert.assertEquals(vm1.type(), ma.inner().targetResourceType());
            Assert.assertEquals(vm1.regionName(), ma.inner().targetResourceRegion());
            Assert.assertEquals(0, ma.alertCriterias().size());
            Assert.assertEquals(1, ma.dynamicAlertCriterias().size());
            MetricDynamicAlertCondition condition = ma.dynamicAlertCriterias().get("Metric2");
            Assert.assertEquals("Percentage CPU", condition.metricName());
            Assert.assertEquals(MetricAlertRuleTimeAggregation.AVERAGE, condition.timeAggregation());
            Assert.assertEquals(DynamicThresholdOperator.GREATER_THAN, condition.condition());
            Assert.assertEquals(DynamicThresholdSensitivity.HIGH, condition.alertSensitivity());
            Assert.assertEquals(4, (int) condition.failingPeriods().numberOfEvaluationPeriods());
            Assert.assertEquals(2, (int) condition.failingPeriods().minFailingPeriodsToAlert());
            Assert.assertEquals(time30MinBefore, condition.ignoreDataBefore());

            monitorManager.alertRules().metricAlerts().deleteById(ma.id());
        } finally {
            resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
        }
    }

    @Test
    public void canCRUDActivityLogAlerts() throws Exception {

        try {
            ActionGroup ag = monitorManager.actionGroups().define("simpleActionGroup")
                    .withNewResourceGroup(RG_NAME, Region.US_EAST2)
                    .defineReceiver("first")
                        .withPushNotification("azurepush@outlook.com")
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

            ActivityLogAlert ala = monitorManager.alertRules().activityLogAlerts().define("somename")
                    .withExistingResourceGroup(RG_NAME)
                    .withTargetSubscription(monitorManager.subscriptionId())
                    .withDescription("AutoScale-VM-Creation-Failed")
                    .withRuleEnabled()
                    .withActionGroups(ag.id())
                    .withEqualsCondition("category", "Administrative")
                    .withEqualsCondition("resourceId", justAvm.id())
                    .withEqualsCondition("operationName", "Microsoft.Compute/virtualMachines/delete")
                    .create();

            Assert.assertNotNull(ala);
            Assert.assertEquals(1, ala.scopes().size());
            Assert.assertEquals("/subscriptions/" + monitorManager.subscriptionId(), ala.scopes().iterator().next());
            Assert.assertEquals("AutoScale-VM-Creation-Failed", ala.description());
            Assert.assertEquals(true, ala.enabled());
            Assert.assertEquals(1, ala.actionGroupIds().size());
            Assert.assertEquals(ag.id(), ala.actionGroupIds().iterator().next());
            Assert.assertEquals(3, ala.equalsConditions().size());
            Assert.assertEquals("Administrative", ala.equalsConditions().get("category"));
            Assert.assertEquals(justAvm.id(), ala.equalsConditions().get("resourceId"));
            Assert.assertEquals("Microsoft.Compute/virtualMachines/delete", ala.equalsConditions().get("operationName"));

            ActivityLogAlert alaFromGet = monitorManager.alertRules().activityLogAlerts().getById(ala.id());

            Assert.assertEquals(ala.scopes().size(), alaFromGet.scopes().size());
            Assert.assertEquals(ala.scopes().iterator().next(), alaFromGet.scopes().iterator().next());
            Assert.assertEquals(ala.description(), alaFromGet.description());
            Assert.assertEquals(ala.enabled(), alaFromGet.enabled());
            Assert.assertEquals(ala.actionGroupIds().size(), alaFromGet.actionGroupIds().size());
            Assert.assertEquals(ala.actionGroupIds().iterator().next(), alaFromGet.actionGroupIds().iterator().next());
            Assert.assertEquals(ala.equalsConditions().size(), alaFromGet.equalsConditions().size());
            Assert.assertEquals(ala.equalsConditions().get("category"), alaFromGet.equalsConditions().get("category"));
            Assert.assertEquals(ala.equalsConditions().get("resourceId"), alaFromGet.equalsConditions().get("resourceId"));
            Assert.assertEquals(ala.equalsConditions().get("operationName"), alaFromGet.equalsConditions().get("operationName"));

            ala.update()
                    .withRuleDisabled()
                    .withoutEqualsCondition("operationName")
                    .withEqualsCondition("status", "Failed")
                    .apply();

            Assert.assertEquals(1, ala.scopes().size());
            Assert.assertEquals("/subscriptions/" + monitorManager.subscriptionId(), ala.scopes().iterator().next());
            Assert.assertEquals("AutoScale-VM-Creation-Failed", ala.description());
            Assert.assertEquals(false, ala.enabled());
            Assert.assertEquals(1, ala.actionGroupIds().size());
            Assert.assertEquals(ag.id(), ala.actionGroupIds().iterator().next());
            Assert.assertEquals(3, ala.equalsConditions().size());
            Assert.assertEquals("Administrative", ala.equalsConditions().get("category"));
            Assert.assertEquals(justAvm.id(), ala.equalsConditions().get("resourceId"));
            Assert.assertEquals("Failed", ala.equalsConditions().get("status"));
            Assert.assertEquals(false, ala.equalsConditions().containsKey("operationName"));

            PagedList<ActivityLogAlert> alertsInRg = monitorManager.alertRules().activityLogAlerts().listByResourceGroup(RG_NAME);

            Assert.assertEquals(1, alertsInRg.size());
            alaFromGet = alertsInRg.get(0);;

            Assert.assertEquals(ala.scopes().size(), alaFromGet.scopes().size());
            Assert.assertEquals(ala.scopes().iterator().next(), alaFromGet.scopes().iterator().next());
            Assert.assertEquals(ala.description(), alaFromGet.description());
            Assert.assertEquals(ala.enabled(), alaFromGet.enabled());
            Assert.assertEquals(ala.actionGroupIds().size(), alaFromGet.actionGroupIds().size());
            Assert.assertEquals(ala.actionGroupIds().iterator().next(), alaFromGet.actionGroupIds().iterator().next());
            Assert.assertEquals(ala.equalsConditions().size(), alaFromGet.equalsConditions().size());
            Assert.assertEquals(ala.equalsConditions().get("category"), alaFromGet.equalsConditions().get("category"));
            Assert.assertEquals(ala.equalsConditions().get("resourceId"), alaFromGet.equalsConditions().get("resourceId"));
            Assert.assertEquals(ala.equalsConditions().get("status"), alaFromGet.equalsConditions().get("status"));
            Assert.assertEquals(ala.equalsConditions().containsKey("operationName"), alaFromGet.equalsConditions().containsKey("operationName"));

            monitorManager.alertRules().activityLogAlerts().deleteById(ala.id());
        }
        finally {
            resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
        }
    }
}

