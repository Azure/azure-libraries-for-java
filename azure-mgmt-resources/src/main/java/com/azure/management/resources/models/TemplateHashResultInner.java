// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.resources.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The TemplateHashResult model.
 */
@Fluent
public final class TemplateHashResultInner {
    /*
     * The minified template string.
     */
    @JsonProperty(value = "minifiedTemplate")
    private String minifiedTemplate;

    /*
     * The template hash.
     */
    @JsonProperty(value = "templateHash")
    private String templateHash;

    /**
     * Get the minifiedTemplate property: The minified template string.
     * 
     * @return the minifiedTemplate value.
     */
    public String getMinifiedTemplate() {
        return this.minifiedTemplate;
    }

    /**
     * Set the minifiedTemplate property: The minified template string.
     * 
     * @param minifiedTemplate the minifiedTemplate value to set.
     * @return the TemplateHashResultInner object itself.
     */
    public TemplateHashResultInner setMinifiedTemplate(String minifiedTemplate) {
        this.minifiedTemplate = minifiedTemplate;
        return this;
    }

    /**
     * Get the templateHash property: The template hash.
     * 
     * @return the templateHash value.
     */
    public String getTemplateHash() {
        return this.templateHash;
    }

    /**
     * Set the templateHash property: The template hash.
     * 
     * @param templateHash the templateHash value to set.
     * @return the TemplateHashResultInner object itself.
     */
    public TemplateHashResultInner setTemplateHash(String templateHash) {
        this.templateHash = templateHash;
        return this;
    }
}
