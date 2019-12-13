/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources.fluentcore.arm.models.implementation;

import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedIterable;
import com.azure.management.resources.ResourceGroup;

import java.util.List;

/**
 * Defines a list of resources paginated across resource groups.
 *
 * @param <E> the item type
 */
public abstract class GroupPagedIterable<E> extends PagedIterable<E> {

    /**
     * Creates an instance from a list of resource groups.
     *
     * @param resourceGroupList the list of resource groups
     */
    public GroupPagedIterable(PagedFlux<ResourceGroup> resourceGroupList) {
        super(null);
        // super(resourceGroupList);
    }

    /**
     * Override this method to implement how to list resources in a resource group.
     *
     * @param resourceGroupName the name of the resource group
     * @return the list of resources in this group.
     */
    public abstract List<E> listNextGroup(String resourceGroupName);
}
