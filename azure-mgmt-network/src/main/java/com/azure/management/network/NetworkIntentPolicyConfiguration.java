// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The NetworkIntentPolicyConfiguration model.
 */
@Fluent
public final class NetworkIntentPolicyConfiguration {
    /*
     * The name of the Network Intent Policy for storing in target
     * subscription.
     */
    @JsonProperty(value = "networkIntentPolicyName")
    private String networkIntentPolicyName;

    /*
     * Network Intent Policy resource.
     */
    @JsonProperty(value = "sourceNetworkIntentPolicy")
    private NetworkIntentPolicy sourceNetworkIntentPolicy;

    /**
     * Get the networkIntentPolicyName property: The name of the Network Intent
     * Policy for storing in target subscription.
     * 
     * @return the networkIntentPolicyName value.
     */
    public String getNetworkIntentPolicyName() {
        return this.networkIntentPolicyName;
    }

    /**
     * Set the networkIntentPolicyName property: The name of the Network Intent
     * Policy for storing in target subscription.
     * 
     * @param networkIntentPolicyName the networkIntentPolicyName value to set.
     * @return the NetworkIntentPolicyConfiguration object itself.
     */
    public NetworkIntentPolicyConfiguration setNetworkIntentPolicyName(String networkIntentPolicyName) {
        this.networkIntentPolicyName = networkIntentPolicyName;
        return this;
    }

    /**
     * Get the sourceNetworkIntentPolicy property: Network Intent Policy
     * resource.
     * 
     * @return the sourceNetworkIntentPolicy value.
     */
    public NetworkIntentPolicy getSourceNetworkIntentPolicy() {
        return this.sourceNetworkIntentPolicy;
    }

    /**
     * Set the sourceNetworkIntentPolicy property: Network Intent Policy
     * resource.
     * 
     * @param sourceNetworkIntentPolicy the sourceNetworkIntentPolicy value to
     * set.
     * @return the NetworkIntentPolicyConfiguration object itself.
     */
    public NetworkIntentPolicyConfiguration setSourceNetworkIntentPolicy(NetworkIntentPolicy sourceNetworkIntentPolicy) {
        this.sourceNetworkIntentPolicy = sourceNetworkIntentPolicy;
        return this;
    }
}
