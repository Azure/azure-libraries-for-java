/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor.implementation;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.monitor.MetricDefinition;
import com.microsoft.azure.management.monitor.MetricDefinitions;
import rx.Observable;
import rx.functions.Func1;

import java.util.List;

/**
 * Implementation for {@link MetricDefinitions}.
 */
@LangDefinition
class MetricDefinitionsImpl
    implements MetricDefinitions {

    private final MonitorManager myManager;

    MetricDefinitionsImpl(final MonitorManager monitorManager) {
        this.myManager = monitorManager;
    }

    @Override
    public MonitorManager manager() {
        return this.myManager;
    }

    @Override
    public MonitorManagementClientImpl inner() {
        return this.myManager.inner();
    }

    @Override
    public List<MetricDefinition> listByResource(String resourceId) {
        return this.listByResourceAsync(resourceId).toBlocking().last();
    }

    @Override
    public Observable<List<MetricDefinition>> listByResourceAsync(String resourceId) {
        return this.inner().metricDefinitions().listAsync(resourceId)
                .map(new Func1<List<MetricDefinitionInner>, List<MetricDefinition>>() {
                    @Override
                    public List<MetricDefinition> call(List<MetricDefinitionInner> metricDefinitionInners) {
                        return Lists.transform(metricDefinitionInners, new Function<MetricDefinitionInner, MetricDefinition>() {
                            @Override
                            public MetricDefinition apply(MetricDefinitionInner metricDefinitionInner) {
                                return new MetricDefinitionImpl(metricDefinitionInner, myManager);
                            }
                        });
                    }
                });
    }
}
