// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.storage.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The UsageListResult model.
 */
@Fluent
public final class UsageListResultInner {
    /*
     * Gets or sets the list of Storage Resource Usages.
     */
    @JsonProperty(value = "value")
    private List<UsageInner> value;

    /**
     * Get the value property: Gets or sets the list of Storage Resource
     * Usages.
     * 
     * @return the value value.
     */
    public List<UsageInner> getValue() {
        return this.value;
    }

    /**
     * Set the value property: Gets or sets the list of Storage Resource
     * Usages.
     * 
     * @param value the value value to set.
     * @return the UsageListResultInner object itself.
     */
    public UsageListResultInner setValue(List<UsageInner> value) {
        this.value = value;
        return this;
    }
}
