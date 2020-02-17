// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.storage.models;

import com.azure.core.annotation.Immutable;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The StorageAccountListResult model.
 */
@Immutable
public final class StorageAccountListResultInner {
    /*
     * Gets the list of storage accounts and their properties.
     */
    @JsonProperty(value = "value", access = JsonProperty.Access.WRITE_ONLY)
    private List<StorageAccountInner> value;

    /*
     * Request URL that can be used to query next page of storage accounts.
     * Returned when total number of requested storage accounts exceed maximum
     * page size.
     */
    @JsonProperty(value = "nextLink", access = JsonProperty.Access.WRITE_ONLY)
    private String nextLink;

    /**
     * Get the value property: Gets the list of storage accounts and their
     * properties.
     * 
     * @return the value value.
     */
    public List<StorageAccountInner> getValue() {
        return this.value;
    }

    /**
     * Get the nextLink property: Request URL that can be used to query next
     * page of storage accounts. Returned when total number of requested
     * storage accounts exceed maximum page size.
     * 
     * @return the nextLink value.
     */
    public String getNextLink() {
        return this.nextLink;
    }
}
