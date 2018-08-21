/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerinstance;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.containerinstance.implementation.ContainerGroupInner;
import com.microsoft.azure.management.containerinstance.implementation.ContainerInstanceManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import rx.Completable;
import rx.Observable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * An immutable client-side representation of an Azure Container Group.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_3_0)
public interface ContainerGroup extends
    GroupableResource<ContainerInstanceManager, ContainerGroupInner>,
    Refreshable<ContainerGroup>,
    Updatable<ContainerGroup.Update> {

    /***********************************************************
     * Getters
     ***********************************************************/

    /**
     * @return the container instances in this container group
     */
    Map<String, Container> containers();

    /**
     * @return all the ports publicly exposed for this container group
     */
    @Beta(Beta.SinceVersion.V1_5_0)
    Set<Port> externalPorts();

    /**
     * @return the TCP ports publicly exposed for this container group
     */
    int[] externalTcpPorts();

    /**
     * @return the UDP ports publicly exposed for this container group
     */
    int[] externalUdpPorts();

    /**
     * @return the volumes for this container group
     */
    Map<String, Volume> volumes();

    /**
     * @return the Docker image registry servers by which the container group is created from
     */
    Collection<String> imageRegistryServers();

    /**
     * @return the container group restart policy

     */
    @Beta(Beta.SinceVersion.V1_5_0)
    ContainerGroupRestartPolicy restartPolicy();

    /**
     * @return the DNS prefix which was specified at creation time
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    String dnsPrefix();

    /**
     * @return the FQDN for the container group
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    String fqdn();

    /**
     * @return the IP address
     */
    String ipAddress();

    /**
     * @return true if IP address is public
     */
    boolean isIPAddressPublic();

    /**
     * @return the base level OS type required by the containers in the group
     */
    OperatingSystemTypes osType();

    /**
     * @return the state of the container group; only valid in response
     */
    String state();

    /**
     * @return the provisioningState of the container group
     */
    String provisioningState();

    /**
     * @return the container group events
     */
    @Beta(Beta.SinceVersion.V1_5_0)
    Set<Event> events();


    /***********************************************************
     * Actions
     ***********************************************************/

    /**
     * Restarts all containers in a container group in place. If container image has updates, new image will be downloaded.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    @Method
    void restart();

    /**
     * Restarts all containers in a container group in place asynchronously. If container image has updates, new image will be downloaded.
     *
     * @return a representation of the deferred computation of this call
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    @Method
    Completable restartAsync();

    /**
     * Stops all containers in a container group. Compute resources will be de-allocated and billing will stop.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    @Method
    void stop();

    /**
     * Stops all containers in a container group asynchronously. Compute resources will be de-allocated and billing will stop.
     *
     * @return a representation of the deferred computation of this call
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    @Method
    Completable stopAsync();

    /**
     * Get the log content for the specified container instance within the container group.
     *
     * @param containerName the container instance name
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return all available log lines
     */
    String getLogContent(String containerName);

    /**
     * Get the log content for the specified container instance within the container group.
     *
     * @param containerName the container instance name
     * @param tailLineCount only get the last log lines up to this
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the log lines from the end, up to the number specified
     */
    String getLogContent(String containerName, int tailLineCount);

    /**
     * Get the log content for the specified container instance within the container group.
     *
     * @param containerName the container instance name
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return a representation of the future computation of this call
     */
    Observable<String> getLogContentAsync(String containerName);

    /**
     * Get the log content for the specified container instance within the container group.
     *
     * @param containerName the container instance name
     * @param tailLineCount only get the last log lines up to this
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return a representation of the future computation of this call
     */
    Observable<String> getLogContentAsync(String containerName, int tailLineCount);

    /**
     * Starts the exec command for a specific container instance.
     *
     * @param containerName the container instance name
     * @param command the command to be executed
     * @param row the row size of the terminal
     * @param column the column size of the terminal
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the log lines from the end, up to the number specified
     */
    @Beta(Beta.SinceVersion.V1_11_0)
    ContainerExecResponse executeCommand(String containerName, String command, int row, int column);

    /**
     * Starts the exec command for a specific container instance within the container group.
     *
     * @param containerName the container instance name
     * @param command the command to be executed
     * @param row the row size of the terminal
     * @param column the column size of the terminal
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return a representation of the future computation of this call
     */
    @Beta(Beta.SinceVersion.V1_11_0)
    Observable<ContainerExecResponse> executeCommandAsync(String containerName, String command, int row, int column);


    /**
     * Starts the exec command for a specific container instance within the current group asynchronously.
     */
    interface Definition extends
        DefinitionStages.Blank,
        DefinitionStages.WithGroup,
        DefinitionStages.WithOsType,
        DefinitionStages.WithPublicOrPrivateImageRegistry,
        DefinitionStages.WithPrivateImageRegistryOrVolume,
        DefinitionStages.WithVolume,
        DefinitionStages.WithFirstContainerInstance,
        DefinitionStages.WithNextContainerInstance,
        DefinitionStages.WithCreate {
    }

    /**
     * Grouping of the container group definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of the container group definition.
         */
        interface Blank
            extends GroupableResource.DefinitionWithRegion<WithGroup> {
        }

        /**
         * The stage of the container group definition allowing to specify the resource group.
         */
        interface WithGroup
            extends GroupableResource.DefinitionStages.WithGroup<DefinitionStages.WithOsType> {
        }

        /**
         * The stage of the container group definition allowing to specify the OS type.
         */
        interface WithOsType {
            /**
             * Specifies this is a Linux container group.
             *
             * @return the next stage of the definition
             */
            WithPublicOrPrivateImageRegistry withLinux();

            /**
             * Specifies this is a Windows container group.
             *
             * @return the next stage of the definition
             */
            WithPublicOrPrivateImageRegistry withWindows();
        }

        /**
         * The stage of the container group definition allowing to specify a public only or private image registry.
         */
        interface WithPublicOrPrivateImageRegistry extends WithPublicImageRegistryOnly, WithPrivateImageRegistry {
        }

        /**
         * The stage of the container group definition allowing to skip the private image registry.
         */
        interface WithPublicImageRegistryOnly {
            /**
             * Only public container image repository will be used to create the container instances in the container group.
             *
             * @return the next stage of the definition
             */
            WithPrivateImageRegistryOrVolume withPublicImageRegistryOnly();
        }

        /**
         * The stage of the container group definition allowing to specify a private image registry.
         */
        interface WithPrivateImageRegistry {
            /**
             * Specifies the private container image registry server login for the container group.
             *
             * @param server Docker image registry server, without protocol such as "http" and "https"
             * @param username the username for the private registry
             * @param password the password for the private registry
             * @return the next stage of the definition
             */
            WithPrivateImageRegistryOrVolume withPrivateImageRegistry(String server, String username, String password);
        }

        /**
         * The stage of the container group definition allowing to specify a private image registry or a volume.
         */
        interface WithPrivateImageRegistryOrVolume extends WithPrivateImageRegistry {
            /**
             * Skips the definition of volumes to be shared by the container instances.
             *
             * <p>
             * An IllegalArgumentException will be thrown if a container instance attempts to define a volume mounting.
             * @return the next stage of the definition
             */
            WithFirstContainerInstance withoutVolume();

            /**
             * Specifies a new Azure file share name to be created.
             *
             * @param volumeName the name of the volume
             * @param shareName the Azure file share name to be created
             * @return the next stage of the definition
             */
            WithFirstContainerInstance withNewAzureFileShareVolume(String volumeName, String shareName);

            /**
             * Specifies an empty directory volume that can be shared by the container instances in the container group.
             *
             * @param name the name of the empty directory volume
             * @return the next stage of the definition
             */
            @Beta(Beta.SinceVersion.V1_7_0)
            WithFirstContainerInstance withEmptyDirectoryVolume(String name);

            /**
             * Begins the definition of a volume that can be shared by the container instances in the container group.
             *
             * <p>
             * The definition must be completed with a call to {@link VolumeDefinitionStages.WithVolumeAttach#attach()}
             * @param name the name of the volume
             * @return the next stage of the definition
             */
            VolumeDefinitionStages.VolumeDefinitionBlank<WithVolume> defineVolume(String name);
        }



        /**
         * The stage of the container group definition allowing to specify a volume that can be mounted by a container instance.
         */
        interface WithVolume extends WithFirstContainerInstance {
            /**
             * Begins the definition of a volume that can be shared by the container instances in the container group.
             *
             * <p>
             * The definition must be completed with a call to {@link VolumeDefinitionStages.WithVolumeAttach#attach()}
             * @param name the name of the volume
             * @return the next stage of the definition
             */
            VolumeDefinitionStages.VolumeDefinitionBlank<WithVolume> defineVolume(String name);
        }

        /**
         * Grouping of volume definition stages.
         */
        interface VolumeDefinitionStages {
            /**
             * The first stage of the volume definition.
             *
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface VolumeDefinitionBlank<ParentT> extends WithAzureFileShare<ParentT> {
            }

            /**
             * The stage of the volume definition allowing to specify a read only Azure File Share name.
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithAzureFileShare<ParentT> {
                /**
                 * Specifies an existing Azure file share name.
                 *
                 * @param shareName an existing Azure file share name
                 * @return the next stage of the definition
                 */
                WithStorageAccountName<ParentT> withExistingReadWriteAzureFileShare(String shareName);

                /**
                 * Specifies an existing Azure file share name.
                 *
                 * @param shareName an existing Azure file share name
                 * @return the next stage of the definition
                 */
                WithStorageAccountName<ParentT> withExistingReadOnlyAzureFileShare(String shareName);
            }

            /**
             * The stage of the volume definition allowing to specify the storage account name to access to the Azure file.
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithStorageAccountName<ParentT> {
                /**
                 * Specifies the storage account name to access to the Azure file.
                 *
                 * @param storageAccountName the storage account name
                 * @return the next stage of the definition
                 */
                WithStorageAccountKey<ParentT> withStorageAccountName(String storageAccountName);
            }

            /**
             * The stage of the volume definition allowing to specify the storage account key to access to the Azure file.
             *
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithStorageAccountKey<ParentT> {
                /**
                 * Specifies the storage account key to access to the Azure file.
                 *
                 * @param storageAccountKey the storage account key
                 * @return the next stage of the definition
                 */
                WithVolumeAttach<ParentT> withStorageAccountKey(String storageAccountKey);
            }

            /**
             * The stage of the volume definition allowing to specify the secrets map.
             *
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithSecretsMap<ParentT> {
                /**
                 * Specifies the secrets map.
                 * <p>
                 * The secret value must be specified in Base64 encoding
                 *
                 * @param secrets the new volume secrets map; value must be in Base64 encoding
                 * @return the next stage of the definition
                 */
                @Beta(Beta.SinceVersion.V1_7_0)
                WithVolumeAttach<ParentT> withSecrets(Map<String, String> secrets);
            }

            /**
             * The stage of the volume definition allowing to specify the Git URL mappings.
             *
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithGitUrl<ParentT> {
                /**
                 * Specifies the Git URL for the new volume.
                 *
                 * @param gitUrl the Git URL for the new volume
                 * @return the next stage of the definition
                 */
                @Beta(Beta.SinceVersion.V1_7_0)
                WithGitDirectoryName<ParentT> withGitUrl(String gitUrl);
            }

            /**
             * The stage of the volume definition allowing to specify the Git target directory name mappings.
             *
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithGitDirectoryName<ParentT> extends WithGitRevision<ParentT> {
                /**
                 * Specifies the Git target directory name for the new volume.
                 * <p>
                 * Must not contain or start with '..'.  If '.' is supplied, the volume directory will be the
                 * git repository.  Otherwise, if specified, the volume will contain the git repository in the
                 * subdirectory with the given name.
                 *
                 * @param gitDirectoryName the Git target directory name for the new volume
                 * @return the next stage of the definition
                 */
                @Beta(Beta.SinceVersion.V1_7_0)
                WithGitRevision<ParentT> withGitDirectoryName(String gitDirectoryName);
            }

            /**
             * The stage of the volume definition allowing to specify the Git revision.
             *
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithGitRevision<ParentT> extends WithVolumeAttach<ParentT> {
                /**
                 * Specifies the Git revision for the new volume.
                 *
                 * @param gitRevision the Git revision for the new volume
                 * @return the next stage of the definition
                 */
                @Beta(Beta.SinceVersion.V1_7_0)
                WithVolumeAttach<ParentT> withGitRevision(String gitRevision);
            }

            /** The final stage of the volume definition.
             * <p>
             * At this stage, any remaining optional settings can be specified, or the subnet definition
             * can be attached to the parent virtual network definition.
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithVolumeAttach<ParentT> extends
                Attachable.InDefinition<ParentT> {
            }

            /**
             * Grouping of the container group's volume definition stages.
             */
            interface VolumeDefinition<ParentT> extends
                    VolumeDefinitionBlank<ParentT>,
                    WithAzureFileShare<ParentT>,
                    WithStorageAccountName<ParentT>,
                    WithStorageAccountKey<ParentT>,
                    WithSecretsMap<ParentT>,
                    WithGitUrl<ParentT>,
                    WithGitDirectoryName<ParentT>,
                    WithGitRevision<ParentT>,
                    WithVolumeAttach<ParentT> {
            }
        }

        /**
         * The stage of the container group definition allowing to specify first required container instance.
         */
        interface WithFirstContainerInstance {
            /**
             * Begins the definition of a container instance.
             *
             * @param name the name of the container instance
             * @return the next stage of the definition
             */
            ContainerInstanceDefinitionStages.ContainerInstanceDefinitionBlank<WithNextContainerInstance> defineContainerInstance(String name);

            /**
             * Defines one container instance for the specified image with one CPU count and 1.5 GB memory, with TCP port 80 opened externally.
             *
             * @param imageName the name of the container image
             * @return the next stage of the definition
             */
            WithCreate withContainerInstance(String imageName);

            /**
             * Defines one container instance for the specified image with one CPU count and 1.5 GB memory, with a custom TCP port opened externally.
             *
             * @param imageName the name of the container image
             * @param port the external port to be opened
             * @return the next stage of the definition
             */
            WithCreate withContainerInstance(String imageName, int port);
        }

        /**
         * The stage of the container group definition allowing to specify a container instance.
         */
        interface WithNextContainerInstance extends WithCreate {
            /**
             * Begins the definition of a container instance.
             *
             * @param name the name of the volume
             * @return the next stage of the definition
             */
            ContainerInstanceDefinitionStages.ContainerInstanceDefinitionBlank<WithNextContainerInstance> defineContainerInstance(String name);
        }

        /**
         * Grouping of volume definition stages.
         */
        interface ContainerInstanceDefinitionStages {
            /**
             * The first stage of the container instance definition.
             *
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface ContainerInstanceDefinitionBlank<ParentT> extends WithImage<ParentT> {
            }

            /**
             * The stage of the container instance definition allowing to specify the container image.
             *
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithImage<ParentT> {
                /**
                 * Specifies the container image to be used.
                 *
                 * @param imageName the container image
                 * @return the next stage of the definition
                 */
                WithOrWithoutPorts<ParentT> withImage(String imageName);
            }

            /**
             * The stage of the container instance definition allowing to specify (or not) the container ports.
             *
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithOrWithoutPorts<ParentT> extends
                WithPorts<ParentT>,
                WithoutPorts<ParentT> {
            }

            /**
             * The stage of the container instance definition allowing not to specify any container ports internal or external.
             *
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithoutPorts<ParentT> {
                /**
                 * Specifies that not ports will be opened internally or externally for this container instance.
                 *
                 * @return the next stage of the definition
                 */
                WithContainerInstanceAttach<ParentT> withoutPorts();
            }

            /**
             * The stage of the container instance definition allowing to specify one or more container ports.
             *
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithPortsOrContainerInstanceAttach<ParentT> extends
                WithPorts<ParentT>,
                    WithContainerInstanceAttach<ParentT> {
            }

            /**
             * The stage of the container instance definition allowing to specify the container ports.
             *
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            @Beta(Beta.SinceVersion.V1_8_0)
            interface WithPorts<ParentT> {
                /**
                 * Specifies the container's TCP ports available to external clients.
                 * <p>
                 * A public IP address will be create to allow external clients to reach the containers within the group.
                 * To enable external clients to reach a container within the group, you must expose the port on the
                 *   IP address and from the container. Because containers within the group share a port namespace, port
                 *   mapping is not supported.
                 *
                 * @param ports array of TCP ports to be exposed externally
                 * @return the next stage of the definition
                 */
                WithPortsOrContainerInstanceAttach<ParentT> withExternalTcpPorts(int... ports);

                /**
                 * Specifies the container's TCP port available to external clients.
                 * <p>
                 * A public IP address will be create to allow external clients to reach the containers within the group.
                 * To enable external clients to reach a container within the group, you must expose the port on the
                 *   IP address and from the container. Because containers within the group share a port namespace, port
                 *   mapping is not supported.
                 *
                 * @param port TCP port to be exposed externally
                 * @return the next stage of the definition
                 */
                WithPortsOrContainerInstanceAttach<ParentT> withExternalTcpPort(int port);

                /**
                 * Specifies the container's UDP ports available to external clients.
                 * <p>
                 * A public IP address will be create to allow external clients to reach the containers within the group.
                 * To enable external clients to reach a container within the group, you must expose the port on the
                 *   IP address and from the container. Because containers within the group share a port namespace, port
                 *   mapping is not supported.
                 *
                 * @param ports array of UDP ports to be exposed externally
                 * @return the next stage of the definition
                 */
                WithPortsOrContainerInstanceAttach<ParentT> withExternalUdpPorts(int... ports);

                /**
                 * Specifies the container's UDP port available to external clients.
                 * <p>
                 * A public IP address will be create to allow external clients to reach the containers within the group.
                 * To enable external clients to reach a container within the group, you must expose the port on the
                 *   IP address and from the container. Because containers within the group share a port namespace, port
                 *   mapping is not supported.
                 *
                 * @param port UDP port to be exposed externally
                 * @return the next stage of the definition
                 */
                WithPortsOrContainerInstanceAttach<ParentT> withExternalUdpPort(int port);

                /**
                 * Specifies the container's TCP ports are available to internal clients only (other container instances within the container group).
                 * <p>
                 * Containers within a group can reach each other via localhost on the ports that they have exposed,
                 *   even if those ports are not exposed externally on the group's IP address.
                 *
                 * @param ports array of TCP ports to be exposed internally
                 * @return the next stage of the definition
                 */
                @Beta(Beta.SinceVersion.V1_8_0)
                WithPortsOrContainerInstanceAttach<ParentT> withInternalTcpPorts(int... ports);

                /**
                 * Specifies the container's Udp ports are available to internal clients only (other container instances within the container group).
                 * <p>
                 * Containers within a group can reach each other via localhost on the ports that they have exposed,
                 *   even if those ports are not exposed externally on the group's IP address.
                 *
                 * @param ports array of UDP ports to be exposed internally
                 * @return the next stage of the definition
                 */
                @Beta(Beta.SinceVersion.V1_8_0)
                WithPortsOrContainerInstanceAttach<ParentT> withInternalUdpPorts(int... ports);

                /**
                 * Specifies the container's TCP port is available to internal clients only (other container instances within the container group).
                 * <p>
                 * Containers within a group can reach each other via localhost on the ports that they have exposed,
                 *   even if those ports are not exposed externally on the group's IP address.
                 *
                 * @param port TCP port to be exposed internally
                 * @return the next stage of the definition
                 */
                @Beta(Beta.SinceVersion.V1_8_0)
                WithPortsOrContainerInstanceAttach<ParentT> withInternalTcpPort(int port);

                /**
                 * Specifies the container's UDP port is available to internal clients only (other container instances within the container group).
                 * <p>
                 * Containers within a group can reach each other via localhost on the ports that they have exposed,
                 *   even if those ports are not exposed externally on the group's IP address.
                 *
                 * @param port UDP port to be exposed internally
                 * @return the next stage of the definition
                 */
                @Beta(Beta.SinceVersion.V1_8_0)
                WithPortsOrContainerInstanceAttach<ParentT> withInternalUdpPort(int port);
            }

            /**
             * The stage of the container instance definition allowing to specify the number of CPU cores.
             * <p>
             * The CPU cores can be specified as a fraction, i.e. 1.5 represents one and a half atomic CPU cores
             *   will be assigned to this container instance.
             *
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithCpuCoreCount<ParentT> {
                /**
                 * Specifies the number of CPU cores assigned to this container instance.
                 *
                 * @param cpuCoreCount the number of CPU cores
                 * @return the next stage of the definition
                 */
                WithContainerInstanceAttach<ParentT> withCpuCoreCount(double cpuCoreCount);
            }

            /**
             * The stage of the container instance definition allowing to specify the memory size in GB.
             * <p>
             * The memory size can be specified as a fraction, i.e. 1.5 represents one and a half GB of memory.
             *
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithMemorySize<ParentT> {
                /**
                 * Specifies the memory size in GB assigned to this container instance.
                 *
                 * @param memorySize the memory size in GB
                 * @return the next stage of the definition
                 */
                WithContainerInstanceAttach<ParentT> withMemorySizeInGB(double memorySize);
            }

            /**
             * The stage of the container instance definition allowing to specify the starting command line.
             *
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithStartingCommandLine<ParentT> {
                /**
                 * Specifies the starting command lines.
                 *
                 * @param executable the executable which it will call after initializing the container
                 * @param parameters the parameter list for the executable to be called
                 * @return the next stage of the definition
                 */
                @Beta(Beta.SinceVersion.V1_11_0)
                WithContainerInstanceAttach<ParentT> withStartingCommandLine(String executable, String... parameters);

                /**
                 * Specifies the starting command line.
                 *
                 * @param executable the executable or path to the executable that will be called after initializing the container
                 * @return the next stage of the definition
                 */
                WithContainerInstanceAttach<ParentT> withStartingCommandLine(String executable);
            }

            /**
             * The stage of the container instance definition allowing to specify the environment variables.
             *
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithEnvironmentVariables<ParentT> {
                /**
                 * Specifies the environment variables.
                 *
                 * @param environmentVariables the environment variables in a name and value pair to be set after the container gets initialized
                 * @return the next stage of the definition
                 */
                WithContainerInstanceAttach<ParentT> withEnvironmentVariables(Map<String, String> environmentVariables);

                /**
                 * Specifies the environment variable.
                 *
                 * @param envName the environment variable name
                 * @param envValue the environment variable value
                 * @return the next stage of the definition
                 */
                WithContainerInstanceAttach<ParentT> withEnvironmentVariable(String envName, String envValue);
            }

            /**
             * The stage of the container instance definition allowing to specify volume mount setting.
             *
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithVolumeMountSetting<ParentT> {
                /**
                 * Specifies the container group's volume to be mounted by the container instance at a specified mount path.
                 * <p>
                 * Mounting an Azure file share as a volume in a container is a two-step process. First, you provide
                 *   the details of the share as part of defining the container group, then you specify how you wan
                 *   the volume mounted within one or more of the containers in the group.
                 *
                 * @param volumeName the volume name as defined in the volumes of the container group
                 * @param mountPath the local path the volume will be mounted at
                 * @return the next stage of the definition
                 * @throws IllegalArgumentException thrown if volumeName was not defined in the respective container group definition stage.
                 */
                WithContainerInstanceAttach<ParentT> withVolumeMountSetting(String volumeName, String mountPath);

                /**
                 * Specifies the container group's volume to be mounted by the container instance at a specified mount path.
                 * <p>
                 * Mounting an Azure file share as a volume in a container is a two-step process. First, you provide
                 *   the details of the share as part of defining the container group, then you specify how you wan
                 *   the volume mounted within one or more of the containers in the group.
                 *
                 * @param volumeMountSetting the name and value pair representing volume names as defined in the volumes of the container group and the local paths the volume will be mounted at
                 * @return the next stage of the definition
                 * @throws IllegalArgumentException thrown if volumeName was not defined in the respective container group definition stage.
                 */
                WithContainerInstanceAttach<ParentT> withVolumeMountSetting(Map<String, String> volumeMountSetting);

                /**
                 * Specifies the container group's volume to be mounted by the container instance at a specified mount path.
                 * <p>
                 * Mounting an Azure file share as a volume in a container is a two-step process. First, you provide
                 *   the details of the share as part of defining the container group, then you specify how you wan
                 *   the volume mounted within one or more of the containers in the group.
                 *
                 * @param volumeName the volume name as defined in the volumes of the container group
                 * @param mountPath the local path the volume will be mounted at
                 * @return the next stage of the definition
                 * @throws IllegalArgumentException thrown if volumeName was not defined in the respective container group definition stage.
                 */
                WithContainerInstanceAttach<ParentT> withReadOnlyVolumeMountSetting(String volumeName, String mountPath);

                /**
                 * Specifies the container group's volume to be mounted by the container instance at a specified mount path.
                 * <p>
                 * Mounting an Azure file share as a volume in a container is a two-step process. First, you provide
                 *   the details of the share as part of defining the container group, then you specify how you wan
                 *   the volume mounted within one or more of the containers in the group.
                 *
                 * @param volumeMountSetting the name and value pair representing volume names as defined in the volumes of the container group and the local paths the volume will be mounted at
                 * @return the next stage of the definition
                 * @throws IllegalArgumentException thrown if volumeName was not defined in the respective container group definition stage.
                 */
                WithContainerInstanceAttach<ParentT> withReadOnlyVolumeMountSetting(Map<String, String> volumeMountSetting);
            }

            /** The final stage of the container instance definition.
             * <p>
             * At this stage, any remaining optional settings can be specified, or the subnet definition
             * can be attached to the parent virtual network definition.
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithContainerInstanceAttach<ParentT> extends
                WithCpuCoreCount<ParentT>,
                WithMemorySize<ParentT>,
                WithStartingCommandLine<ParentT>,
                WithEnvironmentVariables<ParentT>,
                WithVolumeMountSetting<ParentT>,
                Attachable.InDefinition<ParentT> {
            }

            /**
             * Grouping of the container group's volume definition stages.
             */
            interface ContainerInstanceDefinition<ParentT> extends
                    ContainerInstanceDefinitionBlank<ParentT>,
                    WithImage<ParentT>,
                    WithOrWithoutPorts<ParentT>,
                    WithPortsOrContainerInstanceAttach<ParentT>,
                    WithContainerInstanceAttach<ParentT> {
            }
        }

        /**
         * The stage of the container group definition allowing to specify the container group restart policy.
         */
        interface WithRestartPolicy {
            /**
             * Specifies the restart policy for all the container instances within the container group.
             *
             * @param restartPolicy the restart policy for all the container instances within the container group
             * @return the next stage of the definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            WithCreate withRestartPolicy(ContainerGroupRestartPolicy restartPolicy);
        }

        /**
         * The stage of the container group definition allowing to specify the DNS prefix label.
         */
        interface WithDnsPrefix {
            /**
             * Specifies the DNS prefix to be used to create the FQDN for the container group.
             *
             * @param dnsPrefix the DNS prefix to be used to create the FQDN for the container group
             * @return the next stage of the definition
             */
            WithCreate withDnsPrefix(String dnsPrefix);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be created
         *   (via {@link WithCreate#create()}), but also allows for any other optional settings to be specified.
         */
        interface WithCreate extends
            WithRestartPolicy,
            WithDnsPrefix,
            Creatable<ContainerGroup>,
            Resource.DefinitionWithTags<WithCreate> {
        }
    }

    /**
     * The template for an update operation, containing all the settings that can be modified.
     */
    interface Update extends
        Resource.UpdateWithTags<Update>,
        Appliable<ContainerGroup> {
    }

}
