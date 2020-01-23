// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The ConnectionMonitorSource model.
 */
@Fluent
public final class ConnectionMonitorSource {
    /*
     * The ID of the resource used as the source by connection monitor.
     */
    @JsonProperty(value = "resourceId", required = true)
    private String resourceId;

    /*
     * The source port used by connection monitor.
     */
    @JsonProperty(value = "port")
    private Integer port;

    /**
     * Get the resourceId property: The ID of the resource used as the source
     * by connection monitor.
     * 
     * @return the resourceId value.
     */
    public String getResourceId() {
        return this.resourceId;
    }

    /**
     * Set the resourceId property: The ID of the resource used as the source
     * by connection monitor.
     * 
     * @param resourceId the resourceId value to set.
     * @return the ConnectionMonitorSource object itself.
     */
    public ConnectionMonitorSource setResourceId(String resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    /**
     * Get the port property: The source port used by connection monitor.
     * 
     * @return the port value.
     */
    public Integer getPort() {
        return this.port;
    }

    /**
     * Set the port property: The source port used by connection monitor.
     * 
     * @param port the port value to set.
     * @return the ConnectionMonitorSource object itself.
     */
    public ConnectionMonitorSource setPort(Integer port) {
        this.port = port;
        return this;
    }
}
