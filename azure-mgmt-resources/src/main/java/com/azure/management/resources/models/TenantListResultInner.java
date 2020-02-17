// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.resources.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The TenantListResult model.
 */
@Fluent
public final class TenantListResultInner {
    /*
     * An array of tenants.
     */
    @JsonProperty(value = "value")
    private List<TenantIdDescriptionInner> value;

    /*
     * The URL to use for getting the next set of results.
     */
    @JsonProperty(value = "nextLink", required = true)
    private String nextLink;

    /**
     * Get the value property: An array of tenants.
     * 
     * @return the value value.
     */
    public List<TenantIdDescriptionInner> getValue() {
        return this.value;
    }

    /**
     * Set the value property: An array of tenants.
     * 
     * @param value the value value to set.
     * @return the TenantListResultInner object itself.
     */
    public TenantListResultInner setValue(List<TenantIdDescriptionInner> value) {
        this.value = value;
        return this;
    }

    /**
     * Get the nextLink property: The URL to use for getting the next set of
     * results.
     * 
     * @return the nextLink value.
     */
    public String getNextLink() {
        return this.nextLink;
    }

    /**
     * Set the nextLink property: The URL to use for getting the next set of
     * results.
     * 
     * @param nextLink the nextLink value to set.
     * @return the TenantListResultInner object itself.
     */
    public TenantListResultInner setNextLink(String nextLink) {
        this.nextLink = nextLink;
        return this;
    }
}
