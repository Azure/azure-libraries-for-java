/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.core.management.SubResource;
import com.azure.management.network.LoadBalancer;
import com.azure.management.network.LoadBalancerHttpProbe;
import com.azure.management.network.LoadBalancerTcpProbe;
import com.azure.management.network.LoadBalancingRule;
import com.azure.management.network.ProbeProtocol;
import com.azure.management.network.models.ProbeInner;
import com.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.azure.management.resources.fluentcore.arm.models.implementation.ChildResourceImpl;
import com.azure.management.resources.fluentcore.utils.Utils;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implementation for LoadBalancerTcpProbe and its create and update interfaces.
 */
class LoadBalancerProbeImpl
        extends ChildResourceImpl<ProbeInner, LoadBalancerImpl, LoadBalancer>
        implements
        LoadBalancerTcpProbe,
        LoadBalancerTcpProbe.Definition<LoadBalancer.DefinitionStages.WithCreate>,
        LoadBalancerTcpProbe.UpdateDefinition<LoadBalancer.Update>,
        LoadBalancerTcpProbe.Update,
        LoadBalancerHttpProbe,
        LoadBalancerHttpProbe.Definition<LoadBalancer.DefinitionStages.WithCreate>,
        LoadBalancerHttpProbe.UpdateDefinition<LoadBalancer.Update>,
        LoadBalancerHttpProbe.Update {

    LoadBalancerProbeImpl(ProbeInner inner, LoadBalancerImpl parent) {
        super(inner, parent);
    }

    // Getters

    @Override
    public int intervalInSeconds() {
        return Utils.toPrimitiveInt(this.inner().getIntervalInSeconds());
    }

    @Override
    public int port() {
        return Utils.toPrimitiveInt(this.inner().getPort());
    }

    @Override
    public int numberOfProbes() {
        return Utils.toPrimitiveInt(this.inner().getNumberOfProbes());
    }

    @Override
    public String name() {
        return this.inner().getName();
    }

    @Override
    public ProbeProtocol protocol() {
        return this.inner().getProtocol();
    }

    @Override
    public String requestPath() {
        return this.inner().getRequestPath();
    }

    @Override
    public Map<String, LoadBalancingRule> loadBalancingRules() {
        final Map<String, LoadBalancingRule> rules = new TreeMap<>();
        if (this.inner().getLoadBalancingRules() != null) {
            for (SubResource inner : this.inner().getLoadBalancingRules()) {
                String name = ResourceUtils.nameFromResourceId(inner.getId());
                LoadBalancingRule rule = this.parent().loadBalancingRules().get(name);
                if (rule != null) {
                    rules.put(name, rule);
                }
            }
        }

        return Collections.unmodifiableMap(rules);
    }

    // Fluent setters

    @Override
    public LoadBalancerProbeImpl withPort(int port) {
        this.inner().setPort(port);
        return this;
    }

    @Override
    public LoadBalancerProbeImpl withRequestPath(String requestPath) {
        this.inner().setRequestPath(requestPath);
        return this;
    }

    @Override
    public LoadBalancerProbeImpl withIntervalInSeconds(int seconds) {
        this.inner().setIntervalInSeconds(seconds);
        return this;
    }

    @Override
    public LoadBalancerProbeImpl withNumberOfProbes(int probes) {
        this.inner().setNumberOfProbes(probes);
        return this;
    }

    // Verbs

    @Override
    public LoadBalancerImpl attach() {
        return this.parent().withProbe(this);
    }
}
