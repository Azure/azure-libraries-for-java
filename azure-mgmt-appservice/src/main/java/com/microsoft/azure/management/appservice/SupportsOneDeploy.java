/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice;

import com.microsoft.azure.management.apigeneration.Beta;
import rx.Completable;
import rx.Observable;

import java.io.File;
import java.io.InputStream;

/**
 * Provides access to OneDeploy.
 */
@Beta(Beta.SinceVersion.V1_36_0)
public interface SupportsOneDeploy {

    /**
     * Deploy a file to Azure site.
     *
     * @param type the deploy type
     * @param file the file to upload
     */
    void deploy(DeployType type, File file);

    /**
     * Deploy a file to Azure site.
     *
     * @param type the deploy type
     * @param file the file to upload
     * @return a completable of the operation
     */
    Completable deployAsync(DeployType type, File file);

    /**
     * Deploy a file to Azure site.
     *
     * @param type the deploy type
     * @param file the file to upload
     * @param deployOptions the deploy options
     */
    void deploy(DeployType type, File file, DeployOptions deployOptions);

    /**
     * Deploy a file to Azure site.
     *
     * @param type the deploy type
     * @param file the file to upload
     * @param deployOptions the deploy options
     * @return a completable of the operation
     */
    Completable deployAsync(DeployType type, File file, DeployOptions deployOptions);

    /**
     * Deploy a file to Azure site.
     *
     * @param type the deploy type
     * @param file the file to upload
     */
    void deploy(DeployType type, InputStream file);

    /**
     * Deploy a file to Azure site.
     *
     * @param type the deploy type
     * @param file the file to upload
     * @return a completable of the operation
     */
    Completable deployAsync(DeployType type, InputStream file);

    /**
     * Deploy a file to Azure site.
     *
     * @param type the deploy type
     * @param file the file to upload
     * @param deployOptions the deploy options
     */
    void deploy(DeployType type, InputStream file, DeployOptions deployOptions);

    /**
     * Deploy a file to Azure site.
     *
     * @param type the deploy type
     * @param file the file to upload
     * @param deployOptions the deploy options
     * @return a completable of the operation
     */
    Completable deployAsync(DeployType type, InputStream file, DeployOptions deployOptions);

    /**
     * Deploy a file to Azure site.
     *
     * @param type the deploy type
     * @param file the file to upload
     * @param deployOptions the deploy options
     * @return the result of the deployment, which contains the deployment ID for query on the deployment status.
     */
    AsyncDeploymentResult pushDeploy(DeployType type, File file, DeployOptions deployOptions);

    /**
     * Deploy a file to Azure site.
     *
     * @param type the deploy type
     * @param file the file to upload
     * @param deployOptions the deploy options
     * @return the result of the deployment, which contains the deployment ID for query on the deployment status.
     */
    Observable<AsyncDeploymentResult> pushDeployAsync(DeployType type, File file, DeployOptions deployOptions);

    /**
     * Deploy a file to Azure site.
     *
     * @param type the deploy type
     * @param file the file to upload
     * @param deployOptions the deploy options
     * @return the result of the deployment, which contains the deployment ID for query on the deployment status.
     */
    AsyncDeploymentResult pushDeploy(DeployType type, InputStream file, DeployOptions deployOptions);

    /**
     * Deploy a file to Azure site.
     *
     * @param type the deploy type
     * @param file the file to upload
     * @param deployOptions the deploy options
     * @return the result of the deployment, which contains the deployment ID for query on the deployment status.
     */
    Observable<AsyncDeploymentResult> pushDeployAsync(DeployType type, InputStream file, DeployOptions deployOptions);

    /**
     * Gets the deployment status of the web app.
     *
     * @param deploymentId the deployment ID of the web app.
     * @return the deployment status.
     */
    Observable<DeploymentStatus> getDeploymentStatusAsync(String deploymentId);

    /**
     * Gets the deployment status of the web app.
     *
     * @param deploymentId the deployment ID of the web app.
     * @return the deployment status.
     */
    DeploymentStatus getDeploymentStatus(String deploymentId);
}
