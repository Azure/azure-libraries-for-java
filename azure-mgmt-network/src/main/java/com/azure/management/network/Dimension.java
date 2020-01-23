// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Dimension model.
 */
@Fluent
public final class Dimension {
    /*
     * The name of the dimension.
     */
    @JsonProperty(value = "name")
    private String name;

    /*
     * The display name of the dimension.
     */
    @JsonProperty(value = "displayName")
    private String displayName;

    /*
     * The internal name of the dimension.
     */
    @JsonProperty(value = "internalName")
    private String internalName;

    /**
     * Get the name property: The name of the dimension.
     * 
     * @return the name value.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name property: The name of the dimension.
     * 
     * @param name the name value to set.
     * @return the Dimension object itself.
     */
    public Dimension setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the displayName property: The display name of the dimension.
     * 
     * @return the displayName value.
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Set the displayName property: The display name of the dimension.
     * 
     * @param displayName the displayName value to set.
     * @return the Dimension object itself.
     */
    public Dimension setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Get the internalName property: The internal name of the dimension.
     * 
     * @return the internalName value.
     */
    public String getInternalName() {
        return this.internalName;
    }

    /**
     * Set the internalName property: The internal name of the dimension.
     * 
     * @param internalName the internalName value to set.
     * @return the Dimension object itself.
     */
    public Dimension setInternalName(String internalName) {
        this.internalName = internalName;
        return this;
    }
}