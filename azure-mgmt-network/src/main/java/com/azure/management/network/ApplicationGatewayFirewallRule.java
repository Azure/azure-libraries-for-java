// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The ApplicationGatewayFirewallRule model.
 */
@Fluent
public final class ApplicationGatewayFirewallRule {
    /*
     * The identifier of the web application firewall rule.
     */
    @JsonProperty(value = "ruleId", required = true)
    private int ruleId;

    /*
     * The description of the web application firewall rule.
     */
    @JsonProperty(value = "description")
    private String description;

    /**
     * Get the ruleId property: The identifier of the web application firewall
     * rule.
     * 
     * @return the ruleId value.
     */
    public int getRuleId() {
        return this.ruleId;
    }

    /**
     * Set the ruleId property: The identifier of the web application firewall
     * rule.
     * 
     * @param ruleId the ruleId value to set.
     * @return the ApplicationGatewayFirewallRule object itself.
     */
    public ApplicationGatewayFirewallRule setRuleId(int ruleId) {
        this.ruleId = ruleId;
        return this;
    }

    /**
     * Get the description property: The description of the web application
     * firewall rule.
     * 
     * @return the description value.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set the description property: The description of the web application
     * firewall rule.
     * 
     * @param description the description value to set.
     * @return the ApplicationGatewayFirewallRule object itself.
     */
    public ApplicationGatewayFirewallRule setDescription(String description) {
        this.description = description;
        return this;
    }
}
