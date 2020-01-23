// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The LogSpecification model.
 */
@Fluent
public final class LogSpecification {
    /*
     * The name of the specification.
     */
    @JsonProperty(value = "name")
    private String name;

    /*
     * The display name of the specification.
     */
    @JsonProperty(value = "displayName")
    private String displayName;

    /*
     * Duration of the blob.
     */
    @JsonProperty(value = "blobDuration")
    private String blobDuration;

    /**
     * Get the name property: The name of the specification.
     * 
     * @return the name value.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name property: The name of the specification.
     * 
     * @param name the name value to set.
     * @return the LogSpecification object itself.
     */
    public LogSpecification setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the displayName property: The display name of the specification.
     * 
     * @return the displayName value.
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Set the displayName property: The display name of the specification.
     * 
     * @param displayName the displayName value to set.
     * @return the LogSpecification object itself.
     */
    public LogSpecification setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Get the blobDuration property: Duration of the blob.
     * 
     * @return the blobDuration value.
     */
    public String getBlobDuration() {
        return this.blobDuration;
    }

    /**
     * Set the blobDuration property: Duration of the blob.
     * 
     * @param blobDuration the blobDuration value to set.
     * @return the LogSpecification object itself.
     */
    public LogSpecification setBlobDuration(String blobDuration) {
        this.blobDuration = blobDuration;
        return this;
    }
}