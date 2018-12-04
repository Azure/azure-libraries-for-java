/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry.implementation;
//
//import com.microsoft.azure.management.apigeneration.LangDefinition;
//import com.microsoft.azure.management.containerregistry.BuildStep;
//import com.microsoft.azure.management.containerregistry.BuildTask;
//import com.microsoft.azure.management.containerregistry.DockerBuildStepUpdateParameters;
//import com.microsoft.azure.management.containerregistry.ProvisioningState;
//import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
//import rx.Observable;
//import rx.functions.Func1;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
/**
 * Implementation for Build Step.
 */
public class BuildStepImpl {

}
//@LangDefinition
//public class BuildStepImpl
//    extends ExternalChildResourceImpl<BuildStep, BuildStepInner, BuildTaskImpl, BuildTask>
//    implements
//        BuildStep,
//        BuildStep.BuildTaskBuildStepsDefinition,
//        BuildStep.Update {
//    protected BuildStepImpl(String name, BuildTaskImpl parent, BuildStepInner innerObject) {
//        super(name, parent, innerObject);
//    }
//
//    private DockerBuildStepUpdateParameters dockerBuildStepUpdateParameters;
//
//    private DockerBuildStepUpdateParameters dockerBuildStepUpdateParameters() {
//        if (this.dockerBuildStepUpdateParameters == null) {
//            this.dockerBuildStepUpdateParameters = new DockerBuildStepUpdateParameters();
//        }
//        return this.dockerBuildStepUpdateParameters;
//    }
//
//    @Override
//    public String id() {
//        return this.inner().id();
//    }
//
//    @Override
//    public ProvisioningState provisioningState() {
//        if (this.inner().properties() != null) {
//            return this.inner().properties().provisioningState();
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public BuildStepImpl withBranch(String branch) {
//        this.dockerBuildStepUpdateParameters().withBranch(branch);
//        return this;
//    }
//
//    @Override
//    public BuildStepImpl withContextPath(String contextPath) {
//        this.dockerBuildStepUpdateParameters().withContextPath(contextPath);
//        return this;
//    }
//
//    @Override
//    public BuildStepImpl withDockerFilePath(String dockerFilePath) {
//        this.dockerBuildStepUpdateParameters().withDockerFilePath(dockerFilePath);
//        return this;
//    }
//
//    @Override
//    public BuildStepImpl withImageNames(String... imageNames) {
//        this.dockerBuildStepUpdateParameters().withImageNames(Arrays.asList(imageNames));
//        return this;
//    }
//
//    @Override
//    public BuildStepImpl withImageCacheEnabled() {
//        this.dockerBuildStepUpdateParameters().withNoCache(false);
//        return this;
//    }
//
//    @Override
//    public BuildStepImpl withImageCacheDisabled() {
//        this.dockerBuildStepUpdateParameters().withNoCache(true);
//        return this;
//    }
//
//    @Override
//    public BuildStepImpl withImagePushEnabled() {
//        this.dockerBuildStepUpdateParameters().withIsPushEnabled(true);
//        return this;
//    }
//
//    @Override
//    public BuildStepImpl withImagePushDisabled() {
//        this.dockerBuildStepUpdateParameters().withIsPushEnabled(false);
//        return this;
//    }
//
//    @Override
//    public BuildStepImpl buildArgument(String name, String value, boolean secrecy) {
//        if (this.dockerBuildStepUpdateParameters().buildArguments() == null) {
//            this.dockerBuildStepUpdateParameters().withBuildArguments(new ArrayList<BuildArgumentInner>());
//        }
//        this.dockerBuildStepUpdateParameters().buildArguments().add(new BuildArgumentInner()
//            .withName(name)
//            .withValue(value)
//            .withIsSecret(secrecy)
//        );
//        return this;
//    }
//
//    @Override
//    public Update update() {
//        this.setPendingOperation(PendingOperation.ToBeUpdated);
//        return this;
//    }
//
//    @Override
//    public Observable<BuildStep> createResourceAsync() {
//        final BuildStepImpl self = this;
//        return this.parent().parent().manager().inner().buildSteps()
//            .createAsync(this.parent().parent().resourceGroupName(), this.parent().parent().name(), this.parent().name(), this.name())
//            .flatMap(new Func1<BuildStepInner, Observable<BuildStep>>() {
//                @Override
//                public Observable<BuildStep> call(BuildStepInner buildStepInner) {
//                    return self.updateResourceAsync();
//                }
//            });
//    }
//
//    @Override
//    public Observable<BuildStep> updateResourceAsync() {
//        final BuildStepImpl self = this;
//        return this.parent().parent().manager().inner().buildSteps()
//            .updateAsync(this.parent().parent().resourceGroupName(), this.parent().parent().name(), this.parent().name(), this.name(),
//                new BuildStepUpdateParametersInner().withProperties(self.dockerBuildStepUpdateParameters))
//            .map(new Func1<BuildStepInner, BuildStep>() {
//                @Override
//                public BuildStep call(BuildStepInner buildStepInner) {
//                    self.setInner(buildStepInner);
//                    self.dockerBuildStepUpdateParameters = null;
//                    return self;
//                }
//            });
//    }
//
//    @Override
//    public Observable<Void> deleteResourceAsync() {
//        return this.parent().parent().manager().inner().buildSteps()
//            .deleteAsync(this.parent().parent().resourceGroupName(), this.parent().parent().name(), this.parent().name(), this.name());
//    }
//
//    @Override
//    protected Observable<BuildStepInner> getInnerAsync() {
//        return this.parent().parent().manager().inner().buildSteps()
//            .getAsync(this.parent().parent().resourceGroupName(), this.parent().parent().name(), this.parent().name(), this.name());
//    }
//}
