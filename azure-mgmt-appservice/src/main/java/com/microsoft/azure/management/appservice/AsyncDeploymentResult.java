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

    /**
     * Creates an AsyncDeploymentResult instance.
     *
     * @param deploymentId the deployment ID.
     */
    public AsyncDeploymentResult(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    /**
     * @return the deployment ID. It can be {@code null} if tracking deployment is disabled.
     */
    public String deploymentId() {
        return deploymentId;
    }
}
