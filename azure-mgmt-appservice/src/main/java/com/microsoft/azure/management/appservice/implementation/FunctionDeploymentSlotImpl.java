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
    FunctionDeploymentSlotImpl(String name, SiteInner innerObject, SiteConfigResourceInner configObject, FunctionAppImpl parent) {
        super(name, innerObject, configObject, parent);
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
}