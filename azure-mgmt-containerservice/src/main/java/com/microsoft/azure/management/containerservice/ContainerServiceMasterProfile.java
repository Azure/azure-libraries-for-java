/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.containerservice;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Profile for the container service master.
 */
public class ContainerServiceMasterProfile {
    /**
     * Number of masters (VMs) in the container service cluster. Allowed values
     * are 1, 3, and 5. The default value is 1.
     */
    @JsonProperty(value = "count")
    private Integer count;

    /**
     * DNS prefix to be used to create the FQDN for the master pool.
     */
    @JsonProperty(value = "dnsPrefix", required = true)
    private String dnsPrefix;

    /**
     * Size of agent VMs. Possible values include: 'Standard_A1',
     * 'Standard_A10', 'Standard_A11', 'Standard_A1_v2', 'Standard_A2',
     * 'Standard_A2_v2', 'Standard_A2m_v2', 'Standard_A3', 'Standard_A4',
     * 'Standard_A4_v2', 'Standard_A4m_v2', 'Standard_A5', 'Standard_A6',
     * 'Standard_A7', 'Standard_A8', 'Standard_A8_v2', 'Standard_A8m_v2',
     * 'Standard_A9', 'Standard_B2ms', 'Standard_B2s', 'Standard_B4ms',
     * 'Standard_B8ms', 'Standard_D1', 'Standard_D11', 'Standard_D11_v2',
     * 'Standard_D11_v2_Promo', 'Standard_D12', 'Standard_D12_v2',
     * 'Standard_D12_v2_Promo', 'Standard_D13', 'Standard_D13_v2',
     * 'Standard_D13_v2_Promo', 'Standard_D14', 'Standard_D14_v2',
     * 'Standard_D14_v2_Promo', 'Standard_D15_v2', 'Standard_D16_v3',
     * 'Standard_D16s_v3', 'Standard_D1_v2', 'Standard_D2', 'Standard_D2_v2',
     * 'Standard_D2_v2_Promo', 'Standard_D2_v3', 'Standard_D2s_v3',
     * 'Standard_D3', 'Standard_D32_v3', 'Standard_D32s_v3', 'Standard_D3_v2',
     * 'Standard_D3_v2_Promo', 'Standard_D4', 'Standard_D4_v2',
     * 'Standard_D4_v2_Promo', 'Standard_D4_v3', 'Standard_D4s_v3',
     * 'Standard_D5_v2', 'Standard_D5_v2_Promo', 'Standard_D64_v3',
     * 'Standard_D64s_v3', 'Standard_D8_v3', 'Standard_D8s_v3', 'Standard_DS1',
     * 'Standard_DS11', 'Standard_DS11_v2', 'Standard_DS11_v2_Promo',
     * 'Standard_DS12', 'Standard_DS12_v2', 'Standard_DS12_v2_Promo',
     * 'Standard_DS13', 'Standard_DS13-2_v2', 'Standard_DS13-4_v2',
     * 'Standard_DS13_v2', 'Standard_DS13_v2_Promo', 'Standard_DS14',
     * 'Standard_DS14-4_v2', 'Standard_DS14-8_v2', 'Standard_DS14_v2',
     * 'Standard_DS14_v2_Promo', 'Standard_DS15_v2', 'Standard_DS1_v2',
     * 'Standard_DS2', 'Standard_DS2_v2', 'Standard_DS2_v2_Promo',
     * 'Standard_DS3', 'Standard_DS3_v2', 'Standard_DS3_v2_Promo',
     * 'Standard_DS4', 'Standard_DS4_v2', 'Standard_DS4_v2_Promo',
     * 'Standard_DS5_v2', 'Standard_DS5_v2_Promo', 'Standard_E16_v3',
     * 'Standard_E16s_v3', 'Standard_E2_v3', 'Standard_E2s_v3',
     * 'Standard_E32-16s_v3', 'Standard_E32-8s_v3', 'Standard_E32_v3',
     * 'Standard_E32s_v3', 'Standard_E4_v3', 'Standard_E4s_v3',
     * 'Standard_E64-16s_v3', 'Standard_E64-32s_v3', 'Standard_E64_v3',
     * 'Standard_E64s_v3', 'Standard_E8_v3', 'Standard_E8s_v3', 'Standard_F1',
     * 'Standard_F16', 'Standard_F16s', 'Standard_F16s_v2', 'Standard_F1s',
     * 'Standard_F2', 'Standard_F2s', 'Standard_F2s_v2', 'Standard_F32s_v2',
     * 'Standard_F4', 'Standard_F4s', 'Standard_F4s_v2', 'Standard_F64s_v2',
     * 'Standard_F72s_v2', 'Standard_F8', 'Standard_F8s', 'Standard_F8s_v2',
     * 'Standard_G1', 'Standard_G2', 'Standard_G3', 'Standard_G4',
     * 'Standard_G5', 'Standard_GS1', 'Standard_GS2', 'Standard_GS3',
     * 'Standard_GS4', 'Standard_GS4-4', 'Standard_GS4-8', 'Standard_GS5',
     * 'Standard_GS5-16', 'Standard_GS5-8', 'Standard_H16', 'Standard_H16m',
     * 'Standard_H16mr', 'Standard_H16r', 'Standard_H8', 'Standard_H8m',
     * 'Standard_L16s', 'Standard_L32s', 'Standard_L4s', 'Standard_L8s',
     * 'Standard_M128-32ms', 'Standard_M128-64ms', 'Standard_M128ms',
     * 'Standard_M128s', 'Standard_M64-16ms', 'Standard_M64-32ms',
     * 'Standard_M64ms', 'Standard_M64s', 'Standard_NC12', 'Standard_NC12s_v2',
     * 'Standard_NC12s_v3', 'Standard_NC24', 'Standard_NC24r',
     * 'Standard_NC24rs_v2', 'Standard_NC24rs_v3', 'Standard_NC24s_v2',
     * 'Standard_NC24s_v3', 'Standard_NC6', 'Standard_NC6s_v2',
     * 'Standard_NC6s_v3', 'Standard_ND12s', 'Standard_ND24rs',
     * 'Standard_ND24s', 'Standard_ND6s', 'Standard_NV12', 'Standard_NV24',
     * 'Standard_NV6'.
     */
    @JsonProperty(value = "vmSize", required = true)
    private ContainerServiceVMSizeTypes vmSize;

