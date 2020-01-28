/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.management.network.ExpressRouteCircuit;
import com.azure.management.network.ExpressRouteCircuitPeering;
import com.azure.management.network.ExpressRouteCircuitPeerings;
import com.azure.management.network.ExpressRouteCircuitServiceProviderProperties;
import com.azure.management.network.ExpressRouteCircuitSkuType;
import com.azure.management.network.ServiceProviderProvisioningState;
import com.azure.management.network.TagsObject;
import com.azure.management.network.models.ExpressRouteCircuitAuthorizationInner;
import com.azure.management.network.models.ExpressRouteCircuitInner;
import com.azure.management.network.models.ExpressRouteCircuitPeeringInner;
import com.azure.management.network.models.GroupableParentResourceWithTagsImpl;
import com.azure.management.resources.fluentcore.utils.Utils;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ExpressRouteCircuitImpl extends GroupableParentResourceWithTagsImpl<
        ExpressRouteCircuit,
        ExpressRouteCircuitInner,
        ExpressRouteCircuitImpl,
        NetworkManager>
        implements
        ExpressRouteCircuit,
        ExpressRouteCircuit.Definition,
        ExpressRouteCircuit.Update {
    private ExpressRouteCircuitPeeringsImpl peerings;
    private Map<String, ExpressRouteCircuitPeering> expressRouteCircuitPeerings;

    ExpressRouteCircuitImpl(String name, ExpressRouteCircuitInner innerObject, NetworkManager manager) {
        super(name, innerObject, manager);
        initializeChildrenFromInner();
    }

    @Override
    public ExpressRouteCircuitImpl withServiceProvider(String serviceProviderName) {
        ensureServiceProviderProperties().setServiceProviderName(serviceProviderName);
        return this;
    }

    @Override
    public ExpressRouteCircuitImpl withPeeringLocation(String location) {
        ensureServiceProviderProperties().setPeeringLocation(location);
        return this;
    }

    @Override
    public ExpressRouteCircuitImpl withBandwidthInMbps(int bandwidthInMbps) {
        ensureServiceProviderProperties().setBandwidthInMbps(bandwidthInMbps);
        return this;
    }

    @Override
    public ExpressRouteCircuitImpl withSku(ExpressRouteCircuitSkuType sku) {
        inner().setSku(sku.sku());
        return this;
    }


    @Override
    public ExpressRouteCircuitImpl withClassicOperations() {
        inner().setAllowClassicOperations(true);
        return this;
    }

    @Override
    public ExpressRouteCircuitImpl withoutClassicOperations() {
        inner().setAllowClassicOperations(false);
        return this;
    }

    @Override
    public ExpressRouteCircuitImpl withAuthorization(String authorizationName) {
        ensureAuthorizations().add(new ExpressRouteCircuitAuthorizationInner().setName(authorizationName));
        return this;
    }

    private List<ExpressRouteCircuitAuthorizationInner> ensureAuthorizations() {
        if (inner().getAuthorizations() == null) {
            inner().setAuthorizations(new ArrayList<ExpressRouteCircuitAuthorizationInner>());
        }
        return inner().getAuthorizations();
    }

    private ExpressRouteCircuitServiceProviderProperties ensureServiceProviderProperties() {
        if (inner().getServiceProviderProperties() == null) {
            inner().setServiceProviderProperties(new ExpressRouteCircuitServiceProviderProperties());
        }
        return inner().getServiceProviderProperties();
    }

    protected void beforeCreating() {
    }

    @Override
    protected void afterCreating() {
    }

    @Override
    protected Mono<ExpressRouteCircuitInner> createInner() {
        return this.manager().inner().expressRouteCircuits().createOrUpdateAsync(
                this.resourceGroupName(), this.name(), this.inner());
    }

    @Override
    protected void initializeChildrenFromInner() {
        expressRouteCircuitPeerings = new HashMap<>();
        if (inner().getPeerings() != null) {
            for (ExpressRouteCircuitPeeringInner peering : inner().getPeerings()) {
                expressRouteCircuitPeerings.put(peering.getName(),
                        new ExpressRouteCircuitPeeringImpl(this, peering, manager().inner().expressRouteCircuitPeerings(), peering.getPeeringType()));
            }
        }
    }

    @Override
    protected Mono<ExpressRouteCircuitInner> getInnerAsync() {
        return this.manager().inner().expressRouteCircuits().getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public Mono<ExpressRouteCircuit> refreshAsync() {
        return super.refreshAsync().map(expressRouteCircuit -> {
            ExpressRouteCircuitImpl impl = (ExpressRouteCircuitImpl) expressRouteCircuit;
            impl.initializeChildrenFromInner();
            return impl;
        });
    }

    @Override
    protected Mono<ExpressRouteCircuitInner> applyTagsToInnerAsync() {
        TagsObject parameters = new TagsObject().setTags(inner().getTags());
        return this.manager().inner().expressRouteCircuits().updateTagsAsync(resourceGroupName(), name(), parameters);
    }

    // Getters

    @Override
    public ExpressRouteCircuitPeerings peerings() {
        if (peerings == null) {
            peerings = new ExpressRouteCircuitPeeringsImpl(this);
        }
        return peerings;
    }

    @Override
    public ExpressRouteCircuitSkuType sku() {
        return ExpressRouteCircuitSkuType.fromSku(inner().getSku());
    }

    @Override
    public boolean isAllowClassicOperations() {
        return Utils.toPrimitiveBoolean(inner().isAllowClassicOperations());
    }

    @Override
    public String circuitProvisioningState() {
        return inner().getCircuitProvisioningState();
    }

    @Override
    public ServiceProviderProvisioningState serviceProviderProvisioningState() {
        return inner().getServiceProviderProvisioningState();
    }

    @Override
    public String serviceKey() {
        return inner().getServiceKey();
    }

    @Override
    public String serviceProviderNotes() {
        return inner().getServiceProviderNotes();
    }

    @Override
    public ExpressRouteCircuitServiceProviderProperties serviceProviderProperties() {
        return inner().getServiceProviderProperties();
    }

    @Override
    public String provisioningState() {
        return inner().getProvisioningState();
    }

    @Override
    public Map<String, ExpressRouteCircuitPeering> peeringsMap() {
        return expressRouteCircuitPeerings;
    }
}
