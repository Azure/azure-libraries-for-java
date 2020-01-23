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
 * The ExpressRouteCrossConnectionListResult model.
 */
@Fluent
public final class ExpressRouteCrossConnectionListResultInner {
    /*
     * A list of ExpressRouteCrossConnection resources.
     */
    @JsonProperty(value = "value")
    private List<ExpressRouteCrossConnectionInner> value;

    /*
     * The URL to get the next set of results.
     */
    @JsonProperty(value = "nextLink", access = JsonProperty.Access.WRITE_ONLY)
    private String nextLink;

    /**
     * Get the value property: A list of ExpressRouteCrossConnection resources.
     * 
     * @return the value value.
     */
    public List<ExpressRouteCrossConnectionInner> getValue() {
        return this.value;
    }

    /**
     * Set the value property: A list of ExpressRouteCrossConnection resources.
     * 
     * @param value the value value to set.
     * @return the ExpressRouteCrossConnectionListResultInner object itself.
     */
    public ExpressRouteCrossConnectionListResultInner setValue(List<ExpressRouteCrossConnectionInner> value) {
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
}
