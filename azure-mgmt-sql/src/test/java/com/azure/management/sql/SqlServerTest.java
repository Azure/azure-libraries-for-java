/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.sql;

import com.azure.management.RestClient;
import com.azure.management.RestClientBuilder;
import com.azure.management.resources.core.TestBase;
import com.azure.management.resources.fluentcore.utils.SdkContext;
import com.azure.management.resources.implementation.ResourceManager;
import com.azure.management.sql.implementation.SqlServerManager;
import com.azure.management.storage.implementation.StorageManager;

public abstract class SqlServerTest extends TestBase {
    protected static ResourceManager resourceManager;
    protected static SqlServerManager sqlServerManager;
    protected static StorageManager storageManager;
    protected static String RG_NAME = "";
    protected static String SQL_SERVER_NAME = "";

    @Override
    protected RestClient buildRestClient(RestClientBuilder builder, boolean isMocked) {
//        if (!isMocked) {
//        return super.buildRestClient(builder.withReadTimeout(150, TimeUnit.SECONDS) , isMocked);
        return super.buildRestClient(builder, isMocked);
    }

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        RG_NAME = generateRandomResourceName("rgsql", 20);
        SQL_SERVER_NAME = generateRandomResourceName("javasqlserver", 20);

        resourceManager = ResourceManager
                .authenticate(restClient)
                .withSubscription(defaultSubscription);

        sqlServerManager = SqlServerManager
                .authenticate(restClient, domain, defaultSubscription);

        storageManager = StorageManager
            .authenticate(restClient, defaultSubscription);
    }

    @Override
    protected void cleanUpResources() {
        SdkContext.sleep(1000);
        resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
    }
}
