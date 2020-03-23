/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.azure.management.containerregistry;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Properties that describe a base image dependency.
 */
public class BaseImageDependency {
    /**
     * The type of the base image dependency. Possible values include:
     * 'BuildTime', 'RunTime'.
     */
    @JsonProperty(value = "type")
    private BaseImageDependencyType type;

    /**
     * The registry login server.
     */
    @JsonProperty(value = "registry")
    private String registry;

    /**
     * The repository name.
     */
    @JsonProperty(value = "repository")
    private String repository;

    /**
     * The tag name.
     */
    @JsonProperty(value = "tag")
    private String tag;

    /**
     * The sha256-based digest of the image manifest.
     */
    @JsonProperty(value = "digest")
    private String digest;

    /**
     * Get the type of the base image dependency. Possible values include: 'BuildTime', 'RunTime'.
     *
     * @return the type value
     */
    public BaseImageDependencyType type() {
        return this.type;
    }

    /**
     * Set the type of the base image dependency. Possible values include: 'BuildTime', 'RunTime'.
     *
     * @param type the type value to set
     * @return the BaseImageDependency object itself.
     */
    public BaseImageDependency withType(BaseImageDependencyType type) {
        this.type = type;
        return this;
    }

    /**
     * Get the registry login server.
     *
     * @return the registry value
     */
    public String registry() {
        return this.registry;
    }

    /**
     * Set the registry login server.
     *
     * @param registry the registry value to set
     * @return the BaseImageDependency object itself.
     */
    public BaseImageDependency withRegistry(String registry) {
        this.registry = registry;
        return this;
    }

    /**
     * Get the repository name.
     *
     * @return the repository value
     */
    public String repository() {
        return this.repository;
    }

    /**
     * Set the repository name.
     *
     * @param repository the repository value to set
     * @return the BaseImageDependency object itself.
     */
    public BaseImageDependency withRepository(String repository) {
        this.repository = repository;
        return this;
    }

    /**
     * Get the tag name.
     *
     * @return the tag value
     */
    public String tag() {
        return this.tag;
    }

    /**
     * Set the tag name.
     *
     * @param tag the tag value to set
     * @return the BaseImageDependency object itself.
     */
    public BaseImageDependency withTag(String tag) {
        this.tag = tag;
        return this;
    }

    /**
     * Get the sha256-based digest of the image manifest.
     *
     * @return the digest value
     */
    public String digest() {
        return this.digest;
    }

    /**
     * Set the sha256-based digest of the image manifest.
     *
     * @param digest the digest value to set
     * @return the BaseImageDependency object itself.
     */
    public BaseImageDependency withDigest(String digest) {
        this.digest = digest;
        return this;
    }

}
