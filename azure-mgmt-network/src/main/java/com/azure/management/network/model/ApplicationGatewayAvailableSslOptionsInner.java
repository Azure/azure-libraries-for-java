// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.model;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.Resource;
import com.azure.core.management.SubResource;
import com.azure.management.network.ApplicationGatewaySslCipherSuite;
import com.azure.management.network.ApplicationGatewaySslPolicyName;
import com.azure.management.network.ApplicationGatewaySslProtocol;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The ApplicationGatewayAvailableSslOptions model.
 */
@JsonFlatten
@Fluent
public class ApplicationGatewayAvailableSslOptionsInner extends Resource {
    /*
     * List of available Ssl predefined policy.
     */
    @JsonProperty(value = "properties.predefinedPolicies")
    private List<SubResource> predefinedPolicies;

    /*
     * Ssl predefined policy name enums.
     */
    @JsonProperty(value = "properties.defaultPolicy")
    private ApplicationGatewaySslPolicyName defaultPolicy;

    /*
     * List of available Ssl cipher suites.
     */
    @JsonProperty(value = "properties.availableCipherSuites")
    private List<ApplicationGatewaySslCipherSuite> availableCipherSuites;

    /*
     * List of available Ssl protocols.
     */
    @JsonProperty(value = "properties.availableProtocols")
    private List<ApplicationGatewaySslProtocol> availableProtocols;

    /**
     * Get the predefinedPolicies property: List of available Ssl predefined
     * policy.
     * 
     * @return the predefinedPolicies value.
     */
    public List<SubResource> getPredefinedPolicies() {
        return this.predefinedPolicies;
    }

    /**
     * Set the predefinedPolicies property: List of available Ssl predefined
     * policy.
     * 
     * @param predefinedPolicies the predefinedPolicies value to set.
     * @return the ApplicationGatewayAvailableSslOptionsInner object itself.
     */
    public ApplicationGatewayAvailableSslOptionsInner setPredefinedPolicies(List<SubResource> predefinedPolicies) {
        this.predefinedPolicies = predefinedPolicies;
        return this;
    }

    /**
     * Get the defaultPolicy property: Ssl predefined policy name enums.
     * 
     * @return the defaultPolicy value.
     */
    public ApplicationGatewaySslPolicyName getDefaultPolicy() {
        return this.defaultPolicy;
    }

    /**
     * Set the defaultPolicy property: Ssl predefined policy name enums.
     * 
     * @param defaultPolicy the defaultPolicy value to set.
     * @return the ApplicationGatewayAvailableSslOptionsInner object itself.
     */
    public ApplicationGatewayAvailableSslOptionsInner setDefaultPolicy(ApplicationGatewaySslPolicyName defaultPolicy) {
        this.defaultPolicy = defaultPolicy;
        return this;
    }

    /**
     * Get the availableCipherSuites property: List of available Ssl cipher
     * suites.
     * 
     * @return the availableCipherSuites value.
     */
    public List<ApplicationGatewaySslCipherSuite> getAvailableCipherSuites() {
        return this.availableCipherSuites;
    }

    /**
     * Set the availableCipherSuites property: List of available Ssl cipher
     * suites.
     * 
     * @param availableCipherSuites the availableCipherSuites value to set.
     * @return the ApplicationGatewayAvailableSslOptionsInner object itself.
     */
    public ApplicationGatewayAvailableSslOptionsInner setAvailableCipherSuites(List<ApplicationGatewaySslCipherSuite> availableCipherSuites) {
        this.availableCipherSuites = availableCipherSuites;
        return this;
    }

    /**
     * Get the availableProtocols property: List of available Ssl protocols.
     * 
     * @return the availableProtocols value.
     */
    public List<ApplicationGatewaySslProtocol> getAvailableProtocols() {
        return this.availableProtocols;
    }

    /**
     * Set the availableProtocols property: List of available Ssl protocols.
     * 
     * @param availableProtocols the availableProtocols value to set.
     * @return the ApplicationGatewayAvailableSslOptionsInner object itself.
     */
    public ApplicationGatewayAvailableSslOptionsInner setAvailableProtocols(List<ApplicationGatewaySslProtocol> availableProtocols) {
        this.availableProtocols = availableProtocols;
        return this;
    }
}
