// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.resources.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The DeploymentExportResult model.
 */
@Fluent
public final class DeploymentExportResultInner {
    /*
     * The template content.
     */
    @JsonProperty(value = "template")
    private Object template;

    /**
     * Get the template property: The template content.
     * 
     * @return the template value.
     */
    public Object getTemplate() {
        return this.template;
    }

    /**
     * Set the template property: The template content.
     * 
     * @param template the template value to set.
     * @return the DeploymentExportResultInner object itself.
     */
    public DeploymentExportResultInner setTemplate(Object template) {
        this.template = template;
        return this;
    }
}
