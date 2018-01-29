/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.monitor.implementation.MetricDefinitionsInner;
import com.microsoft.azure.management.monitor.implementation.MonitorManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import rx.Observable;

import java.util.List;


/**
 * Entry point for Monitor Metric Definitions API.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Monitor.Fluent")
public interface MetricDefinitions extends
        HasManager<MonitorManager>,
        HasInner<MetricDefinitionsInner> {

    /**
     * Lists Metric Definitions for a given resource.
     *
     * @param resourceId The resource Id.
     * @return list of metric definitions.
     */
    List<MetricDefinition> listByResource(String resourceId);

    /**
     * Lists Metric Definitions for a given resource.
     *
     * @param resourceId The resource Id.
     * @return a representation of the deferred computation of Metric Definitions list call.
     */
    Observable<MetricDefinition> listByResourceAsync(String resourceId);
}
