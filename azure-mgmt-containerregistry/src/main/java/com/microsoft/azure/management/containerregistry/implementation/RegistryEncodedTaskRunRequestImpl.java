/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.containerregistry.EncodedTaskRunRequest;
import com.microsoft.azure.management.containerregistry.OverridingValue;
import com.microsoft.azure.management.containerregistry.PlatformProperties;
import com.microsoft.azure.management.containerregistry.RegistryEncodedTaskRunRequest;
import com.microsoft.azure.management.containerregistry.RegistryTaskRun;
import com.microsoft.azure.management.containerregistry.SetValue;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class RegistryEncodedTaskRunRequestImpl implements
        RegistryEncodedTaskRunRequest,
        RegistryEncodedTaskRunRequest.Definition,
        HasInner<EncodedTaskRunRequest> {

    private EncodedTaskRunRequest inner;
    private RegistryTaskRunImpl registryTaskRunImpl;


    @Override
    public int timeout() {
        return this.inner.timeout();
    }

    @Override
    public PlatformProperties platform() {
        return this.inner.platform();
    }

    @Override
    public int cpuCount() {
        return this.inner.agentConfiguration().cpu();
    }

    @Override
    public String sourceLocation() {
        return this.inner.sourceLocation();
    }

    @Override
    public boolean isArchiveEnabled() {
        return this.inner.isArchiveEnabled();
    }

    RegistryEncodedTaskRunRequestImpl(RegistryTaskRunImpl registryTaskRunImpl) {
        this.inner = new EncodedTaskRunRequest();
        this.registryTaskRunImpl = registryTaskRunImpl;
    }

    @Override
    public DefinitionStages.EncodedTaskRunRequestStep defineEncodedTaskStep() {
        return this;
    }

    @Override
    public DefinitionStages.EncodedTaskRunRequestStepAttachable withBase64EncodedTaskContent(String encodedTaskContent) {
        this.inner.withEncodedTaskContent(encodedTaskContent);
        return this;
    }

    @Override
    public DefinitionStages.EncodedTaskRunRequestStepAttachable withBase64EncodedValueContent(String encodedValueContent) {
        this.inner.withEncodedValuesContent(encodedValueContent);
        return this;
    }

    @Override
    public DefinitionStages.EncodedTaskRunRequestStepAttachable withOverridingValues(Map<String, OverridingValue> overridingValues) {
        List<SetValue> overridingValuesList = new ArrayList<SetValue>();
        for (Map.Entry<String, OverridingValue> entry : overridingValues.entrySet()) {
            SetValue value = new SetValue();
            value.withName(entry.getKey());
            value.withValue(entry.getValue().value());
            value.withIsSecret(entry.getValue().isSecret());
            overridingValuesList.add(value);

        }
        this.inner.withValues(overridingValuesList);
        return this;
    }

    @Override
    public DefinitionStages.EncodedTaskRunRequestStepAttachable withOverridingValue(String name, OverridingValue overridingValue) {
        if (this.inner.values() == null) {
            this.inner.withValues(new ArrayList<SetValue>());
        }
        SetValue value = new SetValue();
        value.withName(name);
        value.withValue(overridingValue.value());
        value.withIsSecret(overridingValue.isSecret());
        this.inner.values().add(value);
        return this;
    }

    @Override
    public RegistryTaskRun.DefinitionStages.RunRequestExecutableWithSourceLocation attach() {
        this.registryTaskRunImpl.withEncodedTaskRunRequest(this.inner);
        return this.registryTaskRunImpl;
    }

    @Override
    public EncodedTaskRunRequest inner() {
        return this.inner;
    }

}
