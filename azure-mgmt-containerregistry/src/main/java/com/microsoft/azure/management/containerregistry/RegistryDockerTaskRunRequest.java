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
 * An immutable client-side representation of an Azure registry Docker task run request.
 */
@Fluent()
@Beta(Beta.SinceVersion.V1_1_0)
public interface RegistryDockerTaskRunRequest {

    /**
     * @return the length of the timeout.
     */
    int timeout();

    /**
     * @return the properties of the platform.
     */
    PlatformProperties platform();

    /**
     * @return the number of CPUs.
     */
    int cpuCount();

    /**
     * @return the location of the source control.
     */
    String sourceLocation();

    /**
     * @return whether archive is enabled.
     */
    boolean isArchiveEnabled();

    /**
     * Container interface for all the definitions related to a registry Docker task run request.
     */
    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.DockerTaskRunRequestStep,
            DefinitionStages.DockerTaskRunRequestStepAttachable {

    }

    /**
     * Grouping of registry Docker task run request definition stages.
     */
    interface DefinitionStages {

        /**
         * The first stage of a container registry Docker task run request definition.
         */
        interface Blank {
            /**
             * The function that begins the definition of the Docker task step in the task run request.
             *
             * @return the next stage of the container Docker task run request definition.
             */
            DockerTaskRunRequestStep defineDockerTaskStep();
        }

        /**
         * The stage of the container Docker task run request definition that specifies the path to the Docker file.
         */
        interface DockerTaskRunRequestStep {

            /**
             * The function that specifies the path to the Docker file.
             *
             * @param path the path to the Docker file.
             * @return the next stage of the container Docker task run request definition.
             */
            DockerTaskRunRequestStepAttachable withDockerFilePath(String path);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be attached,
         *  but also allows for any other optional settings to be specified.
         */
        interface DockerTaskRunRequestStepAttachable extends Attachable<RegistryTaskRun.DefinitionStages.RunRequestExecutableWithSourceLocation> {

            /**
             * The function that specifies the list of image names.
             *
             * @param imageNames the list of image names.
             * @return the next stage of the container Docker task run request definition.
             */
            DockerTaskRunRequestStepAttachable withImageNames(List<String> imageNames);

            /**
             * The function that specifies push is enabled.
             *
             * @return the next stage of the container Docker task run request definition.
             */
            DockerTaskRunRequestStepAttachable withPushEnabled();

            /**
             * The function that specifies push is disabled.
             *
             * @return the next stage of the container Docker task run request definition.
             */
            DockerTaskRunRequestStepAttachable withPushDisabled();

            /**
             * The function that specifies a cache will be used.
             *
             * @return the next stage of the container Docker task run request definition.
             */
            DockerTaskRunRequestStepAttachable withCache();

            /**
             * The function that specifies a cache will not be used.
             *
             * @return the next stage of the container Docker task run request definition.
             */
            DockerTaskRunRequestStepAttachable withoutCache();
        }
    }
}
