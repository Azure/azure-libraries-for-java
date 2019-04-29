/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.compute.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.azure.management.compute.InstanceViewStatus;

import java.util.List;

/**
 * The RunCommandResultInner model.
 */
public class RunCommandResultInner {
    /**
     * Run command operation response.
     */
    @JsonProperty(value = "value")
    private List<InstanceViewStatus> value;

    /**
     * Get run command operation response.
     *
     * @return the value value
     */
    public List<InstanceViewStatus> value() {
        return this.value;
    }

    /**
     * Set run command operation response.
     *
     * @param value the value value to set
     * @return the RunCommandResultInner object itself.
     */
    public RunCommandResultInner withValue(List<InstanceViewStatus> value) {
        this.value = value;
        return this;
    }

}
