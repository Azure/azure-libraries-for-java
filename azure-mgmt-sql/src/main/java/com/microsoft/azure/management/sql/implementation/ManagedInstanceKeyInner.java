/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.sql.ServerKeyType;
import org.joda.time.DateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.ProxyResource;

/**
 * A managed instance key.
 */
@JsonFlatten
public class ManagedInstanceKeyInner extends ProxyResource {
    /**
     * Kind of encryption protector. This is metadata used for the Azure portal
     * experience.
     */
    @JsonProperty(value = "kind", access = JsonProperty.Access.WRITE_ONLY)
    private String kind;

    /**
     * The key type like 'ServiceManaged', 'AzureKeyVault'. Possible values
     * include: 'ServiceManaged', 'AzureKeyVault'.
     */
    @JsonProperty(value = "properties.serverKeyType", required = true)
    private ServerKeyType serverKeyType;

    /**
     * The URI of the key. If the ServerKeyType is AzureKeyVault, then the URI
     * is required.
     */
    @JsonProperty(value = "properties.uri")
    private String uri;

    /**
     * Thumbprint of the key.
     */
    @JsonProperty(value = "properties.thumbprint", access = JsonProperty.Access.WRITE_ONLY)
    private String thumbprint;

    /**
     * The key creation date.
     */
    @JsonProperty(value = "properties.creationDate", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime creationDate;

    /**
     * Get kind of encryption protector. This is metadata used for the Azure portal experience.
     *
     * @return the kind value
     */
    public String kind() {
        return this.kind;
    }

    /**
     * Get the key type like 'ServiceManaged', 'AzureKeyVault'. Possible values include: 'ServiceManaged', 'AzureKeyVault'.
     *
     * @return the serverKeyType value
     */
    public ServerKeyType serverKeyType() {
        return this.serverKeyType;
    }

    /**
     * Set the key type like 'ServiceManaged', 'AzureKeyVault'. Possible values include: 'ServiceManaged', 'AzureKeyVault'.
     *
     * @param serverKeyType the serverKeyType value to set
     * @return the ManagedInstanceKeyInner object itself.
     */
    public ManagedInstanceKeyInner withServerKeyType(ServerKeyType serverKeyType) {
        this.serverKeyType = serverKeyType;
        return this;
    }

    /**
     * Get the URI of the key. If the ServerKeyType is AzureKeyVault, then the URI is required.
     *
     * @return the uri value
     */
    public String uri() {
        return this.uri;
    }

    /**
     * Set the URI of the key. If the ServerKeyType is AzureKeyVault, then the URI is required.
     *
     * @param uri the uri value to set
     * @return the ManagedInstanceKeyInner object itself.
     */
    public ManagedInstanceKeyInner withUri(String uri) {
        this.uri = uri;
        return this;
    }

    /**
     * Get thumbprint of the key.
     *
     * @return the thumbprint value
     */
    public String thumbprint() {
        return this.thumbprint;
    }

    /**
     * Get the key creation date.
     *
     * @return the creationDate value
     */
    public DateTime creationDate() {
        return this.creationDate;
    }

}
