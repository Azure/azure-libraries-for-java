/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources;

import com.microsoft.azure.v2.AzureEnvironment;
import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.v2.management.resources.core.TestBase;
import com.microsoft.azure.v2.management.resources.implementation.ResourceManager;
import com.microsoft.rest.v2.http.HttpPipeline;
import org.junit.Assert;
import org.junit.Test;

public class TenantsTests extends TestBase {
    protected static ResourceManager.Authenticated resourceManager;

    @Override
    protected void initializeClients(HttpPipeline pipeline, String defaultSubscription, String domain, AzureEnvironment environment) {
        resourceManager = ResourceManager
                .authenticate(pipeline, environment);
    }

    @Override
    protected void cleanUpResources() {

    }

    @Test
    public void canListTenants() throws Exception {
        PagedList<Tenant> tenants = resourceManager.tenants().list();
        Assert.assertTrue(tenants.size() > 0);
    }
}
