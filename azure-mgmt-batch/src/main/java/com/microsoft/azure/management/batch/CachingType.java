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
 * Defines values for CachingType.
 */
public enum CachingType {
    /** The caching mode for the disk is not enabled. */
    NONE("None"),

    /** The caching mode for the disk is read only. */
    READ_ONLY("ReadOnly"),

    /** The caching mode for the disk is read and write. */
    READ_WRITE("ReadWrite");

    /** The actual serialized value for a CachingType instance. */
    private String value;

    CachingType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a CachingType instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed CachingType object, or null if unable to parse.
     */
    @JsonCreator
    public static CachingType fromString(String value) {
        CachingType[] items = CachingType.values();
        for (CachingType item : items) {
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
