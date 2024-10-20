/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.compute;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Specifies settings related to VM Guest Patching on Linux.
 */
public class LinuxPatchSettings {
    /**
     * Specifies the mode of VM Guest Patching to IaaS virtual machine.&lt;br
     * /&gt;&lt;br /&gt; Possible values are:&lt;br /&gt;&lt;br /&gt;
     * **ImageDefault** - The virtual machine's default patching configuration
     * is used. &lt;br /&gt;&lt;br /&gt; **AutomaticByPlatform** - The virtual
     * machine will be automatically updated by the platform. The property
     * provisionVMAgent must be true. Possible values include: 'ImageDefault',
     * 'AutomaticByPlatform'.
     */
    @JsonProperty(value = "patchMode")
    private LinuxVMGuestPatchMode patchMode;

    /**
     * Get specifies the mode of VM Guest Patching to IaaS virtual machine.&lt;br /&gt;&lt;br /&gt; Possible values are:&lt;br /&gt;&lt;br /&gt; **ImageDefault** - The virtual machine's default patching configuration is used. &lt;br /&gt;&lt;br /&gt; **AutomaticByPlatform** - The virtual machine will be automatically updated by the platform. The property provisionVMAgent must be true. Possible values include: 'ImageDefault', 'AutomaticByPlatform'.
     *
     * @return the patchMode value
     */
    public LinuxVMGuestPatchMode patchMode() {
        return this.patchMode;
    }

    /**
     * Set specifies the mode of VM Guest Patching to IaaS virtual machine.&lt;br /&gt;&lt;br /&gt; Possible values are:&lt;br /&gt;&lt;br /&gt; **ImageDefault** - The virtual machine's default patching configuration is used. &lt;br /&gt;&lt;br /&gt; **AutomaticByPlatform** - The virtual machine will be automatically updated by the platform. The property provisionVMAgent must be true. Possible values include: 'ImageDefault', 'AutomaticByPlatform'.
     *
     * @param patchMode the patchMode value to set
     * @return the LinuxPatchSettings object itself.
     */
    public LinuxPatchSettings withPatchMode(LinuxVMGuestPatchMode patchMode) {
        this.patchMode = patchMode;
        return this;
    }

}
