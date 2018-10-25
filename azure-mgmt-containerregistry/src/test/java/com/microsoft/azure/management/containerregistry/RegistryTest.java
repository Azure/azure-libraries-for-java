/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerregistry;

import com.microsoft.azure.management.containerregistry.implementation.ContainerRegistryManager;
import com.microsoft.azure.management.resources.core.TestBase;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.implementation.ResourceManager;
import com.microsoft.rest.RestClient;

/**
 * The base for storage manager tests.
 */
public abstract class RegistryTest extends TestBase {
    protected static ResourceManager resourceManager;
    protected static ContainerRegistryManager registryManager;

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        resourceManager = ResourceManager
                .authenticate(restClient)
                .withSubscription(defaultSubscription);

        registryManager = ContainerRegistryManager
                .authenticate(restClient, defaultSubscription);



    }


}
