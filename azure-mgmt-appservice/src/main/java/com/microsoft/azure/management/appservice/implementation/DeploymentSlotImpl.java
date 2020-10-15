/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.appservice.DeployOptions;
import com.microsoft.azure.management.appservice.DeployType;
import com.microsoft.azure.management.appservice.DeploymentSlot;
import com.microsoft.azure.management.appservice.WebApp;
import rx.Completable;
import rx.exceptions.Exceptions;
import rx.functions.Action0;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The implementation for DeploymentSlot.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.AppService.Fluent")
class DeploymentSlotImpl
        extends DeploymentSlotBaseImpl<
            DeploymentSlot,
            DeploymentSlotImpl,
            WebAppImpl,
            DeploymentSlot.DefinitionStages.WithCreate,
            DeploymentSlot.Update>
        implements
            DeploymentSlot,
            DeploymentSlot.Definition,
            DeploymentSlot.Update {

    DeploymentSlotImpl(String name, SiteInner innerObject, SiteConfigResourceInner siteConfig, SiteLogsConfigInner logConfig, WebAppImpl parent) {
        super(name, innerObject, siteConfig, logConfig, parent);
    }

    @Override
    public DeploymentSlotImpl withConfigurationFromParent() {
        return withConfigurationFromWebApp(this.parent());
    }

    @Override
    public DeploymentSlotImpl withConfigurationFromWebApp(WebApp webApp) {
        this.siteConfig = ((WebAppBaseImpl) webApp).siteConfig;
        configurationSource = webApp;
        return this;
    }

    @Override
    public Completable warDeployAsync(File warFile) {
        return warDeployAsync(warFile, null);
    }

    @Override
    public void warDeploy(File warFile) {
        warDeployAsync(warFile).await();
    }

    @Override
    public Completable warDeployAsync(InputStream warFile) {
        return warDeployAsync(warFile, null);
    }

    @Override
    public void warDeploy(InputStream warFile) {
        warDeployAsync(warFile).await();
    }

    @Override
    public Completable warDeployAsync(File warFile, String appName) {
        try {
            final InputStream is = new FileInputStream(warFile);
            return warDeployAsync(new FileInputStream(warFile), appName).doAfterTerminate(new Action0() {
                @Override
                public void call() {
                    try {
                        is.close();
                    } catch (IOException e) {
                        Exceptions.propagate(e);
                    }
                }
            });
        } catch (IOException e) {
            return Completable.error(e);
        }
    }

    @Override
    public void warDeploy(File warFile, String appName) {
        warDeployAsync(warFile, appName).await();
    }

    @Override
    public Completable warDeployAsync(InputStream warFile, String appName) {
        return kuduClient.warDeployAsync(warFile, appName);
    }

    @Override
    public void warDeploy(InputStream warFile, String appName) {
        warDeployAsync(warFile, appName).await();
    }

    @Override
    public void zipDeploy(File zipFile) {
        zipDeployAsync(zipFile).await();
    }

    @Override
    public void zipDeploy(InputStream zipFile) {
        zipDeployAsync(zipFile).await();
    }

    @Override
    public Completable zipDeployAsync(InputStream zipFile) {
        return kuduClient.zipDeployAsync(zipFile).concatWith(stopAsync()).concatWith(startAsync());
    }

    @Override
    public Completable zipDeployAsync(File zipFile) {
        try {
            final InputStream is = new FileInputStream(zipFile);
            return zipDeployAsync(new FileInputStream(zipFile)).doAfterTerminate(new Action0() {
                @Override
                public void call() {
                    try {
                        is.close();
                    } catch (IOException e) {
                        Exceptions.propagate(e);
                    }
                }
            });
        } catch (IOException e) {
            return Completable.error(e);
        }
    }


    @Override
    public void deploy(DeployType type, File file) {
        deployAsync(type, file).await();
    }

    @Override
    public Completable deployAsync(DeployType type, File file) {
        return deployAsync(type, file, new DeployOptions());
    }

    @Override
    public void deploy(DeployType type, File file, DeployOptions deployOptions) {
        deployAsync(type, file, deployOptions).await();
    }

    @Override
    public Completable deployAsync(DeployType type, File file, DeployOptions deployOptions) {
        try {
            final InputStream is = new FileInputStream(file);
            return deployAsync(type, new FileInputStream(file), deployOptions).doAfterTerminate(new Action0() {
                @Override
                public void call() {
                    try {
                        is.close();
                    } catch (IOException e) {
                        Exceptions.propagate(e);
                    }
                }
            });
        } catch (IOException e) {
            return Completable.error(e);
        }
    }

    @Override
    public void deploy(DeployType type, InputStream file) {
        deployAsync(type, file).await();
    }

    @Override
    public Completable deployAsync(DeployType type, InputStream file) {
        return kuduClient.deployAsync(type, file, null, null, null);
    }

    @Override
    public void deploy(DeployType type, InputStream file, DeployOptions deployOptions) {
        deployAsync(type, file, deployOptions).await();
    }

    @Override
    public Completable deployAsync(DeployType type, InputStream file, DeployOptions deployOptions) {
        return kuduClient.deployAsync(type, file, deployOptions.path(), deployOptions.restartSite(), deployOptions.cleanDeployment());
    }
}
