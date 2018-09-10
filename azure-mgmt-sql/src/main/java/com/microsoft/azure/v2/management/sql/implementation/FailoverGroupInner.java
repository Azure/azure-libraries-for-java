/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.v2.management.sql.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.azure.v2.management.sql.FailoverGroupReadOnlyEndpoint;
import com.microsoft.azure.v2.management.sql.FailoverGroupReadWriteEndpoint;
import com.microsoft.azure.v2.management.sql.FailoverGroupReplicationRole;
import com.microsoft.azure.v2.management.sql.PartnerInfo;
import com.microsoft.azure.v2.management.sql.ProxyResource;
import com.microsoft.rest.v2.serializer.JsonFlatten;
import java.util.List;
import java.util.Map;

/**
 * A failover group.
 */
@JsonFlatten
public class FailoverGroupInner extends ProxyResource {
    /**
     * Resource location.
     */
    @JsonProperty(value = "location", access = JsonProperty.Access.WRITE_ONLY)
    private String location;

    /**
     * Resource tags.
     */
    @JsonProperty(value = "tags")
    private Map<String, String> tags;

    /**
     * Read-write endpoint of the failover group instance.
     */
    @JsonProperty(value = "properties.readWriteEndpoint", required = true)
    private FailoverGroupReadWriteEndpoint readWriteEndpoint;

    /**
     * Read-only endpoint of the failover group instance.
     */
    @JsonProperty(value = "properties.readOnlyEndpoint")
    private FailoverGroupReadOnlyEndpoint readOnlyEndpoint;

    /**
     * Local replication role of the failover group instance. Possible values
     * include: 'Primary', 'Secondary'.
     */
    @JsonProperty(value = "properties.replicationRole", access = JsonProperty.Access.WRITE_ONLY)
    private FailoverGroupReplicationRole replicationRole;

    /**
     * Replication state of the failover group instance.
     */
    @JsonProperty(value = "properties.replicationState", access = JsonProperty.Access.WRITE_ONLY)
    private String replicationState;

    /**
     * List of partner server information for the failover group.
     */
    @JsonProperty(value = "properties.partnerServers", required = true)
    private List<PartnerInfo> partnerServers;

    /**
     * List of databases in the failover group.
     */
    @JsonProperty(value = "properties.databases")
    private List<String> databasesProperty;

    /**
     * Get the location value.
     *
     * @return the location value.
     */
    public String location() {
        return this.location;
    }

    /**
     * Get the tags value.
     *
     * @return the tags value.
     */
    public Map<String, String> tags() {
        return this.tags;
    }

    /**
     * Set the tags value.
     *
     * @param tags the tags value to set.
     * @return the FailoverGroupInner object itself.
     */
    public FailoverGroupInner withTags(Map<String, String> tags) {
        this.tags = tags;
        return this;
    }

    /**
     * Get the readWriteEndpoint value.
     *
     * @return the readWriteEndpoint value.
     */
    public FailoverGroupReadWriteEndpoint readWriteEndpoint() {
        return this.readWriteEndpoint;
    }

    /**
     * Set the readWriteEndpoint value.
     *
     * @param readWriteEndpoint the readWriteEndpoint value to set.
     * @return the FailoverGroupInner object itself.
     */
    public FailoverGroupInner withReadWriteEndpoint(FailoverGroupReadWriteEndpoint readWriteEndpoint) {
        this.readWriteEndpoint = readWriteEndpoint;
        return this;
    }

    /**
     * Get the readOnlyEndpoint value.
     *
     * @return the readOnlyEndpoint value.
     */
    public FailoverGroupReadOnlyEndpoint readOnlyEndpoint() {
        return this.readOnlyEndpoint;
    }

    /**
     * Set the readOnlyEndpoint value.
     *
     * @param readOnlyEndpoint the readOnlyEndpoint value to set.
     * @return the FailoverGroupInner object itself.
     */
    public FailoverGroupInner withReadOnlyEndpoint(FailoverGroupReadOnlyEndpoint readOnlyEndpoint) {
        this.readOnlyEndpoint = readOnlyEndpoint;
        return this;
    }

    /**
     * Get the replicationRole value.
     *
     * @return the replicationRole value.
     */
    public FailoverGroupReplicationRole replicationRole() {
        return this.replicationRole;
    }

    /**
     * Get the replicationState value.
     *
     * @return the replicationState value.
     */
    public String replicationState() {
        return this.replicationState;
    }

    /**
     * Get the partnerServers value.
     *
     * @return the partnerServers value.
     */
    public List<PartnerInfo> partnerServers() {
        return this.partnerServers;
    }

    /**
     * Set the partnerServers value.
     *
     * @param partnerServers the partnerServers value to set.
     * @return the FailoverGroupInner object itself.
     */
    public FailoverGroupInner withPartnerServers(List<PartnerInfo> partnerServers) {
        this.partnerServers = partnerServers;
        return this;
    }

    /**
     * Get the databasesProperty value.
     *
     * @return the databasesProperty value.
     */
    public List<String> databasesProperty() {
        return this.databasesProperty;
    }

    /**
     * Set the databasesProperty value.
     *
     * @param databasesProperty the databasesProperty value to set.
     * @return the FailoverGroupInner object itself.
     */
    public FailoverGroupInner withDatabasesProperty(List<String> databasesProperty) {
        this.databasesProperty = databasesProperty;
        return this;
    }
}
