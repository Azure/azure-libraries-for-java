/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cdn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Url signing key.
 */
public class UrlSigningKey {
    /**
     * Defines the customer defined key Id. This id will exist in the incoming
     * request to indicate the key used to form the hash.
     */
    @JsonProperty(value = "keyId", required = true)
    private String keyId;

    /**
     * Defines the parameters for using customer key vault for Url Signing Key.
     */
    @JsonProperty(value = "keySourceParameters", required = true)
    private KeyVaultSigningKeyParameters keySourceParameters;

    /**
     * Get defines the customer defined key Id. This id will exist in the incoming request to indicate the key used to form the hash.
     *
     * @return the keyId value
     */
    public String keyId() {
        return this.keyId;
    }

    /**
     * Set defines the customer defined key Id. This id will exist in the incoming request to indicate the key used to form the hash.
     *
     * @param keyId the keyId value to set
     * @return the UrlSigningKey object itself.
     */
    public UrlSigningKey withKeyId(String keyId) {
        this.keyId = keyId;
        return this;
    }

    /**
     * Get defines the parameters for using customer key vault for Url Signing Key.
     *
     * @return the keySourceParameters value
     */
    public KeyVaultSigningKeyParameters keySourceParameters() {
        return this.keySourceParameters;
    }

    /**
     * Set defines the parameters for using customer key vault for Url Signing Key.
     *
     * @param keySourceParameters the keySourceParameters value to set
     * @return the UrlSigningKey object itself.
     */
    public UrlSigningKey withKeySourceParameters(KeyVaultSigningKeyParameters keySourceParameters) {
        this.keySourceParameters = keySourceParameters;
        return this;
    }

}
