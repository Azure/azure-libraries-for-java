/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedIterable;
import com.azure.management.network.models.LocalNetworkGatewayInner;
import com.azure.management.network.models.LocalNetworkGatewaysInner;
import com.azure.management.resources.ResourceGroup;
import com.azure.management.resources.fluentcore.arm.collection.implementation.GroupableResourcesImpl;

import com.azure.management.network.LocalNetworkGateway;
import com.azure.management.network.LocalNetworkGateways;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Implementation for LocalNetworkGateways.
 */
class LocalNetworkGatewaysImpl
        extends GroupableResourcesImpl<
        LocalNetworkGateway,
        LocalNetworkGatewayImpl,
        LocalNetworkGatewayInner,
        LocalNetworkGatewaysInner,
        NetworkManager>
        implements LocalNetworkGateways {

    LocalNetworkGatewaysImpl(final NetworkManager networkManager) {
        super(networkManager.inner().localNetworkGateways(), networkManager);
    }

    @Override
    public LocalNetworkGatewayImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    public PagedIterable<LocalNetworkGateway> list() {
        return new GroupPagedList<LocalNetworkGateway>(this.manager().getResourceManager().resourceGroups().list()) {
            @Override
            public List<LocalNetworkGateway> listNextGroup(String resourceGroupName) {
                return wrapList(LocalNetworkGatewaysImpl.this.inner().listByResourceGroup(resourceGroupName));
            }
        };
    }

    @Override
    public PagedFlux<LocalNetworkGateway> listAsync() {
        return this.manager().resourceManager().resourceGroups().listAsync()
                .flatMap(new Func1<ResourceGroup, Observable<LocalNetworkGateway>>() {
                    @Override
                    public Observable<LocalNetworkGateway> call(ResourceGroup resourceGroup) {
                        return wrapPageAsync(inner().listByResourceGroupAsync(resourceGroup.name()));
                    }
                });
    }

    @Override
    public PagedIterable<LocalNetworkGateway> listByResourceGroup(String groupName) {
        return wrapList(this.inner().listByResourceGroup(groupName));
    }

    @Override
    public PagedFlux<LocalNetworkGateway> listByResourceGroupAsync(String groupName) {
        return wrapPageAsync(this.inner().listByResourceGroupAsync(groupName));
    }

    @Override
    protected Mono<LocalNetworkGatewayInner> getInnerAsync(String groupName, String name) {
        return this.inner().getByResourceGroupAsync(groupName, name);
    }

    @Override
    protected Mono<Void> deleteInnerAsync(String groupName, String name) {
        return this.inner().deleteAsync(groupName, name);
    }

    // Fluent model create helpers

    @Override
    protected LocalNetworkGatewayImpl wrapModel(String name) {
        LocalNetworkGatewayInner inner = new LocalNetworkGatewayInner();

        return new LocalNetworkGatewayImpl(name, inner, super.manager());
    }

    @Override
    protected LocalNetworkGatewayImpl wrapModel(LocalNetworkGatewayInner inner) {
        if (inner == null) {
            return null;
        }
        return new LocalNetworkGatewayImpl(inner.getName(), inner, this.manager());
    }
}

