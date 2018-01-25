/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.apigeneration.LangMethodDefinition;
import com.microsoft.azure.management.monitor.implementation.EventDataInner;
import org.joda.time.DateTime;

import java.util.Map;

/**
 * The Azure event log entries are of type EventData.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Monitor.Fluent.Models")
public interface EventData {

    /**
     * Get the authorization value.
     *
     * @return the authorization value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    SenderAuthorization authorization();

    /**
     * Get the claims value.
     *
     * @return the claims value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    Map<String, String> claims();

    /**
     * Get the caller value.
     *
     * @return the caller value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String caller();

    /**
     * Get the description value.
     *
     * @return the description value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String description();

    /**
     * Get the id value.
     *
     * @return the id value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String id();

    /**
     * Get the eventDataId value.
     *
     * @return the eventDataId value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String eventDataId();

    /**
     * Get the correlationId value.
     *
     * @return the correlationId value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String correlationId();

    /**
     * Get the eventName value.
     *
     * @return the eventName value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    LocalizableString eventName();

    /**
     * Get the category value.
     *
     * @return the category value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    LocalizableString category();

    /**
     * Get the httpRequest value.
     *
     * @return the httpRequest value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    HttpRequestInfo httpRequest();

    /**
     * Get the level value.
     *
     * @return the level value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    EventLevel level();

    /**
     * Get the resourceGroupName value.
     *
     * @return the resourceGroupName value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String resourceGroupName();

    /**
     * Get the resourceProviderName value.
     *
     * @return the resourceProviderName value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    LocalizableString resourceProviderName();

    /**
     * Get the resourceId value.
     *
     * @return the resourceId value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String resourceId();

    /**
     * Get the resourceType value.
     *
     * @return the resourceType value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    LocalizableString resourceType();

    /**
     * Get the operationId value.
     *
     * @return the operationId value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String operationId();

    /**
     * Get the operationName value.
     *
     * @return the operationName value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    LocalizableString operationName();

    /**
     * Get the properties value.
     *
     * @return the properties value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    Map<String, String> properties();

    /**
     * Get the status value.
     *
     * @return the status value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    LocalizableString status();

    /**
     * Get the subStatus value.
     *
     * @return the subStatus value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    LocalizableString subStatus();

    /**
     * Get the eventTimestamp value.
     *
     * @return the eventTimestamp value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    DateTime eventTimestamp();

    /**
     * Get the submissionTimestamp value.
     *
     * @return the submissionTimestamp value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    DateTime submissionTimestamp();

    /**
     * Get the subscriptionId value.
     *
     * @return the subscriptionId value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String subscriptionId();

    /**
     * Get the tenantId value.
     *
     * @return the tenantId value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String tenantId();
}
