/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.redis;


import com.microsoft.azure.management.compute.implementation.ComputeManager;
import com.microsoft.azure.management.monitor.implementation.MonitorManager;
import com.microsoft.azure.management.resources.core.TestBase;
import com.microsoft.azure.management.resources.implementation.ResourceManager;
import com.microsoft.rest.RestClient;

/**
 * The base for Monitor manager tests.
 */
public class MonitorManagementTest extends TestBase {
    protected static ResourceManager resourceManager;
    protected static MonitorManager monitorManager;
    protected static ComputeManager computeManager;

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {

        resourceManager = ResourceManager
                .authenticate(restClient)
                .withSubscription(defaultSubscription);

        monitorManager = MonitorManager
                .authenticate(restClient, defaultSubscription);

        computeManager = ComputeManager
                .authenticate(restClient, defaultSubscription);
    }

    @Override
    protected void cleanUpResources() {
    }
}

