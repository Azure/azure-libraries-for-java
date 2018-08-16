/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor.samples;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.appservice.AppServicePlan;
import com.microsoft.azure.management.appservice.OperatingSystem;
import com.microsoft.azure.management.appservice.PricingTier;
import com.microsoft.azure.management.monitor.ActionGroup;
import com.microsoft.azure.management.monitor.ActivityLogAlert;
import com.microsoft.azure.management.monitor.EventData;
import com.microsoft.azure.management.monitor.MetricAlert;
import com.microsoft.azure.management.monitor.MetricAlertRuleCondition;
import com.microsoft.azure.management.monitor.MetricAlertRuleTimeAggregation;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.azure.management.storage.AccessTier;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.azure.management.storage.StorageAccountKey;
import com.microsoft.rest.LogLevel;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.io.File;
import java.util.List;

/**
 * This sample shows examples of configuring Metric Alerts for WebApp instance performance monitoring through app service plan.
 *  - Create a App Service plan
 *  - Setup an action group to trigger a notification to the heavy performance alerts
 *  - Create auto-mitigated metric alerts for the App Service plan when
 *    - average CPUPercentage on any of Web App instance (where Instance = *) over the last 5 minutes is above 80%
 *    - average MemoryPercentage on three of my instances (where Instance = "RD00155D44CA4E", "RD07893F35CE3D", "RD00093E32CE8F") over the last 5 minutes is above 90%
 */
public final class WebAppPerformanceMonitoringAlerts {

    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        final String rgName = Utils.createRandomName("rgMonitor");

        try {
            // ============================================================
            // Create an App Service plan
            System.out.println("Creating App Service plan");

            AppServicePlan servicePlan = azure.appServices().appServicePlans().define("HighlyAvailableWebApps")
                    .withRegion(Region.US_EAST2)
                    .withNewResourceGroup(rgName)
                    .withPricingTier(PricingTier.PREMIUM_P1)
                    .withOperatingSystem(OperatingSystem.WINDOWS)
                    .create();

            System.out.println("App Service plan created:");
            Utils.print(servicePlan);

            // ============================================================
            // Create an action group to send notifications in case metric alert condition will be triggered
            ActionGroup ag = azure.actionGroups().define("criticalPerformanceActionGroup")
                    .withExistingResourceGroup(rgName)
                    .defineReceiver("tierOne")
                        .withAzureAppPush("ops_on_duty@performancemonitoring.com")
                        .withEmail("ops_on_duty@performancemonitoring.com")
                        .withSms("1", "4255655665")
                        .withVoice("1", "2062066050")
                        .withWebhook("https://www.weeneedmorepower.performancemonitoring.com")
                    .attach()
                    .defineReceiver("tierTwo")
                        .withEmail("ceo@performancemonitoring.com")
                        .attach()
                    .create();
            Utils.print(ag);

            // ============================================================
            // Set a trigger to fire each time
            MetricAlert ma = azure.alertRules().metricAlerts().define("Critical performance alert")
                    .withExistingResourceGroup(rgName)
                    .withTargetResource(servicePlan.id())
                    .withWindowSize(Period.minutes(5))
                    .withEvaluationFrequency(Period.minutes(1))
                    .withSeverity(3)
                    .withDescription("This alert rule is for U5 – Single resource-multiple criteria – with dimensions – with star")
                    .withRuleEnabled()
                    .withActionGroups(ag.id())
                    .defineAlertCriteria("Metric1")
                        .withSignalName("CPUPercentage")
                        .withCondition(MetricAlertRuleCondition.GREATER_THAN, MetricAlertRuleTimeAggregation.TOTAL, 80)
                        .withMetricNamespace("Microsoft.Web/serverfarms")
                        .withDimensionFilter("Instance", "*")
                        .attach()
                    .defineAlertCriteria("Metric2")
                        .withSignalName("MemoryPercentage")
                        .withCondition(MetricAlertRuleCondition.GREATER_THAN, MetricAlertRuleTimeAggregation.TOTAL, 90)
                        .withMetricNamespace("Microsoft.Web/serverfarms")
                        .withDimensionFilter("Instance", "RD00155D44CA4E", "RD07893F35CE3D", "RD00093E32CE8F")
                        .attach()
                    .withAutoMitigation()
                    .create();
            Utils.print(ma);

            return true;
        } catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        } finally {
            if (azure.resourceGroups().getByName(rgName) != null) {
                System.out.println("Deleting Resource Group: " + rgName);
                azure.resourceGroups().deleteByName(rgName);
                System.out.println("Deleted Resource Group: " + rgName);
            } else {
                System.out.println("Did not create any resources in Azure. No clean up is necessary");
            }
        }
        return false;
    }

    /**
     * Main entry point.
     * @param args the parameters
     */
    public static void main(String[] args) {
        try {

            final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));

            Azure azure = Azure.configure()
                    .withLogLevel(LogLevel.NONE)
                    .authenticate(credFile)
                    .withDefaultSubscription();

            // Print selected subscription
            System.out.println("Selected subscription: " + azure.subscriptionId());

            runSample(azure);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
