// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The GetVpnSitesConfigurationRequest model.
 */
@Fluent
public final class GetVpnSitesConfigurationRequest {
    /*
     * List of resource-ids of the vpn-sites for which config is to be
     * downloaded.
     */
    @JsonProperty(value = "vpnSites")
    private List<String> vpnSites;

    /*
     * The sas-url to download the configurations for vpn-sites.
     */
    @JsonProperty(value = "outputBlobSasUrl", required = true)
    private String outputBlobSasUrl;

    /**
     * Get the vpnSites property: List of resource-ids of the vpn-sites for
     * which config is to be downloaded.
     * 
     * @return the vpnSites value.
     */
    public List<String> getVpnSites() {
        return this.vpnSites;
    }

    /**
     * Set the vpnSites property: List of resource-ids of the vpn-sites for
     * which config is to be downloaded.
     * 
     * @param vpnSites the vpnSites value to set.
     * @return the GetVpnSitesConfigurationRequest object itself.
     */
    public GetVpnSitesConfigurationRequest setVpnSites(List<String> vpnSites) {
        this.vpnSites = vpnSites;
        return this;
    }

    /**
     * Get the outputBlobSasUrl property: The sas-url to download the
     * configurations for vpn-sites.
     * 
     * @return the outputBlobSasUrl value.
     */
    public String getOutputBlobSasUrl() {
        return this.outputBlobSasUrl;
    }

    /**
     * Set the outputBlobSasUrl property: The sas-url to download the
     * configurations for vpn-sites.
     * 
     * @param outputBlobSasUrl the outputBlobSasUrl value to set.
     * @return the GetVpnSitesConfigurationRequest object itself.
     */
    public GetVpnSitesConfigurationRequest setOutputBlobSasUrl(String outputBlobSasUrl) {
        this.outputBlobSasUrl = outputBlobSasUrl;
        return this;
    }
}
