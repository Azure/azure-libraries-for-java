/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for StatusOptions.
 */
public enum StatusOptions {
    /** Enum value Ready. */
    READY("Ready"),

    /** Enum value Pending. */
    PENDING("Pending"),

    /** Enum value Creating. */
    CREATING("Creating");

    /** The actual serialized value for a StatusOptions instance. */
    private String value;

    StatusOptions(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a StatusOptions instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed StatusOptions object, or null if unable to parse.
     */
    @JsonCreator
    public static StatusOptions fromString(String value) {
        StatusOptions[] items = StatusOptions.values();
        for (StatusOptions item : items) {
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
