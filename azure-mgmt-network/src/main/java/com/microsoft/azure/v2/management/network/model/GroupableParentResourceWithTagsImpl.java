/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network.model;

import com.microsoft.azure.v2.management.resources.fluentcore.arm.implementation.ManagerBase;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.implementation.GroupableParentResourceImpl;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import rx.Observable;
import rx.functions.Func1;

/**
 * The implementation for {@link GroupableResource} that can update tags as a separate operation.
 *
 * @param <FluentModelT> The fluent model type
 * @param <InnerModelT> Azure inner resource class type
 * @param <FluentModelImplT> the implementation type of the fluent model type
 * @param <ManagerT> the service manager type
 */
public abstract class GroupableParentResourceWithTagsImpl<
        FluentModelT extends Resource,
        InnerModelT extends com.microsoft.azure.v2.Resource,
        FluentModelImplT extends GroupableParentResourceWithTagsImpl<FluentModelT, InnerModelT, FluentModelImplT, ManagerT>,
        ManagerT extends ManagerBase>
        extends
            GroupableParentResourceImpl<FluentModelT, InnerModelT, FluentModelImplT, ManagerT>
        implements
            UpdatableWithTags<FluentModelT>,
            AppliableWithTags<FluentModelT> {
    protected GroupableParentResourceWithTagsImpl(String name, InnerModelT innerObject, ManagerT manager) {
        super(name, innerObject, manager);
    }

    @Override
    @SuppressWarnings("unchecked")
    public FluentModelImplT updateTags() {
        return (FluentModelImplT) this;
    }

    @Override
    public FluentModelT applyTags() {
        return applyTagsAsync().toBlocking().last();
    }

    protected abstract Observable<InnerModelT> applyTagsToInnerAsync();

    @Override
    public Observable<FluentModelT> applyTagsAsync() {
        @SuppressWarnings("unchecked")
        final FluentModelT self = (FluentModelT) this;
        return applyTagsToInnerAsync()
                .flatMap(new Func1<InnerModelT, Observable<FluentModelT>>() {
                    @Override
                    public Observable<FluentModelT> call(InnerModelT inner) {
                        setInner(inner);
                        return Observable.just(self);                    }
                });
    }

    @Override
    public ServiceFuture<FluentModelT> applyTagsAsync(ServiceCallback<FluentModelT> callback) {
        return ServiceFuture.fromBody(applyTagsAsync(), callback);
    }
}
