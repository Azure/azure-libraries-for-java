/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.storage;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The encryption settings on the storage account.
 */
public class Encryption {
    /**
     * List of services which support encryption.
     */
    @JsonProperty(value = "services")
    private EncryptionServices services;

    /**
     * The encryption keySource (provider). Possible values (case-insensitive):
     * Microsoft.Storage, Microsoft.Keyvault. Possible values include:
     * 'Microsoft.Storage', 'Microsoft.Keyvault'.
     */
    @JsonProperty(value = "keySource", required = true)
    private KeySource keySource;

    /**
     * A boolean indicating whether or not the service applies a secondary
     * layer of encryption with platform managed keys for data at rest.
     */
    @JsonProperty(value = "requireInfrastructureEncryption")
    private Boolean requireInfrastructureEncryption;

    /**
     * Properties provided by key vault.
     */
    @JsonProperty(value = "keyvaultproperties")
    private KeyVaultProperties keyVaultProperties;

    /**
     * Get list of services which support encryption.
     *
     * @return the services value
     */
    public EncryptionServices services() {
        return this.services;
    }

    /**
     * Set list of services which support encryption.
     *
     * @param services the services value to set
     * @return the Encryption object itself.
     */
    public Encryption withServices(EncryptionServices services) {
        this.services = services;
        return this;
    }

    /**
     * Get the encryption keySource (provider). Possible values (case-insensitive):  Microsoft.Storage, Microsoft.Keyvault. Possible values include: 'Microsoft.Storage', 'Microsoft.Keyvault'.
     *
     * @return the keySource value
     */
    public KeySource keySource() {
        return this.keySource;
    }

    /**
     * Set the encryption keySource (provider). Possible values (case-insensitive):  Microsoft.Storage, Microsoft.Keyvault. Possible values include: 'Microsoft.Storage', 'Microsoft.Keyvault'.
     *
     * @param keySource the keySource value to set
     * @return the Encryption object itself.
     */
    public Encryption withKeySource(KeySource keySource) {
        this.keySource = keySource;
        return this;
    }

    /**
     * Get a boolean indicating whether or not the service applies a secondary layer of encryption with platform managed keys for data at rest.
     *
     * @return the requireInfrastructureEncryption value
     */
    public Boolean requireInfrastructureEncryption() {
        return this.requireInfrastructureEncryption;
    }

    /**
     * Set a boolean indicating whether or not the service applies a secondary layer of encryption with platform managed keys for data at rest.
     *
     * @param requireInfrastructureEncryption the requireInfrastructureEncryption value to set
     * @return the Encryption object itself.
     */
    public Encryption withRequireInfrastructureEncryption(Boolean requireInfrastructureEncryption) {
        this.requireInfrastructureEncryption = requireInfrastructureEncryption;
        return this;
    }

    /**
     * Get properties provided by key vault.
     *
     * @return the keyVaultProperties value
     */
    public KeyVaultProperties keyVaultProperties() {
        return this.keyVaultProperties;
    }

    /**
     * Set properties provided by key vault.
     *
     * @param keyVaultProperties the keyVaultProperties value to set
     * @return the Encryption object itself.
     */
    public Encryption withKeyVaultProperties(KeyVaultProperties keyVaultProperties) {
        this.keyVaultProperties = keyVaultProperties;
        return this;
    }

}
