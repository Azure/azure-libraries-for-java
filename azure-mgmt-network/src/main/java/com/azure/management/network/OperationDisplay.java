// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The OperationDisplay model.
 */
@Fluent
public final class OperationDisplay {
    /*
     * Service provider: Microsoft Network.
     */
    @JsonProperty(value = "provider")
    private String provider;

    /*
     * Resource on which the operation is performed.
     */
    @JsonProperty(value = "resource")
    private String resource;

    /*
     * Type of the operation: get, read, delete, etc.
     */
    @JsonProperty(value = "operation")
    private String operation;

    /*
     * Description of the operation.
     */
    @JsonProperty(value = "description")
    private String description;

    /**
     * Get the provider property: Service provider: Microsoft Network.
     * 
     * @return the provider value.
     */
    public String getProvider() {
        return this.provider;
    }

    /**
     * Set the provider property: Service provider: Microsoft Network.
     * 
     * @param provider the provider value to set.
     * @return the OperationDisplay object itself.
     */
    public OperationDisplay setProvider(String provider) {
        this.provider = provider;
        return this;
    }

    /**
     * Get the resource property: Resource on which the operation is performed.
     * 
     * @return the resource value.
     */
    public String getResource() {
        return this.resource;
    }

    /**
     * Set the resource property: Resource on which the operation is performed.
     * 
     * @param resource the resource value to set.
     * @return the OperationDisplay object itself.
     */
    public OperationDisplay setResource(String resource) {
        this.resource = resource;
        return this;
    }

    /**
     * Get the operation property: Type of the operation: get, read, delete,
     * etc.
     * 
     * @return the operation value.
     */
    public String getOperation() {
        return this.operation;
    }

    /**
     * Set the operation property: Type of the operation: get, read, delete,
     * etc.
     * 
     * @param operation the operation value to set.
     * @return the OperationDisplay object itself.
     */
    public OperationDisplay setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    /**
     * Get the description property: Description of the operation.
     * 
     * @return the description value.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set the description property: Description of the operation.
     * 
     * @param description the description value to set.
     * @return the OperationDisplay object itself.
     */
    public OperationDisplay setDescription(String description) {
        this.description = description;
        return this;
    }
}
