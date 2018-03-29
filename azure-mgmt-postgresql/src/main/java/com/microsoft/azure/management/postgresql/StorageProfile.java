/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.postgresql;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Storage Profile properties of a server.
 */
public class StorageProfile {
    /**
     * Backup retention days for the server.
     */
    @JsonProperty(value = "backupRetentionDays")
    private Integer backupRetentionDays;

    /**
     * Enable Geo-redundant or not for server backup. Possible values include:
     * 'Enabled', 'Disabled'.
     */
    @JsonProperty(value = "geoRedundantBackup")
    private GeoRedundantBackup geoRedundantBackup;

    /**
     * Max storage allowed for a server.
     */
    @JsonProperty(value = "storageMB")
    private Integer storageMB;

    /**
     * Get the backupRetentionDays value.
     *
     * @return the backupRetentionDays value
     */
    public Integer backupRetentionDays() {
        return this.backupRetentionDays;
    }

    /**
     * Set the backupRetentionDays value.
     *
     * @param backupRetentionDays the backupRetentionDays value to set
     * @return the StorageProfile object itself.
     */
    public StorageProfile withBackupRetentionDays(Integer backupRetentionDays) {
        this.backupRetentionDays = backupRetentionDays;
        return this;
    }

    /**
     * Get the geoRedundantBackup value.
     *
     * @return the geoRedundantBackup value
     */
    public GeoRedundantBackup geoRedundantBackup() {
        return this.geoRedundantBackup;
    }

    /**
     * Set the geoRedundantBackup value.
     *
     * @param geoRedundantBackup the geoRedundantBackup value to set
     * @return the StorageProfile object itself.
     */
    public StorageProfile withGeoRedundantBackup(GeoRedundantBackup geoRedundantBackup) {
        this.geoRedundantBackup = geoRedundantBackup;
        return this;
    }

    /**
     * Get the storageMB value.
     *
     * @return the storageMB value
     */
    public Integer storageMB() {
        return this.storageMB;
    }

    /**
     * Set the storageMB value.
     *
     * @param storageMB the storageMB value to set
     * @return the StorageProfile object itself.
     */
    public StorageProfile withStorageMB(Integer storageMB) {
        this.storageMB = storageMB;
        return this;
    }

}
