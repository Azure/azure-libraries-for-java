// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.util.ExpandableStringEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Collection;

/**
 * Defines values for FirewallPolicyRuleConditionType.
 */
public final class FirewallPolicyRuleConditionType extends ExpandableStringEnum<FirewallPolicyRuleConditionType> {
    /**
     * Static value ApplicationRuleCondition for FirewallPolicyRuleConditionType.
     */
    public static final FirewallPolicyRuleConditionType APPLICATION_RULE_CONDITION = fromString("ApplicationRuleCondition");

    /**
     * Static value NetworkRuleCondition for FirewallPolicyRuleConditionType.
     */
    public static final FirewallPolicyRuleConditionType NETWORK_RULE_CONDITION = fromString("NetworkRuleCondition");

    /**
     * Creates or finds a FirewallPolicyRuleConditionType from its string representation.
     * 
     * @param name a name to look for.
     * @return the corresponding FirewallPolicyRuleConditionType.
     */
    @JsonCreator
    public static FirewallPolicyRuleConditionType fromString(String name) {
        return fromString(name, FirewallPolicyRuleConditionType.class);
    }

    /**
     * @return known FirewallPolicyRuleConditionType values.
     */
    public static Collection<FirewallPolicyRuleConditionType> values() {
        return values(FirewallPolicyRuleConditionType.class);
    }
}
