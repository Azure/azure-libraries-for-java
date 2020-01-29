/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.core.management.SubResource;
import com.azure.management.network.ExpressRouteCircuit;
import com.azure.management.network.IpsecPolicy;
import com.azure.management.network.LocalNetworkGateway;
import com.azure.management.network.TunnelConnectionHealth;
import com.azure.management.network.VirtualNetworkGateway;
import com.azure.management.network.VirtualNetworkGatewayConnection;
import com.azure.management.network.VirtualNetworkGatewayConnectionStatus;
import com.azure.management.network.VirtualNetworkGatewayConnectionType;
import com.azure.management.network.models.AppliableWithTags;
import com.azure.management.network.models.VirtualNetworkGatewayConnectionInner;
import com.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.azure.management.resources.fluentcore.utils.Utils;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;


/**
 * Implementation for VirtualNetworkGatewayConnection and its create and update interfaces.
 */
public class VirtualNetworkGatewayConnectionImpl
        extends GroupableResourceImpl<VirtualNetworkGatewayConnection, VirtualNetworkGatewayConnectionInner, VirtualNetworkGatewayConnectionImpl, NetworkManager>
        implements VirtualNetworkGatewayConnection,
            VirtualNetworkGatewayConnection.Definition,
            VirtualNetworkGatewayConnection.Update,
        AppliableWithTags<VirtualNetworkGatewayConnection> {
    private final VirtualNetworkGateway parent;

    VirtualNetworkGatewayConnectionImpl(String name,
                                VirtualNetworkGatewayImpl parent,
                                VirtualNetworkGatewayConnectionInner inner) {
        super(name, inner, parent.manager());
        this.parent = parent;
    }

    @Override
    public VirtualNetworkGateway parent() {
        return parent;
    }

    @Override
    public String authorizationKey() {
        return inner().getAuthorizationKey();
    }

    @Override
    public String virtualNetworkGateway1Id() {
        if (inner().getVirtualNetworkGateway1() == null) {
            return null;
        }
        return inner().getVirtualNetworkGateway1().getId();
    }

    @Override
    public String virtualNetworkGateway2Id() {
        if (inner().getVirtualNetworkGateway2() == null) {
            return null;
        }
        return inner().getVirtualNetworkGateway2().getId();
    }

    @Override
    public String localNetworkGateway2Id() {
        if (inner().getLocalNetworkGateway2() == null) {
            return null;
        }
        return inner().getLocalNetworkGateway2().getId();
    }

    @Override
    public VirtualNetworkGatewayConnectionType connectionType() {
        return inner().getConnectionType();
    }

    @Override
    public int routingWeight() {
        return Utils.toPrimitiveInt(inner().getRoutingWeight());
    }

    @Override
    public String sharedKey() {
        return inner().getSharedKey();
    }

    @Override
    public VirtualNetworkGatewayConnectionStatus connectionStatus() {
        return inner().getConnectionStatus();
    }

    @Override
    public Collection<TunnelConnectionHealth> tunnelConnectionStatus() {
        return Collections.unmodifiableCollection(inner().getTunnelConnectionStatus());
    }

    @Override
    public long egressBytesTransferred() {
        return Utils.toPrimitiveLong(inner().getEgressBytesTransferred());
    }

    @Override
    public long ingressBytesTransferred() {
        return Utils.toPrimitiveLong(inner().getIngressBytesTransferred());
    }

    @Override
    public String peerId() {
        return inner().getPeer() == null ? null : inner().getPeer().getId();
    }

    @Override
    public boolean isBgpEnabled() {
        return Utils.toPrimitiveBoolean(inner().isEnableBgp());
    }

    @Override
    public boolean usePolicyBasedTrafficSelectors() {
        return Utils.toPrimitiveBoolean(inner().isUsePolicyBasedTrafficSelectors());
    }

    @Override
    public Collection<IpsecPolicy> ipsecPolicies() {
        return Collections.unmodifiableCollection(inner().getIpsecPolicies());
    }

    @Override
    public String provisioningState() {
        return inner().getProvisioningState();
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withSiteToSite() {
        inner().setConnectionType(VirtualNetworkGatewayConnectionType.IPSEC);
        return this;
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withVNetToVNet() {
        inner().setConnectionType(VirtualNetworkGatewayConnectionType.VNET2VNET);
        return this;
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withExpressRoute(String circuitId) {
        inner().setConnectionType(VirtualNetworkGatewayConnectionType.EXPRESS_ROUTE);
        inner().setPeer(new SubResource().setId(circuitId));
        return this;
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withExpressRoute(ExpressRouteCircuit circuit) {
        return withExpressRoute(circuit.id());
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withLocalNetworkGateway(LocalNetworkGateway localNetworkGateway) {
        inner().setLocalNetworkGateway2(localNetworkGateway.inner());
        return this;
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withSecondVirtualNetworkGateway(VirtualNetworkGateway virtualNetworkGateway2) {
        inner().setVirtualNetworkGateway2(virtualNetworkGateway2.inner());
        return this;
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withSharedKey(String sharedKey) {
        inner().setSharedKey(sharedKey);
        return this;
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withBgp() {
        inner().setEnableBgp(true);
        return this;
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withoutBgp() {
        inner().setEnableBgp(false);
        return this;
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withAuthorization(String authorizationKey) {
        inner().setAuthorizationKey(authorizationKey);
        return this;
    }

    @Override
    protected Mono<VirtualNetworkGatewayConnectionInner> getInnerAsync() {
        return myManager.inner().virtualNetworkGatewayConnections().getByResourceGroupAsync(resourceGroupName(), name());
    }

    @Override
    public Mono<VirtualNetworkGatewayConnection> createResourceAsync() {
        beforeCreating();
        return myManager.inner().virtualNetworkGatewayConnections().createOrUpdateAsync(
                this.resourceGroupName(), this.name(), this.inner())
                .map(innerToFluentMap(this));
    }

    private void beforeCreating() {
        inner().setVirtualNetworkGateway1(parent.inner());
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl updateTags() {
        return this;
    }

    @Override
    public VirtualNetworkGatewayConnection applyTags() {
        return applyTagsAsync().block();
    }

    @Override
    public Mono<VirtualNetworkGatewayConnection> applyTagsAsync() {
        return this.manager().inner().virtualNetworkGatewayConnections().updateTagsAsync(resourceGroupName(), name(), inner().getTags())
                .flatMap(inner -> refreshAsync());
    }
}
