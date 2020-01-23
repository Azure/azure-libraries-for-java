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
import com.azure.management.network.AddressSpace;
import com.azure.management.network.ProvisioningState;
import com.azure.management.network.VpnClientConnectionHealth;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The P2SVpnGateway model.
 */
@JsonFlatten
@Fluent
public class P2SVpnGatewayInner extends Resource {
    /*
     * Gets a unique read-only string that changes whenever the resource is
     * updated.
     */
    @JsonProperty(value = "etag", access = JsonProperty.Access.WRITE_ONLY)
    private String etag;

    /*
     * Reference to another subresource.
     */
    @JsonProperty(value = "properties.virtualHub")
    private SubResource virtualHub;

    /*
     * The current provisioning state.
     */
    @JsonProperty(value = "properties.provisioningState")
    private ProvisioningState provisioningState;

    /*
     * The scale unit for this p2s vpn gateway.
     */
    @JsonProperty(value = "properties.vpnGatewayScaleUnit")
    private Integer vpnGatewayScaleUnit;

    /*
     * Reference to another subresource.
     */
    @JsonProperty(value = "properties.p2SVpnServerConfiguration")
    private SubResource p2SVpnServerConfiguration;

    /*
     * AddressSpace contains an array of IP address ranges that can be used by
     * subnets of the virtual network.
     */
    @JsonProperty(value = "properties.vpnClientAddressPool")
    private AddressSpace vpnClientAddressPool;

    /*
     * AddressSpace contains an array of IP address ranges that can be used by
     * subnets of the virtual network.
     */
    @JsonProperty(value = "properties.customRoutes")
    private AddressSpace customRoutes;

    /*
     * VpnClientConnectionHealth properties.
     */
    @JsonProperty(value = "properties.vpnClientConnectionHealth", access = JsonProperty.Access.WRITE_ONLY)
    private VpnClientConnectionHealth vpnClientConnectionHealth;

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
     * Get the virtualHub property: Reference to another subresource.
     * 
     * @return the virtualHub value.
     */
    public SubResource getVirtualHub() {
        return this.virtualHub;
    }

    /**
     * Set the virtualHub property: Reference to another subresource.
     * 
     * @param virtualHub the virtualHub value to set.
     * @return the P2SVpnGatewayInner object itself.
     */
    public P2SVpnGatewayInner setVirtualHub(SubResource virtualHub) {
        this.virtualHub = virtualHub;
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
     * @return the P2SVpnGatewayInner object itself.
     */
    public P2SVpnGatewayInner setProvisioningState(ProvisioningState provisioningState) {
        this.provisioningState = provisioningState;
        return this;
    }

    /**
     * Get the vpnGatewayScaleUnit property: The scale unit for this p2s vpn
     * gateway.
     * 
     * @return the vpnGatewayScaleUnit value.
     */
    public Integer getVpnGatewayScaleUnit() {
        return this.vpnGatewayScaleUnit;
    }

    /**
     * Set the vpnGatewayScaleUnit property: The scale unit for this p2s vpn
     * gateway.
     * 
     * @param vpnGatewayScaleUnit the vpnGatewayScaleUnit value to set.
     * @return the P2SVpnGatewayInner object itself.
     */
    public P2SVpnGatewayInner setVpnGatewayScaleUnit(Integer vpnGatewayScaleUnit) {
        this.vpnGatewayScaleUnit = vpnGatewayScaleUnit;
        return this;
    }

    /**
     * Get the p2SVpnServerConfiguration property: Reference to another
     * subresource.
     * 
     * @return the p2SVpnServerConfiguration value.
     */
    public SubResource getP2SVpnServerConfiguration() {
        return this.p2SVpnServerConfiguration;
    }

    /**
     * Set the p2SVpnServerConfiguration property: Reference to another
     * subresource.
     * 
     * @param p2SVpnServerConfiguration the p2SVpnServerConfiguration value to
     * set.
     * @return the P2SVpnGatewayInner object itself.
     */
    public P2SVpnGatewayInner setP2SVpnServerConfiguration(SubResource p2SVpnServerConfiguration) {
        this.p2SVpnServerConfiguration = p2SVpnServerConfiguration;
        return this;
    }

    /**
     * Get the vpnClientAddressPool property: AddressSpace contains an array of
     * IP address ranges that can be used by subnets of the virtual network.
     * 
     * @return the vpnClientAddressPool value.
     */
    public AddressSpace getVpnClientAddressPool() {
        return this.vpnClientAddressPool;
    }

    /**
     * Set the vpnClientAddressPool property: AddressSpace contains an array of
     * IP address ranges that can be used by subnets of the virtual network.
     * 
     * @param vpnClientAddressPool the vpnClientAddressPool value to set.
     * @return the P2SVpnGatewayInner object itself.
     */
    public P2SVpnGatewayInner setVpnClientAddressPool(AddressSpace vpnClientAddressPool) {
        this.vpnClientAddressPool = vpnClientAddressPool;
        return this;
    }

    /**
     * Get the customRoutes property: AddressSpace contains an array of IP
     * address ranges that can be used by subnets of the virtual network.
     * 
     * @return the customRoutes value.
     */
    public AddressSpace getCustomRoutes() {
        return this.customRoutes;
    }

    /**
     * Set the customRoutes property: AddressSpace contains an array of IP
     * address ranges that can be used by subnets of the virtual network.
     * 
     * @param customRoutes the customRoutes value to set.
     * @return the P2SVpnGatewayInner object itself.
     */
    public P2SVpnGatewayInner setCustomRoutes(AddressSpace customRoutes) {
        this.customRoutes = customRoutes;
        return this;
    }

    /**
     * Get the vpnClientConnectionHealth property: VpnClientConnectionHealth
     * properties.
     * 
     * @return the vpnClientConnectionHealth value.
     */
    public VpnClientConnectionHealth getVpnClientConnectionHealth() {
        return this.vpnClientConnectionHealth;
    }
}