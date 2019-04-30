/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.batch;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for ElevationLevel.
 */
public enum ElevationLevel {
    /** Enum value NonAdmin. */
    NON_ADMIN("NonAdmin"),

    /** Enum value Admin. */
    ADMIN("Admin");

    /** The actual serialized value for a ElevationLevel instance. */
    private String value;

    ElevationLevel(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a ElevationLevel instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed ElevationLevel object, or null if unable to parse.
     */
    @JsonCreator
    public static ElevationLevel fromString(String value) {
        ElevationLevel[] items = ElevationLevel.values();
        for (ElevationLevel item : items) {
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
