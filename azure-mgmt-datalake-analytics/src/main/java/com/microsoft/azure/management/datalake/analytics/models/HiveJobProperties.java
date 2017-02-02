/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.datalake.analytics.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Hive job properties used when submitting and retrieving Hive jobs.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("Hive")
public class HiveJobProperties extends JobProperties {
    /**
     * the Hive logs location.
     */
    @JsonProperty(value = "logsLocation", access = JsonProperty.Access.WRITE_ONLY)
    private String logsLocation;

    /**
     * the location of Hive job output files (both execution output and
     * results).
     */
    @JsonProperty(value = "outputLocation", access = JsonProperty.Access.WRITE_ONLY)
    private String outputLocation;

    /**
     * the number of statements that will be run based on the script.
     */
    @JsonProperty(value = "statementCount", access = JsonProperty.Access.WRITE_ONLY)
    private Integer statementCount;

    /**
     * the number of statements that have been run based on the script.
     */
    @JsonProperty(value = "executedStatementCount", access = JsonProperty.Access.WRITE_ONLY)
    private Integer executedStatementCount;

    /**
     * Get the logsLocation value.
     *
     * @return the logsLocation value
     */
    public String logsLocation() {
        return this.logsLocation;
    }

    /**
     * Get the outputLocation value.
     *
     * @return the outputLocation value
     */
    public String outputLocation() {
        return this.outputLocation;
    }

    /**
     * Get the statementCount value.
     *
     * @return the statementCount value
     */
    public Integer statementCount() {
        return this.statementCount;
    }

    /**
     * Get the executedStatementCount value.
     *
     * @return the executedStatementCount value
     */
    public Integer executedStatementCount() {
        return this.executedStatementCount;
    }

}
