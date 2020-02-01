// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.management.network.ConnectionMonitorDestination;
import com.azure.management.network.ConnectionMonitorSource;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * The ConnectionMonitor model.
 */
@JsonFlatten
@Fluent
public class ConnectionMonitorInner {
    /*
     * Connection monitor location.
     */
    @JsonProperty(value = "location")
    private String location;

    /*
     * Connection monitor tags.
     */
    @JsonProperty(value = "tags")
    private Map<String, String> tags;

    /*
     * Describes the source of connection monitor.
     */
    @JsonProperty(value = "properties.source", required = true)
    private ConnectionMonitorSource source;

    /*
     * Describes the destination of connection monitor.
     */
    @JsonProperty(value = "properties.destination", required = true)
    private ConnectionMonitorDestination destination;

    /*
     * Determines if the connection monitor will start automatically once
     * created.
     */
    @JsonProperty(value = "properties.autoStart")
    private Boolean autoStart;

    /*
     * Monitoring interval in seconds.
     */
    @JsonProperty(value = "properties.monitoringIntervalInSeconds")
    private Integer monitoringIntervalInSeconds;

    /**
     * Get the location property: Connection monitor location.
     * 
     * @return the location value.
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Set the location property: Connection monitor location.
     * 
     * @param location the location value to set.
     * @return the ConnectionMonitorInner object itself.
     */
    public ConnectionMonitorInner setLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * Get the tags property: Connection monitor tags.
     * 
     * @return the tags value.
     */
    public Map<String, String> getTags() {
        return this.tags;
    }

    /**
     * Set the tags property: Connection monitor tags.
     * 
     * @param tags the tags value to set.
     * @return the ConnectionMonitorInner object itself.
     */
    public ConnectionMonitorInner setTags(Map<String, String> tags) {
        this.tags = tags;
        return this;
    }

    /**
     * Get the source property: Describes the source of connection monitor.
     * 
     * @return the source value.
     */
    public ConnectionMonitorSource getSource() {
        return this.source;
    }

    /**
     * Set the source property: Describes the source of connection monitor.
     * 
     * @param source the source value to set.
     * @return the ConnectionMonitorInner object itself.
     */
    public ConnectionMonitorInner setSource(ConnectionMonitorSource source) {
        this.source = source;
        return this;
    }

    /**
     * Get the destination property: Describes the destination of connection
     * monitor.
     * 
     * @return the destination value.
     */
    public ConnectionMonitorDestination getDestination() {
        return this.destination;
    }

    /**
     * Set the destination property: Describes the destination of connection
     * monitor.
     * 
     * @param destination the destination value to set.
     * @return the ConnectionMonitorInner object itself.
     */
    public ConnectionMonitorInner setDestination(ConnectionMonitorDestination destination) {
        this.destination = destination;
        return this;
    }

    /**
     * Get the autoStart property: Determines if the connection monitor will
     * start automatically once created.
     * 
     * @return the autoStart value.
     */
    public Boolean isAutoStart() {
        return this.autoStart;
    }

    /**
     * Set the autoStart property: Determines if the connection monitor will
     * start automatically once created.
     * 
     * @param autoStart the autoStart value to set.
     * @return the ConnectionMonitorInner object itself.
     */
    public ConnectionMonitorInner setAutoStart(Boolean autoStart) {
        this.autoStart = autoStart;
        return this;
    }

    /**
     * Get the monitoringIntervalInSeconds property: Monitoring interval in
     * seconds.
     * 
     * @return the monitoringIntervalInSeconds value.
     */
    public Integer getMonitoringIntervalInSeconds() {
        return this.monitoringIntervalInSeconds;
    }

    /**
     * Set the monitoringIntervalInSeconds property: Monitoring interval in
     * seconds.
     * 
     * @param monitoringIntervalInSeconds the monitoringIntervalInSeconds value
     * to set.
     * @return the ConnectionMonitorInner object itself.
     */
    public ConnectionMonitorInner setMonitoringIntervalInSeconds(Integer monitoringIntervalInSeconds) {
        this.monitoringIntervalInSeconds = monitoringIntervalInSeconds;
        return this;
    }
}
