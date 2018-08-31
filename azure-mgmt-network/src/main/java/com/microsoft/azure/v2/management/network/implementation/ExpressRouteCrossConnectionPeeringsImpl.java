/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.network.ExpressRouteCrossConnection;
import com.microsoft.azure.v2.management.network.ExpressRouteCrossConnectionPeering;
import com.microsoft.azure.v2.management.network.ExpressRouteCrossConnectionPeerings;
import com.microsoft.azure.v2.management.network.ExpressRoutePeeringType;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.IndependentChildrenImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.PagedListConverter;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

/**
 * Represents Express Route Cross Connection Peerings collection associated with Network Watcher.
 */
@LangDefinition
class ExpressRouteCrossConnectionPeeringsImpl extends IndependentChildrenImpl<
        ExpressRouteCrossConnectionPeering,
        ExpressRouteCrossConnectionPeeringImpl,
        ExpressRouteCrossConnectionPeeringInner,
        ExpressRouteCrossConnectionPeeringsInner,
        NetworkManager,
        ExpressRouteCrossConnection>
        implements ExpressRouteCrossConnectionPeerings {
    private final ExpressRouteCrossConnectionImpl parent;

    /**
     * Creates a new ExpressRouteCrossConnectionPeeringsImpl.
     *
     * @param parent the Express Route Circuit associated with ExpressRouteCrossConnectionPeering
     */
    ExpressRouteCrossConnectionPeeringsImpl(ExpressRouteCrossConnectionImpl parent) {
        super(parent.manager().inner().expressRouteCrossConnectionPeerings(), parent.manager());
        this.parent = parent;
    }

    @Override
    public final PagedList<ExpressRouteCrossConnectionPeering> list() {
        return (new PagedListConverter<ExpressRouteCrossConnectionPeeringInner, ExpressRouteCrossConnectionPeering>() {
            @Override
            public Observable<ExpressRouteCrossConnectionPeering> typeConvertAsync(ExpressRouteCrossConnectionPeeringInner inner) {
                return Observable.just((ExpressRouteCrossConnectionPeering) wrapModel(inner));
            }
        }).convert(ReadableWrappersImpl.convertToPagedList(inner().list(parent.resourceGroupName(), parent.name())));
    }

    /**
     * @return an observable emits packet captures in this collection
     */
    @Override
    public Observable<ExpressRouteCrossConnectionPeering> listAsync() {
        return wrapPageAsync(inner().listAsync(parent.resourceGroupName(), parent.name()));
    }

    @Override
    protected ExpressRouteCrossConnectionPeeringImpl wrapModel(String name) {
        return new ExpressRouteCrossConnectionPeeringImpl(parent, new ExpressRouteCrossConnectionPeeringInner(), ExpressRoutePeeringType.fromString(name));
    }

    protected ExpressRouteCrossConnectionPeeringImpl wrapModel(ExpressRouteCrossConnectionPeeringInner inner) {
        return (inner == null) ? null : new ExpressRouteCrossConnectionPeeringImpl(parent, inner, inner.peeringType());
    }

    @Override
    public ExpressRouteCrossConnectionPeeringImpl defineAzurePrivatePeering() {
        return new ExpressRouteCrossConnectionPeeringImpl(parent, new ExpressRouteCrossConnectionPeeringInner(), ExpressRoutePeeringType.AZURE_PRIVATE_PEERING);
    }

    @Override
    public ExpressRouteCrossConnectionPeeringImpl defineMicrosoftPeering() {
        return new ExpressRouteCrossConnectionPeeringImpl(parent, new ExpressRouteCrossConnectionPeeringInner(), ExpressRoutePeeringType.MICROSOFT_PEERING);
    }

    @Override
    public Observable<ExpressRouteCrossConnectionPeering> getByNameAsync(String name) {
        return inner().getAsync(parent.resourceGroupName(), parent.name(), name)
                .map(new Func1<ExpressRouteCrossConnectionPeeringInner, ExpressRouteCrossConnectionPeering>() {
                    @Override
                    public ExpressRouteCrossConnectionPeering call(ExpressRouteCrossConnectionPeeringInner inner) {
                        return wrapModel(inner);
                    }
                });
    }

    @Override
    public ExpressRouteCrossConnectionPeering getByName(String name) {
        return getByNameAsync(name).toBlocking().last();
    }

    @Override
    public void deleteByName(String name) {
        deleteByNameAsync(name).await();
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
        return deleteByParentAsync(parent.resourceGroupName(), parent.name(), name);
    }

    @Override
    public ExpressRouteCrossConnection parent() {
        return parent;
    }

    @Override
    public Completable deleteByParentAsync(String groupName, String parentName, String name) {
        return this.inner().deleteAsync(groupName, parentName, name)
                .map(new Func1<Void, Void>() {
                    @Override
                    public Void call(Void result) {
                        parent.refresh();
                        return result;
                    }
                }).toCompletable();
    }

    @Override
    public Observable<ExpressRouteCrossConnectionPeering> getByParentAsync(String resourceGroup, String parentName, String name) {
        return inner().getAsync(resourceGroup, parentName, name)
                .map(new Func1<ExpressRouteCrossConnectionPeeringInner, ExpressRouteCrossConnectionPeering>() {
                    @Override
                    public ExpressRouteCrossConnectionPeering call(ExpressRouteCrossConnectionPeeringInner inner) {
                        return wrapModel(inner);
                    }
                });
    }

    @Override
    public PagedList<ExpressRouteCrossConnectionPeering> listByParent(String resourceGroupName, String parentName) {
        return wrapList(this.inner().list(resourceGroupName, parentName));
    }
}