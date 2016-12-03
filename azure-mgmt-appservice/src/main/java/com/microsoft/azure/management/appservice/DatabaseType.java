/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.appservice;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for DatabaseType.
 */
public final class DatabaseType {
    /** Static value SqlAzure for DatabaseType. */
    public static final DatabaseType SQL_AZURE = new DatabaseType("SqlAzure");

    /** Static value MySql for DatabaseType. */
    public static final DatabaseType MY_SQL = new DatabaseType("MySql");

    /** Static value LocalMySql for DatabaseType. */
    public static final DatabaseType LOCAL_MY_SQL = new DatabaseType("LocalMySql");

    private String value;

    /**
     * Creates a custom value for DatabaseType.
     * @param value the custom value
     */
    public DatabaseType(String value) {
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
        if (!(obj instanceof DatabaseType)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        DatabaseType rhs = (DatabaseType) obj;
        if (value == null) {
            return rhs.value == null;
        } else {
            return value.equals(rhs.value);
        }
    }
}
