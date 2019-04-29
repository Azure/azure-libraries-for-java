/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.compute;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Describes a Virtual Machine Scale Set VM Reimage Parameters.
 */
public class VirtualMachineScaleSetReimageParameters extends VirtualMachineScaleSetVMReimageParameters {
    /**
     * The virtual machine scale set instance ids. Omitting the virtual machine
     * scale set instance ids will result in the operation being performed on
     * all virtual machines in the virtual machine scale set.
     */
    @JsonProperty(value = "instanceIds")
    private List<String> instanceIds;

    /**
     * Get the virtual machine scale set instance ids. Omitting the virtual machine scale set instance ids will result in the operation being performed on all virtual machines in the virtual machine scale set.
     *
     * @return the instanceIds value
     */
    public List<String> instanceIds() {
        return this.instanceIds;
    }

    /**
     * Set the virtual machine scale set instance ids. Omitting the virtual machine scale set instance ids will result in the operation being performed on all virtual machines in the virtual machine scale set.
     *
     * @param instanceIds the instanceIds value to set
     * @return the VirtualMachineScaleSetReimageParameters object itself.
     */
    public VirtualMachineScaleSetReimageParameters withInstanceIds(List<String> instanceIds) {
        this.instanceIds = instanceIds;
        return this;
    }

}
