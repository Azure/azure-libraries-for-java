/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.datafactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * A copy activity source for Amazon Redshift Source.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("AmazonRedshiftSource")
public class AmazonRedshiftSource extends CopySource {
    /**
     * Database query. Type: string (or Expression with resultType string).
     */
    @JsonProperty(value = "query")
    private Object query;

    /**
     * The Amazon S3 settings needed for the interim Amazon S3 when copying
     * from Amazon Redshift with unload. With this, data from Amazon Redshift
     * source will be unloaded into S3 first and then copied into the targeted
     * sink from the interim S3.
     */
    @JsonProperty(value = "redshiftUnloadSettings")
    private RedshiftUnloadSettings redshiftUnloadSettings;

    /**
     * Get the query value.
     *
     * @return the query value
     */
    public Object query() {
        return this.query;
    }

    /**
     * Set the query value.
     *
     * @param query the query value to set
     * @return the AmazonRedshiftSource object itself.
     */
    public AmazonRedshiftSource withQuery(Object query) {
        this.query = query;
        return this;
    }

    /**
     * Get the redshiftUnloadSettings value.
     *
     * @return the redshiftUnloadSettings value
     */
    public RedshiftUnloadSettings redshiftUnloadSettings() {
        return this.redshiftUnloadSettings;
    }

    /**
     * Set the redshiftUnloadSettings value.
     *
     * @param redshiftUnloadSettings the redshiftUnloadSettings value to set
     * @return the AmazonRedshiftSource object itself.
     */
    public AmazonRedshiftSource withRedshiftUnloadSettings(RedshiftUnloadSettings redshiftUnloadSettings) {
        this.redshiftUnloadSettings = redshiftUnloadSettings;
        return this;
    }

}
