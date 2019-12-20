// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.resources.models;

import com.azure.core.annotation.Fluent;
import com.azure.management.resources.ResourceGroupProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * The ResourceGroup model.
 */
@Fluent
public final class ResourceGroupInner {
    /*
     * The ID of the resource group.
     */
    @JsonProperty(value = "id", access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    /*
     * The name of the resource group.
     */
    @JsonProperty(value = "name", access = JsonProperty.Access.WRITE_ONLY)
    private String name;

    /*
     * The type of the resource group.
     */
    @JsonProperty(value = "type", access = JsonProperty.Access.WRITE_ONLY)
    private String type;

    /*
     * The resource group properties.
     */
    @JsonProperty(value = "properties")
    private ResourceGroupProperties properties;

    /*
     * The location of the resource group. It cannot be changed after the
     * resource group has been created. It must be one of the supported Azure
     * locations.
     */
    @JsonProperty(value = "location", required = true)
    private String location;

    /*
     * The ID of the resource that manages this resource group.
     */
    @JsonProperty(value = "managedBy")
    private String managedBy;

    /*
     * The tags attached to the resource group.
     */
    @JsonProperty(value = "tags")
    private Map<String, String> tags;

    /**
     * Get the id property: The ID of the resource group.
     * 
     * @return the id value.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Get the name property: The name of the resource group.
     * 
     * @return the name value.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the type property: The type of the resource group.
     * 
     * @return the type value.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Get the properties property: The resource group properties.
     * 
     * @return the properties value.
     */
    public ResourceGroupProperties getProperties() {
        return this.properties;
    }

    /**
     * Set the properties property: The resource group properties.
     * 
     * @param properties the properties value to set.
     * @return the ResourceGroupInner object itself.
     */
    public ResourceGroupInner setProperties(ResourceGroupProperties properties) {
        this.properties = properties;
        return this;
    }

    /**
     * Get the location property: The location of the resource group. It cannot
     * be changed after the resource group has been created. It must be one of
     * the supported Azure locations.
     * 
     * @return the location value.
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Set the location property: The location of the resource group. It cannot
     * be changed after the resource group has been created. It must be one of
     * the supported Azure locations.
     * 
     * @param location the location value to set.
     * @return the ResourceGroupInner object itself.
     */
    public ResourceGroupInner setLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * Get the managedBy property: The ID of the resource that manages this
     * resource group.
     * 
     * @return the managedBy value.
     */
    public String getManagedBy() {
        return this.managedBy;
    }

    /**
     * Set the managedBy property: The ID of the resource that manages this
     * resource group.
     * 
     * @param managedBy the managedBy value to set.
     * @return the ResourceGroupInner object itself.
     */
    public ResourceGroupInner setManagedBy(String managedBy) {
        this.managedBy = managedBy;
        return this;
    }

    /**
     * Get the tags property: The tags attached to the resource group.
     * 
     * @return the tags value.
     */
    public Map<String, String> getTags() {
        return this.tags;
    }

    /**
     * Set the tags property: The tags attached to the resource group.
     * 
     * @param tags the tags value to set.
     * @return the ResourceGroupInner object itself.
     */
    public ResourceGroupInner setTags(Map<String, String> tags) {
        this.tags = tags;
        return this;
    }
}
