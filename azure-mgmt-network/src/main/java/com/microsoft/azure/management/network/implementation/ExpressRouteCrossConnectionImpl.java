/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network.implementation;

import com.microsoft.azure.management.network.ExpressRouteCircuitReference;
import com.microsoft.azure.management.network.ExpressRouteCrossConnection;
import com.microsoft.azure.management.network.ExpressRouteCrossConnectionPeering;
import com.microsoft.azure.management.network.ExpressRouteCrossConnectionPeerings;
import com.microsoft.azure.management.network.ProvisioningState;
import com.microsoft.azure.management.network.ServiceProviderProvisioningState;
import com.microsoft.azure.management.network.model.GroupableParentResourceWithTagsImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import rx.Observable;
import rx.functions.Func1;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation for ExpressRouteCrossConnection.
 */
public class ExpressRouteCrossConnectionImpl extends GroupableParentResourceWithTagsImpl<
        ExpressRouteCrossConnection,
        ExpressRouteCrossConnectionInner,
        ExpressRouteCrossConnectionImpl,
        NetworkManager>
        implements
        ExpressRouteCrossConnection,
        ExpressRouteCrossConnection.Update {
    private ExpressRouteCrossConnectionPeeringsImpl peerings;
    private Map<String, ExpressRouteCrossConnectionPeering> crossConnectionPeerings;

    ExpressRouteCrossConnectionImpl(String name, ExpressRouteCrossConnectionInner innerObject, NetworkManager manager) {
        super(name, innerObject, manager);
        initializeChildrenFromInner();
    }

    protected void beforeCreating() {
    }

    @Override
    protected void afterCreating() {
    }

    @Override
    protected Observable<ExpressRouteCrossConnectionInner> createInner() {
        return this.manager().inner().expressRouteCrossConnections().createOrUpdateAsync(
                this.resourceGroupName(), this.name(), this.inner());
    }

    @Override
    protected void initializeChildrenFromInner() {
        crossConnectionPeerings = new HashMap<>();
        if (inner().peerings() != null) {
            for (ExpressRouteCrossConnectionPeeringInner peering : inner().peerings()) {
                crossConnectionPeerings.put(peering.name(),
                        new ExpressRouteCrossConnectionPeeringImpl(this, peering, peering.peeringType()));
            }
        }
    }

    @Override
    protected Observable<ExpressRouteCrossConnectionInner> getInnerAsync() {
        return this.manager().inner().expressRouteCrossConnections().getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public Observable<ExpressRouteCrossConnection> refreshAsync() {
        return super.refreshAsync().map(new Func1<ExpressRouteCrossConnection, ExpressRouteCrossConnection>() {
            @Override
            public ExpressRouteCrossConnection call(ExpressRouteCrossConnection expressRouteCrossConnection) {
                ExpressRouteCrossConnectionImpl impl = (ExpressRouteCrossConnectionImpl) expressRouteCrossConnection;
                impl.initializeChildrenFromInner();
                return impl;
            }
        });
    }

    @Override
    protected Observable<ExpressRouteCrossConnectionInner> applyTagsToInnerAsync() {
        return this.manager().inner().expressRouteCrossConnections().updateTagsAsync(resourceGroupName(), name(), inner().getTags());
    }

    @Override
    public ExpressRouteCrossConnectionPeerings peerings() {
        if (peerings == null) {
            peerings = new ExpressRouteCrossConnectionPeeringsImpl(this);
        }
        return peerings;
    }

    @Override
    public String primaryAzurePort() {
        return inner().primaryAzurePort();
    }

    @Override
    public String secondaryAzurePort() {
        return inner().secondaryAzurePort();
    }

    @Override
    public Integer sTag() {
        return inner().sTag();
    }

    @Override
    public String peeringLocation() {
        return inner().peeringLocation();
    }

    @Override
    public int bandwidthInMbps() {
        return Utils.toPrimitiveInt(inner().bandwidthInMbps());
    }

    @Override
    public ExpressRouteCircuitReference expressRouteCircuit() {
        return inner().expressRouteCircuit();
    }

    @Override
    public ServiceProviderProvisioningState serviceProviderProvisioningState() {
        return inner().serviceProviderProvisioningState();
    }

    @Override
    public String serviceProviderNotes() {
        return inner().serviceProviderNotes();
    }

    @Override
    public ProvisioningState provisioningState() {
        return inner().provisioningState();
    }

    @Override
    public Map<String, ExpressRouteCrossConnectionPeering> peeringsMap() {
        return Collections.unmodifiableMap(crossConnectionPeerings);
    }

    @Override
    public Update withServiceProviderProvisioningState(ServiceProviderProvisioningState state) {
        inner().withServiceProviderProvisioningState(state);
        return this;
    }

    @Override
    public Update withServiceProviderNotes(String notes) {
        inner().withServiceProviderNotes(notes);
        return this;
    }
}
