// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The EvaluatedNetworkSecurityGroup model.
 */
@Fluent
public final class EvaluatedNetworkSecurityGroup {
    /*
     * Network security group ID.
     */
    @JsonProperty(value = "networkSecurityGroupId")
    private String networkSecurityGroupId;

    /*
     * Resource ID of nic or subnet to which network security group is applied.
     */
    @JsonProperty(value = "appliedTo")
    private String appliedTo;

    /*
     * Matched rule.
     */
    @JsonProperty(value = "matchedRule")
    private MatchedRule matchedRule;

    /*
     * List of network security rules evaluation results.
     */
    @JsonProperty(value = "rulesEvaluationResult", access = JsonProperty.Access.WRITE_ONLY)
    private List<NetworkSecurityRulesEvaluationResult> rulesEvaluationResult;

    /**
     * Get the networkSecurityGroupId property: Network security group ID.
     * 
     * @return the networkSecurityGroupId value.
     */
    public String getNetworkSecurityGroupId() {
        return this.networkSecurityGroupId;
    }

    /**
     * Set the networkSecurityGroupId property: Network security group ID.
     * 
     * @param networkSecurityGroupId the networkSecurityGroupId value to set.
     * @return the EvaluatedNetworkSecurityGroup object itself.
     */
    public EvaluatedNetworkSecurityGroup setNetworkSecurityGroupId(String networkSecurityGroupId) {
        this.networkSecurityGroupId = networkSecurityGroupId;
        return this;
    }

    /**
     * Get the appliedTo property: Resource ID of nic or subnet to which
     * network security group is applied.
     * 
     * @return the appliedTo value.
     */
    public String getAppliedTo() {
        return this.appliedTo;
    }

    /**
     * Set the appliedTo property: Resource ID of nic or subnet to which
     * network security group is applied.
     * 
     * @param appliedTo the appliedTo value to set.
     * @return the EvaluatedNetworkSecurityGroup object itself.
     */
    public EvaluatedNetworkSecurityGroup setAppliedTo(String appliedTo) {
        this.appliedTo = appliedTo;
        return this;
    }

    /**
     * Get the matchedRule property: Matched rule.
     * 
     * @return the matchedRule value.
     */
    public MatchedRule getMatchedRule() {
        return this.matchedRule;
    }

    /**
     * Set the matchedRule property: Matched rule.
     * 
     * @param matchedRule the matchedRule value to set.
     * @return the EvaluatedNetworkSecurityGroup object itself.
     */
    public EvaluatedNetworkSecurityGroup setMatchedRule(MatchedRule matchedRule) {
        this.matchedRule = matchedRule;
        return this;
    }

    /**
     * Get the rulesEvaluationResult property: List of network security rules
     * evaluation results.
     * 
     * @return the rulesEvaluationResult value.
     */
    public List<NetworkSecurityRulesEvaluationResult> getRulesEvaluationResult() {
        return this.rulesEvaluationResult;
    }
}
