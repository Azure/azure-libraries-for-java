/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.mediaservices;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for TrackPropertyType.
 */
public enum TrackPropertyType {
    /** Enum value Unknown. */
    UNKNOWN("Unknown"),

    /** Enum value FourCC. */
    FOUR_CC("FourCC");

    /** The actual serialized value for a TrackPropertyType instance. */
    private String value;

    TrackPropertyType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a TrackPropertyType instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed TrackPropertyType object, or null if unable to parse.
     */
    @JsonCreator
    public static TrackPropertyType fromString(String value) {
        TrackPropertyType[] items = TrackPropertyType.values();
        for (TrackPropertyType item : items) {
            if (item.toString().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
