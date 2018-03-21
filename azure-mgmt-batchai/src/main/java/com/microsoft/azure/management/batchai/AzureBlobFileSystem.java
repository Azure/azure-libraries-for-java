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
 * Client-side representation for AzureBlobFileSystemReference.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_6_0)
public interface AzureBlobFileSystem extends Indexable,
        HasInner<AzureBlobFileSystemReference> {

    /**
     * Definition of azure blob file system reference.
     *
     * @param <ParentT> the stage of the parent definition to return to after attaching this definition
     */
    interface Definition<ParentT> extends
            DefinitionStages.Blank<ParentT>,
            DefinitionStages.WithStorageContainerName<ParentT>,
            DefinitionStages.WithRelativeMountPath<ParentT>,
            DefinitionStages.WithAzureStorageCredentials<ParentT>,
            DefinitionStages.WithAttach<ParentT> {
    }

    /**
     * Definition stages for azure blob file system reference.
     */
    interface DefinitionStages {

        interface WithAttach<ParentT> extends
                Attachable.InDefinition<ParentT>,
                WithMountOptions<ParentT> {
        }

        interface Blank<ParentT> extends WithStorageAccount<ParentT> {
        }

        interface WithStorageAccount<ParentT> {
            WithStorageContainerName<ParentT> withStorageAccountName(String storageAccountName);
        }

        interface WithStorageContainerName<ParentT> {
            WithRelativeMountPath<ParentT> withContainerName(String containerName);
        }

        interface WithRelativeMountPath<ParentT> {
            WithAzureStorageCredentials<ParentT> withRelativeMountPath(String mountPath);
        }

        interface WithAzureStorageCredentials<ParentT> {
            WithAttach<ParentT> withAccountKey(String accountKey);

            WithAttach<ParentT> withKeyVaultSecretReference(KeyVaultSecretReference keyVaultSecretReference);
        }

        interface WithMountOptions<ParentT> {
            DefinitionStages.WithAttach<ParentT> withMountOptions(String mountOptions);
        }
    }
}
