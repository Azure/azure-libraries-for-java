/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry;


import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.containerregistry.implementation.TaskInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import org.joda.time.DateTime;

import java.util.List;

/**
 * An immutable client-side representation of an Azure registry task.
 */
@Fluent()
@Beta(Beta.SinceVersion.V1_1_0)
public interface Task extends
        Resource,
        HasInner<TaskInner>,
        Refreshable<Task>
        /*Updatable<Task.Update>*/ {

    /**
     * @return the parent ID of this resource
     */
    String parentId();

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
     * @return the build timeout settings in seconds
     */
    int timeout();

    /**
     * Container interface for all the definitions related to a registry task.
     */
    interface Definition extends
    DefinitionStages.Blank,
    DefinitionStages.Location,
    DefinitionStages.Platform,
    DefinitionStages.TaskType,
    DefinitionStages.TaskCreatable {

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
             * @param resourceGroupName the name of the resource group used to create the existing container registry.
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
             * @param location the location.
             * @return the next stage of the container registry task definition.
             */
            Platform withLocation(String location);
        }

        /**
         * The stage of the container registry task definition allowing to specify the platform.
         */
        interface Platform {

            /**
             * The function that allows us to specify a Linux OS system for the platform.
             *
             * @return the next stage of the container registry task definition.
             */
            TaskType withLinux();

            /**
             * The function that allows us to specify a Windows OS system for the platform.
             *
             * @return the next stage of the container registry task definition.
             */
            TaskType withWindows();

            /**
             * The function that allows us to specify a Linux OS system and architecture for the platform.
             *
             * @param architecture the CPU architecture.
             * @return the next stage of the container registry task definition.
             */
            TaskType withLinux(Architecture architecture);

            /**
             * The function that allows us to specify a Windows OS system and architecture for the platform.
             *
             * @param architecture the CPU architecture
             * @return the next stage of the container registry task definition.
             */
            TaskType withWindows(Architecture architecture);

            /**
             * The function that allows us to specify a Linux OS system, architecture, and CPU variant.
             *
             * @param architecture the CPU architecture.
             * @param variant the CPU variant.
             * @return the next stage of the container registry task definition.
             */
            TaskType withLinux(Architecture architecture, Variant variant);

            /**
             * The function that allows us to specify a Windows OS system, architecture, and CPU variant.
             *
             * @param architecture the CPU architecture.
             * @param variant the CPU variant.
             * @return the next stage of the container registry task definition.
             */
            TaskType withWindows(Architecture architecture, Variant variant);

            /**
             * The function that allows us to specify platform.
             *
             * @param platformProperties the properties of the platform.
             * @return the next stage of the container registry task definition.
             */
            TaskType withPlatform(PlatformProperties platformProperties);
        }

        /**
         * The stage of the container registry task definition that allows us to specify the type of task step.
         */
        interface TaskType {

            /**
             * The function that allows us to have a task step of type FileTaskStep.
             *
             * @return the first stage of the FileTaskStep definition.
             */
            RegistryFileTaskStep.DefinitionStages.Blank defineFileTaskStep();

            /**
             * The function that allows us to have a task step of type EncodedTaskStep.
             *
             * @return the first stage of the EncodedTaskStep definition.
             */
            RegistryEncodedTaskStep.DefinitionStages.Blank defineEncodedTaskStep();

            /**
             * The function that allows us to have a task step of type DockerTaskStep.
             *
             * @return the first stage of the DockerTaskStep definition.
             */
            RegistryDockerTaskStep.DefinitionStages.Blank defineDockerTaskStep();
        }




        /**
         * The stage of the container registry task definition that allows us to specify the trigger for the container registry task.
         */
        interface Trigger {

            /**
             * The function that allows us to specify a list of source triggers.
             *
             * @param sourceTriggers a list that contains SourceTriggers.
             * @return the next stage of the container registry task definition.
             */
            TaskCreatable withTrigger(List<SourceTrigger> sourceTriggers);

            /**
             * The function that allows us to specify a BaseImageTrigger.
             *
             * @param baseImageTrigger a BaseImageTrigger.
             * @return the next stage of the container registry task definition.
             */
            TaskCreatable withTrigger(BaseImageTrigger baseImageTrigger);

            /**
             * The function that allows us to specify both a list of SourceTriggers and a BaseImageTrigger.
             *
             * @param sourceTriggers a list that contains SourceTriggers.
             * @param baseImageTrigger a BaseImageTrigger.
             * @return the next stage of the container registry task definition.
             */
            TaskCreatable withTrigger(List<SourceTrigger> sourceTriggers, BaseImageTrigger baseImageTrigger);
        }


        /**
         * The stage of the container registry task definition that allows us to specify the AgentConfiguration for the container registry task.
         */
        interface AgentConfiguration {

            /**
             * The function that allows us to set the count of the CPU.
             *
             * @param count the CPU count.
             * @return the next stage of the container registry task definition.
             */
            TaskCreatable withCpuCount(int count);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be created,
         *  but also allows for any other optional settings to be specified.
         */
        interface TaskCreatable extends
                Trigger,
                AgentConfiguration,
                Creatable<Task> {

        }
    }


}
