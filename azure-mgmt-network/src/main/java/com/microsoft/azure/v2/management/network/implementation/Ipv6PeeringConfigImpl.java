/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.network.ExpressRouteCircuitPeeringConfig;
import com.microsoft.azure.v2.management.network.ExpressRouteCrossConnectionPeering;
import com.microsoft.azure.v2.management.network.Ipv6PeeringConfig;
import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

import java.util.ArrayList;
import java.util.List;

/**
 *  Implementation for Ipv6PeeringConfig.
 */
@LangDefinition
class Ipv6PeeringConfigImpl
        extends IndexableWrapperImpl<Ipv6ExpressRouteCircuitPeeringConfigInner>
        implements
        Ipv6PeeringConfig,
        Ipv6PeeringConfig.Definition<ExpressRouteCrossConnectionPeering.DefinitionStages.WithCreate>,
        Ipv6PeeringConfig.UpdateDefinition<ExpressRouteCrossConnectionPeering.Update>,
        Ipv6PeeringConfig.Update {
    private final ExpressRouteCrossConnectionPeeringImpl parent;
    Ipv6PeeringConfigImpl(Ipv6ExpressRouteCircuitPeeringConfigInner innerObject, ExpressRouteCrossConnectionPeeringImpl parent) {
        super(innerObject);
        this.parent = parent;
    }

    @Override
    public Ipv6PeeringConfigImpl withAdvertisedPublicPrefixes(List<String> publicPrefixes) {
        ensureMicrosoftPeeringConfig().withAdvertisedPublicPrefixes(publicPrefixes);
        return this;
    }


    @Override
    public Ipv6PeeringConfigImpl withAdvertisedPublicPrefix(String publicPrefix) {
        ExpressRouteCircuitPeeringConfig peeringConfig = ensureMicrosoftPeeringConfig();
        if (peeringConfig.advertisedPublicPrefixes() == null) {
            peeringConfig.withAdvertisedPublicPrefixes(new ArrayList<String>());
        }
        peeringConfig.advertisedPublicPrefixes().add(publicPrefix);
        return this;
    }

    @Override
    public Ipv6PeeringConfigImpl withPrimaryPeerAddressPrefix(String addressPrefix) {
        inner().withPrimaryPeerAddressPrefix(addressPrefix);
        return this;
    }

    @Override
    public Ipv6PeeringConfigImpl withSecondaryPeerAddressPrefix(String addressPrefix) {
        inner().withSecondaryPeerAddressPrefix(addressPrefix);
        return this;
    }

    @Override
    public Ipv6PeeringConfigImpl withCustomerASN(int customerASN) {
        ensureMicrosoftPeeringConfig().withCustomerASN(customerASN);
        return this;
    }

    @Override
    public Ipv6PeeringConfigImpl withRouteFilter(String routeFilterId) {
        inner().withRouteFilter(new RouteFilterInner().withId(routeFilterId));
        return this;
    }

    @Override
    public Ipv6PeeringConfigImpl withoutRouteFilter() {
        inner().withRouteFilter(null);
        return this;
    }

    @Override
    public Ipv6PeeringConfigImpl withRoutingRegistryName(String routingRegistryName) {
        ensureMicrosoftPeeringConfig().withRoutingRegistryName(routingRegistryName);
        return this;
    }

    private ExpressRouteCircuitPeeringConfig ensureMicrosoftPeeringConfig() {
        if (inner().microsoftPeeringConfig() == null) {
            inner().withMicrosoftPeeringConfig(new ExpressRouteCircuitPeeringConfig());
        }
        return inner().microsoftPeeringConfig();
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
