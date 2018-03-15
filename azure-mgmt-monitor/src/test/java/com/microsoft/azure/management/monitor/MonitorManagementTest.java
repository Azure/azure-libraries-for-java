/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.compute.implementation.ComputeManager;
import com.microsoft.azure.management.eventhub.implementation.EventHubManager;
import com.microsoft.azure.management.monitor.implementation.MonitorManager;
import com.microsoft.azure.management.resources.core.TestBase;
import com.microsoft.azure.management.resources.implementation.ResourceManager;
import com.microsoft.azure.management.storage.implementation.StorageManager;
import com.microsoft.rest.RestClient;

/**
 * The base for Monitor manager tests.
 */
public class MonitorManagementTest extends TestBase {
    protected static ResourceManager resourceManager;
    protected static MonitorManager monitorManager;
    protected static ComputeManager computeManager;
    protected static StorageManager storageManager;
    protected static EventHubManager eventHubManager;

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {

        resourceManager = ResourceManager
                .authenticate(restClient)
                .withSubscription(defaultSubscription);

        monitorManager = MonitorManager
                .authenticate(restClient, defaultSubscription);

        computeManager = ComputeManager
                .authenticate(restClient, defaultSubscription);

        storageManager = StorageManager
                .authenticate(restClient, defaultSubscription);

        eventHubManager = EventHubManager
                .authenticate(restClient, defaultSubscription);
    }

    @Override
    protected void cleanUpResources() {
    }
}

