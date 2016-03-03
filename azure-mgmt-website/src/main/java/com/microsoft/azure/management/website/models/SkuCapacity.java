/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator 0.15.0.0
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package com.microsoft.azure.management.website.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Description of the App Service Plan scale options.
 */
public class SkuCapacity {
    /**
     * Minimum number of Workers for this App Service Plan SKU.
     */
    private Integer minimum;

    /**
     * Maximum number of Workers for this App Service Plan SKU.
     */
    private Integer maximum;

    /**
     * Default number of Workers for this App Service Plan SKU.
     */
    @JsonProperty(value = "default")
    private Integer defaultProperty;

    /**
     * Available scale configurations for an App Service Plan.
     */
    private String scaleType;

    /**
     * Get the minimum value.
     *
     * @return the minimum value
     */
    public Integer getMinimum() {
        return this.minimum;
    }

    /**
     * Set the minimum value.
     *
     * @param minimum the minimum value to set
     */
    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

    /**
     * Get the maximum value.
     *
     * @return the maximum value
     */
    public Integer getMaximum() {
        return this.maximum;
    }

    /**
     * Set the maximum value.
     *
     * @param maximum the maximum value to set
     */
    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    /**
     * Get the defaultProperty value.
     *
     * @return the defaultProperty value
     */
    public Integer getDefaultProperty() {
        return this.defaultProperty;
    }

    /**
     * Set the defaultProperty value.
     *
     * @param defaultProperty the defaultProperty value to set
     */
    public void setDefaultProperty(Integer defaultProperty) {
        this.defaultProperty = defaultProperty;
    }

    /**
     * Get the scaleType value.
     *
     * @return the scaleType value
     */
    public String getScaleType() {
        return this.scaleType;
    }

    /**
     * Set the scaleType value.
     *
     * @param scaleType the scaleType value to set
     */
    public void setScaleType(String scaleType) {
        this.scaleType = scaleType;
    }

}
