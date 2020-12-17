/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.appservice;

/**
 * Result of async deployment.
 */
public class AsyncDeploymentResult {

    private final String deploymentId;

    public AsyncDeploymentResult(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getDeploymentId() {
        return deploymentId;
    }
}
