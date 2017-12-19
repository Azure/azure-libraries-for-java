/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasParent;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;

/**
 * A client-side representation of a mode setup task of a Batch AI cluster.
 */
@Fluent
@Beta
public interface NodeSetupTask extends
        HasInner<SetupTask>,
        Indexable,
        HasParent<Cluster> {
    /**
     * Grouping of node setup definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of the node setup definition.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface Blank<ParentT> extends WithCommandLine<ParentT> {
        }

        /**
         * The stage of the setup task definition allowing to specify the command line instructions.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithCommandLine<ParentT> {
            /**
             * @param commandLine command Line to start Setup process
             * @return the next stage of the definition
             */
            WithStdOutErrPath<ParentT> withCommandLine(String commandLine);
        }

        /**
         * The stage of the setup task definition allowing to specify if ommand line instructions should run in elevated mode.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithElevatedMode<ParentT> {
            /**
             * Specifies that the setup task should run in elevated mode.
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withRunElevated();
        }

        /**
         * The stage of the setup task definition allowing to specify where Batch AI will upload stdout and stderr of the setup task.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithStdOutErrPath<ParentT> {
            /**
             * @param stdOutErrPathPrefix the path where the Batch AI service will upload the stdout and stderror of setup task
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withStdOutErrPath(String stdOutErrPathPrefix);
        }

        /**
         * The stage of the setup task definition allowing to specify environment variables.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithEnvironmentVariable<ParentT> {
            /**
             * @param name name of the variable to set
             * @param value value of the variable to set
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withEnvironmentVariable(String name, String value);
        }

        /** The final stage of the setup task definition.
         * At this stage, any remaining optional settings can be specified, or the setup task definition
         * can be attached to the parent cluster definition.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithAttach<ParentT> extends
                Attachable.InDefinition<ParentT>,
                WithElevatedMode<ParentT>,
                WithEnvironmentVariable<ParentT> {
        }
    }

    /** The entirety of a setup task definition.
     * @param <ParentT> the stage of the parent definition to return to after attaching this definition
     */
    interface Definition<ParentT> extends
            DefinitionStages.Blank<ParentT>,
            DefinitionStages.WithElevatedMode<ParentT>,
            DefinitionStages.WithStdOutErrPath<ParentT>,
            DefinitionStages.WithEnvironmentVariable<ParentT>,
            DefinitionStages.WithAttach<ParentT> {
    }
}
