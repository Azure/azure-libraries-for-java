/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.graphrbac;

import com.azure.management.RestClient;
import com.azure.management.graphrbac.implementation.GraphRbacManager;
import com.azure.management.resources.core.TestBase;
import com.azure.management.resources.implementation.ResourceManager;

/**
 * The base for storage manager tests.
 */
public abstract class GraphRbacManagementTest extends TestBase {
    protected static GraphRbacManager graphRbacManager;
    protected static ResourceManager resourceManager;

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        graphRbacManager = GraphRbacManager.authenticate(restClient, domain);
        resourceManager = ResourceManager.authenticate(restClient).withSubscription(defaultSubscription);
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8888");
        System.setProperty("https.proxyPort", "8888");
    }

    @Override
    protected void cleanUpResources() {
    }
}
