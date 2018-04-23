/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.arm.implementation;

import com.microsoft.azure.arm.resources.implementation.ManagerBaseCore;
import com.microsoft.azure.management.resources.implementation.ResourceManager;
import com.microsoft.rest.RestClient;

/**
 * Base class for Azure resource managers.
 */
public abstract class ManagerBase extends ManagerBaseCore {
    private ResourceManager resourceManager;

    protected ManagerBase(RestClient restClient, String subscriptionId) {
        super(subscriptionId);
        if (restClient != null) {
            this.resourceManager = ResourceManager.authenticate(restClient).withSubscription(subscriptionId);
        }
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
