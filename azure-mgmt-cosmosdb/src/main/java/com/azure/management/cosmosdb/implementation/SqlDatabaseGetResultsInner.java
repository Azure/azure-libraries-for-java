/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.azure.management.cosmosdb.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.azure.management.cosmosdb.ARMResourceProperties;

/**
 * An Azure Cosmos DB SQL database.
 */
@JsonFlatten
public class SqlDatabaseGetResultsInner extends ARMResourceProperties {
    /**
     * Name of the Cosmos DB SQL database.
     */
    @JsonProperty(value = "properties.id", required = true)
    private String sqlDatabaseGetResultsId;

    /**
     * A system generated property. A unique identifier.
     */
    @JsonProperty(value = "properties._rid", access = JsonProperty.Access.WRITE_ONLY)
    private String _rid;

    /**
     * A system generated property that denotes the last updated timestamp of
     * the resource.
     */
    @JsonProperty(value = "properties._ts", access = JsonProperty.Access.WRITE_ONLY)
    private Object _ts;

    /**
     * A system generated property representing the resource etag required for
     * optimistic concurrency control.
     */
    @JsonProperty(value = "properties._etag", access = JsonProperty.Access.WRITE_ONLY)
    private String _etag;

    /**
     * A system generated property that specified the addressable path of the
     * collections resource.
     */
    @JsonProperty(value = "properties._colls")
    private String _colls;

    /**
     * A system generated property that specifies the addressable path of the
     * users resource.
     */
    @JsonProperty(value = "properties._users")
    private String _users;

    /**
     * Get name of the Cosmos DB SQL database.
     *
     * @return the sqlDatabaseGetResultsId value
     */
    public String sqlDatabaseGetResultsId() {
        return this.sqlDatabaseGetResultsId;
    }

    /**
     * Set name of the Cosmos DB SQL database.
     *
     * @param sqlDatabaseGetResultsId the sqlDatabaseGetResultsId value to set
     * @return the SqlDatabaseGetResultsInner object itself.
     */
    public SqlDatabaseGetResultsInner withSqlDatabaseGetResultsId(String sqlDatabaseGetResultsId) {
        this.sqlDatabaseGetResultsId = sqlDatabaseGetResultsId;
        return this;
    }

    /**
     * Get a system generated property. A unique identifier.
     *
     * @return the _rid value
     */
    public String _rid() {
        return this._rid;
    }

    /**
     * Get a system generated property that denotes the last updated timestamp of the resource.
     *
     * @return the _ts value
     */
    public Object _ts() {
        return this._ts;
    }

    /**
     * Get a system generated property representing the resource etag required for optimistic concurrency control.
     *
     * @return the _etag value
     */
    public String _etag() {
        return this._etag;
    }

    /**
     * Get a system generated property that specified the addressable path of the collections resource.
     *
     * @return the _colls value
     */
    public String _colls() {
        return this._colls;
    }

    /**
     * Set a system generated property that specified the addressable path of the collections resource.
     *
     * @param _colls the _colls value to set
     * @return the SqlDatabaseGetResultsInner object itself.
     */
    public SqlDatabaseGetResultsInner with_colls(String _colls) {
        this._colls = _colls;
        return this;
    }

    /**
     * Get a system generated property that specifies the addressable path of the users resource.
     *
     * @return the _users value
     */
    public String _users() {
        return this._users;
    }

    /**
     * Set a system generated property that specifies the addressable path of the users resource.
     *
     * @param _users the _users value to set
     * @return the SqlDatabaseGetResultsInner object itself.
     */
    public SqlDatabaseGetResultsInner with_users(String _users) {
        this._users = _users;
        return this;
    }

}
