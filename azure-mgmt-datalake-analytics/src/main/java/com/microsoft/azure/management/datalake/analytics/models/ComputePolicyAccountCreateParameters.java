/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.datalake.analytics.models;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * The parameters used to create a new compute policy.
 */
@JsonFlatten
public class ComputePolicyAccountCreateParameters {
    /**
     * The unique name of the policy to create.
     */
    @JsonProperty(value = "name", required = true)
    private String name;

    /**
     * The AAD object identifier for the entity to create a policy for.
     */
    @JsonProperty(value = "properties.objectId", required = true)
    private UUID objectId;

    /**
     * The type of AAD object the object identifier refers to. Possible values
     * include: 'User', 'Group', 'ServicePrincipal'.
     */
    @JsonProperty(value = "properties.objectType", required = true)
    private AADObjectType objectType;

    /**
     * The maximum degree of parallelism per job this user can use to submit
     * jobs. This property, the min priority per job property, or both must be
     * passed.
     */
    @JsonProperty(value = "properties.maxDegreeOfParallelismPerJob")
    private Integer maxDegreeOfParallelismPerJob;

    /**
     * The minimum priority per job this user can use to submit jobs. This
     * property, the max degree of parallelism per job property, or both must
     * be passed.
     */
    @JsonProperty(value = "properties.minPriorityPerJob")
    private Integer minPriorityPerJob;

    /**
     * Get the name value.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the name value.
     *
     * @param name the name value to set
     * @return the ComputePolicyAccountCreateParameters object itself.
     */
    public ComputePolicyAccountCreateParameters withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the objectId value.
     *
     * @return the objectId value
     */
    public UUID objectId() {
        return this.objectId;
    }

    /**
     * Set the objectId value.
     *
     * @param objectId the objectId value to set
     * @return the ComputePolicyAccountCreateParameters object itself.
     */
    public ComputePolicyAccountCreateParameters withObjectId(UUID objectId) {
        this.objectId = objectId;
        return this;
    }

    /**
     * Get the objectType value.
     *
     * @return the objectType value
     */
    public AADObjectType objectType() {
        return this.objectType;
    }

    /**
     * Set the objectType value.
     *
     * @param objectType the objectType value to set
     * @return the ComputePolicyAccountCreateParameters object itself.
     */
    public ComputePolicyAccountCreateParameters withObjectType(AADObjectType objectType) {
        this.objectType = objectType;
        return this;
    }

    /**
     * Get the maxDegreeOfParallelismPerJob value.
     *
     * @return the maxDegreeOfParallelismPerJob value
     */
    public Integer maxDegreeOfParallelismPerJob() {
        return this.maxDegreeOfParallelismPerJob;
    }

    /**
     * Set the maxDegreeOfParallelismPerJob value.
     *
     * @param maxDegreeOfParallelismPerJob the maxDegreeOfParallelismPerJob value to set
     * @return the ComputePolicyAccountCreateParameters object itself.
     */
    public ComputePolicyAccountCreateParameters withMaxDegreeOfParallelismPerJob(Integer maxDegreeOfParallelismPerJob) {
        this.maxDegreeOfParallelismPerJob = maxDegreeOfParallelismPerJob;
        return this;
    }

    /**
     * Get the minPriorityPerJob value.
     *
     * @return the minPriorityPerJob value
     */
    public Integer minPriorityPerJob() {
        return this.minPriorityPerJob;
    }

    /**
     * Set the minPriorityPerJob value.
     *
     * @param minPriorityPerJob the minPriorityPerJob value to set
     * @return the ComputePolicyAccountCreateParameters object itself.
     */
    public ComputePolicyAccountCreateParameters withMinPriorityPerJob(Integer minPriorityPerJob) {
        this.minPriorityPerJob = minPriorityPerJob;
        return this;
    }

}
