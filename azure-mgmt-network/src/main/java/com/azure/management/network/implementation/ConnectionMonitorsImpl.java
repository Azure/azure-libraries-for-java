/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.core.http.rest.PagedIterable;
import com.azure.management.network.models.ConnectionMonitorResultInner;
import com.azure.management.network.models.ConnectionMonitorsInner;
import com.azure.management.resources.fluentcore.arm.ResourceId;
import com.azure.management.resources.fluentcore.arm.collection.implementation.CreatableResourcesImpl;
import com.azure.management.resources.fluentcore.arm.collection.implementation.ReadableWrappersImpl;
import com.azure.management.network.ConnectionMonitor;
import com.azure.management.network.ConnectionMonitors;


import java.util.List;

/**
 * Represents Connection Monitors collection associated with Network Watcher.
 */
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
    public final PagedIterable<ConnectionMonitor> list() {
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
    public PagedIterable<ConnectionMonitor> listAsync() {
        Observable<List<ConnectionMonitorResultInner>> list = inner().listAsync(parent.resourceGroupName(), parent.name());
        return ReadableWrappersImpl.convertListToInnerAsync(list).map(new Func1<ConnectionMonitorResultInner, ConnectionMonitor>() {
            @Override
            public ConnectionMonitor call(ConnectionMonitorResultInner inner) {
                return wrapModel(inner);
            }
        });
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
                .map(new Func1<ConnectionMonitorResultInner, ConnectionMonitor>() {
                    @Override
                    public ConnectionMonitor call(ConnectionMonitorResultInner inner) {
                        return wrapModel(inner);
                    }
                });
    }

    @Override
    public ConnectionMonitor getByName(String name) {
        return getByNameAsync(name).toBlocking().last();
    }

    @Override
    public void deleteByName(String name) {
        deleteByNameAsync(name).await();
    }

    @Override
    public Completable deleteByNameAsync(String name) {
        return this.inner().deleteAsync(parent.resourceGroupName(),
                parent.name(),
                name).toCompletable();
    }

    @Override
    public ConnectionMonitorsInner inner() {
        return innerCollection;
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        ResourceId resourceId = ResourceId.fromString(id);
        return this.inner().deleteAsync(resourceId.resourceGroupName(), resourceId.parent().name(), resourceId.name()).toCompletable();
    }
}
