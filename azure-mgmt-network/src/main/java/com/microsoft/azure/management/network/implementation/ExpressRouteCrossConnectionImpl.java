/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network.implementation;

import com.microsoft.azure.management.network.ExpressRouteCircuitReference;
import com.microsoft.azure.management.network.ExpressRouteCrossConnection;
import com.microsoft.azure.management.network.ServiceProviderProvisioningState;
import com.microsoft.azure.management.network.model.GroupableParentResourceWithTagsImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import rx.Observable;
import rx.functions.Func1;

import java.util.HashMap;

public class ExpressRouteCrossConnectionImpl extends GroupableParentResourceWithTagsImpl<
        ExpressRouteCrossConnection,
        ExpressRouteCrossConnectionInner,
        ExpressRouteCrossConnectionImpl,
        NetworkManager>
        implements
        ExpressRouteCrossConnection,
        ExpressRouteCrossConnection.Definition,
        ExpressRouteCrossConnection.Update {
//    private ExpressRouteCrossConnectionPeeringsImpl peerings;
//    private Map<String, ExpressRouteCrossConnectionPeering> ExpressRouteCrossConnectionPeerings;

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
//        ExpressRouteCrossConnectionPeerings = new HashMap<>();
//        if (inner().peerings() != null) {
//            for (ExpressRouteCrossConnectionPeeringInner peering : inner().peerings()) {
//                ExpressRouteCrossConnectionPeerings.put(peering.name(),
//                        new ExpressRouteCrossConnectionPeeringImpl(this, peering, manager().inner().ExpressRouteCrossConnectionPeerings(), peering.peeringType()));
//            }
//        }
    }

    @Override
    protected Observable<ExpressRouteCrossConnectionInner> getInnerAsync() {
        return this.manager().inner().expressRouteCrossConnections().getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public Observable<ExpressRouteCrossConnection> refreshAsync() {
        return super.refreshAsync().map(new Func1<ExpressRouteCrossConnection, ExpressRouteCrossConnection>() {
            @Override
            public ExpressRouteCrossConnection call(ExpressRouteCrossConnection ExpressRouteCrossConnection) {
                ExpressRouteCrossConnectionImpl impl = (ExpressRouteCrossConnectionImpl) ExpressRouteCrossConnection;
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
    public String provisioningState() {
        return inner().provisioningState();
    }

    // Getters

//    @Override
//    public ExpressRouteCrossConnectionPeerings peerings() {
//        if (peerings == null) {
//            peerings = new ExpressRouteCrossConnectionPeeringsImpl(this);
//        }
//        return peerings;
//    }
}
