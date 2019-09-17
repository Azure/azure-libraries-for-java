/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.compute;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is the storage profile of a Gallery Image Version.
 */
public class GalleryImageVersionStorageProfile {
    /**
     * The osDiskImage property.
     */
    @JsonProperty(value = "osDiskImage", access = JsonProperty.Access.WRITE_ONLY)
    private GalleryOSDiskImage osDiskImage;

    /**
     * A list of data disk images.
     */
    @JsonProperty(value = "dataDiskImages", access = JsonProperty.Access.WRITE_ONLY)
    private List<GalleryDataDiskImage> dataDiskImages;

    /**
     * Get the osDiskImage value.
     *
     * @return the osDiskImage value
     */
    public GalleryOSDiskImage osDiskImage() {
        return this.osDiskImage;
    }

    /**
     * Get a list of data disk images.
     *
     * @return the dataDiskImages value
     */
    public List<GalleryDataDiskImage> dataDiskImages() {
        return this.dataDiskImages;
    }

}
