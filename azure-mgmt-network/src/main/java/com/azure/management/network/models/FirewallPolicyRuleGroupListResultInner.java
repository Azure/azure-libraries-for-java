// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The FirewallPolicyRuleGroupListResult model.
 */
@Fluent
public final class FirewallPolicyRuleGroupListResultInner {
    /*
     * List of FirewallPolicyRuleGroups in a FirewallPolicy.
     */
    @JsonProperty(value = "value")
    private List<FirewallPolicyRuleGroupInner> value;

    /*
     * URL to get the next set of results.
     */
    @JsonProperty(value = "nextLink")
    private String nextLink;

    /**
     * Get the value property: List of FirewallPolicyRuleGroups in a
     * FirewallPolicy.
     * 
     * @return the value value.
     */
    public List<FirewallPolicyRuleGroupInner> value() {
        return this.value;
    }

    /**
     * Set the value property: List of FirewallPolicyRuleGroups in a
     * FirewallPolicy.
     * 
     * @param value the value value to set.
     * @return the FirewallPolicyRuleGroupListResultInner object itself.
     */
    public FirewallPolicyRuleGroupListResultInner withValue(List<FirewallPolicyRuleGroupInner> value) {
        this.value = value;
        return this;
    }

    /**
     * Get the nextLink property: URL to get the next set of results.
     * 
     * @return the nextLink value.
     */
    public String nextLink() {
        return this.nextLink;
    }

    /**
     * Set the nextLink property: URL to get the next set of results.
     * 
     * @param nextLink the nextLink value to set.
     * @return the FirewallPolicyRuleGroupListResultInner object itself.
     */
    public FirewallPolicyRuleGroupListResultInner withNextLink(String nextLink) {
        this.nextLink = nextLink;
        return this;
    }
}
