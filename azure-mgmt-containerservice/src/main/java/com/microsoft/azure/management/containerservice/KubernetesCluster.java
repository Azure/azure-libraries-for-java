/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerservice;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.containerservice.implementation.ContainerServiceManager;
import com.microsoft.azure.management.containerservice.implementation.ManagedClusterInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;

import java.util.Map;

/**
 * A client-side representation for a managed Kubernetes cluster.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_4_0)
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
     * @return the key vault reference to the service principal secret
     */
    KeyVaultSecretRef keyVaultSecretReference();

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
        DefinitionStages.WithKeyVaultSecret,
        DefinitionStages.WithDnsPrefix,
        DefinitionStages.WithAgentPool,
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
             * @return the next stage of the definition
             */
            WithLinuxRootUsername withVersion(KubernetesVersion kubernetesVersion);

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

            /**
             * Properties for cluster service principals.
             *
             * @param vaultId the ID for the service principal
             * @return the next stage
             */
            WithKeyVaultSecret withKeyVaultReference(String vaultId);
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify the KeyVault secret name and version.
         */
        interface WithKeyVaultSecret {
            /**
             * Specifies the KeyVault secret.
             *
             * @param secretName the KeyVault reference to the secret which stores the password associated with the service principal
             * @return the next stage of the definition
             */
            WithAgentPool withKeyVaultSecret(String secretName);

            /**
             * Specifies the KeyVault secret and the version of it.
             *
             * @param secretName the KeyVault reference to the secret which stores the password associated with the service principal
             * @param secretVersion the KeyVault secret version to be used
             * @return the next stage of the definition
             */
            WithAgentPool withKeyVaultSecret(String secretName, String secretVersion);
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify an agent pool profile.
         */
        interface WithAgentPool {
            /**
             * Begins the definition of a agent pool profile to be attached to the Kubernetes cluster.
             *
             * @param name the name for the agent pool profile
             * @return the stage representing configuration for the agent pool profile
             */
            KubernetesClusterAgentPool.DefinitionStages.Blank<KubernetesCluster.DefinitionStages.WithCreate> defineAgentPool(String name);
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
         * The stage of the definition which contains all the minimum required inputs for
         * the resource to be created, but also allows for any other optional settings to
         * be specified.
         */
        interface WithCreate extends
            Creatable<KubernetesCluster>,
            WithDnsPrefix,
            Resource.DefinitionWithTags<WithCreate> {
        }
    }

    /**
     * The template for an update operation, containing all the settings that can be modified.
     */
    interface Update extends
        Resource.UpdateWithTags<KubernetesCluster.Update>,
        Appliable<KubernetesCluster>,
        KubernetesCluster.UpdateStages.WithUpdateAgentPoolCount {
    }

    /**
     * Grouping of the Kubernetes cluster update stages.
     */
    interface UpdateStages {
        /**
         * The stage of the Kubernetes cluster update definition allowing to specify the number of agents in the specified pool.
         */
        interface WithUpdateAgentPoolCount {
            /**
             * Updates the agent pool virtual machine count.
             *
             * @param agentPoolName the name of the agent pool to be updated
             * @param agentCount the number of agents (virtual machines) to host docker containers.
             * @return the next stage of the update
             */
            KubernetesCluster.Update withAgentVirtualMachineCount(String agentPoolName, int agentCount);

            /**
             * Updates all the agent pools virtual machine count.
             *
             * @param agentCount the number of agents (virtual machines) to host docker containers.
             * @return the next stage of the update
             */
            KubernetesCluster.Update withAgentVirtualMachineCount(int agentCount);
        }
    }
}
