/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.containerservice;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Describes the Power State of the cluster.
 */
public class PowerState {
    /**
     * Tells whether the cluster is Running or Stopped. Possible values
     * include: 'Running', 'Stopped'.
     */
    @JsonProperty(value = "code")
    private Code code;

    /**
     * Get tells whether the cluster is Running or Stopped. Possible values include: 'Running', 'Stopped'.
     *
     * @return the code value
     */
    public Code code() {
        return this.code;
    }

    /**
     * Set tells whether the cluster is Running or Stopped. Possible values include: 'Running', 'Stopped'.
     *
     * @param code the code value to set
     * @return the PowerState object itself.
     */
    public PowerState withCode(Code code) {
        this.code = code;
        return this;
    }

}
