// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.model;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.Resource;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The NetworkSecurityGroup model.
 */
@JsonFlatten
@Fluent
public class NetworkSecurityGroupInner extends Resource {
    /*
     * A unique read-only string that changes whenever the resource is updated.
     */
    @JsonProperty(value = "etag")
    private String etag;

    /*
     * A collection of security rules of the network security group.
     */
    @JsonProperty(value = "properties.securityRules")
    private List<SecurityRuleInner> securityRules;

    /*
     * The default security rules of network security group.
     */
    @JsonProperty(value = "properties.defaultSecurityRules")
    private List<SecurityRuleInner> defaultSecurityRules;

    /*
     * A collection of references to network interfaces.
     */
    @JsonProperty(value = "properties.networkInterfaces", access = JsonProperty.Access.WRITE_ONLY)
    private List<NetworkInterfaceInner> networkInterfaces;

    /*
     * A collection of references to subnets.
     */
    @JsonProperty(value = "properties.subnets", access = JsonProperty.Access.WRITE_ONLY)
    private List<SubnetInner> subnets;

    /*
     * The resource GUID property of the network security group resource.
     */
    @JsonProperty(value = "properties.resourceGuid")
    private String resourceGuid;

    /*
     * The provisioning state of the public IP resource. Possible values are:
     * 'Updating', 'Deleting', and 'Failed'.
     */
    @JsonProperty(value = "properties.provisioningState")
    private String provisioningState;

    /**
     * Get the etag property: A unique read-only string that changes whenever
     * the resource is updated.
     * 
     * @return the etag value.
     */
    public String getEtag() {
        return this.etag;
    }

    /**
     * Set the etag property: A unique read-only string that changes whenever
     * the resource is updated.
     * 
     * @param etag the etag value to set.
     * @return the NetworkSecurityGroupInner object itself.
     */
    public NetworkSecurityGroupInner setEtag(String etag) {
        this.etag = etag;
        return this;
    }

    /**
     * Get the securityRules property: A collection of security rules of the
     * network security group.
     * 
     * @return the securityRules value.
     */
    public List<SecurityRuleInner> getSecurityRules() {
        return this.securityRules;
    }

    /**
     * Set the securityRules property: A collection of security rules of the
     * network security group.
     * 
     * @param securityRules the securityRules value to set.
     * @return the NetworkSecurityGroupInner object itself.
     */
    public NetworkSecurityGroupInner setSecurityRules(List<SecurityRuleInner> securityRules) {
        this.securityRules = securityRules;
        return this;
    }

    /**
     * Get the defaultSecurityRules property: The default security rules of
     * network security group.
     * 
     * @return the defaultSecurityRules value.
     */
    public List<SecurityRuleInner> getDefaultSecurityRules() {
        return this.defaultSecurityRules;
    }

    /**
     * Set the defaultSecurityRules property: The default security rules of
     * network security group.
     * 
     * @param defaultSecurityRules the defaultSecurityRules value to set.
     * @return the NetworkSecurityGroupInner object itself.
     */
    public NetworkSecurityGroupInner setDefaultSecurityRules(List<SecurityRuleInner> defaultSecurityRules) {
        this.defaultSecurityRules = defaultSecurityRules;
        return this;
    }

    /**
     * Get the networkInterfaces property: A collection of references to
     * network interfaces.
     * 
     * @return the networkInterfaces value.
     */
    public List<NetworkInterfaceInner> getNetworkInterfaces() {
        return this.networkInterfaces;
    }

    /**
     * Get the subnets property: A collection of references to subnets.
     * 
     * @return the subnets value.
     */
    public List<SubnetInner> getSubnets() {
        return this.subnets;
    }

    /**
     * Get the resourceGuid property: The resource GUID property of the network
     * security group resource.
     * 
     * @return the resourceGuid value.
     */
    public String getResourceGuid() {
        return this.resourceGuid;
    }

    /**
     * Set the resourceGuid property: The resource GUID property of the network
     * security group resource.
     * 
     * @param resourceGuid the resourceGuid value to set.
     * @return the NetworkSecurityGroupInner object itself.
     */
    public NetworkSecurityGroupInner setResourceGuid(String resourceGuid) {
        this.resourceGuid = resourceGuid;
        return this;
    }

    /**
     * Get the provisioningState property: The provisioning state of the public
     * IP resource. Possible values are: 'Updating', 'Deleting', and 'Failed'.
     * 
     * @return the provisioningState value.
     */
    public String getProvisioningState() {
        return this.provisioningState;
    }

    /**
     * Set the provisioningState property: The provisioning state of the public
     * IP resource. Possible values are: 'Updating', 'Deleting', and 'Failed'.
     * 
     * @param provisioningState the provisioningState value to set.
     * @return the NetworkSecurityGroupInner object itself.
     */
    public NetworkSecurityGroupInner setProvisioningState(String provisioningState) {
        this.provisioningState = provisioningState;
        return this;
    }
}
