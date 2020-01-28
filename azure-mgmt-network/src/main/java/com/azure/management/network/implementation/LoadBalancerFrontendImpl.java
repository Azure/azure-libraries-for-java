/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.azure.core.management.SubResource;
import com.azure.management.network.models.FrontendIPConfigurationInner;
import com.azure.management.network.models.PublicIPAddressInner;
import com.azure.management.network.models.SubnetInner;
import com.azure.management.resources.fluentcore.arm.AvailabilityZoneId;
import com.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.azure.management.resources.fluentcore.arm.models.implementation.ChildResourceImpl;
import com.azure.management.resources.fluentcore.model.Creatable;
import com.azure.management.resources.fluentcore.utils.SdkContext;
import com.azure.management.network.LoadBalancerFrontend;
import com.azure.management.network.IPAllocationMethod;
import com.azure.management.network.LoadBalancerInboundNatPool;
import com.azure.management.network.LoadBalancerInboundNatRule;
import com.azure.management.network.LoadBalancerPrivateFrontend;
import com.azure.management.network.LoadBalancerPublicFrontend;
import com.azure.management.network.LoadBalancer;
import com.azure.management.network.LoadBalancingRule;
import com.azure.management.network.Network;
import com.azure.management.network.PublicIPAddress;
import com.azure.management.network.Subnet;


/**
 *  Implementation for LoadBalancerPublicFrontend.
 */
