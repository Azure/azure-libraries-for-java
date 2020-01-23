// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.model;

import com.azure.core.annotation.Fluent;
import com.azure.management.network.ConnectionMonitorSourceStatus;
import com.azure.management.network.ConnectionStateSnapshot;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The ConnectionMonitorQueryResult model.
 */
@Fluent
public final class ConnectionMonitorQueryResultInner {
    /*
     * Status of connection monitor source.
     */
    @JsonProperty(value = "sourceStatus")
    private ConnectionMonitorSourceStatus sourceStatus;

    /*
     * Information about connection states.
     */
    @JsonProperty(value = "states")
    private List<ConnectionStateSnapshot> states;

    /**
     * Get the sourceStatus property: Status of connection monitor source.
     * 
     * @return the sourceStatus value.
     */
    public ConnectionMonitorSourceStatus getSourceStatus() {
        return this.sourceStatus;
    }

    /**
     * Set the sourceStatus property: Status of connection monitor source.
     * 
     * @param sourceStatus the sourceStatus value to set.
     * @return the ConnectionMonitorQueryResultInner object itself.
     */
    public ConnectionMonitorQueryResultInner setSourceStatus(ConnectionMonitorSourceStatus sourceStatus) {
        this.sourceStatus = sourceStatus;
        return this;
    }

    /**
     * Get the states property: Information about connection states.
     * 
     * @return the states value.
     */
    public List<ConnectionStateSnapshot> getStates() {
        return this.states;
    }

    /**
     * Set the states property: Information about connection states.
     * 
     * @param states the states value to set.
     * @return the ConnectionMonitorQueryResultInner object itself.
     */
    public ConnectionMonitorQueryResultInner setStates(List<ConnectionStateSnapshot> states) {
        this.states = states;
        return this;
    }
}
