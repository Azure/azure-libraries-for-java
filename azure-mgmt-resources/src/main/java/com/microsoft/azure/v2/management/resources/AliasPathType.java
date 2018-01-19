/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.v2.management.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The type of the paths for alias.
 */
public class AliasPathType {
    /**
     * The path of an alias.
     */
    @JsonProperty(value = "path")
    private String path;

    /**
     * The API versions.
     */
    @JsonProperty(value = "apiVersions")
    private List<String> apiVersions;

    /**
     * Get the path value.
     *
     * @return the path value.
     */
    public String path() {
        return this.path;
    }

    /**
     * Set the path value.
     *
     * @param path the path value to set.
     * @return the AliasPathType object itself.
     */
    public AliasPathType withPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * Get the apiVersions value.
     *
     * @return the apiVersions value.
     */
    public List<String> apiVersions() {
        return this.apiVersions;
    }

    /**
     * Set the apiVersions value.
     *
     * @param apiVersions the apiVersions value to set.
     * @return the AliasPathType object itself.
     */
    public AliasPathType withApiVersions(List<String> apiVersions) {
        this.apiVersions = apiVersions;
        return this;
    }
}
