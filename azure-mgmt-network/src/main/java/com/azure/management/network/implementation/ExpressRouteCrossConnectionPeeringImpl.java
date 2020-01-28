/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.management.network.ExpressRouteCircuitPeeringConfig;
import com.azure.management.network.ExpressRouteCrossConnection;
import com.azure.management.network.ExpressRouteCrossConnectionPeering;
import com.azure.management.network.ExpressRoutePeeringState;
import com.azure.management.network.ExpressRoutePeeringType;
import com.azure.management.network.Ipv6ExpressRouteCircuitPeeringConfig;
import com.azure.management.network.models.ExpressRouteCrossConnectionPeeringInner;
import com.azure.management.network.models.ExpressRouteCrossConnectionPeeringsInner;
import com.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.azure.management.resources.fluentcore.utils.Utils;
import reactor.core.publisher.Mono;

import java.util.Arrays;

class ExpressRouteCrossConnectionPeeringImpl extends
        CreatableUpdatableImpl<ExpressRouteCrossConnectionPeering, ExpressRouteCrossConnectionPeeringInner, ExpressRouteCrossConnectionPeeringImpl>
        implements
        ExpressRouteCrossConnectionPeering,
        ExpressRouteCrossConnectionPeering.Definition,
        ExpressRouteCrossConnectionPeering.Update {
    private final ExpressRouteCrossConnectionPeeringsInner client;
    private final ExpressRouteCrossConnection parent;

    ExpressRouteCrossConnectionPeeringImpl(ExpressRouteCrossConnectionImpl parent, ExpressRouteCrossConnectionPeeringInner innerObject, ExpressRoutePeeringType type) {
        super(type.toString(), innerObject);
        this.client = parent.manager().inner().expressRouteCrossConnectionPeerings();
        this.parent = parent;
        inner().setPeeringType(type);
    }

    @Override
    public ExpressRouteCrossConnectionPeeringImpl withAdvertisedPublicPrefixes(String publicPrefix) {
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
    public ExpressRouteCrossConnectionPeeringImpl withPrimaryPeerAddressPrefix(String addressPrefix) {
        inner().setPrimaryPeerAddressPrefix(addressPrefix);
        return this;
    }

    @Override
    public ExpressRouteCrossConnectionPeeringImpl withSecondaryPeerAddressPrefix(String addressPrefix) {
        inner().setSecondaryPeerAddressPrefix(addressPrefix);
        return this;
    }

    @Override
    public ExpressRouteCrossConnectionPeeringImpl withVlanId(int vlanId) {
        inner().setVlanId(vlanId);
        return this;
    }

    @Override
    public ExpressRouteCrossConnectionPeeringImpl withPeerAsn(long peerAsn) {
        inner().setPeerASN(peerAsn);
        return this;
    }

    @Override
    public DefinitionStages.WithCreate withSharedKey(String sharedKey) {
        inner().setSharedKey(sharedKey);
        return this;
    }


    @Override
    public Ipv6PeeringConfigImpl defineIpv6Config() {
        return new Ipv6PeeringConfigImpl(new Ipv6ExpressRouteCircuitPeeringConfig(), this);
    }

    @Override
    public ExpressRouteCrossConnectionPeeringImpl withoutIpv6Config() {
        inner().setIpv6PeeringConfig(null);
        return this;
    }

    ExpressRouteCrossConnectionPeeringImpl attachIpv6Config(Ipv6PeeringConfigImpl ipv6PeeringConfig) {
        inner().setIpv6PeeringConfig(ipv6PeeringConfig.inner());
        return this;
    }

    @Override
    public ExpressRouteCrossConnectionPeeringImpl withCustomerASN(int customerASN) {
        ensureMicrosoftPeeringConfig().setCustomerASN(customerASN);
        return this;
    }

    @Override
    public ExpressRouteCrossConnectionPeeringImpl withRoutingRegistryName(String routingRegistryName) {
        ensureMicrosoftPeeringConfig().setRoutingRegistryName(routingRegistryName);
        return this;
    }

    @Override
    public ExpressRouteCrossConnectionPeeringImpl withState(ExpressRoutePeeringState state) {
        inner().setState(state);
        return this;
    }

    @Override
    protected Mono<ExpressRouteCrossConnectionPeeringInner> getInnerAsync() {
        return this.client.getAsync(parent.resourceGroupName(), parent.name(), name());
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().getId() == null;
    }

    @Override
    public Mono<ExpressRouteCrossConnectionPeering> createResourceAsync() {
        return this.client.createOrUpdateAsync(parent.resourceGroupName(), parent.name(), this.name(), inner())
                .map(innerModel -> {
                    ExpressRouteCrossConnectionPeeringImpl.this.setInner(innerModel);
                    parent.refresh();
                    return ExpressRouteCrossConnectionPeeringImpl.this;
                });
    }

    // Getters

    @Override
    public String id() {
        return inner().getId();
    }

    @Override
    public NetworkManager manager() {
        return parent.manager();
    }

    @Override
    public String resourceGroupName() {
        return parent.resourceGroupName();
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
    public int azureASN() {
        return Utils.toPrimitiveInt(inner().getAzureASN());
    }

    @Override
    public long peerASN() {
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
    public String provisioningState() {
        return inner().getProvisioningState();
    }

    @Override
    public String gatewayManagerEtag() {
        return inner().getGatewayManagerEtag();
    }

    @Override
    public String lastModifiedBy() {
        return inner().getLastModifiedBy();
    }

    @Override
    public Ipv6ExpressRouteCircuitPeeringConfig ipv6PeeringConfig() {
        return inner().getIpv6PeeringConfig();
    }
}
