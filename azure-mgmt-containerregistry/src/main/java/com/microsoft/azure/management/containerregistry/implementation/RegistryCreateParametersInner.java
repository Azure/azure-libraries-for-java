/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.containerregistry.implementation;

import java.util.Map;
import com.microsoft.azure.management.containerregistry.Sku;
import com.microsoft.azure.management.containerregistry.StorageAccountParameters;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * The parameters for creating a container registry.
 */
@JsonFlatten
public class RegistryCreateParametersInner {
    /**
     * The tags for the container registry.
     */
    @JsonProperty(value = "tags")
    private Map<String, String> tags;

    /**
     * The location of the container registry. This cannot be changed after the
     * resource is created.
     */
    @JsonProperty(value = "location", required = true)
    private String location;

    /**
     * The SKU of the container registry.
     */
    @JsonProperty(value = "sku", required = true)
    private Sku sku;

    /**
     * The value that indicates whether the admin user is enabled. This value
     * is false by default.
     */
    @JsonProperty(value = "properties.adminUserEnabled")
    private Boolean adminUserEnabled;

    /**
     * The parameters of a storage account for the container registry. If
     * specified, the storage account must be in the same physical location as
     * the container registry.
     */
    @JsonProperty(value = "properties.storageAccount", required = true)
    private StorageAccountParameters storageAccount;

    /**
     * Get the tags value.
     *
     * @return the tags value
     */
    public Map<String, String> tags() {
        return this.tags;
    }

    /**
     * Set the tags value.
     *
     * @param tags the tags value to set
     * @return the RegistryCreateParametersInner object itself.
     */
    public RegistryCreateParametersInner withTags(Map<String, String> tags) {
        this.tags = tags;
        return this;
    }

    /**
     * Get the location value.
     *
     * @return the location value
     */
    public String location() {
        return this.location;
    }

    /**
     * Set the location value.
     *
     * @param location the location value to set
     * @return the RegistryCreateParametersInner object itself.
     */
    public RegistryCreateParametersInner withLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * Get the sku value.
     *
     * @return the sku value
     */
    public Sku sku() {
        return this.sku;
    }

    /**
     * Set the sku value.
     *
     * @param sku the sku value to set
     * @return the RegistryCreateParametersInner object itself.
     */
    public RegistryCreateParametersInner withSku(Sku sku) {
        this.sku = sku;
        return this;
    }

    /**
     * Get the adminUserEnabled value.
     *
     * @return the adminUserEnabled value
     */
    public Boolean adminUserEnabled() {
        return this.adminUserEnabled;
    }

    /**
     * Set the adminUserEnabled value.
     *
     * @param adminUserEnabled the adminUserEnabled value to set
     * @return the RegistryCreateParametersInner object itself.
     */
    public RegistryCreateParametersInner withAdminUserEnabled(Boolean adminUserEnabled) {
        this.adminUserEnabled = adminUserEnabled;
        return this;
    }

    /**
     * Get the storageAccount value.
     *
     * @return the storageAccount value
     */
    public StorageAccountParameters storageAccount() {
        return this.storageAccount;
    }

    /**
     * Set the storageAccount value.
     *
     * @param storageAccount the storageAccount value to set
     * @return the RegistryCreateParametersInner object itself.
     */
    public RegistryCreateParametersInner withStorageAccount(StorageAccountParameters storageAccount) {
        this.storageAccount = storageAccount;
        return this;
    }

}
