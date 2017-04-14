/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.compute;

import com.microsoft.azure.SubResource;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Describes a reference to Key Vault Key.
 */
public class KeyVaultKeyReference {
    /**
     * The URL referencing a key in a Key Vault.
     */
    @JsonProperty(value = "keyUrl", required = true)
    private String keyUrl;

    /**
     * The relative URL of the Key Vault containing the key.
     */
    @JsonProperty(value = "sourceVault", required = true)
    private SubResource sourceVault;

    /**
     * Get the keyUrl value.
     *
     * @return the keyUrl value
     */
    public String keyUrl() {
        return this.keyUrl;
    }

    /**
     * Set the keyUrl value.
     *
     * @param keyUrl the keyUrl value to set
     * @return the KeyVaultKeyReference object itself.
     */
    public KeyVaultKeyReference withKeyUrl(String keyUrl) {
        this.keyUrl = keyUrl;
        return this;
    }

    /**
     * Get the sourceVault value.
     *
     * @return the sourceVault value
     */
    public SubResource sourceVault() {
        return this.sourceVault;
    }

    /**
     * Set the sourceVault value.
     *
     * @param sourceVault the sourceVault value to set
     * @return the KeyVaultKeyReference object itself.
     */
    public KeyVaultKeyReference withSourceVault(SubResource sourceVault) {
        this.sourceVault = sourceVault;
        return this;
    }

}
