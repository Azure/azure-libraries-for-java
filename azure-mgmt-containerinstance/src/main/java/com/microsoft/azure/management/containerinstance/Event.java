/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.containerinstance;

import org.joda.time.DateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A container group or container instance event.
 */
public class Event {
    /**
     * The count of the event.
     */
    @JsonProperty(value = "count")
    private Integer count;

    /**
     * The date-time of the earliest logged event.
     */
    @JsonProperty(value = "firstTimestamp")
    private DateTime firstTimestamp;

    /**
     * The date-time of the latest logged event.
     */
    @JsonProperty(value = "lastTimestamp")
    private DateTime lastTimestamp;

    /**
     * The event name.
     */
    @JsonProperty(value = "name")
    private String name;

    /**
     * The event message.
     */
    @JsonProperty(value = "message")
    private String message;

    /**
     * The event type.
     */
    @JsonProperty(value = "type")
    private String type;

    /**
     * Get the count value.
     *
     * @return the count value
     */
    public Integer count() {
        return this.count;
    }

    /**
     * Set the count value.
     *
     * @param count the count value to set
     * @return the Event object itself.
     */
    public Event withCount(Integer count) {
        this.count = count;
        return this;
    }

    /**
     * Get the firstTimestamp value.
     *
     * @return the firstTimestamp value
     */
    public DateTime firstTimestamp() {
        return this.firstTimestamp;
    }

    /**
     * Set the firstTimestamp value.
     *
     * @param firstTimestamp the firstTimestamp value to set
     * @return the Event object itself.
     */
    public Event withFirstTimestamp(DateTime firstTimestamp) {
        this.firstTimestamp = firstTimestamp;
        return this;
    }

    /**
     * Get the lastTimestamp value.
     *
     * @return the lastTimestamp value
     */
    public DateTime lastTimestamp() {
        return this.lastTimestamp;
    }

    /**
     * Set the lastTimestamp value.
     *
     * @param lastTimestamp the lastTimestamp value to set
     * @return the Event object itself.
     */
    public Event withLastTimestamp(DateTime lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
        return this;
    }

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
     * @return the Event object itself.
     */
    public Event withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the message value.
     *
     * @return the message value
     */
    public String message() {
        return this.message;
    }

    /**
     * Set the message value.
     *
     * @param message the message value to set
     * @return the Event object itself.
     */
    public Event withMessage(String message) {
        this.message = message;
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
     * @return the Event object itself.
     */
    public Event withType(String type) {
        this.type = type;
        return this;
    }

}
