/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.mediaservices;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonSubTypes;

/**
 * Base class for the output of a Job.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@odata.type")
@JsonTypeName("JobOutput")
@JsonSubTypes({
    @JsonSubTypes.Type(name = "#Microsoft.Media.JobOutputAsset", value = JobOutputAsset.class)
})
public class JobOutput {
    /**
     * If the JobOutput is in the error state, it contains the details of the
     * error.
     */
    @JsonProperty(value = "error", access = JsonProperty.Access.WRITE_ONLY)
    private JobError error;

    /**
     * State of the JobOutput. Possible values include: 'Canceled',
     * 'Canceling', 'Error', 'Finished', 'Processing', 'Queued', 'Scheduled'.
     */
    @JsonProperty(value = "state", access = JsonProperty.Access.WRITE_ONLY)
    private JobState state;

    /**
     * If the JobOutput is in the processing state, it contains the percentage
     * of the job completed from 0 to 100 percent.
     */
    @JsonProperty(value = "progress", access = JsonProperty.Access.WRITE_ONLY)
    private int progress;

    /**
     * Get the error value.
     *
     * @return the error value
     */
    public JobError error() {
        return this.error;
    }

    /**
     * Get the state value.
     *
     * @return the state value
     */
    public JobState state() {
        return this.state;
    }

    /**
     * Get the progress value.
     *
     * @return the progress value
     */
    public int progress() {
        return this.progress;
    }

}
