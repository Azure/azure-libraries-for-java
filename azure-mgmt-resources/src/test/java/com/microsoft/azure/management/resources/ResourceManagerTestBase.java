package com.microsoft.azure.management.resources;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.resources.implementation.ResourceManager;
import com.microsoft.rest.LogLevel;

/**
 * The base for resource manager tests.
 */
abstract class ResourceManagerTestBase {
    protected static ResourceManager resourceClient;

    static void createClient() throws Exception {
        resourceClient = ResourceManager
                .configure()
                .withLogLevel(LogLevel.BODY_AND_HEADERS)
                .authenticate(
                new ApplicationTokenCredentials(
                        System.getenv("client-id"),
                        System.getenv("domain"),
                        System.getenv("secret"),
                        AzureEnvironment.AZURE)
        ).withSubscription(System.getenv("subscription-id"));
    }
}
