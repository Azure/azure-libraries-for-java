/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.sql.RestorePointTypes;
import org.joda.time.DateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * Database restore points.
 */
@JsonFlatten
public class RestorePointInner extends ProxyResourceInner {
    /**
     * Resource location.
     */
    @JsonProperty(value = "location", access = JsonProperty.Access.WRITE_ONLY)
    private String location;

    /**
     * The type of restore point. Possible values include: 'CONTINUOUS',
     * 'DISCRETE'.
     */
    @JsonProperty(value = "properties.restorePointType", access = JsonProperty.Access.WRITE_ONLY)
    private RestorePointTypes restorePointType;

    /**
     * The earliest time to which this database can be restored.
     */
    @JsonProperty(value = "properties.earliestRestoreDate", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime earliestRestoreDate;

    /**
     * The time the backup was taken.
     */
    @JsonProperty(value = "properties.restorePointCreationDate", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime restorePointCreationDate;

    /**
     * The label of restore point for backup request by user.
     */
    @JsonProperty(value = "properties.restorePointLabel", access = JsonProperty.Access.WRITE_ONLY)
    private String restorePointLabel;

    /**
     * Get the location value.
     *
     * @return the location value
     */
    public String location() {
        return this.location;
    }

    /**
     * Get the restorePointType value.
     *
     * @return the restorePointType value
     */
    public RestorePointTypes restorePointType() {
        return this.restorePointType;
    }

    /**
     * Get the earliestRestoreDate value.
     *
     * @return the earliestRestoreDate value
     */
    public DateTime earliestRestoreDate() {
        return this.earliestRestoreDate;
    }

    /**
     * Get the restorePointCreationDate value.
     *
     * @return the restorePointCreationDate value
     */
    public DateTime restorePointCreationDate() {
        return this.restorePointCreationDate;
    }

    /**
     * Get the restorePointLabel value.
     *
     * @return the restorePointLabel value
     */
    public String restorePointLabel() {
        return this.restorePointLabel;
    }

}
