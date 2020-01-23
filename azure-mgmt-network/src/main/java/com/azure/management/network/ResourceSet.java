// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The ResourceSet model.
 */
@Fluent
public class ResourceSet {
    /*
     * The list of subscriptions.
     */
    @JsonProperty(value = "subscriptions")
    private List<String> subscriptions;

    /**
     * Get the subscriptions property: The list of subscriptions.
     * 
     * @return the subscriptions value.
     */
    public List<String> getSubscriptions() {
        return this.subscriptions;
    }

    /**
     * Set the subscriptions property: The list of subscriptions.
     * 
     * @param subscriptions the subscriptions value to set.
     * @return the ResourceSet object itself.
     */
    public ResourceSet setSubscriptions(List<String> subscriptions) {
        this.subscriptions = subscriptions;
        return this;
    }
}
