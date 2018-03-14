/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.web;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Information needed to create resources on an App Service Environment.
 */
public class HostingEnvironmentDeploymentInfo {
    /**
     * Name of the App Service Environment.
     */
    @JsonProperty(value = "name")
    private String name;

    /**
     * Location of the App Service Environment.
     */
    @JsonProperty(value = "location")
    private String location;

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
     * @return the HostingEnvironmentDeploymentInfo object itself.
     */
    public HostingEnvironmentDeploymentInfo withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the location value.
     *
     * @return the location value
     */
    public String location() {
        return this.location;
    }

    /**
     * Set the location value.
     *
     * @param location the location value to set
     * @return the HostingEnvironmentDeploymentInfo object itself.
     */
    public HostingEnvironmentDeploymentInfo withLocation(String location) {
        this.location = location;
        return this;
    }

}
