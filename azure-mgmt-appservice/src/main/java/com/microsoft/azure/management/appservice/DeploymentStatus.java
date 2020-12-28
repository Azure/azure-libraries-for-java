/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.appservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.azure.ProxyResource;
import com.microsoft.rest.serializer.JsonFlatten;

@JsonFlatten
public class DeploymentStatus extends ProxyResource {

    @JsonProperty(value = "properties.id")
    private BuildStatus deploymentId;

    @JsonProperty(value = "properties.buildStatus")
    private BuildStatus buildStatus;

    @JsonProperty(value = "properties.numberOfInstancesInProgress")
    int numberOfInstancesInProgress;

    @JsonProperty(value = "properties.numberOfInstancesSuccessful")
    int numberOfInstancesSuccessful;

    @JsonProperty(value = "properties.numberOfInstancesFailed")
    int numberOfInstancesFailed;

    public BuildStatus deploymentId() {
        return deploymentId;
    }

    public BuildStatus buildStatus() {
        return buildStatus;
    }

    public int numberOfInstancesInProgress() {
        return numberOfInstancesInProgress;
    }

    public int numberOfInstancesSuccessful() {
        return numberOfInstancesSuccessful;
    }

    public int numberOfInstancesFailed() {
        return numberOfInstancesFailed;
    }
}
