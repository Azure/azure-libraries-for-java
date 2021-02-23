/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cdn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines the parameters for the origin group override action.
 */
public class OriginGroupOverrideActionParameters {
    /**
     * The odatatype property.
     */
    @JsonProperty(value = "@odata\\.type", required = true)
    private String odatatype;

    /**
     * defines the OriginGroup that would override the DefaultOriginGroup.
     */
    @JsonProperty(value = "originGroup", required = true)
    private ResourceReference originGroup;

    /**
     * Creates an instance of OriginGroupOverrideActionParameters class.
     * @param originGroup defines the OriginGroup that would override the DefaultOriginGroup.
     */
    public OriginGroupOverrideActionParameters() {
        odatatype = "#Microsoft.Azure.Cdn.Models.DeliveryRuleOriginGroupOverrideActionParameters";
    }

    /**
     * Get the odatatype value.
     *
     * @return the odatatype value
     */
    public String odatatype() {
        return this.odatatype;
    }

    /**
     * Set the odatatype value.
     *
     * @param odatatype the odatatype value to set
     * @return the OriginGroupOverrideActionParameters object itself.
     */
    public OriginGroupOverrideActionParameters withOdatatype(String odatatype) {
        this.odatatype = odatatype;
        return this;
    }

    /**
     * Get defines the OriginGroup that would override the DefaultOriginGroup.
     *
     * @return the originGroup value
     */
    public ResourceReference originGroup() {
        return this.originGroup;
    }

    /**
     * Set defines the OriginGroup that would override the DefaultOriginGroup.
     *
     * @param originGroup the originGroup value to set
     * @return the OriginGroupOverrideActionParameters object itself.
     */
    public OriginGroupOverrideActionParameters withOriginGroup(ResourceReference originGroup) {
        this.originGroup = originGroup;
        return this;
    }

}
