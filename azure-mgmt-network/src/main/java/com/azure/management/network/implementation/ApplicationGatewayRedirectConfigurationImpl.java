/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.core.management.SubResource;
import com.azure.management.network.ApplicationGateway;
import com.azure.management.network.ApplicationGatewayListener;
import com.azure.management.network.ApplicationGatewayRedirectConfiguration;
import com.azure.management.network.ApplicationGatewayRedirectType;
import com.azure.management.network.ApplicationGatewayRequestRoutingRule;
import com.azure.management.network.models.ApplicationGatewayRedirectConfigurationInner;
import com.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.azure.management.resources.fluentcore.arm.models.implementation.ChildResourceImpl;
import com.azure.management.resources.fluentcore.utils.Utils;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implementation for ApplicationGatewayRedirectConfiguration.
 */
class ApplicationGatewayRedirectConfigurationImpl
        extends ChildResourceImpl<ApplicationGatewayRedirectConfigurationInner, ApplicationGatewayImpl, ApplicationGateway>
        implements
        ApplicationGatewayRedirectConfiguration,
        ApplicationGatewayRedirectConfiguration.Definition<ApplicationGateway.DefinitionStages.WithCreate>,
        ApplicationGatewayRedirectConfiguration.UpdateDefinition<ApplicationGateway.Update>,
        ApplicationGatewayRedirectConfiguration.Update {

    ApplicationGatewayRedirectConfigurationImpl(ApplicationGatewayRedirectConfigurationInner inner, ApplicationGatewayImpl parent) {
        super(inner, parent);
    }

    // Getters
    @Override
    public String name() {
        return this.inner().getName();
    }

    @Override
    public ApplicationGatewayRedirectType type() {
        return this.inner().getRedirectType();
    }

    @Override
    public ApplicationGatewayListener targetListener() {
        SubResource ref = this.inner().getTargetListener();
        if (ref == null) {
            return null;
        }

        String name = ResourceUtils.nameFromResourceId(ref.getId());
        return this.parent().listeners().get(name);
    }

    @Override
    public String targetUrl() {
        return this.inner().getTargetUrl();
    }

    @Override
    public Map<String, ApplicationGatewayRequestRoutingRule> requestRoutingRules() {
        Map<String, ApplicationGatewayRequestRoutingRule> rules = new TreeMap<>();
        if (null != this.inner().getRequestRoutingRules()) {
            for (SubResource ruleRef : this.inner().getRequestRoutingRules()) {
                String ruleName = ResourceUtils.nameFromResourceId(ruleRef.getId());
                ApplicationGatewayRequestRoutingRule rule = this.parent().requestRoutingRules().get(ruleName);
                if (null != rule) {
                    rules.put(ruleName, rule);
                }
            }
        }

        return Collections.unmodifiableMap(rules);
    }

    @Override
    public boolean isPathIncluded() {
        return Utils.toPrimitiveBoolean(this.inner().isIncludePath());
    }

    @Override
    public boolean isQueryStringIncluded() {
        return Utils.toPrimitiveBoolean(this.inner().isIncludeQueryString());
    }

    // Verbs

    @Override
    public ApplicationGatewayImpl attach() {
        return this.parent().withRedirectConfiguration(this);
    }

    // Helpers

    // Withers

    @Override
    public ApplicationGatewayRedirectConfigurationImpl withTargetUrl(String url) {
        this.inner()
                .setTargetUrl(url)
                .setTargetListener(null)
                .setIncludePath(null);
        return this;
    }

    @Override
    public ApplicationGatewayRedirectConfigurationImpl withTargetListener(String name) {
        if (name == null) {
            this.inner().setTargetListener(null);
        } else {
            SubResource ref = new SubResource().setId(this.parent().futureResourceId() + "/httpListeners/" + name);
            this.inner()
                    .setTargetListener(ref)
                    .setTargetUrl(null);
        }

        return this;
    }

    @Override
    public ApplicationGatewayRedirectConfigurationImpl withoutTargetListener() {
        this.inner().setTargetListener(null);
        return this;
    }

    @Override
    public ApplicationGatewayRedirectConfigurationImpl withoutTargetUrl() {
        this.inner().setTargetUrl(null);
        return this;
    }

    @Override
    public ApplicationGatewayRedirectConfigurationImpl withType(ApplicationGatewayRedirectType redirectType) {
        this.inner().setRedirectType(redirectType);
        return this;
    }

    @Override
    public ApplicationGatewayRedirectConfigurationImpl withPathIncluded() {
        this.inner().setIncludePath(true);
        return this;
    }

    @Override
    public ApplicationGatewayRedirectConfigurationImpl withQueryStringIncluded() {
        this.inner().setIncludeQueryString(true);
        return this;
    }

    @Override
    public ApplicationGatewayRedirectConfigurationImpl withoutPathIncluded() {
        this.inner().setIncludePath(null);
        return this;
    }

    @Override
    public ApplicationGatewayRedirectConfigurationImpl withoutQueryStringIncluded() {
        this.inner().setIncludeQueryString(null);
        return this;
    }
}
