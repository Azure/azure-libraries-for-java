/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network.implementation;

import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.network.ExpressRouteCircuit;
import com.microsoft.azure.v2.management.network.ExpressRouteCircuitPeering;
import com.microsoft.azure.v2.management.network.ExpressRouteCircuitPeerings;
import com.microsoft.azure.v2.management.network.ExpressRoutePeeringType;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.IndependentChildrenImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.PagedListConverter;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Represents Express Route Circuit Peerings collection associated with Network Watcher.
 */
@LangDefinition
class ExpressRouteCircuitPeeringsImpl extends IndependentChildrenImpl<
        ExpressRouteCircuitPeering,
        ExpressRouteCircuitPeeringImpl,
        ExpressRouteCircuitPeeringInner,
        ExpressRouteCircuitPeeringsInner,
        NetworkManager,
        ExpressRouteCircuit>
        implements ExpressRouteCircuitPeerings {
    private final ExpressRouteCircuitImpl parent;

    /**
     * Creates a new ExpressRouteCircuitPeeringsImpl.
     *
     * @param parent the Express Route Circuit associated with ExpressRouteCircuitPeering
     */
    ExpressRouteCircuitPeeringsImpl(ExpressRouteCircuitImpl parent) {
        super(parent.manager().inner().expressRouteCircuitPeerings(), parent.manager());
        this.parent = parent;
    }

    @Override
    public final PagedList<ExpressRouteCircuitPeering> list() {
        return (new PagedListConverter<ExpressRouteCircuitPeeringInner, ExpressRouteCircuitPeering>() {
            @Override
            public Observable<ExpressRouteCircuitPeering> typeConvertAsync(ExpressRouteCircuitPeeringInner inner) {
                return Observable.just((ExpressRouteCircuitPeering) wrapModel(inner));
            }
        }).convert(ReadableWrappersImpl.convertToPagedList(inner().list(parent.resourceGroupName(), parent.name())));
    }

    /**
     * @return an observable emits packet captures in this collection
     */
    @Override
    public Observable<ExpressRouteCircuitPeering> listAsync() {
        return wrapPageAsync(inner().listAsync(parent.resourceGroupName(), parent.name()));
    }

    @Override
    protected ExpressRouteCircuitPeeringImpl wrapModel(String name) {
        return new ExpressRouteCircuitPeeringImpl(parent, new ExpressRouteCircuitPeeringInner(), inner(), ExpressRoutePeeringType.fromString(name));
    }

    protected ExpressRouteCircuitPeeringImpl wrapModel(ExpressRouteCircuitPeeringInner inner) {
        return (inner == null) ? null : new ExpressRouteCircuitPeeringImpl(parent, inner, inner(), inner.peeringType());
    }

    @Override
    public ExpressRouteCircuitPeeringImpl defineAzurePrivatePeering() {
        return new ExpressRouteCircuitPeeringImpl(parent, new ExpressRouteCircuitPeeringInner(), inner(), ExpressRoutePeeringType.AZURE_PRIVATE_PEERING);
    }

    @Override
    public ExpressRouteCircuitPeeringImpl defineAzurePublicPeering() {
        return new ExpressRouteCircuitPeeringImpl(parent, new ExpressRouteCircuitPeeringInner(), inner(), ExpressRoutePeeringType.AZURE_PUBLIC_PEERING);
    }

    @Override
    public ExpressRouteCircuitPeeringImpl defineMicrosoftPeering() {
        return new ExpressRouteCircuitPeeringImpl(parent, new ExpressRouteCircuitPeeringInner(), inner(), ExpressRoutePeeringType.MICROSOFT_PEERING);
    }

    @Override
    public Observable<ExpressRouteCircuitPeering> getByNameAsync(String name) {
        return inner().getAsync(parent.resourceGroupName(), parent.name(), name)
                .map(inner -> (ExpressRouteCircuitPeering) wrapModel(inner))
                .toObservable();
    }

    @Override
    public ExpressRouteCircuitPeering getByName(String name) {
        return getByNameAsync(name).blockingLast();
    }

    @Override
    public void deleteByName(String name) {
        deleteByNameAsync(name).blockingAwait();
    }

    @Override
    public ServiceFuture<Void> deleteByNameAsync(String name, ServiceCallback<Void> callback) {
        return this.inner().deleteAsync(parent.resourceGroupName(),
                parent.name(),
                name,
                callback);
    }

    @Override
    public Completable deleteByNameAsync(String name) {
        return this.inner().deleteAsync(parent.resourceGroupName(), parent.name(), name);
    }

    @Override
    public ExpressRouteCircuit parent() {
        return parent;
    }

    @Override
    public Completable deleteByParentAsync(String groupName, String parentName, String name) {
        return this.inner().deleteAsync(groupName, parentName, name);
    }

    @Override
    public Maybe<ExpressRouteCircuitPeering> getByParentAsync(String resourceGroup, String parentName, String name) {
        return inner().getAsync(resourceGroup, parentName, name)
                .map(inner -> wrapModel(inner));
    }

    @Override
    public PagedList<ExpressRouteCircuitPeering> listByParent(String resourceGroupName, String parentName) {
        return wrapList(this.inner().list(resourceGroupName, parentName));
    }
}