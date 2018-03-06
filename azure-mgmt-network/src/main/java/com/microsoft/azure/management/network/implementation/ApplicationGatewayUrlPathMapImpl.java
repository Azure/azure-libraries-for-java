/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network.implementation;

import com.microsoft.azure.SubResource;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.network.ApplicationGateway;
import com.microsoft.azure.management.network.ApplicationGatewayBackend;
import com.microsoft.azure.management.network.ApplicationGatewayBackendHttpConfiguration;
import com.microsoft.azure.management.network.ApplicationGatewayRedirectConfiguration;
import com.microsoft.azure.management.network.ApplicationGatewayUrlPathMap;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ChildResourceImpl;

/**
 *  Implementation for application gateway URL path map.
 */
@LangDefinition
class ApplicationGatewayUrlPathMapImpl
    extends ChildResourceImpl<ApplicationGatewayUrlPathMapInner, ApplicationGatewayImpl, ApplicationGateway>
    implements
        ApplicationGatewayUrlPathMap,
        ApplicationGatewayUrlPathMap.Definition<ApplicationGateway.DefinitionStages.WithCreate>,
        ApplicationGatewayUrlPathMap.UpdateDefinition<ApplicationGateway.Update>,
        ApplicationGatewayUrlPathMap.Update {

    ApplicationGatewayUrlPathMapImpl(ApplicationGatewayUrlPathMapInner inner, ApplicationGatewayImpl parent) {
        super(inner, parent);
    }

    // Getters
    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public ApplicationGatewayBackend defaultBackend() {
        SubResource backendRef = this.inner().defaultBackendAddressPool();
        return (backendRef != null) ? this.parent().backends().get(ResourceUtils.nameFromResourceId(backendRef.id())) : null;
    }

    @Override
    public ApplicationGatewayBackendHttpConfiguration defaultBackendHttpConfiguration() {
        SubResource backendHttpConfigRef = this.inner().defaultBackendHttpSettings();
        return (backendHttpConfigRef != null) ? this.parent().backendHttpConfigurations().get(ResourceUtils.nameFromResourceId(backendHttpConfigRef.id())) : null;
    }

    @Override
    public ApplicationGatewayRedirectConfiguration defaultRedirectConfiguration() {
        SubResource redirectRef = this.inner().defaultRedirectConfiguration();
        return (redirectRef != null) ? this.parent().redirectConfigurations().get(ResourceUtils.nameFromResourceId(redirectRef.id())) : null;
    }

    // Verbs

    @Override
    public ApplicationGatewayImpl attach() {
        return this.parent().withUrlPathMap(this);
    }

    // Helpers

    // Withers

}
