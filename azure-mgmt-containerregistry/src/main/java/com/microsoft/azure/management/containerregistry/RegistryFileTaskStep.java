/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerregistry;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.Settable;
import javafx.scene.Parent;

import java.util.Map;

/**
 * An immutable client-side representation of an Azure RegistryFileTaskStep registry task.
 */
@Fluent()
@Beta(Beta.SinceVersion.V1_1_0)
public interface RegistryFileTaskStep {

    /**
     * Container interface for all the definitions related to a RegistryFileTaskStep.
     */
    interface Definition extends
            RegistryFileTaskStep.DefinitionStages.Blank,
            RegistryFileTaskStep.DefinitionStages.FileTaskStep,
            RegistryFileTaskStep.DefinitionStages.FileTaskStepAttachable {

    }

    interface Update extends
            RegistryFileTaskStep.UpdateStages.FileTaskStep,
            RegistryFileTaskStep.UpdateStages.ValuePath,
            RegistryFileTaskStep.UpdateStages.OverridingValues,
            Settable<Task.Update> {

    }

    /**
     * Grouping of registry task definition stages.
     */
    interface DefinitionStages {

        /**
         * The first stage of a RegistryFileTaskStep definition.
         */
        interface Blank extends FileTaskStep {

        }

        /**
         * The stage of the container registry FileTaskStep definition allowing to specify the task path.
         */
        interface FileTaskStep {

            /**
             * The function that specifies the path to the task file.
             *
             * @param path the path to the task file.
             * @return the next stage of the container registry FileTaskStep definition.
             */
            FileTaskStepAttachable withTaskPath(String path);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be attached,
         *  but also allows for any other optional settings to be specified.
         */
        interface FileTaskStepAttachable extends Attachable<Task.DefinitionStages.TaskCreatable> {

            /**
             * The function that specifies the path to the values.
             *
             * @param path the path to the values.
             * @return the next stage of the container registry FileTaskStep definition.
             */
            FileTaskStepAttachable withValuesPath(String path);

            /**
             * The function that specifies the values that override the corresponding values specified under the function withValuesPath().
             *
             * @param overridingValues a map which contains the values that will override the corresponding values specified under the function withValuesPath().
             * @return the next stage of the container registry FileTaskStep definition.
             */
            FileTaskStepAttachable withOverridingValues(Map<String, OverridingValue> overridingValues);

            /**
             * The function that specifies a single value that will override the corresponding value specified under the function withValuesPath().
             *
             * @param name the name of the value to be overriden.
             * @param overridingValue the value of the value to be overriden.
             * @return the next stage of the container registry FileTaskStep definition.
             */
            FileTaskStepAttachable withOverridingValue(String name, OverridingValue overridingValue);
        }
    }

    /**
     * Grouping of registry task update stages.
     */
    interface UpdateStages {

        /**
         * The stage of the container registry FileTaskStep update allowing to specify the task path.
         */
        interface FileTaskStep {

            /**
             * The function that specifies the path to the task file.
             *
             * @param path the path to the task file.
             * @return the next stage of the container registry FileTaskStep update.
             */
            Update withTaskPath(String path);
        }


        /**
         * The stage of the container registry FileTaskStep update allowing to specify the path to the values.
         */
        interface ValuePath {

            /**
             * The function that specifies the path to the values.
             *
             * @param path the path to the values.
             * @return the next stage of the container registry FileTaskStep update.
             */
            Update withValuesPath(String path);

        }

        /**
         * The stage of the container registry FileTaskStep update allowing to specify the overriding values.
         */
        interface OverridingValues {

            /**
             * The function that specifies the values that override the corresponding values specified under the function withValuesPath().
             *
             * @param overridingValues a map which contains the values that will override the corresponding values specified under the function withValuesPath().
             * @return the next stage of the container registry FileTaskStep update.
             */
            Update withOverridingValues(Map<String, OverridingValue> overridingValues);

            /**
             * The function that specifies a single value that will override the corresponding value specified under the function withValuesPath().
             *
             * @param name the name of the value to be overriden.
             * @param overridingValue the value of the value to be overriden.
             * @return the next stage of the container registry FileTaskStep update.
             */
            Update withOverridingValue(String name, OverridingValue overridingValue);
        }
    }
}
