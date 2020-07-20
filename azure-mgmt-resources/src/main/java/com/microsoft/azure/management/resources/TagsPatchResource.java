/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wrapper resource for tags patch API request only.
 */
public class TagsPatchResource {
    /**
     * The operation type for the patch API. Possible values include:
     * 'Replace', 'Merge', 'Delete'.
     */
    @JsonProperty(value = "operation")
    private TagOperation operation;

    /**
     * The set of tags.
     */
    @JsonProperty(value = "properties")
    private Tags properties;

    /**
     * Get the operation type for the patch API. Possible values include: 'Replace', 'Merge', 'Delete'.
     *
     * @return the operation value
     */
    public TagOperation operation() {
        return this.operation;
    }

    /**
     * Set the operation type for the patch API. Possible values include: 'Replace', 'Merge', 'Delete'.
     *
     * @param operation the operation value to set
     * @return the TagsPatchResource object itself.
     */
    public TagsPatchResource withOperation(TagOperation operation) {
        this.operation = operation;
        return this;
    }

    /**
     * Get the set of tags.
     *
     * @return the properties value
     */
    public Tags properties() {
        return this.properties;
    }

    /**
     * Set the set of tags.
     *
     * @param properties the properties value to set
     * @return the TagsPatchResource object itself.
     */
    public TagsPatchResource withProperties(Tags properties) {
        this.properties = properties;
        return this;
    }

}
