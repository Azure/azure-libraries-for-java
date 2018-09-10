/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.v2.management.sql.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.azure.v2.management.sql.ProxyResource;
import com.microsoft.azure.v2.management.sql.VirtualNetworkRuleState;
import com.microsoft.rest.v2.serializer.JsonFlatten;

/**
 * A virtual network rule.
 */
@JsonFlatten
public class VirtualNetworkRuleInner extends ProxyResource {
    /**
     * The ARM resource id of the virtual network subnet.
     */
    @JsonProperty(value = "properties.virtualNetworkSubnetId", required = true)
    private String virtualNetworkSubnetId;

    /**
     * Create firewall rule before the virtual network has vnet service
     * endpoint enabled.
     */
    @JsonProperty(value = "properties.ignoreMissingVnetServiceEndpoint")
    private Boolean ignoreMissingVnetServiceEndpoint;

    /**
     * Virtual Network Rule State. Possible values include: 'Initializing',
     * 'InProgress', 'Ready', 'Deleting', 'Unknown'.
     */
    @JsonProperty(value = "properties.state", access = JsonProperty.Access.WRITE_ONLY)
    private VirtualNetworkRuleState state;

    /**
     * Get the virtualNetworkSubnetId value.
     *
     * @return the virtualNetworkSubnetId value.
     */
    public String virtualNetworkSubnetId() {
        return this.virtualNetworkSubnetId;
    }

    /**
     * Set the virtualNetworkSubnetId value.
     *
     * @param virtualNetworkSubnetId the virtualNetworkSubnetId value to set.
     * @return the VirtualNetworkRuleInner object itself.
     */
    public VirtualNetworkRuleInner withVirtualNetworkSubnetId(String virtualNetworkSubnetId) {
        this.virtualNetworkSubnetId = virtualNetworkSubnetId;
        return this;
    }

    /**
     * Get the ignoreMissingVnetServiceEndpoint value.
     *
     * @return the ignoreMissingVnetServiceEndpoint value.
     */
    public Boolean ignoreMissingVnetServiceEndpoint() {
        return this.ignoreMissingVnetServiceEndpoint;
    }

    /**
     * Set the ignoreMissingVnetServiceEndpoint value.
     *
     * @param ignoreMissingVnetServiceEndpoint the
     * ignoreMissingVnetServiceEndpoint value to set.
     * @return the VirtualNetworkRuleInner object itself.
     */
    public VirtualNetworkRuleInner withIgnoreMissingVnetServiceEndpoint(Boolean ignoreMissingVnetServiceEndpoint) {
        this.ignoreMissingVnetServiceEndpoint = ignoreMissingVnetServiceEndpoint;
        return this;
    }

    /**
     * Get the state value.
     *
     * @return the state value.
     */
    public VirtualNetworkRuleState state() {
        return this.state;
    }
}
