/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.management.network.models.ConnectivityInformationInner;
import com.azure.management.resources.fluentcore.model.implementation.ExecutableImpl;
import com.azure.management.resources.fluentcore.utils.Utils;
import com.azure.management.network.ConnectionStatus;
import com.azure.management.network.ConnectivityCheck;
import com.azure.management.network.ConnectivityDestination;
import com.azure.management.network.ConnectivityHop;
import com.azure.management.network.ConnectivityParameters;
import com.azure.management.network.ConnectivitySource;
import com.azure.management.network.Protocol;
import com.azure.management.network.models.HasNetworkInterfaces;
import reactor.core.publisher.Mono;


import java.util.List;

/**
 * Implementation of ConnectivityCheck.
 */
public class ConnectivityCheckImpl extends ExecutableImpl<ConnectivityCheck>
        implements ConnectivityCheck, ConnectivityCheck.Definition {

    private final NetworkWatcherImpl parent;
    private ConnectivityParameters parameters = new ConnectivityParameters();
    private ConnectivityInformationInner result;

    ConnectivityCheckImpl(NetworkWatcherImpl parent) {
        this.parent = parent;
    }

    @Override
    public ConnectivityCheckImpl fromSourceVirtualMachine(String sourceResourceId) {
        ensureConnectivitySource().setResourceId(sourceResourceId);
        return this;
    }

    @Override
    public DefinitionStages.WithExecute fromSourceVirtualMachine(HasNetworkInterfaces vm) {
        ensureConnectivitySource().setResourceId(vm.id());
        return this;
    }

    @Override
    public ConnectivityCheckImpl toDestinationResourceId(String resourceId) {
        ensureConnectivityDestination().setResourceId(resourceId);
        return this;
    }

    @Override
    public ConnectivityCheckImpl toDestinationAddress(String address) {
        ensureConnectivityDestination().setAddress(address);
        return this;
    }

    @Override
    public ConnectivityCheckImpl toDestinationPort(int port) {
        ensureConnectivityDestination().setPort(port);
        return this;
    }

    @Override
    public ConnectivityCheckImpl fromSourcePort(int port) {
        ensureConnectivitySource().setPort(port);
        return this;
    }

    @Override
    public ConnectivityCheckImpl withProtocol(Protocol protocol) {
        parameters.setProtocol(protocol);
        return this;
    }

    private ConnectivitySource ensureConnectivitySource() {
        if (parameters.getSource() == null) {
            parameters.setSource(new ConnectivitySource());
        }
        return parameters.getSource();
    }

    private ConnectivityDestination ensureConnectivityDestination() {
        if (parameters.getDestination() == null) {
            parameters.setDestination(new ConnectivityDestination());
        }
        return parameters.getDestination();
    }

    @Override
    public NetworkWatcherImpl parent() {
        return parent;
    }

    @Override
    public List<ConnectivityHop> hops() {
        return result.getHops();
    }

    @Override
    public ConnectionStatus connectionStatus() {
        return result.getConnectionStatus();
    }

    @Override
    public int avgLatencyInMs() {
        return Utils.toPrimitiveInt(result.getAvgLatencyInMs());
    }

    @Override
    public int minLatencyInMs() {
        return Utils.toPrimitiveInt(result.getMinLatencyInMs());
    }

    @Override
    public int maxLatencyInMs() {
        return Utils.toPrimitiveInt(result.getMaxLatencyInMs());
    }

    @Override
    public int probesSent() {
        return Utils.toPrimitiveInt(result.getProbesSent());
    }

    @Override
    public int probesFailed() {
        return Utils.toPrimitiveInt(result.getProbesFailed());
    }

    @Override
    public Mono<ConnectivityCheck> executeWorkAsync() {
        return this.parent().manager().inner().networkWatchers()
                .checkConnectivityAsync(parent.resourceGroupName(), parent.name(), parameters)
                .map(connectivityInformation -> {
                    ConnectivityCheckImpl.this.result = connectivityInformation;
                    return ConnectivityCheckImpl.this;
                });
    }
}
