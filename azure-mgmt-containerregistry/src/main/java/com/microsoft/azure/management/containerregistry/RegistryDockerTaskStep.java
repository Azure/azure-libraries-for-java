/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Settable;

import java.util.List;

/**
 * An immutable client-side representation of an Azure RegistryDockerTaskStep registry task.
 */
@Fluent()
@Beta
public interface RegistryDockerTaskStep extends
        HasInner<DockerTaskStep>,
        RegistryTaskStep {
    /**
     * @return the image names of this Docker task step
     */
    List<String> imageNames();

    /**
     * @return whether push is enabled for this Docker task step
     */
    boolean isPushEnabled();

    /**
     * @return whether there is no cache for this Docker task step
     */
    boolean noCache();

    /**
     * @return Docker file path for this Docker task step
     */
    String dockerFilePath();

    /**
     * @return the arguments this Docker task step
     */
    List<Argument> arguments();

    /**
     * Container interface for all the definitions related to a RegistryDockerTaskStep.
     */
    interface Definition extends
            RegistryDockerTaskStep.DefinitionStages.Blank,
            RegistryDockerTaskStep.DefinitionStages.DockerFilePath,
            RegistryDockerTaskStep.DefinitionStages.DockerTaskStepAttachable {
    }

    /**
     * Container interface for all the updates related to a RegistryDockerTaskStep.
     */
    interface Update extends
            RegistryDockerTaskStep.UpdateStages.DockerFilePath,
            RegistryDockerTaskStep.UpdateStages.ImageNames,
            RegistryDockerTaskStep.UpdateStages.Push,
            RegistryDockerTaskStep.UpdateStages.Cache,
            Settable<Task.Update> {
    }

    /**
     * Grouping of registry Docker task definition stages.
     */
    interface DefinitionStages {

        /**
         * The first stage of a DockerFileTaskStep definition.
         */
        interface Blank extends DockerFilePath {
        }

        /**
         * The stage of the container registry DockerTaskStep definition allowing to specify the path to the Docker file.
         */
        interface DockerFilePath {
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
             * The function that specifies the use of a cache.
             *
             * @return the next step of the container registry DockerTaskStep definition.
             */
            DockerTaskStepAttachable withCache();

            /**
             * The function that specifies not using a cache.
             *
             * @return the next step of the container registry DockerTaskStep definition.
             */
            DockerTaskStepAttachable withoutCache();
        }
    }

    /**
     * Grouping of registry Docker task update stages.
     */
    interface UpdateStages {
        /**
         * The stage of the container registry DockerTaskStep update allowing to specify the Docker file path.
         */
        interface DockerFilePath {
            /**
             * The function that specifies the path to the Docker file.
             *
             * @param path the path to the Docker file.
             * @return the next stage of the container registry DockerTaskStep update.
             */
            Update withDockerFilePath(String path);
        }

        /**
         * The stage of the container registry DockerTaskStep update allowing to specify the image names.
         */
        interface ImageNames {
            /**
             * The function that specifies the image names.
             *
             * @param imageNames the list of the names of the images.
             * @return the next stage of the container registry DockerTaskStep update.
             */
            Update withImageNames(List<String> imageNames);
        }

        /**
         * The stage of the container registry DockerTaskStep update allowing to specify whether push is enabled or not.
         */
        interface Push {
            /**
             * The function that specifies push is enabled.
             *
             * @return the next stage of the container registry DockerTaskStep update.
             */
            Update withPushEnabled();

            /**
             * The function that specifies push is disabled.
             *
             * @return the next stage of the container registry DockerTaskStep update.
             */
            Update withPushDisabled();
        }

        /**
         * The stage of the container registry DockerTaskStep update allowing to specify whether to have a cache or not.
         */
        interface Cache {
            /**
             * The function that specifies the task has a cache.
             *
             * @return the next stage of the container registry DockerTaskStep update.
             */
            Update withCache();

            /**
             * The function that specifies the task does not have a cache.
             *
             * @return the next stage of the container registry DockerTaskStep update.
             */
            Update withoutCache();
        }
    }
}
