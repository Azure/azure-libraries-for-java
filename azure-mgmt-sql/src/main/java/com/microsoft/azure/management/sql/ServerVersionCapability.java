/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The server capabilities.
 */
public class ServerVersionCapability {
    /**
     * The server version name.
     */
    @JsonProperty(value = "name", access = JsonProperty.Access.WRITE_ONLY)
    private String name;

    /**
     * The status of the server version. Possible values include: 'Visible',
     * 'Available', 'Default', 'Disabled'.
     */
    @JsonProperty(value = "status", access = JsonProperty.Access.WRITE_ONLY)
    private CapabilityStatus status;

    /**
     * The list of supported database editions.
     */
    @JsonProperty(value = "supportedEditions", access = JsonProperty.Access.WRITE_ONLY)
    private List<EditionCapability> supportedEditions;

    /**
     * The list of supported elastic pool editions.
     */
    @JsonProperty(value = "supportedElasticPoolEditions", access = JsonProperty.Access.WRITE_ONLY)
    private List<ElasticPoolEditionCapability> supportedElasticPoolEditions;

    /**
     * Get the server version name.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Get the status of the server version. Possible values include: 'Visible', 'Available', 'Default', 'Disabled'.
     *
     * @return the status value
     */
    public CapabilityStatus status() {
        return this.status;
    }

    /**
     * Get the list of supported database editions.
     *
     * @return the supportedEditions value
     */
    public List<EditionCapability> supportedEditions() {
        return this.supportedEditions;
    }

    /**
     * Get the list of supported elastic pool editions.
     *
     * @return the supportedElasticPoolEditions value
     */
    public List<ElasticPoolEditionCapability> supportedElasticPoolEditions() {
        return this.supportedElasticPoolEditions;
    }

}