class LoadBalancerFrontendImpl
    extends ChildResourceImpl<FrontendIPConfigurationInner, LoadBalancerImpl, LoadBalancer>
    implements
        LoadBalancerFrontend,
        LoadBalancerPrivateFrontend,
        LoadBalancerPrivateFrontend.Definition<LoadBalancer.DefinitionStages.WithCreate>,
        LoadBalancerPrivateFrontend.UpdateDefinition<LoadBalancer.Update>,
        LoadBalancerPrivateFrontend.Update,
        LoadBalancerPublicFrontend,
        LoadBalancerPublicFrontend.Definition<LoadBalancer.DefinitionStages.WithCreate>,
        LoadBalancerPublicFrontend.UpdateDefinition<LoadBalancer.Update>,
        LoadBalancerPublicFrontend.Update {

    LoadBalancerFrontendImpl(FrontendIPConfigurationInner inner, LoadBalancerImpl parent) {
        super(inner, parent);
    }

    // Getters

    @Override
    public String networkId() {
        SubResource subnetRef = this.inner().getSubnet();
        if (subnetRef != null) {
            return ResourceUtils.parentResourceIdFromResourceId(subnetRef.getId());
        } else {
            return null;
        }
    }

    @Override
    public String subnetName() {
        SubResource subnetRef = this.inner().getSubnet();
        if (subnetRef != null) {
            return ResourceUtils.nameFromResourceId(subnetRef.getId());
        } else {
            return null;
        }
    }

    @Override
    public String privateIPAddress() {
        return this.inner().getPrivateIPAddress();
    }

    @Override
    public IPAllocationMethod privateIPAllocationMethod() {
        return this.inner().getPrivateIPAllocationMethod();
    }

    @Override
    public String name() {
        return this.inner().getName();
    }

    @Override
    public String publicIPAddressId() {
        return this.inner().getPublicIPAddress().getId();
    }

    @Override
    public boolean isPublic() {
        return (this.inner().getPublicIPAddress() != null);
    }

    @Override
    public Map<String, LoadBalancingRule> loadBalancingRules() {
        final Map<String, LoadBalancingRule> rules = new TreeMap<>();
        if (this.inner().getLoadBalancingRules() != null) {
            for (SubResource innerRef : this.inner().getLoadBalancingRules()) {
                String name = ResourceUtils.nameFromResourceId(innerRef.getId());
                LoadBalancingRule rule = this.parent().loadBalancingRules().get(name);
                if (rule != null) {
                    rules.put(name, rule);
                }
            }
        }

        return Collections.unmodifiableMap(rules);
    }

    @Override
    public Map<String, LoadBalancerInboundNatPool> inboundNatPools() {
        final Map<String, LoadBalancerInboundNatPool> pools = new TreeMap<>();
        if (this.inner().getInboundNatPools() != null) {
            for (SubResource innerRef : this.inner().getInboundNatPools()) {
                String name = ResourceUtils.nameFromResourceId(innerRef.getId());
                LoadBalancerInboundNatPool pool = this.parent().inboundNatPools().get(name);
                if (pool != null) {
                    pools.put(name, pool);
                }
            }
        }

        return Collections.unmodifiableMap(pools);
    }

    @Override
    public Map<String, LoadBalancerInboundNatRule> inboundNatRules() {
        final Map<String, LoadBalancerInboundNatRule> rules = new TreeMap<>();
        if (this.inner().getInboundNatRules() != null) {
            for (SubResource innerRef : this.inner().getInboundNatRules()) {
                String name = ResourceUtils.nameFromResourceId(innerRef.getId());
                LoadBalancerInboundNatRule rule = this.parent().inboundNatRules().get(name);
                if (rule != null) {
                    rules.put(name, rule);
                }
            }
        }

        return Collections.unmodifiableMap(rules);
    }

    // Fluent setters

    @Override
    public LoadBalancerFrontendImpl withExistingSubnet(Network network, String subnetName) {
        return this.withExistingSubnet(network.id(), subnetName);
    }

    @Override
    public LoadBalancerFrontendImpl withExistingSubnet(String parentNetworkResourceId, String subnetName) {
        SubnetInner subnetRef = (SubnetInner) new SubnetInner()
                .setId(parentNetworkResourceId + "/subnets/" + subnetName);
        this.inner()
                .setSubnet(subnetRef)
                .setPublicIPAddress(null); // Ensure no conflicting public and private settings
        return this;
    }

    @Override
    public LoadBalancerFrontendImpl withAvailabilityZone(AvailabilityZoneId zoneId) {
        // Note: Zone is not updatable as of now, so this is available only during definition time.
        // Service return `ResourceAvailabilityZonesCannotBeModified` upon attempt to append a new
        // zone or remove one. Trying to remove the last one means attempt to change resource from
        // zonal to regional, which is not supported.
        //
        // Zone is supported only for internal load balancer, hence exposed only for PrivateFrontEnd
        //
        if (this.inner().getZones() == null) {
            this.inner().setZones(new ArrayList<String>());
        }
        this.inner().getZones().add(zoneId.toString());
        return this;
    }

    @Override
    public LoadBalancerFrontendImpl withExistingPublicIPAddress(PublicIPAddress pip) {
        return this.withExistingPublicIPAddress(pip.id());
    }

    @Override
    public LoadBalancerFrontendImpl withExistingPublicIPAddress(String resourceId) {
        PublicIPAddressInner pipRef = new PublicIPAddressInner().withId(resourceId);
        this.inner()
                .setPublicIPAddress(pipRef)

                // Ensure no conflicting public and private settings
                .setSubnet(null)
                .setPrivateIPAddress(null)
                .setPrivateIPAllocationMethod(null);
        return this;
    }

    @Override
    public LoadBalancerFrontendImpl withoutPublicIPAddress() {
        this.inner().setPublicIPAddress(null);
        return this;
    }

    @Override
    public LoadBalancerFrontendImpl withPrivateIPAddressDynamic() {
        this.inner()
                .setPrivateIPAddress(null)
                .setPrivateIPAllocationMethod(IPAllocationMethod.DYNAMIC)

                // Ensure no conflicting public and private settings
                .setPublicIPAddress(null);
        return this;
    }

    @Override
    public LoadBalancerFrontendImpl withPrivateIPAddressStatic(String ipAddress) {
        this.inner()
                .setPrivateIPAddress(ipAddress)
                .setPrivateIPAllocationMethod(IPAllocationMethod.STATIC)

                // Ensure no conflicting public and private settings
                .setPublicIPAddress(null);
        return this;
    }

    @Override
    public LoadBalancerFrontendImpl withNewPublicIPAddress(String leafDnsLabel) {
        this.parent().withNewPublicIPAddress(leafDnsLabel, this.name());
        return this;
    }

    @Override
    public LoadBalancerFrontendImpl withNewPublicIPAddress(Creatable<PublicIPAddress> creatable) {
        this.parent().withNewPublicIPAddress(creatable, this.name());
        return this;
    }

    @Override
    public LoadBalancerFrontendImpl withNewPublicIPAddress() {
        String dnsLabel = SdkContext.randomResourceName("fe", 20);
        return this.withNewPublicIPAddress(dnsLabel);
    }

    // Verbs

    @Override
    public LoadBalancerImpl attach() {
        return this.parent().withFrontend(this);
    }

    @Override
    public PublicIPAddress getPublicIPAddress() {
        final String pipId = this.publicIPAddressId();
        if (pipId == null) {
            return null;
        } else {
            return this.parent().manager().publicIPAddresses().getById(pipId);
        }
    }

    @Override
    public Subnet getSubnet() {
        return this.parent().manager().getAssociatedSubnet(this.inner().getSubnet());
    }

    @Override
    public Set<AvailabilityZoneId> availabilityZones() {
        Set<AvailabilityZoneId> zones = new HashSet<>();
        if (this.inner().getZones() != null) {
            for (String zone : this.inner().getZones()) {
                zones.add(AvailabilityZoneId.fromString(zone));
            }
        }
        return Collections.unmodifiableSet(zones);
    }
}

