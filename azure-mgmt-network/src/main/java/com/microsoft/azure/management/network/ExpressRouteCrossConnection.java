/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.network.implementation.ExpressRouteCrossConnectionInner;
import com.microsoft.azure.management.network.implementation.NetworkManager;
import com.microsoft.azure.management.network.model.UpdatableWithTags;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;

import java.util.Map;

/**
 * Entry point for Express Route Cross Connection management API in Azure.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_11_0)
public interface ExpressRouteCrossConnection extends
        GroupableResource<NetworkManager, ExpressRouteCrossConnectionInner>,
        Refreshable<ExpressRouteCrossConnection>,
        Updatable<ExpressRouteCrossConnection.Update>,
        UpdatableWithTags<ExpressRouteCrossConnection> {
    /**
     * @return the name of the primary  port
     */
    String primaryAzurePort();

    /**
     * @return the name of the secondary  port
     */
    String secondaryAzurePort();

    /**
     * The identifier of the circuit traffic.
     */
    Integer sTag();

    /**
     * @return the peering location of the ExpressRoute circuit
     */
    String peeringLocation();

    /**
     * The circuit bandwidth In Mbps.
     */
    int bandwidthInMbps();

    /**
     * The ExpressRouteCircuit.
     */
    ExpressRouteCircuitReference expressRouteCircuit();

    /**
     * @return the provisioning state of the circuit in the connectivity provider system
     */
    ServiceProviderProvisioningState serviceProviderProvisioningState();

    /**
     * Additional read only notes set by the connectivity provider.
     */
    String serviceProviderNotes();

    /**
     * Gets the provisioning state of the express route cross connection resource. Possible values
     * are: 'Updating', 'Deleting', and 'Failed'.
     */
    String provisioningState();

    /**
     * @return the peerings associated with this express route cross connection, indexed by name
     */
//    Map<String, ExpressRouteCrossConnectionPeering> peeringsMap();

    /**
     * The entirety of the express route circuit definition.
     */
    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.WithGroup,
//            DefinitionStages.WithServiceProvider,
//            DefinitionStages.WithPeeringLocation,
//            DefinitionStages.WithBandwidth,
//            DefinitionStages.WithSku,
            DefinitionStages.WithCreate {
    }

    /**
     * Grouping of express route cross connection definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of express route circuit definition.
         */
        interface Blank extends GroupableResource.DefinitionWithRegion<WithGroup> {
        }

        /**
         * The stage of express route cross connection definition allowing to specify the resource group.
         */
        interface WithGroup
                extends GroupableResource.DefinitionStages.WithGroup<DefinitionStages.WithCreate> {
        }

        /**
         * The stage of the express route cross connection definition which contains all the minimum required inputs for
         * the resource to be created, but also allows for any other optional settings to be specified.
         */
        interface WithCreate extends
                Creatable<ExpressRouteCrossConnection>,
                Resource.DefinitionWithTags<WithCreate> {
        }
    }

    /**
     * Grouping of express route cross connection update stages.
     */
    interface UpdateStages {
    }

    /**
     * The template for a express route cross connection update operation, containing all the settings that
     * can be modified.
     */
    interface Update extends
            Appliable<ExpressRouteCrossConnection>,
            Resource.UpdateWithTags<Update> {
    }
}
