/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.arm.collection.SupportsCreating;
import com.microsoft.azure.arm.collection.SupportsDeletingById;
import com.microsoft.azure.arm.collection.SupportsListing;
import com.microsoft.azure.arm.resources.collection.SupportsGettingById;
import com.microsoft.azure.arm.resources.collection.SupportsListingByResourceGroup;
import com.microsoft.azure.management.apigeneration.Fluent;

/**
 * Entry point to policy assignment management API.
 */
@Fluent
public interface PolicyAssignments extends
        SupportsListing<PolicyAssignment>,
        SupportsListingByResourceGroup<PolicyAssignment>,
        SupportsGettingById<PolicyAssignment>,
        SupportsCreating<PolicyAssignment.DefinitionStages.Blank>,
        SupportsDeletingById {
    /**
     * List policy assignments of the resource.
     *
     * @param resourceId the ID of the resource
     * @return the list of policy assignments
     */
    PagedList<PolicyAssignment> listByResource(final String resourceId);
}
