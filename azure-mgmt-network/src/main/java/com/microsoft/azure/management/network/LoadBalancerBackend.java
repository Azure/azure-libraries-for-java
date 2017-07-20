/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network;

import java.util.Set;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.network.implementation.BackendAddressPoolInner;
import com.microsoft.azure.management.network.model.HasBackendNics;
import com.microsoft.azure.management.network.model.HasLoadBalancingRules;
import com.microsoft.azure.management.network.model.HasNetworkInterfaces;
import com.microsoft.azure.management.resources.fluentcore.arm.models.ChildResource;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.Settable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * An immutable client-side representation of a load balancer backend address pool.
 */
@Fluent()
@Beta
public interface LoadBalancerBackend extends
    HasInner<BackendAddressPoolInner>,
    ChildResource<LoadBalancer>,
    HasLoadBalancingRules,
    HasBackendNics {

    /**
     * @return a list of the resource IDs of the virtual machines associated with this backend
     */
    Set<String> getVirtualMachineIds();

    /**
     * Grouping of load balancer backend definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of a load balancer backend definition.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface Blank<ParentT> extends WithAttach<ParentT> {
        }

        /**
         * The stage of a load balancer backend definition allowing to select a set of virtual machines to load balance
         * the network traffic among.
         * @param <ReturnT> the next stage of the definition
         */
        interface WithVirtualMachine<ReturnT> {
            /**
             * Adds the specified set of virtual machines, assuming they are from the same
             * availability set, to this back end address pool.
             * <p>
             * This will add references to the primary IP configurations of the primary network interfaces of
             * the provided set of virtual machines.
             * <p>
             * If the virtual machines are not in the same availability set, they will not be associated with this back end.
             * <p>
             * Only those virtual machines will be associated with the load balancer that already have an existing
             * network interface. Virtual machines without a network interface will be skipped.
             * @param vms existing virtual machines
             * @return the next stage of the definition
             */
            @Beta(SinceVersion.V1_2_0)
            WithAttach<ReturnT> withExistingVirtualMachines(HasNetworkInterfaces...vms);
        }

        /** The final stage of a load balancer backend definition.
         * <p>
         * At this stage, any remaining optional settings can be specified, or the definition
         * can be attached to the parent load balancer definition using {@link WithAttach#attach()}.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithAttach<ParentT> extends
            Attachable.InDefinition<ParentT>,
            WithVirtualMachine<ParentT> {
        }
    }

    /** The entirety of a load balancer backend definition.
     * @param <ParentT> the stage of the parent definition to return to after attaching this definition
     */
    interface Definition<ParentT> extends
        DefinitionStages.Blank<ParentT>,
        DefinitionStages.WithAttach<ParentT> {
    }

    /**
     * Grouping of load balancer backend update stages.
     */
    interface UpdateStages {
    }

    /**
     * The entirety of a load balancer backend update as part of a load balancer update.
     */
    interface Update extends
        Settable<LoadBalancer.Update> {
    }

    /**
     * Grouping of load balancer backend definition stages applicable as part of a load balancer update.
     */
    interface UpdateDefinitionStages {
        /**
         * The first stage of a load balancer backend definition.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface Blank<ParentT> extends WithAttach<ParentT> {
        }

        /**
         * The stage of a load balancer backend definition allowing to select a set of virtual machines to load balance
         * the network traffic among.
         * @param <ReturnT> the next stage of the definition
         */
        interface WithVirtualMachine<ReturnT> {
            /**
             * Adds the specified set of virtual machines, assuming they are from the same
             * availability set, to this back end address pool.
             * <p>
             * This will add references to the primary IP configurations of the primary network interfaces of
             * the provided set of virtual machines.
             * <p>
             * If the virtual machines are not in the same availability set, they will not be associated with this back end.
             * <p>
             * Only those virtual machines will be associated with the load balancer that already have an existing
             * network interface. Virtual machines without a network interface will be skipped.
             * @param vms existing virtual machines
             * @return the next stage of the definition
             */
            @Beta(SinceVersion.V1_2_0)
            WithAttach<ReturnT> withExistingVirtualMachines(HasNetworkInterfaces...vms);
        }

        /** The final stage of a load balancer backend definition.
         * <p>
         * At this stage, any remaining optional settings can be specified, or the definition
         * can be attached to the parent load balancer definition using {@link WithAttach#attach()}.
         * @param <ParentT> the return type of {@link WithAttach#attach()}
         */
        interface WithAttach<ParentT> extends
            Attachable.InUpdate<ParentT>,
            WithVirtualMachine<ParentT> {
        }
    }

    /** The entirety of a load balancer backend definition as part of a load balancer update.
     * @param <ParentT> the return type of the final {@link UpdateDefinitionStages.WithAttach#attach()}
     */
    interface UpdateDefinition<ParentT> extends
        UpdateDefinitionStages.Blank<ParentT>,
        UpdateDefinitionStages.WithAttach<ParentT> {
    }
}
