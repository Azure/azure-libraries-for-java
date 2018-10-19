/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.containerregistry.RegistryEncodedTaskStep;
import com.microsoft.azure.management.containerregistry.EncodedTaskStep;
import com.microsoft.azure.management.containerregistry.SetValue;
import com.microsoft.azure.management.containerregistry.Task;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.containerregistry.OverridingValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class RegistryEncodedTaskStepImpl implements
        RegistryEncodedTaskStep,
        RegistryEncodedTaskStep.Definition,
        HasInner<EncodedTaskStep> {

    private EncodedTaskStep encodedTaskStep;
    private TaskImpl taskImpl;

    RegistryEncodedTaskStepImpl(TaskImpl taskImpl) {
        this.encodedTaskStep = new EncodedTaskStep();
        this.taskImpl = taskImpl;
    }

    @Override
    public DefinitionStages.EncodedTaskStepAttachable withBase64EncodedTaskContent(String encodedTaskContent) {
        encodedTaskStep.withBase64EncodedTaskContent(encodedTaskContent);
        return this;
    }

    @Override
    public DefinitionStages.EncodedTaskStepAttachable withBase64EncodedValueContent(String encodedValueContent) {
        encodedTaskStep.withBase64EncodedValuesContent(encodedValueContent);
        return this;
    }

    @Override
    public DefinitionStages.EncodedTaskStepAttachable withOverridingValues(Map<String, OverridingValue> overridingValues) {
        List<SetValue> overridingValuesList = new ArrayList<SetValue>();
        for (Map.Entry<String, OverridingValue> entry : overridingValues.entrySet()) {
            SetValue value = new SetValue();
            value.withName(entry.getKey());
            value.withValue(entry.getValue().value());
            value.withIsSecret(entry.getValue().isSecret());
            overridingValuesList.add(value);

        }
        encodedTaskStep.withValues(overridingValuesList);
        return this;
    }

    @Override
    public DefinitionStages.EncodedTaskStepAttachable withOverridingValue(String name, OverridingValue overridingValue) {
        if (encodedTaskStep.values() == null) {
            encodedTaskStep.withValues(new ArrayList<SetValue>());
        }
        SetValue value = new SetValue();
        value.withName(name);
        value.withValue(overridingValue.value());
        value.withIsSecret(overridingValue.isSecret());
        encodedTaskStep.values().add(value);
        return this;
    }

    @Override
    public Task.DefinitionStages.TaskCreatable attach() {
        return this.taskImpl;
    }

    @Override
    public EncodedTaskStep inner() {
        return encodedTaskStep;
    }
}
