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
 * The PacketCaptureListResult model.
 */
@Fluent
public final class PacketCaptureListResultInner {
    /*
     * Information about packet capture sessions.
     */
    @JsonProperty(value = "value")
    private List<PacketCaptureResultInner> value;

    /**
     * Get the value property: Information about packet capture sessions.
     * 
     * @return the value value.
     */
    public List<PacketCaptureResultInner> getValue() {
        return this.value;
    }

    /**
     * Set the value property: Information about packet capture sessions.
     * 
     * @param value the value value to set.
     * @return the PacketCaptureListResultInner object itself.
     */
    public PacketCaptureListResultInner setValue(List<PacketCaptureResultInner> value) {
        this.value = value;
        return this;
    }
}
