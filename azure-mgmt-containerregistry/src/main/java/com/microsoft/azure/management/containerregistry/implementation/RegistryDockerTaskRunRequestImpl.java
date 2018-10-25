/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.containerregistry.DockerBuildRequest;
import com.microsoft.azure.management.containerregistry.PlatformProperties;
import com.microsoft.azure.management.containerregistry.RegistryDockerTaskRunRequest;
import com.microsoft.azure.management.containerregistry.RegistryTaskRun;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

import java.util.List;

class RegistryDockerTaskRunRequestImpl implements
        RegistryDockerTaskRunRequest,
        RegistryDockerTaskRunRequest.Definition,
        HasInner<DockerBuildRequest> {

    private DockerBuildRequest inner;
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

    RegistryDockerTaskRunRequestImpl(RegistryTaskRunImpl registryTaskRunImpl) {
        this.inner = new DockerBuildRequest();
        this.registryTaskRunImpl = registryTaskRunImpl;
    }


    @Override
    public DefinitionStages.DockerTaskRunRequestStep defineDockerTaskStep() {
        return this;
    }

    @Override
    public DefinitionStages.DockerTaskRunRequestStepAttachable withDockerFilePath(String path) {
        this.inner.withDockerFilePath(path);
        return this;
    }

    @Override
    public DefinitionStages.DockerTaskRunRequestStepAttachable withImageNames(List<String> imageNames) {
        this.inner.withImageNames(imageNames);
        return this;
    }

    @Override
    public DefinitionStages.DockerTaskRunRequestStepAttachable withPushEnabled() {
        this.inner.withIsPushEnabled(true);
        return this;
    }

    @Override
    public DefinitionStages.DockerTaskRunRequestStepAttachable withPushDisabled() {
        this.inner.withIsPushEnabled(false);
        return this;
    }

    @Override
    public DefinitionStages.DockerTaskRunRequestStepAttachable withCache() {
        this.inner.withNoCache(false);
        return this;
    }

    @Override
    public DefinitionStages.DockerTaskRunRequestStepAttachable withoutCache() {
        this.inner.withNoCache(true);
        return this;
    }

    @Override
    public RegistryTaskRun.DefinitionStages.RunRequestExecutableWithSourceLocation attach() {
        this.registryTaskRunImpl.withDockerTaskRunRequest(this.inner);
        return this.registryTaskRunImpl;
    }

    @Override
    public DockerBuildRequest inner() {
        return this.inner;
    }
}
