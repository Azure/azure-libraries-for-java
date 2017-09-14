/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.datalake.analytics.models;

import java.util.UUID;
import org.joda.time.DateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Run info for a specific job pipeline.
 */
public class JobPipelineRunInformation {
    /**
     * the run identifier of an instance of pipeline executions (a GUID).
     */
    @JsonProperty(value = "runId", access = JsonProperty.Access.WRITE_ONLY)
    private UUID runId;

    /**
     * the time this instance was last submitted.
     */
    @JsonProperty(value = "lastSubmitTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime lastSubmitTime;

    /**
     * Get the runId value.
     *
     * @return the runId value
     */
    public UUID runId() {
        return this.runId;
    }

    /**
     * Get the lastSubmitTime value.
     *
     * @return the lastSubmitTime value
     */
    public DateTime lastSubmitTime() {
        return this.lastSubmitTime;
    }

}
