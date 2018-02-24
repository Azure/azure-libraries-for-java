/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.datalake.store;

import com.microsoft.azure.management.datalake.store.implementation.DataLakeStoreAccountManagementClientImpl;
import com.microsoft.azure.management.resources.core.TestBase;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.implementation.ResourceManager;
import com.microsoft.rest.RestClient;

public class DataLakeStoreManagementTest extends TestBase {
    protected static ResourceManager resourceManagementClient;
    protected static DataLakeStoreAccountManagementClientImpl dataLakeStoreAccountManagementClient;
    protected static Region environmentLocation;
    protected static String resourceGroupName;

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain)
    {
        environmentLocation = Region.US_EAST2;
        resourceGroupName = generateRandomResourceName("adlsrg",15);

        // Create the resource group
        resourceManagementClient = ResourceManager
                .authenticate(restClient)
                .withSubscription(defaultSubscription);

        resourceManagementClient.resourceGroups()
                .define(resourceGroupName)
                .withRegion(environmentLocation)
                .create();

        // Create the ADLA client
        dataLakeStoreAccountManagementClient = new DataLakeStoreAccountManagementClientImpl(restClient);
        dataLakeStoreAccountManagementClient.withSubscriptionId(defaultSubscription);
    }

    @Override
    protected void cleanUpResources() {
        resourceManagementClient.resourceGroups().deleteByName(resourceGroupName);
    }

}