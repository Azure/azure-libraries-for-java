/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.samples;

import com.azure.management.monitor.samples.AutoscaleSettingsBasedOnPerformanceOrSchedule;
import com.azure.management.monitor.samples.QueryMetricsAndActivityLogs;
import com.azure.management.monitor.samples.SecurityBreachOrRiskActivityLogAlerts;
import com.azure.management.monitor.samples.WebAppPerformanceMonitoringAlerts;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class MonitorTests extends SamplesTestBase {

    @Test
    @Ignore("Live only sample due to the need to query metrics at the current execution time which is always variable.")
    public void testQueryMetricsAndActivityLogs() {
        Assert.assertTrue(QueryMetricsAndActivityLogs.runSample(azure));
    }

    @Test
    @Ignore("Live only sample due to the need to query metrics at the current execution time which is always variable.")
    public void testSecurityBreachOrRiskActivityLogAlerts() {
        Assert.assertTrue(SecurityBreachOrRiskActivityLogAlerts.runSample(azure));
    }

    @Test
    public void testWebAppPerformanceMonitoringAlerts() {
        Assert.assertTrue(WebAppPerformanceMonitoringAlerts.runSample(azure));
    }

    @Test
    public void testAutoscaleSettingsBasedOnPerformanceOrSchedule() {
        Assert.assertTrue(AutoscaleSettingsBasedOnPerformanceOrSchedule.runSample(azure));
    }
}
