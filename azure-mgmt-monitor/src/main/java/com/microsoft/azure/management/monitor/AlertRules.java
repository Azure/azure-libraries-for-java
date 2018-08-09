/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.monitor.implementation.MonitorManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;

/**
 *  Entry point to AlertRules management API.
 */
@Fluent
public interface AlertRules extends
        HasManager<MonitorManager> {

    MetricAlerts metricAlerts();

    ActivityLogAlerts activityLogAlerts();
}
