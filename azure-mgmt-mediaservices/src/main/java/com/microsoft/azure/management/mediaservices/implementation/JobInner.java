/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.mediaservices.implementation;

import org.joda.time.DateTime;
import com.microsoft.azure.management.mediaservices.JobState;
import com.microsoft.azure.management.mediaservices.JobInput;
import java.util.List;
import com.microsoft.azure.management.mediaservices.JobOutput;
import com.microsoft.azure.management.mediaservices.Priority;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * A Job resource type.
 */
@JsonFlatten
public class JobInner extends ProxyResourceInner {
    /**
     * The date and time when the Job was created.
     */
    @JsonProperty(value = "properties.created", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime created;

    /**
     * The current state of the job. Possible values include: 'Canceled',
     * 'Canceling', 'Error', 'Finished', 'Processing', 'Queued', 'Scheduled'.
     */
    @JsonProperty(value = "properties.state", access = JsonProperty.Access.WRITE_ONLY)
    private JobState state;

    /**
     * The customer supplied description of the Job.
     */
    @JsonProperty(value = "properties.description")
    private String description;

    /**
     * The inputs for the Job.
     */
    @JsonProperty(value = "properties.input", required = true)
    private JobInput input;

    /**
     * The date and time when the Job was last updated.
     */
    @JsonProperty(value = "properties.lastModified", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime lastModified;

    /**
     * The outputs for the Job.
     */
    @JsonProperty(value = "properties.outputs", required = true)
    private List<JobOutput> outputs;

    /**
     * Priority with which the job should be processed.  Higher priority jobs
     * are processed before lower priority jobs if there is resource
     * contention. If not set, the default is normal. Possible values include:
     * 'Low', 'Normal', 'High'.
     */
    @JsonProperty(value = "properties.priority")
    private Priority priority;

    /**
     * Get the created value.
     *
     * @return the created value
     */
    public DateTime created() {
        return this.created;
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
     * Get the description value.
     *
     * @return the description value
     */
    public String description() {
        return this.description;
    }

    /**
     * Set the description value.
     *
     * @param description the description value to set
     * @return the JobInner object itself.
     */
    public JobInner withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get the input value.
     *
     * @return the input value
     */
    public JobInput input() {
        return this.input;
    }

    /**
     * Set the input value.
     *
     * @param input the input value to set
     * @return the JobInner object itself.
     */
    public JobInner withInput(JobInput input) {
        this.input = input;
        return this;
    }

    /**
     * Get the lastModified value.
     *
     * @return the lastModified value
     */
    public DateTime lastModified() {
        return this.lastModified;
    }

    /**
     * Get the outputs value.
     *
     * @return the outputs value
     */
    public List<JobOutput> outputs() {
        return this.outputs;
    }

    /**
     * Set the outputs value.
     *
     * @param outputs the outputs value to set
     * @return the JobInner object itself.
     */
    public JobInner withOutputs(List<JobOutput> outputs) {
        this.outputs = outputs;
        return this;
    }

    /**
     * Get the priority value.
     *
     * @return the priority value
     */
    public Priority priority() {
        return this.priority;
    }

    /**
     * Set the priority value.
     *
     * @param priority the priority value to set
     * @return the JobInner object itself.
     */
    public JobInner withPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

}
