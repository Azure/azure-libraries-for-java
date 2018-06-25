/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.containerregistry;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * The parameters for updating a build task.
 */
@JsonFlatten
public class BuildTaskUpdateParameters {
    /**
     * The alternative updatable name for a build task.
     */
    @JsonProperty(value = "properties.alias")
    private String alias;

    /**
     * The current status of build task. Possible values include: 'Disabled',
     * 'Enabled'.
     */
    @JsonProperty(value = "properties.status")
    private BuildTaskStatus status;

    /**
     * The platform properties against which the build has to happen.
     */
    @JsonProperty(value = "properties.platform")
    private PlatformProperties platform;

    /**
     * Build timeout in seconds.
     */
    @JsonProperty(value = "properties.timeout")
    private Integer timeout;

    /**
     * The properties that describes the source(code) for the build task.
     */
    @JsonProperty(value = "properties.sourceRepository")
    private SourceRepositoryUpdateParameters sourceRepository;

    /**
     * The ARM resource tags.
     */
    @JsonProperty(value = "tags")
    private Map<String, String> tags;

    /**
     * Get the alternative updatable name for a build task.
     *
     * @return the alias value
     */
    public String alias() {
        return this.alias;
    }

    /**
     * Set the alternative updatable name for a build task.
     *
     * @param alias the alias value to set
     * @return the BuildTaskUpdateParameters object itself.
     */
    public BuildTaskUpdateParameters withAlias(String alias) {
        this.alias = alias;
        return this;
    }

    /**
     * Get the current status of build task. Possible values include: 'Disabled', 'Enabled'.
     *
     * @return the status value
     */
    public BuildTaskStatus status() {
        return this.status;
    }

    /**
     * Set the current status of build task. Possible values include: 'Disabled', 'Enabled'.
     *
     * @param status the status value to set
     * @return the BuildTaskUpdateParameters object itself.
     */
    public BuildTaskUpdateParameters withStatus(BuildTaskStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Get the platform properties against which the build has to happen.
     *
     * @return the platform value
     */
    public PlatformProperties platform() {
        return this.platform;
    }

    /**
     * Set the platform properties against which the build has to happen.
     *
     * @param platform the platform value to set
     * @return the BuildTaskUpdateParameters object itself.
     */
    public BuildTaskUpdateParameters withPlatform(PlatformProperties platform) {
        this.platform = platform;
        return this;
    }

    /**
     * Get build timeout in seconds.
     *
     * @return the timeout value
     */
    public Integer timeout() {
        return this.timeout;
    }

    /**
     * Set build timeout in seconds.
     *
     * @param timeout the timeout value to set
     * @return the BuildTaskUpdateParameters object itself.
     */
    public BuildTaskUpdateParameters withTimeout(Integer timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * Get the properties that describes the source(code) for the build task.
     *
     * @return the sourceRepository value
     */
    public SourceRepositoryUpdateParameters sourceRepository() {
        return this.sourceRepository;
    }

    /**
     * Set the properties that describes the source(code) for the build task.
     *
     * @param sourceRepository the sourceRepository value to set
     * @return the BuildTaskUpdateParameters object itself.
     */
    public BuildTaskUpdateParameters withSourceRepository(SourceRepositoryUpdateParameters sourceRepository) {
        this.sourceRepository = sourceRepository;
        return this;
    }

    /**
     * Get the ARM resource tags.
     *
     * @return the tags value
     */
    public Map<String, String> tags() {
        return this.tags;
    }

    /**
     * Set the ARM resource tags.
     *
     * @param tags the tags value to set
     * @return the BuildTaskUpdateParameters object itself.
     */
    public BuildTaskUpdateParameters withTags(Map<String, String> tags) {
        this.tags = tags;
        return this;
    }

}
