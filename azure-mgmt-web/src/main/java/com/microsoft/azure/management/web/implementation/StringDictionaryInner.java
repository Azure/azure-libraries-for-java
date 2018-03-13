/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.web.implementation;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.azure.management.web.ProxyOnlyResource;

/**
 * String dictionary resource.
 */
public class StringDictionaryInner extends ProxyOnlyResource {
    /**
     * Settings.
     */
    @JsonProperty(value = "properties")
    private Map<String, String> properties;

    /**
     * Get the properties value.
     *
     * @return the properties value
     */
    public Map<String, String> properties() {
        return this.properties;
    }

    /**
     * Set the properties value.
     *
     * @param properties the properties value to set
     * @return the StringDictionaryInner object itself.
     */
    public StringDictionaryInner withProperties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

}
