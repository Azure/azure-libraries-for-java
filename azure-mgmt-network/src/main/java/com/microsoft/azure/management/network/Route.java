/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.network.implementation.RouteInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.ChildResource;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.Settable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * An immutable client-side representation of a route of a route table.
 */
@Fluent()
public interface Route extends
    HasInner<RouteInner>,
    ChildResource<RouteTable> {

    /**
     * @return the destination address prefix, expressed using the CIDR notation, to which the route applies
     */
    String destinationAddressPrefix();

    /**
     * @return the type of the next hop
     */
    RouteNextHopType nextHopType();

    /**
     * @return the IP address of the next hop
     */
    String nextHopIpAddress();

    // Grouping of route definition stages

    /**
     * Grouping of route definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of a route definition.
         * @param <ParentT> the return type of the final {@link WithAttach#attach()}
         */
        interface Blank<ParentT> extends WithDestinationAddressPrefix<ParentT> {
        }

        /** The final stage of a route definition.
         * <p>
         * At this stage, any remaining optional settings can be specified, or the route definition
         * can be attached to the parent route table definition using {@link WithAttach#attach()}.
         * @param <ParentT> the return type of {@link WithAttach#attach()}
         */
        interface WithAttach<ParentT> extends
            Attachable.InDefinition<ParentT> {
        }

        /**
         * The stage of a route definition allowing to specify the destination address prefix.
         * @param <ParentT> the return type of {@link WithAttach#attach()}
         */
        interface WithDestinationAddressPrefix<ParentT> {
            /**
             * Specifies the destination address prefix to apply the route to.
             * @param cidr an address prefix expressed in the CIDR notation
             * @return the next stage of the definition
             */
            WithNextHopType<ParentT> withDestinationAddressPrefix(String cidr);
        }

        /**
         * The stage of a route definition allowing to specify the next hop type.
         * @param <ParentT> the return type of {@link WithAttach#attach()}
         */
        interface WithNextHopType<ParentT> {
            /**
             * Specifies the next hop type.
             * <p>
             * To use a virtual appliance, use {@link #withNextHopToVirtualAppliance(String)} instead and specify its IP address.
             * @param nextHopType a hop type
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withNextHop(RouteNextHopType nextHopType);

            /**
             * Specifies the IP address of the virtual appliance for the next hop to go to.
             * @param ipAddress an IP address of an existing virtual appliance (virtual machine)
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withNextHopToVirtualAppliance(String ipAddress);
        }
    }

    /** The entirety of a route definition.
     * @param <ParentT> the return type of the final {@link DefinitionStages.WithAttach#attach()}
     */
    interface Definition<ParentT> extends
        DefinitionStages.Blank<ParentT>,
        DefinitionStages.WithAttach<ParentT>,
        DefinitionStages.WithNextHopType<ParentT>,
        DefinitionStages.WithDestinationAddressPrefix<ParentT> {
    }

    /**
     * Grouping of route update stages.
     */
    interface UpdateStages {
        /**
         * The stage of a route update allowing to modify the destination address prefix.
         */
        interface WithDestinationAddressPrefix {
            /**
             * Specifies the destination address prefix to apply the route to.
             * @param cidr an address prefix expressed in the CIDR notation
             * @return the next stage of the update
             */
            Update withDestinationAddressPrefix(String cidr);
        }

        /**
         * The stage of a route update allowing to specify the next hop type.
         */
        interface WithNextHopType {
            /**
             * Specifies the next hop type.
             * <p>
             * To use a virtual appliance, use {@link #withNextHopToVirtualAppliance(String)} instead and specify its IP address.
             * @param nextHopType a hop type
             * @return the next stage of the update
             */
            Update withNextHop(RouteNextHopType nextHopType);

            /**
             * Specifies the IP address of the virtual appliance for the next hop to go to.
             * @param ipAddress an IP address of an existing virtual appliance (virtual machine)
             * @return the next stage of the update
             */
            Update withNextHopToVirtualAppliance(String ipAddress);
        }
    }

    /**
     * The entirety of a route update as part of a route table update.
     */
    interface Update extends
        Settable<RouteTable.Update>,
        UpdateStages.WithDestinationAddressPrefix,
        UpdateStages.WithNextHopType {
    }

    /**
     * Grouping of route definition stages applicable as part of a route table update.
     */
    interface UpdateDefinitionStages {
        /**
         * The first stage of a route definition.
         * @param <ParentT> the return type of the final {@link WithAttach#attach()}
         */
        interface Blank<ParentT> extends WithDestinationAddressPrefix<ParentT> {
        }

        /** The final stage of a route definition.
         * <p>
         * At this stage, any remaining optional settings can be specified, or the route definition
         * can be attached to the parent route table definition using {@link WithAttach#attach()}.
         * @param <ParentT> the return type of {@link WithAttach#attach()}
         */
        interface WithAttach<ParentT> extends
            Attachable.InUpdate<ParentT> {
        }

        /**
         * The stage of a route definition allowing to specify the destination address prefix.
         * @param <ParentT> the return type of {@link WithAttach#attach()}
         */
        interface WithDestinationAddressPrefix<ParentT> {
            /**
             * Specifies the destination address prefix to apply the route to.
             * @param cidr an address prefix expressed in the CIDR notation
             * @return the next stage of the definition
             */
            WithNextHopType<ParentT> withDestinationAddressPrefix(String cidr);
        }

        /**
         * The stage of a route definition allowing to specify the next hop type.
         * @param <ParentT> the return type of {@link WithAttach#attach()}
         */
        interface WithNextHopType<ParentT> {
            /**
             * Specifies the next hop type.
             * <p>
             * To use a virtual appliance, use {@link #withNextHopToVirtualAppliance(String)} instead and specify its IP address.
             * @param nextHopType a hop type
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withNextHop(RouteNextHopType nextHopType);

            /**
             * Specifies the IP address of the virtual appliance for the next hop to go to.
             * @param ipAddress an IP address of an existing virtual appliance (virtual machine)
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withNextHopToVirtualAppliance(String ipAddress);
        }
    }

    /** The entirety of a route definition as part of a route table update.
     * @param <ParentT> the return type of the final {@link UpdateDefinitionStages.WithAttach#attach()}
     */
    interface UpdateDefinition<ParentT> extends
       UpdateDefinitionStages.Blank<ParentT>,
       UpdateDefinitionStages.WithAttach<ParentT>,
       UpdateDefinitionStages.WithNextHopType<ParentT>,
       UpdateDefinitionStages.WithDestinationAddressPrefix<ParentT> {
    }
}
