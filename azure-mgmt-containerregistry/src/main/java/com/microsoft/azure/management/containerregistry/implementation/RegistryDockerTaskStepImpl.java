/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerregistry.Argument;
import com.microsoft.azure.management.containerregistry.DockerBuildStepUpdateParameters;
import com.microsoft.azure.management.containerregistry.DockerTaskStep;
import com.microsoft.azure.management.containerregistry.RegistryDockerTaskStep;
import com.microsoft.azure.management.containerregistry.RegistryTask;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@LangDefinition
class RegistryDockerTaskStepImpl
        extends RegistryTaskStepImpl
        implements
        RegistryDockerTaskStep,
        RegistryDockerTaskStep.Definition,
        RegistryDockerTaskStep.Update,
        HasInner<DockerTaskStep> {

    private DockerTaskStep inner;
    private DockerBuildStepUpdateParameters dockerTaskStepUpdateParameters;
    private RegistryTaskImpl taskImpl;

    RegistryDockerTaskStepImpl(RegistryTaskImpl taskImpl) {
        super(taskImpl.inner().step());
        this.inner = new DockerTaskStep();
        if (taskImpl.inner().step() != null && !(taskImpl.inner().step() instanceof DockerTaskStep)) {
            throw new IllegalArgumentException("Constructor for RegistryDockerTaskStepImpl invoked for class that is not DockerTaskStep");
        }
        this.taskImpl = taskImpl;
        this.dockerTaskStepUpdateParameters = new DockerBuildStepUpdateParameters();
    }

    @Override
    public List<String> imageNames() {
        DockerTaskStep dockerTaskStep = (DockerTaskStep) this.taskImpl.inner().step();
        if (dockerTaskStep.imageNames() == null) {
            return Collections.unmodifiableList(new ArrayList<String>());
        }
        return Collections.unmodifiableList(dockerTaskStep.imageNames());
    }

    @Override
    public boolean isPushEnabled() {
        DockerTaskStep dockerTaskStep = (DockerTaskStep) this.taskImpl.inner().step();
        return Utils.toPrimitiveBoolean(dockerTaskStep.isPushEnabled());
    }

    @Override
    public boolean noCache() {
        DockerTaskStep dockerTaskStep = (DockerTaskStep) this.taskImpl.inner().step();
        return Utils.toPrimitiveBoolean(dockerTaskStep.noCache());
    }

    @Override
    public String dockerFilePath() {
        DockerTaskStep dockerTaskStep = (DockerTaskStep) this.taskImpl.inner().step();
        return dockerTaskStep.dockerFilePath();
    }

    @Override
    public List<Argument> arguments() {
        DockerTaskStep dockerTaskStep = (DockerTaskStep) this.taskImpl.inner().step();
        if (dockerTaskStep.arguments() == null) {
            return Collections.unmodifiableList(new ArrayList<Argument>());
        }
        return Collections.unmodifiableList(dockerTaskStep.arguments());
    }

    @Override
    public RegistryDockerTaskStepImpl withDockerFilePath(String path) {
        if (isInCreateMode()) {
            this.inner.withDockerFilePath(path);
        } else {
            this.dockerTaskStepUpdateParameters.withDockerFilePath(path);
        }
        return this;
    }

    @Override
    public RegistryDockerTaskStepImpl withImageNames(List<String> imageNames) {
        if (isInCreateMode()) {
            this.inner.withImageNames(imageNames);
        } else {
            this.dockerTaskStepUpdateParameters.withImageNames(imageNames);
        }
        return this;
    }

    @Override
    public RegistryDockerTaskStepImpl withPushEnabled() {
        if (isInCreateMode()) {
            this.inner.withIsPushEnabled(true);
        } else {
            this.dockerTaskStepUpdateParameters.withIsPushEnabled(true);
        }
        return this;
    }

    @Override
    public RegistryDockerTaskStepImpl withPushDisabled() {
        if (isInCreateMode()) {
            this.inner.withIsPushEnabled(false);
        } else {
            this.dockerTaskStepUpdateParameters.withIsPushEnabled(false);
        }
        return this;
    }

    @Override
    public RegistryDockerTaskStepImpl withCache() {
        if (isInCreateMode()) {
            this.inner.withNoCache(false);
        } else {
            this.dockerTaskStepUpdateParameters.withNoCache(false);
        }
        return this;
    }

    @Override
    public RegistryDockerTaskStepImpl withoutCache() {
        if (isInCreateMode()) {
            this.inner.withNoCache(true);
        } else {
            this.dockerTaskStepUpdateParameters.withNoCache(true);
        }
        return this;
    }

    @Override
    public RegistryTask.DefinitionStages.TaskCreatable attach() {
        this.taskImpl.withDockerTaskStepCreateParameters(inner);
        return this.taskImpl;
    }

    @Override
    public RegistryTask.Update parent() {
        this.taskImpl.withDockerTaskStepUpdateParameters(dockerTaskStepUpdateParameters);
        return this.taskImpl;
    }

    @Override
    public DockerTaskStep inner() {
        return this.inner;
    }

    private boolean isInCreateMode() {
        if (this.taskImpl.inner().id() == null) {
            return true;
        }
        return false;
    }
}
