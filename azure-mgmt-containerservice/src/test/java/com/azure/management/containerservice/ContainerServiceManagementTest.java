/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.containerservice;

import com.azure.management.containerservice.implementation.ContainerServiceManager;
import com.microsoft.azure.management.resources.core.TestBase;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.implementation.ResourceManager;
import com.microsoft.rest.RestClient;

public class ContainerServiceManagementTest extends TestBase {
    protected static ResourceManager resourceManager;
    protected static ContainerServiceManager containerServiceManager;
    protected static String RG_NAME = "";

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        RG_NAME = generateRandomResourceName("javaacsrg", 15);

        resourceManager = ResourceManager
                .authenticate(restClient)
                .withSubscription(defaultSubscription);

       containerServiceManager = ContainerServiceManager
                .authenticate(restClient, defaultSubscription);

       resourceManager.resourceGroups()
               .define(RG_NAME)
               .withRegion(Region.US_EAST)
               .create();
    }

    @Override
    protected void cleanUpResources() {
        resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
    }
}