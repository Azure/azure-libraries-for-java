// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The FlowLogFormatParameters model.
 */
@Fluent
public final class FlowLogFormatParameters {
    /*
     * The file type of flow log.
     */
    @JsonProperty(value = "type")
    private String type;

    /*
     * The version (revision) of the flow log.
     */
    @JsonProperty(value = "version")
    private Integer version;

    /**
     * Creates an instance of FlowLogFormatParameters class.
     */
    public FlowLogFormatParameters() {
        type = "JSON";
    }

    /**
     * Get the type property: The file type of flow log.
     * 
     * @return the type value.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Set the type property: The file type of flow log.
     * 
     * @param type the type value to set.
     * @return the FlowLogFormatParameters object itself.
     */
    public FlowLogFormatParameters setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get the version property: The version (revision) of the flow log.
     * 
     * @return the version value.
     */
    public Integer getVersion() {
        return this.version;
    }

    /**
     * Set the version property: The version (revision) of the flow log.
     * 
     * @param version the version value to set.
     * @return the FlowLogFormatParameters object itself.
     */
    public FlowLogFormatParameters setVersion(Integer version) {
        this.version = version;
        return this;
    }
}
