/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.recoveryservices;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for VaultUpgradeState.
 */
public final class VaultUpgradeState {
    /** Static value Unknown for VaultUpgradeState. */
    public static final VaultUpgradeState UNKNOWN = new VaultUpgradeState("Unknown");

    /** Static value InProgress for VaultUpgradeState. */
    public static final VaultUpgradeState IN_PROGRESS = new VaultUpgradeState("InProgress");

    /** Static value Upgraded for VaultUpgradeState. */
    public static final VaultUpgradeState UPGRADED = new VaultUpgradeState("Upgraded");

    /** Static value Failed for VaultUpgradeState. */
    public static final VaultUpgradeState FAILED = new VaultUpgradeState("Failed");

    private String value;

    /**
     * Creates a custom value for VaultUpgradeState.
     * @param value the custom value
     */
    public VaultUpgradeState(String value) {
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
        if (!(obj instanceof VaultUpgradeState)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        VaultUpgradeState rhs = (VaultUpgradeState) obj;
        if (value == null) {
            return rhs.value == null;
        } else {
            return value.equals(rhs.value);
        }
    }
}
