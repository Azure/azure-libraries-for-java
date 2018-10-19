/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;

import java.util.List;

/**
 * An immutable client-side representation of an Azure RegistryDockerTaskStep registry task.
 */
@Fluent()
@Beta(Beta.SinceVersion.V1_1_0)
public interface RegistryDockerTaskStep {

    /**
     * Container interface for all the definitions related to a RegistryFileTaskStep.
     */
    interface Definition extends
            RegistryDockerTaskStep.DefinitionStages.Blank,
            RegistryDockerTaskStep.DefinitionStages.DockerTaskStep,
            RegistryDockerTaskStep.DefinitionStages.DockerTaskStepAttachable {

    }

    /**
     * Grouping of registry task definition stages.
     */
    interface DefinitionStages {

        /**
         * The first stage of a DockerFileTaskStep definition.
         */
        interface Blank extends DockerTaskStep {

        }

        /**
         * The stage of the container registry DockerTaskStep definition allowing to specify the path to the Docker file.
         */
        interface DockerTaskStep {

            /**
             * The function that specifies the path to the Docker file.
             *
             * @param path the path to the Docker file.
             * @return the next stage of the container registry DockerTaskStep definition.
             */
            DockerTaskStepAttachable withDockerFilePath(String path);

        }

        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be attached,
         *  but also allows for any other optional settings to be specified.
         */
        interface DockerTaskStepAttachable extends Attachable<Task.DefinitionStages.TaskCreatable> {

            /**
             * The function that specifies the list of image names.
             *
             * @param imageNames the image names.
             * @return the next step of the container registry DockerTaskStep definition.
             */
            DockerTaskStepAttachable withImageNames(List<String> imageNames);

            /**
             * The function that enables push.
             *
             * @return the next step of the container registry DockerTaskStep definition.
             */
            DockerTaskStepAttachable withPushEnabled();

            /**
             * The function that disables push.
             *
             * @return the next step of the container registry DockerTaskStep definition.
             */
            DockerTaskStepAttachable withPushDisabled();

            /**
             * The function that specifies the use a cache.
             * @return the next step of the container registry DockerTaskStep definition.
             */
            DockerTaskStepAttachable withCache();

            /**
             * The function that specifies not using a cache.
             * @return the next step of the container registry DockerTaskStep definition.
             */
            DockerTaskStepAttachable withoutCache();
        }

    }
}
