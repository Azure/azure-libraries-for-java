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
import com.microsoft.azure.management.network.ApplicationGatewayPathRule;
import com.microsoft.azure.management.network.ApplicationGatewayRedirectConfiguration;
import com.microsoft.azure.management.network.ApplicationGatewayUrlPathMap;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ChildResourceImpl;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implementation for application gateway URL path map.
 */
@LangDefinition
class ApplicationGatewayPathRuleImpl
        extends ChildResourceImpl<ApplicationGatewayPathRuleInner, ApplicationGatewayUrlPathMapImpl, ApplicationGatewayUrlPathMap>
        implements
        ApplicationGatewayPathRule,
        ApplicationGatewayPathRule.Definition<ApplicationGatewayUrlPathMap.DefinitionStages.WithAttach<ApplicationGateway.DefinitionStages.WithCreate>>,
        ApplicationGatewayPathRule.UpdateDefinition<ApplicationGatewayUrlPathMap.Update>,
        ApplicationGatewayPathRule.Update {

    ApplicationGatewayPathRuleImpl(ApplicationGatewayPathRuleInner inner, ApplicationGatewayUrlPathMapImpl parent) {
        super(inner, parent);
    }

    // Getters
    @Override
    public String name() {
        return this.inner().name();
    }

    // Verbs

    @Override
    public ApplicationGatewayUrlPathMapImpl attach() {
        return this.parent().withPathRule(this);
    }

    @Override
    public ApplicationGatewayPathRuleImpl toBackendHttpConfiguration(String name) {
        SubResource httpConfigRef = new SubResource()
                .withId(this.parent().parent().futureResourceId() + "/backendHttpSettingsCollection/" + name);
        this.inner().withBackendHttpSettings(httpConfigRef);
        return this;
    }

    @Override
    public ApplicationGatewayPathRuleImpl toBackendHttpPort(int portNumber) {
        // todo
        return this;
    }

    @Override
    public ApplicationGatewayPathRuleImpl toBackend(String name) {
        this.inner().withBackendAddressPool(this.parent().parent().ensureBackendRef(name));
        return this;
    }

    @Override
    public ApplicationGatewayPathRuleImpl withRedirectConfiguration(String name) {
        // todo
        return null;
    }

    @Override
    public ApplicationGatewayBackend backend() {
        return null;
    }

    @Override
    public ApplicationGatewayBackendHttpConfiguration backendHttpConfiguration() {
        return null;
    }

    @Override
    public ApplicationGatewayRedirectConfiguration redirectConfiguration() {
        return null;
    }

    @Override
    public ApplicationGatewayPathRuleImpl withPath(String path) {
        if (inner().paths() == null) {
            inner().withPaths(new ArrayList<String>());
        }
        inner().paths().add(path);
        return this;
    }

    @Override
    public ApplicationGatewayPathRuleImpl withPaths(String... paths) {
        inner().withPaths(Arrays.asList(paths));
        return this;
    }
}
