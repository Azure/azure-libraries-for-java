// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.resources;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The DeploymentsWhatIfAtSubscriptionScopeHeaders model.
 */
@Fluent
public final class DeploymentsWhatIfAtSubscriptionScopeHeaders {
    /*
     * The Retry-After property.
     */
    @JsonProperty(value = "Retry-After")
    private String retryAfter;

    /*
     * The Location property.
     */
    @JsonProperty(value = "Location")
    private String location;

    /**
     * Get the retryAfter property: The Retry-After property.
     * 
     * @return the retryAfter value.
     */
    public String getRetryAfter() {
        return this.retryAfter;
    }

    /**
     * Set the retryAfter property: The Retry-After property.
     * 
     * @param retryAfter the retryAfter value to set.
     * @return the DeploymentsWhatIfAtSubscriptionScopeHeaders object itself.
     */
    public DeploymentsWhatIfAtSubscriptionScopeHeaders setRetryAfter(String retryAfter) {
        this.retryAfter = retryAfter;
        return this;
    }

    /**
     * Get the location property: The Location property.
     * 
     * @return the location value.
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Set the location property: The Location property.
     * 
     * @param location the location value to set.
     * @return the DeploymentsWhatIfAtSubscriptionScopeHeaders object itself.
     */
    public DeploymentsWhatIfAtSubscriptionScopeHeaders setLocation(String location) {
        this.location = location;
        return this;
    }
}
