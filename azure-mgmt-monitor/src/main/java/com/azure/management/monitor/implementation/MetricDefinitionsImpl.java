/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.monitor.implementation;

import com.azure.management.monitor.MetricDefinition;
import com.azure.management.monitor.MetricDefinitions;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import rx.Observable;
import rx.functions.Func1;

import java.util.List;

/**
 * Implementation for {@link MetricDefinitions}.
 */
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
    public MetricDefinitionsInner inner() {
        return this.myManager.inner().metricDefinitions();
    }

    @Override
    public List<MetricDefinition> listByResource(String resourceId) {
        return Lists.transform(this.inner().list(resourceId), new Function<MetricDefinitionInner, MetricDefinition>() {
                    @Override
                    public MetricDefinition apply(MetricDefinitionInner metricDefinitionInner) {
                        return  new MetricDefinitionImpl(metricDefinitionInner, myManager);
                    }
                });
    }

    @Override
    public Observable<MetricDefinition> listByResourceAsync(String resourceId) {
        return this.inner().listAsync(resourceId)
                .flatMapIterable(new Func1<List<MetricDefinitionInner>, Iterable<MetricDefinitionInner>>() {
                    @Override
                    public Iterable<MetricDefinitionInner> call(List<MetricDefinitionInner> items) {
                        return items;
                    }
                }).map(new Func1<MetricDefinitionInner, MetricDefinition>() {
                    @Override
                    public MetricDefinition call(MetricDefinitionInner metricDefinitionInner) {
                        return new MetricDefinitionImpl(metricDefinitionInner, myManager);
                    }
                });
    }
}
