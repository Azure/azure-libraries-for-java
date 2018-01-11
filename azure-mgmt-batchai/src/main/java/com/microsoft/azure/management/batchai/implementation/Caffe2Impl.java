/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAIJob;
import com.microsoft.azure.management.batchai.Caffe2Settings;
import com.microsoft.azure.management.batchai.ToolTypeSettings;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

/**
 * Represents Caffe2 settings.
 */
@LangDefinition
public class Caffe2Impl extends IndexableWrapperImpl<Caffe2Settings>
        implements
        ToolTypeSettings.Caffe2,
        ToolTypeSettings.Caffe2.Definition<BatchAIJob.DefinitionStages.WithCreate> {
    private BatchAIJobImpl parent;

    Caffe2Impl(Caffe2Settings inner, BatchAIJobImpl parent) {
        super(inner);
        this.parent = parent;
    }

    @Override
    public BatchAIJob parent() {
        return parent;
    }

    @Override
    public BatchAIJob.DefinitionStages.WithCreate attach() {
        this.parent.attachCaffe2Settings(this);
        return parent;
    }

    @Override
    public Caffe2Impl withCommandLineArgs(String commandLineArgs) {
        inner().withCommandLineArgs(commandLineArgs);
        return this;
    }

        @Override
    public Caffe2Impl withPythonInterpreterPath(String path) {
        inner().withPythonInterpreterPath(path);
        return this;
    }

    @Override
    public Caffe2Impl withPythonScriptFile(String pythonScriptFilePath) {
        inner().withPythonScriptFilePath(pythonScriptFilePath);
        return this;
    }
}