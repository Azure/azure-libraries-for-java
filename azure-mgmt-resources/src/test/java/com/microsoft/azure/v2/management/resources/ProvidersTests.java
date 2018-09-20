/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources;

import com.microsoft.azure.v2.AzureEnvironment;
import com.microsoft.azure.v2.management.resources.core.TestBase;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.v2.management.resources.implementation.ResourceManager;
import com.microsoft.rest.v2.http.HttpPipeline;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ProvidersTests extends TestBase {
    protected static ResourceManager resourceManager;

    @Override
    protected void initializeClients(HttpPipeline pipeline, String defaultSubscription, String domain, AzureEnvironment environment) {
        resourceManager = ResourceManager
                .authenticate(pipeline, environment)
                .withSubscription(defaultSubscription);
    }

    @Override
    protected void cleanUpResources() {
    }

    @Test
    public void canUnregisterAndRegisterProvider() throws Exception {
        List<Provider> providers = resourceManager.providers().list();
        int size = providers.size();
        Assert.assertTrue(size > 0);
        Provider provider = providers.get(0);
        resourceManager.providers().unregister(provider.namespace());
        provider = resourceManager.providers().getByName(provider.namespace());
        while (provider.registrationState().equals("Unregistering")) {
            SdkContext.sleep(5000);
            provider = resourceManager.providers().getByName(provider.namespace());
        }
        resourceManager.providers().register(provider.namespace());
        while (provider.registrationState().equals("Unregistered")
                || provider.registrationState().equalsIgnoreCase("Registering")) {
            SdkContext.sleep(5 * 1000);
            provider = resourceManager.providers().getByName(provider.namespace());
        }
        Assert.assertEquals("Registered", provider.registrationState());
        List<ProviderResourceType> resourceTypes = provider.resourceTypes();
        Assert.assertTrue(resourceTypes.size() > 0);
    }
}
