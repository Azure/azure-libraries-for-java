/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cdn;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Describes a managed rule group.
 */
public class ManagedRuleGroupDefinition {
    /**
     * Name of the managed rule group.
     */
    @JsonProperty(value = "ruleGroupName", access = JsonProperty.Access.WRITE_ONLY)
    private String ruleGroupName;

    /**
     * Description of the managed rule group.
     */
    @JsonProperty(value = "description", access = JsonProperty.Access.WRITE_ONLY)
    private String description;

    /**
     * List of rules within the managed rule group.
     */
    @JsonProperty(value = "rules", access = JsonProperty.Access.WRITE_ONLY)
    private List<ManagedRuleDefinition> rules;

    /**
     * Get name of the managed rule group.
     *
     * @return the ruleGroupName value
     */
    public String ruleGroupName() {
        return this.ruleGroupName;
    }

    /**
     * Get description of the managed rule group.
     *
     * @return the description value
     */
    public String description() {
        return this.description;
    }

    /**
     * Get list of rules within the managed rule group.
     *
     * @return the rules value
     */
    public List<ManagedRuleDefinition> rules() {
        return this.rules;
    }

}
