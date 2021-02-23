/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cdn;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for ResponseBasedDetectedErrorTypes.
 */
public enum ResponseBasedDetectedErrorTypes {
    /** Enum value None. */
    NONE("None"),

    /** Enum value TcpErrorsOnly. */
    TCP_ERRORS_ONLY("TcpErrorsOnly"),

    /** Enum value TcpAndHttpErrors. */
    TCP_AND_HTTP_ERRORS("TcpAndHttpErrors");

    /** The actual serialized value for a ResponseBasedDetectedErrorTypes instance. */
    private String value;

    ResponseBasedDetectedErrorTypes(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a ResponseBasedDetectedErrorTypes instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed ResponseBasedDetectedErrorTypes object, or null if unable to parse.
     */
    @JsonCreator
    public static ResponseBasedDetectedErrorTypes fromString(String value) {
        ResponseBasedDetectedErrorTypes[] items = ResponseBasedDetectedErrorTypes.values();
        for (ResponseBasedDetectedErrorTypes item : items) {
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
