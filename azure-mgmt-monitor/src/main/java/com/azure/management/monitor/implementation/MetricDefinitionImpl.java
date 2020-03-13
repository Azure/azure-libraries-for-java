/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.monitor.implementation;
import com.azure.management.monitor.LocalizableString;
import com.azure.management.monitor.MetricAvailability;
import com.azure.management.monitor.MetricDefinition;
import com.azure.management.monitor.ResultType;
import com.azure.management.monitor.Unit;
import com.azure.management.monitor.AggregationType;
import com.azure.management.monitor.MetricCollection;
import com.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import java.time.OffsetDateTime;
import org.joda.time.Period;
import rx.Observable;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;

/**
 * The Azure metric definition entries are of type MetricDefinition.
 */
class MetricDefinitionImpl
        extends WrapperImpl<MetricDefinitionInner>
            implements
        MetricDefinition,
                MetricDefinition.MetricsQueryDefinition {

    private final MonitorManager myManager;
    private MetricDefinitionInner inner;
    private LocalizableString name;
    private List<LocalizableString> dimensions;
    private DateTime queryStartTime;
    private DateTime queryEndTime;
    private String aggreagation;
    private Period interval;
    private String odataFilter;
    private ResultType resultType;
    private Integer top;
    private String orderBy;
    private String namespaceFilter;

    MetricDefinitionImpl(final MetricDefinitionInner innerModel, final MonitorManager monitorManager) {
        super(innerModel);
        this.myManager = monitorManager;
        this.inner = innerModel;
        this.name = (inner.name() == null) ? null : new LocalizableStringImpl(inner.name());
        this.dimensions = null;
        if (this.inner.dimensions() != null
                && this.inner.dimensions().size() > 0) {
            this.dimensions = new ArrayList<>();
            for (LocalizableStringInner lsi : inner.dimensions()) {
                this.dimensions.add(new LocalizableStringImpl(lsi));
            }
        }
    }

    @Override
    public MonitorManager manager() {
        return this.myManager;
    }

    public String resourceId() {
        return this.inner.resourceId();
    }

    public LocalizableString name() {
        return this.name;
    }

    @Override
    public String namespace() {
        return this.inner.namespace();
    }

    @Override
    public boolean isDimensionRequired() {
        return this.inner.isDimensionRequired();
    }

    @Override
    public List<LocalizableString> dimensions() {
        return this.dimensions;
    }

    @Override
    public List<AggregationType> supportedAggregationTypes() {
        return this.inner.supportedAggregationTypes();
    }

    public Unit unit() {
        return this.inner.unit();
    }

    public AggregationType primaryAggregationType() {
        return this.inner.primaryAggregationType();
    }

    public List<MetricAvailability> metricAvailabilities() {
        return this.inner.metricAvailabilities();
    }

    public String id() {
        return this.inner.id();
    }

    @Override
    public MetricDefinitionImpl defineQuery() {
        this.aggreagation = null;
        this.interval = null;
        this.resultType = null;
        this.top = null;
        this.orderBy = null;
        this.namespaceFilter = null;
        return this;
    }

    @Override
    public MetricDefinitionImpl startingFrom(OffsetDateTime startTime) {
        this.queryStartTime = startTime;
        return this;
    }

    @Override
    public MetricDefinitionImpl endsBefore(OffsetDateTime endTime) {
        this.queryEndTime = endTime;
        return this;
    }

    @Override
    public MetricDefinitionImpl withAggregation(String aggregation) {
        this.aggreagation = aggregation;
        return this;
    }

    @Override
    public MetricDefinitionImpl withInterval(Period interval) {
        this.interval = interval;
        return this;
    }

    @Override
    public MetricDefinitionImpl withOdataFilter(String odataFilter) {
        this.odataFilter = odataFilter;
        return this;
    }

    @Override
    public MetricDefinitionImpl withResultType(ResultType resultType) {
        this.resultType = resultType;
        return this;
    }

    @Override
    public MetricDefinitionImpl selectTop(int top) {
        this.top = top;
        return this;
    }

    @Override
    public MetricDefinitionImpl orderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    @Override
    public MetricsQueryDefinitionStages.WithMetricsQueryExecute filterByNamespace(String namespaceName) {
        this.namespaceFilter = namespaceName;
        return this;
    }

    @Override
    public MetricCollection execute() {
        return this.executeAsync().toBlocking().last();
    }

    @Override
    public Observable<MetricCollection> executeAsync() {
        return this.manager().inner().metrics().listAsync(this.inner.resourceId(),
                String.format("%s/%s",
                        this.queryStartTime.withZone(OffsetDateTimeZone.UTC).toString(ISODateTimeFormat.dateTime()),
                        this.queryEndTime.withZone(OffsetDateTimeZone.UTC).toString(ISODateTimeFormat.dateTime())),
                this.interval,
                this.inner.name().value(),
                this.aggreagation,
                this.top,
                this.orderBy,
                this.odataFilter,
                this.resultType,
                this.namespaceFilter).map(new Func1<ResponseInner, MetricCollection>() {
                    @Override
                    public MetricCollection call(ResponseInner responseInner) {
                        return new MetricCollectionImpl(responseInner);
                    }
                });
    }
}
