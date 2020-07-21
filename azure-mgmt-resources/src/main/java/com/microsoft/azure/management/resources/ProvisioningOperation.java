/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for ProvisioningOperation.
 */
public enum ProvisioningOperation {
    /** The provisioning operation is not specified. */
    NOT_SPECIFIED("NotSpecified"),

    /** The provisioning operation is create. */
    CREATE("Create"),

    /** The provisioning operation is delete. */
    DELETE("Delete"),

    /** The provisioning operation is waiting. */
    WAITING("Waiting"),

    /** The provisioning operation is waiting Azure async operation. */
    AZURE_ASYNC_OPERATION_WAITING("AzureAsyncOperationWaiting"),

    /** The provisioning operation is waiting for resource cache. */
    RESOURCE_CACHE_WAITING("ResourceCacheWaiting"),

    /** The provisioning operation is action. */
    ACTION("Action"),

    /** The provisioning operation is read. */
    READ("Read"),

    /** The provisioning operation is evaluate output. */
    EVALUATE_DEPLOYMENT_OUTPUT("EvaluateDeploymentOutput"),

    /** The provisioning operation is cleanup. This operation is part of the 'complete' mode deployment. */
    DEPLOYMENT_CLEANUP("DeploymentCleanup");

    /** The actual serialized value for a ProvisioningOperation instance. */
    private String value;

    ProvisioningOperation(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a ProvisioningOperation instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed ProvisioningOperation object, or null if unable to parse.
     */
    @JsonCreator
    public static ProvisioningOperation fromString(String value) {
        ProvisioningOperation[] items = ProvisioningOperation.values();
        for (ProvisioningOperation item : items) {
            if (item.toString().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
