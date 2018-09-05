/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network.implementation;

import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.network.Network;
import com.microsoft.azure.v2.management.network.NetworkPeering;
import com.microsoft.azure.v2.management.network.NetworkPeerings;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.IndependentChildrenImpl;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 *  Implementation for network peerings.
 */
@LangDefinition
class NetworkPeeringsImpl
    extends IndependentChildrenImpl<
        NetworkPeering,
        NetworkPeeringImpl,
        VirtualNetworkPeeringInner,
        VirtualNetworkPeeringsInner,
        NetworkManager,
        Network>

    implements NetworkPeerings {

    private final NetworkImpl network;

    // Constructor to use from the context of a parent
    NetworkPeeringsImpl(final NetworkImpl parent) {
        super(parent.manager().inner().virtualNetworkPeerings(), parent.manager());
        this.network = parent;
    }

    @Override
    public NetworkPeeringImpl define(String name) {
        return wrapModel(name);
    }

    // Fluent model create helpers

    @Override
    protected NetworkPeeringImpl wrapModel(String name) {
        VirtualNetworkPeeringInner inner = new VirtualNetworkPeeringInner().withName(name);
        return new NetworkPeeringImpl(inner, this.network);
    }

    @Override
    protected NetworkPeeringImpl wrapModel(VirtualNetworkPeeringInner inner) {
        return (inner != null) ? new NetworkPeeringImpl(inner, this.network) : null;
    }

    @Override
    public Completable deleteByParentAsync(String groupName, String parentName, final String name) {
        return this.manager().networks()
                // Get the parent network of the peering to delete
                .getByResourceGroupAsync(groupName, parentName)
                // Then find the local peering to delete
                .flatMap(localNetwork -> {
                    String peeringId = localNetwork.id() + "/peerings/" + name;
                    return localNetwork.peerings().getByIdAsync(peeringId);
                })
                // Then get the remote peering if available and possible to delete
                .flatMapObservable(localPeering -> {
                    if (!localPeering.isSameSubscription()) {
                        return Observable.just(localPeering);
                    } else {
                        return Observable.just(localPeering).concatWith(localPeering.getRemotePeeringAsync());
                    }
                })
                // Then delete each peering (this will be called for each of the peerings, so at least once for the local peering, and second time for the remote one if any
                .flatMapCompletable(peering -> {
                    String networkName = ResourceUtils.nameFromResourceId(peering.networkId());
                    return peering.manager().inner().virtualNetworkPeerings().deleteAsync(
                            peering.resourceGroupName(),
                            networkName,
                            peering.name());
                });
   }

    @Override
    public Maybe<NetworkPeering> getByParentAsync(String resourceGroup, String parentName, String name) {
        return this.inner().getAsync(resourceGroup, parentName, name)
                .map(inner -> wrapModel(inner));
    }

    @Override
    public PagedList<NetworkPeering> listByParent(String resourceGroupName, String parentName) {
        return wrapList(this.inner().list(resourceGroupName, parentName));
    }

    @Override
    public PagedList<NetworkPeering> list() {
        return this.wrapList(this.inner().list(this.network.resourceGroupName(), this.network.name()));
    }

    @Override
    public Observable<NetworkPeering> listAsync() {
        return this.wrapPageAsync(this.inner().listAsync(this.network.resourceGroupName(), this.network.name()));
    }

    @Override
    public NetworkPeering getByRemoteNetwork(Network network) {
        return (network != null) ? this.getByRemoteNetwork(network.id()) : null;
    }

    @Override
    public NetworkPeering getByRemoteNetwork(String remoteNetworkResourceId) {
        if (remoteNetworkResourceId != null) {
            for (NetworkPeering peering : this.list()) {
                if (peering.remoteNetworkId().equalsIgnoreCase(remoteNetworkResourceId)) {
                    return peering;
                }
            }
        }
        return null;
    }

    @Override
    public Observable<NetworkPeering> getByRemoteNetworkAsync(Network network) {
        if (network != null) {
            return this.getByRemoteNetworkAsync(network.id());
        } else {
            return Observable.just(null);
        }
    }

    @Override
    public Observable<NetworkPeering> getByRemoteNetworkAsync(final String remoteNetworkResourceId) {
        if (remoteNetworkResourceId == null) {
            return Observable.empty();
        } else {
            return this.listAsync()
                    .filter(peering -> {
                        if (peering == null) {
                            return false;
                        } else {
                            return remoteNetworkResourceId.equalsIgnoreCase(peering.remoteNetworkId());
                        }
                    });
        }
    }
}
