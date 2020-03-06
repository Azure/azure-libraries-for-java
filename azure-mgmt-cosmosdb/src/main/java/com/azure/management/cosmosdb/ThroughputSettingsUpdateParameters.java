/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.azure.management.cosmosdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * Parameters to update Cosmos DB resource throughput.
 */
@JsonFlatten
public class ThroughputSettingsUpdateParameters extends ARMResourceProperties {
    /**
     * The standard JSON format of a resource throughput.
     */
    @JsonProperty(value = "properties.resource", required = true)
    private ThroughputSettingsResource resource;

    /**
     * Get the standard JSON format of a resource throughput.
     *
     * @return the resource value
     */
    public ThroughputSettingsResource resource() {
        return this.resource;
    }

    /**
     * Set the standard JSON format of a resource throughput.
     *
     * @param resource the resource value to set
     * @return the ThroughputSettingsUpdateParameters object itself.
     */
    public ThroughputSettingsUpdateParameters withResource(ThroughputSettingsResource resource) {
        this.resource = resource;
        return this;
    }

}
