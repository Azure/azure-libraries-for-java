// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The BastionHostListResult model.
 */
@Fluent
public final class BastionHostListResultInner {
    /*
     * List of Bastion Hosts in a resource group.
     */
    @JsonProperty(value = "value")
    private List<BastionHostInner> value;

    /*
     * URL to get the next set of results.
     */
    @JsonProperty(value = "nextLink")
    private String nextLink;

    /**
     * Get the value property: List of Bastion Hosts in a resource group.
     * 
     * @return the value value.
     */
    public List<BastionHostInner> value() {
        return this.value;
    }

    /**
     * Set the value property: List of Bastion Hosts in a resource group.
     * 
     * @param value the value value to set.
     * @return the BastionHostListResultInner object itself.
     */
    public BastionHostListResultInner withValue(List<BastionHostInner> value) {
        this.value = value;
        return this;
    }

    /**
     * Get the nextLink property: URL to get the next set of results.
     * 
     * @return the nextLink value.
     */
    public String nextLink() {
        return this.nextLink;
    }

    /**
     * Set the nextLink property: URL to get the next set of results.
     * 
     * @param nextLink the nextLink value to set.
     * @return the BastionHostListResultInner object itself.
     */
    public BastionHostListResultInner withNextLink(String nextLink) {
        this.nextLink = nextLink;
        return this;
    }
}
