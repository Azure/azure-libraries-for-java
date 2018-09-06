/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.compute.implementation;

import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.compute.AvailabilitySet;
import com.microsoft.azure.v2.management.compute.AvailabilitySetSkuTypes;
import com.microsoft.azure.v2.management.compute.AvailabilitySets;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.GroupableResourcesImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.implementation.GroupPagedList;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

import java.util.List;

/**
 * The implementation for AvailabilitySets.
 */
@LangDefinition
class AvailabilitySetsImpl
    extends GroupableResourcesImpl<
        AvailabilitySet,
        AvailabilitySetImpl,
        AvailabilitySetInner,
        AvailabilitySetsInner,
        ComputeManager>
    implements AvailabilitySets {

    AvailabilitySetsImpl(final ComputeManager computeManager) {
        super(computeManager.inner().availabilitySets(), computeManager);
    }

    @Override
    public PagedList<AvailabilitySet> list() {
        final AvailabilitySetsImpl self = this;
        return new GroupPagedList<AvailabilitySet>(this.manager().resourceManager().resourceGroups().list()) {
            @Override
            public List<AvailabilitySet> listNextGroup(String resourceGroupName) {
                return wrapList(self.inner().listByResourceGroup(resourceGroupName));
            }
        };
    }

    @Override
    public Observable<AvailabilitySet> listAsync() {
        return this.manager().resourceManager().resourceGroups().listAsync()
                .flatMap(resourceGroup -> wrapPageAsync(inner().listByResourceGroupAsync(resourceGroup.name())));
    }

    @Override
    public PagedList<AvailabilitySet> listByResourceGroup(String groupName) {
        return wrapList(this.inner().listByResourceGroup(groupName));
    }

    @Override
    public Observable<AvailabilitySet> listByResourceGroupAsync(String resourceGroupName) {
        return wrapPageAsync(this.inner().listByResourceGroupAsync(resourceGroupName));
    }

    @Override
    protected Maybe<AvailabilitySetInner> getInnerAsync(String resourceGroupName, String name) {
        return this.inner().getByResourceGroupAsync(resourceGroupName, name);
    }

    @Override
    public AvailabilitySetImpl define(String name) {
        return wrapModel(name).withSku(AvailabilitySetSkuTypes.MANAGED);
    }

    @Override
    protected Completable deleteInnerAsync(String groupName, String name) {
        return this.inner().deleteAsync(groupName, name).flatMapCompletable(o -> Completable.complete());
    }

    /**************************************************************
     * Fluent model helpers.
     **************************************************************/

    @Override
    protected AvailabilitySetImpl wrapModel(String name) {
        return new AvailabilitySetImpl(name,
                new AvailabilitySetInner(),
                this.manager());
    }

    @Override
    protected AvailabilitySetImpl wrapModel(AvailabilitySetInner availabilitySetInner) {
        if (availabilitySetInner == null) {
            return null;
        }
        return new AvailabilitySetImpl(availabilitySetInner.name(),
                availabilitySetInner,
                this.manager());
    }
}
