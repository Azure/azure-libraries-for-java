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
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ChildResourceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation for application gateway URL path map.
 */
@LangDefinition
class ApplicationGatewayUrlPathMapImpl
    extends ChildResourceImpl<ApplicationGatewayUrlPathMapInner, ApplicationGatewayImpl, ApplicationGateway>
    implements
        ApplicationGatewayUrlPathMap,
        ApplicationGatewayUrlPathMap.Definition<ApplicationGateway.DefinitionStages.WithRequestRoutingRuleOrCreate>,
        ApplicationGatewayUrlPathMap.UpdateDefinition<ApplicationGateway.Update>,
        ApplicationGatewayUrlPathMap.Update {
    private Map<String, ApplicationGatewayPathRule> pathRules;

    ApplicationGatewayUrlPathMapImpl(ApplicationGatewayUrlPathMapInner inner, ApplicationGatewayImpl parent) {
        super(inner, parent);
        initializePathRules();
    }

    private void initializePathRules() {
        pathRules = new HashMap<>();
        for (ApplicationGatewayPathRuleInner inner : inner().pathRules()) {
            pathRules.put(inner.name(), new ApplicationGatewayPathRuleImpl(inner, this));
        }
    }

    // Getters
    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public Map<String, ApplicationGatewayPathRule> pathRules() {
        return Collections.unmodifiableMap(pathRules);
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

    @Override
    public ApplicationGatewayUrlPathMapImpl toBackendHttpConfiguration(String name) {
        SubResource httpConfigRef = new SubResource()
                .withId(this.parent().futureResourceId() + "/backendHttpSettingsCollection/" + name);
        this.inner().withDefaultBackendHttpSettings(httpConfigRef);
        return this;
    }

    @Override
    public ApplicationGatewayUrlPathMapImpl toBackendHttpPort(int portNumber) {
        // todo
        return null;
    }

    @Override
    public ApplicationGatewayUrlPathMapImpl toBackend(String name) {
        this.inner().withDefaultBackendAddressPool(this.parent().ensureBackendRef(name));
        return this;
    }

    @Override
    public ApplicationGatewayUrlPathMapImpl withRedirectConfiguration(String name) {
        if (name == null) {
            this.inner().withDefaultRedirectConfiguration(null);
        } else {
            SubResource ref = new SubResource().withId(this.parent().futureResourceId() + "/redirectConfigurations/" + name);
            this.inner()
                    .withDefaultRedirectConfiguration(ref)
                    .withDefaultBackendAddressPool(null)
                    .withDefaultBackendHttpSettings(null);
        }
        return this;
    }

    @Override
    public ApplicationGatewayPathRuleImpl definePathRule(String name) {
        return new ApplicationGatewayPathRuleImpl(new ApplicationGatewayPathRuleInner().withName(name), this);
    }

    ApplicationGatewayUrlPathMapImpl withPathRule(ApplicationGatewayPathRuleImpl pathRule) {
        if (pathRule != null) {
            List<ApplicationGatewayPathRuleInner> rules = new ArrayList<>();
            rules.add(pathRule.inner());
            this.inner().withPathRules(rules);
        }
        return this;
    }

    @Override
    public ApplicationGatewayUrlPathMapImpl fromListener(String name) {
        SubResource listenerRef = new SubResource().withId(this.parent().futureResourceId() + "/HTTPListeners/" + name);
        parent().requestRoutingRules().get(this.name()).inner().withHttpListener(listenerRef);
        return this;
    }

    @Override
    public ApplicationGatewayUrlPathMapImpl fromPublicFrontend() {
        return null;
    }

    @Override
    public ApplicationGatewayUrlPathMapImpl fromPrivateFrontend() {
        return null;
    }

    @Override
    public ApplicationGatewayUrlPathMapImpl fromFrontendHttpPort(int portNumber) {
        return null;
    }
}
