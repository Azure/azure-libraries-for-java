/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerregistry;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.storage.OverridingValue;

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
             * The function that allows us to specify the path to the task file.
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
             * The function that allows us to specify the path to the values.
             *
             * @param path the path to the values.
             * @return the next stage of the container registry FileTaskStep definition.
             */
            FileTaskStepAttachable withValuesPath(String path);

            /**
             * The function that allows us to specify values that override the corresponding values specified under the function withValuesPath().
             *
             * @param overridingValues a map which contains the values that will override the corresponding values specified under the function withValuesPath().
             * @return the next stage of the container registry FileTaskStep definition.
             */
            FileTaskStepAttachable withOverridingValues(Map<String, OverridingValue> overridingValues);

            /**
             * The function that allows us to specify a single value that will override the corresponding value specified under the function withValuesPath().
             *
             * @param name the name of the value to be overriden.
             * @param overridingValue the value of the value to be overriden.
             * @return the next stage of the container registry FileTaskStep definition.
             */
            FileTaskStepAttachable withOverridingValue(String name, OverridingValue overridingValue);
        }
    }
}
