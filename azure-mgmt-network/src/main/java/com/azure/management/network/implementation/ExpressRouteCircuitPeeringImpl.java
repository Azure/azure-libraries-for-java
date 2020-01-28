/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.management.network.ExpressRouteCircuit;
import com.azure.management.network.ExpressRouteCircuitPeering;
import com.azure.management.network.ExpressRouteCircuitPeeringConfig;
import com.azure.management.network.ExpressRoutePeeringState;
import com.azure.management.network.ExpressRoutePeeringType;
import com.azure.management.network.Ipv6ExpressRouteCircuitPeeringConfig;
import com.azure.management.network.models.ExpressRouteCircuitPeeringInner;
import com.azure.management.network.models.ExpressRouteCircuitPeeringsInner;
import com.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.azure.management.resources.fluentcore.utils.Utils;
import reactor.core.publisher.Mono;

import java.util.Arrays;

class ExpressRouteCircuitPeeringImpl extends
        CreatableUpdatableImpl<ExpressRouteCircuitPeering, ExpressRouteCircuitPeeringInner, ExpressRouteCircuitPeeringImpl>
        implements
        ExpressRouteCircuitPeering,
        ExpressRouteCircuitPeering.Definition,
        ExpressRouteCircuitPeering.Update {
    private final ExpressRouteCircuitPeeringsInner client;
    private final ExpressRouteCircuit parent;
    private ExpressRouteCircuitStatsImpl stats;

    ExpressRouteCircuitPeeringImpl(ExpressRouteCircuitImpl parent, ExpressRouteCircuitPeeringInner innerObject,
                                   ExpressRouteCircuitPeeringsInner client, ExpressRoutePeeringType type) {
        super(type.toString(), innerObject);
        this.client = client;
        this.parent = parent;
        this.stats = new ExpressRouteCircuitStatsImpl(innerObject.getStats());
        inner().setPeeringType(type);
    }

    @Override
    public ExpressRouteCircuitPeeringImpl withAdvertisedPublicPrefixes(String publicPrefix) {
        ensureMicrosoftPeeringConfig().setAdvertisedPublicPrefixes(Arrays.asList(publicPrefix));
        return this;
    }

    private ExpressRouteCircuitPeeringConfig ensureMicrosoftPeeringConfig() {
        if (inner().getMicrosoftPeeringConfig() == null) {
            inner().setMicrosoftPeeringConfig(new ExpressRouteCircuitPeeringConfig());
        }
        return inner().getMicrosoftPeeringConfig();
    }

    @Override
    public ExpressRouteCircuitPeeringImpl withPrimaryPeerAddressPrefix(String addressPrefix) {
        inner().setPrimaryPeerAddressPrefix(addressPrefix);
        return this;
    }

    @Override
    public ExpressRouteCircuitPeeringImpl withSecondaryPeerAddressPrefix(String addressPrefix) {
        inner().setSecondaryPeerAddressPrefix(addressPrefix);
        return this;
    }

    @Override
    public ExpressRouteCircuitPeeringImpl withVlanId(int vlanId) {
        inner().setVlanId(vlanId);
        return this;
    }

    @Override
    public ExpressRouteCircuitPeeringImpl withPeerAsn(long peerAsn) {
        inner().setPeerASN(peerAsn);
        return this;
    }

    @Override
    protected Mono<ExpressRouteCircuitPeeringInner> getInnerAsync() {
        return this.client.getAsync(parent.resourceGroupName(), parent.name(), name());
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().getId() == null;
    }

    @Override
    public Mono<ExpressRouteCircuitPeering> createResourceAsync() {
        return this.client.createOrUpdateAsync(parent.resourceGroupName(), parent.name(), this.name(), inner())
                .map(innerModel -> {
                    ExpressRouteCircuitPeeringImpl.this.setInner(innerModel);
                    stats = new ExpressRouteCircuitStatsImpl(innerModel.getStats());
                    parent.refresh();
                    return ExpressRouteCircuitPeeringImpl.this;
                });
    }

    // Getters

    @Override
    public String id() {
        return inner().getId();
    }

    @Override
    public ExpressRoutePeeringType peeringType() {
        return inner().getPeeringType();
    }

    @Override
    public ExpressRoutePeeringState state() {
        return inner().getState();
    }

    @Override
    public int azureAsn() {
        return Utils.toPrimitiveInt(inner().getAzureASN());
    }

    @Override
    public long peerAsn() {
        return Utils.toPrimitiveLong(inner().getPeerASN());
    }

    @Override
    public String primaryPeerAddressPrefix() {
        return inner().getPrimaryPeerAddressPrefix();
    }

    @Override
    public String secondaryPeerAddressPrefix() {
        return inner().getSecondaryPeerAddressPrefix();
    }

    @Override
    public String primaryAzurePort() {
        return inner().getPrimaryAzurePort();
    }

    @Override
    public String secondaryAzurePort() {
        return inner().getSecondaryAzurePort();
    }

    @Override
    public String sharedKey() {
        return inner().getSharedKey();
    }

    @Override
    public int vlanId() {
        return Utils.toPrimitiveInt(inner().getVlanId());
    }

    @Override
    public ExpressRouteCircuitPeeringConfig microsoftPeeringConfig() {
        return inner().getMicrosoftPeeringConfig();
    }

    @Override
    public ExpressRouteCircuitStatsImpl stats() {
        return stats;
    }

    @Override
    public String provisioningState() {
        return inner().getProvisioningState();
    }

    @Override
    public String lastModifiedBy() {
        return inner().getLastModifiedBy();
    }

    @Override
    public Ipv6ExpressRouteCircuitPeeringConfig ipv6PeeringConfig() {
        return inner().getIpv6PeeringConfig();
    }

    @Override
    public NetworkManager manager() {
        return parent.manager();
    }

    @Override
    public String resourceGroupName() {
        return parent.resourceGroupName();
    }
}
