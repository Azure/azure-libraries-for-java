/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cosmosdb.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.rest.SkipParentValidation;
import com.microsoft.azure.Resource;

/**
 * An Azure Cosmos DB Gremlin database.
 */
@JsonFlatten
@SkipParentValidation
public class GremlinDatabaseInner extends Resource {
    /**
     * Name of the Cosmos DB Gremlin database.
     */
    @JsonProperty(value = "properties.id", required = true)
    private String gremlinDatabaseId;

    /**
     * A system generated property. A unique identifier.
     */
    @JsonProperty(value = "properties.rid")
    private String rid;

    /**
     * A system generated property that denotes the last updated timestamp of
     * the resource.
     */
    @JsonProperty(value = "properties.ts")
    private Object ts;

    /**
     * A system generated property representing the resource etag required for
     * optimistic concurrency control.
     */
    @JsonProperty(value = "properties.etag")
    private String etag;

    /**
     * Get name of the Cosmos DB Gremlin database.
     *
     * @return the gremlinDatabaseId value
     */
    public String gremlinDatabaseId() {
        return this.gremlinDatabaseId;
    }

    /**
     * Set name of the Cosmos DB Gremlin database.
     *
     * @param gremlinDatabaseId the gremlinDatabaseId value to set
     * @return the GremlinDatabaseInner object itself.
     */
    public GremlinDatabaseInner withGremlinDatabaseId(String gremlinDatabaseId) {
        this.gremlinDatabaseId = gremlinDatabaseId;
        return this;
    }

    /**
     * Get a system generated property. A unique identifier.
     *
     * @return the rid value
     */
    public String rid() {
        return this.rid;
    }

    /**
     * Set a system generated property. A unique identifier.
     *
     * @param rid the rid value to set
     * @return the GremlinDatabaseInner object itself.
     */
    public GremlinDatabaseInner withRid(String rid) {
        this.rid = rid;
        return this;
    }

    /**
     * Get a system generated property that denotes the last updated timestamp of the resource.
     *
     * @return the ts value
     */
    public Object ts() {
        return this.ts;
    }

    /**
     * Set a system generated property that denotes the last updated timestamp of the resource.
     *
     * @param ts the ts value to set
     * @return the GremlinDatabaseInner object itself.
     */
    public GremlinDatabaseInner withTs(Object ts) {
        this.ts = ts;
        return this;
    }

    /**
     * Get a system generated property representing the resource etag required for optimistic concurrency control.
     *
     * @return the etag value
     */
    public String etag() {
        return this.etag;
    }

    /**
     * Set a system generated property representing the resource etag required for optimistic concurrency control.
     *
     * @param etag the etag value to set
     * @return the GremlinDatabaseInner object itself.
     */
    public GremlinDatabaseInner withEtag(String etag) {
        this.etag = etag;
        return this;
    }

}
