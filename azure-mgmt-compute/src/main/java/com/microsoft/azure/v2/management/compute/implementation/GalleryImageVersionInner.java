/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.v2.management.compute.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.azure.v2.Resource;
import com.microsoft.azure.v2.management.compute.GalleryImageVersionPublishingProfile;
import com.microsoft.azure.v2.management.compute.GalleryImageVersionStorageProfile;
import com.microsoft.azure.v2.management.compute.ReplicationStatus;
import com.microsoft.rest.v2.serializer.JsonFlatten;

/**
 * Specifies information about the gallery image version that you want to
 * create or update.
 */
@JsonFlatten
public class GalleryImageVersionInner extends Resource {
    /**
     * The publishingProfile property.
     */
    @JsonProperty(value = "properties.publishingProfile", required = true)
    private GalleryImageVersionPublishingProfile publishingProfile;

    /**
     * The current state of the gallery image version.
     * The provisioning state, which only appears in the response. Possible
     * values include: 'Creating', 'Updating', 'Failed', 'Succeeded',
     * 'Deleting', 'Migrating'.
     */
    @JsonProperty(value = "properties.provisioningState", access = JsonProperty.Access.WRITE_ONLY)
    private String provisioningState;

    /**
     * The storageProfile property.
     */
    @JsonProperty(value = "properties.storageProfile", access = JsonProperty.Access.WRITE_ONLY)
    private GalleryImageVersionStorageProfile storageProfile;

    /**
     * The replicationStatus property.
     */
    @JsonProperty(value = "properties.replicationStatus", access = JsonProperty.Access.WRITE_ONLY)
    private ReplicationStatus replicationStatus;

    /**
     * Get the publishingProfile value.
     *
     * @return the publishingProfile value.
     */
    public GalleryImageVersionPublishingProfile publishingProfile() {
        return this.publishingProfile;
    }

    /**
     * Set the publishingProfile value.
     *
     * @param publishingProfile the publishingProfile value to set.
     * @return the GalleryImageVersionInner object itself.
     */
    public GalleryImageVersionInner withPublishingProfile(GalleryImageVersionPublishingProfile publishingProfile) {
        this.publishingProfile = publishingProfile;
        return this;
    }

    /**
     * Get the provisioningState value.
     *
     * @return the provisioningState value.
     */
    public String provisioningState() {
        return this.provisioningState;
    }

    /**
     * Get the storageProfile value.
     *
     * @return the storageProfile value.
     */
    public GalleryImageVersionStorageProfile storageProfile() {
        return this.storageProfile;
    }

    /**
     * Get the replicationStatus value.
     *
     * @return the replicationStatus value.
     */
    public ReplicationStatus replicationStatus() {
        return this.replicationStatus;
    }
}
