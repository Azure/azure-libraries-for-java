/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.azure.management.containerregistry.FileTaskRunRequest;
import com.azure.management.containerregistry.OverridingValue;
import com.azure.management.containerregistry.PlatformProperties;
import com.azure.management.containerregistry.RegistryFileTaskRunRequest;
import com.azure.management.containerregistry.SetValue;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class RegistryFileTaskRunRequestImpl implements
        RegistryFileTaskRunRequest,
        RegistryFileTaskRunRequest.Definition,
        HasInner<FileTaskRunRequest> {

    private FileTaskRunRequest inner;
    private RegistryTaskRunImpl registryTaskRunImpl;


    @Override
    public int timeout() {
        return Utils.toPrimitiveInt(this.inner.timeout());
    }

    @Override
    public PlatformProperties platform() {
        return this.inner.platform();
    }

    @Override
    public int cpuCount() {
        if (this.inner.agentConfiguration() == null) {
            return 0;
        }
        return Utils.toPrimitiveInt(this.inner.agentConfiguration().cpu());
    }

    @Override
    public String sourceLocation() {
        return this.inner.sourceLocation();
    }

    @Override
    public boolean isArchiveEnabled() {
        return Utils.toPrimitiveBoolean(this.inner.isArchiveEnabled());
    }

    RegistryFileTaskRunRequestImpl(RegistryTaskRunImpl registryTaskRunImpl) {
        this.inner = new FileTaskRunRequest();
        this.registryTaskRunImpl = registryTaskRunImpl;
    }

    @Override
    public RegistryFileTaskRunRequestImpl defineFileTaskStep() {
        return this;
    }

    @Override
    public RegistryFileTaskRunRequestImpl withTaskPath(String taskPath) {
        this.inner.withTaskFilePath(taskPath);
        return this;
    }

    @Override
    public RegistryFileTaskRunRequestImpl withValuesPath(String valuesPath) {
        this.inner.withValuesFilePath(valuesPath);
        return this;
    }

    @Override
    public RegistryFileTaskRunRequestImpl withOverridingValues(Map<String, OverridingValue> overridingValues) {
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
        this.inner.withValues(overridingValuesList);
        return this;
    }

    @Override
    public RegistryFileTaskRunRequestImpl withOverridingValue(String name, OverridingValue overridingValue) {
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
    public RegistryTaskRunImpl attach() {
        this.registryTaskRunImpl.withFileTaskRunRequest(this.inner);
        return this.registryTaskRunImpl;
    }

    @Override
    public FileTaskRunRequest inner() {
        return this.inner;
    }
}
