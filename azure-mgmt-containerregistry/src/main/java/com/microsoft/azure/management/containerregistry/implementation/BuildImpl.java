/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerregistry.Build;
import com.microsoft.azure.management.containerregistry.BuildStatus;
import com.microsoft.azure.management.containerregistry.BuildType;
import com.microsoft.azure.management.containerregistry.GitCommitTrigger;
import com.microsoft.azure.management.containerregistry.ImageDescriptor;
import com.microsoft.azure.management.containerregistry.ImageUpdateTrigger;
import com.microsoft.azure.management.containerregistry.OsType;
import com.microsoft.azure.management.containerregistry.PlatformProperties;
import com.microsoft.azure.management.containerregistry.ProvisioningState;
import com.microsoft.azure.management.containerregistry.QuickBuildRequest;
import com.microsoft.azure.management.containerregistry.Registry;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
import org.joda.time.DateTime;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Implementation for Build.
 */
@LangDefinition
public class BuildImpl
    extends ExternalChildResourceImpl<Build, BuildInner, RegistryImpl, Registry>
    implements
        Build,
        Build.QueuedQuickBuildDefinition,
        Build.Update {

    private QuickBuildRequest quickBuildRequest;

    protected BuildImpl(String name, RegistryImpl parent, BuildInner innerObject) {
        super(name, parent, innerObject);
    }

    /**
     * @return the QuickBuildRequest internal object
     */
    public QuickBuildRequest quickBuildRequest() {
        if (this.quickBuildRequest == null) {
            this.quickBuildRequest = new QuickBuildRequest();
        }
        return this.quickBuildRequest;
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public String buildId() {
        return this.inner().buildId();
    }

    @Override
    public String name() {
        return this.inner().buildId();
    }

    @Override
    public BuildStatus status() {
        return this.inner().status();
    }

    @Override
    public DateTime lastUpdatedTime() {
        return this.inner().lastUpdatedTime();
    }

    @Override
    public BuildType buildType() {
        return this.inner().buildType();
    }

    @Override
    public DateTime createTime() {
        return this.inner().createTime();
    }

    @Override
    public DateTime startTime() {
        return this.inner().startTime();
    }

    @Override
    public DateTime finishTime() {
        return this.inner().finishTime();
    }

    @Override
    public List<ImageDescriptor> outputImages() {
        List<ImageDescriptor> result = this.inner().outputImages();
        if (result == null) {
            result = new ArrayList<>();
        }
        return Collections.unmodifiableList(result);
    }

    @Override
    public String buildTask() {
        return this.inner().buildTask();
    }

    @Override
    public ImageUpdateTrigger imageUpdateTrigger() {
        return this.inner().imageUpdateTrigger();
    }

    @Override
    public GitCommitTrigger gitCommitTrigger() {
        return this.inner().gitCommitTrigger();
    }

    @Override
    public boolean isArchiveEnabled() {
        return this.inner().isArchiveEnabled();
    }

    @Override
    public OsType osType() {
        if (this.inner().platform() != null) {
            return this.inner().platform().osType();
        } else {
            return null;
        }
    }

    @Override
    public int cpuCount() {
        if (this.inner().platform() != null) {
            return this.inner().platform().cpu();
        } else {
            return 0;
        }
    }

    @Override
    public ProvisioningState provisioningState() {
        return this.inner().provisioningState();
    }

    @Override
    public String getLogLink() {
        return this.getLogLinkAsync().toBlocking().single();
    }

    @Override
    public Observable<String> getLogLinkAsync() {
        return this.parent().manager().inner().builds()
            .getLogLinkAsync(this.parent().resourceGroupName(), this.parent().name(), this.buildId())
            .map(new Func1<BuildGetLogResultInner, String>() {
                @Override
                public String call(BuildGetLogResultInner buildGetLogResultInner) {
                    return buildGetLogResultInner.logLink();
                }
            });
    }

    @Override
    public BuildImpl withArchivingEnabled() {
        this.inner().withIsArchiveEnabled(true);
        return this;
    }

    @Override
    public BuildImpl withArchivingDisabled() {
        this.inner().withIsArchiveEnabled(false);
        return this;
    }

    @Override
    public Update update() {
        return this;
    }

    @Override
    public Observable<Build> createResourceAsync() {
        final BuildImpl self = this;
        return this.parent().manager().inner().registries()
            .queueBuildAsync(this.parent().resourceGroupName(), this.parent().name(), this.quickBuildRequest)
            .map(new Func1<BuildInner, Build>() {
                @Override
                public Build call(BuildInner buildInner) {
                    self.setInner(buildInner);
                    return self;
                }
            });
    }

    @Override
    public Observable<Build> updateResourceAsync() {
        final BuildImpl self = this;
        return this.parent().manager().inner().builds()
            .updateAsync(this.parent().resourceGroupName(), this.parent().name(), this.buildId(), this.inner().isArchiveEnabled())
            .map(new Func1<BuildInner, Build>() {
                @Override
                public Build call(BuildInner buildInner) {
                    self.setInner(buildInner);
                    return self;
                }
            });
    }

    @Override
    public Observable<Void> deleteResourceAsync() {
        return null;
    }

    @Override
    protected Observable<BuildInner> getInnerAsync() {
        return this.parent().manager().inner().builds()
            .getAsync(this.parent().resourceGroupName(), this.parent().name(), this.buildId());
    }

    @Override
    public BuildImpl withOSType(OsType osType) {
        if (this.quickBuildRequest().platform() == null) {
            this.quickBuildRequest().withPlatform(new PlatformProperties());
        }
        this.quickBuildRequest().platform().withOsType(osType);
        return this;
    }

    @Override
    public BuildImpl withSourceLocation(String sourceLocation) {
        this.quickBuildRequest().withSourceLocation(sourceLocation);
        return this;
    }

    @Override
    public BuildImpl withDockerFilePath(String dockerFilePath) {
        this.quickBuildRequest().withDockerFilePath(dockerFilePath);
        return this;
    }

    @Override
    public BuildArgumentImpl defineBuildArgument(String name) {
        return new BuildArgumentImpl(this, name);
    }

    @Override
    public BuildImpl withCpuCoresCount(int count) {
        if (this.quickBuildRequest().platform() == null) {
            this.quickBuildRequest().withPlatform(new PlatformProperties());
        }
        this.quickBuildRequest().platform().withCpu(count);
        return this;
    }

    @Override
    public BuildImpl withBuildTimeoutInSeconds(int buildTimeoutInSeconds) {
        this.quickBuildRequest().withTimeout(buildTimeoutInSeconds);
        return this;
    }

    @Override
    public BuildImpl withImageNames(String... imageNames) {
        this.quickBuildRequest().withImageNames(Arrays.asList(imageNames));
        return this;
    }

    @Override
    public BuildImpl withImageCacheEnabled() {
        this.quickBuildRequest().withNoCache(false);
        return this;
    }

    @Override
    public BuildImpl withImageCacheDisabled() {
        this.quickBuildRequest().withNoCache(true);
        return this;
    }

    @Override
    public BuildImpl withImagePushEnabled() {
        this.quickBuildRequest().withIsPushEnabled(true);
        return this;
    }

    @Override
    public BuildImpl withImagePushDisabled() {
        this.quickBuildRequest().withIsPushEnabled(false);
        return this;
    }
}
