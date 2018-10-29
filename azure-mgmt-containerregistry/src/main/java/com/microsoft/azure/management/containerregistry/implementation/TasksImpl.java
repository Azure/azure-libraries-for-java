/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.containerregistry.Task;
import com.microsoft.azure.management.containerregistry.Tasks;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

class TasksImpl implements Tasks {

    private final ContainerRegistryManager registryManager;

    TasksImpl(ContainerRegistryManager registryManager) {
        this.registryManager = registryManager;
    }
    @Override
    public Task.DefinitionStages.Blank define(String name) {
        return new TaskImpl(this.registryManager, name);
    }

    @Override
    public Observable<Task> listByRegistryAsync(String resourceGroupName, String registryName) {
        return this.registryManager.inner().tasks().listAsync(resourceGroupName, registryName)
                .flatMapIterable(new Func1<Page<TaskInner>, Iterable<TaskInner>>() {
                    @Override
                    public Iterable<TaskInner> call(Page<TaskInner> page) {
                        return page.items();
                    }
                })
                .map(new Func1<TaskInner, Task>() {
                    @Override
                    public Task call(TaskInner inner) {
                        return wrapModel(inner);
                    }
                });
    }

    @Override
    public PagedList<Task> listByRegistry(String resourceGroupName, String registryName) {
        final TasksImpl self = this;
        return (new PagedListConverter<TaskInner, Task>() {
            @Override
            public Observable<Task> typeConvertAsync(final TaskInner inner) {
                return Observable.<Task>just(wrapModel(inner));
            }
        }).convert(self.inner().list(resourceGroupName, registryName));
    }

    @Override
    public Observable<Task> getByRegistryAsync(String resourceGroupName, String registryName, String taskName, boolean includeSecrets) {
        if (includeSecrets) {
            return this.registryManager.inner().tasks().getDetailsAsync(resourceGroupName, registryName, taskName).map(new Func1<TaskInner, Task>() {
                @Override
                public Task call(TaskInner taskInner) {
                    return new TaskImpl(registryManager, taskInner);
                }
            });
        } else {
            return this.registryManager.inner().tasks().getAsync(resourceGroupName, registryName, taskName).map(new Func1<TaskInner, Task>() {
                @Override
                public Task call(TaskInner taskInner) {
                    return new TaskImpl(registryManager, taskInner);
                }
            });
        }
    }

    @Override
    public Task getByRegistry(String resourceGroupName, String registryName, String taskName, boolean includeSecrets) {
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

    private TaskImpl wrapModel(TaskInner innerModel) {
        return new TaskImpl(this.registryManager, innerModel);
    }

    @Override
    public TasksInner inner() {
        return this.registryManager.inner().tasks();
    }
}
