/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources.fluentcore.arm.implementation;

import com.azure.management.RestClient;
import com.azure.management.RestClientBuilder;
import com.azure.management.resources.implementation.ResourceManager;

/**
 * Base class for Azure resource managers.
 */
public abstract class ManagerBase {

    private ResourceManager resourceManager;
    private final String subscriptionId;
    private final RestClient restClient;

    protected ManagerBase(RestClient restClient, String subscriptionId) {
        this.restClient = restClient;
        if (restClient != null) {
            this.resourceManager = ResourceManager.authenticate(restClient).withSubscription(subscriptionId);
        }
        this.subscriptionId = subscriptionId;
    }

    /**
     * @return the ID of the subscription the manager is working with
     */
    public String getSubscriptionId() {
        return this.subscriptionId;
    }

    protected final void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    /**
     * @return the {@link ResourceManager} associated with this manager
     */
    public ResourceManager getResourceManager() {
        return this.resourceManager;
    }

    public RestClientBuilder newRestClientBuilder() {
        return restClient.newBuilder();
    }
}
