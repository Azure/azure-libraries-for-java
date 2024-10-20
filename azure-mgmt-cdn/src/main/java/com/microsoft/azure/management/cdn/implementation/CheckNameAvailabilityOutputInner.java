/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cdn.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Output of check name availability API.
 */
public class CheckNameAvailabilityOutputInner {
    /**
     * Indicates whether the name is available.
     */
    @JsonProperty(value = "nameAvailable", access = JsonProperty.Access.WRITE_ONLY)
    private Boolean nameAvailable;

    /**
     * The reason why the name is not available.
     */
    @JsonProperty(value = "reason", access = JsonProperty.Access.WRITE_ONLY)
    private String reason;

    /**
     * The detailed error message describing why the name is not available.
     */
    @JsonProperty(value = "message", access = JsonProperty.Access.WRITE_ONLY)
    private String message;

    /**
     * Get indicates whether the name is available.
     *
     * @return the nameAvailable value
     */
    public Boolean nameAvailable() {
        return this.nameAvailable;
    }

    /**
     * Get the reason why the name is not available.
     *
     * @return the reason value
     */
    public String reason() {
        return this.reason;
    }

    /**
     * Get the detailed error message describing why the name is not available.
     *
     * @return the message value
     */
    public String message() {
        return this.message;
    }

}
