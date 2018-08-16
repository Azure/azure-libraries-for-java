/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.graphrbac;

import com.microsoft.azure.v2.management.graphrbac.implementation.GraphRbacManager;
import com.microsoft.azure.v2.management.resources.core.TestBase;
import com.microsoft.azure.v2.management.resources.implementation.ResourceManager;
import com.microsoft.rest.v2.http.HttpPipeline;

/**
 * The base for storage manager tests.
 */
public abstract class GraphRbacManagementTest extends TestBase {
    protected static GraphRbacManager graphRbacManager;
    protected static ResourceManager resourceManager;

    @Override
    protected void initializeClients(HttpPipeline httpPipeline, String defaultSubscription, String domain) {
        graphRbacManager = GraphRbacManager.authenticate(httpPipeline, domain);
        resourceManager = ResourceManager.authenticate(httpPipeline).withSubscription(defaultSubscription);
    }

    @Override
    protected void cleanUpResources() {
    }
}
