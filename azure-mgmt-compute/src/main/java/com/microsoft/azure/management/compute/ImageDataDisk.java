/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.compute;

import com.microsoft.azure.SubResource;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Describes a data disk.
 */
public class ImageDataDisk {
    /**
     * The logical unit number.
     */
    @JsonProperty(required = true)
    private int lun;

    /**
     * The snapshot.
     */
    private SubResource snapshot;

    /**
     * The managedDisk.
     */
    private SubResource managedDisk;

    /**
     * The Virtual Hard Disk.
     */
    private String blobUri;

    /**
     * The caching type. Possible values include: 'None', 'ReadOnly',
     * 'ReadWrite'.
     */
    private CachingTypes caching;

    /**
     * The initial disk size in GB for blank data disks, and the new desired
     * size for existing OS and Data disks.
     */
    private Integer diskSizeGB;

    /**
     * Get the lun value.
     *
     * @return the lun value
     */
    public int lun() {
        return this.lun;
    }

    /**
     * Set the lun value.
     *
     * @param lun the lun value to set
     * @return the ImageDataDisk object itself.
     */
    public ImageDataDisk withLun(int lun) {
        this.lun = lun;
        return this;
    }

    /**
     * Get the snapshot value.
     *
     * @return the snapshot value
     */
    public SubResource snapshot() {
        return this.snapshot;
    }

    /**
     * Set the snapshot value.
     *
     * @param snapshot the snapshot value to set
     * @return the ImageDataDisk object itself.
     */
    public ImageDataDisk withSnapshot(SubResource snapshot) {
        this.snapshot = snapshot;
        return this;
    }

    /**
     * Get the managedDisk value.
     *
     * @return the managedDisk value
     */
    public SubResource managedDisk() {
        return this.managedDisk;
    }

    /**
     * Set the managedDisk value.
     *
     * @param managedDisk the managedDisk value to set
     * @return the ImageDataDisk object itself.
     */
    public ImageDataDisk withManagedDisk(SubResource managedDisk) {
        this.managedDisk = managedDisk;
        return this;
    }

    /**
     * Get the blobUri value.
     *
     * @return the blobUri value
     */
    public String blobUri() {
        return this.blobUri;
    }

    /**
     * Set the blobUri value.
     *
     * @param blobUri the blobUri value to set
     * @return the ImageDataDisk object itself.
     */
    public ImageDataDisk withBlobUri(String blobUri) {
        this.blobUri = blobUri;
        return this;
    }

    /**
     * Get the caching value.
     *
     * @return the caching value
     */
    public CachingTypes caching() {
        return this.caching;
    }

    /**
     * Set the caching value.
     *
     * @param caching the caching value to set
     * @return the ImageDataDisk object itself.
     */
    public ImageDataDisk withCaching(CachingTypes caching) {
        this.caching = caching;
        return this;
    }

    /**
     * Get the diskSizeGB value.
     *
     * @return the diskSizeGB value
     */
    public Integer diskSizeGB() {
        return this.diskSizeGB;
    }

    /**
     * Set the diskSizeGB value.
     *
     * @param diskSizeGB the diskSizeGB value to set
     * @return the ImageDataDisk object itself.
     */
    public ImageDataDisk withDiskSizeGB(Integer diskSizeGB) {
        this.diskSizeGB = diskSizeGB;
        return this;
    }

}
