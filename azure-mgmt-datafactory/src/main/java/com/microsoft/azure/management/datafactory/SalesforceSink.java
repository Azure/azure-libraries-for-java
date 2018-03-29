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
 * A copy activity Salesforce sink.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("SalesforceSink")
public class SalesforceSink extends CopySink {
    /**
     * The write behavior for the operation. Default is Insert. Possible values
     * include: 'Insert', 'Upsert'.
     */
    @JsonProperty(value = "writeBehavior")
    private SalesforceSinkWriteBehavior writeBehavior;

    /**
     * The name of the external ID field for upsert operation. Default value is
     * 'Id' column. Type: string (or Expression with resultType string).
     */
    @JsonProperty(value = "externalIdFieldName")
    private Object externalIdFieldName;

    /**
     * The flag indicating whether or not to ignore null values from input
     * dataset (except key fields) during write operation. Default value is
     * false. If set it to true, it means ADF will leave the data in the
     * destination object unchanged when doing upsert/update operation and
     * insert defined default value when doing insert operation, versus ADF
     * will update the data in the destination object to NULL when doing
     * upsert/update operation and insert NULL value when doing insert
     * operation. Type: boolean (or Expression with resultType boolean).
     */
    @JsonProperty(value = "ignoreNullValues")
    private Object ignoreNullValues;

    /**
     * Get the writeBehavior value.
     *
     * @return the writeBehavior value
     */
    public SalesforceSinkWriteBehavior writeBehavior() {
        return this.writeBehavior;
    }

    /**
     * Set the writeBehavior value.
     *
     * @param writeBehavior the writeBehavior value to set
     * @return the SalesforceSink object itself.
     */
    public SalesforceSink withWriteBehavior(SalesforceSinkWriteBehavior writeBehavior) {
        this.writeBehavior = writeBehavior;
        return this;
    }

    /**
     * Get the externalIdFieldName value.
     *
     * @return the externalIdFieldName value
     */
    public Object externalIdFieldName() {
        return this.externalIdFieldName;
    }

    /**
     * Set the externalIdFieldName value.
     *
     * @param externalIdFieldName the externalIdFieldName value to set
     * @return the SalesforceSink object itself.
     */
    public SalesforceSink withExternalIdFieldName(Object externalIdFieldName) {
        this.externalIdFieldName = externalIdFieldName;
        return this;
    }

    /**
     * Get the ignoreNullValues value.
     *
     * @return the ignoreNullValues value
     */
    public Object ignoreNullValues() {
        return this.ignoreNullValues;
    }

    /**
     * Set the ignoreNullValues value.
     *
     * @param ignoreNullValues the ignoreNullValues value to set
     * @return the SalesforceSink object itself.
     */
    public SalesforceSink withIgnoreNullValues(Object ignoreNullValues) {
        this.ignoreNullValues = ignoreNullValues;
        return this;
    }

}
