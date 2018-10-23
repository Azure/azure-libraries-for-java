/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.containerregistry.AgentProperties;
import com.microsoft.azure.management.containerregistry.Architecture;
import com.microsoft.azure.management.containerregistry.BaseImageTrigger;
import com.microsoft.azure.management.containerregistry.BaseImageTriggerUpdateParameters;
import com.microsoft.azure.management.containerregistry.DockerBuildStepUpdateParameters;
import com.microsoft.azure.management.containerregistry.DockerTaskStep;
import com.microsoft.azure.management.containerregistry.EncodedTaskStep;
import com.microsoft.azure.management.containerregistry.EncodedTaskStepUpdateParameters;
import com.microsoft.azure.management.containerregistry.FileTaskStep;
import com.microsoft.azure.management.containerregistry.FileTaskStepUpdateParameters;
import com.microsoft.azure.management.containerregistry.OS;
import com.microsoft.azure.management.containerregistry.PlatformProperties;
import com.microsoft.azure.management.containerregistry.PlatformUpdateParameters;
import com.microsoft.azure.management.containerregistry.ProvisioningState;
import com.microsoft.azure.management.containerregistry.RegistryDockerTaskStep;
import com.microsoft.azure.management.containerregistry.RegistryEncodedTaskStep;
import com.microsoft.azure.management.containerregistry.RegistryFileTaskStep;
import com.microsoft.azure.management.containerregistry.RegistryTaskStep;
import com.microsoft.azure.management.containerregistry.SourceTrigger;
import com.microsoft.azure.management.containerregistry.SourceTriggerUpdateParameters;
import com.microsoft.azure.management.containerregistry.Task;
import com.microsoft.azure.management.containerregistry.TaskStatus;
import com.microsoft.azure.management.containerregistry.TaskUpdateParameters;
import com.microsoft.azure.management.containerregistry.TriggerProperties;
import com.microsoft.azure.management.containerregistry.TriggerUpdateParameters;
import com.microsoft.azure.management.containerregistry.Variant;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import org.joda.time.DateTime;
import rx.Observable;
import rx.functions.Func1;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class TaskImpl implements
        Task,
        Task.Definition,
        Task.Update {

    private String resourceGroupName;
    private String registryName;
    private String taskName;
    private TaskInner inner;
    private TasksInner tasksInner;
    private TaskUpdateParameters taskUpdateParameters;
    private RegistryTaskStep registryTaskStep;

    @Override
    public String resourceGroupName() {
        return ResourceUtils.groupFromResourceId(this.id());
    }

    @Override
    public String parentId() {
        return ResourceUtils.parentResourceIdFromResourceId(this.id());
    }

    @Override
    public ProvisioningState provisioningState() {
        return this.inner.provisioningState();
    }

    @Override
    public DateTime creationDate() {
        return this.inner.creationDate();
    }

    @Override
    public TaskStatus status() {
        return this.inner.status();
    }

    @Override
    public RegistryTaskStep registryTaskStep() {
        if (registryTaskStep != null) {
            return registryTaskStep;
        }
        if (this.inner.step() instanceof FileTaskStep) {
            registryTaskStep = new RegistryFileTaskStepImpl(this);
        } else if (this.inner.step() instanceof EncodedTaskStep) {
            registryTaskStep = new RegistryEncodedTaskStepImpl(this);
        } else if (this.inner.step() instanceof DockerTaskStep) {
            registryTaskStep = new RegistryDockerTaskStepImpl(this);
        }
        return registryTaskStep;
    }

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
    public TriggerProperties trigger() {
        return this.inner.trigger();
    }

    TaskImpl(TasksInner tasksInner, String taskName) {
        this.tasksInner = tasksInner;
        this.taskName = taskName;
        this.inner = new TaskInner();
        this.taskUpdateParameters = new TaskUpdateParameters();
    }

    TaskImpl(TasksInner tasksInner, TaskInner inner) {
        this.tasksInner = tasksInner;
        this.taskName = inner.name();
        this.inner = inner;
        this.resourceGroupName = ResourceUtils.groupFromResourceId(this.inner.id());
        this.registryName = ResourceUtils.nameFromResourceId(ResourceUtils.parentResourceIdFromResourceId(this.inner.id()));
        this.taskUpdateParameters = new TaskUpdateParameters();
    }


    @Override
    public DefinitionStages.Location withExistingRegistry(String resourceGroupName, String registryName) {
        this.resourceGroupName = resourceGroupName;
        this.registryName = registryName;
        return this;
    }

    @Override
    public RegistryFileTaskStep.DefinitionStages.Blank defineFileTaskStep() {
        return new RegistryFileTaskStepImpl(this);
    }

    @Override
    public RegistryEncodedTaskStep.DefinitionStages.Blank defineEncodedTaskStep() {
        return new RegistryEncodedTaskStepImpl(this);
    }

    @Override
    public RegistryDockerTaskStep.DefinitionStages.Blank defineDockerTaskStep() {
        return new RegistryDockerTaskStepImpl(this);
    }


    @Override
    public DefinitionStages.Platform withLocation(String location) {
        this.inner.withLocation(location);
        return this;
    }

    @Override
    public TaskImpl withLinux() {
        if (isInCreateMode()) {
            if (this.inner.platform() == null) {
                this.inner.withPlatform(new PlatformProperties());
            }
            this.inner.platform().withOs(OS.LINUX);
            return this;
        } else {
            this.taskUpdateParameters.platform().withOs(OS.LINUX);
            return this;
        }
    }

    @Override
    public TaskImpl withWindows() {
        if (isInCreateMode()) {
            if (this.inner.platform() == null) {
                this.inner.withPlatform(new PlatformProperties());
            }
            this.inner.platform().withOs(OS.WINDOWS);
            return this;
        } else {
            this.taskUpdateParameters.platform().withOs(OS.WINDOWS);
            return this;
        }
    }

    @Override
    public TaskImpl withLinux(Architecture architecture) {
        if (isInCreateMode()) {
            if (this.inner.platform() == null) {
                this.inner.withPlatform(new PlatformProperties());
            }
            this.inner.platform().withOs(OS.LINUX).withArchitecture(architecture);
            return this;
        } else {
            this.taskUpdateParameters.platform().withOs(OS.LINUX).withArchitecture(architecture);
            return this;
        }
    }

    @Override
    public TaskImpl withWindows(Architecture architecture) {
        if (isInCreateMode()) {
            if (this.inner.platform() == null) {
                this.inner.withPlatform(new PlatformProperties());
            }
            this.inner.platform().withOs(OS.WINDOWS).withArchitecture(architecture);
            return this;
        } else {
            this.taskUpdateParameters.platform().withOs(OS.WINDOWS).withArchitecture(architecture);
            return this;
        }
    }

    @Override
    public TaskImpl withLinux(Architecture architecture, Variant variant) {
        if (isInCreateMode()) {
            if (this.inner.platform() == null) {
                this.inner.withPlatform(new PlatformProperties());
            }
            this.inner.platform().withOs(OS.LINUX).withArchitecture(architecture).withVariant(variant);
            return this;
        } else {
            this.taskUpdateParameters.platform().withOs(OS.LINUX).withArchitecture(architecture).withVariant(variant);
            return this;
        }
    }

    @Override
    public TaskImpl withWindows(Architecture architecture, Variant variant) {
        if (isInCreateMode()) {
            if (this.inner.platform() == null) {
                this.inner.withPlatform(new PlatformProperties());
            }
            this.inner.platform().withOs(OS.WINDOWS).withArchitecture(architecture).withVariant(variant);
            return this;
        } else {
            this.taskUpdateParameters.platform().withOs(OS.WINDOWS).withArchitecture(architecture).withVariant(variant);
            return this;
        }
    }

    @Override
    public TaskImpl withPlatform(PlatformProperties platformProperties) {
        this.inner.withPlatform(platformProperties);
        return this;
    }

    @Override
    public TaskImpl withPlatform(PlatformUpdateParameters platformProperties) {
        this.taskUpdateParameters.withPlatform(platformProperties);
        return this;
    }

    @Override
    public TaskImpl withTrigger(List<SourceTrigger> sourceTriggers) {
        if (this.inner.trigger() == null) {
            this.inner.withTrigger(new TriggerProperties());
        }
        this.inner.trigger().withSourceTriggers(sourceTriggers);
        return this;
    }

    @Override
    public TaskImpl withTrigger(ArrayList<SourceTriggerUpdateParameters> sourceTriggers) {
        TriggerUpdateParameters triggerUpdateParameters = new TriggerUpdateParameters();
        triggerUpdateParameters.withSourceTriggers(sourceTriggers);
        this.taskUpdateParameters.withTrigger(triggerUpdateParameters);
        return this;
    }

    @Override
    public TaskImpl withTrigger(BaseImageTrigger baseImageTrigger) {
        if (this.inner.trigger() == null) {
            this.inner.withTrigger(new TriggerProperties());
        }
        this.inner.trigger().withBaseImageTrigger(baseImageTrigger);
        return this;
    }

    @Override
    public TaskImpl withTrigger(BaseImageTriggerUpdateParameters baseImageTrigger) {
        TriggerUpdateParameters triggerUpdateParameters = new TriggerUpdateParameters();
        triggerUpdateParameters.withBaseImageTrigger(baseImageTrigger);
        this.taskUpdateParameters.withTrigger(triggerUpdateParameters);
        return this;
    }

    @Override
    public TaskImpl withTrigger(List<SourceTrigger> sourceTriggers, BaseImageTrigger baseImageTrigger) {
        if (this.inner.trigger() == null) {
            this.inner.withTrigger(new TriggerProperties());
        }
        this.inner.trigger().withSourceTriggers(sourceTriggers).withBaseImageTrigger(baseImageTrigger);
        return this;
    }

    @Override
    public TaskImpl withTrigger(List<SourceTriggerUpdateParameters> sourceTriggers, BaseImageTriggerUpdateParameters baseImageTrigger) {
        TriggerUpdateParameters triggerUpdateParameters = new TriggerUpdateParameters();
        triggerUpdateParameters.withSourceTriggers(sourceTriggers);
        triggerUpdateParameters.withBaseImageTrigger(baseImageTrigger);
        this.taskUpdateParameters.withTrigger(triggerUpdateParameters);
        return this;
    }

    @Override
    public TaskImpl withCpuCount(int count) {
        if (isInCreateMode()) {
            if (this.inner.agentConfiguration() == null) {
                this.inner.withAgentConfiguration(new AgentProperties());
            }
            this.inner.agentConfiguration().withCpu(count);
        } else {
            if (this.taskUpdateParameters.agentConfiguration() == null) {
                this.taskUpdateParameters.withAgentConfiguration(new AgentProperties());
            }
            this.taskUpdateParameters.agentConfiguration().withCpu(count);
        }
        return this;
    }

    @Override
    public TaskImpl withTimeout(int timeout) {
        if (isInCreateMode()) {
            this.inner.withTimeout(timeout);
        } else {
            this.taskUpdateParameters.withTimeout(timeout);
        }
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

        return tasksInner.createAsync(resourceGroupName, registryName, taskName, this.inner).map(new Func1<TaskInner, Indexable>() {
            @Override
            public Indexable call(TaskInner taskInner) {
                inner = taskInner;
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
        return refreshAsync().toBlocking().last();
    }

    @Override
    public Observable<Task> refreshAsync() {
        final Task task = this;
        return this.tasksInner.getAsync(resourceGroupName, registryName, taskName).map(new Func1<TaskInner, Task>() {
            @Override
            public Task call(TaskInner taskInner) {
                inner = taskInner;
                return task;
            }
        });
    }

    @Override
    public Update update() {
        return this;
    }

    @Override
    public Task apply() {
        return applyAsync().toBlocking().last();
    }

    @Override
    public Observable<Task> applyAsync() {
        final Task task = this;
        return tasksInner.updateAsync(resourceGroupName, registryName, taskName, this.taskUpdateParameters).map(new Func1<TaskInner, Task>() {
            @Override
            public Task call(TaskInner taskInner) {
                inner = taskInner;
                taskUpdateParameters = new TaskUpdateParameters();
                registryTaskStep = null;
                return task;
            }
        });
    }

    @Override
    public ServiceFuture<Task> applyAsync(ServiceCallback<Task> callback) {
        return null;
    }

    private boolean isInCreateMode() {
        if (this.inner().id() == null) {
            return true;
        }
        return false;
    }

    void withFileTaskStepCreateParameters(FileTaskStep fileTaskStep) {
        this.inner.withStep(fileTaskStep);
    }

    void withFileTaskStepUpdateParameters(FileTaskStepUpdateParameters fileTaskStepUpdateParameters) {
        this.taskUpdateParameters.withStep(fileTaskStepUpdateParameters);
    }

    void withEncodedTaskStepCreateParameters(EncodedTaskStep encodedTaskStep) {
        this.inner.withStep(encodedTaskStep);
    }

    void withEncodedTaskStepUpdateParameters(EncodedTaskStepUpdateParameters encodedTaskStepUpdateParameters) {
        this.taskUpdateParameters.withStep(encodedTaskStepUpdateParameters);
    }

    void withDockerTaskStepCreateParameters(DockerTaskStep dockerTaskStep) {
        this.inner.withStep(dockerTaskStep);
    }

    void withDockerTaskStepUpdateParameters(DockerBuildStepUpdateParameters dockerTaskStepUpdateParameters) {
        this.taskUpdateParameters.withStep(dockerTaskStepUpdateParameters);
    }

    @Override
    public RegistryFileTaskStep.Update updateFileTaskStep() {
        if (!(this.inner.step() instanceof FileTaskStep)) {
            throw new IllegalArgumentException("Calling updateFileTaskStep on a Task that is not a file task.");
        }
        return new RegistryFileTaskStepImpl(this);
    }

    @Override
    public RegistryEncodedTaskStep.Update updateEncodedTaskStep() {
        if (!(this.inner.step() instanceof EncodedTaskStep)) {
            throw new IllegalArgumentException("Calling updateEncodedTaskStep on a Task that is not an encoded task.");
        }
        return new RegistryEncodedTaskStepImpl(this);
    }

    @Override
    public RegistryDockerTaskStep.Update updateDockerTaskStep() {
        if (!(this.inner.step() instanceof DockerTaskStep)) {
            throw new IllegalArgumentException("Calling updateDockerTaskStep on a Task that is not a Docker task.");
        }
        return new RegistryDockerTaskStepImpl(this);
    }
}
