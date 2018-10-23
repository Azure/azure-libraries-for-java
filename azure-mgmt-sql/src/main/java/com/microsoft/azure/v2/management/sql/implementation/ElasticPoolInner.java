/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.v2.management.sql.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.azure.v2.management.sql.ElasticPoolLicenseType;
import com.microsoft.azure.v2.management.sql.ElasticPoolPerDatabaseSettings;
import com.microsoft.azure.v2.management.sql.ElasticPoolState;
import com.microsoft.azure.v2.management.sql.Sku;
import com.microsoft.azure.v2.management.sql.TrackedResource;
import com.microsoft.rest.v2.serializer.JsonFlatten;
import java.time.OffsetDateTime;

/**
 * An elastic pool.
 */
@JsonFlatten
public class ElasticPoolInner extends TrackedResource {
    /**
     * The sku property.
     */
    @JsonProperty(value = "sku")
    private Sku sku;

    /**
     * Kind of elastic pool. This is metadata used for the Azure portal
     * experience.
     */
    @JsonProperty(value = "kind", access = JsonProperty.Access.WRITE_ONLY)
    private String kind;

    /**
     * The state of the elastic pool. Possible values include: 'Creating',
     * 'Ready', 'Disabled'.
     */
    @JsonProperty(value = "properties.state", access = JsonProperty.Access.WRITE_ONLY)
    private ElasticPoolState state;

    /**
     * The creation date of the elastic pool (ISO8601 format).
     */
    @JsonProperty(value = "properties.creationDate", access = JsonProperty.Access.WRITE_ONLY)
    private OffsetDateTime creationDate;

    /**
     * The storage limit for the database elastic pool in bytes.
     */
    @JsonProperty(value = "properties.maxSizeBytes")
    private Long maxSizeBytes;

    /**
     * The per database settings for the elastic pool.
     */
    @JsonProperty(value = "properties.perDatabaseSettings")
    private ElasticPoolPerDatabaseSettings perDatabaseSettings;

    /**
     * Whether or not this elastic pool is zone redundant, which means the
     * replicas of this elastic pool will be spread across multiple
     * availability zones.
     */
    @JsonProperty(value = "properties.zoneRedundant")
    private Boolean zoneRedundant;

    /**
     * The license type to apply for this elastic pool. Possible values
     * include: 'LicenseIncluded', 'BasePrice'.
     */
    @JsonProperty(value = "properties.licenseType")
    private ElasticPoolLicenseType licenseType;

    /**
     * Get the sku value.
     *
     * @return the sku value.
     */
    public Sku sku() {
        return this.sku;
    }

    /**
     * Set the sku value.
     *
     * @param sku the sku value to set.
     * @return the ElasticPoolInner object itself.
     */
    public ElasticPoolInner withSku(Sku sku) {
        this.sku = sku;
        return this;
    }

    /**
     * Get the kind value.
     *
     * @return the kind value.
     */
    public String kind() {
        return this.kind;
    }

    /**
     * Get the state value.
     *
     * @return the state value.
     */
    public ElasticPoolState state() {
        return this.state;
    }

    /**
     * Get the creationDate value.
     *
     * @return the creationDate value.
     */
    public OffsetDateTime creationDate() {
        return this.creationDate;
    }

    /**
     * Get the maxSizeBytes value.
     *
     * @return the maxSizeBytes value.
     */
    public Long maxSizeBytes() {
        return this.maxSizeBytes;
    }

    /**
     * Set the maxSizeBytes value.
     *
     * @param maxSizeBytes the maxSizeBytes value to set.
     * @return the ElasticPoolInner object itself.
     */
    public ElasticPoolInner withMaxSizeBytes(Long maxSizeBytes) {
        this.maxSizeBytes = maxSizeBytes;
        return this;
    }

    /**
     * Get the perDatabaseSettings value.
     *
     * @return the perDatabaseSettings value.
     */
    public ElasticPoolPerDatabaseSettings perDatabaseSettings() {
        return this.perDatabaseSettings;
    }

    /**
     * Set the perDatabaseSettings value.
     *
     * @param perDatabaseSettings the perDatabaseSettings value to set.
     * @return the ElasticPoolInner object itself.
     */
    public ElasticPoolInner withPerDatabaseSettings(ElasticPoolPerDatabaseSettings perDatabaseSettings) {
        this.perDatabaseSettings = perDatabaseSettings;
        return this;
    }

    /**
     * Get the zoneRedundant value.
     *
     * @return the zoneRedundant value.
     */
    public Boolean zoneRedundant() {
        return this.zoneRedundant;
    }

    /**
     * Set the zoneRedundant value.
     *
     * @param zoneRedundant the zoneRedundant value to set.
     * @return the ElasticPoolInner object itself.
     */
    public ElasticPoolInner withZoneRedundant(Boolean zoneRedundant) {
        this.zoneRedundant = zoneRedundant;
        return this;
    }

    /**
     * Get the licenseType value.
     *
     * @return the licenseType value.
     */
    public ElasticPoolLicenseType licenseType() {
        return this.licenseType;
    }

    /**
     * Set the licenseType value.
     *
     * @param licenseType the licenseType value to set.
     * @return the ElasticPoolInner object itself.
     */
    public ElasticPoolInner withLicenseType(ElasticPoolLicenseType licenseType) {
        this.licenseType = licenseType;
        return this;
    }
}
