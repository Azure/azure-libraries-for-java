// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The VirtualHubRoute model.
 */
@Fluent
public final class VirtualHubRoute {
    /*
     * List of all addressPrefixes.
     */
    @JsonProperty(value = "addressPrefixes")
    private List<String> addressPrefixes;

    /*
     * NextHop ip address.
     */
    @JsonProperty(value = "nextHopIpAddress")
    private String nextHopIpAddress;

    /**
     * Get the addressPrefixes property: List of all addressPrefixes.
     * 
     * @return the addressPrefixes value.
     */
    public List<String> getAddressPrefixes() {
        return this.addressPrefixes;
    }

    /**
     * Set the addressPrefixes property: List of all addressPrefixes.
     * 
     * @param addressPrefixes the addressPrefixes value to set.
     * @return the VirtualHubRoute object itself.
     */
    public VirtualHubRoute setAddressPrefixes(List<String> addressPrefixes) {
        this.addressPrefixes = addressPrefixes;
        return this;
    }

    /**
     * Get the nextHopIpAddress property: NextHop ip address.
     * 
     * @return the nextHopIpAddress value.
     */
    public String getNextHopIpAddress() {
        return this.nextHopIpAddress;
    }

    /**
     * Set the nextHopIpAddress property: NextHop ip address.
     * 
     * @param nextHopIpAddress the nextHopIpAddress value to set.
     * @return the VirtualHubRoute object itself.
     */
    public VirtualHubRoute setNextHopIpAddress(String nextHopIpAddress) {
        this.nextHopIpAddress = nextHopIpAddress;
        return this;
    }
}
