/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network.implementation;

import com.microsoft.azure.SubResource;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.network.NetworkWatcher;
import com.microsoft.azure.management.network.Topology;
import com.microsoft.azure.management.network.TopologyParameters;
import com.microsoft.azure.management.network.TopologyResource;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.ExecutableImpl;
import org.joda.time.DateTime;
import rx.Observable;
import rx.functions.Func1;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The implementation of Topology.
 */
@LangDefinition
class TopologyImpl extends ExecutableImpl<Topology>
        implements Topology,
        Topology.Definition {
    private Map<String, TopologyResource> resources;
    private final NetworkWatcherImpl parent;
    private TopologyParameters parameters = new TopologyParameters();
    private TopologyInner inner;

    TopologyImpl(NetworkWatcherImpl parent) {
        this.parent = parent;
    }

    @Override
    public String id() {
        return inner().id();
    }

    @Override
    public TopologyParameters topologyParameters() {
        return parameters;
    }

    @Override
    public DateTime createdTime() {
        return inner().createdDateTime();
    }

    @Override
    public DateTime lastModifiedTime() {
        return inner().lastModified();
    }

    @Override
    public Map<String, TopologyResource> resources() {
        return Collections.unmodifiableMap(this.resources);
    }

    private void initializeResourcesFromInner() {
        this.resources = new TreeMap<>();
        List<TopologyResource> topologyResources = this.inner().resources();
        if (topologyResources != null) {
            for (TopologyResource resource : topologyResources) {
                this.resources.put(resource.id(), resource);
            }
        }
    }

    @Override
    public NetworkWatcher parent() {
        return parent;
    }

    @Override
    public TopologyImpl withTargetResourceGroup(String resourceGroupName) {
        parameters.withTargetResourceGroupName(resourceGroupName);
        return this;
    }

    @Override
    public TopologyImpl withTargetNetwork(String networkId) {
        parameters.withTargetVirtualNetwork(new SubResource().withId(networkId));
        return this;
    }

    @Override
    public TopologyImpl withTargetSubnet(String subnetName) {
        parameters.withTargetSubnet(new SubResource().withId(parameters.targetVirtualNetwork().id() + "/subnets/" + subnetName));
        return this;
    }

    @Override
    public TopologyInner inner() {
        return this.inner;
    }

    @Override
    public Observable<Topology> executeWorkAsync() {
        return this.parent().manager().inner().networkWatchers()
                .getTopologyAsync(parent().resourceGroupName(), parent().name(), parameters)
                .map(new Func1<TopologyInner, Topology>() {
                    @Override
                    public Topology call(TopologyInner topologyInner) {
                        TopologyImpl.this.inner = topologyInner;
                        TopologyImpl.this.initializeResourcesFromInner();
                        return TopologyImpl.this;
                    }
                });
    }
}