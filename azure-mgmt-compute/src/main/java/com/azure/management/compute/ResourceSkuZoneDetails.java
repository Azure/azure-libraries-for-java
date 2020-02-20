// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.compute;

import com.azure.core.annotation.Immutable;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The ResourceSkuZoneDetails model.
 */
@Immutable
public final class ResourceSkuZoneDetails {
    /*
     * The set of zones that the SKU is available in with the specified
     * capabilities.
     */
    @JsonProperty(value = "name", access = JsonProperty.Access.WRITE_ONLY)
    private List<String> name;

    /*
     * A list of capabilities that are available for the SKU in the specified
     * list of zones.
     */
    @JsonProperty(value = "capabilities", access = JsonProperty.Access.WRITE_ONLY)
    private List<ResourceSkuCapabilities> capabilities;

    /**
     * Get the name property: The set of zones that the SKU is available in
     * with the specified capabilities.
     * 
     * @return the name value.
     */
    public List<String> name() {
        return this.name;
    }

    /**
     * Get the capabilities property: A list of capabilities that are available
     * for the SKU in the specified list of zones.
     * 
     * @return the capabilities value.
     */
    public List<ResourceSkuCapabilities> capabilities() {
        return this.capabilities;
    }
}
