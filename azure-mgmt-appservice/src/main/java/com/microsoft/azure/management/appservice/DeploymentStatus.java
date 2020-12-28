/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.appservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

@JsonFlatten
public class DeploymentStatus {

    @JsonProperty(value = "properties.buildStatus")
    private BuildStatus buildStatus;

    public BuildStatus buildStatus() {
        return buildStatus;
    }
}
