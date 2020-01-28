/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.core.management.SubResource;
import com.azure.management.network.ApplicationGateway;
import com.azure.management.network.ApplicationGatewayBackend;
import com.azure.management.network.ApplicationGatewayBackendHttpConfiguration;
import com.azure.management.network.ApplicationGatewayPathRule;
import com.azure.management.network.ApplicationGatewayRedirectConfiguration;
import com.azure.management.network.ApplicationGatewayUrlPathMap;
import com.azure.management.network.models.ApplicationGatewayPathRuleInner;
import com.azure.management.network.models.ApplicationGatewayUrlPathMapInner;
import com.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.azure.management.resources.fluentcore.arm.models.implementation.ChildResourceImpl;
import com.azure.management.resources.fluentcore.utils.SdkContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation for application gateway URL path map.
 */
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
        if (inner().getPathRules() != null) {
            for (ApplicationGatewayPathRuleInner inner : inner().getPathRules()) {
                pathRules.put(inner.getName(), new ApplicationGatewayPathRuleImpl(inner, this));
            }
        }
    }

    // Getters
    @Override
    public String name() {
        return this.inner().getName();
    }

    @Override
    public Map<String, ApplicationGatewayPathRule> pathRules() {
        return Collections.unmodifiableMap(pathRules);
    }

    @Override
    public ApplicationGatewayBackend defaultBackend() {
        SubResource backendRef = this.inner().getDefaultBackendAddressPool();
        return (backendRef != null) ? this.parent().backends().get(ResourceUtils.nameFromResourceId(backendRef.getId())) : null;
    }

    @Override
    public ApplicationGatewayBackendHttpConfiguration defaultBackendHttpConfiguration() {
        SubResource backendHttpConfigRef = this.inner().getDefaultBackendHttpSettings();
        return (backendHttpConfigRef != null) ? this.parent().backendHttpConfigurations().get(ResourceUtils.nameFromResourceId(backendHttpConfigRef.getId())) : null;
    }

    @Override
    public ApplicationGatewayRedirectConfiguration defaultRedirectConfiguration() {
        SubResource redirectRef = this.inner().getDefaultRedirectConfiguration();
        return (redirectRef != null) ? this.parent().redirectConfigurations().get(ResourceUtils.nameFromResourceId(redirectRef.getId())) : null;
    }

    // Verbs

    @Override
    public ApplicationGatewayImpl attach() {
        return this.parent().withUrlPathMap(this);
    }

    @Override
    public ApplicationGatewayUrlPathMapImpl toBackendHttpConfiguration(String name) {
        SubResource httpConfigRef = new SubResource()
                .setId(this.parent().futureResourceId() + "/backendHttpSettingsCollection/" + name);
        this.inner().setDefaultBackendHttpSettings(httpConfigRef);
        return this;
    }

    @Override
    public ApplicationGatewayUrlPathMapImpl toBackendHttpPort(int portNumber) {
        String name = SdkContext.randomResourceName("backcfg", 12);
        this.parent().defineBackendHttpConfiguration(name)
                .withPort(portNumber)
                .attach();
        return this.toBackendHttpConfiguration(name);
    }

    @Override
    public ApplicationGatewayUrlPathMapImpl toBackend(String name) {
        this.inner().setDefaultBackendAddressPool(this.parent().ensureBackendRef(name));
        return this;
    }

    @Override
    public ApplicationGatewayUrlPathMapImpl withRedirectConfiguration(String name) {
        if (name == null) {
            this.inner().setDefaultRedirectConfiguration(null);
        } else {
            SubResource ref = new SubResource().setId(this.parent().futureResourceId() + "/redirectConfigurations/" + name);
            this.inner()
                    .setDefaultRedirectConfiguration(ref)
                    .setDefaultBackendAddressPool(null)
                    .setDefaultBackendHttpSettings(null);
        }
        return this;
    }

    @Override
    public ApplicationGatewayPathRuleImpl definePathRule(String name) {
        return new ApplicationGatewayPathRuleImpl(new ApplicationGatewayPathRuleInner().setName(name), this);
    }

    ApplicationGatewayUrlPathMapImpl withPathRule(ApplicationGatewayPathRuleImpl pathRule) {
        if (pathRule != null) {
            if (inner().getPathRules() == null) {
                inner().setPathRules(new ArrayList<ApplicationGatewayPathRuleInner>());
            }
            inner().getPathRules().add(pathRule.inner());
        }
        return this;
    }

    @Override
    public ApplicationGatewayUrlPathMapImpl fromListener(String name) {
        SubResource listenerRef = new SubResource().setId(this.parent().futureResourceId() + "/HTTPListeners/" + name);
        parent().requestRoutingRules().get(this.name()).inner().setHttpListener(listenerRef);
        return this;
    }

    @Override
    public ApplicationGatewayUrlPathMapImpl toBackendIPAddress(String ipAddress) {
        this.parent().updateBackend(ensureBackend().name()).withIPAddress(ipAddress);
        return this;
    }

    @Override
    public ApplicationGatewayUrlPathMapImpl toBackendIPAddresses(String... ipAddresses) {
        if (ipAddresses != null) {
            for (String ipAddress : ipAddresses) {
                this.toBackendIPAddress(ipAddress);
            }
        }
        return this;
    }

    @Override
    public ApplicationGatewayUrlPathMapImpl toBackendFqdn(String fqdn) {
        this.parent().updateBackend(ensureBackend().name()).withFqdn(fqdn);
        return this;
    }

    private ApplicationGatewayBackendImpl ensureBackend() {
        ApplicationGatewayBackendImpl backend = (ApplicationGatewayBackendImpl) this.defaultBackend();
        if (backend == null) {
            backend = this.parent().ensureUniqueBackend();
            this.toBackend(backend.name());
        }
        return backend;
    }
}
