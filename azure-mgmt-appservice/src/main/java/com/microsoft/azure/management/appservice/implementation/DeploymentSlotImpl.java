/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.appservice.DeploymentSlot;
import com.microsoft.azure.management.appservice.WebApp;

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

    DeploymentSlotImpl(String name, SiteInner innerObject, SiteConfigResourceInner configObject, WebAppImpl parent) {
        super(name, innerObject, configObject, parent);
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
}