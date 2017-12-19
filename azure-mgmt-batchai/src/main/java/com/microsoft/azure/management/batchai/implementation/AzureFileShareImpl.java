/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.AzureFileShare;
import com.microsoft.azure.management.batchai.AzureFileShareReference;
import com.microsoft.azure.management.batchai.AzureStorageCredentialsInfo;
import com.microsoft.azure.management.batchai.BatchAICluster;
import com.microsoft.azure.management.batchai.KeyVaultSecretReference;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

/**
 * Represents Azure file share reference.
 */
@LangDefinition
public class AzureFileShareImpl extends IndexableWrapperImpl<AzureFileShareReference>
        implements
        AzureFileShare,
        AzureFileShare.Definition<BatchAICluster.DefinitionStages.WithCreate> {
    private BatchAIClusterImpl parent;

    AzureFileShareImpl(AzureFileShareReference inner, BatchAIClusterImpl parent) {
        super(inner);
        this.parent = parent;
    }

    @Override
    public Definition<BatchAICluster.DefinitionStages.WithCreate> withStorageAccountName(String storageAccountName) {
        inner().withAccountName(storageAccountName);
        return this;
    }

    @Override
    public Definition<BatchAICluster.DefinitionStages.WithCreate> withAzureFileUrl(String azureFileUrl) {
        inner().withAzureFileUrl(azureFileUrl);
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
        this.parent.attachAzureFileShare(this);
        return parent;
    }

    @Override
    public DefinitionStages.WithAttach<BatchAICluster.DefinitionStages.WithCreate> withAccountKey(String accountKey) {
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
    public DefinitionStages.WithAttach<BatchAICluster.DefinitionStages.WithCreate> withKeyVaultSecretReference(KeyVaultSecretReference keyVaultSecretReference) {
        ensureCredentials().withAccountKeySecretReference(keyVaultSecretReference);
        return this;
    }

    @Override
    public DefinitionStages.WithAttach<BatchAICluster.DefinitionStages.WithCreate> withFileMode(String fileMode) {
        inner().withFileMode(fileMode);
        return this;
    }

    @Override
    public DefinitionStages.WithAttach<BatchAICluster.DefinitionStages.WithCreate> withDirectoryMode(String directoryMode) {
        inner().withDirectoryMode(directoryMode);
        return this;
    }
}