/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.containerregistry.Task;
import com.microsoft.azure.management.containerregistry.Tasks;

class TasksImpl implements Tasks {

    private final ContainerRegistryManager registryManager;

    TasksImpl(ContainerRegistryManager registryManager) {
        this.registryManager = registryManager;
    }
    @Override
    public Task.DefinitionStages.Blank define(String name) {
        return new TaskImpl(this.registryManager.inner().tasks(), name, new TaskInner());
    }
}
