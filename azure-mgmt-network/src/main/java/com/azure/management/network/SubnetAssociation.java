// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.azure.management.network.models.SecurityRuleInner;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The SubnetAssociation model.
 */
@Fluent
public final class SubnetAssociation {
    /*
     * Subnet ID.
     */
    @JsonProperty(value = "id", access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    /*
     * Collection of custom security rules.
     */
    @JsonProperty(value = "securityRules")
    private List<SecurityRuleInner> securityRules;

    /**
     * Get the id property: Subnet ID.
     * 
     * @return the id value.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Get the securityRules property: Collection of custom security rules.
     * 
     * @return the securityRules value.
     */
    public List<SecurityRuleInner> securityRules() {
        return this.securityRules;
    }

    /**
     * Set the securityRules property: Collection of custom security rules.
     * 
     * @param securityRules the securityRules value to set.
     * @return the SubnetAssociation object itself.
     */
    public SubnetAssociation withSecurityRules(List<SecurityRuleInner> securityRules) {
        this.securityRules = securityRules;
        return this;
    }
}
