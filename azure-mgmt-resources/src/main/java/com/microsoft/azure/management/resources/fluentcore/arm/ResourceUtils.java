/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.arm;

import com.microsoft.azure.arm.resources.ResourceUtilsCore;
import com.microsoft.azure.management.resources.Provider;
import com.microsoft.azure.management.resources.ProviderResourceType;

/**
 * Utility methods for Azure resource IDs.
 */
public final class ResourceUtils extends ResourceUtilsCore {
    private ResourceUtils() {
    }

    /**
     * Find out the default api version to make a REST request with from
     * the resource provider.
     *
     * @param id       the resource ID
     * @param provider the resource provider
     * @return the default api version to use
     */
    public static String defaultApiVersion(String id, Provider provider) {
        String resourceType = resourceTypeFromResourceId(id).toLowerCase();
        // Exact match
        for (ProviderResourceType prt : provider.resourceTypes()) {
            if (prt.resourceType().equalsIgnoreCase(resourceType)) {
                return prt.apiVersions().get(0);
            }
        }
        // child resource, e.g. sites/config
        for (ProviderResourceType prt : provider.resourceTypes()) {
            if (prt.resourceType().toLowerCase().contains("/" + resourceType)) {
                return prt.apiVersions().get(0);
            }
        }
        // look for parent
        String parentId = parentResourceIdFromResourceId(id);
        if (parentId != null) {
            return defaultApiVersion(parentId, provider);
        } else {
            // Fallback: use a random one, not guaranteed to work
            return provider.resourceTypes().get(0).apiVersions().get(0);
        }
    }
}