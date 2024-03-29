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
 * Properties of the FirewallPolicyNatRuleAction.
 */
public class FirewallPolicyNatRuleAction {
    /**
     * The type of action. Possible values include: 'DNAT'.
     */
    @JsonProperty(value = "type")
    private FirewallPolicyNatRuleActionType type;

    /**
     * Get the type of action. Possible values include: 'DNAT'.
     *
     * @return the type value
     */
    public FirewallPolicyNatRuleActionType type() {
        return this.type;
    }

    /**
     * Set the type of action. Possible values include: 'DNAT'.
     *
     * @param type the type value to set
     * @return the FirewallPolicyNatRuleAction object itself.
     */
    public FirewallPolicyNatRuleAction withType(FirewallPolicyNatRuleActionType type) {
        this.type = type;
        return this;
    }

}
