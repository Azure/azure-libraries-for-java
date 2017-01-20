/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.compute.implementation;


/**
 * Describes the properties of a VM size.
 */
public class VirtualMachineSizeInner {
    /**
     * The name of the virtual machine size.
     */
    private String name;

    /**
     * The number of cores supported by the virtual machine size.
     */
    private Integer numberOfCores;

    /**
     * The OS disk size, in MB, allowed by the virtual machine size.
     */
    private Integer osDiskSizeInMB;

    /**
     * The resource disk size, in MB, allowed by the virtual machine size.
     */
    private Integer resourceDiskSizeInMB;

    /**
     * The amount of memory, in MB, supported by the virtual machine size.
     */
    private Integer memoryInMB;

    /**
     * The maximum number of data disks that can be attached to the virtual
     * machine size.
     */
    private Integer maxDataDiskCount;

    /**
     * Get the name value.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the name value.
     *
     * @param name the name value to set
     * @return the VirtualMachineSizeInner object itself.
     */
    public VirtualMachineSizeInner withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the numberOfCores value.
     *
     * @return the numberOfCores value
     */
    public Integer numberOfCores() {
        return this.numberOfCores;
    }

    /**
     * Set the numberOfCores value.
     *
     * @param numberOfCores the numberOfCores value to set
     * @return the VirtualMachineSizeInner object itself.
     */
    public VirtualMachineSizeInner withNumberOfCores(Integer numberOfCores) {
        this.numberOfCores = numberOfCores;
        return this;
    }

    /**
     * Get the osDiskSizeInMB value.
     *
     * @return the osDiskSizeInMB value
     */
    public Integer osDiskSizeInMB() {
        return this.osDiskSizeInMB;
    }

    /**
     * Set the osDiskSizeInMB value.
     *
     * @param osDiskSizeInMB the osDiskSizeInMB value to set
     * @return the VirtualMachineSizeInner object itself.
     */
    public VirtualMachineSizeInner withOsDiskSizeInMB(Integer osDiskSizeInMB) {
        this.osDiskSizeInMB = osDiskSizeInMB;
        return this;
    }

    /**
     * Get the resourceDiskSizeInMB value.
     *
     * @return the resourceDiskSizeInMB value
     */
    public Integer resourceDiskSizeInMB() {
        return this.resourceDiskSizeInMB;
    }

    /**
     * Set the resourceDiskSizeInMB value.
     *
     * @param resourceDiskSizeInMB the resourceDiskSizeInMB value to set
     * @return the VirtualMachineSizeInner object itself.
     */
    public VirtualMachineSizeInner withResourceDiskSizeInMB(Integer resourceDiskSizeInMB) {
        this.resourceDiskSizeInMB = resourceDiskSizeInMB;
        return this;
    }

    /**
     * Get the memoryInMB value.
     *
     * @return the memoryInMB value
     */
    public Integer memoryInMB() {
        return this.memoryInMB;
    }

    /**
     * Set the memoryInMB value.
     *
     * @param memoryInMB the memoryInMB value to set
     * @return the VirtualMachineSizeInner object itself.
     */
    public VirtualMachineSizeInner withMemoryInMB(Integer memoryInMB) {
        this.memoryInMB = memoryInMB;
        return this;
    }

    /**
     * Get the maxDataDiskCount value.
     *
     * @return the maxDataDiskCount value
     */
    public Integer maxDataDiskCount() {
        return this.maxDataDiskCount;
    }

    /**
     * Set the maxDataDiskCount value.
     *
     * @param maxDataDiskCount the maxDataDiskCount value to set
     * @return the VirtualMachineSizeInner object itself.
     */
    public VirtualMachineSizeInner withMaxDataDiskCount(Integer maxDataDiskCount) {
        this.maxDataDiskCount = maxDataDiskCount;
        return this;
    }

}
