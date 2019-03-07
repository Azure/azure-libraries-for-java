/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.compute;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.compute.implementation.VirtualMachineInstanceViewInner;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

import java.util.List;

/**
 * An immutable client-side representation of an Azure VM Instance View object.
 */
@Fluent
public interface VirtualMachineInstanceView extends HasInner<VirtualMachineInstanceViewInner> {
    /**
     * Get specifies the update domain of the virtual machine.
     *
     * @return the platformUpdateDomain value
     */
    public Integer platformUpdateDomain();


    /**
     * Get the computer name assigned to the virtual machine.
     *
     * @return the computerName value
     */
    public String computerName();

    /**
     * Get the Operating System running on the virtual machine.
     *
     * @return the osName value
     */
    public String osName();

    /**
     * Get the version of Operating System running on the virtual machine.
     *
     * @return the osVersion value
     */
    public String osVersion();


    /**
     * Get the Remote desktop certificate thumbprint.
     *
     * @return the rdpThumbPrint value
     */
    public String rdpThumbPrint();


    /**
     * Get the VM Agent running on the virtual machine.
     *
     * @return the vmAgent value
     */
    public VirtualMachineAgentInstanceView vmAgent();


    /**
     * Get the Maintenance Operation status on the virtual machine.
     *
     * @return the maintenanceRedeployStatus value
     */
    public MaintenanceRedeployStatus maintenanceRedeployStatus();


    /**
     * Get the virtual machine disk information.
     *
     * @return the disks value
     */
    public List<DiskInstanceView> disks();


    /**
     * Get the extensions information.
     *
     * @return the extensions value
     */
    public List<VirtualMachineExtensionInstanceView> extensions();


    /**
     * Get boot Diagnostics is a debugging feature which allows you to view Console Output and Screenshot to diagnose VM status. &lt;br&gt;&lt;br&gt; You can easily view the output of your console log. &lt;br&gt;&lt;br&gt; Azure also enables you to see a screenshot of the VM from the hypervisor.
     *
     * @return the bootDiagnostics value
     */
    public BootDiagnosticsInstanceView bootDiagnostics();


    /**
     * Get the resource status information.
     *
     * @return the statuses value
     */
    public List<InstanceViewStatus> statuses();
}
