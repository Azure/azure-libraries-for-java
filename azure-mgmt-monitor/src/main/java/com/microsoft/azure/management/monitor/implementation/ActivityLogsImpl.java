/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor.implementation;

import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.monitor.ActivityLogs;
import com.microsoft.azure.management.monitor.EventData;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
import rx.Observable;
import rx.functions.Func1;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation for {@link ActivityLogs}.
 */
@LangDefinition
class ActivityLogsImpl
    implements ActivityLogs,
        ActivityLogs.Definition {

    private final MonitorManager myManager;
    private DateTime queryStartTime;
    private DateTime queryEndTime;
    private Set<String> responsePropertySelector;
    private String filterString;

    ActivityLogsImpl(final MonitorManager monitorManager) {
        this.myManager = monitorManager;
        this.responsePropertySelector = new HashSet<>();
        this.filterString = "";
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
    public FilterDefinitionStages.WithStartTimeFilter defineQuery() {
        this.responsePropertySelector = new HashSet<>();
        this.filterString = "";
        return this;
    }

    @Override
    public FilterDefinitionStages.WithEndFilter withStartTime(DateTime startTime) {
        this.queryStartTime = startTime;
        return this;
    }

    @Override
    public FilterDefinitionStages.WithFieldFilter withEndTime(DateTime endTime) {
        this.queryEndTime = endTime;
        return this;
    }

    @Override
    public FilterDefinitionStages.WithSelect withAllPropertiesInResponse() {
        this.responsePropertySelector.clear();
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition defineResponseProperties() {
        this.responsePropertySelector.clear();
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition withAuthorization() {
        this.responsePropertySelector.add("authorization");
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition withClaims() {
        this.responsePropertySelector.add("claims");
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition withCorrelationId() {
        this.responsePropertySelector.add("correlationId");
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition withDescription() {
        this.responsePropertySelector.add("description");
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition withEventDataId() {
        this.responsePropertySelector.add("eventDataId");
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition withEventName() {
        this.responsePropertySelector.add("eventName");
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition withEventTimestamp() {
        this.responsePropertySelector.add("eventTimestamp");
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition withHttpRequest() {
        this.responsePropertySelector.add("httpRequest");
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition withLevel() {
        this.responsePropertySelector.add("level");
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition withOperationId() {
        this.responsePropertySelector.add("operationId");
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition withOperationName() {
        this.responsePropertySelector.add("operationName");
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition withProperties() {
        this.responsePropertySelector.add("properties");
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition withResourceGroupName() {
        this.responsePropertySelector.add("resourceGroupName");
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition withResourceProviderName() {
        this.responsePropertySelector.add("resourceProviderName");
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition withResourceId() {
        this.responsePropertySelector.add("resourceId");
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition withStatus() {
        this.responsePropertySelector.add("status");
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition withSubmissionTimestamp() {
        this.responsePropertySelector.add("submissionTimestamp");
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition withSubStatus() {
        this.responsePropertySelector.add("subStatus");
        return this;
    }

    @Override
    public FilterDefinitionStages.WithResponsePropertyDefinition withSubscriptionId() {
        this.responsePropertySelector.add("subscriptionId");
        return this;
    }

    @Override
    public FilterDefinitionStages.WithSelect apply() {
        return this;
    }

    @Override
    public FilterDefinitionStages.WithExecute filterByResourceGroup(String resourceGroupName) {
        this.filterString = String.format(" and resourceGroupName eq '%s'", resourceGroupName);
        return this;
    }

    @Override
    public FilterDefinitionStages.WithExecute filterByResource(String resourceId) {
        this.filterString = String.format(" and resourceUri eq '%s'", resourceId);
        return this;
    }

    @Override
    public FilterDefinitionStages.WithExecute filterByResourceProvider(String resourceProviderName) {
        this.filterString = String.format(" and resourceProvider eq '%s'", resourceProviderName);
        return this;
    }

    @Override
    public FilterDefinitionStages.WithExecute filterByCorrelationId(String correlationId) {
        this.filterString = String.format(" and correlationId eq '%s'", correlationId);
        return this;
    }

    @Override
    public PagedList<EventData> execute() {
        return listEventData(getOdataFilterString() + this.filterString);
    }

    @Override
    public Observable<EventData> executeAsync() {
        return listEventDataAsync(getOdataFilterString() + this.filterString);
    }
    private String getOdataFilterString() {
        return String.format("eventTimestamp ge '%s' and eventTimestamp le '%s'",
                this.queryStartTime.withZone(DateTimeZone.UTC).toString(ISODateTimeFormat.dateTime()),
                this.queryEndTime.withZone(DateTimeZone.UTC).toString(ISODateTimeFormat.dateTime()));
    }

    private PagedList<EventData> listEventData(String filter) {
        return (new PagedListConverter<EventDataInner, EventData>() {
            @Override
            public Observable<EventData> typeConvertAsync(EventDataInner inner) {
                return Observable.just((EventData) new EventData(inner));
            }
        }).convert(this.inner().activityLogs().list(filter, StringUtils.join(this.responsePropertySelector, ',')));
    }

    private Observable<EventData> listEventDataAsync(String filter) {
        return this.inner().activityLogs().listAsync(filter, StringUtils.join(this.responsePropertySelector, ','))
                .flatMap(new Func1<Page<EventDataInner>, Observable<EventData>>() {
                    @Override
                    public Observable<EventData> call(Page<EventDataInner> eventDataInnerPage) {
                        return Observable.from(eventDataInnerPage.items())
                                .map(new Func1<EventDataInner, EventData>() {
                                    @Override
                                    public EventData call(EventDataInner eventDataInner) {
                                        return new EventData(eventDataInner);
                                    }
                                });
                    }
                });
    }
}
