/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources;

import com.azure.core.http.rest.PagedIterable;
import com.azure.core.management.PagedList;
import com.azure.management.RestClient;
import com.azure.management.resources.core.TestBase;
import com.azure.management.resources.implementation.ResourceManager;
import org.junit.Assert;
import org.junit.Test;

public class SubscriptionsTests extends TestBase {
    protected static ResourceManager.Authenticated resourceManager;

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        resourceManager = ResourceManager
                .authenticate(restClient);
    }

    @Override
    protected void cleanUpResources() {

    }

    @Test
    public void canListSubscriptions() throws Exception {
        PagedIterable<Subscription> subscriptions = resourceManager.subscriptions().list();
        Assert.assertTrue(subscriptions.size() > 0);
    }

    @Test
    public void canListLocations() throws Exception {
        PagedIterable<Location> locations = resourceManager.subscriptions().list().get(0).listLocations();
        Assert.assertTrue(locations.size() > 0);
    }
}
