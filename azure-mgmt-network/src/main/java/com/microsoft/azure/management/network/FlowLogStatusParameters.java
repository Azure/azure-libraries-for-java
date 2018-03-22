/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.network;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters that define a resource to query flow log and traffic analytics
 * (optional) status.
 */
public class FlowLogStatusParameters {
    /**
     * The target resource where getting the flow logg and traffic analytics
     * (optional) status.
     */
    @JsonProperty(value = "targetResourceId", required = true)
    private String targetResourceId;

    /**
     * Get the targetResourceId value.
     *
     * @return the targetResourceId value
     */
    public String targetResourceId() {
        return this.targetResourceId;
    }

    /**
     * Set the targetResourceId value.
     *
     * @param targetResourceId the targetResourceId value to set
     * @return the FlowLogStatusParameters object itself.
     */
    public FlowLogStatusParameters withTargetResourceId(String targetResourceId) {
        this.targetResourceId = targetResourceId;
        return this;
    }

}
