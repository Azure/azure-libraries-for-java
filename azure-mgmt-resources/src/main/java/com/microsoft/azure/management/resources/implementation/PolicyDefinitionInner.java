/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.resources.implementation;

import com.microsoft.azure.management.resources.PolicyType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * The policy definition.
 */
@JsonFlatten
public class PolicyDefinitionInner {
    /**
     * The type of policy definition. Possible values are NotSpecified,
     * BuiltIn, and Custom. Possible values include: 'NotSpecified', 'BuiltIn',
     * 'Custom'.
     */
    @JsonProperty(value = "properties.policyType")
    private PolicyType policyType;

    /**
     * The display name of the policy definition.
     */
    @JsonProperty(value = "properties.displayName")
    private String displayName;

    /**
     * The policy definition description.
     */
    @JsonProperty(value = "properties.description")
    private String description;

    /**
     * The policy rule.
     */
    @JsonProperty(value = "properties.policyRule")
    private Object policyRule;

    /**
     * The ID of the policy definition.
     */
    @JsonProperty(value = "id")
    private String id;

    /**
     * The name of the policy definition. If you do not specify a value for
     * name, the value is inferred from the name value in the request URI.
     */
    @JsonProperty(value = "name")
    private String name;

    /**
     * Get the policyType value.
     *
     * @return the policyType value
     */
    public PolicyType policyType() {
        return this.policyType;
    }

    /**
     * Set the policyType value.
     *
     * @param policyType the policyType value to set
     * @return the PolicyDefinitionInner object itself.
     */
    public PolicyDefinitionInner withPolicyType(PolicyType policyType) {
        this.policyType = policyType;
        return this;
    }

    /**
     * Get the displayName value.
     *
     * @return the displayName value
     */
    public String displayName() {
        return this.displayName;
    }

    /**
     * Set the displayName value.
     *
     * @param displayName the displayName value to set
     * @return the PolicyDefinitionInner object itself.
     */
    public PolicyDefinitionInner withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Get the description value.
     *
     * @return the description value
     */
    public String description() {
        return this.description;
    }

    /**
     * Set the description value.
     *
     * @param description the description value to set
     * @return the PolicyDefinitionInner object itself.
     */
    public PolicyDefinitionInner withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get the policyRule value.
     *
     * @return the policyRule value
     */
    public Object policyRule() {
        return this.policyRule;
    }

    /**
     * Set the policyRule value.
     *
     * @param policyRule the policyRule value to set
     * @return the PolicyDefinitionInner object itself.
     */
    public PolicyDefinitionInner withPolicyRule(Object policyRule) {
        this.policyRule = policyRule;
        return this;
    }

    /**
     * Get the id value.
     *
     * @return the id value
     */
    public String id() {
        return this.id;
    }

    /**
     * Set the id value.
     *
     * @param id the id value to set
     * @return the PolicyDefinitionInner object itself.
     */
    public PolicyDefinitionInner withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Get the name value.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the name value.
     *
     * @param name the name value to set
     * @return the PolicyDefinitionInner object itself.
     */
    public PolicyDefinitionInner withName(String name) {
        this.name = name;
        return this;
    }

}
