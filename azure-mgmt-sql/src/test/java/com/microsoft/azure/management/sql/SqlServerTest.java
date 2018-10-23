/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.sql;

import com.microsoft.azure.v2.management.resources.core.TestBase;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.v2.management.resources.implementation.ResourceManager;
import com.microsoft.azure.v2.management.sql.implementation.SqlServerManager;
import com.microsoft.azure.v2.management.storage.implementation.StorageManager;
import com.microsoft.rest.v2.http.HttpPipeline;
import com.microsoft.rest.v2.http.HttpPipelineBuilder;

import java.util.concurrent.TimeUnit;

public abstract class SqlServerTest extends TestBase {
    protected static ResourceManager resourceManager;
    protected static SqlServerManager sqlServerManager;
    protected static StorageManager storageManager;
    protected static String RG_NAME = "";
    protected static String SQL_SERVER_NAME = "";

    @Override
    protected HttpPipeline buildRestClient(HttpPipelineBuilder builder, boolean isMocked) {
        if (!isMocked) {
        return super.buildRestClient(builder.withTimeoutPolicy(150, TimeUnit.SECONDS) , isMocked);
    }
        return super.buildRestClient(builder, isMocked);
    }

    @Override
    protected void initializeClients(HttpPipeline httpPipeline, String defaultSubscription, String domain) {
        RG_NAME = generateRandomResourceName("rgsql", 20);
        SQL_SERVER_NAME = generateRandomResourceName("javasqlserver", 20);

        resourceManager = ResourceManager
                .authenticate(httpPipeline)
                .withSubscription(defaultSubscription);

        sqlServerManager = SqlServerManager
                .authenticate(httpPipeline, domain, defaultSubscription);

        storageManager = StorageManager
            .authenticate(httpPipeline, defaultSubscription);
    }

    @Override
    protected void cleanUpResources() {
        SdkContext.sleep(1000);
        resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
    }
}
