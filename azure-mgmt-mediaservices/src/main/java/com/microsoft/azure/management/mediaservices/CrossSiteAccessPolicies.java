/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.mediaservices;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The client access policy.
 */
public class CrossSiteAccessPolicies {
    /**
     * The content of clientaccesspolicy.xml used by Silverlight.
     */
    @JsonProperty(value = "clientAccessPolicy")
    private String clientAccessPolicy;

    /**
     * The content of crossdomain.xml used by Silverlight.
     */
    @JsonProperty(value = "crossDomainPolicy")
    private String crossDomainPolicy;

    /**
     * Get the clientAccessPolicy value.
     *
     * @return the clientAccessPolicy value
     */
    public String clientAccessPolicy() {
        return this.clientAccessPolicy;
    }

    /**
     * Set the clientAccessPolicy value.
     *
     * @param clientAccessPolicy the clientAccessPolicy value to set
     * @return the CrossSiteAccessPolicies object itself.
     */
    public CrossSiteAccessPolicies withClientAccessPolicy(String clientAccessPolicy) {
        this.clientAccessPolicy = clientAccessPolicy;
        return this;
    }

    /**
     * Get the crossDomainPolicy value.
     *
     * @return the crossDomainPolicy value
     */
    public String crossDomainPolicy() {
        return this.crossDomainPolicy;
    }

    /**
     * Set the crossDomainPolicy value.
     *
     * @param crossDomainPolicy the crossDomainPolicy value to set
     * @return the CrossSiteAccessPolicies object itself.
     */
    public CrossSiteAccessPolicies withCrossDomainPolicy(String crossDomainPolicy) {
        this.crossDomainPolicy = crossDomainPolicy;
        return this;
    }

}
