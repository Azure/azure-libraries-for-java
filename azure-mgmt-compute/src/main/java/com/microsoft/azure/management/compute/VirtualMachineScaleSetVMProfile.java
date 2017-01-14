/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.compute;


/**
 * Describes a virtual machine scale set virtual machine profile.
 */
public class VirtualMachineScaleSetVMProfile {
    /**
     * The virtual machine scale set OS profile.
     */
    private VirtualMachineScaleSetOSProfile osProfile;

    /**
     * The virtual machine scale set storage profile.
     */
    private VirtualMachineScaleSetStorageProfile storageProfile;

    /**
     * The virtual machine scale set network profile.
     */
    private VirtualMachineScaleSetNetworkProfile networkProfile;

    /**
     * The virtual machine scale set extension profile.
     */
    private VirtualMachineScaleSetExtensionProfile extensionProfile;

    /**
     * Get the osProfile value.
     *
     * @return the osProfile value
     */
    public VirtualMachineScaleSetOSProfile osProfile() {
        return this.osProfile;
    }

    /**
     * Set the osProfile value.
     *
     * @param osProfile the osProfile value to set
     * @return the VirtualMachineScaleSetVMProfile object itself.
     */
    public VirtualMachineScaleSetVMProfile withOsProfile(VirtualMachineScaleSetOSProfile osProfile) {
        this.osProfile = osProfile;
        return this;
    }

    /**
     * Get the storageProfile value.
     *
     * @return the storageProfile value
     */
    public VirtualMachineScaleSetStorageProfile storageProfile() {
        return this.storageProfile;
    }

    /**
     * Set the storageProfile value.
     *
     * @param storageProfile the storageProfile value to set
     * @return the VirtualMachineScaleSetVMProfile object itself.
     */
    public VirtualMachineScaleSetVMProfile withStorageProfile(VirtualMachineScaleSetStorageProfile storageProfile) {
        this.storageProfile = storageProfile;
        return this;
    }

    /**
     * Get the networkProfile value.
     *
     * @return the networkProfile value
     */
    public VirtualMachineScaleSetNetworkProfile networkProfile() {
        return this.networkProfile;
    }

    /**
     * Set the networkProfile value.
     *
     * @param networkProfile the networkProfile value to set
     * @return the VirtualMachineScaleSetVMProfile object itself.
     */
    public VirtualMachineScaleSetVMProfile withNetworkProfile(VirtualMachineScaleSetNetworkProfile networkProfile) {
        this.networkProfile = networkProfile;
        return this;
    }

    /**
     * Get the extensionProfile value.
     *
     * @return the extensionProfile value
     */
    public VirtualMachineScaleSetExtensionProfile extensionProfile() {
        return this.extensionProfile;
    }

    /**
     * Set the extensionProfile value.
     *
     * @param extensionProfile the extensionProfile value to set
     * @return the VirtualMachineScaleSetVMProfile object itself.
     */
    public VirtualMachineScaleSetVMProfile withExtensionProfile(VirtualMachineScaleSetExtensionProfile extensionProfile) {
        this.extensionProfile = extensionProfile;
        return this;
    }

}
