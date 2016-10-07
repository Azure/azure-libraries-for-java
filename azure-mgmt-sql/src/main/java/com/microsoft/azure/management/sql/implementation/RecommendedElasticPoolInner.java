/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.sql.ElasticPoolEditions;
import org.joda.time.DateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.Resource;

/**
 * Represents an Azure SQL Recommended Elastic Pool.
 */
@JsonFlatten
public class RecommendedElasticPoolInner extends Resource {
    /**
     * Gets the edition of the Azure SQL Recommended Elastic Pool. The
     * ElasticPoolEditions enumeration contains all the valid editions.
     * Possible values include: 'Basic', 'Standard', 'Premium'.
     */
    @JsonProperty(value = "properties.databaseEdition", access = JsonProperty.Access.WRITE_ONLY)
    private ElasticPoolEditions databaseEdition;

    /**
     * Gets the DTU for the Sql Azure Recommended Elastic Pool.
     */
    @JsonProperty(value = "properties.dtu", access = JsonProperty.Access.WRITE_ONLY)
    private Double dtu;

    /**
     * Gets the minimum DTU for the database.
     */
    @JsonProperty(value = "properties.databaseDtuMin", access = JsonProperty.Access.WRITE_ONLY)
    private Double databaseDtuMin;

    /**
     * Gets the maximum DTU for the database.
     */
    @JsonProperty(value = "properties.databaseDtuMax", access = JsonProperty.Access.WRITE_ONLY)
    private Double databaseDtuMax;

    /**
     * Gets storage size in megabytes.
     */
    @JsonProperty(value = "properties.storageMB", access = JsonProperty.Access.WRITE_ONLY)
    private Double storageMB;

    /**
     * Gets the observation period start.
     */
    @JsonProperty(value = "properties.observationPeriodStart", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime observationPeriodStart;

    /**
     * Gets the observation period start.
     */
    @JsonProperty(value = "properties.observationPeriodEnd", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime observationPeriodEnd;

    /**
     * Gets maximum observed DTU.
     */
    @JsonProperty(value = "properties.maxObservedDtu", access = JsonProperty.Access.WRITE_ONLY)
    private Double maxObservedDtu;

    /**
     * Gets maximum observed storage in megabytes.
     */
    @JsonProperty(value = "properties.maxObservedStorageMB", access = JsonProperty.Access.WRITE_ONLY)
    private Double maxObservedStorageMB;

    /**
     * Gets the list of Azure Sql Databases in this pool. Expanded property.
     */
    @JsonProperty(value = "properties.databases", access = JsonProperty.Access.WRITE_ONLY)
    private List<DatabaseInner> databases;

    /**
     * Gets the list of Azure Sql Databases housed in the server. Expanded
     * property.
     */
    @JsonProperty(value = "properties.metrics", access = JsonProperty.Access.WRITE_ONLY)
    private List<RecommendedElasticPoolMetricInner> metrics;

    /**
     * Get the databaseEdition value.
     *
     * @return the databaseEdition value
     */
    public ElasticPoolEditions databaseEdition() {
        return this.databaseEdition;
    }

    /**
     * Get the dtu value.
     *
     * @return the dtu value
     */
    public Double dtu() {
        return this.dtu;
    }

    /**
     * Get the databaseDtuMin value.
     *
     * @return the databaseDtuMin value
     */
    public Double databaseDtuMin() {
        return this.databaseDtuMin;
    }

    /**
     * Get the databaseDtuMax value.
     *
     * @return the databaseDtuMax value
     */
    public Double databaseDtuMax() {
        return this.databaseDtuMax;
    }

    /**
     * Get the storageMB value.
     *
     * @return the storageMB value
     */
    public Double storageMB() {
        return this.storageMB;
    }

    /**
     * Get the observationPeriodStart value.
     *
     * @return the observationPeriodStart value
     */
    public DateTime observationPeriodStart() {
        return this.observationPeriodStart;
    }

    /**
     * Get the observationPeriodEnd value.
     *
     * @return the observationPeriodEnd value
     */
    public DateTime observationPeriodEnd() {
        return this.observationPeriodEnd;
    }

    /**
     * Get the maxObservedDtu value.
     *
     * @return the maxObservedDtu value
     */
    public Double maxObservedDtu() {
        return this.maxObservedDtu;
    }

    /**
     * Get the maxObservedStorageMB value.
     *
     * @return the maxObservedStorageMB value
     */
    public Double maxObservedStorageMB() {
        return this.maxObservedStorageMB;
    }

    /**
     * Get the databases value.
     *
     * @return the databases value
     */
    public List<DatabaseInner> databases() {
        return this.databases;
    }

    /**
     * Get the metrics value.
     *
     * @return the metrics value
     */
    public List<RecommendedElasticPoolMetricInner> metrics() {
        return this.metrics;
    }

}
