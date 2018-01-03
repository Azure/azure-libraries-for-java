/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.AzureBlobFileSystem;
import com.microsoft.azure.management.batchai.AzureBlobFileSystemReference;
import com.microsoft.azure.management.batchai.AzureStorageCredentialsInfo;
import com.microsoft.azure.management.batchai.BatchAICluster;
import com.microsoft.azure.management.batchai.KeyVaultSecretReference;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

/**
 * Represents Azure blob file system reference.
 */
@LangDefinition
public class AzureBlobFileSystemImpl extends IndexableWrapperImpl<AzureBlobFileSystemReference>
        implements
        AzureBlobFileSystem,
        AzureBlobFileSystem.Definition<BatchAICluster.DefinitionStages.WithCreate> {
    private BatchAIClusterImpl parent;

    AzureBlobFileSystemImpl(AzureBlobFileSystemReference inner, BatchAIClusterImpl parent) {
        super(inner);
        this.parent = parent;
    }

    @Override
    public Definition<BatchAICluster.DefinitionStages.WithCreate> withStorageAccountName(String storageAccountName) {
        inner().withAccountName(storageAccountName);
        return this;
    }


    @Override
    public Definition<BatchAICluster.DefinitionStages.WithCreate> withRelativeMountPath(String mountPath) {
        inner().withRelativeMountPath(mountPath);
        return this;
    }

    @Override
    public BatchAICluster parent() {
        return parent;
    }

    @Override
    public BatchAICluster.DefinitionStages.WithCreate attach() {
        this.parent.attachAzureBlobFileSystem(this);
        return parent;
    }

    @Override
    public AzureBlobFileSystemImpl withAccountKey(String accountKey) {
        ensureCredentials().withAccountKey(accountKey);
        return this;
    }

    private AzureStorageCredentialsInfo ensureCredentials() {
        if (inner().credentials() == null) {
            inner().withCredentials(new AzureStorageCredentialsInfo());
        }
        return inner().credentials();
    }

    @Override
    public AzureBlobFileSystemImpl withKeyVaultSecretReference(KeyVaultSecretReference keyVaultSecretReference) {
        ensureCredentials().withAccountKeySecretReference(keyVaultSecretReference);
        return this;
    }

    @Override
    public AzureBlobFileSystemImpl withContainerName(String containerName) {
        inner().withContainerName(containerName);
        return this;
    }

    @Override
    public AzureBlobFileSystemImpl withMountOptions(String mountOptions) {
        inner().withMountOptions(mountOptions);
        return this;
    }
}