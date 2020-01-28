/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.core.management.SubResource;
import com.azure.management.network.ExpressRouteCircuitPeeringConfig;
import com.azure.management.network.ExpressRouteCrossConnectionPeering;
import com.azure.management.network.Ipv6ExpressRouteCircuitPeeringConfig;
import com.azure.management.network.Ipv6PeeringConfig;
import com.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation for Ipv6PeeringConfig.
 */
class Ipv6PeeringConfigImpl
        extends IndexableWrapperImpl<Ipv6ExpressRouteCircuitPeeringConfig>
        implements
        Ipv6PeeringConfig,
        Ipv6PeeringConfig.Definition<ExpressRouteCrossConnectionPeering.DefinitionStages.WithCreate>,
        Ipv6PeeringConfig.UpdateDefinition<ExpressRouteCrossConnectionPeering.Update>,
        Ipv6PeeringConfig.Update {
    private final ExpressRouteCrossConnectionPeeringImpl parent;

    Ipv6PeeringConfigImpl(Ipv6ExpressRouteCircuitPeeringConfig innerObject, ExpressRouteCrossConnectionPeeringImpl parent) {
        super(innerObject);
        this.parent = parent;
    }

    @Override
    public Ipv6PeeringConfigImpl withAdvertisedPublicPrefixes(List<String> publicPrefixes) {
        ensureMicrosoftPeeringConfig().setAdvertisedPublicPrefixes(publicPrefixes);
        return this;
    }


    @Override
    public Ipv6PeeringConfigImpl withAdvertisedPublicPrefix(String publicPrefix) {
        ExpressRouteCircuitPeeringConfig peeringConfig = ensureMicrosoftPeeringConfig();
        if (peeringConfig.getAdvertisedPublicPrefixes() == null) {
            peeringConfig.setAdvertisedPublicPrefixes(new ArrayList<String>());
        }
        peeringConfig.getAdvertisedPublicPrefixes().add(publicPrefix);
        return this;
    }

    @Override
    public Ipv6PeeringConfigImpl withPrimaryPeerAddressPrefix(String addressPrefix) {
        inner().setPrimaryPeerAddressPrefix(addressPrefix);
        return this;
    }

    @Override
    public Ipv6PeeringConfigImpl withSecondaryPeerAddressPrefix(String addressPrefix) {
        inner().setSecondaryPeerAddressPrefix(addressPrefix);
        return this;
    }

    @Override
    public Ipv6PeeringConfigImpl withCustomerASN(int customerASN) {
        ensureMicrosoftPeeringConfig().setCustomerASN(customerASN);
        return this;
    }

    @Override
    public Ipv6PeeringConfigImpl withRouteFilter(String routeFilterId) {
        inner().setRouteFilter(new SubResource().setId(routeFilterId));
        return this;
    }

    @Override
    public Ipv6PeeringConfigImpl withoutRouteFilter() {
        inner().setRouteFilter(null);
        return this;
    }

    @Override
    public Ipv6PeeringConfigImpl withRoutingRegistryName(String routingRegistryName) {
        ensureMicrosoftPeeringConfig().setRoutingRegistryName(routingRegistryName);
        return this;
    }

    private ExpressRouteCircuitPeeringConfig ensureMicrosoftPeeringConfig() {
        if (inner().getMicrosoftPeeringConfig() == null) {
            inner().setMicrosoftPeeringConfig(new ExpressRouteCircuitPeeringConfig());
        }
        return inner().getMicrosoftPeeringConfig();
    }

    @Override
    public ExpressRouteCrossConnectionPeeringImpl attach() {
        return parent.attachIpv6Config(this);
    }

    @Override
    public ExpressRouteCrossConnectionPeeringImpl parent() {
        return parent;
    }
}
