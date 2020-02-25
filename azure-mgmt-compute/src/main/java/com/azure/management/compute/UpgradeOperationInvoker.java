// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.compute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for UpgradeOperationInvoker.
 */
public enum UpgradeOperationInvoker {
    /**
     * Enum value Unknown.
     */
    UNKNOWN("Unknown"),

    /**
     * Enum value User.
     */
    USER("User"),

    /**
     * Enum value Platform.
     */
    PLATFORM("Platform");

    /**
     * The actual serialized value for a UpgradeOperationInvoker instance.
     */
    private final String value;

    UpgradeOperationInvoker(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a UpgradeOperationInvoker instance.
     * 
     * @param value the serialized value to parse.
     * @return the parsed UpgradeOperationInvoker object, or null if unable to parse.
     */
    @JsonCreator
    public static UpgradeOperationInvoker fromString(String value) {
        UpgradeOperationInvoker[] items = UpgradeOperationInvoker.values();
        for (UpgradeOperationInvoker item : items) {
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
