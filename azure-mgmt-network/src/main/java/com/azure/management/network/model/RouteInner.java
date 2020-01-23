// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.model;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.SubResource;
import com.azure.management.network.RouteNextHopType;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Route model.
 */
@JsonFlatten
@Fluent
public class RouteInner extends SubResource {
    /*
     * The name of the resource that is unique within a resource group. This
     * name can be used to access the resource.
     */
    @JsonProperty(value = "name")
    private String name;

    /*
     * A unique read-only string that changes whenever the resource is updated.
     */
    @JsonProperty(value = "etag")
    private String etag;

    /*
     * The destination CIDR to which the route applies.
     */
    @JsonProperty(value = "properties.addressPrefix")
    private String addressPrefix;

    /*
     * The type of Azure hop the packet should be sent to.
     */
    @JsonProperty(value = "properties.nextHopType")
    private RouteNextHopType nextHopType;

    /*
     * The IP address packets should be forwarded to. Next hop values are only
     * allowed in routes where the next hop type is VirtualAppliance.
     */
    @JsonProperty(value = "properties.nextHopIpAddress")
    private String nextHopIpAddress;

    /*
     * The provisioning state of the resource. Possible values are: 'Updating',
     * 'Deleting', and 'Failed'.
     */
    @JsonProperty(value = "properties.provisioningState")
    private String provisioningState;

    /**
     * Get the name property: The name of the resource that is unique within a
     * resource group. This name can be used to access the resource.
     * 
     * @return the name value.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name property: The name of the resource that is unique within a
     * resource group. This name can be used to access the resource.
     * 
     * @param name the name value to set.
     * @return the RouteInner object itself.
     */
    public RouteInner setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the etag property: A unique read-only string that changes whenever
     * the resource is updated.
     * 
     * @return the etag value.
     */
    public String getEtag() {
        return this.etag;
    }

    /**
     * Set the etag property: A unique read-only string that changes whenever
     * the resource is updated.
     * 
     * @param etag the etag value to set.
     * @return the RouteInner object itself.
     */
    public RouteInner setEtag(String etag) {
        this.etag = etag;
        return this;
    }

    /**
     * Get the addressPrefix property: The destination CIDR to which the route
     * applies.
     * 
     * @return the addressPrefix value.
     */
    public String getAddressPrefix() {
        return this.addressPrefix;
    }

    /**
     * Set the addressPrefix property: The destination CIDR to which the route
     * applies.
     * 
     * @param addressPrefix the addressPrefix value to set.
     * @return the RouteInner object itself.
     */
    public RouteInner setAddressPrefix(String addressPrefix) {
        this.addressPrefix = addressPrefix;
        return this;
    }

    /**
     * Get the nextHopType property: The type of Azure hop the packet should be
     * sent to.
     * 
     * @return the nextHopType value.
     */
    public RouteNextHopType getNextHopType() {
        return this.nextHopType;
    }

    /**
     * Set the nextHopType property: The type of Azure hop the packet should be
     * sent to.
     * 
     * @param nextHopType the nextHopType value to set.
     * @return the RouteInner object itself.
     */
    public RouteInner setNextHopType(RouteNextHopType nextHopType) {
        this.nextHopType = nextHopType;
        return this;
    }

    /**
     * Get the nextHopIpAddress property: The IP address packets should be
     * forwarded to. Next hop values are only allowed in routes where the next
     * hop type is VirtualAppliance.
     * 
     * @return the nextHopIpAddress value.
     */
    public String getNextHopIpAddress() {
        return this.nextHopIpAddress;
    }

    /**
     * Set the nextHopIpAddress property: The IP address packets should be
     * forwarded to. Next hop values are only allowed in routes where the next
     * hop type is VirtualAppliance.
     * 
     * @param nextHopIpAddress the nextHopIpAddress value to set.
     * @return the RouteInner object itself.
     */
    public RouteInner setNextHopIpAddress(String nextHopIpAddress) {
        this.nextHopIpAddress = nextHopIpAddress;
        return this;
    }

    /**
     * Get the provisioningState property: The provisioning state of the
     * resource. Possible values are: 'Updating', 'Deleting', and 'Failed'.
     * 
     * @return the provisioningState value.
     */
    public String getProvisioningState() {
        return this.provisioningState;
    }

    /**
     * Set the provisioningState property: The provisioning state of the
     * resource. Possible values are: 'Updating', 'Deleting', and 'Failed'.
     * 
     * @param provisioningState the provisioningState value to set.
     * @return the RouteInner object itself.
     */
    public RouteInner setProvisioningState(String provisioningState) {
        this.provisioningState = provisioningState;
        return this;
    }
}
