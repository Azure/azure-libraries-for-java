// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The UsageName model.
 */
@Fluent
public final class UsageName {
    /*
     * A string describing the resource name.
     */
    @JsonProperty(value = "value")
    private String value;

    /*
     * A localized string describing the resource name.
     */
    @JsonProperty(value = "localizedValue")
    private String localizedValue;

    /**
     * Get the value property: A string describing the resource name.
     * 
     * @return the value value.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Set the value property: A string describing the resource name.
     * 
     * @param value the value value to set.
     * @return the UsageName object itself.
     */
    public UsageName setValue(String value) {
        this.value = value;
        return this;
    }

    /**
     * Get the localizedValue property: A localized string describing the
     * resource name.
     * 
     * @return the localizedValue value.
     */
    public String getLocalizedValue() {
        return this.localizedValue;
    }

    /**
     * Set the localizedValue property: A localized string describing the
     * resource name.
     * 
     * @param localizedValue the localizedValue value to set.
     * @return the UsageName object itself.
     */
    public UsageName setLocalizedValue(String localizedValue) {
        this.localizedValue = localizedValue;
        return this;
    }
}