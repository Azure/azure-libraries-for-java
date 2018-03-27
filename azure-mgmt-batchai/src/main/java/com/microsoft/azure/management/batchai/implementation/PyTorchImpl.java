/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIJob;
import com.microsoft.azure.management.batchai.PyTorchSettings;
import com.microsoft.azure.management.batchai.ToolTypeSettings;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

/**
 * Represents PyTorch settings.
 */
@LangDefinition
class PyTorchImpl extends IndexableWrapperImpl<PyTorchSettings>
        implements
        ToolTypeSettings.PyTorch,
        ToolTypeSettings.PyTorch.Definition<BatchAIJob.DefinitionStages.WithCreate> {
    private BatchAIJobImpl parent;

    PyTorchImpl(PyTorchSettings inner, BatchAIJobImpl parent) {
        super(inner);
        this.parent = parent;
    }

    @Override
    public BatchAIJob parent() {
        return parent;
    }

    @Override
    public BatchAIJob.DefinitionStages.WithCreate attach() {
        this.parent.attachPyTorchSettings(this);
        return parent;
    }

    @Override
    public PyTorchImpl withCommandLineArgs(String commandLineArgs) {
        inner().withCommandLineArgs(commandLineArgs);
        return this;
    }

    @Override
    public PyTorchImpl withProcessCount(int processCount) {
        inner().withProcessCount(processCount);
        return this;
    }

    @Override
    public PyTorchImpl withPythonInterpreterPath(String path) {
        inner().withPythonInterpreterPath(path);
        return this;
    }

    @Override
    public PyTorchImpl withPythonScriptFile(String pythonScriptFilePath) {
        inner().withPythonScriptFilePath(pythonScriptFilePath);
        return this;
    }

    @Override
    public PyTorchImpl withCommunicationBackend(String communicationBackend) {
        inner().withCommunicationBackend(communicationBackend);
        return this;
    }
}
