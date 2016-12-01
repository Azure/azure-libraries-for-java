/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.website;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Name of a metric for any resource .
 */
public class ResourceMetricName {
    /**
     * metric name value.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String value;

    /**
     * Localized metric name value.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String localizedValue;

    /**
     * Get the value value.
     *
     * @return the value value
     */
    public String value() {
        return this.value;
    }

    /**
     * Get the localizedValue value.
     *
     * @return the localizedValue value
     */
    public String localizedValue() {
        return this.localizedValue;
    }

}
