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
 * The system generated resource properties associated with SQL databases and
 * SQL containers.
 */
public class ExtendedResourceProperties {
    /**
     * A system generated property. A unique identifier.
     */
    @JsonProperty(value = "rid")
    private String rid;

    /**
     * A system generated property that denotes the last updated timestamp of
     * the resource.
     */
    @JsonProperty(value = "ts")
    private Object ts;

    /**
     * A system generated property representing the resource etag required for
     * optimistic concurrency control.
     */
    @JsonProperty(value = "etag")
    private String etag;

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
     * @return the ExtendedResourceProperties object itself.
     */
    public ExtendedResourceProperties withRid(String rid) {
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
     * @return the ExtendedResourceProperties object itself.
     */
    public ExtendedResourceProperties withTs(Object ts) {
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
     * @return the ExtendedResourceProperties object itself.
     */
    public ExtendedResourceProperties withEtag(String etag) {
        this.etag = etag;
        return this;
    }

}
