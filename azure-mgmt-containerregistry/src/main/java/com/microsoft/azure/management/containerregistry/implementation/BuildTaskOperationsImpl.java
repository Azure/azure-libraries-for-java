/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerregistry.BuildTask;
import com.microsoft.azure.management.containerregistry.BuildTaskOperations;
import com.microsoft.azure.management.containerregistry.SourceRepositoryProperties;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

/**
 * Represents a build task collection of operations associated with a container registry.
 */
@LangDefinition
public class BuildTaskOperationsImpl implements BuildTaskOperations {
    private final RegistryImpl containerRegistry;

    BuildTaskOperationsImpl(RegistryImpl containerRegistry) {
        this.containerRegistry = containerRegistry;
    }

    @Override
    public BuildTask.DefinitionStages.Blank define(String buildTaskName) {
        return new BuildTaskImpl(buildTaskName, containerRegistry, new BuildTaskInner());
    }

    @Override
    public BuildTask get(String buildTaskName) {
        return this.getAsync(buildTaskName).toBlocking().single();
    }

    @Override
    public Observable<BuildTask> getAsync(final String buildTaskName) {
        final BuildTaskOperationsImpl self = this;
        if (this.containerRegistry == null) {
            return null;
        }
        return this.containerRegistry.manager().inner().buildTasks()
            .getAsync(this.containerRegistry.resourceGroupName(), this.containerRegistry.name(), buildTaskName)
            .map(new Func1<BuildTaskInner, BuildTask>() {
                @Override
                public BuildTask call(BuildTaskInner buildTaskInner) {
                    return new BuildTaskImpl(buildTaskName, self.containerRegistry, buildTaskInner);
                }
            });
    }

    @Override
    public SourceRepositoryProperties getSourceRepositoryProperties(String buildTaskName) {
        return this.getSourceRepositoryPropertiesAsync(buildTaskName).toBlocking().single();
    }

    @Override
    public Observable<SourceRepositoryProperties> getSourceRepositoryPropertiesAsync(String buildTaskName) {
        if (this.containerRegistry == null) {
            return null;
        }
        return this.containerRegistry.manager().inner().buildTasks()
            .listSourceRepositoryPropertiesAsync(this.containerRegistry.resourceGroupName(), this.containerRegistry.name(), buildTaskName)
            .map(new Func1<SourceRepositoryPropertiesInner, SourceRepositoryProperties>() {
                @Override
                public SourceRepositoryProperties call(SourceRepositoryPropertiesInner sourceRepositoryPropertiesInner) {
                    return new SourceRepositoryPropertiesImpl(sourceRepositoryPropertiesInner);
                }
            });
    }

    @Override
    public void delete(String buildTaskName) {
        this.containerRegistry.manager().inner().buildTasks()
            .delete(this.containerRegistry.resourceGroupName(), this.containerRegistry.name(), buildTaskName);
    }

    @Override
    public Completable deleteAsync(String buildTaskName) {
        return this.containerRegistry.manager().inner().buildTasks()
            .deleteAsync(this.containerRegistry.resourceGroupName(), this.containerRegistry.name(), buildTaskName).toCompletable();
    }

    @Override
    public PagedList<BuildTask> list() {
        final BuildTaskOperationsImpl self = this;
        final PagedListConverter<BuildTaskInner, BuildTask> converter = new PagedListConverter<BuildTaskInner, BuildTask>() {
            @Override
            public Observable<BuildTask> typeConvertAsync(BuildTaskInner inner) {
                return Observable.just((BuildTask) new BuildTaskImpl(inner.name(), self.containerRegistry, inner));
            }
        };

        return converter.convert(this.containerRegistry.manager().inner().buildTasks()
            .list(this.containerRegistry.resourceGroupName(), this.containerRegistry.name()));
    }

    @Override
    public Observable<BuildTask> listAsync() {
        final BuildTaskOperationsImpl self = this;

        return this.containerRegistry.manager().inner().buildTasks()
            .listAsync(this.containerRegistry.resourceGroupName(), this.containerRegistry.name())
            .flatMap(new Func1<Page<BuildTaskInner>, Observable<BuildTaskInner>>() {
                @Override
                public Observable<BuildTaskInner> call(Page<BuildTaskInner> buildTaskInnerPage) {
                    return Observable.from(buildTaskInnerPage.items());
                }
            }).map(new Func1<BuildTaskInner, BuildTask>() {
                @Override
                public BuildTask call(BuildTaskInner inner) {
                    return new BuildTaskImpl(inner.name(), self.containerRegistry, inner);
                }
            });
    }

    @Override
    public PagedList<BuildTask> listWithFilter(String filter) {
        final BuildTaskOperationsImpl self = this;
        final PagedListConverter<BuildTaskInner, BuildTask> converter = new PagedListConverter<BuildTaskInner, BuildTask>() {
            @Override
            public Observable<BuildTask> typeConvertAsync(BuildTaskInner inner) {
                return Observable.just((BuildTask) new BuildTaskImpl(inner.name(), self.containerRegistry, inner));
            }
        };

        return converter.convert(this.containerRegistry.manager().inner().buildTasks()
            .list(this.containerRegistry.resourceGroupName(), this.containerRegistry.name(), filter, null));
    }

    @Override
    public Observable<BuildTask> listWithFilterAsync(String filter) {
        final BuildTaskOperationsImpl self = this;

        return this.containerRegistry.manager().inner().buildTasks()
            .listAsync(this.containerRegistry.resourceGroupName(), this.containerRegistry.name(), filter, null)
            .flatMap(new Func1<Page<BuildTaskInner>, Observable<BuildTaskInner>>() {
                @Override
                public Observable<BuildTaskInner> call(Page<BuildTaskInner> buildTaskInnerPage) {
                    return Observable.from(buildTaskInnerPage.items());
                }
            }).map(new Func1<BuildTaskInner, BuildTask>() {
                @Override
                public BuildTask call(BuildTaskInner inner) {
                    return new BuildTaskImpl(inner.name(), self.containerRegistry, inner);
                }
            });
    }
}
