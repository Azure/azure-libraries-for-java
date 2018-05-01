/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network.implementation;

import com.microsoft.azure.SubResource;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.network.ExpressRouteCircuit;
import com.microsoft.azure.management.network.IpsecPolicy;
import com.microsoft.azure.management.network.LocalNetworkGateway;
import com.microsoft.azure.management.network.TunnelConnectionHealth;
import com.microsoft.azure.management.network.VirtualNetworkGateway;
import com.microsoft.azure.management.network.VirtualNetworkGatewayConnection;
import com.microsoft.azure.management.network.VirtualNetworkGatewayConnectionStatus;
import com.microsoft.azure.management.network.VirtualNetworkGatewayConnectionType;
import com.microsoft.azure.management.network.model.AppliableWithTags;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Observable;
import rx.functions.Func1;

import java.util.Collection;
import java.util.Collections;


/**
 * Implementation for VirtualNetworkGatewayConnection and its create and update interfaces.
 */
@LangDefinition
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
        return inner().authorizationKey();
    }

    @Override
    public String virtualNetworkGateway1Id() {
        if (inner().virtualNetworkGateway1() == null) {
            return null;
        }
        return inner().virtualNetworkGateway1().id();
    }

    @Override
    public String virtualNetworkGateway2Id() {
        if (inner().virtualNetworkGateway2() == null) {
            return null;
        }
        return inner().virtualNetworkGateway2().id();
    }

    @Override
    public String localNetworkGateway2Id() {
        if (inner().localNetworkGateway2() == null) {
            return null;
        }
        return inner().localNetworkGateway2().id();
    }

    @Override
    public VirtualNetworkGatewayConnectionType connectionType() {
        return inner().connectionType();
    }

    @Override
    public int routingWeight() {
        return Utils.toPrimitiveInt(inner().routingWeight());
    }

    @Override
    public String sharedKey() {
        return inner().sharedKey();
    }

    @Override
    public VirtualNetworkGatewayConnectionStatus connectionStatus() {
        return inner().connectionStatus();
    }

    @Override
    public Collection<TunnelConnectionHealth> tunnelConnectionStatus() {
        return Collections.unmodifiableCollection(inner().tunnelConnectionStatus());
    }

    @Override
    public long egressBytesTransferred() {
        return Utils.toPrimitiveLong(inner().egressBytesTransferred());
    }

    @Override
    public long ingressBytesTransferred() {
        return Utils.toPrimitiveLong(inner().ingressBytesTransferred());
    }

    @Override
    public String peerId() {
        return inner().peer() == null ? null : inner().peer().id();
    }

    @Override
    public boolean isBgpEnabled() {
        return Utils.toPrimitiveBoolean(inner().enableBgp());
    }

    @Override
    public boolean usePolicyBasedTrafficSelectors() {
        return Utils.toPrimitiveBoolean(inner().usePolicyBasedTrafficSelectors());
    }

    @Override
    public Collection<IpsecPolicy> ipsecPolicies() {
        return Collections.unmodifiableCollection(inner().ipsecPolicies());
    }

    @Override
    public String provisioningState() {
        return inner().provisioningState();
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withSiteToSite() {
        inner().withConnectionType(VirtualNetworkGatewayConnectionType.IPSEC);
        return this;
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withVNetToVNet() {
        inner().withConnectionType(VirtualNetworkGatewayConnectionType.VNET2VNET);
        return this;
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withExpressRoute(String circuitId) {
        inner().withConnectionType(VirtualNetworkGatewayConnectionType.EXPRESS_ROUTE);
        inner().withPeer(new SubResource().withId(circuitId));
        return this;
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withExpressRoute(ExpressRouteCircuit circuit) {
        return withExpressRoute(circuit.id());
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withLocalNetworkGateway(LocalNetworkGateway localNetworkGateway) {
        inner().withLocalNetworkGateway2(localNetworkGateway.inner());
        return this;
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withSecondVirtualNetworkGateway(VirtualNetworkGateway virtualNetworkGateway2) {
        inner().withVirtualNetworkGateway2(virtualNetworkGateway2.inner());
        return this;
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withSharedKey(String sharedKey) {
        inner().withSharedKey(sharedKey);
        return this;
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withBgp() {
        inner().withEnableBgp(true);
        return this;
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withoutBgp() {
        inner().withEnableBgp(false);
        return this;
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl withAuthorization(String authorizationKey) {
        inner().withAuthorizationKey(authorizationKey);
        return this;
    }

    @Override
    protected Observable<VirtualNetworkGatewayConnectionInner> getInnerAsync() {
        return myManager.inner().virtualNetworkGatewayConnections().getByResourceGroupAsync(resourceGroupName(), name());
    }

    @Override
    public Observable<VirtualNetworkGatewayConnection> createResourceAsync() {
        beforeCreating();
        return myManager.inner().virtualNetworkGatewayConnections().createOrUpdateAsync(
                this.resourceGroupName(), this.name(), this.inner())
                .map(innerToFluentMap(this));
    }

    private void beforeCreating() {
        inner().withVirtualNetworkGateway1(parent.inner());
    }

    @Override
    public VirtualNetworkGatewayConnectionImpl updateTags() {
        return this;
    }

    @Override
    public VirtualNetworkGatewayConnection applyTags() {
        return applyTagsAsync().toBlocking().last();
    }

    @Override
    public Observable<VirtualNetworkGatewayConnection> applyTagsAsync() {
        return this.manager().inner().virtualNetworkGatewayConnections().updateTagsAsync(resourceGroupName(), name(), inner().getTags())
                .flatMap(new Func1<VirtualNetworkGatewayConnectionListEntityInner, Observable<VirtualNetworkGatewayConnection>>() {
                    @Override
                    public Observable<VirtualNetworkGatewayConnection> call(VirtualNetworkGatewayConnectionListEntityInner inner) {
                        return refreshAsync();
                    }
                });
    }

    @Override
    public ServiceFuture<VirtualNetworkGatewayConnection> applyTagsAsync(ServiceCallback<VirtualNetworkGatewayConnection> callback) {
        return ServiceFuture.fromBody(applyTagsAsync(), callback);
    }
}
