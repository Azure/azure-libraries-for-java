/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.appservice.FunctionApp;
import com.microsoft.azure.management.appservice.FunctionDeploymentSlot;
import com.microsoft.azure.management.appservice.FunctionDeploymentSlot.DefinitionStages.WithCreate;
import rx.Completable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The implementation for FunctionDeploymentSlot.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.AppService.Fluent")
class FunctionDeploymentSlotImpl
        extends DeploymentSlotBaseImpl<
            FunctionDeploymentSlot,
            FunctionDeploymentSlotImpl,
            FunctionAppImpl,
            FunctionDeploymentSlot.DefinitionStages.WithCreate,
            FunctionDeploymentSlot.Update>
        implements
        FunctionDeploymentSlot,
        FunctionDeploymentSlot.Definition,
        FunctionDeploymentSlot.Update {
    private KuduClient kuduClient;

    FunctionDeploymentSlotImpl(String name, SiteInner innerObject, SiteConfigResourceInner configObject, FunctionAppImpl parent) {
        super(name, innerObject, configObject, parent);
        kuduClient = new KuduClient(this);
    }

    @Override
    public WithCreate withConfigurationFromParent() {
        return withConfigurationFromFunctionApp(this.parent());
    }

    @Override
    public WithCreate withConfigurationFromFunctionApp(FunctionApp app) {
        this.siteConfig = ((WebAppBaseImpl) app).siteConfig;
        configurationSource = app;
        return this;
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
        return kuduClient.zipDeployAsync(zipFile);
    }

    @Override
    public Completable zipDeployAsync(File zipFile) {
        try {
            return zipDeployAsync(new FileInputStream(zipFile));
        } catch (IOException e) {
            return Completable.error(e);
        }
    }
}