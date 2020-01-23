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
 * The ApplicationGatewayAvailableSslPredefinedPolicies model.
 */
@Fluent
public final class ApplicationGatewayAvailableSslPredefinedPoliciesInner {
    /*
     * List of available Ssl predefined policy.
     */
    @JsonProperty(value = "value")
    private List<ApplicationGatewaySslPredefinedPolicyInner> value;

    /*
     * URL to get the next set of results.
     */
    @JsonProperty(value = "nextLink")
    private String nextLink;

    /**
     * Get the value property: List of available Ssl predefined policy.
     * 
     * @return the value value.
     */
    public List<ApplicationGatewaySslPredefinedPolicyInner> getValue() {
        return this.value;
    }

    /**
     * Set the value property: List of available Ssl predefined policy.
     * 
     * @param value the value value to set.
     * @return the ApplicationGatewayAvailableSslPredefinedPoliciesInner object
     * itself.
     */
    public ApplicationGatewayAvailableSslPredefinedPoliciesInner setValue(List<ApplicationGatewaySslPredefinedPolicyInner> value) {
        this.value = value;
        return this;
    }

    /**
     * Get the nextLink property: URL to get the next set of results.
     * 
     * @return the nextLink value.
     */
    public String getNextLink() {
        return this.nextLink;
    }

    /**
     * Set the nextLink property: URL to get the next set of results.
     * 
     * @param nextLink the nextLink value to set.
     * @return the ApplicationGatewayAvailableSslPredefinedPoliciesInner object
     * itself.
     */
    public ApplicationGatewayAvailableSslPredefinedPoliciesInner setNextLink(String nextLink) {
        this.nextLink = nextLink;
        return this;
    }
}
