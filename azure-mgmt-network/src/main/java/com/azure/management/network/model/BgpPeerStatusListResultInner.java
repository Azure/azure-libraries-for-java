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
 * The BgpPeerStatusListResult model.
 */
@Fluent
public final class BgpPeerStatusListResultInner {
    /*
     * List of BGP peers.
     */
    @JsonProperty(value = "value")
    private List<BgpPeerStatusInner> value;

    /**
     * Get the value property: List of BGP peers.
     * 
     * @return the value value.
     */
    public List<BgpPeerStatusInner> getValue() {
        return this.value;
    }

    /**
     * Set the value property: List of BGP peers.
     * 
     * @param value the value value to set.
     * @return the BgpPeerStatusListResultInner object itself.
     */
    public BgpPeerStatusListResultInner setValue(List<BgpPeerStatusInner> value) {
        this.value = value;
        return this;
    }
}