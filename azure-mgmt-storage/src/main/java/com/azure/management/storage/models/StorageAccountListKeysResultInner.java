// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.storage.models;

import com.azure.core.annotation.Immutable;
import com.azure.management.storage.StorageAccountKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The StorageAccountListKeysResult model.
 */
@Immutable
public final class StorageAccountListKeysResultInner {
    /*
     * Gets the list of storage account keys and their properties for the
     * specified storage account.
     */
    @JsonProperty(value = "keys", access = JsonProperty.Access.WRITE_ONLY)
    private List<StorageAccountKey> keys;

    /**
     * Get the keys property: Gets the list of storage account keys and their
     * properties for the specified storage account.
     * 
     * @return the keys value.
     */
    public List<StorageAccountKey> getKeys() {
        return this.keys;
    }
}
