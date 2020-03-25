// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.storage.models;

import com.azure.core.annotation.Fluent;
import com.azure.management.storage.PrivateLinkResource;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The PrivateLinkResourceListResult model.
 */
@Fluent
public final class PrivateLinkResourceListResultInner {
    /*
     * Array of private link resources
     */
    @JsonProperty(value = "value")
    private List<PrivateLinkResource> value;

    /**
     * Get the value property: Array of private link resources.
     * 
     * @return the value value.
     */
    public List<PrivateLinkResource> getValue() {
        return this.value;
    }

    /**
     * Set the value property: Array of private link resources.
     * 
     * @param value the value value to set.
     * @return the PrivateLinkResourceListResultInner object itself.
     */
    public PrivateLinkResourceListResultInner setValue(List<PrivateLinkResource> value) {
        this.value = value;
        return this;
    }
}