    /**
     * OS Disk Size in GB to be used to specify the disk size for every machine
     * in this master/agent pool. If you specify 0, it will apply the default
     * osDisk size according to the vmSize specified.
     */
    @JsonProperty(value = "osDiskSizeGB")
    private Integer osDiskSizeGB;

    /**
     * VNet SubnetID specifies the vnet's subnet identifier. If you specify
     * either master VNet Subnet, or agent VNet Subnet, you need to specify
     * both. And they have to be in the same VNet.
     */
    @JsonProperty(value = "vnetSubnetID")
    private String vnetSubnetID;

    /**
     * FirstConsecutiveStaticIP used to specify the first static ip of masters.
     */
    @JsonProperty(value = "firstConsecutiveStaticIP")
    private String firstConsecutiveStaticIP;

    /**
     * Storage profile specifies what kind of storage used. Choose from
     * StorageAccount and ManagedDisks. Leave it empty, we will choose for you
     * based on the orchestrator choice. Possible values include:
     * 'StorageAccount', 'ManagedDisks'.
     */
    @JsonProperty(value = "storageProfile")
    private ContainerServiceStorageProfileTypes storageProfile;

    /**
     * FDQN for the master pool.
     */
    @JsonProperty(value = "fqdn", access = JsonProperty.Access.WRITE_ONLY)
    private String fqdn;

    /**
     * Get the count value.
     *
     * @return the count value
     */
    public Integer count() {
        return this.count;
    }

    /**
     * Set the count value.
     *
     * @param count the count value to set
     * @return the ContainerServiceMasterProfile object itself.
     */
    public ContainerServiceMasterProfile withCount(Integer count) {
        this.count = count;
        return this;
    }

    /**
     * Get the dnsPrefix value.
     *
     * @return the dnsPrefix value
     */
    public String dnsPrefix() {
        return this.dnsPrefix;
    }

    /**
     * Set the dnsPrefix value.
     *
     * @param dnsPrefix the dnsPrefix value to set
     * @return the ContainerServiceMasterProfile object itself.
     */
    public ContainerServiceMasterProfile withDnsPrefix(String dnsPrefix) {
        this.dnsPrefix = dnsPrefix;
        return this;
    }

    /**
     * Get the vmSize value.
     *
     * @return the vmSize value
     */
    public ContainerServiceVMSizeTypes vmSize() {
        return this.vmSize;
    }

    /**
     * Set the vmSize value.
     *
     * @param vmSize the vmSize value to set
     * @return the ContainerServiceMasterProfile object itself.
     */
    public ContainerServiceMasterProfile withVmSize(ContainerServiceVMSizeTypes vmSize) {
        this.vmSize = vmSize;
        return this;
    }

    /**
     * Get the osDiskSizeGB value.
     *
     * @return the osDiskSizeGB value
     */
    public Integer osDiskSizeGB() {
        return this.osDiskSizeGB;
    }

    /**
     * Set the osDiskSizeGB value.
     *
     * @param osDiskSizeGB the osDiskSizeGB value to set
     * @return the ContainerServiceMasterProfile object itself.
     */
    public ContainerServiceMasterProfile withOsDiskSizeGB(Integer osDiskSizeGB) {
        this.osDiskSizeGB = osDiskSizeGB;
        return this;
    }

    /**
     * Get the vnetSubnetID value.
     *
     * @return the vnetSubnetID value
     */
    public String vnetSubnetID() {
        return this.vnetSubnetID;
    }

    /**
     * Set the vnetSubnetID value.
     *
     * @param vnetSubnetID the vnetSubnetID value to set
     * @return the ContainerServiceMasterProfile object itself.
     */
    public ContainerServiceMasterProfile withVnetSubnetID(String vnetSubnetID) {
        this.vnetSubnetID = vnetSubnetID;
        return this;
    }

    /**
     * Get the firstConsecutiveStaticIP value.
     *
     * @return the firstConsecutiveStaticIP value
     */
    public String firstConsecutiveStaticIP() {
        return this.firstConsecutiveStaticIP;
    }

    /**
     * Set the firstConsecutiveStaticIP value.
     *
     * @param firstConsecutiveStaticIP the firstConsecutiveStaticIP value to set
     * @return the ContainerServiceMasterProfile object itself.
     */
    public ContainerServiceMasterProfile withFirstConsecutiveStaticIP(String firstConsecutiveStaticIP) {
        this.firstConsecutiveStaticIP = firstConsecutiveStaticIP;
        return this;
    }

    /**
     * Get the storageProfile value.
     *
     * @return the storageProfile value
     */
    public ContainerServiceStorageProfileTypes storageProfile() {
        return this.storageProfile;
    }

    /**
     * Set the storageProfile value.
     *
     * @param storageProfile the storageProfile value to set
     * @return the ContainerServiceMasterProfile object itself.
     */
    public ContainerServiceMasterProfile withStorageProfile(ContainerServiceStorageProfileTypes storageProfile) {
        this.storageProfile = storageProfile;
        return this;
    }

    /**
     * Get the fqdn value.
     *
     * @return the fqdn value
     */
    public String fqdn() {
        return this.fqdn;
    }

}
