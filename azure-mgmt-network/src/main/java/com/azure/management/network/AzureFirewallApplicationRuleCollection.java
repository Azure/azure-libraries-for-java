// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.SubResource;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The AzureFirewallApplicationRuleCollection model.
 */
@JsonFlatten
@Fluent
public class AzureFirewallApplicationRuleCollection extends SubResource {
    /*
     * Gets name of the resource that is unique within a resource group. This
     * name can be used to access the resource.
     */
    @JsonProperty(value = "name")
    private String name;

    /*
     * Gets a unique read-only string that changes whenever the resource is
     * updated.
     */
    @JsonProperty(value = "etag", access = JsonProperty.Access.WRITE_ONLY)
    private String etag;

    /*
     * Priority of the application rule collection resource.
     */
    @JsonProperty(value = "properties.priority")
    private Integer priority;

    /*
     * Properties of the AzureFirewallRCAction.
     */
    @JsonProperty(value = "properties.action")
    private AzureFirewallRCAction action;

    /*
     * Collection of rules used by a application rule collection.
     */
    @JsonProperty(value = "properties.rules")
    private List<AzureFirewallApplicationRule> rules;

    /*
     * The current provisioning state.
     */
    @JsonProperty(value = "properties.provisioningState")
    private ProvisioningState provisioningState;

    /**
     * Get the name property: Gets name of the resource that is unique within a
     * resource group. This name can be used to access the resource.
     * 
     * @return the name value.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name property: Gets name of the resource that is unique within a
     * resource group. This name can be used to access the resource.
     * 
     * @param name the name value to set.
     * @return the AzureFirewallApplicationRuleCollection object itself.
     */
    public AzureFirewallApplicationRuleCollection setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the etag property: Gets a unique read-only string that changes
     * whenever the resource is updated.
     * 
     * @return the etag value.
     */
    public String getEtag() {
        return this.etag;
    }

    /**
     * Get the priority property: Priority of the application rule collection
     * resource.
     * 
     * @return the priority value.
     */
    public Integer getPriority() {
        return this.priority;
    }

    /**
     * Set the priority property: Priority of the application rule collection
     * resource.
     * 
     * @param priority the priority value to set.
     * @return the AzureFirewallApplicationRuleCollection object itself.
     */
    public AzureFirewallApplicationRuleCollection setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Get the action property: Properties of the AzureFirewallRCAction.
     * 
     * @return the action value.
     */
    public AzureFirewallRCAction getAction() {
        return this.action;
    }

    /**
     * Set the action property: Properties of the AzureFirewallRCAction.
     * 
     * @param action the action value to set.
     * @return the AzureFirewallApplicationRuleCollection object itself.
     */
    public AzureFirewallApplicationRuleCollection setAction(AzureFirewallRCAction action) {
        this.action = action;
        return this;
    }

    /**
     * Get the rules property: Collection of rules used by a application rule
     * collection.
     * 
     * @return the rules value.
     */
    public List<AzureFirewallApplicationRule> getRules() {
        return this.rules;
    }

    /**
     * Set the rules property: Collection of rules used by a application rule
     * collection.
     * 
     * @param rules the rules value to set.
     * @return the AzureFirewallApplicationRuleCollection object itself.
     */
    public AzureFirewallApplicationRuleCollection setRules(List<AzureFirewallApplicationRule> rules) {
        this.rules = rules;
        return this;
    }

    /**
     * Get the provisioningState property: The current provisioning state.
     * 
     * @return the provisioningState value.
     */
    public ProvisioningState getProvisioningState() {
        return this.provisioningState;
    }

    /**
     * Set the provisioningState property: The current provisioning state.
     * 
     * @param provisioningState the provisioningState value to set.
     * @return the AzureFirewallApplicationRuleCollection object itself.
     */
    public AzureFirewallApplicationRuleCollection setProvisioningState(ProvisioningState provisioningState) {
        this.provisioningState = provisioningState;
        return this;
    }
}
