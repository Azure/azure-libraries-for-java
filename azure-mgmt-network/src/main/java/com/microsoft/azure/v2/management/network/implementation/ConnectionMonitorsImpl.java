/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network.implementation;

import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.network.ConnectionMonitor;
import com.microsoft.azure.v2.management.network.ConnectionMonitors;
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
 * Represents Connection Monitors collection associated with Network Watcher.
 */
@LangDefinition
class ConnectionMonitorsImpl extends
        CreatableResourcesImpl<ConnectionMonitor,
                ConnectionMonitorImpl,
                ConnectionMonitorResultInner>
        implements ConnectionMonitors {
    private final NetworkWatcherImpl parent;
    private final ConnectionMonitorsInner innerCollection;

    /**
     * Creates a new ConnectionMonitorsImpl.
     *
     * @param parent the Network Watcher associated with Connection Monitors
     */
    ConnectionMonitorsImpl(ConnectionMonitorsInner innerCollection, NetworkWatcherImpl parent) {
        this.parent = parent;
        this.innerCollection = innerCollection;
    }

    @Override
    public final PagedList<ConnectionMonitor> list() {
        return (new PagedListConverter<ConnectionMonitorResultInner, ConnectionMonitor>() {
            @Override
            public Observable<ConnectionMonitor> typeConvertAsync(ConnectionMonitorResultInner inner) {
                return Observable.just((ConnectionMonitor) wrapModel(inner));
            }
        }).convert(ReadableWrappersImpl.convertToPagedList(inner().list(parent.resourceGroupName(), parent.name())));
    }

    /**
     * @return an observable emits connection monitors in this collection
     */
    @Override
    public Observable<ConnectionMonitor> listAsync() {
        Maybe<List<ConnectionMonitorResultInner>> list = inner().listAsync(parent.resourceGroupName(), parent.name());
        return ReadableWrappersImpl.convertListToInnerAsync(list.toObservable())
                .map(inner -> wrapModel(inner));
    }

    @Override
    protected ConnectionMonitorImpl wrapModel(String name) {
        return new ConnectionMonitorImpl(name, parent, new ConnectionMonitorResultInner(), inner());
    }

    protected ConnectionMonitorImpl wrapModel(ConnectionMonitorResultInner inner) {
        return (inner == null) ? null : new ConnectionMonitorImpl(inner.name(), parent, inner, inner());
    }

    @Override
    public ConnectionMonitorImpl define(String name) {
        return new ConnectionMonitorImpl(name, parent, new ConnectionMonitorResultInner(), inner());
    }

    @Override
    public Observable<ConnectionMonitor> getByNameAsync(String name) {
        return inner().getAsync(parent.resourceGroupName(), parent.name(), name)
                .map(inner -> (ConnectionMonitor) wrapModel(inner))
                .toObservable();
    }

    @Override
    public ConnectionMonitor getByName(String name) {
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
    public ConnectionMonitorsInner inner() {
        return innerCollection;
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        ResourceId resourceId = ResourceId.fromString(id);
        return this.inner().deleteAsync(resourceId.resourceGroupName(), resourceId.parent().name(), resourceId.name());
    }
}
