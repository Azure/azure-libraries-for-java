/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedIterable;
import com.azure.management.network.VirtualNetworkGateway;
import com.azure.management.network.VirtualNetworkGateways;
import com.azure.management.network.models.VirtualNetworkGatewayInner;
import com.azure.management.network.models.VirtualNetworkGatewaysInner;
import com.azure.management.resources.ResourceGroup;
import com.azure.management.resources.fluentcore.arm.collection.implementation.GroupableResourcesImpl;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 *  Implementation for VirtualNetworkGateways.
 */
class VirtualNetworkGatewaysImpl
        extends GroupableResourcesImpl<
                VirtualNetworkGateway,
                VirtualNetworkGatewayImpl,
                VirtualNetworkGatewayInner,
                VirtualNetworkGatewaysInner,
                NetworkManager>
        implements VirtualNetworkGateways {

    VirtualNetworkGatewaysImpl(final NetworkManager networkManager) {
        super(networkManager.inner().virtualNetworkGateways(), networkManager);
    }

    @Override
    public VirtualNetworkGatewayImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    public PagedIterable<VirtualNetworkGateway> list() {
        final VirtualNetworkGatewaysImpl self = this;
        return new GroupPagedList<VirtualNetworkGateway>(this.manager().resourceManager().resourceGroups().list()) {
            @Override
            public List<VirtualNetworkGateway> listNextGroup(String resourceGroupName) {
                return wrapList(self.inner().listByResourceGroup(resourceGroupName));
            }
        };
    }

    @Override
    public PagedFlux<VirtualNetworkGateway> listAsync() {
        return this.manager().getResourceManager().resourceGroups().listAsync()
                .flatMap(new Func1<ResourceGroup, Observable<VirtualNetworkGateway>>() {
                    @Override
                    public Observable<VirtualNetworkGateway> call(ResourceGroup resourceGroup) {
                        return wrapPageAsync(inner().listByResourceGroupAsync(resourceGroup.name()));
                    }
                });
    }

    @Override
    public PagedIterable<VirtualNetworkGateway> listByResourceGroup(String groupName) {
        return wrapList(this.inner().listByResourceGroup(groupName));
    }

    @Override
    public PagedFlux<VirtualNetworkGateway> listByResourceGroupAsync(String groupName) {
        return wrapPageAsync(this.inner().listByResourceGroupAsync(groupName));
    }

    @Override
    protected Mono<VirtualNetworkGatewayInner> getInnerAsync(String groupName, String name) {
        return this.inner().getByResourceGroupAsync(groupName, name);
    }

    @Override
    protected Mono<Void> deleteInnerAsync(String groupName, String name) {
        return this.inner().deleteAsync(groupName, name);
    }

    // Fluent model create helpers

    @Override
    protected VirtualNetworkGatewayImpl wrapModel(String name) {
        VirtualNetworkGatewayInner inner = new VirtualNetworkGatewayInner();

        return new VirtualNetworkGatewayImpl(name, inner, super.manager());
    }

    @Override
    protected VirtualNetworkGatewayImpl wrapModel(VirtualNetworkGatewayInner inner) {
        if (inner == null) {
            return null;
        }
        return new VirtualNetworkGatewayImpl(inner.getName(), inner, this.manager());
    }
}

