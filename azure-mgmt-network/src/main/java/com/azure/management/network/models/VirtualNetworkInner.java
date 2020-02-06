// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.Resource;
import com.azure.core.management.SubResource;
import com.azure.management.network.AddressSpace;
import com.azure.management.network.DhcpOptions;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The VirtualNetwork model.
 */
@JsonFlatten
@Fluent
public class VirtualNetworkInner extends Resource {
    /*
     * Gets a unique read-only string that changes whenever the resource is
     * updated.
     */
    @JsonProperty(value = "etag")
    private String etag;

    /*
     * AddressSpace contains an array of IP address ranges that can be used by
     * subnets of the virtual network.
     */
    @JsonProperty(value = "properties.addressSpace")
    private AddressSpace addressSpace;

    /*
     * DhcpOptions contains an array of DNS servers available to VMs deployed
     * in the virtual network. Standard DHCP option for a subnet overrides VNET
     * DHCP options.
     */
    @JsonProperty(value = "properties.dhcpOptions")
    private DhcpOptions dhcpOptions;

    /*
     * A list of subnets in a Virtual Network.
     */
    @JsonProperty(value = "properties.subnets")
    private List<SubnetInner> subnets;

    /*
     * A list of peerings in a Virtual Network.
     */
    @JsonProperty(value = "properties.virtualNetworkPeerings")
    private List<VirtualNetworkPeeringInner> virtualNetworkPeerings;

    /*
     * The resourceGuid property of the Virtual Network resource.
     */
    @JsonProperty(value = "properties.resourceGuid")
    private String resourceGuid;

    /*
     * The provisioning state of the PublicIP resource. Possible values are:
     * 'Updating', 'Deleting', and 'Failed'.
     */
    @JsonProperty(value = "properties.provisioningState")
    private String provisioningState;

    /*
     * Indicates if DDoS protection is enabled for all the protected resources
     * in the virtual network. It requires a DDoS protection plan associated
     * with the resource.
     */
    @JsonProperty(value = "properties.enableDdosProtection")
    private Boolean enableDdosProtection;

    /*
     * Indicates if VM protection is enabled for all the subnets in the virtual
     * network.
     */
    @JsonProperty(value = "properties.enableVmProtection")
    private Boolean enableVmProtection;

    /*
     * Reference to another subresource.
     */
    @JsonProperty(value = "properties.ddosProtectionPlan")
    private SubResource ddosProtectionPlan;

    /*
     * Resource ID.
     */
    @JsonProperty(value = "id")
    private String id;

    /**
     * Get the etag property: Gets a unique read-only string that changes
     * whenever the resource is updated.
     * 
     * @return the etag value.
     */
    public String etag() {
        return this.etag;
    }

    /**
     * Set the etag property: Gets a unique read-only string that changes
     * whenever the resource is updated.
     * 
     * @param etag the etag value to set.
     * @return the VirtualNetworkInner object itself.
     */
    public VirtualNetworkInner withEtag(String etag) {
        this.etag = etag;
        return this;
    }

    /**
     * Get the addressSpace property: AddressSpace contains an array of IP
     * address ranges that can be used by subnets of the virtual network.
     * 
     * @return the addressSpace value.
     */
    public AddressSpace addressSpace() {
        return this.addressSpace;
    }

    /**
     * Set the addressSpace property: AddressSpace contains an array of IP
     * address ranges that can be used by subnets of the virtual network.
     * 
     * @param addressSpace the addressSpace value to set.
     * @return the VirtualNetworkInner object itself.
     */
    public VirtualNetworkInner withAddressSpace(AddressSpace addressSpace) {
        this.addressSpace = addressSpace;
        return this;
    }

    /**
     * Get the dhcpOptions property: DhcpOptions contains an array of DNS
     * servers available to VMs deployed in the virtual network. Standard DHCP
     * option for a subnet overrides VNET DHCP options.
     * 
     * @return the dhcpOptions value.
     */
    public DhcpOptions dhcpOptions() {
        return this.dhcpOptions;
    }

    /**
     * Set the dhcpOptions property: DhcpOptions contains an array of DNS
     * servers available to VMs deployed in the virtual network. Standard DHCP
     * option for a subnet overrides VNET DHCP options.
     * 
     * @param dhcpOptions the dhcpOptions value to set.
     * @return the VirtualNetworkInner object itself.
     */
    public VirtualNetworkInner withDhcpOptions(DhcpOptions dhcpOptions) {
        this.dhcpOptions = dhcpOptions;
        return this;
    }

    /**
     * Get the subnets property: A list of subnets in a Virtual Network.
     * 
     * @return the subnets value.
     */
    public List<SubnetInner> subnets() {
        return this.subnets;
    }

