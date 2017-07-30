/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.machinelearning;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for ParameterType.
 */
public final class ParameterType {
    /** Static value String for ParameterType. */
    public static final ParameterType STRING = new ParameterType("String");

    /** Static value Int for ParameterType. */
    public static final ParameterType INT = new ParameterType("Int");

    /** Static value Float for ParameterType. */
    public static final ParameterType FLOAT = new ParameterType("Float");

    /** Static value Enumerated for ParameterType. */
    public static final ParameterType ENUMERATED = new ParameterType("Enumerated");

    /** Static value Script for ParameterType. */
    public static final ParameterType SCRIPT = new ParameterType("Script");

    /** Static value Mode for ParameterType. */
    public static final ParameterType MODE = new ParameterType("Mode");

    /** Static value Credential for ParameterType. */
    public static final ParameterType CREDENTIAL = new ParameterType("Credential");

    /** Static value Boolean for ParameterType. */
    public static final ParameterType BOOLEAN = new ParameterType("Boolean");

    /** Static value Double for ParameterType. */
    public static final ParameterType DOUBLE = new ParameterType("Double");

    /** Static value ColumnPicker for ParameterType. */
    public static final ParameterType COLUMN_PICKER = new ParameterType("ColumnPicker");

    /** Static value ParameterRange for ParameterType. */
    public static final ParameterType PARAMETER_RANGE = new ParameterType("ParameterRange");

    /** Static value DataGatewayName for ParameterType. */
    public static final ParameterType DATA_GATEWAY_NAME = new ParameterType("DataGatewayName");

    private String value;

    /**
     * Creates a custom value for ParameterType.
     * @param value the custom value
     */
    public ParameterType(String value) {
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
        if (!(obj instanceof ParameterType)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        ParameterType rhs = (ParameterType) obj;
        if (value == null) {
            return rhs.value == null;
        } else {
            return value.equals(rhs.value);
        }
    }
}
