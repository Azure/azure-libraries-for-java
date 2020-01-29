/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.core.management.CloudException;
import com.azure.core.management.SubResource;
import com.azure.management.network.AddressSpace;
import com.azure.management.network.DdosProtectionPlan;
import com.azure.management.network.DhcpOptions;
import com.azure.management.network.Network;
import com.azure.management.network.NetworkPeerings;
import com.azure.management.network.Subnet;
import com.azure.management.network.models.GroupableParentResourceWithTagsImpl;
import com.azure.management.network.models.IPAddressAvailabilityResultInner;
import com.azure.management.network.models.SubnetInner;
import com.azure.management.network.models.VirtualNetworkInner;
import com.azure.management.resources.fluentcore.model.Creatable;
import com.azure.management.resources.fluentcore.utils.SdkContext;
import com.azure.management.resources.fluentcore.utils.Utils;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Implementation for Network and its create and update interfaces.
 */
class NetworkImpl
        extends GroupableParentResourceWithTagsImpl<
        Network,
        VirtualNetworkInner,
        NetworkImpl,
        NetworkManager>
        implements
        Network,
        Network.Definition,
        Network.Update {

    private Map<String, Subnet> subnets;
    private NetworkPeeringsImpl peerings;
    private Creatable<DdosProtectionPlan> ddosProtectionPlanCreatable;

    NetworkImpl(String name,
                final VirtualNetworkInner innerModel,
                final NetworkManager networkManager) {
        super(name, innerModel, networkManager);
    }

    @Override
    protected void initializeChildrenFromInner() {
        // Initialize subnets
        this.subnets = new TreeMap<>();
        List<SubnetInner> inners = this.inner().getSubnets();
        if (inners != null) {
            for (SubnetInner inner : inners) {
                SubnetImpl subnet = new SubnetImpl(inner, this);
                this.subnets.put(inner.getName(), subnet);
            }
        }

        this.peerings = new NetworkPeeringsImpl(this);
    }

    // Verbs

    @Override
    public Mono<Network> refreshAsync() {
        return super.refreshAsync().map(network -> {
            NetworkImpl impl = (NetworkImpl) network;
            impl.initializeChildrenFromInner();
            return impl;
        });
    }

    @Override
    protected Mono<VirtualNetworkInner> getInnerAsync() {
        return this.manager().inner().virtualNetworks().getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    protected Mono<VirtualNetworkInner> applyTagsToInnerAsync() {
        return this.manager().inner().virtualNetworks().updateTagsAsync(resourceGroupName(), name(), inner().getTags());
    }

    @Override
    public boolean isPrivateIPAddressAvailable(String ipAddress) {
        IPAddressAvailabilityResultInner result = checkIPAvailability(ipAddress);
        return (result != null) ? result.isAvailable() : false;
    }

    @Override
    public boolean isPrivateIPAddressInNetwork(String ipAddress) {
        IPAddressAvailabilityResultInner result = checkIPAvailability(ipAddress);
        return (result != null) ? true : false;
    }

    // Helpers

    private IPAddressAvailabilityResultInner checkIPAvailability(String ipAddress) {
        if (ipAddress == null) {
            return null;
        }
        IPAddressAvailabilityResultInner result = null;
        try {
            result = this.manager().networks().inner().checkIPAddressAvailability(
                    this.resourceGroupName(),
                    this.name(),
                    ipAddress);
        } catch (CloudException e) {
            if (!e.body().code().equalsIgnoreCase("PrivateIPAddressNotInAnySubnet")) {
                throw e; // Rethrow if the exception reason is anything other than IP address not found
            }
        }
        return result;
    }

    NetworkImpl withSubnet(SubnetImpl subnet) {
        this.subnets.put(subnet.name(), subnet);
        return this;
    }

    // Setters (fluent)

    @Override
    public NetworkImpl withDnsServer(String ipAddress) {
        if (this.inner().getDhcpOptions() == null) {
            this.inner().setDhcpOptions(new DhcpOptions());
        }

        if (this.inner().getDhcpOptions().getDnsServers() == null) {
            this.inner().getDhcpOptions().setDnsServers(new ArrayList<String>());
        }

        this.inner().getDhcpOptions().getDnsServers().add(ipAddress);
        return this;
    }

    @Override
    public NetworkImpl withSubnet(String name, String cidr) {
        return this.defineSubnet(name)
                .withAddressPrefix(cidr)
                .attach();
    }

    @Override
    public NetworkImpl withSubnets(Map<String, String> nameCidrPairs) {
        this.subnets.clear();
        for (Entry<String, String> pair : nameCidrPairs.entrySet()) {
            this.withSubnet(pair.getKey(), pair.getValue());
        }
        return this;
    }

    @Override
    public NetworkImpl withoutSubnet(String name) {
        this.subnets.remove(name);
        return this;
    }

    @Override
    public NetworkImpl withAddressSpace(String cidr) {
        if (this.inner().getAddressSpace() == null) {
            this.inner().setAddressSpace(new AddressSpace());
        }

        if (this.inner().getAddressSpace().getAddressPrefixes() == null) {
            this.inner().getAddressSpace().setAddressPrefixes(new ArrayList<String>());
        }

        this.inner().getAddressSpace().getAddressPrefixes().add(cidr);
        return this;
    }

    @Override
    public SubnetImpl defineSubnet(String name) {
        SubnetInner inner = new SubnetInner()
                .setName(name);
        return new SubnetImpl(inner, this);
    }

    @Override
    public NetworkImpl withoutAddressSpace(String cidr) {
        if (cidr == null) {
            // Skip
        } else if (this.inner().getAddressSpace() == null) {
            // Skip
        } else if (this.inner().getAddressSpace().getAddressPrefixes() == null) {
            // Skip
        } else {
            this.inner().getAddressSpace().getAddressPrefixes().remove(cidr);
        }
        return this;
    }

    // Getters

    @Override
    public List<String> addressSpaces() {
        List<String> addressSpaces = new ArrayList<String>();
        if (this.inner().getAddressSpace() == null) {
            return Collections.unmodifiableList(addressSpaces);
        } else if (this.inner().getAddressSpace().getAddressPrefixes() == null) {
            return Collections.unmodifiableList(addressSpaces);
        } else {
            return Collections.unmodifiableList(this.inner().getAddressSpace().getAddressPrefixes());
        }
    }

    @Override
    public List<String> dnsServerIPs() {
        List<String> ips = new ArrayList<String>();
        if (this.inner().getDhcpOptions() == null) {
            return Collections.unmodifiableList(ips);
        } else if (this.inner().getDhcpOptions().getDnsServers() == null) {
            return Collections.unmodifiableList(ips);
        } else {
            return this.inner().getDhcpOptions().getDnsServers();
        }
    }

    @Override
    public Map<String, Subnet> subnets() {
        return Collections.unmodifiableMap(this.subnets);
    }

    @Override
    protected void beforeCreating() {
        // Ensure address spaces
        if (this.addressSpaces().size() == 0) {
            this.withAddressSpace("10.0.0.0/16");
        }

        if (isInCreateMode()) {
            // Create a subnet as needed, covering the entire first address space
            if (this.subnets.size() == 0) {
                this.withSubnet("subnet1", this.addressSpaces().get(0));
            }
        }

        // Reset and update subnets
        this.inner().setSubnets(innersFromWrappers(this.subnets.values()));
    }

    @Override
    protected void afterCreating() {
        initializeChildrenFromInner();
    }

    @Override
    public SubnetImpl updateSubnet(String name) {
        return (SubnetImpl) this.subnets.get(name);
    }

    @Override
    protected Mono<VirtualNetworkInner> createInner() {
        if (ddosProtectionPlanCreatable != null && this.taskResult(ddosProtectionPlanCreatable.key()) != null) {
            DdosProtectionPlan ddosProtectionPlan = this.<DdosProtectionPlan>taskResult(ddosProtectionPlanCreatable.key());
            withExistingDdosProtectionPlan(ddosProtectionPlan.id());
        }
        return this.manager().inner().virtualNetworks().createOrUpdateAsync(this.resourceGroupName(), this.name(), this.inner())
                .map(virtualNetworkInner -> {
                    NetworkImpl.this.ddosProtectionPlanCreatable = null;
                    return virtualNetworkInner;
                });
    }

    @Override
    public NetworkPeerings peerings() {
        return this.peerings;
    }

    @Override
    public boolean isDdosProtectionEnabled() {
        return Utils.toPrimitiveBoolean(inner().isEnableDdosProtection());
    }

    @Override
    public boolean isVmProtectionEnabled() {
        return Utils.toPrimitiveBoolean(inner().isEnableVmProtection());
    }

    @Override
    public String ddosProtectionPlanId() {
        return inner().getDdosProtectionPlan() == null ? null : inner().getDdosProtectionPlan().getId();
    }

    @Override
    public NetworkImpl withNewDdosProtectionPlan() {
        inner().setEnableDdosProtection(true);
        DdosProtectionPlan.DefinitionStages.WithGroup ddosProtectionPlanWithGroup = manager().ddosProtectionPlans()
                .define(SdkContext.randomResourceName(name(), 20))
                .withRegion(region());
        if (super.creatableGroup != null && isInCreateMode()) {
            ddosProtectionPlanCreatable = ddosProtectionPlanWithGroup.withNewResourceGroup(super.creatableGroup);
        } else {
            ddosProtectionPlanCreatable = ddosProtectionPlanWithGroup.withExistingResourceGroup(resourceGroupName());
        }
        this.addDependency(ddosProtectionPlanCreatable);
        return this;
    }

    @Override
    public NetworkImpl withExistingDdosProtectionPlan(String planId) {
        inner().setEnableDdosProtection(true).setDdosProtectionPlan(new SubResource().setId(planId));
        return this;
    }

    @Override
    public NetworkImpl withoutDdosProtectionPlan() {
        inner().setEnableDdosProtection(false).setEnableDdosProtection(null);
        return this;
    }

    @Override
    public NetworkImpl withVmProtection() {
        inner().setEnableVmProtection(true);
        return this;
    }

    @Override
    public NetworkImpl withoutVmProtection() {
        inner().setEnableVmProtection(false);
        return this;
    }
}
