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
 * Defines values for DiffDiskPlacement.
 */
public enum DiffDiskPlacement {
    /** The Ephemeral OS Disk is stored on the VM cache. */
    CACHE_DISK("CacheDisk");

    /** The actual serialized value for a DiffDiskPlacement instance. */
    private String value;

    DiffDiskPlacement(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a DiffDiskPlacement instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed DiffDiskPlacement object, or null if unable to parse.
     */
    @JsonCreator
    public static DiffDiskPlacement fromString(String value) {
        DiffDiskPlacement[] items = DiffDiskPlacement.values();
        for (DiffDiskPlacement item : items) {
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
