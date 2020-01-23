// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.model;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The VpnProfileResponse model.
 */
@Fluent
public final class VpnProfileResponseInner {
    /*
     * URL to the VPN profile.
     */
    @JsonProperty(value = "profileUrl")
    private String profileUrl;

    /**
     * Get the profileUrl property: URL to the VPN profile.
     * 
     * @return the profileUrl value.
     */
    public String getProfileUrl() {
        return this.profileUrl;
    }

    /**
     * Set the profileUrl property: URL to the VPN profile.
     * 
     * @param profileUrl the profileUrl value to set.
     * @return the VpnProfileResponseInner object itself.
     */
    public VpnProfileResponseInner setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
        return this;
    }
}