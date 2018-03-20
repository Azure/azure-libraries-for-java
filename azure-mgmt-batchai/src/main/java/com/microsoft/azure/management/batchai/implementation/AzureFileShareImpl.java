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
import com.microsoft.azure.management.batchai.KeyVaultSecretReference;
import com.microsoft.azure.management.batchai.model.HasMountVolumes;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

/**
 * Represents Azure file share reference.
 */
@LangDefinition
class AzureFileShareImpl<ParentT> extends IndexableWrapperImpl<AzureFileShareReference>
        implements
        AzureFileShare,
        AzureFileShare.Definition<ParentT> {
    private HasMountVolumes parent;

    AzureFileShareImpl(AzureFileShareReference inner, HasMountVolumes parent) {
        super(inner);
        this.parent = parent;
    }

    @Override
    public Definition<ParentT> withStorageAccountName(String storageAccountName) {
        inner().withAccountName(storageAccountName);
        return this;
    }

    @Override
    public Definition<ParentT> withAzureFileUrl(String azureFileUrl) {
        inner().withAzureFileUrl(azureFileUrl);
        return this;
    }

    @Override
    public Definition<ParentT> withRelativeMountPath(String mountPath) {
        inner().withRelativeMountPath(mountPath);
        return this;
    }

    @Override
    public ParentT attach() {
        this.parent.attachAzureFileShare(this);
        return (ParentT) parent;
    }

    @Override
    public DefinitionStages.WithAttach<ParentT> withAccountKey(String accountKey) {
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
    public DefinitionStages.WithAttach<ParentT> withKeyVaultSecretReference(KeyVaultSecretReference keyVaultSecretReference) {
        ensureCredentials().withAccountKeySecretReference(keyVaultSecretReference);
        return this;
    }

    @Override
    public DefinitionStages.WithAttach<ParentT> withFileMode(String fileMode) {
        inner().withFileMode(fileMode);
        return this;
    }

    @Override
    public DefinitionStages.WithAttach<ParentT> withDirectoryMode(String directoryMode) {
        inner().withDirectoryMode(directoryMode);
        return this;
    }
}