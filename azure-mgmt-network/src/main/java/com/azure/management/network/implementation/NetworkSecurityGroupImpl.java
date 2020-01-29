/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.management.network.NetworkSecurityGroup;
import com.azure.management.network.NetworkSecurityRule;
import com.azure.management.network.Subnet;
import com.azure.management.network.models.GroupableParentResourceWithTagsImpl;
import com.azure.management.network.models.NetworkInterfaceInner;
import com.azure.management.network.models.NetworkSecurityGroupInner;
import com.azure.management.network.models.SecurityRuleInner;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Implementation for NetworkSecurityGroup and its create and update interfaces.
 */
class NetworkSecurityGroupImpl
        extends GroupableParentResourceWithTagsImpl<
        NetworkSecurityGroup,
        NetworkSecurityGroupInner,
        NetworkSecurityGroupImpl,
        NetworkManager>
        implements
        NetworkSecurityGroup,
        NetworkSecurityGroup.Definition,
        NetworkSecurityGroup.Update {

    private Map<String, NetworkSecurityRule> rules;
    private Map<String, NetworkSecurityRule> defaultRules;

    NetworkSecurityGroupImpl(
            final String name,
            final NetworkSecurityGroupInner innerModel,
            final NetworkManager networkManager) {
        super(name, innerModel, networkManager);
    }

    @Override
    protected void initializeChildrenFromInner() {
        this.rules = new TreeMap<>();
        List<SecurityRuleInner> inners = this.inner().getSecurityRules();
        if (inners != null) {
            for (SecurityRuleInner inner : inners) {
                this.rules.put(inner.getName(), new NetworkSecurityRuleImpl(inner, this));
            }
        }

        this.defaultRules = new TreeMap<>();
        inners = this.inner().getDefaultSecurityRules();
        if (inners != null) {
            for (SecurityRuleInner inner : inners) {
                this.defaultRules.put(inner.getName(), new NetworkSecurityRuleImpl(inner, this));
            }
        }
    }

    // Verbs

    @Override
    public NetworkSecurityRuleImpl updateRule(String name) {
        return (NetworkSecurityRuleImpl) this.rules.get(name);
    }

    @Override
    public NetworkSecurityRuleImpl defineRule(String name) {
        SecurityRuleInner inner = new SecurityRuleInner();
        inner.setName(name);
        inner.setPriority(100); // Must be at least 100
        return new NetworkSecurityRuleImpl(inner, this);
    }

    @Override
    public Mono<NetworkSecurityGroup> refreshAsync() {
        return super.refreshAsync().map(networkSecurityGroup -> {
            NetworkSecurityGroupImpl impl = (NetworkSecurityGroupImpl) networkSecurityGroup;

            impl.initializeChildrenFromInner();
            return impl;
        });
    }

    @Override
    protected Mono<NetworkSecurityGroupInner> getInnerAsync() {
        return this.manager().inner().networkSecurityGroups().getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    protected Mono<NetworkSecurityGroupInner> applyTagsToInnerAsync() {
        return this.manager().inner().networkSecurityGroups().updateTagsAsync(resourceGroupName(), name(), inner().getTags());
    }

    @Override
    public List<Subnet> listAssociatedSubnets() {
        return this.myManager.listAssociatedSubnets(this.inner().getSubnets());
    }

    // Setters (fluent)

    @Override
    public Update withoutRule(String name) {
        this.rules.remove(name);
        return this;
    }

    NetworkSecurityGroupImpl withRule(NetworkSecurityRuleImpl rule) {
        this.rules.put(rule.name(), rule);
        return this;
    }

    // Getters

    @Override
    public Map<String, NetworkSecurityRule> securityRules() {
        return Collections.unmodifiableMap(this.rules);
    }

    @Override
    public Map<String, NetworkSecurityRule> defaultSecurityRules() {
        return Collections.unmodifiableMap(this.defaultRules);
    }

    @Override
    public Set<String> networkInterfaceIds() {
        Set<String> ids = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        if (this.inner().getNetworkInterfaces() != null) {
            for (NetworkInterfaceInner inner : this.inner().getNetworkInterfaces()) {
                ids.add(inner.getId());
            }
        }
        return Collections.unmodifiableSet(ids);
    }

    @Override
    protected void beforeCreating() {
        // Reset and update subnets
        this.inner().setSecurityRules(innersFromWrappers(this.rules.values()));
    }

    @Override
    protected void afterCreating() {
    }

    @Override
    protected Mono<NetworkSecurityGroupInner> createInner() {
        return this.manager().inner().networkSecurityGroups().createOrUpdateAsync(this.resourceGroupName(), this.name(), this.inner());
    }
}
