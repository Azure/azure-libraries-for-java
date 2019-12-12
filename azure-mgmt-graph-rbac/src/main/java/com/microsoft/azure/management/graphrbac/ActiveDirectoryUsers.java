/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.graphrbac;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.graphrbac.implementation.GraphRbacManager;
import com.microsoft.azure.management.graphrbac.implementation.UsersInner;
import com.azure.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.azure.management.resources.fluentcore.arm.collection.SupportsGettingByNameAsync;
import com.azure.management.resources.fluentcore.arm.models.HasManager;
import com.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.azure.management.resources.fluentcore.collection.SupportsDeletingById;
import com.azure.management.resources.fluentcore.collection.SupportsListing;
import com.azure.management.resources.fluentcore.model.HasInner;

/**
 * Entry point to AD user management API.
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.Graph.RBAC.Fluent")
public interface ActiveDirectoryUsers extends
        SupportsGettingById<ActiveDirectoryUser>,
        SupportsGettingByNameAsync<ActiveDirectoryUser>,
        SupportsListing<ActiveDirectoryUser>,
        SupportsCreating<ActiveDirectoryUser.DefinitionStages.Blank>,
        SupportsDeletingById,
        HasManager<GraphRbacManager>,
        HasInner<UsersInner> {
}
