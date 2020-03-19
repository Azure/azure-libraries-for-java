// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.monitor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for ScaleDirection.
 */
public enum ScaleDirection {
    /**
     * Enum value None.
     */
    NONE("None"),

    /**
     * Enum value Increase.
     */
    INCREASE("Increase"),

    /**
     * Enum value Decrease.
     */
    DECREASE("Decrease");

    /**
     * The actual serialized value for a ScaleDirection instance.
     */
    private final String value;

    ScaleDirection(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a ScaleDirection instance.
     * 
     * @param value the serialized value to parse.
     * @return the parsed ScaleDirection object, or null if unable to parse.
     */
    @JsonCreator
    public static ScaleDirection fromString(String value) {
        ScaleDirection[] items = ScaleDirection.values();
        for (ScaleDirection item : items) {
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
