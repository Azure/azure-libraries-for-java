/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIJob;
import com.microsoft.azure.management.batchai.OutputDirectory;
import com.microsoft.azure.management.batchai.OutputDirectorySettings;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

@LangDefinition
class OutputDirectorySettingsImpl extends IndexableWrapperImpl<OutputDirectory>
        implements OutputDirectorySettings.Definition<BatchAIJob.DefinitionStages.WithCreate> {
    private BatchAIJobImpl parent;

    protected OutputDirectorySettingsImpl(OutputDirectory inner, BatchAIJobImpl parent) {
        super(inner);
        this.parent = parent;
    }

    @Override
    public OutputDirectorySettingsImpl withPathPrefix(String pathPrefix) {
        inner().withPathPrefix(pathPrefix);
        return this;
    }

    @Override
    public OutputDirectorySettingsImpl withPathSuffix(String pathSuffix) {
        inner().withPathSuffix(pathSuffix);
        return this;
    }

    @Override
    public OutputDirectorySettingsImpl withCreateNew(boolean createNew) {
        inner().withCreateNew(createNew);
        return this;
    }

    @Override
    public BatchAIJobImpl attach() {
        this.parent.attachOutputDirectory(this);
        return parent;
    }
}
