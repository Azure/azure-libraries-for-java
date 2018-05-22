/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.network.implementation.ExpressRouteCrossConnectionPeeringInner;
import com.microsoft.azure.management.network.implementation.NetworkManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.IndependentChild;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;

/**
 * Client-side representation of express route cross connection peering object, associated with express route cross connection.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_11_0)
public interface ExpressRouteCrossConnectionPeering extends
        IndependentChild<NetworkManager>,
        HasInner<ExpressRouteCrossConnectionPeeringInner>,
        Refreshable<ExpressRouteCrossConnectionPeering>,
        Updatable<ExpressRouteCrossConnectionPeering.Update> {
    /**
     * @return the peering type
     */
    ExpressRoutePeeringType peeringType();

    /**
     * @return the peering state
     */
    ExpressRoutePeeringState state();

    /**
     * @return the Azure ASN
     */
    int azureASN();

    /**
     * @return the peer ASN
     */
    long peerASN();

    /**
     * @return the primary address prefix
     */
    String primaryPeerAddressPrefix();

    /**
     * The secondary address prefix.
     */
    String secondaryPeerAddressPrefix();

    /**
     * @return the primary port
     */
    String primaryAzurePort();

    /**
     * @return the secondary port
     */
    String secondaryAzurePort();

    /**
     * @return the shared key
     */
    String sharedKey();

    /**
     * @return the VLAN ID
     */
    int vlanId();

    /**
     * @return the Microsoft peering configuration
     */
    ExpressRouteCircuitPeeringConfig microsoftPeeringConfig();

    /**
     * @return  the provisioning state of the public IP resource
     */
    String provisioningState();

    /**
     * @return the GatewayManager Etag
     */
    String gatewayManagerEtag();

    /**
     * @return whether the provider or the customer last modified the peering
     */
    String lastModifiedBy();

    /**
     * @return the IPv6 peering configuration.
     */
    Ipv6ExpressRouteCircuitPeeringConfig ipv6PeeringConfig();


    /**
     * The entirety of the express route Cross Connection peering definition.
     */
    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.WithAdvertisedPublicPrefixes,
            DefinitionStages.WithPrimaryPeerAddressPrefix,
            DefinitionStages.WithSecondaryPeerAddressPrefix,
            DefinitionStages.WithVlanId,
            DefinitionStages.WithPeerAsn,
            DefinitionStages.WithCreate {
    }

    /**
     * Grouping of express route Cross Connection peering definition stages.
     */
    interface DefinitionStages {
        interface Blank extends WithPrimaryPeerAddressPrefix {
        }

        /**
         * The stage of Express Route Cross Connection Peering definition allowing to specify advertised address prefixes.
         */
        interface WithAdvertisedPublicPrefixes {
            /**
             * Specify advertised prefixes: sets a list of all prefixes that are planned to advertise over the BGP session.
             * Only public IP address prefixes are accepted. A set of prefixes can be sent as a comma-separated list.
             * These prefixes must be registered to you in an RIR / IRR.
             * @param publicPrefixes advertised prefixes
             * @return next stage of definition
             */
            WithPrimaryPeerAddressPrefix withAdvertisedPublicPrefixes(String publicPrefixes);
        }

        /**
         * The stage of Express Route Cross Connection Peering definition allowing to specify primary address prefix.
         */
        interface WithPrimaryPeerAddressPrefix {
            WithSecondaryPeerAddressPrefix withPrimaryPeerAddressPrefix(String addressPrefix);
        }

        /**
         * The stage of Express Route Cross Connection Peering definition allowing to specify secondary address prefix.
         */
        interface WithSecondaryPeerAddressPrefix {
            WithVlanId withSecondaryPeerAddressPrefix(String addressPrefix);
        }

        /**
         * The stage of Express Route Cross Connection Peering definition allowing to specify VLAN ID.
         */
        interface WithVlanId {
            /**
             *
             * @param vlanId a valid VLAN ID to establish this peering on. No other peering in the circuit can use the same VLAN ID
             * @return next stage of definition
             */
            WithPeerAsn withVlanId(int vlanId);
        }

        /**
         * The stage of Express Route Cross Connection Peering definition allowing to specify AS number for peering.
         */
        interface WithPeerAsn {
            /**
             * @param peerAsn AS number for peering. Both 2-byte and 4-byte AS numbers can be used
             * @return next stage of definition
             */
            WithCreate withPeerAsn(long peerAsn);
        }

        interface WithSharedKey {
            WithCreate withSharedKey(String sharedKey);
        }

        interface WithIpv6PeeringConfig {
            Ipv6PeeringConfig.DefinitionStages.Blank<WithCreate> defineIpv6Config();
        }

        interface WithCreate extends
                Creatable<ExpressRouteCrossConnectionPeering>,
                DefinitionStages.WithSharedKey,
                DefinitionStages.WithIpv6PeeringConfig {
        }
    }

    /**
     * Grouping of express route cross connection peering update stages.
     */
    interface Update extends Appliable<ExpressRouteCrossConnectionPeering>,
            UpdateStages.WithAdvertisedPublicPrefixes,
            UpdateStages.WithPrimaryPeerAddressPrefix,
            UpdateStages.WithSecondaryPeerAddressPrefix,
            UpdateStages.WithVlanId,
            UpdateStages.WithPeerAsn {
    }

    /**
     * The template for express route Cross Connection peering update operation, containing all the settings that
     * can be modified.
     */
    interface UpdateStages {
        /**
         * The stage of Express Route Cross Connection Peering update allowing to specify advertised address prefixes.
         */
        interface WithAdvertisedPublicPrefixes {
            Update withAdvertisedPublicPrefixes(String publicPrefixes);
        }

        /**
         * The stage of Express Route Cross Connection Peering update allowing to specify primary address prefix.
         */
        interface WithPrimaryPeerAddressPrefix {
            Update withPrimaryPeerAddressPrefix(String addressPrefix);
        }

        /**
         * The stage of Express Route Cross Connection Peering update allowing to specify secondary address prefix.
         */
        interface WithSecondaryPeerAddressPrefix {
            Update withSecondaryPeerAddressPrefix(String addressPrefix);
        }

        /**
         * The stage of Express Route Cross Connection Peering update allowing to specify VLAN ID.
         */
        interface WithVlanId {
            Update withVlanId(int vlanId);
        }

        /**
         * The stage of Express Route Cross Connection Peering update allowing to specify AS number for peering.
         */
        interface WithPeerAsn {
            Update withPeerAsn(long peerAsn);
        }
    }
}
