/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.containerregistry.EncodedTaskStepUpdateParameters;
import com.microsoft.azure.management.containerregistry.RegistryEncodedTaskStep;
import com.microsoft.azure.management.containerregistry.EncodedTaskStep;
import com.microsoft.azure.management.containerregistry.SetValue;
import com.microsoft.azure.management.containerregistry.Task;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.containerregistry.OverridingValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class RegistryEncodedTaskStepImpl
        extends RegistryTaskStepImpl
        implements
        RegistryEncodedTaskStep,
        RegistryEncodedTaskStep.Definition,
        RegistryEncodedTaskStep.Update,
        HasInner<EncodedTaskStep> {

    private EncodedTaskStep encodedTaskStep;
    private EncodedTaskStepUpdateParameters encodedTaskStepUpdateParameters;
    private TaskImpl taskImpl;

    RegistryEncodedTaskStepImpl(TaskImpl taskImpl) {
        super(taskImpl.inner().step());
        this.encodedTaskStep = new EncodedTaskStep();
        this.taskImpl = taskImpl;
        this.encodedTaskStepUpdateParameters = new EncodedTaskStepUpdateParameters();
    }

    @Override
    public String encodedTaskContent() {
        EncodedTaskStep encodedTaskStep = (EncodedTaskStep) taskImpl.inner().step();
        return encodedTaskStep.encodedTaskContent();
    }

    @Override
    public String encodedValuesContent() {
        EncodedTaskStep encodedTaskStep = (EncodedTaskStep) taskImpl.inner().step();
        return encodedTaskStep.encodedValuesContent();
    }

    @Override
    public List<SetValue> values() {
        EncodedTaskStep encodedTaskStep = (EncodedTaskStep) taskImpl.inner().step();
        return encodedTaskStep.values();
    }

    @Override
    public RegistryEncodedTaskStepImpl withBase64EncodedTaskContent(String encodedTaskContent) {
        if (isInCreateMode()) {
            encodedTaskStep.withBase64EncodedTaskContent(encodedTaskContent);
        } else {
            encodedTaskStepUpdateParameters.withEncodedTaskContent(encodedTaskContent);
        }
        return this;
    }

    @Override
    public RegistryEncodedTaskStepImpl withBase64EncodedValueContent(String encodedValueContent) {
        if (isInCreateMode()) {
            encodedTaskStep.withBase64EncodedValuesContent(encodedValueContent);
        } else {
            encodedTaskStepUpdateParameters.withEncodedValuesContent(encodedValueContent);
        }
        return this;
    }

    @Override
    public RegistryEncodedTaskStepImpl withOverridingValues(Map<String, OverridingValue> overridingValues) {
        List<SetValue> overridingValuesList = new ArrayList<SetValue>();
        for (Map.Entry<String, OverridingValue> entry : overridingValues.entrySet()) {
            SetValue value = new SetValue();
            value.withName(entry.getKey());
            value.withValue(entry.getValue().value());
            value.withIsSecret(entry.getValue().isSecret());
            overridingValuesList.add(value);

        }
        if (isInCreateMode()) {
            encodedTaskStep.withValues(overridingValuesList);
        } else {
            encodedTaskStepUpdateParameters.withValues(overridingValuesList);
        }
        return this;
    }

    @Override
    public RegistryEncodedTaskStepImpl withOverridingValue(String name, OverridingValue overridingValue) {
        if (encodedTaskStep.values() == null) {
            encodedTaskStep.withValues(new ArrayList<SetValue>());
        }
        SetValue value = new SetValue();
        value.withName(name);
        value.withValue(overridingValue.value());
        value.withIsSecret(overridingValue.isSecret());
        if (isInCreateMode()) {
            encodedTaskStep.values().add(value);
        } else {
            encodedTaskStepUpdateParameters.values().add(value);
        }
        return this;
    }

    @Override
    public Task.DefinitionStages.TaskCreatable attach() {
        this.taskImpl.withEncodedTaskStepCreateParameters(encodedTaskStep);
        return this.taskImpl;
    }

    @Override
    public EncodedTaskStep inner() {
        return encodedTaskStep;
    }

    @Override
    public Task.Update parent() {
        this.taskImpl.withEncodedTaskStepUpdateParameters(encodedTaskStepUpdateParameters);
        return this.taskImpl;
    }

    private boolean isInCreateMode() {
        if (this.taskImpl.inner().id() == null) {
            return true;
        }
        return false;
    }


}
