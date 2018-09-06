/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.v2.management.compute;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Capture Virtual Machine parameters.
 */
public final class VirtualMachineCaptureParameters {
    /**
     * The captured virtual hard disk's name prefix.
     */
    @JsonProperty(value = "vhdPrefix", required = true)
    private String vhdPrefix;

    /**
     * The destination container name.
     */
    @JsonProperty(value = "destinationContainerName", required = true)
    private String destinationContainerName;

    /**
     * Specifies whether to overwrite the destination virtual hard disk, in
     * case of conflict.
     */
    @JsonProperty(value = "overwriteVhds", required = true)
    private boolean overwriteVhds;

    /**
     * Get the vhdPrefix value.
     *
     * @return the vhdPrefix value.
     */
    public String vhdPrefix() {
        return this.vhdPrefix;
    }

    /**
     * Set the vhdPrefix value.
     *
     * @param vhdPrefix the vhdPrefix value to set.
     * @return the VirtualMachineCaptureParameters object itself.
     */
    public VirtualMachineCaptureParameters withVhdPrefix(String vhdPrefix) {
        this.vhdPrefix = vhdPrefix;
        return this;
    }

    /**
     * Get the destinationContainerName value.
     *
     * @return the destinationContainerName value.
     */
    public String destinationContainerName() {
        return this.destinationContainerName;
    }

    /**
     * Set the destinationContainerName value.
     *
     * @param destinationContainerName the destinationContainerName value to
     * set.
     * @return the VirtualMachineCaptureParameters object itself.
     */
    public VirtualMachineCaptureParameters withDestinationContainerName(String destinationContainerName) {
        this.destinationContainerName = destinationContainerName;
        return this;
    }

    /**
     * Get the overwriteVhds value.
     *
     * @return the overwriteVhds value.
     */
    public boolean overwriteVhds() {
        return this.overwriteVhds;
    }

    /**
     * Set the overwriteVhds value.
     *
     * @param overwriteVhds the overwriteVhds value to set.
     * @return the VirtualMachineCaptureParameters object itself.
     */
    public VirtualMachineCaptureParameters withOverwriteVhds(boolean overwriteVhds) {
        this.overwriteVhds = overwriteVhds;
        return this;
    }
}
