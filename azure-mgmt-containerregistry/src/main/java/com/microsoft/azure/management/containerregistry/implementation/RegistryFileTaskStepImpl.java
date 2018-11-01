/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerregistry.FileTaskStep;
import com.microsoft.azure.management.containerregistry.FileTaskStepUpdateParameters;
import com.microsoft.azure.management.containerregistry.RegistryFileTaskStep;
import com.microsoft.azure.management.containerregistry.RegistryTask;
import com.microsoft.azure.management.containerregistry.SetValue;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.containerregistry.OverridingValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@LangDefinition
class RegistryFileTaskStepImpl
        extends RegistryTaskStepImpl
        implements
        RegistryFileTaskStep,
        RegistryFileTaskStep.Definition,
        RegistryFileTaskStep.Update,
        HasInner<FileTaskStep> {

    private FileTaskStep inner;
    private FileTaskStepUpdateParameters fileTaskStepUpdateParameters;
    private RegistryTaskImpl taskImpl;

    RegistryFileTaskStepImpl(RegistryTaskImpl taskImpl) {
        super(taskImpl.inner().step());
        this.inner = new FileTaskStep();
        if (taskImpl.inner().step() != null &&  !(taskImpl.inner().step() instanceof FileTaskStep)) {
            throw new IllegalArgumentException("Constructor for RegistryFileTaskStepImpl invoked for class that is not FileTaskStep");
        }
        this.taskImpl = taskImpl;
        this.fileTaskStepUpdateParameters = new FileTaskStepUpdateParameters();
    }

    @Override
    public String taskFilePath() {
        FileTaskStep fileTaskStep = (FileTaskStep) this.taskImpl.inner().step();
        return fileTaskStep.taskFilePath();
    }

    @Override
    public String valuesFilePath() {
        FileTaskStep fileTaskStep = (FileTaskStep) this.taskImpl.inner().step();
        return fileTaskStep.valuesFilePath();
    }

    @Override
    public List<SetValue> values() {
        FileTaskStep fileTaskStep = (FileTaskStep) this.taskImpl.inner().step();
        if (fileTaskStep.values() == null) {
            return Collections.unmodifiableList(new ArrayList<SetValue>());
        }
        return Collections.unmodifiableList(fileTaskStep.values());
    }

    @Override
    public RegistryFileTaskStepImpl withTaskPath(String path) {
        if (isInCreateMode()) {
            this.inner.withTaskFilePath(path);
        } else {
            this.fileTaskStepUpdateParameters.withTaskFilePath(path);
        }
        return this;
    }

    @Override
    public RegistryFileTaskStepImpl withValuesPath(String path) {
        if (isInCreateMode()) {
            this.inner.withValuesFilePath(path);
        } else {
            this.fileTaskStepUpdateParameters.withValuesFilePath(path);
        }
        return this;
    }

    @Override
    public RegistryFileTaskStepImpl withOverridingValues(Map<String, OverridingValue> overridingValues) {
        if (overridingValues.size() == 0) {
            return this;
        }
        List<SetValue> overridingValuesList = new ArrayList<SetValue>();
        for (Map.Entry<String, OverridingValue> entry : overridingValues.entrySet()) {
            SetValue value = new SetValue();
            value.withName(entry.getKey());
            value.withValue(entry.getValue().value());
            value.withIsSecret(entry.getValue().isSecret());
            overridingValuesList.add(value);

        }
        if (isInCreateMode()) {
            this.inner.withValues(overridingValuesList);
        } else {
            this.fileTaskStepUpdateParameters.withValues(overridingValuesList);
        }
        return this;
    }

    @Override
    public RegistryFileTaskStepImpl withOverridingValue(String name, OverridingValue overridingValue) {
        if (this.inner.values() == null) {
            this.inner.withValues(new ArrayList<SetValue>());
        }
        SetValue value = new SetValue();
        value.withName(name);
        value.withValue(overridingValue.value());
        value.withIsSecret(overridingValue.isSecret());
        if (isInCreateMode()) {
            this.inner.values().add(value);
        } else {
            this.fileTaskStepUpdateParameters.values().add(value);
        }
        return this;
    }

    @Override
    public RegistryTask.DefinitionStages.TaskCreatable attach() {
        this.taskImpl.withFileTaskStepCreateParameters(inner);
        return this.taskImpl;
    }

    @Override
    public RegistryTask.Update parent() {
        this.taskImpl.withFileTaskStepUpdateParameters(fileTaskStepUpdateParameters);
        return this.taskImpl;
    }

    @Override
    public FileTaskStep inner() {
        return this.inner;
    }

    private boolean isInCreateMode() {
        if (this.taskImpl.inner().id() == null) {
            return true;
        }
        return false;
    }
}
