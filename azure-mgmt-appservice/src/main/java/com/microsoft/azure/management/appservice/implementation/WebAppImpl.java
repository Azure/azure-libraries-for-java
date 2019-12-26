/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.appservice.AppServicePlan;
import com.microsoft.azure.management.appservice.DeploymentSlots;
import com.microsoft.azure.management.appservice.OperatingSystem;
import com.microsoft.azure.management.appservice.PricingTier;
import com.microsoft.azure.management.appservice.RuntimeStack;
import com.microsoft.azure.management.appservice.WebApp;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import rx.Completable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The implementation for WebApp.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.AppService.Fluent")
class WebAppImpl
        extends AppServiceBaseImpl<WebApp, WebAppImpl, WebApp.DefinitionStages.WithCreate, WebApp.Update>
        implements
            WebApp,
            WebApp.Definition,
            WebApp.DefinitionStages.ExistingWindowsPlanWithGroup,
            WebApp.DefinitionStages.ExistingLinuxPlanWithGroup,
            WebApp.Update,
            WebApp.UpdateStages.WithCredentials,
            WebApp.UpdateStages.WithStartUpCommand {

    private DeploymentSlots deploymentSlots;

    WebAppImpl(String name, SiteInner innerObject, SiteConfigResourceInner siteConfig, SiteLogsConfigInner logConfig, AppServiceManager manager) {
        super(name, innerObject, siteConfig, logConfig, manager);
    }

    @Override
    public DeploymentSlots deploymentSlots() {
        if (deploymentSlots == null) {
            deploymentSlots = new DeploymentSlotsImpl(this);
        }
        return deploymentSlots;
    }

    @Override
    public WebAppImpl withBuiltInImage(RuntimeStack runtimeStack) {
        ensureLinuxPlan();
        cleanUpContainerSettings();
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        siteConfig.withLinuxFxVersion(String.format("%s|%s", runtimeStack.stack(), runtimeStack.version()));
        if (runtimeStack.stack().equals("NODE")) {
            siteConfig.withNodeVersion(runtimeStack.version());
        }
        if (runtimeStack.stack().equals("PHP")) {
            siteConfig.withPhpVersion(runtimeStack.version());
        }
        if (runtimeStack.stack().equals("DOTNETCORE")) {
            siteConfig.withNetFrameworkVersion(runtimeStack.version());
        }
        return this;
    }

    @Override
    protected void cleanUpContainerSettings() {
        if (siteConfig != null && siteConfig.linuxFxVersion() != null) {
            siteConfig.withLinuxFxVersion(null);
        }
        // PHP
        if (siteConfig != null && siteConfig.phpVersion() != null) {
            siteConfig.withPhpVersion(null);
        }
        // Node
        if (siteConfig != null && siteConfig.nodeVersion() != null) {
            siteConfig.withNodeVersion(null);
        }
        // .NET
        if (siteConfig != null && siteConfig.netFrameworkVersion() != null) {
            siteConfig.withNetFrameworkVersion("v4.0");
        }
        // Docker Hub
        withoutAppSetting(SETTING_DOCKER_IMAGE);
        withoutAppSetting(SETTING_REGISTRY_SERVER);
        withoutAppSetting(SETTING_REGISTRY_USERNAME);
        withoutAppSetting(SETTING_REGISTRY_PASSWORD);
    }

    @Override
    public WebAppImpl withStartUpCommand(String startUpCommand) {
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        siteConfig.withAppCommandLine(startUpCommand);
        return this;
    }

    @Override
    public WebAppImpl withExistingWindowsPlan(AppServicePlan appServicePlan) {
        return super.withExistingAppServicePlan(appServicePlan);
    }

    @Override
    public WebAppImpl withExistingLinuxPlan(AppServicePlan appServicePlan) {
        return super.withExistingAppServicePlan(appServicePlan);
    }

    @Override
    public WebAppImpl withNewWindowsPlan(PricingTier pricingTier) {
        return super.withNewAppServicePlan(OperatingSystem.WINDOWS, pricingTier);
    }

    @Override
    public WebAppImpl withNewWindowsPlan(Creatable<AppServicePlan> appServicePlanCreatable) {
        return super.withNewAppServicePlan(appServicePlanCreatable);
    }

    @Override
    public WebAppImpl withNewLinuxPlan(PricingTier pricingTier) {
        return super.withNewAppServicePlan(OperatingSystem.LINUX, pricingTier);
    }

    @Override
    public WebAppImpl withNewLinuxPlan(Creatable<AppServicePlan> appServicePlanCreatable) {
        return super.withNewAppServicePlan(appServicePlanCreatable);
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
            return warDeployAsync(new FileInputStream(warFile), appName);
        } catch (IOException e) {
            return Completable.error(e);
        }
    }

    @Override
    public void warDeploy(File warFile, String appName) {
        warDeployAsync(warFile, appName).await();
    }
    @Override
    public void warDeploy(InputStream warFile, String appName) {
        warDeployAsync(warFile, appName).await();
    }

    @Override
    public Completable warDeployAsync(InputStream warFile, String appName) {
        return kuduClient.warDeployAsync(warFile, appName);
    }

    @Override
    public Completable zipDeployAsync(File zipFile) {
        try {
            return zipDeployAsync(new FileInputStream(zipFile));
        } catch (IOException e) {
            return Completable.error(e);
        }
    }

    @Override
    public void zipDeploy(File zipFile) {
        zipDeployAsync(zipFile).await();
    }

    @Override
    public Completable zipDeployAsync(InputStream zipFile) {
        return kuduClient.zipDeployAsync(zipFile).concatWith(WebAppImpl.this.stopAsync()).concatWith(WebAppImpl.this.startAsync());
    }

    @Override
    public void zipDeploy(InputStream zipFile) {
        zipDeployAsync(zipFile).await();
    }
}