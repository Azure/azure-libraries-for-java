/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.management.network.models.ConnectionMonitorInner;
import com.azure.management.network.models.ConnectionMonitorQueryResultInner;
import com.azure.management.network.models.ConnectionMonitorResultInner;
import com.azure.management.network.models.ConnectionMonitorsInner;
import com.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.azure.management.resources.fluentcore.utils.Utils;
import com.azure.management.network.ConnectionMonitor;
import com.azure.management.network.ConnectionMonitorDestination;
import com.azure.management.network.ConnectionMonitorQueryResult;
import com.azure.management.network.ConnectionMonitorSource;
import com.azure.management.network.NetworkWatcher;
import com.azure.management.network.ProvisioningState;
import com.azure.management.network.models.HasNetworkInterfaces;
import reactor.core.publisher.Mono;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implementation for Connection Monitor and its create and update interfaces.
 */
public class ConnectionMonitorImpl extends
        CreatableUpdatableImpl<ConnectionMonitor, ConnectionMonitorResultInner, ConnectionMonitorImpl>
        implements
        ConnectionMonitor,
        ConnectionMonitor.Definition {
    private final ConnectionMonitorsInner client;
    private final ConnectionMonitorInner createParameters;
    private final NetworkWatcher parent;

    ConnectionMonitorImpl(String name, NetworkWatcherImpl parent, ConnectionMonitorResultInner innerObject,
                          ConnectionMonitorsInner client) {
        super(name, innerObject);
        this.client = client;
        this.parent = parent;
        this.createParameters = new ConnectionMonitorInner().setLocation(parent.regionName());
    }

    @Override
    protected Mono<ConnectionMonitorResultInner> getInnerAsync() {
        return this.client.getAsync(parent.resourceGroupName(), parent.name(), name());
    }

    @Override
    public String location() {
        return inner().getLocation();
    }

    @Override
    public Map<String, String> tags() {
        Map<String, String> tags = this.inner().getTags();
        if (tags == null) {
            tags = new TreeMap<>();
        }
        return Collections.unmodifiableMap(tags);
    }

    @Override
    public ConnectionMonitorSource source() {
        return inner().source();
    }

    @Override
    public ConnectionMonitorDestination destination() {
        return inner().destination();
    }

    @Override
    public boolean autoStart() {
        return Utils.toPrimitiveBoolean(inner().autoStart());
    }

    @Override
    public ProvisioningState provisioningState() {
        return inner().provisioningState();
    }

    @Override
    public DateTime startTime() {
        return inner().startTime();
    }

    @Override
    public String monitoringStatus() {
        return inner().monitoringStatus();
    }

    @Override
    public int monitoringIntervalInSeconds() {
        return Utils.toPrimitiveInt(inner().monitoringIntervalInSeconds());
    }

    @Override
    public void stop() {
        stopAsync().await();
    }

    @Override
    public Completable stopAsync() {
        return this.client.stopAsync(parent.resourceGroupName(), parent.name(), name())
                .flatMap(new Func1<Void, Observable<?>>() {
                    @Override
                    public Observable<?> call(Void aVoid) {
                        return refreshAsync();
                    }
                }).toCompletable();
    }

    @Override
    public void start() {
        startAsync().await();
    }

    @Override
    public Completable startAsync() {
        return this.client.startAsync(parent.resourceGroupName(), parent.name(), name())
                .flatMap(new Func1<Void, Observable<?>>() {
                    @Override
                    public Observable<?> call(Void aVoid) {
                        return refreshAsync();
                    }
                }).toCompletable();
    }

    @Override
    public ConnectionMonitorQueryResult query() {
        return queryAsync().toBlocking().last();
    }

    @Override
    public Observable<ConnectionMonitorQueryResult> queryAsync() {
        return this.client.queryAsync(parent.resourceGroupName(), parent.name(), name())
                .map(new Func1<ConnectionMonitorQueryResultInner, ConnectionMonitorQueryResult>() {
                    @Override
                    public ConnectionMonitorQueryResult call(ConnectionMonitorQueryResultInner inner) {
                        return new ConnectionMonitorQueryResultImpl(inner);
                    }
                });
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().id() == null;
    }

    @Override
    public Mono<ConnectionMonitor> createResourceAsync() {
        return this.client.createOrUpdateAsync(parent.resourceGroupName(), parent.name(), this.name(), createParameters)
                .map(innerToFluentMap(this));
    }

    @Override
    public String id() {
        return inner().getId();
    }

    @Override
    public ConnectionMonitorImpl withSourceId(String resourceId) {
        ensureConnectionMonitorSource().setResourceId(resourceId);
        return this;
    }

    @Override
    public ConnectionMonitorImpl withSource(HasNetworkInterfaces vm) {
        ensureConnectionMonitorSource().setResourceId(vm.id());
        return this;
    }

    @Override
    public ConnectionMonitorImpl withDestinationId(String resourceId) {
        ensureConnectionMonitorDestination().setResourceId(resourceId);
        return this;
    }

    @Override
    public ConnectionMonitorImpl withDestination(HasNetworkInterfaces vm) {
        ensureConnectionMonitorDestination().setResourceId(vm.id());
        return this;
    }

    @Override
    public DefinitionStages.WithDestinationPort withDestinationAddress(String address) {
        ensureConnectionMonitorDestination().setAddress(address);
        return this;
    }

    private ConnectionMonitorSource ensureConnectionMonitorSource() {
        if (createParameters.getSource() == null) {
            createParameters.setSource(new ConnectionMonitorSource());
        }
        return createParameters.getSource();
    }

    private ConnectionMonitorDestination ensureConnectionMonitorDestination() {
        if (createParameters.getDestination() == null) {
            createParameters.setDestination(new ConnectionMonitorDestination());
        }
        return createParameters.getDestination();
    }

    @Override
    public ConnectionMonitorImpl withDestinationPort(int port) {
        ensureConnectionMonitorDestination().setPort(port);
        return this;
    }

    @Override
    public ConnectionMonitorImpl withSourcePort(int port) {
        ensureConnectionMonitorSource().setPort(port);
        return this;
    }

    @Override
    public ConnectionMonitorImpl withoutAutoStart() {
        createParameters.setAutoStart(false);
        return this;
    }

    @Override
    public final ConnectionMonitorImpl withTags(Map<String, String> tags) {
        this.createParameters.setTags(new HashMap<>(tags));
        return this;
    }

    @Override
    public ConnectionMonitorImpl withTag(String key, String value) {
        if (this.createParameters.getTags() == null) {
            this.createParameters.setTags(new HashMap<String, String>());
        }
        this.createParameters.getTags().put(key, value);
        return this;
    }

    @Override
    public ConnectionMonitorImpl withoutTag(String key) {
        if (this.createParameters.getTags() != null) {
            this.createParameters.getTags().remove(key);
        }
        return this;
    }

    @Override
    public ConnectionMonitorImpl withMonitoringInterval(int seconds) {
        createParameters.setMonitoringIntervalInSeconds(seconds);
        return this;
    }
}
