/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for PerformanceLevelUnit.
 */
public enum PerformanceLevelUnit {
    /** Enum value DTU. */
    DTU("DTU");

    /** The actual serialized value for a PerformanceLevelUnit instance. */
    private String value;

    PerformanceLevelUnit(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a PerformanceLevelUnit instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed PerformanceLevelUnit object, or null if unable to parse.
     */
    @JsonCreator
    public static PerformanceLevelUnit fromString(String value) {
        PerformanceLevelUnit[] items = PerformanceLevelUnit.values();
        for (PerformanceLevelUnit item : items) {
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
