/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.management.network.Access;
import com.azure.management.network.Direction;
import com.azure.management.network.IpFlowProtocol;
import com.azure.management.network.VerificationIPFlow;
import com.azure.management.network.VerificationIPFlowParameters;
import com.azure.management.network.models.VerificationIPFlowResultInner;
import com.azure.management.resources.fluentcore.model.implementation.ExecutableImpl;
import reactor.core.publisher.Mono;


/**
 * Implementation of VerificationIPFlow.
 */
public class VerificationIPFlowImpl extends ExecutableImpl<VerificationIPFlow>
        implements VerificationIPFlow, VerificationIPFlow.Definition {
    private final NetworkWatcherImpl parent;
    private VerificationIPFlowParameters parameters = new VerificationIPFlowParameters();
    private VerificationIPFlowResultInner result;

    VerificationIPFlowImpl(NetworkWatcherImpl parent) {
        this.parent = parent;
    }

    @Override
    public VerificationIPFlowImpl withTargetResourceId(String targetResourceId) {
        parameters.setTargetResourceId(targetResourceId);
        return this;
    }

    @Override
    public VerificationIPFlowImpl withDirection(Direction direction) {
        parameters.setDirection(direction);
        return this;
    }

    @Override
    public DefinitionStages.WithProtocol inbound() {
        return withDirection(Direction.INBOUND);
    }

    @Override
    public DefinitionStages.WithProtocol outbound() {
        return withDirection(Direction.OUTBOUND);
    }

    @Override
    public VerificationIPFlowImpl withProtocol(IpFlowProtocol protocol) {
        parameters.setProtocol(protocol);
        return this;
    }

    @Override
    public DefinitionStages.WithLocalIP withTCP() {
        return withProtocol(IpFlowProtocol.TCP);
    }

    @Override
    public DefinitionStages.WithLocalIP withUDP() {
        return withProtocol(IpFlowProtocol.UDP);
    }

    @Override
    public VerificationIPFlowImpl withLocalPort(String localPort) {
        parameters.setLocalPort(localPort);
        return this;
    }

    @Override
    public VerificationIPFlowImpl withRemotePort(String remotePort) {
        parameters.setRemotePort(remotePort);
        return this;
    }

    @Override
    public VerificationIPFlowImpl withLocalIPAddress(String localIPAddress) {
        parameters.setLocalIPAddress(localIPAddress);
        return this;
    }

    @Override
    public VerificationIPFlowImpl withRemoteIPAddress(String remoteIPAddress) {
        parameters.setRemoteIPAddress(remoteIPAddress);
        return this;
    }

    @Override
    public VerificationIPFlow withTargetNetworkInterfaceId(String targetNetworkInterfaceId) {
        parameters.setTargetNicResourceId(targetNetworkInterfaceId);
        return this;
    }

    @Override
    public NetworkWatcherImpl parent() {
        return parent;
    }

    @Override
    public Access access() {
        return this.result.getAccess();
    }

    @Override
    public String ruleName() {
        return this.result.getRuleName();
    }

    @Override
    public Mono<VerificationIPFlow> executeWorkAsync() {
        return this.parent().manager().inner().networkWatchers()
                .verifyIPFlowAsync(parent.resourceGroupName(), parent.name(), parameters)
                .map(verificationIPFlowResultInner -> {
                    VerificationIPFlowImpl.this.result = verificationIPFlowResultInner;
                    return VerificationIPFlowImpl.this;
                });
    }
}
