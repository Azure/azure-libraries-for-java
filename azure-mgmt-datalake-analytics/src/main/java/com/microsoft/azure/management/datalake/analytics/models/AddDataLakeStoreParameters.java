/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.datalake.analytics.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * Additional Data Lake Store parameters.
 */
@JsonFlatten
public class AddDataLakeStoreParameters {
    /**
     * the optional suffix for the Data Lake Store account.
     */
    @JsonProperty(value = "properties.suffix")
    private String suffix;

    /**
     * Get the suffix value.
     *
     * @return the suffix value
     */
    public String suffix() {
        return this.suffix;
    }

    /**
     * Set the suffix value.
     *
     * @param suffix the suffix value to set
     * @return the AddDataLakeStoreParameters object itself.
     */
    public AddDataLakeStoreParameters withSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

}
