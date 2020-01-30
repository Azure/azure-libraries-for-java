/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.network.implementation;

import java.util.List;
import com.microsoft.azure.management.network.ExpressRouteCircuitArpTable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response for ListArpTable associated with the Express Route Circuits API.
 */
public class ExpressRouteCircuitsArpTableListResultInner {
    /**
     * A list of the ARP tables.
     */
    @JsonProperty(value = "value")
    private List<ExpressRouteCircuitArpTable> value;

    /**
     * The URL to get the next set of results.
     */
    @JsonProperty(value = "nextLink")
    private String nextLink;

    /**
     * Get a list of the ARP tables.
     *
     * @return the value value
     */
    public List<ExpressRouteCircuitArpTable> value() {
        return this.value;
    }

    /**
     * Set a list of the ARP tables.
     *
     * @param value the value value to set
     * @return the ExpressRouteCircuitsArpTableListResultInner object itself.
     */
    public ExpressRouteCircuitsArpTableListResultInner withValue(List<ExpressRouteCircuitArpTable> value) {
        this.value = value;
        return this;
    }

    /**
     * Get the URL to get the next set of results.
     *
     * @return the nextLink value
     */
    public String nextLink() {
        return this.nextLink;
    }

    /**
     * Set the URL to get the next set of results.
     *
     * @param nextLink the nextLink value to set
     * @return the ExpressRouteCircuitsArpTableListResultInner object itself.
     */
    public ExpressRouteCircuitsArpTableListResultInner withNextLink(String nextLink) {
        this.nextLink = nextLink;
        return this;
    }

}
