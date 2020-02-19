// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.appservice.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.azure.management.appservice.ProxyOnlyResource;

/**
 * The DomainOwnershipIdentifier model.
 */
@JsonFlatten
@Fluent
public class DomainOwnershipIdentifierInner extends ProxyOnlyResource {
    /*
     * Ownership Id.
     */
    @JsonProperty(value = "properties.ownershipId")
    private String ownershipId;

    /**
     * Get the ownershipId property: Ownership Id.
     * 
     * @return the ownershipId value.
     */
    public String ownershipId() {
        return this.ownershipId;
    }

    /**
     * Set the ownershipId property: Ownership Id.
     * 
     * @param ownershipId the ownershipId value to set.
     * @return the DomainOwnershipIdentifierInner object itself.
     */
    public DomainOwnershipIdentifierInner withOwnershipId(String ownershipId) {
        this.ownershipId = ownershipId;
        return this;
    }
}
