/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.iothub;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * IoT Hub capacity information.
 */
public class IotHubCapacity {
    /**
     * The minimum number of units.
     */
    @JsonProperty(value = "minimum", access = JsonProperty.Access.WRITE_ONLY)
    private Long minimum;

    /**
     * The maximum number of units.
     */
    @JsonProperty(value = "maximum", access = JsonProperty.Access.WRITE_ONLY)
    private Long maximum;

    /**
     * The default number of units.
     */
    @JsonProperty(value = "default", access = JsonProperty.Access.WRITE_ONLY)
    private Long defaultProperty;

    /**
     * The type of the scaling enabled. Possible values include: 'Automatic',
     * 'Manual', 'None'.
     */
    @JsonProperty(value = "scaleType", access = JsonProperty.Access.WRITE_ONLY)
    private IotHubScaleType scaleType;

    /**
     * Get the minimum value.
     *
     * @return the minimum value
     */
    public Long minimum() {
        return this.minimum;
    }

    /**
     * Get the maximum value.
     *
     * @return the maximum value
     */
    public Long maximum() {
        return this.maximum;
    }

    /**
     * Get the defaultProperty value.
     *
     * @return the defaultProperty value
     */
    public Long defaultProperty() {
        return this.defaultProperty;
    }

    /**
     * Get the scaleType value.
     *
     * @return the scaleType value
     */
    public IotHubScaleType scaleType() {
        return this.scaleType;
    }

}
