/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.gallery;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for ScaleType.
 */
public enum ScaleType {
    /** Enum value ChangeCount. */
    CHANGE_COUNT("ChangeCount"),

    /** Enum value PercentChangeCount. */
    PERCENT_CHANGE_COUNT("PercentChangeCount"),

    /** Enum value ExactCount. */
    EXACT_COUNT("ExactCount");

    /** The actual serialized value for a ScaleType instance. */
    private String value;

    ScaleType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a ScaleType instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed ScaleType object, or null if unable to parse.
     */
    @JsonCreator
    public static ScaleType fromString(String value) {
        ScaleType[] items = ScaleType.values();
        for (ScaleType item : items) {
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
