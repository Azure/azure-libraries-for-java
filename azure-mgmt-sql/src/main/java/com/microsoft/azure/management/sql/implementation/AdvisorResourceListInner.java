/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import java.util.List;

/**
 * Response to a list request for the advisor resource.
 */
public class AdvisorResourceListInner {
    /**
     * The value property.
     */
    private List<AdvisorResourceInner> value;

    /**
     * The nextLink property.
     */
    private String nextLink;

    /**
     * Get the value value.
     *
     * @return the value value
     */
    public List<AdvisorResourceInner> value() {
        return this.value;
    }

    /**
     * Set the value value.
     *
     * @param value the value value to set
     * @return the AdvisorResourceListInner object itself.
     */
    public AdvisorResourceListInner withValue(List<AdvisorResourceInner> value) {
        this.value = value;
        return this;
    }

    /**
     * Get the nextLink value.
     *
     * @return the nextLink value
     */
    public String nextLink() {
        return this.nextLink;
    }

    /**
     * Set the nextLink value.
     *
     * @param nextLink the nextLink value to set
     * @return the AdvisorResourceListInner object itself.
     */
    public AdvisorResourceListInner withNextLink(String nextLink) {
        this.nextLink = nextLink;
        return this;
    }

}
