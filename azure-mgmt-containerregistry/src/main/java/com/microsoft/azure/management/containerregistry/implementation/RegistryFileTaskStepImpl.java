/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.containerregistry.FileTaskStep;
import com.microsoft.azure.management.containerregistry.FileTaskStepUpdateParameters;
import com.microsoft.azure.management.containerregistry.RegistryFileTaskStep;
import com.microsoft.azure.management.containerregistry.SetValue;
import com.microsoft.azure.management.containerregistry.Task;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.containerregistry.OverridingValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class RegistryFileTaskStepImpl
        extends RegistryTaskStepImpl
        implements
        RegistryFileTaskStep,
        RegistryFileTaskStep.Definition,
        RegistryFileTaskStep.Update,
        HasInner<FileTaskStep> {

    private FileTaskStep fileTaskStep;
    private FileTaskStepUpdateParameters fileTaskStepUpdateParameters;
    private TaskImpl taskImpl;

    RegistryFileTaskStepImpl(TaskImpl taskImpl) {
        super(taskImpl.inner().step());
        this.fileTaskStep = new FileTaskStep();
        this.taskImpl = taskImpl;
        this.fileTaskStepUpdateParameters = new FileTaskStepUpdateParameters();
    }

    @Override
    public String taskFilePath() {
        FileTaskStep fileTaskStep = (FileTaskStep) taskImpl.inner().step();
        return fileTaskStep.taskFilePath();
    }

    @Override
    public String valuesFilePath() {
        FileTaskStep fileTaskStep = (FileTaskStep) taskImpl.inner().step();
        return fileTaskStep.valuesFilePath();
    }

    @Override
    public List<SetValue> values() {
        FileTaskStep fileTaskStep = (FileTaskStep) taskImpl.inner().step();
        return fileTaskStep.values();
    }

    @Override
    public RegistryFileTaskStepImpl withTaskPath(String path) {
        if (isInCreateMode()) {
            fileTaskStep.withTaskFilePath(path);
        } else {
            fileTaskStepUpdateParameters.withTaskFilePath(path);
        }
        return this;
    }

    @Override
    public RegistryFileTaskStepImpl withValuesPath(String path) {
        if (isInCreateMode()) {
            fileTaskStep.withValuesFilePath(path);
        } else {
            fileTaskStepUpdateParameters.withValuesFilePath(path);
        }
        return this;
    }

    @Override
    public RegistryFileTaskStepImpl withOverridingValues(Map<String, OverridingValue> overridingValues) {
        List<SetValue> overridingValuesList = new ArrayList<SetValue>();
        for (Map.Entry<String, OverridingValue> entry : overridingValues.entrySet()) {
            SetValue value = new SetValue();
            value.withName(entry.getKey());
            value.withValue(entry.getValue().value());
            value.withIsSecret(entry.getValue().isSecret());
            overridingValuesList.add(value);

        }
        if (isInCreateMode()) {
            fileTaskStep.withValues(overridingValuesList);
        } else {
            fileTaskStepUpdateParameters.withValues(overridingValuesList);
        }
        return this;
    }

    @Override
    public RegistryFileTaskStepImpl withOverridingValue(String name, OverridingValue overridingValue) {
        if (fileTaskStep.values() == null) {
            fileTaskStep.withValues(new ArrayList<SetValue>());
        }
        SetValue value = new SetValue();
        value.withName(name);
        value.withValue(overridingValue.value());
        value.withIsSecret(overridingValue.isSecret());
        if (isInCreateMode()) {
            fileTaskStep.values().add(value);
        } else {
            fileTaskStepUpdateParameters.values().add(value);
        }
        return this;
    }

    @Override
    public Task.DefinitionStages.TaskCreatable attach() {
        this.taskImpl.withFileTaskStepCreateParameters(fileTaskStep);
        return this.taskImpl;
    }

    @Override
    public FileTaskStep inner() {
        return fileTaskStep;
    }

    @Override
    public Task.Update parent() {
        this.taskImpl.withFileTaskStepUpdateParameters(fileTaskStepUpdateParameters);
        return this.taskImpl;
    }

    private boolean isInCreateMode() {
        if (this.taskImpl.inner().id() == null) {
            return true;
        }
        return false;
    }


}
