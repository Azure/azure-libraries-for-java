/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources;

import com.microsoft.azure.v2.management.resources.core.TestBase;
import com.microsoft.azure.v2.management.resources.implementation.ResourceManager;
import com.microsoft.rest.v2.http.HttpPipeline;

/**
 * The base for resource manager tests.
 */
class ResourceManagerTestBase extends TestBase {
    protected static ResourceManager resourceClient;

    @Override
    protected void initializeClients(HttpPipeline pipeline, String defaultSubscription, String domain) {
        resourceClient = ResourceManager
                .authenticate(pipeline)
                .withSubscription(defaultSubscription);
    }

    @Override
    protected void cleanUpResources() {

    }
}
