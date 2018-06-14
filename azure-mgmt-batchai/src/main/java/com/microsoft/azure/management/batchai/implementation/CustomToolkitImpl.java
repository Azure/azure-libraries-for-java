/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIJob;
import com.microsoft.azure.management.batchai.CustomToolkitSettings;
import com.microsoft.azure.management.batchai.ToolTypeSettings;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

/**
 * Represents the settings for a custom toolkit job settings.
 */
@LangDefinition
class CustomToolkitImpl extends IndexableWrapperImpl<CustomToolkitSettings>
        implements
        ToolTypeSettings.CustomToolkit,
        ToolTypeSettings.CustomToolkit.Definition<BatchAIJob.DefinitionStages.WithCreate> {
    private BatchAIJobImpl parent;

    CustomToolkitImpl(CustomToolkitSettings inner, BatchAIJobImpl parent) {
        super(inner);
        this.parent = parent;
    }

    @Override
    public BatchAIJob parent() {
        return parent;
    }

    @Override
    public BatchAIJob.DefinitionStages.WithCreate attach() {
        this.parent.attachCustomToolkitSettings(this);
        return parent;
    }

    @Override
    public CustomToolkitImpl withCommandLine(String commandLine) {
        inner().withCommandLine(commandLine);
        return this;
    }
}
