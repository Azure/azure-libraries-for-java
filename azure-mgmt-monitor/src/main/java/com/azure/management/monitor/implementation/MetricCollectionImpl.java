/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.monitor.implementation;

import com.azure.management.monitor.Metric;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.azure.management.monitor.MetricCollection;
import com.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import org.joda.time.Period;

import java.util.List;

/**
 * The Azure {@link MetricCollection} wrapper class implementation.
 */
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
        return Lists.transform(this.inner().value(), new Function<MetricInner, Metric>() {
            @Override
            public Metric apply(MetricInner metricInner) {
                return  new MetricImpl(metricInner);
            }
        });
    }
}
