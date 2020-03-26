// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.sql;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The CompleteDatabaseRestoreDefinition model.
 */
@Fluent
public final class CompleteDatabaseRestoreDefinition {
    /*
     * The last backup name to apply
     */
    @JsonProperty(value = "lastBackupName", required = true)
    private String lastBackupName;

    /**
     * Get the lastBackupName property: The last backup name to apply.
     * 
     * @return the lastBackupName value.
     */
    public String lastBackupName() {
        return this.lastBackupName;
    }

    /**
     * Set the lastBackupName property: The last backup name to apply.
     * 
     * @param lastBackupName the lastBackupName value to set.
     * @return the CompleteDatabaseRestoreDefinition object itself.
     */
    public CompleteDatabaseRestoreDefinition withLastBackupName(String lastBackupName) {
        this.lastBackupName = lastBackupName;
        return this;
    }
}
