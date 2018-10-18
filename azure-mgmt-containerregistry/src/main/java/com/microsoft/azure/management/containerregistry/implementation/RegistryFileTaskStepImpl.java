/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.containerregistry.FileTaskStep;
import com.microsoft.azure.management.containerregistry.RegistryFileTaskStep;
import com.microsoft.azure.management.containerregistry.SetValue;
import com.microsoft.azure.management.containerregistry.Task;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.storage.OverridingValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class RegistryFileTaskStepImpl implements
        RegistryFileTaskStep,
        RegistryFileTaskStep.Definition,
        HasInner<FileTaskStep> {

    private FileTaskStep fileTaskStep;
    private TaskImpl taskImpl;

    RegistryFileTaskStepImpl(TaskImpl taskImpl) {
        this.fileTaskStep = new FileTaskStep();
        this.taskImpl = taskImpl;
    }
    @Override
    public DefinitionStages.FileTaskStepAttachable withTaskPath(String path) {
        fileTaskStep.withTaskFilePath(path);
        return this;
    }

    @Override
    public DefinitionStages.FileTaskStepAttachable withValuesPath(String path) {
        fileTaskStep.withValuesFilePath(path);
        return this;
    }

    @Override
    public DefinitionStages.FileTaskStepAttachable withOverridingValues(Map<String, OverridingValue> overridingValues) {
        List<SetValue> overridingValuesList = new ArrayList<SetValue>();
        for (Map.Entry<String, OverridingValue> entry : overridingValues.entrySet()) {
            SetValue value = new SetValue();
            value.withName(entry.getKey());
            value.withValue(entry.getValue().value());
            value.withIsSecret(entry.getValue().isSecret());
            overridingValuesList.add(value);

        }
        fileTaskStep.withValues(overridingValuesList);
        return this;
    }

    @Override
    public DefinitionStages.FileTaskStepAttachable withOverridingValue(String name, OverridingValue overridingValue) {
        if (fileTaskStep.values() == null) {
            fileTaskStep.withValues(new ArrayList<SetValue>());
        }
        SetValue value = new SetValue();
        value.withName(name);
        value.withValue(overridingValue.value());
        value.withIsSecret(overridingValue.isSecret());
        fileTaskStep.values().add(value);
        return this;
    }

    @Override
    public Task.DefinitionStages.TaskCreatable attach() {
        return this.taskImpl;
    }

    @Override
    public FileTaskStep inner() {
        return fileTaskStep;
    }
}
