/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cdn;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for CustomDomainResourceState.
 */
public final class CustomDomainResourceState {
    /** Static value Creating for CustomDomainResourceState. */
    public static final CustomDomainResourceState CREATING = new CustomDomainResourceState("Creating");

    /** Static value Active for CustomDomainResourceState. */
    public static final CustomDomainResourceState ACTIVE = new CustomDomainResourceState("Active");

    /** Static value Deleting for CustomDomainResourceState. */
    public static final CustomDomainResourceState DELETING = new CustomDomainResourceState("Deleting");

    private String value;

    /**
     * Creates a custom value for CustomDomainResourceState.
     * @param value the custom value
     */
    public CustomDomainResourceState(String value) {
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
        if (!(obj instanceof CustomDomainResourceState)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        CustomDomainResourceState rhs = (CustomDomainResourceState) obj;
        if (value == null) {
            return rhs.value == null;
        } else {
            return value.equals(rhs.value);
        }
    }
}
