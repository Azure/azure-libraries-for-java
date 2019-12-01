// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.azure.management.resources.implementation;

import com.azure.core.http.rest.Page;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class GenericResourceInnerPage implements Page<GenericResourceInner> {

    /**
     * The link to the next page.
     */
    @JsonProperty("nextLink")
    private String continuationToken;

    /**
     * The list of items.
     */
    @JsonProperty("value")
    private List<GenericResourceInner> items;

    /**
     * Gets the link to the next page. Or {@code null} if there are no more resources to fetch.
     *
     * @return The link to the next page.
     */
    @Override
    public String getContinuationToken() {
        return this.continuationToken;
    }

    @Override
    public List<GenericResourceInner> getItems() {
        return items;
    }
}
