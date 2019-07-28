/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.sql.ServerConnectionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.ProxyResource;

/**
 * A server secure connection policy.
 */
@JsonFlatten
public class ServerConnectionPolicyInner extends ProxyResource {
    /**
     * Metadata used for the Azure portal experience.
     */
    @JsonProperty(value = "kind", access = JsonProperty.Access.WRITE_ONLY)
    private String kind;

    /**
     * Resource location.
     */
    @JsonProperty(value = "location", access = JsonProperty.Access.WRITE_ONLY)
    private String location;

    /**
     * The server connection type. Possible values include: 'Default', 'Proxy',
     * 'Redirect'.
     */
    @JsonProperty(value = "properties.connectionType", required = true)
    private ServerConnectionType connectionType;

    /**
     * Get metadata used for the Azure portal experience.
     *
     * @return the kind value
     */
    public String kind() {
        return this.kind;
    }

    /**
     * Get resource location.
     *
     * @return the location value
     */
    public String location() {
        return this.location;
    }

    /**
     * Get the server connection type. Possible values include: 'Default', 'Proxy', 'Redirect'.
     *
     * @return the connectionType value
     */
    public ServerConnectionType connectionType() {
        return this.connectionType;
    }

    /**
     * Set the server connection type. Possible values include: 'Default', 'Proxy', 'Redirect'.
     *
     * @param connectionType the connectionType value to set
     * @return the ServerConnectionPolicyInner object itself.
     */
    public ServerConnectionPolicyInner withConnectionType(ServerConnectionType connectionType) {
        this.connectionType = connectionType;
        return this;
    }

}
