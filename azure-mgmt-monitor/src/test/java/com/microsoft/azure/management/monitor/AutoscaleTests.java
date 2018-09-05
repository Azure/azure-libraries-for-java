/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.appservice.AppServicePlan;
import com.microsoft.azure.management.appservice.OperatingSystem;
import com.microsoft.azure.management.appservice.PricingTier;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.rest.RestClient;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Test;

public class AutoscaleTests extends MonitorManagementTest {
    private static String RG_NAME = "";

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        RG_NAME = generateRandomResourceName("jMonitor_", 18);

        super.initializeClients(restClient, defaultSubscription, domain);
    }
    @Override
    protected void cleanUpResources() {
        resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
    }

    @Test
    public void canCRUDAutoscale() throws Exception {

        try {
            resourceManager.resourceGroups().define(RG_NAME)
                    .withRegion(Region.US_EAST2)
                    .withTag("type", "autoscale")
                    .withTag("tagname", "tagvalue")
                    .create();

            AppServicePlan servicePlan = appServiceManager.appServicePlans().define("HighlyAvailableWebApps")
                    .withRegion(Region.US_EAST2)
                    .withExistingResourceGroup(RG_NAME)
                    .withPricingTier(PricingTier.PREMIUM_P1)
                    .withOperatingSystem(OperatingSystem.WINDOWS)
                    .create();

            AutoscaleSetting setting = monitorManager.autoscaleSettings()
                    .define("somesettingZ")
                    .withRegion(Region.US_EAST2)
                    .withExistingResourceGroup(RG_NAME)
                    .withTargetResource(servicePlan.id())

                    .defineAutoscaleProfile("Default")
                        .withScheduleBasedScale(3)
                        .withRecurrentSchedule("UTC", "18:00", DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.SATURDAY)
                        .attach()

                    .defineAutoscaleProfile("AutoScaleProfile1")
                        .withMetricBasedScale(1, 10, 1)
                        .defineScaleRule()
                            .withMetricSource(servicePlan.id())
                            // current swagger does not support namespace selection
                            //.withMetricName("CPUPercentage", "Microsoft.Web/serverfarms")
                            .withMetricName("CPUPercentage")
                            .withStatistic(Period.minutes(10), Period.minutes(1), MetricStatisticType.AVERAGE)
                            .withCondition(ComparisonOperationType.GREATER_THAN, TimeAggregationType.AVERAGE, 70)
                            .withScaleAction(ScaleDirection.INCREASE, ScaleType.EXACT_COUNT, 10, Period.hours(12))
                            .attach()
                        .withFixedDateSchedule("UTC", DateTime.parse("2050-10-12T20:15:10Z"), DateTime.parse("2051-09-11T16:08:04Z"))
                        .attach()

                    .defineAutoscaleProfile("AutoScaleProfile2")
                        .withMetricBasedScale(1, 5, 3)
                        .defineScaleRule()
                            .withMetricSource(servicePlan.id())
                            .withMetricName("CPUPercentage")
                            .withStatistic(Period.minutes(10), Period.minutes(1), MetricStatisticType.AVERAGE)
                            .withCondition(ComparisonOperationType.LESS_THAN, TimeAggregationType.AVERAGE, 20)
                            .withScaleAction(ScaleDirection.DECREASE, ScaleType.EXACT_COUNT, 1, Period.hours(3))
                            .attach()
                        .withRecurrentSchedule("UTC", "12:13", DayOfWeek.FRIDAY)
                        .attach()

                    .withAdminEmailNotification()
                    .withCoAdminEmailNotification()
                    .withCustomEmailsNotification("me@mycorp.com", "you@mycorp.com", "him@mycorp.com")
                    .withAutoscaleDisabled()
                    .create();

            Assert.assertNotNull(setting);
            Assert.assertEquals("somesettingZ", setting.name());
            Assert.assertEquals(servicePlan.id(), setting.targetResourceId());
            Assert.assertTrue(setting.adminEmailNotificationEnabled());
            Assert.assertTrue(setting.coAdminEmailNotificationEnabled());
            Assert.assertFalse(setting.autoscaleEnabled());
            Assert.assertEquals(3, setting.customEmailsNotification().size());
            Assert.assertEquals("me@mycorp.com", setting.customEmailsNotification().get(0));
            Assert.assertEquals("you@mycorp.com", setting.customEmailsNotification().get(1));
            Assert.assertEquals("him@mycorp.com", setting.customEmailsNotification().get(2));

            Assert.assertEquals(3, setting.profiles().size());

            AutoscaleProfile tempProfile = setting.profiles().get("Default");
            Assert.assertNotNull(tempProfile);
            Assert.assertEquals("Default", tempProfile.name());
            Assert.assertEquals(3, tempProfile.defaultInstanceCount());
            Assert.assertEquals(3, tempProfile.maxInstanceCount());
            Assert.assertEquals(3, tempProfile.minInstanceCount());
            Assert.assertNull(tempProfile.fixedDateSchedule());
            Assert.assertNotNull(tempProfile.rules());
            Assert.assertEquals(0, tempProfile.rules().size());
            Assert.assertNotNull(tempProfile.recurrentSchedule());
            Assert.assertEquals(RecurrenceFrequency.WEEK, tempProfile.recurrentSchedule().frequency());
            Assert.assertNotNull(tempProfile.recurrentSchedule().schedule());
            Assert.assertEquals(3, tempProfile.recurrentSchedule().schedule().days().size());
            Assert.assertTrue(tempProfile.recurrentSchedule().schedule().days().contains(DayOfWeek.MONDAY.toString()));
            Assert.assertTrue(tempProfile.recurrentSchedule().schedule().days().contains(DayOfWeek.TUESDAY.toString()));
            Assert.assertTrue(tempProfile.recurrentSchedule().schedule().days().contains(DayOfWeek.SATURDAY.toString()));
            Assert.assertEquals(1, tempProfile.recurrentSchedule().schedule().hours().size());
            Assert.assertEquals(1, tempProfile.recurrentSchedule().schedule().minutes().size());
            Assert.assertTrue(tempProfile.recurrentSchedule().schedule().hours().contains(18));
            Assert.assertTrue(tempProfile.recurrentSchedule().schedule().minutes().contains(0));
            Assert.assertTrue(tempProfile.recurrentSchedule().schedule().timeZone().equalsIgnoreCase("UTC"));

            tempProfile = setting.profiles().get("AutoScaleProfile1");
            Assert.assertNotNull(tempProfile);
            Assert.assertEquals("AutoScaleProfile1", tempProfile.name());
            Assert.assertEquals(1, tempProfile.defaultInstanceCount());
            Assert.assertEquals(10, tempProfile.maxInstanceCount());
            Assert.assertEquals(1, tempProfile.minInstanceCount());
            Assert.assertNotNull(tempProfile.fixedDateSchedule());
            Assert.assertTrue(tempProfile.fixedDateSchedule().timeZone().equalsIgnoreCase("UTC"));
            Assert.assertEquals(DateTime.parse("2050-10-12T20:15:10Z"), tempProfile.fixedDateSchedule().start());
            Assert.assertEquals(DateTime.parse("2051-09-11T16:08:04Z"), tempProfile.fixedDateSchedule().end());
            Assert.assertNull(tempProfile.recurrentSchedule());
            Assert.assertNotNull(tempProfile.rules());
            Assert.assertEquals(1, tempProfile.rules().size());
            ScaleRule rule = tempProfile.rules().get(0);
            Assert.assertEquals(servicePlan.id(), rule.metricSource());
            Assert.assertEquals("CPUPercentage", rule.metricName());
            Assert.assertEquals(Period.minutes(10), rule.duration());
            Assert.assertEquals(Period.minutes(1), rule.frequency());
            Assert.assertEquals(MetricStatisticType.AVERAGE, rule.frequencyStatistic());
            Assert.assertEquals(ComparisonOperationType.GREATER_THAN, rule.condition());
            Assert.assertEquals(TimeAggregationType.AVERAGE, rule.timeAggregation());
            Assert.assertEquals(70, rule.threshold(), 0.001);
            Assert.assertEquals(ScaleDirection.INCREASE, rule.scaleDirection());
            Assert.assertEquals(ScaleType.EXACT_COUNT, rule.scaleType());
            Assert.assertEquals(10, rule.scaleInstanceCount());
            Assert.assertEquals(Period.hours(12), rule.coolDown());

            tempProfile = setting.profiles().get("AutoScaleProfile2");
            Assert.assertNotNull(tempProfile);
            Assert.assertEquals("AutoScaleProfile2", tempProfile.name());
            Assert.assertEquals(3, tempProfile.defaultInstanceCount());
            Assert.assertEquals(5, tempProfile.maxInstanceCount());
            Assert.assertEquals(1, tempProfile.minInstanceCount());
            Assert.assertNull(tempProfile.fixedDateSchedule());
            Assert.assertNotNull(tempProfile.recurrentSchedule().schedule());
            Assert.assertEquals(1, tempProfile.recurrentSchedule().schedule().days().size());
            Assert.assertTrue(tempProfile.recurrentSchedule().schedule().days().contains(DayOfWeek.FRIDAY.toString()));
            Assert.assertEquals(1, tempProfile.recurrentSchedule().schedule().hours().size());
            Assert.assertEquals(1, tempProfile.recurrentSchedule().schedule().minutes().size());
            Assert.assertTrue(tempProfile.recurrentSchedule().schedule().hours().contains(12));
            Assert.assertTrue(tempProfile.recurrentSchedule().schedule().minutes().contains(13));
            Assert.assertTrue(tempProfile.recurrentSchedule().schedule().timeZone().equalsIgnoreCase("UTC"));

            Assert.assertNotNull(tempProfile.rules());
            Assert.assertEquals(1, tempProfile.rules().size());
            rule = tempProfile.rules().get(0);
            Assert.assertEquals(servicePlan.id(), rule.metricSource());
            Assert.assertEquals("CPUPercentage", rule.metricName());
            Assert.assertEquals(Period.minutes(10), rule.duration());
            Assert.assertEquals(Period.minutes(1), rule.frequency());
            Assert.assertEquals(MetricStatisticType.AVERAGE, rule.frequencyStatistic());
            Assert.assertEquals(ComparisonOperationType.LESS_THAN, rule.condition());
            Assert.assertEquals(TimeAggregationType.AVERAGE, rule.timeAggregation());
            Assert.assertEquals(20, rule.threshold(), 0.001);
            Assert.assertEquals(ScaleDirection.DECREASE, rule.scaleDirection());
            Assert.assertEquals(ScaleType.EXACT_COUNT, rule.scaleType());
            Assert.assertEquals(1, rule.scaleInstanceCount());
            Assert.assertEquals(Period.hours(3), rule.coolDown());

            AutoscaleSetting settingFromGet = monitorManager.autoscaleSettings().getById(setting.id());

            setting.update()
                    .defineAutoscaleProfile("very new profile")
                        .withScheduleBasedScale(10)
                        .withFixedDateSchedule("UTC", DateTime.now().minusDays(2), DateTime.now())
                        .attach()
                    .defineAutoscaleProfile("a new profile")
                        .withMetricBasedScale(5, 7, 6)
                        .defineScaleRule()
                            .withMetricSource(servicePlan.id())
                            .withMetricName("CPUPercentage")
                            .withStatistic(Period.hours(10), Period.hours(1), MetricStatisticType.AVERAGE)
                            .withCondition(ComparisonOperationType.LESS_THAN, TimeAggregationType.TOTAL, 6)
                            .withScaleAction(ScaleDirection.DECREASE, ScaleType.PERCENT_CHANGE_COUNT, 10, Period.hours(10))
                            .attach()
                        .attach()
                    .updateAutoscaleProfile("AutoScaleProfile2")
                        .updateScaleRule(0)
                            .withStatistic(Period.minutes(15), Period.minutes(1), MetricStatisticType.AVERAGE)
                            .parent()
                        .withFixedDateSchedule("UTC", DateTime.now().minusDays(2), DateTime.now())
                        .defineScaleRule()
                            .withMetricSource(servicePlan.id())
                            .withMetricName("CPUPercentage")
                            .withStatistic(Period.hours(5), Period.hours(3), MetricStatisticType.AVERAGE)
                            .withCondition(ComparisonOperationType.LESS_THAN, TimeAggregationType.TOTAL, 50)
                            .withScaleAction(ScaleDirection.DECREASE, ScaleType.PERCENT_CHANGE_COUNT, 25, Period.hours(2))
                        .attach()
                        .withoutScaleRule(1)
                        .parent()
                    .withAutoscaleEnabled()
                    .withoutCoAdminEmailNotification()
                    .apply();


            settingFromGet = monitorManager.autoscaleSettings().listByResourceGroup(RG_NAME).get(0);

            monitorManager.autoscaleSettings().deleteById(settingFromGet.id());
        }
        finally {
            resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
        }
    }
}

