/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIJob;
import com.microsoft.azure.management.batchai.CaffeSettings;
import com.microsoft.azure.management.batchai.ToolTypeSettings;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

/**
 * Represents Caffe settings.
 */
@LangDefinition
public class CaffeImpl extends IndexableWrapperImpl<CaffeSettings>
        implements
        ToolTypeSettings.Caffe,
        ToolTypeSettings.Caffe.Definition<BatchAIJob.DefinitionStages.WithCreate> {
    private BatchAIJobImpl parent;

    CaffeImpl(CaffeSettings inner, BatchAIJobImpl parent) {
        super(inner);
        this.parent = parent;
    }

    @Override
    public BatchAIJob parent() {
        return parent;
    }

    @Override
    public BatchAIJob.DefinitionStages.WithCreate attach() {
        this.parent.attachCaffeSettings(this);
        return parent;
    }

    @Override
    public CaffeImpl withCommandLineArgs(String commandLineArgs) {
        inner().withCommandLineArgs(commandLineArgs);
        return this;
    }

    @Override
    public CaffeImpl withProcessCount(int processCount) {
        inner().withProcessCount(processCount);
        return this;
    }

    @Override
    public CaffeImpl withPythonInterpreterPath(String path) {
        inner().withPythonInterpreterPath(path);
        return this;
    }

    @Override
    public CaffeImpl withConfigFile(String configFilePath) {
        inner().withConfigFilePath(configFilePath);
        return this;
    }

    @Override
    public CaffeImpl withPythonScriptFile(String pythonScriptFilePath) {
        inner().withPythonScriptFilePath(pythonScriptFilePath);
        return this;
    }
}