// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.containerregistry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for TrustPolicyType.
 */
public enum TrustPolicyType {
    /**
     * Enum value Notary.
     */
    NOTARY("Notary");

    /**
     * The actual serialized value for a TrustPolicyType instance.
     */
    private final String value;

    TrustPolicyType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a TrustPolicyType instance.
     * 
     * @param value the serialized value to parse.
     * @return the parsed TrustPolicyType object, or null if unable to parse.
     */
    @JsonCreator
    public static TrustPolicyType fromString(String value) {
        TrustPolicyType[] items = TrustPolicyType.values();
        for (TrustPolicyType item : items) {
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
