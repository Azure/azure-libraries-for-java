/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIJob;
import com.microsoft.azure.management.batchai.CNTKsettings;
import com.microsoft.azure.management.batchai.ToolTypeSettings;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

/**
 * Represents Microsoft Cognitive Toolkit settings.
 */
@LangDefinition
public class CognitiveToolkitImpl extends IndexableWrapperImpl<CNTKsettings>
        implements
        ToolTypeSettings.CognitiveToolkit,
        ToolTypeSettings.CognitiveToolkit.Definition<BatchAIJob.DefinitionStages.WithCreate> {
    private static final String BRAIN_SCRIPT = "BrainScript";
    private static final String PYTHON = "Python";
    private BatchAIJobImpl parent;

    CognitiveToolkitImpl(CNTKsettings inner, BatchAIJobImpl parent) {
        super(inner);
        this.parent = parent;
    }

    @Override
    public BatchAIJob parent() {
        return parent;
    }

    @Override
    public BatchAIJob.DefinitionStages.WithCreate attach() {
        this.parent.attachCntkSettings(this);
        return parent;
    }

    @Override
    public CognitiveToolkitImpl withBrainScript(String configFilePath) {
        inner().withLanguageType(BRAIN_SCRIPT);
        inner().withConfigFilePath(configFilePath);
        return this;
    }

    @Override
    public CognitiveToolkitImpl withPythonScriptFile(String pythonScriptFilePath) {
        inner().withLanguageType(PYTHON);
        inner().withPythonScriptFilePath(pythonScriptFilePath);
        return this;
    }

    @Override
    public CognitiveToolkitImpl withCommandLineArgs(String commandLineArgs) {
        inner().withCommandLineArgs(commandLineArgs);
        return this;
    }

    @Override
    public CognitiveToolkitImpl withProcessCount(int processCount) {
        inner().withProcessCount(processCount);
        return this;
    }

    @Override
    public CognitiveToolkitImpl withPythonInterpreterPath(String path) {
        inner().withPythonInterpreterPath(path);
        return this;
    }
}