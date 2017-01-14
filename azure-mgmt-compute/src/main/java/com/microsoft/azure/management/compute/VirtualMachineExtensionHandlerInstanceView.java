/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.compute;


/**
 * The instance view of a virtual machine extension handler.
 */
public class VirtualMachineExtensionHandlerInstanceView {
    /**
     * Full type of the extension handler which includes both publisher and
     * type.
     */
    private String type;

    /**
     * The type version of the extension handler.
     */
    private String typeHandlerVersion;

    /**
     * The extension handler status.
     */
    private InstanceViewStatus status;

    /**
     * Get the type value.
     *
     * @return the type value
     */
    public String type() {
        return this.type;
    }

    /**
     * Set the type value.
     *
     * @param type the type value to set
     * @return the VirtualMachineExtensionHandlerInstanceView object itself.
     */
    public VirtualMachineExtensionHandlerInstanceView withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get the typeHandlerVersion value.
     *
     * @return the typeHandlerVersion value
     */
    public String typeHandlerVersion() {
        return this.typeHandlerVersion;
    }

    /**
     * Set the typeHandlerVersion value.
     *
     * @param typeHandlerVersion the typeHandlerVersion value to set
     * @return the VirtualMachineExtensionHandlerInstanceView object itself.
     */
    public VirtualMachineExtensionHandlerInstanceView withTypeHandlerVersion(String typeHandlerVersion) {
        this.typeHandlerVersion = typeHandlerVersion;
        return this;
    }

    /**
     * Get the status value.
     *
     * @return the status value
     */
    public InstanceViewStatus status() {
        return this.status;
    }

    /**
     * Set the status value.
     *
     * @param status the status value to set
     * @return the VirtualMachineExtensionHandlerInstanceView object itself.
     */
    public VirtualMachineExtensionHandlerInstanceView withStatus(InstanceViewStatus status) {
        this.status = status;
        return this;
    }

}
