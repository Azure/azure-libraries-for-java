/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice;

import com.microsoft.azure.management.apigeneration.Beta;
import rx.Completable;

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
    @Beta(Beta.SinceVersion.V1_36_0)
    void deploy(DeployType type, File file);

    /**
     * Deploy a file to Azure site.
     *
     * @param type the deploy type
     * @param file the file to upload
     * @return a completable of the operation
     */
    @Beta(Beta.SinceVersion.V1_36_0)
    Completable deployAsync(DeployType type, File file);

    /**
     * Deploy a file to Azure site.
     *
     * @param type the deploy type
     * @param file the file to upload
     * @param deployOptions the deploy options
     */
    @Beta(Beta.SinceVersion.V1_36_0)
    void deploy(DeployType type, File file, DeployOptions deployOptions);

    /**
     * Deploy a file to Azure site.
     *
     * @param type the deploy type
     * @param file the file to upload
     * @param deployOptions the deploy options
     * @return a completable of the operation
     */
    @Beta(Beta.SinceVersion.V1_36_0)
    Completable deployAsync(DeployType type, File file, DeployOptions deployOptions);

    /**
     * Deploy a file to Azure site.
     *
     * @param type the deploy type
     * @param file the file to upload
     */
    @Beta(Beta.SinceVersion.V1_36_0)
    void deploy(DeployType type, InputStream file);

    /**
     * Deploy a file to Azure site.
     *
     * @param type the deploy type
     * @param file the file to upload
     * @return a completable of the operation
     */
    @Beta(Beta.SinceVersion.V1_36_0)
    Completable deployAsync(DeployType type, InputStream file);

    /**
     * Deploy a file to Azure site.
     *
     * @param type the deploy type
     * @param file the file to upload
     * @param deployOptions the deploy options
     */
    @Beta(Beta.SinceVersion.V1_36_0)
    void deploy(DeployType type, InputStream file, DeployOptions deployOptions);

    /**
     * Deploy a file to Azure site.
     *
     * @param type the deploy type
     * @param file the file to upload
     * @param deployOptions the deploy options
     * @return a completable of the operation
     */
    @Beta(Beta.SinceVersion.V1_36_0)
    Completable deployAsync(DeployType type, InputStream file, DeployOptions deployOptions);
}
