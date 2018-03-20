/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.recoveryservicessiterecovery;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Single Host fabric provider specific VM settings.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "instanceType")
@JsonTypeName("HyperVVirtualMachine")
public class HyperVVirtualMachineDetails extends ConfigurationSettings {
    /**
     * The source id of the object.
     */
    @JsonProperty(value = "sourceItemId")
    private String sourceItemId;

    /**
     * The id of the object in fabric.
     */
    @JsonProperty(value = "generation")
    private String generation;

    /**
     * The Last replication time.
     */
    @JsonProperty(value = "osDetails")
    private OSDetails osDetails;

    /**
     * The Last successful failover time.
     */
    @JsonProperty(value = "diskDetails")
    private List<DiskDetails> diskDetails;

    /**
     * A value indicating whether the VM has a physical disk attached. String
     * value of {SrsDataContract.PresenceStatus} enum. Possible values include:
     * 'Unknown', 'Present', 'NotPresent'.
     */
    @JsonProperty(value = "hasPhysicalDisk")
    private PresenceStatus hasPhysicalDisk;

    /**
     * A value indicating whether the VM has a fibre channel adapter attached.
     * String value of {SrsDataContract.PresenceStatus} enum. Possible values
     * include: 'Unknown', 'Present', 'NotPresent'.
     */
    @JsonProperty(value = "hasFibreChannelAdapter")
    private PresenceStatus hasFibreChannelAdapter;

    /**
     * A value indicating whether the VM has a shared VHD attached. String
     * value of {SrsDataContract.PresenceStatus} enum. Possible values include:
     * 'Unknown', 'Present', 'NotPresent'.
     */
    @JsonProperty(value = "hasSharedVhd")
    private PresenceStatus hasSharedVhd;

    /**
     * Get the sourceItemId value.
     *
     * @return the sourceItemId value
     */
    public String sourceItemId() {
        return this.sourceItemId;
    }

    /**
     * Set the sourceItemId value.
     *
     * @param sourceItemId the sourceItemId value to set
     * @return the HyperVVirtualMachineDetails object itself.
     */
    public HyperVVirtualMachineDetails withSourceItemId(String sourceItemId) {
        this.sourceItemId = sourceItemId;
        return this;
    }

    /**
     * Get the generation value.
     *
     * @return the generation value
     */
    public String generation() {
        return this.generation;
    }

    /**
     * Set the generation value.
     *
     * @param generation the generation value to set
     * @return the HyperVVirtualMachineDetails object itself.
     */
    public HyperVVirtualMachineDetails withGeneration(String generation) {
        this.generation = generation;
        return this;
    }

    /**
     * Get the osDetails value.
     *
     * @return the osDetails value
     */
    public OSDetails osDetails() {
        return this.osDetails;
    }

    /**
     * Set the osDetails value.
     *
     * @param osDetails the osDetails value to set
     * @return the HyperVVirtualMachineDetails object itself.
     */
    public HyperVVirtualMachineDetails withOsDetails(OSDetails osDetails) {
        this.osDetails = osDetails;
        return this;
    }

    /**
     * Get the diskDetails value.
     *
     * @return the diskDetails value
     */
    public List<DiskDetails> diskDetails() {
        return this.diskDetails;
    }

    /**
     * Set the diskDetails value.
     *
     * @param diskDetails the diskDetails value to set
     * @return the HyperVVirtualMachineDetails object itself.
     */
    public HyperVVirtualMachineDetails withDiskDetails(List<DiskDetails> diskDetails) {
        this.diskDetails = diskDetails;
        return this;
    }

    /**
     * Get the hasPhysicalDisk value.
     *
     * @return the hasPhysicalDisk value
     */
    public PresenceStatus hasPhysicalDisk() {
        return this.hasPhysicalDisk;
    }

    /**
     * Set the hasPhysicalDisk value.
     *
     * @param hasPhysicalDisk the hasPhysicalDisk value to set
     * @return the HyperVVirtualMachineDetails object itself.
     */
    public HyperVVirtualMachineDetails withHasPhysicalDisk(PresenceStatus hasPhysicalDisk) {
        this.hasPhysicalDisk = hasPhysicalDisk;
        return this;
    }

    /**
     * Get the hasFibreChannelAdapter value.
     *
     * @return the hasFibreChannelAdapter value
     */
    public PresenceStatus hasFibreChannelAdapter() {
        return this.hasFibreChannelAdapter;
    }

    /**
     * Set the hasFibreChannelAdapter value.
     *
     * @param hasFibreChannelAdapter the hasFibreChannelAdapter value to set
     * @return the HyperVVirtualMachineDetails object itself.
     */
    public HyperVVirtualMachineDetails withHasFibreChannelAdapter(PresenceStatus hasFibreChannelAdapter) {
        this.hasFibreChannelAdapter = hasFibreChannelAdapter;
        return this;
    }

    /**
     * Get the hasSharedVhd value.
     *
     * @return the hasSharedVhd value
     */
    public PresenceStatus hasSharedVhd() {
        return this.hasSharedVhd;
    }

    /**
     * Set the hasSharedVhd value.
     *
     * @param hasSharedVhd the hasSharedVhd value to set
     * @return the HyperVVirtualMachineDetails object itself.
     */
    public HyperVVirtualMachineDetails withHasSharedVhd(PresenceStatus hasSharedVhd) {
        this.hasSharedVhd = hasSharedVhd;
        return this;
    }

}
