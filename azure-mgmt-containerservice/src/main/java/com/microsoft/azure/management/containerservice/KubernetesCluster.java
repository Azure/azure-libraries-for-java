/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerservice;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.containerservice.implementation.ContainerServiceManager;
import com.microsoft.azure.management.containerservice.implementation.ManagedClusterInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;

import java.util.Map;

/**
 * A client-side representation for a managed Kubernetes cluster.
 */
@Fluent
@Beta(SinceVersion.V1_15_0)
public interface KubernetesCluster extends
        GroupableResource<ContainerServiceManager, ManagedClusterInner>,
        Refreshable<KubernetesCluster>,
        Updatable<KubernetesCluster.Update>,
    OrchestratorServiceBase {

    /**
     * @return the provisioning state of the Kubernetes cluster
     */
    String provisioningState();

    /**
     * @return the DNS prefix which was specified at creation time
     */
    String dnsPrefix();

    /**
     * @return the FQDN for the master pool
     */
    String fqdn();

    /**
     * @return the Kubernetes version
     */
    KubernetesVersion version();

    /**
     * @return the Kubernetes configuration file content with administrative privileges to the cluster
     */
    byte[] adminKubeConfigContent();

    /**
     * @return the Kubernetes configuration file content with user-level privileges to the cluster
     */
    byte[] userKubeConfigContent();

    /**
     * @return the service principal client ID
     */
    String servicePrincipalClientId();

    /**
     * @return the service principal secret
     */
    String servicePrincipalSecret();

    /**
     * @return the Linux root username
     */
    String linuxRootUsername();

    /**
     * @return the Linux SSH key
     */
    String sshKey();

    /**
     * @return the agent pools in the Kubernetes cluster
     */
    Map<String, KubernetesClusterAgentPool> agentPools();

    /**
     * @return the network profile settings for the cluster
     */
    @Beta(SinceVersion.V1_15_0)
    ContainerServiceNetworkProfile networkProfile();

    /**
     * @return the cluster's add-on's profiles
     */
    @Beta(SinceVersion.V1_15_0)
    Map<String, ManagedClusterAddonProfile> addonProfiles();

    /**
     * @return the name of the resource group containing agent pool nodes
     */
    @Beta(SinceVersion.V1_15_0)
    String nodeResourceGroup();

    /**
     * @return true if Kubernetes Role-Based Access Control is enabled
     */
    @Beta(SinceVersion.V1_15_0)
    boolean enableRBAC();

    /**
     * @return the sku of the cluster
     */
    @Beta(SinceVersion.V1_34_0)
    ManagedClusterSKU sku();

    // Fluent interfaces

    /**
     * Interface for all the definitions related to a Kubernetes cluster.
     */
    interface Definition extends
        KubernetesCluster.DefinitionStages.Blank,
        KubernetesCluster.DefinitionStages.WithGroup,
        KubernetesCluster.DefinitionStages.WithVersion,
        DefinitionStages.WithLinuxRootUsername,
        DefinitionStages.WithLinuxSshKey,
        DefinitionStages.WithServicePrincipalClientId,
        DefinitionStages.WithServicePrincipalProfile,
        DefinitionStages.WithDnsPrefix,
        DefinitionStages.WithAgentPool,
        DefinitionStages.WithNetworkProfile,
        DefinitionStages.WithAddOnProfiles,
        KubernetesCluster.DefinitionStages.WithCreate {
    }

    /**
     * Grouping of Kubernetes cluster definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of a container service definition.
         */
        interface Blank extends
            DefinitionWithRegion<WithGroup> {
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify the resource group.
         */
        interface WithGroup extends
            GroupableResource.DefinitionStages.WithGroup<WithVersion> {
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify orchestration type.
         */
        interface WithVersion {
            /**
             * Specifies the version for the Kubernetes cluster.
             *
             * @param kubernetesVersion the kubernetes version
             * @return the next stage of the definition
             */
            @Deprecated
            WithLinuxRootUsername withVersion(KubernetesVersion kubernetesVersion);

            /**
             * Specifies the version for the Kubernetes cluster.
             *
             * @param kubernetesVersion the kubernetes version
             * @return the next stage of the definition
             */
            WithLinuxRootUsername withVersion(String kubernetesVersion);

            /**
             * Uses the latest version for the Kubernetes cluster.
             *
             * @return the next stage of the definition
             */
            @Method
            WithLinuxRootUsername withLatestVersion();
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specific the Linux root username.
         */
        interface WithLinuxRootUsername {
            /**
             * Begins the definition to specify Linux root username.
             *
             * @param rootUserName the root username
             * @return the next stage of the definition
             */
            WithLinuxSshKey withRootUsername(String rootUserName);
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specific the Linux SSH key.
         */
        interface WithLinuxSshKey {
            /**
             * Begins the definition to specify Linux ssh key.
             *
             * @param sshKeyData the SSH key data
             * @return the next stage of the definition
             */
            WithServicePrincipalClientId withSshKey(String sshKeyData);
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify the service principal client ID.
         */
        interface WithServicePrincipalClientId {
            /**
             * Properties for Kubernetes cluster service principal.
             *
             * @param clientId the ID for the service principal
             * @return the next stage
             */
            WithServicePrincipalProfile withServicePrincipalClientId(String clientId);
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify the service principal secret.
         */
        interface WithServicePrincipalProfile {
            /**
             * Properties for  service principal.
             *
             * @param secret the secret password associated with the service principal
             * @return the next stage
             */
            WithAgentPool withServicePrincipalSecret(String secret);
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify an agent pool profile.
         */
        interface WithAgentPool {
            /**
             * Begins the definition of an agent pool profile to be attached to the Kubernetes cluster.
             *
             * @param name the name for the agent pool profile
             * @return the stage representing configuration for the agent pool profile
             */
            KubernetesClusterAgentPool.DefinitionStages.Blank<? extends WithCreate> defineAgentPool(String name);
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify a network profile.
         */
        @Beta(SinceVersion.V1_15_0)
        interface WithNetworkProfile {
            /**
             * Begins the definition of a network profile to be attached to the Kubernetes cluster.
             *
             * @return the stage representing configuration for the network profile
             */
            @Beta(SinceVersion.V1_15_0)
            NetworkProfileDefinitionStages.Blank<KubernetesCluster.DefinitionStages.WithCreate> defineNetworkProfile();
        }

        /**
         * The Kubernetes cluster definition allowing to specify a network profile.
         */
        interface NetworkProfileDefinitionStages {
            /**
             * The first stage of a network profile definition.
             *
             * @param <ParentT>  the stage of the Kubernetes cluster network profile definition to return to after attaching this definition
             */
            @Beta(SinceVersion.V1_15_0)
            interface Blank<ParentT>  extends WithAttach<ParentT> {
                /**
                 * Specifies the network plugin type to be used for building the Kubernetes network.
                 *
                 * @param networkPlugin the network plugin type to be used for building the Kubernetes network
                 * @return the next stage of the definition
                 */
                WithAttach<ParentT> withNetworkPlugin(NetworkPlugin networkPlugin);
            }

            /**
             * The stage of a network profile definition allowing to specify the network policy.
             *
             * @param <ParentT>  the stage of the network profile definition to return to after attaching this definition
             */
            interface WithNetworkPolicy<ParentT> {
                /**
                 * Specifies the network policy to be used for building the Kubernetes network.
                 *
                 * @param networkPolicy the network policy to be used for building the Kubernetes network
                 * @return the next stage of the definition
                 */
                WithAttach<ParentT> withNetworkPolicy(NetworkPolicy networkPolicy);
            }

            /**
             * The stage of a network profile definition allowing to specify a CIDR notation IP range from which to
             * assign pod IPs when kubenet is used.
             *
             * @param <ParentT>  the stage of the network profile definition to return to after attaching this definition
             */
            interface WithPodCidr<ParentT> {
                /**
                 * Specifies a CIDR notation IP range from which to assign pod IPs when kubenet is used.
                 *
                 * @param podCidr the CIDR notation IP range from which to assign pod IPs when kubenet is used
                 * @return the next stage of the definition
                 */
                WithAttach<ParentT> withPodCidr(String podCidr);
            }

            /**
             * The stage of a network profile definition allowing to specify a CIDR notation IP range from which to
             * assign service cluster IPs.
             *
             * @param <ParentT>  the stage of the network profile definition to return to after attaching this definition
             */
            interface WithServiceCidr<ParentT> {
                /**
                 * Specifies a CIDR notation IP range from which to assign service cluster IPs; must not overlap with
                 *   any subnet IP ranges.
                 *
                 * @param serviceCidr the CIDR notation IP range from which to assign service cluster IPs; it must not
                 *                    overlap with any Subnet IP ranges
                 * @return the next stage of the definition
                 */
                WithAttach<ParentT> withServiceCidr(String serviceCidr);
            }

            /**
             * The stage of a network profile definition allowing to specify an IP address assigned to the Kubernetes DNS service.
             *
             * @param <ParentT>  the stage of the network profile definition to return to after attaching this definition
             */
            interface WithDnsServiceIP<ParentT> {
                /**
                 * Specifies an IP address assigned to the Kubernetes DNS service; it must be within the Kubernetes service
                 *   address range specified in the service CIDR.
                 *
                 * @param dnsServiceIP the IP address assigned to the Kubernetes DNS service; it must be within the
                 *                     Kubernetes service address range specified in the service CIDR
                 * @return the next stage of the definition
                 */
                WithAttach<ParentT> withDnsServiceIP(String dnsServiceIP);
            }

            /**
             * The stage of a network profile definition allowing to specify a CIDR notation IP range assigned to the
             *   Docker bridge network.
             *
             * @param <ParentT>  the stage of the network profile definition to return to after attaching this definition
             */
            interface WithDockerBridgeCidr<ParentT> {
                /**
                 * Specifies a CIDR notation IP range assigned to the Docker bridge network; it must not overlap with
                 *   any subnet IP ranges or the Kubernetes service address range.
                 *
                 * @param dockerBridgeCidr the CIDR notation IP range assigned to the Docker bridge network; it must not
                 *                         overlap with any subnet IP ranges or the Kubernetes service address range
                 * @return the next stage of the definition
                 */
                WithAttach<ParentT> withDockerBridgeCidr(String dockerBridgeCidr);
            }

            /** The final stage of a network profile definition.
             * At this stage, any remaining optional settings can be specified, or the container service agent pool
             * can be attached to the parent container service definition.
             * @param <ParentT> the stage of the container service definition to return to after attaching this definition
             */
            interface WithAttach<ParentT> extends
                NetworkProfileDefinitionStages.WithNetworkPolicy<ParentT>,
                NetworkProfileDefinitionStages.WithPodCidr<ParentT>,
                NetworkProfileDefinitionStages.WithServiceCidr<ParentT>,
                NetworkProfileDefinitionStages.WithDnsServiceIP<ParentT>,
                NetworkProfileDefinitionStages.WithDockerBridgeCidr<ParentT>,
                Attachable.InDefinition<ParentT> {
            }
        }

        /**
         * The Kubernetes cluster network profile definition.
         * The entirety of a Kubernetes cluster network profile definition as a part of a parent definition.
         * @param <ParentT>  the stage of the container service definition to return to after attaching this definition
         */
        interface NetworkProfileDefinition<ParentT> extends
            NetworkProfileDefinitionStages.Blank<ParentT>,
            NetworkProfileDefinitionStages.WithNetworkPolicy<ParentT>,
            NetworkProfileDefinitionStages.WithPodCidr<ParentT>,
            NetworkProfileDefinitionStages.WithServiceCidr<ParentT>,
            NetworkProfileDefinitionStages.WithDnsServiceIP<ParentT>,
            NetworkProfileDefinitionStages.WithDockerBridgeCidr<ParentT>,
            NetworkProfileDefinitionStages.WithAttach<ParentT> {
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify the DNS prefix label.
         */
        interface WithDnsPrefix {
            /**
             * Specifies the DNS prefix to be used to create the FQDN for the master pool.
             *
             * @param dnsPrefix the DNS prefix to be used to create the FQDN for the master pool
             * @return the next stage of the definition
             */
            KubernetesCluster.DefinitionStages.WithCreate withDnsPrefix(String dnsPrefix);
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify the cluster's add-on's profiles.
         */
        interface WithAddOnProfiles {
            /**
             * Specifies the cluster's add-on's profiles.
             *
             * @param addOnProfileMap the cluster's add-on's profiles
             * @return the next stage of the definition
             */
            @Beta(SinceVersion.V1_15_0)
            KubernetesCluster.DefinitionStages.WithCreate withAddOnProfiles(Map<String, ManagedClusterAddonProfile> addOnProfileMap);
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify the cluster's sku.
         */
        interface WithSku {
            /**
             * Specifies the cluster's sku.
             *
             * @param sku the cluster's sku
             * @return the next stage of the definition
             */
            @Beta(SinceVersion.V1_34_0)
            KubernetesCluster.DefinitionStages.WithCreate withSku(ManagedClusterSKU sku);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for
         * the resource to be created, but also allows for any other optional settings to
         * be specified.
         */
        interface WithCreate extends
            Creatable<KubernetesCluster>,
            WithNetworkProfile,
            WithDnsPrefix,
            WithAddOnProfiles,
            WithSku,
            WithAgentPool,
            Resource.DefinitionWithTags<WithCreate> {
        }
    }

    /**
     * The template for an update operation, containing all the settings that can be modified.
     */
    interface Update extends
        KubernetesCluster.UpdateStages.WithAgentPool,
        KubernetesCluster.UpdateStages.WithAddOnProfiles,
        KubernetesCluster.UpdateStages.WithNetworkProfile,
        KubernetesCluster.UpdateStages.WithRBAC,
        Resource.UpdateWithTags<KubernetesCluster.Update>,
        Appliable<KubernetesCluster> {
    }

    /**
     * Grouping of the Kubernetes cluster update stages.
     */
    interface UpdateStages {
        /**
         * The stage of the Kubernetes cluster update definition allowing to specify the agent poll in the cluster.
         */
        interface WithAgentPool {
            /**
             * Updates the agent pool virtual machine count.
             *
             * Recommended to use `updateAgentPool` method.
             *
             * @param agentPoolName the name of the agent pool to be updated
             * @param agentCount the number of agents (virtual machines) to host docker containers.
             * @return the stage representing configuration for the agent pool profile
             */
            @Beta(SinceVersion.V1_15_0)
            KubernetesCluster.Update withAgentPoolVirtualMachineCount(String agentPoolName, int agentCount);

            /**
             * Updates the virtual machine count for all agent pools.
             *
             * @deprecated use `updateAgentPool` method to update a specific agent pool.
             *
             * @param agentCount the number of agents (virtual machines) to host docker containers.
             * @return the stage representing configuration for the agent pool profile
             */
            @Beta(SinceVersion.V1_15_0)
            @Deprecated
            KubernetesCluster.Update withAgentPoolVirtualMachineCount(int agentCount);

            /**
             * Begins the definition of an agent pool profile to be attached to the Kubernetes cluster.
             *
             * @param name the name for the agent pool profile
             * @return the stage representing configuration for the agent pool profile
             */
            KubernetesClusterAgentPool.DefinitionStages.Blank<? extends Update> defineAgentPool(String name);

            /**
             * Begins the definition of an agent pool profile to be attached to the Kubernetes cluster.
             *
             * @param name the name for the agent pool profile
             * @return the stage representing configuration for the agent pool profile
             */
            KubernetesClusterAgentPool.Update<? extends Update> updateAgentPool(String name);
        }

        /**
         * The stage of the Kubernetes cluster update definition allowing to specify the cluster's add-on's profiles.
         */
        interface WithAddOnProfiles {
            /**
             * Updates the cluster's add-on's profiles.
             *
             * @param addOnProfileMap the cluster's add-on's profiles
             * @return the next stage of the update
             */
            @Beta(SinceVersion.V1_15_0)
            KubernetesCluster.Update withAddOnProfiles(Map<String, ManagedClusterAddonProfile> addOnProfileMap);
        }

        /**
         * The stage of the Kubernetes cluster update definition allowing to specify the cluster's network profile.
         */
        interface WithNetworkProfile {
            /**
             * Updates the cluster's network profile.
             *
             * @param networkProfile the cluster's networkProfile
             * @return the next stage of the update
             */
            @Beta(SinceVersion.V1_15_0)
            KubernetesCluster.Update withNetworkProfile(ContainerServiceNetworkProfile networkProfile);
        }

        /**
         * The stage of the Kubernetes cluster update definition allowing to specify if Kubernetes Role-Based Access Control is enabled or disabled.
         */
        interface WithRBAC {
            /**
             * Updates the cluster to specify the Kubernetes Role-Based Access Control is enabled.
             *
             * @return the next stage of the update
             */
            @Beta(SinceVersion.V1_15_0)
            KubernetesCluster.Update withRBACEnabled();

            /**
             * Updates the cluster to specify the Kubernetes Role-Based Access Control is disabled.
             *
             * @return the next stage of the update
             */
            @Beta(SinceVersion.V1_15_0)
            KubernetesCluster.Update withRBACDisabled();
        }
    }
}
