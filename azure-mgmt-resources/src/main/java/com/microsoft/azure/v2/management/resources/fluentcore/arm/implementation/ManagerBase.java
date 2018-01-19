/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.fluentcore.arm.implementation;

import com.microsoft.azure.v2.management.resources.implementation.ResourceManager;
import com.microsoft.rest.v2.http.HttpPipeline;

/**
 * Base class for Azure resource managers.
 */
public abstract class ManagerBase {

    private ResourceManager resourceManager;
    private final String subscriptionId;

    protected ManagerBase(HttpPipeline pipeline, String subscriptionId) {
        if (pipeline != null) {
            this.resourceManager = ResourceManager.authenticate(pipeline).withSubscription(subscriptionId);
        }
        this.subscriptionId = subscriptionId;
    }

    /**
     * @return the ID of the subscription the manager is working with
     */
    public String subscriptionId() {
        return this.subscriptionId;
    }

    protected final void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    /**
     * @return the {@link ResourceManager} associated with this manager
     */
    public ResourceManager resourceManager() {
        return this.resourceManager;
    }
}
