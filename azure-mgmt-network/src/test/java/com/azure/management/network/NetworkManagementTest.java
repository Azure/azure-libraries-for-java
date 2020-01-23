/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.network;

import com.azure.management.network.implementation.NetworkManager;
import com.microsoft.azure.management.keyvault.implementation.KeyVaultManager;
import com.microsoft.azure.management.msi.implementation.MSIManager;
import com.microsoft.azure.management.resources.core.TestBase;
import com.microsoft.azure.management.resources.implementation.ResourceManager;
import com.microsoft.rest.RestClient;

public class NetworkManagementTest extends TestBase {
    protected static ResourceManager resourceManager;
    protected static NetworkManager networkManager;
    protected static KeyVaultManager keyVaultManager;
    protected static MSIManager msiManager;
    protected static String RG_NAME = "";

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        RG_NAME = generateRandomResourceName("javanwmrg", 15);

        resourceManager = ResourceManager
                .authenticate(restClient)
                .withSubscription(defaultSubscription);

        networkManager = NetworkManager
                .authenticate(restClient, defaultSubscription);

        keyVaultManager = KeyVaultManager
                .authenticate(restClient, domain, defaultSubscription);

        msiManager = MSIManager
                .authenticate(restClient, defaultSubscription);
    }

    @Override
    protected void cleanUpResources() {
        resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
    }
}