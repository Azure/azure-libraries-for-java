/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.appservice.AppServicePlan;
import com.microsoft.azure.management.appservice.DeployType;
import com.microsoft.azure.management.appservice.DeployOptions;
import com.microsoft.azure.management.appservice.DeploymentSlots;
import com.microsoft.azure.management.appservice.OperatingSystem;
import com.microsoft.azure.management.appservice.PricingTier;
import com.microsoft.azure.management.appservice.RuntimeStack;
import com.microsoft.azure.management.appservice.WebApp;
import com.microsoft.azure.management.appservice.WebAppRuntimeStack;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import rx.Completable;
import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.functions.Func1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

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
            WebApp.DefinitionStages.WithWindowsRuntimeStack,
            WebApp.Update,
            WebApp.UpdateStages.WithCredentials,
            WebApp.UpdateStages.WithStartUpCommand {

    private DeploymentSlots deploymentSlots;
    private WebAppRuntimeStack runtimeStackOnWindowsOSToUpdate;

    WebAppImpl(String name, SiteInner innerObject, SiteConfigResourceInner siteConfig, SiteLogsConfigInner logConfig, AppServiceManager manager) {
        super(name, innerObject, siteConfig, logConfig, manager);
    }

    @Override
    public WebAppImpl update() {
        runtimeStackOnWindowsOSToUpdate = null;
        return super.update();
    };

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
    public WebAppImpl withNewWindowsPlan(String appServicePlanName, PricingTier pricingTier) {
        return super.withNewAppServicePlan(appServicePlanName, OperatingSystem.WINDOWS, pricingTier);
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
    public WebAppImpl withNewLinuxPlan(String appServicePlanName, PricingTier pricingTier) {
        return super.withNewAppServicePlan(appServicePlanName, OperatingSystem.LINUX, pricingTier);
    }

    @Override
    public WebAppImpl withNewLinuxPlan(Creatable<AppServicePlan> appServicePlanCreatable) {
        return super.withNewAppServicePlan(appServicePlanCreatable);
    }

    @Override
    public WebAppImpl withRuntimeStack(WebAppRuntimeStack runtimeStack) {
        runtimeStackOnWindowsOSToUpdate = runtimeStack;
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

    @Override
    Observable<Indexable> submitMetadata() {
        Observable<Indexable> observable = super.submitMetadata();
        if (runtimeStackOnWindowsOSToUpdate != null) {
            observable = observable.flatMap(new Func1<Indexable, Observable<StringDictionaryInner>>() {
                // list metadata
                @Override
                public Observable<StringDictionaryInner> call(Indexable indexable) {
                    return listMetadata();
                }
            }).flatMap(new Func1<StringDictionaryInner, Observable<StringDictionaryInner>>() {
                // merge with change, then update
                @Override
                public Observable<StringDictionaryInner> call(StringDictionaryInner stringDictionaryInner) {
                    if (stringDictionaryInner == null) {
                        stringDictionaryInner = new StringDictionaryInner();
                    }
                    if (stringDictionaryInner.properties() == null) {
                        stringDictionaryInner.withProperties(new HashMap<String, String>());
                    }
                    stringDictionaryInner.properties().put("CURRENT_STACK", runtimeStackOnWindowsOSToUpdate.runtime());
                    return updateMetadata(stringDictionaryInner);
                }
            }).map(new Func1<StringDictionaryInner, Indexable>() {
                // clean up
                @Override
                public Indexable call(StringDictionaryInner stringDictionaryInner) {
                    runtimeStackOnWindowsOSToUpdate = null;
                    return WebAppImpl.this;
                }
            });
        }
        return observable;
    }

    Observable<StringDictionaryInner> listMetadata() {
        return this.manager().inner().webApps().listMetadataAsync(resourceGroupName(), name());
    }

    Observable<StringDictionaryInner> updateMetadata(StringDictionaryInner inner) {
        return this.manager().inner().webApps().updateMetadataAsync(resourceGroupName(), name(), inner);
    }
}
