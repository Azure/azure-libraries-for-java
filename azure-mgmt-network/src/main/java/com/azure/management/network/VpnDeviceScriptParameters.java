// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The VpnDeviceScriptParameters model.
 */
@Fluent
public final class VpnDeviceScriptParameters {
    /*
     * The vendor for the vpn device.
     */
    @JsonProperty(value = "vendor")
    private String vendor;

    /*
     * The device family for the vpn device.
     */
    @JsonProperty(value = "deviceFamily")
    private String deviceFamily;

    /*
     * The firmware version for the vpn device.
     */
    @JsonProperty(value = "firmwareVersion")
    private String firmwareVersion;

    /**
     * Get the vendor property: The vendor for the vpn device.
     * 
     * @return the vendor value.
     */
    public String vendor() {
        return this.vendor;
    }

    /**
     * Set the vendor property: The vendor for the vpn device.
     * 
     * @param vendor the vendor value to set.
     * @return the VpnDeviceScriptParameters object itself.
     */
    public VpnDeviceScriptParameters withVendor(String vendor) {
        this.vendor = vendor;
        return this;
    }

    /**
     * Get the deviceFamily property: The device family for the vpn device.
     * 
     * @return the deviceFamily value.
     */
    public String deviceFamily() {
        return this.deviceFamily;
    }

    /**
     * Set the deviceFamily property: The device family for the vpn device.
     * 
     * @param deviceFamily the deviceFamily value to set.
     * @return the VpnDeviceScriptParameters object itself.
     */
    public VpnDeviceScriptParameters withDeviceFamily(String deviceFamily) {
        this.deviceFamily = deviceFamily;
        return this;
    }

    /**
     * Get the firmwareVersion property: The firmware version for the vpn
     * device.
     * 
     * @return the firmwareVersion value.
     */
    public String firmwareVersion() {
        return this.firmwareVersion;
    }

    /**
     * Set the firmwareVersion property: The firmware version for the vpn
     * device.
     * 
     * @param firmwareVersion the firmwareVersion value to set.
     * @return the VpnDeviceScriptParameters object itself.
     */
    public VpnDeviceScriptParameters withFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
        return this;
    }
}
