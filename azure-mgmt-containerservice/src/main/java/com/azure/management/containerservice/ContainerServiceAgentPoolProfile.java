// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.containerservice;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The ContainerServiceAgentPoolProfile model.
 */
@Fluent
public final class ContainerServiceAgentPoolProfile {
    /*
     * Unique name of the agent pool profile in the context of the subscription
     * and resource group.
     */
    @JsonProperty(value = "name", required = true)
    private String name;

    /*
     * Number of agents (VMs) to host docker containers. Allowed values must be
     * in the range of 1 to 100 (inclusive). The default value is 1.
     */
    @JsonProperty(value = "count")
    private Integer count;

    /*
     * Size of agent VMs.
     */
    @JsonProperty(value = "vmSize", required = true)
    private ContainerServiceVMSizeTypes vmSize;

    /*
     * OS Disk Size in GB to be used to specify the disk size for every machine
     * in this master/agent pool. If you specify 0, it will apply the default
     * osDisk size according to the vmSize specified.
     */
    @JsonProperty(value = "osDiskSizeGB")
    private Integer osDiskSizeGB;

    /*
     * DNS prefix to be used to create the FQDN for the agent pool.
     */
    @JsonProperty(value = "dnsPrefix")
    private String dnsPrefix;

    /*
     * FQDN for the agent pool.
     */
    @JsonProperty(value = "fqdn", access = JsonProperty.Access.WRITE_ONLY)
    private String fqdn;

    /*
     * Ports number array used to expose on this agent pool. The default opened
     * ports are different based on your choice of orchestrator.
     */
    @JsonProperty(value = "ports")
    private List<Integer> ports;

    /*
     * Storage profile specifies what kind of storage used. Choose from
     * StorageAccount and ManagedDisks. Leave it empty, we will choose for you
     * based on the orchestrator choice.
     */
    @JsonProperty(value = "storageProfile")
    private ContainerServiceStorageProfileTypes storageProfile;

    /*
     * VNet SubnetID specifies the VNet's subnet identifier.
     */
    @JsonProperty(value = "vnetSubnetID")
    private String vnetSubnetID;

    /*
     * OsType to be used to specify os type. Choose from Linux and Windows.
     * Default to Linux.
     */
    @JsonProperty(value = "osType")
    private OSType osType;

    /**
     * Get the name property: Unique name of the agent pool profile in the
     * context of the subscription and resource group.
     * 
     * @return the name value.
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the name property: Unique name of the agent pool profile in the
     * context of the subscription and resource group.
     * 
     * @param name the name value to set.
     * @return the ContainerServiceAgentPoolProfile object itself.
     */
    public ContainerServiceAgentPoolProfile withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the count property: Number of agents (VMs) to host docker
     * containers. Allowed values must be in the range of 1 to 100 (inclusive).
     * The default value is 1.
     * 
     * @return the count value.
     */
    public Integer count() {
        return this.count;
    }

    /**
     * Set the count property: Number of agents (VMs) to host docker
     * containers. Allowed values must be in the range of 1 to 100 (inclusive).
     * The default value is 1.
     * 
     * @param count the count value to set.
     * @return the ContainerServiceAgentPoolProfile object itself.
     */
    public ContainerServiceAgentPoolProfile withCount(Integer count) {
        this.count = count;
        return this;
    }

    /**
     * Get the vmSize property: Size of agent VMs.
     * 
     * @return the vmSize value.
     */
    public ContainerServiceVMSizeTypes vmSize() {
        return this.vmSize;
    }

    /**
     * Set the vmSize property: Size of agent VMs.
     * 
     * @param vmSize the vmSize value to set.
     * @return the ContainerServiceAgentPoolProfile object itself.
     */
    public ContainerServiceAgentPoolProfile withVmSize(ContainerServiceVMSizeTypes vmSize) {
        this.vmSize = vmSize;
        return this;
    }

    /**
     * Get the osDiskSizeGB property: OS Disk Size in GB to be used to specify
     * the disk size for every machine in this master/agent pool. If you
     * specify 0, it will apply the default osDisk size according to the vmSize
     * specified.
     * 
     * @return the osDiskSizeGB value.
     */
    public Integer osDiskSizeGB() {
        return this.osDiskSizeGB;
    }

