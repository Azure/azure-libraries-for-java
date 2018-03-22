/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.datalake.analytics.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

/**
 * A Data Lake Analytics job data path item.
 */
public class JobDataPath {
    /**
     * the id of the job this data is for.
     */
    @JsonProperty(value = "jobId", access = JsonProperty.Access.WRITE_ONLY)
    private UUID jobId;

    /**
     * the command that this job data relates to.
     */
    @JsonProperty(value = "command", access = JsonProperty.Access.WRITE_ONLY)
    private String command;

    /**
     * the list of paths to all of the job data.
     */
    @JsonProperty(value = "paths", access = JsonProperty.Access.WRITE_ONLY)
    private List<String> paths;

    /**
     * Get the jobId value.
     *
     * @return the jobId value
     */
    public UUID jobId() {
        return this.jobId;
    }

    /**
     * Get the command value.
     *
     * @return the command value
     */
    public String command() {
        return this.command;
    }

    /**
     * Get the paths value.
     *
     * @return the paths value
     */
    public List<String> paths() {
        return this.paths;
    }

}
