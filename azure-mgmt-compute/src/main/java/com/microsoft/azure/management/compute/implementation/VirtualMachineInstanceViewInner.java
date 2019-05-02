/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.compute.implementation;

import com.microsoft.azure.management.compute.VirtualMachineAgentInstanceView;
import com.microsoft.azure.management.compute.MaintenanceRedeployStatus;
import java.util.List;
import com.microsoft.azure.management.compute.DiskInstanceView;
import com.microsoft.azure.management.compute.VirtualMachineExtensionInstanceView;
import com.microsoft.azure.management.compute.BootDiagnosticsInstanceView;
import com.microsoft.azure.management.compute.InstanceViewStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The instance view of a virtual machine.
 */
public class VirtualMachineInstanceViewInner {
    /**
     * Specifies the update domain of the virtual machine.
     */
    @JsonProperty(value = "platformUpdateDomain")
    private Integer platformUpdateDomain;

    /**
     * Specifies the fault domain of the virtual machine.
     */
    @JsonProperty(value = "platformFaultDomain")
    private Integer platformFaultDomain;

    /**
     * The computer name assigned to the virtual machine.
     */
    @JsonProperty(value = "computerName")
    private String computerName;

    /**
     * The Operating System running on the virtual machine.
     */
    @JsonProperty(value = "osName")
    private String osName;

    /**
     * The version of Operating System running on the virtual machine.
     */
    @JsonProperty(value = "osVersion")
    private String osVersion;

    /**
     * The Remote desktop certificate thumbprint.
     */
    @JsonProperty(value = "rdpThumbPrint")
    private String rdpThumbPrint;

    /**
     * The VM Agent running on the virtual machine.
     */
    @JsonProperty(value = "vmAgent")
    private VirtualMachineAgentInstanceView vmAgent;

    /**
     * The Maintenance Operation status on the virtual machine.
     */
    @JsonProperty(value = "maintenanceRedeployStatus")
    private MaintenanceRedeployStatus maintenanceRedeployStatus;

    /**
     * The virtual machine disk information.
     */
    @JsonProperty(value = "disks")
    private List<DiskInstanceView> disks;

    /**
     * The extensions information.
     */
    @JsonProperty(value = "extensions")
    private List<VirtualMachineExtensionInstanceView> extensions;

    /**
     * Boot Diagnostics is a debugging feature which allows you to view Console
     * Output and Screenshot to diagnose VM status. &lt;br&gt;&lt;br&gt; You
     * can easily view the output of your console log. &lt;br&gt;&lt;br&gt;
     * Azure also enables you to see a screenshot of the VM from the
     * hypervisor.
     */
    @JsonProperty(value = "bootDiagnostics")
    private BootDiagnosticsInstanceView bootDiagnostics;

    /**
     * The resource status information.
     */
    @JsonProperty(value = "statuses")
    private List<InstanceViewStatus> statuses;

    /**
     * Get specifies the update domain of the virtual machine.
     *
     * @return the platformUpdateDomain value
     */
    public Integer platformUpdateDomain() {
        return this.platformUpdateDomain;
    }

    /**
     * Set specifies the update domain of the virtual machine.
     *
     * @param platformUpdateDomain the platformUpdateDomain value to set
     * @return the VirtualMachineInstanceViewInner object itself.
     */
    public VirtualMachineInstanceViewInner withPlatformUpdateDomain(Integer platformUpdateDomain) {
        this.platformUpdateDomain = platformUpdateDomain;
        return this;
    }

    /**
     * Get specifies the fault domain of the virtual machine.
     *
     * @return the platformFaultDomain value
     */
    public Integer platformFaultDomain() {
        return this.platformFaultDomain;
    }

    /**
     * Set specifies the fault domain of the virtual machine.
     *
     * @param platformFaultDomain the platformFaultDomain value to set
     * @return the VirtualMachineInstanceViewInner object itself.
     */
    public VirtualMachineInstanceViewInner withPlatformFaultDomain(Integer platformFaultDomain) {
        this.platformFaultDomain = platformFaultDomain;
        return this;
    }

    /**
     * Get the computer name assigned to the virtual machine.
     *
     * @return the computerName value
     */
    public String computerName() {
        return this.computerName;
    }

    /**
     * Set the computer name assigned to the virtual machine.
     *
     * @param computerName the computerName value to set
     * @return the VirtualMachineInstanceViewInner object itself.
     */
    public VirtualMachineInstanceViewInner withComputerName(String computerName) {
        this.computerName = computerName;
        return this;
    }

    /**
     * Get the Operating System running on the virtual machine.
     *
     * @return the osName value
     */
    public String osName() {
        return this.osName;
    }

    /**
     * Set the Operating System running on the virtual machine.
     *
     * @param osName the osName value to set
     * @return the VirtualMachineInstanceViewInner object itself.
     */
    public VirtualMachineInstanceViewInner withOsName(String osName) {
        this.osName = osName;
        return this;
    }

