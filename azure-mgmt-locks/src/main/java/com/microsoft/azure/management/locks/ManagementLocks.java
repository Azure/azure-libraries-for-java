/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.locks;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.locks.implementation.AuthorizationManager;
import com.microsoft.azure.management.locks.implementation.ManagementLocksInner;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsBatchDeletion;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsDeletingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsGettingById;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.SupportsListingByResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsBatchCreation;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsDeletingById;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * Entry point to management lock management.
 */
@Fluent()
@Beta(SinceVersion.V1_4_0)
public interface ManagementLocks extends
    SupportsListing<ManagementLock>,
    SupportsCreating<ManagementLock.DefinitionStages.Blank>,
    SupportsDeletingById,
    SupportsListingByResourceGroup<ManagementLock>,
    SupportsGettingByResourceGroup<ManagementLock>,
    SupportsGettingById<ManagementLock>,
    SupportsDeletingByResourceGroup,
    SupportsBatchCreation<ManagementLock>,
    SupportsBatchDeletion,
    HasManager<AuthorizationManager>,
    HasInner<ManagementLocksInner> {
}
