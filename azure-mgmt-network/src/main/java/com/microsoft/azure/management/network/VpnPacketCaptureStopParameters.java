/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.network;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stop packet capture parameters.
 */
public class VpnPacketCaptureStopParameters {
    /**
     * SAS url for packet capture on virtual network gateway.
     */
    @JsonProperty(value = "sasUrl")
    private String sasUrl;

    /**
     * Get sAS url for packet capture on virtual network gateway.
     *
     * @return the sasUrl value
     */
    public String sasUrl() {
        return this.sasUrl;
    }

    /**
     * Set sAS url for packet capture on virtual network gateway.
     *
     * @param sasUrl the sasUrl value to set
     * @return the VpnPacketCaptureStopParameters object itself.
     */
    public VpnPacketCaptureStopParameters withSasUrl(String sasUrl) {
        this.sasUrl = sasUrl;
        return this;
    }

}
