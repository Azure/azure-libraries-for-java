/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.network;

import com.microsoft.azure.v2.AzureEnvironment;
import com.microsoft.azure.v2.management.network.implementation.NetworkManager;
import com.microsoft.azure.v2.management.resources.core.TestBase;
import com.microsoft.azure.v2.management.resources.implementation.ResourceManager;
import com.microsoft.rest.v2.http.HttpPipeline;

import java.io.IOException;

public class NetworkManagementTest extends TestBase {
    protected static ResourceManager resourceManager;
    protected static NetworkManager networkManager;
    protected static String RG_NAME = "";


    @Override
    protected void initializeClients(HttpPipeline pipeline, String defaultSubscription, String domain, AzureEnvironment environment) throws IOException {
        RG_NAME = generateRandomResourceName("javanwmrg", 15);

        resourceManager = ResourceManager
                .authenticate(pipeline, environment)
                .withSubscription(defaultSubscription);

        networkManager = NetworkManager
                .authenticate(pipeline, defaultSubscription, environment);
    }

    @Override
    protected void cleanUpResources() {
        resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
    }
}