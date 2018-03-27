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
 * Client-side representation for AzureFileShareReference.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_6_0)
public interface AzureFileShare extends Indexable,
        HasInner<AzureFileShareReference> {

    /**
     * Definition of azure file share reference.
     *
     * @param <ParentT> the stage of the parent definition to return to after attaching this definition
     */
    interface Definition<ParentT> extends
            DefinitionStages.Blank<ParentT>,
            DefinitionStages.WithAzureFileUrl<ParentT>,
            DefinitionStages.WithRelativeMountPath<ParentT>,
            DefinitionStages.WithAzureStorageCredentials<ParentT>,
            DefinitionStages.WithAttach<ParentT> {
    }

    /**
     * Definition stages for azure file share reference.
     */
    interface DefinitionStages {

        interface WithAttach<ParentT> extends
                Attachable.InDefinition<ParentT>,
                WithFileMode<ParentT>,
                WithDirectoryMode<ParentT> {
        }

        interface Blank<ParentT> extends WithStorageAccount<ParentT> {
        }

        interface WithStorageAccount<ParentT> {
            WithAzureFileUrl<ParentT> withStorageAccountName(String storageAccountName);
        }

        interface WithAzureFileUrl<ParentT> {
            WithRelativeMountPath<ParentT> withAzureFileUrl(String azureFileUrl);
        }

        interface WithRelativeMountPath<ParentT> {
            WithAzureStorageCredentials<ParentT> withRelativeMountPath(String mountPath);
        }

        interface WithAzureStorageCredentials<ParentT> {
            WithAttach<ParentT> withAccountKey(String accountKey);

            WithAttach<ParentT> withKeyVaultSecretReference(KeyVaultSecretReference keyVaultSecretReference);
        }

        interface WithFileMode<ParentT> {
            DefinitionStages.WithAttach<ParentT> withFileMode(String fileMode);
        }

        interface WithDirectoryMode<ParentT> {
            DefinitionStages.WithAttach<ParentT> withDirectoryMode(String directoryMode);
        }
    }
}
