/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.redis.implementation;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * Response to put/get patch schedules for Redis cache.
 */
@JsonFlatten
public class RedisPatchScheduleInner extends ProxyResourceInner {
    /**
     * List of patch schedules for a Redis cache.
     */
    @JsonProperty(value = "properties.scheduleEntries", required = true)
    private List<ScheduleEntryInner> scheduleEntries;

    /**
     * Get the scheduleEntries value.
     *
     * @return the scheduleEntries value
     */
    public List<ScheduleEntryInner> scheduleEntries() {
        return this.scheduleEntries;
    }

    /**
     * Set the scheduleEntries value.
     *
     * @param scheduleEntries the scheduleEntries value to set
     * @return the RedisPatchScheduleInner object itself.
     */
    public RedisPatchScheduleInner withScheduleEntries(List<ScheduleEntryInner> scheduleEntries) {
        this.scheduleEntries = scheduleEntries;
        return this;
    }

}
