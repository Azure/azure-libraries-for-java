/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cosmosdb;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Cosmos DB Cassandra keyspace resource object.
 */
public class CassandraKeyspaceResource {
    /**
     * Name of the Cosmos DB Cassandra keyspace.
     */
    @JsonProperty(value = "id", required = true)
    private String id;

    /**
     * Get name of the Cosmos DB Cassandra keyspace.
     *
     * @return the id value
     */
    public String id() {
        return this.id;
    }

    /**
     * Set name of the Cosmos DB Cassandra keyspace.
     *
     * @param id the id value to set
     * @return the CassandraKeyspaceResource object itself.
     */
    public CassandraKeyspaceResource withId(String id) {
        this.id = id;
        return this;
    }

}
