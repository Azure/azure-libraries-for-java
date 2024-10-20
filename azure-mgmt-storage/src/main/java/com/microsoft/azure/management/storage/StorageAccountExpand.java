/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for StorageAccountExpand.
 */
public enum StorageAccountExpand {
    /** Enum value geoReplicationStats. */
    GEO_REPLICATION_STATS("geoReplicationStats"),

    /** Enum value blobRestoreStatus. */
    BLOB_RESTORE_STATUS("blobRestoreStatus");

    /** The actual serialized value for a StorageAccountExpand instance. */
    private String value;

    StorageAccountExpand(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a StorageAccountExpand instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed StorageAccountExpand object, or null if unable to parse.
     */
    @JsonCreator
    public static StorageAccountExpand fromString(String value) {
        StorageAccountExpand[] items = StorageAccountExpand.values();
        for (StorageAccountExpand item : items) {
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
