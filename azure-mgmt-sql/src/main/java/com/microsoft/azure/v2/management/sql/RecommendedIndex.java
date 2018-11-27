/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.v2.management.sql;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.v2.serializer.JsonFlatten;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents a database recommended index.
 */
@JsonFlatten
public class RecommendedIndex extends ProxyResource {
    /**
     * The proposed index action. You can create a missing index, drop an
     * unused index, or rebuild an existing index to improve its performance.
     * Possible values include: 'Create', 'Drop', 'Rebuild'.
     */
    @JsonProperty(value = "properties.action", access = JsonProperty.Access.WRITE_ONLY)
    private RecommendedIndexAction action;

    /**
     * The current recommendation state. Possible values include: 'Active',
     * 'Pending', 'Executing', 'Verifying', 'Pending Revert', 'Reverting',
     * 'Reverted', 'Ignored', 'Expired', 'Blocked', 'Success'.
     */
    @JsonProperty(value = "properties.state", access = JsonProperty.Access.WRITE_ONLY)
    private RecommendedIndexState state;

    /**
     * The UTC datetime showing when this resource was created (ISO8601
     * format).
     */
    @JsonProperty(value = "properties.created", access = JsonProperty.Access.WRITE_ONLY)
    private OffsetDateTime created;

    /**
     * The UTC datetime of when was this resource last changed (ISO8601
     * format).
     */
    @JsonProperty(value = "properties.lastModified", access = JsonProperty.Access.WRITE_ONLY)
    private OffsetDateTime lastModified;

    /**
     * The type of index (CLUSTERED, NONCLUSTERED, COLUMNSTORE, CLUSTERED
     * COLUMNSTORE). Possible values include: 'CLUSTERED', 'NONCLUSTERED',
     * 'COLUMNSTORE', 'CLUSTERED COLUMNSTORE'.
     */
    @JsonProperty(value = "properties.indexType", access = JsonProperty.Access.WRITE_ONLY)
    private RecommendedIndexType indexType;

    /**
     * The schema where table to build index over resides.
     */
    @JsonProperty(value = "properties.schema", access = JsonProperty.Access.WRITE_ONLY)
    private String schema;

    /**
     * The table on which to build index.
     */
    @JsonProperty(value = "properties.table", access = JsonProperty.Access.WRITE_ONLY)
    private String table;

    /**
     * Columns over which to build index.
     */
    @JsonProperty(value = "properties.columns", access = JsonProperty.Access.WRITE_ONLY)
    private List<String> columns;

    /**
     * The list of column names to be included in the index.
     */
    @JsonProperty(value = "properties.includedColumns", access = JsonProperty.Access.WRITE_ONLY)
    private List<String> includedColumns;

    /**
     * The full build index script.
     */
    @JsonProperty(value = "properties.indexScript", access = JsonProperty.Access.WRITE_ONLY)
    private String indexScript;

    /**
     * The estimated impact of doing recommended index action.
     */
    @JsonProperty(value = "properties.estimatedImpact", access = JsonProperty.Access.WRITE_ONLY)
    private List<OperationImpact> estimatedImpact;

    /**
     * The values reported after index action is complete.
     */
    @JsonProperty(value = "properties.reportedImpact", access = JsonProperty.Access.WRITE_ONLY)
    private List<OperationImpact> reportedImpact;

    /**
     * Get the action value.
     *
     * @return the action value.
     */
    public RecommendedIndexAction action() {
        return this.action;
    }

    /**
     * Get the state value.
     *
     * @return the state value.
     */
    public RecommendedIndexState state() {
        return this.state;
    }

    /**
     * Get the created value.
     *
     * @return the created value.
     */
    public OffsetDateTime created() {
        return this.created;
    }

    /**
     * Get the lastModified value.
     *
     * @return the lastModified value.
     */
    public OffsetDateTime lastModified() {
        return this.lastModified;
    }

    /**
     * Get the indexType value.
     *
     * @return the indexType value.
     */
    public RecommendedIndexType indexType() {
        return this.indexType;
    }

    /**
     * Get the schema value.
     *
     * @return the schema value.
     */
    public String schema() {
        return this.schema;
    }

    /**
     * Get the table value.
     *
     * @return the table value.
     */
    public String table() {
        return this.table;
    }

    /**
     * Get the columns value.
     *
     * @return the columns value.
     */
    public List<String> columns() {
        return this.columns;
    }

    /**
     * Get the includedColumns value.
     *
     * @return the includedColumns value.
     */
    public List<String> includedColumns() {
        return this.includedColumns;
    }

    /**
     * Get the indexScript value.
     *
     * @return the indexScript value.
     */
    public String indexScript() {
        return this.indexScript;
    }

    /**
     * Get the estimatedImpact value.
     *
     * @return the estimatedImpact value.
     */
    public List<OperationImpact> estimatedImpact() {
        return this.estimatedImpact;
    }

    /**
     * Get the reportedImpact value.
     *
     * @return the reportedImpact value.
     */
    public List<OperationImpact> reportedImpact() {
        return this.reportedImpact;
    }
}
