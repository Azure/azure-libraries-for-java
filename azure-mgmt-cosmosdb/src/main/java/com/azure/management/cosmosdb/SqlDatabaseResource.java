/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.azure.management.cosmosdb;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Cosmos DB SQL database resource object.
 */
public class SqlDatabaseResource {
    /**
     * Name of the Cosmos DB SQL database.
     */
    @JsonProperty(value = "id", required = true)
    private String id;

    /**
     * Get name of the Cosmos DB SQL database.
     *
     * @return the id value
     */
    public String id() {
        return this.id;
    }

    /**
     * Set name of the Cosmos DB SQL database.
     *
     * @param id the id value to set
     * @return the SqlDatabaseResource object itself.
     */
    public SqlDatabaseResource withId(String id) {
        this.id = id;
        return this;
    }

}
