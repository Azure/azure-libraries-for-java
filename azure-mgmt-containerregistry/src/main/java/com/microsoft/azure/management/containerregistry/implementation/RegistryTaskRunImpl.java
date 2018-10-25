/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.containerregistry.AgentProperties;
import com.microsoft.azure.management.containerregistry.Architecture;
import com.microsoft.azure.management.containerregistry.DockerBuildRequest;
import com.microsoft.azure.management.containerregistry.EncodedTaskRunRequest;
import com.microsoft.azure.management.containerregistry.FileTaskRunRequest;
import com.microsoft.azure.management.containerregistry.OS;
import com.microsoft.azure.management.containerregistry.OverridingValue;
import com.microsoft.azure.management.containerregistry.PlatformProperties;
import com.microsoft.azure.management.containerregistry.RegistryTaskRun;
import com.microsoft.azure.management.containerregistry.RunRequest;
import com.microsoft.azure.management.containerregistry.SetValue;
import com.microsoft.azure.management.containerregistry.TaskRunRequest;
import com.microsoft.azure.management.containerregistry.Variant;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


class RegistryTaskRunImpl implements
        RegistryTaskRun,
        RegistryTaskRun.Definition {

    private String resourceGroupName;
    private String registryName;
    private RunInner inner;
    private RunRequest runRequest;
    private RegistriesInner registriesInner;
    private FileTaskRunRequest fileTaskRunRequest;
    private EncodedTaskRunRequest encodedTaskRunRequest;
    private DockerBuildRequest dockerTaskRunRequest;
    private TaskRunRequest taskRunRequest;
    private PlatformProperties platform;


    @Override
    public String resourceGroupName() {
        return this.resourceGroupName;
    }

    @Override
    public String registryName() {
        return this.registryName;
    }

    @Override
    public String taskName() {
        return this.inner.task();
    }


    RegistryTaskRunImpl(RegistriesInner registriesInner) {
        this.registriesInner = registriesInner;
        this.platform = new PlatformProperties();
        this.inner = new RunInner();
    }

    @Override
    public RegistryTaskRunImpl withExistingRegistry(String resourceGroupName, String registryName) {
        this.resourceGroupName = resourceGroupName;
        this.registryName = registryName;
        return this;
    }

    @Override
    public RegistryFileTaskRunRequestImpl withFileTaskRunRequest() {
        return new RegistryFileTaskRunRequestImpl(this);
    }

    @Override
    public RegistryEncodedTaskRunRequestImpl withEncodedTaskRunRequest() {
        return new RegistryEncodedTaskRunRequestImpl(this);
    }

    @Override
    public RegistryDockerTaskRunRequestImpl withDockerTaskRunRequest() {
        return new RegistryDockerTaskRunRequestImpl(this);
    }

    @Override
    public RegistryTaskRunImpl withTaskRunRequest(String taskName) {
        this.taskRunRequest = new TaskRunRequest();
        this.taskRunRequest.withTaskName(taskName);
        this.inner.withTask(taskName);
        return this;
    }

    @Override
    public RegistryTaskRunImpl withCpuCount(int count) {
        if (this.fileTaskRunRequest != null) {
            if (this.fileTaskRunRequest.agentConfiguration() == null) {
                this.fileTaskRunRequest.withAgentConfiguration(new AgentProperties());
            }
            this.fileTaskRunRequest.agentConfiguration().withCpu(count);
        } else if (this.encodedTaskRunRequest != null) {
            if (this.encodedTaskRunRequest.agentConfiguration() == null) {
                this.encodedTaskRunRequest.withAgentConfiguration(new AgentProperties());
            }
            this.encodedTaskRunRequest.agentConfiguration().withCpu(count);
        } else if (this.dockerTaskRunRequest != null) {
            if (this.dockerTaskRunRequest.agentConfiguration() == null) {
                this.dockerTaskRunRequest.withAgentConfiguration(new AgentProperties());
            }
            this.dockerTaskRunRequest.agentConfiguration().withCpu(count);
        }

        return this;
    }

    @Override
    public RegistryTaskRunImpl withSourceLocation(String location) {
        if (this.fileTaskRunRequest != null) {
            this.fileTaskRunRequest.withSourceLocation(location);
        } else if (this.encodedTaskRunRequest != null) {
            this.encodedTaskRunRequest.withSourceLocation(location);
        } else if (this.dockerTaskRunRequest != null) {
            this.dockerTaskRunRequest.withSourceLocation(location);
        }
        return this;
    }

    @Override
    public RegistryTaskRunImpl withTimeout(int timeout) {
        if (this.fileTaskRunRequest != null) {
            this.fileTaskRunRequest.withTimeout(timeout);
        } else if (this.encodedTaskRunRequest != null) {
            this.encodedTaskRunRequest.withTimeout(timeout);
        } else if (this.dockerTaskRunRequest != null) {
            this.dockerTaskRunRequest.withTimeout(timeout);
        }
        return this;
    }

    @Override
    public RegistryTaskRunImpl withOverridingValues(Map<String, OverridingValue> overridingValues) {
        List<SetValue> overridingValuesList = new ArrayList<SetValue>();
        for (Map.Entry<String, OverridingValue> entry : overridingValues.entrySet()) {
            SetValue value = new SetValue();
            value.withName(entry.getKey());
            value.withValue(entry.getValue().value());
            value.withIsSecret(entry.getValue().isSecret());
            overridingValuesList.add(value);

        }
        this.taskRunRequest.withValues(overridingValuesList);
        return this;
    }

    @Override
    public RegistryTaskRunImpl withOverridingValue(String name, OverridingValue overridingValue) {
        if (this.taskRunRequest.values() == null) {
            this.taskRunRequest.withValues(new ArrayList<SetValue>());
        }
        SetValue value = new SetValue();
        value.withName(name);
        value.withValue(overridingValue.value());
        value.withIsSecret(overridingValue.isSecret());
        this.taskRunRequest.values().add(value);
        return this;
    }

    @Override
    public RegistryTaskRunImpl withArchiveEnabled() {
        this.inner.withIsArchiveEnabled(true);
        return this;
    }

    @Override
    public RegistryTaskRunImpl withArchiveDisabled() {
        this.inner.withIsArchiveEnabled(false);
        return this;
    }

    @Override
    public RegistryTaskRunImpl withLinux() {
        this.platform.withOs(OS.LINUX);
        return this;
    }

    @Override
    public RegistryTaskRunImpl withWindows() {
        this.platform.withOs(OS.WINDOWS);
        return this;
    }

    @Override
    public RegistryTaskRunImpl withLinux(Architecture architecture) {
        this.platform.withOs(OS.LINUX).withArchitecture(architecture);
        return this;
    }

    @Override
    public RegistryTaskRunImpl withWindows(Architecture architecture) {
        this.platform.withOs(OS.WINDOWS).withArchitecture(architecture);
        return this;
    }

    @Override
    public RegistryTaskRunImpl withLinux(Architecture architecture, Variant variant) {
        this.platform.withOs(OS.LINUX).withArchitecture(architecture).withVariant(variant);
        return this;
    }

    @Override
    public RegistryTaskRunImpl withWindows(Architecture architecture, Variant variant) {
        this.platform.withOs(OS.WINDOWS).withArchitecture(architecture).withVariant(variant);
        return this;
    }

    @Override
    public RegistryTaskRunImpl withPlatform(PlatformProperties platformProperties) {
        this.platform = platformProperties;
        return this;
    }

    @Override
    public RegistryTaskRun execute() {
        return executeAsync().toBlocking().last();
    }

    @Override
    public Observable<RegistryTaskRun> executeAsync() {
        final RegistryTaskRun registryTaskRun = this;
        if (this.fileTaskRunRequest != null) {
            return this.registriesInner.scheduleRunAsync(this.resourceGroupName, this.registryName, this.fileTaskRunRequest).map((runInner) -> registryTaskRun);
        } else if (this.encodedTaskRunRequest != null) {
            return this.registriesInner.scheduleRunAsync(this.resourceGroupName, this.registryName, this.encodedTaskRunRequest).map((runInner) -> registryTaskRun);
        } else if (this.dockerTaskRunRequest != null) {
            return this.registriesInner.scheduleRunAsync(this.resourceGroupName, this.registryName, this.dockerTaskRunRequest).map(new Func1<RunInner, RegistryTaskRun>() {
                @Override
                public RegistryTaskRun call(RunInner runInner) {
                    return registryTaskRun;
                }
            });
        } else if (this.taskRunRequest != null) {
            return this.registriesInner.scheduleRunAsync(this.resourceGroupName, this.registryName, this.taskRunRequest).map(new Func1<RunInner, RegistryTaskRun>() {
                @Override
                public RegistryTaskRun call(RunInner runInner) {
                    return registryTaskRun;
                }
            });
        }
        throw new RuntimeException("Unsupported file task run request");
    }

    @Override
    public ServiceFuture<RegistryTaskRun> executeAsync(ServiceCallback<RegistryTaskRun> callback) {
        return null;
    }

    @Override
    public RunInner inner() {
        return this.inner;
    }

    @Override
    public String key() {
        return null;
    }





    void withFileTaskRunRequest(FileTaskRunRequest fileTaskRunRequest) {
        this.fileTaskRunRequest = fileTaskRunRequest;
        this.fileTaskRunRequest.withPlatform(platform);
    }

    void withEncodedTaskRunRequest(EncodedTaskRunRequest encodedTaskRunRequest) {
        this.encodedTaskRunRequest = encodedTaskRunRequest;
        this.encodedTaskRunRequest.withPlatform(platform);
    }

    void withDockerTaskRunRequest(DockerBuildRequest dockerTaskRunRequest) {
        this.dockerTaskRunRequest = dockerTaskRunRequest;
        this.dockerTaskRunRequest.withPlatform(platform);
    }

}
