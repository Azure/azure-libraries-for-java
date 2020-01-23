// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.model;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.SubResource;
import com.azure.management.network.IPAllocationMethod;
import com.azure.management.network.IPVersion;
import com.azure.management.network.ProvisioningState;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The PrivateLinkServiceIpConfiguration model.
 */
@JsonFlatten
@Fluent
public class PrivateLinkServiceIpConfigurationInner extends SubResource {
    /*
     * The name of private link service ip configuration.
     */
    @JsonProperty(value = "name")
    private String name;

    /*
     * A unique read-only string that changes whenever the resource is updated.
     */
    @JsonProperty(value = "etag", access = JsonProperty.Access.WRITE_ONLY)
    private String etag;

    /*
     * The resource type.
     */
    @JsonProperty(value = "type", access = JsonProperty.Access.WRITE_ONLY)
    private String type;

    /*
     * The private IP address of the IP configuration.
     */
    @JsonProperty(value = "properties.privateIPAddress")
    private String privateIPAddress;

    /*
     * IP address allocation method.
     */
    @JsonProperty(value = "properties.privateIPAllocationMethod")
    private IPAllocationMethod privateIPAllocationMethod;

    /*
     * Subnet in a virtual network resource.
     */
    @JsonProperty(value = "properties.subnet")
    private SubnetInner subnet;

    /*
     * Whether the ip configuration is primary or not.
     */
    @JsonProperty(value = "properties.primary")
    private Boolean primary;

    /*
     * The current provisioning state.
     */
    @JsonProperty(value = "properties.provisioningState")
    private ProvisioningState provisioningState;

    /*
     * IP address version.
     */
    @JsonProperty(value = "properties.privateIPAddressVersion")
    private IPVersion privateIPAddressVersion;

    /**
     * Get the name property: The name of private link service ip
     * configuration.
     * 
     * @return the name value.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name property: The name of private link service ip
     * configuration.
     * 
     * @param name the name value to set.
     * @return the PrivateLinkServiceIpConfigurationInner object itself.
     */
    public PrivateLinkServiceIpConfigurationInner setName(String name) {
        this.name = name;
        return this;
    }

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
     * Get the type property: The resource type.
     * 
     * @return the type value.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Get the privateIPAddress property: The private IP address of the IP
     * configuration.
     * 
     * @return the privateIPAddress value.
     */
    public String getPrivateIPAddress() {
        return this.privateIPAddress;
    }

    /**
     * Set the privateIPAddress property: The private IP address of the IP
     * configuration.
     * 
     * @param privateIPAddress the privateIPAddress value to set.
     * @return the PrivateLinkServiceIpConfigurationInner object itself.
     */
    public PrivateLinkServiceIpConfigurationInner setPrivateIPAddress(String privateIPAddress) {
        this.privateIPAddress = privateIPAddress;
        return this;
    }

    /**
     * Get the privateIPAllocationMethod property: IP address allocation
     * method.
     * 
     * @return the privateIPAllocationMethod value.
     */
    public IPAllocationMethod getPrivateIPAllocationMethod() {
        return this.privateIPAllocationMethod;
    }

    /**
     * Set the privateIPAllocationMethod property: IP address allocation
     * method.
     * 
     * @param privateIPAllocationMethod the privateIPAllocationMethod value to
     * set.
     * @return the PrivateLinkServiceIpConfigurationInner object itself.
     */
    public PrivateLinkServiceIpConfigurationInner setPrivateIPAllocationMethod(IPAllocationMethod privateIPAllocationMethod) {
        this.privateIPAllocationMethod = privateIPAllocationMethod;
        return this;
    }

    /**
     * Get the subnet property: Subnet in a virtual network resource.
     * 
     * @return the subnet value.
     */
    public SubnetInner getSubnet() {
        return this.subnet;
    }

    /**
     * Set the subnet property: Subnet in a virtual network resource.
     * 
     * @param subnet the subnet value to set.
     * @return the PrivateLinkServiceIpConfigurationInner object itself.
     */
    public PrivateLinkServiceIpConfigurationInner setSubnet(SubnetInner subnet) {
        this.subnet = subnet;
        return this;
    }

    /**
     * Get the primary property: Whether the ip configuration is primary or
     * not.
     * 
     * @return the primary value.
     */
    public Boolean isPrimary() {
        return this.primary;
    }

    /**
     * Set the primary property: Whether the ip configuration is primary or
     * not.
     * 
     * @param primary the primary value to set.
     * @return the PrivateLinkServiceIpConfigurationInner object itself.
     */
    public PrivateLinkServiceIpConfigurationInner setPrimary(Boolean primary) {
        this.primary = primary;
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
     * @return the PrivateLinkServiceIpConfigurationInner object itself.
     */
    public PrivateLinkServiceIpConfigurationInner setProvisioningState(ProvisioningState provisioningState) {
        this.provisioningState = provisioningState;
        return this;
    }

    /**
     * Get the privateIPAddressVersion property: IP address version.
     * 
     * @return the privateIPAddressVersion value.
     */
    public IPVersion getPrivateIPAddressVersion() {
        return this.privateIPAddressVersion;
    }

    /**
     * Set the privateIPAddressVersion property: IP address version.
     * 
     * @param privateIPAddressVersion the privateIPAddressVersion value to set.
     * @return the PrivateLinkServiceIpConfigurationInner object itself.
     */
    public PrivateLinkServiceIpConfigurationInner setPrivateIPAddressVersion(IPVersion privateIPAddressVersion) {
        this.privateIPAddressVersion = privateIPAddressVersion;
        return this;
    }
}