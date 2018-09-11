/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.v2.management.sql.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.azure.v2.management.sql.ProxyResource;
import com.microsoft.azure.v2.management.sql.SyncAgentState;
import com.microsoft.rest.v2.serializer.JsonFlatten;
import java.time.OffsetDateTime;

/**
 * An Azure SQL Database sync agent.
 */
@JsonFlatten
public class SyncAgentInner extends ProxyResource {
    /**
     * Name of the sync agent.
     */
    @JsonProperty(value = "properties.name", access = JsonProperty.Access.WRITE_ONLY)
    private String syncAgentName;

    /**
     * ARM resource id of the sync database in the sync agent.
     */
    @JsonProperty(value = "properties.syncDatabaseId")
    private String syncDatabaseId;

    /**
     * Last alive time of the sync agent.
     */
    @JsonProperty(value = "properties.lastAliveTime", access = JsonProperty.Access.WRITE_ONLY)
    private OffsetDateTime lastAliveTime;

    /**
     * State of the sync agent. Possible values include: 'Online', 'Offline',
     * 'NeverConnected'.
     */
    @JsonProperty(value = "properties.state", access = JsonProperty.Access.WRITE_ONLY)
    private SyncAgentState state;

    /**
     * If the sync agent version is up to date.
     */
    @JsonProperty(value = "properties.isUpToDate", access = JsonProperty.Access.WRITE_ONLY)
    private Boolean isUpToDate;

    /**
     * Expiration time of the sync agent version.
     */
    @JsonProperty(value = "properties.expiryTime", access = JsonProperty.Access.WRITE_ONLY)
    private OffsetDateTime expiryTime;

    /**
     * Version of the sync agent.
     */
    @JsonProperty(value = "properties.version", access = JsonProperty.Access.WRITE_ONLY)
    private String version;

    /**
     * Get the syncAgentName value.
     *
     * @return the syncAgentName value.
     */
    public String syncAgentName() {
        return this.syncAgentName;
    }

    /**
     * Get the syncDatabaseId value.
     *
     * @return the syncDatabaseId value.
     */
    public String syncDatabaseId() {
        return this.syncDatabaseId;
    }

    /**
     * Set the syncDatabaseId value.
     *
     * @param syncDatabaseId the syncDatabaseId value to set.
     * @return the SyncAgentInner object itself.
     */
    public SyncAgentInner withSyncDatabaseId(String syncDatabaseId) {
        this.syncDatabaseId = syncDatabaseId;
        return this;
    }

    /**
     * Get the lastAliveTime value.
     *
     * @return the lastAliveTime value.
     */
    public OffsetDateTime lastAliveTime() {
        return this.lastAliveTime;
    }

    /**
     * Get the state value.
     *
     * @return the state value.
     */
    public SyncAgentState state() {
        return this.state;
    }

    /**
     * Get the isUpToDate value.
     *
     * @return the isUpToDate value.
     */
    public Boolean isUpToDate() {
        return this.isUpToDate;
    }

    /**
     * Get the expiryTime value.
     *
     * @return the expiryTime value.
     */
    public OffsetDateTime expiryTime() {
        return this.expiryTime;
    }

    /**
     * Get the version value.
     *
     * @return the version value.
     */
    public String version() {
        return this.version;
    }
}