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
 * Defines values for StreamingLocatorContentKeyType.
 */
public enum StreamingLocatorContentKeyType {
    /** Enum value CommonEncryptionCenc. */
    COMMON_ENCRYPTION_CENC("CommonEncryptionCenc"),

    /** Enum value CommonEncryptionCbcs. */
    COMMON_ENCRYPTION_CBCS("CommonEncryptionCbcs"),

    /** Enum value EnvelopeEncryption. */
    ENVELOPE_ENCRYPTION("EnvelopeEncryption");

    /** The actual serialized value for a StreamingLocatorContentKeyType instance. */
    private String value;

    StreamingLocatorContentKeyType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a StreamingLocatorContentKeyType instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed StreamingLocatorContentKeyType object, or null if unable to parse.
     */
    @JsonCreator
    public static StreamingLocatorContentKeyType fromString(String value) {
        StreamingLocatorContentKeyType[] items = StreamingLocatorContentKeyType.values();
        for (StreamingLocatorContentKeyType item : items) {
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
