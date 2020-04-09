// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.cdn.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.Resource;
import com.azure.management.cdn.OriginResourceState;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Origin model.
 */
@JsonFlatten
@Fluent
public class OriginInner extends Resource {
    /*
     * The address of the origin. Domain names, IPv4 addresses, and IPv6
     * addresses are supported.
     */
    @JsonProperty(value = "properties.hostName")
    private String hostName;

    /*
     * The value of the HTTP port. Must be between 1 and 65535.
     */
    @JsonProperty(value = "properties.httpPort")
    private Integer httpPort;

    /*
     * The value of the https port. Must be between 1 and 65535.
     */
    @JsonProperty(value = "properties.httpsPort")
    private Integer httpsPort;

    /*
     * Resource status of the origin.
     */
    @JsonProperty(value = "properties.resourceState", access = JsonProperty.Access.WRITE_ONLY)
    private OriginResourceState resourceState;

    /*
     * Provisioning status of the origin.
     */
    @JsonProperty(value = "properties.provisioningState", access = JsonProperty.Access.WRITE_ONLY)
    private String provisioningState;

    /**
     * Get the hostName property: The address of the origin. Domain names, IPv4
     * addresses, and IPv6 addresses are supported.
     * 
     * @return the hostName value.
     */
    public String hostName() {
        return this.hostName;
    }

    /**
     * Set the hostName property: The address of the origin. Domain names, IPv4
     * addresses, and IPv6 addresses are supported.
     * 
     * @param hostName the hostName value to set.
     * @return the OriginInner object itself.
     */
    public OriginInner withHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    /**
     * Get the httpPort property: The value of the HTTP port. Must be between 1
     * and 65535.
     * 
     * @return the httpPort value.
     */
    public Integer httpPort() {
        return this.httpPort;
    }

    /**
     * Set the httpPort property: The value of the HTTP port. Must be between 1
     * and 65535.
     * 
     * @param httpPort the httpPort value to set.
     * @return the OriginInner object itself.
     */
    public OriginInner withHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
        return this;
    }

    /**
     * Get the httpsPort property: The value of the https port. Must be between
     * 1 and 65535.
     * 
     * @return the httpsPort value.
     */
    public Integer httpsPort() {
        return this.httpsPort;
    }

    /**
     * Set the httpsPort property: The value of the https port. Must be between
     * 1 and 65535.
     * 
     * @param httpsPort the httpsPort value to set.
     * @return the OriginInner object itself.
     */
    public OriginInner withHttpsPort(Integer httpsPort) {
        this.httpsPort = httpsPort;
        return this;
    }

    /**
     * Get the resourceState property: Resource status of the origin.
     * 
     * @return the resourceState value.
     */
    public OriginResourceState resourceState() {
        return this.resourceState;
    }

    /**
     * Get the provisioningState property: Provisioning status of the origin.
     * 
     * @return the provisioningState value.
     */
    public String provisioningState() {
        return this.provisioningState;
    }
}
