/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.appservice.DeploymentSlotBase;
import com.microsoft.azure.management.appservice.FunctionApp;
import com.microsoft.azure.management.appservice.FunctionDeploymentSlot;
import com.microsoft.azure.management.appservice.FunctionDeploymentSlot.DefinitionStages.WithCreate;
import com.microsoft.azure.management.appservice.SitePatchResource;
import rx.Completable;
import rx.Observable;
import rx.functions.Action0;

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
            DeploymentSlotBase<FunctionDeploymentSlot>>
        implements
        FunctionDeploymentSlot,
        FunctionDeploymentSlot.Definition,
        DeploymentSlotBase.Update<FunctionDeploymentSlot> {

    FunctionDeploymentSlotImpl(String name, SiteInner innerObject, SiteConfigResourceInner siteConfig, SiteLogsConfigInner logConfig, FunctionAppImpl parent) {
        super(name, innerObject, siteConfig, logConfig, parent);
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
            final InputStream is = new FileInputStream(zipFile);
            return zipDeployAsync(new FileInputStream(zipFile)).doAfterTerminate(new Action0() {
                @Override
                public void call() {
                    try {
                        is.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (IOException e) {
            return Completable.error(e);
        }
    }

    @Override
    Observable<SiteInner> submitSite(final SiteInner site) {
        return submitSiteWithoutSiteConfig(site);
    }

    @Override
    Observable<SiteInner> submitSite(final SitePatchResource siteUpdate) {
        // PATCH does not work for function app slot
        return submitSiteWithoutSiteConfig(this.inner());
    }
}
