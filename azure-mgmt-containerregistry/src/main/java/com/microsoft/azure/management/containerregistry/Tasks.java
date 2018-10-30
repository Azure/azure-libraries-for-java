/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.containerregistry.implementation.TasksInner;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import rx.Completable;
import rx.Observable;


/**
 * Interface to define the Tasks collection.
 */
@Fluent()
@Beta
public interface Tasks extends
        HasInner<TasksInner>,
        SupportsCreating<Task.DefinitionStages.Blank> {
    /**
     * Lists the tasks in a registry asynchronously.
     *
     * @param resourceGroupName the resource group of the parent registry.
     * @param registryName the name of the parent registry.
     * @return the tasks with parent registry registry.
     */
    Observable<Task> listByRegistryAsync(String resourceGroupName, String registryName);

    /**
     * Lists the tasks in a registry.
     *
     * @param resourceGroupName the resource group of the parent registry.
     * @param registryName the name of the parent registry.
     * @return the tasks with parent registry registry.
     */
    PagedList<Task> listByRegistry(String resourceGroupName, String registryName);

    /**
     * Gets a task in a registry asynchronously.
     *
     * @param resourceGroupName the resource group of the parent registry.
     * @param registryName the name of the parent registry.
     * @param taskName the name of the task.
     * @param includeSecrets whether to include secrets or not.
     * @return the task
     */
    Observable<Task> getByRegistryAsync(String resourceGroupName, String registryName, String taskName, boolean includeSecrets);

    /**
     * Gets a task in a registry.
     *
     * @param resourceGroupName the resource group of the parent registry.
     * @param registryName the name of the parent registry.
     * @param taskName the name of the task.
     * @param includeSecrets whether to include secrets or not.
     * @return the task
     */
    Task getByRegistry(String resourceGroupName, String registryName, String taskName, boolean includeSecrets);

    /**
     * Deletes a task in a registry asynchronously.
     * @param resourceGroupName the resource group of the parent registry.
     * @param registryName the name of the parent registry.
     * @param taskName the name of the task.
     * @return the handle to the request.
     */
    Completable deleteByRegistryAsync(String resourceGroupName, String registryName, String taskName);

    /**
     * Deletes a task in a registry.
     * @param resourceGroupName the resource group of the parent registry.
     * @param registryName the name of the parent registry.
     * @param taskName the name of the task.
     */
    void deleteByRegistry(String resourceGroupName, String registryName, String taskName);
}
