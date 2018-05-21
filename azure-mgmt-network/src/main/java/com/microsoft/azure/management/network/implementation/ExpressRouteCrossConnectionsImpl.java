/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.network.ExpressRouteCrossConnection;
import com.microsoft.azure.management.network.ExpressRouteCrossConnections;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceId;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Observable;
import rx.functions.Func1;

@LangDefinition
class ExpressRouteCrossConnectionsImpl extends
        ReadableWrappersImpl<ExpressRouteCrossConnection, ExpressRouteCrossConnectionImpl, ExpressRouteCrossConnectionInner>
        implements ExpressRouteCrossConnections {
    private final NetworkManager manager;

    ExpressRouteCrossConnectionsImpl(NetworkManager manager) {
        this.manager = manager;
    }

    @Override
    protected ExpressRouteCrossConnectionImpl wrapModel(ExpressRouteCrossConnectionInner inner) {
        if (inner == null) {
            return null;
        }
        return new ExpressRouteCrossConnectionImpl(inner.name(), inner, this.manager());
    }

    @Override
    public ExpressRouteCrossConnection getById(String id) {
        return getByIdAsync(id).toBlocking().last();
    }

    @Override
    public Observable<ExpressRouteCrossConnection> getByIdAsync(String id) {
        ResourceId resourceId = ResourceId.fromString(id);
        return getByResourceGroupAsync(resourceId.resourceGroupName(), resourceId.name());
    }

    @Override
    public ServiceFuture<ExpressRouteCrossConnection> getByIdAsync(String id, ServiceCallback<ExpressRouteCrossConnection> callback) {
        return ServiceFuture.fromBody(getByIdAsync(id), callback);
    }

    @Override
    public ExpressRouteCrossConnection getByResourceGroup(String resourceGroupName, String name) {
        return getByResourceGroupAsync(resourceGroupName, name).toBlocking().last();
    }

    @Override
    public Observable<ExpressRouteCrossConnection> getByResourceGroupAsync(String resourceGroupName, String name) {
        return this.inner().getByResourceGroupAsync(resourceGroupName, name).map(new Func1<ExpressRouteCrossConnectionInner, ExpressRouteCrossConnection>() {
            @Override
            public ExpressRouteCrossConnection call(ExpressRouteCrossConnectionInner inner) {
                return wrapModel(inner);
            }
        });
    }

    @Override
    public ServiceFuture<ExpressRouteCrossConnection> getByResourceGroupAsync(String resourceGroupName, String name, ServiceCallback<ExpressRouteCrossConnection> callback) {
        return ServiceFuture.fromBody(getByResourceGroupAsync(resourceGroupName, name), callback);
    }

    @Override
    public PagedList<ExpressRouteCrossConnection> listByResourceGroup(String resourceGroupName) {
        return wrapList(this.inner().listByResourceGroup(resourceGroupName));
    }

    @Override
    public Observable<ExpressRouteCrossConnection> listByResourceGroupAsync(String resourceGroupName) {
        return wrapPageAsync(this.inner().listByResourceGroupAsync(resourceGroupName));
    }

    @Override
    public NetworkManager manager() {
        return manager;
    }

    @Override
    public PagedList<ExpressRouteCrossConnection> list() {
        return wrapList(inner().list());
    }

    @Override
    public Observable<ExpressRouteCrossConnection> listAsync() {
        return wrapPageAsync(inner().listAsync());
    }

    @Override
    public ExpressRouteCrossConnectionsInner inner() {
        return manager.inner().expressRouteCrossConnections();
    }
}
