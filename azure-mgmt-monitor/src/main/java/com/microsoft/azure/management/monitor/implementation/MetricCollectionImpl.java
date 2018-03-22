/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.monitor.Metric;
import com.microsoft.azure.management.monitor.MetricCollection;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import org.joda.time.Period;

import java.util.List;

/**
 * The Azure {@link MetricCollection} wrapper class implementation.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Monitor.Fluent.Models")
class MetricCollectionImpl
        extends WrapperImpl<ResponseInner> implements MetricCollection {

    MetricCollectionImpl(ResponseInner innerObject) {
        super(innerObject);
    }

    @Override
    public String namespace() {
        return this.inner().namespace();
    }

    @Override
    public String resourceRegion() {
        return this.inner().resourceregion();
    }

    @Override
    public Double cost() {
        return this.inner().cost();
    }

    @Override
    public String timespan() {
        return this.inner().timespan();
    }

    @Override
    public Period interval() {
        return this.inner().interval();
    }

    @Override
    public List<Metric> metrics() {
        return this.inner().value();
    }
}
