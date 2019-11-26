/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cosmosdb.implementation;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.management.cosmosdb.ARMProxyResource;

/**
 * A private link resource.
 */
@JsonFlatten
public class PrivateLinkResourceInner extends ARMProxyResource {
    /**
     * The private link resource group id.
     */
    @JsonProperty(value = "properties.groupId", access = JsonProperty.Access.WRITE_ONLY)
    private String groupId;

    /**
     * The private link resource required member names.
     */
    @JsonProperty(value = "properties.requiredMembers", access = JsonProperty.Access.WRITE_ONLY)
    private List<String> requiredMembers;

    /**
     * Get the private link resource group id.
     *
     * @return the groupId value
     */
    public String groupId() {
        return this.groupId;
    }

    /**
     * Get the private link resource required member names.
     *
     * @return the requiredMembers value
     */
    public List<String> requiredMembers() {
        return this.requiredMembers;
    }

}
