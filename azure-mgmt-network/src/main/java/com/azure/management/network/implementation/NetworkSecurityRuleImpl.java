/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.management.network.NetworkSecurityGroup;
import com.azure.management.network.NetworkSecurityRule;
import com.azure.management.network.SecurityRuleAccess;
import com.azure.management.network.SecurityRuleDirection;
import com.azure.management.network.SecurityRuleProtocol;
import com.azure.management.network.models.ApplicationSecurityGroupInner;
import com.azure.management.network.models.SecurityRuleInner;
import com.azure.management.resources.fluentcore.arm.models.implementation.ChildResourceImpl;
import com.azure.management.resources.fluentcore.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation for {@link NetworkSecurityRule} and its create and update interfaces.
 */
class NetworkSecurityRuleImpl
        extends ChildResourceImpl<SecurityRuleInner, NetworkSecurityGroupImpl, NetworkSecurityGroup>
        implements
        NetworkSecurityRule,
        NetworkSecurityRule.Definition<NetworkSecurityGroup.DefinitionStages.WithCreate>,
        NetworkSecurityRule.UpdateDefinition<NetworkSecurityGroup.Update>,
        NetworkSecurityRule.Update {
    private Map<String, ApplicationSecurityGroupInner> sourceAsgs = new HashMap<>();
    private Map<String, ApplicationSecurityGroupInner> destinationAsgs = new HashMap<>();

    NetworkSecurityRuleImpl(SecurityRuleInner inner, NetworkSecurityGroupImpl parent) {
        super(inner, parent);
        if (inner.getSourceApplicationSecurityGroups() != null) {
            for (ApplicationSecurityGroupInner asg : inner.getSourceApplicationSecurityGroups()) {
                sourceAsgs.put(asg.getId(), asg);
            }
        }
        if (inner.getDestinationApplicationSecurityGroups() != null) {
            for (ApplicationSecurityGroupInner asg : inner.getDestinationApplicationSecurityGroups()) {
                destinationAsgs.put(asg.getId(), asg);
            }
        }
    }

    // Getters

    @Override
    public String name() {
        return this.inner().getName();
    }

    @Override
    public SecurityRuleDirection direction() {
        return this.inner().getDirection();
    }

    @Override
    public SecurityRuleProtocol protocol() {
        return this.inner().getProtocol();
    }

    @Override
    public SecurityRuleAccess access() {
        return this.inner().getAccess();
    }

    @Override
    public String sourceAddressPrefix() {
        return this.inner().getSourceAddressPrefix();
    }

    @Override
    public List<String> sourceAddressPrefixes() {
        return Collections.unmodifiableList(this.inner().getSourceAddressPrefixes());
    }

    @Override
    public String sourcePortRange() {
        return this.inner().getSourcePortRange();
    }

    @Override
    public List<String> sourcePortRanges() {
        return Collections.unmodifiableList(inner().getSourcePortRanges());
    }

    @Override
    public String destinationAddressPrefix() {
        return this.inner().getDestinationAddressPrefix();
    }

    @Override
    public List<String> destinationAddressPrefixes() {
        return Collections.unmodifiableList(this.inner().getDestinationAddressPrefixes());
    }

    @Override
    public String destinationPortRange() {
        return this.inner().getDestinationPortRange();
    }

    @Override
    public List<String> destinationPortRanges() {
        return Collections.unmodifiableList(inner().getDestinationPortRanges());
    }

    @Override
    public int priority() {
        return Utils.toPrimitiveInt(this.inner().getPriority());
    }

    @Override
    public Set<String> sourceApplicationSecurityGroupIds() {
        return Collections.unmodifiableSet(sourceAsgs.keySet());
    }

    @Override
    public Set<String> destinationApplicationSecurityGroupIds() {
        return Collections.unmodifiableSet(destinationAsgs.keySet());
    }

    // Fluent setters

    @Override
    public NetworkSecurityRuleImpl allowInbound() {
        return this
                .withDirection(SecurityRuleDirection.INBOUND)
                .withAccess(SecurityRuleAccess.ALLOW);
    }

    @Override
    public NetworkSecurityRuleImpl allowOutbound() {
        return this
                .withDirection(SecurityRuleDirection.OUTBOUND)
                .withAccess(SecurityRuleAccess.ALLOW);
    }

    @Override
    public NetworkSecurityRuleImpl denyInbound() {
        return this
                .withDirection(SecurityRuleDirection.INBOUND)
                .withAccess(SecurityRuleAccess.DENY);
    }

    @Override
    public NetworkSecurityRuleImpl denyOutbound() {
        return this
                .withDirection(SecurityRuleDirection.OUTBOUND)
                .withAccess(SecurityRuleAccess.DENY);
    }

    @Override
    public NetworkSecurityRuleImpl withProtocol(SecurityRuleProtocol protocol) {
        this.inner().setProtocol(protocol);
        return this;
    }

    @Override
    public NetworkSecurityRuleImpl withAnyProtocol() {
        return this.withProtocol(SecurityRuleProtocol.ASTERISK);
    }

    @Override
    public NetworkSecurityRuleImpl fromAddress(String cidr) {
        this.inner().setSourceAddressPrefix(cidr);
        this.inner().setSourceAddressPrefixes(null);
        this.inner().setSourceApplicationSecurityGroups(null);
        return this;
    }

    @Override
    public NetworkSecurityRuleImpl fromAnyAddress() {
        this.inner().setSourceAddressPrefix("*");
        this.inner().setSourceAddressPrefixes(null);
        this.inner().setSourceApplicationSecurityGroups(null);
        return this;
    }

    @Override
    public NetworkSecurityRuleImpl fromAddresses(String... addresses) {
        this.inner().setSourceAddressPrefixes(Arrays.asList(addresses));
        this.inner().setSourceAddressPrefix(null);
        this.inner().setSourceApplicationSecurityGroups(null);
        return this;
    }

    @Override
    public NetworkSecurityRuleImpl fromPort(int port) {
        this.inner().setSourcePortRange(String.valueOf(port));
        this.inner().setSourcePortRanges(null);
        return this;
    }

    @Override
    public NetworkSecurityRuleImpl fromAnyPort() {
        this.inner().setSourcePortRange("*");
        this.inner().setSourcePortRanges(null);
        return this;
    }

    @Override
    public NetworkSecurityRuleImpl fromPortRange(int from, int to) {
        this.inner().setSourcePortRange(String.valueOf(from) + "-" + String.valueOf(to));
        this.inner().setSourcePortRanges(null);
        return this;
    }

    @Override
    public NetworkSecurityRuleImpl fromPortRanges(String... ranges) {
        this.inner().setSourcePortRanges(Arrays.asList(ranges));
        this.inner().setSourcePortRange(null);
        return this;
    }

    @Override
    public NetworkSecurityRuleImpl toAddress(String cidr) {
        this.inner().setDestinationAddressPrefix(cidr);
        this.inner().setDestinationAddressPrefixes(null);
        this.inner().setDestinationApplicationSecurityGroups(null);
        return this;
    }

    @Override
    public NetworkSecurityRuleImpl toAddresses(String... addresses) {
        this.inner().setDestinationAddressPrefixes(Arrays.asList(addresses));
        this.inner().setDestinationAddressPrefix(null);
        this.inner().setDestinationApplicationSecurityGroups(null);
        return this;
    }

    @Override
    public NetworkSecurityRuleImpl toAnyAddress() {
        this.inner().setDestinationAddressPrefix("*");
        this.inner().setDestinationAddressPrefixes(null);
        this.inner().setDestinationApplicationSecurityGroups(null);
        return this;
    }

    @Override
    public NetworkSecurityRuleImpl toPort(int port) {
        this.inner().setDestinationPortRange(String.valueOf(port));
        this.inner().setDestinationPortRanges(null);
        return this;
    }

    @Override
    public NetworkSecurityRuleImpl toAnyPort() {
        this.inner().setDestinationPortRange("*");
        this.inner().setDestinationPortRanges(null);
        return this;
    }

    @Override
    public NetworkSecurityRuleImpl toPortRange(int from, int to) {
        this.inner().setDestinationPortRange(String.valueOf(from) + "-" + String.valueOf(to));
        this.inner().setDestinationPortRanges(null);
        return this;
    }

    @Override
    public NetworkSecurityRuleImpl toPortRanges(String... ranges) {
        this.inner().setDestinationPortRanges(Arrays.asList(ranges));
        this.inner().setDestinationPortRange(null);
        return this;
    }

    @Override
    public NetworkSecurityRuleImpl withPriority(int priority) {
        if (priority < 100 || priority > 4096) {
            throw new IllegalArgumentException("The priority number of a network security rule must be between 100 and 4096.");
        }

        this.inner().setPriority(priority);
        return this;
    }

    @Override
    public NetworkSecurityRuleImpl withDescription(String description) {
        this.inner().setDescription(description);
        return this;
    }

    @Override
    public NetworkSecurityRuleImpl withSourceApplicationSecurityGroup(String id) {
        sourceAsgs.put(id, new ApplicationSecurityGroupInner().withId(id));
        inner().setSourceAddressPrefix(null);
        inner().setSourceAddressPrefixes(null);
        return this;
    }

    @Override
    public NetworkSecurityRuleImpl withDestinationApplicationSecurityGroup(String id) {
        destinationAsgs.put(id, new ApplicationSecurityGroupInner().withId(id));
        inner().setDestinationAddressPrefix(null);
        inner().setDestinationAddressPrefixes(null);
        return this;
    }

    // Helpers

    private NetworkSecurityRuleImpl withDirection(SecurityRuleDirection direction) {
        this.inner().setDirection(direction);
        return this;
    }

    private NetworkSecurityRuleImpl withAccess(SecurityRuleAccess permission) {
        this.inner().setAccess(permission);
        return this;
    }


    // Verbs

    @Override
    public NetworkSecurityGroupImpl attach() {
        inner().setSourceApplicationSecurityGroups(new ArrayList<>(sourceAsgs.values()));
        inner().setDestinationApplicationSecurityGroups(new ArrayList<>(destinationAsgs.values()));
        return this.parent().withRule(this);
    }

    @Override
    public String description() {
        return this.inner().getDescription();
    }
}
