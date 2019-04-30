/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIJob;
import com.microsoft.azure.management.batchai.CustomMpiSettings;
import com.microsoft.azure.management.batchai.ToolTypeSettings;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

/**
 * Represents the settings for a custom MPI job.
 */
@LangDefinition
class CustomMpiImpl extends IndexableWrapperImpl<CustomMpiSettings>
        implements
        ToolTypeSettings.CustomMpi,
        ToolTypeSettings.CustomMpi.Definition<BatchAIJob.DefinitionStages.WithCreate> {
    private BatchAIJobImpl parent;

    CustomMpiImpl(CustomMpiSettings inner, BatchAIJobImpl parent) {
        super(inner);
        this.parent = parent;
    }

    @Override
    public BatchAIJob parent() {
        return parent;
    }

    @Override
    public BatchAIJob.DefinitionStages.WithCreate attach() {
        this.parent.attachCustomMpiSettings(this);
        return parent;
    }

    @Override
    public CustomMpiImpl withProcessCount(int processCount) {
        inner().withProcessCount(processCount);
        return this;
    }

    @Override
    public CustomMpiImpl withCommandLine(String commandLine) {
        inner().withCommandLine(commandLine);
        return this;
    }
}
