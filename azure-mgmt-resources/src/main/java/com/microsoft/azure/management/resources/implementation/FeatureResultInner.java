/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.resources.implementation;

import com.microsoft.azure.management.resources.FeatureProperties;

/**
 * Previewed feature information.
 */
public class FeatureResultInner {
    /**
     * The name of the feature.
     */
    private String name;

    /**
     * Properties of the previewed feature.
     */
    private FeatureProperties properties;

    /**
     * The resource ID of the feature.
     */
    private String id;

    /**
     * The resource type of the feature.
     */
    private String type;

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
     * @return the FeatureResultInner object itself.
     */
    public FeatureResultInner withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the properties value.
     *
     * @return the properties value
     */
    public FeatureProperties properties() {
        return this.properties;
    }

    /**
     * Set the properties value.
     *
     * @param properties the properties value to set
     * @return the FeatureResultInner object itself.
     */
    public FeatureResultInner withProperties(FeatureProperties properties) {
        this.properties = properties;
        return this;
    }

    /**
     * Get the id value.
     *
     * @return the id value
     */
    public String id() {
        return this.id;
    }

    /**
     * Set the id value.
     *
     * @param id the id value to set
     * @return the FeatureResultInner object itself.
     */
    public FeatureResultInner withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Get the type value.
     *
     * @return the type value
     */
    public String type() {
        return this.type;
    }

    /**
     * Set the type value.
     *
     * @param type the type value to set
     * @return the FeatureResultInner object itself.
     */
    public FeatureResultInner withType(String type) {
        this.type = type;
        return this;
    }

}
