/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.containerregistry.AgentProperties;
import com.microsoft.azure.management.containerregistry.Architecture;
import com.microsoft.azure.management.containerregistry.BaseImageTrigger;
import com.microsoft.azure.management.containerregistry.OS;
import com.microsoft.azure.management.containerregistry.PlatformProperties;
import com.microsoft.azure.management.containerregistry.ProvisioningState;
import com.microsoft.azure.management.containerregistry.RegistryDockerTaskStep;
import com.microsoft.azure.management.containerregistry.RegistryEncodedTaskStep;
import com.microsoft.azure.management.containerregistry.RegistryFileTaskStep;
import com.microsoft.azure.management.containerregistry.SourceTrigger;
import com.microsoft.azure.management.containerregistry.Task;
import com.microsoft.azure.management.containerregistry.TaskStatus;
import com.microsoft.azure.management.containerregistry.TriggerProperties;
import com.microsoft.azure.management.containerregistry.Variant;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import org.joda.time.DateTime;
import rx.Observable;
import rx.functions.Func1;


import java.util.List;
import java.util.Map;

class TaskImpl implements
        Task,
        Task.Definition {

    private String resourceGroupName;
    private String registryName;
    private String taskName;
    private TaskInner inner;
    private TasksInner tasksInner;
    private RegistryFileTaskStepImpl fileTaskStep;
    private RegistryEncodedTaskStepImpl encodedTaskStep;
    private RegistryDockerTaskStepImpl dockerTaskStep;

    @Override
    public String parentId() {
        return null;
    }

    @Override
    public ProvisioningState provisioningState() {
        return null;
    }

    @Override
    public DateTime creationDate() {
        return null;
    }

    @Override
    public TaskStatus status() {
        return null;
    }

    @Override
    public int timeout() {
        return 0;
    }

    TaskImpl(TasksInner tasksInner, String taskName, TaskInner inner) {
        this.tasksInner = tasksInner;
        this.taskName = taskName;
        this.inner = inner;
    }

    @Override
    public DefinitionStages.Location withExistingRegistry(String resourceGroupName, String registryName) {
        this.resourceGroupName = resourceGroupName;
        this.registryName = registryName;
        return this;
    }

    @Override
    public RegistryFileTaskStep.DefinitionStages.Blank defineFileTaskStep() {
        this.fileTaskStep = new RegistryFileTaskStepImpl(this);
        return this.fileTaskStep;
    }

    @Override
    public RegistryEncodedTaskStep.DefinitionStages.Blank defineEncodedTaskStep() {
        this.encodedTaskStep = new RegistryEncodedTaskStepImpl(this);
        return this.encodedTaskStep;
    }

    @Override
    public RegistryDockerTaskStep.DefinitionStages.Blank defineDockerTaskStep() {
        this.dockerTaskStep = new RegistryDockerTaskStepImpl(this);
        return this.dockerTaskStep;
    }


    @Override
    public DefinitionStages.Platform withLocation(String location) {
        this.inner.withLocation(location);
        return this;
    }

    @Override
    public DefinitionStages.TaskType withLinux() {
        if (this.inner.platform() == null) {
            this.inner.withPlatform(new PlatformProperties());
        }
        this.inner.platform().withOs(OS.LINUX);
        return this;
    }

    @Override
    public DefinitionStages.TaskType withWindows() {
        if (this.inner.platform() == null) {
            this.inner.withPlatform(new PlatformProperties());
        }
        this.inner.platform().withOs(OS.WINDOWS);
        return this;
    }

    @Override
    public DefinitionStages.TaskType withLinux(Architecture architecture) {
        if (this.inner.platform() == null) {
            this.inner.withPlatform(new PlatformProperties());
        }
        this.inner.platform().withOs(OS.LINUX).withArchitecture(architecture);
        return this;
    }

    @Override
    public DefinitionStages.TaskType withWindows(Architecture architecture) {
        if (this.inner.platform() == null) {
            this.inner.withPlatform(new PlatformProperties());
        }
        this.inner.platform().withOs(OS.WINDOWS).withArchitecture(architecture);
        return this;
    }

    @Override
    public DefinitionStages.TaskType withLinux(Architecture architecture, Variant variant) {
        if (this.inner.platform() == null) {
            this.inner.withPlatform(new PlatformProperties());
        }
        this.inner.platform().withOs(OS.LINUX).withArchitecture(architecture).withVariant(variant);
        return this;
    }

    @Override
    public DefinitionStages.TaskType withWindows(Architecture architecture, Variant variant) {
        if (this.inner.platform() == null) {
            this.inner.withPlatform(new PlatformProperties());
        }
        this.inner.platform().withOs(OS.WINDOWS).withArchitecture(architecture).withVariant(variant);
        return this;
    }

    @Override
    public DefinitionStages.TaskType withPlatform(PlatformProperties platformProperties) {
        this.inner.withPlatform(platformProperties);
        return this;
    }

    @Override
    public DefinitionStages.TaskCreatable withTrigger(List<SourceTrigger> sourceTriggers) {
        if (this.inner.trigger() == null) {
            this.inner.withTrigger(new TriggerProperties());
        }
        this.inner.trigger().withSourceTriggers(sourceTriggers);
        return this;
    }

    @Override
    public DefinitionStages.TaskCreatable withTrigger(BaseImageTrigger baseImageTrigger) {
        if (this.inner.trigger() == null) {
            this.inner.withTrigger(new TriggerProperties());
        }
        this.inner.trigger().withBaseImageTrigger(baseImageTrigger);
        return this;
    }

    @Override
    public DefinitionStages.TaskCreatable withTrigger(List<SourceTrigger> sourceTriggers, BaseImageTrigger baseImageTrigger) {
        if (this.inner.trigger() == null) {
            this.inner.withTrigger(new TriggerProperties());
        }
        this.inner.trigger().withSourceTriggers(sourceTriggers).withBaseImageTrigger(baseImageTrigger);
        return this;
    }

    @Override
    public DefinitionStages.TaskCreatable withCpuCount(int count) {
        if (this.inner.agentConfiguration() == null) {
            this.inner.withAgentConfiguration(new AgentProperties());
        }
        this.inner.agentConfiguration().withCpu(count);
        return this;
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public String type() {
        return this.inner().type();
    }

    @Override
    public String regionName() {
        return this.inner().location();
    }

    @Override
    public Region region() {
        return Region.fromName(this.regionName());
    }

    @Override
    public Map<String, String> tags() {
        return this.inner().getTags();
    }

    @Override
    public Task create() {
        return (Task) createAsync().toBlocking().last();
    }

    @Override
    public ServiceFuture<Task> createAsync(ServiceCallback<Task> callback) {
        return null;
    }

    @Override
    public Observable<Indexable> createAsync() {
        final Task task = this;
        if (this.fileTaskStep != null) {
            this.inner.withStep(this.fileTaskStep.inner());
        } else if (this.encodedTaskStep != null) {
            this.inner.withStep(this.encodedTaskStep.inner());
        } else if (this.dockerTaskStep != null) {
            this.inner.withStep(this.dockerTaskStep.inner());
        }
        return tasksInner.createAsync(resourceGroupName, registryName, taskName, this.inner).map(new Func1<TaskInner, Indexable>() {
            @Override
            public Indexable call(TaskInner taskInner) {
                return task;
            }
        });
    }

    @Override
    public TaskInner inner() {
        return this.inner;
    }

    @Override
    public String key() {
        return null;
    }

    @Override
    public Task refresh() {
        return null;
    }

    @Override
    public Observable<Task> refreshAsync() {
        return null;
    }

}
