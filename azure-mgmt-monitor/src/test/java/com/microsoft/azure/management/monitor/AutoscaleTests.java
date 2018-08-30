/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.appservice.AppServicePlan;
import com.microsoft.azure.management.appservice.OperatingSystem;
import com.microsoft.azure.management.appservice.PricingTier;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.rest.RestClient;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

public class AutoscaleTests extends MonitorManagementTest {
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
    public void canCRUDAutoscale() throws Exception {

        try {
            AppServicePlan servicePlan = appServiceManager.appServicePlans().define("HighlyAvailableWebApps")
                    .withRegion(Region.US_EAST2)
                    .withNewResourceGroup(RG_NAME)
                    .withPricingTier(PricingTier.PREMIUM_P1)
                    .withOperatingSystem(OperatingSystem.WINDOWS)
                    .create();

            AutoscaleSetting setting = monitorManager.autoscaleSettings()
                    .define("somesettingZ")
                    .withExistingResourceGroup(RG_NAME)
                    .withTargetResource(servicePlan.id())

                    .defineAutoscaleProfile("AutoScaleProfile1")
                        .withMetricBasedScale(1, 10, 1)
                        .defineScaleRule()
                            .withMetricSource(servicePlan.id())
                            .withMetricName("CPUPercentage", "Microsoft.Web/serverfarms")
                            .withStatistic(Period.minutes(10), Period.minutes(1), MetricStatisticType.AVERAGE)
                            .withCondition(ComparisonOperationType.GREATER_THAN, TimeAggregationType.AVERAGE, 70)
                            .withScaleAction(ScaleDirection.INCREASE, ScaleType.EXACT_COUNT, 10, Period.hours(12))
                            .attach()
                        .withFixedDateSchedule("EST", DateTime.now().minusDays(2), DateTime.now())
                        .attach()

                    .defineAutoscaleProfile("AutoScaleProfile2")
                        .withMetricBasedScale(1, 5, 3)
                        .defineScaleRule()
                            .withMetricSource(servicePlan.id())
                            .withMetricName("CPUPercentage", "Microsoft.Web/serverfarms")
                            .withStatistic(Period.minutes(10), Period.minutes(1), MetricStatisticType.AVERAGE)
                            .withCondition(ComparisonOperationType.LESS_THAN, TimeAggregationType.AVERAGE, 20)
                            .withScaleAction(ScaleDirection.DECREASE, ScaleType.EXACT_COUNT, 1, Period.hours(3))
                            .attach()
                        .withRecurrentSchedule("EST", "18:00", DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.SATURDAY)
                        .attach()

                    .defineAutoscaleProfile("ScheduleBased")
                        .withScheduleBasedScale(10)
                        .withRecurrentSchedule("EST", "18:00", DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.SATURDAY)
                        .attach()

                    .withAdminEmailNotification()
                    .withCoAdminEmailNotification()
                    .withCustomEmailNotification("me@mycorp.com;you@mycorp.com;him@mycorp.com")
                    .withAutoscaleDisabled()
                    .create();

            setting.update()
                    .defineAutoscaleProfile("very new profile")
                        .withScheduleBasedScale(10)
                        .withFixedDateSchedule("pst", DateTime.now().minusDays(2), DateTime.now())
                        .attach()
                    .defineAutoscaleProfile("a new profile")
                        .withMetricBasedScale(5, 7, 6)
                        .defineScaleRule()
                            .withMetricSource(servicePlan.id())
                            .withMetricName("CPUPercentage", "Microsoft.Web/serverfarms")
                            .withStatistic(Period.days(10), Period.days(1), MetricStatisticType.AVERAGE)
                            .withCondition(ComparisonOperationType.LESS_THAN, TimeAggregationType.TOTAL, 6)
                            .withScaleAction(ScaleDirection.DECREASE, ScaleType.PERCENT_CHANGE_COUNT, 10, Period.hours(10))
                            .attach()
                        .attach()
                    .updateAutoscaleProfile("AutoScaleProfile2")
                        .updateScaleRule(0)
                            .withStatistic(Period.minutes(15), Period.minutes(1), MetricStatisticType.AVERAGE)
                        .parent()
                        .withFixedDateSchedule("PST", DateTime.now().minusDays(2), DateTime.now())
                        .defineScaleRule()
                            .withMetricSource(servicePlan.id())
                            .withMetricName("CPUPercentage", "Microsoft.Web/serverfarms")
                            .withStatistic(Period.days(5), Period.days(3), MetricStatisticType.AVERAGE)
                            .withCondition(ComparisonOperationType.LESS_THAN, TimeAggregationType.TOTAL, 50)
                            .withScaleAction(ScaleDirection.DECREASE, ScaleType.PERCENT_CHANGE_COUNT, 25, Period.hours(2))
                        .attach()
                        .withoutScaleRule(1)
                        .parent()
                    .withAutoscaleEnabled()
                    .withoutCoAdminEmailNotification()
                    .apply();
        }
        finally {
            resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
        }
    }
}

