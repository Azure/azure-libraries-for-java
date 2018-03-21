/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;

/**
 * Client-side representation for FileServerReference.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_6_0)
public interface FileServer extends Indexable,
        HasInner<FileServerReference> {

    /**
     * Definition of file server reference.
     *
     * @param <ParentT> the stage of the parent definition to return to after attaching this definition
     */
    interface Definition<ParentT> extends
            DefinitionStages.Blank<ParentT>,
            DefinitionStages.WithRelativeMountPath<ParentT>,
            DefinitionStages.WithAttach<ParentT> {
    }

    /**
     * Definition stages for azure blob file system reference.
     */
    interface DefinitionStages {

        interface WithAttach<ParentT> extends
                Attachable.InDefinition<ParentT>,
                WithSourceDirectory<ParentT>,
                WithMountOptions<ParentT> {
        }

        interface Blank<ParentT> extends WithFileServer<ParentT> {
        }

        interface WithFileServer<ParentT> {
            WithRelativeMountPath<ParentT> withFileServerId(String fileServerId);
        }

        interface WithRelativeMountPath<ParentT> {
            WithAttach<ParentT> withRelativeMountPath(String mountPath);
        }

        interface WithSourceDirectory<ParentT> {
            WithAttach<ParentT> withSourceDirectory(String sourceDirectory);
        }

        interface WithMountOptions<ParentT> {
            DefinitionStages.WithAttach<ParentT> withMountOptions(String mountOptions);
        }
    }
}
