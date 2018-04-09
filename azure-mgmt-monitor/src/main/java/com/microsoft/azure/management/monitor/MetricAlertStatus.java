/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.monitor;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An alert status.
 */
public class MetricAlertStatus {
    /**
     * The status name.
     */
    @JsonProperty(value = "name")
    private String name;

    /**
     * The alert rule arm id.
     */
    @JsonProperty(value = "id")
    private String id;

    /**
     * The extended resource type name.
     */
    @JsonProperty(value = "type")
    private String type;

    /**
     * The alert status properties of the metric alert status.
     */
    @JsonProperty(value = "properties")
    private MetricAlertStatusProperties properties;

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
     * @return the MetricAlertStatus object itself.
     */
    public MetricAlertStatus withName(String name) {
        this.name = name;
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
     * @return the MetricAlertStatus object itself.
     */
    public MetricAlertStatus withId(String id) {
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
     * @return the MetricAlertStatus object itself.
     */
    public MetricAlertStatus withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get the properties value.
     *
     * @return the properties value
     */
    public MetricAlertStatusProperties properties() {
        return this.properties;
    }

    /**
     * Set the properties value.
     *
     * @param properties the properties value to set
     * @return the MetricAlertStatus object itself.
     */
    public MetricAlertStatus withProperties(MetricAlertStatusProperties properties) {
        this.properties = properties;
        return this;
    }

}
