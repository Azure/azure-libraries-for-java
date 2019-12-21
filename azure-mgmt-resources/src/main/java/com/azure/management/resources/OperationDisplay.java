// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.resources;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The OperationDisplay model.
 */
@Fluent
public final class OperationDisplay {
    /*
     * Service provider: Microsoft.Features
     */
    @JsonProperty(value = "provider")
    private String provider;

    /*
     * Resource on which the operation is performed: Profile, endpoint, etc.
     */
    @JsonProperty(value = "resource")
    private String resource;

    /*
     * Operation type: Read, write, delete, etc.
     */
    @JsonProperty(value = "operation")
    private String operation;

    /**
     * Get the provider property: Service provider: Microsoft.Features.
     * 
     * @return the provider value.
     */
    public String getProvider() {
        return this.provider;
    }

    /**
     * Set the provider property: Service provider: Microsoft.Features.
     * 
     * @param provider the provider value to set.
     * @return the OperationDisplay object itself.
     */
    public OperationDisplay setProvider(String provider) {
        this.provider = provider;
        return this;
    }

    /**
     * Get the resource property: Resource on which the operation is performed:
     * Profile, endpoint, etc.
     * 
     * @return the resource value.
     */
    public String getResource() {
        return this.resource;
    }

    /**
     * Set the resource property: Resource on which the operation is performed:
     * Profile, endpoint, etc.
     * 
     * @param resource the resource value to set.
     * @return the OperationDisplay object itself.
     */
    public OperationDisplay setResource(String resource) {
        this.resource = resource;
        return this;
    }

    /**
     * Get the operation property: Operation type: Read, write, delete, etc.
     * 
     * @return the operation value.
     */
    public String getOperation() {
        return this.operation;
    }

    /**
     * Set the operation property: Operation type: Read, write, delete, etc.
     * 
     * @param operation the operation value to set.
     * @return the OperationDisplay object itself.
     */
    public OperationDisplay setOperation(String operation) {
        this.operation = operation;
        return this;
    }
}
