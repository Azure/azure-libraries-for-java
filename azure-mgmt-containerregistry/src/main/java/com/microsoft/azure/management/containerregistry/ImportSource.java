/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.containerregistry;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The ImportSource model.
 */
public class ImportSource {
    /**
     * The resource identifier of the source Azure Container Registry.
     */
    @JsonProperty(value = "resourceId")
    private String resourceId;

    /**
     * Get the resourceId value.
     *
     * @return the resourceId value
     */
    public String resourceId() {
        return this.resourceId;
    }

    /**
     * Set the resourceId value.
     *
     * @param resourceId the resourceId value to set
     * @return the ImportSource object itself.
     */
    public ImportSource withResourceId(String resourceId) {
        this.resourceId = resourceId;
        return this;
    }

}
