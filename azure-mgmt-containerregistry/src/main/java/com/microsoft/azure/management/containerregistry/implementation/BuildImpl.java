/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry.implementation;

import java.util.List;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerregistry.Build;
import com.microsoft.azure.management.containerregistry.BuildGetLogResult;
import com.microsoft.azure.management.containerregistry.BuildStatus;
import com.microsoft.azure.management.containerregistry.BuildType;
import com.microsoft.azure.management.containerregistry.GitCommitTrigger;
import com.microsoft.azure.management.containerregistry.ImageDescriptor;
import com.microsoft.azure.management.containerregistry.ImageUpdateTrigger;
import com.microsoft.azure.management.containerregistry.PlatformProperties;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

import org.joda.time.DateTime;

import rx.Observable;
import rx.functions.Func1;

/**
 * Implementation for Build.
 */
@LangDefinition
public class BuildImpl
    extends WrapperImpl<BuildInner>
    implements Build {

    private ContainerRegistryManager containerRegistryManager;
    private String resourceGroupName;
    private String registryName;

    /**
     * Creates an instance of the build object.
     *
     * @param inner the inner object
     */
    BuildImpl(BuildInner inner, ContainerRegistryManager containerRegistryManager, String resourceGroupName, String registryName) {
        super(inner);

        this.containerRegistryManager = containerRegistryManager;
        this.resourceGroupName = resourceGroupName;
        this.registryName = registryName;
    }

    @Override
    public String buildId() {
        return inner().buildId();
    }

    @Override
    public Build refresh() {
        BuildInner buildInner = this.containerRegistryManager.inner().builds().get(this.resourceGroupName, this.registryName, this.buildId());
        this.setInner(buildInner);

        return this;
    }

    @Override
    public Observable<Build> refreshAsync() {
        final BuildImpl self = this;
        return this.containerRegistryManager.inner().builds().getAsync(this.resourceGroupName, this.registryName, this.buildId()).map(new Func1<BuildInner, Build>() {
            @Override
            public Build call(BuildInner buildInner) {
                self.setInner(buildInner);
                return self;
            }
        });
    }

    @Override
    public BuildStatus status() {    
        return inner().status();
    }

    @Override
    public DateTime lastUpdatedTime() {
        return inner().lastUpdatedTime();
    }

    @Override
    public BuildType buildType() {
        return inner().buildType();
    }

    @Override
    public DateTime createTime() {
        return inner().createTime();
    }

    @Override
    public DateTime startTime() {
        return inner().startTime();
    }

    @Override
    public DateTime finishTime() {
        return inner().finishTime();
    }

    @Override
    public List<ImageDescriptor> outputImages() {
        return inner().outputImages();
    }

    @Override
    public String buildTask() {
        return inner().buildTask();
    }

    @Override
    public ImageUpdateTrigger imageUpdateTrigger() {
        return inner().imageUpdateTrigger();
    }

    @Override
    public GitCommitTrigger gitCommitTrigger() {
        return inner().gitCommitTrigger();
    }

    @Override
    public Boolean isArchiveEnabled() {
        return inner().isArchiveEnabled();
    }

    @Override
    public PlatformProperties platform() {
        return inner().platform();
    }

    @Override
    public BuildGetLogResult getLogLink() {
        BuildGetLogResultInner logResultInner = this.containerRegistryManager.inner().builds().getLogLink(this.resourceGroupName, this.registryName, this.buildId());

        return new BuildGetLogResultImpl(logResultInner);
    }

    @Override
    public Observable<BuildGetLogResult> getLogLinkAsync() {
        return this.containerRegistryManager.inner().builds().getLogLinkAsync(this.resourceGroupName, this.registryName, this.buildId())
            .map(new Func1<BuildGetLogResultInner, BuildGetLogResult>() {
                @Override
                public BuildGetLogResult call(BuildGetLogResultInner logResultInner) {
                    return new BuildGetLogResultImpl(logResultInner);
                }
            });
    }
}
