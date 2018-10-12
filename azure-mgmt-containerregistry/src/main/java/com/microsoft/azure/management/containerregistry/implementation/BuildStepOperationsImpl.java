/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerregistry.BuildStep;
import com.microsoft.azure.management.containerregistry.BuildStepOperations;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

/**
 * Represents a build task collection of operations associated with the build steps.
 */
@LangDefinition
public class BuildStepOperationsImpl implements BuildStepOperations {
    private final RegistryImpl containerRegistry;
    private final BuildTaskImpl buildTask;

    BuildStepOperationsImpl(BuildTaskImpl buildTask) {
        this.buildTask = buildTask;
        this.containerRegistry = buildTask.parent();
    }

    @Override
    public BuildStep.BuildTaskBuildStepsDefinitionStages.Blank define(String buildStepName) {
        BuildStepImpl buildStep = new BuildStepImpl(buildStepName, this.buildTask, new BuildStepInner());
        buildStep.setPendingOperation(ExternalChildResourceImpl.PendingOperation.ToBeCreated);
        return buildStep;
    }

    @Override
    public BuildStep get(String buildStepName) {
        return this.getAsync(buildStepName).toBlocking().single();
    }

    @Override
    public Observable<BuildStep> getAsync(final String buildStepName) {
        final BuildStepOperationsImpl self = this;
        return this.containerRegistry.manager().inner().buildSteps()
            .getAsync(this.containerRegistry.resourceGroupName(), this.containerRegistry.name(), this.buildTask.name(), buildStepName)
            .map(new Func1<BuildStepInner, BuildStep>() {
                @Override
                public BuildStep call(BuildStepInner buildStepInner) {
                    return new BuildStepImpl(buildStepName, self.buildTask, buildStepInner);
                }
            });
    }

    @Override
    public void delete(String buildStepName) {
        this.deleteAsync(buildStepName);
    }

    @Override
    public Completable deleteAsync(String buildStepName) {
        return this.containerRegistry.manager().inner().buildSteps()
            .deleteAsync(this.containerRegistry.resourceGroupName(), this.containerRegistry.name(), this.buildTask.name(), buildStepName)
            .toCompletable();
    }

    @Override
    public PagedList<BuildStep> list() {
        final BuildStepOperationsImpl self = this;
        final PagedListConverter<BuildStepInner, BuildStep> converter = new PagedListConverter<BuildStepInner, BuildStep>() {
            @Override
            public Observable<BuildStep> typeConvertAsync(BuildStepInner inner) {
                return Observable.just((BuildStep) new BuildStepImpl(inner.name(), self.buildTask, inner));
            }
        };

        return converter.convert(this.containerRegistry.manager().inner().buildSteps()
            .list(this.containerRegistry.resourceGroupName(), this.containerRegistry.name(), this.buildTask.name()));
    }

    @Override
    public Observable<BuildStep> listAsync() {
        final BuildStepOperationsImpl self = this;

        return this.containerRegistry.manager().inner().buildSteps()
            .listAsync(this.containerRegistry.resourceGroupName(), this.containerRegistry.name(), this.buildTask.name())
            .flatMap(new Func1<Page<BuildStepInner>, Observable<BuildStepInner>>() {
                @Override
                public Observable<BuildStepInner> call(Page<BuildStepInner> buildStepInnerPage) {
                    return Observable.from(buildStepInnerPage.items());
                }
            }).map(new Func1<BuildStepInner, BuildStep>() {
                @Override
                public BuildStep call(BuildStepInner inner) {
                    return new BuildStepImpl(inner.name(), self.buildTask, inner);
                }
            });
    }
}
