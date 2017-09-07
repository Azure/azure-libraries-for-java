/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network;

import com.microsoft.azure.SubResource;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.network.implementation.NetworkManager;
import com.microsoft.azure.management.network.implementation.VirtualNetworkGatewayConnectionInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasParent;
import com.microsoft.azure.management.resources.fluentcore.arm.models.IndependentChildResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;

import java.util.List;

/**
 * Client-side representation of Virtual Network Gateway Connection object, associated with Virtual Network Gateway.
 */
@Fluent
@Beta
public interface VirtualNetworkGatewayConnection extends
        IndependentChildResource<NetworkManager, VirtualNetworkGatewayConnectionInner>,
        Refreshable<VirtualNetworkGatewayConnection>,
        Updatable<VirtualNetworkGatewayConnection>,
        HasParent<VirtualNetworkGateway> {

    /**
     * Get the authorizationKey value.
     *
     * @return the authorizationKey value
     */
    String authorizationKey();

    /**
     * @return the reference to virtual network gateway resource
     */
    VirtualNetworkGateway virtualNetworkGateway1();

    /**
     * @return the reference to virtual network gateway resource.
     */
    VirtualNetworkGateway virtualNetworkGateway2();

    /**
     * @return the reference to local network gateway resource
     */
    LocalNetworkGateway localNetworkGateway2();

    /**
     * Get the gateway connection type. Possible values are:
     * 'Ipsec','Vnet2Vnet','ExpressRoute', and 'VPNClient.
     *
     * @return the connectionType value
     */
    VirtualNetworkGatewayConnectionType connectionType();

    /**
     * @return the routing weight
     */
    int routingWeight();

    /**
     * Set the routingWeight value.
     *
     * @param routingWeight the routingWeight value to set
     * @return the VirtualNetworkGatewayConnectionInner object itself.
     */
//    public VirtualNetworkGatewayConnectionInner withRoutingWeight(Integer routingWeight) {
//        this.routingWeight = routingWeight;
//        return this;
//    }

    /**
     * @return the IPSec shared key
     */
    String sharedKey();

    /**
     * Get the Virtual Network Gateway connection status. Possible values are
     * 'Unknown', 'Connecting', 'Connected' and 'NotConnected'. Possible values
     * include: 'Unknown', 'Connecting', 'Connected', 'NotConnected'.
     *
     * @return the connectionStatus value
     */
    VirtualNetworkGatewayConnectionStatus connectionStatus();

    /**
     * Get the tunnelConnectionStatus value.
     *
     * @return collection of all tunnels' connection health status
     */
    List<TunnelConnectionHealth> tunnelConnectionStatus();

    /**
     * @return the egress bytes transferred in this connection
     */
    long egressBytesTransferred();

    /**
     * @return the egress bytes transferred in this connection.
     */
    long ingressBytesTransferred();

    /**
     * @return the reference to peerings resource
     */
    SubResource peer();
    /**
     //     * Set the peer value.
     //     *
     //     * @param peer the peer value to set
     //     * @return the VirtualNetworkGatewayConnectionInner object itself.
     //     */
//    public VirtualNetworkGatewayConnectionInner withPeer(SubResource peer) {
//        this.peer = peer;
//        return this;
//    }

    /**
     * @return the enableBgp flag
     */
    Boolean isBgpEnabled();

//    /**
//     * Set the enableBgp value.
//     *
//     * @param enableBgp the enableBgp value to set
//     * @return the VirtualNetworkGatewayConnectionInner object itself.
//     */
//    public VirtualNetworkGatewayConnectionInner withEnableBgp(Boolean enableBgp) {
//        this.enableBgp = enableBgp;
//        return this;
//    }

    /**
     * @return if policy-based traffic selectors enabled
     */
    Boolean usePolicyBasedTrafficSelectors();

//    /**
//     * Set the usePolicyBasedTrafficSelectors value.
//     *
//     * @param usePolicyBasedTrafficSelectors the usePolicyBasedTrafficSelectors value to set
//     * @return the VirtualNetworkGatewayConnectionInner object itself.
//     */
//    public VirtualNetworkGatewayConnectionInner withUsePolicyBasedTrafficSelectors(Boolean usePolicyBasedTrafficSelectors) {
//        this.usePolicyBasedTrafficSelectors = usePolicyBasedTrafficSelectors;
//        return this;
//    }

    /**
     * @return the IPSec Policies to be considered by this connection
     */
    List<IpsecPolicy> ipsecPolicies();
//
//    /**
//     * Set the ipsecPolicies value.
//     *
//     * @param ipsecPolicies the ipsecPolicies value to set
//     * @return the VirtualNetworkGatewayConnectionInner object itself.
//     */
//    public VirtualNetworkGatewayConnectionInner withIpsecPolicies(List<IpsecPolicy> ipsecPolicies) {
//        this.ipsecPolicies = ipsecPolicies;
//        return this;
//    }

    /**
     * @return the provisioning state of the VirtualNetworkGatewayConnection resource
     */
    String provisioningState();

    /**
     * The entirety of the virtual network gateway connection definition.
     */
    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.WithConnectionType,
            DefinitionStages.WithLocalNetworkGateway,
            DefinitionStages.WithSecondVirtualNetworkGateway,
            DefinitionStages.WithExpressRoute,
            DefinitionStages.WithSharedKey,
            DefinitionStages.WithCreate {
    }

    /**
     * Grouping of virtual network gateway connection definition stages.
     */
    interface DefinitionStages {
        interface Blank extends WithConnectionType {
        }

        interface WithConnectionType {
            DefinitionStages.WithLocalNetworkGateway withSiteToSite();

            DefinitionStages.WithSecondVirtualNetworkGateway withVNetToVNet();

            DefinitionStages.WithExpressRoute withExpressRoute();
        }

        interface WithLocalNetworkGateway {
            DefinitionStages.WithSharedKey withLocalNetworkGateway(LocalNetworkGateway localNetworkGateway);
        }

        interface WithSecondVirtualNetworkGateway {
            DefinitionStages.WithSharedKey withSecondVirtualNetworkGateway(VirtualNetworkGateway virtualNetworkGateway2);
        }

        interface WithExpressRoute {
        }

        interface WithSharedKey {
            DefinitionStages.WithCreate withSharedKey(String sharedKey);
        }

        interface WithBGPEnabled {
            DefinitionStages.WithCreate enableBGP();
        }

        interface WithCreate extends
                Creatable<VirtualNetworkGatewayConnection>,
                Resource.DefinitionWithTags<WithCreate>,
                DefinitionStages.WithBGPEnabled {
        }
    }

    interface Update extends
            UpdateStages.WithBGPEnabled {
    }

    /**
     * Grouping of virtual network gateway connection update stages.
     */
    interface UpdateStages {

        interface WithBGPEnabled {
            Update enableBGP();

            Update disableBGP();
        }
    }
}
