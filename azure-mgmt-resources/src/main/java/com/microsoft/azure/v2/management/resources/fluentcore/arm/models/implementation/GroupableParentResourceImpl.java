/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.fluentcore.arm.models.implementation;

import com.microsoft.azure.v2.management.resources.fluentcore.arm.implementation.ManagerBase;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.Resource;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * The implementation for GroupableResource.
 * (Internal use only)
 *
 * @param <FluentModelT> The fluent model type
 * @param <InnerModelT> Azure inner resource class type
 * @param <FluentModelImplT> the implementation type of the fluent model type
 * @param <ManagerT> the service manager type
 */
public abstract class GroupableParentResourceImpl<
        FluentModelT extends Resource,
        InnerModelT extends com.microsoft.azure.v2.Resource,
        FluentModelImplT extends GroupableParentResourceImpl<FluentModelT, InnerModelT, FluentModelImplT, ManagerT>,
        ManagerT extends ManagerBase>
        extends
        GroupableResourceImpl<FluentModelT, InnerModelT, FluentModelImplT, ManagerT>
        implements
        GroupableResource<ManagerT, InnerModelT> {

    protected GroupableParentResourceImpl(
            String name,
            InnerModelT innerObject,
            ManagerT manager) {
        super(name, innerObject, manager);
        initializeChildrenFromInner();
    }

    protected abstract Observable<InnerModelT> createInner();
    protected abstract void initializeChildrenFromInner();
    protected abstract void beforeCreating();
    protected abstract void afterCreating();

    @Override
    public Observable<FluentModelT> createResourceAsync() {
        @SuppressWarnings("unchecked")
        final FluentModelT self = (FluentModelT) this;
        beforeCreating();
        return createInner()
                .flatMap(new Function<InnerModelT, Observable<FluentModelT>>() {
                    @Override
                    public Observable<FluentModelT> apply(InnerModelT inner) {
                        setInner(inner);
                        try {
                            initializeChildrenFromInner();
                            afterCreating();
                            return Observable.just(self);
                        } catch (Exception e) {
                            return Observable.error(e);
                        }
                    }
                });
    }
}