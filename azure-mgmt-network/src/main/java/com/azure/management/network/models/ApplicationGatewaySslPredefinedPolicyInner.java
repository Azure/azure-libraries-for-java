// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.SubResource;
import com.azure.management.network.ApplicationGatewaySslCipherSuite;
import com.azure.management.network.ApplicationGatewaySslProtocol;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The ApplicationGatewaySslPredefinedPolicy model.
 */
@JsonFlatten
@Fluent
public class ApplicationGatewaySslPredefinedPolicyInner extends SubResource {
    /*
     * Name of the Ssl predefined policy.
     */
    @JsonProperty(value = "name")
    private String name;

    /*
     * Ssl cipher suites to be enabled in the specified order for application
     * gateway.
     */
    @JsonProperty(value = "properties.cipherSuites")
    private List<ApplicationGatewaySslCipherSuite> cipherSuites;

    /*
     * Ssl protocol enums.
     */
    @JsonProperty(value = "properties.minProtocolVersion")
    private ApplicationGatewaySslProtocol minProtocolVersion;

    /**
     * Get the name property: Name of the Ssl predefined policy.
     * 
     * @return the name value.
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the name property: Name of the Ssl predefined policy.
     * 
     * @param name the name value to set.
     * @return the ApplicationGatewaySslPredefinedPolicyInner object itself.
     */
    public ApplicationGatewaySslPredefinedPolicyInner withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the cipherSuites property: Ssl cipher suites to be enabled in the
     * specified order for application gateway.
     * 
     * @return the cipherSuites value.
     */
    public List<ApplicationGatewaySslCipherSuite> cipherSuites() {
        return this.cipherSuites;
    }

    /**
     * Set the cipherSuites property: Ssl cipher suites to be enabled in the
     * specified order for application gateway.
     * 
     * @param cipherSuites the cipherSuites value to set.
     * @return the ApplicationGatewaySslPredefinedPolicyInner object itself.
     */
    public ApplicationGatewaySslPredefinedPolicyInner withCipherSuites(List<ApplicationGatewaySslCipherSuite> cipherSuites) {
        this.cipherSuites = cipherSuites;
        return this;
    }

    /**
     * Get the minProtocolVersion property: Ssl protocol enums.
     * 
     * @return the minProtocolVersion value.
     */
    public ApplicationGatewaySslProtocol minProtocolVersion() {
        return this.minProtocolVersion;
    }

    /**
     * Set the minProtocolVersion property: Ssl protocol enums.
     * 
     * @param minProtocolVersion the minProtocolVersion value to set.
     * @return the ApplicationGatewaySslPredefinedPolicyInner object itself.
     */
    public ApplicationGatewaySslPredefinedPolicyInner withMinProtocolVersion(ApplicationGatewaySslProtocol minProtocolVersion) {
        this.minProtocolVersion = minProtocolVersion;
        return this;
    }
}
