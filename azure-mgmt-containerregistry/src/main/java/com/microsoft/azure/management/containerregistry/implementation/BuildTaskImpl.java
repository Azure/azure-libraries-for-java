/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry.implementation;
//
//import com.microsoft.azure.management.apigeneration.LangDefinition;
//import com.microsoft.azure.management.containerregistry.Build;
//import com.microsoft.azure.management.containerregistry.BuildTask;
//import com.microsoft.azure.management.containerregistry.BuildTaskStatus;
//import com.microsoft.azure.management.containerregistry.OsType;
//import com.microsoft.azure.management.containerregistry.PlatformProperties;
//import com.microsoft.azure.management.containerregistry.ProvisioningState;
//import com.microsoft.azure.management.containerregistry.Registry;
//import com.microsoft.azure.management.containerregistry.SourceControlAuthInfo;
//import com.microsoft.azure.management.containerregistry.SourceControlType;
//import com.microsoft.azure.management.containerregistry.TokenType;
//import com.microsoft.azure.management.resources.fluentcore.arm.Region;
//import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
//import org.joda.time.DateTime;
//import rx.Observable;
//import rx.functions.Func1;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
/**
 * Implementation for BuildTask.
 */
public class BuildTaskImpl {

}
//@LangDefinition
//public class BuildTaskImpl
//    extends ExternalChildResourceImpl<BuildTask, BuildTaskInner, RegistryImpl, Registry>
//    implements
//        BuildTask,
//        BuildTask.BuildTaskDefinition,
//        BuildTask.Update {
//
//    protected BuildTaskImpl(String name, RegistryImpl parent, BuildTaskInner innerObject) {
//        super(name, parent, innerObject);
//    }
//
//    @Override
//    public String id() {
//        return this.inner().id();
//    }
//
//
//    @Override
//    public String type() {
//        return this.inner().type();
//    }
//
//    @Override
//    public String regionName() {
//        return this.inner().location();
//    }
//
//    @Override
//    public Region region() {
//        return Region.fromName(this.inner().location());
//    }
//
//    @Override
//    public Map<String, String> tags() {
//        return Collections.unmodifiableMap(this.inner().getTags());
//    }
//
//    @Override
//    public String parentId() {
//        return this.parent() != null ? this.parent().id() : null;
//    }
//
//    @Override
//    public ProvisioningState provisioningState() {
//        return this.inner().provisioningState();
//    }
//
//    @Override
//    public DateTime creationDate() {
//        return this.inner().creationDate();
//    }
//
//    @Override
//    public String alias() {
//        return this.inner().alias();
//    }
//
//    @Override
//    public BuildTaskStatus status() {
//        return this.inner().status();
//    }
//
//    @Override
//    public int timeout() {
//        return this.inner().timeout();
//    }
//
//    @Override
//    public OsType osType() {
//        return this.inner().platform() != null ? this.inner().platform().osType() : null;
//    }
//
//    @Override
//    public int cpuCount() {
//        return this.inner().platform() != null ? this.inner().platform().cpu() : 0;
//    }
//
//    @Override
//    public SourceControlType sourceControlType() {
//        return this.inner().sourceRepository() != null ? this.inner().sourceRepository().sourceControlType() : null;
//    }
//
//    @Override
//    public String repositoryUrl() {
//        return this.inner().sourceRepository() != null ? this.inner().sourceRepository().repositoryUrl() : null;
//    }
//
//    @Override
//    public boolean isCommitTriggerEnabled() {
//        return this.inner().sourceRepository() != null ? this.inner().sourceRepository().isCommitTriggerEnabled() : false;
//    }
//
//    @Override
//    public TokenType authenticationTokenType() {
//        if (this.inner().sourceRepository() != null && this.inner().sourceRepository().sourceControlAuthProperties() != null) {
//            return this.inner().sourceRepository().sourceControlAuthProperties().tokenType();
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public String authenticationToken() {
//        if (this.inner().sourceRepository() != null && this.inner().sourceRepository().sourceControlAuthProperties() != null) {
//            return this.inner().sourceRepository().sourceControlAuthProperties().token();
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public String refreshToken() {
//        if (this.inner().sourceRepository() != null && this.inner().sourceRepository().sourceControlAuthProperties() != null) {
//            return this.inner().sourceRepository().sourceControlAuthProperties().refreshToken();
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public String scope() {
//        if (this.inner().sourceRepository() != null && this.inner().sourceRepository().sourceControlAuthProperties() != null) {
//            return this.inner().sourceRepository().sourceControlAuthProperties().scope();
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public int tokenExpirationTimeInSeconds() {
//        if (this.inner().sourceRepository() != null && this.inner().sourceRepository().sourceControlAuthProperties() != null) {
//            return this.inner().sourceRepository().sourceControlAuthProperties().expiresIn();
//        } else {
//            return 0;
//        }
//    }
//
//    @Override
//    public Build queueBuildTask() {
//        return this.parent().queuedBuilds().queueBuildTask(this.name());
//    }
//
//    @Override
//    public Observable<Build> queueBuildTaskAsync() {
//        return this.parent().queuedBuilds().queueBuildTaskAsync(this.name());
//    }
//
//    @Override
//    public BuildStepOperationsImpl buildSteps() {
//        return new BuildStepOperationsImpl(this);
//    }
//
//    @Override
//    public BuildTaskImpl withOSType(OsType osType) {
//        if (this.inner().platform() == null) {
//            this.inner().withPlatform(new PlatformProperties());
//        }
//        this.inner().platform().withOsType(osType);
//        return this;
//    }
//
//    @Override
//    public BuildTaskImpl withSourceControlType(SourceControlType sourceControlType) {
//        if (this.inner().sourceRepository() == null) {
//            this.inner().withSourceRepository(new SourceRepositoryPropertiesInner());
//        }
//        this.inner().sourceRepository().withSourceControlType(sourceControlType);
//        return this;
//    }
//
//    @Override
//    public BuildTaskImpl withRepositoryUrl(String repositoryUrl) {
//        if (this.inner().sourceRepository() == null) {
//            this.inner().withSourceRepository(new SourceRepositoryPropertiesInner());
//        }
//        this.inner().sourceRepository().withRepositoryUrl(repositoryUrl);
//        return this;
//    }
//
//    @Override
//    public BuildTaskImpl withAuthenticationTokenType(TokenType tokenType) {
//        if (this.inner().sourceRepository() == null) {
//            this.inner().withSourceRepository(new SourceRepositoryPropertiesInner());
//        }
//        if (this.inner().sourceRepository().sourceControlAuthProperties() == null) {
//            this.inner().sourceRepository().withSourceControlAuthProperties(new SourceControlAuthInfo());
//        }
//        this.inner().sourceRepository().sourceControlAuthProperties().withTokenType(tokenType);
//        return this;
//    }
//
//    @Override
//    public BuildTaskImpl withAuthenticationToken(String token) {
//        if (this.inner().sourceRepository() == null) {
//            this.inner().withSourceRepository(new SourceRepositoryPropertiesInner());
//        }
//        if (this.inner().sourceRepository().sourceControlAuthProperties() == null) {
//            this.inner().sourceRepository().withSourceControlAuthProperties(new SourceControlAuthInfo());
//        }
//        this.inner().sourceRepository().sourceControlAuthProperties().withToken(token);
//        return this;
//    }
//
//    @Override
//    public BuildTaskImpl withAlias(String alias) {
//        this.inner().withAlias(alias);
//        return this;
//    }
//
//    @Override
//    public BuildTaskImpl withAuthenticationRefreshToken(String refreshToken) {
//        if (this.inner().sourceRepository() == null) {
//            this.inner().withSourceRepository(new SourceRepositoryPropertiesInner());
//        }
//        if (this.inner().sourceRepository().sourceControlAuthProperties() == null) {
//            this.inner().sourceRepository().withSourceControlAuthProperties(new SourceControlAuthInfo());
//        }
//        this.inner().sourceRepository().sourceControlAuthProperties().withRefreshToken(refreshToken);
//        return this;
//    }
//
//    @Override
//    public BuildTaskImpl withTokenExpirationTimeInSeconds(int expirationTimeInSeconds) {
//        if (this.inner().sourceRepository() == null) {
//            this.inner().withSourceRepository(new SourceRepositoryPropertiesInner());
//        }
//        if (this.inner().sourceRepository().sourceControlAuthProperties() == null) {
//            this.inner().sourceRepository().withSourceControlAuthProperties(new SourceControlAuthInfo());
//        }
//        this.inner().sourceRepository().sourceControlAuthProperties().withExpiresIn(expirationTimeInSeconds);
//        return this;
//    }
//
//    @Override
//    public BuildTaskImpl withAuthenticationTokenTypeAccessScope(String scope) {
//        if (this.inner().sourceRepository() == null) {
//            this.inner().withSourceRepository(new SourceRepositoryPropertiesInner());
//        }
//        if (this.inner().sourceRepository().sourceControlAuthProperties() == null) {
//            this.inner().sourceRepository().withSourceControlAuthProperties(new SourceControlAuthInfo());
//        }
//        this.inner().sourceRepository().sourceControlAuthProperties().withScope(scope);
//        return this;
//    }
//
//    @Override
//    public BuildTaskImpl withCommitTriggerEnabled() {
//        if (this.inner().sourceRepository() == null) {
//            this.inner().withSourceRepository(new SourceRepositoryPropertiesInner());
//        }
//        this.inner().sourceRepository().withIsCommitTriggerEnabled(true);
//        return this;
//    }
//
//    @Override
//    public BuildTaskImpl withCommitTriggerDisabled() {
//        if (this.inner().sourceRepository() == null) {
//            this.inner().withSourceRepository(new SourceRepositoryPropertiesInner());
//        }
//        this.inner().sourceRepository().withIsCommitTriggerEnabled(false);
//        return this;
//    }
//
//    @Override
//    public BuildTaskImpl withCpuCoresCount(int count) {
//        if (this.inner().platform() == null) {
//            this.inner().withPlatform(new PlatformProperties());
//        }
//        this.inner().platform().withCpu(count);
//        return this;
//    }
//
//    @Override
//    public BuildTaskImpl withBuildTimeoutInSeconds(int buildTimeoutInSeconds) {
//        this.inner().withTimeout(buildTimeoutInSeconds);
//        return this;
//    }
//
//    @Override
//    public BuildTaskImpl withBuildTaskStatusEnabled() {
//        this.inner().withStatus(BuildTaskStatus.ENABLED);
//        return this;
//    }
//
//    @Override
//    public BuildTaskImpl withBuildTaskStatusDisabled() {
//        this.inner().withStatus(BuildTaskStatus.DISABLED);
//        return this;
//    }
//
//    @Override
//    public BuildTaskImpl withTags(Map<String, String> tags) {
//        if (tags != null) {
//            this.inner().withTags(new HashMap<String, String>());
//            for (Map.Entry<String, String> entry : inner().getTags().entrySet()) {
//                this.inner().getTags().put(entry.getKey(), entry.getValue());
//            }
//        }
//        return this;
//    }
//
//    @Override
//    public BuildTaskImpl withTag(String key, String value) {
//        if (key != null && value != null) {
//            ensureValidTags().put(key, value);
//        }
//        return this;
//    }
//
//    @Override
//    public BuildTaskImpl withoutTag(String key) {
//        if (key != null && this.inner().getTags() != null) {
//            this.inner().getTags().remove(key);
//        }
//        return this;
//    }
//
//    private Map<String, String> ensureValidTags() {
//        if (this.inner().getTags() == null) {
//            this.inner().withTags(new HashMap<String, String>());
//        }
//        return this.inner().getTags();
//    }
//
//    @Override
//    public Update update() {
//        return this;
//    }
//
//    @Override
//    public Observable<BuildTask> createResourceAsync() {
//        final BuildTaskImpl self = this;
//        this.inner().withLocation(this.parent().regionName());
//        return this.parent().manager().inner().buildTasks()
//            .createAsync(this.parent().resourceGroupName(), this.parent().name(), this.name(), this.inner())
//            .map(new Func1<BuildTaskInner, BuildTask>() {
//                @Override
//                public BuildTask call(BuildTaskInner buildTaskInner) {
//                    self.setInner(buildTaskInner);
//                    return self;
//                }
//            });
//    }
//
//    @Override
//    public Observable<BuildTask> updateResourceAsync() {
//        return createResourceAsync();
//    }
//
//    @Override
//    public Observable<Void> deleteResourceAsync() {
//        return this.parent().manager().inner().buildTasks()
//            .deleteAsync(this.parent().resourceGroupName(), this.parent().name(), this.name());
//    }
//
//    @Override
//    protected Observable<BuildTaskInner> getInnerAsync() {
//        return this.parent().manager().inner().buildTasks()
//            .getAsync(this.parent().resourceGroupName(), this.parent().name(), this.name());
//    }
//}
