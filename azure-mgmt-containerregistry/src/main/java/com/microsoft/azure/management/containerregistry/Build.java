/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.containerregistry.implementation.BuildInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.ExternalChildResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import org.joda.time.DateTime;
import rx.Observable;

import java.util.List;

/**
 * An object that represents a queued build for a container registry.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_14_0)
public interface Build  extends
    ExternalChildResource<Build, Registry>,
    HasName,
    HasId,
    HasInner<BuildInner>,
    Refreshable<Build>,
    Updatable<Build.Update> {

    /**
     *@return the unique identifier for the queued build
     */
    String buildId();

    /**
     *@return the current status of the queued build
     */
    BuildStatus status();

    /**
     *@return the last updated time for the queued build
     */
    DateTime lastUpdatedTime();

    /**
     *@return the type of the queued build
     */
    BuildType buildType();

    /**
     *@return the time the build was created
     */
    DateTime createTime();

    /**
     *@return the time the build started
     */
    DateTime startTime();

    /**
     *@return the time the build finished
     */
    DateTime finishTime();

    /**
     *@return the list of all images that were generated from the build
     */
    List<ImageDescriptor> outputImages();

    /**
     *@return the build task with which the build was started
     */
    String buildTask();

    /**
     *@return the image update trigger that caused the build
     */
    ImageUpdateTrigger imageUpdateTrigger();

    /**
     *@return the git commit trigger that caused the build
     */
    GitCommitTrigger gitCommitTrigger();

    /**
     *@return the value that indicates whether archiving is enabled or not
     */
    boolean isArchiveEnabled();

    /**
     * @return the OS type required for the build
     */
    OsType osType();

    /**
     * @return the CPU configuration in terms of number of cores required for the build
     */
    int cpuCount();

    /**
     *@return the provisioning state of a build
     */
    ProvisioningState provisioningState();

    /**
     * @return link to logs for a azure container registry build.
     */
    String getLogLink();

    /**
     *@return a representation of the future computation of this call, returning a link to logs for a azure container
     *   registry build.
     */
    Observable<String> getLogLinkAsync();


    /**
     * Grouping of queued build task definition stages.
     */
    interface QueuedQuickBuildDefinitionStages {
        /**
         * The first stage of the queued quick build definition.
         */
        interface Blank extends WithOSType {
        }

        /**
         * The stage of the queue build definition allowing to specify the OS type required for the build.
         */
        interface WithOSType {
            /**
             * Specifies the OS type required for the build.
             *
             * @param osType the operating system type required for the build
             * @return the next stage of the definition
             */
            QueuedQuickBuildDefinitionStages.WithSourceLocation withOSType(OsType osType);
        }

        /**
         * The stage of the queue build definition allowing to specify the source location.
         */
        interface WithSourceLocation {
            /**
             * Specifies the source location required for the build.
             *
             * @param sourceLocation the source location required for the build
             * @return the next stage of the definition
             */
            QueuedQuickBuildDefinitionStages.WithDockerFilePath withSourceLocation(String sourceLocation);
        }

        /**
         * The stage of the queue build definition allowing to specify the Docker file path.
         */
        interface WithDockerFilePath {
            /**
             * Specifies the Docker file path required for the build.
             *
             * @param dockerFilePath the Docker file path required for the build
             * @return the next stage of the definition
             */
            QueuedQuickBuildDefinitionStages.WithCreate withDockerFilePath(String dockerFilePath);
        }

        /**
         * The stage of the queued build definition allowing to specify the build arguments.
         */
        interface WithBuildArgument {
            /**
             * Begins the definition of the build argument settings.
             *
             * <p>
             * @param name the name of the build argument
             * @return the next stage of the definition
             */
            QueuedQuickBuildArgumentDefinitionStages.BuildArgumentDefinitionBlank<QueuedQuickBuildDefinitionStages.WithCreate> defineBuildArgument(String name);
        }

        /**
         * Grouping of build argument definition stages for a queued build.
         */
        interface QueuedQuickBuildArgumentDefinitionStages {
            /**
             * The first stage of the build argument definition.
             *
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface BuildArgumentDefinitionBlank<ParentT> extends WithValue<ParentT> {
            }

            /**
             * The stage of the build argument definition allowing to specify the value of the build argument.
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithValue<ParentT> {
                /**
                 * Specifies the value of the build argument.
                 *
                 * @param value the value of the build argument
                 * @return the next stage of the definition
                 */
                QueuedQuickBuildArgumentDefinitionStages.WithBuildArgumentAttach<ParentT> withValue(String value);
            }

            /**
             * The stage of the build argument definition allowing to specify the type of the build argument.
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithType<ParentT> {
                /**
                 * Specifies the type of the build argument.
                 *
                 * @param type the type of the build argument; for instance "DockerBuildArgument"
                 * @return the next stage of the definition
                 */
                QueuedQuickBuildArgumentDefinitionStages.WithBuildArgumentAttach<ParentT> withType(String type);
            }

            /**
             * The stage of the build argument definition allowing to specify the secrecy of the build argument.
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithSecrecy<ParentT> {
                /**
                 * Specifies the build argument is secret and hides it from the logs.
                 *
                 * @return the next stage of the definition
                 */
                QueuedQuickBuildArgumentDefinitionStages.WithBuildArgumentAttach<ParentT> withSecrecyEnabled();

                /**
                 * Specifies the build argument is not a secret.
                 *
                 * @return the next stage of the definition
                 */
                QueuedQuickBuildArgumentDefinitionStages.WithBuildArgumentAttach<ParentT> withSecrecyDisabled();
            }

            /** The final stage of the build argument definition.
             * <p>
             * At this stage, any remaining optional settings can be specified, or the subnet definition
             * can be attached to the parent virtual network definition.
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithBuildArgumentAttach<ParentT> extends
                QueuedQuickBuildArgumentDefinitionStages.WithType<ParentT>,
                QueuedQuickBuildArgumentDefinitionStages.WithSecrecy<ParentT>,
                Attachable.InDefinition<ParentT> {
            }

            /**
             * Grouping of the build argument definition stages.
             */
            interface BuildArgumentDefinition<ParentT> extends
                QueuedQuickBuildArgumentDefinitionStages.BuildArgumentDefinitionBlank<ParentT>,
                QueuedQuickBuildArgumentDefinitionStages.WithValue<ParentT>,
                QueuedQuickBuildArgumentDefinitionStages.WithType<ParentT>,
                QueuedQuickBuildArgumentDefinitionStages.WithSecrecy<ParentT>,
                QueuedQuickBuildArgumentDefinitionStages.WithBuildArgumentAttach<ParentT> {
            }
        }

        /**
         * The stage of the queue build definition allowing to specify the CPU configuration required for the build.
         */
        interface WithCpuCoresCount {
            /**
             * Specifies the CPU configuration in terms of number of cores required for the build.
             *
             * @param count the CPU configuration in terms of number of cores required for the build
             * @return the next stage of the definition
             */
            QueuedQuickBuildDefinitionStages.WithCreate withCpuCoresCount(int count);
        }

        /**
         * The stage of the queue build update definition allowing to specify the build timeout.
         */
        interface WithBuildTimeoutInSeconds {
            /**
             * Specifies the build timeout.
             *
             * @param buildTimeoutInSeconds the build timeout in seconds
             * @return the next stage of the definition
             */
            QueuedQuickBuildDefinitionStages.WithCreate withBuildTimeoutInSeconds(int buildTimeoutInSeconds);
        }

        /**
         * The stage of the queue build definition allowing to specify the image names for the build.
         */
        interface WithImageNames {
            /**
             * Specifies the image names for the build.
             *
             * @param imageNames the image names to be used by the build
             * @return the next stage of the definition
             */
            QueuedQuickBuildDefinitionStages.WithCreate withImageNames(String... imageNames);
        }

        /**
         * The stage of the queue build definition allowing to specify if the image cache is enabled or not.
         */
        interface WithImageCache {
            /**
             * Specifies that the image cache is enabled.
             *
             * @return the next stage of the definition
             */
            QueuedQuickBuildDefinitionStages.WithCreate withImageCacheEnabled();

            /**
             * Specifies that the image cache is disabled.
             *
             * @return the next stage of the definition
             */
            QueuedQuickBuildDefinitionStages.WithCreate withImageCacheDisabled();
        }

        /**
         * The stage of the queue build definition allowing to specify if the image built should be pushed to the registry or not.
         */
        interface WithImagePush {
            /**
             * Specifies that the image built should be pushed to the registry.
             *
             * @return the next stage of the definition
             */
            QueuedQuickBuildDefinitionStages.WithCreate withImagePushEnabled();

            /**
             * Specifies that the image built should not be pushed to the registry.
             *
             * @return the next stage of the definition
             */
            QueuedQuickBuildDefinitionStages.WithCreate withImagePushDisabled();
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be created,
         *  but also allows for any other optional settings to be specified.
         */
        interface WithCreate extends
            QueuedQuickBuildDefinitionStages.WithBuildArgument,
            QueuedQuickBuildDefinitionStages.WithCpuCoresCount,
            QueuedQuickBuildDefinitionStages.WithBuildTimeoutInSeconds,
            QueuedQuickBuildDefinitionStages.WithImageNames,
            QueuedQuickBuildDefinitionStages.WithImageCache,
            QueuedQuickBuildDefinitionStages.WithImagePush,
            Creatable<Build> {
        }
    }

    /**
     * Grouping of the container register queued quick build definitions.
     */
    interface QueuedQuickBuildDefinition extends
        Build.QueuedQuickBuildDefinitionStages.Blank,
        Build.QueuedQuickBuildDefinitionStages.WithOSType,
        Build.QueuedQuickBuildDefinitionStages.WithSourceLocation,
        Build.QueuedQuickBuildDefinitionStages.WithDockerFilePath,
        Build.QueuedQuickBuildDefinitionStages.WithBuildArgument,
        Build.QueuedQuickBuildDefinitionStages.WithCpuCoresCount,
        Build.QueuedQuickBuildDefinitionStages.WithBuildTimeoutInSeconds,
        QueuedQuickBuildDefinitionStages.WithImageNames,
        QueuedQuickBuildDefinitionStages.WithImageCache,
        QueuedQuickBuildDefinitionStages.WithImagePush,
        Build.QueuedQuickBuildDefinitionStages.WithCreate {
    }

    /**
     * The entirety of a build task update.
     */
    interface Update extends
        Build.UpdateStages.WithArchiving,
        Appliable<Build> {
    }

    /**
     * Grouping of queued build update stages.
     */
    interface UpdateStages {
        /**
         * The stage of the queue build update definition allowing to specify whether archiving is enabled or not.
         */
        interface WithArchiving {
            /**
             * Specifies the archiving is enabled.
             *
             * @return the next stage of the definition
             */
            Build.Update withArchivingEnabled();

            /**
             * Specifies the archiving is disabled.
             *
             * @return the next stage of the definition
             */
            Build.Update withArchivingDisabled();
        }
    }
}
