// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The CloudErrorBody model.
 */
@Fluent
public final class CloudErrorBody {
    /*
     * An identifier for the error. Codes are invariant and are intended to be
     * consumed programmatically.
     */
    @JsonProperty(value = "code")
    private String code;

    /*
     * A message describing the error, intended to be suitable for display in a
     * user interface.
     */
    @JsonProperty(value = "message")
    private String message;

    /*
     * The target of the particular error. For example, the name of the
     * property in error.
     */
    @JsonProperty(value = "target")
    private String target;

    /*
     * A list of additional details about the error.
     */
    @JsonProperty(value = "details")
    private List<CloudErrorBody> details;

    /**
     * Get the code property: An identifier for the error. Codes are invariant
     * and are intended to be consumed programmatically.
     * 
     * @return the code value.
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Set the code property: An identifier for the error. Codes are invariant
     * and are intended to be consumed programmatically.
     * 
     * @param code the code value to set.
     * @return the CloudErrorBody object itself.
     */
    public CloudErrorBody setCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * Get the message property: A message describing the error, intended to be
     * suitable for display in a user interface.
     * 
     * @return the message value.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Set the message property: A message describing the error, intended to be
     * suitable for display in a user interface.
     * 
     * @param message the message value to set.
     * @return the CloudErrorBody object itself.
     */
    public CloudErrorBody setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Get the target property: The target of the particular error. For
     * example, the name of the property in error.
     * 
     * @return the target value.
     */
    public String getTarget() {
        return this.target;
    }

    /**
     * Set the target property: The target of the particular error. For
     * example, the name of the property in error.
     * 
     * @param target the target value to set.
     * @return the CloudErrorBody object itself.
     */
    public CloudErrorBody setTarget(String target) {
        this.target = target;
        return this;
    }

    /**
     * Get the details property: A list of additional details about the error.
     * 
     * @return the details value.
     */
    public List<CloudErrorBody> getDetails() {
        return this.details;
    }

    /**
     * Set the details property: A list of additional details about the error.
     * 
     * @param details the details value to set.
     * @return the CloudErrorBody object itself.
     */
    public CloudErrorBody setDetails(List<CloudErrorBody> details) {
        this.details = details;
        return this;
    }
}
