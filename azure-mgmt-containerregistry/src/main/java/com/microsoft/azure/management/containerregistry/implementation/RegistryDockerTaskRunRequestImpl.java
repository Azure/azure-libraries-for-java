/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerregistry.DockerBuildRequest;
import com.microsoft.azure.management.containerregistry.PlatformProperties;
import com.microsoft.azure.management.containerregistry.RegistryDockerTaskRunRequest;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;

import java.util.List;

@LangDefinition
class RegistryDockerTaskRunRequestImpl implements
        RegistryDockerTaskRunRequest,
        RegistryDockerTaskRunRequest.Definition,
        HasInner<DockerBuildRequest> {

    private DockerBuildRequest inner;
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

    RegistryDockerTaskRunRequestImpl(RegistryTaskRunImpl registryTaskRunImpl) {
        this.inner = new DockerBuildRequest();
        this.registryTaskRunImpl = registryTaskRunImpl;
    }

    @Override
    public RegistryDockerTaskRunRequestImpl defineDockerTaskStep() {
        return this;
    }

    @Override
    public RegistryDockerTaskRunRequestImpl withDockerFilePath(String path) {
        this.inner.withDockerFilePath(path);
        return this;
    }

    @Override
    public RegistryDockerTaskRunRequestImpl withImageNames(List<String> imageNames) {
        this.inner.withImageNames(imageNames);
        return this;
    }

    @Override
    public RegistryDockerTaskRunRequestImpl withPushEnabled() {
        this.inner.withIsPushEnabled(true);
        return this;
    }

    @Override
    public RegistryDockerTaskRunRequestImpl withPushDisabled() {
        this.inner.withIsPushEnabled(false);
        return this;
    }

    @Override
    public RegistryDockerTaskRunRequestImpl withCache() {
        this.inner.withNoCache(false);
        return this;
    }

    @Override
    public RegistryDockerTaskRunRequestImpl withoutCache() {
        this.inner.withNoCache(true);
        return this;
    }

    @Override
    public RegistryTaskRunImpl attach() {
        this.registryTaskRunImpl.withDockerTaskRunRequest(this.inner);
        return this.registryTaskRunImpl;
    }

    @Override
    public DockerBuildRequest inner() {
        return this.inner;
    }
}
