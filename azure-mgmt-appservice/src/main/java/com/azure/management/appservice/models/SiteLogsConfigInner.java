// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.appservice.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.azure.management.appservice.ApplicationLogsConfig;
import com.azure.management.appservice.EnabledConfig;
import com.azure.management.appservice.HttpLogsConfig;
import com.azure.management.appservice.ProxyOnlyResource;

/**
 * The SiteLogsConfig model.
 */
@JsonFlatten
@Fluent
public class SiteLogsConfigInner extends ProxyOnlyResource {
    /*
     * Application logs configuration.
     */
    @JsonProperty(value = "properties.applicationLogs")
    private ApplicationLogsConfig applicationLogs;

    /*
     * Http logs configuration.
     */
    @JsonProperty(value = "properties.httpLogs")
    private HttpLogsConfig httpLogs;

    /*
     * Enabled configuration.
     */
    @JsonProperty(value = "properties.failedRequestsTracing")
    private EnabledConfig failedRequestsTracing;

    /*
     * Enabled configuration.
     */
    @JsonProperty(value = "properties.detailedErrorMessages")
    private EnabledConfig detailedErrorMessages;

    /**
     * Get the applicationLogs property: Application logs configuration.
     * 
     * @return the applicationLogs value.
     */
    public ApplicationLogsConfig applicationLogs() {
        return this.applicationLogs;
    }

    /**
     * Set the applicationLogs property: Application logs configuration.
     * 
     * @param applicationLogs the applicationLogs value to set.
     * @return the SiteLogsConfigInner object itself.
     */
    public SiteLogsConfigInner withApplicationLogs(ApplicationLogsConfig applicationLogs) {
        this.applicationLogs = applicationLogs;
        return this;
    }

    /**
     * Get the httpLogs property: Http logs configuration.
     * 
     * @return the httpLogs value.
     */
    public HttpLogsConfig httpLogs() {
        return this.httpLogs;
    }

    /**
     * Set the httpLogs property: Http logs configuration.
     * 
     * @param httpLogs the httpLogs value to set.
     * @return the SiteLogsConfigInner object itself.
     */
    public SiteLogsConfigInner withHttpLogs(HttpLogsConfig httpLogs) {
        this.httpLogs = httpLogs;
        return this;
    }

    /**
     * Get the failedRequestsTracing property: Enabled configuration.
     * 
     * @return the failedRequestsTracing value.
     */
    public EnabledConfig failedRequestsTracing() {
        return this.failedRequestsTracing;
    }

    /**
     * Set the failedRequestsTracing property: Enabled configuration.
     * 
     * @param failedRequestsTracing the failedRequestsTracing value to set.
     * @return the SiteLogsConfigInner object itself.
     */
    public SiteLogsConfigInner withFailedRequestsTracing(EnabledConfig failedRequestsTracing) {
        this.failedRequestsTracing = failedRequestsTracing;
        return this;
    }

    /**
     * Get the detailedErrorMessages property: Enabled configuration.
     * 
     * @return the detailedErrorMessages value.
     */
    public EnabledConfig detailedErrorMessages() {
        return this.detailedErrorMessages;
    }

    /**
     * Set the detailedErrorMessages property: Enabled configuration.
     * 
     * @param detailedErrorMessages the detailedErrorMessages value to set.
     * @return the SiteLogsConfigInner object itself.
     */
    public SiteLogsConfigInner withDetailedErrorMessages(EnabledConfig detailedErrorMessages) {
        this.detailedErrorMessages = detailedErrorMessages;
        return this;
    }
}