    /**
     * Set the osDiskSizeGB property: OS Disk Size in GB to be used to specify
     * the disk size for every machine in this master/agent pool. If you
     * specify 0, it will apply the default osDisk size according to the vmSize
     * specified.
     * 
     * @param osDiskSizeGB the osDiskSizeGB value to set.
     * @return the ContainerServiceAgentPoolProfile object itself.
     */
    public ContainerServiceAgentPoolProfile withOsDiskSizeGB(Integer osDiskSizeGB) {
        this.osDiskSizeGB = osDiskSizeGB;
        return this;
    }

    /**
     * Get the dnsPrefix property: DNS prefix to be used to create the FQDN for
     * the agent pool.
     * 
     * @return the dnsPrefix value.
     */
    public String dnsPrefix() {
        return this.dnsPrefix;
    }

    /**
     * Set the dnsPrefix property: DNS prefix to be used to create the FQDN for
     * the agent pool.
     * 
     * @param dnsPrefix the dnsPrefix value to set.
     * @return the ContainerServiceAgentPoolProfile object itself.
     */
    public ContainerServiceAgentPoolProfile withDnsPrefix(String dnsPrefix) {
        this.dnsPrefix = dnsPrefix;
        return this;
    }

    /**
     * Get the fqdn property: FQDN for the agent pool.
     * 
     * @return the fqdn value.
     */
    public String fqdn() {
        return this.fqdn;
    }

    /**
     * Get the ports property: Ports number array used to expose on this agent
     * pool. The default opened ports are different based on your choice of
     * orchestrator.
     * 
     * @return the ports value.
     */
    public List<Integer> ports() {
        return this.ports;
    }

    /**
     * Set the ports property: Ports number array used to expose on this agent
     * pool. The default opened ports are different based on your choice of
     * orchestrator.
     * 
     * @param ports the ports value to set.
     * @return the ContainerServiceAgentPoolProfile object itself.
     */
    public ContainerServiceAgentPoolProfile withPorts(List<Integer> ports) {
        this.ports = ports;
        return this;
    }

    /**
     * Get the storageProfile property: Storage profile specifies what kind of
     * storage used. Choose from StorageAccount and ManagedDisks. Leave it
     * empty, we will choose for you based on the orchestrator choice.
     * 
     * @return the storageProfile value.
     */
    public ContainerServiceStorageProfileTypes storageProfile() {
        return this.storageProfile;
    }

    /**
     * Set the storageProfile property: Storage profile specifies what kind of
     * storage used. Choose from StorageAccount and ManagedDisks. Leave it
     * empty, we will choose for you based on the orchestrator choice.
     * 
     * @param storageProfile the storageProfile value to set.
     * @return the ContainerServiceAgentPoolProfile object itself.
     */
    public ContainerServiceAgentPoolProfile withStorageProfile(ContainerServiceStorageProfileTypes storageProfile) {
        this.storageProfile = storageProfile;
        return this;
    }

    /**
     * Get the vnetSubnetID property: VNet SubnetID specifies the VNet's subnet
     * identifier.
     * 
     * @return the vnetSubnetID value.
     */
    public String vnetSubnetID() {
        return this.vnetSubnetID;
    }

    /**
     * Set the vnetSubnetID property: VNet SubnetID specifies the VNet's subnet
     * identifier.
     * 
     * @param vnetSubnetID the vnetSubnetID value to set.
     * @return the ContainerServiceAgentPoolProfile object itself.
     */
    public ContainerServiceAgentPoolProfile withVnetSubnetID(String vnetSubnetID) {
        this.vnetSubnetID = vnetSubnetID;
        return this;
    }

    /**
     * Get the osType property: OsType to be used to specify os type. Choose
     * from Linux and Windows. Default to Linux.
     * 
     * @return the osType value.
     */
    public OSType osType() {
        return this.osType;
    }

    /**
     * Set the osType property: OsType to be used to specify os type. Choose
     * from Linux and Windows. Default to Linux.
     * 
     * @param osType the osType value to set.
     * @return the ContainerServiceAgentPoolProfile object itself.
     */
    public ContainerServiceAgentPoolProfile withOsType(OSType osType) {
        this.osType = osType;
        return this;
    }
}
