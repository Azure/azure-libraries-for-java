/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for ServerDisasterRecoveryRole.
 */
public final class ServerDisasterRecoveryRole {
    /** Static value None for ServerDisasterRecoveryRole. */
    public static final ServerDisasterRecoveryRole NONE = new ServerDisasterRecoveryRole("None");

    /** Static value Primary for ServerDisasterRecoveryRole. */
    public static final ServerDisasterRecoveryRole PRIMARY = new ServerDisasterRecoveryRole("Primary");

    /** Static value Secondary for ServerDisasterRecoveryRole. */
    public static final ServerDisasterRecoveryRole SECONDARY = new ServerDisasterRecoveryRole("Secondary");

    private String value;

    /**
     * Creates a custom value for ServerDisasterRecoveryRole.
     * @param value the custom value
     */
    public ServerDisasterRecoveryRole(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ServerDisasterRecoveryRole)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        ServerDisasterRecoveryRole rhs = (ServerDisasterRecoveryRole) obj;
        if (value == null) {
            return rhs.value == null;
        } else {
            return value.equals(rhs.value);
        }
    }
}