    /**
     * Get the version of Operating System running on the virtual machine.
     *
     * @return the osVersion value
     */
    public String osVersion() {
        return this.osVersion;
    }

    /**
     * Set the version of Operating System running on the virtual machine.
     *
     * @param osVersion the osVersion value to set
     * @return the VirtualMachineInstanceViewInner object itself.
     */
    public VirtualMachineInstanceViewInner withOsVersion(String osVersion) {
        this.osVersion = osVersion;
        return this;
    }

    /**
     * Get the Remote desktop certificate thumbprint.
     *
     * @return the rdpThumbPrint value
     */
    public String rdpThumbPrint() {
        return this.rdpThumbPrint;
    }

    /**
     * Set the Remote desktop certificate thumbprint.
     *
     * @param rdpThumbPrint the rdpThumbPrint value to set
     * @return the VirtualMachineInstanceViewInner object itself.
     */
    public VirtualMachineInstanceViewInner withRdpThumbPrint(String rdpThumbPrint) {
        this.rdpThumbPrint = rdpThumbPrint;
        return this;
    }

    /**
     * Get the VM Agent running on the virtual machine.
     *
     * @return the vmAgent value
     */
    public VirtualMachineAgentInstanceView vmAgent() {
        return this.vmAgent;
    }

    /**
     * Set the VM Agent running on the virtual machine.
     *
     * @param vmAgent the vmAgent value to set
     * @return the VirtualMachineInstanceViewInner object itself.
     */
    public VirtualMachineInstanceViewInner withVmAgent(VirtualMachineAgentInstanceView vmAgent) {
        this.vmAgent = vmAgent;
        return this;
    }

    /**
     * Get the Maintenance Operation status on the virtual machine.
     *
     * @return the maintenanceRedeployStatus value
     */
    public MaintenanceRedeployStatus maintenanceRedeployStatus() {
        return this.maintenanceRedeployStatus;
    }

    /**
     * Set the Maintenance Operation status on the virtual machine.
     *
     * @param maintenanceRedeployStatus the maintenanceRedeployStatus value to set
     * @return the VirtualMachineInstanceViewInner object itself.
     */
    public VirtualMachineInstanceViewInner withMaintenanceRedeployStatus(MaintenanceRedeployStatus maintenanceRedeployStatus) {
        this.maintenanceRedeployStatus = maintenanceRedeployStatus;
        return this;
    }

    /**
     * Get the virtual machine disk information.
     *
     * @return the disks value
     */
    public List<DiskInstanceView> disks() {
        return this.disks;
    }

    /**
     * Set the virtual machine disk information.
     *
     * @param disks the disks value to set
     * @return the VirtualMachineInstanceViewInner object itself.
     */
    public VirtualMachineInstanceViewInner withDisks(List<DiskInstanceView> disks) {
        this.disks = disks;
        return this;
    }

    /**
     * Get the extensions information.
     *
     * @return the extensions value
     */
    public List<VirtualMachineExtensionInstanceView> extensions() {
        return this.extensions;
    }

    /**
     * Set the extensions information.
     *
     * @param extensions the extensions value to set
     * @return the VirtualMachineInstanceViewInner object itself.
     */
    public VirtualMachineInstanceViewInner withExtensions(List<VirtualMachineExtensionInstanceView> extensions) {
        this.extensions = extensions;
        return this;
    }

    /**
     * Get boot Diagnostics is a debugging feature which allows you to view Console Output and Screenshot to diagnose VM status. &lt;br&gt;&lt;br&gt; You can easily view the output of your console log. &lt;br&gt;&lt;br&gt; Azure also enables you to see a screenshot of the VM from the hypervisor.
     *
     * @return the bootDiagnostics value
     */
    public BootDiagnosticsInstanceView bootDiagnostics() {
        return this.bootDiagnostics;
    }

    /**
     * Set boot Diagnostics is a debugging feature which allows you to view Console Output and Screenshot to diagnose VM status. &lt;br&gt;&lt;br&gt; You can easily view the output of your console log. &lt;br&gt;&lt;br&gt; Azure also enables you to see a screenshot of the VM from the hypervisor.
     *
     * @param bootDiagnostics the bootDiagnostics value to set
     * @return the VirtualMachineInstanceViewInner object itself.
     */
    public VirtualMachineInstanceViewInner withBootDiagnostics(BootDiagnosticsInstanceView bootDiagnostics) {
        this.bootDiagnostics = bootDiagnostics;
        return this;
    }

    /**
     * Get the resource status information.
     *
     * @return the statuses value
     */
    public List<InstanceViewStatus> statuses() {
        return this.statuses;
    }

    /**
     * Set the resource status information.
     *
     * @param statuses the statuses value to set
     * @return the VirtualMachineInstanceViewInner object itself.
     */
    public VirtualMachineInstanceViewInner withStatuses(List<InstanceViewStatus> statuses) {
        this.statuses = statuses;
        return this;
    }

}
