/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIJob;
import com.microsoft.azure.management.batchai.TensorFlowSettings;
import com.microsoft.azure.management.batchai.ToolTypeSettings;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

/**
 * Represents TensorFlow settings.
 */
@LangDefinition
public class TensorFlowImpl extends IndexableWrapperImpl<TensorFlowSettings>
        implements
        ToolTypeSettings.TensorFlow,
        ToolTypeSettings.TensorFlow.Definition<BatchAIJob.DefinitionStages.WithCreate> {
    private BatchAIJobImpl parent;

    TensorFlowImpl(TensorFlowSettings inner, BatchAIJobImpl parent) {
        super(inner);
        this.parent = parent;
    }

    @Override
    public BatchAIJob parent() {
        return parent;
    }

    @Override
    public BatchAIJob.DefinitionStages.WithCreate attach() {
        this.parent.attachTensorFlowSettings(this);
        return parent;
    }

    @Override
    public TensorFlowImpl withMasterCommandLineArgs(String commandLineArgs) {
        inner().withMasterCommandLineArgs(commandLineArgs);
        return this;
    }

    @Override
    public TensorFlowImpl withPythonInterpreterPath(String path) {
        inner().withPythonInterpreterPath(path);
        return this;
    }

    @Override
    public TensorFlowImpl withPython(String pythonScriptFilePath) {
        inner().withPythonScriptFilePath(pythonScriptFilePath);
        return this;
    }

    @Override
    public TensorFlowImpl withWorkerCommandLineArgs(String commandLineArgs) {
        inner().withWorkerCommandLineArgs(commandLineArgs);
        return this;
    }

    @Override
    public TensorFlowImpl withParameterServerCommandLineArgs(String commandLineArgs) {
        inner().withParameterServerCommandLineArgs(commandLineArgs);
        return this;
    }

    @Override
    public TensorFlowImpl withWorkerCount(int workerCount) {
        inner().withWorkerCount(workerCount);
        return this;
    }

    @Override
    public TensorFlowImpl withParameterServerCount(int parameterServerCount) {
        inner().withParameterServerCount(parameterServerCount);
        return this;
    }
}