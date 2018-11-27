/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.v2.management.sql;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for RecommendedIndexState.
 */
public enum RecommendedIndexState {
    /**
     * Enum value Active.
     */
    ACTIVE("Active"),

    /**
     * Enum value Pending.
     */
    PENDING("Pending"),

    /**
     * Enum value Executing.
     */
    EXECUTING("Executing"),

    /**
     * Enum value Verifying.
     */
    VERIFYING("Verifying"),

    /**
     * Enum value Pending Revert.
     */
    PENDING_REVERT("Pending Revert"),

    /**
     * Enum value Reverting.
     */
    REVERTING("Reverting"),

    /**
     * Enum value Reverted.
     */
    REVERTED("Reverted"),

    /**
     * Enum value Ignored.
     */
    IGNORED("Ignored"),

    /**
     * Enum value Expired.
     */
    EXPIRED("Expired"),

    /**
     * Enum value Blocked.
     */
    BLOCKED("Blocked"),

    /**
     * Enum value Success.
     */
    SUCCESS("Success");

    /**
     * The actual serialized value for a RecommendedIndexState instance.
     */
    private final String value;

    private RecommendedIndexState(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a RecommendedIndexState instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed RecommendedIndexState object, or null if unable to parse.
     */
    @JsonCreator
    public static RecommendedIndexState fromString(String value) {
        RecommendedIndexState[] items = RecommendedIndexState.values();
        for (RecommendedIndexState item : items) {
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
