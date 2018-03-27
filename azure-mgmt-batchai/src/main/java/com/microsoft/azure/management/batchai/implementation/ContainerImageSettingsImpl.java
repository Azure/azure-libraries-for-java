/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIJob;
import com.microsoft.azure.management.batchai.ContainerImageSettings;
import com.microsoft.azure.management.batchai.ImageSourceRegistry;
import com.microsoft.azure.management.batchai.KeyVaultSecretReference;
import com.microsoft.azure.management.batchai.PrivateRegistryCredentials;
import com.microsoft.azure.management.batchai.ResourceId;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

@LangDefinition
class ContainerImageSettingsImpl extends IndexableWrapperImpl<ImageSourceRegistry>
        implements ContainerImageSettings.Definition<BatchAIJob.DefinitionStages.WithCreate> {
    private BatchAIJobImpl parent;

    protected ContainerImageSettingsImpl(ImageSourceRegistry inner, BatchAIJobImpl parent) {
        super(inner);
        this.parent = parent;
    }

    @Override
    public BatchAIJob.DefinitionStages.WithCreate attach() {
        this.parent.attachImageSourceRegistry(this);
        return parent;
    }

    @Override
    public ContainerImageSettingsImpl withRegistryUrl(String serverUrl) {
        inner().withServerUrl(serverUrl);
        return this;
    }

    @Override
    public ContainerImageSettingsImpl withRegistryUsername(String username) {
        inner().withCredentials(new PrivateRegistryCredentials().withUsername(username));
        return this;
    }

    @Override
    public ContainerImageSettingsImpl withRegistryPassword(String password) {
        inner().credentials().withPassword(password);
        return this;
    }

    @Override
    public ContainerImageSettingsImpl withRegistrySecretReference(String keyVaultId, String secretUrl) {
        inner().credentials().withPasswordSecretReference(
                new KeyVaultSecretReference()
                        .withSourceVault(new ResourceId().withId(keyVaultId))
                        .withSecretUrl(secretUrl));
        return this;
    }
}
