// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.containerregistry;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The TrustPolicy model.
 */
@Fluent
public final class TrustPolicy {
    /*
     * The type of trust policy.
     */
    @JsonProperty(value = "type")
    private String type;

    /*
     * The value that indicates whether the policy is enabled or not.
     */
    @JsonProperty(value = "status")
    private PolicyStatus status;

    /**
     * Creates an instance of TrustPolicy class.
     */
    public TrustPolicy() {
        type = "Notary";
    }

    /**
     * Get the type property: The type of trust policy.
     * 
     * @return the type value.
     */
    public String type() {
        return this.type;
    }

    /**
     * Set the type property: The type of trust policy.
     * 
     * @param type the type value to set.
     * @return the TrustPolicy object itself.
     */
    public TrustPolicy withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get the status property: The value that indicates whether the policy is
     * enabled or not.
     * 
     * @return the status value.
     */
    public PolicyStatus status() {
        return this.status;
    }

    /**
     * Set the status property: The value that indicates whether the policy is
     * enabled or not.
     * 
     * @param status the status value to set.
     * @return the TrustPolicy object itself.
     */
    public TrustPolicy withStatus(PolicyStatus status) {
        this.status = status;
        return this;
    }
}