    /**
     * Set the subnets property: A list of subnets in a Virtual Network.
     * 
     * @param subnets the subnets value to set.
     * @return the VirtualNetworkInner object itself.
     */
    public VirtualNetworkInner withSubnets(List<SubnetInner> subnets) {
        this.subnets = subnets;
        return this;
    }

    /**
     * Get the virtualNetworkPeerings property: A list of peerings in a Virtual
     * Network.
     * 
     * @return the virtualNetworkPeerings value.
     */
    public List<VirtualNetworkPeeringInner> virtualNetworkPeerings() {
        return this.virtualNetworkPeerings;
    }

    /**
     * Set the virtualNetworkPeerings property: A list of peerings in a Virtual
     * Network.
     * 
     * @param virtualNetworkPeerings the virtualNetworkPeerings value to set.
     * @return the VirtualNetworkInner object itself.
     */
    public VirtualNetworkInner withVirtualNetworkPeerings(List<VirtualNetworkPeeringInner> virtualNetworkPeerings) {
        this.virtualNetworkPeerings = virtualNetworkPeerings;
        return this;
    }

    /**
     * Get the resourceGuid property: The resourceGuid property of the Virtual
     * Network resource.
     * 
     * @return the resourceGuid value.
     */
    public String resourceGuid() {
        return this.resourceGuid;
    }

    /**
     * Set the resourceGuid property: The resourceGuid property of the Virtual
     * Network resource.
     * 
     * @param resourceGuid the resourceGuid value to set.
     * @return the VirtualNetworkInner object itself.
     */
    public VirtualNetworkInner withResourceGuid(String resourceGuid) {
        this.resourceGuid = resourceGuid;
        return this;
    }

    /**
     * Get the provisioningState property: The provisioning state of the
     * PublicIP resource. Possible values are: 'Updating', 'Deleting', and
     * 'Failed'.
     * 
     * @return the provisioningState value.
     */
    public String provisioningState() {
        return this.provisioningState;
    }

    /**
     * Set the provisioningState property: The provisioning state of the
     * PublicIP resource. Possible values are: 'Updating', 'Deleting', and
     * 'Failed'.
     * 
     * @param provisioningState the provisioningState value to set.
     * @return the VirtualNetworkInner object itself.
     */
    public VirtualNetworkInner withProvisioningState(String provisioningState) {
        this.provisioningState = provisioningState;
        return this;
    }

    /**
     * Get the enableDdosProtection property: Indicates if DDoS protection is
     * enabled for all the protected resources in the virtual network. It
     * requires a DDoS protection plan associated with the resource.
     * 
     * @return the enableDdosProtection value.
     */
    public Boolean enableDdosProtection() {
        return this.enableDdosProtection;
    }

    /**
     * Set the enableDdosProtection property: Indicates if DDoS protection is
     * enabled for all the protected resources in the virtual network. It
     * requires a DDoS protection plan associated with the resource.
     * 
     * @param enableDdosProtection the enableDdosProtection value to set.
     * @return the VirtualNetworkInner object itself.
     */
    public VirtualNetworkInner withEnableDdosProtection(Boolean enableDdosProtection) {
        this.enableDdosProtection = enableDdosProtection;
        return this;
    }

    /**
     * Get the enableVmProtection property: Indicates if VM protection is
     * enabled for all the subnets in the virtual network.
     * 
     * @return the enableVmProtection value.
     */
    public Boolean enableVmProtection() {
        return this.enableVmProtection;
    }

    /**
     * Set the enableVmProtection property: Indicates if VM protection is
     * enabled for all the subnets in the virtual network.
     * 
     * @param enableVmProtection the enableVmProtection value to set.
     * @return the VirtualNetworkInner object itself.
     */
    public VirtualNetworkInner withEnableVmProtection(Boolean enableVmProtection) {
        this.enableVmProtection = enableVmProtection;
        return this;
    }

    /**
     * Get the ddosProtectionPlan property: Reference to another subresource.
     * 
     * @return the ddosProtectionPlan value.
     */
    public SubResource ddosProtectionPlan() {
        return this.ddosProtectionPlan;
    }

    /**
     * Set the ddosProtectionPlan property: Reference to another subresource.
     * 
     * @param ddosProtectionPlan the ddosProtectionPlan value to set.
     * @return the VirtualNetworkInner object itself.
     */
    public VirtualNetworkInner withDdosProtectionPlan(SubResource ddosProtectionPlan) {
        this.ddosProtectionPlan = ddosProtectionPlan;
        return this;
    }

    /**
     * Get the id property: Resource ID.
     * 
     * @return the id value.
     */
    public String id() {
        return this.id;
    }

    /**
     * Set the id property: Resource ID.
     * 
     * @param id the id value to set.
     * @return the VirtualNetworkInner object itself.
     */
    public VirtualNetworkInner withId(String id) {
        this.id = id;
        return this;
    }
}
