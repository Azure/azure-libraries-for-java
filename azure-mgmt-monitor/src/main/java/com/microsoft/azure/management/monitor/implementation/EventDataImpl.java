/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.monitor.EventData;
import com.microsoft.azure.management.monitor.EventLevel;
import com.microsoft.azure.management.monitor.HttpRequestInfo;
import com.microsoft.azure.management.monitor.LocalizableString;
import com.microsoft.azure.management.monitor.SenderAuthorization;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import org.joda.time.DateTime;

import java.util.Map;

/**
 * The Azure {@link EventData} wrapper class implementation.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Monitor.Fluent.Models")
class EventDataImpl
        extends WrapperImpl<EventDataInner> implements EventData {
    private LocalizableString eventName;
    private LocalizableString category;
    private LocalizableString resourceProviderName;
    private LocalizableString resourceType;
    private LocalizableString operationName;
    private LocalizableString status;
    private LocalizableString subStatus;

    EventDataImpl(EventDataInner innerObject) {
        super(innerObject);
        this.eventName = (inner().eventName() == null) ? null : new LocalizableStringImpl(inner().eventName());
        this.category = (inner().category() == null) ? null : new LocalizableStringImpl(inner().category());
        this.resourceProviderName = (inner().resourceProviderName() == null) ? null : new LocalizableStringImpl(inner().resourceProviderName());
        this.resourceType = (inner().resourceType() == null) ? null : new LocalizableStringImpl(inner().resourceType());
        this.operationName = (inner().operationName() == null) ? null : new LocalizableStringImpl(inner().operationName());
        this.status = (inner().status() == null) ? null : new LocalizableStringImpl(inner().status());
        this.subStatus = (inner().subStatus() == null) ? null : new LocalizableStringImpl(inner().subStatus());
    }

    @Override
    public SenderAuthorization authorization() {
        return this.inner().authorization();
    }

    @Override
    public Map<String, String> claims() {
        return this.inner().claims();
    }

    @Override
    public String caller() {
        return this.inner().caller();
    }

    @Override
    public String description() {
        return this.inner().description();
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public String eventDataId() {
        return this.inner().eventDataId();
    }

    @Override
    public String correlationId() {
        return this.inner().correlationId();
    }

    @Override
    public LocalizableString eventName() {
        return this.eventName;
    }

    @Override
    public LocalizableString category() {
        return this.category;
    }

    @Override
    public HttpRequestInfo httpRequest() {
        return this.inner().httpRequest();
    }

    @Override
    public EventLevel level() {
        return this.inner().level();
    }

    @Override
    public String resourceGroupName() {
        return this.inner().resourceGroupName();
    }

    @Override
    public LocalizableString resourceProviderName() {
        return this.resourceProviderName;
    }

    @Override
    public String resourceId() {
        return this.inner().resourceId();
    }

    @Override
    public LocalizableString resourceType() {
        return this.resourceType;
    }

    @Override
    public String operationId() {
        return this.inner().operationId();
    }

    @Override
    public LocalizableString operationName() {
        return this.operationName;
    }

    @Override
    public Map<String, String> properties() {
        return this.inner().properties();
    }

    @Override
    public LocalizableString status() {
        return this.status;
    }

    @Override
    public LocalizableString subStatus() {
        return this.subStatus;
    }

    @Override
    public DateTime eventTimestamp() {
        return this.inner().eventTimestamp();
    }

    @Override
    public DateTime submissionTimestamp() {
        return this.inner().submissionTimestamp();
    }

    @Override
    public String subscriptionId() {
        return this.inner().subscriptionId();
    }

    @Override
    public String tenantId() {
        return this.inner().tenantId();
    }
}
