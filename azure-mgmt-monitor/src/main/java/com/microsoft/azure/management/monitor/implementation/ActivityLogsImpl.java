/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor.implementation;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.monitor.EventDataPropertyName;
import com.microsoft.azure.management.monitor.ActivityLogs;
import com.microsoft.azure.management.monitor.EventData;
import com.microsoft.azure.management.monitor.LocalizableString;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
import rx.Observable;
import rx.functions.Func1;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 * Implementation for {@link ActivityLogs}.
 */
@LangDefinition
class ActivityLogsImpl
    implements ActivityLogs,
        ActivityLogs.ActivityLogsQueryDefinition {

    private final MonitorManager myManager;
    private DateTime queryStartTime;
    private DateTime queryEndTime;
    private TreeSet<String> responsePropertySelector;
    private String filterString;
    private boolean filterForTenant;

    ActivityLogsImpl(final MonitorManager monitorManager) {
        this.myManager = monitorManager;
        this.responsePropertySelector = new TreeSet<>();
        this.filterString = "";
        this.filterForTenant = false;
    }

    @Override
    public MonitorManager manager() {
        return this.myManager;
    }

    @Override
    public ActivityLogsInner inner() {
        return this.myManager.inner().activityLogs();
    }

    @Override
    public List<LocalizableString> listEventCategories() {
        return Lists.transform(this.manager().inner().eventCategories().list(), new Function<LocalizableStringInner, LocalizableString>() {
            @Override
            public LocalizableString apply(LocalizableStringInner input) {
                return new LocalizableStringImpl(input);
            }
        });
    }

    @Override
    public Observable<LocalizableString> listEventCategoriesAsync() {
        return this.manager().inner().eventCategories().listAsync()
                .flatMap(new Func1<List<LocalizableStringInner>, Observable<LocalizableString>>() {
                    @Override
                    public Observable<LocalizableString> call(List<LocalizableStringInner> localizableStringInners) {
                        return Observable.from(localizableStringInners)
                                .map(new Func1<LocalizableStringInner, LocalizableString>() {
                                    @Override
                                    public LocalizableString call(LocalizableStringInner localizableStringInner) {
                                        return new LocalizableStringImpl(localizableStringInner);
                                    }
                                });
                    }
                });
    }

    @Override
    public ActivityLogsQueryDefinitionStages.WithEventDataStartTimeFilter defineQuery() {
        this.responsePropertySelector.clear();
        this.filterString = "";
        this.filterForTenant = false;
        return this;
    }

    @Override
    public ActivityLogsImpl startingFrom(DateTime startTime) {
        this.queryStartTime = startTime;
        return this;
    }

    @Override
    public ActivityLogsImpl endsBefore(DateTime endTime) {
        this.queryEndTime = endTime;
        return this;
    }

    @Override
    public ActivityLogsImpl withAllPropertiesInResponse() {
        this.responsePropertySelector.clear();
        return this;
    }

    @Override
    public ActivityLogsImpl withResponseProperties(EventDataPropertyName... responseProperties) {
        this.responsePropertySelector.clear();

        this.responsePropertySelector.addAll(Lists.transform(Arrays.asList(responseProperties), new Function<EventDataPropertyName, String>() {
            @Override
            public String apply(EventDataPropertyName eventDataPropertyName) {
                return eventDataPropertyName.toString();
            }
        }));
        return this;
    }

    @Override
    public ActivityLogsImpl filterByResourceGroup(String resourceGroupName) {
        this.filterString = String.format(" and resourceGroupName eq '%s'", resourceGroupName);
        return this;
    }

    @Override
    public ActivityLogsImpl filterByResource(String resourceId) {
        this.filterString = String.format(" and resourceUri eq '%s'", resourceId);
        return this;
    }

    @Override
    public ActivityLogsImpl filterByResourceProvider(String resourceProviderName) {
        this.filterString = String.format(" and resourceProvider eq '%s'", resourceProviderName);
        return this;
    }

    @Override
    public ActivityLogsImpl filterByCorrelationId(String correlationId) {
        this.filterString = String.format(" and correlationId eq '%s'", correlationId);
        return this;
    }

    @Override
    public ActivityLogsImpl filterAtTenantLevel() {
        this.filterForTenant = true;
        return this;
    }

    @Override
    public PagedList<EventData> execute() {
        if (this.filterForTenant) {
            return listEventDataForTenant(getOdataFilterString() + this.filterString + " eventChannels eq 'Admin, Operation'");
        }
        return listEventData(getOdataFilterString() + this.filterString);
    }

    @Override
    public Observable<EventData> executeAsync() {
        if (this.filterForTenant) {
            return listEventDataForTenantAsync(getOdataFilterString() + this.filterString + " eventChannels eq 'Admin, Operation'");
        }
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
                return Observable.just((EventData) new EventDataImpl(inner));
            }
        }).convert(this.inner().list(filter, createPropertyFilter()));
    }

    private PagedList<EventData> listEventDataForTenant(String filter) {
        return (new PagedListConverter<EventDataInner, EventData>() {
            @Override
            public Observable<EventData> typeConvertAsync(EventDataInner inner) {
                return Observable.just((EventData) new EventDataImpl(inner));
            }
        }).convert(this.manager().inner().tenantActivityLogs().list(filter, createPropertyFilter()));
    }

    private Observable<EventData> listEventDataAsync(String filter) {
        return this.inner().listAsync(filter, createPropertyFilter())
                .flatMap(new Func1<Page<EventDataInner>, Observable<EventData>>() {
                    @Override
                    public Observable<EventData> call(Page<EventDataInner> eventDataInnerPage) {
                        return Observable.from(eventDataInnerPage.items())
                                .map(new Func1<EventDataInner, EventData>() {
                                    @Override
                                    public EventData call(EventDataInner eventDataInner) {
                                        return new EventDataImpl(eventDataInner);
                                    }
                                });
                    }
                });
    }

    private Observable<EventData> listEventDataForTenantAsync(String filter) {
        return this.manager().inner().tenantActivityLogs().listAsync(filter, createPropertyFilter())
                .flatMap(new Func1<Page<EventDataInner>, Observable<EventData>>() {
                    @Override
                    public Observable<EventData> call(Page<EventDataInner> eventDataInnerPage) {
                        return Observable.from(eventDataInnerPage.items())
                                .map(new Func1<EventDataInner, EventData>() {
                                    @Override
                                    public EventData call(EventDataInner eventDataInner) {
                                        return new EventDataImpl(eventDataInner);
                                    }
                                });
                    }
                });
    }

    private String createPropertyFilter() {
        String propertyFilter = StringUtils.join(this.responsePropertySelector, ',');
        if (propertyFilter != null && propertyFilter.trim().isEmpty()) {
            propertyFilter = null;
        }
        return propertyFilter;
    }
}
