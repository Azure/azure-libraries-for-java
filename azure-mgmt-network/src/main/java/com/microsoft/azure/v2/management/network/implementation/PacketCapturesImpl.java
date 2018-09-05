/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network.implementation;

import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.network.PacketCapture;
import com.microsoft.azure.v2.management.network.PacketCaptures;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceId;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.CreatableResourcesImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.PagedListConverter;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

import java.util.List;

/**
 * Represents Packet Captures collection associated with Network Watcher.
 */
@LangDefinition
class PacketCapturesImpl extends
        CreatableResourcesImpl<PacketCapture,
                        PacketCaptureImpl,
                        PacketCaptureResultInner>
        implements PacketCaptures {
    private final NetworkWatcherImpl parent;
    protected final PacketCapturesInner innerCollection;

    /**
     * Creates a new PacketCapturesImpl.
     *
     * @param parent the Network Watcher associated with Packet Captures
     */
    PacketCapturesImpl(PacketCapturesInner innerCollection, NetworkWatcherImpl parent) {
        this.parent = parent;
        this.innerCollection = innerCollection;
    }

    @Override
    public final PagedList<PacketCapture> list() {
        return (new PagedListConverter<PacketCaptureResultInner, PacketCapture>() {
            @Override
            public Observable<PacketCapture> typeConvertAsync(PacketCaptureResultInner inner) {
                return Observable.just((PacketCapture) wrapModel(inner));
            }
        }).convert(ReadableWrappersImpl.convertToPagedList(inner().list(parent.resourceGroupName(), parent.name())));
    }

    /**
     * @return an observable emits packet captures in this collection
     */
    @Override
    public Observable<PacketCapture> listAsync() {
        Maybe<List<PacketCaptureResultInner>> list = inner().listAsync(parent.resourceGroupName(), parent.name());

        return ReadableWrappersImpl.convertListToInnerAsync(list.toObservable())
                .map(inner -> wrapModel(inner));
    }

    @Override
    protected PacketCaptureImpl wrapModel(String name) {
        return new PacketCaptureImpl(name, parent, new PacketCaptureResultInner(), inner());
    }

    protected PacketCaptureImpl wrapModel(PacketCaptureResultInner inner) {
        return (inner == null) ? null : new PacketCaptureImpl(inner.name(), parent, inner, inner());
    }

    @Override
    public PacketCaptureImpl define(String name) {
        return new PacketCaptureImpl(name, parent, new PacketCaptureResultInner(), inner());
    }

    @Override
    public Observable<PacketCapture> getByNameAsync(String name) {
        return inner().getAsync(parent.resourceGroupName(), parent.name(), name)
                .map(inner -> (PacketCapture) wrapModel(inner))
                .toObservable();
    }

    @Override
    public PacketCapture getByName(String name) {
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
        return this.inner().deleteAsync(parent.resourceGroupName(),
                parent.name(),
                name);
    }

    @Override
    public PacketCapturesInner inner() {
        return innerCollection;
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        ResourceId resourceId = ResourceId.fromString(id);
        return this.inner().deleteAsync(resourceId.resourceGroupName(), resourceId.parent().name(), resourceId.name());
    }
}
