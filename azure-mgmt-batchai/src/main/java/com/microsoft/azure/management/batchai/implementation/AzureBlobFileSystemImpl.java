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
import com.microsoft.azure.management.batchai.Cluster;
import com.microsoft.azure.management.batchai.KeyVaultSecretReference;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

/**
 * Represents Azure blob file system reference.
 */
@LangDefinition
public class AzureBlobFileSystemImpl extends IndexableWrapperImpl<AzureBlobFileSystemReference>
        implements
        AzureBlobFileSystem,
        AzureBlobFileSystem.Definition<Cluster.DefinitionStages.WithCreate> {
    private ClusterImpl parent;

    AzureBlobFileSystemImpl(AzureBlobFileSystemReference inner, ClusterImpl parent) {
        super(inner);
        this.parent = parent;
    }

    @Override
    public Definition<Cluster.DefinitionStages.WithCreate> withStorageAccountName(String storageAccountName) {
        inner().withAccountName(storageAccountName);
        return this;
    }


    @Override
    public Definition<Cluster.DefinitionStages.WithCreate> withRelativeMountPath(String mountPath) {
        inner().withRelativeMountPath(mountPath);
        return this;
    }

    @Override
    public Cluster parent() {
        return parent;
    }

    @Override
    public Cluster.DefinitionStages.WithCreate attach() {
        this.parent.attachAzureBlobFileSystem(this);
        return parent;
    }

    @Override
    public DefinitionStages.WithAttach<Cluster.DefinitionStages.WithCreate> withAccountKey(String accountKey) {
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
    public DefinitionStages.WithAttach<Cluster.DefinitionStages.WithCreate> withKeyVaultSecretReference(KeyVaultSecretReference keyVaultSecretReference) {
        ensureCredentials().withAccountKeySecretReference(keyVaultSecretReference);
        return this;
    }

    @Override
    public DefinitionStages.WithRelativeMountPath<Cluster.DefinitionStages.WithCreate> withContainerName(String containerName) {
        inner().withContainerName(containerName);
        return this;
    }

    @Override
    public DefinitionStages.WithAttach<Cluster.DefinitionStages.WithCreate> withMountOptions(String mountOptions) {
        inner().withMountOptions(mountOptions);
        return this;
    }
}