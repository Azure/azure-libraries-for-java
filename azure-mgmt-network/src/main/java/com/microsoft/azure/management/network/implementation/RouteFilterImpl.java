/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.network.RouteFilter;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import rx.Observable;

/**
 *  Implementation for RouteFilter and its create and update interfaces.
 */
@LangDefinition
class RouteFilterImpl
        extends GroupableResourceImpl<
        RouteFilter,
        RouteFilterInner,
        RouteFilterImpl,
        NetworkManager>
        implements
        RouteFilter,
        RouteFilter.Definition,
        RouteFilter.Update {

    RouteFilterImpl(
            final String name,
            final RouteFilterInner innerModel,
            final NetworkManager networkManager) {
        super(name, innerModel, networkManager);
    }

    @Override
    protected Observable<RouteFilterInner> getInnerAsync() {
        return this.manager().inner().routeFilters().getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public Observable<RouteFilter> createResourceAsync() {
        return this.manager().inner().routeFilters().createOrUpdateAsync(resourceGroupName(), name(), inner())
                .map(innerToFluentMap(this));
    }

    @Override
    public String provisioningState() {
        return inner().provisioningState();
    }
}
