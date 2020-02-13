// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.Resource;
import com.azure.management.network.ProvisioningState;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The NetworkWatcher model.
 */
@JsonFlatten
@Fluent
public class NetworkWatcherInner extends Resource {
    /*
     * A unique read-only string that changes whenever the resource is updated.
     */
    @JsonProperty(value = "etag")
    private String etag;

    /*
     * The current provisioning state.
     */
    @JsonProperty(value = "properties.provisioningState")
    private ProvisioningState provisioningState;

    /*
     * Resource ID.
     */
    @JsonProperty(value = "id")
    private String id;

    /**
     * Get the etag property: A unique read-only string that changes whenever
     * the resource is updated.
     * 
     * @return the etag value.
     */
    public String etag() {
        return this.etag;
    }

    /**
     * Set the etag property: A unique read-only string that changes whenever
     * the resource is updated.
     * 
     * @param etag the etag value to set.
     * @return the NetworkWatcherInner object itself.
     */
    public NetworkWatcherInner withEtag(String etag) {
        this.etag = etag;
        return this;
    }

    /**
     * Get the provisioningState property: The current provisioning state.
     * 
     * @return the provisioningState value.
     */
    public ProvisioningState provisioningState() {
        return this.provisioningState;
    }

    /**
     * Set the provisioningState property: The current provisioning state.
     * 
     * @param provisioningState the provisioningState value to set.
     * @return the NetworkWatcherInner object itself.
     */
    public NetworkWatcherInner withProvisioningState(ProvisioningState provisioningState) {
        this.provisioningState = provisioningState;
        return this;
    }

    /**
     * Get the id property: Resource ID.
     * 
     * @return the id value.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Set the id property: Resource ID.
     * 
     * @param id the id value to set.
     * @return the NetworkWatcherInner object itself.
     */
    public NetworkWatcherInner withId(String id) {
        this.id = id;
        return this;
    }
}
