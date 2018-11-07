/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerregistry;


import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.containerregistry.implementation.TaskInner;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * An immutable client-side representation of an Azure registry task.
 */
@Fluent()
@Beta
public interface RegistryTask extends
        Resource,
        HasInner<TaskInner>,
        Refreshable<RegistryTask>,
        Updatable<RegistryTask.Update> {

    /**
     * @return the name of the resource's resource group
     */
    String resourceGroupName();


    /**
     * @return the parent ID of this resource
     */
    String parentRegistryId();

    /**
     * @return the provisioning state of the build task
     */
    ProvisioningState provisioningState();

    /**
     * @return the creation date of build task
     */
    DateTime creationDate();

    /**
     * @return the current status of build task
     */
    TaskStatus status();

    /**
     * @return the RegistryTaskStep of the current task
     */
    RegistryTaskStep registryTaskStep();

    /**
     * @return the build timeout settings in seconds
     */
    int timeout();

    /**
     * @return the build timeout settings in seconds
     */
    PlatformProperties platform();

    /**
     * @return the CPU count
     */
    int cpuCount();

    /**
     * @return the trigger of the task
     */
    TriggerProperties trigger();

    /**
     * Container interface for all the definitions related to a registry task.
     */
    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.Location,
            DefinitionStages.Platform,
            DefinitionStages.TaskStepType,
            DefinitionStages.TriggerTypes,
            DefinitionStages.TaskCreatable {
    }

    /**
     * Container interface for all the updates related to a registry task.
     */
    interface Update extends
            UpdateStages.Platform,
            UpdateStages.Trigger,
            UpdateStages.AgentConfiguration,
            UpdateStages.Timeout,
            UpdateStages.TaskStepType,
            Appliable<RegistryTask> {
    }

    /**
     * Grouping of registry task definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of a container registry task definition.
         */
        interface Blank {
            /**
             * The parameters referencing an existing container registry under which this task resides.
             *
             * @param resourceGroupName the name of the parent container registry resource group.
             * @param registryName the name of the existing container registry.
             * @return the next stage of the container registry task definition.
             */
            Location withExistingRegistry(String resourceGroupName, String registryName);
        }

        /**
         * The stage of the container registry task definition allowing to specify location.
         */
        interface Location {
            /**
             * The parameters specifying location of the container registry task.
             *
             * @param location the location of the container registry task.
             * @return the next stage of the container registry task definition.
             */
            Platform withLocation(String location);

            /**
             * The parameters specifying location of the container registry task.
             *
             * @param location the location of the container registry task.
             * @return the next stage of the container registry task definition.
             */
            Platform withLocation(Region location);
        }

        /**
         * The stage of the container registry task definition allowing to specify the platform.
         */
        interface Platform {
            /**
             * The function that specifies a Linux OS system for the platform.
             *
             * @return the next stage of the container registry task definition.
             */
            TaskStepType withLinux();

            /**
             * The function that specifies a Windows OS system for the platform.
             *
             * @return the next stage of the container registry task definition.
             */
            TaskStepType withWindows();

            /**
             * The function that specifies a Linux OS system and architecture for the platform.
             *
             * @param architecture the CPU architecture.
             * @return the next stage of the container registry task definition.
             */
            TaskStepType withLinux(Architecture architecture);

            /**
             * The function that specifies a Windows OS system and architecture for the platform.
             *
             * @param architecture the CPU architecture
             * @return the next stage of the container registry task definition.
             */
            TaskStepType withWindows(Architecture architecture);

            /**
             * The function that specifies a Linux OS system, architecture, and CPU variant.
             *
             * @param architecture the CPU architecture.
             * @param variant the CPU variant.
             * @return the next stage of the container registry task definition.
             */
            TaskStepType withLinux(Architecture architecture, Variant variant);

            /**
             * The function that specifies a Windows OS system, architecture, and CPU variant.
             *
             * @param architecture the CPU architecture.
             * @param variant the CPU variant.
             * @return the next stage of the container registry task definition.
             */
            TaskStepType withWindows(Architecture architecture, Variant variant);

            /**
             * The function that specifies a platform.
             *
             * @param platformProperties the properties of the platform.
             * @return the next stage of the container registry task definition.
             */
            TaskStepType withPlatform(PlatformProperties platformProperties);
        }

        /**
         * The stage of the container registry task definition that specifies the type of task step.
         */
        interface TaskStepType {
            /**
             * The function that specifies a task step of type FileTaskStep.
             *
             * @return the first stage of the FileTaskStep definition.
             */
            RegistryFileTaskStep.DefinitionStages.Blank defineFileTaskStep();

            /**
             * The function that specifies a task step of type EncodedTaskStep.
             *
             * @return the first stage of the EncodedTaskStep definition.
             */
            RegistryEncodedTaskStep.DefinitionStages.Blank defineEncodedTaskStep();

            /**
             * The function that specifies a task step of type DockerTaskStep.
             *
             * @return the first stage of the DockerTaskStep definition.
             */
            RegistryDockerTaskStep.DefinitionStages.Blank defineDockerTaskStep();
        }

        /**
         * The stage of the container registry task definition that allows users to define either a source trigger and/or a base image trigger.
         */
        interface TriggerTypes {
            /**
             * The function that begins the definition of a source trigger.
             *
             * @return the first stage of the RegistrySourceTrigger definition.
             */
            RegistrySourceTrigger.DefinitionStages.Blank defineSourceTrigger();

            /**
             * The function that defines a base image trigger with the two parameters required for base image trigger creation.
             *
             * @param baseImageTriggerType the trigger type for the base image. Can be "All", "Runtime", or something else that the user inputs.
             * @param baseImageTriggerName the name of the base image trigger.
             * @return the next stage of the container registry task definition.
             */
            TaskCreatable withBaseImageTrigger(BaseImageTriggerType baseImageTriggerType, String baseImageTriggerName);

            /**
             * The function that defines a base image trigger with all possible parameters for base image trigger creation.
             *
             * @param baseImageTriggerType the trigger type for the base image. Can be "All", "Runtime", or something else that the user inputs.
             * @param baseImageTriggerName the name of the base image trigger.
             * @param triggerStatus the status for the trigger. Can be enabled, disabled, or something else that the user inputs.
             * @return the next stage of the container registry task definition.
             */
            TaskCreatable withBaseImageTrigger(BaseImageTriggerType baseImageTriggerType, String baseImageTriggerName, TriggerStatus triggerStatus);
        }

        /**
         * The stage of the container registry task definition that specifies the AgentConfiguration for the container registry task.
         */
        interface AgentConfiguration {
            /**
             * The function that specifies the count of the CPU.
             *
             * @param count the CPU count.
             * @return the next stage of the container registry task definition.
             */
            TaskCreatable withCpuCount(int count);
        }

        /**
         * The stage of the container registry task definition that specifies the timeout for the container registry task.
         */
        interface Timeout {
            /**
             * The function that sets the timeout time.
             *
             * @param timeout the time for timeout.
             * @return the next stage of the container registry task definition.
             */
            TaskCreatable withTimeout(int timeout);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be created,
         *  but also allows for any other optional settings to be specified.
         */
        interface TaskCreatable extends
                AgentConfiguration,
                Timeout,
                TriggerTypes,
                Creatable<RegistryTask> {
        }
    }

    /**
     * Grouping of registry task update stages.
     */
    interface UpdateStages {

        /**
         * The stage of the container registry task definition that specifies the type of task step.
         */
        interface TaskStepType {
            /**
             * The function that specifies a task step of type FileTaskStep.
             *
             * @return the first stage of the FileTaskStep update.
             */
            RegistryFileTaskStep.Update updateFileTaskStep();

            /**
             * The function that specifies a task step of type EncodedTaskStep.
             *
             * @return the first stage of the EncodedTaskStep update.
             */
            RegistryEncodedTaskStep.Update updateEncodedTaskStep();

            /**
             * The function that specifies a task step of type DockerTaskStep.
             *
             * @return the first stage of the DockerTaskStep update.
             */
            RegistryDockerTaskStep.Update updateDockerTaskStep();
        }

        /**
         * The stage of the container registry task update allowing to update the platform.
         */
        interface Platform {
            /**
             * The function that specifies a Linux OS system for the platform.
             *
             * @return the next stage of the container registry task update.
             */
            Update withLinux();

            /**
             * The function that specifies a Windows OS system for the platform.
             *
             * @return the next stage of the container registry task update.
             */
            Update withWindows();

            /**
             * The function that specifies a Linux OS system and architecture for the platform.
             *
             * @param architecture the CPU architecture.
             * @return the next stage of the container registry task update.
             */
            Update withLinux(Architecture architecture);

            /**
             * The function that specifies a Windows OS system and architecture for the platform.
             *
             * @param architecture the CPU architecture
             * @return the next stage of the container registry task update.
             */
            Update withWindows(Architecture architecture);

            /**
             * The function that specifies a Linux OS system, architecture, and CPU variant.
             *
             * @param architecture the CPU architecture.
             * @param variant the CPU variant.
             * @return the next stage of the container registry task update.
             */
            Update withLinux(Architecture architecture, Variant variant);

            /**
             * The function that specifies a Windows OS system, architecture, and CPU variant.
             *
             * @param architecture the CPU architecture.
             * @param variant the CPU variant.
             * @return the next stage of the container registry task update.
             */
            Update withWindows(Architecture architecture, Variant variant);

            /**
             * The function that specifies a platform.
             *
             * @param platformProperties the properties of the platform.
             * @return the next stage of the container registry task update.
             */
            Update withPlatform(PlatformUpdateParameters platformProperties);
        }

        /**
         * The stage of the container registry task update that updates the trigger for the container registry task.
         */
        interface Trigger {
            /**
             * The function that updates a list of source triggers.
             *
             * @param sourceTriggers an ArrayList of SourceTriggers.
             * @return the next stage of the container registry task update.
             */
            Update withTrigger(ArrayList<SourceTriggerUpdateParameters> sourceTriggers);

            /**
             * The function that updates a BaseImageTrigger.
             *
             * @param baseImageTrigger a BaseImageTrigger.
             * @return the next stage of the container registry task update.
             */
            Update withTrigger(BaseImageTriggerUpdateParameters baseImageTrigger);

            /**
             * The function that updates both a list of SourceTriggers and a BaseImageTrigger.
             *
             * @param sourceTriggers an ArrayList that contains SourceTriggers.
             * @param baseImageTrigger a BaseImageTrigger.
             * @return the next stage of the container registry task update.
             */
            Update withTrigger(List<SourceTriggerUpdateParameters> sourceTriggers, BaseImageTriggerUpdateParameters baseImageTrigger);
        }

        /**
         * The stage of the container registry task update that updates the AgentConfiguration for the container registry task.
         */
        interface AgentConfiguration {
            /**
             * The function that updates the count of the CPU.
             *
             * @param count the CPU count.
             * @return the next stage of the container registry task update.
             */
            Update withCpuCount(int count);
        }

        /**
         * The stage of the container registry task update that updates the timeout for the container registry task.
         */
        interface Timeout {
            /**
             * The function that updates the timeout time.
             *
             * @param timeout the time for timeout.
             * @return the next stage of the container registry task update.
             */
            Update withTimeout(int timeout);
        }
    }
}
