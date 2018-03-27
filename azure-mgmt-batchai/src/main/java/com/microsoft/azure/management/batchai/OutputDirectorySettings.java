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
 * Specifies the settings for output directory.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_8_0)
public interface OutputDirectorySettings extends Indexable,
        HasParent<BatchAIJob>,
        HasInner<OutputDirectory> {

    /**
     * Definition of output directory settings.
     *
     * @param <ParentT> the stage of the parent definition to return to after attaching this definition
     */
    interface Definition<ParentT> extends
            DefinitionStages.Blank<ParentT>,
            DefinitionStages.WithAttach<ParentT> {
    }

    /**
     * Definition stages for OutputDirectory settings.
     */
    interface DefinitionStages {

        /**
         * The final stage of the output directory settings definition.
         * At this stage, any remaining optional settings can be specified, or the output directory settings definition
         * can be attached to the parent Batch AI job definition.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithAttach<ParentT> extends
                Attachable.InDefinition<ParentT>,
                WithPathSuffix<ParentT>,
                WithCreateNew<ParentT> {
        }

        /**
         * The first stage of the output directory settings definition.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface Blank<ParentT> extends WithPathPrefix<ParentT> {
        }

        /**
         * Specifies the prefix path where the output directory will be created.
         * NOTE: This is an absolute path to prefix. E.g.
         * $AZ_BATCHAI_MOUNT_ROOT/MyNFS/MyLogs. You can find the full path to the
         * output directory by combining pathPrefix, jobOutputDirectoryPathSegment
         * (reported by get job) and pathSuffix.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithPathPrefix<ParentT> {
            /**
             * @param pathPrefix path prefix
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withPathPrefix(String pathPrefix);
        }

        /**
         * Specifies the suffix path where the output directory will be created. E.g. models.
         * You can find the full path to the output directory by combining
         * pathPrefix, jobOutputDirectoryPathSegment (reported by get job) and
         * pathSuffix.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithPathSuffix<ParentT> {
            /**
             * @param pathSuffix path prefix
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withPathSuffix(String pathSuffix);
        }

        interface WithCreateNew<ParentT> {
            /**
             * @param createNew flag whether to create new directory.
             * Default is true. If false, then the directory is not created and can be
             * any directory path that the user specifies.
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withCreateNew(boolean createNew);
        }
    }
}