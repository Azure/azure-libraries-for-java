/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerregistry.EncodedTaskStepUpdateParameters;
import com.microsoft.azure.management.containerregistry.RegistryEncodedTaskStep;
import com.microsoft.azure.management.containerregistry.EncodedTaskStep;
import com.microsoft.azure.management.containerregistry.SetValue;
import com.microsoft.azure.management.containerregistry.Task;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.containerregistry.OverridingValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@LangDefinition
class RegistryEncodedTaskStepImpl
        extends RegistryTaskStepImpl
        implements
        RegistryEncodedTaskStep,
        RegistryEncodedTaskStep.Definition,
        RegistryEncodedTaskStep.Update,
        HasInner<EncodedTaskStep> {

    private EncodedTaskStep inner;
    private EncodedTaskStepUpdateParameters encodedTaskStepUpdateParameters;
    private TaskImpl taskImpl;

    RegistryEncodedTaskStepImpl(TaskImpl taskImpl) {
        super(taskImpl.inner().step());
        this.inner = new EncodedTaskStep();
        if (taskImpl.inner().step() != null && !(taskImpl.inner().step() instanceof EncodedTaskStep)) {
            throw new IllegalArgumentException("Constructor for RegistryEncodedTaskStepImpl invoked for class that is not an EncodedTaskStep");
        }
        this.taskImpl = taskImpl;
        this.encodedTaskStepUpdateParameters = new EncodedTaskStepUpdateParameters();
    }

    @Override
    public String encodedTaskContent() {
        EncodedTaskStep encodedTaskStep = (EncodedTaskStep) this.taskImpl.inner().step();
        return encodedTaskStep.encodedTaskContent();
    }

    @Override
    public String encodedValuesContent() {
        EncodedTaskStep encodedTaskStep = (EncodedTaskStep) this.taskImpl.inner().step();
        return encodedTaskStep.encodedValuesContent();
    }

    @Override
    public List<SetValue> values() {
        EncodedTaskStep encodedTaskStep = (EncodedTaskStep) this.taskImpl.inner().step();
        if (encodedTaskStep.values() == null) {
            return Collections.unmodifiableList(new ArrayList<SetValue>());
        }
        return Collections.unmodifiableList(encodedTaskStep.values());
    }

    @Override
    public RegistryEncodedTaskStepImpl withBase64EncodedTaskContent(String encodedTaskContent) {
        if (isInCreateMode()) {
            this.inner.withBase64EncodedTaskContent(encodedTaskContent);
        } else {
            this.encodedTaskStepUpdateParameters.withEncodedTaskContent(encodedTaskContent);
        }
        return this;
    }

    @Override
    public RegistryEncodedTaskStepImpl withBase64EncodedValueContent(String encodedValueContent) {
        if (isInCreateMode()) {
            this.inner.withBase64EncodedValuesContent(encodedValueContent);
        } else {
            this.encodedTaskStepUpdateParameters.withEncodedValuesContent(encodedValueContent);
        }
        return this;
    }

    @Override
    public RegistryEncodedTaskStepImpl withOverridingValues(Map<String, OverridingValue> overridingValues) {
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
            this.encodedTaskStepUpdateParameters.withValues(overridingValuesList);
        }
        return this;
    }

    @Override
    public RegistryEncodedTaskStepImpl withOverridingValue(String name, OverridingValue overridingValue) {
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
            this.encodedTaskStepUpdateParameters.values().add(value);
        }
        return this;
    }

    @Override
    public Task.DefinitionStages.TaskCreatable attach() {
        this.taskImpl.withEncodedTaskStepCreateParameters(this.inner);
        return this.taskImpl;
    }

    @Override
    public Task.Update parent() {
        this.taskImpl.withEncodedTaskStepUpdateParameters(this.encodedTaskStepUpdateParameters);
        return this.taskImpl;
    }

    @Override
    public EncodedTaskStep inner() {
        return this.inner;
    }

    private boolean isInCreateMode() {
        if (this.taskImpl.inner().id() == null) {
            return true;
        }
        return false;
    }
}
