/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cdn.implementation;

import java.util.List;
import com.microsoft.azure.management.cdn.ContinentsResponseContinentsItem;
import com.microsoft.azure.management.cdn.ContinentsResponseCountryOrRegionsItem;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Continents Response.
 */
public class ContinentsResponseInner {
    /**
     * The continents property.
     */
    @JsonProperty(value = "continents")
    private List<ContinentsResponseContinentsItem> continents;

    /**
     * The countryOrRegions property.
     */
    @JsonProperty(value = "countryOrRegions")
    private List<ContinentsResponseCountryOrRegionsItem> countryOrRegions;

    /**
     * Get the continents value.
     *
     * @return the continents value
     */
    public List<ContinentsResponseContinentsItem> continents() {
        return this.continents;
    }

    /**
     * Set the continents value.
     *
     * @param continents the continents value to set
     * @return the ContinentsResponseInner object itself.
     */
    public ContinentsResponseInner withContinents(List<ContinentsResponseContinentsItem> continents) {
        this.continents = continents;
        return this;
    }

    /**
     * Get the countryOrRegions value.
     *
     * @return the countryOrRegions value
     */
    public List<ContinentsResponseCountryOrRegionsItem> countryOrRegions() {
        return this.countryOrRegions;
    }

    /**
     * Set the countryOrRegions value.
     *
     * @param countryOrRegions the countryOrRegions value to set
     * @return the ContinentsResponseInner object itself.
     */
    public ContinentsResponseInner withCountryOrRegions(List<ContinentsResponseCountryOrRegionsItem> countryOrRegions) {
        this.countryOrRegions = countryOrRegions;
        return this;
    }

}
