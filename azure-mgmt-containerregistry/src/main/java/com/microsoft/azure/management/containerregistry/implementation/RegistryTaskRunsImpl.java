/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.containerregistry.RegistryTaskRun;
import com.microsoft.azure.management.containerregistry.RegistryTaskRuns;

class RegistryTaskRunsImpl implements RegistryTaskRuns {

    private ContainerRegistryManager registryManager;

    RegistryTaskRunsImpl(ContainerRegistryManager registryManager) {
        this.registryManager = registryManager;
    }


    @Override
    public RegistryTaskRun.DefinitionStages.BlankFromRuns scheduleRun() {
        return new RegistryTaskRunImpl(this.registryManager.inner().registries());
    }
}
