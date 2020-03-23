/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.containerregistry.implementation;

import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.azure.management.containerregistry.RegistryTask;
import com.azure.management.containerregistry.RegistryTasks;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

@LangDefinition
class RegistryTasksImpl implements RegistryTasks {

    private final ContainerRegistryManager registryManager;

    RegistryTasksImpl(ContainerRegistryManager registryManager) {
        this.registryManager = registryManager;
    }
    @Override
    public RegistryTask.DefinitionStages.Blank define(String name) {
        return new RegistryTaskImpl(this.registryManager, name);
    }

    @Override
    public Observable<RegistryTask> listByRegistryAsync(String resourceGroupName, String registryName) {
        return this.registryManager.inner().tasks().listAsync(resourceGroupName, registryName)
                .flatMapIterable(new Func1<Page<TaskInner>, Iterable<TaskInner>>() {
                    @Override
                    public Iterable<TaskInner> call(Page<TaskInner> page) {
                        return page.items();
                    }
                })
                .map(new Func1<TaskInner, RegistryTask>() {
                    @Override
                    public RegistryTask call(TaskInner inner) {
                        return wrapModel(inner);
                    }
                });
    }

    @Override
    public PagedList<RegistryTask> listByRegistry(String resourceGroupName, String registryName) {
        final RegistryTasksImpl self = this;
        return (new PagedListConverter<TaskInner, RegistryTask>() {
            @Override
            public Observable<RegistryTask> typeConvertAsync(final TaskInner inner) {
                return Observable.<RegistryTask>just(wrapModel(inner));
            }
        }).convert(self.inner().list(resourceGroupName, registryName));
    }

    @Override
    public Observable<RegistryTask> getByRegistryAsync(String resourceGroupName, String registryName, String taskName, boolean includeSecrets) {
        if (includeSecrets) {
            return this.registryManager.inner().tasks().getDetailsAsync(resourceGroupName, registryName, taskName).map(new Func1<TaskInner, RegistryTask>() {
                @Override
                public RegistryTask call(TaskInner taskInner) {
                    return new RegistryTaskImpl(registryManager, taskInner);
                }
            });
        } else {
            return this.registryManager.inner().tasks().getAsync(resourceGroupName, registryName, taskName).map(new Func1<TaskInner, RegistryTask>() {
                @Override
                public RegistryTask call(TaskInner taskInner) {
                    return new RegistryTaskImpl(registryManager, taskInner);
                }
            });
        }
    }

    @Override
    public RegistryTask getByRegistry(String resourceGroupName, String registryName, String taskName, boolean includeSecrets) {
        return this.getByRegistryAsync(resourceGroupName, registryName, taskName, includeSecrets).toBlocking().last();
    }

    @Override
    public Completable deleteByRegistryAsync(String resourceGroupName, String registryName, String taskName) {
        return this.registryManager.inner().tasks().deleteAsync(resourceGroupName, registryName, taskName).toCompletable();
    }

    @Override
    public void deleteByRegistry(String resourceGroupName, String registryName, String taskName) {
        this.deleteByRegistryAsync(resourceGroupName, registryName, taskName).await();
    }

    private RegistryTaskImpl wrapModel(TaskInner innerModel) {
        return new RegistryTaskImpl(this.registryManager, innerModel);
    }

    @Override
    public TasksInner inner() {
        return this.registryManager.inner().tasks();
    }
}
