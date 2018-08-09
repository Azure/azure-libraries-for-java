/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.monitor.MetricAlert;
import com.microsoft.azure.management.monitor.MetricAlerts;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.TopLevelModifiableResourcesImpl;

/**
 * Implementation for {@link MetricAlerts}.
 */
@LangDefinition
class MetricAlertsImpl
        extends TopLevelModifiableResourcesImpl<
                        MetricAlert,
                        MetricAlertImpl,
                        MetricAlertResourceInner,
                        MetricAlertsInner,
                        MonitorManager>
        implements MetricAlerts {

    MetricAlertsImpl(final MonitorManager monitorManager) {
        super(monitorManager.inner().metricAlerts(), monitorManager);
    }

    @Override
    protected MetricAlertImpl wrapModel(String name) {
        return new MetricAlertImpl(name, new MetricAlertResourceInner(), this.manager());
    }

    @Override
    protected MetricAlertImpl wrapModel(MetricAlertResourceInner inner) {
        if (inner ==  null) {
            return null;
        }
        return new MetricAlertImpl(inner.name(), inner, this.manager());
    }

    @Override
    public MetricAlertImpl define(String name) {
        return wrapModel(name);
    }
}
