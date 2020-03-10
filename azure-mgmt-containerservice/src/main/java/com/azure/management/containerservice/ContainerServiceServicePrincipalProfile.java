/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.azure.management.containerservice;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Information about a service principal identity for the cluster to use for
 * manipulating Azure APIs. Either secret or keyVaultSecretRef must be
 * specified.
 */
public class ContainerServiceServicePrincipalProfile {
    /**
     * The ID for the service principal.
     */
    @JsonProperty(value = "clientId", required = true)
    private String clientId;

    /**
     * The secret password associated with the service principal in plain text.
     */
    @JsonProperty(value = "secret")
    private String secret;

    /**
     * Reference to a secret stored in Azure Key Vault.
     */
    @JsonProperty(value = "keyVaultSecretRef")
    private KeyVaultSecretRef keyVaultSecretRef;

    /**
     * Get the ID for the service principal.
     *
     * @return the clientId value
     */
    public String clientId() {
        return this.clientId;
    }

    /**
     * Set the ID for the service principal.
     *
     * @param clientId the clientId value to set
     * @return the ContainerServiceServicePrincipalProfile object itself.
     */
    public ContainerServiceServicePrincipalProfile withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * Get the secret password associated with the service principal in plain text.
     *
     * @return the secret value
     */
    public String secret() {
        return this.secret;
    }

    /**
     * Set the secret password associated with the service principal in plain text.
     *
     * @param secret the secret value to set
     * @return the ContainerServiceServicePrincipalProfile object itself.
     */
    public ContainerServiceServicePrincipalProfile withSecret(String secret) {
        this.secret = secret;
        return this;
    }

    /**
     * Get reference to a secret stored in Azure Key Vault.
     *
     * @return the keyVaultSecretRef value
     */
    public KeyVaultSecretRef keyVaultSecretRef() {
        return this.keyVaultSecretRef;
    }

    /**
     * Set reference to a secret stored in Azure Key Vault.
     *
     * @param keyVaultSecretRef the keyVaultSecretRef value to set
     * @return the ContainerServiceServicePrincipalProfile object itself.
     */
    public ContainerServiceServicePrincipalProfile withKeyVaultSecretRef(KeyVaultSecretRef keyVaultSecretRef) {
        this.keyVaultSecretRef = keyVaultSecretRef;
        return this;
    }

}
