/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIJob;
import com.microsoft.azure.management.batchai.HorovodSettings;
import com.microsoft.azure.management.batchai.ToolTypeSettings;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

/**
 * Represents Horovod settings.
 */
@LangDefinition
class HorovodImpl extends IndexableWrapperImpl<HorovodSettings>
        implements
        ToolTypeSettings.Horovod,
        ToolTypeSettings.Horovod.Definition<BatchAIJob.DefinitionStages.WithCreate> {
    private BatchAIJobImpl parent;

    HorovodImpl(HorovodSettings inner, BatchAIJobImpl parent) {
        super(inner);
        this.parent = parent;
    }

    @Override
    public BatchAIJob parent() {
        return parent;
    }

    @Override
    public BatchAIJob.DefinitionStages.WithCreate attach() {
        this.parent.attachHorovodSettings(this);
        return parent;
    }

    @Override
    public HorovodImpl withCommandLineArgs(String commandLineArgs) {
        inner().withCommandLineArgs(commandLineArgs);
        return this;
    }

    @Override
    public HorovodImpl withProcessCount(int processCount) {
        inner().withProcessCount(processCount);
        return this;
    }

    @Override
    public HorovodImpl withPythonInterpreterPath(String path) {
        inner().withPythonInterpreterPath(path);
        return this;
    }

    @Override
    public HorovodImpl withPythonScriptFile(String pythonScriptFilePath) {
        inner().withPythonScriptFilePath(pythonScriptFilePath);
        return this;
    }
}
