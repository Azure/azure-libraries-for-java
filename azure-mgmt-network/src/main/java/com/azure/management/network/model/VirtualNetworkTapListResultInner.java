// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.model;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The VirtualNetworkTapListResult model.
 */
@Fluent
public final class VirtualNetworkTapListResultInner {
    /*
     * A list of VirtualNetworkTaps in a resource group.
     */
    @JsonProperty(value = "value")
    private List<VirtualNetworkTapInner> value;

    /*
     * The URL to get the next set of results.
     */
    @JsonProperty(value = "nextLink")
    private String nextLink;

    /**
     * Get the value property: A list of VirtualNetworkTaps in a resource
     * group.
     * 
     * @return the value value.
     */
    public List<VirtualNetworkTapInner> getValue() {
        return this.value;
    }

    /**
     * Set the value property: A list of VirtualNetworkTaps in a resource
     * group.
     * 
     * @param value the value value to set.
     * @return the VirtualNetworkTapListResultInner object itself.
     */
    public VirtualNetworkTapListResultInner setValue(List<VirtualNetworkTapInner> value) {
        this.value = value;
        return this;
    }

    /**
     * Get the nextLink property: The URL to get the next set of results.
     * 
     * @return the nextLink value.
     */
    public String getNextLink() {
        return this.nextLink;
    }

    /**
     * Set the nextLink property: The URL to get the next set of results.
     * 
     * @param nextLink the nextLink value to set.
     * @return the VirtualNetworkTapListResultInner object itself.
     */
    public VirtualNetworkTapListResultInner setNextLink(String nextLink) {
        this.nextLink = nextLink;
        return this;
    }
}
